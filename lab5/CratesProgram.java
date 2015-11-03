package fillingcrates;

/**
 * JOOSE2 Lab 5 -- 2015. bin-packing algorithms. This class is the program entry
 * point. It initializes some packing strategies (defined by students) and then
 * tests them out on a known sequence of inputs.
 * 
 * @author mefoster (based on code from jsinger)
 */
public class CratesProgram {

	public static void main(String... args) {
		// The test set given in the lab sheet
		int[] items = { 75, 50, 20, 60, 40, 50 };

		// Comment out the strategies you haven't implemented yet
		PackingStrategy[] strategies = { 
				new NextFit(),
				new FirstFit(),
				new BestFit(),
		};

		// Send each item in turn to each packing strategy ...
		for (int item : items) {
			for (PackingStrategy s : strategies) {
				s.addAmount(item);
			}
		}

		// ... and then print out the results
		for (PackingStrategy s : strategies) {
			System.out.println(s.toString());
		}
	}
}