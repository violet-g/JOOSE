package employeemanager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import workers.Employee;

/**
 * A window on an employee record system. The window displays all employee
 * records and allows the user to add a new employee, edit an existing employee,
 * delete an employee from the list, or save the list of employees to a file.
 * 
 * @author Gabriela Georgieva, 2130120g; starter code - mefoster
 *
 */

@SuppressWarnings("serial")
public class EmployeeFrame extends JFrame implements ActionListener {

	// The buttons to display
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton saveButton;

	// The underlying list of employees, and the GUI object to display them
	private DefaultListModel<Employee> listModel;
	private JList<Employee> employeeList;

	public static final String SAVE_FILE = "employees.txt";

	/**
	 * Creates and displays a new EmployeeFrame. The program exits when the
	 * window is closed.
	 */
	public EmployeeFrame() {

		// Basic window features
		super("Employee Manager");
		setLocationByPlatform(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Try to make it look like a native application -- using
		// try/multi-catch
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		// Initialise an empty list model, a JList to display it, and a scroll
		// pane to contain the list
		listModel = new DefaultListModel<>();
		employeeList = new JList<>(listModel);
		JScrollPane employeeScroll = new JScrollPane(employeeList);
		employeeScroll.setBorder(new TitledBorder("Employee List"));

		// Initialise all buttons and add the current class as an action
		// listener to all
		addButton = new JButton("Add Employee");
		addButton.addActionListener(this);
		editButton = new JButton("Edit Employee");
		editButton.addActionListener(this);
		deleteButton = new JButton("Delete Employee");
		deleteButton.addActionListener(this);
		saveButton = new JButton("Save Employee List");
		saveButton.addActionListener(this);

		// Lay out the buttons in a line
		Box topBox = Box.createHorizontalBox();
		topBox.add(addButton);
		topBox.add(Box.createHorizontalStrut(10));
		topBox.add(editButton);
		topBox.add(Box.createHorizontalStrut(10));
		topBox.add(deleteButton);
		topBox.add(Box.createHorizontalStrut(10));
		topBox.add(saveButton);

		// Lay out the window
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topBox, BorderLayout.NORTH);
		getContentPane().add(employeeScroll, BorderLayout.CENTER);
		pack();
	}

	/**
	 * Returns the ListModel -- i.e., the list of all employees.
	 * 
	 * @return The current list of employees
	 */
	public DefaultListModel<Employee> getListModel() {
		return this.listModel;
	}

	/**
	 * Responds to a click on any of the buttons on the window.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		// Determine which button was pushed
		Object source = event.getSource();

		// Add Employee has been pressed
		if (source == addButton) {

			// create the dialog
			AddEmployeeDialog dialog = new AddEmployeeDialog(this);

			// make it visible
			dialog.setVisible(true);
		}

		// Edit Employee has been pressed
		if (source == editButton) {
			Employee selected = employeeList.getSelectedValue();

			// check if an employee has been selected
			if (selected != null) {

				// create the dialog
				EditEmployeeDialog dialog = new EditEmployeeDialog(this, selected);

				// make it visible
				dialog.setVisible(true);
			}
		}

		// Save Employee List has been pressed
		if (source == saveButton) {
			try {
				PrintWriter out = new PrintWriter(new FileWriter(SAVE_FILE, true));

				Enumeration<Employee> employees = listModel.elements();
				while (employees.hasMoreElements()) {
					out.println(employees.nextElement().toString());
				}
				out.close();
			} catch (Exception e) {
			}
		}

		// Delete Employee has been pressed
		if (source == deleteButton) {
			Employee selected = employeeList.getSelectedValue();

			// check if an employee has been selected
			if (selected != null) {

				String message = "Are you sure you want to delete this employee?";

				// record the response
				int response = JOptionPane.showConfirmDialog(this, message);

				// evaluate the response
				if (response == 0) {

					// remove the element only if YES has been pressed (the
					// button at position 0)
					listModel.removeElement(selected);
				}
			}
		}
	}

	/**
	 * Main method: just creates and displays a new EmployeeFrame.
	 * 
	 * @param args
	 *            Command-line arguments (not used)
	 */
	public static void main(String[] args) {
		new EmployeeFrame().setVisible(true);
	}

}