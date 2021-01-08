package Evaluation;

import Models.*;

import java.util.Collections;

import static Models.GlobalInfo.*;

public class NetEvaluation {

	public static void main(String[] args) {
		GlobalInfo.setupRace(25);

		nRosberg.setTyreCompound(3);
		nRosberg.init(84.197);

		lHamilton.setTyreCompound(3);
		lHamilton.init(83.837);

		for(int i = 1; i <= GlobalInfo.totalLaps; i++){
			for(Driver driver : drivers){
				driver.setLapNumber(i);
				driver.setTyreAge(i);
				driver.setTotalRaceTime(driver.getTotalRaceTime() + driver.getCorrectedLapTime());
			}
		}

		Collections.sort(drivers);
		System.out.println("Race Results:");
		for(int i = 1; i <= drivers.size(); i++){
			System.out.println(i + ". " + drivers.get(i - 1).getName());
		}
	}

}
