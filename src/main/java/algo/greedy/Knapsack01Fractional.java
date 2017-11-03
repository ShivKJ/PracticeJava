package algo.greedy;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparingDouble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import algo.ds.adaptablePQ.DataWrapper;

public final class Knapsack01Fractional {
	private Knapsack01Fractional() {}

	public static abstract class KnapSackItem<T> implements DataWrapper<T> {
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

	public static <T> List<KnapSackItem<T>> knapSackFractional(Collection<? extends KnapSackItem<T>> knapSackItems, double W) {
		List<KnapSackItem<T>> items = new ArrayList<>(knapSackItems);

		items.sort(reverseOrder(comparingDouble(KnapSackItem<T>::ratio)));

		Iterator<KnapSackItem<T>> iter = items.iterator();

		double w = 0;// how much has been picked

		List<KnapSackItem<T>> output = new ArrayList<>();

		while (iter.hasNext() && w < W) {
			KnapSackItem<T> item = iter.next();

			item.weightPicked = item.weight + w <= W ? item.weight : W - w;

			w += item.weightPicked;

			output.add(item);
		}

		return output;
	}
}
