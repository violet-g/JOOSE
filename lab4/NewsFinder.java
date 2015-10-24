package checknews;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NewsFinder {

	private String url;
	private String content;

	public NewsFinder(String url) {
		this.url = url;
		Scanner s = null;
		try {
			s = new Scanner(new URL(url).openStream());
			while (s.hasNextLine()) {
				content += s.nextLine();
			}
		} catch (MalformedURLException malfEx) {
			System.out.println("MalformedURLException caught.");
		} catch (IOException ioEx) {
			System.out.println("IOException caught.");
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	public boolean isInNews(Object o) {
		String countryName = o.toString();
		return content.contains(countryName);
	}
}