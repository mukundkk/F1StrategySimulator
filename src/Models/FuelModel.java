package Models;

import org.apache.commons.math3.*;

public class FuelModel {

	Driver driver;



	public FuelModel(Driver driver){
		this.driver = driver;
	}

	public void calcFuel(){
		double fuelRemaining = (1 - (driver.getLapNumber() / (double) GlobalInfo.totalLaps)) * 100;
		driver.setFuelRemaining(fuelRemaining);
	}

	public void correctLapTime(){
		double correctedLapTime = driver.getFuelCoefficientZero() + (driver.getFuelCoefficientOne() * driver.getFuelRemaining());
	}

}
