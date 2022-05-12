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
	public Mutex file;
	public Mutex userInput;
	public Mutex userOutput;

	public Interpreter() {
		file = new Mutex("file");
		userInput = new Mutex("userInput");
		userOutput = new Mutex("userOutput");
	}

	public void interpretation(int PID) throws IOException, FileNotFoundException {
		ArrayList<String> instructionsHere = new ArrayList<String>();
		BufferedReader br;
		switch (PID) {
		case 1: {
			br = new BufferedReader(new FileReader(
					"C:\\Lucy\\Semester 6\\Operating Systems (CSEN 602)\\OSProject\\Team_37\\src\\programs\\Program_1.txt"));
			String currentLine = br.readLine();
			while (currentLine != null) {
				instructionsHere.add(currentLine);
				currentLine = br.readLine();
			}
			break;
		}
		case 2: {
			br = new BufferedReader(new FileReader(
					"C:\\Lucy\\Semester 6\\Operating Systems (CSEN 602)\\OSProject\\Team_37\\src\\programs\\Program_2.txt"));
			String currentLine = br.readLine();
			while (currentLine != null) {
				instructionsHere.add(currentLine);
				currentLine = br.readLine();
			}
			break;
		}
		case 3: {
			br = new BufferedReader(new FileReader(
					"C:\\Lucy\\Semester 6\\Operating Systems (CSEN 602)\\OSProject\\Team_37\\src\\programs\\Program_3.txt"));
			String currentLine = br.readLine();
			while (currentLine != null) {
				instructionsHere.add(currentLine);
				currentLine = br.readLine();
			}
			break;
		}
		default:
			br = null;
			System.out.println("Program " + PID + " not found.");
		}

		// create new process that has PCB+program code(instructions).
		Process p = new Process(PID, 0, Status.NEW, instructionsHere);
		p.setStatus(Status.READY);
		Main.getReadyQ().enqueue(p);
	}

	public void convert(Process p) throws IOException {

		for (int i = 0; i < Main.getTimeSlice(); i++) {

			if (p.getPc() >= p.getInstructions().size()) {
				break;
			} else if (p.getStatus() == Status.BLOCKED) {
				for (int j = 0; j < Main.getReadyQ().size(); j++) {
					if (Main.getReadyQ().peek().getProcessID() == p.getProcessID()) {
						Main.getReadyQ().dequeue();
						break;
					} else
						Main.getReadyQ().enqueue(Main.getReadyQ().dequeue());
				}
				break;
			} else {

				System.out.println(" Instrustion " + p.getInstructions().get(p.getPc()) + " is currently executing");
				String[] content = p.getInstructions().get(p.getPc()).split(" ");
				if (p.getInstructions().get(p.getPc()).contains("printFromTo"))
					SystemCalls.printFromTo(content[1], content[2], p.getProcessID());
				else if (p.getInstructions().get(p.getPc()).contains("print"))
					SystemCalls.print(content[1], p.getProcessID());
				else if (p.getInstructions().get(p.getPc()).contains("assign")) {
					if (content[content.length - 2].equals("readFile")) {
						Object y = SystemCalls.readFile(content[3]);
						Main.setTime(Main.getTime() + 1);
						if (Main.getTime() == Main.getTime4Process1()) {
							Main.getIp().interpretation(Main.getP1id());
						} else if (Main.getTime() == Main.getTime4Process2()) {
							Main.getIp().interpretation(Main.getP2id());
						} else if (Main.getTime() == Main.getTime4Process3()) {
							Main.getIp().interpretation(Main.getP3id());
						}
						i++;
						if (i == Main.getTimeSlice()) {
							ArrayList<String> s = new ArrayList<>();
							for (int j = 0; j < p.getInstructions().size(); j++) {
								if (j!=p.getPc()) {
									s.add(p.getInstructions().get(j));
								} else {
									s.add(p.getInstructions().get(j));
									s.add("assign " + content[1] + " " + y);
								}
							}
							p.setInstructions(s);
						} else {
							SystemCalls.assign(content[1], y, p.getProcessID());
						}
					} else if (content[content.length - 1].equals("input")) {
						Scanner sc = new Scanner(System.in);
						System.out.print("Please enter a value");
						Object input = sc.next();
						Main.setTime(Main.getTime() + 1);
						if (Main.getTime() == Main.getTime4Process1()) {
							Main.getIp().interpretation(Main.getP1id());
						} else if (Main.getTime() == Main.getTime4Process2()) {
							Main.getIp().interpretation(Main.getP2id());
						} else if (Main.getTime() == Main.getTime4Process3()) {
							Main.getIp().interpretation(Main.getP3id());
						}
						i++;
						if (i == Main.getTimeSlice()) {
							ArrayList<String> s = new ArrayList<>();
							for (int j = 0; j < p.getInstructions().size(); j++) {
								if (j!=p.getPc()) {
									s.add(p.getInstructions().get(j));
								} else {
									s.add(p.getInstructions().get(j));
									s.add("assign " + content[1] + " " + input);
								}
							}
							p.setInstructions(s);
						} else {
							try {
								int y = Integer.parseInt((String) input);
								SystemCalls.assign(content[1], input, p.getProcessID());
							} catch (Exception e) {
								String y = (String) input;
								SystemCalls.assign(content[1], y, p.getProcessID());
							}
						}
					} else {
						try {
							int y = Integer.parseInt((String) content[2]);
							SystemCalls.assign(content[1], content[2], p.getProcessID());
						} catch (Exception e) {
							String y = (String) content[2];
							SystemCalls.assign(content[1], y, p.getProcessID());
						}
					}
				} else if (p.getInstructions().get(p.getPc()).contains("writeFile"))
					SystemCalls.writeFile(content[1], content[2]);
				else if (p.getInstructions().get(p.getPc()).contains("readFile"))
					SystemCalls.readFile(content[1]);
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

				p.setPc((p.getPc()) + 1);
				Main.setTime(Main.getTime() + 1);
				if (Main.getTime() == Main.getTime4Process1()) {
					Main.getIp().interpretation(Main.getP1id());
				} else if (Main.getTime() == Main.getTime4Process2()) {
					Main.getIp().interpretation(Main.getP2id());
				} else if (Main.getTime() == Main.getTime4Process3()) {
					Main.getIp().interpretation(Main.getP3id());
				}
			}
		}
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