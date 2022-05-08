package programs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SystemCalls {
	public static void print(String x) {
		System.out.println(x);
	}
	public static void assign(int x,int y) {
		x=y;
	}
	public static void assign(String x,String y) {
		x=y;
	}
	public static void writeFile(String x,String y) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(x));
		writer.write(y);
		writer.close();
	}
	public static void readFile(String x) throws FileNotFoundException,IOException {
		BufferedReader br = new BufferedReader(new FileReader(x));
		String currentLine = br.readLine();
		while(currentLine != null) {
			System.out.println(currentLine);
		}
	}
	public static void printFromTo(int x, int y) {
		for(int i=x;i<=y;i++) {
			System.out.print(i+" ");
		}
	}
}
