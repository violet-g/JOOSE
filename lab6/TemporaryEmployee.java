package workers;

import java.time.LocalDate;

public class TemporaryEmployee extends HourlyEmployee {

	public TemporaryEmployee(String familyName, String givenName, LocalDate startingDate, double hourlyRate,
			int contractedHours) {
		super(familyName, givenName, startingDate, hourlyRate, contractedHours, "temporary");
	}

	@Override
	public double calculateMonthlyWage() {
		return getWorkedHours() * getHourlyRate();
	}

}
