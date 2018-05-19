package clarion.students.leader.ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import school.ui.finances.CashBookController;

public class VisitationDay extends JDialog{

	JLabel labelstudentName,labelclass,labelStudentID,labelVisitorsName,labelContact,labelIDType,labelIDNo,labelAddress,labelRship;
	JTextField fiStuName,fiStuClass,fiStuID,fiVisitorName,fiVisitorContact,fiIDtype,fiIDNo,fiAddress,fiRship;
	JButton btnSave;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new VisitationDay();
	}
	
	public VisitationDay() {
		// TODO Auto-generated constructor stub
		setTitle("Students Visitation Information");
		setSize(510, 345);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel panelhome=new JPanel();
		add(panelhome);
		
		labelstudentName=new JLabel("Student Name:");
		labelstudentName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelhome.add(labelstudentName);
		labelstudentName.setPreferredSize(new Dimension(250, 25));
		labelstudentName.setForeground(Color.WHITE);
		
		fiStuName=new JTextField();
		panelhome.add(fiStuName);
		fiStuName.setPreferredSize(new Dimension(200, 25));
		
		panelhome.setBackground(Color.decode("#203B3B"));
		
		labelclass=new JLabel("Student Class:");
		labelclass.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelhome.add(labelclass);
		labelclass.setPreferredSize(new Dimension(250, 25));
		labelclass.setForeground(Color.WHITE);
		
		fiStuClass=new JTextField();
		panelhome.add(fiStuClass);
		fiStuClass.setPreferredSize(new Dimension(200, 25));
		
		
		
		labelStudentID=new JLabel("Student ID Number:");
		labelStudentID.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelhome.add(labelStudentID);
		labelStudentID.setPreferredSize(new Dimension(250, 25));
		labelStudentID.setForeground(Color.WHITE);
		
		
		fiStuID=new JTextField();
		fiStuID.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
				
				Connection myConnection = null;
				
				PreparedStatement myPreparedStatement = null;
				ResultSet myResultSet = null;
				
				try {
					
					myConnection = CashBookController.getConnection();
							
					
					myPreparedStatement = myConnection.prepareStatement("select concat"
							+ "(first_name,' ', middle_name, ' ', last_name) as name, "
							+ "class_joining from tableadmissionstudentdetails"
							+ " where id_number='"+fiStuID.getText()+"'");
					
					
					myResultSet = myPreparedStatement.executeQuery();
					
					while(myResultSet.next()){
						fiStuName.setText(myResultSet.getString(1));
						fiStuClass.setText(myResultSet.getString(2));
						
					}
					
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}finally {
					
					if(myPreparedStatement != null){
						try {
							myPreparedStatement.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if(myConnection != null){
						try {
							myConnection.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		panelhome.add(fiStuID);
		fiStuID.setPreferredSize(new Dimension(200, 25));
		
		
		labelVisitorsName=new JLabel("Visitor's Name:");
		labelVisitorsName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelhome.add(labelVisitorsName);
		labelVisitorsName.setPreferredSize(new Dimension(250, 25));
		labelVisitorsName.setForeground(Color.WHITE);
		

		
		fiVisitorName=new JTextField();
		panelhome.add(fiVisitorName);
		fiVisitorName.setPreferredSize(new Dimension(200, 25));
		
		
		
		labelContact=new JLabel("Visitor's Contact:");
		labelContact.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelhome.add(labelContact);
		labelContact.setPreferredSize(new Dimension(250, 25));
		labelContact.setForeground(Color.WHITE);
		
		
		fiVisitorContact=new JTextField();
		panelhome.add(fiVisitorContact);
		fiVisitorContact.setPreferredSize(new Dimension(200, 25));
		
		
		labelIDType=new JLabel("Visitor's ID Type:");
		labelIDType.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelhome.add(labelIDType);
		labelIDType.setPreferredSize(new Dimension(250, 25));
		labelIDType.setForeground(Color.WHITE);
		
		fiIDtype=new JTextField();
		panelhome.add(fiIDtype);
		fiIDtype.setPreferredSize(new Dimension(200, 25));
		
		
		labelIDNo=new JLabel("Visitor's ID No:");
		labelIDNo.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelhome.add(labelIDNo);
		labelIDNo.setPreferredSize(new Dimension(250, 25));
		labelIDNo.setForeground(Color.WHITE);
//		JLabel labelstudentName,labelclass,labelStudentID,labelVisitorsName,labelContact,labelIDType,labelIDNo,labelAddress,labelRship;
//		JTextField fiStuName,fiStuClass,fiStuID,fiVisitorName,fiVisitorContact,fiIDtype,fiIDNo,fiAddress,fiRship;

		
		fiIDNo=new JTextField();
		panelhome.add(fiIDNo);
		fiIDNo.setPreferredSize(new Dimension(200, 25));
		
		
		labelAddress=new JLabel("Visitor's Address:");
		labelAddress.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelhome.add(labelAddress);
		labelAddress.setPreferredSize(new Dimension(250, 25));
		labelAddress.setForeground(Color.WHITE);
		
		fiAddress=new JTextField();
		panelhome.add(fiAddress);
		fiAddress.setPreferredSize(new Dimension(200, 25));
		
		labelRship=new JLabel("Relationship:");
		labelRship.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelhome.add(labelRship);
		labelRship.setPreferredSize(new Dimension(250, 25));
		labelRship.setForeground(Color.WHITE);
		
		fiRship=new JTextField();
		panelhome.add(fiRship);
		fiRship.setPreferredSize(new Dimension(200, 25));
		
		btnSave=new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
				Connection myConnection = null;
				
				PreparedStatement myPreparedStatement = null;
				
				try {
					myConnection =  CashBookController.getConnection();
					myPreparedStatement = myConnection.prepareStatement("insert into visitation"
							+ "(student_name, student_class, student_id_number, visitor_name, visitor_contact,"
							+ "visitor_id_type, visitor_id_number, visitor_address, visitor_student_relationship)"
							+ "VALUES("
							+ "'"+fiStuName.getText()+"', '"+fiStuClass.getText()+"', '"+fiStuID.getText()+"', '"+fiVisitorName.getText()+"', '"+fiVisitorContact.getText()+"',"
							+ "'"+fiIDtype.getText()+"', '"+fiIDNo.getText()+"', '"+fiAddress.getText()+"', '"+fiRship.getText()+"'"
							+ ")");
					
					
					myPreparedStatement.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Visitation record successfully saved");
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Visitation record not saved");
				} finally {
					if (myPreparedStatement != null) {
						try {
							myPreparedStatement.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					if (myConnection != null) {
						try {
							myConnection.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					
					
				}
				
				
			}
		});
		panelhome.add(btnSave);
		panelhome.setLayout(new FlowLayout(FlowLayout.RIGHT));

		setVisible(true);
	}

}
