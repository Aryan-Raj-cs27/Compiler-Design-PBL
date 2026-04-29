package Module_6_FinalCompiler;

public class AssemblyGenerator {
	public static void generate(String[] tac, String printVar) {
		printDivider();
		System.out.println("Target x86 Assembly Output:");
		printDivider();

		for (String line : tac) {
			emitForTac(line);
			System.out.println();
		}

		System.out.println("; print " + printVar);
		System.out.println("MOV eax, " + printVar);
		System.out.println("CALL PRINT_INT");
	}

	private static void emitForTac(String tacLine) {
		String[] parts = tacLine.split("=", 2);
		String lhs = parts[0].trim();
		String rhs = parts[1].trim();

		if (rhs.contains("*")) {
			String[] operands = rhs.split("\\*", 2);
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
			String[] operands = rhs.split("\\+", 2);
			String op1 = operands[0].trim();
			String op2 = operands[1].trim();

			System.out.println("; " + lhs + " = " + op1 + " + " + op2);
			System.out.println("MOV eax, " + op1);
			System.out.println("ADD eax, " + op2);
			System.out.println("MOV " + lhs + ", eax");
			return;
		}

		if (rhs.contains("-")) {
			String[] operands = rhs.split("-", 2);
			String op1 = operands[0].trim();
			String op2 = operands[1].trim();

			System.out.println("; " + lhs + " = " + op1 + " - " + op2);
			System.out.println("MOV eax, " + op1);
			System.out.println("SUB eax, " + op2);
			System.out.println("MOV " + lhs + ", eax");
			return;
		}

		if (rhs.contains("/")) {
			String[] operands = rhs.split("/", 2);
			String op1 = operands[0].trim();
			String op2 = operands[1].trim();

			System.out.println("; " + lhs + " = " + op1 + " / " + op2);
			System.out.println("MOV eax, " + op1);
			System.out.println("MOV ebx, " + op2);
			System.out.println("IDIV ebx");
			System.out.println("MOV " + lhs + ", eax");
			return;
		}

		System.out.println("; " + lhs + " = " + rhs);
		System.out.println("MOV eax, " + rhs);
		System.out.println("MOV " + lhs + ", eax");
	}

	private static void printDivider() {
		System.out.println("----------------------------------------");
	}
}
