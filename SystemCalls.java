package programs;

import java.io.BufferedReader;
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
	private static Hashtable<String, Object> table1 = new Hashtable<String, Object>();
	private static Hashtable<String, Object> table2 = new Hashtable<String, Object>();
	private static Hashtable<String, Object> table3 = new Hashtable<String, Object>();

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

	public static void assign(String x, Object y, int pid) {
		switch (pid) {
		case 1:
			table1.put(x, y);
			break;
		case 2:
			table2.put(x, y);
			break;
		case 3:
			table3.put(x, y);
			break;
		}
	}

//	public static void assign(String x, String y, int pid) {
//		switch (pid) {
//		case 1:
//			table1.put(x, y);
//			break;
//		case 2:
//			table2.put(x, y);
//			break;
//		case 3:
//			table3.put(x, y);
//			break;
//		}
//	}

	public static void writeFile(String x, String y) throws IOException {
		// we assumed that String x will be the file path in the form of name.txt
		File newFile = new File(x);
		newFile.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
		writer.write(y);
		writer.close();
		
		
	}

	public static Object readFile(String x) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(x));
		ArrayList<String> s=new ArrayList<>();
		String currentLine = br.readLine();
		while (currentLine != null) {
			s.add(currentLine);
			//System.out.println(currentLine);
			currentLine = br.readLine();
		}
		//System.out.println(s);
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
//	public void readFromMemory(int processID) {
//	for(int i=0;i<memoryIntegers.get(processID).size();i++) {
//		System.out.println(memoryIntegers.get(processID).get(i));
//	}
//	for(int i=0;i<memoryStrings.size();i++) {
//		System.out.println(memoryStrings.get(processID).get(i));
//	}
//}

}