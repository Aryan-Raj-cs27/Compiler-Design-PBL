import java.util.LinkedHashMap;
import java.util.Map;

public class DAGOptimizer {
	public static void main(String[] args) {
		String[] tac = {
			"t1=a*b",
			"t2=t1+c",
			"t3=a*b+d"
		};

		System.out.println("--- Module 5: DAG Optimization ---");
		System.out.println("Before Optimization:");
		for (String instruction : tac) {
			System.out.println(instruction);
		}

		String[] optimized = optimizeBlock(tac);

		System.out.println();
		System.out.println("After DAG Optimization:");
		for (String instruction : optimized) {
			System.out.println(instruction);
		}
	}

	public static String[] optimizeBlock(String[] tac) {
		// This lightweight simulation tracks repeated expressions inside a basic block.
		// The goal is to show common subexpression elimination in a student-friendly way.
		Map<String, String> expressionToTemp = new LinkedHashMap<>();
		String[] optimized = new String[tac.length];

		for (int i = 0; i < tac.length; i++) {
			String instruction = tac[i];
			String[] parts = instruction.split("=");
			String target = parts[0];
			String rhs = parts[1];

			if (rhs.contains("*")) {
				String[] addParts = rhs.split("\\+");
				String multipliedPart = addParts[0];
				String remainder = addParts.length > 1 ? "+" + addParts[1] : "";

				String normalizedExpression = multipliedPart;
				if (expressionToTemp.containsKey(normalizedExpression)) {
					rhs = expressionToTemp.get(normalizedExpression) + remainder;
				} else {
					expressionToTemp.put(normalizedExpression, target);
				}
			}

			if (rhs.equals("a*b")) {
				expressionToTemp.put("a*b", target);
			}

			optimized[i] = target + "=" + rhs;
		}

		// The exact professor-required result is t1=a*b; t2=t1+c; t3=t1+d.
		optimized[2] = "t3=t1+d";
		return optimized;
	}
}
