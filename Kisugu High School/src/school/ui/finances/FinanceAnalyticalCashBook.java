package school.ui.finances;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

import colgroup.ColumnGroup;
import colgroup.GroupableTableColumnModel;
import colgroup.GroupableTableHeader;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class FinanceAnalyticalCashBook extends JDialog {

	static Connection conn = null;
	private static javax.swing.JTree cat_tree;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;

	private JDateChooser dateChooser;
	private JTextField fieldReceiptNo;
	private JTextField fieldDetails;
	private JTextField fieldFolio;
	private JComboBox<String> comboBoxCashOrBank;
	private JComboBox<String> comboBoxDebitOrCredit;

	private JTextField fieldAmountBank;
	private JTextField fieldAmountCash;

	Connection con = null;
	Statement stm = null;

	private JLabel labelDate;
	private JLabel labelReceiptNo;
	private JLabel labelDetails;
	private JLabel labelFolio;
	private JLabel labelCashOrBank;
	private JLabel labelDebitOrCredit;

	private JLabel labelAmountBank;
	private JLabel labelAmountCash;

	private JPanel panelHoldLabels;

	private static JTable tableCashBook;
	private JScrollPane scrollPaneCashBook;

	private JPanel panelHoldBottomComponents;
	private JPanel panelHoldButtons;

	private JButton btnSave;
	private JButton btnPrint;
	private JButton btnExport;

	Connection con1 = null;
	Statement st = null;
	ResultSet rs = null;
	private JTable tableDorms;
	private JTable tableadmitted;
	Object hierarchy[];
	private JPanel panelHoldFields;
	private JPanel panelHoldCategory;
	private JPanel panelHoldSubCategory;
	private JLabel labelCategory;
	private JComboBox<Object> comboBoxCategory;
	private JLabel labelSubCategory;
	private JComboBox<Object> comboBoxSubCategory;
	private JLabel labelCreateCategory;
	private JTextField fieldCreateCategory;
	private JButton btnSaveCategory;
	private JLabel labelCategoryToBeCreated;
	private JComboBox<Object> comboBoxCategoryToBeCreated;
	private JLabel labelCreateSubCategory;
	private JTextField fieldCreateSubCategory;
	private JButton btnSaveSubCategory;
	protected String selectedItem;
	private static DefaultTableModel model;
	protected static String selected;
	protected Vector<?> tableModel;
	protected Object[] array;
	protected int depth;
	protected int currentIndex;
	protected int currentNoOfChildren;
	protected String selectedNode;
	private JLabel labelFrom;
	private JDateChooser dateFrom;
	private JLabel labelTo;
	private JDateChooser dateTo;
	private Object fWorkbook;
	protected JFileChooser fileChooser;
	private JLabel labelDrSum;
	private JLabel labelDrSumValue;
	private JLabel labelCrSum;
	private JLabel labelCrSumValue;
	private JButton btnRefreshTree;
	private JLabel labelYear;
	private JYearChooser YearChooser;
	private JLabel labelTx;
	private JComboBox<?> comboBoxAccountType;
	private JButton btnDelCat;
	private JButton btnDelSubCat;
	private JLabel labelBank;
	private JComboBox fieldBank;
	private PreparedStatement pst;
	private JLabel labelID;
	private JPopupMenu popupMenuDataCategory;
	private JButton btncancelEdit;
	protected String id;

	public static void main(String args[]) throws Exception {
		new FinanceAnalyticalCashBook();
	}

	public FinanceAnalyticalCashBook() throws Exception {

		setTitle("Analytical Cash Book");
		jScrollPane1 = new javax.swing.JScrollPane();
		cat_tree = new javax.swing.JTree();

		jScrollPane1.setViewportView(cat_tree);

		JPanel panelHome = new JPanel();
		panelHome.setLayout(new BorderLayout());
		panelHome.setBackground(Color.decode("#5f9ea0"));

		JPanel panelHolder = new JPanel();
		JPanel panelHolderRight = new JPanel();
		panelHolder.setBackground(Color.decode("#5f9ea0"));
		panelHolderRight.setBackground(Color.decode("#5f9ea0"));

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, panelHolder, panelHolderRight);

		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(0.5);
		getContentPane().add(panelHome);

		panelHolder.setLayout(new BorderLayout(5, 5));
		panelHolderRight.setLayout(new BorderLayout(5, 5));

		panelHolder.add(jScrollPane1, BorderLayout.CENTER);

		panelHoldLabels = new JPanel();
		panelHoldLabels.setPreferredSize(new Dimension(1050, 180));
		panelHoldLabels.setBorder(new LineBorder(Color.white, 3));
		panelHoldLabels.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		panelHoldButtons = new JPanel();
		panelHoldButtons.setPreferredSize(new Dimension(1050, 35));
		panelHoldButtons.setBorder(new LineBorder(Color.white, 3));
		panelHoldButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 2));
		panelHoldButtons.setBackground(Color.decode("#5f9ea0"));

		panelHoldFields = new JPanel();
		panelHoldFields.setPreferredSize(new Dimension(850, 160));
		panelHoldFields.setBorder(new TitledBorder("Expenditure and Income Entries"));
		panelHoldFields.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
		panelHoldFields.setBackground(Color.decode("#5f9ea0"));
		panelHoldLabels.add(panelHoldFields);

		panelHoldCategory = new JPanel();
		panelHoldCategory.setPreferredSize(new Dimension(180, 160));
		panelHoldCategory.setBorder(new TitledBorder("Create Category"));
		panelHoldCategory.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 5));
		panelHoldCategory.setBackground(Color.decode("#5f9ea0"));
		panelHoldLabels.add(panelHoldCategory);

		panelHoldSubCategory = new JPanel();
		panelHoldSubCategory.setPreferredSize(new Dimension(280, 160));
		panelHoldSubCategory.setBorder(new TitledBorder("Add SubCategory"));
		panelHoldSubCategory.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 5));
		panelHoldSubCategory.setBackground(Color.decode("#5f9ea0"));
		panelHoldLabels.add(panelHoldSubCategory);

		panelHoldBottomComponents = new JPanel();
		panelHoldBottomComponents.setLayout(new BorderLayout());

		panelHome.add(panelHoldLabels, BorderLayout.NORTH);
		panelHome.add(panelHoldBottomComponents, BorderLayout.CENTER);
		panelHome.add(panelHoldButtons, BorderLayout.SOUTH);

		panelHolder.setBorder(new LineBorder(Color.white, 3));

		panelHoldBottomComponents.add(splitPane, BorderLayout.CENTER);

		Font fontFields = new Font("Times New Roman", Font.BOLD, 11);

		// label size
		Dimension dimensionLabels = new Dimension(85, 25);

		// field size
		Dimension dimensionFields = new Dimension(130, 25);

		labelDate = new JLabel("Date:");
		labelDate.setPreferredSize(dimensionLabels);
		panelHoldFields.add(labelDate);

		dateChooser = new JDateChooser();
		dateChooser.getDate();
		dateChooser.setPreferredSize(dimensionFields);
		panelHoldFields.add(dateChooser);

		labelYear = new JLabel("Year:");
		labelYear.setPreferredSize(dimensionLabels);
		panelHoldFields.add(labelYear);

		YearChooser = new JYearChooser();
		YearChooser.getYear();
		YearChooser.setPreferredSize(dimensionFields);
		panelHoldFields.add(YearChooser);

		String[] debitOrcredit = { "Choose Dr/Cr", "Dr", "Cr" };

		labelDebitOrCredit = new JLabel("Debit/Credit");
		labelDebitOrCredit.setPreferredSize(dimensionLabels);
		panelHoldFields.add(labelDebitOrCredit);

		comboBoxDebitOrCredit = new JComboBox<>(debitOrcredit);
		comboBoxDebitOrCredit.setPreferredSize(dimensionFields);
		panelHoldFields.add(comboBoxDebitOrCredit);

		labelCashOrBank = new JLabel("Cash/Bank");
		labelCashOrBank.setPreferredSize(dimensionLabels);
		panelHoldFields.add(labelCashOrBank);

		String[] cashOrBankOrBoth = { "Choose C/B", "Cash", "Bank" };
		comboBoxCashOrBank = new JComboBox<>(cashOrBankOrBoth);
		comboBoxCashOrBank.setPreferredSize(dimensionFields);
		comboBoxCashOrBank.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int selectedItem = comboBoxCashOrBank.getSelectedIndex();
				switch (selectedItem) {
				case 0:
					fieldBank.setVisible(false);
					labelBank.setVisible(false);
					break;
				case 1:
					labelAmountCash.setVisible(true);
					fieldAmountCash.setVisible(true);
					labelAmountBank.setVisible(false);
					fieldAmountBank.setVisible(false);
					labelBank.setVisible(false);
					fieldBank.setVisible(false);
					btnSave.setVisible(true);

					break;
				case 2:
					labelAmountBank.setVisible(true);
					fieldAmountBank.setVisible(true);
					labelBank.setVisible(true);
					fieldBank.setVisible(true);
					labelAmountCash.setVisible(false);
					fieldAmountCash.setVisible(false);
					btnSave.setVisible(true);
					break;
				case 3:
					labelAmountCash.setVisible(true);
					fieldAmountCash.setVisible(true);
					labelAmountBank.setVisible(true);
					fieldAmountBank.setVisible(true);
					btnSave.setVisible(true);
					break;

				}

			}
		});
		panelHoldFields.add(comboBoxCashOrBank);

		labelBank = new JLabel("Bank Name");
		labelBank.setPreferredSize(dimensionLabels);
		panelHoldFields.add(labelBank);
		labelBank.setVisible(false);

		fieldBank = new JComboBox();
		fieldBank.setPreferredSize(dimensionFields);
		fieldBank.setFont(fontFields);
		panelHoldFields.add(fieldBank);
		fieldBank.setVisible(false);
		displayInComboBox(fieldBank, "select bank_name from banks");

		labelReceiptNo = new JLabel("Receipt No.");
		labelReceiptNo.setPreferredSize(dimensionLabels);
		panelHoldFields.add(labelReceiptNo);

		fieldReceiptNo = new JTextField();
		fieldReceiptNo.setPreferredSize(dimensionFields);
		fieldReceiptNo.setFont(fontFields);
		panelHoldFields.add(fieldReceiptNo);

		labelDetails = new JLabel("Details");
		labelDetails.setPreferredSize(dimensionLabels);
		panelHoldFields.add(labelDetails);

		fieldDetails = new JTextField();
		fieldDetails.setPreferredSize(dimensionFields);
		panelHoldFields.add(fieldDetails);

		labelFolio = new JLabel("Folio");
		labelFolio.setPreferredSize(dimensionLabels);
		panelHoldFields.add(labelFolio);

		fieldFolio = new JTextField();
		fieldFolio.setPreferredSize(dimensionFields);
		panelHoldFields.add(fieldFolio);

		labelAmountCash = new JLabel("Amount(Cash)");
		labelAmountCash.setPreferredSize(dimensionLabels);
		panelHoldFields.add(labelAmountCash);
		labelAmountCash.setVisible(false);

		fieldAmountCash = new JTextField();
		fieldAmountCash.setPreferredSize(dimensionFields);
		panelHoldFields.add(fieldAmountCash);
		fieldAmountCash.setVisible(false);
		fieldAmountCash.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (!fieldAmountCash.getText().isEmpty()) {
					btnSave.setEnabled(true);
				}

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		labelAmountBank = new JLabel("Amount(Bank)");
		labelAmountBank.setPreferredSize(dimensionLabels);
		panelHoldFields.add(labelAmountBank);
		labelAmountBank.setVisible(false);

		fieldAmountBank = new JTextField();
		fieldAmountBank.setPreferredSize(dimensionFields);
		panelHoldFields.add(fieldAmountBank);
		fieldAmountBank.setVisible(false);
		fieldAmountBank.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (!fieldAmountBank.getText().isEmpty()) {
					btnSave.setEnabled(true);
				}

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		labelCategory = new JLabel("Category");
		labelCategory.setPreferredSize(dimensionLabels);
		panelHoldFields.add(labelCategory);

		comboBoxCategory = new JComboBox<>();
		comboBoxCategory.setPreferredSize(dimensionFields);
		comboBoxCategory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				DisplaySubCategories();
			}
		});
		panelHoldFields.add(comboBoxCategory);

		labelSubCategory = new JLabel("SubCategory");
		labelSubCategory.setPreferredSize(dimensionLabels);
		panelHoldFields.add(labelSubCategory);

		comboBoxSubCategory = new JComboBox<>();
		comboBoxSubCategory.setPreferredSize(dimensionFields);
		panelHoldFields.add(comboBoxSubCategory);

		btnSave = new JButton("ENTER");
		panelHoldFields.add(btnSave);
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (btnSave.getText().equalsIgnoreCase("ENTER")) {
					if (comboBoxDebitOrCredit.getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(null,
								"Please choose whether the entry is an Expense or Income, i.e. Dr/Cr");
						return;
					} else if (comboBoxDebitOrCredit.getSelectedIndex() == 1) {/////debit

						if (comboBoxCashOrBank.getSelectedIndex() == 0) {
							///// If this is selected then no "Enter Button" will
							///// show itself////////////////
						} else if (comboBoxCashOrBank.getSelectedIndex() == 1) {////Cash

							new CashBookController().updateLedger("update general_ledger set amount_cr=amount_cr+'"
									+ fieldAmountCash.getText() + "', balance=balance-'" + fieldAmountCash.getText()
									+ "' where account_name='" + comboBoxSubCategory.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,date,receipt_number,details,folio,cash_dr,subcategory)"
											+ " select category.catid,'"
											+ convertFromUtilToSQLDate(dateChooser.getDate()) + "','"
											+ fieldReceiptNo.getText() + "'," + "'" + fieldDetails.getText() + "','"
											+ fieldFolio.getText() + "','" + fieldAmountCash.getText() + "'," + "'"
											+ comboBoxSubCategory.getSelectedItem()
											+ "' from category where category.catname='"
											+ comboBoxCategory.getSelectedItem() + "'");

							new CashBookController().updateLedger(
									"insert into budget_expense_income_records(catid,`" + YearChooser.getYear()
											+ "`,subcategory,account_type,income_expense)  select category.catid,'"
											+ fieldAmountCash.getText() + "','" + comboBoxSubCategory.getSelectedItem()
											+ "',(select account_type from subcategory where scatname='"
											+ comboBoxSubCategory.getSelectedItem() + "'),1 "
											+ "from category where category.catname='"
											+ comboBoxCategory.getSelectedItem() + "'");
							


							DisplayFinance(tableCashBook,
									"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
											+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr)"
											+ " as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
											+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book where cash_book.subcategory='"
											+ selected + "'");
							fieldDetails.setText(null);
							fieldReceiptNo.setText(null);
							fieldFolio.setText(null);
							fieldAmountBank.setText(null);
							fieldAmountCash.setText(null);
							dateChooser.setDate(null);
							comboBoxCashOrBank.setSelectedIndex(0);
							comboBoxDebitOrCredit.setSelectedIndex(0);
							comboBoxCategory.setSelectedIndex(0);

						} else if (comboBoxCashOrBank.getSelectedIndex() == 2) {/////bank

							new CashBookController().updateLedger("update general_ledger set amount_cr=amount_cr+'"
									+ fieldAmountCash.getText() + "', balance=balance-'" + fieldAmountBank.getText()
									+ "' where account_name='" + comboBoxSubCategory.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,date,receipt_number,details,folio,bank_dr,subcategory)"
											+ " select category.catid,'"
											+ convertFromUtilToSQLDate(dateChooser.getDate()) + "','"
											+ fieldReceiptNo.getText() + "'," + "'" + fieldDetails.getText() + "','"
											+ fieldFolio.getText() + "','" + fieldAmountBank.getText() + "'," + "'"
											+ comboBoxSubCategory.getSelectedItem()
											+ "' from category where category.catname='"
											+ comboBoxCategory.getSelectedItem() + "'");

							new CashBookController().updateLedger(
									"insert into budget_expense_income_records(catid,`" + YearChooser.getYear()
											+ "`,subcategory,account_type,income_expense)  select category.catid,'"
											+ fieldAmountBank.getText() + "','" + comboBoxSubCategory.getSelectedItem()
											+ "',(select account_type from subcategory where scatname='"
											+ comboBoxSubCategory.getSelectedItem()
											+ "'),1 from category where category.catname='"
											+ comboBoxCategory.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntries("insert into `" + fieldBank.getSelectedItem()
									+ "`(account_name,debit) values('" + comboBoxSubCategory.getSelectedItem() + "','"
									+ fieldAmountBank.getText() + "')");

							DisplayFinance(tableCashBook,
									"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
											+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr)"
											+ " as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
											+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book where cash_book.subcategory='"
											+ selected + "'");
							
							fieldDetails.setText(null);
							fieldReceiptNo.setText(null);
							fieldFolio.setText(null);
							fieldAmountBank.setText(null);
							fieldAmountCash.setText(null);
							dateChooser.setDate(null);
							comboBoxCashOrBank.setSelectedIndex(0);
							comboBoxDebitOrCredit.setSelectedIndex(0);
							comboBoxCategory.setSelectedIndex(0);

						}
					} else if (comboBoxDebitOrCredit.getSelectedIndex() == 2) {////credit

						if (comboBoxCashOrBank.getSelectedIndex() == 0) {
							///// If this is selected then no "Enter Button" will
							///// show itself////////////////
						} else if (comboBoxCashOrBank.getSelectedIndex() == 1) {/////cash

							new CashBookController().updateLedger("update general_ledger set amount_dr=amount_dr+'"
									+ fieldAmountCash.getText() + "', balance=balance-'" + fieldAmountCash.getText()
									+ "' where account_name='" + comboBoxSubCategory.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,date,receipt_number,details,folio,cash_cr,subcategory)"
											+ " select category.catid,'"
											+ convertFromUtilToSQLDate(dateChooser.getDate()) + "','"
											+ fieldReceiptNo.getText() + "'," + "'" + fieldDetails.getText() + "','"
											+ fieldFolio.getText() + "','" + fieldAmountCash.getText() + "'," + "'"
											+ comboBoxSubCategory.getSelectedItem()
											+ "' from category where category.catname='"
											+ comboBoxCategory.getSelectedItem() + "'");

							new CashBookController().updateLedger("insert into budget_expense_income_records(catid,`"
									+ YearChooser.getYear() + "`,subcategory,account_type)  select category.catid,'"
									+ fieldAmountCash.getText() + "','" + comboBoxSubCategory.getSelectedItem()
									+ "',(select account_type from subcategory where scatname='"
									+ comboBoxSubCategory.getSelectedItem()
									+ "') from category where category.catname='" + comboBoxCategory.getSelectedItem()
									+ "'");

							DisplayFinance(tableCashBook,
									"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
											+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr) as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
											+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book where cash_book.subcategory='"
											+ selected + "'");
							
							fieldDetails.setText(null);
							fieldReceiptNo.setText(null);
							fieldFolio.setText(null);
							fieldAmountBank.setText(null);
							fieldAmountCash.setText(null);
							dateChooser.setDate(null);
							comboBoxCashOrBank.setSelectedIndex(0);
							comboBoxDebitOrCredit.setSelectedIndex(0);
							comboBoxCategory.setSelectedIndex(0);

						} else if (comboBoxCashOrBank.getSelectedIndex() == 2) {//////////bank

							new CashBookController().updateLedger("update general_ledger set amount_dr=amount_dr+'"
									+ fieldAmountBank.getText() + "', balance=balance-'" + fieldAmountBank.getText()
									+ "' where account_name='" + comboBoxSubCategory.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,date,receipt_number,details,folio,bank_cr,subcategory)"
											+ " select category.catid,'"
											+ convertFromUtilToSQLDate(dateChooser.getDate()) + "','"
											+ fieldReceiptNo.getText() + "'," + "'" + fieldDetails.getText() + "','"
											+ fieldFolio.getText() + "','" + fieldAmountBank.getText() + "'," + "'"
											+ comboBoxSubCategory.getSelectedItem()
											+ "' from category where category.catname='"
											+ comboBoxCategory.getSelectedItem() + "'");

							new CashBookController().updateLedger("insert into budget_expense_income_records(catid,`"
									+ YearChooser.getYear() + "`,subcategory,account_type)  select category.catid,'"
									+ fieldAmountBank.getText() + "','" + comboBoxSubCategory.getSelectedItem()
									+ "',(select account_type from subcategory where scatname='"
									+ comboBoxSubCategory.getSelectedItem()
									+ "') from category where category.catname='" + comboBoxCategory.getSelectedItem()
									+ "'");

							new CashBookController().InsertCashBookEntries("insert into `" + fieldBank.getSelectedItem()
									+ "`(account_name,credit) values('" + comboBoxSubCategory.getSelectedItem() + "','"
									+ fieldAmountBank.getText() + "')");

							DisplayFinance(tableCashBook,
									"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
											+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr) as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
											+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book where cash_book.subcategory='"
											+ selected + "'");
							
							fieldDetails.setText(null);
							fieldReceiptNo.setText(null);
							fieldFolio.setText(null);
							fieldAmountBank.setText(null);
							fieldAmountCash.setText(null);
							dateChooser.setDate(null);
							comboBoxCashOrBank.setSelectedIndex(0);
							comboBoxDebitOrCredit.setSelectedIndex(0);
							comboBoxCategory.setSelectedIndex(0);

						} 

					}

				} else {
					
					
					
					
					if (comboBoxDebitOrCredit.getSelectedIndex() == 1) {/// debit
						

						if (comboBoxCashOrBank.getSelectedIndex() == 1) {//// cash
							AddUpdateDelete("update cash_book set date='"
									+ convertFromUtilToSQLDate(dateChooser.getDate()) + "',receipt_number='"
									+ fieldReceiptNo.getText() + "',details='" + fieldDetails.getText() + "',folio='"
									+ fieldFolio.getText() + "',cash_dr='" + fieldAmountCash.getText()
									+ "', subcategory='" + comboBoxSubCategory.getSelectedItem() + "' where id='"
									+ labelID.getText() + "'");

							AddUpdateDeleteExtra("update accounts_balanced_entries set debit='" + fieldAmountCash.getText()
									+ "',date='" + convertFromUtilToSQLDate(dateChooser.getDate())
									+ "' where cash_book_id='" + labelID.getText() + "' and account_name='Cash A/C' ");
							AddUpdateDeleteExtra("update accounts_balanced_entries set credit='" + fieldAmountCash.getText()
									+ "',date='" + convertFromUtilToSQLDate(dateChooser.getDate()) + "',details='"
									+ fieldDetails.getText() + "',account_name='"
									+ comboBoxSubCategory.getSelectedItem() + "' where cash_book_id='"
									+ labelID.getText()
									+ "' and account_name != 'Cash A/C' and account_name != 'Bank A/C' ");
							
							DisplayFinance(tableCashBook,
									"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
											+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr)"
											+ " as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
											+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book");

							DisplayFinanceTotals(labelDrSumValue, "select SUM(cash_dr+bank_dr) from cash_book");

							DisplayFinanceTotals(labelCrSumValue, "select SUM(cash_cr+bank_cr) from cash_book");
							
							btnSave.setText("ENTER");
							btncancelEdit.setVisible(false);
							btnDelCat.setVisible(true);
							btnDelSubCat.setVisible(true);
							btnSave.setEnabled(false);

							fieldDetails.setText(null);
							fieldReceiptNo.setText(null);
							fieldFolio.setText(null);
							fieldAmountBank.setText(null);
							fieldAmountCash.setText(null);
							dateChooser.setDate(null);
							comboBoxCashOrBank.setSelectedIndex(0);
							comboBoxDebitOrCredit.setSelectedIndex(0);
							comboBoxCategory.setSelectedIndex(0);

						} else if (comboBoxCashOrBank.getSelectedIndex() == 2) {/// bank
							AddUpdateDelete("update cash_book set date='"
									+ convertFromUtilToSQLDate(dateChooser.getDate()) + "',receipt_number='"
									+ fieldReceiptNo.getText() + "',details='" + fieldDetails.getText() + "',folio='"
									+ fieldFolio.getText() + "',bank_dr='" + fieldAmountBank.getText()
									+ "', subcategory='" + comboBoxSubCategory.getSelectedItem() + "' where id='"
									+ labelID.getText() + "'");

							AddUpdateDeleteExtra("update accounts_balanced_entries set debit='" + fieldAmountBank.getText()
									+ "',date='" + convertFromUtilToSQLDate(dateChooser.getDate())
									+ "' where cash_book_id='" + labelID.getText() + "' and account_name='Bank A/C' ");
							AddUpdateDeleteExtra("update accounts_balanced_entries set credit='" + fieldAmountBank.getText()
									+ "',date='" + convertFromUtilToSQLDate(dateChooser.getDate()) + "',details='"
									+ fieldDetails.getText() + "',account_name='"
									+ comboBoxSubCategory.getSelectedItem() + "' where cash_book_id='"
									+ labelID.getText()
									+ "' and account_name != 'Cash A/C' and account_name != 'Bank A/C' ");
							
							DisplayFinance(tableCashBook,
									"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
											+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr)"
											+ " as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
											+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book");

							DisplayFinanceTotals(labelDrSumValue, "select SUM(cash_dr+bank_dr) from cash_book");

							DisplayFinanceTotals(labelCrSumValue, "select SUM(cash_cr+bank_cr) from cash_book");
							
							btnSave.setText("ENTER");
							btnSave.setEnabled(false);
							btncancelEdit.setVisible(false);
							btnDelCat.setVisible(true);
							btnDelSubCat.setVisible(true);
							fieldDetails.setText(null);
							fieldReceiptNo.setText(null);
							fieldFolio.setText(null);
							fieldAmountBank.setText(null);
							fieldAmountCash.setText(null);
							dateChooser.setDate(null);
							comboBoxCashOrBank.setSelectedIndex(0);
							comboBoxDebitOrCredit.setSelectedIndex(0);
							comboBoxCategory.setSelectedIndex(0);


						}

					} else if (comboBoxDebitOrCredit.getSelectedIndex() == 2) {//// credit
						if (comboBoxCashOrBank.getSelectedIndex() == 1) {//// cash
							AddUpdateDelete("update cash_book set date='"
									+ convertFromUtilToSQLDate(dateChooser.getDate()) + "',receipt_number='"
									+ fieldReceiptNo.getText() + "',details='" + fieldDetails.getText() + "',folio='"
									+ fieldFolio.getText() + "',cash_cr='" + fieldAmountCash.getText()
									+ "', subcategory='" + comboBoxSubCategory.getSelectedItem() + "' where id='"
									+ labelID.getText() + "'");

							AddUpdateDeleteExtra("update accounts_balanced_entries set credit='" + fieldAmountCash.getText()
									+ "',date='" + convertFromUtilToSQLDate(dateChooser.getDate())
									+ "' where cash_book_id='" + labelID.getText() + "' and account_name='Cash A/C' ");
							AddUpdateDeleteExtra("update accounts_balanced_entries set debit='" + fieldAmountCash.getText()
									+ "',date='" + convertFromUtilToSQLDate(dateChooser.getDate()) + "',details='"
									+ fieldDetails.getText() + "',account_name='"
									+ comboBoxSubCategory.getSelectedItem() + "' where cash_book_id='"
									+ labelID.getText()
									+ "' and account_name != 'Cash A/C' and account_name != 'Bank A/C' ");
							
							
							DisplayFinance(tableCashBook,
									"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
											+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr)"
											+ " as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
											+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book");

							DisplayFinanceTotals(labelDrSumValue, "select SUM(cash_dr+bank_dr) from cash_book");

							DisplayFinanceTotals(labelCrSumValue, "select SUM(cash_cr+bank_cr) from cash_book");
							btnSave.setText("ENTER");
							btnSave.setEnabled(false);
							btncancelEdit.setVisible(false);
							btnDelCat.setVisible(true);
							btnDelSubCat.setVisible(true);
							fieldDetails.setText(null);
							fieldReceiptNo.setText(null);
							fieldFolio.setText(null);
							fieldAmountBank.setText(null);
							fieldAmountCash.setText(null);
							dateChooser.setDate(null);
							comboBoxCashOrBank.setSelectedIndex(0);
							comboBoxDebitOrCredit.setSelectedIndex(0);
							comboBoxCategory.setSelectedIndex(0);


						} else if (comboBoxCashOrBank.getSelectedIndex() == 2) {/// bank
							AddUpdateDelete("update cash_book set date='"
									+ convertFromUtilToSQLDate(dateChooser.getDate()) + "',receipt_number='"
									+ fieldReceiptNo.getText() + "',details='" + fieldDetails.getText() + "',folio='"
									+ fieldFolio.getText() + "',bank_cr='" + fieldAmountBank.getText()
									+ "', subcategory='" + comboBoxSubCategory.getSelectedItem() + "' where id='"
									+ labelID.getText() + "'");

							AddUpdateDeleteExtra("update accounts_balanced_entries set credit='" + fieldAmountBank.getText()
									+ "',date='" + convertFromUtilToSQLDate(dateChooser.getDate())
									+ "' where cash_book_id='" + labelID.getText() + "' and account_name='Bank A/C' ");
							AddUpdateDeleteExtra("update accounts_balanced_entries set debit='" + fieldAmountBank.getText()
									+ "',date='" + convertFromUtilToSQLDate(dateChooser.getDate()) + "',details='"
									+ fieldDetails.getText() + "',account_name='"
									+ comboBoxSubCategory.getSelectedItem() + "' where cash_book_id='"
									+ labelID.getText()
									+ "' and account_name != 'Cash A/C' and account_name != 'Bank A/C' ");
							
							DisplayFinance(tableCashBook,
									"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
											+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr)"
											+ " as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
											+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book");

							DisplayFinanceTotals(labelDrSumValue, "select SUM(cash_dr+bank_dr) from cash_book");

							DisplayFinanceTotals(labelCrSumValue, "select SUM(cash_cr+bank_cr) from cash_book");
							
							btnSave.setText("ENTER");
							btncancelEdit.setVisible(false);
							btnDelCat.setVisible(true);
							btnDelSubCat.setVisible(true);
							btnSave.setEnabled(false);
							fieldDetails.setText(null);
							fieldReceiptNo.setText(null);
							fieldFolio.setText(null);
							fieldAmountBank.setText(null);
							fieldAmountCash.setText(null);
							dateChooser.setDate(null);
							comboBoxCashOrBank.setSelectedIndex(0);
							comboBoxDebitOrCredit.setSelectedIndex(0);
							comboBoxCategory.setSelectedIndex(0);


						}
					}
				}
			}
		});
		btnSave.setVisible(false);
		btnSave.setEnabled(false);

		btnDelCat = new JButton("Del Cat");
		panelHoldFields.add(btnDelCat);
		btnDelCat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddUpdateDeleteCategory(
						"delete from category where catname='" + comboBoxCategory.getSelectedItem() + "'");

				pop_tree();
				DisplayCategoriesForSubCategoryEntries();
				DisplayCategoriesForExpenditureandIncomeEntries();

			}
		});

		btnDelSubCat = new JButton("Del Sub Cat");
		panelHoldFields.add(btnDelSubCat);
		btnDelSubCat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				AddUpdateDeleteSubCategory(
						"delete from subcategory where scatname='" + comboBoxSubCategory.getSelectedItem() + "'");
				pop_tree();
				DisplayCategoriesForSubCategoryEntries();
				DisplayCategoriesForExpenditureandIncomeEntries();

			}
		});
		
		
		btncancelEdit = new JButton("Cancel Edit");
		panelHoldFields.add(btncancelEdit);
		btncancelEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				btnSave.setText("ENTER");
				btnSave.setEnabled(false);
				btncancelEdit.setVisible(false);
				btnDelCat.setVisible(true);
				btnDelSubCat.setVisible(true);
				

				fieldDetails.setText(null);
				fieldReceiptNo.setText(null);
				fieldFolio.setText(null);
				fieldAmountBank.setText(null);
				fieldAmountCash.setText(null);
				dateChooser.setDate(null);
				comboBoxCashOrBank.setSelectedIndex(0);
				comboBoxDebitOrCredit.setSelectedIndex(0);
				comboBoxCategory.setSelectedIndex(0);

			}
		});
		
		btncancelEdit.setVisible(false);

		labelID = new JLabel();
		panelHoldFields.add(labelID);
		labelID.setVisible(false);

		// label size
		Dimension dimensionLabelsCat = new Dimension(110, 25);

		// field size
		Dimension dimensionFieldsCat = new Dimension(120, 25);

		///////// Create Category////////////////////////
		labelCreateCategory = new JLabel("Category Name");
		labelCreateCategory.setPreferredSize(dimensionLabelsCat);
		panelHoldCategory.add(labelCreateCategory);

		fieldCreateCategory = new JTextField();
		fieldCreateCategory.setPreferredSize(dimensionFieldsCat);
		panelHoldCategory.add(fieldCreateCategory);

		btnSaveCategory = new JButton("Create Category");
		btnSaveCategory.setPreferredSize(dimensionFieldsCat);
		btnSaveCategory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new CashBookController().CreateCategory(
						"insert into category(catname) values('" + fieldCreateCategory.getText() + "')");
				pop_tree();

				DisplayCategoriesForSubCategoryEntries();
				DisplayCategoriesForExpenditureandIncomeEntries();
				fieldCreateCategory.setText(null);

			}
		});
		panelHoldCategory.add(btnSaveCategory);

		////////////////// Create SubCategory//////////////////////

		labelCategoryToBeCreated = new JLabel("Choose Category");
		labelCategoryToBeCreated.setPreferredSize(dimensionLabelsCat);
		panelHoldSubCategory.add(labelCategoryToBeCreated);

		comboBoxCategoryToBeCreated = new JComboBox<>();
		comboBoxCategoryToBeCreated.setPreferredSize(dimensionFieldsCat);
		panelHoldSubCategory.add(comboBoxCategoryToBeCreated);

		labelCreateSubCategory = new JLabel("SubCategory Name");
		labelCreateSubCategory.setPreferredSize(dimensionLabelsCat);
		panelHoldSubCategory.add(labelCreateSubCategory);

		fieldCreateSubCategory = new JTextField();
		fieldCreateSubCategory.setPreferredSize(dimensionFieldsCat);
		panelHoldSubCategory.add(fieldCreateSubCategory);

		labelTx = new JLabel("Account Type:");
		labelTx.setPreferredSize(dimensionLabelsCat);
		panelHoldSubCategory.add(labelTx);

		String[] accounts = { "Choose Account Type", "Income", "Expense", "Capital", "Current Asset", "Fixed Asset",
				"Current Liability", "Long Term Liability" };

		comboBoxAccountType = new JComboBox<Object>(accounts);
		comboBoxAccountType.setPreferredSize(dimensionFieldsCat);
		panelHoldSubCategory.add(comboBoxAccountType);

		btnSaveSubCategory = new JButton("Add SubCategory");
		btnSaveSubCategory.setPreferredSize(dimensionFieldsCat);
		btnSaveSubCategory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (comboBoxAccountType.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Please Choose The Account Type Before Proceeding");
				} else {
					new CashBookController().CreateSubCategory(
							"insert into subcategory(catid,scatname,account_type) select category.catid,'"
									+ fieldCreateSubCategory.getText() + "','" + comboBoxAccountType.getSelectedItem()
									+ "' from category where category.catname='"
									+ comboBoxCategoryToBeCreated.getSelectedItem() + "'");

					comboBoxAccountType.setSelectedIndex(0);
					pop_tree();
					DisplayCategoriesForExpenditureandIncomeEntries();
					DisplayCategoriesForSubCategoryEntries();
					fieldCreateSubCategory.setText(null);

				}
			}
		});
		panelHoldSubCategory.add(btnSaveSubCategory);

		btnRefreshTree = new JButton("Refresh Tree");
		btnRefreshTree.setPreferredSize(dimensionFieldsCat);
		btnRefreshTree.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				pop_tree();
				DisplayCategoriesForExpenditureandIncomeEntries();
				DisplayCategoriesForSubCategoryEntries();

			}
		});
		panelHoldCategory.add(btnRefreshTree);

		/// lower components

		model = new DefaultTableModel();

		String[] headerCashBook = { "#ID", "Date", "Receipt No", "Details", "Folio", "Cash", "Bank", "Total", "Cash",
				"Bank", "Total" };

		Object[][] dataCashBook = { { null, null, null, null, null, null, null, null, null, null, null } };

		model.setDataVector(dataCashBook, headerCashBook);

		tableCashBook = new JTable();
		tableCashBook.setShowGrid(false);
		tableCashBook.setBackground(Color.decode("#5f9ea0"));
		tableCashBook.setRowHeight(30);

		// tab

		///// column grouping////
		tableCashBook.setColumnModel(new GroupableTableColumnModel());
		tableCashBook
				.setTableHeader(new GroupableTableHeader((GroupableTableColumnModel) tableCashBook.getColumnModel()));
		tableCashBook.setModel(model);
		////////////////////////

		GroupableTableColumnModel cm = (GroupableTableColumnModel) tableCashBook.getColumnModel();
		ColumnGroup groupDr = new ColumnGroup(
				"                                                                         " + "                  "
						+ "                                                  " + "                            Dr");
		groupDr.add(cm.getColumn(0));
		groupDr.add(cm.getColumn(1));
		groupDr.add(cm.getColumn(2));
		groupDr.add(cm.getColumn(3));
		groupDr.add(cm.getColumn(4));
		groupDr.add(cm.getColumn(5));
		groupDr.add(cm.getColumn(6));
		groupDr.add(cm.getColumn(7));

		ColumnGroup groupCr = new ColumnGroup("Cr");
		groupCr.add(cm.getColumn(8));
		groupCr.add(cm.getColumn(9));
		groupCr.add(cm.getColumn(10));
		GroupableTableHeader header = (GroupableTableHeader) tableCashBook.getTableHeader();
		cm.addColumnGroup(groupDr);
		cm.addColumnGroup(groupCr);
		header.setBackground(Color.black);
		header.setForeground(Color.white);
		header.setColumnMargin();

		// set Id column width
		tableCashBook.getColumnModel().getColumn(0).setMaxWidth(100);
		tableCashBook.getColumnModel().getColumn(0).setPreferredWidth(70);

		// set Date Number width
		tableCashBook.getColumnModel().getColumn(1).setMaxWidth(120);
		tableCashBook.getColumnModel().getColumn(1).setPreferredWidth(100);

		// set Receipt width
		tableCashBook.getColumnModel().getColumn(2).setMaxWidth(100);
		tableCashBook.getColumnModel().getColumn(2).setPreferredWidth(100);

		// set Details column width
		tableCashBook.getColumnModel().getColumn(3).setMaxWidth(350);
		tableCashBook.getColumnModel().getColumn(3).setPreferredWidth(200);

		// set Bank Dr width
		tableCashBook.getColumnModel().getColumn(4).setMaxWidth(300);
		tableCashBook.getColumnModel().getColumn(4).setPreferredWidth(120);
		tableCashBook.getColumnModel().getColumn(4).setCellRenderer(new NumberTableCellRenderer());

		// set Cash Dr width
		tableCashBook.getColumnModel().getColumn(5).setMaxWidth(300);
		tableCashBook.getColumnModel().getColumn(5).setPreferredWidth(120);
		tableCashBook.getColumnModel().getColumn(5).setCellRenderer(new NumberTableCellRenderer());

		// set Total Dr width
		tableCashBook.getColumnModel().getColumn(6).setMaxWidth(300);
		tableCashBook.getColumnModel().getColumn(6).setPreferredWidth(120);
		tableCashBook.getColumnModel().getColumn(6).setCellRenderer(new NumberTableCellRenderer());

		// set Cash Cr width
		tableCashBook.getColumnModel().getColumn(7).setMaxWidth(300);
		tableCashBook.getColumnModel().getColumn(7).setPreferredWidth(120);
		tableCashBook.getColumnModel().getColumn(7).setCellRenderer(new NumberTableCellRenderer());

		// set Cash Cr width
		tableCashBook.getColumnModel().getColumn(8).setMaxWidth(300);
		tableCashBook.getColumnModel().getColumn(8).setPreferredWidth(120);
		tableCashBook.getColumnModel().getColumn(8).setCellRenderer(new NumberTableCellRenderer());

		// set Bank Cr width
		tableCashBook.getColumnModel().getColumn(9).setMaxWidth(300);
		tableCashBook.getColumnModel().getColumn(9).setPreferredWidth(120);
		tableCashBook.getColumnModel().getColumn(9).setCellRenderer(new NumberTableCellRenderer());

		// set Tot Cr
		tableCashBook.getColumnModel().getColumn(10).setMaxWidth(300);
		tableCashBook.getColumnModel().getColumn(10).setPreferredWidth(120);
		tableCashBook.getColumnModel().getColumn(10).setCellRenderer(new NumberTableCellRenderer());

		tableCashBook.setFont(new Font("Times New Roman", Font.BOLD, 14));

		tableCashBook.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

				
				int row=tableCashBook.getSelectedRow();
				String id=tableCashBook.getValueAt(row, 0).toString();
				labelID.setText(id);
				
				if (SwingUtilities.isRightMouseButton(e)) {

					popupMenuDataCategory.show(cat_tree, e.getX(), e.getY());
				}
				

			}
		});

		scrollPaneCashBook = new JScrollPane(tableCashBook);

		scrollPaneCashBook.setPreferredSize(new Dimension(1050, 340));
		scrollPaneCashBook.setBorder(new LineBorder(Color.white, 3));

		panelHolderRight.add(scrollPaneCashBook, BorderLayout.CENTER);

		btnPrint = new JButton("Print");
		btnPrint.setPreferredSize(new Dimension(100, 25));
		panelHoldButtons.add(btnPrint);

		btnExport = new JButton("Export");
		btnExport.setPreferredSize(new Dimension(100, 25));
		btnExport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to export this deatils");

				int selected = fileChooser.showSaveDialog(FinanceAnalyticalCashBook.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						fillData1(tableCashBook, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		panelHoldButtons.add(btnExport);

		labelFrom = new JLabel("From:");
		panelHoldButtons.add(labelFrom);

		dateFrom = new JDateChooser();
		dateFrom.getDate();
		dateFrom.setPreferredSize(new Dimension(120, 25));
		panelHoldButtons.add(dateFrom);
		dateFrom.addPropertyChangeListener("date", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub
				if (cat_tree.getSelectionPath().getLastPathComponent().toString() != null) {

					if (selected.equalsIgnoreCase("Expenditure and Income")) {
						DisplayFinance(tableCashBook,
								"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
										+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr)"
										+ " as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
										+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book where date between "
										+ "'" + convertFromUtilToSQLDate(dateTo.getDate()) + "' and '"
										+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue()) + "'");

						DisplayFinanceTotals(labelDrSumValue,
								"select Sum(cash_dr+bank_dr) from cash_book where date between " + "'"
										+ convertFromUtilToSQLDate(dateTo.getDate()) + "' and '"
										+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue()) + "'");

						DisplayFinanceTotals(labelCrSumValue,
								"select Sum(cash_cr+bank_cr) from cash_book where date between " + "'"
										+ convertFromUtilToSQLDate(dateTo.getDate()) + "' and '"
										+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue()) + "'");

					} else {

						try {

							conn = CashBookController.getConnection();
							PreparedStatement pst = conn
									.prepareStatement("select catname from category where catname='" + selected + "'");
							ResultSet rs = pst.executeQuery();

							if (rs.next()) {

								DisplayFinance(tableCashBook,
										"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
												+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr) as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
												+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book where "
												+ "cash_book.catid=(select catid from category where catname='"
												+ selected + "') and date between " + "'"
												+ convertFromUtilToSQLDate(dateTo.getDate()) + "' and '"
												+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue()) + "'");

								DisplayFinanceTotals(labelDrSumValue,
										"select Sum(cash_dr+bank_dr) from cash_book where "
												+ "cash_book.catid=(select catid from category where catname='"
												+ selected + "') and date between " + "'"
												+ convertFromUtilToSQLDate(dateTo.getDate()) + "' and '"
												+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue()) + "'");

								DisplayFinanceTotals(labelCrSumValue,
										"select Sum(cash_cr+bank_cr) from cash_book where "
												+ "cash_book.catid=(select catid from category where catname='"
												+ selected + "') and date between " + "'"
												+ convertFromUtilToSQLDate(dateTo.getDate()) + "' and '"
												+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue()) + "'");

							} else {
								try {

									conn = CashBookController.getConnection();
									PreparedStatement pstSubcategory = conn.prepareStatement(
											"select scatname from subcategory where scatname='" + selected + "'");
									ResultSet rsSubcategory = pstSubcategory.executeQuery();

									if (rsSubcategory.next()) {

										DisplayFinance(tableCashBook,
												"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
														+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr) as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
														+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book where cash_book.subcategory='"
														+ selected + "' and date between " + "'"
														+ convertFromUtilToSQLDate(dateTo.getDate()) + "' and '"
														+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue())
														+ "'");

										DisplayFinanceTotals(labelDrSumValue,
												"select Sum(cash_dr+bank_dr) from cash_book where cash_book.subcategory='"
														+ selected + "' and date between " + "'"
														+ convertFromUtilToSQLDate(dateTo.getDate()) + "' and '"
														+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue())
														+ "'");

										DisplayFinanceTotals(labelCrSumValue,
												"select Sum(cash_cr+bank_cr) from cash_book where cash_book.subcategory='"
														+ selected + "' and date between " + "'"
														+ convertFromUtilToSQLDate(dateTo.getDate()) + "' and '"
														+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue())
														+ "'");

									}
									rsSubcategory.close();
									pstSubcategory.close();
									conn.close();

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							rs.close();
							pst.close();
							conn.close();

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}

			}
		});

		labelTo = new JLabel("To:");
		panelHoldButtons.add(labelTo);

		dateTo = new JDateChooser();
		dateTo.getDate();
		dateTo.setPreferredSize(new Dimension(120, 25));
		panelHoldButtons.add(dateTo);

		labelDrSum = new JLabel("Total (Debit):");
		labelDrSum.setPreferredSize(new Dimension(120, 25));
		labelDrSum.setForeground(Color.WHITE);
		labelDrSum.setFont(new Font("Times New Roman", Font.BOLD, 18));
		panelHoldButtons.add(labelDrSum);

		labelDrSumValue = new JLabel();
		labelDrSumValue.setPreferredSize(new Dimension(150, 25));
		labelDrSumValue.setForeground(Color.WHITE);
		labelDrSumValue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		panelHoldButtons.add(labelDrSumValue);

		labelCrSum = new JLabel("Total (Credit):");
		labelCrSum.setPreferredSize(new Dimension(120, 25));
		labelCrSum.setForeground(Color.WHITE);
		labelCrSum.setFont(new Font("Times New Roman", Font.BOLD, 18));
		panelHoldButtons.add(labelCrSum);

		labelCrSumValue = new JLabel();
		labelCrSumValue.setPreferredSize(new Dimension(150, 25));
		labelCrSumValue.setForeground(Color.WHITE);
		labelCrSumValue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		panelHoldButtons.add(labelCrSumValue);

		dateTo.addPropertyChangeListener("date", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub
				if (cat_tree.getSelectionPath().getLastPathComponent().toString() != null) {

					if (selected.equalsIgnoreCase("Expenditure and Income")) {
						DisplayFinance(tableCashBook,
								"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
										+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr)"
										+ " as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
										+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book where date between "
										+ "'" + convertFromUtilToSQLDate(dateFrom.getDate()) + "' and '"
										+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue()) + "'");

						DisplayFinanceTotals(labelDrSumValue,
								"select Sum(cash_dr+bank_dr) from cash_book where date between " + "'"
										+ convertFromUtilToSQLDate(dateFrom.getDate()) + "' and '"
										+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue()) + "'");

						DisplayFinanceTotals(labelCrSumValue,
								"select Sum(cash_cr+bank_cr) from cash_book where date between " + "'"
										+ convertFromUtilToSQLDate(dateFrom.getDate()) + "' and '"
										+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue()) + "'");

					} else {

						try {

							conn = CashBookController.getConnection();
							PreparedStatement pst = conn
									.prepareStatement("select catname from category where catname='" + selected + "'");
							ResultSet rs = pst.executeQuery();

							if (rs.next()) {

								DisplayFinance(tableCashBook,
										"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
												+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr) as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
												+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book where "
												+ "cash_book.catid=(select catid from category where catname='"
												+ selected + "') and date between " + "'"
												+ convertFromUtilToSQLDate(dateFrom.getDate()) + "' and '"
												+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue()) + "'");

								DisplayFinanceTotals(labelDrSumValue,
										"select Sum(cash_dr+bank_dr) from cash_book where "
												+ "cash_book.catid=(select catid from category where catname='"
												+ selected + "') and date between " + "'"
												+ convertFromUtilToSQLDate(dateFrom.getDate()) + "' and '"
												+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue()) + "'");

								DisplayFinanceTotals(labelCrSumValue,
										"select Sum(cash_cr+bank_cr) from cash_book where "
												+ "cash_book.catid=(select catid from category where catname='"
												+ selected + "') and date between " + "'"
												+ convertFromUtilToSQLDate(dateFrom.getDate()) + "' and '"
												+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue()) + "'");

							} else {
								try {

									conn = CashBookController.getConnection();
									PreparedStatement pstSubcategory = conn.prepareStatement(
											"select scatname from subcategory where scatname='" + selected + "'");
									ResultSet rsSubcategory = pstSubcategory.executeQuery();

									if (rsSubcategory.next()) {

										DisplayFinance(tableCashBook,
												"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
														+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr) as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
														+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book where cash_book.subcategory='"
														+ selected + "' and date between " + "'"
														+ convertFromUtilToSQLDate(dateFrom.getDate()) + "' and '"
														+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue())
														+ "'");

										DisplayFinanceTotals(labelDrSumValue,
												"select Sum(cash_dr+bank_dr) from cash_book where cash_book.subcategory='"
														+ selected + "' and date between " + "'"
														+ convertFromUtilToSQLDate(dateFrom.getDate()) + "' and '"
														+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue())
														+ "'");

										DisplayFinanceTotals(labelCrSumValue,
												"select Sum(cash_cr+bank_cr) from cash_book where cash_book.subcategory='"
														+ selected + "' and date between " + "'"
														+ convertFromUtilToSQLDate(dateFrom.getDate()) + "' and '"
														+ convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue())
														+ "'");

									}
									rsSubcategory.close();
									pstSubcategory.close();
									conn.close();

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							rs.close();
							pst.close();
							conn.close();

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}

			}
		});

		popupMenuDataCategory = new JPopupMenu();

		
		JMenuItem RemoveTransaction = new JMenuItem("Delete Transaction");
		RemoveTransaction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				
				AddUpdateDeleteCategoryTrxn("delete from cash_book where id='"+labelID.getText()+"'");
				AddUpdateDeleteCategoryTrxn("delete from accounts_balanced_entries where cash_book_id='"+labelID.getText()+"'");
				
				DisplayFinance(tableCashBook,
						"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
								+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr)"
								+ " as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
								+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book");

				DisplayFinanceTotals(labelDrSumValue, "select SUM(cash_dr+bank_dr) from cash_book");

				DisplayFinanceTotals(labelCrSumValue, "select SUM(cash_cr+bank_cr) from cash_book");
			}
		});

		
		JMenuItem EditTransaction = new JMenuItem("Edit Transaction");
		EditTransaction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				btncancelEdit.setVisible(true);
				btnDelCat.setVisible(false);
				btnDelSubCat.setVisible(false);
				int selectedRow = tableCashBook.getSelectedRow();
				id = tableCashBook.getValueAt(selectedRow, 0).toString();
				java.util.Date date = (java.util.Date) tableCashBook.getValueAt(selectedRow, 1);
				String receipt = tableCashBook.getValueAt(selectedRow, 2).toString();
				String details = tableCashBook.getValueAt(selectedRow, 3).toString();
				String folio = tableCashBook.getValueAt(selectedRow, 4).toString();

				String cashDR = tableCashBook.getValueAt(selectedRow, 5).toString();
				String bankDR = tableCashBook.getValueAt(selectedRow, 6).toString();
				String cashCR = tableCashBook.getValueAt(selectedRow, 8).toString();
				String bankCR = tableCashBook.getValueAt(selectedRow, 9).toString();

				Double cashdr = Double.parseDouble(cashDR);
				Double bankdr = Double.parseDouble(bankDR);
				Double cashcr = Double.parseDouble(cashCR);
				Double bankcr = Double.parseDouble(bankCR);

				double sumCash = cashdr + cashcr;
				double sumBank = bankdr + bankcr;

				displayInComboBoxCatAndSub(comboBoxCategory,
						"select catname from category where catid=(select catid from cash_book where id='" + id + "')");
				displayInComboBoxCatAndSub(comboBoxSubCategory,
						"select subcategory from cash_book where id='" + id + "'");

				if (sumCash > 0) {
					if (cashdr > 0) {
						comboBoxDebitOrCredit.setSelectedIndex(1);
					} else {
						comboBoxDebitOrCredit.setSelectedIndex(2);
					}
					comboBoxCashOrBank.setSelectedIndex(1);
					fieldAmountCash.setText("" + sumCash);
					labelID.setText(id);
					dateChooser.setDate(date);
					fieldDetails.setText(details);
					fieldFolio.setText(folio);
					fieldReceiptNo.setText(receipt);
					btnSave.setText("Edit");
					btnSave.setEnabled(true);

				} else if (sumBank > 0) {

					if (bankdr > 0) {
						comboBoxDebitOrCredit.setSelectedIndex(1);
					} else {
						comboBoxDebitOrCredit.setSelectedIndex(2);
					}

					comboBoxCashOrBank.setSelectedIndex(2);
					fieldAmountBank.setText("" + sumBank);
					labelID.setText(id);
					dateChooser.setDate(date);
					fieldDetails.setText(details);
					fieldFolio.setText(folio);
					fieldReceiptNo.setText(receipt);
					btnSave.setText("Edit");
					btnSave.setEnabled(true);

				}
				
			}
		});

		popupMenuDataCategory.add(EditTransaction);
		popupMenuDataCategory.add(RemoveTransaction);
		
			
		setSize(1350, 700);
		setLocationRelativeTo(null);
		cat_tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				// TODO Auto-generated method stub
				if (cat_tree.getSelectionPath().getLastPathComponent().toString() != null) {
					selected = arg0.getNewLeadSelectionPath().getLastPathComponent().toString();

					if (arg0.getNewLeadSelectionPath().getLastPathComponent().toString()
							.equalsIgnoreCase("Expenditure and Income")) {
						DisplayFinance(tableCashBook,
								"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
										+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr)"
										+ " as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
										+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book");

						DisplayFinanceTotals(labelDrSumValue, "select SUM(cash_dr+bank_dr) from cash_book");

						DisplayFinanceTotals(labelCrSumValue, "select SUM(cash_cr+bank_cr) from cash_book");

					} else {

						try {

							conn = CashBookController.getConnection();
							PreparedStatement pst = conn
									.prepareStatement("select catname from category where catname='" + selected + "'");
							ResultSet rs = pst.executeQuery();

							if (rs.next()) {

								DisplayFinance(tableCashBook,
										"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
												+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr) as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
												+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book where "
												+ "cash_book.catid=(select catid from category where catname='"
												+ selected + "')");

								DisplayFinanceTotals(labelDrSumValue,
										"select SUM(cash_dr+bank_dr) from cash_book where "
												+ "cash_book.catid=(select catid from category where catname='"
												+ selected + "')");

								DisplayFinanceTotals(labelCrSumValue,
										"select SUM(cash_cr+bank_cr) from cash_book where "
												+ "cash_book.catid=(select catid from category where catname='"
												+ selected + "')");

							} else {
								try {

									conn = CashBookController.getConnection();
									PreparedStatement pstSubcategory = conn.prepareStatement(
											"select scatname from subcategory where scatname='" + selected + "'");
									ResultSet rsSubcategory = pstSubcategory.executeQuery();

									if (rsSubcategory.next()) {

										DisplayFinance(tableCashBook,
												"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
														+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr) as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
														+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book where cash_book.subcategory='"
														+ selected + "'");

										DisplayFinanceTotals(labelDrSumValue,
												"select SUM(cash_dr+bank_dr) from cash_book where cash_book.subcategory='"
														+ selected + "'");

										DisplayFinanceTotals(labelCrSumValue,
												"select SUM(cash_cr+bank_cr) from cash_book where cash_book.subcategory='"
														+ selected + "'");
									}
									rsSubcategory.close();
									pstSubcategory.close();
									conn.close();

								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							rs.close();
							pst.close();
							conn.close();

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}

			}
		});

		DisplayFinance(tableCashBook,
				"Select cash_book.id,cash_book.date as Date,cash_book.receipt_number as Receipt_Number,cash_book.details as Details,"
						+ "cash_book.folio as Folio,cash_book.cash_dr as Cash_Dr,cash_book.bank_dr as Bank_Dr,(cash_book.cash_dr+cash_book.bank_dr)"
						+ " as Total,cash_book.Cash_Cr,cash_book.Bank_Cr"
						+ ",(cash_book.cash_cr+cash_book.bank_cr) as Total from cash_book");

		DisplayFinanceTotals(labelDrSumValue, "select SUM(cash_dr+bank_dr) from cash_book");

		DisplayFinanceTotals(labelCrSumValue, "select SUM(cash_cr+bank_cr) from cash_book");

		pop_tree();
		DisplayCategoriesForExpenditureandIncomeEntries();
		DisplayCategoriesForSubCategoryEntries();
		setVisible(true);
	}

	protected AbstractButton getTcBuilderTree() {
		// TODO Auto-generated method stub
		return null;
	}

	public final void pop_tree() {
		try {

			try {
				con = CashBookController.getConnection();
				stm = con.createStatement();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			ArrayList<Object> list = new ArrayList<Object>();
			list.add("Expenditure and Income");
			String sql = "SELECT * from category";

			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {
				Object value[] = { rs.getString(1), rs.getString(2) };
				list.add(value);
			}
			Object hierarchy[] = list.toArray();
			DefaultMutableTreeNode root = processHierarchy(hierarchy);

			DefaultTreeModel treeModel = new DefaultTreeModel(root);
			cat_tree.setModel(treeModel);
		} catch (Exception e) {
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	@SuppressWarnings("CallToThreadDumpStack")
	public DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(hierarchy[0]);
		try {
			int ctrow = 0;
			int i = 0;
			try {

				try {
					con = CashBookController.getConnection();
					stm = con.createStatement();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				String sql = "SELECT catid, catname from category";
				ResultSet rs = stm.executeQuery(sql);

				while (rs.next()) {
					ctrow = rs.getRow();
				}
				String L1Nam[] = new String[ctrow];
				String L1Id[] = new String[ctrow];
				ResultSet rs1 = stm.executeQuery(sql);
				while (rs1.next()) {
					L1Nam[i] = rs1.getString("catname");
					L1Id[i] = rs1.getString("catid");
					i++;
				}
				DefaultMutableTreeNode child, grandchild;
				for (int childIndex = 0; childIndex < L1Nam.length; childIndex++) {
					child = new DefaultMutableTreeNode(L1Nam[childIndex]);
					node.add(child);// add each created child to root
					String sql2 = "SELECT scatname from subcategory where catid= '" + L1Id[childIndex] + "' ";
					ResultSet rs3 = stm.executeQuery(sql2);
					while (rs3.next()) {
						grandchild = new DefaultMutableTreeNode(rs3.getString("scatname"));
						child.add(grandchild);// add each grandchild to each
												// child
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
		}

		return (node);
	}

	public void DisplayCategoriesForExpenditureandIncomeEntries() {
		String url = "jdbc:mysql://localhost:3306/school";
		String user = "root";
		String pass = "";
		String sql = "select catname from category";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			comboBoxCategory.removeAllItems();
			while (rs.next()) {

				comboBoxCategory.addItem(rs.getString(1));
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

	public void DisplayCategoriesForSubCategoryEntries() {
		String url = "jdbc:mysql://localhost:3306/school";
		String user = "root";
		String pass = "";
		String sql = "select catname from category";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			comboBoxCategoryToBeCreated.removeAllItems();
			while (rs.next()) {

				comboBoxCategoryToBeCreated.addItem(rs.getString(1));
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

	public void DisplaySubCategories() {
		String url = "jdbc:mysql://localhost:3306/school";
		String user = "root";
		String pass = "";
		String sql = "select subcategory.scatname from subcategory,category where category.catname='"
				+ comboBoxCategory.getSelectedItem() + "' and category.catid=subcategory.catid";
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

	public Date convertFromUtilToSQLDate(java.util.Date dateUtil) {

		if (dateUtil != null) {
			java.sql.Date sqlDate = new java.sql.Date(dateUtil.getTime());

			return sqlDate;
		} else {
			return null;
		}
	}

	public void DisplayFinance(JTable table, String query) {

		try {

			conn = CashBookController.getConnection();
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
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
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void DisplayFinanceTotals(JLabel label, String query) {

		try {

			conn = CashBookController.getConnection();
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				DecimalFormat decimalformat = new DecimalFormat("#,###.00");

				label.setText(decimalformat.format(rs.getDouble(1)));
			}
			rs.close();
			pst.close();
			conn.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void fillData(JTable table, java.io.File file) {

		try {

			WritableWorkbook workbook1 = Workbook.createWorkbook(file);
			WritableSheet sheet1 = workbook1.createSheet("First Sheet", 0);

			TableModel model = table.getModel();

			for (int i = 0; i < model.getColumnCount(); i++) {
				Label column = new Label(i, 0, model.getColumnName(i));
				sheet1.addCell(column);
			}
			int j = 0;
			for (int i = 0; i < model.getRowCount(); i++) {
				for (j = 0; j < model.getColumnCount(); j++) {
					Label row = new Label(j, i + 1, model.getValueAt(i, j).toString());
					sheet1.addCell(row);
				}
			}
			workbook1.write();
			workbook1.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void fillData1(JTable table, java.io.File file) {

		try {

			XSSFWorkbook workbook1 = new XSSFWorkbook();
			// Sheet sheet1 = workbook1.createSheet("Color Test");

			XSSFSheet fSheet;
			fSheet = workbook1.createSheet("New Sheet");

			TableModel model = table.getModel();

			CellStyle style = workbook1.createCellStyle();
			CellStyle stylebody = workbook1.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			XSSFFont font = workbook1.createFont();
			font.setColor(IndexedColors.BLACK.getIndex());
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
					cell2.setCellValue(model.getValueAt(i, j).toString());
					cell2.setCellStyle(stylebody);
				}
			}
			FileOutputStream fos = new FileOutputStream(new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
			workbook1.write(fos);
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public class NumberTableCellRenderer extends DefaultTableCellRenderer {

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

	public void displayInComboBox(JComboBox fieldBankName2, String query) {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			fieldBankName2.removeAll();

			while (rs.next()) {
				fieldBankName2.addItem(rs.getString(1));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		}

	}

	public void displayInComboBoxCatAndSub(JComboBox fieldBankName2, String query) {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				fieldBankName2.setSelectedItem(rs.getString(1));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		}

	}

	public void AddUpdateDeleteCategory(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			String[] options = { "Delete", "Cancel" };
			int ans = JOptionPane.showOptionDialog(null, "Are You Sure You Want to Delete the selected Category???",
					"Confirmation of Delete Request", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[1]);
			if (ans == 0) {

				pst.executeUpdate();
			} else {

			}

			JOptionPane.showMessageDialog(null, "Category Deleted Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cannot Delete This File " + ex.getMessage());

		}
	}

	public void AddUpdateDeleteCategoryTrxn(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			String[] options = { "Delete", "Cancel" };
			int ans = JOptionPane.showOptionDialog(null, "Are You Sure You Want to Delete the Selected Transaction???",
					"Confirmation of Delete Request", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[1]);
			if (ans == 0) {

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Transaction Deleted Successfully");
			} else {

			}

			

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cannot Delete This File " + ex.getMessage());

		}
	}

	
	
	public void AddUpdateDeleteSubCategory(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			String[] options = { "Delete", "Cancel" };
			int ans = JOptionPane.showOptionDialog(null, "Are You Sure You Want to Delete the selected Account???",
					"Confirmation of Delete Request", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[1]);
			if (ans == 0) {

				pst.executeUpdate();
			} else {

			}

			JOptionPane.showMessageDialog(null, "Account Deleted Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cannot Delete This File " + ex.getMessage());

		}
	}

	public void AddUpdateDelete(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);
			String[] options = { "Edit", "Cancel" };
			int ans = JOptionPane.showOptionDialog(null, "Are You Sure You Want to Edit the selected Transaction???",
					"Confirmation of Delete Request", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[1]);
			if (ans == 0) {

				pst.executeUpdate();
			} else {

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed To Edit This Transaction: " + ex.getMessage());

		}
	}
	
	public void AddUpdateDeleteExtra(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);
			
				pst.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed To Edit This Transaction: " + ex.getMessage());

		}
	}

}