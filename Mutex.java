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
	private QueueObj readyQ;
	private QueueObj generalBlockedQ;

	public Mutex(String m, QueueObj readyQ, QueueObj generalBlockedQ) {
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
		p.status = Status.RUNNING;
		if (Flag.equals("file")) {
			if (this.file == true) {
				this.file = false;
				pid_file = p.processID;
			} else {
				this.fileBlockedQ.enqueue(p);
				p.status = Status.BLOCKED;
				System.out.println("ready Queue");
				readyQ.printQueue();
				System.out.println("Blocked Queue");
				generalBlockedQ.printQueue();
				generalBlockedQ.enqueue(p);
			}
		} else if (Flag.equals("userInput")) {
			if (this.userInput == true) {
				this.userInput = false;
				pid_input = p.processID;
			} else {
				this.userInputBlockedQ.enqueue(p);
				p.status = Status.BLOCKED;
				System.out.println("ready Queue");
				readyQ.printQueue();
				System.out.println("Blocked Queue");
				generalBlockedQ.printQueue();
				generalBlockedQ.enqueue(p);
			}
		} else {
			if (this.userOutput == true) {
				this.userOutput = false;
				pid_output = p.processID;
			} else {
				this.userOutputBlockedQ.enqueue(p);
				p.status = Status.BLOCKED;
				System.out.println("ready Queue");
				readyQ.printQueue();
				System.out.println("Blocked Queue");
				generalBlockedQ.printQueue();
				generalBlockedQ.enqueue(p);
			}
		}

	}

	public void semSignal(String Flag, Process p) {
		if (Flag.equals("file")) {
			if (p.processID == pid_file) {
				if (fileBlockedQ.isEmpty())
					file = true;
				else {
					Process p1 = (Process) fileBlockedQ.dequeue();
					p1.status = Status.READY;
					readyQ.enqueue(p1);
					pid_file = p1.processID;
				}
			}
		} else if (Flag.equals("userInput")) {
			if (p.processID == pid_input) {
				if (userInputBlockedQ.isEmpty())
					userInput = true;
				else {
					Process p1 = (Process) userInputBlockedQ.dequeue();
					p1.status = Status.READY;
					readyQ.enqueue(p1);
					pid_input = p1.processID;
				}
			}
		}

		else {
			if (p.processID == pid_output) {
				if (userOutputBlockedQ.isEmpty())
					userOutput = true;
				else {
					Process p1 = (Process) userOutputBlockedQ.dequeue();
					p1.status = Status.READY;
					readyQ.enqueue(p1);
					pid_output = p1.processID;
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