package Module_4_and_5_Optimizer;

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
		String[] optimized = new String[tac.length];
		String knownMulExpression = null;
		String knownMulTemp = null;

		for (int i = 0; i < tac.length; i++) {
			String instruction = tac[i];
			String[] parts = instruction.split("=");
			String lhs = parts[0].trim();
			String rhs = parts[1].trim();

			if (rhs.contains("*") && !rhs.contains("+")) {
				knownMulExpression = rhs;
				knownMulTemp = lhs;
				optimized[i] = lhs + "=" + rhs;
				continue;
			}

			if (knownMulExpression != null && rhs.startsWith(knownMulExpression + "+")) {
				String remainder = rhs.substring((knownMulExpression + "+").length());
				rhs = knownMulTemp + "+" + remainder;
			}

			optimized[i] = lhs + "=" + rhs;
		}

		return optimized;
	}
}
