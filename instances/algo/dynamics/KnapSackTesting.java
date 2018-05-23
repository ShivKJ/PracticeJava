package algo.dynamics;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;
import java.io.IOException;

import algo.dyamic.KnapSack01;
import algo.dyamic.KnapSack01.KnapSackItem;

public class KnapSackTesting {
	private static class Item extends KnapSackItem {
		boolean taken;

		Item(int weight, int value) {

			super(weight, value);
		}

		void taken() {
			this.taken = true;
		}

		@Override
		public String toString() {

			return taken ? "1" : "0";
		}

	}

	private static Item getItem(String line) {
		String[] split = line.split("\\s+");
		return new Item(parseInt(split[1]), parseInt(split[0]));
	}

	private static void run(String file) throws IOException {
		try (BufferedReader reader = newBufferedReader(get(file))) {
			int capacity = parseInt(reader.readLine().split("\\s+")[1]);

			Item[] items = reader.lines()
			                     .map(KnapSackTesting::getItem)
			                     .toArray(Item[]::new);

			KnapSack01.knapsack01(items, capacity).forEach(Item::taken);

			out.println(stream(items).mapToInt(Item::value).sum() + " 0");
			out.println(stream(items).map(Item::toString).collect(joining(" ")));

		} catch (IOException e) {
			throw new IOException();
		}

	}

	public static void main(String[] args) throws IOException {
		String fileName = stream(args).filter(x -> x.startsWith("--file="))
		                              .findAny()
		                              .orElseThrow(IllegalArgumentException::new)
		                              .substring(7);

		run(fileName);

	}
}
