package workers;

import java.time.LocalDate;

/**
 * A salaried employee: i.e., an employee whose pay is determined by an annual salary.
 * 
 * @author mefoster
 *
 */
public class SalariedEmployee extends Employee {

	private double annualSalary;

	/**
	 * @param familyName
	 * @param givenName
	 * @param startingDate
	 * @param annualSalary
	 */
	public SalariedEmployee(String familyName, String givenName, LocalDate startingDate, double annualSalary) {
		super(familyName, givenName, startingDate);
		this.annualSalary = annualSalary;
		this.employeeType = "Salaried";
	}

	/**
	 * @return the annualSalary
	 */
	public double getAnnualSalary() {
		return annualSalary;
	}

	/**
	 * @param annualSalary
	 *            the annualSalary to set
	 */
	public void setAnnualSalary(double annualSalary) {
		this.annualSalary = annualSalary;
	}

	/**
	 * Returns the salary as a string, for use in toString().
	 * 
	 * @see workers.Employee#getDetails()
	 */
	@Override
	public String getDetails() {
		return String.format("Salary: £%.2f", annualSalary);
	}

}
