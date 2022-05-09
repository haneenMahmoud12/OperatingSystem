package programs;

import java.io.IOException;
import programs.Interpreter;

public class Scheduler {
	private int timeSlice;
	private QueueObj readyQ;
	private QueueObj generalBlockedQ;
	private Interpreter interpreter;


	public Scheduler(int timeSlice, Interpreter interpreter) {
		this.timeSlice = timeSlice;
		this.interpreter = interpreter;
		this.readyQ=interpreter.getReadyQ();
		this.generalBlockedQ=interpreter.getGeneralBlockedQ();
	}

	public int getTimeSlice() {
		return timeSlice;
	}

	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}

	public void scheduling(int time) throws IOException {
		while (!readyQ.isEmpty()) {
			Process p =readyQ.dequeue();
			p.setStatus(Status.RUNNING);
			System.out.println("Program" +" "+ p.getProcessID() +" "+ "is currently executing");
			System.out.println("ready Queue");
			readyQ.printQueue();
			System.out.println("Blocked Queue");
			generalBlockedQ.printQueue();
			System.out.println("scheduler "+p.getInstructions().size());
			interpreter.convert(p, timeSlice,time);
			if (p.getPc() > p.getInstructions().size()) {
				p.setStatus(Status.FINISHED);
				interpreter.getFinishedProcessesQ().enqueue(p);
				//interpreter.finishedProcessesQ.enqueue(p);
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

}