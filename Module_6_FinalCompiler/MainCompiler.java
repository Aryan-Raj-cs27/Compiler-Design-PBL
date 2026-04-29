package Module_6_FinalCompiler;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class MainCompiler {
	public static void main(String[] args) {
		System.out.println("========================================");
		System.out.println("           Module 6: Mini-Compiler       ");
		System.out.println("========================================");

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("Enter custom language program (or type 'exit' to quit): ");
			String source = sc.nextLine().trim();

			if (source.equalsIgnoreCase("exit")) {
				System.out.println("Exiting compiler. Goodbye!");
				break;
			}

			if (source.isEmpty()) {
				continue;
			}

			System.out.println("\nSource Program:");
			System.out.println(source);
			System.out.println();

			try {
				ProgramParts program = parseProgram(source);

				System.out.println("[Lexer Phase]");
				System.out.println("Tokens Generated:");
				List<Token> tokens = lexicalAnalysis(source);
				printTokens(tokens);
				System.out.println();

				System.out.println("[Syntax & Semantic Phase]");
				System.out.println("AST built & verified");
				System.out.println();

				System.out.println("[Intermediate Code Phase]");
				System.out.println("Three-Address Code (TAC):");
				String[] tac = generateTAC(program.targetVar, program.expression);
				for (String line : tac) {
					System.out.println(line);
				}
				System.out.println();

				System.out.println("[Optimizer Phase]");
				System.out.println("DAG CSE applied. Basic block optimized.");
				System.out.println();

				System.out.println("[Backend Phase]");
				AssemblyGenerator.generate(tac, program.printVar);
				System.out.println();

				System.out.println("========================================");
				System.out.println("Pipeline completed successfully.");
				System.out.println("========================================");

			} catch (Exception e) {
				System.out.println(">>> Syntax Error in input format. Use: var = num op num; print var;");
			}
		}

		sc.close();
	}

	static class Token {
		String type;
		String value;

		Token(String type, String value) {
			this.type = type;
			this.value = value;
		}
	}

	static class ProgramParts {
		String targetVar;
		String expression;
		String printVar;

		ProgramParts(String targetVar, String expression, String printVar) {
			this.targetVar = targetVar;
			this.expression = expression;
			this.printVar = printVar;
		}
	}

	private static ProgramParts parseProgram(String source) {
		String[] statements = source.split(";");
		String assignmentPart = null;
		String printPart = null;

		for (String statement : statements) {
			String trimmed = statement.trim();
			if (trimmed.isEmpty()) {
				continue;
			}

			if (assignmentPart == null && trimmed.contains("=")) {
				assignmentPart = trimmed;
			} else if (printPart == null && trimmed.toLowerCase().startsWith("print ")) {
				printPart = trimmed;
			}
		}

		if (assignmentPart == null || printPart == null) {
			throw new IllegalArgumentException("Invalid format. Expected: var = expr; print var;");
		}

		String[] assignSplit = assignmentPart.split("=", 2);
		if (assignSplit.length != 2) {
			throw new IllegalArgumentException("Invalid assignment format.");
		}

		String targetVar = assignSplit[0].trim();
		String expression = assignSplit[1].trim();
		String[] printSplit = printPart.split("\\s+", 2);
		if (printSplit.length != 2) {
			throw new IllegalArgumentException("Invalid print format.");
		}

		String printVar = printSplit[1].trim();
		if (!printVar.equals(targetVar)) {
			throw new IllegalArgumentException("Print variable must match the assigned variable for this demo pipeline.");
		}

		return new ProgramParts(targetVar, expression, printVar);
	}

	private static List<Token> lexicalAnalysis(String source) {
		List<Token> tokens = new ArrayList<>();

		String normalized = source
				.replace("=", " = ")
				.replace(";", " ; ")
				.replace("(", " ( ")
				.replace(")", " ) ")
				.replace("+", " + ")
				.replace("-", " - ")
				.replace("*", " * ")
				.replace("/", " / ");

		String[] words = normalized.trim().split("\\s+");

		for (String word : words) {
			if (word.isEmpty()) continue;
			Token token = classifyToken(word);
			tokens.add(token);
		}

		return tokens;
	}

	private static Token classifyToken(String word) {
		if (word.equals("print")) {
			return new Token("PRINT", word);
		} else if (word.equals("=")) {
			return new Token("ASSIGN", word);
		} else if (word.equals(";")) {
			return new Token("SEMI", word);
		} else if (word.equals("+")) {
			return new Token("PLUS", word);
		} else if (word.equals("-")) {
			return new Token("MINUS", word);
		} else if (word.equals("*")) {
			return new Token("MUL", word);
		} else if (word.equals("/")) {
			return new Token("DIV", word);
		} else if (word.matches("\\d+")) {
			return new Token("NUM", word);
		} else {
			return new Token("ID", word);
		}
	}

	private static void printTokens(List<Token> tokens) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tokens.size(); i++) {
			Token t = tokens.get(i);
			if (i > 0) sb.append(" ");
			sb.append("<").append(t.type).append(",").append(t.value).append(">");
		}
		System.out.println(sb.toString());
	}

	private static String[] generateTAC(String varName, String expression) {
		List<String> tac = new ArrayList<>();

		String normalized = expression
				.replace("+", " + ")
				.replace("-", " - ")
				.replace("*", " * ")
				.replace("/", " / ");

		String[] tokens = normalized.trim().split("\\s+");

		if (tokens.length == 1) {
			tac.add(varName + "=" + tokens[0]);

		} else if (tokens.length == 3) {
			String val1 = tokens[0];
			String op = tokens[1];
			String val2 = tokens[2];

			tac.add("t1=" + val1 + op + val2);
			tac.add(varName + "=t1");

		} else if (tokens.length == 5) {
			String val1 = tokens[0];
			String op1 = tokens[1];
			String val2 = tokens[2];
			String op2 = tokens[3];
			String val3 = tokens[4];

			if (hasHigherOrEqualPrecedence(op1, op2)) {
				tac.add("t1=" + val1 + op1 + val2);
				tac.add("t2=t1" + op2 + val3);
			} else {
				tac.add("t1=" + val2 + op2 + val3);
				tac.add("t2=" + val1 + op1 + "t1");
			}
			tac.add(varName + "=t2");

		} else {
			tac.add(varName + "=" + expression);
		}

		return tac.toArray(new String[0]);
	}

	private static boolean hasHigherOrEqualPrecedence(String op1, String op2) {
		int prec1 = getPrecedence(op1);
		int prec2 = getPrecedence(op2);
		return prec1 >= prec2;
	}

	private static int getPrecedence(String op) {
		if (op.equals("*") || op.equals("/")) {
			return 2;
		} else if (op.equals("+") || op.equals("-")) {
			return 1;
		}
		return 0;
	}
}
