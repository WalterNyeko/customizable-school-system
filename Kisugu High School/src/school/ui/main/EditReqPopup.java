package school.ui.main;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class EditReqPopup extends JFrame {

	JDateChooser dc2;

	public JTextField fieldforname;

	public JTextField fieldforQty;

	public JTextField fieldforclass;

	public JTextField fieldforID;

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new EditReqPopup();

	}

	public EditReqPopup() {
		
		
		this.setTitle("Edit Requirement");
		this.setSize(350, 240);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		JPanel thispanel=new JPanel();
		
		
		thispanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,15));
		
		JLabel reqclass=new JLabel("Responsible Class:");
		reqclass.setPreferredSize(new Dimension(150, 25));
		thispanel.add(reqclass);
		
		fieldforclass=new JTextField();
		fieldforclass.setPreferredSize(new Dimension(120, 25));
		thispanel.add(fieldforclass);
		
		
		
		JLabel reqname=new JLabel("Name of Requirement:");
		reqname.setPreferredSize(new Dimension(150, 25));
		thispanel.add(reqname);
		
		fieldforname=new JTextField();
		fieldforname.setPreferredSize(new Dimension(120, 25));
		thispanel.add(fieldforname);
		
		
		
		JLabel reqQty=new JLabel("Quantity of Requirement:");
		reqQty.setPreferredSize(new Dimension(150, 25));
		thispanel.add(reqQty);
		
		fieldforQty=new JTextField();
		fieldforQty.setPreferredSize(new Dimension(120, 25));
		thispanel.add(fieldforQty);
		
		
		JLabel reqID=new JLabel("Requirement ID:");
		reqID.setPreferredSize(new Dimension(150, 25));
		thispanel.add(reqID);
		
		fieldforID=new JTextField();
		fieldforID.setPreferredSize(new Dimension(120, 25));
		fieldforID.setEditable(false);
		thispanel.add(fieldforID);
		
		JButton del=new JButton("Delete");
		del.setPreferredSize(new Dimension(120, 25));
		del.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
                String [] options ={"Delete","Cancel"};
				
				int ans=JOptionPane.showOptionDialog(null, "Are You Sure You Want to Delete This ???",
						"Confirmation of Delete Request", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,options[1]);
				if(ans==0)
				{
				
				
				AddUpdateDelete("delete from requirementslist where ID='"+fieldforID.getText()+"'");
				
				}else{
					
				}
			}
		});
		thispanel.add(del);
		
		
		JButton saveChanges=new JButton("Save Changes");
		saveChanges.setPreferredSize(new Dimension(120, 25));
		saveChanges.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				AddUpdateDelete("update requirementslist set class_requirements='"+fieldforclass.getText()+"',requirement_name='"+fieldforname.getText()+"',standard_quantity='"+fieldforQty.getText()+"' where ID='"+fieldforID.getText()+"'");
			}
		});
		thispanel.add(saveChanges);
		
		/*dc2=new JDateChooser();
		dc2.getDate();
		dc2.setPreferredSize(new Dimension(150,25));
		thispanel.add(dc2);
		*/
		this.setResizable(false);
		this.add(thispanel);
		this.setVisible(true);
		
		
	}

	public void AddUpdateDelete(String querries) {

		String url = "jdbc:mysql://localhost:3306/school";
		String user = "root";
		String pass = "";

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = DriverManager.getConnection(url, user, pass);
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Request Executed Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

}
