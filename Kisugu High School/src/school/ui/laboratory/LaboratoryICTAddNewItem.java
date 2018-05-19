package school.ui.laboratory;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import school.ui.finances.CashBookController;
import school.ui.student.AddNewUNSA;

public class LaboratoryICTAddNewItem extends JDialog {

	public JTextField fieldItemName;
	public JTextField fieldType;
	public JScrollPane scrollPaneDescription;
	public JTextArea areaDescription;
	public JDateChooser dateChooserDateIn;
	public JTextField fieldDonor;
	public JTextField fieldRecievedBy;
	public JButton btnAdd;
	public JButton btnCancel;

	public JLabel labelItemName;
	public JLabel labelType;
	public JLabel labelDescription;
	public JLabel labelDateIn;
	public JLabel labelDonor;
	public JLabel labelRecievedBy;
	protected LaboratoryICT laboratoryICT;

	public LaboratoryICTAddNewItem() {

		buildDialog();
	}

	private void buildDialog() {
		setTitle("Add New Item In ICT Laboratory");
		setSize(540, 300);
		setLocationRelativeTo(null);

		setLayout(new FlowLayout(FlowLayout.CENTER, 2, 15));

		Dimension dimLabels = new Dimension(100, 25);
		Dimension dimFields = new Dimension(150, 25);
		Dimension dimBtns = new Dimension(80, 30);

		labelItemName = new JLabel("Item Name:");
		labelItemName.setPreferredSize(dimLabels);
		add(labelItemName);
		fieldItemName = new JTextField();
		fieldItemName.setPreferredSize(dimFields);
		add(fieldItemName);

		labelType = new JLabel("Type:");
		labelType.setPreferredSize(dimLabels);
		add(labelType);
		fieldType = new JTextField();
		fieldType.setPreferredSize(dimFields);
		add(fieldType);

		labelDescription = new JLabel("Description:");
		labelDescription.setPreferredSize(dimLabels);
		add(labelDescription);
		areaDescription = new JTextArea();
		areaDescription.setWrapStyleWord(true);
		areaDescription.setLineWrap(true);
		scrollPaneDescription = new JScrollPane(areaDescription);
		scrollPaneDescription.setPreferredSize(new Dimension(150, 80));
		add(scrollPaneDescription);

		labelDateIn = new JLabel("Date In:");
		labelDateIn.setPreferredSize(dimLabels);
		add(labelDateIn);
		dateChooserDateIn = new JDateChooser();
		dateChooserDateIn.getDate();
		dateChooserDateIn.setPreferredSize(dimFields);
		add(dateChooserDateIn);

		labelDonor = new JLabel("Donor:");
		labelDonor.setPreferredSize(dimLabels);
		add(labelDonor);
		fieldDonor = new JTextField();
		fieldDonor.setPreferredSize(dimFields);
		add(fieldDonor);

		labelRecievedBy = new JLabel("Recieved By:");
		labelRecievedBy.setPreferredSize(dimLabels);
		add(labelRecievedBy);
		fieldRecievedBy = new JTextField();
		fieldRecievedBy.setPreferredSize(dimFields);
		add(fieldRecievedBy);

		btnAdd = new JButton("Add");
		btnAdd.setPreferredSize(dimBtns);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (btnAdd.getText().equals("Add")) {
					InsertSilently(
							"insert into laboratory_ict_items(item_name,item_type,item_description,item_date_in,item_donor,item_receiver)"
									+ " values('" + fieldItemName.getText() + "','" + fieldType.getText() + "','"
									+ areaDescription.getText() + "'," + "'"
									+ convertFromUtilToSQLDate(dateChooserDateIn.getDate()) + "','"
									+ fieldDonor.getText() + "'," + "'" + fieldRecievedBy.getText() + "')");

					laboratoryICT = new LaboratoryICT();
					displayData(laboratoryICT.tableItems,
							"select item_name,item_type,item_description,item_date_in,item_donor,item_receiver from laboratory_ict_items");
				} else {

					InsertSilently(
							"insert into laboratory_ict_items(item_name,item_type,item_description,item_date_in,item_donor,item_receiver)"
									+ " values('" + fieldItemName.getText() + "','" + fieldType.getText() + "','"
									+ areaDescription.getText() + "'," + "'"
									+ convertFromUtilToSQLDate(dateChooserDateIn.getDate()) + "','"
									+ fieldDonor.getText() + "'," + "'" + fieldRecievedBy.getText()
									+ "') ON DUPLICATE KEY UPDATE item_name=values(item_name),item_type=values(item_type),item_description="
									+ "values(item_description),item_date_in=values(item_date_in),item_donor=values(item_donor),item_receiver=values(item_receiver)");
					
					laboratoryICT = new LaboratoryICT();
					displayData(laboratoryICT.tableItems,
							"select item_name,item_type,item_description,item_date_in,item_donor,item_receiver from laboratory_ict_items");

				}
			}
		});
		add(btnAdd);
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
		btnCancel.setPreferredSize(dimBtns);
		add(btnCancel);

		setVisible(true);
	}

	public JButton getBtnAdd() {
		return btnAdd;
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

	public void InsertSilently(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Request Executed Successfully ");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

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

}
