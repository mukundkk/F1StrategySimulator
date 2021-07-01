package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static Data.GlobalInfo.*;
import Data.Circuits.*;
import Data.GlobalInfo;
import Util.Driver;

public class RaceModel {
	/*
	CIRCUIT NUMBERS:
	Circuit 1: Japan
	Circuit 2: US
	Circuit 3: Mexico
	Circuit 4: Abu Dhabi
	 */
	int circuit;
	int[] tyreCompounds, qualiPositions;
	double[] qualiTimes;
	OvertakingModel overtakingModel;
	FirstLapModel flModel;
	DNFModel dnfModel;
	ArrayList<Driver> drivers;
	ArrayList<Object[]> retiredDrivers;
	private int safetyCarLapCounter;
	private boolean safetyCarActive;
	private boolean deploySafetyCar;

	public RaceModel(int circuit, int[] tyreCompounds, double[] qualiTimes, int[] qualiPositions) {
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
		overtakingModel = new OvertakingModel(circuit, drivers);
		flModel = new FirstLapModel();
		dnfModel = new DNFModel();
		this.tyreCompounds = tyreCompounds;
		this.qualiTimes = qualiTimes;
		this.qualiPositions = qualiPositions;
	}

	public void simulateRace() {
		initialize();

		loop();

		stop();
	}

	private void initialize() {
		// set DNF probabilities for each driver
		dnfModel.assignDNFProbability(drivers);

		// for each driver, set quali time & set their initial tyre compound (for now, defaulting to hardest compound available for the race)
		for (int i = 0; i < drivers.size(); i++) {
			drivers.get(i).init(qualiTimes[i], qualiPositions[i]);
			drivers.get(i).setTyreCompound(tyreCompounds[0]);
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

			// first lap position changes
			if (i == 1){
				for (Driver driver : drivers){
					flModel.getFirstLapChange(driver.getLastName());
					// TODO: figure out how to actually execute the position change without loss of data
				}
			}

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
