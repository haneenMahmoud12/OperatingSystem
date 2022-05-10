package programs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.sun.tools.javac.code.Attribute.Array;

public class SystemCalls {
//	private ArrayList<Integer> p1Ints = new ArrayList<>();
//	private ArrayList<Integer> p2Ints = new ArrayList<>();
//	private ArrayList<Integer> p3Ints = new ArrayList<>();
	private static ArrayList<ArrayList<Integer>> memoryIntegers = new ArrayList<>();
	private static ArrayList<String> p1Strings = new ArrayList<>();
	private ArrayList<String> p2Strings = new ArrayList<>();
	private ArrayList<Integer> p3Strings = new ArrayList<>();
	private static ArrayList<ArrayList<String>> memoryStrings = new ArrayList<>();
//	
//	public SystemCalls() {
//		this.memoryIntegers.add(p1Ints);
//		this.memoryIntegers.add(p2Ints);
//		this.memoryIntegers.add(p3Ints);
//		this.memoryStrings.add(p1Strings);
//		this.memoryStrings.add(p2Strings);
//		this.memoryStrings.add(p3Strings);
//	}
	
	public static void print(String x) {
		//do we need to check the memory for x?
		System.out.println(x);
	}
	public static void assign(String x, int y, int pid, int pc) {
		
		
	}
	public static void assign(String x, String y, int pid, int pc) {
		
	}
	public static void assignInput(String x, String y, int pid, int pc) {
		
	}
public static void assignInput(String x, int y, int pid, int pc) {
		
	}
	
	
	
	
	
	
	
	
	
	
	
//	public static void assign(String x,int y) {
//		
//		memoryIntegers.add(y);
//		x=memoryIntegers.get(memoryIntegers.indexOf(y));
//	}
//	public static void assign(String x,String y) {
//		//taking input
//		memoryStrings.add(y);
//		x=memoryStrings.get(memoryStrings.indexOf(y));
//	}
//	public static void assignInt(String x) {
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Please enter a value");
//		String y = sc.nextInt();
//		memoryStrings.add(y);
//		x=memoryStrings.get(memoryStrings.indexOf(y));
//	}
//	public static void assign(int x) {
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Please enter a value");
//		int y = sc.nextInt();
//		memoryIntegers.add(y);
//		x=memoryIntegers.get(memoryIntegers.indexOf(y));
//	}
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
	public void readFromMemory(int processID) {
		for(int i=0;i<memoryIntegers.size();i++) {
			System.out.println(memoryIntegers.get(i));
		}
		for(int i=0;i<memoryStrings.size();i++) {
			System.out.println(memoryStrings.get(i));
		}
	}
	public void writeToMemory() {
		for(int i=0;i<memoryIntegers.size();i++) {
			System.out.println(memoryIntegers.get(i));
		}
		for(int i=0;i<memoryStrings.size();i++) {
			System.out.println(memoryStrings.get(i));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}