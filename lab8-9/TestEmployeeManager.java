/**
 * 
 */
package test;

import java.awt.Window;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTextField;

import org.jdatepicker.JDatePicker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import employeemanager.AddEmployeeDialog;
import employeemanager.EditEmployeeDialog;
import employeemanager.EmployeeFrame;
import workers.Employee;
import workers.HourlyEmployee;
import workers.SalariedEmployee;

/**
 * @author mefoster
 *
 */
public class TestEmployeeManager {

	private EmployeeFrame frame;

	private Employee addEmployee(String employeeType, String familyName, String givenName, LocalDate startingDate,
			Number... details) {
		Employee newEmployee = null;
		switch (employeeType) {
		case "Salaried":
			newEmployee = new SalariedEmployee(familyName, givenName, startingDate, (Double) details[0]);
			break;
		case "Hourly":
			newEmployee = new HourlyEmployee(familyName, givenName, startingDate, (Double) details[0],
					(Integer) details[1]);
			break;
		}
		if (newEmployee != null) {
			frame.getListModel().addElement(newEmployee);
		}
		return newEmployee;
	}

	private static JComponent getPrivateFieldValue(Window parent, String fieldName)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = parent.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return (JComponent) field.get(parent);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		frame = new EmployeeFrame();
	}

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	/**
	 * Test method for
	 * {@link employeemanager.EmployeeFrame#actionPerformed(java.awt.event.ActionEvent)}
	 * .
	 */
	@Test
	public void testDeleteEmptyList() {
		// Press delete button with empty list -- should do nothing
		try {
			JButton deleteButton = (JButton) getPrivateFieldValue(frame, "deleteButton");
			deleteButton.doClick();
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			Assert.fail("Couldn't access delete button");
		}
	}

	@Test
	public void testDeleteNonEmptyList() {
		addEmployee("Salaried", "Schmoe", "Joe", LocalDate.now(), 10000.0);
		Assert.assertTrue(frame.getListModel().size() == 1);

		try {
			JList employeeList = (JList) getPrivateFieldValue(frame, "employeeList");
			employeeList.setSelectedIndex(0);
			JButton deleteButton = (JButton) getPrivateFieldValue(frame, "deleteButton");
			deleteButton.doClick();
			Assert.assertTrue("Delete does not remove employee", frame.getListModel().isEmpty());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("Couldn't access delete button or employee list");
		}

	}

	@Test
	public void testWriteFile() {
		System.setProperty("user.dir", tempFolder.getRoot().getAbsolutePath());
		Employee emp = addEmployee("Salaried", "Schmoe", "Joe", LocalDate.now(), 10000.0);
		Path saveFile = Paths.get(EmployeeFrame.SAVE_FILE);
		try {
			JButton saveButton = (JButton) getPrivateFieldValue(frame, "saveButton");
			saveButton.doClick();
			Assert.assertTrue("Save file does not exist", Files.exists(saveFile));
			List<String> lines = Files.readAllLines(saveFile);
			Assert.assertTrue("Save file has wrong number of lines", lines.size() == 1);
			Assert.assertTrue("Save file has wrong contents", lines.get(0).equals(emp.toString()));
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("Couldn't access save button");
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Save button threw an I/O exception");
		}
	}

	@Test
	public void testAddEmployeeDialog() {
		AddEmployeeDialog dlg = new AddEmployeeDialog(frame);
		try {
			JButton cancelButton = (JButton) getPrivateFieldValue(dlg, "cancelButton");
			cancelButton.doClick();
			Assert.assertTrue("Too many employees after cancelled Add", frame.getListModel().isEmpty());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("Couldn't access AddEmployeeDialog cancel button");
		}
	}

	@Test
	public void testAddEmployeeDialog2() {
		AddEmployeeDialog dlg = new AddEmployeeDialog(frame);
		try {
			// Access the fields
			JComboBox<String> workerType = (JComboBox<String>) getPrivateFieldValue(dlg, "workerType");
			JTextField givenNameField = (JTextField) getPrivateFieldValue(dlg, "givenNameField");
			JTextField familyNameField = (JTextField) getPrivateFieldValue(dlg, "familyNameField");
			JDatePicker startDatePicker = (JDatePicker) getPrivateFieldValue(dlg, "startDatePicker");
			JTextField salaryField = (JTextField) getPrivateFieldValue(dlg, "salaryField");

			// Set their values
			workerType.setSelectedItem("Salaried");
			givenNameField.setText("Joe");
			familyNameField.setText("Schmoe");
			startDatePicker.getModel().setDate(2015, 6, 1);
			salaryField.setText("10000.0");

			// Access and press the OK button
			JButton okButton = (JButton) getPrivateFieldValue(dlg, "okButton");
			okButton.doClick();

			Assert.assertTrue("Wrong number of employees after add dialog", frame.getListModel().size() == 1);
			Employee emp = frame.getListModel().firstElement();
			Assert.assertTrue("Added employee has wrong type", emp instanceof SalariedEmployee);
			SalariedEmployee sEmp = (SalariedEmployee) emp;
			Assert.assertEquals("Incorrect given name", "Joe", sEmp.getGivenName());
			Assert.assertEquals("Incorrect family name", "Schmoe", sEmp.getFamilyName());
			Assert.assertEquals("Incorrect salary", 10000.0, sEmp.getAnnualSalary(), 0.1);

			// Check date
			GregorianCalendar startDateValue = (GregorianCalendar) startDatePicker.getModel().getValue();
			LocalDate startDate = startDateValue.toZonedDateTime().toLocalDate();
			LocalDate expectedDate = LocalDate.of(2015, 7, 1);
			Assert.assertEquals("Incorrect starting date", expectedDate, startDate);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("Couldn't access AddEmployeeDialog fields");
		}
	}

	@Test
	public void testAddEmployeeDialogNonNumeric() {
		AddEmployeeDialog dlg = new AddEmployeeDialog(frame);
		try {
			JComboBox<String> workerType = (JComboBox<String>) getPrivateFieldValue(dlg, "workerType");
			JTextField salaryField = (JTextField) getPrivateFieldValue(dlg, "salaryField");
			workerType.setSelectedItem("Salaried");
			salaryField.setText("La la la");

			// Access and press the OK button
			JButton okButton = (JButton) getPrivateFieldValue(dlg, "okButton");
			okButton.doClick();

			Assert.assertTrue("Employee added despite badly formatted salary", frame.getListModel().isEmpty());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("Exception processing badly formatted salary");
		}
	}

	@Test
	public void testAddEmployeeDialogNonNumeric2() {
		AddEmployeeDialog dlg = new AddEmployeeDialog(frame);
		try {
			JComboBox<String> workerType = (JComboBox<String>) getPrivateFieldValue(dlg, "workerType");
			JTextField rateField = (JTextField) getPrivateFieldValue(dlg, "rateField");
			workerType.setSelectedItem("Hourly");
			rateField.setText("La la la");

			// Access and press the OK button
			JButton okButton = (JButton) getPrivateFieldValue(dlg, "okButton");
			okButton.doClick();

			Assert.assertTrue("Employee added despite badly formatted annual rate", frame.getListModel().isEmpty());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("Exception processing badly formatted annual rate");
		}
	}

	@Test
	public void testAddEmployeeDialogNonNumeric3() {
		AddEmployeeDialog dlg = new AddEmployeeDialog(frame);
		try {
			JComboBox<String> workerType = (JComboBox<String>) getPrivateFieldValue(dlg, "workerType");
			JTextField hoursField = (JTextField) getPrivateFieldValue(dlg, "hoursField");
			workerType.setSelectedItem("Hourly");
			hoursField.setText("La la la");

			// Access and press the OK button
			JButton okButton = (JButton) getPrivateFieldValue(dlg, "okButton");
			okButton.doClick();

			Assert.assertTrue("Employee added despite badly formatted hours", frame.getListModel().isEmpty());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("Exception processing badly formatted hours");
		}
	}

	@Test
	public void testEditEmployeeDialog() {
		Employee employee = addEmployee("Salaried", "Schmoe", "Joe", LocalDate.now(), 10000.0);
		EditEmployeeDialog dlg = new EditEmployeeDialog(frame, employee);
		try {
			JButton cancelButton = (JButton) getPrivateFieldValue(dlg, "cancelButton");
			cancelButton.doClick();
			Employee emp = frame.getListModel().firstElement();

			Assert.assertTrue("Employee has wrong type after cancelled edit", emp instanceof SalariedEmployee);
			SalariedEmployee sEmp = (SalariedEmployee) emp;
			Assert.assertEquals("Incorrect given name after cancelled edit", "Joe", sEmp.getGivenName());
			Assert.assertEquals("Incorrect family name after cancelled edit", "Schmoe", sEmp.getFamilyName());
			Assert.assertEquals("Incorrect salary after cancelled edit", 10000.0, sEmp.getAnnualSalary(), 0.1);
			Assert.assertEquals("Incorrect starting date after cancelled edit", LocalDate.now(),
					sEmp.getStartingDate());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("Couldn't access EditEmployeeDialog cancel button");
		}
	}

	@Test
	public void testEditEmployeeDialog2() {
		Employee employee = addEmployee("Salaried", "Schmoe", "Joe", LocalDate.now(), 10000.0);
		EditEmployeeDialog dlg = new EditEmployeeDialog(frame, employee);
		try {
			JTextField givenNameField = (JTextField) getPrivateFieldValue(dlg, "givenNameField");
			JTextField familyNameField = (JTextField) getPrivateFieldValue(dlg, "familyNameField");
			JDatePicker startDatePicker = (JDatePicker) getPrivateFieldValue(dlg, "startDatePicker");
			JTextField salaryField = (JTextField) getPrivateFieldValue(dlg, "salaryField");

			// Set their values
			givenNameField.setText("John");
			familyNameField.setText("Doe");
			startDatePicker.getModel().setDate(2015, 6, 1);
			salaryField.setText("20000.0");

			JButton okButton = (JButton) getPrivateFieldValue(dlg, "okButton");
			okButton.doClick();

			Employee emp = frame.getListModel().firstElement();
			Assert.assertTrue("Employee has wrong type after edit", emp instanceof SalariedEmployee);
			SalariedEmployee sEmp = (SalariedEmployee) emp;
			Assert.assertEquals("Incorrect given name after edit", "John", sEmp.getGivenName());
			Assert.assertEquals("Incorrect family name after edit", "Doe", sEmp.getFamilyName());
			Assert.assertEquals("Incorrect salary after edit", 20000.0, sEmp.getAnnualSalary(), 0.1);
			Assert.assertEquals("Incorrect starting date after edit", LocalDate.of(2015, 7, 1), sEmp.getStartingDate());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("Couldn't access EditEmployeeDialog OK button");
		}
	}

	@Test
	public void testEditEmployeeDialog3() {
		Employee employee = addEmployee("Hourly", "Schmoe", "Joe", LocalDate.now(), 50.0, 25);
		EditEmployeeDialog dlg = new EditEmployeeDialog(frame, employee);
		try {
			JTextField givenNameField = (JTextField) getPrivateFieldValue(dlg, "givenNameField");
			JTextField familyNameField = (JTextField) getPrivateFieldValue(dlg, "familyNameField");
			JDatePicker startDatePicker = (JDatePicker) getPrivateFieldValue(dlg, "startDatePicker");
			JTextField rateField = (JTextField) getPrivateFieldValue(dlg, "rateField");
			JTextField hoursField = (JTextField) getPrivateFieldValue(dlg, "hoursField");

			// Set their values
			givenNameField.setText("John");
			familyNameField.setText("Doe");
			startDatePicker.getModel().setDate(2015, 6, 1);
			rateField.setText("75.0");
			hoursField.setText("50");

			JButton okButton = (JButton) getPrivateFieldValue(dlg, "okButton");
			okButton.doClick();

			Employee emp = frame.getListModel().firstElement();
			Assert.assertTrue("Employee has wrong type after edit", emp instanceof HourlyEmployee);
			HourlyEmployee hEmp = (HourlyEmployee) emp;
			Assert.assertEquals("Incorrect given name after edit", "John", hEmp.getGivenName());
			Assert.assertEquals("Incorrect family name after edit", "Doe", hEmp.getFamilyName());
			Assert.assertEquals("Incorrect rate after edit", 75.0, hEmp.getHourlyRate(), 0.1);
			Assert.assertEquals("Incorrect hours after edit", 50, hEmp.getContractedHours());
			Assert.assertEquals("Incorrect starting date after edit", LocalDate.of(2015, 7, 1), hEmp.getStartingDate());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("Couldn't access EditEmployeeDialog OK button");
		}
	}

	@Test
	public void testEditEmployeeDialogNonNumeric() {
		Employee employee = addEmployee("Salaried", "Schmoe", "Joe", LocalDate.now(), 10000.0);
		EditEmployeeDialog dlg = new EditEmployeeDialog(frame, employee);
		try {
			JTextField salaryField = (JTextField) getPrivateFieldValue(dlg, "salaryField");
			salaryField.setText("La la la");

			// Access and press the OK button
			JButton okButton = (JButton) getPrivateFieldValue(dlg, "okButton");
			okButton.doClick();

			Assert.assertEquals("Salary changed despite bad formatting",
					((SalariedEmployee) employee).getAnnualSalary(), 10000.0, 1.0);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("Exception processing badly formatted salary");
		}
	}

	@Test
	public void testEditEmployeeDialogNonNumeric2() {
		Employee employee = addEmployee("Hourly", "Schmoe", "Joe", LocalDate.now(), 50.0, 25);
		EditEmployeeDialog dlg = new EditEmployeeDialog(frame, employee);
		try {
			JTextField rateField = (JTextField) getPrivateFieldValue(dlg, "rateField");
			rateField.setText("La la la");

			// Access and press the OK button
			JButton okButton = (JButton) getPrivateFieldValue(dlg, "okButton");
			okButton.doClick();

			Assert.assertEquals("Rate changed despite bad formatting", ((HourlyEmployee) employee).getHourlyRate(),
					50.0, 1.0);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("Exception processing badly formatted hourly rate");
		}
	}

	@Test
	public void testEditEmployeeDialogNonNumeric3() {
		Employee employee = addEmployee("Hourly", "Schmoe", "Joe", LocalDate.now(), 50.0, 25);
		EditEmployeeDialog dlg = new EditEmployeeDialog(frame, employee);
		try {
			JTextField hoursField = (JTextField) getPrivateFieldValue(dlg, "hoursField");
			hoursField.setText("La la la");

			// Access and press the OK button
			JButton okButton = (JButton) getPrivateFieldValue(dlg, "okButton");
			okButton.doClick();

			Assert.assertEquals("Hours changed despite bad formatting",
					((HourlyEmployee) employee).getContractedHours(), 25);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Assert.fail("Exception processing badly formatted hours");
		}
	}

}
