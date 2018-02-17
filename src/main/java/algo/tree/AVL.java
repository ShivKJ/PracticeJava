package algo.tree;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import java.util.Optional;

public class AVL<K extends Comparable<K>, V> {
	private final static class AVLNode<K extends Comparable<K>, V> {
		final K			k;
		final V			v;
		AVLNode<K, V>	p , l , r;
		int				h;

		AVLNode(K k, V v) {
			this.k = k;
			this.v = v;
			this.h = 1;
		}

		static <K extends Comparable<K>, V> int height(AVLNode<K, V> n) {
			return ofNullable(n).map(AVLNode::h).orElse(0);
		}

		int lh() {
			return height(l);
		}

		int rh() {
			return height(r);
		}

		int h() {
			return h;
		}

		boolean isBalanced() {
			return Math.abs(heightDiff()) <= 1;
		}

		void updateHeight() {
			this.h = 1 + Math.max(lh(), rh());
		}

		int heightDiff() {
			return lh() - rh();
		}

		V v() {
			return v;
		}
	}

	private AVLNode<K, V>	root;
	private int				size;

	public AVL() {
		this.root = null;
		this.size = 0;
	}

	private void leftR(AVLNode<K, V> x) {
		/*
		            |                               |
		            x                               y
		           / \           L(x)              / \
		          A   y        -------->          x   G
		             / \                         / \
		            B   G                       A   B
		 */

		AVLNode<K, V> y = x.r;
		x.r = y.l;

		if (y.l != null)
			y.l.p = x;

		y.p = x.p;

		if (x.p == null)
			this.root = y;
		else if (x.p.l == x)
			x.p.l = y;
		else
			x.p.r = y;

		y.l = x;
		x.p = y;
	}

	private void rightR(AVLNode<K, V> y) {
		/*
		            |                               |
		            y                               x
		           / \           R(y)              / \
		          x   G        ------>            A   y    
		         / \                                 / \
		        A   B                               B   G
		 */
		AVLNode<K, V> x = y.l;

		y.l = x.r;

		if (x.r != null)
			x.r.p = y;

		if (y.p == null)
			this.root = x;
		else if (y == y.p.l)
			y.p.l = x;
		else
			y.p.r = x;

		x.r = y;
		y.p = x;
	}

	public void put(K k, V v) {
		AVLNode<K, V> n = new AVLNode<>(k, v) , x = root , y = null;

		while (x != null) {
			y = x;
			x = k.compareTo(x.k) <= 0 ? x.l : x.r;
		}

		if (y == null)
			root = y;
		else if (k.compareTo(y.k) <= 0)
			y.l = n;
		else
			y.r = n;

		n.p = y;
		this.size++;

		insertBalance(n);

	}

	private void insertBalance(AVLNode<K, V> x) {
		while (x.p != null && x.p.p != null) {
			x.updateHeight();
			x.p.updateHeight();
			x.p.p.updateHeight();

			if (!x.p.p.isBalanced()) {
				balence(x);
				break;
			}
		}
	}

	private void balence(AVLNode<K, V> x) {
		AVLNode<K, V> y = x.p , z = x.p.p;

		if (y == z.l) {
			if (x == y.r) {
				leftR(y);
				y.updateHeight();
				x.updateHeight();
			}
			rightR(z);
		} else {
			if (x == y.l) {
				rightR(y);
				y.updateHeight();
				x.updateHeight();
			}
			leftR(z);
		}

		z.updateHeight();
	}

	private Optional<AVLNode<K, V>> getNode(K k) {
		AVLNode<K, V> x = root;

		while (x != null) {
			int comp = k.compareTo(x.k);

			if (comp == 0)
				return of(x);

			x = comp <= 0 ? x.l : x.r;
		}

		return empty();
	}

	public V get(K k) {
		return getNode(k).map(AVLNode::v).orElse(null);
	}

	public void remove(K k) {
		getNode(k).ifPresent(this::removeNode);
	}

	private void removeNode(AVLNode<K, V> z) {
		AVLNode<K, V> balancingNode = null;
		if (z.l == null) {
			balancingNode = z.p;
			transplant(z, z.r);
		} else if (z.r == null) {
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
		}
		this.size--;

		if (balancingNode != null)
			deleteBalance(balancingNode);

	}

	private void deleteBalance(AVLNode<K, V> z) {
		while (z != null) {
			z.updateHeight();
			int zhDiff = z.heightDiff();

			if (Math.abs(zhDiff) > 1) {
				if (zhDiff > 0) {
					AVLNode<K, V> y = z.l;
					balence(y.heightDiff() >= 0 ? y.l : y.r);
				} else {
					AVLNode<K, V> y = z.r;
					balence(y.heightDiff() >= 0 ? y.r : y.l);
				}
				z.updateHeight();
			}

			z = z.p;
		}
	}

	private static <K extends Comparable<K>, V> AVLNode<K, V> min(AVLNode<K, V> n) {
		while (n.l != null)
			n = n.l;
		return n;
	}

	private void transplant(AVLNode<K, V> u, AVLNode<K, V> v) {
		if (u.p == null)
			this.root = v;
		else if (u.p.l == u)
			u.p.l = v;
		else
			u.p.r = v;

		if (v != null)
			v.p = u.p;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return this.root == null;
	}
}
