package words;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Repeatedly run both word analyzers to illustrate the benefit of memoization.
 * 
 * @author mefoster
 *
 */
public class TestTiming {

	/**
	 * How many times to run the test -- bigger numbers make the difference even
	 * more obvious.
	 */
	private static final int MAX_REPEATS = 10;

	/**
	 * Run each word analyzer on the file indicated by the first element of
	 * args.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Run each word analyzer repeatedly
		long result1 = runInLoop(new SimpleWordAnalyzer(args[0]));
		long result2 = runInLoop(new CachingWordAnalyzer(args[0]));
		
		System.out.printf("\nDifference in total time: %dms\n", result1 - result2);
	}

	/**
	 * Helper method to run the given word analyzer repeatedly in a loop.
	 * 
	 * @param wa The word analyzer instance to run
	 */
	private static long runInLoop(WordAnalyzer wa) {
		System.out.println("Testing " + wa.getClass().getName() + " ...");
		Instant startTime = Instant.now();

		for (int i = 0; i < MAX_REPEATS; i++) {
			// Test all the letters
			Instant start2 = Instant.now();
			for (char c = 'a'; c <= 'z'; c++) {
				// Just run them both and ignore the result
				wa.longestWordStartingWith(c);
				wa.wordWithMostOccurrencesOf(c);
			}
			System.out.printf("- Iteration %d, time %dms\n", i, start2.until(Instant.now(), ChronoUnit.MILLIS));
		}

		Instant endTime = Instant.now();
		long duration = startTime.until(endTime, ChronoUnit.MILLIS);
		System.out.printf("=== Total time for %s: %dms\n\n", wa.getClass().getName(), duration);
		return duration;
	}
}
