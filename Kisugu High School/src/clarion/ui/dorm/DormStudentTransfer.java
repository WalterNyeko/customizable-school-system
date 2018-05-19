package clarion.ui.dorm;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import clarion.controller.dorm.DormController;

public class DormStudentTransfer extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnTransfer;
	private JButton btnCancel;

	private JLabel labelDorms;
	private JComboBox comboBoxDorms;

	private JLabel labelClassNumber;
	private JLabel labelStudentName;
	private JLabel labelStudentClass;
	private JLabel labelYear;

	private JTextField fieldClassNumber;
	private JTextField fieldStudentName;
	private JTextField fieldStudentClass;
	private JTextField fieldYear;

	private DormController controller;

	private String to;

	public DormStudentTransfer() {
		setTitle("Transfer Student");
		setSize(new Dimension(400, 250));
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setLocationRelativeTo(null);

		// dimension label
		Dimension dimensionLabel = new Dimension(150, 30);

		// dimension field
		Dimension dimensionField = new Dimension(200, 30);

		labelClassNumber = new JLabel("Class Number:");
		labelClassNumber.setPreferredSize(dimensionLabel);
		add(labelClassNumber);
		fieldClassNumber = new JTextField();
		fieldClassNumber.setPreferredSize(dimensionField);
		add(fieldClassNumber);

		labelStudentName = new JLabel("Name:");
		labelStudentName.setPreferredSize(dimensionLabel);
		add(labelStudentName);
		fieldStudentName = new JTextField();
		fieldStudentName.setPreferredSize(dimensionField);
		add(fieldStudentName);

		labelStudentClass = new JLabel("Class:");
		labelStudentClass.setPreferredSize(dimensionLabel);
		add(labelStudentClass);
		fieldStudentClass = new JTextField();
		fieldStudentClass.setPreferredSize(dimensionField);
		add(fieldStudentClass);

		labelYear = new JLabel("Year:");
		labelYear.setPreferredSize(dimensionLabel);
		add(labelYear);
		fieldYear = new JTextField();
		fieldYear.setPreferredSize(dimensionField);
		add(fieldYear);

		labelDorms = new JLabel("Choose Dorm Name:");
		labelDorms.setPreferredSize(dimensionLabel);
		add(labelDorms);

		

		List<Object> dorms = null;
//		dorms.add("Choose Dormitory");
		
		try {
			controller = new DormController();
			dorms = controller.fillDormTree();
			comboBoxDorms = new JComboBox<>(dorms.toArray());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// comboBoxDorms = new JComboBox<>();
		comboBoxDorms.setPreferredSize(dimensionField);
		add(comboBoxDorms);

//		comboBoxDorms.addItem("Choose Dormitory");
		
		btnTransfer = new JButton("Transfer");
		add(btnTransfer);

		btnCancel = new JButton("Cancel");
		add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

	}

	public static void main(String[] args) {
		DormStudentTransfer transfer = new DormStudentTransfer();
		transfer.setVisible(true);
	}

	public String getTo() {
		return to;
	}

	public JTextField getFieldClassNumber() {
		return fieldClassNumber;
	}

	public JTextField getFieldStudentName() {
		return fieldStudentName;
	}

	public JTextField getFieldStudentClass() {
		return fieldStudentClass;
	}

	public JTextField getFieldYear() {
		return fieldYear;
	}

	public JButton getBtnTransfer() {
		return btnTransfer;
	}

	public JButton getBtnCancel() {
		return btnCancel;
	}

	public JComboBox getComboBoxDorms() {
		return comboBoxDorms;
	}

}
