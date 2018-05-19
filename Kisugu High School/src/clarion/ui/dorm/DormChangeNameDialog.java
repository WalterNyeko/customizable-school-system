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

public class DormChangeNameDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel labelID;
	private JLabel labelAddNewDorm;
	private JLabel labelChangeDormName;

	private JTextField fieldDormName;
	private JTextField fieldDormNameNew;

	private JButton btnSave;
	private JButton btnCancel;

	private Object dormNamePrevious = null;
	private DormController controller;
	private DormPanel dormPanel;
	private boolean updateMode = false;

	public DormChangeNameDialog(DormPanel theDormPanel, DormController theController, Object theDormName,
			boolean theUpdateMode) {
		this();
		dormPanel = theDormPanel;
		dormNamePrevious = theDormName;
		controller = theController;
		updateMode = theUpdateMode;

		if (updateMode) {
			setTitle("Change the dorm name");
			// labelAddNewDorm.setText("Change the dormitory name");
			populateGUI(dormNamePrevious);

		}

	}

	public DormChangeNameDialog(DormPanel theDormPanel, DormController theController, boolean theUpdateMode) {
		this(theDormPanel, theController, null, false);
	}

	public DormChangeNameDialog() {
		setTitle("Change the dorm name");
		setSize(500, 150);
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setLocationRelativeTo(null);

		labelAddNewDorm = new JLabel("        Create a new dormitory");
		labelAddNewDorm.setPreferredSize(new Dimension(240, 30));
		add(labelAddNewDorm);

		labelChangeDormName = new JLabel("            New Dorm Name:");
		labelChangeDormName.setPreferredSize(new Dimension(240, 30));
		add(labelChangeDormName);

		fieldDormName = new JTextField();
		fieldDormName.setPreferredSize(new Dimension(240, 30));
		add(fieldDormName);

		fieldDormNameNew = new JTextField();
		fieldDormNameNew.setPreferredSize(new Dimension(240, 30));
		add(fieldDormNameNew);

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
				setVisible(false);
			}
		});

		labelID = new JLabel("");
		labelID.setPreferredSize(new Dimension(240, 30));
		add(labelID);
		labelID.setVisible(false);

	}

	private void populateGUI(Object theDormName) {
		fieldDormName.setText((String) theDormName);
	}

	private void save() {
		String dormNamePrevStr = fieldDormName.getText();

		String idStr = labelID.getText().toString();

		String dormNameNewStr = fieldDormNameNew.getText();
		Integer id = new Integer(idStr);

		Object dormNameClass = null;

		dormNamePrevious = dormNameNewStr;

		dormNameClass = (String) dormNamePrevious;

		// int id = 0;
		// dormNameClass = dormNamePrevious;

		try {
			controller.changeDormName(dormNameClass, dormNamePrevStr, dormNameNewStr, id);
			JOptionPane.showMessageDialog(null,
					"Dormitory " + dormNamePrevStr + "\nhas been changed to \n" + dormNameNewStr);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There was a problem.\nThe Dormitory name you tried to create " + "can not be created");
		}

		dormPanel.refreshDormList();

		setVisible(false);

	}

	public JLabel getLabelID() {
		return labelID;
	}

}
