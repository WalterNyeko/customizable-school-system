package school.ui.account;

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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class MyAccountChangeLoginDetailsForgotPasswordPanel extends JPanel {

	private JLabel labelForgotPassword;
	private JLabel labelEmail;

	private JTextField fieldEmail;

	private JButton btnSend;
	private JButton btnDiscard;

	public MyAccountChangeLoginDetailsForgotPasswordPanel() {
		// TODO Auto-generated constructor stub
		setUpForgotPasswordPanel();
	}

	private void setUpForgotPasswordPanel() {
		
		try {
//			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		setLayout(new FlowLayout(FlowLayout.CENTER));

		Border border = BorderFactory.createRaisedSoftBevelBorder();
		setBorder(border);
		setBorder(new LineBorder(Color.blue, 3));
//		setPreferredSize(new Dimension(1160, 480));
		setBackground(Color.decode("#5f9ea0"));

		setPreferredSize(new Dimension(350, 340));

		labelForgotPassword = new JLabel("Forgot password or user name?");
		labelForgotPassword.setFont(new Font("Times New Roman", Font.BOLD, 16));
		labelForgotPassword.setForeground(Color.white);
		labelForgotPassword.setPreferredSize(new Dimension(300, 30));
		add(labelForgotPassword);

		labelEmail = new JLabel("Email:");
		labelEmail.setPreferredSize(new Dimension(90, 30));
		labelEmail.setFont(new Font("Times New Roman", Font.BOLD, 12));
		labelEmail.setForeground(Color.white);
		add(labelEmail);

		fieldEmail = new JTextField();
		fieldEmail.setPreferredSize(new Dimension(200, 30));
		add(fieldEmail);

		btnSend = new JButton("Create New Password");
		btnSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int len = fieldEmail.getText().length();

				if (fieldEmail.getText().isEmpty() || !(fieldEmail.getText().contains("@"))
						|| !(fieldEmail.getText().contains(".com"))) {
					JOptionPane.showMessageDialog(MyAccountChangeLoginDetailsForgotPasswordPanel.this,
							"Please specify a valid email address in the text field", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {

					JOptionPane.showMessageDialog(MyAccountChangeLoginDetailsForgotPasswordPanel.this,
							"A new password will be sent to this email account");
				}
			}
		});
		add(btnSend);

		btnDiscard = new JButton("Discard");
		btnDiscard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fieldEmail.setText("");
			}
		});
		add(btnDiscard);

	}

}
