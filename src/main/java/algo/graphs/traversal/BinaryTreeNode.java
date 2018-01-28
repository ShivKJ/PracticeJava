package algo.graphs.traversal;

import static java.util.Optional.empty;

import java.util.Optional;

public abstract class BinaryTreeNode extends TreeNode {
	public Optional<BinaryTreeNode> left() {
		return empty();
	}

	public Optional<BinaryTreeNode> right() {
		return empty();
	}

	public abstract double leftEdge();

	public abstract double rightEdge();

}
