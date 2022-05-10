package programs;

import java.util.*;
import programs.Scheduler;
import programs.SystemCalls;
import programs.Mutex;
import programs.Process;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Interpreter {
	public ArrayList<String> instructions;
	public Mutex file;
	public Mutex userInput;
	public Mutex userOutput;
	private QueueObj readyQ;
	private QueueObj generalBlockedQ;
	private QueueObj finishedProcessesQ;
	private int time;
//	private static ArrayList<ArrayList<Integer>> memoryIntegers;
//	private static ArrayList<ArrayList<String>> memoryStrings;

	//,ArrayList<ArrayList<Integer>> memoryIntegers,ArrayList<ArrayList<String>> memoryStrings
	public Interpreter(QueueObj readyQ, QueueObj generalBlockedQ, QueueObj finishedProcessesQ, int time) {
		this.readyQ= readyQ;
		this.generalBlockedQ= generalBlockedQ;
		this.finishedProcessesQ=finishedProcessesQ;
		file = new Mutex("file",readyQ, generalBlockedQ);
		userInput = new Mutex("userInput", readyQ, generalBlockedQ);
		userOutput = new Mutex("userOutput", readyQ, generalBlockedQ);
		this.instructions=new ArrayList<String>();
		this.time=time;
//		SystemCalls systemCalls = new SystemCalls();
		
//		this.memoryIntegers=memoryIntegers;
//		this.memoryStrings=memoryStrings;
	}

	public void interpretation(int PID) throws IOException, FileNotFoundException {
		BufferedReader br;
		switch (PID) {
		case 1:
			br = new BufferedReader(new FileReader("C:\\Lucy\\Semester 6\\Operating Systems (CSEN 602)\\OSProject\\Team_37\\src\\programs\\Program_1.txt"));
			break;
		case 2:
			br = new BufferedReader(new FileReader("C:\\Lucy\\Semester 6\\Operating Systems (CSEN 602)\\OSProject\\Team_37\\src\\programs\\Program_2.txt"));
			break;
		case 3:
			br = new BufferedReader(new FileReader("C:\\Lucy\\Semester 6\\Operating Systems (CSEN 602)\\OSProject\\Team_37\\src\\programs\\Program_3.txt"));
			break;
		default:
			br = null;
		}
		String currentLine = br.readLine();
		while (currentLine != null) {
			instructions.add(currentLine);
			currentLine = br.readLine();
		}

		// create new process that has PCB+program code(instructions).
		Process p = new Process(PID, 0, Status.NEW, instructions);
		p.setStatus(Status.READY);
		readyQ.enqueue(p);
	}

	public void convert(Process p, int timeSlice) throws IOException {
		//SystemCalls sysCalls = new SystemCalls();
		
		for (int i = 0; i < timeSlice; i++) {
			if(p.getPc()==p.getInstructions().size()){
				p.setStatus(Status.FINISHED);
				finishedProcessesQ.enqueue(p);
				System.out.println("ready Queue");
				readyQ.printQueue();
				System.out.println("Blocked Queue");
				generalBlockedQ.printQueue();
				this.time++;
				break;
			}
			String[] content = p.getInstructions().get(p.getPc()).split(" ");
			if (p.getInstructions().get(i).toLowerCase().contains("print"))
				SystemCalls.print(content[1], p.getProcessID());
			else if (p.getInstructions().get(p.getPc()).toLowerCase().contains("assign")) {
				if(content[2].equals("readFile")) {
					Scanner sc = new Scanner(System.in);
					System.out.print("Please enter a value");
					String input = sc.next();
					try {
						int y = Integer.parseInt(input);
						SystemCalls.assign(content[1], y,p.getProcessID());
						p.setPc((p.getPc())+1);
						this.time++;
						break;
					} catch(Exception e) {
						String y = input;
						SystemCalls.assign(content[1], y,p.getProcessID());
						p.setPc((p.getPc())+1);
						this.time++;
						break;
					}
				} else {
					try {
						SystemCalls.assign(content[1], Integer.parseInt(content[content.length-1]), p.getProcessID());
					} catch (Exception e) {
						SystemCalls.assign(content[1], content[content.length-1], p.getProcessID());
					}
				}
					
			} else if (p.getInstructions().get(p.getPc()).toLowerCase().contains("writeFile"))
				SystemCalls.writeFile(content[1], content[2]);
			else if (p.getInstructions().get(p.getPc()).toLowerCase().contains("readFile"))
				SystemCalls.readFile(content[1]);
			else if (p.getInstructions().get(p.getPc()).toLowerCase().contains("printFromTo"))
				SystemCalls.printFromTo(Integer.parseInt(content[1]), Integer.parseInt(content[2]));
			else if (p.getInstructions().get(p.getPc()).toLowerCase().contains("semWait")) {
				if (content[1].equals("file")) {
					file.semWait("file", p);
				} else if (content[1].equals("userInput")) {
					userInput.semWait("userInput", p);
				} else {
					userOutput.semWait("userOutput", p);
				}
			} else if (p.getInstructions().get(p.getPc()).toLowerCase().contains("semSignal")) {
				if (content[1].equals("file")) {
					file.semSignal("file", p);
				} else if (content[1].equals("userInput")) {
					userInput.semSignal("userInput", p);
				} else {
					userOutput.semSignal("userOutput", p);
				}
			}
			
			System.out.println("Instrustion" +" "+ p.getInstructions().get(p.getPc())+ " "+ "is currently executing");
			p.setPc((p.getPc())+1);
			this.time++;
		}

	}

	public ArrayList<String> getInstructions() {
		return instructions;
	}

	public void setInstructions(ArrayList<String> instructions) {
		this.instructions = instructions;
	}

	public Mutex getFile() {
		return file;
	}

	public void setFile(Mutex file) {
		this.file = file;
	}

	public Mutex getUserInput() {
		return userInput;
	}

	public void setUserInput(Mutex userInput) {
		this.userInput = userInput;
	}

	public Mutex getUserOutput() {
		return userOutput;
	}

	public void setUserOutput(Mutex userOutput) {
		this.userOutput = userOutput;
	}

	public QueueObj getReadyQ() {
		return readyQ;
	}

	public void setReadyQ(QueueObj readyQ) {
		this.readyQ = readyQ;
	}

	public QueueObj getGeneralBlockedQ() {
		return generalBlockedQ;
	}

	public void setGeneralBlockedQ(QueueObj generalBlockedQ) {
		this.generalBlockedQ = generalBlockedQ;
	}

	public QueueObj getFinishedProcessesQ() {
		return finishedProcessesQ;
	}

	public void setFinishedProcessesQ(QueueObj finishedProcessesQ) {
		this.finishedProcessesQ = finishedProcessesQ;
	}

}