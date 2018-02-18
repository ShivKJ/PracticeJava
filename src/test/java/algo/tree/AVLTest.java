package algo.tree;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class AVLTest {

	@Test
	public void testPut() {
		Random random = new Random(100L);

		AVL<Integer, String> avl = new AVL<>();

		List<Integer> list = random.ints(100000).distinct().boxed().collect(toList());

		for (Integer integer : list)
			avl.put(integer, null);

		for (Integer integer : list)
			assertTrue(avl.containsKey(integer));

	}

	@Test
	public void testGet() {
		AVL<Integer, Character> avl = new AVL<>();
		for (int i = 65; i < 80; i++)
			avl.put(i, (char) i);

		for (int i = 65; i < 80; i++)
			assertEquals(avl.get(i), (char) i);
	}

	@Test
	public void testGetOrDefault() {
		AVL<Integer, String> avl = new AVL<>();
		avl.put(10, "a");
		avl.put(20, "b");
		avl.put(30, "c");
		assertEquals(avl.getOrDefault(40, "d"), "d");
		avl.remove(20);
		assertNull(avl.getOrDefault(20, null));

	}

	@Test
	public void testRemove() {
		List<Integer> l1 = IntStream.range(0, 100).boxed().collect(toList());
		List<Integer> l2 = IntStream.range(100, 200).boxed().collect(toList());
		AVL<Integer, Integer> avl = new AVL<>();

		Stream.concat(l1.stream(), l2.stream()).forEach(x -> avl.put(x, x));

		l1.stream().forEach(avl::remove);

		List<Integer> l3 = IntStream.range(300, 400).boxed().collect(toList());

		l3.forEach(x -> avl.put(x, x));

		Stream.concat(l2.stream(), l3.stream()).forEach(x -> assertNotNull(avl.get(x)));

	}

	@Test
	public void testSize() {
		List<Integer> l1 = IntStream.range(0, 100).boxed().collect(toList());
		AVL<Integer, Integer> avl = new AVL<>();
		l1.stream().forEach(x -> avl.put(x, x));

		assertEquals(l1.size(), avl.size());
	}

	@Test
	public void testIsEmpty() {

		AVL<Integer, Integer> avl = new AVL<>();
		assertTrue(avl.isEmpty());

		List<Integer> l1 = IntStream.range(0, 100).boxed().collect(toList());
		l1.stream().forEach(x -> avl.put(x, x));
		l1.stream().forEach(avl::remove);

		assertTrue(avl.isEmpty());

	}

	@Test
	public void testEntrySet() {
		AVL<Integer, Integer> avl = new AVL<>();
		List<Integer> l1 = IntStream.range(0, 100).boxed().collect(toList());
		l1.stream().forEach(x -> avl.put(x, x));

		Set<Entry<Integer, Integer>> set = avl.entrySet();
		assertTrue(set.size() == l1.size());

		for (Entry<Integer, Integer> entry : set)
			assertTrue(l1.contains(entry.getKey()));
	}

	@Test
	public void testContainsKey() {
		AVL<Integer, Integer> avl = new AVL<>();
		List<Integer> l1 = IntStream.range(0, 10).boxed().collect(toList());
		l1.stream().forEach(x -> avl.put(x, x));

		for (Integer integer : l1)
			assertTrue(avl.containsKey(integer));

	}

	@Test
	public void testClear() {
		AVL<Integer, Integer> avl = new AVL<>();
		List<Integer> l1 = IntStream.range(0, 10).boxed().collect(toList());
		l1.stream().forEach(x -> avl.put(x, x));
		avl.clear();
		assertTrue(avl.isEmpty());

	}

}
