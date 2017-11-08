package algo.sort;

import static com.google.common.base.Stopwatch.createStarted;
import static com.google.common.collect.Comparators.isInOrder;
import static java.util.Comparator.naturalOrder;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.List;
import java.util.Random;

import com.google.common.base.Stopwatch;

import algo.sorting.MergeSort;

class MergeSorting2 {
	public static void main(String[] args) {
		Random random = new Random(10L);
		Double[] arr = random.doubles(3000000, 0, 100).boxed().toArray(Double[]::new);

		Stopwatch stopwatch = createStarted();

		List<Double> list = new MergeSort<>(arr).sort();

		System.out.println(isInOrder(list, naturalOrder()));
		System.out.println(stopwatch.elapsed(MILLISECONDS));
	}
}
