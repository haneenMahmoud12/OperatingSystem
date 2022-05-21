package programs;

public class VarVal {
	private static String Var;
	private static Object Val;
	
	public VarVal(String Var,Object Val) {
		this.Val=Val;
		this.Var=Var;
	}

	public static String getVar() {
		return Var;
	}

	public static void setVar(String var) {
		Var = var;
	}

	public static Object getVal() {
		return Val;
	}

	public static void setVal(Object val) {
		Val = val;
	}

}
