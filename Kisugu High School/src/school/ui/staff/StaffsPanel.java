package school.ui.staff;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import school.ui.finances.CashBookController;

public class StaffsPanel extends JPanel {

	private JPanel panelStaffs;
	private JTable tableStaffs;
	private JScrollPane scrollPaneStaffs;
	private JButton btnBack, btnPrint, addTeacher, addTeacherResponsibility;
	private JLabel labelStaffImage;
	private JLabel labelFake;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JLabel labelSearch;
	private JTextField fieldSearch;
	private JButton btnExport;
	protected JFileChooser fileChooser;

	public StaffsPanel() {
		setSize(1170, 450);
		setLayout(new FlowLayout(FlowLayout.LEFT));

		leftPanel = new JPanel();
		leftPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		leftPanel.setPreferredSize(new Dimension(950, 450));
		leftPanel.setBorder(new LineBorder(Color.white, 2));
		leftPanel.setBackground(new Color(0, 102, 102));
		add(leftPanel);

		rightPanel = new JPanel();
		rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		rightPanel.setPreferredSize(new Dimension(200, 450));
		rightPanel.setBorder(new LineBorder(Color.white, 2));
		rightPanel.setBackground(new Color(0, 102, 102));
		add(rightPanel);

		labelSearch = new JLabel("Search:");
		leftPanel.add(labelSearch);

		fieldSearch = new JTextField();
		fieldSearch.setPreferredSize(new Dimension(300, 25));
		fieldSearch.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				displayData(getTableStaffs(),
						"select * from all_staffs where "
								+ "CONCAT(staff_id,staff_name,staff_contact,staff_gender,staff_address,staff_category) LIKE '%"
								+ fieldSearch.getText() + "%'");

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		leftPanel.add(fieldSearch);

		btnBack = new JButton("Back");
		btnBack.setPreferredSize(new Dimension(170, 25));
		btnPrint = new JButton("Print");
		btnPrint.setPreferredSize(new Dimension(170, 25));
		btnExport = new JButton("Export To Excel");
		btnExport.setPreferredSize(new Dimension(170, 25));
		btnExport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				 fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify name and folder to export this deatils");

					int selected = fileChooser.showSaveDialog(StaffsPanel.this);

					if (selected == JFileChooser.APPROVE_OPTION) {
						try {

							fillData(getTableStaffs(), new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}

			}
		});
		        
		labelStaffImage = new JLabel("Picture Staff");

		labelStaffImage.setPreferredSize(new Dimension(170, 140));
		labelStaffImage.setBorder(new LineBorder(Color.white, 2));
		rightPanel.add(labelStaffImage);

		btnBack.setPreferredSize(new Dimension(170, 25));
		btnPrint.setPreferredSize(new Dimension(170, 25));
		rightPanel.add(btnBack);
		rightPanel.add(btnPrint);
		rightPanel.add(btnExport);

		DefaultTableModel model = new DefaultTableModel();

		String[][] staffsdata = new String[][] {

				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },

		};

		String[] staffsheading = new String[] { "ID Number", "Staff Name", "Contact", "Gender", "Qualification/Post", "Category" };

		model.setDataVector(staffsdata, staffsheading);
		setTableStaffs(new JTable() {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component returnComp = super.prepareRenderer(renderer, row, column);
				Color alternateColor = new Color(202, 202, 206);
				Color whiteColor = Color.decode("#7f8c8d");
				if (!returnComp.getBackground().equals(getSelectionBackground())) {
					Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
					returnComp.setBackground(bg);
					bg = null;
				}
				return returnComp;
			}
		});

		getTableStaffs().setModel(model);
		getTableStaffs().setAutoCreateRowSorter(true);
		getTableStaffs().setShowGrid(false);
		JTableHeader headertableTeachingStaffs = getTableStaffs().getTableHeader();
		headertableTeachingStaffs.setPreferredSize(new Dimension(935, 30));

		scrollPaneStaffs = new JScrollPane(getTableStaffs());
		getTableStaffs().setRowHeight(25);
		scrollPaneStaffs.setPreferredSize(new Dimension(935, 400));
		Border bodaforstaffs = BorderFactory.createRaisedBevelBorder();
		scrollPaneStaffs.setBorder(bodaforstaffs);
		leftPanel.add(scrollPaneStaffs);
		getTableStaffs().setShowGrid(false);
		getTableStaffs().getColumnModel().getColumn(0).setPreferredWidth(85);
		getTableStaffs().getColumnModel().getColumn(2).setPreferredWidth(105);
		getTableStaffs().getColumnModel().getColumn(3).setPreferredWidth(85);
		getTableStaffs().getColumnModel().getColumn(4).setPreferredWidth(115);
		getTableStaffs().getColumnModel().getColumn(5).setPreferredWidth(210);
		getTableStaffs().getColumnModel().getColumn(1).setPreferredWidth(350);
		getTableStaffs().addMouseListener(new MouseListener() {

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
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

				showTeachersPhoto();

			}
		});

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

	public void showTeachersPhoto() {
		

		int selectedRow = getTableStaffs().getSelectedRow();

		String staffID = (String) getTableStaffs().getValueAt(selectedRow, 0);
		String staffName = (String) getTableStaffs().getValueAt(selectedRow, 1);
		try {

			java.sql.Connection conn = CashBookController.getConnection();

			PreparedStatement pst = conn
					.prepareStatement("select photo from staffs_photos where staff_id='" + staffID + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				byte[] img = rs.getBytes("photo");
				ImageIcon image = new ImageIcon(img);
				Image im = image.getImage();
				Image im2 = im.getScaledInstance(labelStaffImage.getWidth(), labelStaffImage.getHeight(),
						Image.SCALE_SMOOTH);
				ImageIcon newImage = new ImageIcon(im2);
				labelStaffImage.setIcon(newImage);

			} else {
				JOptionPane.showMessageDialog(null, "No Image Found For " + staffName + " In The Database",
						"Lacking " + staffName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public JTable getTableStaffs() {
		return tableStaffs;
	}

	public void setTableStaffs(JTable tableStaffs) {
		this.tableStaffs = tableStaffs;
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
