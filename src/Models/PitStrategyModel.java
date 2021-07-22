package Models;

public class PitStrategyModel {
	/*
	For each race, have user input possible pit stop strategies/tyre compound strategies as suggested by Pirelli. These strategies should be stored in the JSON file for the respective race.

	Basic version: before the start of the race (during init()), randomly select a strategy for each driver (as long as it coincides to the driver's starting tyre compound). For drivers starting in the top 10, they must start with the tyres they qualified for Q3 on (also needs to be specified by user). The other drivers may start with any tyre (randomly chosen out of the three possible compounds available for the race). Assign starting tyre. During the race (in loop()), check if it is the correct lap to switch tyres. If it is, add estimated pit stop duration time (given by average pit stop duration for the team during the previous year (± randomly generated variation of no more than 0.3 seconds) to driver's race time. Change tyre compound during this step as well. Follow same steps if there is more than 1 pit stop scheduled for driver.

	Later versions:
	 */

}
