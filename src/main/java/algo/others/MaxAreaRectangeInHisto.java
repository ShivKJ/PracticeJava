package algo.others;

import static java.lang.Math.max;
import static java.util.Collections.asLifoQueue;

import java.util.LinkedList;
import java.util.Queue;

public class MaxAreaRectangeInHisto {
    public static int largestRectangleArea(int[] arr) {
        Queue<Integer> stack = asLifoQueue(new LinkedList<>());
        /*
         * Stack has elements as indices such that top element is greater than
         * all remaining elements.
         */

        int area = 0, currIdx = 0;

        for (currIdx = 0; currIdx < arr.length;)
            if (!stack.isEmpty() && arr[stack.peek()] > arr[currIdx])
                area = max(area, arr[stack.poll()] * width(currIdx, stack));
            else
                stack.add(currIdx++);

        while (!stack.isEmpty())
            area = max(area, arr[stack.poll()] * width(currIdx, stack));

        return area;
    }

    /**
     * If the stack is empty, then with is "currIdx"
     * else, (curr - 1) - topValue.
     * 
     * @param currIdx
     * @param stack
     * @return
     */
    private static int width(int currIdx, Queue<Integer> stack) {
        int lastIdx = stack.isEmpty() ? 0 : (stack.peek() + 1);

        return currIdx - lastIdx;
    }
}
