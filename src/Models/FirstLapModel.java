package Models;


import FileIO.ResultsReader;
import Util.Driver;
import Util.FirstLapDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.util.FastMath;

import java.util.ArrayList;
import java.util.Comparator;


public class FirstLapModel {
	private ResultsReader reader;

	public FirstLapModel() {
		reader = new ResultsReader();
	}

	public int getFirstLapChange(String lastName){
		JDKRandomGenerator rng = new JDKRandomGenerator();
		FirstLapDistribution flDistribution = new FirstLapDistribution(rng);
		flDistribution.setGainedPositions(reader.getAllTrainingFirstLapPositionChanges(lastName));
		return (int) FastMath.rint(flDistribution.sample());
	}

	public void doFirstLapChanges(ArrayList<Driver> drivers){
		// for each driver, get & set new position after first lap
		for (Driver driver : drivers){
			int newPos = driver.getPosition() + getFirstLapChange(driver.getLastName());
			driver.setPosition(newPos);
		}
		// rearrange main driver list based on new positions
		drivers.sort(Comparator.comparingInt(Driver::getPosition));


	}
}
