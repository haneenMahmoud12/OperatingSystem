package programs;

import java.io.IOException;
import programs.Interpreter;

public class Scheduler {
	private int timeSlice;
	private QueueObj readyQ;
	private QueueObj generalBlockedQ;
	private Interpreter interpreter;

//	public Scheduler(int t, QueueObj readyQ, QueueObj generalblockedQ) {
//		this.readyQ = readyQ;
//		this.generalBlockedQ = generalblockedQ;
//		this.timeSlice = t;
//	}

	public Scheduler(int timeSlice, Interpreter interpreter) {
		this.timeSlice = timeSlice;
		this.interpreter = interpreter;
	}

	public int getTimeSlice() {
		return timeSlice;
	}

	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}

	public void scheduling() throws IOException {
		while (!readyQ.isEmpty()) {
			Process p = (Process) readyQ.dequeue();
			p.status = Status.RUNNING;
			System.out.println("Program" +" "+ p.processID +" "+ "is currently executing");
			System.out.println("ready Queue");
			readyQ.printQueue();
			System.out.println("Blocked Queue");
			generalBlockedQ.printQueue();
			interpreter.convert(p, timeSlice);
			if (p.pc > p.instructions.size()) {
				p.status = Status.FINISHED;
				System.out.println("ready Queue");
				readyQ.printQueue();
				System.out.println("Blocked Queue");
				generalBlockedQ.printQueue();
			} else {
				p.status = Status.READY;
				readyQ.enqueue(p);
			}
		}
	}

	public QueueObj getReadyQ() {
		return readyQ;
	}

	public void setReadyQ(QueueObj readyQ) {
		this.readyQ = readyQ;
	}

}
