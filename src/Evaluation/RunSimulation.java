package Evaluation;

import Data.GlobalInfo;
import Models.*;
import Util.DriverInitInfo;

import java.util.Scanner;

public class RunSimulation {

	public static void main(String[] args) {
		// TODO: have prompt for user to input data rather than run hardcoded sim
		// here, add 1) circuit number, 2) tyre compounds, 3) fastest quali times & 4) quali positions (both in this order):
		/*
		Rosberg
		Hamilton
		Vettel
		Raikkonen
		Ricciardo
		Verstappen
		Massa
		Bottas
		Hulkenberg
		Perez
		Magnussen
		Palmer
		Kvyat
		Sainzodo
		Ericsson
		Nasr
		Alonso
		Button
		Wehrlein
		Ocon
		Grosjean
		Gutierrez
		 */
//		DriverInitInfo rosberg = new DriverInitInfo(90.647, 1, 2);
//		DriverInitInfo hamilton = new DriverInitInfo(90.660, 2, 2);
//		DriverInitInfo vettel = new DriverInitInfo(91.028, 4, 2);
//		DriverInitInfo raikkonen = new DriverInitInfo(90.949, 3, 2);
//		DriverInitInfo ricciardo = new DriverInitInfo(91.240, 6, 2);
//		DriverInitInfo verstappen = new DriverInitInfo(91.178, 5, 2);
//		DriverInitInfo massa = new DriverInitInfo(92.380, 12, 0);
//		DriverInitInfo bottas = new DriverInitInfo(92.315, 11, 0);
//		DriverInitInfo hulkenberg = new DriverInitInfo(92.142, 9, 2);
//		DriverInitInfo perez = new DriverInitInfo(91.961, 7, 2);
//		DriverInitInfo magnussen = new DriverInitInfo(93.023, 18, 0);
//		DriverInitInfo palmer = new DriverInitInfo(92.796, 16, 0);
//		DriverInitInfo kvyat = new DriverInitInfo(92.623, 13, 0);
//		DriverInitInfo sainz = new DriverInitInfo(92.685, 14, 0);
//		DriverInitInfo ericsson = new DriverInitInfo(93.222, 19, 0);
//		DriverInitInfo nasr = new DriverInitInfo(93.332, 20, 0);
//		DriverInitInfo alonso = new DriverInitInfo(92.689, 15, 0);
//		DriverInitInfo button = new DriverInitInfo(92.851, 17, 0);
//		DriverInitInfo wehrlein = new DriverInitInfo(93.561, 22, 0);
//		DriverInitInfo ocon = new DriverInitInfo(93.353, 21, 0);
//		DriverInitInfo grosjean = new DriverInitInfo(91.961, 8, 2);
//		DriverInitInfo gutierrez = new DriverInitInfo(92.155, 10, 2);
//
//		DriverInitInfo[] driverInfos = new DriverInitInfo[]{rosberg, hamilton, vettel, raikkonen, ricciardo, verstappen, massa, bottas,
//		hulkenberg, perez, magnussen, palmer, kvyat, sainz, ericsson, nasr, alonso, button, wehrlein, ocon, grosjean, gutierrez};
//
//		int[] tyreCompounds = new int[]{2, 3, 4};
//
//		RaceModel race = new RaceModel(1, tyreCompounds, true, true, driverInfos);
//		try {
//			race.simulateRace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		Scanner sc;
		DriverInitInfo[] infos = new DriverInitInfo[GlobalInfo.getDriverList().size()];
		try {
			sc = new Scanner(System.in);
			System.out.println("Which circuit is the race on (1: Japan; 2: USA; 3: Mexico; 4: Abu Dhabi)? ");
			int circuit = sc.nextInt();
			System.out.println("What is the softest tyre compound for the race (number only)? ");
			int softCompound = sc.nextInt();
			System.out.println("What is the medium tyre compound for the race (number only)? ");
			int mediumCompound = sc.nextInt();
			System.out.println("What is the hardest tyre compound for the race (number only)? ");
			int hardCompound = sc.nextInt();
			System.out.println("Can this race be done on a one-stop strategy (y/n)?");
			String reply = sc.next();
			boolean oneStopPossible = reply.equals("y");
			boolean twoStopPossible = true;
			for (int i = 0; i < GlobalInfo.getDriverList().size(); i++) {
				double qualiTime;
				int qualiPosition;
				int startingTyreCompound = 0;
				System.out.println("What is " + GlobalInfo.getDriverList().get(i).getName() + "'s qualifying time (in seconds)? ");
				qualiTime = sc.nextDouble();
				System.out.println("What is " + GlobalInfo.getDriverList().get(i).getName() + "'s qualifying position? ");
				qualiPosition = sc.nextInt();
				if (qualiPosition <= 10) {
					System.out.println("What tyre compound did " + GlobalInfo.getDriverList().get(i).getName() + " qualify on? ");
					startingTyreCompound = sc.nextInt();
				}
				infos[i] = new DriverInitInfo(qualiTime, qualiPosition, startingTyreCompound);
			}
			sc.close();
			RaceModel raceModel = new RaceModel(circuit, new int[]{hardCompound, mediumCompound, softCompound}, oneStopPossible,
					twoStopPossible, infos);
			raceModel.simulateRace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
