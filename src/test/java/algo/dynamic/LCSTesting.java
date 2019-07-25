package algo.dynamic;

import static algo.dyamic.LCS.lcs;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LCSTesting {

    @Test
    public void testLcs1() {
        String a = "", b = "";
        assertEquals(lcs(a.toCharArray(), b.toCharArray()), "");
    }

    @Test
    public void testLcs2() {
        String a = "abc", b = "";
        assertEquals(lcs(a.toCharArray(), b.toCharArray()), "");
    }

    @Test
    public void testLcs3() {
        String a = "", b = "abc";
        assertEquals(lcs(a.toCharArray(), b.toCharArray()), "");
    }

    @Test
    public void testLcs4() {
        String a = "abcdefgh", b = "axcdgi";
        assertEquals(lcs(a.toCharArray(), b.toCharArray()), "acdg");
    }

    @Test
    public void testLcs5() {
        int[] a = { 4, 1, 6, 3, 2, 7, 8, 2, 5, -1, 9, 6, 9 };
        assertEquals(lcs(a), asList(4, 6, 7, 8, 9));
    }

}
