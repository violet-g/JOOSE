package workers;

import java.time.LocalDate;

public abstract class Employee implements Waged {

	private int employeeNumber;
	private String familyName;
	private String[] givenNames;
	private LocalDate startingDate;
	private String type;

	public static int nextEmployee = 1;

	public Employee(String familyName, String[] givenNames, LocalDate startingDate, String type) {
		this.familyName = familyName;
		this.givenNames = givenNames;
		this.startingDate = startingDate;
		this.employeeNumber = nextEmployee++;
		this.type = type;
	}

	public Employee(String familyName, String givenName, LocalDate startingDate, String type) {
		this(familyName, new String[] { givenName }, startingDate, type);
	}

	private String givenNamesToString() {
		String names = "";
		for (String i : givenNames) {
			names += i + ", ";
		}
		return names.substring(0, names.length() - 2);
	}

	@Override
	public String toString() {
		return familyName + ", " + givenNamesToString() + " (" + type + ", " + "#" + employeeNumber + ", since "
				+ startingDate + ")";
	}
}
