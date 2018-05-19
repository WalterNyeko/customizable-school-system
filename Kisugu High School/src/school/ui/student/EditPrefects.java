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

public class EditPrefects extends JFrame {

	public JTextField fieldID;
	public JTextField fieldName;
	JTextField fieldAddress;
	JTextField fieldParentName;
	JTextField fieldParentPhone;
	JTextField fieldParentEmail;
	JTextField fieldOccupation;
	JTextField fieldFeesPayment;
	JTextField fieldFeesBal;
	public JTextField fieldMiddleName;
	public JTextField fieldLastName;
	public JTextField fieldLastPost;
	JTextField fi1;
	JTextField fi2;
	JTextField fi3;
	JTextField fi4;
	JTextField fi5;
	JTextField fi6;
	JTextField fi7;
	JTextField fi8;
	JTextField fi9;
	JTextField f0;
	JTextField fieldforsearch1;
	JTextField fieldforsearch3;
	public JTextField fieldYearOfRegime;

	JLabel head, Regime;
	public JComboBox comboBoxClasses;
	public JComboBox comboBoxGender;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new EditPrefects();

	}

	public EditPrefects() {
		// TODO Auto-generated constructor stub
		setTitle("Add Prefect's Details");
		setSize(510, 310);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		JPanel homepanel = new JPanel();
		add(homepanel);
		homepanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		homepanel.setBackground(Color.decode("#7f8c8d"));
		JPanel panelfordetails = new JPanel();

		head = new JLabel("Prefect's Details");
		homepanel.add(head);
		head.setForeground(Color.WHITE);
		panelfordetails.setBorder(new LineBorder(Color.WHITE, 3));
		panelfordetails.setBackground(Color.decode("#7f8c8d"));

		Dimension dimfields = new Dimension(200, 25);

		String html = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit Prefect's ID" + "</font>" + "</p>" + "</html>";

		fieldID = new JTextField();
		panelfordetails.add(fieldID);
		fieldID.setToolTipText(html);
		fieldID.setPreferredSize(dimfields);
		fieldID.setText("Student ID:");
		
		

		String htmlStudFName = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit Prefect's First Name" + "</font>" + "</p>" + "</html>";

		fieldName = new JTextField();
		panelfordetails.add(fieldName);
		fieldName.setText("First Name:");
		

		fieldName.setToolTipText(htmlStudFName);
		fieldName.setPreferredSize(dimfields);

		String htmlStudMName = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit Prefect's Middle Name" + "</font>" + "</p>" + "</html>";

		fieldMiddleName = new JTextField();
		panelfordetails.add(fieldMiddleName);
		fieldMiddleName.setToolTipText(htmlStudMName);
		fieldMiddleName.setPreferredSize(dimfields);
		fieldMiddleName.setText("Middle Name:");
		

		String htmlStudLName = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit Prefect's Last Name" + "</font>" + "</p>" + "</html>";

		fieldLastName = new JTextField();
		panelfordetails.add(fieldLastName);
		fieldLastName.setToolTipText(htmlStudLName);
		fieldLastName.setPreferredSize(dimfields);
		fieldLastName.setText("Last Name:");
		

		String htmlclass = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit Prefect's Class" + "</font>" + "</p>" + "</html>";

		String[] classes = { "S.1", "S.2", "S.3", "S.4", "S.5 Arts", "S.5 Sci", "S.6 Arts", "S.6 Sci" };

		comboBoxClasses = new JComboBox(classes);
		// comboBoxClasses.setPreferredSize(new Dimension(80, 20));
		panelfordetails.add(comboBoxClasses);
		comboBoxClasses.setToolTipText(htmlclass);
		comboBoxClasses.setPreferredSize(dimfields);

		String htmlgender = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit Prefect's Gender" + "</font>" + "</p>" + "</html>";

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
				+ "size=\"5\" face=\"Times New Roman\">Edit Prefect's Date Of Birth" + "</font>" + "</p>" + "</html>";

		fieldYearOfRegime = new JTextField();

		panelfordetails.add(fieldYearOfRegime);
		fieldYearOfRegime.setToolTipText(htmldob);
		fieldYearOfRegime.setPreferredSize(dimfields);
		fieldYearOfRegime.setText("year of Regime");
		

		String htmlStudPost = "<html>" + "<p>" + "<font color=\"#27ae60\" "
				+ "size=\"5\" face=\"Times New Roman\">Edit Prefect's Post" + "</font>" + "</p>" + "</html>";

		fieldLastPost = new JTextField();
		panelfordetails.add(fieldLastPost);
		fieldLastPost.setToolTipText(htmlStudPost);
		fieldLastPost.setPreferredSize(dimfields);
		fieldLastPost.setText("Post:");
		
		JButton btnsafe = new JButton("Save Changes");
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

				AddUpdateDelete("update prefects set first_name='"+firstName+"',"
						+ "middle_name='"+middleName+"', last_name='"+lastName+"',student_class='"+studentClass+"',"
						+ "gender='"+gender+"',year_of_regime='"+year+"',post='"+post+"' where id_number='"+id+"'");

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
