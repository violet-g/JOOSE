package creditcard;

import java.util.Currency;

public class CreditCard {

	private static int nextCardNumber = 0;
	private int cardNumber;
	private String cardHolder;
	private double currentBalance;
	private double creditLimit;

	public int getCardNumber() {
		return this.cardNumber;
	}

	public String getCardHolder() {
		return this.cardHolder;
	}

	public double getCurrentBalance() {
		return this.currentBalance;
	}

	public double getCreditLimit() {
		return this.creditLimit;
	}

	public void setCardHolder(String accountHolder) {
		this.cardHolder = accountHolder;
	}

	public boolean setCreditLimit(double creditLimit) {
		if (creditLimit > 0 && creditLimit >= currentBalance) {
			this.creditLimit = creditLimit;
			return true;
		}
		return false;
	}

	public CreditCard() {
		this("", 500.0);
	}

	public CreditCard(String accountHolder, double creditLimit) {
		cardNumber = nextCardNumber++;
		currentBalance = 0.0;
		this.cardHolder = accountHolder;
		this.creditLimit = creditLimit;
	}

	public boolean charge(double amount) {
		if (amount <= creditLimit && amount > 0) {
			currentBalance += amount;
			return true;
		}
		return false;
	}

	public boolean makePayment(double amount) {
		if (amount <= currentBalance && amount > 0) {
			currentBalance -= amount;
			return true;
		}
		return false;
	}

	public String toString() {
		return cardNumber + ", " + cardHolder + ", " + format(currentBalance) + ", " + format(creditLimit);
	}

	private String format(double num) {
		return Currency.getInstance("GBP").getSymbol(java.util.Locale.UK) + String.format("%1.2f", num);
	}

	public boolean equals(Object obj) {
		CreditCard cc = (CreditCard) obj;
		return cc.cardNumber == cardNumber;
	}

}
