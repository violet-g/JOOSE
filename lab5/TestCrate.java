package fillingcrates;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * set of JUnit tests for Java lab 5 Crate class to be defined by students
 *
 * @author mefoster (based on a class by jsinger)
 */
public class TestCrate {

	private static Crate crate;

	@Before
	public void setup() {
	}

	@After
	public void teardown() {
		crate = null;
	}

	/**
	 * check new crate is initially empty
	 */
	@Test
	public void testNewCrateIsEmpty() {
		crate = new Crate(10);
		assertTrue("new crate should be empty", crate.isEmpty());
		assertTrue("new crate should be empty", crate.getUsedCapacity() == 0);
	}

	/**
	 * check crate capacity is correctly initialized
	 */
	@Test
	public void testEmptyCrateCapacity() {
		for (int i = 0; i < 100; i++) {
			crate = new Crate(i);
			assertTrue("crate capacity not correct", crate.getTotalCapacity() == i);
		}
	}

	/**
	 * check crate capacity and availability for empty crate
	 */
	@Test
	public void testEmptyCrateCapacityAndAvailability() {
		for (int i = 0; i < 100; i++) {
			crate = new Crate(i);
			assertTrue("crate capacity not correct", crate.getTotalCapacity() == crate.getAvailableCapacity());
		}
	}

	/**
	 * check crate storage
	 */
	@Test
	public void testCrateStorageCapacity() throws InsufficientCapacityException {
		crate = new Crate(100);
		for (int i = 0; i < 10; i++) {
			crate.store(10);
			assertTrue("crate storage capacity not correct", crate.getUsedCapacity() == 10 * (i + 1));
			assertTrue("crate capacity not updated properly",
					crate.getUsedCapacity() + crate.getAvailableCapacity() == crate.getTotalCapacity());
		}
	}

	/**
	 * check crate storage when exactly the right space is used
	 */
	@Test
	public void testCrateFullStorage() throws InsufficientCapacityException {
		crate = new Crate(100);
		crate.store(100);
		assertTrue("crate should be full now", crate.getAvailableCapacity() == 0);
		assertTrue("crate should be full now", crate.getUsedCapacity() == crate.getTotalCapacity());
	}

	/**
	 * check crate storage when not enough space
	 */
	@Test(expected = InsufficientCapacityException.class)
	public void testCrateInsufficentStorage() throws InsufficientCapacityException {
		crate = new Crate(100);
		crate.store(110);
	}
}