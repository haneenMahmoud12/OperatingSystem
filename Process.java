package programs;

import java.util.*;

public class Process {
	private int processID;
	private int pc; //address of next instruction
	private Status status; //running, ready, waiting,etc..
	private ArrayList<String> instructions; //arrayList containing instructions of each program 
	
	public Process(int processID,int pc,Status status,ArrayList<String> instructions) {
		this.processID=processID;
		this.pc=pc;
		this.status=status;
		this.instructions=instructions;
	}

	public int getProcessID() {
		return processID;
	}

	public void setProcessID(int processID) {
		this.processID = processID;
	}

	public int getPc() {
		return pc;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ArrayList<String> getInstructions() {
		return instructions;
	}

	public void setInstructions(ArrayList<String> instructions) {
		this.instructions = instructions;
	}
	

}
