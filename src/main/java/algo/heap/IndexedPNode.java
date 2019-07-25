package algo.heap;

public abstract class IndexedPNode<E, P extends Comparable<P>> implements PNode<E, P> {
    public static final int DEFAULT_INDEX = -1;
    private int             index;

    public IndexedPNode() {
        this.index = DEFAULT_INDEX;
    }

    public int index() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
