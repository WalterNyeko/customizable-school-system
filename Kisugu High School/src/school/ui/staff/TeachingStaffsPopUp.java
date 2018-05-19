package school.ui.staff;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import school.ui.finances.CashBookController;

public class TeachingStaffsPopUp extends JFrame {

	private JLabel labelTeachingIDNo, labelTeachingName, labelTeachingEmail, labelTeachingContact, labelTeachingAddress,
			labelTeachingGender, labelTeachingResponsiblity;

	JTextField fieldIDNo, fieldName, fieldEmail, fieldContact, fieldAddress, fieldResponsibility;

	JButton btnSave, btnCancel;

	private JPanel holder;

	JComboBox fieldGender;

	private JLabel labelTeachingTIN;

	private JLabel labelTeachingNSSF;

	JTextField fieldTIN;

	JTextField fieldNSSF;

	public static void main(String[] args) {
		new TeachingStaffsPopUp();
	}

	public TeachingStaffsPopUp() {
		setSize(new Dimension(450, 350));
		setResizable(false);
		setLocationRelativeTo(null);

		labelTeachingIDNo = new JLabel("ID Number");
		labelTeachingName = new JLabel("Name");
		labelTeachingEmail = new JLabel("Email");
		labelTeachingTIN = new JLabel("TIN Number");
		labelTeachingNSSF = new JLabel("Qualification");
		labelTeachingContact = new JLabel("Contact");
		labelTeachingAddress = new JLabel("Payroll Status");
		labelTeachingGender = new JLabel("Gender");
		labelTeachingResponsiblity = new JLabel("Responsibilities");

		fieldIDNo = new JTextField();
		fieldName = new JTextField();
		fieldEmail = new JTextField();
		fieldTIN = new JTextField();
		fieldNSSF = new JTextField();
		fieldContact = new JTextField();
		fieldAddress = new JTextField();
		String[] sex = { "Choose Gender", "Male", "Female" };
		fieldGender = new JComboBox(sex);
		fieldResponsibility = new JTextField();

		holder = new JPanel();
		holder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		add(holder);

		Dimension dimFields = new Dimension(200, 25);

		holder.add(labelTeachingIDNo);
		labelTeachingIDNo.setPreferredSize(dimFields);
		holder.add(fieldIDNo);
		fieldIDNo.setPreferredSize(dimFields);
		fieldIDNo.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				displayStaffsInfo();
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		holder.add(labelTeachingName);
		labelTeachingName.setPreferredSize(dimFields);
		holder.add(fieldName);
		fieldName.setPreferredSize(dimFields);

		holder.add(labelTeachingEmail);
		labelTeachingEmail.setPreferredSize(dimFields);
		holder.add(fieldEmail);
		fieldEmail.setPreferredSize(dimFields);

		holder.add(labelTeachingTIN);
		labelTeachingTIN.setPreferredSize(dimFields);
		holder.add(fieldTIN);
		fieldTIN.setPreferredSize(dimFields);

		holder.add(labelTeachingNSSF);
		labelTeachingNSSF.setPreferredSize(dimFields);
		holder.add(fieldNSSF);
		fieldNSSF.setPreferredSize(dimFields);

		holder.add(labelTeachingContact);
		labelTeachingContact.setPreferredSize(dimFields);
		holder.add(fieldContact);
		fieldContact.setPreferredSize(dimFields);

		holder.add(labelTeachingAddress);
		labelTeachingAddress.setPreferredSize(dimFields);
		holder.add(fieldAddress);
		fieldAddress.setPreferredSize(dimFields);

		holder.add(labelTeachingGender);
		labelTeachingGender.setPreferredSize(dimFields);
		holder.add(fieldGender);
		fieldGender.setPreferredSize(dimFields);

		// btns

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (btnSave.getText().equalsIgnoreCase("Save")) {
					AddUpdateDelete("insert into table_teaching_staffs values(" + "'" + fieldIDNo.getText() + "','"
							+ fieldName.getText() + "'," + "'" + fieldEmail.getText() + "','" + fieldTIN.getText()
							+ "','" + fieldNSSF.getText() + "','" + fieldContact.getText() + "'," + "'"
							+ fieldGender.getSelectedItem() + "','" + fieldAddress.getText() + "')");

					AddUpdateDelete("insert into teaching_staffs(staff_id,teacher_name,responsibilty) values(" + "'"
							+ fieldIDNo.getText() + "','" + fieldName.getText() + "'," + "'Subject Teacher')");
					closing();

				} else if (btnSave.getText().equalsIgnoreCase("Save Changes")) {
					AddUpdateDelete("update table_teaching_staffs set id_number='" + fieldIDNo.getText() + "',"
							+ "staff_name='" + fieldName.getText() + "',staff_email='" + fieldEmail.getText() + "',"
							+ "staff_contact='" + fieldContact.getText() + "',staff_gender='"
							+ fieldGender.getSelectedItem() + "'," + "staff_address='" + fieldAddress.getText()
							+ "',tin_number='" + fieldTIN.getText() + "',nssf_number='" + fieldNSSF.getText()
							+ "' where id_number='" + fieldIDNo.getText() + "'");
					closing();
				}
			}
		});
		holder.add(btnSave);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				closing();
			}
		});
		holder.add(btnCancel);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void closing() {

		WindowEvent closing = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closing);
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

	public void displayStaffsInfo() {

		String staffID = fieldIDNo.getText();
		try {

			java.sql.Connection conn = CashBookController.getConnection();

			PreparedStatement pst = conn
					.prepareStatement("select * from table_teaching_staffs where id_number='" + staffID + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				fieldAddress.setText(rs.getString("staff_address"));
				fieldContact.setText(rs.getString("staff_contact"));
				fieldEmail.setText(rs.getString("staff_email"));
				fieldName.setText(rs.getString("staff_name"));
				fieldNSSF.setText(rs.getString("nssf_number"));
				fieldTIN.setText(rs.getString("tin_number"));
				fieldGender.setSelectedItem(rs.getString("staff_gender"));

			} else {
				fieldAddress.setText(rs.getString(null));
				fieldContact.setText(rs.getString(null));
				fieldEmail.setText(rs.getString(null));
				fieldName.setText(rs.getString(null));
				fieldNSSF.setText(rs.getString(null));
				fieldTIN.setText(rs.getString(null));
				fieldGender.setSelectedIndex(0);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
