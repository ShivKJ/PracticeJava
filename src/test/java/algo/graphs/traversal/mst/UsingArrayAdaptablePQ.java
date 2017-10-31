package algo.graphs.traversal.mst;

import java.util.ArrayList;
import java.util.List;

import algo.ds.adaptablePQ.AdaptablePriorityQueue;
import algo.ds.adaptablePQ.ArrayPriorityQueue;
import algo.ds.adaptablePQ.PQNode;

class UsingArrayAdaptablePQ {
	public static void main(String[] args) {
		PQNode<String> n1 = new PQNode<>("S", 1) ,
				n2 = new PQNode<>("A", 2) ,
				n3 = new PQNode<>("B", 3) ,
				n4 = new PQNode<>("C", 4) ,
				n5 = new PQNode<>("D", 4);
		List<PQNode<String>> list = new ArrayList<>();
		list.add(n1);
		list.add(n2);
		list.add(n3);
		list.add(n4);
		list.add(n5);
		AdaptablePriorityQueue<PQNode<String>> apq = new ArrayPriorityQueue<>(list);
		System.out.println(apq.poll().getData());
		apq.updatePriority(n3, 0);
		System.out.println(apq.poll().getData());
		System.out.println(apq.contains(n3));

	}
}
