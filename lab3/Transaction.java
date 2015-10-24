package creditcard2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Transaction {

	private double amount;
	private LocalDateTime timestamp;

	public double getAmount() {
		return this.amount;
	}

	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}

	public Transaction(double amount) {
		this.amount = amount;
		this.timestamp = LocalDateTime.now();
	}

	public abstract boolean apply(CreditCard c);

	protected String formatAmount() {
		return CreditCard.POUNDS + String.format("%1.2f", getAmount());
	}

	protected String formatTimestamp() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
		return getTimestamp().format(format);
	}

}
