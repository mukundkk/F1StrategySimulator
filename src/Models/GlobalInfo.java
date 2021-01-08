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

	public static void setupRace() {
		Collections.addAll(drivers, nRosberg, lHamilton);
	}
}
