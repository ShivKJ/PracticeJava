package algo.graphs.traversal.mst;

import java.util.ArrayList;
import java.util.List;

import algo.ds.adaptablePQ.AdaptablePriorityQueue;
import algo.ds.adaptablePQ.ArrayPriorityQueue;
import algo.ds.adaptablePQ.IndexedPNodeImpl;

class UsingArrayAdaptablePQ {
	public static void main(String[] args) {
		IndexedPNodeImpl<?, Integer> n1 = new IndexedPNodeImpl<>("S", 1) ,
				n2 = new IndexedPNodeImpl<>("A", 2) ,
				n3 = new IndexedPNodeImpl<>("B", 3) ,
				n4 = new IndexedPNodeImpl<>("C", 4) ,
				n5 = new IndexedPNodeImpl<>("D", 4);
		List<IndexedPNodeImpl<?, Integer>> list = new ArrayList<>();
		list.add(n1);
		list.add(n2);
		list.add(n3);
		list.add(n4);
		list.add(n5);
		AdaptablePriorityQueue<IndexedPNodeImpl<?, Integer>> apq = new ArrayPriorityQueue<>(list);
		System.out.println((String) apq.poll().getData());
		apq.updatePriority(n3, 0);
		System.out.println((String) apq.poll().getData());
		System.out.println(apq.contains(n3));

	}
}
