package school.ui.timetable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.toedter.calendar.JDateChooser;

import school.ui.finances.CashBookController;
import school.ui.jdbc.JavaDatabaseSelectStatements;
import school.ui.multiline.MultiLineTableCellRenderer;
import school.ui.tests.DisplayDatabase;

public class TimeTableTeachersSchedule extends JPanel {

	private JTextField fieldNamesOfTeachers;
	private JTextField fieldSupervisingTeacher;
	private JTextField fieldWeekName;
	private JDateChooser dateChooserFrom;
	private JDateChooser dateChooserTo;
	private JButton btnAddWeek;

	private JTable tableWeekSchedules;
	private JScrollPane scrollPaneWeekSchedules;

	private JButton btnBack;
	private JButton btnPrint;
	protected DefaultTableModel tableModelSchedule;
	private Connection conn;

	public TimeTableTeachersSchedule() {
		// TODO Auto-generated constructor stub
		scheduler();
	}

	private void scheduler() {

		Dimension dimFieldWeek = new Dimension(100, 25);

		fieldWeekName = new JTextField();
		fieldWeekName.setPreferredSize(dimFieldWeek);
		fieldWeekName.setText("Name Of Week");
		fieldWeekName.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if (fieldWeekName.getText().isEmpty()) {
					fieldWeekName.setText("Name of Week");
				} else {
					fieldWeekName.setText(fieldWeekName.getText());
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if (fieldWeekName.getText().isEmpty()) {
					fieldWeekName.setText("");
				} else if(fieldWeekName.getText().equalsIgnoreCase("Name Of Week")){
					
					fieldWeekName.setText("");
				}else {
					fieldWeekName.setText(fieldWeekName.getText());
				}

			}
		});
		add(fieldWeekName);

		JLabel labelFrom = new JLabel("From:");
		labelFrom.setFont(new Font("Times New Roman", Font.BOLD, 13));

		add(labelFrom);

		dateChooserFrom = new JDateChooser();
		dateChooserFrom.getDate();
		add(dateChooserFrom);
		dateChooserFrom.setPreferredSize(new Dimension(140, 25));
		dateChooserFrom.addPropertyChangeListener("date", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub
				tableModelSchedule = DisplayTeachersScheduleTimeTable("select week_name,from_date,to_date,teachers_names,supervisor_name "
						+ "from teachers_schedule_time_table where from_date >= '"+convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue())+"'");
				tableWeekSchedules.setModel(tableModelSchedule);

			}
		});

		JLabel labelTo = new JLabel("To:");
		labelTo.setFont(new Font("Times New Roman", Font.BOLD, 13));

		add(labelTo);

		dateChooserTo = new JDateChooser();
		dateChooserTo.getDate();
		dateChooserTo.setPreferredSize(new Dimension(140, 25));
		add(dateChooserTo);
		dateChooserTo.addPropertyChangeListener("date", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub
				tableModelSchedule = DisplayTeachersScheduleTimeTable("select week_name,from_date,to_date,teachers_names,supervisor_name "
						+ "from teachers_schedule_time_table where from_date >= '"+convertFromUtilToSQLDate(dateChooserFrom.getDate())+"' and "
								+ "to_date <= '"+convertFromUtilToSQLDate((java.util.Date) arg0.getNewValue())+"'");
				tableWeekSchedules.setModel(tableModelSchedule);
				
			}
		});

		fieldSupervisingTeacher = new JTextField();
		fieldSupervisingTeacher.setPreferredSize(new Dimension(200, 25));
		fieldSupervisingTeacher.setText("Name Of Supervisor");
		fieldSupervisingTeacher.addFocusListener(new FocusListener() {


			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if (fieldSupervisingTeacher.getText().isEmpty()) {
					fieldSupervisingTeacher.setText("Name Of Supervisor");
				} else {
					fieldSupervisingTeacher.setText(fieldSupervisingTeacher.getText());
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if (fieldSupervisingTeacher.getText().isEmpty()) {
					fieldSupervisingTeacher.setText("");
				} else if(fieldSupervisingTeacher.getText().equals("Name Of Supervisor")){
					
					fieldSupervisingTeacher.setText("");
				}else {
					fieldSupervisingTeacher.setText(fieldSupervisingTeacher.getText());
				}

			}
		});

		fieldNamesOfTeachers = new JTextField();
		fieldNamesOfTeachers.setPreferredSize(new Dimension(325, 25));
		fieldNamesOfTeachers.setText("Names of Teachers (Separate names by commas)");
		fieldNamesOfTeachers.addFocusListener(new FocusListener() {


			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if (fieldNamesOfTeachers.getText().isEmpty()) {
					fieldNamesOfTeachers.setText("Names of Teachers (Separate names by commas)");
				} else {
					fieldNamesOfTeachers.setText(fieldNamesOfTeachers.getText());
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if (fieldNamesOfTeachers.getText().isEmpty()) {
					fieldNamesOfTeachers.setText("");
				} else if(fieldNamesOfTeachers.getText().equals("Names of Teachers (Separate names by commas)")){
					
					fieldNamesOfTeachers.setText("");
				}else {
					fieldNamesOfTeachers.setText(fieldNamesOfTeachers.getText());
				}

			}
		});

	
		fieldNamesOfTeachers.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
				

				tableModelSchedule = DisplayTeachersScheduleTimeTable("select week_name,from_date,to_date,teachers_names,supervisor_name "
						+ "from teachers_schedule_time_table where teachers_names LIKE '%"+fieldNamesOfTeachers.getText()+"%'");
				tableWeekSchedules.setModel(tableModelSchedule);

			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		fieldSupervisingTeacher.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				tableModelSchedule = DisplayTeachersScheduleTimeTable("select week_name,from_date,to_date,teachers_names,supervisor_name "
						+ "from teachers_schedule_time_table where supervisor_name LIKE '%"+fieldSupervisingTeacher.getText()+"%'");
				tableWeekSchedules.setModel(tableModelSchedule);

			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		add(fieldNamesOfTeachers);
		add(fieldSupervisingTeacher);

		btnAddWeek = new JButton("Add Week");
		btnAddWeek.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnAddWeek.setPreferredSize(new Dimension(100, 25));
		btnAddWeek.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddUpdateDelete(
						"insert into teachers_schedule_time_table(week_name,from_date,to_date,teachers_names,supervisor_name) "
								+ "values('" + fieldWeekName.getText() + "'," + "'"
								+ convertFromUtilToSQLDate(dateChooserFrom.getDate()) + "'," + "'"
								+ convertFromUtilToSQLDate(dateChooserTo.getDate()) + "'," + "'"
								+ fieldNamesOfTeachers.getText() + "','" + fieldSupervisingTeacher.getText() + "')");

				tableModelSchedule = DisplayTeachersScheduleTimeTable("select week_name,from_date,to_date,teachers_names,supervisor_name from teachers_schedule_time_table");
				tableWeekSchedules.setModel(tableModelSchedule);

			}
		});

		add(btnAddWeek);

		final DefaultTableModel model = new DefaultTableModel() {

			public Class getColumnClass(int index) {
				switch (index) {
				case 0:
					return String.class;
				case 1:
					return Date.class;
				case 2:
					return Date.class;
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
					return "WEEK";
				case 1:
					return "FROM DATE";
				case 2:
					return "TO DATE";
				case 3:
					return "TEACHERS ON DUTY";
				case 4:
					return "SUPERVISOR";

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
				return false;
			}

			public int getColumnCount() {
				return 5;
			}

		};

		tableWeekSchedules = new JTable(model);
		JTableHeader headerCol = tableWeekSchedules.getTableHeader();
		headerCol.setPreferredSize(new Dimension(1150, 25));

		MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();

		tableWeekSchedules.setDefaultRenderer(Object.class, renderer);
		tableWeekSchedules.setRowHeight(53);

		JTableHeader hd = tableWeekSchedules.getTableHeader();
		hd.setPreferredSize(new Dimension(1150, 25));
		scrollPaneWeekSchedules = new JScrollPane(tableWeekSchedules);

		scrollPaneWeekSchedules.setPreferredSize(new Dimension(1150, 400));
		add(scrollPaneWeekSchedules);

		btnBack = new JButton("Back");
		add(btnBack);

		btnPrint = new JButton("Print");
		add(btnPrint);

		tableModelSchedule = DisplayTeachersScheduleTimeTable("select week_name,from_date,to_date,teachers_names,supervisor_name from teachers_schedule_time_table");
		tableWeekSchedules.setModel(tableModelSchedule);

	}

	public Date convertFromUtilToSQLDate(java.util.Date dateUtil) {

		if (dateUtil != null) {
			java.sql.Date sqlDate = new java.sql.Date(dateUtil.getTime());

			return sqlDate;
		} else {
			return null;
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

	public DefaultTableModel DisplayTeachersScheduleTimeTable(String sql) {

		Object[] columns = { "WEEK", "FROM DATE", "TO DATE", "TEACHERS ON DUTY", "SUPERVISOR" };
		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Age = rs.getString(5);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Age });
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
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
