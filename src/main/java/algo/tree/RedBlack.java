package algo.tree;

import java.util.List;

public class RedBlack<K extends Comparable<K>, V> extends Tree<K, V> {
	private static final boolean	RED	= true , BLACK = false;
	private final BTNode			NIL	= new BTNode(null, null, BLACK);

	@Override
	public <T> T nil() {
		return cast(NIL);
	}

	private BTNode	root;
	private int		size;

	public RedBlack() {
		this.root = nil();
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
		BTNode x = this.root , y = nil();
		int comp = 0;

		while (x != nil()) {
			y = x;

			comp = k.compareTo(x.getKey());

			if (comp == 0)
				return x.setValue(v);

			x = comp < 0 ? x.l : x.r;
		}

		BTNode n = new BTNode(k, v);

		if (y == nil())
			this.root = n;
		else if (comp < 0)
			y.l = n;
		else
			y.r = n;

		n.p = y;

		this.size++;

		insertBalance(n);

		return null;

	}

	private void insertBalance(BTNode z) {
		while (z.p.color == RED) {
			if (z.p == z.p.p.l) {
				BTNode uncle = z.p.p.r;

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
				BTNode uncle = z.p.p.l;
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
		getNode(key).filter(x -> x != nil()).map(BTNode.class::cast).ifPresent(this::removeNode);
	}

	private void removeNode(BTNode z) {
		boolean yCol = z.color;
		BTNode x = null;

		if (z.l == nil()) {
			x = z.r;
			transplant(z, z.r);
		} else if (z.r == nil()) {
			x = z.l;
			transplant(z, z.l);
		} else {
			BTNode y = min(z.r);
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

	void preorder(BTNode n, int h, List<Integer> blackDepth) {
		if (n.color == BLACK)
			h++;

		if (n.l == nil() && n.r == nil())
			blackDepth.add(h);

		if (n.l != nil())
			preorder(n.l, h, blackDepth);

		if (n.r != nil())
			preorder(n.r, h, blackDepth);
	}

	private void deleteBalance(BTNode x) {
		while (x != root && x.color == BLACK) {
			if (x == x.p.l) {
				BTNode sib = x.p.r;

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
				BTNode sib = x.p.l;

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
	public <T extends Node> T root() {
		return cast(this.root);
	}

	@Override
	public <T extends Node> void root(T root) {
		this.root = cast(root);
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public void clear() {
		root(nil());
		this.size = 0;
	}

	//------------------------- BTNode implementation-------------------------------
	private class BTNode extends Node {
		BTNode	p , l , r;
		boolean	color;

		public BTNode(K k, V v) {
			this(k, v, RED);
		}

		public BTNode(K k, V v, boolean color) {
			super(k, v);
			this.color = color;
			this.l = nil();
			this.r = nil();
			this.p = nil();
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

}
