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
	private AVLNode<K, V>	root;
	private int				size;

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
		AVLNode<K, V> x = root() , y = nil();
		int comp = 0;

		while (x != nil()) {

			comp = k.compareTo(x.getKey());

			if (comp == 0)
				return x.setValue(v);

			y = x;
			x = comp < 0 ? x.l : x.r;
		}

		AVLNode<K, V> n = new AVLNode<K, V>(k, v);

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

	private void insertBalance(AVLNode<K, V> x) {
		while (x.p != nil() && x.p.p != nil()) {
			updateHeight(x);
			updateHeight(x.p);
			updateHeight(x.p.p);

			if (!isBalanced(x.p.p)) {
				balence(x);
				break;
			}
			x = x.p;
		}
	}

	private void balence(AVLNode<K, V> x) {
		AVLNode<K, V> y = x.p , z = x.p.p;

		if (y == z.l) {
			if (x == y.r) {
				lr(y);
				updateHeight(y);
				updateHeight(x);
			}
			rr(z);
		} else {
			if (x == y.l) {
				rr(y);
				updateHeight(y);
				updateHeight(x);
			}
			lr(z);
		}

		updateHeight(z);
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

	private void removeNode(AVLNode<K, V> z) {
		AVLNode<K, V> balancingNode = nil();

		if (z.l == nil()) {
			balancingNode = z.p;

			transplant(z, z.r);

		} else if (z.r == nil()) {
			balancingNode = z.p;

			transplant(z, z.l);

		} else {
			AVLNode<K, V> y = min(z.r);

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

	private void deleteBalance(AVLNode<K, V> z) {
		while (z != nil()) {
			updateHeight(z);

			int zhDiff = heightDiff(z);

			if (abs(zhDiff) > 1) {
				if (zhDiff > 0) {
					AVLNode<K, V> y = z.l;
					balence(heightDiff(y) >= 0 ? y.l : y.r);
				} else {
					AVLNode<K, V> y = z.r;
					balence(heightDiff(y) <= 0 ? y.r : y.l);
				}
				updateHeight(z);
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
	 * Removes all Node<K,V>s from Tree.
	 */
	@Override
	public void clear() {
		this.root = nil();
		this.size = 0;
	}

	@Override
	public <T extends Node<K, V>> T root() {

		return cast(root);
	}

	@Override
	public <T> T nil() {
		return null;
	}

	@Override
	public <T extends Node<K, V>> void root(T root) {
		this.root = cast(root);
	}

	int height(AVLNode<K, V> n) {
		return n == nil() ? 0 : n.h;
	}

	boolean isBalanced(AVLNode<K, V> n) {
		return abs(heightDiff(n)) <= 1;
	}

	int heightDiff(AVLNode<K, V> n) {
		return height(n.l) - height(n.r);
	}

	void updateHeight(AVLNode<K, V> n) {
		n.h = 1 + max(height(n.l), height(n.r));
	}

	//--------------------------- AVL Node<K,V> Implementation--------------------------

	private static final class AVLNode<K extends Comparable<K>, V> extends Node<K, V> {
		AVLNode<K, V>	p , l , r;
		int				h;

		AVLNode(K k, V v) {
			super(k, v);
			this.h = 1;
		}

		@Override
		public <T extends Node<K, V>> T l() {

			return cast(l);
		}

		@Override
		public <T extends Node<K, V>> void l(T l) {
			this.l = cast(l);
		}

		@Override
		public <T extends Node<K, V>> T p() {

			return cast(p);
		}

		@Override
		public <T extends Node<K, V>> void p(T p) {
			this.p = cast(p);
		}

		@Override
		public <T extends Node<K, V>> T r() {

			return cast(r);
		}

		@Override
		public <T extends Node<K, V>> void r(T r) {
			this.r = cast(r);
		}

	}

}
