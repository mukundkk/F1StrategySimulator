package Models;

import Data.GlobalInfo;
import FileIO.ResultsReader;
import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.stat.descriptive.summary.Sum;

import java.io.File;
import java.util.*;

import static Data.GlobalInfo.totalLaps;

public class DNFModel {
	double meanDNF;
	double stdDevDNF;
	double priorAlpha;
	double priorBeta;
	ArrayList<Driver> driverList;
	private int numFiles;
	private File[] trainingFiles;
	private ArrayList<Object[]> dnfList;

	public DNFModel() {
		trainingFiles = Objects.requireNonNull(new File("src/Data/Training/").listFiles());
		numFiles = trainingFiles.length;
		driverList = GlobalInfo.getDriverList();
		dnfList = new ArrayList<>();

		calcMeanDNF();
		calcStdDev();
		calcPriorAlpha();
		calcPriorBeta();
	}

	private void calcMeanDNF() {
		double totalDNFFrac = 0;

		// get sum of DNF fractions of all drivers
		for (Driver driver : driverList) {
			totalDNFFrac += calcAvgDriverDNF(driver.getLastName());
		}

		// set average DNF fraction to sum divided by number of drivers
		meanDNF = totalDNFFrac / driverList.size();
	}

	private double calcAvgDriverDNF(String lastName) {
		// get all files in training directory
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

	private void calcStdDev() {
		// use average dnf fraction of all drivers as mean
		double mean = meanDNF;
		double[] terms = new double[driverList.size()];

		// for each driver, calculate squared difference between driver DNF fraction and mean - used as term in sum
		for (int i = 0; i < driverList.size(); i++) {
			double driverDNFFrac = calcAvgDriverDNF(driverList.get(i).getLastName());
			double term = Math.pow((driverDNFFrac - mean), 2);
			terms[i] = term;
		}

		// find the mean of squared differences
		double sum = new Sum().evaluate(terms, 0, terms.length);
		double meanOfSum = sum / driverList.size();

		// standard deviation is the square root of that mean
		stdDevDNF = Math.sqrt(meanOfSum);
	}

	private void calcPriorAlpha() {
		double term1 = (1 - meanDNF) / Math.pow(stdDevDNF, 2);
		double term2 = 1 / meanDNF;
		double term3 = term1 - term2;
		priorAlpha = term3 * Math.pow(meanDNF, 2);
	}

	private void calcPriorBeta() {
		double term1 = (1 / meanDNF) - 1;
		priorBeta = priorAlpha * term1;
	}

	private int getNumDNFs (String lastName) {
		File[] trainingFiles = Objects.requireNonNull(new File("src/Data/Training/").listFiles());
		int numFiles = trainingFiles.length;
		return (int) (calcAvgDriverDNF(lastName) * numFiles);
	}

	private double getDNFProbability (String lastName) {
		double posteriorDistAlpha = getNumDNFs(lastName) + priorAlpha;
		double posteriorDistBeta = numFiles - getNumDNFs(lastName) + priorBeta;
		BetaDistribution posteriorDist = new BetaDistribution(posteriorDistAlpha, posteriorDistBeta);
		return posteriorDist.getNumericalMean();
	}

	/* assign dnf probabilities to each driver, then check if they ever retire and if so in which lap
	add drivers that retire to a list along with the lap in which they retire */
	public void assignDNFProbability(ArrayList<Driver> drivers) {
		for (Driver driver : drivers) {
			driver.setDnfProbability(getDNFProbability(driver.getLastName()));
			if (new Random().nextInt(100) < driver.getDnfProbability() * 100) {
				int lapToRetire = getRandomLap();
				dnfList.add(new Object[]{driver, lapToRetire});
			}
		}
	}

	// check if any drivers need to DNF this lap
	public boolean checkDNFs(int lapNum, ArrayList<Driver> drivers, ArrayList<Object[]> retiredDrivers, boolean safetyCarActive) {
		boolean deploySafetyCar = false;
		for (Object[] arr : dnfList) {
			if ((int) arr[1] == lapNum) {
				// deploy safety car if there is a crash, if there isn't a safety car already out, and a 50% chance succeeds
				deploySafetyCar = new Random().nextBoolean() && !safetyCarActive;

				// if a driver needs to DNF this lap, add the array of driver & lap to retired drivers list
				retiredDrivers.add(arr);

				// remove driver from running list
				for (int i = 0; i < drivers.size(); i++) {
					if (drivers.get(i).getLastName().equals(((Driver)(arr[0])).getLastName())) drivers.remove(i);
				}
			}
		}
		return deploySafetyCar;
	}

	private int getRandomLap() {
		ArrayList<Integer> laps = new ArrayList<>();
		for(int i = 1; i <= totalLaps; i++) {
			if (i == 1) for (int j = 0; j < 10; j++) laps.add(i);
			else laps.add(i);
		}
		int rand = new Random().nextInt(laps.size());
		return laps.get(rand);
	}
}
