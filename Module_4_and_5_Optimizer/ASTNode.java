package Module_4_and_5_Optimizer;

public class ASTNode {
	String value;
	ASTNode left;
	ASTNode right;
	boolean isOperator;

	public ASTNode(String value, boolean isOperator) {
		this.value = value;
		this.isOperator = isOperator;
	}

	public ASTNode(String value, boolean isOperator, ASTNode left, ASTNode right) {
		this.value = value;
		this.isOperator = isOperator;
		this.left = left;
		this.right = right;
	}
}
