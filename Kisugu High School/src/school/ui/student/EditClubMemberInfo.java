package school.ui.student;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import school.ui.finances.CashBookController;

public class EditClubMemberInfo extends JFrame {

	private JLabel labelIDNo, labelStudentName, labelClass, labelGender, labelPosition, labelRegime;
	JTextField fieldIDNo, fieldStudentName, fieldClass, fieldGender, fieldPosition, fieldRegime;
	private JPanel holder;
	private JButton btnSave, btnCancel;

	public EditClubMemberInfo() {
		// TODO Auto-generated constructor stub

		setTitle("Edit Club Member Info");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 250);
		setResizable(false);

		setLocationRelativeTo(null);

		Dimension dimension = new Dimension(70, 20);

		labelIDNo = new JLabel("ID Number");
		labelIDNo.setPreferredSize(dimension);

		labelStudentName = new JLabel("Name");
		labelStudentName.setPreferredSize(dimension);

		labelClass = new JLabel("Class");
		labelClass.setPreferredSize(dimension);

		labelGender = new JLabel("Gender");
		labelGender.setPreferredSize(dimension);

		labelPosition = new JLabel("Position");
		labelPosition.setPreferredSize(dimension);

		labelRegime = new JLabel("Regime");
		labelRegime.setPreferredSize(dimension);

		Dimension fieldDimesnion = new Dimension(200, 25);

		fieldIDNo = new JTextField();
		fieldIDNo.setPreferredSize(fieldDimesnion);

		fieldStudentName = new JTextField();
		fieldStudentName.setPreferredSize(fieldDimesnion);

		fieldClass = new JTextField();
		fieldClass.setPreferredSize(fieldDimesnion);

		fieldGender = new JTextField();
		fieldGender.setPreferredSize(fieldDimesnion);

		fieldPosition = new JTextField();
		fieldPosition.setPreferredSize(fieldDimesnion);

		fieldRegime = new JTextField();
		fieldRegime.setPreferredSize(fieldDimesnion);

		holder = new JPanel();
		holder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
		holder.setBackground(Color.decode("#7f8c8d"));

		holder.add(labelIDNo);
		holder.add(fieldIDNo);

		holder.add(labelStudentName);
		holder.add(fieldStudentName);

		holder.add(labelClass);
		holder.add(fieldClass);

		holder.add(labelGender);
		holder.add(fieldGender);

		holder.add(labelPosition);
		holder.add(fieldPosition);

		holder.add(labelRegime);
		holder.add(fieldRegime);

		fieldIDNo.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub


				String studentSQL = "select CONCAT(first_name, ' ', middle_name, ' ', last_name) "
						+ "as Name,class_joining,gender from " + "tableadmissionstudentdetails where id_number='"
						+ fieldIDNo.getText() + "'";
				try {

					java.sql.Connection conn =  CashBookController.getConnection();

					PreparedStatement pst = conn.prepareStatement(studentSQL);
					ResultSet rs = pst.executeQuery();

					while (rs.next()) {
						fieldStudentName.setText(rs.getString(1));
						fieldClass.setText(rs.getString(2));
						fieldGender.setText(rs.getString(3));
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Dimension btnDim = new Dimension(120, 25);

		btnSave = new JButton("Save Changes");
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (EditClubMemberInfo.this.getTitle().equals("Edit Debator's Club Information")) {

					AddUpdateDelete("update debators set " + "id_number='" + fieldIDNo.getText() + "',student_name='"
							+ fieldStudentName.getText() + "'," + "student_class='" + fieldClass.getText()
							+ "',gender='" + fieldGender.getText() + "',position='" + fieldPosition.getText() + "',"
							+ "regime='" + fieldRegime.getText() + "' where id_number='" + fieldIDNo.getText() + "'");
				} else if (EditClubMemberInfo.this.getTitle().equals("Edit Writer's Club Information")) {
					AddUpdateDelete("update writersclub set " + "id_number='" + fieldIDNo.getText() + "',student_name='"
							+ fieldStudentName.getText() + "'," + "student_class='" + fieldClass.getText()
							+ "',gender='" + fieldGender.getText() + "',position='" + fieldPosition.getText() + "',"
							+ "regime='" + fieldRegime.getText() + "' where id_number='" + fieldIDNo.getText() + "'");
				} else if (EditClubMemberInfo.this.getTitle().equals("Edit Interact's Club Information")) {
					AddUpdateDelete("update interact_club set " + "id_number='" + fieldIDNo.getText()
							+ "',student_name='" + fieldStudentName.getText() + "'," + "student_class='"
							+ fieldClass.getText() + "',gender='" + fieldGender.getText() + "',position='"
							+ fieldPosition.getText() + "'," + "regime='" + fieldRegime.getText()
							+ "' where id_number='" + fieldIDNo.getText() + "'");
				} else if (EditClubMemberInfo.this.getTitle().equals("Edit Scripture Union's Club Information")) {
					AddUpdateDelete("update scripture_union set " + "id_number='" + fieldIDNo.getText()
							+ "',student_name='" + fieldStudentName.getText() + "'," + "student_class='"
							+ fieldClass.getText() + "',gender='" + fieldGender.getText() + "',position='"
							+ fieldPosition.getText() + "'," + "regime='" + fieldRegime.getText()
							+ "' where id_number='" + fieldIDNo.getText() + "'");
				} else if (EditClubMemberInfo.this.getTitle().equals("Edit Scouts And Guide's Club Information")) {
					AddUpdateDelete("update scouts_and_guides set " + "id_number='" + fieldIDNo.getText()
							+ "',student_name='" + fieldStudentName.getText() + "'," + "student_class='"
							+ fieldClass.getText() + "',gender='" + fieldGender.getText() + "',position='"
							+ fieldPosition.getText() + "'," + "regime='" + fieldRegime.getText()
							+ "' where id_number='" + fieldIDNo.getText() + "'");
				} else if (EditClubMemberInfo.this.getTitle().equals("Edit Wild Life Club's Club Information")) {
					AddUpdateDelete("update wild_life_club set " + "id_number='" + fieldIDNo.getText()
							+ "',student_name='" + fieldStudentName.getText() + "'," + "student_class='"
							+ fieldClass.getText() + "',gender='" + fieldGender.getText() + "',position='"
							+ fieldPosition.getText() + "'," + "regime='" + fieldRegime.getText()
							+ "' where id_number='" + fieldIDNo.getText() + "'");
				} else if (EditClubMemberInfo.this.getTitle().equals("Edit Games And Sports Club Information")) {
					AddUpdateDelete("update games_and_sports set " + "id_number='" + fieldIDNo.getText()
							+ "',student_name='" + fieldStudentName.getText() + "'," + "student_class='"
							+ fieldClass.getText() + "',gender='" + fieldGender.getText() + "',position='"
							+ fieldPosition.getText() + "'," + "regime='" + fieldRegime.getText()
							+ "' where id_number='" + fieldIDNo.getText() + "'");
				} else if (EditClubMemberInfo.this.getTitle().equals("Edit Patriotism Club Information")) {
					AddUpdateDelete("update patriotism_club set " + "id_number='" + fieldIDNo.getText()
							+ "',student_name='" + fieldStudentName.getText() + "'," + "student_class='"
							+ fieldClass.getText() + "',gender='" + fieldGender.getText() + "',position='"
							+ fieldPosition.getText() + "'," + "regime='" + fieldRegime.getText()
							+ "' where id_number='" + fieldIDNo.getText() + "'");
				}
				
				EditClubMemberInfo.this.setVisible(false);
			}
		});
		btnSave.setPreferredSize(btnDim);
		holder.add(btnSave);

		btnCancel = new JButton("Cancel");
		btnCancel.setPreferredSize(btnDim);
		holder.add(btnCancel);

		add(holder);

		setVisible(true);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new EditClubMemberInfo();
	}

	public void AddUpdateDelete(String querries) {

		

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn =  CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Request Executed Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

}
