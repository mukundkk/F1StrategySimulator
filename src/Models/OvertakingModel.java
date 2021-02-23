package Models;


import Data.Circuits.AbuDhabi;
import Data.Circuits.Japan;
import Data.Circuits.Mexico;
import Data.Circuits.US;
import Util.Driver;

import java.util.ArrayList;
import java.util.Random;

import static Data.GlobalInfo.*;

public class OvertakingModel {
	/*
	CIRCUIT NUMBERS:
	Circuit 1: Japan
	Circuit 2: US
	Circuit 3: Mexico
	Circuit 4: Abu Dhabi
	 */
	double overtakingThreshold, overtakingProbability;
	ArrayList<Driver> drivers;

	public OvertakingModel(int circuit, ArrayList<Driver> drivers){
		this.drivers = drivers;
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

		// go through each driver pair and check for overtaking actions (add penalty if action is taken)
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
			// If the trailing car is not faster, and does not receive the DRS bonus either, nothing happens and the cars retain their positions
		}

		// make sure gap between drivers does not go below minimum difference
		for (int i = 0; i < drivers.size() - 1; i++){
			double deltaCumulativeLapTime = drivers.get(i).getTotalRaceTime() - drivers.get(i + 1).getTotalRaceTime();
			if (deltaCumulativeLapTime > 0) {
				double differenceScalar = (drivers.get(i + 1).getTotalRaceTime() / drivers.get(i + 1).getLapNumber()) / (55 + new Random().nextInt(65 - 55 + 1));
				drivers.get(i + 1).setTotalRaceTime(drivers.get(i + 1).getTotalRaceTime() + deltaCumulativeLapTime + (differenceScalar * MIN_TIME_DIFFERENCE));
			}
		}
	}

	public void updateDriverList(ArrayList<Driver> drivers) {
		this.drivers = drivers;
	}
}
