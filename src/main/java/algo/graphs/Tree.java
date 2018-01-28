package algo.graphs;

import algo.graphs.traversal.TreeNode;

public interface Tree<V extends TreeNode, W extends Edge<? extends V>> extends Graph<V, W> {

	V getRoot();

	int size();

	int depth();
}
