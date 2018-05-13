package knapsack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import algo.dyamic.KnapSack01;
import algo.dyamic.KnapSack01.KnapSackItem;

public class KnapSackTesting {
	public static void run(String file) {

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = reader.readLine();
			String[] split = line.split("\\s+");
			int divide = 5;
			int itemsCount = Integer.parseInt(split[0]) , capacity = Integer.parseInt(split[1]) / divide;
			KnapSackItem[] items = new KnapSackItem[itemsCount];

			int idx = 0;
			while ((line = reader.readLine()) != null) {
				split = line.split("\\s+");
				items[idx++] = new KnapSackItem(Integer.parseInt(split[1]) / divide, Integer.parseInt(split[0]));
			}
			List<KnapSackItem> taken = KnapSack01.knapsack01(items, capacity);

			System.out.println(taken.stream().mapToInt(KnapSackItem::value).sum());
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		run("data/knapsack/ks_100_0.txt");
	}
}
