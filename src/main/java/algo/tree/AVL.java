package algo.tree;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

/**
 * 
 * @author Shiv_Krishna_Jaiswal
 *
 * @param <K>
 * @param <V>
 */
public class AVL<K extends Comparable<K>, V> {
	private final static class AVLNode<K extends Comparable<K>, V> implements Entry<K, V> {
		final K			k;
		V				v;
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
			return abs(heightDiff()) <= 1;
		}

		void updateHeight() {
			this.h = 1 + max(lh(), rh());
		}

		int heightDiff() {
			return lh() - rh();
		}

		@Override
		public String toString() {

			return format("%s=%s", k, v);
		}

		@Override
		public K getKey() {

			return k;
		}

		@Override
		public V getValue() {

			return v;
		}

		@Override
		public V setValue(V value) {
			V oldV = v;
			this.v = value;
			return oldV;
		}
	}

	private AVLNode<K, V>	root;
	private int				size;

	public AVL() {
		this.root = null;
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
	public V put(K k, V v) {
		AVLNode<K, V> x = this.root , y = null;
		int comp = 0;

		while (x != null) {
			y = x;

			comp = k.compareTo(x.k);

			if (comp == 0) {
				V oldV = x.v;
				x.v = v;
				return oldV;
			}

			x = comp < 0 ? x.l : x.r;
		}

		AVLNode<K, V> n = new AVLNode<>(k, v);

		if (y == null)
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
		while (x.p != null && x.p.p != null) {
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

	/**
	 * Retrieving value corresponding to a key k.
	 * If no such key exists, then returning null.
	 *  
	 * @param k
	 * @return
	 */
	public V get(K k) {
		return getOrDefault(k, null);
	}

	/**
	 * Retrieving value corresponding to a key k.
	 * If no such key exists, then returning default value.
	 *  
	 * @param k
	 * @param v
	 * @return
	 */
	public V getOrDefault(K k, V v) {
		return getNode(k).map(Entry::getValue).orElse(v);
	}

	private Optional<AVLNode<K, V>> getNode(K k) {
		AVLNode<K, V> x = root;

		while (x != null) {
			int comp = k.compareTo(x.k);

			if (comp == 0)
				return of(x);

			x = comp < 0 ? x.l : x.r;
		}

		return empty();
	}

	/**
	 * Removing data corresponding to key k when
	 * k is present in Tree.
	 * 
	 * @param k
	 */
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

			transplant(z, y);

			y.l = z.l;
			z.l.p = y;
		}

		if (balancingNode != null)
			deleteBalance(balancingNode);

		this.size--;

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

	private static <K extends Comparable<K>, V> AVLNode<K, V> min(AVLNode<K, V> n) {
		while (n.l != null)
			n = n.l;
		return n;
	}

	private void deleteBalance(AVLNode<K, V> z) {
		while (z != null) {
			z.updateHeight();
			int zhDiff = z.heightDiff();

			if (abs(zhDiff) > 1) {
				if (zhDiff > 0) {
					AVLNode<K, V> y = z.l;
					balence(y.heightDiff() >= 0 ? y.l : y.r);
				} else {
					AVLNode<K, V> y = z.r;
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
	public int size() {
		return size;
	}

	/**
	 * Returns if Tree has no Node.
	 */
	public boolean isEmpty() {
		return this.root == null;
	}

	private final static class EntrySet<K extends Comparable<K>, V> extends AbstractSet<Entry<K, V>> {
		final List<Entry<K, V>> sortedList;

		public EntrySet(List<Entry<K, V>> sortedList) {
			this.sortedList = sortedList;
		}

		@Override
		public Iterator<Entry<K, V>> iterator() {
			Iterator<Entry<K, V>> iter = sortedList.iterator();

			return new Iterator<Entry<K, V>>() {

				@Override
				public boolean hasNext() {

					return iter.hasNext();
				}

				@Override
				public Entry<K, V> next() {

					return iter.next();
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}

		@Override
		public int size() {
			return sortedList.size();
		}

	}

	/**
	 * Returns set containing entries.
	 * @return
	 */
	public Set<Entry<K, V>> entrySet() {
		List<Entry<K, V>> entries = new ArrayList<>(size);

		inorder(root, entries);

		return new EntrySet<>(entries);
	}

	private static <K extends Comparable<K>, V> void inorder(AVLNode<K, V> n, Collection<Entry<K, V>> coll) {
		if (n.l != null)
			inorder(n.l, coll);

		coll.add(n);

		if (n.r != null)
			inorder(n.r, coll);

	}

	/**
	 * Returns if data corresponding to key is present.
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(K key) {
		return getNode(key).isPresent();
	}

	/**
	 * Removes all Nodes from Tree.
	 */

	public void clear() {
		this.root = null;
		this.size = 0;
	}

}
