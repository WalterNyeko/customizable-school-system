package school.ui.laboratory;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import school.ui.finances.CashBookController;

public class LaboratoryICTRecordEvent extends JDialog {
	public JComboBox fieldItemName;
	public JComboBox fieldType;
	public JTextField fieldResponsiblePerson;
	public JTextField fieldIDNo;
	public JComboBox fieldClass;
	public JTextArea areaDescription;
	public JDateChooser chooser;

	public JButton btnAddEvent;
	public JButton btnCancel;
	public Connection conn;
	public PreparedStatement pst;

	public LaboratoryICTRecordEvent() {

		softEarth();

	}

	private void softEarth() {
		setSize(190, 460);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout(FlowLayout.CENTER, 2, 15));

		Dimension dimFields = new Dimension(150, 30);

		fieldItemName = new JComboBox();
		add(fieldItemName);
		fieldItemName.setPreferredSize(dimFields);
		fieldItemName.setToolTipText("Item Name");
		displayInComboBox(fieldItemName, "select item_name from laboratory_ict_items");
		fieldItemName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				fieldType.removeAllItems();
				displayInComboBox(fieldType, "select item_type from laboratory_ict_items where item_name='"+fieldItemName.getSelectedItem()+"'");
				displayInTextArea(areaDescription, "select item_description from laboratory_ict_items where item_name='"+fieldItemName.getSelectedItem()+"'");
			}
		});
		

		fieldType = new JComboBox();
		add(fieldType);
		fieldType.setPreferredSize(dimFields);
		fieldType.setToolTipText("Item Type");
		displayInComboBox(fieldType, "select item_type from laboratory_ict_items");

		fieldResponsiblePerson = new JTextField();
		add(fieldResponsiblePerson);
		fieldResponsiblePerson.setPreferredSize(dimFields);
		fieldResponsiblePerson.setText("Responsible Person");
		fieldResponsiblePerson.setToolTipText("Responsible Person");
		fieldResponsiblePerson.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (fieldResponsiblePerson.getText().isEmpty()) {
					fieldResponsiblePerson.setText("Responsible Person");
				}

			}

			@Override
			public void focusGained(FocusEvent e) {
				if (!fieldResponsiblePerson.getText().equalsIgnoreCase("Responsible Person")) {
					fieldResponsiblePerson.setText(fieldResponsiblePerson.getText());
				}
			}
		});

		fieldIDNo = new JTextField();
		add(fieldIDNo);
		fieldIDNo.setPreferredSize(dimFields);
		fieldIDNo.setText("Student Code");
		fieldIDNo.setToolTipText("Student Code");
		fieldIDNo.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (fieldIDNo.getText().isEmpty()) {
					fieldIDNo.setText("Student Code");
				}

			}

			@Override
			public void focusGained(FocusEvent e) {
				if (!fieldIDNo.getText().equalsIgnoreCase("Student Code")) {
					fieldIDNo.setText(fieldIDNo.getText());
				}

			}
		});

		fieldClass = new JComboBox();
		add(fieldClass);
		fieldClass.setPreferredSize(dimFields);
		

		areaDescription = new JTextArea();
		add(areaDescription);
		areaDescription.setPreferredSize(new Dimension(150, 80));
		areaDescription.setText("Description");
		areaDescription.setToolTipText("Description");
		areaDescription.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (areaDescription.getText().isEmpty()) {

					areaDescription.setText("Description");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (!areaDescription.getText().equalsIgnoreCase("Description")) {
					areaDescription.setText(areaDescription.getText());
				}
			}
		});

		chooser = new JDateChooser();
		add(chooser);
		chooser.setPreferredSize(dimFields);
		chooser.getDate();

		btnAddEvent = new JButton("Add");
		btnAddEvent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddUpdateDelete("insert into laboratory_ict_events(item_name,item_type,responsible_person,student_code,item_description,event_date) "
						+ "values('"+fieldItemName.getSelectedItem()+"','"+fieldType.getSelectedItem()+"','"+fieldResponsiblePerson.getText()+"','"+fieldIDNo.getText()+"',"
								+ "'"+areaDescription.getText()+"','"+convertFromUtilToSQLDate(chooser.getDate())+"')");
				LaboratoryICT laboratoryICT=new LaboratoryICT();
				displayData(laboratoryICT.tableEvents, "select item_name,item_type,event_date,item_description,responsible_person,student_code,student_class from laboratory_ict_events");
				
			}
		});
		add(btnAddEvent);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
		add(btnCancel);

		displayInComboBox(fieldClass, "select class_name from student_classes");
		setVisible(true);
	}

	public JButton getBtnAddEvent() {
		return btnAddEvent;
	}
	
	public void AddUpdateDelete(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Request Executed Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}
	
	public void displayInComboBox(JComboBox<String> fieldBankName2, String query) {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();


			while (rs.next()) {
				fieldBankName2.addItem(rs.getString(1));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		}

	}
	
	public void displayInTextArea(JTextArea fieldBankName2, String query) {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			fieldBankName2.setText("");
			while (rs.next()) {
				fieldBankName2.setText(rs.getString(1));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		}

	}
	public Date convertFromUtilToSQLDate(java.util.Date dateUtil) {

		if (dateUtil != null) {
			java.sql.Date sqlDate = new java.sql.Date(dateUtil.getTime());

			return sqlDate;
		} else {
			return null;
		}
	}

	
	public void displayData(JTable table, String query) {

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			while (table.getRowCount() > 0) {
				((DefaultTableModel) table.getModel()).removeRow(0);

			}
			int columns = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				Object[] row = new Object[columns];
				for (int i = 1; i <= columns; i++) {
					row[i - 1] = rs.getObject(i);
				}
				((DefaultTableModel) table.getModel()).insertRow(rs.getRow() - 1, row);
			}
			rs.close();
			pst.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
