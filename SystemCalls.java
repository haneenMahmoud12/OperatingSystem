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

	public SystemCalls() {
	}

	public static void print(String x, Process p) {
		int upperBound = p.getMemoryUpperBound();
		for (int i = upperBound - 4; i <= upperBound - 2; i++) {
			String newObj = (String) Main.getMemory()[i];
			if (newObj != null) {
				if (newObj.contains(x)) {
					System.out.println(newObj.substring(2));
					break;
				}
			}
		}
	}

	public static void assign(String x, Object y, Process p) {
		String xy = x + " " + y;
		if (Main.getMemory()[p.getMemoryUpperBound() - 4] == null)
			Main.getMemory()[p.getMemoryUpperBound() - 4] = xy;
		else if (Main.getMemory()[p.getMemoryUpperBound() - 3] == null)
			Main.getMemory()[p.getMemoryUpperBound() - 3] = xy;
		else if (Main.getMemory()[p.getMemoryUpperBound() - 2] == null)
			Main.getMemory()[p.getMemoryUpperBound() - 2] = xy;
		else
			System.out.print("Not enough space in memory!");
	}

	public static void writeFile(String x, String y, Process p) throws IOException {
		boolean exist = true;
		String Objx = "";
		String Objy = "";
		File newFile;
		BufferedWriter writer;
		// finding x
		if (!(Main.getMemory()[p.getMemoryUpperBound() - 4] == null))
			Objx = ((String) Main.getMemory()[p.getMemoryUpperBound() - 4]).substring(0, 1);
		else if (!(Main.getMemory()[p.getMemoryUpperBound() - 3] == null))
			Objx = ((String) Main.getMemory()[p.getMemoryUpperBound() - 3]).substring(0, 1);
		else if (!(Main.getMemory()[p.getMemoryUpperBound() - 2] == null))
			Objx = ((String) Main.getMemory()[p.getMemoryUpperBound() - 2]).substring(0, 1);
		else {
			System.out.print("Variable " + x + " not found in memory!");
			exist = false;
		}

		// finding y
		if (exist == true) {
			if (!(Main.getMemory()[p.getMemoryUpperBound() - 4] == null))
				Objy = ((String) Main.getMemory()[p.getMemoryUpperBound() - 4]).substring(2);
			else if (!(Main.getMemory()[p.getMemoryUpperBound() - 3] == null))
				Objy = ((String) Main.getMemory()[p.getMemoryUpperBound() - 3]).substring(2);
			else if (!(Main.getMemory()[p.getMemoryUpperBound() - 2] == null))
				Objy = ((String) Main.getMemory()[p.getMemoryUpperBound() - 2]).substring(2);
			else
				System.out.print("Variable " + y + " not found in memory!");
		}
		if (Objx == x && Objy == y) {
			newFile = new File((String) Objx);
			newFile.createNewFile();
			writer = new BufferedWriter(new FileWriter(newFile));
			writer.write(Objy);
			writer.close();
		}
	}

	public static Object readFile(String x) throws FileNotFoundException, IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(x));
		ArrayList<String> s = new ArrayList<>();
			String currentLine = br.readLine();
			while (currentLine != null) {
				if (currentLine.equals("null"))
					s.add(null);
				else
					s.add(currentLine);
				currentLine = br.readLine();
			}
		return s;
	}

	public static void printFromTo(String x, String y, Process p) {
		int x1 = 0;
		int y1 = 0;
		int upperBound = p.getMemoryUpperBound();
		for (int i = upperBound - 4; i <= upperBound - 2; i++) {
			String xy = (String) Main.getMemory()[i];
			if (xy != null) {
				if (xy.contains(x)) {
					x1 = Integer.parseInt(xy.substring(2));
				} else if (xy.contains(y)) {
					y1 = Integer.parseInt(xy.substring(2));
				}
			}
		}

		for (int i = x1; i <= y1; i++) {
			System.out.println(i + " ");
		}
	}

}