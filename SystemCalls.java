package programs;

import java.io.BufferedReader;
import programs.VarVal;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.*;

public class SystemCalls {
	// Memory
	private static Hashtable<String, Object> table1 = new Hashtable<String, Object>(); // p1 16 words
	private static Hashtable<String, Object> table2 = new Hashtable<String, Object>(); // p2 15 words
	private static Hashtable<String, Object> table3 = new Hashtable<String, Object>(); // p3 17 words

	private static Object[] memory = new Object[40];
	private static int memoryPointer = 0;
	private static ArrayList<Object> disk = new ArrayList<Object>();

	public SystemCalls() {
	}

	public static void print(String x, int pid) {
		switch (pid) {
		case 1:
			System.out.println(table1.get(x));
			break;
		case 2:
			System.out.println(table2.get(x));
			break;
		case 3:
			System.out.println(table3.get(x));
			break;
		default:
			System.out.println(x);
			break;
		}
	}

	public static void assign(String x, Object y, Process p) {
		VarVal newObj = new VarVal(x, y);
		if (memory[p.getMemoryUpperBound() - 2].equals(0))
			memory[p.getMemoryUpperBound() - 2] = newObj;
		else if (memory[p.getMemoryUpperBound() - 1].equals(0))
			memory[p.getMemoryUpperBound() - 1] = newObj;
		else if (memory[p.getMemoryUpperBound()].equals(0))
			memory[p.getMemoryUpperBound()] = newObj;
		else
			System.out.print("Not enough space in memory!");
	}

	public static void writeFile(String x, String y, Process p) throws IOException {
		boolean exist = true;
		VarVal Objx = new VarVal("", "");
		VarVal Objy = new VarVal("", "");
		File newFile;
		BufferedWriter writer;
		// finding x
		if (!memory[p.getMemoryUpperBound() - 2].equals(0))
			Objx = (VarVal) memory[p.getMemoryUpperBound() - 2];
		else if (!memory[p.getMemoryUpperBound() - 1].equals(0))
			Objx = (VarVal) memory[p.getMemoryUpperBound() - 1];
		else if (!memory[p.getMemoryUpperBound()].equals(0))
			Objx = (VarVal) memory[p.getMemoryUpperBound()];
		else {
			System.out.print("Variable " + x + " not found in memory!");
			exist = false;
		}

		// finding y
		if (exist == true) {
			if (!memory[p.getMemoryUpperBound() - 2].equals(0))
				Objy = (VarVal) memory[p.getMemoryUpperBound() - 2];
			else if (!memory[p.getMemoryUpperBound() - 1].equals(0))
				Objy = (VarVal) memory[p.getMemoryUpperBound() - 1];
			else if (!memory[p.getMemoryUpperBound()].equals(0))
				Objy = (VarVal) memory[p.getMemoryUpperBound()];
			else
				System.out.print("Variable " + y + " not found in memory!");
		}
		if (Objx.getVar() == x && Objy.getVar() == y) {
			newFile = new File((String) Objx.getVal());
			newFile.createNewFile();
			writer = new BufferedWriter(new FileWriter(newFile));
			writer.write((int) Objy.getVal());
			writer.close();
		}
	}

	public static Object readFile(String x) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(x));
		ArrayList<String> s = new ArrayList<>();
		String currentLine = br.readLine();
		while (currentLine != null) {
			s.add(currentLine);
			// System.out.println(currentLine);
			currentLine = br.readLine();
		}
		// System.out.println(s);
		return s;
	}

	public static void printFromTo(String x, String y, int pid) {
		int x1 = 0;
		int y1 = 0;
		switch (pid) {
		case 1: {
			x1 = Integer.parseInt((String) table1.get(x));
			y1 = Integer.parseInt((String) table1.get(y));
		}
			break;
		case 2: {
			x1 = Integer.parseInt((String) table2.get(x));
			y1 = Integer.parseInt((String) table2.get(y));
		}
			break;
		case 3: {
			x1 = Integer.parseInt((String) table3.get(x));
			y1 = Integer.parseInt((String) table3.get(y));
		}
			break;
		}

		for (int i = x1; i <= y1; i++) {
			System.out.println(i + " ");
		}
	}

	public static Object[] getMemory() {
		return memory;
	}

	public static void setMemory(Object[] memory) {
		SystemCalls.memory = memory;
	}

	public static ArrayList<Object> getDisk() {
		return disk;
	}

	public static void setDisk(ArrayList<Object> disk) {
		SystemCalls.disk = disk;
	}

	public static int getMemoryPointer() {
		return memoryPointer;
	}

	public static void setMemoryPointer(int memoryPointer) {
		SystemCalls.memoryPointer = memoryPointer;
	}

}