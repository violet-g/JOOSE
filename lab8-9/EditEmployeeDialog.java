package employeemanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.GregorianCalendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;

import workers.Employee;
import workers.HourlyEmployee;
import workers.SalariedEmployee;

/**
 * A dialog box that allows a user to edit the details of an existing Employee.
 * If the user clicks the "OK" button, the edits are made; if they click
 * "Cancel" (or close the window) then no changes are made.
 * 
 * @author Gabriela Georgieva, 2130120G; starter code - mefoster
 */

@SuppressWarnings("serial")
public class EditEmployeeDialog extends JDialog implements ActionListener {

	// GUI fields
	private JTextField givenNameField;
	private JTextField familyNameField;
	private JDatePicker startDatePicker;
	private JTextField rateField;
	private JTextField hoursField;
	private JTextField salaryField;

	// Buttons
	private JButton okButton;
	private JButton cancelButton;

	// The employee that we are editing
	private Employee employee;
	// The top-level employee frame
	private EmployeeFrame employeeFrame;

	/**
	 * Sets up the user interface, with initial values derived from the given
	 * Employee. All employee types have fields for given and family names and
	 * start date; salaried employees also have a field to edit their salary,
	 * while hourly employees have a field for their rate and contracted hours.
	 * 
	 * If the user presses "OK", then the edits made in the fields are applied
	 * to the given employee; if they press "Cancel" or close the window, the
	 * employee record is not modified.
	 * 
	 * @param employeeFrame
	 *            The top-level employee frame -- used to position the dialog
	 *            box
	 * @param employee
	 *            The employee to edit
	 */
	public EditEmployeeDialog(EmployeeFrame employeeFrame, Employee employee) {

		super(employeeFrame, "Edit Employee #" + employee.getEmployeeNumber(), true);
		setLocationRelativeTo(employeeFrame);
		this.employee = employee;
		this.employeeFrame = employeeFrame;

		// Name fields -- include initial values
		givenNameField = new JTextField(employee.getGivenName(), 20);
		familyNameField = new JTextField(employee.getFamilyName(), 20);

		// Date field -- include initial value
		startDatePicker = new JDateComponentFactory().createJDatePicker();
		LocalDate date = employee.getStartingDate();
		startDatePicker.getModel().setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());

		// Lay out the common fields
		Box workerBox = Box.createHorizontalBox();
		workerBox.add(new JLabel("Start date"));
		workerBox.add((JPanel) startDatePicker);

		Box givenNameBox = Box.createHorizontalBox();
		givenNameBox.add(new JLabel("Given name "));
		givenNameBox.add(givenNameField);

		Box familyNameBox = Box.createHorizontalBox();
		familyNameBox.add(new JLabel("Family name"));
		familyNameBox.add(familyNameField);

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(workerBox);
		getContentPane().add(givenNameBox);
		getContentPane().add(familyNameBox);

		if (employee instanceof HourlyEmployee) {

			// Fields only for hourly workers -- including initial values
			HourlyEmployee hourly = (HourlyEmployee) employee;
			rateField = new JTextField(10);
			rateField.setText(String.valueOf(hourly.getHourlyRate()));
			hoursField = new JTextField(5);
			hoursField.setText(String.valueOf(hourly.getContractedHours()));

			// Lay them out
			Box hourlyBox = Box.createHorizontalBox();
			hourlyBox.setBorder(new TitledBorder("Hourly worker information"));
			hourlyBox.add(new JLabel("Rate"));
			hourlyBox.add(rateField);
			hourlyBox.add(Box.createHorizontalStrut(10));
			hourlyBox.add(new JLabel("Hours"));
			hourlyBox.add(hoursField);

			getContentPane().add(hourlyBox);

		} else if (employee instanceof SalariedEmployee) {
			// Field only for salaried workers -- including initial value
			SalariedEmployee salaried = (SalariedEmployee) employee;
			salaryField = new JTextField(20);
			salaryField.setText(String.valueOf(salaried.getAnnualSalary()));

			// Lay it out
			Box salariedBox = Box.createHorizontalBox();
			salariedBox.setBorder(new TitledBorder("Salaried worker information"));
			salariedBox.add(new JLabel("Salary"));
			salariedBox.add(salaryField);

			getContentPane().add(salariedBox);
		}

		// Buttons at the bottom
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);

		Box bottomBox = Box.createHorizontalBox();
		bottomBox.add(Box.createHorizontalGlue());
		bottomBox.add(okButton);
		bottomBox.add(Box.createHorizontalGlue());
		bottomBox.add(cancelButton);
		bottomBox.add(Box.createHorizontalGlue());

		getContentPane().add(bottomBox);

		pack();
	}

	/**
	 * Responds to a click on either of the buttons in the dialog box. If "OK"
	 * is clicked, the edits made in the dialog box are applied to the employee
	 * records; if "Cancel" is pressed, they are discarded.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Get the LocalDate value from the date picker
		GregorianCalendar startDateValue = (GregorianCalendar) startDatePicker.getModel().getValue();
		LocalDate startingDate = startDateValue.toZonedDateTime().toLocalDate();

		// Determine which button was clicked
		Object source = e.getSource();

		// Cancel button is pressed
		if (source == cancelButton) {
			// Just close the window
			dispose();
		}

		// OK button is pressed
		if (source == okButton) {

			/**
			 * a new try and catch block is necessary for each entry -- if one
			 * has been given invalid input, its value will not be changed, but
			 * the values of the others will be (supposing they are valid)
			 */

			// if the employee is of salaried type
			if (employee instanceof SalariedEmployee) {
				try {
					double salary = Double.parseDouble(salaryField.getText());

					// update the salary
					((SalariedEmployee) employee).setAnnualSalary(salary);

				} catch (Exception ex) {
					System.out.println("Invalid input");
				}

				// if the employee is of hourly type
			} else {
				try {
					double rate = Double.parseDouble(rateField.getText());

					// update the hourly rate
					((HourlyEmployee) employee).setHourlyRate(rate);

				} catch (Exception ex) {
					System.out.println("Invalid input.");
				}
				try {
					int hours = Integer.parseInt(hoursField.getText());

					// update the contracted hours
					((HourlyEmployee) employee).setContractedHours(hours);

				} catch (Exception ex) {
					System.out.println("Invalid input.");
				}
			}

			// update the common fields
			employee.setFamilyName(familyNameField.getText());
			employee.setGivenName(givenNameField.getText());
			employee.setStartingDate(startingDate);

			// update the contents on the screen
			employeeFrame.repaint();

			// close the window
			dispose();
		}
	}
}
