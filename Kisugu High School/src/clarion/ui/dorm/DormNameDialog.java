package clarion.ui.dorm;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import clarion.controller.dorm.DormController;
import clarion.model.dorm.DormName;

public class DormNameDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel labelAddNewDorm;

	private JTextField fieldDormName;

	private JButton btnSave;
	private JButton btnCancel;

	private DormName dormNamePrevious = null;
	private DormController controller;
	private DormPanel dormPanel;
	private boolean updateMode = false;

	public DormNameDialog(DormPanel theDormPanel, DormController theController, DormName theDormName,
			boolean theUpdateMode) {
		this();
		dormPanel = theDormPanel;
		dormNamePrevious = theDormName;
		controller = theController;
		updateMode = theUpdateMode;

		if (updateMode) {
			setTitle("Change the dorm name");
			labelAddNewDorm.setText("Change the dormitory name");
			populateGUI(dormNamePrevious);

		}

	}

	public DormNameDialog(DormPanel theDormPanel, DormController theController, boolean theUpdateMode) {
		this(theDormPanel, theController, null, false);
	}

	public DormNameDialog() {
		setTitle("Create a new dormitory");
		setSize(250, 150);
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setLocationRelativeTo(null);

		labelAddNewDorm = new JLabel("      Create a new dormitory");
		labelAddNewDorm.setPreferredSize(new Dimension(240, 30));
		add(labelAddNewDorm);

		fieldDormName = new JTextField();
		fieldDormName.setPreferredSize(new Dimension(240, 30));
		add(fieldDormName);

		btnSave = new JButton("ENTER");
		add(btnSave);
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

		btnCancel = new JButton("Cancel");
		add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});

	}

	private void populateGUI(DormName theDormName) {
		fieldDormName.setText(theDormName.getDormName());
	}

	private void save() {
		String dormName = fieldDormName.getText();

		DormName dormNameClass = null;

		dormNameClass = new DormName(dormName);

		try {
			controller.createDormTable(dormName, dormNameClass);
			dormPanel.refreshDormList();
			JOptionPane.showMessageDialog(null, "A new Dormitory called\n" + dormName + "\nhas been created");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There was a problem.\nThe Dormitory name you tried to create " + "can not be created");
		}

		dormPanel.refreshDormList();

		setVisible(false);

	}

}
