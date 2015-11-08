package workers;

import java.time.LocalDate;

public class SalariedEmployee extends Employee {

	private double annualSalary;

	public SalariedEmployee(String familyName, String givenName, LocalDate startingDate, double annualSalary) {
		super(familyName, givenName, startingDate, "salaried");
		this.annualSalary = annualSalary;
	}

	@Override
	public double calculateMonthlyWage() {
		return annualSalary / 12;
	}

}
