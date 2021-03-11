package FileIO;

import Data.GlobalInfo;
import Util.Driver;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ResultsReader {
	String circuit;
	int[] qualiPositions;
	int[] racePositions;
	boolean[] dnfStatus;
	private ArrayList<Driver> driverList;
	boolean trainingSet;

	public ResultsReader(String circuit, boolean trainingSet) {
		this.circuit = circuit;
		driverList = GlobalInfo.getDriverList();
		this.trainingSet = trainingSet;
		qualiPositions = new int[driverList.size()];
		racePositions = new int[driverList.size()];
		dnfStatus = new boolean[driverList.size()];
		readFile();
	}

	// TODO: Add method (or add to readFile()) to create and return array of first lap position changes for a given driver

	private void readFile() {
		String trainingDir = "src/Data/Training/";
		String testDir = "src/Data/Test/";
		String filename = circuit;
		String path = trainingSet ? trainingDir + filename : testDir + filename;

		if(Files.exists(Paths.get(path))) {
			try (Reader reader = Files.newBufferedReader(Paths.get(path))) {
				JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
				for(int i = 0; i < driverList.size(); i++) {
					Driver driver = driverList.get(i);
					JsonObject driverObj = (JsonObject) parser.get(driver.getLastName());
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

	public int getQualiPosition(String lastName) {
		return getDriverIndex(lastName) >= 0 ? qualiPositions[getDriverIndex(lastName)] : -1;
	}

	public boolean didDNF(String lastName) {
		return getDriverIndex(lastName) >= 0 ? dnfStatus[getDriverIndex(lastName)] : false;
	}

	public int getRacePosition(String lastName) {
		return getDriverIndex(lastName) >= 0 ? racePositions[getDriverIndex(lastName)] : -1;
	}

	private int getDriverIndex(String lastName) {
		for(int i = 0; i < driverList.size(); i++) {
			if (driverList.get(i).getLastName().equals(lastName)) return i;
		}
		return -1;
	}

}
