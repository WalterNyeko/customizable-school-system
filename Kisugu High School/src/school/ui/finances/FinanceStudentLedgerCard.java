package school.ui.finances;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.toedter.calendar.JYearChooser;

import colgroup.ColumnGroup;
import colgroup.GroupableTableColumnModel;
import colgroup.GroupableTableHeader;
import school.ui.staff.StaffsPanel;

public class FinanceStudentLedgerCard extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2757017458826417250L;

	// students details
	private JTextField fieldNameStudent;
	private JComboBox<String> comboBoxClassStudent;
	public JComboBox fieldPACStudent;
	private JTextField fieldChooserEnrollmentStudent;
	private JTextField fieldChooserDOBStudent;
	private JTextField fieldTypeOfStudent;
	private JTextField fieldAddressStudent;
	private JTextField fieldIDNumberStudent;

	private JLabel labelNameStudent;
	private JLabel labelClassStudent;
	private JLabel labelPACStudent;
	private JLabel labelDateOfEnrollmentStudent;
	private JLabel labelDateDOBStudent;
	private JLabel labelTypeOfStudent;
	private JLabel labelAddressStudent;
	private JLabel labelIDNumberStudent;

	private JComboBox<String> comboBoxTerm;

	private JLabel labelYearChooserFilter;
	private JYearChooser yearChooserFilter;
	private JComboBox<String> comboBoxTermFilter;
	private JComboBox<String> comboBoxClassFilter;

	private JButton btnEnter;
	private JButton btnClear;

	/// Ledger details
	private JYearChooser dateChooserLedger;
	private JTextField fieldReceiptNoLedger;
	private JTextField fieldDetailsLedger;
	private JTextField fieldAmountDebitOrCreditLedger;
	private JLabel labelDateLedger;
	private JLabel labelReceiptNumberLedger;
	private JLabel labelAmountDebitOrCreditLedger;
	private JLabel labelDetailsLedger;
	private JLabel labelChooseTermLedger;
	private JLabel labelDebitOrCreditLedger;
	private JComboBox<String> comboBoxDebitOrCreditTerm;

	private JPanel panelLeft;
	private JPanel panelRight;

	private JTable tableStudentLedgerCard;
	private JScrollPane scrollPaneLedgerCard;
	private JButton btnPrint;
	private JButton btnExport;

	private JPanel panelNorth;
	private JButton btnSetFees;
	private JTextField fieldFeesAmount;
	private JLabel labelChooseClass;
	private JLabel labelChooseTerm;
	private JLabel labelChooseFees;
	private JButton btnViewAllStudents;
	private JButton btnRefreshTable;
	private JLabel labelChoosecategory;
	private JComboBox<String> comboBoxcategoryFilter;
	private JLabel labelChooseSubCategory;
	private JComboBox<String> comboBoxSubCategoryFilter;
	private JLabel labelTotalAmount;
	private JLabel labelTotalAmountValue;
	private JTable tableStudentLedgerCardSummary;
	private JScrollPane scrollPaneLedgerCardSummary;
	private JPanel panelButtons;
	private JPanel panelHoldTables;
	private JLabel labelChoosecategoryPaymentSide;
	private JComboBox comboBoxcategory;
	private JLabel labelChooseSubCategoryPaymentSide;
	private JComboBox comboBoxSubCategory;
	private JCheckBox checkBoxCash;
	private JCheckBox checkBoxBank;

	protected JFileChooser fileChooser;

	private Connection conn;

	private Object pst;

	private JPanel panelCheckBoxes;

	private JPanel panelSelected;

	private JCheckBox labelDay;

	private JCheckBox labelBoarding;

	protected String dayOrBoarding = "Day";

	private JCheckBox labelOnBursary;

	protected String bursaryStatus = "Not On Bursary";

	private JCheckBox labelFullBursary;

	private JCheckBox labelPartialBursary;

	private JTextField fieldClassNumber;

	////

	public static void main(String[] args) {
		new FinanceStudentLedgerCard();
	}

	public FinanceStudentLedgerCard() {

		setTitle("Student Ledger Book");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

		setSize(screensize.width - 20, screensize.height - 50);
		setLocationRelativeTo(null);
		setBackground(Color.decode("#5f9ea0"));

		setLayout(new BorderLayout());

		Dimension dimensionField = new Dimension(200, 25);
		Dimension dimensionLabels = new Dimension(130, 25);

		panelNorth = new JPanel();
		panelNorth.setBackground(Color.decode("#5f9ea0"));
		panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 2));
		add(panelNorth, BorderLayout.NORTH);

		panelLeft = new JPanel();
		panelLeft.setBackground(Color.decode("#5f9ea0"));
		panelLeft.setPreferredSize(new Dimension(750, 280));
		panelLeft.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 2));

		panelLeft.setBorder(new TitledBorder("Fees Balance Payments"));

		panelNorth.add(panelLeft);

		panelRight = new JPanel();
		panelRight.setBackground(Color.decode("#5f9ea0"));
		panelRight.setPreferredSize(new Dimension(500, 280));
		panelRight.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		panelRight.setBorder(new TitledBorder("Set Fees/Filter Students"));
		panelNorth.add(panelRight);

		///// top fields and labels

		labelIDNumberStudent = new JLabel("Class Number:");
		labelIDNumberStudent.setPreferredSize(dimensionLabels);
		panelLeft.add(labelIDNumberStudent);
		fieldIDNumberStudent = new JTextField();
		fieldIDNumberStudent.setPreferredSize(dimensionField);
		fieldIDNumberStudent.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				displayInTexFields("select student_name,fathers_contact,year,"
						+ "mothers_contact,student_class from student_ledger"
						+ " where CONCAT(class_number,payment_code) LIKE '%" + fieldIDNumberStudent.getText()
						+ "%' and year='" + dateChooserLedger.getYear() + "'");

				displayData(tableStudentLedgerCard,
						"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
								+ dateChooserLedger.getYear() + "' and CONCAT(class_number,payment_code) LIKE '%"
								+ fieldIDNumberStudent.getText() + "%'");
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		panelLeft.add(fieldIDNumberStudent);

		labelNameStudent = new JLabel("Name:");
		labelNameStudent.setPreferredSize(dimensionLabels);
		panelLeft.add(labelNameStudent);
		fieldNameStudent = new JTextField();
		fieldNameStudent.setPreferredSize(dimensionField);
		panelLeft.add(fieldNameStudent);

		labelPACStudent = new JLabel("Bank Paid To:");
		labelPACStudent.setPreferredSize(dimensionLabels);
		panelLeft.add(labelPACStudent);
		fieldPACStudent = new JComboBox();
		fieldPACStudent.setPreferredSize(dimensionField);
		panelLeft.add(fieldPACStudent);

		displayInComboBox(fieldPACStudent, "select bank_name from banks");

		labelClassStudent = new JLabel("Class:");
		labelClassStudent.setPreferredSize(dimensionLabels);
		panelLeft.add(labelClassStudent);
		comboBoxClassStudent = new JComboBox<>();
		comboBoxClassStudent.setPreferredSize(dimensionField);
		panelLeft.add(comboBoxClassStudent);

		labelDateOfEnrollmentStudent = new JLabel("Date Of Enrollment");
		labelDateOfEnrollmentStudent.setPreferredSize(dimensionLabels);
		panelLeft.add(labelDateOfEnrollmentStudent);
		fieldChooserEnrollmentStudent = new JTextField();
		fieldChooserEnrollmentStudent.setPreferredSize(dimensionField);
		panelLeft.add(fieldChooserEnrollmentStudent);

		labelDateDOBStudent = new JLabel("Date Of Birth");
		labelDateDOBStudent.setPreferredSize(dimensionLabels);
		panelLeft.add(labelDateDOBStudent);
		fieldChooserDOBStudent = new JTextField();
		fieldChooserDOBStudent.setPreferredSize(dimensionField);
		panelLeft.add(fieldChooserDOBStudent);

		labelTypeOfStudent = new JLabel("Type Of Student");
		labelTypeOfStudent.setPreferredSize(dimensionLabels);
		panelLeft.add(labelTypeOfStudent);
		fieldTypeOfStudent = new JTextField();
		fieldTypeOfStudent.setPreferredSize(dimensionField);
		panelLeft.add(fieldTypeOfStudent);

		labelAddressStudent = new JLabel("Address");
		labelAddressStudent.setPreferredSize(dimensionLabels);
		panelLeft.add(labelAddressStudent);
		fieldAddressStudent = new JTextField();
		fieldAddressStudent.setPreferredSize(dimensionField);
		panelLeft.add(fieldAddressStudent);

		// labels and fields ledger entry
		labelDateLedger = new JLabel("Year:");
		labelDateLedger.setPreferredSize(dimensionLabels);
		panelLeft.add(labelDateLedger);
		dateChooserLedger = new JYearChooser();
		dateChooserLedger.getYear();
		dateChooserLedger.setPreferredSize(dimensionField);
		panelLeft.add(dateChooserLedger);

		labelReceiptNumberLedger = new JLabel("Receipt No.");
		labelReceiptNumberLedger.setPreferredSize(dimensionLabels);
		panelLeft.add(labelReceiptNumberLedger);
		fieldReceiptNoLedger = new JTextField();
		fieldReceiptNoLedger.setPreferredSize(dimensionField);
		panelLeft.add(fieldReceiptNoLedger);

		labelChooseTermLedger = new JLabel("Choose Term");
		labelChooseTermLedger.setPreferredSize(dimensionLabels);
		panelLeft.add(labelChooseTermLedger);

		String[] terms = { "Choose Term", "First Term", "Second Term", "Third Term" };
		comboBoxTerm = new JComboBox<>(terms);
		comboBoxTerm.setPreferredSize(dimensionField);
		panelLeft.add(comboBoxTerm);

		labelDetailsLedger = new JLabel("Details");
		labelDetailsLedger.setPreferredSize(dimensionLabels);
		panelLeft.add(labelDetailsLedger);
		fieldDetailsLedger = new JTextField();
		fieldDetailsLedger.setPreferredSize(dimensionField);
		panelLeft.add(fieldDetailsLedger);

		labelDebitOrCreditLedger = new JLabel("Debit/Credit");
		labelDebitOrCreditLedger.setPreferredSize(dimensionLabels);
		panelLeft.add(labelDebitOrCreditLedger);

		String[] debitOrCredit = { "Debit/Credit", "Dr", "Cr" };
		comboBoxDebitOrCreditTerm = new JComboBox<>(debitOrCredit);
		comboBoxDebitOrCreditTerm.setPreferredSize(dimensionField);
		panelLeft.add(comboBoxDebitOrCreditTerm);

		labelAmountDebitOrCreditLedger = new JLabel("Amount");
		labelAmountDebitOrCreditLedger.setPreferredSize(dimensionLabels);
		panelLeft.add(labelAmountDebitOrCreditLedger);

		fieldAmountDebitOrCreditLedger = new JTextField();
		fieldAmountDebitOrCreditLedger.setPreferredSize(dimensionField);
		panelLeft.add(fieldAmountDebitOrCreditLedger);

		//////////// account receiving the payment

		labelChoosecategoryPaymentSide = new JLabel("Account Category:");
		labelChoosecategoryPaymentSide.setPreferredSize(dimensionLabels);
		panelLeft.add(labelChoosecategoryPaymentSide);

		comboBoxcategory = new JComboBox<>();
		comboBoxcategory.setPreferredSize(dimensionField);
		comboBoxcategory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				displaySubCategoriesPaymentSide();

			}
		});
		panelLeft.add(comboBoxcategory);

		labelChooseSubCategoryPaymentSide = new JLabel("Account SubCategory:");
		labelChooseSubCategoryPaymentSide.setPreferredSize(dimensionLabels);
		panelLeft.add(labelChooseSubCategoryPaymentSide);

		comboBoxSubCategory = new JComboBox<>();
		comboBoxSubCategory.setPreferredSize(dimensionField);

		panelLeft.add(comboBoxSubCategory);

		checkBoxCash = new JCheckBox("Cash");
		checkBoxCash.setFont(new Font("Times New Roman", Font.BOLD, 16));
		checkBoxCash.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (checkBoxCash.isSelected()) {
					checkBoxBank.setSelected(false);
				}

			}
		});
		panelLeft.add(checkBoxCash);

		checkBoxBank = new JCheckBox("Bank");
		checkBoxBank.setFont(new Font("Times New Roman", Font.BOLD, 16));
		checkBoxBank.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (checkBoxBank.isSelected()) {
					checkBoxCash.setSelected(false);
				}

			}
		});
		panelLeft.add(checkBoxBank);

		btnEnter = new JButton("SUBMIT PAYMENT");
		btnEnter.setPreferredSize(new Dimension(200, 25));
		btnEnter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (comboBoxDebitOrCreditTerm.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Please Choose Debit/Credit before proceeding");
				} else if (comboBoxDebitOrCreditTerm.getSelectedIndex() == 1) {

					if (checkBoxCash.isSelected()) {
						String value = fieldAmountDebitOrCreditLedger.getText().toString();
						Double valueAmount = Double.parseDouble(value);

						AddUpdateDelete("update student_ledger set credit=credit+'" + valueAmount
								+ "' where (class_number='" + fieldIDNumberStudent.getText() + "' or payment_code='"
								+ fieldIDNumberStudent.getText() + "') and year='" + dateChooserLedger.getYear()
								+ "' and term='" + comboBoxTerm.getSelectedItem() + "'");

						new CashBookController().InsertCashBookEntries(
								"insert into cash_book(catid,receipt_number,details,folio,cash_dr,subcategory)"
										+ " select category.catid,'" + fieldReceiptNoLedger.getText() + "'," + "'"
										+ fieldDetailsLedger.getText() + "','AutoGenerated Folio','" + valueAmount
										+ "'," + "'" + comboBoxSubCategory.getSelectedItem()
										+ "' from category where category.catname='"
										+ comboBoxcategory.getSelectedItem() + "'");

						new CashBookController().InsertCashBookEntriesSilently(
								"insert into budget_expense_income_records(catid,subcategory,`"
										+ dateChooserLedger.getYear() + "`,account_type)"
										+ " select subcategory.catid,'" + comboBoxSubCategory.getSelectedItem() + "','"
										+ valueAmount + "',account_type from subcategory where subcategory.scatname='"
										+ comboBoxSubCategory.getSelectedItem() + "'");

						displayData(tableStudentLedgerCard,
								"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger");

					} else if (checkBoxBank.isSelected()) {
						String value = fieldAmountDebitOrCreditLedger.getText().toString();
						Double valueAmount = Double.parseDouble(value);

						AddUpdateDelete("update student_ledger set credit=credit+'" + valueAmount
								+ "' where (class_number='" + fieldIDNumberStudent.getText() + "' or payment_code='"
								+ fieldIDNumberStudent.getText() + "') and year='" + dateChooserLedger.getYear()
								+ "' and term='" + comboBoxTerm.getSelectedItem() + "'");

						new CashBookController().InsertCashBookEntries(
								"insert into cash_book(catid,receipt_number,details,folio,bank_dr,subcategory)"
										+ " select category.catid,'" + fieldReceiptNoLedger.getText() + "'," + "'"
										+ fieldDetailsLedger.getText() + "','AutoGenerated Folio','" + valueAmount
										+ "'," + "'" + comboBoxSubCategory.getSelectedItem()
										+ "' from category where category.catname='"
										+ comboBoxcategory.getSelectedItem() + "'");

						new CashBookController().InsertCashBookEntriesSilently(
								"insert into budget_expense_income_records(catid,subcategory,`"
										+ dateChooserLedger.getYear() + "`,account_type)"
										+ " select subcategory.catid,'" + comboBoxSubCategory.getSelectedItem() + "','"
										+ valueAmount + "',account_type from subcategory where subcategory.scatname='"
										+ comboBoxSubCategory.getSelectedItem() + "'");

						saveToBank();

						displayData(tableStudentLedgerCard,
								"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger");

					} else if (!checkBoxCash.isSelected() && !checkBoxBank.isSelected()) {
						JOptionPane.showMessageDialog(null, "Please check either Cash or Bank ");
					}

				} else if (comboBoxDebitOrCreditTerm.getSelectedIndex() == 2) {

				} else {
					JOptionPane.showMessageDialog(null, "An Erro Occured");
				}

			}
		});
		panelLeft.add(btnEnter);

		/*********************************************************************
		 * Debiting Students Accounts Begin Here
		 *********************************************************************/

		Dimension dimFields = new Dimension(130, 23);

		panelCheckBoxes = new JPanel();
		panelCheckBoxes.setPreferredSize(new Dimension(170, 230));
		panelCheckBoxes.setBackground(Color.BLUE);

		labelDay = new JCheckBox("Day ");
		labelDay.setPreferredSize(dimFields);
		labelDay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (labelDay.isSelected()) {
					labelBoarding.setSelected(false);
					dayOrBoarding = "Day";

					displayData(tableStudentLedgerCard,
							"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
									+ yearChooserFilter.getYear() + "' and student_class='"
									+ comboBoxClassFilter.getSelectedItem() + "' and term='"
									+ comboBoxTermFilter.getSelectedItem() + "' and day_boarding='" + dayOrBoarding
									+ "'");

				} else {
					dayOrBoarding = "Boarding";
					displayData(tableStudentLedgerCard,
							"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
									+ yearChooserFilter.getYear() + "' and student_class='"
									+ comboBoxClassFilter.getSelectedItem() + "' and term='"
									+ comboBoxTermFilter.getSelectedItem() + "' and day_boarding='" + dayOrBoarding
									+ "'");
				}
			}
		});
		panelCheckBoxes.add(labelDay);

		labelBoarding = new JCheckBox("Boarding");
		labelBoarding.setPreferredSize(dimFields);
		labelBoarding.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (labelBoarding.isSelected()) {
					labelDay.setSelected(false);
					dayOrBoarding = "Boarding";
					displayData(tableStudentLedgerCard,
							"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
									+ yearChooserFilter.getYear() + "' and student_class='"
									+ comboBoxClassFilter.getSelectedItem() + "' and term='"
									+ comboBoxTermFilter.getSelectedItem() + "' and day_boarding='" + dayOrBoarding
									+ "' and bursary_status='" + bursaryStatus + "'");

				} else {
					dayOrBoarding = "Day";
					displayData(tableStudentLedgerCard,
							"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
									+ yearChooserFilter.getYear() + "' and student_class='"
									+ comboBoxClassFilter.getSelectedItem() + "' and term='"
									+ comboBoxTermFilter.getSelectedItem() + "' and day_boarding='" + dayOrBoarding
									+ "' and bursary_status='" + bursaryStatus + "'");

				}
			}
		});
		panelCheckBoxes.add(labelBoarding);

		labelOnBursary = new JCheckBox("No Bursary");
		labelOnBursary.setPreferredSize(dimFields);
		labelOnBursary.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (labelOnBursary.isSelected()) {
					labelFullBursary.setSelected(false);
					labelPartialBursary.setSelected(false);

					bursaryStatus = "Not On Bursary";

					displayData(tableStudentLedgerCard,
							"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
									+ yearChooserFilter.getYear() + "' and student_class='"
									+ comboBoxClassFilter.getSelectedItem() + "' and term='"
									+ comboBoxTermFilter.getSelectedItem() + "' and day_boarding='" + dayOrBoarding
									+ "' and bursary_status='" + bursaryStatus + "'");

				} else {
					labelFullBursary.setSelected(true);
					bursaryStatus = "Full Bursary";

					displayData(tableStudentLedgerCard,
							"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
									+ yearChooserFilter.getYear() + "' and student_class='"
									+ comboBoxClassFilter.getSelectedItem() + "' and term='"
									+ comboBoxTermFilter.getSelectedItem() + "' and day_boarding='" + dayOrBoarding
									+ "' and bursary_status='" + bursaryStatus + "'");

				}
			}
		});
		panelCheckBoxes.add(labelOnBursary);

		labelFullBursary = new JCheckBox("Full Bursary");
		labelFullBursary.setPreferredSize(dimFields);
		labelFullBursary.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (labelFullBursary.isSelected()) {
					labelOnBursary.setSelected(false);
					labelPartialBursary.setSelected(false);

					bursaryStatus = "Full Bursary";

					displayData(tableStudentLedgerCard,
							"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
									+ yearChooserFilter.getYear() + "' and student_class='"
									+ comboBoxClassFilter.getSelectedItem() + "' and term='"
									+ comboBoxTermFilter.getSelectedItem() + "' and day_boarding='" + dayOrBoarding
									+ "' and bursary_status='" + bursaryStatus + "'");

				} else {

					bursaryStatus = "Not On Bursary";
					displayData(tableStudentLedgerCard,
							"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
									+ yearChooserFilter.getYear() + "' and student_class='"
									+ comboBoxClassFilter.getSelectedItem() + "' and term='"
									+ comboBoxTermFilter.getSelectedItem() + "' and day_boarding='" + dayOrBoarding
									+ "' and bursary_status='" + bursaryStatus + "'");

				}
			}
		});
		panelCheckBoxes.add(labelFullBursary);

		labelPartialBursary = new JCheckBox("Partial Bursary");
		labelPartialBursary.setPreferredSize(dimFields);
		labelPartialBursary.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (labelPartialBursary.isSelected()) {
					labelOnBursary.setSelected(false);
					labelFullBursary.setSelected(false);
					bursaryStatus = "Partial Bursary";

					displayData(tableStudentLedgerCard,
							"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
									+ yearChooserFilter.getYear() + "' and student_class='"
									+ comboBoxClassFilter.getSelectedItem() + "' and term='"
									+ comboBoxTermFilter.getSelectedItem() + "' and day_boarding='" + dayOrBoarding
									+ "' and bursary_status='" + bursaryStatus + "'");

				} else {

					bursaryStatus = "Not On Bursary";
					displayData(tableStudentLedgerCard,
							"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
									+ yearChooserFilter.getYear() + "' and student_class='"
									+ comboBoxClassFilter.getSelectedItem() + "' and term='"
									+ comboBoxTermFilter.getSelectedItem() + "' and day_boarding='" + dayOrBoarding
									+ "' and bursary_status='" + bursaryStatus + "'");

				}
			}
		});
		panelCheckBoxes.add(labelPartialBursary);

		fieldClassNumber = new JTextField("Enter Class Number");
		fieldClassNumber.setPreferredSize(dimFields);
		fieldClassNumber.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				displayData(tableStudentLedgerCard,
						"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
								+ yearChooserFilter.getYear() + "' and student_class='"
								+ comboBoxClassFilter.getSelectedItem() + "' and term='"
								+ comboBoxTermFilter.getSelectedItem() + "' and day_boarding='" + dayOrBoarding
								+ "' and bursary_status='" + bursaryStatus + "' and class_number LIKE '%"
								+ fieldClassNumber.getText() + "%'");

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		panelCheckBoxes.add(fieldClassNumber);

		panelSelected = new JPanel();
		panelSelected.setPreferredSize(new Dimension(300, 230));
		panelSelected.setBackground(Color.GREEN);

		panelRight.add(panelSelected);
		panelRight.add(panelCheckBoxes);

		labelYearChooserFilter = new JLabel("Year:");
		labelYearChooserFilter.setPreferredSize(new Dimension(120, 23));
		panelSelected.add(labelYearChooserFilter);

		yearChooserFilter = new JYearChooser();
		yearChooserFilter.getYear();
		yearChooserFilter.setPreferredSize(new Dimension(150, 23));
		yearChooserFilter.addPropertyChangeListener("year", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				displayData(tableStudentLedgerCard,
						"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
								+ yearChooserFilter.getYear() + "'");

			}
		});
		panelSelected.add(yearChooserFilter);

		labelChooseClass = new JLabel("Class:");
		labelChooseClass.setPreferredSize(new Dimension(120, 23));
		panelSelected.add(labelChooseClass);

		comboBoxClassFilter = new JComboBox<>();
		comboBoxClassFilter.setPreferredSize(new Dimension(150, 23));
		comboBoxClassFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				displayData(tableStudentLedgerCard,
						"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
								+ yearChooserFilter.getYear() + "' and student_class='"
								+ comboBoxClassFilter.getSelectedItem() + "'");

			}
		});
		panelSelected.add(comboBoxClassFilter);

		labelChooseTerm = new JLabel("Term:");
		labelChooseTerm.setPreferredSize(new Dimension(120, 23));
		panelSelected.add(labelChooseTerm);

		comboBoxTermFilter = new JComboBox<>(terms);
		comboBoxTermFilter.setPreferredSize(new Dimension(150, 23));
		comboBoxTermFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				displayData(tableStudentLedgerCard,
						"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger where year='"
								+ yearChooserFilter.getYear() + "' and student_class='"
								+ comboBoxClassFilter.getSelectedItem() + "' and term='"
								+ comboBoxTermFilter.getSelectedItem() + "'");

			}
		});
		panelSelected.add(comboBoxTermFilter);

		labelChoosecategory = new JLabel("Account Category:");
		labelChoosecategory.setPreferredSize(new Dimension(120, 23));
		panelSelected.add(labelChoosecategory);

		comboBoxcategoryFilter = new JComboBox<>(terms);
		comboBoxcategoryFilter.setPreferredSize(new Dimension(150, 23));
		comboBoxcategoryFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				displaySubCategories();

			}
		});
		panelSelected.add(comboBoxcategoryFilter);

		labelChooseSubCategory = new JLabel("Account SubCategory:");
		labelChooseSubCategory.setPreferredSize(new Dimension(120, 23));
		panelSelected.add(labelChooseSubCategory);

		comboBoxSubCategoryFilter = new JComboBox<>(terms);
		comboBoxSubCategoryFilter.setPreferredSize(new Dimension(150, 23));

		panelSelected.add(comboBoxSubCategoryFilter);

		labelChooseFees = new JLabel("Fees Amount:");
		labelChooseFees.setPreferredSize(new Dimension(120, 23));
		panelSelected.add(labelChooseFees);

		fieldFeesAmount = new JTextField();
		fieldFeesAmount.setPreferredSize(new Dimension(150, 23));
		panelSelected.add(fieldFeesAmount);

		btnSetFees = new JButton("Debit Students");
		btnSetFees.setPreferredSize(new Dimension(120, 23));
		btnSetFees.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int rowCounts = tableStudentLedgerCard.getRowCount();
				for (int row = 0; row < rowCounts; row++) {

					String code = tableStudentLedgerCard.getValueAt(row, 2).toString();
					updateFeesForASpecificClass("update student_ledger set debit='" + fieldFeesAmount.getText()
							+ "',account_name='" + comboBoxSubCategoryFilter.getSelectedItem()
							+ "' where payment_code='" + code + "'");
				}

				displayData(tableStudentLedgerCard,
						"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger");

			}
		});
		panelSelected.add(btnSetFees);

		btnViewAllStudents = new JButton("Students Records");
		btnViewAllStudents.setPreferredSize(new Dimension(120, 23));
		panelSelected.add(btnViewAllStudents);

		btnRefreshTable = new JButton("Accounts Records");
		btnRefreshTable.setPreferredSize(new Dimension(120, 23));
		btnRefreshTable.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				scrollPaneLedgerCard.setVisible(false);
				scrollPaneLedgerCardSummary.setVisible(true);

				displayData(tableStudentLedgerCardSummary,
						"select date,term,year,student_class,account_name,details, sum(debit),sum(credit),sum(debit)-sum(credit) from student_ledger group by term,year,student_class");

			}
		});
		panelSelected.add(btnRefreshTable);

		// Dimension of labels and fields

		DefaultTableModel model = new DefaultTableModel();

		String[] headStudentLedgerCard = { "Student Class", "Student Name", "Student Code", "Year", "Term.",
				"Receipt Number", "Details", "Debit", "Credit", "Balance" };

		Object[][] dataStudentLedgerCard = { { null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null } };

		model.setDataVector(dataStudentLedgerCard, headStudentLedgerCard);

		tableStudentLedgerCard = new JTable();
		tableStudentLedgerCard.setRowHeight(30);
		// tab

		///// column grouping////
		tableStudentLedgerCard.setColumnModel(new GroupableTableColumnModel());
		tableStudentLedgerCard.setTableHeader(
				new GroupableTableHeader((GroupableTableColumnModel) tableStudentLedgerCard.getColumnModel()));
		tableStudentLedgerCard.setModel(model);
		tableStudentLedgerCard.setShowGrid(false);

		GroupableTableColumnModel cm = (GroupableTableColumnModel) tableStudentLedgerCard.getColumnModel();
		ColumnGroup groupFeesAndBursaries = new ColumnGroup("FEES AND BURSARIES");
		groupFeesAndBursaries.add(cm.getColumn(7));
		groupFeesAndBursaries.add(cm.getColumn(8));
		groupFeesAndBursaries.add(cm.getColumn(9));

		GroupableTableHeader header = (GroupableTableHeader) tableStudentLedgerCard.getTableHeader();
		cm.addColumnGroup(groupFeesAndBursaries);
		header.setBackground(Color.black);
		header.setForeground(Color.white);
		tableStudentLedgerCard.getColumnModel().getColumn(1).setMaxWidth(300);
		tableStudentLedgerCard.getColumnModel().getColumn(1).setPreferredWidth(250);
		tableStudentLedgerCard.getColumnModel().getColumn(6).setMaxWidth(300);
		tableStudentLedgerCard.getColumnModel().getColumn(6).setPreferredWidth(250);
		tableStudentLedgerCard.getColumnModel().getColumn(7).setCellRenderer(new NumberTableCellRenderer());
		tableStudentLedgerCard.getColumnModel().getColumn(8).setCellRenderer(new NumberTableCellRenderer());
		tableStudentLedgerCard.getColumnModel().getColumn(9).setCellRenderer(new NumberTableCellRenderer());

		scrollPaneLedgerCard = new JScrollPane(tableStudentLedgerCard);

		scrollPaneLedgerCard.setBorder(new LineBorder(Color.white, 3));

		///////////////////// JTabel for Summary Student
		///////////////////// Ledger/////////////////////
		DefaultTableModel modelSummary = new DefaultTableModel();

		String[] headStudentLedgerCardSummary = { "Date", "Term", "Year", "Class", "Account Name", "Details", "Debit",
				"Credit", "Balance" };

		Object[][] dataStudentLedgerCardSummary = { { null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null } };

		modelSummary.setDataVector(dataStudentLedgerCardSummary, headStudentLedgerCardSummary);

		tableStudentLedgerCardSummary = new JTable();
		tableStudentLedgerCardSummary.setRowHeight(30);
		// tab

		///// column grouping////
		tableStudentLedgerCardSummary.setColumnModel(new GroupableTableColumnModel());
		tableStudentLedgerCardSummary.setTableHeader(
				new GroupableTableHeader((GroupableTableColumnModel) tableStudentLedgerCardSummary.getColumnModel()));
		tableStudentLedgerCardSummary.setModel(modelSummary);
		tableStudentLedgerCardSummary.setShowGrid(false);

		GroupableTableColumnModel cmodel = (GroupableTableColumnModel) tableStudentLedgerCardSummary.getColumnModel();
		ColumnGroup groupFeesAndBursariesSummary = new ColumnGroup("FEES AND BURSARIES");
		groupFeesAndBursariesSummary.add(cmodel.getColumn(6));
		groupFeesAndBursariesSummary.add(cmodel.getColumn(7));
		groupFeesAndBursariesSummary.add(cmodel.getColumn(8));

		GroupableTableHeader headerSummary = (GroupableTableHeader) tableStudentLedgerCardSummary.getTableHeader();
		cmodel.addColumnGroup(groupFeesAndBursariesSummary);
		headerSummary.setBackground(Color.black);
		headerSummary.setForeground(Color.white);
		tableStudentLedgerCardSummary.getColumnModel().getColumn(4).setPreferredWidth(250);
		tableStudentLedgerCardSummary.getColumnModel().getColumn(5).setPreferredWidth(250);
		tableStudentLedgerCardSummary.getColumnModel().getColumn(6).setCellRenderer(new NumberTableCellRenderer());
		tableStudentLedgerCardSummary.getColumnModel().getColumn(7).setCellRenderer(new NumberTableCellRenderer());
		tableStudentLedgerCardSummary.getColumnModel().getColumn(8).setCellRenderer(new NumberTableCellRenderer());
		tableStudentLedgerCardSummary.setBackground(Color.decode("#5f9ea0"));
		tableStudentLedgerCard.setBackground(Color.decode("#5f9ea0"));

		scrollPaneLedgerCardSummary = new JScrollPane(tableStudentLedgerCardSummary);

		scrollPaneLedgerCardSummary.setBorder(new LineBorder(Color.white, 3));
		scrollPaneLedgerCard.setPreferredSize(new Dimension(1270, 360));
		scrollPaneLedgerCardSummary.setPreferredSize(new Dimension(1270, 360));

		panelHoldTables = new JPanel();

		add(panelHoldTables, BorderLayout.CENTER);
		panelHoldTables.setBackground(Color.decode("#5f9ea0"));
		panelHoldTables.add(scrollPaneLedgerCard);
		panelHoldTables.add(scrollPaneLedgerCardSummary);

		// scrollPaneLedgerCardSummary.setVisible(false);

		displayInComboBox(comboBoxClassFilter, "select class_name from student_classes");
		displayInComboBox(comboBoxClassStudent, "select class_name from student_classes");
		displayInComboBox(comboBoxTerm, "select term_name from student_terms");
		displayInComboBox(comboBoxTermFilter, "select term_name from student_terms");

		panelButtons = new JPanel();
		panelButtons.setBackground(Color.decode("#5f9ea0"));

		add(panelButtons, BorderLayout.SOUTH);

		btnPrint = new JButton("Print");
		btnPrint.setPreferredSize(new Dimension(100, 30));
		panelButtons.add(btnPrint);

		btnExport = new JButton("Export");
		btnExport.setPreferredSize(new Dimension(100, 30));
		panelButtons.add(btnExport);
		btnExport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to export this deatils");

				int selected = fileChooser.showSaveDialog(FinanceStudentLedgerCard.this);

				if (tableStudentLedgerCard.isVisible()) {
					if (selected == JFileChooser.APPROVE_OPTION) {
						try {

							fillData(tableStudentLedgerCard, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
				} else if (tableStudentLedgerCardSummary.isVisible()) {
					if (selected == JFileChooser.APPROVE_OPTION) {
						try {

							fillData(tableStudentLedgerCardSummary,
									new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
				}
			}
		});

		labelTotalAmount = new JLabel("Debit Amount");
		labelTotalAmount.setPreferredSize(new Dimension(100, 25));
		panelButtons.add(labelTotalAmount);

		labelTotalAmountValue = new JLabel();
		labelTotalAmountValue.setPreferredSize(new Dimension(100, 25));
		labelTotalAmountValue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		panelButtons.add(labelTotalAmountValue);

		displayData(tableStudentLedgerCard,
				"select student_class,student_name,payment_code,year,term,receipt_number,details,debit,credit,(debit-credit) from student_ledger");

		displayCategories();

		displayCategoriesPaymentSide();
		// changeTableCellColor(tableStudentLedgerCard, 7);
		this.setVisible(true);
	}

	public void displayData(JTable table, String query) {

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			while (table.getRowCount() > 0) {
				((DefaultTableModel) table.getModel()).removeRow(0);

			}
			int columns = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				Object[] row = new Object[columns];
				for (int i = 1; i <= columns; i++) {
					row[i - 1] = rs.getObject(i);
				}
				((DefaultTableModel) table.getModel()).insertRow(rs.getRow() - 1, row);
			}
			rs.close();
			pst.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public class NumberTableCellRenderer extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5014627352674864594L;

		public NumberTableCellRenderer() {
			setHorizontalAlignment(JLabel.RIGHT);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (value instanceof Number) {
				value = NumberFormat.getNumberInstance().format(value);
			}
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

	}

	public void updateFeesForASpecificClass(String querries) {

		java.sql.Connection conn = null;
		java.sql.PreparedStatement pst = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Fees Set Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed " + ex.getMessage());

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void displayCategories() {
		String sql = "select catname from category";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			comboBoxcategoryFilter.removeAllItems();
			while (rs.next()) {

				comboBoxcategoryFilter.addItem(rs.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void displayCategoriesPaymentSide() {
		String sql = "select catname from category";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			comboBoxcategory.removeAllItems();
			while (rs.next()) {

				comboBoxcategory.addItem(rs.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void displaySubCategories() {

		String sql = "select subcategory.scatname from subcategory,category where category.catname='"
				+ comboBoxcategoryFilter.getSelectedItem() + "' and category.catid=subcategory.catid";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			comboBoxSubCategoryFilter.removeAllItems();
			while (rs.next()) {

				comboBoxSubCategoryFilter.addItem(rs.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void displaySubCategoriesPaymentSide() {

		String sql = "select subcategory.scatname from subcategory,category where category.catname='"
				+ comboBoxcategory.getSelectedItem() + "' and category.catid=subcategory.catid";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			comboBoxSubCategory.removeAllItems();
			while (rs.next()) {

				comboBoxSubCategory.addItem(rs.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void displayInComboBox(JComboBox combo, String query) {

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();

			combo.removeAllItems();
			while (rs.next()) {

				combo.addItem(rs.getString(1));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void displayInTexFields(String query) {

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();

			if (rs.next()) {

				fieldAddressStudent.setText(rs.getString(2));
				fieldChooserDOBStudent.setText(rs.getString(4));
				fieldChooserEnrollmentStudent.setText(rs.getString(3));
				fieldNameStudent.setText(rs.getString(1));
				comboBoxClassStudent.setSelectedItem(rs.getString(5));

			} else {
				fieldAddressStudent.setText(null);
				fieldChooserDOBStudent.setText(null);
				fieldChooserEnrollmentStudent.setText(null);
				fieldNameStudent.setText(null);
				comboBoxClassStudent.setSelectedItem(null);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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

	public void changeTableCellColor(JTable table, int column_index) {
		table.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				if (!isSelected) {
					double age = Double.parseDouble(table.getValueAt(row, 8).toString());
					// you can set your own implementation here. As an example I
					// have checked if the age is over 20 or not. According to the
					// age return from the table column you get different background colors.
					int req_age = 20;
					// in your case req_age can be rs.getString("column_name"); or something
					// you need to check with..
					if (age < req_age) {
						c.setBackground(new java.awt.Color(255, 101, 18));
					} else {
						c.setBackground(new java.awt.Color(0, 204, 0));
					}

				}
				return c;
			}
		});
	}

	public void fillData(JTable table, java.io.File file) {

		try {

			XSSFWorkbook workbook1 = new XSSFWorkbook();

			XSSFSheet fSheet = workbook1.createSheet("Data Sheet");

			TableModel model = table.getModel();

			CellStyle style = workbook1.createCellStyle();
			CellStyle stylebody = workbook1.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			XSSFFont font = workbook1.createFont();
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);
			style.setFont(font);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setBorderBottom(CellStyle.BORDER_THIN);
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setBorderRight(CellStyle.BORDER_THIN);
			style.setBorderTop(CellStyle.BORDER_THIN);

			stylebody.setBorderBottom(CellStyle.BORDER_THIN);
			stylebody.setBorderLeft(CellStyle.BORDER_THIN);
			stylebody.setBorderRight(CellStyle.BORDER_THIN);
			stylebody.setBorderTop(CellStyle.BORDER_THIN);

			TableColumnModel model1 = table.getTableHeader().getColumnModel();

			XSSFRow fRow1 = fSheet.createRow((short) 0);
			for (int i = 0; i < model.getColumnCount(); i++) {

				XSSFCell cell1 = fRow1.createCell((short) i);
				cell1.setCellValue(model1.getColumn(i).getHeaderValue().toString());
				cell1.setCellStyle(style);
			}
			int j = 0;
			for (int i = 0; i < model.getRowCount(); i++) {
				XSSFRow fRow = fSheet.createRow((short) i + 1);
				for (j = 0; j < model.getColumnCount(); j++) {
					XSSFCell cell2 = fRow.createCell((short) j);

					Object object = model.getValueAt(i, j);
					if (object == "" || object == null) {
						cell2.setCellValue("");
					} else {
						cell2.setCellValue(model.getValueAt(i, j).toString());
					}

					cell2.setCellStyle(stylebody);
					fSheet.autoSizeColumn(j);
				}
			}
			fSheet.setDisplayGridlines(false);
			FileOutputStream fos = new FileOutputStream(new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
			workbook1.write(fos);
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void saveToBank() {

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement("insert into `" + fieldPACStudent.getSelectedItem()
					+ "`(account_name,debit) values('" + comboBoxSubCategory.getSelectedItem() + "','"
					+ fieldAmountDebitOrCreditLedger.getText() + "')");

			((PreparedStatement) pst).executeUpdate();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}
