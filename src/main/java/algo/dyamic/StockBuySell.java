package algo.dyamic;

import static java.lang.Math.max;

public final class StockBuySell {

    public static int maxProfit(int[] values) {
        int minIdx = 0, profit = 0;

        for (int i = 1; i < values.length; i++)
            if (values[i] < values[minIdx])
                minIdx = i;
            else
                profit = max(profit, values[i] - values[minIdx]);

        return profit;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[] { 100, 180, 260, 310, 40, 535, 695 }));
        System.out.println(maxProfit(new int[] { 7, 6, 4, 3, 1 }));
        System.out.println(maxProfit(new int[] { 3, 3 }));
        System.out.println(maxProfit(new int[] { 1, 2, 4, 2, 5, 7, 2, 4, 9, 0 }));
    }

}
