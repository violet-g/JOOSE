package fillingcrates;

import java.util.ArrayList;

/**
 * extend this class to implement a particular
 * bin packing strategy - e.g. first-fit, best-fit.
 * 
 * @author mefoster (based on a class by jsinger)
 */
public abstract class AbstractFit implements PackingStrategy {

    /**
     * constant size of each container
     */
    public static final int SIZE = 100;
    /**
     * the name of the fitting algorithm e.g. "first-fit"
     * used in toString()
     */
    private String fittingAlgorithm;

    /**
     * backing list of FillableContainer instances
     */
    protected ArrayList<FillableContainer> containers;
    
    /**
     * accessor method
     * @return value of fittingAlgorithm instance field
     */
    public String getFittingAlgorithm() {
        return this.fittingAlgorithm;
    }
    
    /**
     * invoke constructor as super(String) from
     * subclasses
     */
    public AbstractFit(String fittingAlgorithm) {
        this.fittingAlgorithm = fittingAlgorithm;
        this.containers = new ArrayList<FillableContainer>();
    }
    
    /**
     * @return number of elements in containers
     * ArrayList
     */
    public int numContainers() {
        return this.containers.size();
    }
    
    /**
     * dump state of fitting to 
     * a String
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Number of crates using ");
        sb.append(this.getFittingAlgorithm());
        sb.append(" = ");
        sb.append("" + this.containers.size() + "\n");
        for (int i=0; i<containers.size(); i++) {
            sb.append("Crate " + i + " has load " + containers.get(i).getUsedCapacity() + "\n");
        }
        return sb.toString();
    }

}
