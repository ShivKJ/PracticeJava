package algo.tree;

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

public abstract class Tree<K extends Comparable<K>, V> {

	public abstract <T> T nil();

	public abstract <T extends Node<K, V>> T root();

	public abstract <T extends Node<K, V>> void root(T root);

	public abstract int size();

	public abstract V put(K k, V v);

	public abstract void remove(K k);

	public abstract void clear();

	public boolean isEmpty() {
		return root() == nil();
	}

	public static abstract class Node<K extends Comparable<K>, V> implements Entry<K, V> {
		private final K	k;
		private V		v;
		private Object	userData;

		public Node(K k, V v) {
			this.k = k;
			this.v = v;
		}

		public abstract <T extends Node<K, V>> T l();

		public abstract <T extends Node<K, V>> void l(T l);

		public abstract <T extends Node<K, V>> T p();

		public abstract <T extends Node<K, V>> void p(T p);

		public abstract <T extends Node<K, V>> T r();

		public abstract <T extends Node<K, V>> void r(T r);

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

		@Override
		public String toString() {

			return k + "=" + v;
		}

		@SuppressWarnings("unchecked")
		public <T> T getUserData() {
			return (T) userData;
		}

		public void setUserData(Object userData) {
			this.userData = userData;
		}

	}

	@SuppressWarnings("unchecked")
	public static <T, K extends Comparable<K>, V> T cast(Node<K, V> n) {
		return (T) n;
	}

	public void lr(Node<K, V> x) {
		/*
		            |                               |
		            x                               y
		           / \           L(x)              / \
		          A   y        -------->          x   G
		             / \                         / \
		            B   G                       A   B
		 */

		Node<K, V> y = x.r();
		x.r(y.l());

		if (y.l() != nil())
			y.l().p(x);

		y.p(x.p());

		if (x.p() == nil())
			root(y);
		else if (x.p().l() == x)
			x.p().l(y);
		else
			x.p().r(y);

		y.l(x);
		x.p(y);
	}

	public void rr(Node<K, V> y) {
		/*
		            |                               |
		            y                               x
		           / \           R(y)              / \
		          x   G        ------>            A   y    
		         / \                                 / \
		        A   B                               B   G
		 */
		Node<K, V> x = y.l();

		y.l(x.r());

		if (x.r() != nil())
			x.r().p(y);

		x.p(y.p());

		if (y.p() == nil())
			root(x);
		else if (y == y.p().l())
			y.p().l(x);
		else
			y.p().r(x);

		x.r(y);
		y.p(x);
	}

	public void transplant(Node<K, V> u, Node<K, V> v) {
		if (u.p() == nil())
			root(v);
		else if (u.p().l() == u)
			u.p().l(v);
		else
			u.p().r(v);

		if (v != nil())
			v.p(u.p());
	}

	public <T extends Node<K, V>> T min(T n) {

		while (n.l() != nil())
			n = n.l();
		return n;
	}

	public void inorder(Node<K, V> n, Collection<Entry<K, V>> coll) {
		if (n.l() != nil())
			inorder(n.l(), coll);

		coll.add(n);

		if (n.r() != nil())
			inorder(n.r(), coll);

	}

	/**
	 * Returns set containing entries.
	 * @return
	 */
	public Set<Entry<K, V>> entrySet() {
		List<Entry<K, V>> entries = new ArrayList<>(size());

		inorder(root(), entries);

		return new EntrySet<>(entries);
	}

	public final static class EntrySet<K extends Comparable<K>, V> extends AbstractSet<Entry<K, V>> {
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

	protected Optional<Node<K, V>> getNode(K k) {
		Node<K, V> x = root();

		while (x != nil()) {
			int comp = k.compareTo(x.getKey());
			if (comp == 0)
				return of(x);

			x = comp < 0 ? x.l() : x.r();
		}

		return ofNullable(nil());
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
		return getNode(k).filter(this::notNull).map(Entry::getValue).orElse(v);
	}

	/**
	 * Returns if data corresponding to key is present.
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(K key) {
		return getNode(key).filter(this::notNull).isPresent();
	}

	protected boolean notNull(Node<K, V> t) {
		return t != nil();
	}
}
