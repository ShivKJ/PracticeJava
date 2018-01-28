package algo.graphs.tree;

import algo.graphs.Edge;
import algo.graphs.traversal.BinaryTreeNode;

public interface BinaryTree<V extends BinaryTreeNode, W extends Edge<? extends V>> extends Tree<V, W> {}
