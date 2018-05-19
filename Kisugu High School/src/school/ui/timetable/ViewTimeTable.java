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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
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

public class ViewTimeTable extends JDialog {

	private JTable tableLesson;
	private JScrollPane scrollPaneLesson;
	JComboBox<String> comboBoxLesson;
	JComboBox<String> comboBoxTerm;
	private JLabel labelClasses;

	JYearChooser yearChooser;

	private JLabel labelChooseYear;
	private JLabel labelChooseTerm;

	private JButton btnPrint;
	private JButton btnBack;
	protected DefaultTableModel tableModelTimeTable;
	private JButton btnView;
	private Connection conn;
	private PreparedStatement pst;
	private DefaultTableCellRenderer rendereralign;
	private DefaultTableModel defaultTBM;
	private JPanel panelDown;
	protected JFileChooser fileChooser;

	public static void main(String[] args) {
		new ViewTimeTable();
	}

	public ViewTimeTable() {
		// TODO Auto-generated constructor stub
		buildLesson();
	}

	private void buildLesson() {

		// Dimension dimLabels = new Dimension(100, 30);
		setTitle("View Time Table");
		setSize(1300, 500);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		this.getContentPane().setBackground(new Color(0, 51, 51));
		JPanel panelTwo = new JPanel();
		panelTwo.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 2));
		add(panelTwo,BorderLayout.NORTH);

		Font fontLabels = new Font("Times New Roman", Font.BOLD, 16);

		labelChooseYear = new JLabel("Choose Year");
		labelChooseYear.setFont(fontLabels);
		panelTwo.add(labelChooseYear);
		yearChooser = new JYearChooser();
		yearChooser.getYear();
		panelTwo.add(yearChooser);

		String[] terms = { "Choose Term"};

		labelChooseTerm = new JLabel("Choose Term");
		labelChooseTerm.setFont(fontLabels);
		panelTwo.add(labelChooseTerm);
		comboBoxTerm = new JComboBox<>(terms);
		comboBoxTerm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				displayData(tableLesson, "select day as Day,eight_forty as 800_840,nine_twenty as 840_920,ten,ten_forty,break,twelve,twelve_forty,one_twenty,lunch,three,"
						+ "three_forty,four_twenty,five from lesson_time_table where student_class='"
						+ comboBoxLesson.getSelectedItem() + "'" + " and term='" + comboBoxTerm.getSelectedItem()
						+ "' and year='" + yearChooser.getYear() + "'");
			}
		});
		panelTwo.add(comboBoxTerm);

		labelClasses = new JLabel("Choose Class");
		labelClasses.setFont(fontLabels);
		panelTwo.add(labelClasses);

		String[] classes = { "Choose Class"};

		comboBoxLesson = new JComboBox<>(classes);
		comboBoxLesson.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				displayData(tableLesson, "select day as Day,eight_forty as 800_840,nine_twenty as 840_920,ten,ten_forty,break,twelve,twelve_forty,one_twenty,lunch,three,"
						+ "three_forty,four_twenty,five from lesson_time_table where student_class='"
						+ comboBoxLesson.getSelectedItem() + "'" + " and term='" + comboBoxTerm.getSelectedItem()
						+ "' and year='" + yearChooser.getYear() + "'");
			}
		});
		panelTwo.add(comboBoxLesson);

		String[] heading = { "Day", "8:00-8:40", "8:40-9:20", "9:20-10:00", "10:00-10:40", "BREAK", "11:20-12:00",
				"12:00-12:40", "12:40-1:20", "LUNCH", "2:20-3:00", "3:00-3:40", "3:40-4:20", "4:20-5:00" };

		Object[][] data = {

				{ "MON", null, null, null, null, "B", null, null, null, "L", null, null, null, null },
				{ "TUE", null, null, null, null, "R", null, null, null, "U", null, null, null, null },
				{ "WED", null, null, null, null, "E", null, null, null, "N", null, null, null, null },
				{ "THU", null, null, null, null, "A", null, null, null, "C", null, null, null, null },
				{ "FRI", null, null, null, null, "K", null, null, null, "H", null, null, null, null },
				{ "SAT", null, null, null, null, null, null, null, null, null, null, null, null, null },
				{ "SUN", null, null, null, null, null, null, null, null, null, null, null, null, null }

		};
		
		defaultTBM=new DefaultTableModel();
		defaultTBM.setDataVector(data, heading);

		tableLesson = new JTable(defaultTBM);

		rendereralign = new DefaultTableCellRenderer();
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
				if (e.getType() == TableModelEvent.UPDATE) {
					int row = tableLesson.getSelectedRow();
					Object column1 = tableLesson.getModel().getValueAt(row, 0);
					Object column2 = tableLesson.getModel().getValueAt(row, 1);
					Object column3 = tableLesson.getModel().getValueAt(row, 2);
					Object column4 = tableLesson.getModel().getValueAt(row, 3);
					Object column5 = tableLesson.getModel().getValueAt(row, 4);
					Object column6 = tableLesson.getModel().getValueAt(row, 5);
					Object column7 = tableLesson.getModel().getValueAt(row, 6);
					Object column8 = tableLesson.getModel().getValueAt(row, 7);
					Object column9 = tableLesson.getModel().getValueAt(row, 8);
					Object column10 = tableLesson.getModel().getValueAt(row, 9);
					Object column11 = tableLesson.getModel().getValueAt(row, 10);
					Object column12 = tableLesson.getModel().getValueAt(row, 11);
					Object column13 = tableLesson.getModel().getValueAt(row, 12);
					Object column14 = tableLesson.getModel().getValueAt(row, 13);

					String url = "jdbc:mysql://localhost:3306/school";
					String user = "root";
					String pass = "";
					String sql = "insert into lesson_time_table(day,eight_forty,nine_twenty,ten,ten_forty,break,twelve,twelve_forty,one_twenty,lunch,three,"
							+ "three_forty,four_twenty,five,year,term,student_class) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update "
							+ "day=VALUES(day)," + "eight_forty=VALUES(eight_forty),"
							+ "nine_twenty=VALUES(nine_twenty)," + "ten=VALUES(ten)," + "ten_forty=VALUES(ten_forty),"
							+ "break=VALUES(break)," + "twelve=VALUES(twelve)," + "twelve_forty=VALUES(twelve_forty),"
							+ "one_twenty=VALUES(one_twenty)," + "lunch=VALUES(lunch)," + "three=VALUES(three),"
							+ "three_forty=VALUES(three_forty)," + "four_twenty=VALUES(four_twenty),"
							+ "five=VALUES(five)," + "year=VALUES(year)," + "term=VALUES(term),"
							+ "student_class=VALUES(student_class)";
					java.sql.Connection conn = null;
					java.sql.PreparedStatement pst = null;

					try {

						conn =  CashBookController.getConnection();
						pst = conn.prepareStatement(sql);
						pst.setString(1, (String) column1);
						pst.setString(2, (String) column2);
						pst.setString(3, (String) column3);
						pst.setString(4, (String) column4);
						pst.setString(5, (String) column5);
						pst.setString(6, (String) column6);
						pst.setString(7, (String) column7);
						pst.setString(8, (String) column8);
						pst.setString(9, (String) column9);
						pst.setString(10, (String) column10);
						pst.setString(11, (String) column11);
						pst.setString(12, (String) column12);
						pst.setString(13, (String) column13);
						pst.setString(14, (String) column14);
						pst.setDouble(15, yearChooser.getYear());
						pst.setString(16, (String) comboBoxTerm.getSelectedItem());
						pst.setString(17, (String) comboBoxLesson.getSelectedItem());
						pst.executeUpdate();

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

			}
		});
		JTableHeader headerCol = tableLesson.getTableHeader();
		headerCol.setPreferredSize(new Dimension(1250, 30));
		scrollPaneLesson = new JScrollPane(tableLesson);
		scrollPaneLesson.setPreferredSize(new Dimension(1250, 400));
		add(scrollPaneLesson,BorderLayout.CENTER);

		panelDown=new JPanel();
		
		btnBack = new JButton("Exit");
		add(panelDown,BorderLayout.SOUTH);
		
		panelDown.add(btnBack);

		btnPrint = new JButton("Export to Excel");
		panelDown.add(btnPrint);
		btnPrint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				 fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify name and folder to export this deatils");

					int selected = fileChooser.showSaveDialog(ViewTimeTable.this);

					if (selected == JFileChooser.APPROVE_OPTION) {
						try {

							fillData(tableLesson, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
			}
		});

		displayData(tableLesson, "select day as Day,eight_forty as 800_840,nine_twenty as 840_920,ten,ten_forty,break,twelve,twelve_forty,one_twenty,lunch,three,"
							+ "three_forty,four_twenty,five from lesson_time_table where year='" + yearChooser.getYear() + "'");
		
		displayInComboBox(comboBoxLesson, "select class_name from student_classes");
		displayInComboBox(comboBoxTerm, "select term_name from student_terms");

		setVisible(true);
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
			    cell2.setCellValue(model.getValueAt(i, j).toString());
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


	
}
