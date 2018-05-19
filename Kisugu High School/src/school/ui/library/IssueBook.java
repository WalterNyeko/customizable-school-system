package school.ui.library;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

import school.ui.finances.CashBookController;
import school.ui.staff.StaffsPanel;

public class IssueBook extends JPanel {
	java.sql.Connection con = null;
	java.sql.PreparedStatement pst = null;
	java.sql.ResultSet rs = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4503655135916902958L;
	private JLabel labelStudentIDNo;
	private JTextField fieldStudentIDNo;

	private JLabel labelTeacherIDNo;
	private JTextField fieldTeacherIDNo;

	private JLabel labelStudentName;
	private JTextField fieldStudentName;

	private JLabel labelTeacherName;
	private JTextField fieldTeacherName;

	private JLabel labelStudentClass;
	private JTextField fieldStudentClass;

	private JLabel labelTeacherContact;
	private JTextField fieldTeacherContact;

	private JLabel labelPicture;

	private JLabel labelBookIDNo;
	private JTextField fieldBookIDNo;

	private JLabel labelBookTitle;
	public JComboBox fieldBookTitle;

	private JLabel labelBookAuthor;
	private JTextField fieldBookAuthor;

	private JLabel labelIssueDate;
	private JDateChooser dateChooserIssueDate;

	private JLabel labelReturnDate;
	private JDateChooser dateChooserReturnDate;

	private JPanel panelHolder;

	private JPanel panelStudentDetails;
	private JPanel panelBookDetails;
	private JPanel panelTeacherDetails;

	private JButton btnTeachers;
	private JButton btnStudents;
	private JButton btnRecordBookDetails;

	public JTable tableTeachers;
	public JTable tableStudents;

	private JScrollPane scrollPaneTeachers;
	private JScrollPane scrollPaneStudents;

	private JButton btnPrint;

	private JLabel labelTitleTeachers;
	private JLabel labelTitleStudents;

	private JPanel panelPictureHolder;
	private JButton btnIssue;
	private JLabel labelStudentYear;
	private JYearChooser fieldStudentYear;
	protected JFileChooser fileChooser;

	public IssueBook() {
		buildGUI();
	}

	private void buildGUI() {

		panelHolder = new JPanel();
		panelHolder.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelHolder.setSize(550, 400);
		// panelHolder.setBackground(Color.red);
		add(panelHolder);

		panelTeacherDetails = new JPanel();
		Border borderTeacherDetails = BorderFactory.createTitledBorder("Teacher Info");
		panelTeacherDetails.setBorder(borderTeacherDetails);
		panelHolder.add(panelTeacherDetails);

		panelStudentDetails = new JPanel();
		Border borderStudentDetails = BorderFactory.createTitledBorder("Student Info");
		panelStudentDetails.setBorder(borderStudentDetails);
		panelHolder.add(panelStudentDetails);

		panelBookDetails = new JPanel();
		Border borderBookDetails = BorderFactory.createTitledBorder("Book Info");
		panelBookDetails.setBorder(borderBookDetails);
		panelHolder.add(panelBookDetails);

		Dimension dimLabels = new Dimension(100, 25);
		Dimension dimTextFields = new Dimension(200, 25);

		new Font("Times New Roman", Font.BOLD, 16);
		new Font("Times New Roman", Font.BOLD, 14);

		labelTeacherIDNo = new JLabel("Teacher ID Number");
		labelTeacherIDNo.setPreferredSize(dimLabels);
		panelTeacherDetails.add(labelTeacherIDNo);
		fieldTeacherIDNo = new JTextField();
		fieldTeacherIDNo.setPreferredSize(dimTextFields);
		fieldTeacherIDNo.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				fieldStudentIDNo.setText(null);
				fieldStudentName.setText(null);
				fieldStudentClass.setText(null);
				showBorrowerDetailsForStaffs();
				displayTeacherPhoto();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		panelTeacherDetails.add(fieldTeacherIDNo);

		labelStudentYear = new JLabel("Year");
		labelStudentYear.setPreferredSize(dimLabels);
		panelStudentDetails.add(labelStudentYear);
		fieldStudentYear = new JYearChooser();
		fieldStudentYear.setPreferredSize(dimTextFields);
		panelStudentDetails.add(fieldStudentYear);

		labelStudentIDNo = new JLabel("Student Code");
		labelStudentIDNo.setPreferredSize(dimLabels);
		panelStudentDetails.add(labelStudentIDNo);
		fieldStudentIDNo = new JTextField();
		fieldStudentIDNo.setPreferredSize(dimTextFields);
		fieldStudentIDNo.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				fieldTeacherIDNo.setText(null);
				fieldTeacherContact.setText(null);
				fieldTeacherName.setText(null);
				showBorrowerDetailsForStudents();
				displayStudentPhoto();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		panelStudentDetails.add(fieldStudentIDNo);

		labelTeacherName = new JLabel("Teacher Name");
		labelTeacherName.setPreferredSize(dimLabels);
		panelTeacherDetails.add(labelTeacherName);
		fieldTeacherName = new JTextField();
		fieldTeacherName.setPreferredSize(dimTextFields);
		panelTeacherDetails.add(fieldTeacherName);

		labelStudentName = new JLabel("Student Name");
		labelStudentName.setPreferredSize(dimLabels);
		panelStudentDetails.add(labelStudentName);
		fieldStudentName = new JTextField();
		fieldStudentName.setPreferredSize(dimTextFields);
		panelStudentDetails.add(fieldStudentName);

		labelTeacherContact = new JLabel("Contact");
		labelTeacherContact.setPreferredSize(dimLabels);
		panelTeacherDetails.add(labelTeacherContact);
		fieldTeacherContact = new JTextField();
		fieldTeacherContact.setPreferredSize(dimTextFields);
		panelTeacherDetails.add(fieldTeacherContact);

		labelStudentClass = new JLabel("Student Class");
		labelStudentClass.setPreferredSize(dimLabels);
		panelStudentDetails.add(labelStudentClass);
		fieldStudentClass = new JTextField();
		fieldStudentClass.setPreferredSize(dimTextFields);
		panelStudentDetails.add(fieldStudentClass);

		panelPictureHolder = new JPanel();

		labelPicture = new JLabel("Picture");
		labelPicture.setPreferredSize(new Dimension(130, 140));
		labelPicture.setBorder(new LineBorder(Color.white, 2));

		panelPictureHolder.setPreferredSize(new Dimension(150, 180));
		// panelPictureHolder.setBackground(Color.green);
		panelPictureHolder.add(labelPicture);

		btnIssue = new JButton("Issue");
		btnIssue.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnIssue.setPreferredSize(new Dimension(130, 30));
		btnIssue.addActionListener(new ActionListener() {

			// String[] headerTeachers = { "Payment Number", "Name", "Contact", "Book
			// Title", "Book Number", "Author",
			// "Issue Date", "Return date" };

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!fieldStudentIDNo.getText().isEmpty()) {
					AddUpdateDeleteLibraryStuffs("insert into issued_books(payment_code,year,"
							+ "student_name,class_number,book_title,book_id,book_author,issue_date,return_date,teacher_or_student)"
							+ " values('" + fieldStudentIDNo.getText() + "','" + fieldStudentYear.getYear() + "','"
							+ fieldStudentName.getText() + "'," + "'" + fieldStudentClass.getText() + "','"
							+ fieldBookTitle.getSelectedItem() + "','" + fieldBookIDNo.getText() + "'," + "'"
							+ fieldBookAuthor.getText() + "','"
							+ convertFromUtilToSQLDate(dateChooserIssueDate.getDate()) + "'," + "'"
							+ convertFromUtilToSQLDate(dateChooserReturnDate.getDate()) + "','Student')");
					displayData(tableStudents,
							"select payment_code,student_name,class_number,book_title,book_id,book_author,issue_date,"
									+ "return_date,CONCAT(TIMESTAMPDIFF(DAY,issue_date,Now())) from issued_books  where teacher_or_student='Student' and returned=0");

				} else if (!fieldTeacherIDNo.getText().isEmpty()) {
					AddUpdateDeleteLibraryStuffs("insert into issued_books(payment_code,year,"
							+ "student_name,class_number,book_title,book_id,book_author,issue_date,return_date,teacher_or_student)"
							+ " values('" + fieldTeacherIDNo.getText() + "','" + fieldStudentYear.getYear() + "','"
							+ fieldTeacherName.getText() + "'," + "'" + fieldTeacherContact.getText() + "','"
							+ fieldBookTitle.getSelectedItem() + "','" + fieldBookIDNo.getText() + "'," + "'"
							+ fieldBookAuthor.getText() + "','"
							+ convertFromUtilToSQLDate(dateChooserIssueDate.getDate()) + "'," + "'"
							+ convertFromUtilToSQLDate(dateChooserReturnDate.getDate()) + "','Teacher')");
					displayData(tableTeachers,
							"select payment_code,student_name,class_number,book_title,book_id,book_author,issue_date,"
									+ "return_date,CONCAT(TIMESTAMPDIFF(DAY,issue_date,Now())) from issued_books  where teacher_or_student='Teacher' and returned=0");
				}

			}
		});
		panelPictureHolder.add(btnIssue);

		panelHolder.add(panelPictureHolder);

		labelBookIDNo = new JLabel("Book ID Number");
		labelBookIDNo.setPreferredSize(dimLabels);
		panelBookDetails.add(labelBookIDNo);
		fieldBookIDNo = new JTextField();
		fieldBookIDNo.setPreferredSize(dimTextFields);
		panelBookDetails.add(fieldBookIDNo);

		fieldBookIDNo.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

				showTitleAndAuthorIfIDisEntered();
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		labelBookTitle = new JLabel("Title");
		labelBookTitle.setPreferredSize(dimLabels);
		panelBookDetails.add(labelBookTitle);
		fieldBookTitle = new JComboBox();
		fieldBookTitle.setPreferredSize(dimTextFields);
		panelBookDetails.add(fieldBookTitle);

		showBookTitles(fieldBookTitle);

		labelBookAuthor = new JLabel("Author");
		labelBookAuthor.setPreferredSize(dimLabels);
		panelBookDetails.add(labelBookAuthor);
		fieldBookAuthor = new JTextField();
		fieldBookAuthor.setPreferredSize(dimTextFields);
		panelBookDetails.add(fieldBookAuthor);

////		fieldBookTitle.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				// TODO Auto-generated method stub
//				showBookAuthorIfTitleIsSelected(fieldBookAuthor);
//			}
//		});
		Calendar cal = Calendar.getInstance();

		labelIssueDate = new JLabel("Issue Date");
		labelIssueDate.setPreferredSize(dimLabels);
		panelBookDetails.add(labelIssueDate);
		dateChooserIssueDate = new JDateChooser();
		dateChooserIssueDate.setPreferredSize(dimTextFields);
		dateChooserIssueDate.getDate();
		panelBookDetails.add(dateChooserIssueDate);

		dateChooserIssueDate.setDate((cal.getTime()));

		labelReturnDate = new JLabel("Return Date");
		labelReturnDate.setPreferredSize(dimLabels);
		panelBookDetails.add(labelReturnDate);
		dateChooserReturnDate = new JDateChooser();
		dateChooserReturnDate.setPreferredSize(dimTextFields);
		dateChooserReturnDate.getDate();
		panelBookDetails.add(dateChooserReturnDate);

		////////////// tables

		labelTitleTeachers = new JLabel("Teachers Borrowing Records");
		labelTitleTeachers.setFont(new Font("Times New Roman", Font.BOLD, 16));
		add(labelTitleTeachers);
		labelTitleTeachers.setVisible(false);

		labelTitleStudents = new JLabel("Students Borrowing Records");
		labelTitleStudents.setFont(new Font("Times New Roman", Font.BOLD, 16));
		add(labelTitleStudents);

		String[] headerTeachers = { "Payment Number", "Name", "Contact", "Book Title", "Book Number", "Author",
				"Issue Date", "Return date", "Duration Taken" };

		Object[][] dataTeachers = { { null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null } };

		DefaultTableModel modelTeachers = new DefaultTableModel();
		modelTeachers.setDataVector(dataTeachers, headerTeachers);
		tableTeachers = new JTable(modelTeachers) {
			@SuppressWarnings("unused")
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {

				Component c = super.prepareRenderer(renderer, row, column);

				if (!isRowSelected(row)) {
					if (tableTeachers.getColumnCount() >= 0) {
						String days = (String) getModel().getValueAt(row, 8);
						int daystaken = Integer.parseInt(days);
						if (daystaken < 14) {
							c.setBackground(Color.LIGHT_GRAY);
						} else {
							c.setBackground(Color.red);
						}
					}
				}
				return c;

			}
		};
		tableTeachers.setShowGrid(false);
		JTableHeader headerColTeachers = tableTeachers.getTableHeader();
		headerColTeachers.setPreferredSize(new Dimension(1150, 30));
		// headerColTeachers.setBackground(Color.black);
		tableTeachers.setRowHeight(30);

		scrollPaneTeachers = new JScrollPane(tableTeachers);
		scrollPaneTeachers.setPreferredSize(new Dimension(1150, 200));
		add(scrollPaneTeachers);
		scrollPaneTeachers.setVisible(false);

		String[] headerStudents = { "ID Number", "Name", "Class", "Book Title", "Book Number", "Author", "Issue Date",
				"Return date", "Duration Taken" };

		Object[][] dataStudents = { { null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null } };
		DefaultTableModel modelStudents = new DefaultTableModel();
		modelStudents.setDataVector(dataStudents, headerStudents);
		tableStudents = new JTable(modelStudents) {
			@SuppressWarnings("unused")
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {

				Component c = super.prepareRenderer(renderer, row, column);

				if (!isRowSelected(row)) {
					if (tableStudents.getColumnCount() >= 0) {
						String days = (String) getModel().getValueAt(row, 8);
						int daystaken = Integer.parseInt(days);
						if (daystaken < 14) {
							c.setBackground(Color.LIGHT_GRAY);
						} else {
							c.setBackground(Color.red);
						}
					}
				}
				return c;

			}
		};
		JTableHeader headerColStudents = tableStudents.getTableHeader();
		tableStudents.setShowGrid(false);
		headerColStudents.setPreferredSize(new Dimension(1150, 30));
		// headerColStudents.setBackground(Color.black);

		displayData(tableTeachers,
				"select payment_code,student_name,class_number,book_title,book_id,book_author,issue_date,"
						+ "return_date,CONCAT(TIMESTAMPDIFF(DAY,issue_date,Now())) from issued_books where teacher_or_student='Teacher'");
		displayData(tableStudents,
				"select payment_code,student_name,class_number,book_title,book_id,book_author,issue_date,"
						+ "return_date,CONCAT(TIMESTAMPDIFF(DAY,issue_date,Now())) from issued_books  where teacher_or_student='Student'");

		tableStudents.setRowHeight(30);
		scrollPaneStudents = new JScrollPane(tableStudents);
		scrollPaneStudents.setPreferredSize(new Dimension(1150, 200));
		add(scrollPaneStudents);

		btnTeachers = new JButton("Teachers");
		btnTeachers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				scrollPaneStudents.setVisible(false);
				scrollPaneTeachers.setVisible(true);
				labelTitleTeachers.setVisible(true);
				labelTitleStudents.setVisible(false);

				displayData(tableTeachers,
						"select payment_code,student_name,class_number,book_title,book_id,book_author,issue_date,"
								+ "return_date,CONCAT(TIMESTAMPDIFF(DAY,issue_date,Now())) from issued_books where teacher_or_student='Teacher' and returned=0");

			}
		});

		add(btnTeachers);

		btnStudents = new JButton("Students");
		btnStudents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				scrollPaneStudents.setVisible(true);
				scrollPaneTeachers.setVisible(false);
				labelTitleTeachers.setVisible(false);
				labelTitleStudents.setVisible(true);
				displayData(tableStudents,
						"select payment_code,student_name,class_number,book_title,book_id,book_author,issue_date,"
								+ "return_date,CONCAT(TIMESTAMPDIFF(DAY,issue_date,Now())) from issued_books  where teacher_or_student='Student' and returned=0");

			}
		});
		add(btnStudents);

		btnPrint = new JButton("Export To Excel");
		btnPrint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to export this deatils");

				int selected = fileChooser.showSaveDialog(IssueBook.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						if (tableStudents.isVisible()) {
							fillData(tableStudents, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
						} else {
							fillData(tableTeachers, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}
		});
		add(btnPrint);

		// btnRecordBookDetails = new JButton("Record Each Book Details");
		// btnRecordBookDetails.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// RecordEachBookDetail detail = new RecordEachBookDetail();
		// detail.setVisible(true);
		// }
		// });
		//
		// add(btnRecordBookDetails);

	}

	public JPanel getPanelHolder() {
		return panelHolder;
	}

	public JPanel getPanelStudentDetails() {
		return panelStudentDetails;
	}

	public JPanel getPanelBookDetails() {
		return panelBookDetails;
	}

	public JPanel getPanelTeacherDetails() {
		return panelTeacherDetails;
	}

	public void showBorrowerDetailsForStudents() {
		Connection conn = null;
		ResultSet rs = null;
		java.sql.PreparedStatement pst = null;
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from all_students_and_parents where payment_code='" + fieldStudentIDNo.getText() + "'");
			rs = pst.executeQuery();

			if (rs.next()) {
				fieldStudentName.setText(rs.getString("student_name"));
				fieldStudentClass.setText(rs.getString("class_number"));

			} else {
				fieldStudentName.setText(null);
				fieldStudentClass.setText(null);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void showBorrowerDetailsForStaffs() {
		Connection conn = null;
		ResultSet rs = null;
		java.sql.PreparedStatement pst = null;
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement("select * from table_teaching_staffs");
			rs = pst.executeQuery();

			if (rs.next()) {
				fieldTeacherName.setText(rs.getString("staff_name"));
				fieldTeacherContact.setText(rs.getString("staff_contact"));

			} else {
				fieldTeacherName.setText(null);
				fieldTeacherContact.setText(null);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void AddUpdateDeleteLibraryStuffs(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Request Executed Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully: " + ex.getMessage());

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

	public void displayData(JTable table, String query) {

		Connection conn = null;
		java.sql.PreparedStatement pst = null;
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

	public void displayStudentPhoto() {

		try {

			java.sql.Connection conn = CashBookController.getConnection();

			java.sql.PreparedStatement pst = conn.prepareStatement(
					"select photo from students_info where payment_code='" + fieldStudentIDNo.getText() + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				byte[] img = rs.getBytes("photo");
				ImageIcon image = new ImageIcon(img);
				Image im = image.getImage();
				Image im2 = im.getScaledInstance(labelPicture.getWidth(), labelPicture.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon newImage = new ImageIcon(im2);
				labelPicture.setIcon(newImage);

			} else {
				// JOptionPane.showMessageDialog(null, "No Image Found For In The Database",
				// "Lacking Photo In Database",
				// JOptionPane.INFORMATION_MESSAGE);
				labelPicture.setIcon(null);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void displayTeacherPhoto() {

		try {

			java.sql.Connection conn = CashBookController.getConnection();

			java.sql.PreparedStatement pst = conn.prepareStatement(
					"select photo from staffs_photos where staff_id='" + fieldTeacherIDNo.getText() + "'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				byte[] img = rs.getBytes("photo");
				ImageIcon image = new ImageIcon(img);
				Image im = image.getImage();
				Image im2 = im.getScaledInstance(labelPicture.getWidth(), labelPicture.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon newImage = new ImageIcon(im2);
				labelPicture.setIcon(newImage);

			} else {
				// JOptionPane.showMessageDialog(null, "No Image Found For In The Database",
				// "Lacking Photo In Database",
				// JOptionPane.INFORMATION_MESSAGE);

				labelPicture.setIcon(null);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void showBookTitles(JComboBox combo) {

		String sql = "select bookTitle from generaloverviewofbooks group by bookTitle";
		try {
			con = CashBookController.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			combo.removeAllItems();
			combo.removeAll();
			combo.addItem("Choose Book Title");
			while (rs.next()) {
				combo.addItem(rs.getString(1));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void showBookAuthorIfTitleIsSelected(JTextField field) {

		String sql = "select author from generaloverviewofbooks where bookTitle='" + fieldBookTitle.getSelectedItem()
				+ "'";
		try {
			con = CashBookController.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next()) {
				field.setText(rs.getString(1));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void showTitleAndAuthorIfIDisEntered() {

		String sql = "select generaloverviewofbooks.bookTitle,generaloverviewofbooks.author from generaloverviewofbooks,"
				+ "issued_books where issued_books.book_id='" + fieldBookIDNo.getText()
				+ "' and generaloverviewofbooks.bookTitle=issued_books.book_title";
		try {
			con = CashBookController.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				fieldBookTitle.setSelectedItem(rs.getString(1));
				fieldBookAuthor.setText(rs.getString(2));
			} else {
				fieldBookTitle.setSelectedIndex(0);
				fieldBookAuthor.setText(null);

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void fillData(JTable table, java.io.File file) {

		try {

			XSSFWorkbook workbook1 = new XSSFWorkbook();

			XSSFSheet fSheet = workbook1.createSheet("Data Sheet");

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

}
