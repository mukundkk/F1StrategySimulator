package Evaluation;

import Models.*;
import Util.DriverInitInfo;

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
		Sainz
		Ericsson
		Nasr
		Alonso
		Button
		Wehrlein
		Ocon
		Grosjean
		Gutierrez
		 */
		DriverInitInfo rosberg = new DriverInitInfo(90.647, 1, 2);
		DriverInitInfo hamilton = new DriverInitInfo(90.660, 2, 2);
		DriverInitInfo vettel = new DriverInitInfo(91.028, 4, 2);
		DriverInitInfo raikkonen = new DriverInitInfo(90.949, 3, 2);
		DriverInitInfo ricciardo = new DriverInitInfo(91.240, 6, 2);
		DriverInitInfo verstappen = new DriverInitInfo(91.178, 5, 2);
		DriverInitInfo massa = new DriverInitInfo(92.380, 12, 2);
		DriverInitInfo bottas = new DriverInitInfo(92.315, 11, 2);
		DriverInitInfo hulkenberg = new DriverInitInfo(92.142, 9, 2);
		DriverInitInfo perez = new DriverInitInfo(91.961, 7, 2);
		DriverInitInfo magnussen = new DriverInitInfo(93.023, 18, 2);
		DriverInitInfo palmer = new DriverInitInfo(92.796, 16, 2);
		DriverInitInfo kvyat = new DriverInitInfo(92.623, 13, 2);
		DriverInitInfo sainz = new DriverInitInfo(92.685, 14, 2);
		DriverInitInfo ericsson = new DriverInitInfo(93.222, 19, 2);
		DriverInitInfo nasr = new DriverInitInfo(93.332, 20, 2);
		DriverInitInfo alonso = new DriverInitInfo(92.689, 15, 2);
		DriverInitInfo button = new DriverInitInfo(92.851, 17, 2);
		DriverInitInfo wehrlein = new DriverInitInfo(93.561, 22, 2);
		DriverInitInfo ocon = new DriverInitInfo(93.353, 21, 2);
		DriverInitInfo grosjean = new DriverInitInfo(91.961, 8, 2);
		DriverInitInfo gutierrez = new DriverInitInfo(92.155, 10, 2);

		DriverInitInfo[] driverInfos = new DriverInitInfo[]{rosberg, hamilton, vettel, raikkonen, ricciardo, verstappen, massa, bottas,
		hulkenberg, perez, magnussen, palmer, kvyat, sainz, ericsson, nasr, alonso, button, wehrlein, ocon, grosjean, gutierrez};

		RaceModel race = new RaceModel(1, driverInfos);
		try {
			race.simulateRace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
