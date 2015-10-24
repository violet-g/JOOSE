package creditcard2;

public class MakePayment extends Transaction {

	public MakePayment(double amount) {
		super(amount);
	}

	public boolean apply(CreditCard c) {
		try {
			return c.makePayment(getAmount());
		} catch (Exception ex) {
			return false;
		}
	}

	public String toString() {
		return formatTimestamp() + " PAYMENT " + formatAmount();
	}

}
