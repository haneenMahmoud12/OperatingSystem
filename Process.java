package programs;

import java.util.*;

public class Process {
	private int processID;
	private int pc; //address of next instruction
	private Status status; //running, ready, waiting,etc..
	private ArrayList<String> instructions; //arrayList containing instructions of each program 
	private int memoryUpperBound;
	private int memoryLowerBound;
	
	public Process(int processID,int pc,Status status,ArrayList<String> instructions, int memoryLowerBound,int memoryUpperBound) {
		this.processID=processID;
		this.pc=pc;
		this.status=status;
		this.instructions=instructions;
		this.memoryUpperBound=memoryUpperBound;
		this.memoryLowerBound=memoryLowerBound;
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

	public int getMemoryUpperBound() {
		return memoryUpperBound;
	}

	public void setMemoryUpperBound(int memoryUpperBound) {
		this.memoryUpperBound = memoryUpperBound;
	}

	public int getMemoryLowerBound() {
		return memoryLowerBound;
	}

	public void setMemoryLowerBound(int memoryLowerBound) {
		this.memoryLowerBound = memoryLowerBound;
	}
	

}
