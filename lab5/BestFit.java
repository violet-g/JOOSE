package fillingcrates;

public class BestFit extends AbstractFit {

	public BestFit() {
		super("best-fit");
	}

	public void addAmount(int amount) {
		if (containers.isEmpty()) {
			containers.add(new Crate(AbstractFit.SIZE));
		}

		int smallest = AbstractFit.SIZE + 1;
		FillableContainer candidate = null;

		for (FillableContainer fc : containers) {
			int capacity = fc.getAvailableCapacity() - amount;
			if (capacity >= 0 && capacity < smallest) {
				smallest = capacity;
				candidate = fc;
			}
		}

		if (candidate == null) {
			candidate = new Crate(AbstractFit.SIZE);
			containers.add(candidate);
		}

		try {
			candidate.store(amount);
		} catch (InsufficientCapacityException exc) {
			System.out.println(exc.getMessage());
		}
	}
}
