package Models;

import java.util.Collections;

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
		for(int i = 1; i <= numTotalLaps; i++){
			simulateLapTimes(i);

		}

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

	private void simulateOvertakes(){

	}
}
