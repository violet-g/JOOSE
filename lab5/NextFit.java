package fillingcrates;

public class NextFit extends AbstractFit {

	public NextFit() {
		super("next-fit");
	}

	public static int countCrates = 0;

	public void addAmount(int amount) {
		if (containers.isEmpty()) {
			containers.add(new Crate(AbstractFit.SIZE));
		}

		FillableContainer candidate = containers.get(countCrates);

		if (candidate.getAvailableCapacity() < amount) {
			candidate = new Crate(AbstractFit.SIZE);
			containers.add(candidate);
			countCrates++;
		}

		try {
			candidate.store(amount);
		} catch (InsufficientCapacityException exception) {
			System.out.println(exception.getMessage());
		}
	}
}
