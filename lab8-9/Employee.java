package workers;

import java.time.LocalDate;

/**
 * A modified version of the Employee class from Lab 6. Represents an employee
 * number, given name, family name, and starting date. Subclasses of this should
 * implement the getDetails() method to append any additional information to the
 * toString() method.
 * 
 * @author mefoster
 *
 */
public abstract class Employee {
	// Common fields
	private int employeeNumber;
	private String familyName;
	private String givenName;
	private LocalDate startingDate;

	// Used to ensure sequential employee numbers
	private static int nextEmployeeNumber = 1;

	// Used in toString() method
	protected String employeeType;

	/**
	 * @return The list of possible employee types
	 */
	public static final String[] getEmployeeTypes() {
		return new String[] { "Salaried", "Hourly" };
	}

	/**
	 * @param familyName
	 * @param givenName
	 * @param startingDate
	 */
	public Employee(String familyName, String givenName, LocalDate startingDate) {
		this.employeeNumber = nextEmployeeNumber++;
		this.familyName = familyName;
		this.givenName = givenName;
		this.startingDate = startingDate;
	}

	/**
	 * Returns a string containing all information about this Employee,
	 * including any subclass-specific details.
	 * 
	 * @see java.lang.Object#toString()
	 * @see #getDetails()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(familyName).append(", ").append(givenName).append(" ");
		builder.append("(").append(employeeType).append(", ");
		builder.append("#").append(employeeNumber).append(", ");
		builder.append("since ").append(startingDate).append(")");
		builder.append(": ").append(getDetails());
		return builder.toString();
	}

	/**
	 * @return the employeeNumber
	 */
	public int getEmployeeNumber() {
		return employeeNumber;
	}

	/**
	 * @return the familyName
	 */
	public String getFamilyName() {
		return familyName;
	}

	/**
	 * @param familyName
	 *            the familyName to set
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	/**
	 * @return the givenName
	 */
	public String getGivenName() {
		return givenName;
	}

	/**
	 * @param givenName
	 *            the givenName to set
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	/**
	 * @return the startingDate
	 */
	public LocalDate getStartingDate() {
		return startingDate;
	}

	/**
	 * @param startingDate
	 *            the startingDate to set
	 */
	public void setStartingDate(LocalDate startingDate) {
		this.startingDate = startingDate;
	}

	/**
	 * Returns any additional details about this Employee for use in toString()
	 * 
	 * @return Additional information (e.g., salary or hourly rate) as a string
	 */
	public abstract String getDetails();

}
