package school.ui.staff;

import java.awt.BorderLayout;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
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

import com.github.sarxos.webcam.Webcam;

import school.ui.finances.CashBookController;
import school.ui.finances.FinanceAnalyticalCashBook;
import school.ui.staff.TeachingStaffsPanel.VideoFeedTaker;

public class TeachingStaffsPanel extends JPanel {

	public JTable tableTeachingStaffs;
	private JScrollPane scrollPaneTeachingStaffs;
	private JButton btnBackTeachingStaff, btnPrintTeachingStaffs;
	public JLabel labelTeachingStaffImage;
	private JLabel labelFakeTeachingStaffs;

	private JButton addStaff;
	private JButton addStaffResponsibility;
	private JComponent leftPanel;
	private JPanel rightPanel;
	private JButton btnExportToExcel;
	private JButton viewResponsibilities;
	private JButton btnUploadStaffPicture;
	private JButton takeStaffPicture;
	private JButton btnClearPicture;
	protected JFileChooser fileChooser;
	public boolean isRunning;
	public Image image;
	public Webcam webcam;
	protected String ss;
	protected JLabel labelStudentPhoto;
	protected JButton btnCapture;
	protected JButton btnStart;
	protected JButton btnStop;
	private JLabel labelSearch;
	private JTextField fieldSearch;
	protected FileInputStream input;
	protected TeachingStaffsPopUp teachingStaffsPopUp;
	public JLabel labelUser;
	private Connection conn;
	private PreparedStatement pst;

	public TeachingStaffsPanel() {
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
		
		
		labelUser = new JLabel("");
		leftPanel.add(labelUser);
		labelUser.setVisible(false);

		labelSearch = new JLabel("Search Everything:");
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
				displayData(tableTeachingStaffs,
						"select table_teaching_staffs.id_number,table_teaching_staffs.staff_name,"
								+ "table_teaching_staffs.staff_email,table_teaching_staffs.tin_number,table_teaching_staffs.nssf_number,table_teaching_staffs.staff_contact,"
								+ "table_teaching_staffs.staff_gender,table_teaching_staffs.staff_address,"
								+ "GROUP_CONCAT(non_teaching_staff.responsibility) from table_teaching_staffs,"
								+ "non_teaching_staff where table_teaching_staffs.id_number=non_teaching_staff.staff_id and concat(table_teaching_staffs.id_number,"
								+ "table_teaching_staffs.staff_name,"
								+ "table_teaching_staffs.staff_email,table_teaching_staffs.staff_contact,table_teaching_staffs.staff_gender,"
								+ "table_teaching_staffs.staff_address,non_teaching_staff.responsibility) LIKE '%"
								+ fieldSearch.getText() + "%' group by non_teaching_staff.staff_id");

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		leftPanel.add(fieldSearch);

		labelTeachingStaffImage = new JLabel("Picture Teaching Staff");
		labelTeachingStaffImage.setPreferredSize(new Dimension(170, 160));
		labelTeachingStaffImage.setBorder(new LineBorder(Color.white, 2));
		rightPanel.add(labelTeachingStaffImage);

		btnBackTeachingStaff = new JButton("Back To Home");
		btnBackTeachingStaff.setPreferredSize(new Dimension(170, 25));
		btnPrintTeachingStaffs = new JButton("Edit Staff's Info");
		btnPrintTeachingStaffs.setPreferredSize(new Dimension(170, 25));
		btnPrintTeachingStaffs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				checkUserRightsBeforeEditing();

				

			}
		});
		btnExportToExcel = new JButton("Export To Excel");
		btnExportToExcel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to export this deatils");

				int selected = fileChooser.showSaveDialog(TeachingStaffsPanel.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						fillData(tableTeachingStaffs, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}
		});
		btnExportToExcel.setPreferredSize(new Dimension(170, 25));

		rightPanel.add(labelTeachingStaffImage);
		rightPanel.add(btnBackTeachingStaff);
		rightPanel.add(btnPrintTeachingStaffs);
		rightPanel.add(btnExportToExcel);
		addStaff = new JButton("Add New Staff");
		addStaff.setPreferredSize(new Dimension(170, 25));
		addStaffResponsibility = new JButton("Add Responsibility");
		addStaffResponsibility.setPreferredSize(new Dimension(170, 25));
		rightPanel.add(addStaff);
		rightPanel.add(addStaffResponsibility);
		addStaff.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				checkUserRightsBeforeAdding();

			}
		});

		addStaffResponsibility.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				checkUserRightsBeforeAddingResponsibility();
			}
		});

		viewResponsibilities = new JButton("View Responsibilities");
		viewResponsibilities.setPreferredSize(new Dimension(170, 25));
		viewResponsibilities.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int row = tableTeachingStaffs.getSelectedRow();
				String column1 = tableTeachingStaffs.getValueAt(row, 0).toString();
				String column2 = tableTeachingStaffs.getValueAt(row, 1).toString();
				StaffsResponsibilities staffsResponsibilities = new StaffsResponsibilities();
				staffsResponsibilities.setTitle(column2 + "; ID Number: " + column1);
				staffsResponsibilities.setLocation(450, 100);

				displayData(staffsResponsibilities.tableSuspension,
						"select staff_id,teacher_name,responsibilty from teaching_staffs where staff_id='" + column1
								+ "'");

			}
		});
		btnUploadStaffPicture = new JButton("Upload Staff Picture");
		btnUploadStaffPicture.setPreferredSize(new Dimension(170, 25));
		btnUploadStaffPicture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
				fc.addChoosableFileFilter(filter);
				int result = fc.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedShit = fc.getSelectedFile();
					String path = selectedShit.getAbsolutePath();
					labelTeachingStaffImage.setIcon(ResizeImage(path));
					ss = path;
				}

				else if (result == JFileChooser.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(null, "No Photo Was Selected", "Please select a photo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		rightPanel.add(viewResponsibilities);
		rightPanel.add(btnUploadStaffPicture);

		takeStaffPicture = new JButton("Take Staff's Picture");
		takeStaffPicture.setPreferredSize(new Dimension(170, 25));
		takeStaffPicture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				JFrame framecamera = new JFrame();
				framecamera.setTitle("Capture image");
				framecamera.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				framecamera.setSize(650, 565);
				framecamera.setLocationRelativeTo(null);
				framecamera.setResizable(false);

				labelStudentPhoto = new JLabel("");
				labelStudentPhoto.setPreferredSize(new Dimension(400, 330));
				labelStudentPhoto.setBorder(new LineBorder(Color.white, 3));
				framecamera.add(labelStudentPhoto, BorderLayout.CENTER);

				btnCapture = new JButton("Capture");
				btnCapture.setPreferredSize(new Dimension(150, 30));
				btnCapture.addActionListener(new ActionListener() {

					private BufferedImage image;
					private JFileChooser fileChooser;

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if (!isRunning) {
							isRunning = true;
							new VideoFeedTaker().start();
						} else {
							isRunning = false;
							image = webcam.getImage();

							fileChooser = new JFileChooser();
							fileChooser.setDialogTitle("Specify where to save this image");

							int userSelection = fileChooser.showSaveDialog(TeachingStaffsPanel.this);

							if (userSelection == JFileChooser.APPROVE_OPTION) {
								try {

									ImageIO.write(image, "PNG", new File(fileChooser.getSelectedFile() + ".png"));

								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}

						}

					}
				});
				JPanel panelSouth = new JPanel();
				framecamera.add(panelSouth, BorderLayout.SOUTH);
				panelSouth.setBorder(new LineBorder(Color.white, 3));
				btnStart = new JButton("Start");
				btnStart.setPreferredSize(new Dimension(150, 30));
				btnStart.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						webcam = Webcam.getDefault();

						webcam.setViewSize(new Dimension(640, 480));

						if (!isRunning) {
							isRunning = true;
							webcam.open();
							new VideoFeedTaker().start();
						} else {
							isRunning = false;
							new VideoFeedTaker().stop();

						}

					}
				});
				panelSouth.add(btnStart);
				panelSouth.add(btnCapture);
				btnStop = new JButton("Stop");
				btnStop.setPreferredSize(new Dimension(150, 30));
				btnStop.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						webcam.close();
					}
				});
				panelSouth.add(btnStop);

				framecamera.setVisible(true);

			}
		});
		btnClearPicture = new JButton("Save Staff Picture");
		btnClearPicture.setPreferredSize(new Dimension(170, 25));
		btnClearPicture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				checkUserRightsBeforeSavingPicture();

			}
		});
		rightPanel.add(takeStaffPicture);
		rightPanel.add(btnClearPicture);

		String[][] staffsdata = new String[][] {

				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },

		};

		String[] staffsheading = new String[] { "ID Number", "Staff Name", "Email", "TIN Number", "Qualification",
				"Contact", "Gender", "Payroll Status", "Responsibility" };
		DefaultTableModel defaultTableModel = new DefaultTableModel();
		defaultTableModel.setDataVector(staffsdata, staffsheading);

		tableTeachingStaffs = new JTable(defaultTableModel) {
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
		};

		tableTeachingStaffs.setShowGrid(false);
		JTableHeader headertableTeachingStaffs = tableTeachingStaffs.getTableHeader();
		headertableTeachingStaffs.setPreferredSize(new Dimension(935, 30));

		scrollPaneTeachingStaffs = new JScrollPane(tableTeachingStaffs);
		tableTeachingStaffs.setRowHeight(25);
		scrollPaneTeachingStaffs.setPreferredSize(new Dimension(935, 400));
		Border bodaforstaffs = BorderFactory.createRaisedBevelBorder();
		scrollPaneTeachingStaffs.setBorder(bodaforstaffs);
		leftPanel.add(scrollPaneTeachingStaffs);
		tableTeachingStaffs.setShowGrid(false);
		tableTeachingStaffs.setAutoCreateRowSorter(true);
		tableTeachingStaffs.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableTeachingStaffs.getColumnModel().getColumn(2).setPreferredWidth(140);
		tableTeachingStaffs.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableTeachingStaffs.getColumnModel().getColumn(4).setPreferredWidth(50);
		tableTeachingStaffs.getColumnModel().getColumn(5).setPreferredWidth(150);
		tableTeachingStaffs.getColumnModel().getColumn(1).setPreferredWidth(250);
		tableTeachingStaffs.getColumnModel().getColumn(6).setPreferredWidth(140);

		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem addNewteacher = new JMenuItem("Add New Teaching Staff");
		JMenuItem deleteTeachingStaff = new JMenuItem("Delete Selected Teaching Staff");

		addNewteacher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				checkUserRightsBeforeAdding();
			}
		});

		JMenuItem editTeacherInfo = new JMenuItem("Edit Teaching Staff Info");
		editTeacherInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				checkUserRightsBeforeEditing();

			}
		});

		JMenuItem addResponsibility = new JMenuItem("Add Teaching Staff's Responsibility");
		addResponsibility.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			checkUserRightsBeforeAddingResponsibility();

			}
		});
		deleteTeachingStaff.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int row = tableTeachingStaffs.getSelectedRow();
				String id = tableTeachingStaffs.getValueAt(row, 0).toString();

				if(row<0) {
					
					JOptionPane.showMessageDialog(null, "Please make sure you have selected the staff");
					
				}else {
					checkUserRights();
				}
				
			}
		});

		popupMenu.add(addNewteacher);
		popupMenu.add(editTeacherInfo);
		popupMenu.add(addResponsibility);
		popupMenu.add(deleteTeachingStaff);

		tableTeachingStaffs.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {

				showStaffsPhoto();
				if (SwingUtilities.isRightMouseButton(e)) {
					popupMenu.show(tableTeachingStaffs, e.getX(), e.getY());
				}

			}
		});

	}

	public void fillData(JTable table, java.io.File file) {

		try {

			XSSFWorkbook workbook1 = new XSSFWorkbook();
			// Sheet sheet1 = workbook1.createSheet("Color Test");

			XSSFSheet fSheet;
			fSheet = workbook1.createSheet("New Sheet");

			TableModel model = table.getModel();

			CellStyle style = workbook1.createCellStyle();
			CellStyle stylebody = workbook1.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			XSSFFont font = workbook1.createFont();
			font.setColor(IndexedColors.BLACK.getIndex());
			style.setFont(font);
			font.setBold(true);
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

					fSheet.autoSizeColumn(0);
					fSheet.autoSizeColumn(1);
					fSheet.autoSizeColumn(2);
					fSheet.autoSizeColumn(3);
					fSheet.autoSizeColumn(4);
					fSheet.autoSizeColumn(5);
					fSheet.autoSizeColumn(6);
					fSheet.autoSizeColumn(7);
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

	class VideoFeedTaker extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isRunning) {

				try {
					image = webcam.getImage();
					labelStudentPhoto.setIcon(new ImageIcon(image));

					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public ImageIcon ResizeImage(String ImagePath) {

		ImageIcon MyImg = new ImageIcon(ImagePath);
		Image img = MyImg.getImage();
		Image newimg = img.getScaledInstance(labelTeachingStaffImage.getWidth(), labelTeachingStaffImage.getHeight(),
				Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newimg);

		return image;

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

	public void showStaffsPhoto() {

		int selectedRow = tableTeachingStaffs.getSelectedRow();

		String staffID = (String) tableTeachingStaffs.getValueAt(selectedRow, 0);
		String staffName = (String) tableTeachingStaffs.getValueAt(selectedRow, 1);
		try {

			java.sql.Connection conn = CashBookController.getConnection();

			PreparedStatement pst = conn
					.prepareStatement("select photo from staffs_photos where staff_id='" + staffID + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				byte[] img = rs.getBytes("photo");
				ImageIcon image = new ImageIcon(img);
				Image im = image.getImage();
				Image im2 = im.getScaledInstance(labelTeachingStaffImage.getWidth(),
						labelTeachingStaffImage.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon newImage = new ImageIcon(im2);
				labelTeachingStaffImage.setIcon(newImage);

			} else {
				JOptionPane.showMessageDialog(null, "No Image Found For " + staffName + " In The Database",
						"Lacking " + staffName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
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
	
	
	public void checkUserRights() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + labelUser.getText() + "'");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				if (rs.getInt("transfer or remove staffs") == 1) {
					
					String[] options = { "Delete Staff", "Cancel" };
					int ans = JOptionPane.showOptionDialog(null, "Are You Sure You Want to Delete the Selected Staff???",
							"Confirmation of Delete Request", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
					if (ans == 0) {
						
						int row = tableTeachingStaffs.getSelectedRow();
						String id = tableTeachingStaffs.getValueAt(row, 0).toString();


						AddUpdateDelete("delete from table_teaching_staffs where id_number='" + id + "'");

					} else {

					}
				} else {
					JOptionPane.showMessageDialog(null, "You Do Not Have Permission To Delete Staffs");
				}
				
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		}

	}
	
	public void checkUserRightsBeforeEditing() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + labelUser.getText() + "'");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				if (rs.getInt("transfer or remove staffs") == 1) {
					
				
						int row = tableTeachingStaffs.getSelectedRow();
						if(row<0) {
							JOptionPane.showMessageDialog(null, "Please select a staff before proceeding");
						}else {
							teachingStaffsPopUp = new TeachingStaffsPopUp();
							teachingStaffsPopUp.setTitle("Edit Teaching Staff Info");
							
							String id = tableTeachingStaffs.getValueAt(row, 0).toString();
							teachingStaffsPopUp.btnSave.setText("Save Changes");
							teachingStaffsPopUp.fieldIDNo.setText(id);
						}

				} else {
					JOptionPane.showMessageDialog(null, "You Do Not Have Permission To Edit Staffs");
				}
				
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		}

	}

	
	
	public void checkUserRightsBeforeAdding() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + labelUser.getText() + "'");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				if (rs.getInt("transfer or remove staffs") == 1) {
					
						

						teachingStaffsPopUp = new TeachingStaffsPopUp();
						if (teachingStaffsPopUp.isShowing()) {
							teachingStaffsPopUp.setTitle("Add New Teaching Staff");
						}else {
							
						}

				} else {
					JOptionPane.showMessageDialog(null, "You Do Not Have Permission To Add Staffs");
				}
				
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		}

	}

	
	public void checkUserRightsBeforeSavingPicture() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + labelUser.getText() + "'");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				if (rs.getInt("transfer or remove staffs") == 1) {
					
					
						
						String sql = "insert into staffs_photos(staff_id,photo) values(?,?) on duplicate key update photo=values(photo);";
						int selectedRow = tableTeachingStaffs.getSelectedRow();
						String selectedID = tableTeachingStaffs.getValueAt(selectedRow, 0).toString();
						java.sql.Connection conn = null;
						java.sql.PreparedStatement pst = null;
						try {
							input = new FileInputStream(new File(ss));
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {

							conn = CashBookController.getConnection();
							pst = conn.prepareStatement(sql);

							pst.setString(1, selectedID);
							pst.setBlob(2, input);

							pst.executeUpdate();

							JOptionPane.showMessageDialog(null, "Staff Photo Saved Successfully");

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}

						labelTeachingStaffImage.setIcon(null);
					
				} else {
					JOptionPane.showMessageDialog(null, "You Do Not Have Permission To Edit Staffs Pictures");
				}
				
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		}

	}

	
	public void checkUserRightsBeforeAddingResponsibility() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + labelUser.getText() + "'");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				if (rs.getInt("transfer or remove staffs") == 1) {
					
					
					int rows = tableTeachingStaffs.getSelectedRow();
					if (rows < 0) {
						JOptionPane.showMessageDialog(null,
								"Please, make sure you have selected a staff from the table below");
					} else {
						TeachersResponsibilities responsibilities = new TeachersResponsibilities();

						String id = tableTeachingStaffs.getValueAt(rows, 0).toString();
						String name = tableTeachingStaffs.getValueAt(rows, 1).toString();

						responsibilities.fieldIDNo.setText(id);
						responsibilities.fieldName.setText(name);
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "You Do Not Have Permission To Add Responsibilities");
				}
				
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		}

	}
}
