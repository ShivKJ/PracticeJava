package algo.dynamic;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import algo.dyamic.KnapSack01;
import algo.dyamic.KnapSack01.KnapSackItem;

public class TestingKnapsack01 {

	public static void print(int[][] arr) {
		for (int[] is : arr) {
			System.out.println(Arrays.toString(is));
		}
	}

	public static void main(String[] args) {
		KnapSackItem[] items = {
		    new KnapSackItem(1, 1),
		    new KnapSackItem(4, 5),
		    new KnapSackItem(3, 4),
		    new KnapSackItem(5, 7) };
		Random random = new Random();
		items = IntStream.range(0, 10000)
		                 .mapToObj(i -> new KnapSackItem(random.nextInt(100) + 10, random.nextInt(5) + 1))
		                 .toArray(KnapSackItem[]::new);

		System.out.println(KnapSack01.knapsack01(items, 300));
	}
}
