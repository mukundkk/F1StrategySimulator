package Models;

import org.apache.commons.math3.*;

public class Driver {
	int lapNumber;
	double fastestQualiTime;
	double currentLapTime;
	double correctedLapTime;
	double fuelRemaining;
	double residualLapTime;
	double tireAge;
	double fuelCoefficientZero;
	double fuelCoefficientOne;

	public Driver(double fcZero, double fcOne){
		this.fuelCoefficientZero = fcZero;
		this.fuelCoefficientOne = fcOne;
	}

	public int getLapNumber() {
		return lapNumber;
	}

	public double getFuelRemaining() {
		return fuelRemaining;
	}

	public void setFuelRemaining(double fuelRemaining) {
		this.fuelRemaining = fuelRemaining;
	}

	public double getFuelCoefficientZero() {
		return fuelCoefficientZero;
	}

	public double getFuelCoefficientOne() {
		return fuelCoefficientOne;
	}
}
