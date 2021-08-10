package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import static Data.GlobalInfo.*;
import Data.Circuits.*;
import Data.GlobalInfo;
import Util.Driver;
import Util.DriverInitInfo;

public class RaceModel {
	/*
	CIRCUIT NUMBERS:
	Circuit 1: Japan
	Circuit 2: US
	Circuit 3: Mexico
	Circuit 4: Abu Dhabi
	 */
	int circuit;
	DriverInitInfo[] driverInfos;
	int[] tyreCompounds;
	OvertakingModel overtakingModel;
	DNFModel dnfModel;
	PitStrategyModel pitModel;
	ArrayList<Driver> drivers;
	ArrayList<Object[]> retiredDrivers;
	private int safetyCarLapCounter;
	private boolean safetyCarActive;
	private boolean deploySafetyCar;

	public RaceModel(int circuit, int[] tyreCompounds, boolean oneStopPossible, boolean twoStopPossible, DriverInitInfo[] driverInfos) {
		this.circuit = circuit;
		safetyCarActive = false;
		deploySafetyCar = false;
		safetyCarLapCounter = 0;
		drivers = GlobalInfo.getDriverList();
		retiredDrivers = new ArrayList<>();
		switch (circuit) {
			case 1:
				totalLaps = Japan.NUM_LAPS;
				break;
			case 2:
				totalLaps = US.NUM_LAPS;
				break;
			case 3:
				totalLaps = Mexico.NUM_LAPS;
				break;
			case 4:
				totalLaps = AbuDhabi.NUM_LAPS;
				break;
			default:
				totalLaps = DEFAULT_NUM_LAPS;
				break;
		}
		this.tyreCompounds = tyreCompounds;
		this.driverInfos = driverInfos;
		overtakingModel = new OvertakingModel(circuit, drivers);
		dnfModel = new DNFModel();
		pitModel = new PitStrategyModel(circuit, totalLaps, tyreCompounds, oneStopPossible, twoStopPossible);
	}

	public void simulateRace() {
		init();

		loop();

		stop();
	}

	private void init() {
		// set DNF probabilities for each driver
		dnfModel.assignDNFProbability(drivers);

		// for each driver, set quali time & set their initial tyre compound/pit strategy
		// if tyre compound is not specified (drivers outside the top 10), randomly select from available compounds
		for (int i = 0; i < drivers.size(); i++) {
			drivers.get(i).init(driverInfos[i].getQualiTime(), driverInfos[i].getQualiPosition());
			if (driverInfos[i].getStartingTyreCompound() == 0) {
				drivers.get(i).setTyreCompound(tyreCompounds[new Random().nextInt(tyreCompounds.length)]);
			}
			else drivers.get(i).setTyreCompound(driverInfos[i].getStartingTyreCompound());
			pitModel.selectDriverStrategy(drivers.get(i).getLastName(), drivers.get(i).getTyreCompound());
		}

		// establish starting grid
		setGridPositions();
	}

	private void setGridPositions() {
		drivers.sort(Comparator.comparingInt(Driver::getQualiPosition));
		// now that the list of drivers is sorted, assign grid position using position variable (as this will be referred to in the race)
		for(int i = 0; i < drivers.size(); i++){
			drivers.get(i).setPosition(i + 1);
		}
	}

	private void loop() {
		// simulate actual race (laps)
		for(int i = 1; i <= totalLaps; i++){

			// check if there are any DNFs and if a safety car needs to be deployed (will be false if SC is already active)
			deploySafetyCar = dnfModel.checkDNFs(i, drivers, retiredDrivers, safetyCarActive);

			// pass safety car status to lap time simulator, check pit stops, simulate lap time
			simulateLapTimes(i, safetyCarActive);

			// drivers cannot overtake under the safety car
			if (!safetyCarActive) {
				overtakingModel.updateDriverList(drivers);
				overtakingModel.simulateOvertakes();
			}

			// see if the safety car should be active starting next lap
			safetyCarActive = safetyCarActive || deploySafetyCar;

			// if the safety car should be deployed, reset counter
			if (deploySafetyCar) safetyCarLapCounter = 0;

			// safety car only lasts for 5 laps  
			else if (safetyCarLapCounter >= 5) {
				safetyCarActive = false;
				safetyCarLapCounter = 0;
			}

			// keep track of how long the safety car has been active
			if (safetyCarActive) safetyCarLapCounter++;
		}
	}

	private void simulateLapTimes(int lapNum, boolean safetyCarActive){

		for(Driver driver : drivers){
			// check if driver needs to pit & change tyre
			int nextTyre = pitModel.checkTyre(driver.getLastName(), lapNum, driver.getTyreCompound());
			boolean addPitTime = false;
			if (nextTyre != 0) {
				driver.setTyreCompound(nextTyre);
				addPitTime = true;
			}

			driver.setLapNumber(lapNum);
			// reset tyre age if switching tyres, else continue
			driver.setTyreAge(addPitTime ? 0 : driver.getTyreAge() + 1);
			// if safety car is active, multiply lap times by safety car factor
			// add pit stop time if needed
			driver.setTotalRaceTime(driver.getTotalRaceTime() + (addPitTime ? pitModel.getPitStopTime(driver.getTeam()) : 0) + driver.getCorrectedLapTime() * (safetyCarActive ? SAFETY_CAR_FACTOR : 1));
		}
		Collections.sort(drivers);
	}

	private void stop() {
		// final sort, print out race results
		Collections.sort(drivers);
		System.out.println("Race Results:");
		for(int i = 1; i <= drivers.size(); i++){
			System.out.println(i + ". " + drivers.get(i - 1).getName());
		}
		for (Object[] retiredDriver : retiredDrivers) {
			System.out.println("R - " + ((Driver) retiredDriver[0]).getName() + " (Lap " + retiredDriver[1] + ")");
		}
	}
}
