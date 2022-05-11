package programs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

public class SystemCalls {
	//The memory is split into two ArrayLists, one for integers and one for strings.
	private static ArrayList<ArrayList<Integer>> memoryIntegers = new ArrayList<ArrayList<Integer>>();
	private static ArrayList<ArrayList<String>> memoryStrings = new ArrayList<ArrayList<String>>();
	
	private static HashMap <String,Object> value=new HashMap<String, Object>();

	public SystemCalls(ArrayList<ArrayList<Integer>> memoryIntegers, ArrayList<ArrayList<String>> memoryStrings) {
		this.memoryIntegers=memoryIntegers;
		this.memoryStrings=memoryStrings;
	}
	
	public static void print(String x, int pid) {
		if(memoryStrings.get(pid).indexOf(x)!=-1)
			System.out.println(value.get(x));
		else
			System.out.println(x);
	}
	public static void assign(String x, int y, int pid) {
			ArrayList<String> temps = memoryStrings.get(pid-1);
			temps.add(x);
			memoryStrings.set(pid-1, temps);
			ArrayList<Integer> tempi = memoryIntegers.get(pid-1);
			tempi.add(y);
			memoryIntegers.set(pid-1, tempi);
		
			//index of y in inner ArrayList (ArrayList of process).
			int indexOfY=memoryIntegers.get(pid-1).indexOf(y);
			
			//index of x in inner ArrayList (ArrayList of process).
			int indexOfX=memoryStrings.get(pid-1).indexOf(x);
			
			//Map x, which is stored in memoryStrings, to y, which is stored in memoryIntegers.
			value.put(memoryStrings.get(pid-1).get(indexOfX), memoryIntegers.get(pid-1).get(indexOfY));
	}
	public static void assign(String x, String y, int pid) {
		ArrayList<String> temps = memoryStrings.get(pid-1);
		temps.add(x);
		temps.add(y);
		memoryStrings.set(pid-1, temps);
	
		//index of y in inner ArrayList (ArrayList of process).
		int indexOfY=memoryStrings.get(pid-1).indexOf(y);
		
		//index of x in inner ArrayList (ArrayList of process).
		int indexOfX=memoryStrings.get(pid-1).indexOf(x);
		
		//Map x, which is stored in memoryStrings, to y, which is stored in memoryIntegers.
		value.put(memoryStrings.get(pid-1).get(indexOfX), memoryStrings.get(pid-1).get(indexOfY));
		
	}

	public static void writeFile(String x,String y) throws IOException {
		//do we add extension to x e.g. .txt, or will x be filename.txt??????
		      File newFile = new File(x);
		      if (newFile.createNewFile()) {
		    	  newFile.createNewFile();
		      } 
	    	  BufferedWriter writer = new BufferedWriter(new FileWriter(x));
	    	  writer.write(y);
	  		  writer.close();
	}
	public static void readFile(String x) throws FileNotFoundException,IOException {
		BufferedReader br = new BufferedReader(new FileReader(x));
		String currentLine = br.readLine();
		while(currentLine != null) {
			System.out.println(currentLine);
			currentLine = br.readLine();
		}
	}
	public static void printFromTo(String x, String y) {
		int x1 = (int) value.get(x);
		int y1 = (int) value.get(y);
		for(int i=x1;i<=y1;i++) {
			System.out.println(i+" ");
		}
	}
	public void readFromMemory(int processID) {
		for(int i=0;i<memoryIntegers.get(processID).size();i++) {
			System.out.println(memoryIntegers.get(processID).get(i));
		}
		for(int i=0;i<memoryStrings.size();i++) {
			System.out.println(memoryStrings.get(processID).get(i));
		}
	}
	public void writeToMemory(Object x,int processID) {
		try {
			ArrayList<String> temps = memoryStrings.get(processID);
			temps.add((String)x);
			memoryStrings.add(processID, temps);
		} catch(Exception e) {
			ArrayList<Integer> temps = memoryIntegers.get(processID);
			temps.add((int)x);
			memoryIntegers.add(processID, temps);
		}
	}

	public static ArrayList<ArrayList<Integer>> getMemoryIntegers() {
		return memoryIntegers;
	}

	public static void setMemoryIntegers(ArrayList<ArrayList<Integer>> memoryIntegers) {
		SystemCalls.memoryIntegers = memoryIntegers;
	}

	public static ArrayList<ArrayList<String>> getMemoryStrings() {
		return memoryStrings;
	}

	public static void setMemoryStrings(ArrayList<ArrayList<String>> memoryStrings) {
		SystemCalls.memoryStrings = memoryStrings;
	}

	public static HashMap<String, Object> getValue() {
		return value;
	}

	public static void setValue(HashMap<String, Object> value) {
		SystemCalls.value = value;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}