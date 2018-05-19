package school.ui.student;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.border.LineBorder;

import school.ui.finances.CashBookController;

public class AddNewUNSA extends JFrame {

	JTextField fieldID, fieldName, fieldAddress, fieldParentName, fieldParentPhone, fieldParentEmail, fieldOccupation,
			fieldFeesPayment, fieldFeesBal, fieldMiddleName, fieldLastName, fieldLastPost, fi1, fi2, fi3, fi4, fi5, fi6,
			fi7, fi8, fi9, f0, fieldforsearch1, fieldforsearch3;
	private JTextField fieldYearOfRegime;

	JLabel head, Regime;
	private JComboBox comboBoxClasses;
	private JComboBox comboBoxGender;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AddNewUNSA();

	}

	public AddNewUNSA() {
		// TODO Auto-generated constructor stub
		setTitle("Add UNSA's Details");
		setSize(510, 310);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		JPanel homepanel = new JPanel();
		add(homepanel);
		homepanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		homepanel.setBackground(Color.decode("#7f8c8d"));
		JPanel panelfordetails = new JPanel();

		head = new JLabel("UNSA's Details");
		homepanel.add(head);
		head.setForeground(Color.WHITE);
		panelfordetails.setBorder(new LineBorder(Color.WHITE, 3));
		panelfordetails.setBackground(Color.decode("#7f8c8d"));

		Dimension dimfields = new Dimension(200, 25);

		String html = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit UNSA's ID" + "</font>" + "</p>" + "</html>";

		fieldID = new JTextField();
		panelfordetails.add(fieldID);
		fieldID.setToolTipText(html);
		fieldID.setPreferredSize(dimfields);
		fieldID.setText("Student ID:");
		fieldID.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
				
				

				Connection myConnection = null;
				PreparedStatement myPreparedStatement = null;
				
				String sqlStatement ="Select first_name,middle_name,last_name,class_joining,gender from tableadmissionstudentdetails where id_number='"+fieldID.getText()+"'";
				
				try{
					
					myConnection =  CashBookController.getConnection();
					myPreparedStatement=myConnection.prepareStatement(sqlStatement);
					ResultSet resultSet=myPreparedStatement.executeQuery();
					
					if(resultSet.next()){
						fieldName.setText(null);
						fieldName.setText(resultSet.getString(1));
						fieldMiddleName.setText(null);
						fieldMiddleName.setText(resultSet.getString(2));
						fieldLastName.setText(null);
						fieldLastName.setText(resultSet.getString(3));
						comboBoxClasses.setSelectedItem(resultSet.getString(4));
						comboBoxGender.setSelectedItem(resultSet.getString(5));
						
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		

		String htmlStudFName = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit UNSA's First Name" + "</font>" + "</p>" + "</html>";

		fieldName = new JTextField();
		panelfordetails.add(fieldName);
		fieldName.setText("First Name:");
		

		fieldName.setToolTipText(htmlStudFName);
		fieldName.setPreferredSize(dimfields);

		String htmlStudMName = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit UNSA's Middle Name" + "</font>" + "</p>" + "</html>";

		fieldMiddleName = new JTextField();
		panelfordetails.add(fieldMiddleName);
		fieldMiddleName.setToolTipText(htmlStudMName);
		fieldMiddleName.setPreferredSize(dimfields);
		fieldMiddleName.setText("Middle Name:");
		

		String htmlStudLName = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit UNSA's Last Name" + "</font>" + "</p>" + "</html>";

		fieldLastName = new JTextField();
		panelfordetails.add(fieldLastName);
		fieldLastName.setToolTipText(htmlStudLName);
		fieldLastName.setPreferredSize(dimfields);
		fieldLastName.setText("Last Name:");
		

		String htmlclass = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit UNSA's Class" + "</font>" + "</p>" + "</html>";

		String[] classes = { "S.1", "S.2", "S.3", "S.4", "S.5 Arts", "S.5 Sci", "S.6 Arts", "S.6 Sci" };

		comboBoxClasses = new JComboBox(classes);
		// comboBoxClasses.setPreferredSize(new Dimension(80, 20));
		panelfordetails.add(comboBoxClasses);
		comboBoxClasses.setToolTipText(htmlclass);
		comboBoxClasses.setPreferredSize(dimfields);

		String htmlgender = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit UNSA's Gender" + "</font>" + "</p>" + "</html>";

		String[] gender = { "Male", "Female" };

		comboBoxGender = new JComboBox(gender);
		// comboBox1.setPreferredSize(new Dimension(80, 20));
		panelfordetails.add(comboBoxGender);
		comboBoxGender.setToolTipText(htmlgender);
		comboBoxGender.setPreferredSize(dimfields);

		Regime = new JLabel("Year of Regime:");
		panelfordetails.add(Regime);
		Regime.setPreferredSize(dimfields);
		Regime.setForeground(Color.WHITE);

		String htmldob = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit UNSA's Date Of Birth" + "</font>" + "</p>" + "</html>";

		fieldYearOfRegime = new JTextField();

		panelfordetails.add(fieldYearOfRegime);
		fieldYearOfRegime.setToolTipText(htmldob);
		fieldYearOfRegime.setPreferredSize(dimfields);
		fieldYearOfRegime.setText("year of Regime");
		

		String htmlStudPost = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit UNSA's Post" + "</font>" + "</p>" + "</html>";

		fieldLastPost = new JTextField();
		panelfordetails.add(fieldLastPost);
		fieldLastPost.setToolTipText(htmlStudPost);
		fieldLastPost.setPreferredSize(dimfields);
		fieldLastPost.setText("Post:");
		
		JButton btnsafe = new JButton("Save");
		panelfordetails.add(btnsafe);
		btnsafe.setPreferredSize(new Dimension(90, 25));
		btnsafe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				String id = fieldID.getText();
				String firstName = fieldName.getText();
				String middleName = fieldMiddleName.getText();
				String lastName = fieldLastName.getText();
				String studentClass = comboBoxClasses.getSelectedItem().toString();
				String gender = comboBoxGender.getSelectedItem().toString();
				String year = fieldYearOfRegime.getText();
				String post = fieldLastPost.getText();

				AddUpdateDelete("insert into unsa values('"+id+"','"+firstName+"','"+middleName+"',"
						+ "'"+lastName+"','"+studentClass+"','"+gender+"','"+year+"','"+post+"')");	
					
					fieldID.setText("Student ID:");
					fieldName.setText("First Name:");
					fieldMiddleName.setText("Middle Name:");
					fieldLastName.setText("Last Name:");
					fieldYearOfRegime.setText("Year of Regime:");
					fieldLastPost.setText("Post:");
				
			}
		});

		JButton btnexit = new JButton("Exit");
		panelfordetails.add(btnexit);
		btnexit.setPreferredSize(new Dimension(90, 25));

		homepanel.add(panelfordetails);
		panelfordetails.setLayout(new FlowLayout(FlowLayout.LEFT, 23, 15));
		panelfordetails.setPreferredSize(new Dimension(480, 220));

		setVisible(true);
	}
	 public void AddUpdateDelete(String querries){
			
			
			
			try{
				
				java.sql.Connection conn=null;
				java.sql.PreparedStatement pst=null;
				conn= CashBookController.getConnection();
				pst=conn.prepareStatement(querries);
				
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Request Executed Successfully");
				
				
				
			}catch(Exception ex){
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Request Not Executed Successfully "+ex.getMessage());
				
			}
		}


}
