package Models;

import org.apache.commons.math3.distribution.NormalDistribution;

public class TyreModel {
	Driver driver;

	public TyreModel(Driver driver){
		this.driver = driver;
	}

	// generate random lap variance for tyre usage
	public double generateRandom(){
		NormalDistribution ndist = new NormalDistribution(0, driver.getStddev());
		return ndist.sample();
	}

	// lap adjustment factor for tyre usage
	public double calcResidual(){
		int currCompound = driver.getTyreCompound();
		double residual = driver.getTyreCoeff(currCompound, 0) + (driver.getTyreCoeff(currCompound, 1) * driver.getTyreAge())
				+ (driver.getTyreCoeff(currCompound, 2) * Math.pow(driver.getTyreAge(), 2)) + generateRandom();
		return residual;
	}


}
