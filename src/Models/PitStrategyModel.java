package Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class PitStrategyModel {
	boolean oneStopPossible, twoStopPossible;
	ArrayList<String> pitStrategies;
	ArrayList<String[]> driverStrategies;
	Integer[] tyreCompounds;
	/*
	TODO: For each race, have user input possible pit stop strategies/tyre compound strategies as suggested by Pirelli. (Later: These strategies should be stored in the JSON file for the respective race.) Basic version: before the start of the race (during init()), randomly select a strategy for each driver (as long as it coincides to the driver's starting tyre compound). For drivers starting in the top 10, they must start with the tyres they qualified for Q3 on (also needs to be specified by user). The other drivers may start with any tyre (randomly chosen out of the three possible compounds available for the race). Assign starting tyre. During the race (in loop()), check if it is the correct lap to switch tyres. If it is, add estimated pit stop duration time (given by average pit stop duration for the team during the previous year (± randomly generated variation of no more than 0.3 seconds) to driver's race time. Change tyre compound during this step as well. Follow same steps if there is more than 1 pit stop scheduled for driver.

*/

	public PitStrategyModel(int[] tyreCompounds, boolean oneStopPossible, boolean twoStopPossible){
		this.tyreCompounds = IntStream.of(tyreCompounds).boxed().toArray(Integer[]::new);
		this.oneStopPossible = oneStopPossible;
		this.twoStopPossible = twoStopPossible;
		driverStrategies = new ArrayList<>();
	}

	private ArrayList<String> generatePitStrategies() {
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
		return pitStrategies;
	}

	public void selectDriverStrategy (String driverLastName, int startingTyreCompound) {
		// randomize strategies
		Collections.shuffle(pitStrategies);
		String strategy = "";
		// starting on hards
		if (startingTyreCompound == Collections.min(Arrays.asList(tyreCompounds))) {
			for (String strat : pitStrategies) {
				if (strat.startsWith("H")) strategy = strat;
			}
		}
		// starting on softs
		else if (startingTyreCompound == Collections.max(Arrays.asList(tyreCompounds))) {
			for (String strat : pitStrategies) {
				if (strat.startsWith("S")) strategy = strat;
			}
		}
		// starting on mediums
		else {
			for (String strat : pitStrategies) {
				if (strat.startsWith("M")) strategy = strat;
			}
		}
		driverStrategies.add(new String[]{driverLastName, strategy});
	}

}
