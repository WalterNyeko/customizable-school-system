package school.ui.student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
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
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import school.ui.finances.CashBookController;

public class GeneralStudentsPanel extends JPanel {

	public JTable tableStudents;
	private JScrollPane scrollPaneStudents;
	private JButton btnBackStudent, btnPrintStudents;
	public JLabel labelStudentImage;
	private JLabel labelFakeStudents;

	private JButton addTeacher;
	private JButton addTeacherResponsibility;
	private JPanel leftPanel, rightPanel;
	private JButton btnExportToExcel;
	private JButton viewResponsibilities;
	private JButton btnUploadStudentPicture;
	private JButton takeStudentPicture;
	private JButton btnClearPicture;
	protected JFileChooser fileChooser;
	protected boolean isRunning;
	protected Webcam webcam;
	public Image image;
	protected JLabel labelStudentPhoto;
	protected JButton btnCapture;
	protected JButton btnStart;
	protected JButton btnStop;
	protected String ss;
	private JLabel labelSearch;
	private JTextField fieldSearch;
	protected FileInputStream input;
	private JPopupMenu popupStudents;
	private JMenuItem detailsStudents;
	private JMenuItem visitationStudents;
	private JMenuItem suspensionStudents;
	private JMenuItem discontinueStudents;
	private DefaultTableModel model;
	public JLabel labelClass;
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private JMenuItem visitationRecords;
	private Connection conn;
	private JMenuItem deleteStudent;
	public JLabel labelUser;
	protected Calendar cal;
	protected String year;
	protected String thisyear;

	public GeneralStudentsPanel() {
		setSize(1170, 450);
		setLayout(new FlowLayout(FlowLayout.CENTER, 2, 10));

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

		labelClass = new JLabel("");
		leftPanel.add(labelClass);

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
				displayData(tableStudents, "select class_number,student_name,sponsor,payment_code,"
						+ "TIMESTAMPDIFF(YEAR,`" + labelClass.getText() + "`.date_of_birth,Now()),dormitory from `"
						+ labelClass.getText() + "` "
						+ "where concat(payment_code,class_number,student_name,sponsor,dormitory,address,term,year,date_of_birth) LIKE '%"
						+ fieldSearch.getText()
						+ "%' and (discipline_status='suspended' or discipline_status is null) group by payment_code");

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		leftPanel.add(fieldSearch);

		btnBackStudent = new JButton("Visitation Day");
		btnBackStudent.setPreferredSize(new Dimension(170, 25));
		btnBackStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int rows = tableStudents.getSelectedRow();
				if (rows < 0) {
					JOptionPane.showMessageDialog(null,
							"Please, make sure you have selected a student from the table below");
				} else {

					String classNo = tableStudents.getValueAt(rows, 0).toString();
					String Name = tableStudents.getValueAt(rows, 1).toString();
					String Code = tableStudents.getValueAt(rows, 3).toString();
					VisitationDay visitationDay = new VisitationDay();
					visitationDay.fiStuID.setText(Code);
					visitationDay.fiStuClass.setText(classNo);
					visitationDay.fiStuName.setText(Name);
				}
			}
		});
		btnPrintStudents = new JButton("Print Student's List");
		btnPrintStudents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to export this pdf file");

				int selected = fileChooser.showSaveDialog(GeneralStudentsPanel.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						print(tableStudents, new java.io.File(fileChooser.getSelectedFile() + ".pdf"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		btnPrintStudents.setPreferredSize(new Dimension(170, 25));
		btnExportToExcel = new JButton("Export To Excel");
		btnExportToExcel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to export this excel file");

				int selected = fileChooser.showSaveDialog(GeneralStudentsPanel.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						fillData(tableStudents, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}
		});
		btnExportToExcel.setPreferredSize(new Dimension(170, 25));
		labelFakeStudents = new JLabel();
		labelStudentImage = new JLabel("Student Picture");

		rightPanel.add(labelStudentImage);
		rightPanel.add(btnBackStudent);
		rightPanel.add(btnPrintStudents);
		rightPanel.add(btnExportToExcel);
		addTeacher = new JButton("Update Students Data");
		addTeacher.setPreferredSize(new Dimension(170, 25));
		addTeacherResponsibility = new JButton("Suspend This Student");
		addTeacherResponsibility.setPreferredSize(new Dimension(170, 25));
		rightPanel.add(addTeacher);
		rightPanel.add(addTeacherResponsibility);
		addTeacher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int rows = tableStudents.getSelectedRow();
				if (rows < 0) {
					JOptionPane.showMessageDialog(null,
							"Please, make sure you have selected a student from the table on the left");
				} else {
					StudentsDataEdit dataEdit = new StudentsDataEdit();
					int row = tableStudents.getSelectedRow();
					String paymentcode = tableStudents.getValueAt(row, 3).toString();
					dataEdit.fieldCode.setText(paymentcode);

				}

			}
		});

		addTeacherResponsibility.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				checkUserRightsBeforeSuspending();
			}
		});

		viewResponsibilities = new JButton("Discontinue This Student");
		viewResponsibilities.setPreferredSize(new Dimension(170, 25));
		viewResponsibilities.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkUserRightsBeforeDiscontinuing();
			}
		});
		btnUploadStudentPicture = new JButton("Upload Student Picture");
		btnUploadStudentPicture.addActionListener(new ActionListener() {

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
					labelStudentImage.setIcon(ResizeImage(path));
					ss = path;
				}

				else if (result == JFileChooser.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(null, "No Photo Was Selected", "Please select a photo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnUploadStudentPicture.setPreferredSize(new Dimension(170, 25));
		rightPanel.add(viewResponsibilities);
		rightPanel.add(btnUploadStudentPicture);

		takeStudentPicture = new JButton("Take Student's Picture");
		takeStudentPicture.setPreferredSize(new Dimension(170, 25));
		takeStudentPicture.addActionListener(new ActionListener() {

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

							int userSelection = fileChooser.showSaveDialog(GeneralStudentsPanel.this);

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

		btnClearPicture = new JButton("Save Student Picture");
		btnClearPicture.setPreferredSize(new Dimension(170, 25));
		btnClearPicture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int row = tableStudents.getSelectedRow();
				String paymentcode = tableStudents.getValueAt(row, 3).toString();

				String sql = "update students_info set photo=? where payment_code='" + paymentcode + "'";
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

					pst.setBlob(1, input);

					pst.executeUpdate();

					JOptionPane.showMessageDialog(null, "Student Photo Saved Successfully");

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				labelStudentImage.setIcon(null);

			}
		});
		rightPanel.add(takeStudentPicture);
		rightPanel.add(btnClearPicture);

		labelStudentImage.setPreferredSize(new Dimension(170, 160));
		labelStudentImage.setBorder(new LineBorder(Color.white, 2));

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
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },

		};

		String[] staffsheading = new String[] { "Class Number", "Student Name", "Sponsored", "Payment Code", "Age",
				"Dormitory" };

		model = new DefaultTableModel();
		model.setDataVector(staffsdata, staffsheading);
		tableStudents = new JTable(model) {
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

		tableStudents.setAutoCreateRowSorter(true);
		tableStudents.setAutoscrolls(true);
		tableStudents.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableStudents.setShowGrid(false);
		JTableHeader headertableStudents = tableStudents.getTableHeader();
		headertableStudents.setPreferredSize(new Dimension(935, 30));

		scrollPaneStudents = new JScrollPane(tableStudents);
		tableStudents.setRowHeight(25);
		scrollPaneStudents.setPreferredSize(new Dimension(935, 400));
		Border bodaforstaffs = BorderFactory.createRaisedBevelBorder();
		scrollPaneStudents.setBorder(bodaforstaffs);
		leftPanel.add(scrollPaneStudents);
		tableStudents.setShowGrid(false);
		tableStudents.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableStudents.getColumnModel().getColumn(2).setPreferredWidth(140);
		tableStudents.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableStudents.getColumnModel().getColumn(4).setPreferredWidth(50);
		tableStudents.getColumnModel().getColumn(5).setPreferredWidth(150);
		tableStudents.getColumnModel().getColumn(1).setPreferredWidth(250);

		popupStudents = new JPopupMenu();
		detailsStudents = new JMenuItem("Edit Student's Details");
		visitationStudents = new JMenuItem("Visitation Day");
		suspensionStudents = new JMenuItem("Suspension Records");
		discontinueStudents = new JMenuItem("Discontinuation Records");
		visitationRecords = new JMenuItem("Visitation Records");
		deleteStudent = new JMenuItem("Delete Selected Student");

		popupStudents.add(detailsStudents);
		popupStudents.add(visitationStudents);
		popupStudents.add(suspensionStudents);
		popupStudents.add(discontinueStudents);
		popupStudents.add(visitationRecords);
		popupStudents.add(deleteStudent);
		popupStudents.setPreferredSize(new Dimension(250, 150));

		detailsStudents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int rows = tableStudents.getSelectedRow();
				if (rows < 0) {
					JOptionPane.showMessageDialog(null,
							"Please, make sure you have selected a student from the table on the left");
				} else {
					StudentsDataEdit dataEdit = new StudentsDataEdit();
					int row = tableStudents.getSelectedRow();
					String paymentcode = tableStudents.getValueAt(row, 3).toString();
					dataEdit.fieldCode.setText(paymentcode);

				}

			}
		});

		visitationStudents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int rows = tableStudents.getSelectedRow();
				if (rows < 0) {
					JOptionPane.showMessageDialog(null,
							"Please, make sure you have selected a student from the table below");
				} else {

					String classNo = tableStudents.getValueAt(rows, 0).toString();
					String Name = tableStudents.getValueAt(rows, 1).toString();
					String Code = tableStudents.getValueAt(rows, 3).toString();
					VisitationDay visitationDay = new VisitationDay();
					visitationDay.fiStuID.setText(Code);
					visitationDay.fiStuClass.setText(classNo);
					visitationDay.fiStuName.setText(Name);
				}

			}
		});

		suspensionStudents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				SuspensionRecords obj = new SuspensionRecords();

			}
		});

		discontinueStudents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DiscontinuationRecords obj = new DiscontinuationRecords();
				

			}
		});

		visitationRecords.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				JDialog dialog = new JDialog();
				dialog.setTitle("Term");
				dialog.setSize(400, 100);
				dialog.setLocationRelativeTo(null);

				JPanel panelMain = new JPanel();
				panelMain.setLayout(new FlowLayout());
				dialog.add(panelMain);

				JLabel labelTerm = new JLabel("Choose Term");
				panelMain.add(labelTerm);

				JComboBox combo = new JComboBox();
				combo.setPreferredSize(new Dimension(150, 25));
				displayInComboBox(combo, "select term_name from student_terms");

				panelMain.add(combo);

				JButton btnContinue = new JButton("Continue");
				panelMain.add(btnContinue);
				btnContinue.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						VisitationRecords obj = new VisitationRecords();
						Calendar cal = Calendar.getInstance();

						String answer = "" + cal.getTime();
						String date = answer.substring(answer.length() - 4);

						displayData(obj.tableSuspension,
								"select student_id_number,"
										+ "student_name,visitor_name,visitor_contact,visitor_address,"
										+ "student_class from visitation where date LIKE '%" + date + "%' and term='"+combo.getSelectedItem()+"'");
						dialog.dispose();
					}
				});

				dialog.setVisible(true);

			}
		});

		deleteStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			
					
					checkUserRights();
					
					cal = Calendar.getInstance();
					year = "" + cal.getTime();
					thisyear = year.substring(year.length() - 4);
					
					displayData(tableStudents,
							"select class_number,student_name,CASE WHEN sponsor='Choose Sponsor' THEN 'Not Sponsored' WHEN sponsor='' THEN 'Unknown' ELSE 'Yes' END AS Sponsored,payment_code,"
									+ "TIMESTAMPDIFF(YEAR,`" + labelClass.getText()
									+ "`.date_of_birth,Now()),dormitory from `"
									+ labelClass.getText() + "`  where year='" + thisyear
									+ "' and (discipline_status='suspended' or discipline_status is null) group by payment_code");


			}
		});

		
		
		
		
		
		tableStudents.addMouseListener(new MouseListener() {

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

				showStudentsPhoto();

				if (SwingUtilities.isRightMouseButton(e)) {
					popupStudents.show(tableStudents, e.getX(), e.getY());
				}

			}
		});

	}

	public void fillData(JTable table, java.io.File file) {

		try {

			XSSFWorkbook workbook1 = new XSSFWorkbook();

			XSSFSheet fSheet;
			fSheet = workbook1.createSheet("Data Sheet");

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

			for (int i = 0; i < model.getRowCount(); i++) {
				XSSFRow fRow = fSheet.createRow((short) i + 1);
				for (int j = 0; j < model.getColumnCount(); j++) {
					XSSFCell cell2 = fRow.createCell((short) j);
					cell2.setCellValue(model.getValueAt(i, j).toString());
					cell2.setCellStyle(stylebody);

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
		Image newimg = img.getScaledInstance(labelStudentImage.getWidth(), labelStudentImage.getHeight(),
				Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newimg);

		return image;

	}

	private void print(JTable table, java.io.File file) {
		com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4.rotate());
		try {
			PdfWriter writer = PdfWriter.getInstance((com.lowagie.text.Document) document,
					new FileOutputStream(new java.io.File(fileChooser.getSelectedFile() + ".pdf")));

			((com.lowagie.text.Document) document).open();
			PdfContentByte cb = writer.getDirectContent();

			cb.saveState();
			Graphics2D g2 = cb.createGraphicsShapes(900, 400);

			Shape oldClip = g2.getClip();
			// g2.clipRect(0, 0, 900, 400);

			tableStudents.print(g2);
			// g2.setClip(oldClip);

			g2.dispose();
			cb.restoreState();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		document.close();

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

	public void showStudentsPhoto() {

		int selectedRow = tableStudents.getSelectedRow();

		String studentNumber = (String) tableStudents.getValueAt(selectedRow, 0);
		String staffName = (String) tableStudents.getValueAt(selectedRow, 1);
		try {

			java.sql.Connection conn = CashBookController.getConnection();

			PreparedStatement pst = conn
					.prepareStatement("select photo from students_info where class_number='" + studentNumber + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				byte[] img = rs.getBytes("photo");
				ImageIcon image = new ImageIcon(img);
				Image im = image.getImage();
				Image im2 = im.getScaledInstance(labelStudentImage.getWidth(), labelStudentImage.getHeight(),
						Image.SCALE_SMOOTH);
				ImageIcon newImage = new ImageIcon(im2);
				labelStudentImage.setIcon(newImage);

			} else {
				JOptionPane.showMessageDialog(null, "No Image Found For " + staffName + " In The Database",
						"Lacking " + staffName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void displayStudentsofAClass(JLabel label, String query) {

		try {
			con = CashBookController.getConnection();
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();

			if (rs.next()) {
				label.setText(rs.getString(1));
			}

		} catch (Exception e) {

		}
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
	
	public void checkUserRights() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + labelUser.getText() + "'");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				if (rs.getInt("discontinue students") == 1) {
					
					String[] options = { "Delete Student", "Cancel" };
					int row = tableStudents.getSelectedRow();
					String paymentcode = tableStudents.getValueAt(row, 3).toString();
					int ans = JOptionPane.showOptionDialog(null, "Are You Sure You Want to Delete the selected Student???",
							"Confirmation of Delete Request", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
					if (ans == 0) {

						AddUpdateDeleteSilently("delete from students_info where payment_code='" + paymentcode + "'");
						AddUpdateDeleteSilently("delete from student_ledger where payment_code='" + paymentcode + "'");
						AddUpdateDeleteSilently("delete from all_students_and_parents where payment_code='" + paymentcode + "'");
						AddUpdateDeleteSilently("delete from dispensary where payment_code='" + paymentcode + "'");
						AddUpdateDeleteSilently("delete from uce_particulars where payment_code='" + paymentcode + "'");
						AddUpdateDeleteSilently("delete from ple_particulars where payment_code='" + paymentcode + "'");
     					AddUpdateDeleteSilently("delete from `senior one` where payment_code='" + paymentcode + "'");
						AddUpdateDeleteSilently("delete from `senior two` where payment_code='" + paymentcode + "'");
						AddUpdateDeleteSilently("delete from `senior three` where payment_code='" + paymentcode + "'");
						AddUpdateDeleteSilently("delete from `senior four` where payment_code='" + paymentcode + "'");
						AddUpdateDeleteSilently("delete from `senior five` where payment_code='" + paymentcode + "'");
						AddUpdateDeleteSilently("delete from subjects_offered where payment_code='" + paymentcode + "'");
						AddUpdateDeleteSilently("delete from subjects_offereda where payment_code='" + paymentcode + "'");
						AddUpdateDelete("delete from `senior six` where payment_code='" + paymentcode + "'");
						

					} else {

					}
				} else {
					JOptionPane.showMessageDialog(null, "You Do Not Have Permission To Delete Students");
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

	public void AddUpdateDelete(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Student Deleted Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

	
	public void AddUpdateDeleteSilently(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();


		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

	public void checkUserRightsBeforeSuspending() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + labelUser.getText() + "'");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				if (rs.getInt("suspend students") == 1) {
					
					String[] options = { "Continue", "Cancel" };
					int ans = JOptionPane.showOptionDialog(null, "Are You Sure You Want to Suspend the Selected Student???",
							"Confirmation of Suspension Request", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
					if (ans == 0) {
						
						int rows = tableStudents.getSelectedRow();
						if (rows < 0) {
							JOptionPane.showMessageDialog(null,
									"Please, make sure you have selected a student from the table below");
						} else {
							int row = tableStudents.getSelectedRow();
							String paymentcode = tableStudents.getValueAt(row, 3).toString();
							SuspensionReason suspensionReason = new SuspensionReason();
							suspensionReason.fieldID.setText(paymentcode);
						}
					} else {

					}
				} else {
					JOptionPane.showMessageDialog(null, "You Do Not Have Permission To Suspend Students");
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
	
	
	
	public void checkUserRightsBeforeDiscontinuing() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + labelUser.getText() + "'");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				if (rs.getInt("discontinue students") == 1) {
					
					String[] options = { "Continue", "Cancel" };
					int ans = JOptionPane.showOptionDialog(null, "Are You Sure You Want to Discontinue the Selected Student???",
							"Confirmation of Discontinuation Request", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
					if (ans == 0) {
						
						int rows = tableStudents.getSelectedRow();
						if (rows < 0) {
							JOptionPane.showMessageDialog(null,
									"Please, make sure you have selected a student from the table below");
						} else {
							int row = tableStudents.getSelectedRow();
							String paymentcode = tableStudents.getValueAt(row, 3).toString();

							DiscontinueStudent discontinueStudent = new DiscontinueStudent();
							discontinueStudent.displayInTitle( "select term from `"+labelClass.getText()+"` where payment_code='"+paymentcode+"' order by id desc LIMIT 1");
							discontinueStudent.fieldID.setText(paymentcode);
							discontinueStudent.classLabel.setText(labelClass.getText());

						}
					} else {

					}
				} else {
					JOptionPane.showMessageDialog(null, "You Do Not Have Permission To Discontinue Students");
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
