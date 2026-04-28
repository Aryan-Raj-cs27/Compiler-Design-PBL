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
			String source = sc.nextLine();

			if (source.equalsIgnoreCase("exit")) {
				System.out.println("Exiting compiler. Goodbye!");
				break;
			}

			if (source.trim().isEmpty()) {
				continue;
			}

			System.out.println("Source Program:");
			System.out.println(source);
			System.out.println();

			try {
				// Parse input format: var = val1 op val2; print var;
				// Extract assignment and print parts
				String[] parts = source.split(";");
				if (parts.length < 2) {
					throw new IllegalArgumentException("Invalid format. Expected: var = expr; print var;");
				}

				String assignmentPart = parts[0].trim();
				String printPart = parts[1].trim();

				// Parse assignment: var = expression
				String[] assignSplit = assignmentPart.split("=");
				if (assignSplit.length != 2) {
					throw new IllegalArgumentException("Invalid assignment format.");
				}

				String varName = assignSplit[0].trim();
				String expression = assignSplit[1].trim();

				// -------------------- Lexer Phase --------------------
				System.out.println("[Lexer Phase]");
				System.out.println("Tokens:");
				List<Token> tokens = lexicalAnalysis(source);
				printTokens(tokens);
				System.out.println();

				// -------------- Intermediate Code Phase ---------------
				System.out.println("[Intermediate Code Phase]");
				System.out.println("Three-Address Code (TAC):");
				String[] tac = generateTAC(varName, expression);
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

			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		}

		sc.close();
	}

	// Token class for lexical analysis
	static class Token {
		String type;
		String value;

		Token(String type, String value) {
			this.type = type;
			this.value = value;
		}
	}

	// Lexical Analysis: tokenize source code dynamically
	private static List<Token> lexicalAnalysis(String source) {
		List<Token> tokens = new ArrayList<>();

		// Add spaces around operators and punctuation for easy splitting
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

	// Classify a single word into a token type
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

	// Print tokens in the format: <TYPE,value> <TYPE,value> ...
	private static void printTokens(List<Token> tokens) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tokens.size(); i++) {
			Token t = tokens.get(i);
			if (i > 0) sb.append(" ");
			sb.append("<").append(t.type).append(",").append(t.value).append(">");
		}
		System.out.println(sb.toString());
	}

	// Generate Three-Address Code dynamically from parsed expression
	private static String[] generateTAC(String varName, String expression) {
		List<String> tac = new ArrayList<>();

		// Tokenize the expression
		String normalized = expression
				.replace("+", " + ")
				.replace("-", " - ")
				.replace("*", " * ")
				.replace("/", " / ");

		String[] tokens = normalized.trim().split("\\s+");

		if (tokens.length == 1) {
			// Simple assignment: var = value
			tac.add(varName + "=" + tokens[0]);

		} else if (tokens.length == 3) {
			// Single binary operation: val1 op val2
			String val1 = tokens[0];
			String op = tokens[1];
			String val2 = tokens[2];

			tac.add("t1=" + val1 + op + val2);
			tac.add(varName + "=t1");

		} else if (tokens.length == 5) {
			// Two operators: val1 op1 val2 op2 val3
			// Handle operator precedence: * and / before + and -
			String val1 = tokens[0];
			String op1 = tokens[1];
			String val2 = tokens[2];
			String op2 = tokens[3];
			String val3 = tokens[4];

			// Check precedence: if op1 has higher or equal precedence than op2
			if (hasHigherOrEqualPrecedence(op1, op2)) {
				tac.add("t1=" + val1 + op1 + val2);
				tac.add("t2=t1" + op2 + val3);
			} else {
				// op2 has higher precedence, compute it first
				tac.add("t1=" + val2 + op2 + val3);
				tac.add("t2=" + val1 + op1 + "t1");
			}
			tac.add(varName + "=t2");

		} else {
			// Fallback: simple assignment
			tac.add(varName + "=" + expression);
		}

		return tac.toArray(new String[0]);
	}

	// Helper: check operator precedence
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
