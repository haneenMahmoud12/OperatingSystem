package programs;
import programs.QueueObj;

public class Mutex {
	private boolean file;
	private QueueObj fileBlockedQ;
	private boolean userInput;
	private QueueObj userInputBlockedQ;
	private boolean userOutput;
	private QueueObj userOutputBlockedQ;
	private QueueObj generalBlockedQ=new QueueObj(3);
	
	public Mutex(String m)
	{
		if(m.equals("file")) {
			this.file=true;
			fileBlockedQ = new QueueObj(3);
		}	
		else if(m.equals("userInput")) {
			this.userInput=true;
			userInputBlockedQ= new QueueObj(3);
		}
		else {
			this.userOutput=true;
			userOutputBlockedQ=new QueueObj(3);
		}
	}

//when semWait()/semSignal() is called, it returns the value of the flag. The caller will then handle what to do, 
//	depending on the value of the flag.
	
	public void semWait(String Flag,Process p) {

		if(Flag.equals("file")) 
		{
			if(this.file==true) {
			this.file=false;
			//make process state=running and handle scheduling
		}
		else {
			this.fileBlockedQ.enqueue(p);
			p.status=Status.BLOCKED;
			generalBlockedQ.enqueue(p);
		}
		}
		else if(Flag.equals("userInput"))
		{
			if(this.userInput==true) {
				this.userInput=false;
				//make process state=running and handle scheduling
			}
			else {
				this.userInputBlockedQ.enqueue(p);
				p.status=Status.BLOCKED;
				generalBlockedQ.enqueue(p);
			}
		}
		else
			{
				if(this.userOutput==true) {
					this.userOutput=false;
					//make process state=running and handle scheduling
				}
				else {
					this.userOutputBlockedQ.enqueue(p);
					p.status=Status.BLOCKED;
					generalBlockedQ.enqueue(p);
				}
			}
		
	}
	public void semSignal(String Flag,Process p) {
//		if(Flag.equals("file")) 
//			
//		else if(Flag.equals("userInput"))
//			
//		else
//			
//		
	}
	
//setters are used by the caller in order to set the flags depending on the returned value of semWait()/semSignal() 
	
	public void setFile(boolean file) {
		this.file = file;
	}
	public void setUserInput(boolean userInput) {
		this.userInput = userInput;
	}
	public void setUserOutput(boolean userOutput) {
		this.userOutput = userOutput;
	}
	public boolean isFile() {
		return file;
	}
	public boolean isUserInput() {
		return userInput;
	}
	public boolean isUserOutput() {
		return userOutput;
	}
}
