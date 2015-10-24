package creditcard2;

public class Charge extends Transaction {

	public Charge(double amount) {
		super(amount);
	}

	public boolean apply(CreditCard c) {
		try {
			return c.charge(getAmount());
		} catch (Exception ex) {
			return false;
		}
	}

	public String toString() {
		return formatTimestamp() + " CHARGE " + formatAmount();
	}

}
