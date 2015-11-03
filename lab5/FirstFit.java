package fillingcrates;

public class FirstFit extends AbstractFit {

	public FirstFit() {
		super("first-fit");
	}

	public void addAmount(int amount) {
		if (containers.isEmpty()) {
			containers.add(new Crate(AbstractFit.SIZE));
		}

		FillableContainer candidate = null;
		for (FillableContainer fc : containers) {
			if (fc.getAvailableCapacity() >= amount) {
				candidate = fc;
				break;
			}
		}

		if (candidate == null) {
			candidate = new Crate(AbstractFit.SIZE);
			containers.add(candidate);
		}

		try {
			candidate.store(amount);
		} catch (InsufficientCapacityException e) {
			System.out.println(e.getMessage());
		}
	}
}
