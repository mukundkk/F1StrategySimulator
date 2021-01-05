package Evaluation;

import Models.*;

public class NetEvaluation {

	public static void main(String[] args) {
		Driver testDriver = new Driver(84.197, 5.595, 0.017, new double[]{0.553, 0.002, 0}, new double[]{0.035, -0.026, 0},
				new double[]{0.076, -0.037, 0}, new double[]{0.249, 0.017, 0}, new double[]{0.035, 0.034, 0}, 1.480);
		testDriver.setTyreCompound(5);
		GlobalInfo.setTotalLaps(25);
		LapTimeVarianceModel lapTimeModel = new LapTimeVarianceModel(testDriver);
		for(int i = 1; i <= GlobalInfo.totalLaps; i++){
			testDriver.setLapNumber(i);
			testDriver.setTyreAge(i);
			System.out.printf("%.3f", lapTimeModel.lapTime());
			System.out.println();
		}
	}
}
