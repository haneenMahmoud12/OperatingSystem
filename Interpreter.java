package programs;

import java.util.*;
import programs.SystemCalls;
import programs.Mutex;
import programs.Process;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Interpreter {
	public ArrayList<String> instructions;
	public QueueObj readyQueue; 
	public QueueObj blockedQueue;
	public Mutex file;
	public Mutex userInput;
	public Mutex userOutput;
	public Interpreter(QueueObj readyQueue,QueueObj blockedQueue) {
		readyQueue=this.readyQueue;
		blockedQueue=this.blockedQueue;
		file = new Mutex("file");
		userInput=new Mutex("userInput");
		userOutput=new Mutex("userOutput");
	}
	public void interpretation(int PID)  throws IOException,FileNotFoundException {
		BufferedReader br;
		switch(PID){
			case 1: br = new BufferedReader(new FileReader("Program_1.txt"));break;
			case 2:br = new BufferedReader(new FileReader("Program_2.txt"));break;
			case 3: br = new BufferedReader(new FileReader("Program_3.txt"));break;
			default: br=null;
		}
		String currentLine = br.readLine();
		while(currentLine != null) {
			instructions.add(currentLine);
		}
		
		//create new process that has PCB+program code(instructions).
		Process p = new Process(PID,0,Status.NEW,instructions);
	}
	public void convert(Process p) throws IOException {
		for(int i=0;i<p.instructions.size();i++) {
			String[] content = p.instructions.get(i).split(" ");
			if(p.instructions.get(i).toLowerCase().contains("print")) 
				SystemCalls.print(content[1]);
			else if(p.instructions.get(i).toLowerCase().contains("assign")) {
				try {
					SystemCalls.assign(Integer.parseInt(content[1]),Integer.parseInt(content[2]));
				}
				catch(Exception e) {
					SystemCalls.assign(content[1], content[2]);
				}
			}
			else if(p.instructions.get(i).toLowerCase().contains("writeFile"))
				SystemCalls.writeFile(content[1], content[2]);
			else if(p.instructions.get(i).toLowerCase().contains("readFile"))
				SystemCalls.readFile(content[1]);
			else if(p.instructions.get(i).toLowerCase().contains("printFromTo"))
				SystemCalls.printFromTo(Integer.parseInt(content[1]), Integer.parseInt(content[2]));
			else if(p.instructions.get(i).toLowerCase().contains("semWait")) {
				if(content[1].equals("file")) {
					file.semWait("file", p);
				}
				else if(content[1].equals("userInput")) {
					userInput.semWait("userInput", p);
				}
				else {
					userOutput.semWait("userOutput", p);
				}
			}
			else {
				if(content[1].equals("file")) {
					file.semSignal("file", p);
				}
				else if(content[1].equals("userInput")) {
					userInput.semSignal("userInput", p);
				}
				else {
					userOutput.semSignal("userOutput", p);
				}
			}
				
		}
	}
	
}
