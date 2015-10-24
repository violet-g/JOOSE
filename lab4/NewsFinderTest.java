package checknews;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JUnit tests for the {@link NewsFinder} class
 * 
 * @author jsinger
 */
public class NewsFinderTest {

	@Test
	public void setupNewsFinder() throws Exception {
		NewsFinder f = null;
		f = new NewsFinder("https://www.glasgow.gov.uk/RSS_Feed_Traffic_Alerts/");
		assertTrue("unable to construct NewsFinder for well-known feed", f != null);
	}

	@Test
	public void setupBrokenNewsFinder() throws Exception {
		boolean exceptionThrown = false;
		try {
			NewsFinder f = new NewsFinder("https://xxxx.yyy");
		} catch (Exception ex) {
			exceptionThrown = true;
		}
		assertFalse("Exceptions should not be thrown from NewsFinder constructor", exceptionThrown);
	}

	@Test
	public void searchInNews1() throws Exception {
		NewsFinder f = new NewsFinder("https://www.glasgow.gov.uk/RSS_Feed_Traffic_Alerts/");
		assertTrue("unable to find Glasgow in local traffic news", f.isInNews("Glasgow"));
	}

	@Test
	public void searchInNews2() throws Exception {
		NewsFinder f = new NewsFinder("http://feeds.bbci.co.uk/news/scotland/rss.xml?edition=uk");
		assertTrue("unable to find Scotland in local BBC news", f.isInNews("Scot"));
	}

}
