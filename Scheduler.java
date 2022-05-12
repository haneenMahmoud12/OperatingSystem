package programs;

import java.io.IOException;
import programs.Interpreter;

public class Scheduler {

	public Scheduler() {
	}

	public void scheduling() throws IOException {

		do {
			if (Main.getTime() == Main.getTime4Process1())
				Main.getIp().interpretation(Main.getP1id());
			else if (Main.getTime() == Main.getTime4Process2())
				Main.getIp().interpretation(Main.getP2id());
			else if (Main.getTime() == Main.getTime4Process3())
				Main.getIp().interpretation(Main.getP3id());
			else
				Main.setTime(Main.getTime() + 1);
		} while (Main.getReadyQ().isEmpty() == true);

		while (!Main.getReadyQ().isEmpty()) {

			Process p = Main.getReadyQ().dequeue();

			p.setStatus(Status.RUNNING);
			System.out.println("Time: " + Main.getTime());
			System.out.println("Program" + " " + p.getProcessID() + " " + "is currently executing");
			System.out.println("ready Queue:");
			Main.getReadyQ().printQueue();
			System.out.println("Blocked Queue:");
			Main.getGeneralBlockedQ().printQueue();

			Main.getIp().convert(p);

			if (p.getPc() >= p.getInstructions().size()) {
				p.setStatus(Status.FINISHED);
				Main.getFinishedProcessesQ().enqueue(p);
				//System.out.println("Time: " + Main.getTime());
				System.out.println("Program  " + p.getProcessID() + " has finished execution.");
				System.out.println("ready Queue");
				Main.getReadyQ().printQueue();
				System.out.println("Blocked Queue");
				Main.getGeneralBlockedQ().printQueue();
			}

			else if (p.getStatus() != Status.BLOCKED) {
				p.setStatus(Status.READY);
				Main.getReadyQ().enqueue(p);

			}

		}
	}
}