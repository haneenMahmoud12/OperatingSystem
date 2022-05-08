package programs;

import java.io.IOException;
import java.util.*;

public class Main {
	private static QueueObj readyQ;
	private static QueueObj generalBlockedQ;

	public Main() {
		this.readyQ = new QueueObj(3);
		this.generalBlockedQ = new QueueObj(3);
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		int timeSlice = sc.nextInt();
		Interpreter ip = new Interpreter(readyQ, generalBlockedQ);
		ip.interpretation(1); //when 
		Scheduler s = new Scheduler(timeSlice,ip);
		s.scheduling();
		
	}
	// Haneen

	// Somaya

	// Nada

	// Salma

}
