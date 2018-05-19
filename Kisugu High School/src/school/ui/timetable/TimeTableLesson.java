
package school.ui.timetable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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

import com.toedter.calendar.JYearChooser;

import school.ui.finances.CashBookController;
import school.ui.multiline.MultiLineTableCellRenderer;

public class TimeTableLesson extends JPanel {

	private JTable tableLesson;
	private JScrollPane scrollPaneLesson;
	private JComboBox<String> comboBoxLesson;
	private JComboBox<String> comboBoxTerm;
	private JLabel labelClasses;
	
	private JYearChooser yearChooser;
	
	
	private JLabel labelChooseYear;
	private JLabel labelChooseTerm;
	
	private JButton btnPrint;
	private JButton btnBack;
	protected DefaultTableModel tableModelTimeTable;
	private JButton btnView;
	private Connection conn;
	private PreparedStatement pst;
	private DefaultTableModel dtModel;
	private JPanel panelDown;

	public TimeTableLesson() {
		// TODO Auto-generated constructor stub
		buildLesson();
	}

	private void buildLesson() {
		
//		Dimension dimLabels = new Dimension(100, 25);
		setLayout(new BorderLayout());
		
		JPanel panelTwo = new JPanel();
		panelTwo.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 2));
		add(panelTwo,BorderLayout.NORTH);
		
		
		Font fontLabels = new Font("Times New Roman", Font.BOLD, 16);
		
		labelChooseYear = new JLabel("Choose Year");
		labelChooseYear.setFont(fontLabels);
		panelTwo.add(labelChooseYear);
		yearChooser = new JYearChooser();
		yearChooser.setPreferredSize(new Dimension(60, 25));
		yearChooser.getYear();
		panelTwo.add(yearChooser);
		
		String[] terms = {"Choose Term"};
		
		labelChooseTerm = new JLabel("Choose Term");
		labelChooseTerm.setFont(fontLabels);
		panelTwo.add(labelChooseTerm);
		comboBoxTerm = new JComboBox<>(terms);
		comboBoxTerm.setPreferredSize(new Dimension(140, 25));
		comboBoxTerm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
//				displayData(tableLesson,"select day,eight_forty,nine_twenty,ten,ten_forty,break,twelve,twelve_forty,one_twenty,lunch,three,"
//						+ "three_forty,four_twenty,five from lesson_time_table where student_class='"+comboBoxLesson.getSelectedItem()+"'"
//								+ " and term='"+comboBoxTerm.getSelectedItem()+"' and year='"+yearChooser.getYear()+"'");
			}
		});
		panelTwo.add(comboBoxTerm);
		
		labelClasses = new JLabel("Choose Class");
		labelClasses.setFont(fontLabels);
		panelTwo.add(labelClasses);
		

		String[] classes = { "Choose Class" };

		comboBoxLesson = new JComboBox<>(classes);
		comboBoxLesson.setPreferredSize(new Dimension(140, 25));
		comboBoxLesson.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
//				displayData(tableLesson,"select day,eight_forty,nine_twenty,ten,ten_forty,break,twelve,twelve_forty,one_twenty,lunch,three,"
//						+ "three_forty,four_twenty,five from lesson_time_table where student_class='"+comboBoxLesson.getSelectedItem()+"'"
//								+ " and term='"+comboBoxTerm.getSelectedItem()+"' and year='"+yearChooser.getYear()+"'");
			}
		});
		panelTwo.add(comboBoxLesson);
		
		btnView=new JButton("View Time Table");
		btnView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ViewTimeTable viewTimeTable=new ViewTimeTable();
				
			}
		});
		panelTwo.add(btnView);
		String[] heading = {"Day", "8:00-8:40", "8:40-9:20", "9:20-10:00", "10:00-10:40",
							"BREAK", "11:20-12:00", "12:00-12:40", "12:40-1:20", "LUNCH", 
							"2:20-3:00", "3:00-3:40", "3:40-4:20", "4:20-5:00"};
		
		
		Object[][] data = {
				
				{"MON",null,null,null,null,"B",null,null,null,"L",null,null,null,null},
				{"TUE",null,null,null,null,"R",null,null,null,"U",null,null,null,null},
				{"WED",null,null,null,null,"E",null,null,null,"N",null,null,null,null},
				{"THU",null,null,null,null,"A",null,null,null,"C",null,null,null,null},
				{"FRI",null,null,null,null,"K",null,null,null,"H",null,null,null,null},
				{"SAT",null,null,null,null,null,null,null,null,null,null,null,null,null},
				{"SUN",null,null,null,null,null,null,null,null,null,null,null,null,null}
				
				
		};
		
		dtModel=new DefaultTableModel();
		dtModel.setDataVector(data, heading);
		
		tableLesson = new JTable(dtModel);
		
		DefaultTableCellRenderer rendereralign=new DefaultTableCellRenderer();
		rendereralign.setHorizontalAlignment(SwingConstants.CENTER);
		rendereralign.setBackground(Color.white);
		tableLesson.getColumnModel().getColumn(0).setCellRenderer(rendereralign);
		tableLesson.getColumnModel().getColumn(5).setCellRenderer(rendereralign);
		tableLesson.getColumnModel().getColumn(9).setCellRenderer(rendereralign);
		

		MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();

		tableLesson.setDefaultRenderer(Object.class, renderer);
		tableLesson.setRowHeight(53);
		tableLesson.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				if(e.getType()==TableModelEvent.UPDATE){
					
					
				}
				
			}
		});
		JTableHeader headerCol = tableLesson.getTableHeader();
		headerCol.setPreferredSize(new Dimension(1150, 25));
		scrollPaneLesson = new JScrollPane(tableLesson);
		scrollPaneLesson.setPreferredSize(new Dimension(1150, 400));
		add(scrollPaneLesson,BorderLayout.CENTER);
		
		
//		displayData(tableLesson,"select day,eight_forty,nine_twenty,ten,ten_forty,break,twelve,twelve_forty,one_twenty,lunch,three,"
//						+ "three_forty,four_twenty,five from lesson_time_table where year='"+yearChooser.getYear()+"'");
//		
		
		displayInComboBox(comboBoxLesson, "select class_name from student_classes");
		displayInComboBox(comboBoxTerm, "select term_name from student_terms");

		panelDown=new JPanel();
		add(panelDown,BorderLayout.SOUTH);
		btnBack = new JButton("Save Time Table");
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String sql ="insert into lesson_time_table(day,eight_forty,nine_twenty,ten,ten_forty,break,twelve,twelve_forty,one_twenty,lunch,three,"
						+ "three_forty,four_twenty,five,year,term,student_class) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				java.sql.Connection conn=null;
				java.sql.PreparedStatement pst=null;
				int rows=tableLesson.getRowCount();
				
				try {
					
					conn= CashBookController.getConnection();
					pst=conn.prepareStatement(sql);
					
					for(int Row=0; Row<rows; Row++){
						String column1=(String) tableLesson.getValueAt(Row, 0);
						String column2=(String) tableLesson.getValueAt(Row, 1);
						String column3=(String) tableLesson.getValueAt(Row, 2);
						String column4=(String) tableLesson.getValueAt(Row, 3);
						String column5=(String) tableLesson.getValueAt(Row, 4);
						String column6=(String) tableLesson.getValueAt(Row, 5);
						String column7=(String) tableLesson.getValueAt(Row, 6);
						String column8=(String) tableLesson.getValueAt(Row, 7);
						String column9=(String) tableLesson.getValueAt(Row, 8);
						String column10=(String) tableLesson.getValueAt(Row, 9);
						String column11=(String) tableLesson.getValueAt(Row, 10);
						String column12=(String) tableLesson.getValueAt(Row, 11);
						String column13=(String) tableLesson.getValueAt(Row, 12);
						String column14=(String) tableLesson.getValueAt(Row, 13);
						
					
					pst.setString(1,  column1);
					pst.setString(2,  column2);
					pst.setString(3,  column3);
					pst.setString(4,  column4);
					pst.setString(5,  column5);
					pst.setString(6,  column6);
					pst.setString(7,  column7);
					pst.setString(8,  column8);
					pst.setString(9,  column9);
					pst.setString(10,  column10);
					pst.setString(11,  column11);
					pst.setString(12,  column12);
					pst.setString(13,  column13);
					pst.setString(14,  column14);
					pst.setDouble(15,  yearChooser.getYear());
					pst.setString(16,  (String) comboBoxTerm.getSelectedItem());
					pst.setString(17,  (String) comboBoxLesson.getSelectedItem());
					pst.addBatch();
					
					}
					
					pst.executeBatch();
					JOptionPane.showMessageDialog(null, "Time Table Successfully Saved");
					
				} catch (Exception e2) {

				
				e2.printStackTrace();
				}finally{
					if(conn!=null){
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
		panelDown.add(btnBack);
		
		btnPrint = new JButton("Print");
		panelDown.add(btnPrint);
		
		
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
