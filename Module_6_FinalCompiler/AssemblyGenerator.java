public class AssemblyGenerator {
	/**
	 * Converts a small set of TAC instructions into dummy x86-like assembly and prints it.
	 *
	 * Expected TAC shape (examples):
	 * - t1=2*3
	 * - t2=t1+4
	 * - a=t2
	 */
	public static void generate(String[] tac) {
		printDivider();
		System.out.println("Dummy x86 Assembly Output:");
		printDivider();

		for (String line : tac) {
			emitForTac(line);
			System.out.println();
		}

		// In the input program we also have: print a;
		// We simulate it as a dummy CALL.
		System.out.println("; print a");
		System.out.println("MOV eax, a");
		System.out.println("CALL PRINT_INT");
	}

	private static void emitForTac(String tacLine) {
		// Split into LHS and RHS.
		String[] parts = tacLine.split("=");
		String lhs = parts[0].trim();
		String rhs = parts[1].trim();

		// Handle binary operations (* and +) and simple assignments.
		if (rhs.contains("*")) {
			String[] operands = rhs.split("\\*");
			String op1 = operands[0].trim();
			String op2 = operands[1].trim();

			System.out.println("; " + lhs + " = " + op1 + " * " + op2);
			System.out.println("MOV eax, " + op1);
			System.out.println("MOV ebx, " + op2);
			System.out.println("IMUL eax, ebx");
			System.out.println("MOV " + lhs + ", eax");
			return;
		}

		if (rhs.contains("+")) {
			String[] operands = rhs.split("\\+");
			String op1 = operands[0].trim();
			String op2 = operands[1].trim();

			System.out.println("; " + lhs + " = " + op1 + " + " + op2);
			System.out.println("MOV eax, " + op1);
			System.out.println("ADD eax, " + op2);
			System.out.println("MOV " + lhs + ", eax");
			return;
		}

		// Simple assignment: lhs = rhs
		System.out.println("; " + lhs + " = " + rhs);
		System.out.println("MOV eax, " + rhs);
		System.out.println("MOV " + lhs + ", eax");
	}

	private static void printDivider() {
		System.out.println("----------------------------------------");
	}
}
