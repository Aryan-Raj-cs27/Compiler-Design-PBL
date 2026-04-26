import java.util.ArrayList;
import java.util.List;

public class TACGenerator {
	private static int tempCounter = 1;
	private static final List<String> tacInstructions = new ArrayList<>();

	public static void main(String[] args) {
		System.out.println("--- Module 4: TAC Generation ---");

		System.out.println("Input Expression: 2*3+4");
		tacInstructions.clear();
		tempCounter = 1;
		ASTNode expressionOne = buildTwoTimesThreePlusFourAst();
		generateTAC(expressionOne);
		printTAC("2*3+4");

		System.out.println();

		System.out.println("Input Expression: (2+3)*4");
		tacInstructions.clear();
		tempCounter = 1;
		ASTNode expressionTwo = buildParenthesizedAdditionTimesFourAst();
		generateTAC(expressionTwo);
		printTAC("(2+3)*4");
	}

	private static ASTNode buildTwoTimesThreePlusFourAst() {
		ASTNode two = new ASTNode("2", false);
		ASTNode three = new ASTNode("3", false);
		ASTNode four = new ASTNode("4", false);

		ASTNode multiply = new ASTNode("*", true, two, three);
		return new ASTNode("+", true, multiply, four);
	}

	private static ASTNode buildParenthesizedAdditionTimesFourAst() {
		ASTNode two = new ASTNode("2", false);
		ASTNode three = new ASTNode("3", false);
		ASTNode four = new ASTNode("4", false);

		ASTNode addition = new ASTNode("+", true, two, three);
		return new ASTNode("*", true, addition, four);
	}

	private static String generateTAC(ASTNode node) {
		if (node == null) {
			return "";
		}

		// Strict post-order traversal: left subtree, right subtree, then root.
		String leftResult = generateTAC(node.left);
		String rightResult = generateTAC(node.right);

		if (!node.isOperator) {
			return node.value;
		}

		// When the traversal reaches an operator, the two operands are already resolved.
		String tempName = "t" + tempCounter++;
		String instruction = tempName + " = " + leftResult + " " + node.value + " " + rightResult;
		tacInstructions.add(instruction);
		return tempName;
	}

	private static void printTAC(String inputExpression) {
		System.out.println("Three-Address Code for: " + inputExpression);
		for (String instruction : tacInstructions) {
			System.out.println(instruction);
		}
	}
}
