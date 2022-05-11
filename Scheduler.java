package programs;

import java.io.IOException;
import programs.Interpreter;

public class Scheduler {
	//private int timeSlice;
	//private QueueObj readyQ;
	//private QueueObj generalBlockedQ;
	//private Interpreter interpreter;

	public Scheduler() {
		//this.timeSlice = timeSlice;
		//this.interpreter = interpreter;
		//this.readyQ=interpreter.getReadyQ();
		//this.generalBlockedQ=interpreter.getGeneralBlockedQ();
	}

//	public int getTimeSlice() {
//		return timeSlice;
//	}
//
//	public void setTimeSlice(int timeSlice) {
//		this.timeSlice = timeSlice;
//	}

	public void scheduling() throws IOException {
		while(Main.getTime()<=Main.getTime4Process3()) {
			if(Main.getTime()==Main.getTime4Process1()) {
				Main.getIp().interpretation(Main.getP1id());
			}
			else if(Main.getTime()==Main.getTime4Process2()) {
				Main.getIp().interpretation(Main.getP2id());
			}
			else if(Main.getTime()==Main.getTime4Process3()){
				Main.getIp().interpretation(Main.getP3id());
			}
			else
				Main.setTime(Main.getTime()+1);
		}
		while (!Main.getReadyQ().isEmpty()) {
			Process p =Main.getReadyQ().dequeue();
			p.setStatus(Status.RUNNING);
			System.out.println(Main.getTime());
			System.out.println("Program" +" "+ p.getProcessID() +" "+ "is currently executing");
			System.out.println("ready Queue:");
			Main.getReadyQ().printQueue();
			System.out.println("Blocked Queue:");
			Main.getGeneralBlockedQ().printQueue();
			Main.getIp().convert(p);
			if (p.getPc()== p.getInstructions().size()) {
				p.setStatus(Status.FINISHED);
				Main.getFinishedProcessesQ().enqueue(p);
				System.out.println("ready Queue");
				Main.getReadyQ().printQueue();
				System.out.println("Blocked Queue");
				Main.getGeneralBlockedQ().printQueue();
			} else {
				p.setStatus(Status.READY);
				Main.getReadyQ().enqueue(p);
			}
		}
	}

//	public QueueObj getReadyQ() {
//		return readyQ;
//	}
//
//	public void setReadyQ(QueueObj readyQ) {
//		this.readyQ = readyQ;
//	}
//
//	public QueueObj getGeneralBlockedQ() {
//		return generalBlockedQ;
//	}
//
//	public void setGeneralBlockedQ(QueueObj generalBlockedQ) {
//		this.generalBlockedQ = generalBlockedQ;
//	}

//	public Interpreter getInterpreter() {
//		return interpreter;
//	}
//
//	public void setInterpreter(Interpreter interpreter) {
//		this.interpreter = interpreter;
//	}


}