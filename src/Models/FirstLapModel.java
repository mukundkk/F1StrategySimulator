package Models;

import static Data.GlobalInfo.getDriverList;
import Util.FirstLapDistribution;
import Util.Driver;

import java.util.ArrayList;

public class FirstLapModel {
	private ArrayList<FirstLapDistribution> driverFirstLapDistributions;

	public FirstLapModel() {
		driverFirstLapDistributions = new ArrayList<>();
	}

	private void setDriverFirstLapDistributions(){
		for(Driver driver : getDriverList()) {
			FirstLapDistribution flDistribution = new FirstLapDistribution();
			/*
			TODO: get gainedPositions array data (in FirstLapDistribution) from corresponding
				array in GlobalInfo
			 */
		}
	}
}
