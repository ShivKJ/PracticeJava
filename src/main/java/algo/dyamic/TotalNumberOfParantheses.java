package algo.dyamic;

public class TotalNumberOfParantheses {
	private final int[] memoizedArray;

	private TotalNumberOfParantheses(int mats) {
		this.memoizedArray = new int[mats];
	}

	public static int paranthesis(int dims) {
		int[] arr = new TotalNumberOfParantheses(dims).solve().memoizedArray;
		return arr[arr.length - 1];
	}

	private TotalNumberOfParantheses solve() {
		this.memoizedArray[0] = 1;

		for (int i = 0; i < memoizedArray.length; i++)
			for (int j = 0; j < i; j++)
				memoizedArray[i] += memoizedArray[j] * memoizedArray[i - j - 1];

		return this;

	}
}
