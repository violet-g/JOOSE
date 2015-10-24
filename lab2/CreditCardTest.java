package creditcard;

import java.lang.reflect.Method;
import java.util.Currency;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * set of JUnit tests for Java lab 2 CreditCard class defined by students.
 * Based extensively on BankAccount tests by Jeremy Singer for last year's class.
 *
 * These tests use reflection to invoke methods on CreditCard - this should prevent
 * Eclipse code completion from automatically creating the empty methods in
 * CreditCard source code.
 *
 * @author MaryEllen.Foster@glasgow.ac.uk
 */
public class CreditCardTest {

	/**
	 * utility CreditCard instance for tests
	 */
	private static CreditCard cc;

	/**
	 * canonical '£' sign for UK currency
	 */
	private static String POUND_SIGN;

	@Before
	public void setup() {
		// set up an empty CreditCard instance
		cc = new CreditCard();
		POUND_SIGN = Currency.getInstance("GBP").getSymbol(Locale.UK);
	}

	@After
	public void teardown() {
		cc = null;
	}

	@Test
	public void testSetAndGetName() throws Exception {
		String name = "Mary Ellen Foster";
		Method setAccountHolderMethod = Class.forName("creditcard.CreditCard").getMethod("setCardHolder", "".getClass());
		Method getAccountHolderMethod = Class.forName("creditcard.CreditCard").getMethod("getCardHolder");
		setAccountHolderMethod.invoke(cc, name);
		Object o = getAccountHolderMethod.invoke(cc);
		Assert.assertTrue("cardHolder getter and setter methods not working properly", o.equals(name));

	}

	@Test
	public void testSetAndPrintName() throws Exception {
		String name = "Mary Ellen Foster";
		Method setAccountHolderMethod = Class.forName("creditcard.CreditCard").getMethod("setCardHolder", "".getClass());
		setAccountHolderMethod.invoke(cc, name);
		Assert.assertTrue("cardHolder not appearing properly in toString() output", cc.toString().contains(name));
	}

	@Test
	public void testDifferentInstances() {
		CreditCard cc2 = new CreditCard();
		Assert.assertFalse("equals() method is faulty (two distinct cards should not be equal)", cc.equals(cc2));
	}

	@Test
	public void testEquals() {
		CreditCard cc2 = new CreditCard();
		Assert.assertTrue("equals() method is faulty (an account should be equal to itself)", cc.equals(cc));
		Assert.assertTrue("equals() method is faulty (an account should be equal to itself)", cc2.equals(cc2));
	}

	@Test
	public void testDifferentAccountNumbers() throws Exception {
		for (int i = 0; i < 100; i++) {
			CreditCard cc2 = new CreditCard();
			Method getAccountNumberMethod = Class.forName("creditcard.CreditCard").getMethod("getCardNumber");
			Assert.assertFalse("no two bank accounts should have the same account number",
					getAccountNumberMethod.invoke(cc).equals(getAccountNumberMethod.invoke(cc2)));
		}
	}

	@Test
	public void testMultipleDifferentCardNumbers() throws Exception {
		int NUM_CARDS = 100;
		Method getAccountNumberMethod = Class.forName("creditcard.CreditCard").getMethod("getCardNumber");
		CreditCard[] cards = new CreditCard[NUM_CARDS];
		for (int i = 0; i < NUM_CARDS; i++) {
			cards[i] = new CreditCard();
			for (int j = 0; j < i; j++) {
				// loop over all previously created accounts
				Assert.assertFalse("no two credit cards should have the same account number",
						getAccountNumberMethod.invoke(cards[i]).equals(getAccountNumberMethod.invoke(cards[j])));
			}
		}
	}

	@Test
	public void testCharge() throws Exception {
		double amount = 1000;
		Method chargeMethod = Class.forName("creditcard.CreditCard").getMethod("charge", double.class);
		Assert.assertFalse("Credit limit check not working properly with default value", ((boolean) (chargeMethod.invoke(cc, amount))));
	}

	@Test
	public void testCharge2() throws Exception {
		double amount = 500;
		Method chargeMethod = Class.forName("creditcard.CreditCard").getMethod("charge", double.class);
		Assert.assertTrue("Credit limit check not working properly with default value", ((boolean) (chargeMethod.invoke(cc, amount))));
	}

	@Test
	public void testCharge3() throws Exception {
		double amount = 400;
		Method chargeMethod = Class.forName("creditcard.CreditCard").getMethod("charge", double.class);
		Assert.assertTrue("Credit limit check not working properly with default value", ((boolean) (chargeMethod.invoke(cc, amount))));
	}
	
	@Test
	public void testInitialLimit() throws Exception {
		Method getCreditLimitMethod = Class.forName("creditcard.CreditCard").getMethod("getCreditLimit");

		Object o = getCreditLimitMethod.invoke(cc);
		Assert.assertEquals("Initial credit limit wrong (or getCreditLimit method wrong)", o, 500.0);
	}

	@Test
	public void testGetAndSetLimit() throws Exception {
		double amount = 400;
		Method setCreditLimitMethod = Class.forName("creditcard.CreditCard").getMethod("setCreditLimit", double.class);
		Method getCreditLimitMethod = Class.forName("creditcard.CreditCard").getMethod("getCreditLimit");
		setCreditLimitMethod.invoke(cc, amount);

		Object o = getCreditLimitMethod.invoke(cc);
		Assert.assertEquals("set/get credit limit do not work properly together", o, amount);
	}

	@Test
	public void testChangeLimit() throws Exception {
		double amount = 400;
		double amount2 = 1000;
		Method chargeMethod = Class.forName("creditcard.CreditCard").getMethod("charge", double.class);
		chargeMethod.invoke(cc, amount);

		Method setCreditLimitMethod = Class.forName("creditcard.CreditCard").getMethod("setCreditLimit", double.class);
		Assert.assertTrue((boolean) (setCreditLimitMethod.invoke(cc, amount2)));
		Assert.assertTrue("unable to adjust creditLimit properly", (boolean) (chargeMethod.invoke(cc, amount)));
	}

	@Test
	public void testChangeLimit2() throws Exception {
		double amount = 400;
		Method chargeMethod = Class.forName("creditcard.CreditCard").getMethod("charge", double.class);
		chargeMethod.invoke(cc, amount);

		Method setCreditLimitMethod = Class.forName("creditcard.CreditCard").getMethod("setCreditLimit", double.class);
		Assert.assertTrue("Should be able to set limit to your exact balance", (boolean) (setCreditLimitMethod.invoke(cc, amount)));
	}
	
	
	@Test
	public void testChargePayCharge() throws Exception {
		double amount = 400;

		Method chargeMethod = Class.forName("creditcard.CreditCard").getMethod("charge", double.class);
		Assert.assertTrue ("Charge didn't work", (boolean)chargeMethod.invoke(cc, amount));
		
		Method makePaymentMethod = Class.forName("creditcard.CreditCard").getMethod("makePayment", double.class);
		Assert.assertTrue("Payment didn't work", (boolean)makePaymentMethod.invoke(cc, amount));
		
		Assert.assertTrue ("Charge didn't work", (boolean)chargeMethod.invoke(cc, amount));
	}
	
	@Test
	public void testPayTooMuch() throws Exception {
		double amount = 400;

		Method makePaymentMethod = Class.forName("creditcard.CreditCard").getMethod("makePayment", double.class);
		Assert.assertFalse("You can't pay more than your balance", (boolean)makePaymentMethod.invoke(cc, amount));
	}
	
	@Test
	public void testNegativeAmounts() throws Exception {
		double amount = -400;

		Method chargeMethod = Class.forName("creditcard.CreditCard").getMethod("charge", double.class);
		Assert.assertFalse ("You can't charge a negative amount", (boolean)chargeMethod.invoke(cc, amount));
		
		Method makePaymentMethod = Class.forName("creditcard.CreditCard").getMethod("makePayment", double.class);
		Assert.assertFalse("You can't pay a negative amount", (boolean)makePaymentMethod.invoke(cc, amount));
	}
	
	@Test
	public void testZeroAmounts() throws Exception {
		double amount = 0;

		Method chargeMethod = Class.forName("creditcard.CreditCard").getMethod("charge", double.class);
		Assert.assertFalse ("You can't charge zero", (boolean)chargeMethod.invoke(cc, amount));
		
		Method makePaymentMethod = Class.forName("creditcard.CreditCard").getMethod("makePayment", double.class);
		Assert.assertFalse("You can't pay zero", (boolean)makePaymentMethod.invoke(cc, amount));
	}
	
	// we want toString() to have neatly formatted balances
	// using '£' character
	@Test
	public void testToString() throws Exception {
		double amount1 = 42.0;
		double amount2 = 0.01;
		Method chargeMethod = Class.forName("creditcard.CreditCard").getMethod("charge", double.class);
		Method makePaymentMethod = Class.forName("creditcard.CreditCard").getMethod("makePayment", double.class);

		chargeMethod.invoke(cc, amount1);
		Assert.assertTrue("toString() does not format balance correctly",
				cc.toString().contains(POUND_SIGN + "42.00"));

		chargeMethod.invoke(cc, amount2);
		Assert.assertTrue("toString() does not format balance correctly",
				cc.toString().contains(POUND_SIGN + "42.01"));

		makePaymentMethod.invoke(cc, amount2);
		makePaymentMethod.invoke(cc, amount2);
		Assert.assertTrue("toString() does not format balance correctly",
				cc.toString().contains(POUND_SIGN + "41.99"));
	}
}
