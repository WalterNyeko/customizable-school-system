package school.ui.timetable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
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
import school.ui.staff.StaffsPanel;

public class ViewTimeTableExamination extends JDialog {

	private JButton btnBack;
	private JButton btnPrint;
	private JYearChooser yearChooser;
	private JComboBox comboBoxTerm;
	private JComboBox comboBoxClass;

	private JTable tableExamination;
	private JScrollPane scrollPaneExamination;

	private JLabel labelYear;
	private Connection conn;
	private PreparedStatement pst;
	private JPanel panelUpper;
	private JPanel panelLower;
	protected JFileChooser fileChooser;

	public static void main(String[] args) {
		new ViewTimeTableExamination();
	}
	public ViewTimeTableExamination() {
		setSize(1300, 500);
		setTitle("View Examination Time Table");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		labelYear = new JLabel("Choose Year");
		labelYear.setPreferredSize(new Dimension(80, 30));
		labelYear.setFont(new Font("Times New Roman", Font.BOLD, 13));
		
		panelUpper=new JPanel();
		add(panelUpper,BorderLayout.NORTH);
		panelUpper.add(labelYear);

		yearChooser = new JYearChooser();
		yearChooser.getYear();
		yearChooser.setPreferredSize(new Dimension(60, 30));
		yearChooser.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelUpper.add(yearChooser);

		String[] terms = { "Choose Term"};

		comboBoxTerm = new JComboBox<>(terms);
		comboBoxTerm.setPreferredSize(new Dimension(140, 30));
		comboBoxTerm.setFont(new Font("Times New Roman", Font.BOLD, 15));
		comboBoxTerm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				displayData(tableExamination,"select day,morning,mid_morning,after_noon,evening from examination_time_table where student_class='"
						+ comboBoxClass.getSelectedItem() + "' and term='" + comboBoxTerm.getSelectedItem()
						+ "' and year='" + yearChooser.getYear() + "'");
		
			}
		});
		panelUpper.add(comboBoxTerm);

		String[] classes = { "Choose Class"};
		comboBoxClass = new JComboBox<>(classes);
		comboBoxClass.setPreferredSize(new Dimension(140, 30));
		comboBoxClass.setFont(new Font("Times New Roman", Font.BOLD, 15));
		comboBoxClass.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				displayData(tableExamination,"select day,morning,mid_morning,after_noon,evening from examination_time_table where student_class='"
						+ comboBoxClass.getSelectedItem() + "' and term='" + comboBoxTerm.getSelectedItem()
						+ "' and year='" + yearChooser.getYear() + "'");
		
			}
		});
		panelUpper.add(comboBoxClass);

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

		tableExamination = new JTable(model) ;
		JTableHeader headerCol = tableExamination.getTableHeader();
		headerCol.setPreferredSize(new Dimension(1250, 30));
		
		MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();

		tableExamination.setDefaultRenderer(Object.class, renderer);
		
		this.getContentPane().setBackground(new Color(0, 51, 51));
		
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
		hd.setPreferredSize(new Dimension(1150, 30));
		tableExamination.setRowHeight(50);

		scrollPaneExamination = new JScrollPane();
		scrollPaneExamination.setViewportView(tableExamination);
		scrollPaneExamination.setPreferredSize(new Dimension(1260, 380));
		add(scrollPaneExamination,BorderLayout.CENTER);

		
		panelLower=new JPanel();
		add(panelLower,BorderLayout.SOUTH);
		btnBack = new JButton("Back");
		panelLower.add(btnBack);

		btnPrint = new JButton("Export to Excel");
		panelLower.add(btnPrint);
		btnPrint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				 fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify name and folder to export this deatils");

					int selected = fileChooser.showSaveDialog(ViewTimeTableExamination.this);

					if (selected == JFileChooser.APPROVE_OPTION) {
						try {

							fillData(tableExamination, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
			}
		});
		displayInComboBox(comboBoxClass, "select class_name from student_classes");
		displayInComboBox(comboBoxTerm, "select term_name from student_terms");
		
		
		
		
		
		
		displayData(tableExamination,"select day,morning,mid_morning,after_noon,evening from examination_time_table where year='" + yearChooser.getYear() + "'");
		
		
		
		
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
