package algo.sorting;

import java.util.ArrayList;
import java.util.List;

public class InsertionSort<T extends Comparable<T>> implements Sort<T> {
	private final List<T> data;

	public InsertionSort(List<? extends T> data) {
		this.data = new ArrayList<>(data);
	}

	@Override
	public List<T> sort() {
		for (int j = 1; j < data.size(); j++) {
			T jth = data.get(j);
			int i = j - 1;

			while (i >= 0 && data.get(i).compareTo(jth) > 0)
				data.set(i + 1, data.get(i--));

			data.set(i + 1, jth);
		}
		return data;
	}

}
