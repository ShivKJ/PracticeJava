package algo.sorting;

import static algo.sorting.Utils.makeArray;
import static algo.sorting.Utils.swap;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.List;

public class HeapSort<T extends Comparable<T>> extends ElementSorter<T> {
	private T[] heaped;// temporary array to create heap.

	public HeapSort(Collection<? extends T> input) {
		super(input);
	}

	public HeapSort(T[] arr) {
		super(arr);
	}

	@Override
	public List<T> sort() {
		if (isTrivial())
			return emptyList();

		heapify();
		cleanHeap();

		return asList(arr);
	}

	private void cleanHeap() {
		for (int i = 0, currentPointer = arr.length - 1; i < arr.length; i++, currentPointer--) {
			arr[i] = heaped[0];
			heaped[0] = heaped[currentPointer];
			bubbleDown(0, currentPointer);
			// setting last element to head of root node and then putting it to correct place.
		}
	}

	private void bubbleDown(int currIndex, int maxIndex) {
		int minIndex = currIndex;

		int leftChild = leftChild(currIndex);

		if (leftChild < maxIndex) {
			if (heaped[leftChild].compareTo(heaped[minIndex]) < 0)
				minIndex = leftChild;

			int rightChild = rightChild(currIndex);

			if (rightChild < maxIndex && heaped[rightChild].compareTo(heaped[minIndex]) < 0)
				minIndex = rightChild;
		}

		if (currIndex != minIndex) {
			swap(heaped, minIndex, currIndex);
			bubbleDown(minIndex, maxIndex);
		}
	}

	private void heapify() {
		this.heaped = makeArray(arr[0], arr.length);

		for (int i = 0; i < arr.length; i++) {
			heaped[i] = arr[i];
			bubbleUp(i); // putting element at correct place.
		}
	}

	private void bubbleUp(int child) {
		if (child != 0) {
			int parent = parent(child);
			if (heaped[parent].compareTo(heaped[child]) > 0) {
				swap(heaped, child, parent);
				bubbleUp(parent);
			}
		}
	}

	private static int parent(int child) {
		return (child + 1) / 2 - 1;
	}

	private static int leftChild(int parent) {
		return 2 * parent + 1;
	}

	private static int rightChild(int parent) {
		return 2 * parent + 2;
	}

}
