package school.ui.account;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;

import com.toedter.calendar.JYearChooser;

public class EmailNotificationPanel extends JPanel{
	
	
	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_HOST_PORT = 465;
    private static final String SMTP_AUTH_USER = "nyekowalter69@gmail.com";
    private static final String SMTP_AUTH_PWD  = "CATRINAH";


	private JTextArea paneEmailNotification,paneSubject,paneRecipient,paneCc;
	private JScrollPane scrollerEmailNotification,scrollerRecipient,scrollerSubject,scrollerCc;
	private JButton btnNotifyParents,btnNotifyTeachers,btnViewEmailNotificationHistory,btnSettings;
	private JPanel rightPanel,leftpanel;
	private JLabel emailbody,emailRecipient,emailSubject,emailCc,chooseYear;
	private JYearChooser yearChooser;
	private JComboBox comboBoxClass;
	
	 String subject;
	 String receiver;
	 String carboncopy;
	 String bodyofmessage;
	 
	 private ConfigUtility configUtil = new ConfigUtility();
	

	public static void main(String[] args) {

		new EmailNotificationPanel();
	}
	public EmailNotificationPanel() {

        setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JToolBar toolbar=new JToolBar();
		toolbar.setPreferredSize(new Dimension(700, 30));
		
		
		Action boldAction = new BoldAction();
	    boldAction.putValue(Action.NAME, "Bold");
	    toolbar.add(boldAction);
	    
	    Action italicAction = new ItalicAction();
	    italicAction.putValue(Action.NAME, "Italic");
	    toolbar.add(italicAction);

	    Action foregroundAction = new ForegroundAction();
	    foregroundAction.putValue(Action.NAME, "Font Color");
	    toolbar.add(foregroundAction);

	    Action formatTextAction = new FontAndSizeAction();
	    formatTextAction.putValue(Action.NAME, "Font and Size");
	    toolbar.add(formatTextAction);
	    
	    
	    leftpanel=new JPanel();
	    leftpanel.setPreferredSize(new Dimension(700, 430));
	    leftpanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,5));
		add(leftpanel);
		
		leftpanel.add(toolbar);
		
		
		emailSubject=new JLabel("Email Subject:");
		emailSubject.setFont(new Font("Times New Roman", Font.BOLD, 16));
		emailSubject.setForeground(Color.blue);
		leftpanel.add(emailSubject);
		
		
		paneSubject=new JTextArea();
		scrollerSubject=new JScrollPane(paneSubject);
		scrollerSubject.setPreferredSize(new Dimension(700, 30));
		leftpanel.add(scrollerSubject);
		
		
		emailRecipient=new JLabel("Send To:");
		emailRecipient.setFont(new Font("Times New Roman", Font.BOLD, 16));
		emailRecipient.setForeground(Color.blue);
		leftpanel.add(emailRecipient);
		
		
		paneRecipient=new JTextArea();
		scrollerRecipient=new JScrollPane(paneRecipient);
		scrollerRecipient.setPreferredSize(new Dimension(700, 30));
		leftpanel.add(scrollerRecipient);
		
		emailCc=new JLabel("Send Cc To:");
		emailCc.setFont(new Font("Times New Roman", Font.BOLD, 16));
		emailCc.setForeground(Color.blue);
		leftpanel.add(emailCc);
		
		paneCc=new JTextArea();
		scrollerCc=new JScrollPane(paneCc);
		scrollerCc.setPreferredSize(new Dimension(700, 70));
		leftpanel.add(scrollerCc);
		
		emailbody=new JLabel("Email Body:");
		emailbody.setFont(new Font("Times New Roman", Font.BOLD, 16));
		emailbody.setForeground(Color.blue);
		leftpanel.add(emailbody);
		
		paneEmailNotification=new JTextArea();
		paneEmailNotification.setBackground(Color.WHITE);
		scrollerEmailNotification=new JScrollPane(paneEmailNotification);
		scrollerEmailNotification.setPreferredSize(new Dimension(700, 110));
		leftpanel.add(scrollerEmailNotification);
		
		
		
		
		rightPanel=new JPanel();
		rightPanel.setPreferredSize(new Dimension(250, 400));
		rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER,2,15));
		add(rightPanel);
		
		Dimension dimBTN=new Dimension(200, 30);
		
		chooseYear=new JLabel("Choose Year:");
		chooseYear.setFont(new Font("Times New Roman", Font.BOLD, 16));
		chooseYear.setPreferredSize(dimBTN);
		chooseYear.setForeground(Color.blue);
		rightPanel.add(chooseYear);
		
		yearChooser=new JYearChooser();
		yearChooser.getYear();
		yearChooser.setPreferredSize(dimBTN);
		rightPanel.add(yearChooser);
		
		
		String[] classes={"Choose Class","S1","S2","S3","S4","S5 Arts","S5 Sci","S6 Arts","S6 Sci","All Classes"};
		comboBoxClass=new JComboBox<>(classes);
		comboBoxClass.setPreferredSize(dimBTN);
		rightPanel.add(comboBoxClass);
		
	
		btnNotifyParents=new JButton("Send Email To Parents");
		btnNotifyParents.setPreferredSize(dimBTN);
		btnNotifyParents.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				buttonSendActionPerformed(event);

			}
		});
		rightPanel.add(btnNotifyParents);
		
		btnNotifyTeachers=new JButton("Send Email To Teachers");
		btnNotifyTeachers.setPreferredSize(dimBTN);
		btnNotifyTeachers.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {

				buttonSendActionPerformed(event);
			}
		});
		rightPanel.add(btnNotifyTeachers);
		
		btnViewEmailNotificationHistory=new JButton("View Email Notice Record");
		btnViewEmailNotificationHistory.setPreferredSize(dimBTN);
		rightPanel.add(btnViewEmailNotificationHistory);
		
		btnSettings=new JButton("My Email Settings");
		btnSettings.setPreferredSize(dimBTN);
		btnSettings.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setupMenu();
				
			}
		});
		rightPanel.add(btnSettings);
		
		
	
	}
	private void buttonSendActionPerformed(ActionEvent event) 
    {
        if (!validateFields()) {
            return;
        }
         
        receiver=paneRecipient.getText();
		bodyofmessage=paneEmailNotification.getText();
		subject=paneSubject.getText();
		carboncopy=paneCc.getText();
		 
       
        try {
        	test();
             
            JOptionPane.showMessageDialog(null,"The Selected Recepients Received The Email","E-mail sent successfully!",JOptionPane.INFORMATION_MESSAGE);
             
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "The System Experienced An Error While Sending The Message: " + ex.getMessage(),
                    "Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
    }

	 private boolean validateFields() {
	        if (paneSubject.getText().equals("")) {
	            JOptionPane.showMessageDialog(this,
	                    "Please Enter The Email Address(s)!",
	                    "Email(s) Missing", JOptionPane.ERROR_MESSAGE);
	            paneSubject.requestFocus();
	            return false;
	        }
	         
	        if (paneRecipient.getText().equals("")) {
	            JOptionPane.showMessageDialog(this,
	                    "Please Write The E-Mail Subject!",
	                    "Subject Missing", JOptionPane.ERROR_MESSAGE);
	            paneRecipient.requestFocus();
	            return false;
	        }
	         
	        if (paneEmailNotification.getText().equals("")) {
	            JOptionPane.showMessageDialog(this,
	                    "Please Write The Message To The Recipient!",
	                    "Message Missing", JOptionPane.ERROR_MESSAGE);
	            paneEmailNotification.requestFocus();
	            return false;
	        }
	         
	        return true;
	    }
		 
	 
	 
	 private void setupMenu() {
	        btnSettings.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	
	               // SettingsDialog dialog = new SettingsDialog(EmailNotificationPanel.class, configUtil);
	               // dialog.setVisible(true);
	            }
	        });
	         
	        
	        
	          }
	 public void test() throws Exception{
	        Properties props = new Properties();

	        props.put("mail.transport.protocol", "smtps");
	        props.put("mail.smtps.host", SMTP_HOST_NAME);
	        props.put("mail.smtps.auth", "true");

	        Session mailSession = Session.getDefaultInstance(props);
	        mailSession.setDebug(true);
	        Transport transport = mailSession.getTransport();

	        MimeMessage message = new MimeMessage(mailSession);
	        message.setSubject(subject);
	        message.setContent(bodyofmessage, "text/plain");

	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
	        message.addRecipients(Message.RecipientType.CC, 
	                InternetAddress.parse(carboncopy));
	        
	        
	        transport.connect
	          (SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

	        transport.sendMessage(message,
	            message.getRecipients(Message.RecipientType.TO));
	        
	        transport.sendMessage(message,
	                message.getRecipients(Message.RecipientType.CC));
	        transport.close();
	    }
}


