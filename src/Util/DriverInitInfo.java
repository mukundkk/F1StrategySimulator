package Util;

public class DriverInitInfo {
	double qualiTime;
	int qualiPosition;
	int startingTyreCompound;

	public DriverInitInfo(double qualiTime, int qualiPosition, int startingTyreCompound){
		this.qualiTime = qualiTime;
		this.qualiPosition = qualiPosition;
		this.startingTyreCompound = startingTyreCompound;
	}

	public double getQualiTime() {
		return qualiTime;
	}

	public int getQualiPosition() {
		return qualiPosition;
	}

	public int getStartingTyreCompound() {
		return startingTyreCompound;
	}
}
