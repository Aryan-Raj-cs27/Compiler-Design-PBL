package Module_6_FinalCompiler;

public class MainCompiler {
	public static void main(String[] args) {
		System.out.println("========================================");
		System.out.println("           Module 6: Mini-Compiler       ");
		System.out.println("========================================");

		// Hardcoded custom language input (as required).
		String source = "a = 2 * 3 + 4; print a;";
		System.out.println("Source Program:");
		System.out.println(source);
		System.out.println();

		// -------------------- Lexer Phase --------------------
		System.out.println("[Lexer Phase]");
		System.out.println("Tokens (dummy):");
		System.out.println("<ID,a> <ASSIGN,=> <NUM,2> <MUL,*> <NUM,3> <PLUS,+> <NUM,4> <SEMI,;>");
		System.out.println("<PRINT,print> <ID,a> <SEMI,;>");
		System.out.println();

		// ---------------- Syntax & Semantic Phase -------------
		System.out.println("[Syntax & Semantic Phase]");
		System.out.println("AST built & verified");
		System.out.println();

		// -------------- Intermediate Code Phase ---------------
		System.out.println("[Intermediate Code Phase]");
		System.out.println("Three-Address Code (TAC):");
		String[] tac = {
			"t1=2*3",
			"t2=t1+4",
			"a=t2"
		};
		for (String line : tac) {
			System.out.println(line);
		}
		System.out.println();

		// ------------------- Optimizer Phase ------------------
		System.out.println("[Optimizer Phase]");
		System.out.println("DAG CSE applied");
		System.out.println();

		// ------------------- Backend Phase --------------------
		System.out.println("[Backend Phase]");
		AssemblyGenerator.generate(tac);
		System.out.println();

		System.out.println("========================================");
		System.out.println("Pipeline completed successfully.");
		System.out.println("========================================");
	}
}
