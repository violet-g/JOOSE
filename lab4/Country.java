package checknews;

import java.util.ArrayList;
import java.util.Locale;

public class Country {

	private String name;

	public Country(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public boolean equals(Object other) {
		Country country = (Country) other;
		return (country.toString().equals(this.name));
	}

	public static ArrayList<Country> getCountries() {
		ArrayList<Country> countries = new ArrayList<Country>();
		Locale[] locales = Locale.getAvailableLocales();
		for (Locale l : locales) {
			String countryName = l.getDisplayCountry(Locale.UK);
			Country nextCountry = new Country(countryName);
			if (!countryName.isEmpty() && !countries.contains(nextCountry)) {
				countries.add(nextCountry);
			}
		}
		return countries;
	}
}