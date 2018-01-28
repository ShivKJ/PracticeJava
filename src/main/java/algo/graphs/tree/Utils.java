package algo.graphs.tree;

import java.util.LinkedList;
import java.util.List;

import algo.graphs.Edge;
import algo.graphs.traversal.BinaryTreeNode;

public final class Utils {

	private Utils() {}

	/**
	 * Finds max sum path which is path having largest length from root to a leaf node.
	 * Assumption:
	 * 1) parent of root is assumed to be null
	 * 2) if a node does not have left child then it is considered to be null. Similarly for right child.
	 * 
	 * Key idea: There exists only one path from root to a leaf child.
	 * 
	 * @param tree
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public static <V extends BinaryTreeNode> List<V> maxSumPath(BinaryTree<V, Edge<V>> tree) {
		V root = tree.getRoot();
		root.setUserData(0.);

		BinaryTreeNode[] best = { root };

		updateNodeData(root, best);

		BinaryTreeNode bestLeaf = best[0];

		LinkedList<V> nodes = new LinkedList<>();

		do
			nodes.addFirst((V) bestLeaf);
		while ((bestLeaf = (V) bestLeaf.parent()) != null);

		return nodes;
	}

	private static void updateNodeData(BinaryTreeNode node, BinaryTreeNode[] best) {
		node.left().ifPresent(left -> updateChildNodeData(node, left, best));
		node.right().ifPresent(right -> updateChildNodeData(node, right, best));
	}

	private static void updateChildNodeData(BinaryTreeNode node, BinaryTreeNode child, BinaryTreeNode[] best) {
		Double userData = node.userData();

		child.setUserData(userData + child.parentEdge());

		if (Double.compare(userData, child.userData()) < 0)
			best[0] = child;

		updateNodeData(child, best);
	}
}
