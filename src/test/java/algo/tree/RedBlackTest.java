package algo.tree;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Test;

public class RedBlackTest {

    @Test
    public void testPut() {
        RedBlack<Integer, Integer> tree = new RedBlack<>();

        Random random = new Random(100L);
        List<Integer> list = random.ints(100000).distinct().boxed().collect(toList());

        for (Integer integer : list)
            tree.put(integer, null);

        for (Integer integer : list)
            assertTrue(tree.containsKey(integer));

    }

    @Test(timeout = 2000)
    @Ignore(value = "It has not been debugged.")
    public void testRemove() {
        RedBlack<Integer, Integer> tree = new RedBlack<>();

        List<Integer> l1 = IntStream.range(0, 1000).boxed().collect(toList());
        List<Integer> l2 = IntStream.range(1000, 2000).boxed().collect(toList());

        Collections.shuffle(l2);
        Collections.shuffle(l1);

        Stream.concat(l1.stream(), l2.stream()).forEach(x -> tree.put(x, x));

        assertTrue(tree.size() == l1.size() + l2.size());
        l1.forEach(tree::remove);

        assertTrue(tree.size() == l2.size());

        for (Integer integer : l2)
            assertTrue(tree.containsKey(integer));

    }

}
