package Evaluation;

import Data.GlobalInfo;
import Models.*;
import Util.DriverInitInfo;

import java.util.Scanner;

public class RunSimulation {

	public static void main(String[] args) {

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
