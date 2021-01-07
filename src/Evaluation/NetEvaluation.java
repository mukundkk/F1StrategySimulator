package Evaluation;

import Models.*;

import java.util.ArrayList;
import java.util.Collections;

import static Models.GlobalInfo.drivers;

public class NetEvaluation {

	public static void main(String[] args) {
		drivers = new ArrayList<>();
		GlobalInfo.setTotalLaps(25);

		Driver nRosberg = new Driver(84.197, 5.595, 0.017, new double[]{0.553, 0.002, 0}, new double[]{0.035, -0.026, 0},
				new double[]{0.076, -0.037, 0}, new double[]{0.249, 0.017, 0}, new double[]{0.035, 0.034, 0}, 1.480, "Nico Rosberg");
		nRosberg.setTyreCompound(3);
		nRosberg.init();

		Driver lHamilton = new Driver(83.837, 5.266, 0.022, new double[]{-0.085, -0.038, 0}, new double[]{0.141, 0.068, 0},
				new double[]{-0.457, -0.013, 0}, new double[]{-0.001, 0.072, 0}, new double[]{0.493, -0.067, 0}, 1.496, "Lewis Hamilton");
		lHamilton.setTyreCompound(3);
		lHamilton.init();

		Collections.addAll(drivers, nRosberg, lHamilton);

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
