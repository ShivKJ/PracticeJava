package algo.others;

public class PowerOfTwo {
    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    public static void main(String[] args) {
        System.out.println(isPowerOfTwo(4));
        System.out.println(isPowerOfTwo(-2147483648));
    }
}
