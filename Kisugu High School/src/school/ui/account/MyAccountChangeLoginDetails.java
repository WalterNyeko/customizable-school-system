
package school.ui.account;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.easynth.lookandfeel.EaSynthLookAndFeel;
import com.sun.stylesheet.css.parser.ParseException;

import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import school.ui.finances.CashBookController;

public class MyAccountChangeLoginDetails extends JPanel {

	private JLabel labelPicture;
	private JLabel labelName;

	private JLabel labelCurrentUserName;
	private JLabel labelCurrentPassword;

	private JLabel labelNewUserName;
	private JLabel labelNewPassword;

	private JLabel labelReEnterNewPassword;

	private JTextField fieldCurrentUserName;
	private JPasswordField fieldCurrentPassword;

	private JTextField fieldNewUserName;
	private JPasswordField fieldNewPassword;

	private JPasswordField fieldReEnteredNewPassword;

	private JButton btnSaveChanges;
	private JButton btnDiscard;
	private JButton btnForgotPassword;

	private JPanel panelToHoldTopLabels;
	private JPanel panelToHoldLowerComponents;
	// private JPanel panelEastern;

	private JPanel panelInsideCenterPanelToHoldLowerComponents;

	private MyAccountChangeLoginDetailsForgotPasswordPanel myAccountChangeLoginDetailsForgotPasswordPanel;

	public MyAccountChangeLoginDetails() {
		setUpLoginChanger();

	}

	private void setUpLoginChanger() {
		try {
//			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

		setLayout(new BorderLayout());
		setBorder(new LineBorder(Color.blue, 3));
		setPreferredSize(new Dimension(1160, 480));
		setBackground(Color.decode("#5f9ea0"));

		Border border = BorderFactory.createRaisedSoftBevelBorder();
//		setBorder(border);
		
		
		/////// font color for labels, white
		Color colorLabels = Color.black;

		////// font size for Labels
		Font fontLabels = new Font("Times New Roman", Font.BOLD, 12);

		panelToHoldTopLabels = new JPanel();

		panelToHoldTopLabels.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(panelToHoldTopLabels, BorderLayout.NORTH);

		labelName = new JLabel("");
		labelName.setFont(fontLabels);
		labelName.setForeground(colorLabels);
		panelToHoldTopLabels.add(labelName);

		labelPicture = new JLabel("Picture");
		labelPicture.setBorder(new LineBorder(Color.RED, 2));
		labelPicture.setPreferredSize(new Dimension(100, 100));
		panelToHoldTopLabels.add(labelPicture);

		///// lower components below the picture//

		panelInsideCenterPanelToHoldLowerComponents = new JPanel();
		Border borderInside = BorderFactory.createRaisedSoftBevelBorder();
		panelInsideCenterPanelToHoldLowerComponents.setBorder(borderInside);
		panelInsideCenterPanelToHoldLowerComponents.setPreferredSize(new Dimension(500, 340));

		panelToHoldLowerComponents = new JPanel();
		panelToHoldLowerComponents.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(panelToHoldLowerComponents, BorderLayout.CENTER);


		panelToHoldLowerComponents.add(panelInsideCenterPanelToHoldLowerComponents);
		
		
		////// panel forgot password/////

		myAccountChangeLoginDetailsForgotPasswordPanel = new MyAccountChangeLoginDetailsForgotPasswordPanel();
		panelToHoldLowerComponents.add(myAccountChangeLoginDetailsForgotPasswordPanel);
		////////////////////////////////

		

		Dimension dimensionFields = new Dimension(200, 30);
		Dimension dimensionLabels = new Dimension(200, 30);

		labelCurrentUserName = new JLabel("Current User Name:");
		labelCurrentUserName.setFont(fontLabels);
		labelCurrentUserName.setForeground(colorLabels);
		labelCurrentUserName.setPreferredSize(dimensionLabels);
		panelInsideCenterPanelToHoldLowerComponents.add(labelCurrentUserName);

		fieldCurrentUserName = new JTextField();
		fieldCurrentUserName.setPreferredSize(dimensionFields);
		panelInsideCenterPanelToHoldLowerComponents.add(fieldCurrentUserName);

		labelCurrentPassword = new JLabel("Enter Current Password:");
		labelCurrentPassword.setFont(fontLabels);
		labelCurrentPassword.setForeground(colorLabels);
		labelCurrentPassword.setPreferredSize(dimensionLabels);
		panelInsideCenterPanelToHoldLowerComponents.add(labelCurrentPassword);

		fieldCurrentPassword = new JPasswordField();
		fieldCurrentPassword.setPreferredSize(dimensionFields);
		panelInsideCenterPanelToHoldLowerComponents.add(fieldCurrentPassword);

		labelNewUserName = new JLabel("Enter New User Name:");
		labelNewUserName.setFont(fontLabels);
		labelNewUserName.setForeground(colorLabels);
		labelNewUserName.setPreferredSize(dimensionLabels);
		panelInsideCenterPanelToHoldLowerComponents.add(labelNewUserName);

		fieldNewUserName = new JTextField();
		fieldNewUserName.setPreferredSize(dimensionFields);
		panelInsideCenterPanelToHoldLowerComponents.add(fieldNewUserName);

		labelNewPassword = new JLabel("Enter New Password:");
		labelNewPassword.setFont(fontLabels);
		labelNewPassword.setForeground(colorLabels);
		labelNewPassword.setPreferredSize(dimensionLabels);
		panelInsideCenterPanelToHoldLowerComponents.add(labelNewPassword);

		fieldNewPassword = new JPasswordField();
		fieldNewPassword.setPreferredSize(dimensionFields);
		panelInsideCenterPanelToHoldLowerComponents.add(fieldNewPassword);

		labelReEnterNewPassword = new JLabel("Re-Enter New Password:");
		labelReEnterNewPassword.setFont(fontLabels);
		labelReEnterNewPassword.setPreferredSize(dimensionLabels);
		labelReEnterNewPassword.setForeground(colorLabels);
		panelInsideCenterPanelToHoldLowerComponents.add(labelReEnterNewPassword);

		fieldReEnteredNewPassword = new JPasswordField();
		fieldReEnteredNewPassword.setPreferredSize(dimensionFields);
		panelInsideCenterPanelToHoldLowerComponents.add(fieldReEnteredNewPassword);

		//// font for the buttons
		Font fontBtns = new Font("Times New Roman", Font.BOLD, 11);

		btnSaveChanges = new JButton("Save Changes");
		
		btnSaveChanges.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
				AddUpdateDeleteSil("update application_users set username='" + fieldNewUserName.getText()
						+ "', password='" + fieldNewPassword.getText() + "' where username='"
						+ fieldCurrentUserName.getText()+"'");
				AddUpdateDeleteSil("update chat_messenger set message_sender='" + fieldNewUserName.getText()
						+ "' where message_sender='" + fieldCurrentUserName.getText()
						+ "'");
				AddUpdateDelete("update chat_messenger set message_receiver='" + fieldNewUserName.getText()
				+ "' where message_receiver='" + fieldCurrentUserName.getText()
				+ "'");
			
			}
		});
		btnSaveChanges.setFont(fontBtns);
		panelInsideCenterPanelToHoldLowerComponents.add(btnSaveChanges);

		btnDiscard = new JButton("Discard Changes");
		btnDiscard.setFont(fontBtns);
		panelInsideCenterPanelToHoldLowerComponents.add(btnDiscard);

		btnForgotPassword = new JButton("Forgot Password?");
		btnForgotPassword.setFont(fontBtns);
		// panelInsideCenterPanelToHoldLowerComponents.add(btnForgotPassword);

	}

	public JPanel getPanelToHoldTopLabels() {
		return panelToHoldTopLabels;
	}

	public JPanel getPanelToHoldLowerComponents() {
		return panelToHoldLowerComponents;
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
	
	public void AddUpdateDeleteSil(String querries) {

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
}
