package FileIO;

import Data.GlobalInfo;
import Models.Driver;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
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
	}

	// should always be run before using writeToFile()
	public void getInfo(){
		Scanner sc;

		try {
			sc = new Scanner(System.in);

			// get circuit name
			System.out.println("Which circuit is this for (country name)?");
			circuit = sc.nextLine();

			// go through driver list and get qualifying/finishing positions and DNF status
			for(int i = 0; i < driverList.size(); i++){
				Driver driver = driverList.get(i);
				System.out.println("Enter " + driver.getName() + "'s qualifying position: ");
				qualiPositions[i] = sc.nextInt();
				System.out.println("Did " + driver.getName() + " DNF? (y/n)");
				if (sc.nextLine().equalsIgnoreCase("y")) {
					dnfStatus[i] = true;
					racePositions[i] = 0;
				} else {
					dnfStatus[i] = false;
					System.out.println("Enter " + driver.getName() + "'s finishing position: ");
					racePositions[i] = sc.nextInt();
				}
			}
		} catch (Exception E) {
			System.out.println("Invalid input");
		}
	}

	// write info for given race to JSON file
	public void writeToFile(boolean trainingSet){
		// check if file exists in training or test set, depending on selected option
		if (Files.exists(Paths.get(trainingSet ? "src/Data/Training/" + circuit + ".json" : "src/Data/Test/" + circuit + ".json"))) {

		} else {

		}
	}
}
