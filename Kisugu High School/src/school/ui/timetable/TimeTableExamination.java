package school.ui.timetable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JYearChooser;

import school.ui.finances.CashBookController;
import school.ui.multiline.MultiLineTableCellRenderer;

public class TimeTableExamination extends JPanel {

	private JButton btnBack;
	private JButton btnPrint;
	private JYearChooser yearChooser;
	private JComboBox<String> comboBoxTerm;
	private JComboBox<String> comboBoxClass;

	private JTable tableExamination;
	private JScrollPane scrollPaneExamination;

	private JLabel labelYear;
	private JButton btnView;
	private Connection conn;
	private PreparedStatement pst;

	public TimeTableExamination() {
		buildExamination();
	}

	private void buildExamination() {

		labelYear = new JLabel("Choose Year");
		labelYear.setPreferredSize(new Dimension(80, 25));
		labelYear.setFont(new Font("Times New Roman", Font.BOLD, 13));
		add(labelYear);

		yearChooser = new JYearChooser();
		yearChooser.getYear();
		yearChooser.setPreferredSize(new Dimension(60, 25));
		yearChooser.setFont(new Font("Times New Roman", Font.BOLD, 15));
		add(yearChooser);

		String[] terms = { "Choose Term" };

		comboBoxTerm = new JComboBox<>(terms);
		comboBoxTerm.setPreferredSize(new Dimension(140, 25));
		comboBoxTerm.setFont(new Font("Times New Roman", Font.BOLD, 15));
		add(comboBoxTerm);

		String[] classes = { "Choose Class"};
		comboBoxClass = new JComboBox<>(classes);
		comboBoxClass.setPreferredSize(new Dimension(140, 25));
		comboBoxClass.setFont(new Font("Times New Roman", Font.BOLD, 15));
		add(comboBoxClass);
		
		btnView=new JButton("View Time Table");
		btnView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ViewTimeTableExamination viewTimeTable=new ViewTimeTableExamination();
				
			}
		});
		add(btnView);


		final DefaultTableModel model = new DefaultTableModel() {

			public Class getColumnClass(int index) {
				switch (index) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				case 4:
					return String.class;
				}
				return super.getColumnClass(index);
			}

			public String getColumnName(int index) {
				switch (index) {
				case 0:
					return "DAYS";
				case 1:
					return "MORNING";
				case 2:
					return "MID MORNING";
				case 3:
					return "AFTERNOON";
				case 4:
					return "EVENING";

				}
				return super.getColumnName(index);
			}

			public Object getValueAt(int row, int col) {

				return super.getValueAt(row, col);

			}

			public void setValueAt(Object value, int row, int col) {

				super.setValueAt(value, row, col);
			}

			public boolean isCellEditable(int row, int col) {
				return col == 0 || col == 1 || col == 2 || col == 3 || col == 4;
			}

			public int getColumnCount() {
				return 5;
			}

		};

		tableExamination = new JTable(model);
		JTableHeader headerCol = tableExamination.getTableHeader();
		headerCol.setPreferredSize(new Dimension(1150, 25));

		MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();

		tableExamination.setDefaultRenderer(Object.class, renderer);

		JPopupMenu popupMenuTableListener = new JPopupMenu();
		JMenuItem menuItemTableListener = new JMenuItem("Add New Row");

		popupMenuTableListener.add(menuItemTableListener);

		menuItemTableListener.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				model.addRow(new Vector());
			}
		});

		model.addRow(new Vector());
		model.addRow(new Vector());
		model.addRow(new Vector());

		tableExamination.setShowGrid(true);

		tableExamination.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (SwingUtilities.isRightMouseButton(e)) {
					popupMenuTableListener.show(tableExamination, e.getX(), e.getY());
				}

			}
		});

		JTableHeader hd = tableExamination.getTableHeader();
		hd.setPreferredSize(new Dimension(1150, 25));
		tableExamination.setRowHeight(50);

		scrollPaneExamination = new JScrollPane();
		scrollPaneExamination.setViewportView(tableExamination);
		scrollPaneExamination.setPreferredSize(new Dimension(1160, 400));
		add(scrollPaneExamination);

		btnBack = new JButton("Save Time Table");
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				String sql = "insert into examination_time_table(day,morning,mid_morning,after_noon,evening,year,term,student_class) values(?,?,?,?,?,?,?,?)";
				java.sql.Connection conn = null;
				java.sql.PreparedStatement pst = null;
				int rows = tableExamination.getRowCount();

				try {

					conn =  CashBookController.getConnection();
					pst = conn.prepareStatement(sql);

					for (int Row = 0; Row < rows; Row++) {
						String column1 = (String) tableExamination.getValueAt(Row, 0);
						String column2 = (String) tableExamination.getValueAt(Row, 1);
						String column3 = (String) tableExamination.getValueAt(Row, 2);
						String column4 = (String) tableExamination.getValueAt(Row, 3);
						String column5 = (String) tableExamination.getValueAt(Row, 4);

						pst.setString(1, column1);
						pst.setString(2, column2);
						pst.setString(3, column3);
						pst.setString(4, column4);
						pst.setString(5, column5);
						pst.setDouble(6, yearChooser.getYear());
						pst.setString(7, (String) comboBoxTerm.getSelectedItem());
						pst.setString(8, (String) comboBoxClass.getSelectedItem());
						pst.addBatch();

					}

					pst.executeBatch();
					JOptionPane.showMessageDialog(null, "Time Table For Examination Saved Successfully");

				} catch (Exception e2) {

					e2.printStackTrace();
				} finally {
					if (conn != null) {
						try {
							conn.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}

			}
		});
		add(btnBack);

		btnPrint = new JButton("Add Row");
		btnPrint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				model.addRow(new Vector());
			}
		});
		add(btnPrint);
		
		
		displayInComboBox(comboBoxClass, "select class_name from student_classes");
		displayInComboBox(comboBoxTerm, "select term_name from student_terms");

	}
	
	public void displayInComboBox(JComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			combo.removeAll();

			while (rs.next()) {
				combo.addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}


}
