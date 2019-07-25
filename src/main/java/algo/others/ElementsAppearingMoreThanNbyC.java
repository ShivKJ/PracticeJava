package algo.others;

import static java.util.Objects.nonNull;
import static java.util.stream.Stream.generate;

public final class ElementsAppearingMoreThanNbyC {
    private final int[]   arr;
    private final int     freq;
    private final Count[] counts;

    private static class Count {
        int e, c;
    }

    public ElementsAppearingMoreThanNbyC(int[] arr, int freq) {
        this.arr = arr;
        this.counts = generate(Count::new).limit(arr.length / freq)
                                          .toArray(Count[]::new);
        this.freq = freq;
    }

    public void process() {
        for (int i = 0; i < arr.length; i++) {
            Count ifNotFound = null;
            boolean found = false;
            int e = arr[i];

            for (int j = 0; j < counts.length; j++)
                if (counts[j].c == 0)
                    ifNotFound = counts[j];
                else if (counts[j].e == e) {
                    counts[j].c++;
                    found = true;
                    break;
                }

            if (!found)
                if (nonNull(ifNotFound)) {
                    ifNotFound.e = e;
                    ifNotFound.c++;
                } else
                    for (Count count : counts)
                        count.c--;

        }

        for (Count count : counts) {
            int freq = 0;

            if (count.c != 0)
                for (int i : arr)
                    if (i == count.e)
                        freq++;

            if (freq > this.freq)
                System.out.println("Element:" + count.e + " freq=" + freq);
        }
    }

    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 1, 5, 5, 5, 2, 0, 0, 0, 0, 0 };
        new ElementsAppearingMoreThanNbyC(arr, 2).process();
    }
}
