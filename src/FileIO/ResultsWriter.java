package FileIO;

import Data.GlobalInfo;
import Models.Driver;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ResultsWriter {
	String circuit;
	int[] qualiPositions;
	int[] racePositions;
	boolean[] dnfStatus;
	private ArrayList<Driver> driverList;

	public ResultsWriter() {
		this.circuit = "";
		driverList = GlobalInfo.getDriverList();
		qualiPositions = new int[driverList.size()];
		racePositions = new int[driverList.size()];
		dnfStatus = new boolean[driverList.size()];
	}

	// should always be run before using writeToFile()
	private void getInfo(){

		try (Scanner sc = new Scanner(System.in)){
			// get circuit name
			System.out.println("Which circuit is this for (country name)?");
			circuit = sc.nextLine();

			// go through driver list and get qualifying/finishing positions and DNF status
			for(int i = 0; i < driverList.size(); i++){
				Driver driver = driverList.get(i);
				System.out.println("Enter " + driver.getName() + "'s qualifying position: ");
				qualiPositions[i] = sc.nextInt();
				System.out.println("Did " + driver.getName() + " DNF? (y/n)");
				String didDNF = sc.next();
				if (didDNF.equalsIgnoreCase("y")) {
					dnfStatus[i] = true;
					racePositions[i] = 0;
				} else {
					dnfStatus[i] = false;
					System.out.println("Enter " + driver.getName() + "'s finishing position: ");
					racePositions[i] = sc.nextInt();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void askForData() {
		boolean loop = false;
		try (Scanner sc1 = new Scanner(System.in);) {
			System.out.println("Should this race be part of the training set? (y/n)");
			String wantsTraining = sc1.next();
			boolean trainingSet = wantsTraining.equalsIgnoreCase("y");
			do {
				writeToFile(trainingSet);
				System.out.println("──────────────────────────────────────────");
				System.out.println("Would you like to enter data for another race? (y/n)");
				String response = sc1.next();
				loop = response.equalsIgnoreCase("y");
				if (loop) {
					System.out.println("Should this race be part of the training set? (y/n)");
					wantsTraining = sc1.next();
					trainingSet = wantsTraining.equalsIgnoreCase("y");
				}
			} while (loop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// write info for given race to JSON file
	private void writeToFile(boolean trainingSet){
		getInfo();
		String trainingDir = "src/Data/Training/";
		String testDir = "src/Data/Test/";
		String filename = circuit + ".json";
		// check if file exists in training or test set, depending on selected option
		String path = trainingSet ? trainingDir + filename : testDir + filename;
		if (Files.exists(Paths.get(path))) {
			// ask user if they want to overwrite existing file
			System.out.println("The file for " + circuit + " already exists. Do you want to overwrite it? (y/n)");
			try (Scanner sc = new Scanner(System.in)){
				String overwrite = sc.nextLine();
				if (overwrite.equalsIgnoreCase("y")) {
					// overwrite file
					File results = new File(path);
					writeToJSON(results);
				} else {
					System.exit(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// create new file and write to it
			File results = new File(path);
			writeToJSON(results);
		}
	}

	private void writeToJSON(File file) {

		// main JSON object
		JsonObject json = new JsonObject();

		try (FileWriter fw = new FileWriter(file)) {
			for (int i = 0; i < driverList.size(); i++) {
				Driver driver = driverList.get(i);

				// nested JSON object for each driver
				JsonObject driverObj = new JsonObject();
				driverObj.put("qualiPosition", qualiPositions[i]);
				driverObj.put("DNF", dnfStatus[i]);
				driverObj.put("racePosition", racePositions[i]);
				json.put(driver.getLastName(), driverObj);
			}

			// write to file
			Jsoner.serialize(json, fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
