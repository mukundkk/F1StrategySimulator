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
	FirstLapModel flModel;
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
		flModel = new FirstLapModel();
		dnfModel = new DNFModel();
		pitModel = new PitStrategyModel(oneStopPossible, twoStopPossible);
	}

	public void simulateRace() {
		init();

		loop();

		stop();
	}

	private void init() {
		// set DNF probabilities for each driver
		dnfModel.assignDNFProbability(drivers);

		// for each driver, set quali time & set their initial tyre compound
		// if tyre compound is not specified (drivers outside the top 10), randomly select from available compounds
		for (int i = 0; i < drivers.size(); i++) {
			drivers.get(i).init(driverInfos[i].getQualiTime(), driverInfos[i].getQualiPosition());
			if (driverInfos[i].getStartingTyreCompound() == 0) {
				drivers.get(i).setTyreCompound(tyreCompounds[new Random().nextInt(tyreCompounds.length)]);
			}
			else drivers.get(i).setTyreCompound(driverInfos[i].getStartingTyreCompound());
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

			// TODO: first lap position changes
//			flModel.doFirstLapChanges(drivers);

			// check if there are any DNFs and if a safety car needs to be deployed (will be false if SC is already active)
			deploySafetyCar = dnfModel.checkDNFs(i, drivers, retiredDrivers, safetyCarActive);

			// pass safety car status to lap time simulator
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
		// TODO: set tyre compound as needed - remember that only 3 consecutive compounds are used per race (e.g. c2, c3, c4)
		for(Driver driver : drivers){
			driver.setLapNumber(lapNum);
			driver.setTyreAge(lapNum);
			// if safety car is active, multiply lap times by safety car factor
			driver.setTotalRaceTime(driver.getTotalRaceTime() + (driver.getCorrectedLapTime() * (safetyCarActive ? SAFETY_CAR_FACTOR : 1)));
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
