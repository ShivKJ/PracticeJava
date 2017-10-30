package algo.graphs.traversal.mst;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Random;

class UsingAdaptablePQ {
	public static void main(String[] args) {
		long time = currentTimeMillis();
		Random random = new Random(10);
		List<PQNode<?>> nodes = random.doubles(1_000_000, 0, 1).mapToObj(x -> new PQNode<>(null, x)).collect(toList());
		AdaptablePriorityQueue<PQNode<?>> adaptablePriorityQueue = new ArrayPriorityQueue<>();
		for (PQNode<?> pqNode : nodes)
			adaptablePriorityQueue.add(pqNode);
		adaptablePriorityQueue.addAll(nodes);

		//		while (!adaptablePriorityQueue.isEmpty())
		//			adaptablePriorityQueue.poll();
		System.out.println(currentTimeMillis() - time);
	}
}
