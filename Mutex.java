package programs;

import programs.QueueObj;
import programs.Scheduler;

public class Mutex {
	private boolean file;
	private QueueObj fileBlockedQ;
	private boolean userInput;
	private QueueObj userInputBlockedQ;
	private boolean userOutput;
	private QueueObj userOutputBlockedQ;
	private int pid_file;
	private int pid_input;
	private int pid_output;

	public Mutex(String m) {
		if (m.equals("file")) {
			this.file = true;
			fileBlockedQ = new QueueObj(3);
		} else if (m.equals("userInput")) {
			this.userInput = true;
			userInputBlockedQ = new QueueObj(3);
		} else {
			this.userOutput = true;
			userOutputBlockedQ = new QueueObj(3);
		}
	}

//when semWait()/semSignal() is called, it returns the value of the flag. The caller will then handle what to do, 
//	depending on the value of the flag.

	public void semWait(String Flag, Process p) {
		int statusLocation;
		if (Flag.equals("file")) {
			if (this.file == true) {
				this.file = false;
				pid_file = p.getProcessID();
			} else {
				p.setStatus(Status.BLOCKED);
				statusLocation = p.getMemoryLowerBound()+2;
				Main.getMemory()[statusLocation] = p.getStatus();
				this.fileBlockedQ.enqueue(p);
				Main.getGeneralBlockedQ().enqueue(p);
				System.out.println("ready Queue");
				Main.getReadyQ().printQueue();
				System.out.println("Blocked Queue");
				Main.getGeneralBlockedQ().printQueue();
				System.out.println("File Blocked Queue");
				fileBlockedQ.printQueue();
			}
		} else if (Flag.equals("userInput")) {
			if (this.userInput == true) {
				this.userInput = false;
				pid_input = p.getProcessID();
			} else {
				p.setStatus(Status.BLOCKED);
				statusLocation = p.getMemoryLowerBound()+2;
				Main.getMemory()[statusLocation] = p.getStatus();
				this.userInputBlockedQ.enqueue(p);
				Main.getGeneralBlockedQ().enqueue(p);
				System.out.println("ready Queue");
				Main.getReadyQ().printQueue();
				System.out.println("Blocked Queue");
				Main.getGeneralBlockedQ().printQueue();
				System.out.println("User Input Blocked Queue");
				userInputBlockedQ.printQueue();
			}
		} else {
			if (this.userOutput == true) {
				this.userOutput = false;
				pid_output = p.getProcessID();
			} else {
				p.setStatus(Status.BLOCKED);
				statusLocation = p.getMemoryLowerBound()+2;
				Main.getMemory()[statusLocation] = p.getStatus();
				this.userOutputBlockedQ.enqueue(p);
				Main.getGeneralBlockedQ().enqueue(p);
				System.out.println("ready Queue");
				Main.getReadyQ().printQueue();
				System.out.println("Blocked Queue");
				Main.getGeneralBlockedQ().printQueue();
				System.out.println("User Output Blocked Queue");
				userOutputBlockedQ.printQueue();
			}
		}

	}

	public void semSignal(String Flag, Process p) {
		int statusLocation;
		if (Flag.equals("file")) {
			if (p.getProcessID() == pid_file) {
				if (fileBlockedQ.isEmpty())
					file = true;
				else {
					Process p1 = fileBlockedQ.dequeue();
					for(int i=0;i<Main.getGeneralBlockedQ().size();i++) {
						Process p2 = Main.getGeneralBlockedQ().dequeue();
						if(p2.getProcessID()!=p1.getProcessID()) {
							Main.getGeneralBlockedQ().enqueue(p2);
						} else
							break;	
					}
					p1.setStatus(Status.READY);
					statusLocation = p.getMemoryLowerBound()+2;
					Main.getMemory()[statusLocation] = p.getStatus();
					if(Main.getReadyQ().peek().getProcessID()!=p1.getProcessID())
						Main.getReadyQ().enqueue(p1);
					pid_file = p1.getProcessID();
				}
			}
		} else if (Flag.equals("userInput")) {
			if (p.getProcessID() == pid_input) {
				if (userInputBlockedQ.isEmpty())
					userInput = true;
				else {
					Process p1 = (Process) userInputBlockedQ.dequeue();
					for(int i=0;i<Main.getGeneralBlockedQ().size();i++) {
						Process p2 = Main.getGeneralBlockedQ().dequeue();
						if(p2.getProcessID()!=p1.getProcessID()) {
							Main.getGeneralBlockedQ().enqueue(p2);
						} else
							break;	
					}
					p1.setStatus(Status.READY);
					statusLocation = p.getMemoryLowerBound()+2;
					Main.getMemory()[statusLocation] = p.getStatus();
					if(Main.getReadyQ().peek().getProcessID()!=p1.getProcessID())
						Main.getReadyQ().enqueue(p1);
					pid_input = p1.getProcessID();
				}
			}
		}

		else {
			if (p.getProcessID() == pid_output) {
				if (userOutputBlockedQ.isEmpty())
					userOutput = true;
				else {
					Process p1 = (Process) userOutputBlockedQ.dequeue();
					for(int i=0;i<Main.getGeneralBlockedQ().size();i++) {
						Process p2 = Main.getGeneralBlockedQ().dequeue();
						if(p2.getProcessID()!=p1.getProcessID()) {
							Main.getGeneralBlockedQ().enqueue(p2);
						} else
							break;	
					}
					p1.setStatus(Status.READY);
					statusLocation = p.getMemoryLowerBound()+2;
					Main.getMemory()[statusLocation] = p.getStatus();
					if(Main.getReadyQ().peek().getProcessID()!=p1.getProcessID())
						Main.getReadyQ().enqueue(p1);
					pid_output = p1.getProcessID();
				}
			}
		}
	}

//setters are used by the caller in order to set the flags depending on the returned value of semWait()/semSignal() 

	public void setFile(boolean file) {
		this.file = file;
	}

	public void setUserInput(boolean userInput) {
		this.userInput = userInput;
	}

	public void setUserOutput(boolean userOutput) {
		this.userOutput = userOutput;
	}

	public boolean isFile() {
		return file;
	}

	public boolean isUserInput() {
		return userInput;
	}

	public boolean isUserOutput() {
		return userOutput;
	}
}