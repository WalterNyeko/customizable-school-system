package school.ui.staff;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import school.ui.finances.CashBookController;

public class TeachersResponsibilities extends JFrame {

	private JLabel labelTeachingIDNo, labelTeachingName, labelTeachingEmail, labelTeachingContact, labelTeachingAddress,
			labelTeachingGender, labelTeachingResponsiblity;

	JTextField fieldIDNo, fieldName, fieldEmail, fieldContact, fieldAddress, fieldResponsibility;

	private JButton btnSave, btnCancel;

	private JPanel holder;

	JComboBox fieldGender;

	private JLabel labelTeachingDorm;

	JComboBox fieldDorm;

	private Connection conn;

	private PreparedStatement pst;

	public static void main(String[] args) {
		new TeachersResponsibilities();
	}

	public TeachersResponsibilities() {
		super("Add Responsibilities");
		setSize(new Dimension(450, 300));
		setResizable(false);
		setLocationRelativeTo(null);

		labelTeachingIDNo = new JLabel("ID Number");
		labelTeachingName = new JLabel("Name");
		labelTeachingGender = new JLabel("Class To Handle:");
		labelTeachingDorm =new JLabel("Dorm To Handle:");
		labelTeachingResponsiblity = new JLabel("Responsibilities");

		fieldIDNo = new JTextField();
		fieldName = new JTextField();
		fieldEmail = new JTextField();
		fieldContact = new JTextField();
		fieldAddress = new JTextField();
		fieldGender = new JComboBox();
		displayInComboBox(fieldGender, "select class_name from student_classes");
		
		fieldDorm = new JComboBox();
		displayInComboBox(fieldDorm, "select dormName from dorm");
		fieldResponsibility = new JTextField();

		holder = new JPanel();
		holder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		add(holder);

		Dimension dimFields = new Dimension(200, 25);

		holder.add(labelTeachingIDNo);
		labelTeachingIDNo.setPreferredSize(dimFields);
		holder.add(fieldIDNo);
		fieldIDNo.setPreferredSize(dimFields);

		holder.add(labelTeachingName);
		labelTeachingName.setPreferredSize(dimFields);
		holder.add(fieldName);
		fieldName.setPreferredSize(dimFields);

		
		holder.add(labelTeachingGender);
		labelTeachingGender.setPreferredSize(dimFields);
		holder.add(fieldGender);
		fieldGender.setPreferredSize(dimFields);
		
		holder.add(labelTeachingDorm);
		labelTeachingDorm.setPreferredSize(dimFields);
		holder.add(fieldDorm);
		fieldDorm.setPreferredSize(dimFields);

		holder.add(labelTeachingResponsiblity);
		labelTeachingResponsiblity.setPreferredSize(dimFields);
		holder.add(fieldResponsibility);
		fieldResponsibility.setPreferredSize(dimFields);

		
		// btns

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddUpdateDelete("insert into teaching_staffs(staff_id,teacher_name,class_assigned,responsibilty,dormitory)"
						+ " values('" + fieldIDNo.getText() + "',"
						+ "'"+fieldName.getText() + "',"
						+ "'" + fieldGender.getSelectedItem() + "',"
						+ "'" + fieldResponsibility.getText() + "',"
						+ "'" + fieldDorm.getSelectedItem() + "')");

			}
		});
		holder.add(btnSave);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
		holder.add(btnCancel);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
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

			fieldBankName2.removeAll();

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


}
