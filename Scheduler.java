package programs;

import java.io.IOException;
import programs.Interpreter;

public class Scheduler {
	private int timeSlice;
	private QueueObj readyQ;
	private QueueObj generalBlockedQ;
	private Interpreter interpreter;
	private int time;

	public Scheduler(int timeSlice, Interpreter interpreter,int time) {
		this.timeSlice = timeSlice;
		this.interpreter = interpreter;
		this.readyQ=interpreter.getReadyQ();
		this.generalBlockedQ=interpreter.getGeneralBlockedQ();
		this.time=time;
	}

	public int getTimeSlice() {
		return timeSlice;
	}

	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}

	public void scheduling() throws IOException {
		while (!readyQ.isEmpty()) {
			Process p =readyQ.dequeue();
			p.setStatus(Status.RUNNING);
			System.out.println(time);
			System.out.println("Program" +" "+ p.getProcessID() +" "+ "is currently executing");
			System.out.println("ready Queue");
			readyQ.printQueue();
			System.out.println("Blocked Queue");
			generalBlockedQ.printQueue();
			interpreter.convert(p, timeSlice);
			if (p.getPc()== p.getInstructions().size()) {
				p.setStatus(Status.FINISHED);
				interpreter.getFinishedProcessesQ().enqueue(p);
				System.out.println("ready Queue");
				readyQ.printQueue();
				System.out.println("Blocked Queue");
				generalBlockedQ.printQueue();
			} else {
				p.setStatus(Status.READY);
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

	public QueueObj getGeneralBlockedQ() {
		return generalBlockedQ;
	}

	public void setGeneralBlockedQ(QueueObj generalBlockedQ) {
		this.generalBlockedQ = generalBlockedQ;
	}

	public Interpreter getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

}