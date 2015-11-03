package fillingcrates;

public class Crate implements FillableContainer {

	public int totalCapacity = 100;
	public int availableCapacity;
	public int usedCapacity;

	public Crate(int capacity) {
		this.totalCapacity = capacity;
		this.availableCapacity = capacity;
	}

	public int getTotalCapacity() {
		return this.totalCapacity;
	}

	public int getAvailableCapacity() {
		return this.availableCapacity;
	}

	public int getUsedCapacity() {
		return this.usedCapacity;
	}

	public boolean isEmpty() {
		return (getTotalCapacity() == getAvailableCapacity());
	}

	public void store(int size) throws InsufficientCapacityException {
		if (usedCapacity + size > totalCapacity) {
			throw new InsufficientCapacityException(size, availableCapacity);
		}
		usedCapacity += size;
		availableCapacity = totalCapacity - usedCapacity;
	}
}
