package Models;

import static Models.GlobalInfo.*;

public class LapTimeVarianceModel {
	FuelModel fuelModel;
	TyreModel tyreModel;
	Driver driver;

	public LapTimeVarianceModel(Driver driver){
		this.driver = driver;
		fuelModel = new FuelModel(driver);
		tyreModel = new TyreModel(driver);
	}

	public double simulateLapTime(){
		double lapTime = driver.getQualiTime() + fuelModel.correctLapTime() + tyreModel.calcResidual();
		if(driver.DRSBonusActive()) lapTime += DRS_BONUS;
		driver.setCorrectedLapTime(lapTime);
		driver.setDRSBonus(false);
		return lapTime;
	}


}
