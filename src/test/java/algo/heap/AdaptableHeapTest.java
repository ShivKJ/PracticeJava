package algo.heap;

import static com.google.common.collect.Comparators.isInOrder;
import static java.util.Collections.emptyList;
import static java.util.Collections.min;
import static java.util.Collections.shuffle;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Test;

public class AdaptableHeapTest {
	private static final long SEED = 10L;

	private static List<IndexedPNode<String, Integer>> sampleData(long seed) {
		Random random = new Random(seed);
		List<IndexedPNode<String, Integer>> list = IntStream.range(65, 80)
		                                                    .mapToObj(x -> (char) x)
		                                                    .map(String::valueOf)
		                                                    .map(x -> new IndexedPNodeImpl<>(x, random.nextInt(100)))
		                                                    .collect(toList());
		shuffle(list, random);
		return list;
	}

	@Test
	public void testSize() {
		List<IndexedPNode<String, Integer>> list = sampleData(SEED);
		AdaptablePriorityQueue<IndexedPNode<String, Integer>> queue = new ArrayPriorityQueue<>(list);
		assertEquals(list.size(), queue.size());
	}

	@Test
	public void testIsEmpty() {
		List<IndexedPNode<String, Integer>> list = sampleData(SEED);

		AdaptablePriorityQueue<IndexedPNode<String, Integer>> queue = new ArrayPriorityQueue<>(list);

		List<Integer> sorted = new ArrayList<>(queue.size());

		while (!queue.isEmpty())
			sorted.add(queue.poll().getPriority());

		assertTrue(isInOrder(sorted, comparingInt(x -> x)));
	}

	@Test
	public void testClear() {

		AdaptablePriorityQueue<IndexedPNode<String, Integer>> queue = new ArrayPriorityQueue<>(sampleData(SEED));
		queue.clear();

		assertTrue(queue.isEmpty());
		assertEquals(queue.size(), 0);
	}

	@Test
	public void testArrayPriorityQueue() {
		try {
			new ArrayPriorityQueue<>();
			new ArrayPriorityQueue<>(emptyList());
			new ArrayPriorityQueue<>(Integer::compare);
			new ArrayPriorityQueue<>(emptyList(), Integer::compare);

			assertTrue(true);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testUpdatePriority() {
		List<IndexedPNode<String, Integer>> list = sampleData(SEED);
		Random random = new Random();

		for (int i = 0; i < 2 * list.size(); i++) {
			shuffle(list);
			AdaptablePriorityQueue<IndexedPNode<String, Integer>> queue = new ArrayPriorityQueue<>(list);

			for (int j = 0; j < list.size() / 2; j++)
				queue.updatePriority(list.get(random.nextInt(list.size())), random.nextInt(100));

			list = new ArrayList<>(list.size());

			while (!queue.isEmpty())
				list.add(queue.poll());

			assertTrue(isInOrder(list, comparingInt(PNode::getPriority)));
		}
	}

	@Test
	public void testPoll() {
		List<IndexedPNode<String, Integer>> list = sampleData(SEED);

		IndexedPNode<String, Integer> node = min(list, comparingInt(PNode::getPriority));

		AdaptablePriorityQueue<IndexedPNode<String, Integer>> queue = new ArrayPriorityQueue<>(list);

		assertEquals(queue.poll().getPriority(), node.getPriority());

	}

	@Test
	public void testAddE() {
		List<IndexedPNode<String, Integer>> list = sampleData(SEED);
		AdaptablePriorityQueue<IndexedPNode<String, Integer>> queue = new ArrayPriorityQueue<>(list.subList(0, list.size() - 1));

		queue.add(list.get(list.size() - 1));
		assertEquals(queue.size(), list.size());
	}

	@Test
	public void testAddAllCollectionOfQextendsE() {
		List<IndexedPNode<String, Integer>> list = sampleData(SEED);
		AdaptablePriorityQueue<IndexedPNode<String, Integer>> queue = new ArrayPriorityQueue<>();
		queue.addAll(list);

		assertEquals(queue.size(), list.size());

		list = new ArrayList<>(queue.size());

		while (queue.isEmpty())
			list.add(queue.poll());

		assertTrue(isInOrder(list, comparingInt(PNode::getPriority)));

	}

	@Test
	public void testOffer() {
		List<IndexedPNode<String, Integer>> list = sampleData(SEED);
		AdaptablePriorityQueue<IndexedPNode<String, Integer>> queue = new ArrayPriorityQueue<>(list.subList(0, list.size() - 1));

		queue.offer(list.get(list.size() - 1));
		assertEquals(queue.size(), list.size());
	}

	@Test
	public void testPeek() {
		List<IndexedPNode<String, Integer>> list = sampleData(SEED);

		IndexedPNode<String, Integer> node = min(list, comparingInt(PNode::getPriority));

		AdaptablePriorityQueue<IndexedPNode<String, Integer>> queue = new ArrayPriorityQueue<>(list);

		assertEquals(queue.peek().getPriority(), node.getPriority());
	}
}
