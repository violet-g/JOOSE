package creditcard2;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * set of JUnit tests for Java lab 3 CreditCard class defined by students
 * 
 * @author Mary Ellen Foster, based on test cases by Jeremy Singer
 */
public class TestCreditCard2 {

	private static CreditCard card;
	private Method applyMethod;
	private Method getTimestampMethod;

	@Before
	public void setup() {
		// set up an empty BankAccount instance
		card = new CreditCard("Fred Bloggs, Esquire", 500.0);

		// init methods for reflective calls
		try {
			applyMethod = Class.forName("creditcard2.Transaction").getMethod("apply", card.getClass());
			getTimestampMethod = Class.forName("creditcard2.Transaction").getMethod("getTimestamp");
		} catch (ReflectiveOperationException e) {
			applyMethod = null;
			getTimestampMethod = null;
		}
	}

	@After
	public void teardown() {
		card = null;
	}

	/**
	 * check timestamping for transactions is working ok
	 */
	@Test
	public void timeStampTest() throws Exception {
		LocalDateTime now = LocalDateTime.now();
		Charge c = new Charge(100.0);
		MakePayment p = new MakePayment(100.0);

		// Make sure that the date is close to the same
		LocalDateTime cDate = (LocalDateTime) (getTimestampMethod.invoke(c));
		LocalDateTime pDate = (LocalDateTime) (getTimestampMethod.invoke(p));

		long cDiff = now.until(cDate, ChronoUnit.MILLIS);
		long pDiff = now.until(pDate, ChronoUnit.MILLIS);

		Assert.assertTrue("Newly created Charge timestamp more than 1s from current time", Math.abs(cDiff) < 1000);
		Assert.assertTrue("Newly created MakePayment timestamp more than 1s from current time", Math.abs(pDiff) < 1000);
	}


	@Test
	public void testCharge() throws Exception {
		double amount = 1000;
		Charge c = new Charge (amount);
		
		Boolean result = (Boolean)(applyMethod.invoke(c, card));
		Assert.assertFalse("Credit limit check not working properly with default value", result);
	}

	@Test
	public void testCharge2() throws Exception {
		double amount = 500;
		Charge c = new Charge (amount);
		
		Boolean result = (Boolean)(applyMethod.invoke(c, card));
		Assert.assertTrue("Credit limit check not working properly with default value", result);
	}

	@Test
	public void testCharge3() throws Exception {
		double amount = 400;
		Charge c = new Charge (amount);
		
		Boolean result = (Boolean)(applyMethod.invoke(c, card));
		Assert.assertTrue("Credit limit check not working properly with default value", result);
	}
	
	@Test
	public void testChargePayCharge() throws Exception {
		double amount = 400;
		Charge c = new Charge (amount);
		MakePayment p = new MakePayment (amount);

		Assert.assertTrue ("Charge didn't work", (boolean)applyMethod.invoke(c, card));
		
		Assert.assertTrue("Payment didn't work", (boolean)applyMethod.invoke(p, card));
		
		Assert.assertTrue ("Charge didn't work", (boolean)applyMethod.invoke(c, card));
	}
	
	@Test
	public void testPayTooMuch() throws Exception {
		double amount = 400;
		MakePayment p = new MakePayment (amount);

		Assert.assertFalse("You can't pay more than your balance", (boolean)applyMethod.invoke(p, card));
	}
	
	@Test
	public void testNegativeAmounts() throws Exception {
		double amount = -400;
		Charge c = new Charge (amount);
		MakePayment p = new MakePayment (amount);

		Assert.assertFalse ("You can't charge a negative amount", (boolean)applyMethod.invoke(c, card));
		
		Assert.assertFalse("You can't pay a negative amount", (boolean)applyMethod.invoke(p, card));
	}
	
	@Test
	public void testZeroAmounts() throws Exception {
		double amount = 0;
		Charge c = new Charge (amount);
		MakePayment p = new MakePayment (amount);

		Assert.assertFalse ("You can't charge a negative amount", (boolean)applyMethod.invoke(c, card));
		
		Assert.assertFalse("You can't pay a negative amount", (boolean)applyMethod.invoke(p, card));
	}
	

	/**
	 * check toString is formatted properly, with timestamp
	 */
	@Test
	public void testChargeToString() {
		double amount = 42.0;
		Charge c = new Charge(amount);
		LocalDateTime now = LocalDateTime.now();
		String yearStr = DateTimeFormatter.ISO_LOCAL_DATE.format(now);
		assertTrue("charge toString does not contain amount", c.toString().contains(String.valueOf(amount)));
		assertTrue("charge toString does not contain year",
				c.toString().contains(yearStr));
		assertTrue("charge toString does not contain CHARGE", c.toString().contains("CHARGE"));
	}

	/**
	 * check toString is formatted properly, with timestamp
	 */
	@Test
	public void testPaymentToString() {
		double amount = 42.0;
		MakePayment p = new MakePayment(amount);
		LocalDateTime now = LocalDateTime.now();
		String yearStr = DateTimeFormatter.ISO_LOCAL_DATE.format(now);
		assertTrue("payment toString does not contain amount", p.toString().contains(String.valueOf(amount)));
		assertTrue("payment toString does not contain year",
				p.toString().contains(yearStr));
		assertTrue("payment toString does not contain PAYMENT", p.toString().contains("PAYMENT"));
	}

}

