package workers;

/**
 * Every member of the company who is on the payroll is a Waged worker. This
 * means we can compute the monthly pay due to that worker.
 * 
 * @author mefoster (based on code from jsinger)
 */
public interface Waged {
	/**
	 * compute the monthly wage packet (in pounds) due for a particular worker
	 * 
	 * @return The monthly wage for this employee
	 */
	double calculateMonthlyWage();
}