package school.ui.finances;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

import clarion.finance.dao.AccountConnect;
import colgroup.ColumnGroup;
import colgroup.GroupableTableColumnModel;
import colgroup.GroupableTableHeader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import school.ui.staff.StaffsPanel;

public class FinanceStatementOfFinancialPosition extends JPanel {

	private static ObservableList<UserMaster> row;

	private static ObservableList<String> col;

	public JLabel labelTableHeading;
	public JLabel labelYearOne;
	public JLabel labelYearTwo;
	public JLabel labelYearTerm;
	public JLabel labelComboTermOne;
	public JLabel labelComboTermTwo;

	public static JYearChooser yearChooserOne;
	public static JYearChooser yearChooserTwo;

	public JYearChooser yearChooserTerm;
	public JComboBox comboBoxTermOne;
	public JComboBox comboBoxTermTwo;

	public JPanel panelTopHolder;
	public JPanel panelTopHolderOne;
	public JPanel panelTopHolderTwo;

	public JTable tableFinancialPosition;
	public JScrollPane scrollPaneFinancialPosition;
	public JButton btnExport;
	public JButton btnPrint;
	public Connection conn;
	private VBox vBoxTable;
	private Scene scene;
	public TableView tableViewAccountsLiability;

	private JScrollPane scrollPaneFinancialPositionLiabilities;

	public JTable tableFinancialPositionLiabilities;

	private JScrollPane scrollPaneFinancialPositionCapital;

	public JTable tableFinancialPositionCapital;

	private JScrollPane scrollPaneFinancialPositionAssets;

	public JTable tableFinancialPositionAssets;

	private JPanel panelTopButtonPanel;

	private JButton btnExportAssets;

	private JButton btnExportLiabilities;

	private JButton btnExportCapital;

	private JButton btnPrintAssets;

	private JButton btnPrintLiabilities;

	private JButton btnPrintCapital;

	protected JFileChooser fileChooser;

	private JLabel paintingChild1;

	private JLabel paintingChild2;

	private JLabel paintingChild3;

	public FinanceStatementOfFinancialPosition() {
		statement();
	}

	@SuppressWarnings("unchecked")
	private void statement() {

		setBorder(new LineBorder(Color.blue, 3));
		setPreferredSize(new Dimension(1160, 480));
		setBackground(Color.decode("#5f9ea0"));

		panelTopHolder = new JPanel();
		panelTopHolder.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelTopHolder.setSize(1000, 100);
		add(panelTopHolder);

		panelTopHolderOne = new JPanel();
		panelTopHolderOne.setBorder(new LineBorder(Color.white, 3));
		panelTopHolder.add(panelTopHolderOne);
		panelTopHolderOne.setPreferredSize(new Dimension(1125, 45));

		Dimension dimensionLabels = new Dimension(130, 30);
		Dimension dimensionfields = new Dimension(150, 30);

		labelYearOne = new JLabel("Period 1");
		panelTopHolderOne.add(labelYearOne);

		yearChooserOne = new JYearChooser();
		yearChooserOne.getYear();
		yearChooserOne.setPreferredSize(dimensionfields);
		yearChooserOne.addPropertyChangeListener("year", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				displayBalanceSheet(
						tableFinancialPositionAssets,
						"select asset_name,`" + FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
								+ "`, `" + FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
								+ "` from school_fixed_assets UNION "
								+ "SELECT 'Fees Due', (select SUM(debit-credit) from student_ledger where year='"
								+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
								+ "'), (select SUM(debit-credit) from student_ledger where year='"
								+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
								+ "') from student_ledger"
								+ " UNION select 'Receivables',CASE WHEN SUM(debit)-SUM(credit)>0 and"
								+ " date LIKE '%" + FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
								+ "%' THEN SUM(debit)-SUM(credit)"
								+ " ELSE 0 END AS Debit,CASE WHEN SUM(debit)-SUM(credit)>0 and"
								+ " date LIKE '%" + FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
								+ "%' THEN SUM(debit)-SUM(credit)"
								+ " ELSE 0 END AS Debit from accounts_balanced_entries where account_name='Debtors A/C' UNION "
								+ "SELECT 'Cash Balance', CASE WHEN SUM(debit)-SUM(credit)>0 and date LIKE '%"
								+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
								+ "%' THEN SUM(debit)-SUM(credit) ELSE 0 END AS Cash,"
								+ "CASE WHEN SUM(debit)-SUM(credit)>0 and date LIKE '%"
								+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
								+ "%' THEN SUM(debit)-SUM(credit) ELSE 0 END AS Cash from accounts_balanced_entries where account_name='Cash A/C' "
								+ "UNION SELECT 'Bank Balance', CASE WHEN SUM(debit)-SUM(credit)>0 and date LIKE '%"
								+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
								+ "%' THEN SUM(debit)-SUM(credit) ELSE 0 END AS Bank,CASE WHEN SUM(debit)-SUM(credit)>0 and date LIKE '%"
								+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
								+ "%' THEN SUM(debit)-SUM(credit) ELSE 0 END AS Cash from accounts_balanced_entries where account_name='Bank A/C'");

				displayBalanceSheet(
						tableFinancialPositionLiabilities,
						"select 'Payables',CASE WHEN SUM(credit)-SUM(debit)>0 and date LIKE '%"
								+ yearChooserOne.getYear() + "%' "
								+ "THEN SUM(credit)-SUM(debit) ELSE 0 END AS Debit,CASE WHEN SUM(credit)-SUM(debit)>0 "
								+ "and date LIKE '%"
								+ yearChooserTwo.getYear()
								+ "%' THEN SUM(credit)-SUM(debit) ELSE 0 END AS Debit from accounts_balanced_entries where account_name='Creditors A/C' UNION "
								+ "SELECT 'Prepaid Fees', CASE WHEN SUM(credit)-SUM(debit)>0 "
								+ "and date LIKE '%"
								+ yearChooserOne.getYear() + "%' "
								+ "THEN SUM(credit)-SUM(debit) ELSE 0 END AS Extras,CASE WHEN SUM(credit)-SUM(debit)>0 "
								+ "and date LIKE '%"
								+ yearChooserTwo.getYear() + "%' "
								+ "THEN SUM(credit)-SUM(debit) ELSE 0 END AS Extras from student_ledger where credit is not null group by account_name "
								+ "UNION SELECT 'Bank Overdraft', CASE WHEN SUM(credit)-SUM(debit)>0 and date LIKE '%"
								+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
								+ "%' THEN SUM(credit)-SUM(debit) ELSE 0 END AS Bank,CASE WHEN SUM(credit)-SUM(debit)>0 and date LIKE '%"
								+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
								+ "%' THEN SUM(credit)-SUM(debit) ELSE 0 END AS Cash from accounts_balanced_entries where account_name='Bank A/C'");


				displayBalanceSheet(
						tableFinancialPositionCapital,
						"select subcategory,sum(`"
								+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear() + "`),sum(`"
								+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
								+ "`) from budget_expense_income_records"
								+ " where account_type='Capital' group by subcategory");
			}
		});
		panelTopHolderOne.add(yearChooserOne);

		labelYearTwo = new JLabel("Period 2");
		panelTopHolderOne.add(labelYearTwo);

		yearChooserTwo = new JYearChooser();
		yearChooserTwo.getYear();
		yearChooserTwo.setPreferredSize(dimensionfields);
		yearChooserTwo.addPropertyChangeListener("year", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				displayBalanceSheet(
						tableFinancialPositionAssets,
						"select asset_name,`" + FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
								+ "`, `" + FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
								+ "` from school_fixed_assets UNION "
								+ "SELECT 'Fees Due', (select SUM(debit-credit) from student_ledger where year='"
								+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
								+ "'), (select SUM(debit-credit) from student_ledger where year='"
								+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
								+ "') from student_ledger"
								+ " UNION select 'Receivables',CASE WHEN SUM(debit)-SUM(credit)>0 and"
								+ " date LIKE '%" + FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
								+ "%' THEN SUM(debit)-SUM(credit)"
								+ " ELSE 0 END AS Debit,CASE WHEN SUM(debit)-SUM(credit)>0 and"
								+ " date LIKE '%" + FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
								+ "%' THEN SUM(debit)-SUM(credit)"
								+ " ELSE 0 END AS Debit from accounts_balanced_entries where account_name='Debtors A/C' UNION "
								+ "SELECT 'Cash Balance', CASE WHEN SUM(debit)-SUM(credit)>0 and date LIKE '%"
								+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
								+ "%' THEN SUM(debit)-SUM(credit) ELSE 0 END AS Cash,"
								+ "CASE WHEN SUM(debit)-SUM(credit)>0 and date LIKE '%"
								+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
								+ "%' THEN SUM(debit)-SUM(credit) ELSE 0 END AS Cash from accounts_balanced_entries where account_name='Cash A/C' "
								+ "UNION SELECT 'Bank Balance', CASE WHEN SUM(debit)-SUM(credit)>0 and date LIKE '%"
								+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
								+ "%' THEN SUM(debit)-SUM(credit) ELSE 0 END AS Bank,CASE WHEN SUM(debit)-SUM(credit)>0 and date LIKE '%"
								+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
								+ "%' THEN SUM(debit)-SUM(credit) ELSE 0 END AS Cash from accounts_balanced_entries where account_name='Bank A/C'");

				displayBalanceSheet(
						tableFinancialPositionLiabilities,
						"select 'Payables',CASE WHEN SUM(credit)-SUM(debit)>0 and date LIKE '%"
								+ yearChooserOne.getYear() + "%' "
								+ "THEN SUM(credit)-SUM(debit) ELSE 0 END AS Debit,CASE WHEN SUM(credit)-SUM(debit)>0 "
								+ "and date LIKE '%"
								+ yearChooserTwo.getYear()
								+ "%' THEN SUM(credit)-SUM(debit) ELSE 0 END AS Debit from accounts_balanced_entries where account_name='Creditors A/C' UNION "
								+ "SELECT 'Prepaid Fees', CASE WHEN SUM(credit)-SUM(debit)>0 "
								+ "and date LIKE '%"
								+ yearChooserOne.getYear() + "%' "
								+ "THEN SUM(credit)-SUM(debit) ELSE 0 END AS Extras,CASE WHEN SUM(credit)-SUM(debit)>0 "
								+ "and date LIKE '%"
								+ yearChooserTwo.getYear() + "%' "
								+ "THEN SUM(credit)-SUM(debit) ELSE 0 END AS Extras from student_ledger where credit is not null group by account_name "
								+ "UNION SELECT 'Bank Overdraft', CASE WHEN SUM(credit)-SUM(debit)>0 and date LIKE '%"
								+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
								+ "%' THEN SUM(credit)-SUM(debit) ELSE 0 END AS Bank,CASE WHEN SUM(credit)-SUM(debit)>0 and date LIKE '%"
								+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
								+ "%' THEN SUM(credit)-SUM(debit) ELSE 0 END AS Cash from accounts_balanced_entries where account_name='Bank A/C'");

				displayBalanceSheet(
						tableFinancialPositionCapital,
						"select subcategory,sum(`"
								+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear() + "`),sum(`"
								+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
								+ "`) from budget_expense_income_records"
								+ " where account_type='Capital' group by subcategory");
			}
		});
		panelTopHolderOne.add(yearChooserTwo);

		///// Assets Table///////

		DefaultTableModel model = new DefaultTableModel();

		String[] head = { "Assets", "Period 1", "Period 2" };

		Object[][] data = { { null, null, null }, { null, null, null }, { null, null, null }, { null, null, null },
				{ null, null, null }, { null, null, null }

		};
		model.setDataVector(data, head);

		tableFinancialPositionAssets = new JTable();
		tableFinancialPositionAssets.setPreferredSize(new Dimension(370, 320));
		tableFinancialPositionAssets.setRowHeight(30);

		JTableHeader hd = tableFinancialPositionAssets.getTableHeader();
		hd.setPreferredSize(new Dimension(10, 40));
		tableFinancialPositionAssets.setColumnModel(new GroupableTableColumnModel());
		tableFinancialPositionAssets.setTableHeader(
				new GroupableTableHeader((GroupableTableColumnModel) tableFinancialPositionAssets.getColumnModel()));
		tableFinancialPositionAssets.setModel(model);
		////////////////////////

		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setBackground(Color.black);
		cellRenderer.setForeground(Color.white);

		GroupableTableColumnModel cm = (GroupableTableColumnModel) tableFinancialPositionAssets.getColumnModel();
		ColumnGroup groupReceipts = new ColumnGroup("Amount");
		groupReceipts.add(cm.getColumn(1));
		groupReceipts.add(cm.getColumn(2));

		GroupableTableHeader header = (GroupableTableHeader) tableFinancialPositionAssets.getTableHeader();
		cm.addColumnGroup(groupReceipts);
		header.setColumnMargin();
		tableFinancialPositionAssets.getColumnModel().getColumn(0).setPreferredWidth(130);

		scrollPaneFinancialPositionAssets = new JScrollPane(tableFinancialPositionAssets);
		scrollPaneFinancialPositionAssets.setPreferredSize(new Dimension(375, 320));
		scrollPaneFinancialPositionAssets.setBorder(new LineBorder(Color.white, 3));
		add(scrollPaneFinancialPositionAssets);

		////////////// Liabilities Table/////////////////////

		DefaultTableModel modelExpense = new DefaultTableModel();

		String[] headExpense = { "Liabilities", "Period 1", "Period 2" };

		Object[][] dataExpense = { { null, null, null }, { null, null, null }, { null, null, null },
				{ null, null, null }, { null, null, null }, { null, null, null }

		};
		modelExpense.setDataVector(dataExpense, headExpense);

		tableFinancialPositionLiabilities = new JTable();
		tableFinancialPositionLiabilities.setPreferredSize(new Dimension(370, 320));
		tableFinancialPositionLiabilities.setRowHeight(30);

		JTableHeader hdExpense = tableFinancialPositionLiabilities.getTableHeader();
		hdExpense.setPreferredSize(new Dimension(10, 30));
		tableFinancialPositionLiabilities.setColumnModel(new GroupableTableColumnModel());
		tableFinancialPositionLiabilities.setTableHeader(new GroupableTableHeader(
				(GroupableTableColumnModel) tableFinancialPositionLiabilities.getColumnModel()));
		tableFinancialPositionLiabilities.setModel(modelExpense);
		////////////////////////

		DefaultTableCellRenderer cellRendererExpense = new DefaultTableCellRenderer();
		cellRendererExpense.setBackground(Color.black);
		cellRendererExpense.setForeground(Color.white);

		GroupableTableColumnModel cmExpense = (GroupableTableColumnModel) tableFinancialPositionLiabilities
				.getColumnModel();
		ColumnGroup groupReceiptsExpense = new ColumnGroup("Amuont");
		groupReceiptsExpense.add(cmExpense.getColumn(1));
		groupReceiptsExpense.add(cmExpense.getColumn(2));

		GroupableTableHeader headerExpense = (GroupableTableHeader) tableFinancialPositionLiabilities.getTableHeader();
		cmExpense.addColumnGroup(groupReceiptsExpense);
		headerExpense.setColumnMargin();
		tableFinancialPositionLiabilities.getColumnModel().getColumn(0).setPreferredWidth(130);

		scrollPaneFinancialPositionLiabilities = new JScrollPane(tableFinancialPositionLiabilities);
		scrollPaneFinancialPositionLiabilities.setPreferredSize(new Dimension(375, 320));
		scrollPaneFinancialPositionLiabilities.setBorder(new LineBorder(Color.white, 3));
		add(scrollPaneFinancialPositionLiabilities);

		////////////// Capital Table/////////////////////

		DefaultTableModel modelCapitalCapital = new DefaultTableModel();

		String[] headCapitalCapital = { "Capital", "Period 1", "Period 2" };

		Object[][] dataCapitalCapital = { { null, null, null }, { null, null, null }, { null, null, null },
				{ null, null, null }, { null, null, null }, { null, null, null }

		};
		modelCapitalCapital.setDataVector(dataCapitalCapital, headCapitalCapital);

		tableFinancialPositionCapital = new JTable();
		tableFinancialPositionCapital.setPreferredSize(new Dimension(370, 320));
		tableFinancialPositionCapital.setRowHeight(30);

		JTableHeader hdCapital = tableFinancialPositionCapital.getTableHeader();
		hdCapital.setPreferredSize(new Dimension(10, 30));
		tableFinancialPositionCapital.setColumnModel(new GroupableTableColumnModel());
		tableFinancialPositionCapital.setTableHeader(
				new GroupableTableHeader((GroupableTableColumnModel) tableFinancialPositionCapital.getColumnModel()));
		tableFinancialPositionCapital.setModel(modelCapitalCapital);
		////////////////////////

		DefaultTableCellRenderer cellRendererCapital = new DefaultTableCellRenderer();
		cellRendererCapital.setBackground(Color.black);
		cellRendererCapital.setForeground(Color.white);

		GroupableTableColumnModel cmCapital = (GroupableTableColumnModel) tableFinancialPositionCapital
				.getColumnModel();
		ColumnGroup groupReceiptsCapital = new ColumnGroup("Amount");
		groupReceiptsCapital.add(cmCapital.getColumn(1));
		groupReceiptsCapital.add(cmCapital.getColumn(2));

		GroupableTableHeader headerCapital = (GroupableTableHeader) tableFinancialPositionCapital.getTableHeader();
		cmCapital.addColumnGroup(groupReceiptsCapital);
		headerCapital.setColumnMargin();
		tableFinancialPositionCapital.getColumnModel().getColumn(0).setPreferredWidth(130);

		scrollPaneFinancialPositionCapital = new JScrollPane(tableFinancialPositionCapital);
		scrollPaneFinancialPositionCapital.setPreferredSize(new Dimension(375, 320));
		scrollPaneFinancialPositionCapital.setBorder(new LineBorder(Color.white, 3));
		add(scrollPaneFinancialPositionCapital);

		panelTopButtonPanel = new JPanel();
		panelTopButtonPanel.setBorder(new LineBorder(Color.white, 3));
		add(panelTopButtonPanel);
		panelTopButtonPanel.setPreferredSize(new Dimension(1135, 80));

		btnExportAssets = new JButton("Export Assets");
		btnExportLiabilities = new JButton("Export Liabilities");
		btnExportCapital = new JButton("Export Capital");
		btnPrintAssets = new JButton("Print Assets");
		btnPrintLiabilities = new JButton("Print Liabilities");
		btnPrintCapital = new JButton("Print Capital");

		btnExportAssets.setPreferredSize(new Dimension(120, 25));
		btnExportLiabilities.setPreferredSize(new Dimension(120, 25));
		btnExportCapital.setPreferredSize(new Dimension(120, 25));
		btnPrintAssets.setPreferredSize(new Dimension(120, 25));
		btnPrintLiabilities.setPreferredSize(new Dimension(120, 25));
		btnPrintCapital.setPreferredSize(new Dimension(120, 25));
		
		paintingChild1= new JLabel();
		paintingChild1.setPreferredSize(new Dimension(120, 25));
		paintingChild2= new JLabel();
		paintingChild2.setPreferredSize(new Dimension(120, 25));
		paintingChild3= new JLabel();
		paintingChild3.setPreferredSize(new Dimension(80, 25));

		panelTopButtonPanel.add(btnExportAssets);
		panelTopButtonPanel.add(btnPrintAssets);
		panelTopButtonPanel.add(paintingChild1);
		panelTopButtonPanel.add(btnExportLiabilities);
		panelTopButtonPanel.add(btnPrintLiabilities);
		panelTopButtonPanel.add(paintingChild2);
		panelTopButtonPanel.add(btnExportCapital);
		panelTopButtonPanel.add(btnPrintCapital);
		
		
		panelTopButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,5,20));
		
		
//		panelTopButtonPanel.add(paintingChild3);

		btnExportAssets.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to export this deatils");

				int selected = fileChooser.showSaveDialog(FinanceStatementOfFinancialPosition.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						fillData(tableFinancialPositionAssets,
								new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			}
		});

		btnExportCapital.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to export this deatils");

				int selected = fileChooser.showSaveDialog(FinanceStatementOfFinancialPosition.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						fillData(tableFinancialPositionCapital,
								new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			}
		});

		btnExportLiabilities.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to export this deatils");

				int selected = fileChooser.showSaveDialog(FinanceStatementOfFinancialPosition.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						fillData(tableFinancialPositionLiabilities,
								new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			}
		});

		
		btnPrintAssets.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to save this deatils");

				int selected = fileChooser.showSaveDialog(FinanceStatementOfFinancialPosition.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						print(tableFinancialPositionAssets,
								new java.io.File(fileChooser.getSelectedFile() + ".pdf"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			}
		});

		btnPrintCapital.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to save this deatils");

				int selected = fileChooser.showSaveDialog(FinanceStatementOfFinancialPosition.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						print(tableFinancialPositionCapital,
								new java.io.File(fileChooser.getSelectedFile() + ".pdf"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			}
		});

		btnPrintLiabilities.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to save this deatils");

				int selected = fileChooser.showSaveDialog(FinanceStatementOfFinancialPosition.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						print(tableFinancialPositionLiabilities,
								new java.io.File(fileChooser.getSelectedFile() + ".pdf"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			}
		});

		
		
		displayBalanceSheet(tableFinancialPositionAssets, "select asset_name,`" + yearChooserOne.getYear() + "`, `"
				+ yearChooserTwo.getYear() + "` from school_fixed_assets UNION "
				+ "SELECT 'Fees Receivable', (select sum(debit)-sum(credit) from student_ledger where debit is not null and year='"
				+ yearChooserOne.getYear() + "' group by account_name),"
				+ "(select sum(debit)-sum(credit) from student_ledger where debit is not null and year='"
				+ yearChooserTwo.getYear() + "' group by account_name) from student_ledger where debit is not null"
				+ " UNION select account_name,CASE WHEN SUM(debit)-SUM(credit)>0 and" + " date LIKE '%"
				+ yearChooserOne.getYear() + "%' THEN SUM(debit)-SUM(credit)"
				+ " ELSE 0 END AS Debit,CASE WHEN SUM(debit)-SUM(credit)>0 and" + " date LIKE '%"
				+ yearChooserTwo.getYear() + "%' THEN SUM(debit)-SUM(credit)"
				+ " ELSE 0 END AS Debit from accounts_balanced_entries where account_name='Debtors A/C'");

		displayBalanceSheet(tableFinancialPositionLiabilities,
				"select account_name,CASE WHEN SUM(credit)-SUM(debit)>0 and date LIKE '%" + yearChooserOne.getYear()
						+ "%' " + "THEN SUM(credit)-SUM(debit) ELSE 0 END AS Debit,CASE WHEN SUM(credit)-SUM(debit)>0 "
						+ "and date LIKE '%" + yearChooserTwo.getYear()
						+ "%' THEN SUM(credit)-SUM(debit) ELSE 0 END AS Debit from accounts_balanced_entries where account_name='Creditors A/C' UNION "
						+ "SELECT 'Prepaid Fees', CASE WHEN SUM(credit)-SUM(debit)>0 " + "and date LIKE '%"
						+ yearChooserOne.getYear() + "%' "
						+ "THEN SUM(credit)-SUM(debit) ELSE 0 END AS Extras,CASE WHEN SUM(credit)-SUM(debit)>0 "
						+ "and date LIKE '%" + yearChooserTwo.getYear() + "%' "
						+ "THEN SUM(credit)-SUM(debit) ELSE 0 END AS Extras from student_ledger where debit is not null group by account_name");
		displayBalanceSheet(tableFinancialPositionCapital,
				"select subcategory,sum(`" + yearChooserOne.getYear() + "`),sum(`" + yearChooserTwo.getYear()
						+ "`) from budget_expense_income_records"
						+ " where account_type='Capital' group by subcategory");
	}

	public void displayBalanceSheet(JTable table, String query) {

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
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void displayInComboBox(JComboBox<String> combo, String query) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();

			combo.removeAllItems();
			combo.removeAll();
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

	public ObservableList<UserMaster> populateAssetsTable() throws SQLException {
		ObservableList<UserMaster> assets = FXCollections.observableArrayList();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = AccountConnect.getConnection().prepareStatement(
					"select * from budget_expense_income_records where account_type='Current Asset' or account_type='Fixed Asset'");

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				BigDecimal id = myRs.getBigDecimal("id");

				String subcategoory = myRs.getString("subcategory");

				int year1 = Integer.parseInt("" + yearChooserOne.getYear());
				int answer = year1;

				int year2 = Integer.parseInt("" + yearChooserOne.getYear());
				int answer2 = year2 + 1;

				String yearOne = myRs.getString("" + answer);
				String yearTwo = myRs.getString("" + answer2);

				// UserMaster assetsdata = new UserMaster(id, subcategoory, yearOne, yearTwo);
				//
				// assets.add(assetsdata);

			}

			return assets;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return assets;
	}

	public void populateAssetsTableWithData() {
		ObservableList<UserMaster> assets = null;
		try {
			assets = populateAssetsTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tableViewAccountsLiability.itemsProperty().setValue(assets);

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
					cell2.setCellValue(model.getValueAt(i, j).toString());
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
