package algo.divideAndConquer;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class TestingMaximumSubArray {

    @Test
    public void testMaximumSubArray() {
        int[] arr = {
            100, 113, 110, 85, 105, 102, 86, 63, 81, 101, 94, 106, 101, 79, 94, 90, 97
        };
        new MaximumSubArray(arr);
        assertTrue(true);
    }

    @Test
    public void testMaxSubArray() {
        int[] arr = {
            100, 113, 110, 85, 105, 102, 86, 63, 81, 101, 94, 106, 101, 79, 94, 90, 97
        };
        int[] out = new MaximumSubArray(arr).maxSubArray();
        assertTrue(Arrays.equals(out, new int[] { 7, 10, 43 }));
    }

}
