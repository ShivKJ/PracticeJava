package instances.knapsack;

import static java.util.Arrays.stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import algo.dyamic.KnapSack01;
import algo.dyamic.KnapSack01.KnapSackItem;

public class KnapSackTesting {
	private static class Item extends KnapSackItem {
		final int idx;

		public Item(int weight, int value, int idx) {

			super(weight, value);
			this.idx = idx;
		}

	}

	private static Item[] getItems(BufferedReader reader, int itemsCount) throws IOException {
		Item[] items = new Item[itemsCount];
		String line = reader.readLine();
		String[] split = line.split("\\s+");
		int idx = 0;

		while ((line = reader.readLine()) != null) {
			split = line.split("\\s+");
			items[idx] = new Item(Integer.parseInt(split[1]), Integer.parseInt(split[0]), idx);
			idx++;
		}
		return items;

	}

	private static int[] run(String file) {
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String[] split = reader.readLine().split("\\s+");
			int itemsCount = Integer.parseInt(split[0]) , capacity = Integer.parseInt(split[1]);

			Item[] items = getItems(reader, itemsCount);
			List<Item> taken = KnapSack01.knapsack01(items, capacity);
			
			System.out.println(taken.stream().mapToInt(KnapSackItem::value).sum() + " 0");

			int[] out = new int[itemsCount];
			taken.forEach(x -> out[x.idx] = 1);

			//			System.out.println("weight: " + taken.stream().mapToInt(KnapSackItem::weight).sum() + " AND actual cap: " + capacity);

			for (int idx1 : out)
				System.out.print(idx1 + " ");

			System.out.println();

			return out;

		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String fileName = stream(args).filter(x -> x.startsWith("--file="))
		                              .findAny()
		                              .orElseThrow(IllegalArgumentException::new)
		                              .substring(7);

		run(fileName);

	}
}
