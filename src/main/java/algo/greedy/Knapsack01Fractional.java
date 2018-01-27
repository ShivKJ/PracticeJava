package algo.greedy;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparingDouble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class Knapsack01Fractional {
	private Knapsack01Fractional() {}

	public static class KnapSackItem {
		private final double	value , weight;
		private double			weightPicked;

		public KnapSackItem(double value, double weight) {
			this.value = value;
			this.weight = weight;
		}

		public double getValue() {
			return value;
		}

		public double getWeight() {
			return weight;
		}

		public double getWeightPicked() {
			return weightPicked;
		}

		double ratio() {
			return value / weight;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends KnapSackItem> List<T> knapSackFractional(Collection<T> knapSackItems, double W) {
		List<T> items = new ArrayList<>(knapSackItems);
		
		items.sort(reverseOrder(comparingDouble(KnapSackItem::ratio)));

		Iterator<T> iter = items.iterator();

		double w = 0;// how much has been picked

		List<T> output = new ArrayList<>();

		while (iter.hasNext() && w < W) {
			KnapSackItem item = iter.next();

			item.weightPicked = item.weight + w <= W ? item.weight : W - w;

			w += item.weightPicked;

			output.add((T) item);
		}

		return output;
	}
}
