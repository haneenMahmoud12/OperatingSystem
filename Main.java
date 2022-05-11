package programs;

import java.io.IOException;
import java.util.*;

public class Main {
	private static QueueObj readyQ;
	private static QueueObj generalBlockedQ;
	private static QueueObj finishedProcessesQ;
	private static int time;
	private static ArrayList<ArrayList<Integer>> memoryIntegers;
	private static ArrayList<ArrayList<String>> memoryStrings;
	private static int timeSlice;
	private static boolean timeSliceNotOver;
	private static Interpreter ip;
	private static int p1id;
	private static int time4Process1;
	private static int p2id;
	private static int time4Process2;
	private static int p3id;
	private static int time4Process3;
	public Main() {
		readyQ = new QueueObj(3);
		generalBlockedQ = new QueueObj(3);
		finishedProcessesQ = new QueueObj(3);
		time=0;
		memoryIntegers = new ArrayList<ArrayList<Integer>>();
		memoryStrings = new ArrayList<ArrayList<String>>();
		ip = new Interpreter();
		timeSliceNotOver = false;
	}

	public static void main(String[] args) throws IOException {
		Scanner ts = new Scanner(System.in);
		System.out.println("Please enter a timeslice.");
		timeSlice = ts.nextInt();
		
		
		Scanner p1 = new Scanner(System.in);
		System.out.println("Please enter the program number you want to start execution.");
		p1id = p1.nextInt();
		Scanner tp1 = new Scanner(System.in);
		System.out.println("Please enter the time you want this program to start execution at.");
		time4Process1 = tp1.nextInt();
		
		Scanner p2 = new Scanner(System.in);
		System.out.println("Please enter the program number you want to start execution.");
		p2id = p2.nextInt();
		Scanner tp2 = new Scanner(System.in);
		System.out.println("Please enter the time you want this program to start execution at.");
		time4Process2 = tp2.nextInt();
		
		Scanner p3 = new Scanner(System.in);
		System.out.println("Please enter the program number you want to start execution.");
		p3id = p3.nextInt();
		Scanner tp3 = new Scanner(System.in);
		System.out.println("Please enter the time you want this program to start execution at.");
		time4Process3 = tp3.nextInt();
		
		Main m = new Main();
		
		memoryIntegers.add(new ArrayList<Integer>());
		memoryIntegers.add(new ArrayList<Integer>());
		memoryIntegers.add(new ArrayList<Integer>());
		memoryStrings.add(new ArrayList<String>());
		memoryStrings.add(new ArrayList<String>());
		memoryStrings.add(new ArrayList<String>());
		SystemCalls systemCalls = new SystemCalls(memoryIntegers,memoryStrings);
		
		
		Scheduler s = new Scheduler();
		s.scheduling();	
	}


	public static QueueObj getReadyQ() {
		return readyQ;
	}

	public static void setReadyQ(QueueObj readyQ) {
		Main.readyQ = readyQ;
	}

	public static QueueObj getGeneralBlockedQ() {
		return generalBlockedQ;
	}

	public static void setGeneralBlockedQ(QueueObj generalBlockedQ) {
		Main.generalBlockedQ = generalBlockedQ;
	}

	public static QueueObj getFinishedProcessesQ() {
		return finishedProcessesQ;
	}

	public static void setFinishedProcessesQ(QueueObj finishedProcessesQ) {
		Main.finishedProcessesQ = finishedProcessesQ;
	}

	public static int getTime() {
		return time;
	}

	public static void setTime(int time) {
		Main.time = time;
	}

	public static ArrayList<ArrayList<Integer>> getMemoryIntegers() {
		return memoryIntegers;
	}

	public static void setMemoryIntegers(ArrayList<ArrayList<Integer>> memoryIntegers) {
		Main.memoryIntegers = memoryIntegers;
	}

	public static ArrayList<ArrayList<String>> getMemoryStrings() {
		return memoryStrings;
	}

	public static void setMemoryStrings(ArrayList<ArrayList<String>> memoryStrings) {
		Main.memoryStrings = memoryStrings;
	}

	public static int getTimeSlice() {
		return timeSlice;
	}

	public static void setTimeSlice(int timeSlice) {
		Main.timeSlice = timeSlice;
	}

	public static Interpreter getIp() {
		return ip;
	}

	public static void setIp(Interpreter ip) {
		Main.ip = ip;
	}

	public static int getP1id() {
		return p1id;
	}

	public static void setP1id(int p1id) {
		Main.p1id = p1id;
	}

	public static int getTime4Process1() {
		return time4Process1;
	}

	public static void setTime4Process1(int time4Process1) {
		Main.time4Process1 = time4Process1;
	}

	public static int getP2id() {
		return p2id;
	}

	public static void setP2id(int p2id) {
		Main.p2id = p2id;
	}

	public static int getTime4Process2() {
		return time4Process2;
	}

	public static void setTime4Process2(int time4Process2) {
		Main.time4Process2 = time4Process2;
	}

	public static int getP3id() {
		return p3id;
	}

	public static void setP3id(int p3id) {
		Main.p3id = p3id;
	}

	public static int getTime4Process3() {
		return time4Process3;
	}

	public static void setTime4Process3(int time4Process3) {
		Main.time4Process3 = time4Process3;
	}

	public static boolean isTimeSliceNotOver() {
		return timeSliceNotOver;
	}

	public static void setTimeSliceNotOver(boolean timeSliceNotOver) {
		Main.timeSliceNotOver = timeSliceNotOver;
	}


}