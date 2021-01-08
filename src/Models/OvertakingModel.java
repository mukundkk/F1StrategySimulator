package Models;

import Circuits.AbuDhabi;
import Circuits.Japan;
import Circuits.Mexico;
import Circuits.US;

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
	}

	public void simulateOvertakes(int lapNum){
		for(int i = 0; i < drivers.size() - 1; i++) {
			double deltaCumulativeLapTime = drivers.get(i + 1).getTotalRaceTime() - drivers.get(i).getTotalRaceTime();
			if (deltaCumulativeLapTime < overtakingThreshold) {
				/*
				TODO use overtaking probability to decide whether the overtake occurs or not
				TODO if overtake is successful: cars change positions (use setPosition) and both receive time penalty (added to cumulative
				 lap time)
				 */
			}
			else if (deltaCumulativeLapTime < DRS_THRESHOLD) {
				/*
				TODO trailing car (i + 1) gets DRS bonus in the next lap (maybe have a boolean attribute in the Driver class that mentions whether
				 or not it will get a bonus on the next lap (but remember to toggle off as needed)
				 */
			}
			/*
			- If the trailing car is not faster, and does not receive the DRS bonus either, nothing happens and the cars retain their
			positions
			TODO consider car before the trailing & leading car (i + 2) when correcting cumulative lap times for minimum time difference: this
			 means that after adding or subtracting times as needed, make sure the gap between every car is no less than the minimum time
			 difference as specified in the Models.GlobalInfo class.
			 */
		}
	}
}
