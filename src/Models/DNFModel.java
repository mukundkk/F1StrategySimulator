package Models;

import Data.GlobalInfo;
import FileIO.ResultsReader;
import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.BinomialDistribution;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class DNFModel {
	double avgDNFFraction;
	double stdDevDNF;
	double alpha;
	double beta;
	ArrayList<Driver> driverList;

	public DNFModel() {
		driverList = GlobalInfo.getDriverList();
		calcAvgTotalDNF();
		System.out.println(avgDNFFraction);
	}

	private void calcAvgTotalDNF() {
		double totalDNFFrac = 0;

		// get sum of DNF fractions of all drivers
		for (Driver driver : driverList) {
			totalDNFFrac += calcAvgDriverDNF(driver.getLastName());
		}

		// set average DNF fraction to sum divided by number of drivers
		avgDNFFraction = totalDNFFrac / driverList.size();
	}

	private double calcAvgDriverDNF(String lastName) {
		// get all files in training directory
		File[] trainingFiles = Objects.requireNonNull(new File("src/Data/Training/").listFiles());
		int numFiles = trainingFiles.length;
		double numDNFs = 0;

		// check how many times the given driver DNFed
		for (File trainingFile : trainingFiles) {
			String circuit = trainingFile.getName();
			if (didDriverDNF(circuit, lastName)) numDNFs++;
		}

		// return average DNF as a fraction
		return numDNFs / numFiles;
	}

	private boolean didDriverDNF(String circuit, String lastName) {
		return new ResultsReader(circuit, true).didDNF(lastName);
	}
}
