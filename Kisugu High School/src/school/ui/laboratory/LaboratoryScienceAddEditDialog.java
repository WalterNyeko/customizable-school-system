package school.ui.laboratory;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import school.ui.finances.CashBookController;

public class LaboratoryScienceAddEditDialog extends JDialog {
	private JLabel labelItemName;
	private JLabel labelType;
	private JLabel labelDescription;
	private JLabel labelQuantity;
	private JLabel labelDateIn;
	private JLabel labelDonor;
	private JLabel labelRecievedBy;

	private JTextField fieldItemName;
	private JTextField fieldType;
	private JTextField fieldDescription;
	private JTextField fieldQuantity;
	private JDateChooser dateChooserDateIn;
	private JTextField fieldDonor;
	private JTextField fieldReceivedBy;

	private JButton btnAdd;
	private JButton btnCancel;
	protected LaboratorySciencePractical laboratoryICT;

	public LaboratoryScienceAddEditDialog() {
		buildDialog();
	}

	private void buildDialog() {
		setTitle("Add New Science Laboratory Item");
		setSize(new Dimension(380, 420));
		setLocationRelativeTo(null);
		setLayout(new FlowLayout(FlowLayout.CENTER, 2, 15));

		Dimension dimensionLabels = new Dimension(100, 30);
		Dimension dimensionFields = new Dimension(200, 30);

		labelItemName = new JLabel("Item Name:");
		labelItemName.setPreferredSize(dimensionLabels);
		add(labelItemName);

		fieldItemName = new JTextField();
		fieldItemName.setPreferredSize(dimensionFields);
		add(fieldItemName);

		labelType = new JLabel("Type:");
		labelType.setPreferredSize(dimensionLabels);
		add(labelType);

		fieldType = new JTextField();
		fieldType.setPreferredSize(dimensionFields);
		add(fieldType);

		labelDescription = new JLabel("Description:");
		labelDescription.setPreferredSize(dimensionLabels);
		add(labelDescription);

		fieldDescription = new JTextField();
		fieldDescription.setPreferredSize(dimensionFields);
		add(fieldDescription);

		labelQuantity = new JLabel("Quantity:");
		labelQuantity.setPreferredSize(dimensionLabels);
		add(labelQuantity);

		fieldQuantity = new JTextField();
		fieldQuantity.setPreferredSize(dimensionFields);
		add(fieldQuantity);

		labelDateIn = new JLabel("Date In");
		labelDateIn.setPreferredSize(dimensionLabels);
		add(labelDateIn);

		dateChooserDateIn = new JDateChooser();
		dateChooserDateIn.getDate();
		dateChooserDateIn.setPreferredSize(dimensionFields);
		add(dateChooserDateIn);

		labelDonor = new JLabel("Donor:");
		labelDonor.setPreferredSize(dimensionLabels);
		add(labelDonor);

		fieldDonor = new JTextField();
		fieldDonor.setPreferredSize(dimensionFields);
		add(fieldDonor);

		labelRecievedBy = new JLabel("Recieved By:");
		labelRecievedBy.setPreferredSize(dimensionLabels);
		add(labelRecievedBy);

		fieldReceivedBy = new JTextField();
		fieldReceivedBy.setPreferredSize(dimensionFields);
		add(fieldReceivedBy);

		btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnAdd.setPreferredSize(new Dimension(80, 30));
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				InsertSilently(
						"insert into laboratory_science_items(item_name,item_type,item_description,item_date_in,item_donor,item_receiver)"
								+ " values('" + fieldItemName.getText() + "','" + fieldType.getText() + "','"
								+ fieldDescription.getText() + "'," + "'"
								+ convertFromUtilToSQLDate(dateChooserDateIn.getDate()) + "','" + fieldDonor.getText()
								+ "'," + "'" + fieldReceivedBy.getText() + "')");
			 laboratoryICT=new LaboratorySciencePractical();
				displayData(laboratoryICT.tableItems, "select item_name,item_type,item_description,"
						+ "item_date_in,item_donor,item_receiver from laboratory_science_items");
				setVisible(false);
			}
		});
		add(btnAdd);

		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnCancel.setPreferredSize(new Dimension(100, 30));
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
		add(btnCancel);

		setVisible(true);
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

			JOptionPane.showMessageDialog(null, "Request Executed Successfully " );
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
