package Models;

public class Driver implements Comparable<Driver>{
	int lapNumber;
	double fastestQualiTime;
	double currentLapTime;
	double correctedLapTime;
	double fuelRemaining;
	double residualLapTime;
	double fuelCoefficientZero;
	double fuelCoefficientOne;
	double[] c5coeffs;
	double[] c4coeffs;
	double[] c3coeffs;
	double[] c2coeffs;
	double[] c1coeffs;

	double totalRaceTime;

	int tyreCompound;
	int tyreAge;
	double stddev;

	String name;

	LapTimeVarianceModel lapTimeModel;

	public Driver(double qualiTime, double fcZero, double fcOne, double[] c5coeffs, double[] c4coeffs, double[] c3coeffs, double[] c2coeffs, double[] c1coeffs, double stddev, String name){
		this.fastestQualiTime = qualiTime;
		this.fuelCoefficientZero = fcZero;
		this.fuelCoefficientOne = fcOne;
		this.c5coeffs = c5coeffs;
		this.c4coeffs = c4coeffs;
		this.c3coeffs = c3coeffs;
		this.c2coeffs = c2coeffs;
		this.c1coeffs = c1coeffs;
		this.stddev = stddev;
		this.name = name;
		totalRaceTime = 0;
	}

	public int getLapNumber() {
		return lapNumber;
	}

	public void setLapNumber(int lapNumber) {
		this.lapNumber = lapNumber;
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

	public void setCorrectedLapTime(double correctedLapTime) {
		this.correctedLapTime = correctedLapTime;
	}

	public double getCorrectedLapTime() {
		lapTimeModel.lapTime();
		return correctedLapTime;
	}

	public double getFastestQualiTime() {
		return fastestQualiTime;
	}

	public double getStddev() {
		return stddev;
	}

	public int getTyreCompound() {
		return tyreCompound;
	}

	public void setTyreCompound(int tyreCompound) {
		this.tyreCompound = tyreCompound;
	}

	public int getTyreAge() {
		return tyreAge;
	}

	public void setTyreAge(int tyreAge) {
		this.tyreAge = tyreAge;
	}



	public double getTyreCoeff(int compound, int cfNum){
		double coeff = 0;
		switch(compound){
			case 1:
				switch(cfNum){
					case 0:
						coeff = c1coeffs[0];
						break;
					case 1:
						coeff = c1coeffs[1];
						break;
					case 2:
						coeff = c1coeffs[2];
						break;
					default:
						System.out.println("Not a valid tyre coefficient");
						System.exit(0);
				}
				break;
			case 2:
				switch(cfNum){
					case 0:
						coeff = c2coeffs[0];
						break;
					case 1:
						coeff = c2coeffs[1];
						break;
					case 2:
						coeff = c2coeffs[2];
						break;
					default:
						System.out.println("Not a valid tyre coefficient");
						System.exit(0);
				}
				break;
			case 3:
				switch(cfNum){
					case 0:
						coeff = c3coeffs[0];
						break;
					case 1:
						coeff = c3coeffs[1];
						break;
					case 2:
						coeff = c3coeffs[2];
						break;
					default:
						System.out.println("Not a valid tyre coefficient");
						System.exit(0);
				}
				break;
			case 4:
				switch(cfNum){
					case 0:
						coeff = c4coeffs[0];
						break;
					case 1:
						coeff = c4coeffs[1];
						break;
					case 2:
						coeff = c4coeffs[2];
						break;
					default:
						System.out.println("Not a valid tyre coefficient");
						System.exit(0);
				}
				break;
			case 5:
				switch(cfNum){
					case 0:
						coeff = c5coeffs[0];
						break;
					case 1:
						coeff = c5coeffs[1];
						break;
					case 2:
						coeff = c5coeffs[2];
						break;
					default:
						System.out.println("Not a valid tyre coefficient");
						System.exit(0);
				}
				break;
			default:
				System.out.println("Not a valid tyre compound");
				System.exit(0);
		}
		return coeff;
	}

	public double getTotalRaceTime() {
		return totalRaceTime;
	}

	public void setTotalRaceTime(double totalRaceTime) {
		this.totalRaceTime = totalRaceTime;
	}

	public void init(){
		lapTimeModel = new LapTimeVarianceModel(this);
	}

	public String getName() {
		return name;
	}

	public int compareTo(Driver otherDriver){
		return Double.compare(totalRaceTime, otherDriver.getTotalRaceTime());
	}
}