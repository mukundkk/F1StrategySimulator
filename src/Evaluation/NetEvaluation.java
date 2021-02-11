package Evaluation;

import Models.*;

public class NetEvaluation {

	public static void main(String[] args) {
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
		RaceModel race = new RaceModel(1, new int[]{2, 3, 4}, new double[]{90.647, 90.660, 91.028, 90.949, 91.240, 91.178, 92.380,
				92.315, 92.142, 91.961, 93.023, 92.796, 92.623, 92.685, 93.222, 93.332, 92.689, 92.851, 93.561, 93.353, 91.961, 92.155},
				new int[]{1, 2, 4, 3, 6, 5, 12, 11, 9, 7, 18, 16, 13, 14, 19, 20, 15, 17, 22, 21, 8, 10});
		race.simulateRace();
	}

}
