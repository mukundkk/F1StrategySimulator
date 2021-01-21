package Models;


import Circuits.AbuDhabi;
import Circuits.Japan;
import Circuits.Mexico;
import Circuits.US;

import java.util.Random;

import static Models.GlobalInfo.*;

public class OvertakingModel {
	/*
	CIRCUIT NUMBERS:
	Circuit 1: Japan
	Circuit 2: US
	Circuit 3: Mexico
	Circuit 4: Abu Dhabi
	 */
	double overtakingThreshold, overtakingProbability;

	public OvertakingModel(int circuit){
		switch (circuit) {
			case 1:
				overtakingThreshold = Japan.OVERTAKING_THRESHOLD;
				overtakingProbability = Japan.OVERTAKING_PROBABILITY;
				break;
			case 2:
				overtakingThreshold = US.OVERTAKING_THRESHOLD;
				overtakingProbability = US.OVERTAKING_PROBABILITY;
				break;
			case 3:
				overtakingThreshold = Mexico.OVERTAKING_THRESHOLD;
				overtakingProbability = Mexico.OVERTAKING_PROBABILITY;
				break;
			case 4:
				overtakingThreshold = AbuDhabi.OVERTAKING_THRESHOLD;
				overtakingProbability = AbuDhabi.OVERTAKING_PROBABILITY;
				break;
			default:
				overtakingThreshold = DEFAULT_OVERTAKING_THRESHOLD;
				overtakingProbability = DEFAULT_OVERTAKING_PROBABILITY;
				break;
		}
		overtakingProbability *= 100;
	}

	public void simulateOvertakes(){
		for(int i = 0; i < drivers.size() - 1; i++) {
			int actualPos = i + 1;
			double deltaCumulativeLapTime = drivers.get(i + 1).getTotalRaceTime() - drivers.get(i).getTotalRaceTime();
			if (deltaCumulativeLapTime < overtakingThreshold && new Random().nextInt(100) < overtakingProbability) {
				drivers.get(i + 1).setPosition(actualPos);
				drivers.get(i + 1).setTotalRaceTime(drivers.get(i + 1).getTotalRaceTime() + OVERTAKING_PENALTY);
				drivers.get(i).setPosition(actualPos + 1);
				drivers.get(i).setTotalRaceTime(drivers.get(i).getTotalRaceTime() + OVERTAKING_PENALTY);
			}
			else if (deltaCumulativeLapTime < DRS_THRESHOLD) {
				drivers.get(i + 1).setDRSBonus(true);
			}
			/*
			- If the trailing car is not faster, and does not receive the DRS bonus either, nothing happens and the cars retain their
			positions
			TODO consider car before the trailing & leading car (i + 2) when correcting cumulative lap times for minimum time difference: this
			 means that after adding or subtracting times as needed, make sure the gap between every car is no less than the minimum time
			 difference as specified in the Models.GlobalInfo class.
			 */
//			deltaCumulativeLapTime = drivers.get(i + 1).getTotalRaceTime() - drivers.get(i).getTotalRaceTime();
//			if (Math.abs(deltaCumulativeLapTime) < MIN_TIME_DIFFERENCE) {
//
//			}
		}
	}
}
