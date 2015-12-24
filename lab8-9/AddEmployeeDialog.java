package employeemanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.GregorianCalendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
 * A dialog box that allows a user to specify the details of a new Employee. If
 * the user clicks the "OK" button, the new employee is added to the list; if
 * they click "Cancel" (or close the window) then no employee is added.
 * 
 * @author Gabriela Georgieva, 2130120G; starter code - mefoster
 *
 */

@SuppressWarnings("serial")
public class AddEmployeeDialog extends JDialog implements ActionListener {

	// Common fields
	private JComboBox<String> workerType;
	private JTextField givenNameField;
	private JTextField familyNameField;
	private JDatePicker startDatePicker;

	// Fields that depend on the employee type
	private JTextField rateField;
	private JTextField hoursField;
	private JTextField salaryField;

	// Buttons
	private JButton okButton;
	private JButton cancelButton;

	// The employee frame -- used to position the dialog and to access the
	// employee list
	private EmployeeFrame employeeFrame;

	/**
	 * Creates a new AddEmployeeDialog as a child of the given EmployeeFrame.
	 * 
	 * @param frame
	 *            The parent EmployeeFrame -- used to position the dialog and to
	 *            access the list of employees
	 * @see EmployeeFrame#getListModel()
	 */
	public AddEmployeeDialog(final EmployeeFrame frame) {

		// Basic initialisation
		super(frame, "Add Employee", true);
		setLocationRelativeTo(employeeFrame);
		this.employeeFrame = frame;

		// Common fields
		workerType = new JComboBox<String>(Employee.getEmployeeTypes());
		givenNameField = new JTextField(20);
		familyNameField = new JTextField(20);
		startDatePicker = new JDateComponentFactory().createJDatePicker();

		// Fields only for hourly workers
		rateField = new JTextField(10);
		hoursField = new JTextField(5);

		// Field only for salaried workers
		salaryField = new JTextField(10);

		// Top line
		Box workerBox = Box.createHorizontalBox();
		workerBox.add(new JLabel("Worker type"));
		workerBox.add(workerType);
		workerBox.add(new JLabel("Start date"));
		workerBox.add((JPanel) startDatePicker);

		// Next lines (names)
		Box givenNameBox = Box.createHorizontalBox();
		givenNameBox.add(new JLabel("Given name "));
		givenNameBox.add(givenNameField);

		Box familyNameBox = Box.createHorizontalBox();
		familyNameBox.add(new JLabel("Family name"));
		familyNameBox.add(familyNameField);

		// Hourly-worker fields
		Box hourlyBox = Box.createHorizontalBox();
		hourlyBox.setBorder(new TitledBorder("Hourly worker information"));
		hourlyBox.add(new JLabel("Rate"));
		hourlyBox.add(rateField);
		hourlyBox.add(Box.createHorizontalStrut(10));
		hourlyBox.add(new JLabel("Hours"));
		hourlyBox.add(hoursField);

		// Salaried-worker fields
		Box salariedBox = Box.createHorizontalBox();
		salariedBox.setBorder(new TitledBorder("Salaried worker information"));
		salariedBox.add(new JLabel("Salary"));
		salariedBox.add(salaryField);

		// Ensure that only the appropriate fields are enabled, depending on the
		// worker type chosen
		workerType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				String type = (String) workerType.getSelectedItem();
				salaryField.setEnabled("Salaried".equals(type));
				rateField.setEnabled("Hourly".equals(type));
				hoursField.setEnabled("Hourly".equals(type));
			}
		});

		workerType.setSelectedItem(null);

		// Create buttons and add the current class as an ActionListener
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);

		// Bottom row of GUI: buttons
		Box bottomBox = Box.createHorizontalBox();
		bottomBox.add(Box.createHorizontalGlue());
		bottomBox.add(okButton);
		bottomBox.add(Box.createHorizontalGlue());
		bottomBox.add(cancelButton);
		bottomBox.add(Box.createHorizontalGlue());

		// Lay out the GUI
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(workerBox);
		getContentPane().add(givenNameBox);
		getContentPane().add(familyNameBox);
		getContentPane().add(hourlyBox);
		getContentPane().add(salariedBox);
		getContentPane().add(Box.createVerticalStrut(10));
		getContentPane().add(bottomBox);
		pack();
	}

	/**
	 * Responds to a click on either of the buttons on the dialog. If the OK
	 * button is pressed, a new employee is added to the list; if the Cancel
	 * button is pressed, then nothing is changed.
	 * 
	 * The type of employee that is added depends on the state of the
	 * "Worker type" combo box.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// Convert the value from the date picker into a LocalDate
		GregorianCalendar startDateValue = (GregorianCalendar) startDatePicker.getModel().getValue();
		LocalDate startDate = startDateValue.toZonedDateTime().toLocalDate();

		// Work out which button was pressed
		Object source = event.getSource();

		// Cancel button is pressed
		if (source == cancelButton) {
			// Just close the window
			dispose();
		}

		// OK button is pressed
		if (source == okButton) {
			try {
				// create a new employee
				Employee newEmployee;

				// if the employee is of salaried type
				if (workerType.getSelectedItem().equals("Salaried")) {
					double salary = Double.parseDouble(salaryField.getText());

					// use constructor for salaried employees -- the employee
					// has a family name, given name, start date and salary
					newEmployee = new SalariedEmployee(familyNameField.getText(), givenNameField.getText(), startDate,
							salary);

					// if the employee is of hourly type
				} else {
					double rate = Double.parseDouble(rateField.getText());
					int hours = Integer.parseInt(hoursField.getText());

					// use constructor for hourly employees -- the employee has
					// a family name, given name, start date, hourly rate and
					// contracted hours
					newEmployee = new HourlyEmployee(familyNameField.getText(), givenNameField.getText(), startDate,
							rate, hours);
				}

				// add the new employee to the list of employees
				employeeFrame.getListModel().addElement(newEmployee);

				// in case an exception has been thrown
			} catch (Exception ex) {
				System.out.println("Invalid input.");
			}

			// then just close the window
			dispose();
		}
	}
}