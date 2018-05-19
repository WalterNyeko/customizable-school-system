package school.ui.finances;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JDateChooser;

import school.ui.finances.FinanceAnalyticalCashBook.NumberTableCellRenderer;

public class FinanceTrialBalance extends JDialog {

	private JTable tableTrialBalance;
	private JScrollPane scrollPaneTrialBalance;
	private java.sql.Connection con = null;

	private JButton btnPrint;
	private JButton btnExport;
	private JLabel labelTo;
	private JDateChooser dateTo;
	private JDateChooser dateFrom;
	private JLabel labelFrom;
	private JLabel labelDebit;
	private JLabel labelCredit;
	private JLabel labelDebitValue;
	private JLabel labelCreditValue;
	private Object[] column;

	Object[][] data;

	JTable fixedTable, table;

	private int FIXED_NUM = 1;
	private Connection conn;
	private float sumAllStudents;
	private Object sum;
	private double total;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemAdd;
	private JMenuItem menuItemAdd1;
	private JMenuItem menuItemRemove;
	private JMenuItem menuItemRemoveAll;
	private JMenuItem count;
	private JMenuItem range;
	private JMenuItem none;
	private DefaultTableModel dm;
	private JLabel labelSumDebit;
	private JLabel labelSumCredit;

	public FinanceTrialBalance() {
		setUpTrialBalance();
	}

	private void setUpTrialBalance() {
		setTitle("Trial Balance");
		setSize(675, 700);
		setBackground(Color.decode("#5f9ea0"));
		setResizable(false);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		data = new Object[][] { { "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" },
				{ "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" },
				{ "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" },
				{ "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" }, { "", "", "" },
				{ "", "", "" } };
		column = new Object[] { "Details/Account Name", "Debit", "Credit" };

		AbstractTableModel model = new AbstractTableModel() {
			public int getColumnCount() {
				return column.length;
			}

			public int getRowCount() {
				return data.length - FIXED_NUM;
			}

			public String getColumnName(int col) {
				return (String) column[col];
			}

			public Object getValueAt(int row, int col) {
				return data[row][col];
			}

			public void setValueAt(Object obj, int row, int col) {
				data[row][col] = obj;
			}

			public boolean CellEditable(int row, int col) {
				return true;
			}
		};

		tableTrialBalance = new JTable();
		tableTrialBalance.setModel(model);
		tableTrialBalance.setRowHeight(30);
		tableTrialBalance.getColumnModel().getColumn(0).setPreferredWidth(298);
		tableTrialBalance.getColumnModel().getColumn(1).setPreferredWidth(178);
		tableTrialBalance.getColumnModel().getColumn(2).setPreferredWidth(178);
		tableTrialBalance.getColumnModel().getColumn(1).setCellRenderer(new NumberTableCellRenderer());
		tableTrialBalance.getColumnModel().getColumn(2).setCellRenderer(new NumberTableCellRenderer());
		tableTrialBalance.setBackground(Color.decode("#5f9ea0"));

		tableTrialBalance.setFont(new Font("Times New Roman", Font.BOLD, 16));

		JTableHeader hd = tableTrialBalance.getTableHeader();
		hd.setPreferredSize(new Dimension(10, 30));

		AbstractTableModel fixedModel = new AbstractTableModel() {
			public int getColumnCount() {
				return column.length;
			}

			public int getRowCount() {
				return FIXED_NUM;
			}

			public Object getValueAt(int row, int col) {
				return data[row + (data.length - FIXED_NUM)][col];
			}
		};

		tableTrialBalance.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableTrialBalance.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		fixedTable = new JTable(fixedModel);
		fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		fixedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		fixedTable.getColumnModel().getColumn(0).setPreferredWidth(430);
		fixedTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		fixedTable.getColumnModel().getColumn(2).setPreferredWidth(100);

		tableTrialBalance.getColumnModel().getColumn(1).setCellRenderer(new NumberTableCellRenderer());
		tableTrialBalance.getColumnModel().getColumn(2).setCellRenderer(new NumberTableCellRenderer());

		fixedTable.setRowHeight(30);
		fixedTable.setPreferredSize(new Dimension(825, 30));

		JScrollPane scroll = new JScrollPane(tableTrialBalance);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(630, 1000));
		scroll.setBorder(new LineBorder(Color.white, 3));
		add(scroll);

		JScrollPane fixedScroll = new JScrollPane(fixedTable) {
			public void setColumnHeaderView(Component view) {
			} // work around
		}; //
			// fixedScroll.setColumnHeader(null);

		fixedScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JScrollBar bar = fixedScroll.getVerticalScrollBar();
		JScrollBar dummyBar = new JScrollBar() {
			public void paint(Graphics g) {
			}
		};
		dummyBar.setPreferredSize(bar.getPreferredSize());
		fixedScroll.setVerticalScrollBar(dummyBar);

		final JScrollBar bar1 = scroll.getHorizontalScrollBar();
		JScrollBar bar2 = fixedScroll.getHorizontalScrollBar();
		bar2.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				bar1.setValue(e.getValue());
			}
		});

		// scroll.setPreferredSize(new Dimension(400, 100));
		fixedScroll.setPreferredSize(new Dimension(1000, 52)); // Hmm...
		add(scroll, BorderLayout.CENTER);

		JPanel panelSouth = new JPanel();

		add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setPreferredSize(new Dimension(620, 100));
		// panelSouth.add(fixedScroll);

		// btnExport = new JButton("Export");
		// add(btnExport);
		//
		// btnPrint = new JButton("Print");
		// add(btnPrint);
		//
		//

		// panelSouth.setLayout(new FlowLayout(FlowLayout.RIGHT));

		labelSumDebit = new JLabel("");
		labelSumDebit.setPreferredSize(new Dimension(320, 25));
		labelSumDebit.setFont(new Font("Times New Roman", Font.BOLD, 16));
		labelSumDebit.setForeground(Color.blue);
		panelSouth.add(labelSumDebit);

		labelSumCredit = new JLabel("");
		labelSumCredit.setPreferredSize(new Dimension(320, 25));
		labelSumCredit.setFont(new Font("Times New Roman", Font.BOLD, 16));
		labelSumCredit.setForeground(Color.blue);
		panelSouth.add(labelSumCredit);

		labelFrom = new JLabel("From:");
		panelSouth.add(labelFrom);
		panelSouth.setBackground(Color.decode("#5f9ea0"));

		dateFrom = new JDateChooser();
		dateFrom.getDate();
		dateFrom.setPreferredSize(new Dimension(120, 25));
		panelSouth.add(dateFrom);
		dateFrom.addPropertyChangeListener("date", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub
			}
		});

		labelTo = new JLabel("To:");
		panelSouth.add(labelTo);

		dateTo = new JDateChooser();
		dateTo.getDate();
		dateTo.setPreferredSize(new Dimension(120, 25));
		panelSouth.add(dateTo);
		dateTo.addPropertyChangeListener("date", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub
				// fixedTable.setValueAt((getSum()), 0, 1);
				// fixedTable.setValueAt(Float.toString(getSum()), 1, 1);

				AbstractTableModel modelNew = displayTrialBalanceWithDates();
				tableTrialBalance.setModel(modelNew);
				tableTrialBalance.getColumnModel().getColumn(0).setPreferredWidth(298);
				tableTrialBalance.getColumnModel().getColumn(1).setPreferredWidth(178);
				tableTrialBalance.getColumnModel().getColumn(2).setPreferredWidth(178);
				labelSumCredit.setText("CREDIT SUM = " + Float.toString(getSumCredit()));
				labelSumDebit.setText("DEBIT SUM = " + Float.toString(getSumDebit()));
			}
		});

		// labelDebit = new JLabel("Debit Sum:");
		// add(labelDebit);
		//
		// labelDebitValue = new JLabel();
		// labelDebitValue.setPreferredSize(new Dimension(120, 25));
		// add(labelDebitValue);
		//
		//
		// labelCredit = new JLabel("Credit Sum:");
		// add(labelCredit);
		//
		// labelCreditValue = new JLabel();
		// labelCreditValue.setPreferredSize(new Dimension(120, 25));
		// add(labelCreditValue);
		//

		// displayRecordsInTable(tableTrialBalance, "select account_name,"
		// + "CASE WHEN SUM(debit)-SUM(credit)>0 THEN SUM(debit)-SUM(credit)
		// ELSE 0 END AS Debit,"
		// + "CASE WHEN SUM(credit)-SUM(debit)>0 THEN SUM(credit)-SUM(debit)
		// ELSE 0 END AS Credit from accounts_balanced_entries "
		// + "group by account_name");

		AbstractTableModel modelNew = displayTrialBalance();
		tableTrialBalance.setModel(modelNew);

		// fixedTable.setModel(modelNewfixed);
		// fixedTable.setValueAt((getSumAllStudents()), 0, 1);
		tableTrialBalance.getColumnModel().getColumn(0).setPreferredWidth(298);
		tableTrialBalance.getColumnModel().getColumn(1).setPreferredWidth(178);
		tableTrialBalance.getColumnModel().getColumn(2).setPreferredWidth(178);

		fixedTable.getColumnModel().getColumn(0).setPreferredWidth(300);
		fixedTable.getColumnModel().getColumn(1).setPreferredWidth(180);
		fixedTable.getColumnModel().getColumn(2).setPreferredWidth(180);

		popupMenu = new JPopupMenu();
		menuItemAdd = new JMenuItem("Show Maximum Payment Made");
		menuItemAdd.setFont(new Font("Times New Roman", Font.BOLD, 16));
		menuItemAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JMenuItem menu = (JMenuItem) e.getSource();
				if (menu == menuItemAdd) {
					// fem3.setText(Float.toString(getMax()));

				}

			}
		});
		Image imgage = new ImageIcon(this.getClass().getResource("root.jpg")).getImage();
		menuItemAdd.setIcon(new ImageIcon(imgage));

		menuItemAdd1 = new JMenuItem("Show Minimum Payment Made");
		menuItemAdd1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		menuItemAdd1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JMenuItem menu = (JMenuItem) e.getSource();
				if (menu == menuItemAdd1) {
					// fem4.setText(Float.toString(getMin()));

				}

			}
		});
		Image imgage3 = new ImageIcon(this.getClass().getResource("root.jpg")).getImage();
		menuItemAdd1.setIcon(new ImageIcon(imgage3));

		menuItemRemove = new JMenuItem("Show Average Company Earning");
		menuItemRemove.setFont(new Font("Times New Roman", Font.BOLD, 16));
		menuItemRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JMenuItem menu = (JMenuItem) e.getSource();

				if (menu == menuItemRemove) {
					// fem2.setText(Float.toString(getAverage()));

				}
			}
		});
		Image imgage1 = new ImageIcon(this.getClass().getResource("root.jpg")).getImage();
		menuItemRemove.setIcon(new ImageIcon(imgage1));

		menuItemRemoveAll = new JMenuItem("Show Sum Of Credit/Debit");
		menuItemRemoveAll.setFont(new Font("Times New Roman", Font.BOLD, 16));

		menuItemRemoveAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JMenuItem menu = (JMenuItem) e.getSource();
				if (menu == menuItemRemoveAll) {
					// getSum();
					// fem1.setText(Float.toString(getSum()));
					// fixedTable.setValueAt(Float.toString(getSum()), 0, 1);

					AbstractTableModel modelNew = displayTrialBalanceWithDates();
					tableTrialBalance.setModel(modelNew);

					tableTrialBalance.getColumnModel().getColumn(0).setPreferredWidth(298);
					tableTrialBalance.getColumnModel().getColumn(1).setPreferredWidth(178);
					tableTrialBalance.getColumnModel().getColumn(2).setPreferredWidth(178);

					labelSumCredit.setText("CREDIT SUM = " + Float.toString(getSumCredit()));
					labelSumDebit.setText("DEBIT SUM = " + Float.toString(getSumDebit()));
				}
			}
		});
		Image image = new ImageIcon(this.getClass().getResource("root.jpg")).getImage();
		menuItemRemoveAll.setIcon(new ImageIcon(image));

		count = new JMenuItem("Show Counts Of Payment Made");
		count.setFont(new Font("Times New Roman", Font.BOLD, 16));
		count.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JMenuItem menu = (JMenuItem) e.getSource();
				if (menu == count) {

				}

			}
		});
		Image image3 = new ImageIcon(this.getClass().getResource("root.jpg")).getImage();
		count.setIcon(new ImageIcon(image3));

		range = new JMenuItem("Show Range Of Payment Made");
		range.setFont(new Font("Times New Roman", Font.BOLD, 16));
		range.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JMenuItem menu = (JMenuItem) e.getSource();
				if (menu == range) {

				}

			}
		});
		Image image1 = new ImageIcon(this.getClass().getResource("root.jpg")).getImage();
		range.setIcon(new ImageIcon(image1));

		none = new JMenuItem("Show Average");
		none.setFont(new Font("Times New Roman", Font.BOLD, 16));
		none.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JMenuItem menu = (JMenuItem) e.getSource();
				if (menu == none) {

				}

			}
		});
		Image image2 = new ImageIcon(this.getClass().getResource("root.jpg")).getImage();
		none.setIcon(new ImageIcon(image2));

		popupMenu.add(menuItemAdd);
		popupMenu.add(menuItemAdd1);

		popupMenu.add(menuItemRemoveAll);
		popupMenu.add(none);
		popupMenu.add(menuItemRemove);
		popupMenu.add(count);
		popupMenu.add(range);

		tableTrialBalance.setComponentPopupMenu(popupMenu);

		setVisible(true);
	}

	public static void main(String[] args) {
		new FinanceTrialBalance();
	}

	public void displayRecordsInTable(JTable table, String query) {

		try {

			con = CashBookController.getConnection();
			PreparedStatement pst = con.prepareStatement(query);
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
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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

	public AbstractTableModel displayTrialBalance() {

		Object[] columns = { "Details/Account Name", "Debit", "Credit" };

		dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select account_name,"
				+ "CASE WHEN SUM(debit)-SUM(credit)>0 THEN SUM(debit)-SUM(credit) ELSE 0 END AS Debit,"
				+ "CASE WHEN SUM(credit)-SUM(debit)>0 THEN SUM(credit)-SUM(debit) ELSE 0 END AS Credit from accounts_balanced_entries "
				+ "group by account_name";

		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String Name = rs.getString(1);
				String Debit = rs.getString(2);
				String Credit = rs.getString(3);

				dm.addRow(new String[] { Name, Debit, Credit });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public AbstractTableModel displayTrialBalanceWithDates() {

		Object[] columns = { "Details/Account Name", "Debit", "Credit" };

		dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select account_name,"
				+ "CASE WHEN SUM(debit)-SUM(credit)>0 THEN SUM(debit)-SUM(credit) ELSE 0 END AS Debit,"
				+ "CASE WHEN SUM(credit)-SUM(debit)>0 THEN SUM(credit)-SUM(debit) ELSE 0 END AS Credit from accounts_balanced_entries "
				+ "where date between '" + convertFromUtilToSQLDate(dateFrom.getDate()) + "' and '"
				+ convertFromUtilToSQLDate(dateTo.getDate()) + "'" + "group by account_name";
		try {
			Connection conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String Class = rs.getString(1);
				String NAME = rs.getString(2);
				String Standard = rs.getString(3);

				dm.addRow(new String[] { Class, NAME, Standard });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dm;
	}

	public AbstractTableModel displayTrialBalanceSums() {

		Object[] columns = { "Details/Account Name", "Debit", "Credit" };

		DefaultTableModel dm = new DefaultTableModel();

		// dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select CASE WHEN SUM(debit)-SUM(credit)>0 THEN SUM((debit)-(credit)) ELSE 0 END AS Debit,"
				+ "CASE WHEN SUM(credit)-SUM(debit)>0 THEN SUM(SUM(credit)-SUM(debit)) ELSE 0 END AS Credit from accounts_balanced_entries "
				+ "group by account_name";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String Class = rs.getString(1);
				String NAME = rs.getString(2);
				String Standard = rs.getString(3);

				dm.addRow(new String[] { Class, NAME, Standard });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public double getSumAllStudents() {
		for (int i = 0; i < tableTrialBalance.getRowCount(); i++) {
			total = 0;
			String Amount = (String) tableTrialBalance.getValueAt(i, 2).toString();
			Integer answer = new Integer(Amount);
			total = answer + total;
			System.out.println(total);
		}
		return total;

	}

	public float getSumCredit() {

		int rowsCount = tableTrialBalance.getRowCount();
		float sum = 0;
		for (int i = 0; i < rowsCount; i++) {
			sum = sum + Float.parseFloat(tableTrialBalance.getValueAt(i, 2).toString());
		}

		return sum;

	}

	public float getSumDebit() {

		int rowsCount = tableTrialBalance.getRowCount();
		float sum = 0;
		for (int i = 0; i < rowsCount; i++) {
			sum = sum + Float.parseFloat(tableTrialBalance.getValueAt(i, 1).toString());
		}

		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(12);
		String answer = "" + sum;

		String sumFinal = df.format(Double.parseDouble(answer));
		float finalAnswer = Float.parseFloat(sumFinal);
		return finalAnswer;

	}

	public float getAverageDebit() {
		float sum = getSumDebit();
		int rowsCount = tableTrialBalance.getRowCount();
		float average = sum / rowsCount;
		return average;

	}

	public float getAverageCredit() {
		float sum = getSumCredit();
		int rowsCount = tableTrialBalance.getRowCount();
		float average = sum / rowsCount;
		return average;

	}

	public float getMax() {

		ArrayList<Float> list = new ArrayList<Float>();
		for (int i = 0; i < tableTrialBalance.getRowCount(); i++) {

			list.add(Float.parseFloat(tableTrialBalance.getValueAt(i, 2).toString()));
		}

		float max = Collections.max(list);

		return max;

	}

	public float getMin() {

		ArrayList<Float> list = new ArrayList<Float>();
		for (int i = 0; i < tableTrialBalance.getRowCount(); i++) {

			list.add(Float.parseFloat(tableTrialBalance.getValueAt(i, 2).toString()));
		}

		float min = Collections.min(list);
		return min;
	}

	public float getCount() {

		int rowsCount = tableTrialBalance.getRowCount();

		return rowsCount;

	}

	public float getRange() {

		float maximum = getMax();
		float minimum = getMin();
		float range = maximum - minimum;
		return range;

	}

	private void addNewRow() {
		dm.addRow(new String[0]);
	}

	private void removeCurrentRow() {
		int selectedRow = tableTrialBalance.getSelectedRow();
		dm.removeRow(selectedRow);
	}

	private void removeAllRows() {
		int rowCount = dm.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			dm.removeRow(0);
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

}
