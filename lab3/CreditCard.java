package creditcard2;

/**
 * A sample implementation of the CreditCard class, with the addition of
 * Exceptions on the charge(), makePayment(), and setCreditLimit() classes.
 * 
 * @author Mary Ellen Foster 2015-10-17
 */
public class CreditCard {

	/**
	 * A static field to ensure unique card numbers.
	 */
	private static int maxCardNumber = 1;

	/**
	 * Private fields
	 */
	private int cardNumber;
	private String cardHolder;
	private double currentBalance;
	private double creditLimit;

	/**
	 * The default credit limit if none is specified
	 */
	private static final int DEFAULT_LIMIT = 500;

	/**
	 * Creates a credit card with unspecified card holder and default credit
	 * limit.
	 */
	public CreditCard() {
		this("", DEFAULT_LIMIT);
	}

	/**
	 * Creates a credit card with the specified card holder and credit limit. If
	 * the limit is invalid, the default limit is used.
	 * 
	 * @param cardHolder
	 * @param creditLimit
	 */
	public CreditCard(String cardHolder, double creditLimit) {
		if (creditLimit <= 0) {
			System.err.println("Invalid credit limit specified; using default");
			creditLimit = DEFAULT_LIMIT;
		}
		this.cardHolder = cardHolder;
		this.creditLimit = creditLimit;
		this.currentBalance = 0;
		cardNumber = maxCardNumber++;
	}

	/**
	 * Attempts to make a charge to the credit card.
	 * 
	 * @param amount
	 *            The amount to charge
	 * @return true if the card still has enough room to make the charge, and
	 *         false if not
	 * @throws Exception
	 *             if the amount to charge is invalid (i.e., not positive)
	 */
	public boolean charge(double amount) throws Exception {
		if (amount <= 0) {
			throw new Exception("Invalid charge amount: " + amount);
		}
		if ((amount + currentBalance) <= creditLimit) {
			currentBalance += amount;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Attempts to make a payment to the credit card.
	 * 
	 * @param amount
	 *            The amount to pay
	 * @return true if the payment amount is less than or equal to the current
	 *         balance, and false if it is greater than it (i.e., no
	 *         overpayments possible)
	 * @throws Exception
	 *             if the amount to pay is invalid (i.e., not positive)
	 */
	public boolean makePayment(double amount) throws Exception {
		if (amount <= 0) {
			throw new Exception("Invalid payment amount: " + amount);
		}
		if (amount <= currentBalance) {
			currentBalance -= amount;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the name of the card holder
	 */
	public String getCardHolder() {
		return cardHolder;
	}

	/**
	 * Sets a new card holder name
	 * 
	 * @param cardHolder
	 *            the new name
	 */
	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	/**
	 * @return the current credit limit
	 */
	public double getCreditLimit() {
		return creditLimit;
	}

	/**
	 * Sets the new credit limit
	 * 
	 * @param creditLimit
	 *            the new limit to set
	 * @return true if the attempt is successful (i.e., the new limit is not
	 *         less than the current balance) and false if it is not
	 * @throws Exception
	 *             if the new credit limit is invalid (i.e., not positive)
	 */
	public boolean setCreditLimit(double creditLimit) throws Exception {
		if (creditLimit <= 0) {
			throw new Exception("Invalid credit limit: " + creditLimit);
		}
		if (creditLimit < currentBalance) {
			return false;
		} else {
			this.creditLimit = creditLimit;
			return true;
		}
	}

	/**
	 * @return the card number
	 */
	public int getCardNumber() {
		return cardNumber;
	}

	/**
	 * @return the current balance
	 */
	public double getCurrentBalance() {
		return currentBalance;
	}

	/**
	 * A constant representing the pound sign
	 */
	public static final String POUNDS = java.util.Currency.getInstance("GBP").getSymbol(java.util.Locale.UK);

	/**
	 * Returns a string containing all of the details of this card.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String balanceStr = String.format(POUNDS + "%.2f", currentBalance);
		String limitStr = String.format(POUNDS + "%.2f", creditLimit);
		return "Card #" + cardNumber + ", holder '" + cardHolder + "', balance: " + balanceStr + " limit: " + limitStr;
	}

	/**
	 * Compares two credit cards for equality -- they are equal only if they
	 * have the same card number.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o instanceof CreditCard) {
			return ((CreditCard) o).cardNumber == this.cardNumber;
		} else {
			return false;
		}
	}
}
