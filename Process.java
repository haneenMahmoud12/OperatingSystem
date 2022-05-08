package programs;

import java.util.*;

public class Process {
	int processID;
	int pc; //address of next instruction
	Status status; //running, ready, waiting,etc..
	ArrayList<String> instructions; //arrayList containing instructions of each program 
	
	public Process(int processID,int pc,Status status,ArrayList<String> instructions) {
		this.processID=processID;
		this.pc=pc;
		this.status=status;
		this.instructions=instructions;
	}
	

}
