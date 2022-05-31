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
			else {
				Main.setTime(Main.getTime() + 1);
				System.out.println("Time: "+Main.getTime());
				System.out.println("Memory:");
				for (int i = 0; i < Main.getMemory().length; i++) {
					System.out.print(Main.getMemory()[i]);
//					if(Main.getMemory()[i]==null)
//						break;
				}
			}
		} while (Main.getReadyQ().isEmpty() == true);

		while (!Main.getReadyQ().isEmpty()) {

			Process p = Main.getReadyQ().dequeue();
			if (!Main.getProcessLocation().contains(p))
				Main.swapping();

			p.setStatus(Status.RUNNING);
			int statusLocation = p.getMemoryLowerBound()+2;
			Main.getMemory()[statusLocation] = p.getStatus();
			System.out.println("Time: " + Main.getTime());
			System.out.println("Program" + " " + p.getProcessID() + " " + "is currently executing");
			System.out.println("ready Queue:");
			Main.getReadyQ().printQueue();
			System.out.println("Blocked Queue:");
			Main.getGeneralBlockedQ().printQueue();
			Main.getIp().convert(p);

			if (p.getPc() >= p.getInstructions().size()) {
				p.setStatus(Status.FINISHED);
				statusLocation = p.getMemoryLowerBound()+2;
				Main.getMemory()[statusLocation] = p.getStatus();
				Main.getFinishedProcessesQ().enqueue(p);
				System.out.println("Program  " + p.getProcessID() + " has finished execution.");
				System.out.println("ready Queue");
				Main.getReadyQ().printQueue();
				System.out.println("Blocked Queue");
				Main.getGeneralBlockedQ().printQueue();
			}

			else if (p.getStatus() != Status.BLOCKED) {
				p.setStatus(Status.READY);
				statusLocation = p.getMemoryLowerBound()+2;
				Main.getMemory()[statusLocation] = p.getStatus();
				Main.getReadyQ().enqueue(p);

			}

		}
	}
}