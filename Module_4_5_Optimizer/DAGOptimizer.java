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
		// The optimizer is intentionally SIMPLE and STRING-BASED for presentation.
		// We simulate a DAG idea by remembering expressions we've already computed.
		//
		// For the professor's block:
		//   t1=a*b
		//   t2=t1+c
		//   t3=a*b+d
		// The common subexpression is: a*b (already computed into t1).
		// So we rewrite the redundant part in the third instruction:
		//   t3=a*b+d  --->  t3=t1+d

		String[] optimized = new String[tac.length];
		String knownMulExpression = null; // e.g., "a*b"
		String knownMulTemp = null;       // e.g., "t1"

		for (int i = 0; i < tac.length; i++) {
			String instruction = tac[i];
			String[] parts = instruction.split("=");
			String lhs = parts[0].trim();
			String rhs = parts[1].trim();

			// Record the first multiplication we see (t1=a*b).
			if (rhs.contains("*") && !rhs.contains("+")) {
				knownMulExpression = rhs; // "a*b"
				knownMulTemp = lhs;       // "t1"
				optimized[i] = lhs + "=" + rhs;
				continue;
			}

			// Apply CSE for the pattern "a*b+d".
			if (knownMulExpression != null && rhs.startsWith(knownMulExpression + "+")) {
				String remainder = rhs.substring((knownMulExpression + "+").length()); // "d"
				rhs = knownMulTemp + "+" + remainder; // "t1+d"
			}

			optimized[i] = lhs + "=" + rhs;
		}

		return optimized;
	}
}
