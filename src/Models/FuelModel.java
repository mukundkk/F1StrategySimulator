package Models;

import Data.GlobalInfo;

public class FuelModel {

	Driver driver;

	public FuelModel(Driver driver){
		this.driver = driver;
	}

	// calculate percent of fuel remaining
	public double calcFuel(){
		double fuelRemaining = (1 - (driver.getLapNumber() / (double) GlobalInfo.totalLaps)) * 100;
		driver.setFuelRemaining(fuelRemaining);
		return fuelRemaining;
	}

	// adjust lap time based on fuel remaining
	public double correctLapTime(){
		double correctedLapTime = driver.getFuelCoefficientZero() + (driver.getFuelCoefficientOne() * calcFuel());
		driver.setCorrectedLapTime(correctedLapTime);
		return correctedLapTime;
	}

}
