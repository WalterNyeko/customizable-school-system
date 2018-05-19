package school.ui.student;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.toedter.calendar.JDateChooser;

import school.ui.finances.CashBookController;

public class DiscontinueStudent extends JDialog{

	JLabel reason,duration,dateofsuspension;
	JTextArea areaReason;
	JButton btnSave;
	JDateChooser dcDateofSusp;
	JTextField fieldDuration;
	
	private JLabel idLabel;
	public JTextField fieldID;
	public JLabel classLabel;
	private Connection conn;
	private PreparedStatement pst;
	protected Calendar cal;
	protected String year;
	protected String thisyear;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new DiscontinueStudent();
	}
	
	public DiscontinueStudent() {
		// TODO Auto-generated constructor stub
		
		setTitle("Students Information");
		setSize(500, 360);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		JPanel panelhome=new JPanel();
		add(panelhome);

		idLabel=new JLabel("Student ID No.");
		idLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelhome.add(idLabel);
		idLabel.setPreferredSize(new Dimension(200, 25));
		idLabel.setForeground(Color.WHITE);
		
		fieldID=new JTextField();
		panelhome.add(fieldID);
		fieldID.setEditable(false);
		fieldID.setPreferredSize(new Dimension(120, 25));
		
		classLabel=new JLabel("");
		classLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelhome.add(classLabel);
		classLabel.setPreferredSize(new Dimension(90, 25));
		
		
		
		reason=new JLabel("Reason For Discontinuing");
		reason.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelhome.add(reason);
		reason.setPreferredSize(new Dimension(250, 25));
		reason.setForeground(Color.WHITE);
		
		areaReason=new JTextArea();
		areaReason.setLineWrap(true);
		areaReason.setWrapStyleWord(true);
		
		
		
		JScrollPane paneforarea=new JScrollPane(areaReason);
		panelhome.add(paneforarea);
		paneforarea.setPreferredSize(new Dimension(480, 200));
		Border boda=BorderFactory.createRaisedBevelBorder();
		paneforarea.setBorder(boda);
		
		dateofsuspension=new JLabel("Date Of Discontinuing");
		panelhome.add(dateofsuspension);
		dateofsuspension.setPreferredSize(new Dimension(195, 25));
		dateofsuspension.setForeground(Color.WHITE);
		
		dcDateofSusp=new JDateChooser();
		dcDateofSusp.getDate();
		panelhome.add(dcDateofSusp);
		dcDateofSusp.setPreferredSize(new Dimension(210, 25));
		
		
		
		btnSave=new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
				Connection myConnection = null;
				
				PreparedStatement myPreparedStatement = null;
				
				try {
					myConnection =  CashBookController.getConnection();
					 cal=Calendar.getInstance();
					 year=""+cal.getTime();
					 thisyear=year.substring(year.length()-4);
					myPreparedStatement = myConnection.prepareStatement("insert into dismissal_record"
							+ "(id_number,reason,date,term,year)"
							+ ""
							+ ""
							+ "VALUES("
							+ "'"+fieldID.getText()+"', "
							+ "'"+areaReason.getText()+"',"
							+ "'"+convertFromUtilToSQLDate(dcDateofSusp.getDate())+"','"+getTitle()+"','"+thisyear+"'"
							+ ")");
					
					
					myPreparedStatement.executeUpdate();
					
					AddUpdateDelete("update `"+classLabel.getText()+"` set discipline_status='discontinued' where payment_code='"+fieldID.getText()+"'");
					JOptionPane.showMessageDialog(null, "Dismissal record successfully saved");
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Dismissal record not saved");
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
		
		panelhome.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelhome.setBackground(Color.decode("#203B3B"));
		
		this.setResizable(false);
		
		
		setVisible(true);
	}
	 public Date convertFromUtilToSQLDate(java.util.Date dateUtil){
			
			if(dateUtil!=null){
				java.sql.Date sqlDate=new java.sql.Date(dateUtil.getTime());
				
				return sqlDate;
			}else{
			return null;
			}
		}
	 public void AddUpdateDelete(String querries) {

			try {

				java.sql.Connection conn = null;
				java.sql.PreparedStatement pst = null;
				conn = CashBookController.getConnection();
				pst = conn.prepareStatement(querries);

				pst.executeUpdate();


			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

			}
		}
	 
	 
	 public void displayInTitle(String query) {
			try {
				conn = CashBookController.getConnection();
				pst = conn.prepareStatement(query);

				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					this.setTitle(rs.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (conn != null) {

					try {
						conn.close();
						pst.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		}
	
}
