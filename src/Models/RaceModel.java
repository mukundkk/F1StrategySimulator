package Models;

import java.util.Collections;
import java.util.Comparator;

import static Data.GlobalInfo.*;
import Data.Circuits.*;

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
	DNFModel dnfModel;

	public RaceModel(int circuit, int[] tyreCompounds, double[] qualiTimes, int[] qualiPositions) {
		this.circuit = circuit;
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
		overtakingModel = new OvertakingModel(circuit);
		dnfModel = new DNFModel();
		this.tyreCompounds = tyreCompounds;
		this.qualiTimes = qualiTimes;
		this.qualiPositions = qualiPositions;
	}

	public void simulateRace() {
		// add driver profiles to main list and set DNF probabilities for each driver
		setupRace();
		dnfModel.assignDNFProbability(drivers);

		// for each driver, set quali time & set their initial tyre compound (for now, defaulting to hardest compound available for the race)
		for (int i = 0; i < drivers.size(); i++) {
			drivers.get(i).init(qualiTimes[i], qualiPositions[i]);
			drivers.get(i).setTyreCompound(tyreCompounds[0]);
		}

		// establish starting grid
		setGridPositions();

		// simulate actual race (laps)
		for(int i = 1; i <= totalLaps; i++){
			simulateLapTimes(i);
			overtakingModel.simulateOvertakes();
		}

		// final sort
		Collections.sort(drivers);
		System.out.println("Race Results:");
		for(int i = 1; i <= drivers.size(); i++){
			System.out.println(i + ". " + drivers.get(i - 1).getName());
		}
	}

	private void simulateLapTimes(int lapNum){
		// set tyre compound as needed - remember that only 3 consecutive compounds are used per race
		// (e.g. c2, c3, c4)
		for(Driver driver : drivers){
			driver.setLapNumber(lapNum);
			driver.setTyreAge(lapNum);
			driver.setTotalRaceTime(driver.getTotalRaceTime() + driver.getCorrectedLapTime());
		}
		Collections.sort(drivers);
	}

	private void setGridPositions() {
		Collections.sort(drivers, Comparator.comparingInt(Driver::getQualiPosition));
		// now that the list of drivers is sorted, assign grid position using position variable (as this will be referred to in the race)
		for(int i = 0; i < drivers.size(); i++){
			drivers.get(i).setPosition(i + 1);
		}
	}
}
