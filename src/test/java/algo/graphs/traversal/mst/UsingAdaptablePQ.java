package algo.graphs.traversal.mst;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Random;

import algo.ds.adaptablePQ.AdaptablePriorityQueue;
import algo.ds.adaptablePQ.ArrayPriorityQueue;
import algo.ds.adaptablePQ.IndexedPNodeImpl;

class UsingAdaptablePQ {
	public static void main(String[] args) {
		long time = currentTimeMillis();
		Random random = new Random(10);
		List<IndexedPNodeImpl<?, Double>> nodes = random.doubles(1_000_000, 0, 1).mapToObj(x -> new IndexedPNodeImpl<>(null, x)).collect(toList());
		AdaptablePriorityQueue<IndexedPNodeImpl<?, Double>> adaptablePriorityQueue = new ArrayPriorityQueue<>();
		for (IndexedPNodeImpl<?, Double> pqNode : nodes)
			adaptablePriorityQueue.add(pqNode);
		adaptablePriorityQueue.addAll(nodes);

		//		while (!adaptablePriorityQueue.isEmpty())
		//			adaptablePriorityQueue.poll();
		System.out.println(currentTimeMillis() - time);
	}
}
