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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

import com.mysql.fabric.xmlrpc.base.Data;
import com.toedter.calendar.JDateChooser;

import school.ui.finances.CashBookController;

public class ReturnBook extends JPanel {
	Connection conn = null;
	ResultSet rs = null;
	java.sql.PreparedStatement pst = null;
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
	private JTextField fieldBookTitle;

	private JLabel labelBookAuthor;
	private JTextField fieldBookAuthor;

	private JLabel labelReturnDateExpected;
	private JTextField fieldReturnDateExpected;

	private JPanel panelHolder;

	private JPanel panelStudentDetails;
	private JPanel panelBookDetails;
	private JPanel panelTeacherDetails;

	private JButton btnTeachers;
	private JButton btnStudents;

	private JTable tableTeachers;
	private JTable tableStudents;

	private JScrollPane scrollPaneTeachers;
	private JScrollPane scrollPaneStudents;

	private JButton btnPrint;

	private JLabel labelTitleTeachers;
	private JLabel labelTitleStudents;

	private JPanel panelPictureHolder;
	private JButton btnReturn;
	private JLabel labelBook;
	private JLabel labelBookTotal;
	protected JFileChooser fileChooser;

	public ReturnBook() {
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

		Font fontlabels = new Font("Times New Roman", Font.BOLD, 16);
		Font fontFieldText = new Font("Times New Roman", Font.BOLD, 14);

		labelTeacherIDNo = new JLabel("Teacher ID No");
		labelTeacherIDNo.setPreferredSize(dimLabels);
		panelTeacherDetails.add(labelTeacherIDNo);
		fieldTeacherIDNo = new JTextField();
		fieldTeacherIDNo.setPreferredSize(dimTextFields);
		panelTeacherDetails.add(fieldTeacherIDNo);
		fieldTeacherIDNo.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				displayTeacherPhoto();
				showTeacherDetails();
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		fieldTeacherIDNo.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				displayCountsofUnreturnedBooksTeachers();
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		labelStudentIDNo = new JLabel("Student Code");
		labelStudentIDNo.setPreferredSize(dimLabels);
		panelStudentDetails.add(labelStudentIDNo);
		fieldStudentIDNo = new JTextField();
		fieldStudentIDNo.setPreferredSize(dimTextFields);
		panelStudentDetails.add(fieldStudentIDNo);
		fieldStudentIDNo.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				displayStudentPhoto();
				showStudentDetails();
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		fieldStudentIDNo.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				displayCountsofUnreturnedBooksStudents();
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		labelTeacherName = new JLabel("Teacher Name");
		labelTeacherName.setPreferredSize(dimLabels);
		panelTeacherDetails.add(labelTeacherName);
		fieldTeacherName = new JTextField();
		fieldTeacherName.setEditable(false);
		fieldTeacherName.setPreferredSize(dimTextFields);
		panelTeacherDetails.add(fieldTeacherName);

		labelStudentName = new JLabel("Student Name");
		labelStudentName.setPreferredSize(dimLabels);
		panelStudentDetails.add(labelStudentName);
		fieldStudentName = new JTextField();
		fieldStudentName.setEditable(false);
		fieldStudentName.setPreferredSize(dimTextFields);
		panelStudentDetails.add(fieldStudentName);

		labelTeacherContact = new JLabel("Contact");
		labelTeacherContact.setPreferredSize(dimLabels);
		panelTeacherDetails.add(labelTeacherContact);
		fieldTeacherContact = new JTextField();
		fieldTeacherContact.setEditable(false);
		fieldTeacherContact.setPreferredSize(dimTextFields);
		panelTeacherDetails.add(fieldTeacherContact);

		labelStudentClass = new JLabel("Class");
		labelStudentClass.setPreferredSize(dimLabels);
		panelStudentDetails.add(labelStudentClass);
		fieldStudentClass = new JTextField();
		fieldStudentClass.setEditable(false);
		fieldStudentClass.setPreferredSize(dimTextFields);
		panelStudentDetails.add(fieldStudentClass);

		panelPictureHolder = new JPanel();

		labelPicture = new JLabel("Picture");
		labelPicture.setPreferredSize(new Dimension(130, 140));
		labelPicture.setBorder(new LineBorder(Color.white, 2));

		panelPictureHolder.setPreferredSize(new Dimension(150, 180));
		// panelPictureHolder.setBackground(Color.green);
		panelPictureHolder.add(labelPicture);

		
		Calendar cal=Calendar.getInstance();
		
		btnReturn = new JButton("Return");
		btnReturn.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnReturn.setPreferredSize(new Dimension(130, 30));
		panelPictureHolder.add(btnReturn);
		btnReturn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (fieldTeacherIDNo.getText().isEmpty()) {
					new CashBookController().updateBookReturns(
							"update issued_books set returned=1,actual_return='"+convertFromUtilToSQLDate(cal.getTime())+"' " + "where payment_code='" + fieldStudentIDNo.getText()
									+ "' and book_id='" + fieldBookIDNo.getText() + "' and returned=0");
					displayData(tableStudents, "select payment_code,student_name,class_number,book_title,book_id,book_author,"
							+ "return_date,actual_return from issued_books where returned=1 and teacher_or_student='Student'");
				
				} else {
					new CashBookController().updateBookReturns(
							"update issued_books set returned=1,actual_return='"+convertFromUtilToSQLDate(cal.getTime())+"' " + "where payment_code='" + fieldTeacherIDNo.getText()
									+ "' and book_id='" + fieldBookIDNo.getText() + "' and returned=0");
					displayData(tableTeachers, "select payment_code,student_name,class_number,book_title,book_id,book_author,"
							+ "return_date,actual_return from issued_books where returned=1 and teacher_or_student='Teacher'");
				}

			}
		});

		panelHolder.add(panelPictureHolder);

		labelBookIDNo = new JLabel("Book ID No");
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
				checkIfTeacherOrStudent();
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		labelBookTitle = new JLabel("Title");
		labelBookTitle.setPreferredSize(dimLabels);
		panelBookDetails.add(labelBookTitle);
		fieldBookTitle = new JTextField();
		fieldBookTitle.setEditable(false);
		fieldBookTitle.setPreferredSize(dimTextFields);
		panelBookDetails.add(fieldBookTitle);

		labelBookAuthor = new JLabel("Author");
		labelBookAuthor.setPreferredSize(dimLabels);
		panelBookDetails.add(labelBookAuthor);
		fieldBookAuthor = new JTextField();
		fieldBookAuthor.setEditable(false);
		fieldBookAuthor.setPreferredSize(dimTextFields);
		panelBookDetails.add(fieldBookAuthor);

		labelReturnDateExpected = new JLabel("Expected Return");
		labelReturnDateExpected.setPreferredSize(dimLabels);
		panelBookDetails.add(labelReturnDateExpected);
		fieldReturnDateExpected = new JTextField();
		fieldReturnDateExpected.setEditable(false);
		fieldReturnDateExpected.setPreferredSize(dimTextFields);
		panelBookDetails.add(fieldReturnDateExpected);

		labelBook = new JLabel("Unreturned:");
		labelBook.setPreferredSize(dimLabels);
		labelBook.setForeground(Color.blue);
		labelBook.setFont(new Font("Times New Roman", Font.BOLD, 18));
		panelBookDetails.add(labelBook);

		labelBookTotal = new JLabel("");
		labelBookTotal.setPreferredSize(dimLabels);
		labelBookTotal.setForeground(Color.red);
		labelBookTotal.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panelBookDetails.add(labelBookTotal);

		////////////// tables

		labelTitleTeachers = new JLabel("Teachers Return Records");
		labelTitleTeachers.setFont(new Font("Times New Roman", Font.BOLD, 16));
		add(labelTitleTeachers);
		labelTitleTeachers.setVisible(false);

		labelTitleStudents = new JLabel("Students Return Records");
		labelTitleStudents.setFont(new Font("Times New Roman", Font.BOLD, 16));
		add(labelTitleStudents);

		String[] headerTeachers = { "ID Number", "Name", "Contact", "Book Title", "Book ID", "Author",
				"Return Date Expected", "Return Date Exact" };

		Object[][] dataTeachers =  { { null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null} };

		DefaultTableModel modelTr=new DefaultTableModel();
		modelTr.setDataVector(dataTeachers, headerTeachers);
		tableTeachers = new JTable(modelTr);
		JTableHeader headerColBooks = tableTeachers.getTableHeader();
		headerColBooks.setPreferredSize(new Dimension(1150, 30));
		// headerColBooks.setBackground(Color.black);

		tableTeachers.setRowHeight(30);
		displayData(tableTeachers, "select payment_code,student_name,class_number,book_title,book_id,book_author,"
				+ "return_date,actual_return from issued_books where returned=1 and teacher_or_student='Teacher'");

		scrollPaneTeachers = new JScrollPane(tableTeachers);
		scrollPaneTeachers.setPreferredSize(new Dimension(1150, 200));
		add(scrollPaneTeachers);
		scrollPaneTeachers.setVisible(false);

		String[] headerStudents = { "ID Number", "Name", "Class", "Book Title", "Book ID", "Author",
				"Return Date Expected", "Return Date Exact" };

		Object[][] dataStudents = { { null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null},
				{ null, null, null, null, null, null, null, null} };

		DefaultTableModel modelSt=new DefaultTableModel();
		modelSt.setDataVector(dataStudents, headerStudents);
		tableStudents = new JTable(modelSt);
		JTableHeader headerColStudent = tableStudents.getTableHeader();
		headerColStudent.setPreferredSize(new Dimension(1150, 30));
		// headerColBooks.setBackground(Color.black);

		tableStudents.setRowHeight(30);
		displayData(tableStudents, "select payment_code,student_name,class_number,book_title,book_id,book_author,"
				+ "return_date,actual_return from issued_books where returned=1 and teacher_or_student='Student'");

		tableStudents.setRowHeight(30);
		scrollPaneStudents = new JScrollPane(tableStudents);
		scrollPaneStudents.setPreferredSize(new Dimension(1150, 200));
		add(scrollPaneStudents);

		btnTeachers = new JButton("Teachers");
		btnTeachers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				scrollPaneStudents.setVisible(false);
				scrollPaneTeachers.setVisible(true);
				labelTitleTeachers.setVisible(true);
				labelTitleStudents.setVisible(false);
			}
		});
		add(btnTeachers);

		btnStudents = new JButton("Students");
		btnStudents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				scrollPaneStudents.setVisible(true);
				scrollPaneTeachers.setVisible(false);
				labelTitleTeachers.setVisible(false);
				labelTitleStudents.setVisible(true);
			}
		});
		add(btnStudents);

		btnPrint = new JButton("Export to Excel");
		btnPrint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to export this deatils");

				int selected = fileChooser.showSaveDialog(ReturnBook.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						if(tableStudents.isVisible()) {
							fillData(tableStudents, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
						}else {
							fillData(tableTeachers, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}
		});
		add(btnPrint);

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
					"select * from issued_books where payment_code='" + fieldStudentIDNo.getText() + "'");
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

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from issued_books where payment_code='" + fieldStudentIDNo.getText() + "'");
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

	public void checkIfTeacherOrStudent() {

		String sql = "select * from issued_books where returned=0 and book_id='" + fieldBookIDNo.getText() + "'";
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getString("teacher_or_student").equals("Student")) {
					fieldTeacherContact.setText(null);
					fieldTeacherName.setText(null);
					fieldTeacherIDNo.setText(null);

					fieldStudentName.setText(rs.getString("student_name"));
					fieldBookTitle.setText(rs.getString("book_title"));
					fieldBookAuthor.setText(rs.getString("book_author"));
					fieldReturnDateExpected.setText(rs.getString("return_date"));

					fieldStudentClass.setText(rs.getString("class_number"));
					fieldStudentIDNo.setText(rs.getString("payment_code"));

				} else {
					fieldStudentClass.setText(null);
					fieldStudentIDNo.setText(null);
					fieldStudentName.setText(null);
					fieldBookTitle.setText(rs.getString("book_title"));
					fieldBookAuthor.setText(rs.getString("book_author"));
					fieldReturnDateExpected.setText(rs.getString("return_date"));

					fieldTeacherContact.setText(rs.getString("class_number"));
					fieldTeacherName.setText(rs.getString("student_name"));
					fieldTeacherIDNo.setText(rs.getString("payment_code"));

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void showStudentDetails() {

		String sql = "select * from issued_books where returned=0 and payment_code='" + fieldStudentIDNo.getText() + "'";
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				fieldTeacherContact.setText(null);
				fieldTeacherName.setText(null);
				fieldTeacherIDNo.setText(null);

				fieldStudentName.setText(rs.getString("student_name"));
				fieldBookTitle.setText(rs.getString("book_title"));
				fieldBookAuthor.setText(rs.getString("book_author"));
				fieldReturnDateExpected.setText(rs.getString("return_date"));
				fieldBookIDNo.setText(rs.getString("book_id"));
				fieldStudentClass.setText(rs.getString("class_number"));
				fieldStudentIDNo.setText(rs.getString("payment_code"));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void showTeacherDetails() {

		String sql = "select * from issued_books where returned=0 and payment_code='" + fieldTeacherIDNo.getText() + "'";
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				fieldStudentClass.setText(null);
				fieldStudentIDNo.setText(null);
				fieldStudentName.setText(null);
				fieldBookTitle.setText(rs.getString("book_title"));
				fieldBookAuthor.setText(rs.getString("book_author"));
				fieldReturnDateExpected.setText(rs.getString("return_date"));
				fieldBookIDNo.setText(rs.getString("book_id"));
				fieldTeacherContact.setText(rs.getString("class_number"));
				fieldTeacherName.setText(rs.getString("student_name"));
				fieldTeacherIDNo.setText(rs.getString("payment_code"));

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void displayCountsofUnreturnedBooksStudents() {
		String sql = "select count(payment_code) from issued_books where payment_code='" + fieldStudentIDNo.getText()
				+ "' and returned=0";
		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				labelBookTotal.setText(rs.getString(1) + " Book(s)");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void displayCountsofUnreturnedBooksTeachers() {
		String sql = "select count(payment_code) from issued_books where payment_code='" + fieldTeacherIDNo.getText()
				+ "' and returned=0";
		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				labelBookTotal.setText(rs.getString(1) + " Book(s)");
			}

		} catch (Exception e) {
			// TODO: handle exception
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
	
	public Date convertFromUtilToSQLDate(java.util.Date dateUtil) {

		if (dateUtil != null) {
			java.sql.Date sqlDate = new java.sql.Date(dateUtil.getTime());

			return sqlDate;
		} else {
			return null;
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
			FileOutputStream fos =new FileOutputStream(new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
			workbook1.write(fos);
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}
}
