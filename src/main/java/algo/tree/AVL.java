package algo.tree;

import static java.lang.Math.abs;
import static java.lang.Math.max;

/**
 * 
 * @author Shiv_Krishna_Jaiswal
 *
 * @param <K>
 * @param <V>
 */
public class AVL<K extends Comparable<K>, V> extends Tree<K, V> {
	private AVLNode	root;
	private int		size;

	public AVL() {
		root(nil());
		this.size = 0;
	}

	/**
	 * Storing value v with key k. If a key already exists in Tree, then
	 * replacing old value with new value.
	 * 
	 * Returns previously associated value or Null.
	 * 
	 * @param k
	 * @param v
	 * @return 
	 */
	@Override
	public V put(K k, V v) {
		AVLNode x = root() , y = nil();
		int comp = 0;

		while (x != nil()) {

			comp = k.compareTo(x.getKey());

			if (comp == 0)
				return x.setValue(v);

			y = x;
			x = comp < 0 ? x.l : x.r;
		}

		AVLNode n = new AVLNode(k, v);

		if (y == nil())
			this.root = n;
		else if (comp < 0)
			y.l = n;
		else
			y.r = n;

		n.p = y;
		insertBalance(n);

		this.size++;

		return null;

	}

	private void insertBalance(AVLNode x) {
		while (x.p != nil() && x.p.p != nil()) {
			x.updateHeight();
			x.p.updateHeight();
			x.p.p.updateHeight();

			if (!x.p.p.isBalanced()) {
				balence(x);
				break;
			}
			x = x.p;
		}
	}

	private void balence(AVLNode x) {
		AVLNode y = x.p , z = x.p.p;

		if (y == z.l) {
			if (x == y.r) {
				lr(y);
				y.updateHeight();
				x.updateHeight();
			}
			rr(z);
		} else {
			if (x == y.l) {
				rr(y);
				y.updateHeight();
				x.updateHeight();
			}
			lr(z);
		}

		z.updateHeight();
	}

	/**
	 * Removing data corresponding to key k when
	 * k is present in Tree.
	 * 
	 * @param k
	 */
	@Override
	public void remove(K k) {
		getNode(k).filter(x -> x != nil()).map(AVLNode.class::cast).ifPresent(this::removeNode);
	}

	private void removeNode(AVLNode z) {
		AVLNode balancingNode = nil();

		if (z.l == nil()) {
			balancingNode = z.p;

			transplant(z, z.r);

		} else if (z.r == nil()) {
			balancingNode = z.p;

			transplant(z, z.l);

		} else {
			AVLNode y = min(z.r);

			if (y.p != z) {

				balancingNode = y.p;
				transplant(y, y.r);

				y.r = z.r;
				z.r.p = y;
			} else
				balancingNode = y;

			transplant(z, y);

			y.l = z.l;
			z.l.p = y;
		}

		if (balancingNode != nil())
			deleteBalance(balancingNode);

		this.size--;

	}

	private void deleteBalance(AVLNode z) {
		while (z != nil()) {
			z.updateHeight();

			int zhDiff = z.heightDiff();

			if (abs(zhDiff) > 1) {
				if (zhDiff > 0) {
					AVLNode y = z.l;
					balence(y.heightDiff() >= 0 ? y.l : y.r);
				} else {
					AVLNode y = z.r;
					balence(y.heightDiff() <= 0 ? y.r : y.l);
				}
				z.updateHeight();
			}

			z = z.p;
		}
	}

	/**
	 * Returns number of element in Tree.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Removes all Nodes from Tree.
	 */
	@Override
	public void clear() {
		this.root = nil();
		this.size = 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Node> T root() {

		return (T) root;
	}

	@Override
	public <T extends Node> void root(T root) {
		this.root = cast(root);
	}

	//--------------------------- AVL Node Implementation--------------------------

	private final class AVLNode extends Node {
		AVLNode	p , l , r;
		int		h;

		AVLNode(K k, V v) {
			super(k, v);
			this.h = 1;
		}

		int height(AVLNode n) {
			return n == nil() ? 0 : n.h;
		}

		int lh() {
			return height(l);
		}

		int rh() {
			return height(r);
		}

		boolean isBalanced() {
			return abs(heightDiff()) <= 1;
		}

		void updateHeight() {
			this.h = 1 + max(lh(), rh());
		}

		int heightDiff() {
			return lh() - rh();
		}

		@Override
		public <T extends Node> T l() {

			return cast(l);
		}

		@Override
		public <T extends Node> void l(T l) {
			this.l = cast(l);
		}

		@Override
		public <T extends Node> T p() {

			return cast(p);
		}

		@Override
		public <T extends Node> void p(T p) {
			this.p = cast(p);
		}

		@Override
		public <T extends Node> T r() {

			return cast(r);
		}

		@Override
		public <T extends Node> void r(T r) {
			this.r = cast(r);
		}

	}

	@Override
	public <T> T nil() {
		return null;
	}
}
