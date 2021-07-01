package Models;


import FileIO.ResultsReader;
import Util.FirstLapDistribution;
import org.apache.commons.math3.util.FastMath;


public class FirstLapModel {
	private ResultsReader reader;

	public FirstLapModel() {
		reader = new ResultsReader();
	}

	public int getFirstLapChange(String lastName){
			FirstLapDistribution flDistribution = new FirstLapDistribution();
			flDistribution.setGainedPositions(reader.getAllTrainingFirstLapPositionChanges(lastName));
			return (int) FastMath.rint(flDistribution.sample());
	}
}
