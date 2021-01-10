package Models;

import java.util.Collections;
import java.util.Comparator;

import static Models.GlobalInfo.*;
import Circuits.*;

public class RaceModel {
	/*
	CIRCUIT NUMBERS:
	Circuit 1: Japan
	Circuit 2: US
	Circuit 3: Mexico
	Circuit 4: Abu Dhabi
	 */
	int numTotalLaps, circuit;
	OvertakingModel overtakingModel;

	public RaceModel(int circuit) {
		this.circuit = circuit;
		switch (circuit) {
			case 1:
				numTotalLaps = Japan.NUM_LAPS;
				break;
			case 2:
				numTotalLaps = US.NUM_LAPS;
				break;
			case 3:
				numTotalLaps = Mexico.NUM_LAPS;
				break;
			case 4:
				numTotalLaps = AbuDhabi.NUM_LAPS;
				break;
			default:
				numTotalLaps = DEFAULT_NUM_LAPS;
				break;
		}
		overtakingModel = new OvertakingModel(circuit);
	}

	public void simulateRace() {
		// establish starting grid
		setGridPositions();

		// simulate actual race (laps)
		for(int i = 1; i <= numTotalLaps; i++){
			simulateLapTimes(i);
			overtakingModel.simulateOvertakes(i);
		}

		// final sort
		Collections.sort(drivers);
		System.out.println("Race Results:");
		for(int i = 1; i <= drivers.size(); i++){
			System.out.println(i + ". " + drivers.get(i - 1).getName());
		}
	}

	private void simulateLapTimes(int lapNum){
		for(Driver driver : drivers){
			driver.setLapNumber(lapNum);
			driver.setTyreAge(lapNum);
			driver.setTotalRaceTime(driver.getTotalRaceTime() + driver.getCorrectedLapTime());
			Collections.sort(drivers);
		}
	}

	private void setGridPositions() {
		// sort drivers according to quali time
		Collections.sort(drivers, Comparator.comparingDouble(Driver::getQualiTime));
		// now that the list of drivers is sorted, explicitly assign starting grid position
		for(int i = 1; i <= drivers.size(); i++){
			drivers.get(i).setPosition(i);
		}
	}
}
