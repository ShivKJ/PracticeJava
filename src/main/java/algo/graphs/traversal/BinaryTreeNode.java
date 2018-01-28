package algo.graphs.traversal;

import java.util.Optional;

public abstract class BinaryTreeNode extends TreeNode {
	public abstract Optional<BinaryTreeNode> left();

	public abstract Optional<BinaryTreeNode> right();

}
