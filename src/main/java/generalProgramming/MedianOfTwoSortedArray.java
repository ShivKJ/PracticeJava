package generalProgramming;

public class MedianOfTwoSortedArray {
	@SuppressWarnings("unused")
	public static int median(int[] a, int[] b) {
		if (a.length > b.length)
			return median(b, a);
		int idx1 = a.length / 2 , idx2 = (a.length + b.length + 1) / 2 - idx1;
		//TODO
		
		
		return 0;
	}
}
