package fillingcrates;

/**
 * represents an entity with
 * a finite capacity that can be
 * filled by storing things in it.
 * 
 * @author mefoster (based on jsinger)
 */
interface FillableContainer {
    
    /**
     * @return
     * total capacity of container,
     * equals available capacity when
     * container is empty
     */
    int getTotalCapacity();

    /**
     * @return
     * currently
     * available capacity for further
     * storage
     */
    int getAvailableCapacity();
    
    /**
     * @return
     * capacity that is occupied in
     * this container.
     *
     * @invariant 
     * getUsedCapacity() + getAvailableCapacity() == 
     * getTotalCapacity()
     */
    int getUsedCapacity();

    /**
     * nothing is stored in this
     * container. 
     * @return true when the container is empty
     * @invariant
     * isEmpty() implies (getUsedCapacity() == 0)
     */
    boolean isEmpty();

    /**
     * store some more 'stuff'
     * in this container.
     * @throws InsufficientCapacityException if there
     * is not enough room in the container for the
     * store to succeed
     */
    void store(int size) throws InsufficientCapacityException;
}