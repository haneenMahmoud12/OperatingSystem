package programs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import programs.Process;

public class Main {
	private static QueueObj readyQ;
	private static QueueObj generalBlockedQ;
	private static QueueObj finishedProcessesQ;
	private static int time;
	private static int timeSlice;
	private static boolean timeSliceNotOver;
	private static Interpreter ip;
	private static int p1id;
	private static int time4Process1;
	private static int p2id;
	private static int time4Process2;
	private static int p3id;
	private static int time4Process3;
	public static QueueObj availableProcessesQ;
	private static Object[] memory;
	private static int memoryPointer;
	private static Hashtable<Integer, Process> processLocation = new Hashtable<Integer, Process>();
	private static Process[] processesInDisk;
	// Integer is the process upperBound in the memory

	public Main() {
		readyQ = new QueueObj(3);
		generalBlockedQ = new QueueObj(3);
		finishedProcessesQ = new QueueObj(3);
		availableProcessesQ = new QueueObj(3);
		time = 0;
		ip = new Interpreter();
		timeSliceNotOver = false;
		memoryPointer = -1;
		memory = new Object[40];
		processesInDisk = new Process[3];
	}

	public static void processCreation(ArrayList<String> instructionsHere, int PID) throws IOException {
		int size = 5 + instructionsHere.size() + 3;
		if (size > (memory.length - (memoryPointer + 1))) {
			// remove a process from the memory and put it in disk
			Process p1 = processLocation.get(memoryPointer);
			System.out.println("Process " + p1.getProcessID() + " is being swapped into the disk.");
			BufferedWriter writer = new BufferedWriter(new FileWriter("disk.txt"));
			for (int i = p1.getMemoryLowerBound(); i <= memoryPointer; i++) {
				if (memory[i] == null)
					writer.write("null");
				else 
					writer.write(memory[i].toString());
				writer.newLine();
				memory[i] = null;	
			}
			writer.close();
			memoryPointer = p1.getMemoryLowerBound() - 1;
			processesInDisk[0] = p1;
			processLocation.remove(p1.getMemoryUpperBound());
		}
		int LowerBound;
		int UpperBound;
		memoryPointer++;
		LowerBound = memoryPointer;
		UpperBound = (LowerBound - 1) + size;
		Process p = new Process(PID, 0, Status.NEW, instructionsHere, LowerBound, UpperBound);

		// System.out.println(p.getMemoryLowerBound());
		p.setStatus(Status.READY);
		readyQ.enqueue(p);
		memory[memoryPointer] = p.getProcessID();
		memoryPointer++;
		memory[memoryPointer] = p.getPc();
		memoryPointer++;
		memory[memoryPointer] = p.getStatus();
		memoryPointer++;

		for (int i = 0; i < instructionsHere.size(); i++) {
			memory[memoryPointer] = instructionsHere.get(i);
			memoryPointer++;
		}
		memory[memoryPointer]=null;
		memoryPointer ++;
		memory[memoryPointer]=null;
		memoryPointer ++;
		memory[memoryPointer]=null;
		memoryPointer ++;
		memory[memoryPointer] = p.getMemoryLowerBound();
		memoryPointer++;
		memory[memoryPointer] = p.getMemoryUpperBound();
		memoryPointer = p.getMemoryUpperBound();
		// only p can have that location
		processLocation.put(UpperBound, p);
	}

	public static void swapping() throws FileNotFoundException, IOException {
		ArrayList<String> temp = new ArrayList<>();
		temp = (ArrayList<String>) SystemCalls.readFile("disk.txt");
		Process pBackInMemory = processesInDisk[0];
		File f = new File("C:\\Users\\hanee\\OneDrive\\Desktop\\Team_37\\src\\programs\\disk.txt");
		PrintWriter p = new PrintWriter(f);
		p.print("");
		p.close();

		// remove a process from the memory and put it in disk
		// System.out.println(memoryPointer);
		Process p1 = processLocation.get(memoryPointer);
		System.out.println("Process " + p1.getProcessID() + " is being swapped into the disk, and Process "+pBackInMemory.getProcessID()+" is being swapped out of the disk.");
		BufferedWriter writer = new BufferedWriter(new FileWriter("disk.txt"));
		for (int i = p1.getMemoryLowerBound(); i <= memoryPointer; i++) {
			if (memory[i] == null)
				writer.write("null");
			else 
				writer.write(memory[i].toString());
			writer.newLine();
			memory[i] = null;	
		}
		writer.close();
		memoryPointer = p1.getMemoryLowerBound() - 1;
		processesInDisk[0] = p1;
		processLocation.remove(p1.getMemoryUpperBound());
		for (int i = 0; i < temp.size(); i++) {
			memoryPointer++;
			memory[memoryPointer] = temp.get(i);
		}
		processLocation.put(pBackInMemory.getMemoryUpperBound(), pBackInMemory);
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

		// disk

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

	public static QueueObj getAvailableProcessesQ() {
		return availableProcessesQ;
	}

	public static void setAvailableProcessesQ(QueueObj availableProcessesQ) {
		Main.availableProcessesQ = availableProcessesQ;
	}

	public static Object[] getMemory() {
		return memory;
	}

	public static void setMemory(Object[] memory) {
		Main.memory = memory;
	}

	public static int getMemoryPointer() {
		return memoryPointer;
	}

	public static void setMemoryPointer(int memoryPointer) {
		Main.memoryPointer = memoryPointer;
	}

	public static Hashtable<Integer, Process> getProcessLocation() {
		return processLocation;
	}

	public static void setProcessLocation(Hashtable<Integer, Process> processLocation) {
		Main.processLocation = processLocation;
	}

}