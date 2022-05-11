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
	public Interpreter() {
		file = new Mutex("file");
		userInput = new Mutex("userInput");
		userOutput = new Mutex("userOutput");
		this.instructions=new ArrayList<String>();
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
		Main.getReadyQ().enqueue(p);
		System.out.println("ready Queue:");
		Main.getReadyQ().printQueue();
		System.out.println("Blocked Queue:");
		Main.getGeneralBlockedQ().printQueue();
	}

	public void convert(Process p) throws IOException {
		
		for (int i = 0; i < Main.getTimeSlice(); i++) {
			if(p.getPc()==p.getInstructions().size()){
				p.setStatus(Status.FINISHED);
				Main.getFinishedProcessesQ().enqueue(p);
				System.out.println("ready Queue");
				Main.getReadyQ().printQueue();
				System.out.println("Blocked Queue");
				Main.getGeneralBlockedQ().printQueue();
				Main.setTime(Main.getTime()+1);
				if(Main.getTime()==Main.getTime4Process1()) {
					Main.getIp().interpretation(Main.getP1id());
				}
				else if(Main.getTime()==Main.getTime4Process2()) {
					Main.getIp().interpretation(Main.getP2id());
				}
				else if(Main.getTime()==Main.getTime4Process3()){
					Main.getIp().interpretation(Main.getP3id());
				}
				break;
			}
			if(p.getStatus()==Status.BLOCKED) {
				for(int j=0;j<Main.getReadyQ().size();j++) {
					if(Main.getReadyQ().peek().getProcessID()==p.getProcessID()) {
						Main.getReadyQ().dequeue();
						break;
					}
					else
						Main.getReadyQ().enqueue(Main.getReadyQ().dequeue());
				}
				break;
			}
			else {
			System.out.println("Instrustion" +" "+ p.getInstructions().get(p.getPc())+ " "+ "is currently executing");
			String[] content = p.getInstructions().get(p.getPc()).split(" ");
			if (p.getInstructions().get(i).contains("print"))
				SystemCalls.print(content[1], p.getProcessID());
			else if (p.getInstructions().get(p.getPc()).contains("assign")) {
				if(content[2].equals("readFile")) {
					Scanner sc = new Scanner(System.in);
					System.out.print("Please enter a value");
					String input = sc.next();
					try {
						int y = Integer.parseInt(input);
						SystemCalls.assign(content[1], y,p.getProcessID());
						p.setPc((p.getPc())+1);
						Main.setTime(Main.getTime()+1);
						break;
					} catch(Exception e) {
						String y = input;
						SystemCalls.assign(content[1], y,p.getProcessID());
						p.setPc((p.getPc())+1);
						Main.setTime(Main.getTime()+1);
						break;
					}
				} else {
					try {
						SystemCalls.assign(content[1], Integer.parseInt(content[content.length-1]), p.getProcessID());
					} catch (Exception e) {
						SystemCalls.assign(content[1], content[content.length-1], p.getProcessID());
					}
				}
					
			} else if (p.getInstructions().get(p.getPc()).contains("writeFile"))
				SystemCalls.writeFile(content[1], content[2]);
			else if (p.getInstructions().get(p.getPc()).contains("readFile"))
				SystemCalls.readFile(content[1]);
			else if (p.getInstructions().get(p.getPc()).contains("printFromTo"))
				SystemCalls.printFromTo(content[1], content[2]);
			else if (p.getInstructions().get(p.getPc()).contains("semWait")) {
				if (content[1].equals("file")) {
					file.semWait("file", p);
				} else if (content[1].equals("userInput")) {
					userInput.semWait("userInput", p);
				} else {
					userOutput.semWait("userOutput", p);
				}
			} else if (p.getInstructions().get(p.getPc()).contains("semSignal")) {
				if (content[1].equals("file")) {
					file.semSignal("file", p);
				} else if (content[1].equals("userInput")) {
					userInput.semSignal("userInput", p);
				} else {
					userOutput.semSignal("userOutput", p);
				}
			}
			
			p.setPc((p.getPc())+1);
			Main.setTime(Main.getTime()+1);
			if(Main.getTime()==Main.getTime4Process1()) {
				Main.getIp().interpretation(Main.getP1id());
			}
			else if(Main.getTime()==Main.getTime4Process2()) {
				Main.getIp().interpretation(Main.getP2id());
			}
			else if(Main.getTime()==Main.getTime4Process3()){
				Main.getIp().interpretation(Main.getP3id());
			}
		}
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



}