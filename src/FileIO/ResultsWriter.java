package FileIO;

import com.github.cliftonlabs.json_simple.JsonObject;

import java.util.Scanner;

public class ResultsWriter {
	String circuit;
	int[] qualiPositions;
	int[] racePositions;
	boolean[] dnfStatus;

	public ResultsWriter(String circuit) {
		this.circuit = circuit;
	}

	public void getInfo(){
		Scanner sc;

		try {
			sc = new Scanner(System.in);

		} catch (Exception E) {

		}
	}
}
