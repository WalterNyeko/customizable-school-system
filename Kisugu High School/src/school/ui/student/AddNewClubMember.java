package school.ui.student;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import school.ui.finances.CashBookController;

public class AddNewClubMember extends JFrame {

	private JLabel labelIDNo, labelStudentName, labelClass, labelGender, labelPosition, labelRegime;
	private JTextField fieldIDNo, fieldStudentName, fieldClass, fieldGender, fieldPosition, fieldRegime;
	private JPanel holder;
	private JButton btnAdd, btnCancel;

	public AddNewClubMember() {
		// TODO Auto-generated constructor stub

		setTitle("Add New Club Member");

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

		Dimension btnDim = new Dimension(100, 25);

		btnAdd = new JButton("Add");
		btnAdd.setPreferredSize(btnDim);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (AddNewClubMember.this.getTitle().equals("Add Debator's Club Information")) {

					AddUpdateDelete(
							"insert into debators( id_number,student_name,student_class,gender,position,regime) "
									+ "values('" + fieldIDNo.getText() + "','" + fieldStudentName.getText() + "'," + "'"
									+ fieldClass.getText() + "','" + fieldGender.getText() + "','"
									+ fieldPosition.getText() + "'," + "'" + fieldRegime.getText() + "')");
				} else if (AddNewClubMember.this.getTitle().equals("Add Writer's Club Information")) {
					AddUpdateDelete(
							"insert into writersclub( id_number,student_name,student_class,gender,position,regime) "
									+ "values('" + fieldIDNo.getText() + "','" + fieldStudentName.getText() + "'," + "'"
									+ fieldClass.getText() + "','" + fieldGender.getText() + "','"
									+ fieldPosition.getText() + "'," + "'" + fieldRegime.getText() + "')");
				} else if (AddNewClubMember.this.getTitle().equals("Add Interact Club's Information")) {
					AddUpdateDelete(
							"insert into interact_club( id_number,student_name,student_class,gender,position,regime) "
									+ "values('" + fieldIDNo.getText() + "','" + fieldStudentName.getText() + "'," + "'"
									+ fieldClass.getText() + "','" + fieldGender.getText() + "','"
									+ fieldPosition.getText() + "'," + "'" + fieldRegime.getText() + "')");
				} else if (AddNewClubMember.this.getTitle().equals("Add Wild Life Club's Information")) {
					AddUpdateDelete(
							"insert into wild_life_club( id_number,student_name,student_class,gender,position,regime) "
									+ "values('" + fieldIDNo.getText() + "','" + fieldStudentName.getText() + "'," + "'"
									+ fieldClass.getText() + "','" + fieldGender.getText() + "','"
									+ fieldPosition.getText() + "'," + "'" + fieldRegime.getText() + "')");
				} else if (AddNewClubMember.this.getTitle().equals("Add Scouts And Guides Club Information")) {
					AddUpdateDelete(
							"insert into scouts_and_guides( id_number,student_name,student_class,gender,position,regime) "
									+ "values('" + fieldIDNo.getText() + "','" + fieldStudentName.getText() + "'," + "'"
									+ fieldClass.getText() + "','" + fieldGender.getText() + "','"
									+ fieldPosition.getText() + "'," + "'" + fieldRegime.getText() + "')");
				} else if (AddNewClubMember.this.getTitle().equals("Add Scripture Union Club's Information")) {
					AddUpdateDelete(
							"insert into scripture_union( id_number,student_name,student_class,gender,position,regime) "
									+ "values('" + fieldIDNo.getText() + "','" + fieldStudentName.getText() + "'," + "'"
									+ fieldClass.getText() + "','" + fieldGender.getText() + "','"
									+ fieldPosition.getText() + "'," + "'" + fieldRegime.getText() + "')");
				} else if (AddNewClubMember.this.getTitle().equals("Add Games And Sports Club's Information")) {
					AddUpdateDelete(
							"insert into games_and_sports( id_number,student_name,student_class,gender,position,regime) "
									+ "values('" + fieldIDNo.getText() + "','" + fieldStudentName.getText() + "'," + "'"
									+ fieldClass.getText() + "','" + fieldGender.getText() + "','"
									+ fieldPosition.getText() + "'," + "'" + fieldRegime.getText() + "')");
				} else if (AddNewClubMember.this.getTitle().equals("Add Patriotism Club's Information")) {
					AddUpdateDelete(
							"insert into patriotism_club( id_number,student_name,student_class,gender,position,regime) "
									+ "values('" + fieldIDNo.getText() + "','" + fieldStudentName.getText() + "'," + "'"
									+ fieldClass.getText() + "','" + fieldGender.getText() + "','"
									+ fieldPosition.getText() + "'," + "'" + fieldRegime.getText() + "')");
				}

				AddNewClubMember.this.setVisible(false);
				
			}
		});
		holder.add(btnAdd);

		btnCancel = new JButton("Cancel");
		btnCancel.setPreferredSize(btnDim);
		holder.add(btnCancel);
		btnCancel.addActionListener(e -> {
			this.setVisible(false);
		});

		add(holder);

		setVisible(true);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AddNewClubMember();
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
