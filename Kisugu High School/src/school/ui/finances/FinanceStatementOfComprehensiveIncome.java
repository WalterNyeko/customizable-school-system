package school.ui.finances;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.toedter.calendar.JYearChooser;

import clarion.finance.core.GeneralLedger;
import clarion.finance.dao.GeneralLedgerDAO;
import colgroup.ColumnGroup;
import colgroup.GroupableTableColumnModel;
import colgroup.GroupableTableHeader;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import school.ui.staff.StaffsPanel;

public class FinanceStatementOfComprehensiveIncome extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3414612723209113173L;
	public JLabel labelTableHeading;
	public JLabel labelYearOne;
	public JLabel labelYearTwo;
	public JLabel labelYearTerm;
	public JLabel labelComboTermOne;
	public JLabel labelComboTermTwo;

	public JYearChooser yearChooserOne;
	public JYearChooser yearChooserTwo;

	public JYearChooser yearChooserTerm;
	public JComboBox<String> comboBoxTermOne;
	public JComboBox<String> comboBoxTermTwo;

	public JPanel panelTopHolder;
	public JPanel panelTopHolderOne;
	public JPanel panelTopHolderTwo;

	public JTable tableComprehensiveIncome;
	public JScrollPane scrollPaneComprehensiveIncome;
	public JButton btnExport;
	public JButton btnPrint;
	public Connection conn;
	public JTable tableComprehensiveIncomeExpense;
	public JScrollPane scrollPaneComprehensiveIncomeExpense;
	public JLabel labelPeriodOneIncome;
	public JLabel labelPeriodOneIncomeValue;
	public JLabel labelPeriodTwoIncome;
	public JLabel labelPeriodTwoIncomeValue;
	public JLabel labelPeriodOneExpenditure;
	public JLabel labelPeriodOneExpenditureValue;
	public JLabel labelPeriodTwoExpenditure;
	public JLabel labelPeriodTwoExpenditureValue;
	public JLabel labelPeriodOneNetIncome;
	public JLabel labelPeriodTwoNetIncome;
	public JLabel labelPeriodOneNetIncomeValue;
	public JLabel labelPeriodTwoNetIncomeValue;
	private TableView tableViewAccountsExpenditure;
	private JPanel panelYear1;
	private JPanel panelYear2;
	private JPanel panelButtons;
	public DecimalFormat decimalformat;
	protected JFileChooser fileChooser;
	private GroupableTableHeader headerExpense;
	private JButton btnPrintRevenue;
	private JButton btnPrintExpense;

	public FinanceStatementOfComprehensiveIncome() {
		statement();
	}

	@SuppressWarnings("unchecked")
	private void statement() {

		setBorder(new LineBorder(Color.blue, 3));
		setPreferredSize(new Dimension(1165, 485));
		setBackground(Color.decode("#5f9ea0"));

		panelTopHolder = new JPanel();
		panelTopHolder.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelTopHolder.setSize(580, 100);
		add(panelTopHolder);

		panelTopHolderOne = new JPanel();
		panelTopHolderOne.setBorder(new LineBorder(Color.white, 3));
		panelTopHolder.add(panelTopHolderOne);

		panelTopHolderTwo = new JPanel();
		panelTopHolderTwo.setBorder(new LineBorder(Color.white, 3));
		panelTopHolder.add(panelTopHolderTwo);

		Dimension dimensionLabels = new Dimension(120, 30);
		Dimension dimensionfields = new Dimension(150, 30);

		labelYearOne = new JLabel("Period 1");
		labelYearOne.setPreferredSize(dimensionLabels);
		panelTopHolderOne.add(labelYearOne);

		yearChooserOne = new JYearChooser();
		yearChooserOne.getYear();
		yearChooserOne.setPreferredSize(dimensionfields);
		yearChooserOne.addPropertyChangeListener("year", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub

				panelYear1.setBorder(new TitledBorder(yearChooserOne.getYear() + " Net Income"));
				labelPeriodOneIncome.setText(yearChooserOne.getYear() + " Income");
				labelPeriodOneExpenditure.setText(yearChooserOne.getYear() + " Expense");
				labelPeriodOneNetIncome.setText(yearChooserOne.getYear() + " Net Income");
				displayStatementOfComprehensiveIncome(tableComprehensiveIncome,
						"select subcategory,sum(`" + comboBoxTermOne.getSelectedItem() + "`),sum(`"
								+ yearChooserOne.getYear() + "`),sum(`" + comboBoxTermTwo.getSelectedItem() + "`),sum(`"
								+ yearChooserTwo.getYear()
								+ "`) from budget_expense_income_records where account_type='Income' group by "
								+ "subcategory");

				displayStatementOfComprehensiveIncome(tableComprehensiveIncomeExpense,
						"select subcategory,sum(`" + comboBoxTermOne.getSelectedItem() + "`)," + "sum(`"
								+ yearChooserOne.getYear() + "`), " + "sum(`" + comboBoxTermTwo.getSelectedItem()
								+ "`),sum(`" + yearChooserTwo.getYear()
								+ "`) from budget_expense_income_records where account_type='Expense' group by "
								+ "subcategory UNION SELECT 'Depreciation Expense',0 as budget1, sum(asset_cost)-sum(`"
								+ yearChooserOne.getYear() + "`),0 as budget2, " + "sum(asset_cost)-sum(`"
								+ yearChooserTwo.getYear() + "`) from school_fixed_assets");
				labelPeriodOneIncomeValue
				.setText("" + decimalformat.format(
						getSumOfActualIncomeInPeriodOne()));
		labelPeriodTwoIncomeValue
				.setText("" + decimalformat.format(
						getSumOfActualIncomeInPeriodTwo()));
		labelPeriodOneExpenditureValue
				.setText("" + decimalformat.format(
						getSumOfActualExpenseInPeriodOne()));
		labelPeriodTwoExpenditureValue
				.setText("" + decimalformat.format(
						getSumOfActualExpenseInPeriodTwo()));

		double oneIncome = Double.parseDouble(Float.toString(getSumOfActualIncomeInPeriodOne()));
		double twoIncome = Double.parseDouble(Float.toString(getSumOfActualIncomeInPeriodTwo()));

		double oneExpenditure = Double.parseDouble(Float.toString(getSumOfActualExpenseInPeriodOne()));
		double twoExpenditure = Double.parseDouble(Float.toString(getSumOfActualExpenseInPeriodTwo()));

		double oneNetIncome = oneIncome - oneExpenditure;
		double twoNetIncome = twoIncome - twoExpenditure;

		labelPeriodOneNetIncomeValue
				.setText("" + decimalformat.format(oneNetIncome));
		labelPeriodTwoNetIncomeValue
				.setText("" + decimalformat.format(twoNetIncome));

			}
		});
		panelTopHolderOne.add(yearChooserOne);

		labelYearTwo = new JLabel("Period 2");
		labelYearTwo.setPreferredSize(dimensionLabels);
		panelTopHolderTwo.add(labelYearTwo);

		yearChooserTwo = new JYearChooser();
		yearChooserTwo.getYear();
		yearChooserTwo.setPreferredSize(dimensionfields);
		yearChooserTwo.addPropertyChangeListener("year", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub

				panelYear2.setBorder(new TitledBorder(yearChooserTwo.getYear() + " Net Income"));
				labelPeriodTwoIncome.setText(yearChooserTwo.getYear() + " Income");
				labelPeriodTwoExpenditure.setText(yearChooserTwo.getYear() + " Expense");
				labelPeriodTwoNetIncome.setText(yearChooserTwo.getYear() + " Net Income");
				displayStatementOfComprehensiveIncome(tableComprehensiveIncome,
						"select subcategory,sum(`" + comboBoxTermOne.getSelectedItem() + "`),sum(`"
								+ yearChooserOne.getYear() + "`),sum(`" + comboBoxTermTwo.getSelectedItem() + "`),sum(`"
								+ yearChooserTwo.getYear()
								+ "`) from budget_expense_income_records where account_type='Income' group by "
								+ "subcategory");

				displayStatementOfComprehensiveIncome(tableComprehensiveIncomeExpense,
						"select subcategory,sum(`" + comboBoxTermOne.getSelectedItem() + "`)," + "sum(`"
								+ yearChooserOne.getYear() + "`), " + "sum(`" + comboBoxTermTwo.getSelectedItem()
								+ "`),sum(`" + yearChooserTwo.getYear()
								+ "`) from budget_expense_income_records where account_type='Expense' group by "
								+ "subcategory UNION SELECT 'Depreciation Expense',0 as budget1, sum(asset_cost)-sum(`"
								+ yearChooserOne.getYear() + "`),0 as budget2, " + "sum(asset_cost)-sum(`"
								+ yearChooserTwo.getYear() + "`) from school_fixed_assets");
				labelPeriodOneIncomeValue
				.setText("" + decimalformat.format(
						getSumOfActualIncomeInPeriodOne()));
		labelPeriodTwoIncomeValue
				.setText("" + decimalformat.format(
						getSumOfActualIncomeInPeriodTwo()));
		labelPeriodOneExpenditureValue
				.setText("" + decimalformat.format(
						getSumOfActualExpenseInPeriodOne()));
		labelPeriodTwoExpenditureValue
				.setText("" + decimalformat.format(
						getSumOfActualExpenseInPeriodTwo()));

		double oneIncome = Double.parseDouble(Float.toString(getSumOfActualIncomeInPeriodOne()));
		double twoIncome = Double.parseDouble(Float.toString(getSumOfActualIncomeInPeriodTwo()));

		double oneExpenditure = Double.parseDouble(Float.toString(getSumOfActualExpenseInPeriodOne()));
		double twoExpenditure = Double.parseDouble(Float.toString(getSumOfActualExpenseInPeriodTwo()));

		double oneNetIncome = oneIncome - oneExpenditure;
		double twoNetIncome = twoIncome - twoExpenditure;

		labelPeriodOneNetIncomeValue
				.setText("" + decimalformat.format(oneNetIncome));
		labelPeriodTwoNetIncomeValue
				.setText("" + decimalformat.format(twoNetIncome));

			}
		});
		panelTopHolderTwo.add(yearChooserTwo);

		labelComboTermOne = new JLabel("Financial Year One");
		labelComboTermOne.setPreferredSize(dimensionLabels);
		panelTopHolderOne.add(labelComboTermOne);

		comboBoxTermOne = new JComboBox<>();
		comboBoxTermOne.setPreferredSize(new Dimension(150, 30));
		
		
		panelTopHolderOne.add(comboBoxTermOne);
		comboBoxTermOne.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				displayStatementOfComprehensiveIncome(tableComprehensiveIncome,
						"select subcategory,sum(`" + comboBoxTermOne.getSelectedItem() + "`),sum(`"
								+ yearChooserOne.getYear() + "`),sum(`" + comboBoxTermTwo.getSelectedItem() + "`),sum(`"
								+ yearChooserTwo.getYear()
								+ "`) from budget_expense_income_records where account_type='Income' group by "
								+ "subcategory");
				displayStatementOfComprehensiveIncome(tableComprehensiveIncomeExpense,
						"select subcategory,sum(`" + comboBoxTermOne.getSelectedItem() + "`)," + "sum(`"
								+ yearChooserOne.getYear() + "`), " + "sum(`" + comboBoxTermTwo.getSelectedItem()
								+ "`),sum(`" + yearChooserTwo.getYear()
								+ "`) from budget_expense_income_records where account_type='Expense' group by "
								+ "subcategory");
				labelPeriodOneIncomeValue
				.setText("" + decimalformat.format(
						getSumOfActualIncomeInPeriodOne()));
		labelPeriodTwoIncomeValue
				.setText("" + decimalformat.format(
						getSumOfActualIncomeInPeriodTwo()));
		labelPeriodOneExpenditureValue
				.setText("" + decimalformat.format(
						getSumOfActualExpenseInPeriodOne()));
		labelPeriodTwoExpenditureValue
				.setText("" + decimalformat.format(
						getSumOfActualExpenseInPeriodTwo()));

		double oneIncome = Double.parseDouble(Float.toString(getSumOfActualIncomeInPeriodOne()));
		double twoIncome = Double.parseDouble(Float.toString(getSumOfActualIncomeInPeriodTwo()));

		double oneExpenditure = Double.parseDouble(Float.toString(getSumOfActualExpenseInPeriodOne()));
		double twoExpenditure = Double.parseDouble(Float.toString(getSumOfActualExpenseInPeriodTwo()));

		double oneNetIncome = oneIncome - oneExpenditure;
		double twoNetIncome = twoIncome - twoExpenditure;

		labelPeriodOneNetIncomeValue
				.setText("" + decimalformat.format(oneNetIncome));
		labelPeriodTwoNetIncomeValue
				.setText("" + decimalformat.format(twoNetIncome));
			}
		});

		labelComboTermTwo = new JLabel("Financial Year Two");
		labelComboTermTwo.setPreferredSize(dimensionLabels);
		panelTopHolderTwo.add(labelComboTermTwo);

		comboBoxTermTwo = new JComboBox<>();
		comboBoxTermTwo.setPreferredSize(new Dimension(150, 30));
		
		comboBoxTermTwo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				displayStatementOfComprehensiveIncome(tableComprehensiveIncome,
						"select subcategory,sum(`" + comboBoxTermOne.getSelectedItem() + "`),sum(`"
								+ yearChooserOne.getYear() + "`),sum(`" + comboBoxTermTwo.getSelectedItem() + "`),sum(`"
								+ yearChooserTwo.getYear()
								+ "`) from budget_expense_income_records where account_type='Income' group by "
								+ "subcategory");

				displayStatementOfComprehensiveIncome(tableComprehensiveIncomeExpense,
						"select subcategory,sum(`" + comboBoxTermOne.getSelectedItem() + "`)," + "sum(`"
								+ yearChooserOne.getYear() + "`), " + "sum(`" + comboBoxTermTwo.getSelectedItem()
								+ "`),sum(`" + yearChooserTwo.getYear()
								+ "`) from budget_expense_income_records where account_type='Expense' group by "
								+ "subcategory");
				labelPeriodOneIncomeValue
				.setText("" + decimalformat.format(
						getSumOfActualIncomeInPeriodOne()));
		labelPeriodTwoIncomeValue
				.setText("" + decimalformat.format(
						getSumOfActualIncomeInPeriodTwo()));
		labelPeriodOneExpenditureValue
				.setText("" + decimalformat.format(
						getSumOfActualExpenseInPeriodOne()));
		labelPeriodTwoExpenditureValue
				.setText("" + decimalformat.format(
						getSumOfActualExpenseInPeriodTwo()));

		double oneIncome = Double.parseDouble(Float.toString(getSumOfActualIncomeInPeriodOne()));
		double twoIncome = Double.parseDouble(Float.toString(getSumOfActualIncomeInPeriodTwo()));

		double oneExpenditure = Double.parseDouble(Float.toString(getSumOfActualExpenseInPeriodOne()));
		double twoExpenditure = Double.parseDouble(Float.toString(getSumOfActualExpenseInPeriodTwo()));

		double oneNetIncome = oneIncome - oneExpenditure;
		double twoNetIncome = twoIncome - twoExpenditure;

		labelPeriodOneNetIncomeValue
				.setText("" + decimalformat.format(oneNetIncome));
		labelPeriodTwoNetIncomeValue
				.setText("" + decimalformat.format(twoNetIncome));
			}
		});
		panelTopHolderTwo.add(comboBoxTermTwo);

		///// Revenue Table///////

		DefaultTableModel model = new DefaultTableModel();

		String[] head = { "Revenue", "Budget", "Actual", "Budget", "Actual" };

		Object[][] data = { { null, null, null, null, null }, { null, null, null, null, null },
				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
				{ null, null, null, null, null }

		};
		model.setDataVector(data, head);

		tableComprehensiveIncome = new JTable();
		tableComprehensiveIncome.setPreferredSize(new Dimension(570, 310));
		tableComprehensiveIncome.setRowHeight(30);

		JTableHeader hd = tableComprehensiveIncome.getTableHeader();
		hd.setPreferredSize(new Dimension(10, 40));
		tableComprehensiveIncome.setColumnModel(new GroupableTableColumnModel());
		tableComprehensiveIncome.setTableHeader(
				new GroupableTableHeader((GroupableTableColumnModel) tableComprehensiveIncome.getColumnModel()));
		tableComprehensiveIncome.setModel(model);

		//////////// Rendere to format columns
		////////////////////////

		tableComprehensiveIncome.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				return super.getTableCellRendererComponent(table, String.format("%.2f", (BigDecimal) value), isSelected,
						hasFocus, row, column);

			}
		});

		tableComprehensiveIncome.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				return super.getTableCellRendererComponent(table, String.format("%.2f", (BigDecimal) value), isSelected,
						hasFocus, row, column);

			}
		});

		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setBackground(Color.black);
		cellRenderer.setForeground(Color.white);

		GroupableTableColumnModel cm = (GroupableTableColumnModel) tableComprehensiveIncome.getColumnModel();
		ColumnGroup groupReceipts = new ColumnGroup("Period 1");
		groupReceipts.add(cm.getColumn(1));
		groupReceipts.add(cm.getColumn(2));

		ColumnGroup groupIssues = new ColumnGroup("Period 2");
		groupIssues.add(cm.getColumn(3));
		groupIssues.add(cm.getColumn(4));

		GroupableTableHeader header = (GroupableTableHeader) tableComprehensiveIncome.getTableHeader();
		cm.addColumnGroup(groupReceipts);
		cm.addColumnGroup(groupIssues);
		header.setColumnMargin();
		tableComprehensiveIncome.getColumnModel().getColumn(0).setPreferredWidth(200);

		scrollPaneComprehensiveIncome = new JScrollPane(tableComprehensiveIncome);
		scrollPaneComprehensiveIncome.setPreferredSize(new Dimension(575, 310));
		scrollPaneComprehensiveIncome.setBorder(new LineBorder(Color.white, 3));
		add(scrollPaneComprehensiveIncome);

		////////////// Expenditure Table/////////////////////

		DefaultTableModel modelExpense = new DefaultTableModel();

		String[] headExpense = { "Expenditure", "Budget", "Actual", "Budget", "Actual" };

		Object[][] dataExpense = { { null, null, null, null, null }, { null, null, null, null, null },
				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
				{ null, null, null, null, null }

		};
		modelExpense.setDataVector(dataExpense, headExpense);

		tableComprehensiveIncomeExpense = new JTable();
		tableComprehensiveIncomeExpense.setPreferredSize(new Dimension(570, 310));
		tableComprehensiveIncomeExpense.setRowHeight(30);

		JTableHeader hdExpense = tableComprehensiveIncomeExpense.getTableHeader();
		hdExpense.setPreferredSize(new Dimension(10, 30));
		tableComprehensiveIncomeExpense.setColumnModel(new GroupableTableColumnModel());
		tableComprehensiveIncomeExpense.setTableHeader(
				new GroupableTableHeader((GroupableTableColumnModel) tableComprehensiveIncomeExpense.getColumnModel()));
		tableComprehensiveIncomeExpense.setModel(modelExpense);
		
		//////////Renderer
		////////////////////////
		tableComprehensiveIncomeExpense.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				return super.getTableCellRendererComponent(table, String.format("%.2f", (BigDecimal) value), isSelected,
						hasFocus, row, column);

			}
		});

		tableComprehensiveIncomeExpense.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				return super.getTableCellRendererComponent(table, String.format("%.2f", (BigDecimal) value), isSelected,
						hasFocus, row, column);

			}
		});
		
		tableComprehensiveIncomeExpense.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				return super.getTableCellRendererComponent(table, String.format("%.2f", (BigDecimal) value), isSelected,
						hasFocus, row, column);

			}
		});

		tableComprehensiveIncomeExpense.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				return super.getTableCellRendererComponent(table, String.format("%.2f", (BigDecimal) value), isSelected,
						hasFocus, row, column);

			}
		});

		DefaultTableCellRenderer cellRendererExpense = new DefaultTableCellRenderer();
		cellRendererExpense.setBackground(Color.black);
		cellRendererExpense.setForeground(Color.white);

		GroupableTableColumnModel cmExpense = (GroupableTableColumnModel) tableComprehensiveIncomeExpense
				.getColumnModel();
		ColumnGroup groupReceiptsExpense = new ColumnGroup("Period 1");
		groupReceiptsExpense.add(cmExpense.getColumn(1));
		groupReceiptsExpense.add(cmExpense.getColumn(2));

		ColumnGroup groupIssuesExpense = new ColumnGroup("Period 2");
		groupIssuesExpense.add(cmExpense.getColumn(3));
		groupIssuesExpense.add(cmExpense.getColumn(4));

		headerExpense = (GroupableTableHeader) tableComprehensiveIncomeExpense.getTableHeader();
		cmExpense.addColumnGroup(groupReceiptsExpense);
		cmExpense.addColumnGroup(groupIssuesExpense);
		headerExpense.setColumnMargin();
		tableComprehensiveIncomeExpense.getColumnModel().getColumn(0).setPreferredWidth(200);

		scrollPaneComprehensiveIncomeExpense = new JScrollPane(tableComprehensiveIncomeExpense);
		scrollPaneComprehensiveIncomeExpense.setPreferredSize(new Dimension(575, 310));
		scrollPaneComprehensiveIncomeExpense.setBorder(new LineBorder(Color.white, 3));
		add(scrollPaneComprehensiveIncomeExpense);

		int year2 = Integer.parseInt("" + yearChooserTwo.getYear());
		int answer = year2 + 1;

		panelYear1 = new JPanel();
		panelYear1.setBorder(new TitledBorder(yearChooserOne.getYear() + " Net Income"));
		panelYear1.setPreferredSize(new Dimension(420, 100));
		panelYear2 = new JPanel();
		panelYear2.setBorder(new TitledBorder(answer + " Net Income"));
		panelYear2.setPreferredSize(new Dimension(420, 100));
		panelButtons = new JPanel();
		panelButtons.setBorder(new TitledBorder("Buttons"));
		panelButtons.setPreferredSize(new Dimension(305, 100));

		labelPeriodOneIncome = new JLabel();
		labelPeriodOneIncome.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelPeriodOneIncome.setForeground(Color.BLUE);
		labelPeriodOneIncome.setText(yearChooserOne.getYear() + " Income:");
		labelPeriodOneIncome.setPreferredSize(new Dimension(180, 20));
		panelYear1.add(labelPeriodOneIncome);

		labelPeriodOneIncomeValue = new JLabel();
		labelPeriodOneIncomeValue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelPeriodOneIncomeValue.setForeground(Color.BLUE);
		labelPeriodOneIncomeValue.setPreferredSize(new Dimension(180, 20));
		panelYear1.add(labelPeriodOneIncomeValue);

		labelPeriodTwoIncome = new JLabel();
		labelPeriodTwoIncome.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelPeriodTwoIncome.setForeground(Color.BLUE);
		labelPeriodTwoIncome.setText(answer + " Income:");
		labelPeriodTwoIncome.setPreferredSize(new Dimension(180, 20));
		panelYear2.add(labelPeriodTwoIncome);

		labelPeriodTwoIncomeValue = new JLabel();
		labelPeriodTwoIncomeValue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelPeriodTwoIncomeValue.setForeground(Color.BLUE);
		labelPeriodTwoIncomeValue.setPreferredSize(new Dimension(180, 20));
		panelYear2.add(labelPeriodTwoIncomeValue);

		labelPeriodOneExpenditure = new JLabel();
		labelPeriodOneExpenditure.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelPeriodOneExpenditure.setForeground(Color.BLUE);
		labelPeriodOneExpenditure.setText(yearChooserOne.getYear() + " Expenses:");
		labelPeriodOneExpenditure.setPreferredSize(new Dimension(180, 20));
		panelYear1.add(labelPeriodOneExpenditure);

		labelPeriodOneExpenditureValue = new JLabel();
		labelPeriodOneExpenditureValue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelPeriodOneExpenditureValue.setForeground(Color.BLUE);
		labelPeriodOneExpenditureValue.setPreferredSize(new Dimension(180, 20));
		panelYear1.add(labelPeriodOneExpenditureValue);

		labelPeriodTwoExpenditure = new JLabel();
		labelPeriodTwoExpenditure.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelPeriodTwoExpenditure.setForeground(Color.BLUE);
		labelPeriodTwoExpenditure.setText(answer + " Expenses:");
		labelPeriodTwoExpenditure.setPreferredSize(new Dimension(180, 20));
		panelYear2.add(labelPeriodTwoExpenditure);

		labelPeriodTwoExpenditureValue = new JLabel();
		labelPeriodTwoExpenditureValue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelPeriodTwoExpenditureValue.setForeground(Color.BLUE);
		labelPeriodTwoExpenditureValue.setPreferredSize(new Dimension(180, 20));
		panelYear2.add(labelPeriodTwoExpenditureValue);

		labelPeriodOneNetIncome = new JLabel();
		labelPeriodOneNetIncome.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelPeriodOneNetIncome.setForeground(Color.BLUE);
		labelPeriodOneNetIncome.setText(yearChooserOne.getYear() + " Net Income:");
		labelPeriodOneNetIncome.setPreferredSize(new Dimension(180, 20));
		panelYear1.add(labelPeriodOneNetIncome);

		labelPeriodOneNetIncomeValue = new JLabel();
		labelPeriodOneNetIncomeValue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelPeriodOneNetIncomeValue.setForeground(Color.BLUE);
		labelPeriodOneNetIncomeValue.setPreferredSize(new Dimension(180, 20));
		panelYear1.add(labelPeriodOneNetIncomeValue);

		labelPeriodTwoNetIncome = new JLabel();
		labelPeriodTwoNetIncome.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelPeriodTwoNetIncome.setForeground(Color.BLUE);
		labelPeriodTwoNetIncome.setText(answer + " Net Income:");
		labelPeriodTwoNetIncome.setPreferredSize(new Dimension(180, 20));
		panelYear2.add(labelPeriodTwoNetIncome);

		labelPeriodTwoNetIncomeValue = new JLabel();
		labelPeriodTwoNetIncomeValue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelPeriodTwoNetIncomeValue.setForeground(Color.BLUE);
		labelPeriodTwoNetIncomeValue.setPreferredSize(new Dimension(180, 20));
		panelYear2.add(labelPeriodTwoNetIncomeValue);

		btnExport = new JButton("Export Revenue");
		btnExport.setPreferredSize(new Dimension(120, 30));
		btnExport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				 fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify name and folder to export this deatils");

					int selected = fileChooser.showSaveDialog(FinanceStatementOfComprehensiveIncome.this);

					if (selected == JFileChooser.APPROVE_OPTION) {
						try {

							fillData(tableComprehensiveIncome, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}

			}
		});
		panelButtons.add(btnExport);

		btnPrint = new JButton("Export Expense");
		btnPrint.setPreferredSize(new Dimension(120, 30));
		btnPrint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				 fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify name and folder to export this deatils");

					int selected = fileChooser.showSaveDialog(FinanceStatementOfComprehensiveIncome.this);

					if (selected == JFileChooser.APPROVE_OPTION) {
						try {

							fillData(tableComprehensiveIncomeExpense, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}

			}
		});
		panelButtons.add(btnPrint);
		
		
		btnPrintRevenue = new JButton("Print Revenue");
		btnPrintRevenue.setPreferredSize(new Dimension(120, 30));
		btnPrintRevenue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				 fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify name and folder to save this deatils");

					int selected = fileChooser.showSaveDialog(FinanceStatementOfComprehensiveIncome.this);

					if (selected == JFileChooser.APPROVE_OPTION) {
						try {

							print(tableComprehensiveIncome, new java.io.File(fileChooser.getSelectedFile() + ".pdf"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}

			}
		});
		panelButtons.add(btnPrintRevenue);

		btnPrintExpense = new JButton("Print Expense");
		btnPrintExpense.setPreferredSize(new Dimension(120, 30));
		btnPrintExpense.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				 fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify name and folder to save this deatils");

					int selected = fileChooser.showSaveDialog(FinanceStatementOfComprehensiveIncome.this);

					if (selected == JFileChooser.APPROVE_OPTION) {
						try {

							print(tableComprehensiveIncomeExpense, new java.io.File(fileChooser.getSelectedFile() + ".pdf"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}

			}
		});
		panelButtons.add(btnPrintExpense);

		add(panelYear1);
		add(panelYear2);
		add(panelButtons);
		decimalformat =new DecimalFormat("#,###.00");
		

		// labelPeriodOneNetIncomeValue.setText(decimalformat.format(rs.getDouble(1)));

		labelPeriodOneNetIncomeValue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelPeriodOneNetIncomeValue.setForeground(Color.BLUE);
		Font font1 = labelPeriodOneNetIncomeValue.getFont();
		Map attributes1 = font1.getAttributes();
		attributes1.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		labelPeriodOneNetIncomeValue.setFont(font1.deriveFont(attributes1));

		labelPeriodTwoNetIncomeValue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelPeriodTwoNetIncomeValue.setForeground(Color.BLUE);
		Font font = labelPeriodTwoNetIncomeValue.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		labelPeriodTwoNetIncomeValue.setFont(font.deriveFont(attributes));

		
		displayInComboBoxOne(
				comboBoxTermTwo, "select fy_name from fy_names");

		displayInComboBoxOne(
				comboBoxTermOne, "select fy_name from fy_names");


	}

	public void displayStatementOfComprehensiveIncome(JTable table, String query) {

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

	public void displayInComboBoxOne(JComboBox<String> comboBoxTermOne, String query) {

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			
			comboBoxTermOne.removeAllItems();
			for (int i = 0; i < comboBoxTermOne.getItemCount(); i++) {
				comboBoxTermOne.remove(i);
			}
			while (rs.next()) {

				comboBoxTermOne.addItem(rs.getString(1));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated scatch block
					e.printStackTrace();
				}
			}
		}
	}

	
	public void displayInComboBoxTwo(JComboBox<String> comboBoxTermOne2, String query) {

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			
			comboBoxTermOne2.removeAllItems();

			for (int i = 0; i < comboBoxTermOne2.getItemCount(); i++) {
				comboBoxTermOne2.remove(i);
			}
			while (rs.next()) {

				comboBoxTermOne2.addItem(rs.getString(1));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated scatch block
					e.printStackTrace();
				}
			}
		}
	}

	public float getSumOfActualIncomeInPeriodOne() {

		int rowsCount = tableComprehensiveIncome.getRowCount();
		float sum = 0;
		for (int i = 0; i < rowsCount; i++) {
			sum = sum + Float.parseFloat(tableComprehensiveIncome.getValueAt(i, 2).toString());
		}

		return sum;

	}

	public float getSumOfActualIncomeInPeriodTwo() {

		int rowsCount = tableComprehensiveIncome.getRowCount();
		float sum = 0;
		for (int i = 0; i < rowsCount; i++) {
			sum = sum + Float.parseFloat(tableComprehensiveIncome.getValueAt(i, 4).toString());
		}

		return sum;

	}

	public float getSumOfActualExpenseInPeriodOne() {

		int rowsCount = tableComprehensiveIncomeExpense.getRowCount();
		float sum = 0;
		for (int i = 0; i < rowsCount; i++) {
			sum = sum + Float.parseFloat(tableComprehensiveIncomeExpense.getValueAt(i, 2).toString());
		}

		return sum;
	}

	public float getSumOfActualExpenseInPeriodTwo() {

		int rowsCount = tableComprehensiveIncomeExpense.getRowCount();
		float sum = 0;
		for (int i = 0; i < rowsCount; i++) {
			sum = sum + Float.parseFloat(tableComprehensiveIncomeExpense.getValueAt(i, 4).toString());
		}

		return sum;

	}

	public void populateExpenditureTable() {
		ObservableList<GeneralLedger> ledgers = null;
		GeneralLedgerDAO dao = new GeneralLedgerDAO();
		try {
			ledgers = dao.populateExpenditureTable();
		} catch (SQLException e) {
			// TODO Auto-generated scatch block
			e.printStackTrace();
		}

		tableViewAccountsExpenditure.itemsProperty().setValue(ledgers);

	}
	

	public void fillData(JTable table, java.io.File file) {

		try {


			XSSFWorkbook workbook1 = new XSSFWorkbook();
			
			XSSFSheet   fSheet = workbook1.createSheet("Data Sheet");

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

		        
		        table.setTableHeader(new GroupableTableHeader((GroupableTableColumnModel) table.getColumnModel()));
		        TableColumnModel model1 = table.getTableHeader().getColumnModel();
		        XSSFRow fRow1 = fSheet.createRow((short) 0);
			for (int i = 0; i < model.getColumnCount(); i++) {
				
				XSSFCell cell1 = fRow1.createCell((short) i);
				cell1.setCellValue(model1.getColumn(i).getHeaderValue().toString());
			    cell1.setCellStyle(style);
			}
			int j = 0;
			for (int i = 0; i < model.getRowCount(); i++) {
				XSSFRow fRow = fSheet.createRow((short) i+1);
				for (j = 0; j < model.getColumnCount(); j++) {
					  XSSFCell cell2 = fRow.createCell((short) j);
					  
					  Object object=model.getValueAt(i, j);
						if(object=="" || object==null) {
							cell2.setCellValue("");
						}else {
							cell2.setCellValue(model.getValueAt(i, j).toString());
						}
				    cell2.setCellStyle(stylebody);
				    fSheet.autoSizeColumn(j);
				}
			}
			fSheet.setDisplayGridlines(false);
			FileOutputStream fos =new FileOutputStream(new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
			workbook1.write(fos);
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}
	

	
	private void print(JTable table, java.io.File file) {
		com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4.rotate());
	    try {
	      PdfWriter writer = PdfWriter.getInstance((com.lowagie.text.Document) document, new FileOutputStream(new java.io.File(fileChooser.getSelectedFile() + ".pdf")));

	      ((com.lowagie.text.Document) document).open();
	      PdfContentByte cb = writer.getDirectContent();

	      cb.saveState();
	      Graphics2D g2 = cb.createGraphicsShapes(600, 600);

//	      Shape oldClip = g2.getClip();
//	      g2.clipRect(0, 0, 600, 600);

	      table.print(g2);
//	      g2.setClip(oldClip);

	      g2.dispose();
	      cb.restoreState();
	    } catch (Exception e) {
	      System.err.println(e.getMessage());
	    }
	    document.close();
		
		
		
	
	  }
}
