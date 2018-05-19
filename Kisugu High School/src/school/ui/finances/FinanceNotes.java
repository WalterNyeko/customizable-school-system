package school.ui.finances;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

import colgroup.ColumnGroup;
import colgroup.GroupableTableColumnModel;
import colgroup.GroupableTableHeader;

public class FinanceNotes extends JPanel {

	public JPanel panelCreateNote;
	public JButton btnAddNote;
	public JLabel labelNoteName;
	public JLabel labelNoteCategory;
	public JTextField fieldNoteName;
	public JComboBox fieldNoteCategory;

	public JPanel panelNoteDetails;
	public JLabel labelNoteYear;
	public JLabel labelNoteTerm;
	public JLabel labelNoteDate;
	public JLabel labelNoteDetails;
	public JLabel labelNoteAmount;

	public JYearChooser chooserNoteYear;
	public JTextField fieldNoteTerm;
	public JDateChooser dateChooserNoteDate;
	public JComboBox fieldNoteDetails;
	public JTextField fieldNoteAmount;

	public JComboBox comboBoxNoteNames;
	public JButton btnEnterDetails;
	public JTable tableNotes;
	public JScrollPane scrollPaneNotes;
	public Component btnPrint;
	public JButton btnExport;
	public JPanel panelCreateBank;
	public JLabel labelBankName;
	public JTextField fieldBankName;
	public JLabel labelBankCategory;
	public JTextField fieldBankCategory;
	public JButton btnAddBank;
	public JButton btnEnterFY;
	public JButton btnEnterEstimates;
	public JComboBox comboBoxFY1;
	private JCheckBox checkDr;
	private JCheckBox checkCr;
	public JYearChooser yearChooserOne;
	private JLabel labelYearTwo;
	public JYearChooser yearChooserTwo;
	private Connection conn;
	private JLabel labelYearOne;
	private JPanel panelTopButtonPanel;
	private JButton btnExportNotes;
	private JButton btnPrintNotes;
	protected JFileChooser fileChooser;

	public FinanceNotes() {
		// TODO Auto-generated constructor stub
		setupNotes();
	}

	private void setupNotes() {

		setBorder(new LineBorder(Color.blue, 3));
		setPreferredSize(new Dimension(1160, 480));
		setBackground(Color.decode("#5f9ea0"));

		panelCreateNote = new JPanel();
		panelCreateNote.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		panelCreateNote.setPreferredSize(new Dimension(300, 120));
		panelCreateNote.setBorder(new LineBorder(Color.white, 3));
		add(panelCreateNote);

		panelNoteDetails = new JPanel();
		panelNoteDetails.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
		panelNoteDetails.setPreferredSize(new Dimension(550, 120));
		panelNoteDetails.setBorder(new LineBorder(Color.white, 3));
		add(panelNoteDetails);

		panelCreateBank = new JPanel();
		panelCreateBank.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		panelCreateBank.setPreferredSize(new Dimension(290, 120));
		panelCreateBank.setBorder(new LineBorder(Color.white, 3));
		add(panelCreateBank);

		Dimension dimensionLabels = new Dimension(90, 25);
		Dimension dimensionFields = new Dimension(150, 25);

		labelNoteName = new JLabel("Note Name:");
		labelNoteName.setPreferredSize(dimensionLabels);
		panelCreateNote.add(labelNoteName);
		fieldNoteName = new JTextField();
		fieldNoteName.setPreferredSize(dimensionFields);
		panelCreateNote.add(fieldNoteName);

		labelNoteCategory = new JLabel("Note Category");
		labelNoteCategory.setPreferredSize(dimensionLabels);
		panelCreateNote.add(labelNoteCategory);
		fieldNoteCategory = new JComboBox();
		fieldNoteCategory.setPreferredSize(dimensionFields);
		panelCreateNote.add(fieldNoteCategory);
		displayInComboBox(fieldNoteCategory, "select catname from category where note_name is null");

		btnAddNote = new JButton("CREATE NOTE");
		btnAddNote.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (checkDr.isSelected()) {

					AddItem("update category set note_name='" + fieldNoteName.getText().replaceAll("'", "''")
							+ "',transactions='Debit' where catname='" + fieldNoteCategory.getSelectedItem() + "'");
					displayInComboBox(fieldNoteCategory, "select catname from category where note_name is null");
				} else if (checkCr.isSelected()) {
					AddItem("update category set note_name='" + fieldNoteName.getText().replaceAll("'", "''")
							+ "',transactions='Credit' where catname='" + fieldNoteCategory.getSelectedItem() + "'");
					displayInComboBox(fieldNoteCategory, "select catname from category where note_name is null");
				} else {
					JOptionPane.showMessageDialog(null,
							"Please select whether the note category is a debit or credit transaction category");
				}

			}
		});
		panelCreateNote.add(btnAddNote);

		checkDr = new JCheckBox("Debit");
		checkDr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (checkDr.isSelected()) {
					checkCr.setSelected(false);
				}
			}
		});
		panelCreateNote.add(checkDr);

		checkCr = new JCheckBox("Credit");
		checkCr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (checkCr.isSelected()) {
					checkDr.setSelected(false);
				}
			}
		});
		panelCreateNote.add(checkCr);

		labelBankName = new JLabel("Bank Name:");
		labelBankName.setPreferredSize(dimensionLabels);
		panelCreateBank.add(labelBankName);
		fieldBankName = new JTextField();
		fieldBankName.setPreferredSize(dimensionFields);
		panelCreateBank.add(fieldBankName);

		labelBankCategory = new JLabel("Account Number");
		labelBankCategory.setPreferredSize(dimensionLabels);
		panelCreateBank.add(labelBankCategory);
		fieldBankCategory = new JTextField();
		fieldBankCategory.setPreferredSize(dimensionFields);
		panelCreateBank.add(fieldBankCategory);

		btnAddBank = new JButton("ADD BANK ACCOUNT");
		btnAddBank.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddBank("CREATE TABLE `" + fieldBankName.getText()
						+ "`(id int(50) PRIMARY KEY AUTO_INCREMENT, account_name varchar(200), date_of_transaction TIMESTAMP, debit decimal(12,2) default 0, credit decimal(12,2) default 0) ");

				AddBank("insert into banks(bank_name,account_number) values('" + fieldBankName.getText() + "','"
						+ fieldBankCategory.getText() + "')");
				fieldBankName.setText(null);
				fieldBankCategory.setText(null);
			}
		});
		panelCreateBank.add(btnAddBank);

		// labelNoteYear = new JLabel("Financial Year One:");
		// labelNoteYear.setPreferredSize(dimensionLabels);
		// panelNoteDetails.add(labelNoteYear);
		//
		// comboBoxFY1 = new JComboBox<>();
		// comboBoxFY1.setPreferredSize(dimensionFields);
		// panelNoteDetails.add(comboBoxFY1);
		//
		// displayInComboBox(comboBoxFY1, "select fy_name from fy_names");

		Dimension dimensionfields = new Dimension(150, 25);

		labelYearOne = new JLabel("Period 1");
		labelYearOne.setPreferredSize(dimensionLabels);
		panelNoteDetails.add(labelYearOne);

		yearChooserOne = new JYearChooser();
		yearChooserOne.getYear();
		yearChooserOne.setPreferredSize(dimensionfields);
		yearChooserOne.addPropertyChangeListener("year", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub

				displayNotes(tableNotes,
						"select budget_expense_income_records.date,budget_expense_income_records.subcategory,SUM(budget_expense_income_records.`"
								+ yearChooserOne.getYear() + "`), SUM(budget_expense_income_records.`"
								+ yearChooserTwo.getYear() + "`) from budget_expense_income_records "
								+ "group by budget_expense_income_records.subcategory");
			}
		});
		panelNoteDetails.add(yearChooserOne);

		labelYearTwo = new JLabel("Period 2");
		labelYearTwo.setPreferredSize(dimensionLabels);
		panelNoteDetails.add(labelYearTwo);

		yearChooserTwo = new JYearChooser();
		yearChooserTwo.getYear();
		yearChooserTwo.setPreferredSize(dimensionfields);
		yearChooserTwo.addPropertyChangeListener("year", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub

				displayNotes(tableNotes,
						"select budget_expense_income_records.date,budget_expense_income_records.subcategory,SUM(budget_expense_income_records.`"
								+ yearChooserOne.getYear() + "`), SUM(budget_expense_income_records.`"
								+ yearChooserTwo.getYear() + "`) from budget_expense_income_records "
								+ "group by budget_expense_income_records.subcategory");
			}
		});
		panelNoteDetails.add(yearChooserTwo);

		// labelNoteDetails = new JLabel("Financial Year Two:");
		// labelNoteDetails.setPreferredSize(dimensionLabels);
		// panelNoteDetails.add(labelNoteDetails);
		// fieldNoteDetails = new JComboBox();
		// fieldNoteDetails.setPreferredSize(dimensionFields);
		// panelNoteDetails.add(fieldNoteDetails);
		//
		// displayInComboBox(fieldNoteDetails, "select fy_name from fy_names");

		// labelNoteAmount = new JLabel("Amount");
		// labelNoteAmount.setPreferredSize(dimensionLabels);
		// panelNoteDetails.add(labelNoteAmount);
		// fieldNoteAmount = new JTextField();
		// fieldNoteAmount.setPreferredSize(dimensionFields);
		// panelNoteDetails.add(fieldNoteAmount);
		//
		// btnEnterDetails = new JButton("ENTER");
		// panelNoteDetails.add(btnEnterDetails);

		btnEnterFY = new JButton("Open FY");
		btnEnterFY.setPreferredSize(new Dimension(200, 25));
		btnEnterFY.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JDialog dialogFY = new JDialog();
				dialogFY.setTitle("Open Financial Year");
				dialogFY.setLocationRelativeTo(null);
				dialogFY.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialogFY.setSize(400, 250);

				JPanel panelHolder = new JPanel();
				JLabel labelYear = new JLabel("Financial Year:");
				labelYear.setPreferredSize(new Dimension(150, 25));
				dialogFY.getContentPane().add(panelHolder);
				panelHolder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
				panelHolder.add(labelYear);

				JYearChooser chooseYearName = new JYearChooser();
				chooseYearName.getYear();
				chooseYearName.setPreferredSize(new Dimension(150, 25));
				panelHolder.add(chooseYearName);

				JLabel labelYearName = new JLabel("Name Of Financial Year:");
				labelYearName.setPreferredSize(new Dimension(150, 25));
				panelHolder.add(labelYearName);

				JTextField chooseYear = new JTextField();
				chooseYear.setPreferredSize(new Dimension(150, 25));
				panelHolder.add(chooseYear);

				JButton btnOpen = new JButton("Open FY");
				btnOpen.setPreferredSize(new Dimension(150, 25));
				btnOpen.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub

						new CashBookController().updateLedger("alter table budget_expense_income_records add column `"
								+ chooseYear.getText() + "` decimal(12,2) default 0");
						new CashBookController().updateLedger("alter table budget_expense_income_records add column `"
								+ chooseYearName.getYear() + "` decimal(12,2) default 0");
						AddItem("insert into fy_names(fy_name) values('" + chooseYear.getText() + "')");
						dialogFY.dispose();

					}
				});
				panelHolder.add(btnOpen);

				dialogFY.setVisible(true);

			}
		});
		panelNoteDetails.add(btnEnterFY);

		btnEnterEstimates = new JButton("Add Budget Estimates");
		btnEnterEstimates.setPreferredSize(new Dimension(200, 25));
		btnEnterEstimates.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JDialog dialogEstimates = new JDialog();
				dialogEstimates.setTitle("Add Financial Year Estimates");
				dialogEstimates.setLocationRelativeTo(null);
				dialogEstimates.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialogEstimates.setSize(600, 250);

				JPanel panelHolder = new JPanel();
				JLabel labelYearname = new JLabel("Choose FY Name:");
				labelYearname.setPreferredSize(new Dimension(180, 25));
				dialogEstimates.getContentPane().add(panelHolder);
				panelHolder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
				panelHolder.add(labelYearname);

				JComboBox chooseItemNameFY = new JComboBox();
				chooseItemNameFY.setPreferredSize(new Dimension(260, 25));
				panelHolder.add(chooseItemNameFY);

				JLabel labelYear = new JLabel("FY Income/Expense:");
				labelYear.setPreferredSize(new Dimension(180, 25));
				panelHolder.add(labelYear);

				JComboBox chooseItemName = new JComboBox();
				chooseItemName.setPreferredSize(new Dimension(260, 25));
				panelHolder.add(chooseItemName);

				JLabel labelYearName = new JLabel("Amount Estimated:");
				labelYearName.setPreferredSize(new Dimension(180, 25));
				panelHolder.add(labelYearName);

				JTextField chooseYear = new JTextField();
				chooseYear.setPreferredSize(new Dimension(260, 25));
				panelHolder.add(chooseYear);

				JCheckBox boxIncome = new JCheckBox("Revenue");
				JCheckBox boxExpense = new JCheckBox("Expenditure");
				boxIncome.setPreferredSize(new Dimension(100, 25));
				boxExpense.setPreferredSize(new Dimension(100, 25));
				panelHolder.add(boxIncome);
				panelHolder.add(boxExpense);
				JButton btnOpen = new JButton("Add Estimate");
				btnOpen.setPreferredSize(new Dimension(150, 25));
				btnOpen.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxIncome.isSelected()) {
							AddItem("insert into budget_expense_income_records(`" + chooseItemNameFY.getSelectedItem()
									+ "`,subcategory,catid,account_type) " + "select '" + chooseYear.getText() + "', '"
									+ chooseItemName.getSelectedItem()
									+ "',subcategory.catid,'Income' from subcategory where scatname='"
									+ chooseItemName.getSelectedItem() + "'");
							displayInComboBox(chooseItemName, "select subcategory.scatname from subcategory");

						} else if (boxExpense.isSelected()) {
							AddItem("insert into budget_expense_income_records(`" + chooseItemNameFY.getSelectedItem()
									+ "`,subcategory,catid,account_type) " + "select '" + chooseYear.getText() + "', '"
									+ chooseItemName.getSelectedItem()
									+ "',subcategory.catid,'Expense' from subcategory where scatname='"
									+ chooseItemName.getSelectedItem() + "'");
						} else {
							JOptionPane.showMessageDialog(null, "Please Choose Re");
						}

					}
				});
				panelHolder.add(btnOpen);

				displayInComboBox(chooseItemNameFY, "select fy_name from fy_names");
				// displayInComboBox(chooseItemName,
				// "select subcategory.scatname from subcategory,budget_expense_income_records
				// where subcategory.catid=budget_expense_income_records.catid and
				// budget_expense_income_records.`"
				// + chooseItemNameFY.getSelectedItem() + "`=0");
				displayInComboBox(chooseItemName, "select subcategory.scatname from subcategory");

				dialogEstimates.setVisible(true);

			}
		});
		panelNoteDetails.add(btnEnterEstimates);

		String[] head = { "Date", "Details", "Amount", "Amount" };

		DefaultTableModel model = new DefaultTableModel();

		Object[][] data = { { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, { null, null, null, null } };
		model.setDataVector(data, head);

		tableNotes = new JTable();
		// tableNotes.setPreferredSize(new Dimension(1160, 290));
		tableNotes.setRowHeight(30);

		JTableHeader hd = tableNotes.getTableHeader();
		hd.setBackground(Color.black);
		hd.setPreferredSize(new Dimension(10, 40));
		tableNotes.setColumnModel(new GroupableTableColumnModel());
		tableNotes.setTableHeader(new GroupableTableHeader((GroupableTableColumnModel) tableNotes.getColumnModel()));
		tableNotes.setModel(model);
		////////////////////////

		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setBackground(Color.black);

		GroupableTableColumnModel cm = (GroupableTableColumnModel) tableNotes.getColumnModel();
		ColumnGroup groupReceipts = new ColumnGroup("Period 1");
		groupReceipts.add(cm.getColumn(2));

		ColumnGroup groupIssues = new ColumnGroup("Period 2");
		groupIssues.add(cm.getColumn(3));

		GroupableTableHeader header = (GroupableTableHeader) tableNotes.getTableHeader();
		cm.addColumnGroup(groupReceipts);
		cm.addColumnGroup(groupIssues);
		header.setColumnMargin();
		tableNotes.getColumnModel().getColumn(0).setPreferredWidth(70);
		tableNotes.getColumnModel().getColumn(1).setPreferredWidth(200);

		scrollPaneNotes = new JScrollPane(tableNotes);
		scrollPaneNotes.setPreferredSize(new Dimension(1150, 300));
		scrollPaneNotes.setBorder(new LineBorder(Color.white, 3));
		add(scrollPaneNotes);

		panelTopButtonPanel = new JPanel();
		panelTopButtonPanel.setBorder(new LineBorder(Color.white, 3));
		add(panelTopButtonPanel);
		panelTopButtonPanel.setPreferredSize(new Dimension(1150, 40));

		btnExportNotes = new JButton("Export Notes");
		btnPrintNotes = new JButton("Print Notes");

		btnExportNotes.setPreferredSize(new Dimension(120, 25));
		btnPrintNotes.setPreferredSize(new Dimension(120, 25));

		panelTopButtonPanel.add(btnExportNotes);
		panelTopButtonPanel.add(btnPrintNotes);

		panelTopButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// panelTopButtonPanel.add(paintingChild3);

		btnExportNotes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to export this deatils");

				int selected = fileChooser.showSaveDialog(FinanceNotes.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						print(tableNotes, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			}
		});

		btnPrintNotes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to save this deatils");

				int selected = fileChooser.showSaveDialog(FinanceNotes.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						print(tableNotes, new java.io.File(fileChooser.getSelectedFile() + ".pdf"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			}
		});

	}

	public void AddBank(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Bank Added Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

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

	public void AddItem(String querries) {

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

	public void displayNotes(JTable table, String query) {

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

	private void print(JTable table, java.io.File file) {
		com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4.rotate());
		try {
			PdfWriter writer = PdfWriter.getInstance((com.lowagie.text.Document) document,
					new FileOutputStream(new java.io.File(fileChooser.getSelectedFile() + ".pdf")));

			((com.lowagie.text.Document) document).open();
			PdfContentByte cb = writer.getDirectContent();

			cb.saveState();
			Graphics2D g2 = cb.createGraphicsShapes(600, 600);

			// Shape oldClip = g2.getClip();
			// g2.clipRect(0, 0, 600, 600);

			table.print(g2);
			// g2.setClip(oldClip);

			g2.dispose();
			cb.restoreState();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		document.close();

	}

}
