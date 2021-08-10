package Models;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.*;
import java.util.stream.IntStream;
import static Data.GlobalInfo.*;

public class PitStrategyModel {
	boolean oneStopPossible, twoStopPossible;
	ArrayList<String> pitStrategies;
	ArrayList<Object[]> driverStrategies;
	Integer[] tyreCompounds;
	int totalLaps;
	int circuit;
	/*
	CIRCUIT NUMBERS:
	Circuit 1: Japan
	Circuit 2: US
	Circuit 3: Mexico
	Circuit 4: Abu Dhabi
	 */

	public PitStrategyModel(int circuit, int totalLaps, int[] tyreCompounds, boolean oneStopPossible, boolean twoStopPossible){
		pitStrategies = new ArrayList<>();
		this.tyreCompounds = IntStream.of(tyreCompounds).boxed().toArray(Integer[]::new);
		this.oneStopPossible = oneStopPossible;
		this.twoStopPossible = twoStopPossible;
		generatePitStrategies();
		driverStrategies = new ArrayList<>();
		this.totalLaps = totalLaps;
		this.circuit = circuit;
	}

	private void generatePitStrategies() {
		if (oneStopPossible) {
			// soft & hard
			pitStrategies.add("SH");
			pitStrategies.add("HS");
			// hard & medium
			pitStrategies.add("MH");
			pitStrategies.add("HM");
			// soft & medium not enough to get through the race - not a viable strategy
		} if (twoStopPossible) {
			// softs & hards
			pitStrategies.add("SHH");
			pitStrategies.add("HSH");
			pitStrategies.add("HHS");
			pitStrategies.add("SSH");
			pitStrategies.add("SHS");
			pitStrategies.add("HSS");
			// softs & mediums
			pitStrategies.add("SMM");
			pitStrategies.add("MSM");
			pitStrategies.add("MMS");
			pitStrategies.add("SSM");
			pitStrategies.add("SMS");
			pitStrategies.add("MSS");
			// mediums & hards
			pitStrategies.add("HMM");
			pitStrategies.add("MHM");
			pitStrategies.add("MMH");
			pitStrategies.add("HHM");
			pitStrategies.add("HMH");
			pitStrategies.add("MHH");
			// soft, medium & hard
			pitStrategies.add("SMH");
			pitStrategies.add("SHM");
			pitStrategies.add("MSH");
			pitStrategies.add("MHS");
			pitStrategies.add("HMS");
			pitStrategies.add("HSM");
		}
	}

	public void selectDriverStrategy (String driverLastName, int startingTyreCompound) {
		// randomize strategies
		Collections.shuffle(pitStrategies);
		String strategy = "";
		// starting on hards
		if (startingTyreCompound == Collections.min(Arrays.asList(tyreCompounds))) {
			for (String strat : pitStrategies) {
				if (strat.charAt(0) == 'H') strategy = strat;
			}
		}
		// starting on softs
		else if (startingTyreCompound == Collections.max(Arrays.asList(tyreCompounds))) {
			for (String strat : pitStrategies) {
				if (strat.charAt(0) == 'S') strategy = strat;
			}
		}
		// starting on mediums
		else {
			for (String strat : pitStrategies) {
				if (strat.charAt(0) == 'M') strategy = strat;
			}
		}

		// determine when each pit stop will take place
		int[] pitStopLaps = new int[strategy.length() - 1];
		for (int i = 0; i < pitStopLaps.length; i++) {
			switch (strategy.charAt(i)) {
				case 'S':
					pitStopLaps[i] = estimateSoftDuration();
					break;
				case 'M':
					pitStopLaps[i] = estimateMediumDuration();
					break;
				case 'H':
					pitStopLaps[i] = estimateHardDuration();
					break;
			}
		}

		// account for if the first two tyres will theoretically last the entire race in a three-tyre strategy
		if (strategy.length() == 3 && (strategy.substring(0, 2).equals("MH") || strategy.substring(0, 2).equals("HM")
			|| strategy.substring(0, 2).equals("HH")) || strategy.substring(0, 2).equals("MM")) {
			pitStopLaps[0] = (int) (pitStopLaps[0] * 0.35);
		}

		// for each driver, store their name, strategy, and when to pit
		driverStrategies.add(new Object[]{driverLastName, strategy, pitStopLaps});
	}

	// estimate how many laps each tyre will 'last' (to estimate when to pit)

	private int estimateSoftDuration() {
		NormalDistribution nDist = new NormalDistribution((int) (0.33 * totalLaps), 3);
		return (int) nDist.sample();
	}

	private int estimateMediumDuration() {
		NormalDistribution nDist = new NormalDistribution((int) (0.5 * totalLaps), 3);
		return (int) nDist.sample();
	}

	private int estimateHardDuration() {
		NormalDistribution nDist = new NormalDistribution((int) (0.66 * totalLaps), 3);
		return (int) nDist.sample();
	}

	private Object[] getDriverStrategy (String driverLastName) {
		for (Object[] arr : driverStrategies) {
			if (arr[0].equals(driverLastName)) return arr;
		}
		System.out.println("Could not find corresponding driver strategy");
		return null;
	}

	public int checkTyre (String driverLastName, int lapNum, int tyreCompound) {
		int nextTyre = 0;
		Object[] strategyArr = getDriverStrategy(driverLastName);
		int[] pitStopLaps = (int[]) Objects.requireNonNull(strategyArr)[2];
		for (int i = 0; i < pitStopLaps.length; i++) {
			if (pitStopLaps[i] == lapNum) {
				char nextTyreCpd = ((String)strategyArr[1]).charAt(i + 1);
				switch (nextTyreCpd) {
					case 'S':
						nextTyre = Collections.max(Arrays.asList(tyreCompounds));
						break;
					case 'M':
						nextTyre = (Collections.min(Arrays.asList(tyreCompounds)) + Collections.max(Arrays.asList(tyreCompounds))) / 2;
						break;
					case 'H':
						nextTyre = Collections.min(Arrays.asList(tyreCompounds));
						break;
				}
			}
		}
		return nextTyre;
	}

	public double getPitStopTime (String team) {
		double pitStopTime = 0;
		switch (circuit) {
			case 1:
				for (Object arr : JAP_PIT_STOP_TIMES){
					if(((Object[])(arr))[0].equals(team)) pitStopTime = (double) ((Object[])(arr))[1];
				}
				break;
			case 2:
				for (Object arr : US_PIT_STOP_TIMES){
					if(((Object[])(arr))[0].equals(team)) pitStopTime = (double) ((Object[])(arr))[1];
				}
				break;
			case 3:
				for (Object arr : MEX_PIT_STOP_TIMES){
					if(((Object[])(arr))[0].equals(team)) pitStopTime = (double) ((Object[])(arr))[1];
				}
				break;
			case 4:
				for (Object arr : AD_PIT_STOP_TIMES){
					if(((Object[])(arr))[0].equals(team)) pitStopTime = (double) ((Object[])(arr))[1];
				}
				break;
		}
		// add slight variation to pit stop time of ± 0.3 seconds
		double variance = (new Random().nextDouble() * 0.6) - 0.3;
		pitStopTime += variance;
		return pitStopTime;
	}

}
