package Models;

import org.apache.commons.math3.*;

import java.util.ArrayList;
import java.util.Collections;

public class GlobalInfo {
	public static final int DEFAULT_NUM_LAPS = 55;

	public static final double DRS_THRESHOLD = 1.0;
	public static final double DRS_BONUS = -0.5;
	public static final double OVERTAKING_PENALTY = 0.5;
	public static final double MIN_TIME_DIFFERENCE = 0.2;

	public static final double DEFAULT_OVERTAKING_THRESHOLD = -1.7;
	public static final double DEFAULT_OVERTAKING_PROBABILITY = 0.15;

	public static int circuit;
	public static ArrayList<Driver> drivers = new ArrayList<>();

	public static Driver nRosberg = new Driver(5.595, 0.017, new double[]{0.553, 0.002, 0}, new double[]{0.035, -0.026, 0},
			new double[]{0.076, -0.037, 0}, new double[]{0.249, 0.017, 0}, new double[]{0.035, 0.034, 0}, 1.480, "Nico Rosberg");
	public static Driver lHamilton = new Driver(5.266, 0.022, new double[]{-0.085, -0.038, 0}, new double[]{0.141, 0.068, 0},
			new double[]{-0.457, -0.013, 0}, new double[]{-0.001, 0.072, 0}, new double[]{0.493, -0.067, 0}, 1.496, "Lewis Hamilton");
	public static Driver sVettel = new Driver(5.180, 0.018, new double[]{-1.19, 0.126, 0}, new double[]{-0.508, -0.008, 0},
			new double[]{0.130, -0.012, 0}, new double[]{0.704, 0.039, 0}, new double[]{0, 0, 0}, 1.394, "Sebastian Vettel");
	public static Driver kRaikkonen = new Driver(4.890, 0.022, new double[]{-0.524, 0.087, 0}, new double[]{-0.101, -0.031, 0},
			new double[]{0.326, -0.049, 0}, new double[]{0.765, 0.040, 0}, new double[]{0.077, 0.055, 0}, 1.243, "Kimi Räikkönen");
	public static Driver dRicciardo = new Driver(4.921, 0.026, new double[]{-2.036, -0.006, 0}, new double[]{-0.292, 0.029, 0},
			new double[]{0.234, -0.037, 0}, new double[]{0.581, 0.004, 0}, new double[]{-0.018, 0.034, 0}, 1.239, "Daniel Ricciardo");
	public static Driver mVerstappen = new Driver(5.080, 0.020, new double[]{-1.962, 0.020, 0}, new double[]{0.067, 0.032, 0},
			new double[]{0.276, -0.053, 0}, new double[]{0.563, 0.006, 0}, new double[]{-0.126, 0.011, 0}, 1.432, "Max Verstappen");
	public static Driver fMassa = new Driver(5.606, 0.018, new double[]{1.044, -0.044, 0}, new double[]{0.059, -0.054, 0},
			new double[]{-0.632, -0.024, 0}, new double[]{0.398, 0.032, 0}, new double[]{1.146, 0.015, 0}, 1.373, "Felipe Massa");
	public static Driver vBottas = new Driver(5.493, 0.022, new double[]{-0.977, -0.107, 0}, new double[]{-0.405, 0.029, 0},
			new double[]{-0.086, -0.025, 0}, new double[]{0.761, 0.030, 0}, new double[]{0.078, -0.056, 0}, 1.446, "Valtteri Bottas");
	public static Driver nHulkenberg = new Driver(4.854, 0.026, new double[]{-1.898, 0.045, 0}, new double[]{-0.621, -0.031, 0},
			new double[]{-0.142, -0.013, 0}, new double[]{1.006, 0.005, 0}, new double[]{0.729, -0.099, 0}, 1.085, "Nico Hülkenberg");
	public static Driver sPerez = new Driver(5.313, 0.022, new double[]{-1.334, -0.273, 0}, new double[]{-0.850, -0.034, 0},
			new double[]{0.046, -0.025, 0}, new double[]{0.681, 0.021, 0}, new double[]{-0.212, -0.014, 0}, 1.560, "Sergio Pérez");

	public static void setupRace() {
		Collections.addAll(drivers, nRosberg, lHamilton, sVettel, kRaikkonen, dRicciardo, mVerstappen, fMassa, vBottas, nHulkenberg, sPerez);
	}
}
