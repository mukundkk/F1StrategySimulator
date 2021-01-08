package Models;

public class LapTimeVarianceModel {
	FuelModel fuelModel;
	TyreModel tyreModel;
	Driver driver;

	public LapTimeVarianceModel(Driver driver){
		this.driver = driver;
		fuelModel = new FuelModel(driver);
		tyreModel = new TyreModel(driver);
	}

	public double lapTime(){
		double lapTime = driver.getQualiTime() + fuelModel.correctLapTime() + tyreModel.calcResidual();
		driver.setCorrectedLapTime(lapTime);
		return lapTime;
	}


}
