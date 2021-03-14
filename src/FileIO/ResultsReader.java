package FileIO;

import Data.GlobalInfo;
import Util.Driver;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ResultsReader {
	String circuit;
	int[] firstLapPositionChange;
	int[] qualiPositions;
	int[] racePositions;
	boolean[] dnfStatus;
	private ArrayList<Driver> driverList;
	boolean trainingSet;

	public ResultsReader(String circuit, boolean trainingSet) {
		this.circuit = circuit;
		driverList = GlobalInfo.getDriverList();
		this.trainingSet = trainingSet;
		firstLapPositionChange = new int[driverList.size()];
		qualiPositions = new int[driverList.size()];
		racePositions = new int[driverList.size()];
		dnfStatus = new boolean[driverList.size()];
		readFile();
	}

	public ResultsReader(){
		driverList = GlobalInfo.getDriverList();
		firstLapPositionChange = new int[driverList.size()];
		qualiPositions = new int[driverList.size()];
		racePositions = new int[driverList.size()];
		dnfStatus = new boolean[driverList.size()];
	}

	// get array of all first lap position changes in the training set for a given driver
	public int[] getAllTrainingFirstLapPositionChanges(String lastName){
		int[] posChanges = new int[getTrainingFiles().length];
		for (int i = 0; i < getTrainingFiles().length; i++){
			readFile(getTrainingFiles()[i].getName(), true);
			posChanges[i] = getFirstLapPositionChange(lastName);
		}
		return posChanges;
	}

	// get array of all qualifying positions in the training set for a given driver
	public int[] getAllTrainingQualiPositions(String lastName){
		int[] qualiPositions = new int[getTrainingFiles().length];
		for (int i = 0; i < getTrainingFiles().length; i++){
			readFile(getTrainingFiles()[i].getName(), true);
			qualiPositions[i] = getQualiPosition(lastName);
		}
		return qualiPositions;
	}

	// get array of all race positions in the training set for a given driver
	public int[] getAllTrainingRacePositions(String lastName){
		int[] racePositions = new int[getTrainingFiles().length];
		for (int i = 0; i < getTrainingFiles().length; i++){
			readFile(getTrainingFiles()[i].getName(), true);
			racePositions[i] = getRacePosition(lastName);
		}
		return racePositions;
	}

	// get array of DNF status for each race in the training set for a given driver
	public boolean[] getAllTrainingDNFs(String lastName){
		boolean[] DNFs = new boolean[getTrainingFiles().length];
		for (int i = 0; i < getTrainingFiles().length; i++){
			readFile(getTrainingFiles()[i].getName(), true);
			DNFs[i] = didDNF(lastName);
		}
		return DNFs;
	}

	// get first lap position change for a given driver, for the race specified when instantiating the class
	public int getFirstLapPositionChange(String lastName){
		return getDriverIndex(lastName) >= 0 ? firstLapPositionChange[getDriverIndex(lastName)] : -1;
	}

	// get qualifying position for a given driver, for the race specified when instantiating the class
	public int getQualiPosition(String lastName) {
		return getDriverIndex(lastName) >= 0 ? qualiPositions[getDriverIndex(lastName)] : -1;
	}

	// get race position for a given driver, for the race specified when instantiating the class
	public int getRacePosition(String lastName) {
		return getDriverIndex(lastName) >= 0 ? racePositions[getDriverIndex(lastName)] : -1;
	}

	// get DNF status for a given driver, for the race specified when instantiating the class
	public boolean didDNF(String lastName) {
		return getDriverIndex(lastName) >= 0 ? dnfStatus[getDriverIndex(lastName)] : false;
	}

	// return array of files in the training directory
	private File[] getTrainingFiles() {
		return new File("src/Data/Training").listFiles();
	}

	// read file for the circuit & category specified when instantiating the class
	private void readFile() {
		readFile(circuit, this.trainingSet);
	}

	// read file for a given circuit & category
	private void readFile(String circuit, boolean trainingSet){
		String trainingDir = "src/Data/Training/";
		String testDir = "src/Data/Test/";
		String path = trainingSet ? trainingDir + circuit : testDir + circuit;

		if(Files.exists(Paths.get(path))) {
			try (Reader reader = Files.newBufferedReader(Paths.get(path))) {
				JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
				for(int i = 0; i < driverList.size(); i++) {
					Driver driver = driverList.get(i);
					JsonObject driverObj = (JsonObject) parser.get(driver.getLastName());
					firstLapPositionChange[i] = ((BigDecimal) (driverObj.get("positionChange"))).intValue();
					qualiPositions[i] = ((BigDecimal) (driverObj.get("qualiPosition"))).intValue();
					dnfStatus[i] = (boolean) driverObj.get("DNF");
					racePositions[i] = ((BigDecimal) driverObj.get("racePosition")).intValue();
				}
			} catch (IOException | JsonException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("The file for " + circuit + " could not be found.");
		}
	}

	// get location of driver in a list or array (for use in other methods)
	private int getDriverIndex(String lastName) {
		for(int i = 0; i < driverList.size(); i++) {
			if (driverList.get(i).getLastName().equals(lastName)) return i;
		}
		return -1;
	}

}
