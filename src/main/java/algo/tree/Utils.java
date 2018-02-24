package algo.tree;

import static java.util.Collections.emptyList;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import algo.tree.Tree.Node;

public class Utils {
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

	public static <T extends Node<K, V>, K extends Comparable<K>, V> List<T> maxDepthPath(Tree<K, V> tree) {
		T root = tree.root();

		if (root == tree.nil())
			return emptyList();

		root.setUserData(1);

		AtomicReference<T> best = new AtomicReference<T>(root);

		updateNodeData(root, best, tree.nil());

		T bestLeaf = best.get();

		LinkedList<T> nodes = new LinkedList<>();

		do
			nodes.addFirst(bestLeaf);
		while ((bestLeaf = bestLeaf.p()) != tree.nil());

		return nodes;
	}

	private static <T extends Node<K, V>, K extends Comparable<K>, V> void updateNodeData(T node, AtomicReference<T> best, Node<K, V> nil) {

		if (node.l() != nil) {
			T l = node.l();

			Integer userData = node.getUserData();
			l.setUserData(1 + userData);

			if (Integer.compare(best.get().getUserData(), l.getUserData()) < 0)
				best.set(l);

			updateNodeData(l, best, nil);
		}
		if (node.r() != nil) {
			T r = node.r();

			Integer userData = node.getUserData();
			r.setUserData(1 + userData);

			if (Integer.compare(best.get().getUserData(), r.getUserData()) < 0)
				best.set(r);

			updateNodeData(r, best, nil);
		}
	}

}
