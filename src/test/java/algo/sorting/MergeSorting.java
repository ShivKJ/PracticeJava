package algo.sorting;

import static com.google.common.base.Stopwatch.createStarted;
import static com.google.common.collect.Comparators.isInOrder;
import static java.lang.System.out;
import static java.util.Comparator.naturalOrder;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Random;

import com.google.common.base.Stopwatch;

import algo.sorting.MergeSort;

class MergeSorting {
	public static void main(String[] args) {
		Random random = new Random(10L);
		List<Double> list = random.doubles(3000000, 0, 100).boxed().collect(toList());

		Stopwatch stopwatch = createStarted();

		List<Double> sortedList = new MergeSort<>(list).sort();
		out.println(isInOrder(sortedList, naturalOrder()));
		out.println(stopwatch.elapsed(MILLISECONDS));
	}
}
