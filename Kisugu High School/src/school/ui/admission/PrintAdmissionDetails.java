package school.ui.admission;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;

public class PrintAdmissionDetails extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panelItems;
	private JLabel labelName;
	private JLabel labelNamePosition;
	private JLabel labelID;
	private JLabel labelIDGoesHere;
	private JLabel labelClass;
	private JLabel labelClassGoesHere;
	private JLabel labelTerm;
	private JLabel labelTermGoesHere;

	
	private JLabel labelSchoolLogo;


	private JLabel labelSchoolName;

	private JLabel labelFormName;

	private JLabel labelStudentPhoto;


	private JLabel labelTypeOfStudent;

	private JLabel labelTypeOfStudentGoesHere;

	private JLabel labelYear;

	private JLabel labelYearGoesHere;

	private JLabel labelFeesAmountPaid;

	private JLabel labelFeesAmountPaidGoesHere;

	private JLabel labelFeesAmountBalance;

	private JLabel labelFeesAmountBalanceGoesHere;

	private JLabel labelFeesAmountBalanceOfPreviousTerms;

	private JLabel labelFeesAmountBalanceOfPreviousTermsGoesHere;

	private JLabel labelHeadingStudents;

	private JLabel labelHeadingFees;

	private JLabel labelHeadingReq;

	private JLabel labelHeadingReqBalance;

	private JLabel labelFeesExpectedDateOfClearence;

	private JLabel labelFeesExpectedDateOfClearenceGoesHere;

	private JLabel labelUnderLine;

	private JPanel panelMiddle;

	private JLabel address1;

	private JLabel address2;

	private JLabel address3;

	private JPanel panelMiddleInner;

	private JPanel panelMiddleInner1;

	public static void main(String[] args) {
		JFrame frame=new JFrame();
		frame.setTitle("Panel");
		frame.setSize(850, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		PrintAdmissionDetails t=new PrintAdmissionDetails();
		frame.add(t);
		frame.setVisible(true);
	}
	@Override 
	public void paintComponent(Graphics g){
		g.drawLine(0, 117, 850, 117);
		g.drawLine(0, 120, 850, 120);
		
		
//		 //vertical line
//	     g.setColor(Color.red);
//	     g.drawLine(20, 20, 20, 120);
//	 
//	     //horizontal line
//	     g.setColor(Color.green);
//	     g.drawLine(20, 20, 120, 20);
//	 
//	     //diagonal line 
//	     g.setColor(Color.blue);
//	     g.drawLine(20, 20, 120, 120);
	}

	public PrintAdmissionDetails() {
		// setBorder(new LineBorder(Color.WHITE,3));

		
		labelSchoolLogo = new JLabel("");
		labelSchoolLogo.setPreferredSize(new Dimension(140, 110));
		Image img1 = new ImageIcon(this.getClass().getResource("logo1.png")).getImage();
		labelSchoolLogo.setIcon(new ImageIcon(img1));
		add(labelSchoolLogo);

		
		panelMiddle=new JPanel();
		panelMiddle.setPreferredSize(new Dimension(530, 110));
		panelMiddle.setBorder(new LineBorder(Color.WHITE, 3));
		panelMiddle.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		add(panelMiddle);

		labelSchoolName = new JLabel("ST. MARY'S HIGH SCHOOL-KISUGU");
		labelSchoolName.setFont(new Font("Times New Roman", Font.BOLD, 28));
		panelMiddle.add(labelSchoolName);
		labelSchoolName.setForeground(Color.BLUE);
		
		panelMiddleInner=new JPanel();
		panelMiddleInner.setPreferredSize(new Dimension(520, 25));
		panelMiddleInner.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		panelMiddle.add(panelMiddleInner);
		
		panelMiddleInner1=new JPanel();
		panelMiddleInner1.setPreferredSize(new Dimension(520, 25));
		panelMiddleInner1.setLayout(new FlowLayout(FlowLayout.CENTER,2,2));
		panelMiddle.add(panelMiddleInner1);
		
		
		address1 = new JLabel();
		address1.setText("P.O.Box 123-Gulu (Uganda)");
		panelMiddleInner.add(address1);
//		address1.setPreferredSize(new Dimension(315, 20));
		address1.setForeground(Color.blue);
		address1.setFont(new Font("Times New Roman", Font.BOLD, 18));

		address2 = new JLabel();
		address2.setText("E-mail: sjcollegelayibi@gmail.com ");
		panelMiddleInner1.add(address2);
//		address2.setPreferredSize(new Dimension(510, 20));
		address2.setForeground(Color.blue);
		address2.setFont(new Font("Times New Roman", Font.BOLD, 18));

		address3 = new JLabel();
		address3.setText("Tel: 0786277071/0314234567");
		panelMiddle.add(address3);
//		address3.setPreferredSize(new Dimension(300, 20));
		address3.setForeground(Color.blue);
		address3.setFont(new Font("Times New Roman", Font.BOLD, 18));

		
		labelStudentPhoto = new JLabel("");
		labelStudentPhoto.setPreferredSize(new Dimension(135, 110));
		Image imgphoto = new ImageIcon(this.getClass().getResource("Bush.jpg")).getImage();
		labelStudentPhoto.setIcon(new ImageIcon(imgphoto));
		add(labelStudentPhoto);

		

		labelFormName = new JLabel("STUDENTS REPORTING FORM");
		labelFormName.setFont(new Font("Times New Roman", Font.BOLD, 24));
		add(labelFormName);
		labelFormName.setForeground(Color.BLUE);

		
		


		// Dimensions

		Dimension dimLabels = new Dimension(350, 25);
		Dimension dimLabelGoes = new Dimension(450, 25);
		Dimension dimLabelHeadingGoes = new Dimension(750, 25);

		// Student Details
		labelHeadingStudents = new JLabel("STUDENT DETAILS:");
		labelHeadingStudents.setPreferredSize(dimLabelHeadingGoes);
		labelHeadingStudents.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelHeadingStudents.setForeground(Color.BLUE);
		add(labelHeadingStudents);

		labelName = new JLabel("Student Name:");
		labelName.setPreferredSize(dimLabels);
		labelName.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelName.setForeground(Color.black);
		add(labelName);

		labelNamePosition = new JLabel("Student Name Goes Here");
		labelNamePosition.setPreferredSize(dimLabelGoes);
		labelNamePosition.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelNamePosition.setForeground(Color.black);
		add(labelNamePosition);

		Font fontName = labelNamePosition.getFont();
		Map attributesfontName = fontName.getAttributes();
		attributesfontName.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
		labelNamePosition.setFont(fontName.deriveFont(attributesfontName));

		labelTypeOfStudent = new JLabel("Type Of Student:");
		labelTypeOfStudent.setPreferredSize(dimLabels);
		labelTypeOfStudent.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelTypeOfStudent.setForeground(Color.black);
		add(labelTypeOfStudent);

		labelTypeOfStudentGoesHere = new JLabel("Type Of Student Goes Here");
		labelTypeOfStudentGoesHere.setPreferredSize(dimLabelGoes);
		labelTypeOfStudentGoesHere.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelTypeOfStudentGoesHere.setForeground(Color.black);
		add(labelTypeOfStudentGoesHere);

		Font fontTypeStudent = labelTypeOfStudentGoesHere.getFont();
		Map attributesTypeStudent = fontTypeStudent.getAttributes();
		attributesTypeStudent.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
		labelTypeOfStudentGoesHere.setFont(fontTypeStudent.deriveFont(attributesTypeStudent));

		labelClass = new JLabel("Student Class:");
		labelClass.setPreferredSize(dimLabels);
		labelClass.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelClass.setForeground(Color.black);
		add(labelClass);

		labelClassGoesHere = new JLabel("Class Goes Here");
		labelClassGoesHere.setPreferredSize(dimLabelGoes);
		labelClassGoesHere.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelClassGoesHere.setForeground(Color.black);
		add(labelClassGoesHere);

		Font fontClass = labelClassGoesHere.getFont();
		Map attributesfontClass = fontClass.getAttributes();
		attributesfontClass.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
		labelClassGoesHere.setFont(fontClass.deriveFont(attributesfontClass));

		labelTerm = new JLabel("Term Of Reporting:");
		labelTerm.setPreferredSize(dimLabels);
		labelTerm.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelTerm.setForeground(Color.black);
		add(labelTerm);

		labelTermGoesHere = new JLabel("Term Goes Here");
		labelTermGoesHere.setPreferredSize(dimLabelGoes);
		labelTermGoesHere.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelTermGoesHere.setForeground(Color.black);
		add(labelTermGoesHere);

		Font fontTerm = labelTermGoesHere.getFont();
		Map attributesfontTerm = fontTerm.getAttributes();
		attributesfontTerm.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
		labelTermGoesHere.setFont(fontTerm.deriveFont(attributesfontTerm));

		labelYear = new JLabel("Year Of Reporting:");
		labelYear.setPreferredSize(dimLabels);
		labelYear.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelYear.setForeground(Color.black);
		add(labelYear);

		labelYearGoesHere = new JLabel("Year Goes Here");
		labelYearGoesHere.setPreferredSize(dimLabelGoes);
		labelYearGoesHere.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelYearGoesHere.setForeground(Color.black);
		add(labelYearGoesHere);

		Font fontYear = labelYearGoesHere.getFont();
		Map attributesfontYear = fontYear.getAttributes();
		attributesfontYear.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
		labelYearGoesHere.setFont(fontYear.deriveFont(attributesfontYear));

		// school fees record section
		labelHeadingFees = new JLabel("FEES PAYMENT DETAILS:");
		labelHeadingFees.setPreferredSize(dimLabelHeadingGoes);
		labelHeadingFees.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelHeadingFees.setForeground(Color.BLUE);
		add(labelHeadingFees);

		labelFeesAmountPaid = new JLabel("Student Fees Amount Paid(This Term):");
		labelFeesAmountPaid.setPreferredSize(dimLabels);
		labelFeesAmountPaid.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelFeesAmountPaid.setForeground(Color.black);
		add(labelFeesAmountPaid);

		labelFeesAmountPaidGoesHere = new JLabel("Fees Amount Paid Goes Here");
		labelFeesAmountPaidGoesHere.setPreferredSize(dimLabelGoes);
		labelFeesAmountPaidGoesHere.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelFeesAmountPaidGoesHere.setForeground(Color.black);
		add(labelFeesAmountPaidGoesHere);

		Font fontFeesAmountPaid = labelFeesAmountPaidGoesHere.getFont();
		Map attributesfontFeesAmountPaid = fontFeesAmountPaid.getAttributes();
		attributesfontFeesAmountPaid.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
		labelFeesAmountPaidGoesHere.setFont(fontFeesAmountPaid.deriveFont(attributesfontFeesAmountPaid));

		labelFeesAmountBalance = new JLabel("Fees Amount Balance(This Term):");
		labelFeesAmountBalance.setPreferredSize(dimLabels);
		labelFeesAmountBalance.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelFeesAmountBalance.setForeground(Color.black);
		add(labelFeesAmountBalance);

		labelFeesAmountBalanceGoesHere = new JLabel("Fees Amount Balance Goes Here");
		labelFeesAmountBalanceGoesHere.setPreferredSize(dimLabelGoes);
		labelFeesAmountBalanceGoesHere.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelFeesAmountBalanceGoesHere.setForeground(Color.black);
		add(labelFeesAmountBalanceGoesHere);

		Font fontFeesAmountBalance = labelFeesAmountBalanceGoesHere.getFont();
		Map attributesfontFeesAmountBalance = fontFeesAmountBalance.getAttributes();
		attributesfontFeesAmountBalance.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
		labelFeesAmountBalanceGoesHere.setFont(fontFeesAmountBalance.deriveFont(attributesfontFeesAmountBalance));

		labelFeesAmountBalanceOfPreviousTerms = new JLabel("Fees Amount Balance Of Previous Terms:");
		labelFeesAmountBalanceOfPreviousTerms.setPreferredSize(dimLabels);
		labelFeesAmountBalanceOfPreviousTerms.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelFeesAmountBalanceOfPreviousTerms.setForeground(Color.black);
		add(labelFeesAmountBalanceOfPreviousTerms);

		labelFeesAmountBalanceOfPreviousTermsGoesHere = new JLabel("Fees Amount Balance Of Previous Terms Goes Here");
		labelFeesAmountBalanceOfPreviousTermsGoesHere.setPreferredSize(dimLabelGoes);
		labelFeesAmountBalanceOfPreviousTermsGoesHere.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelFeesAmountBalanceOfPreviousTermsGoesHere.setForeground(Color.black);
		add(labelFeesAmountBalanceOfPreviousTermsGoesHere);

		Font fontFeesAmountBalanceOfPreviousTerms = labelFeesAmountBalanceOfPreviousTermsGoesHere.getFont();
		Map attributesfontFeesAmountBalanceOfPreviousTerms = fontFeesAmountBalanceOfPreviousTerms.getAttributes();
		attributesfontFeesAmountBalanceOfPreviousTerms.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
		labelFeesAmountBalanceOfPreviousTermsGoesHere.setFont(
				fontFeesAmountBalanceOfPreviousTerms.deriveFont(attributesfontFeesAmountBalanceOfPreviousTerms));
		
		labelFeesExpectedDateOfClearence = new JLabel("Expected Date Of Clearence:");
		labelFeesExpectedDateOfClearence.setPreferredSize(dimLabels);
		labelFeesExpectedDateOfClearence.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelFeesExpectedDateOfClearence.setForeground(Color.black);
		add(labelFeesExpectedDateOfClearence);

		labelFeesExpectedDateOfClearenceGoesHere = new JLabel("Expected Date Of Clearence Goes Here");
		labelFeesExpectedDateOfClearenceGoesHere.setPreferredSize(dimLabelGoes);
		labelFeesExpectedDateOfClearenceGoesHere.setFont(new Font("Times New Roman", Font.BOLD, 14));
		labelFeesExpectedDateOfClearenceGoesHere.setForeground(Color.black);
		add(labelFeesExpectedDateOfClearenceGoesHere);


		Font fontFeesExpectedDateOfClearence = labelFeesExpectedDateOfClearenceGoesHere.getFont();
		Map attributesfontFeesExpectedDateOfClearence = fontFeesExpectedDateOfClearence.getAttributes();
		attributesfontFeesExpectedDateOfClearence.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
		labelFeesExpectedDateOfClearenceGoesHere.setFont(fontFeesExpectedDateOfClearence.deriveFont(attributesfontFeesExpectedDateOfClearence));
		
		// requirements details

		labelHeadingReq = new JLabel("REQUIREMENTS DETAILS:");
		labelHeadingReq.setPreferredSize(dimLabelHeadingGoes);
		labelHeadingReq.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelHeadingReq.setForeground(Color.BLUE);
		add(labelHeadingReq);

		String[][] data11 = new String[][] { { "Term 1", "Req Name", "4", "7", "4" },
				{ "Term 1", "Req Name", "4", "7", "4" }, { "Term 1", "Req Name", "4", "7", "4" },
				{ "Term 1", "Req Name", "4", "7", "4" }, { "Term 1", "Req Name", "4", "7", "4" },
				{ "Term 1", "Req Name", "4", "7", "4" }, { "Term 1", "Req Name", "4", "7", "4" },
				{ "Term 1", "Req Name", "4", "7", "4" },

		};

		String[] heading11 = new String[] { "Term", "Req Name", "Quantity", "Balance", "Standard Quantity" };

		JTable tableReq = new JTable(data11, heading11);
		tableReq.setShowGrid(false);
		JTableHeader headerReq = tableReq.getTableHeader();
		headerReq.setBackground(Color.BLACK);
		headerReq.setPreferredSize(new Dimension(400, 30));
		JScrollPane scrollerb11 = new JScrollPane(tableReq);
		tableReq.setRowHeight(25);
		scrollerb11.setPreferredSize(new Dimension(800, 250));
		add(scrollerb11);

		labelHeadingReqBalance = new JLabel("REQUIREMENTS DETAILS FOR PREVIOUS TERMS:");
		labelHeadingReqBalance.setPreferredSize(dimLabelHeadingGoes);
		labelHeadingReqBalance.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelHeadingReqBalance.setForeground(Color.BLUE);
		add(labelHeadingReqBalance);
		
		String[][] data1Bal = new String[][] { { "Term 1", "Req Name", "4", "7", "4" },
			{ "Term 1", "Req Name", "4", "7", "4" }, { "Term 1", "Req Name", "4", "7", "4" },
			{ "Term 1", "Req Name", "4", "7", "4" }, { "Term 1", "Req Name", "4", "7", "4" },
			{ "Term 1", "Req Name", "4", "7", "4" }, { "Term 1", "Req Name", "4", "7", "4" },
			{ "Term 1", "Req Name", "4", "7", "4" },

	};

	String[] heading1Bal = new String[] { "Term", "Req Name", "Quantity", "Balance", "Standard Quantity" };

	JTable tableReqBal = new JTable(data1Bal, heading1Bal);
	tableReqBal.setShowGrid(false);
	JTableHeader tableReqBalHeader = tableReqBal.getTableHeader();
	tableReqBalHeader.setBackground(Color.BLACK);
	tableReqBalHeader.setPreferredSize(new Dimension(400, 30));
	JScrollPane scrollerb1Bal = new JScrollPane(tableReqBal);
	tableReqBal.setRowHeight(25);
	scrollerb1Bal.setPreferredSize(new Dimension(800, 250));
	add(scrollerb1Bal);

//		setVisible(true);

	}

	
	
	

	public JLabel getLabelName() {
		return labelName;
	}

	public JLabel getLabelNamePosition() {
		return labelNamePosition;
	}

	public JLabel getLabelID() {
		return labelID;
	}

	public Component getLabelIDGoesHere() {
		return labelIDGoesHere;
	}

	public JLabel getLabelClass() {
		return labelClass;
	}

	public Component getLabelClassGoesHere() {
		return labelClassGoesHere;
	}

	public JLabel getLabelTerm() {
		return labelTerm;
	}

	public Component getLabelTermGoesHere() {
		return labelTermGoesHere;
	}

	public JPanel getPanelItems() {
		return panelItems;
	}

}
