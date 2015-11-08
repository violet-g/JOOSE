package workers;

import java.time.LocalDate;

public abstract class HourlyEmployee extends Employee {

	private double hourlyRate;
	private int contractedHours;
	private int workedHours;

	public HourlyEmployee(String familyName, String givenName, LocalDate startingDate, double hourlyRate,
			int contractedHours, String type) {
		super(familyName, givenName, startingDate, "hourly " + type);
		this.hourlyRate = hourlyRate;
		this.contractedHours = contractedHours;
	}

	public double getHourlyRate() {
		return hourlyRate;
	}

	public int getContractedHours() {
		return contractedHours;
	}

	public int getWorkedHours() {
		return workedHours;
	}

	public void setHoursThisMonth(int workedHours) {
		this.workedHours = workedHours;
	}
}
