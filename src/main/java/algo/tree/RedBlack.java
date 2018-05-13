package algo.tree;

import java.util.List;

public class RedBlack<K extends Comparable<K>, V> extends Tree<K, V> {
	private static final boolean		RED	= true , BLACK = false;
	private static final RBNode<?, ?>	NIL	= new RBNode<>(null, null, BLACK);

	private RBNode<K, V>	root;
	private int				size;

	public RedBlack() {
		this.root = nil();
		this.size = 0;
	}

	/**
	 * Storing value v with key k. If a key already exists in Tree, then
	 * replacing old value with new value.
	 * 
	 * @param k
	 * @param v
	 * @return previously associated value or null.
	 */
	@Override
	public V put(K k, V v) {
		RBNode<K, V> x = this.root , y = nil();
		int comp = 0;

		while (x != nil()) {
			y = x;

			comp = k.compareTo(x.getKey());

			if (comp == 0)
				return x.setValue(v);

			x = comp < 0 ? x.l : x.r;
		}

		RBNode<K, V> n = new RBNode<K, V>(k, v);
		n.l = n.r = n.p = nil();

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

	private void insertBalance(RBNode<K, V> z) {
		while (z.p.color == RED) {
			if (z.p == z.p.p.l) {
				RBNode<K, V> uncle = z.p.p.r;

				if (uncle.color == RED) {
					z.p.color = uncle.color = BLACK;
					z.p.p.color = RED;
					z = z.p.p;
				} else {
					if (z == z.p.r) {
						z = z.p;
						lr(z);
					}
					z.p.p.color = RED;
					z.p.color = BLACK;
					rr(z.p.p);
				}
			} else {
				RBNode<K, V> uncle = z.p.p.l;
				if (uncle.color == RED) {
					z.p.color = uncle.color = BLACK;
					z.p.p.color = RED;
					z = z.p.p;
				} else {
					if (z == z.p.l) {
						z = z.p;
						rr(z);
					}
					z.p.p.color = RED;
					z.p.color = BLACK;
					lr(z.p.p);
				}
			}
		}

		this.root.color = BLACK;
	}

	@Override
	public void remove(K key) {
		getNode(key).filter(this::notNull).map(RBNode.class::cast).ifPresent(this::removeNode);
	}

	private void removeNode(RBNode<K, V> z) {
		boolean yCol = z.color;
		RBNode<K, V> x = nil();

		if (z.l == nil()) {
			x = z.r;
			transplant(z, z.r);
		} else if (z.r == nil()) {
			x = z.l;
			transplant(z, z.l);
		} else {
			RBNode<K, V> y = min(z.r);
			yCol = y.color;
			x = y.r;

			if (y.p != z) {
				transplant(y, x);
				y.r = z.r;
				z.r.p = y;
			}

			transplant(z, y);

			y.l = z.l;
			z.l.p = y;
			y.color = z.color;
		}

		if (yCol == BLACK && x != nil())
			deleteBalance(x);

		this.size--;

	}

	void preorder(RBNode<K, V> n, int h, List<Integer> blackDepth) {
		if (n.color == BLACK)
			h++;

		if (n.l == nil() && n.r == nil())
			blackDepth.add(h);

		if (n.l != nil())
			preorder(n.l, h, blackDepth);

		if (n.r != nil())
			preorder(n.r, h, blackDepth);
	}

	private void deleteBalance(RBNode<K, V> x) {
		while (x != root && x.color == BLACK) {
			if (x == x.p.l) {
				RBNode<K, V> sib = x.p.r;

				if (sib.color == RED) {
					sib.color = BLACK;
					x.p.color = RED;
					lr(x.p);
					sib = x.p.r;
				}

				if (sib.l.color == BLACK &&
				    sib.r.color == BLACK) {
					sib.color = RED;
					x = x.p;
				} else {
					if (sib.r.color == BLACK) {
						sib.l.color = BLACK;
						sib.color = RED;
						rr(sib);
						sib = x.p.r;
					}
					sib.color = x.p.color;
					sib.r.color = x.p.color = BLACK;
					lr(x.p);
					x = root;
				}
			} else {
				RBNode<K, V> sib = x.p.l;

				if (sib.color == RED) {
					sib.color = BLACK;
					x.p.color = RED;
					rr(x.p);
					sib = x.p.l;
				}

				if (sib.r.color == BLACK &&
				    sib.l.color == BLACK) {
					sib.color = RED;
					x = x.p;
				} else {
					if (sib.l.color == BLACK) {
						sib.r.color = BLACK;
						sib.color = RED;
						lr(sib);
						sib = x.p.l;
					}
					sib.color = x.p.color;
					sib.l.color = x.p.color = BLACK;
					rr(x.p);
					x = root;
				}
			}
		}

		x.color = BLACK;
	}

	@Override
	public <T extends Node<K, V>> T root() {
		return cast(this.root);
	}

	@Override
	public <T extends Node<K, V>> void root(T root) {
		this.root = cast(root);
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public void clear() {
		this.root=nil();
		this.size = 0;
	}

	@Override
	public <T> T nil() {
		return cast(NIL);
	}

	//------------------------- BTNode<K,V> implementation-------------------------------
	private static class RBNode<K extends Comparable<K>, V> extends Node<K, V> {
		RBNode<K, V>	p , l , r;
		boolean			color;

		public RBNode(K k, V v) {
			this(k, v, RED);
		}

		public RBNode(K k, V v, boolean color) {
			super(k, v);
			this.color = color;
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
