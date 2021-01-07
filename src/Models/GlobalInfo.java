package Models;

import org.apache.commons.math3.*;

import java.util.ArrayList;

public class GlobalInfo {
	public static int circuit, totalLaps;
	public static ArrayList<Driver> drivers;

	public static void setTotalLaps(int totalLaps) {
		GlobalInfo.totalLaps = totalLaps;
	}
}
