package clarion.ui.dorm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import clarion.controller.dorm.DormController;
import clarion.model.dorm.Dorm;
import school.ui.student.GeneralStudentsPanel;

public class DormPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton btnAddNewDorm;
	private JButton btnChangeDormName;
	private JButton btnDeleteDorm;

	private JTable tableDorm;
	private JScrollPane scrollPaneDormList;
	private JScrollPane scrollPaneDormTable;

	private DormController controller;

	private DormTableModel dormTableModel;

	private JList<Object> list;

	private JLabel labelDormName;

	private JPanel panelDormList;

	private JPanel panelButtons;

	private JPanel panelTopHolder;

	private JPanel panelSearchBy;

	private JLabel labelSearchByClassNumber;
	private JLabel labelSearchByName;
	private JLabel labelSearchByYear;

	private JTextField fieldSearchByClassNumber;
	private JTextField fieldSearchByName;
	private JTextField fieldSearchByYear;

	private JPanel panelExportPrintAndTranser;

	private JButton btnExportToExcel;
	private JButton btnPrint;
	private JButton btnTransfer;

	private String borderTitle;

	private JLabel labelID;

	private Dorm dorm;

	private DormStudentTransfer transfer;

	private String to;

	private JPanel panelTopHolderAssetsName;

	protected JFileChooser fileChooser;

	public DormPanel() {
		setUpDormPanel();
	}

	private void setUpDormPanel() {
//		setBackground(Color.decode("#245089"));

		setLayout(new BorderLayout(5,5));

		// dimension for Buttons on the top left corner
		Dimension dimensionButtons = new Dimension(150, 30);

		// dimension for Labels to search
		Dimension dimensionLabels = new Dimension(150, 30);

		// dimension for fields to search
		Dimension dimensionFields = new Dimension(150, 30);
		
		panelTopHolderAssetsName = new JPanel();
//		setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
		setBorder(new LineBorder(Color.blue, 3));
//		setPreferredSize(new Dimension(1160, 480));
//		add(panelTopHolderAssetsName,BorderLayout.CENTER);

		panelTopHolder = new JPanel();
		panelTopHolder.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelTopHolder.setBackground(Color.decode("#5f9ea0"));
		panelTopHolder.setPreferredSize(new Dimension(580, 190));
		add(panelTopHolder, BorderLayout.NORTH);

		btnAddNewDorm = new JButton("Add New Dormitory");
		btnAddNewDorm.setPreferredSize(dimensionButtons);
		btnAddNewDorm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DormNameDialog dialog = new DormNameDialog(DormPanel.this, controller, false);
				dialog.setVisible(true);
			}
		});

		btnChangeDormName = new JButton("Change Dorm Name");
		btnChangeDormName.setPreferredSize(dimensionButtons);
		btnChangeDormName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int dorm = list.getSelectedIndex();

				if (dorm < 0) {
					JOptionPane.showMessageDialog(null, "You must select a dorm from the list");
					return;
				}

				Object name = list.getSelectedValue();

				DormChangeNameDialog dialog = new DormChangeNameDialog(DormPanel.this, controller, name, true);
				dialog.getLabelID().setText(labelID.getText());

				dialog.setVisible(true);
			}
		});

		btnDeleteDorm = new JButton("Remove Dormitory");
		btnDeleteDorm.setPreferredSize(dimensionButtons);
		btnDeleteDorm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selected = list.getSelectedIndex();

				if (selected < 0) {
					JOptionPane.showMessageDialog(null,
							"You must first select a dormitory\n from the list to be removed");
					return;
				}

				int response = JOptionPane.showConfirmDialog(DormPanel.this,
						"Are you sure you want to remove this dormitory forever?", "Confirm", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (response != JOptionPane.YES_OPTION) {
					return;
				}

				Object name = list.getSelectedValue();

				String idStr = labelID.getText();

				Integer id = new Integer(idStr);

				try {
					controller.removeDormitory(name, id);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				refreshDormList();

			}
		});

		panelDormList = new JPanel();
		panelDormList.setPreferredSize(new Dimension(180, 50));
		panelDormList.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelDormList.setBorder(new TitledBorder(new LineBorder(Color.white, 3), "Dormitories"));
//		panelDormList.setBackground(Color.decode("#245"));
		add(panelDormList, BorderLayout.WEST);
		panelDormList.setBackground(Color.decode("#5f9ea0"));

		labelID = new JLabel("ID Number");

		// BUTTONS ARE HELD HERE ... top left corner buttons
		panelButtons = new JPanel();
		panelButtons.setBorder(new TitledBorder(new LineBorder(Color.white, 3), "Manage Dormitories"));
		panelButtons.setPreferredSize(new Dimension(160, 140));
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER));

		panelButtons.setBackground(Color.decode("#5f9ea0"));
		panelTopHolder.add(panelButtons);

		panelButtons.add(btnAddNewDorm);
		panelButtons.add(btnChangeDormName);
		panelButtons.add(btnDeleteDorm);
		panelButtons.setBackground(Color.decode("#5f9ea0"));

		// search by
		panelSearchBy = new JPanel();
		panelSearchBy.setBorder(new TitledBorder(new LineBorder(Color.white, 3), "Search Everything:"));
		panelSearchBy.setPreferredSize(new Dimension(470, 140));
		panelTopHolder.add(panelSearchBy);
		panelSearchBy.setBackground(Color.decode("#5f9ea0"));

		// search by labels
		labelSearchByClassNumber = new JLabel("Search:");
		labelSearchByClassNumber.setPreferredSize(dimensionLabels);
		panelSearchBy.add(labelSearchByClassNumber);

		
		// search by fields
		fieldSearchByClassNumber = new JTextField();
		fieldSearchByClassNumber.setPreferredSize(dimensionFields);
		fieldSearchByClassNumber.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				// List<Dorm> dorms = null;

				List<Dorm> dorms = null;

				String searchName = fieldSearchByClassNumber.getText();

				Object id = null;

				labelDormName.setText(list.getSelectedValue().toString());

				String title = list.getSelectedValue().toString();
				borderTitle = title;

				try {

					dorms = controller.searchStudent(searchName, title);

					// dorms =
					// controller.getAllDormStudents(list.getSelectedValue().toString());

					id = controller.dormID(list.getSelectedValue().toString());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				labelID.setText(id.toString());

				dormTableModel = new DormTableModel(dorms);

				tableDorm.setModel(dormTableModel);

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		panelSearchBy.add(fieldSearchByClassNumber);

		
		// panel for export, print, and transfer buttons
		panelExportPrintAndTranser = new JPanel();
		panelExportPrintAndTranser.setBorder(new TitledBorder(new LineBorder(Color.white, 3), "Extras"));
		panelExportPrintAndTranser.setBackground(Color.decode("#5f9ea0"));
		panelExportPrintAndTranser.setPreferredSize(new Dimension(160, 140));
		panelTopHolder.add(panelExportPrintAndTranser);

		// export, print, and transfer buttons
		btnExportToExcel = new JButton("Export To Excel");
		btnExportToExcel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				 fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify name and folder to export this pdf file");

					int selected = fileChooser.showSaveDialog(DormPanel.this);

					if (selected == JFileChooser.APPROVE_OPTION) {
						try {

							fillData(tableDorm, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
			}
		});
		btnExportToExcel.setPreferredSize(dimensionButtons);
		panelExportPrintAndTranser.add(btnExportToExcel);

		btnPrint = new JButton("Print Table");
		btnPrint.setPreferredSize(dimensionButtons);
		panelExportPrintAndTranser.add(btnPrint);

		btnTransfer = new JButton("Transfer Student");
		btnTransfer.setPreferredSize(dimensionButtons);
		panelExportPrintAndTranser.add(btnTransfer);
		btnTransfer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int row = tableDorm.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(null, "You must select a student from the table");
					return;
				}

				dorm = (Dorm) tableDorm.getValueAt(row, DormTableModel.OBJECT_COLUMN);

				transfer = new DormStudentTransfer();

				transfer.getFieldClassNumber().setText(dorm.getClassNumber());
				transfer.getFieldStudentClass().setText(dorm.getStudentClass());
				transfer.getFieldStudentName().setText(dorm.getStudentName());
				transfer.getFieldYear().setText(dorm.getYear());

				transfer.setVisible(true);

				String classNumber = transfer.getFieldClassNumber().getText();
				String studentName = transfer.getFieldStudentName().getText();
				String studentClass = transfer.getFieldStudentClass().getText();
				String year = transfer.getFieldYear().getText();

				to = new String();

				transfer.getComboBoxDorms().addActionListener(new ActionListener() {
					String toAgain = null;

					@Override
					public void actionPerformed(ActionEvent e) {
						toAgain = transfer.getComboBoxDorms().getSelectedItem().toString();
						to = toAgain;
					}
				});

				transfer.getBtnTransfer().addActionListener(new ActionListener() {

					String fromTable = list.getSelectedValue().toString();
					int id = dorm.getId();

					@Override
					public void actionPerformed(ActionEvent e) {

						try {

							if (!transfer.getComboBoxDorms().getSelectedItem().equals("Choose Dormitory")) {
								controller.transferStudentDorm(classNumber, studentName, studentClass, year, to,
										fromTable, id);
								System.out.println(to);
								transfer.setVisible(false);
								refreshTable();
							} else {
								JOptionPane.showMessageDialog(null,
										"You have to choose a dormitory\n" + "to transfer the selected student to");
								return;
							}

							refreshTable();

						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
				});

			}
		});

		labelDormName = new JLabel("Dormitory Name:");
		add(labelDormName);

		

		List<Object> theLists = null;

		try {
			controller = new DormController();
			theLists = controller.fillDormTree();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		list = new JList<>();
		theLists.remove(0);
		list.setListData(theLists.toArray());

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// table starts here

		borderTitle = new String();

		tableDorm = new JTable();
		tableDorm.setBackground(Color.decode("#5f9ea0"));
		scrollPaneDormTable = new JScrollPane();
		scrollPaneDormTable.setViewportView(tableDorm);
		add(scrollPaneDormTable, BorderLayout.CENTER);

		list.addListSelectionListener(new ListSelectionListener() {

			List<Dorm> dorms = null;

			Object id = null;

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {

					labelDormName.setText(list.getSelectedValue().toString());

					String title = list.getSelectedValue().toString();
					borderTitle = title;

					try {
						dorms = controller.getAllDormStudents(list.getSelectedValue().toString());

						// dormName =
						// controller.dormID(list.getSelectedValue().toString());
						id = controller.dormID(list.getSelectedValue().toString());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					dormTableModel = new DormTableModel(dorms);

					labelID.setText(id.toString());

					tableDorm.setModel(dormTableModel);
					scrollPaneDormTable.setBorder(new TitledBorder(new LineBorder(Color.white, 3), borderTitle));
				}
			}
		});

		scrollPaneDormList = new JScrollPane(list);
		scrollPaneDormList.setPreferredSize(new Dimension(160, 250));
		panelDormList.add(scrollPaneDormList);


		setBackground(Color.decode("#5f9ea0"));
	}

	public void refreshTable() {
		List<Dorm> dorms = null;

		Object id = null;

		labelDormName.setText(list.getSelectedValue().toString());

		String title = list.getSelectedValue().toString();
		borderTitle = title;

		try {
			dorms = controller.getAllDormStudents(list.getSelectedValue().toString());

			id = controller.dormID(list.getSelectedValue().toString());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		labelID.setText(id.toString());

		tableDorm.setModel(dormTableModel);

	}

	public void refreshDormList() {
		

		List<Object> theLists = null;

		try {
			controller = new DormController();
			theLists = controller.fillDormTree();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		theLists.remove(0);

		list.setListData(theLists.toArray());

	}

	public JList<Object> getList() {
		return list;
	}

	public JScrollPane getScrollPaneDormList() {
		return scrollPaneDormList;
	}

	public JTable getTableDorm() {
		return tableDorm;
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
