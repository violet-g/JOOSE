package fillingcrates;

/**
 * indicates when a container does not have
 * enough spare space to complete a store
 * operation successfully
 */
public class InsufficientCapacityException extends Exception {
    
	// Quiet an annoying default warning in Eclipse
	private static final long serialVersionUID = 1L;

	/**
     * @param amount space required for store
     * @param spare space available in container
     * @invariant amount &gt; spare
     */
    public InsufficientCapacityException(int amount, int spare) {
        super("Tried to store amount " + amount + " in container which only has space for " + spare);
    }
}
