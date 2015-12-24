package workers;

import java.time.LocalDate;

/**
 * An hourly employee -- i.e., an employee whose pay is determined by an hourly
 * rate.
 * 
 * @author mefoster
 *
 */
public class HourlyEmployee extends Employee {

	private double hourlyRate;
	private int contractedHours;

	/**
	 * Creates a new HourlyEmployee.
	 * 
	 * @param familyName
	 *            The family name to use
	 * @param givenName
	 *            The given name to use
	 * @param startingDate
	 *            The starting date
	 * @param hourlyRate
	 *            The hourly rate
	 * @param contractedHours
	 *            The worker's contracted hours
	 */
	public HourlyEmployee(String familyName, String givenName, LocalDate startingDate, double hourlyRate,
			int contractedHours) {
		super(familyName, givenName, startingDate);
		this.hourlyRate = hourlyRate;
		this.contractedHours = contractedHours;
		this.employeeType = "Hourly";
	}

	/**
	 * @return the contractedHours
	 */
	public int getContractedHours() {
		return contractedHours;
	}

	/**
	 * @param contractedHours
	 *            the contractedHours to set
	 */
	public void setContractedHours(int contractedHours) {
		this.contractedHours = contractedHours;
	}

	/**
	 * @return the hourly rate
	 */
	public double getHourlyRate() {
		return hourlyRate;
	}

	/**
	 * @param hourlyRate
	 *            the hourly rate to set
	 */
	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	/**
	 * Returns the rate and contracted hours as a string, for use in toString().
	 * 
	 * @see workers.Employee#getDetails()
	 */
	@Override
	public String getDetails() {
		return String.format("Rate: £%.2f, Hours: %d", hourlyRate, contractedHours);
	}

}
