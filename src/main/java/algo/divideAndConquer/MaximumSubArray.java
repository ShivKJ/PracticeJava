package algo.divideAndConquer;

import static java.lang.Integer.MIN_VALUE;

public class MaximumSubArray {
    /**
     * To find sequence of days over which the net change from
     * first day to last day is maximum.
     */

    private final int[] A;

    public MaximumSubArray(int[] arr) {
        // arr[i] = price of stock in beginning of i_th day
        this.A = new int[arr.length - 1];

        for (int i = 0; i < arr.length - 1; i++)
            this.A[i] = arr[i + 1] - arr[i];
    }

    /**
     * Finds the sub array in between index low and high.
     * If these indices are equal then return l, h, A[l].
     * 
     * Otherwise finds middle element. Now the sub array
     * can lie either 
     * 
     * 1) in range l -> mid or 
     * 2) mid + 1 -> h or
     * 3) it can be continuous range containing mid.
     * 
     * Depending on max sum one of three arrays is returned. 
     * 
     * @param low
     * @param high
     * @return 
     */
    private int[] maxSubArray(int low, int high) {
        if (low == high)
            return new int[] { low, high, A[low] };

        int mid = low + high >> 1;

        int[] left = maxSubArray(low, mid);
        int[] right = maxSubArray(mid + 1, high);
        int[] crossing = maxCrossingSubArray(low, mid, high);

        if (left[2] >= right[2] && left[2] >= crossing[2])
            return left;

        if (right[2] >= left[2] && right[2] >= crossing[2])
            return right;

        return crossing;
    }

    /**
     * 	Finds array of max sum passing through mid.
     *  
     * @param low
     * @param mid
     * @param high
     * @return
     */

    private int[] maxCrossingSubArray(int low, int mid, int high) {
        int leftSum = MIN_VALUE, sum = 0, maxLeft = mid;

        for (int i = mid; i >= low; i--) {
            sum += A[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeft = i;
            }
        }

        int rightSum = MIN_VALUE, maxRight = mid + 1;
        sum = 0;

        for (int i = mid + 1; i <= high; i++) {
            sum += A[i];
            if (sum > rightSum) {
                rightSum = sum;
                maxRight = i;
            }
        }

        return new int[] { maxLeft, maxRight, leftSum + rightSum };
    }

    /**
     * To find optimalSubArray we search between start and 
     * last index of difference array.
     * @return
     */
    public int[] maxSubArray() {
        return maxSubArray(0, A.length - 1);
    }

}
