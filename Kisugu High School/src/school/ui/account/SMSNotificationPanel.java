package school.ui.account;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;

import com.toedter.calendar.JYearChooser;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class SMSNotificationPanel extends JPanel{

	private JTextPane paneSMSNotification,paneSubject,paneRecipient;
	private JScrollPane scrollerSMSNotification,scrollerRecipient,scrollerSubject;
	private JButton btnNotifyParents,btnNotifyTeachers,btnViewSMSNotificationHistory;
	private JPanel rightpanel,leftpanel;
	private JLabel smsbody,smsRecipient,smsSubject,chooseYear;
	private JYearChooser yearChooser;
	private JComboBox comboBoxClass;
	private JFXPanel jfxpanel;	

	public static void main(String[] args) {

		new SMSNotificationPanel();
	}
	public SMSNotificationPanel() {

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
		
		
		smsSubject=new JLabel("SMS Subject:");
		smsSubject.setFont(new Font("Times New Roman", Font.BOLD, 16));
		smsSubject.setForeground(Color.blue);
		leftpanel.add(smsSubject);
		
		
		paneSubject=new JTextPane();
		scrollerSubject=new JScrollPane(paneSubject);
		scrollerSubject.setPreferredSize(new Dimension(700, 30));
		leftpanel.add(scrollerSubject);
		
		
		smsRecipient=new JLabel("Send To:");
		smsRecipient.setFont(new Font("Times New Roman", Font.BOLD, 16));
		smsRecipient.setForeground(Color.blue);
		leftpanel.add(smsRecipient);
		
		
		paneRecipient=new JTextPane();
		scrollerRecipient=new JScrollPane(paneRecipient);
		scrollerRecipient.setPreferredSize(new Dimension(700, 100));
		leftpanel.add(scrollerRecipient);
		
		
		
		smsbody=new JLabel("SMS Body:");
		smsbody.setFont(new Font("Times New Roman", Font.BOLD, 16));
		smsbody.setForeground(Color.blue);
		leftpanel.add(smsbody);
		
	    
		final HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setPrefHeight(245);
        
        Scene scene=new Scene(htmlEditor);
        
        jfxpanel=new JFXPanel();
        jfxpanel.setScene(scene);
        
        add(jfxpanel);
		//right panel config
		

		
		Dimension dimBTN=new Dimension(200, 30);
		
		chooseYear=new JLabel("Choose Year:");
		chooseYear.setFont(new Font("Times New Roman", Font.BOLD, 16));
		chooseYear.setPreferredSize(dimBTN);
		chooseYear.setForeground(Color.blue);
		rightpanel.add(chooseYear);
		
		yearChooser=new JYearChooser();
		yearChooser.getYear();
		yearChooser.setPreferredSize(dimBTN);
		rightpanel.add(yearChooser);
		
		
		String[] classes={"Choose Class","S1","S2","S3","S4","S5 Arts","S5 Sci","S6 Arts","S6 Sci","All Classes"};
		comboBoxClass=new JComboBox<>(classes);
		comboBoxClass.setPreferredSize(dimBTN);
		rightpanel.add(comboBoxClass);
		
	
		btnNotifyParents=new JButton("Send SMS To Parents");
		btnNotifyParents.setPreferredSize(dimBTN);
		rightpanel.add(btnNotifyParents);
		
		btnNotifyTeachers=new JButton("Send SMS To Teachers");
		btnNotifyTeachers.setPreferredSize(dimBTN);
		rightpanel.add(btnNotifyTeachers);
		
		btnViewSMSNotificationHistory=new JButton("View SMS Notice Record");
		btnViewSMSNotificationHistory.setPreferredSize(dimBTN);
		rightpanel.add(btnViewSMSNotificationHistory);
		
	
	}

}
