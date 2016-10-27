package iptest;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringTest {

	@Test
	public void test() {
		assertTrue("mucky".compareTo("bucky") > 0);
	}

}
