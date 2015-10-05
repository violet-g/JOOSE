package scrabblescorer;

import org.junit.Assert;
import org.junit.Test;

public class ScrabbleScorerTest {

	@Test
	public void testEmptyString() {
		Assert.assertEquals(0, ScrabbleScorer.computeScore(""));
	}

	@Test
	public void testNonCharacters() {
		Assert.assertEquals(0, ScrabbleScorer.computeScore("&\"$%£&^%*\"^&"));
	}

	@Test
	public void testLowerCase() {
		Assert.assertEquals(6, ScrabbleScorer.computeScore("foo"));
		Assert.assertEquals(11, ScrabbleScorer.computeScore("foo bar"));
		Assert.assertEquals(25, ScrabbleScorer.computeScore("foo, bar, baz"));
	}

	@Test
	public void testUpperCase() {
		Assert.assertEquals(6, ScrabbleScorer.computeScore("FOO"));
		Assert.assertEquals(11, ScrabbleScorer.computeScore("FOO BAR"));
		Assert.assertEquals(25, ScrabbleScorer.computeScore("FOO, BAR, BAZ"));
	}

	@Test
	public void testMixedCase() {
		Assert.assertEquals(6, ScrabbleScorer.computeScore("fOo"));
		Assert.assertEquals(11, ScrabbleScorer.computeScore("foO Bar"));
		Assert.assertEquals(25, ScrabbleScorer.computeScore("foo, Bar, BAZ"));
	}
}
