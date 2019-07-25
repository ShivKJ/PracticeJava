package algo.others;

import static algo.others.BitonicArray.search;
import static java.util.Collections.reverseOrder;
import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Test;

class BitonicTest {

    @Test
    void testSearchIntArrayInt1() {
        int[] arr = { 1, 10, -3 };
        assertEquals(search(arr, 100), -1);
        assertEquals(search(arr, 10), 1);
        assertEquals(search(arr, 1), 0);
        assertEquals(search(arr, -3), 2);
    }

    @Test
    void testSearchIntArrayInt2() {
        Random random = new Random(0);
        IntStream a = random.ints(0, 100000).distinct().limit(1000).sorted();
        IntStream b = random.ints(-1000, 100000).distinct().limit(1000).boxed().sorted(reverseOrder()).mapToInt(i -> i);

        int[] arr = IntStream.concat(a, b).toArray();

        for (int i = 0; i < arr.length; i++)
            assertEquals(arr[search(arr, arr[i])], arr[i]);

    }

}
