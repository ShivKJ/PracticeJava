package algo.dynamic;

import static algo.dyamic.LCS.lcs;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LCSTesting {

	@Test
	public void testLcs1() {
		String a = "" , b = "";
		assertEquals(lcs(a.toCharArray(), b.toCharArray()), "");
	}

	@Test
	public void testLcs2() {
		String a = "abc" , b = "";
		assertEquals(lcs(a.toCharArray(), b.toCharArray()), "");
	}

	@Test
	public void testLcs3() {
		String a = "" , b = "abc";
		assertEquals(lcs(a.toCharArray(), b.toCharArray()), "");
	}

	@Test
	public void testLcs4() {
		String a = "abcdefgh" , b = "axcdgi";
		assertEquals(lcs(a.toCharArray(), b.toCharArray()), "acdg");
	}

}
