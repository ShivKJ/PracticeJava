package algo.sorting;

import static java.util.stream.Collectors.toList;
import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestingQuickSort {
    private static final Random  random = new Random();
    private static List<Integer> toBeSorted;
    private static List<Integer> sortedList;

    private List<Integer> instanceList;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        TestingQuickSort.toBeSorted = random.ints(1000).boxed().collect(toList());
        TestingQuickSort.sortedList = toBeSorted.stream().sorted().collect(toList());
    }

    @AfterClass
    public static void tearDownAfterClass() {
        TestingQuickSort.sortedList.clear();
        TestingQuickSort.toBeSorted.clear();
    }

    @Before
    public void setUp() {
        this.instanceList = new ArrayList<>(toBeSorted);
    }

    @After
    public void tearDown() {
        this.instanceList.clear();
    }

    @Test(timeout = 10)

    public final void testSort() {
        assertEquals(new QuickSort<>(this.instanceList).sort(), sortedList);

    }

}
