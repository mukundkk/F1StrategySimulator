package Models;

import Data.GlobalInfo;

public class FuelModel {

	Driver driver;

	public FuelModel(Driver driver){
		this.driver = driver;
	}

	public double calcFuel(){
		double fuelRemaining = (1 - (driver.getLapNumber() / (double) GlobalInfo.totalLaps)) * 100;
		driver.setFuelRemaining(fuelRemaining);
		return fuelRemaining;
	}

	public double correctLapTime(){
		double correctedLapTime = driver.getFuelCoefficientZero() + (driver.getFuelCoefficientOne() * calcFuel());
		driver.setCorrectedLapTime(correctedLapTime);
		return correctedLapTime;
	}

}
