package programs;

import java.util.ArrayList;

public class ProgramFile {
	private int programID;
	private ArrayList<String> instructions; // arrayList containing instructions of each program

	public ProgramFile(int programID, ArrayList<String> instructions) {
		this.programID = programID;
		this.instructions = instructions;
	}

	public int getProgramID() {
		return programID;
	}

	public ArrayList<String> getInstructions() {
		return instructions;
	}

	public void setInstructions(ArrayList<String> instructions) {
		this.instructions = instructions;
	}
}
