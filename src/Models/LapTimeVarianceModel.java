package Models;

import Util.Driver;

import static Data.GlobalInfo.*;

public class LapTimeVarianceModel {
	FuelModel fuelModel;
	TyreModel tyreModel;
	Driver driver;

	public LapTimeVarianceModel(Driver driver){
		this.driver = driver;
		fuelModel = new FuelModel(driver);
		tyreModel = new TyreModel(driver);
	}

	// combine fuel and tyre models with base time (and DRS if applicable) to get overall lap time
	public double simulateLapTime(){
		double lapTime = driver.getQualiTime() + fuelModel.correctLapTime() + tyreModel.calcResidual();
		if(driver.DRSBonusActive()) lapTime += DRS_BONUS;
		driver.setCorrectedLapTime(lapTime);
		driver.setDRSBonus(false);
		return lapTime;
	}


}
