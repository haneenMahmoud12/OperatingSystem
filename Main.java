package programs;

import java.io.IOException;
import java.util.*;

public class Main {
	private static QueueObj readyQ;
	private static QueueObj generalBlockedQ;
	private static QueueObj finishedProcessesQ;
	public static int time;
	public Main() {
		this.readyQ = new QueueObj(3);
		this.generalBlockedQ = new QueueObj(3);
		this.finishedProcessesQ = new QueueObj(3);
		this.time=0;
	}

	public static void main(String[] args) throws IOException {
		Scanner ts = new Scanner(System.in);
		System.out.println("Please enter a timeslice.");
		int timeSlice = ts.nextInt();
		
		
		Scanner p1 = new Scanner(System.in);
		System.out.println("Please enter the program number you want to start execution.");
		int p1id = p1.nextInt();
		Scanner tp1 = new Scanner(System.in);
		System.out.println("Please enter the time you want this program to start execution at.");
		int time4Process1 = tp1.nextInt();
		
		Scanner p2 = new Scanner(System.in);
		System.out.println("Please enter the program number you want to start execution.");
		int p2id = p2.nextInt();
		Scanner tp2 = new Scanner(System.in);
		System.out.println("Please enter the time you want this program to start execution at.");
		int time4Process2 = tp2.nextInt();
		
		Scanner p3 = new Scanner(System.in);
		System.out.println("Please enter the program number you want to start execution.");
		int p3id = p3.nextInt();
		Scanner tp3 = new Scanner(System.in);
		System.out.println("Please enter the time you want this program to start execution at.");
		int time4Process3 = tp3.nextInt();
		
		Main m = new Main();
		Interpreter ip = new Interpreter(readyQ, generalBlockedQ,finishedProcessesQ,time);
		
		Scheduler s = new Scheduler(timeSlice,ip,time);
		while(!finishedProcessesQ.isFull()) {
			if(time==time4Process1) {
				ip.interpretation(p1id);
				s.scheduling();
			}
			else if(time==time4Process2) {
				ip.interpretation(p2id);
				s.scheduling();
			}
			else if(time==time4Process3){
				ip.interpretation(p3id);
				s.scheduling();
			}
			else
				time++;
		}
		
		
		
	}
	// Haneen

	// Somaya

	// Nada

	// Salma

}