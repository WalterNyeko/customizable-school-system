package clarion.ui.dispensary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
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

import clarion.controller.dispensary.DiagosisAndTreatmentController;
import clarion.model.dispensary.DiagnosisAndTreatment;
import javafx.scene.control.TextField;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import school.ui.finances.CashBookController;
import school.ui.finances.FinanceAnalyticalCashBook;

public class DispensaryPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Components to pick student's information from the dispensary table in the
	 * database.
	 * 
	 * The dispensary table records every student's health record only once i.e at
	 * the time of admission
	 */

	private JLabel labelStudentName;
	private JLabel labelStudentRegistrationNumber;
	private JLabel labelStudentClass;
	private JLabel labelStudentYearOfAdmission;
	private JLabel labelStudentdormitory;

	private JLabel labelFatherName;
	private JLabel labelFatherPhone;
	private JLabel labelFatherAddress;

	private JLabel labelMotherName;
	private JLabel labelMotherPhone;

	private JLabel labelSponsorName;
	private JLabel labelSponsorPhone;

	private JTextField fieldStudentName;
	private JTextField fieldStudentRegistrationNumber;
	private JTextField fieldStudentClass;
	private JTextField fieldStudentYearOfAdmission;
	private JTextField fieldStudentdormitory;

	private JTextField fieldFatherName;
	private JTextField fieldFatherPhone;
	private JTextField fieldFatherAddress;

	private JTextField fieldMotherName;
	private JTextField fieldMotherPhone;

	private JTextField fieldSponsorName;
	private JTextField fieldSponsorPhone;

	private JLabel labelSpecialCase;
	private JTextField fieldSpecialCase;

	/*
	 * Button to show a pop up that will record the necessary diagnosis and
	 * treatment taken
	 */
	private JButton btnRecordDiagosisOrTreatment;
	private JButton btnClear;
	private JButton btnPrint;
	private JButton btnExportToExcel;
	private JButton btnViewAllStudentRecords;

	/*
	 * Pop up dialog that show when the Button (btnRecordDiagosisOrTreatment) is
	 * clicked
	 * 
	 * It contains the necessary info reception fields
	 */

	/*
	 * Panel to hold student info excluding parents and sponsors
	 */
	private JPanel panelStudentInfo;

	/*
	 * Panel to hold parents info only
	 */
	private JPanel panelParentsInfo;

	/*
	 * Panel to hold sponsors info and the buttons for the necessary dispensary
	 * actions
	 */
	private JPanel panelSponsorsInfo;

	/*
	 * Table to hold the four columns required by the dispensary personnel
	 * 
	 * i.e Date, Medical Histroy, Provisional Diagnosis, Treatment/Action Taken
	 */

	private JTable tableDispenary;
	private JScrollPane scrollPaneDispensary;
	private JPanel panelTableToHoldDispensaryRecordPerStudent;

	/*
	 * Dialog to receive record details
	 */

	private JDialog dialogDispensary;

	/*
	 * Components in the dialog
	 */

	private JLabel labelDate;

	private JDateChooser dateChooser;
	private JTextArea areaProvisionalDiagnosis;
	private JTextArea areaTreatmentTaken;
	private JTextArea fieldDiagnosisResult;
	private JScrollPane scrollPaneProvisionalDiagnosis;
	private JScrollPane scrollPaneTreatmentTaken;
	private JButton btnSave;
	private JButton btnCancel;

	// private DispensaryTableModel tableModel;
	// private DiagosisAndTreatmentController controller;

	private JScrollPane scrollPaneResult;

	private JDateChooser dateChooserOnPanel;

	private Connection conn;

	private PreparedStatement pst;

	private JFileChooser fileChooser;

	public DispensaryPanel() throws SQLException {

		/*
		 * This panel just below holds the labels and fields that take the student class
		 * number through which all other required previously recorded records will fill
		 * automatically in the rest of the fields
		 */

		setBorder(new LineBorder(Color.blue, 3));
		setPreferredSize(new Dimension(1160, 480));
		setBackground(Color.decode("#5f9ea0"));

		panelStudentInfo = new JPanel();
		panelStudentInfo.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelStudentInfo.setPreferredSize(new Dimension(1160, 90));
		panelStudentInfo.setBackground(Color.decode("#5f9ea0"));
		add(panelStudentInfo);

		Dimension dimensionStudentInfoLabel = new Dimension(155, 25);

		Dimension dimensionStudentInfoFields = new Dimension(155, 25);

		Dimension dimensionStudentInfoLabels2018 = new Dimension(195, 25);

		Dimension dimensionStudentInfoFields2018 = new Dimension(195, 25);

		Dimension dimensionButtons = new Dimension(120, 25);

		JLabel date = new JLabel("   Date");
		date.setPreferredSize(new Dimension(90, 25));
		panelStudentInfo.add(date);

		labelStudentRegistrationNumber = new JLabel("     Student Payment Code:");
		labelStudentRegistrationNumber.setPreferredSize(dimensionStudentInfoLabels2018);
		panelStudentInfo.add(labelStudentRegistrationNumber);

		labelStudentName = new JLabel("               Student Name:");
		labelStudentName.setPreferredSize(dimensionStudentInfoLabels2018);
		panelStudentInfo.add(labelStudentName);

		labelStudentClass = new JLabel("               Class:");
		labelStudentClass.setPreferredSize(dimensionStudentInfoLabels2018);
		panelStudentInfo.add(labelStudentClass);

		labelStudentdormitory = new JLabel("             Dormitory:");
		labelStudentdormitory.setPreferredSize(dimensionStudentInfoLabels2018);
		panelStudentInfo.add(labelStudentdormitory);

		labelStudentYearOfAdmission = new JLabel("          Year Of Admission:");
		labelStudentYearOfAdmission.setPreferredSize(dimensionStudentInfoLabels2018);
		panelStudentInfo.add(labelStudentYearOfAdmission);

		dateChooserOnPanel = new JDateChooser();
		panelStudentInfo.add(dateChooserOnPanel);
		dateChooserOnPanel.setPreferredSize(new Dimension(120, 25));

		dateChooser = new JDateChooser();
		dateChooser.setPreferredSize(new Dimension(120, 25));

		fieldStudentRegistrationNumber = new JTextField();
		fieldStudentRegistrationNumber.setPreferredSize(dimensionStudentInfoFields2018);
		panelStudentInfo.add(fieldStudentRegistrationNumber);

		fieldStudentName = new JTextField();
		fieldStudentName.setPreferredSize(dimensionStudentInfoFields2018);
		panelStudentInfo.add(fieldStudentName);

		fieldStudentClass = new JTextField();
		fieldStudentClass.setPreferredSize(dimensionStudentInfoFields2018);
		panelStudentInfo.add(fieldStudentClass);

		fieldStudentdormitory = new JTextField();
		fieldStudentdormitory.setPreferredSize(dimensionStudentInfoFields2018);
		panelStudentInfo.add(fieldStudentdormitory);

		fieldStudentYearOfAdmission = new JTextField();
		fieldStudentYearOfAdmission.setPreferredSize(dimensionStudentInfoFields2018);
		panelStudentInfo.add(fieldStudentYearOfAdmission);
		panelStudentInfo.setBorder(new TitledBorder("Students Info"));

		/*
		 * The panelStudentInfo ends here
		 */

		/*
		 * The panelParentsInfo starts here.
		 */

		panelParentsInfo = new JPanel();
		panelParentsInfo.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelParentsInfo.setPreferredSize(new Dimension(1160, 90));

		panelParentsInfo.setBorder(new TitledBorder("Parents Info"));
		//
		panelParentsInfo.setBackground(Color.decode("#5f9ea0"));
		add(panelParentsInfo);

		labelFatherName = new JLabel("    Father's/Guardian's Name:");
		labelFatherName.setPreferredSize(dimensionStudentInfoLabel);
		panelParentsInfo.add(labelFatherName);

		labelFatherPhone = new JLabel("   Father's/Guardian's Phone:");
		labelFatherPhone.setPreferredSize(dimensionStudentInfoLabel);
		panelParentsInfo.add(labelFatherPhone);

		labelFatherAddress = new JLabel("  Father's/Guardian's Address:");
		labelFatherAddress.setPreferredSize(dimensionStudentInfoLabel);
		panelParentsInfo.add(labelFatherAddress);

		labelMotherName = new JLabel("     Mother's Name:");
		labelMotherName.setPreferredSize(dimensionStudentInfoLabel);
		panelParentsInfo.add(labelMotherName);

		labelMotherPhone = new JLabel("     Mother's Phone:");
		labelMotherPhone.setPreferredSize(dimensionStudentInfoLabel);
		panelParentsInfo.add(labelMotherPhone);

		labelSponsorName = new JLabel("Sponsor Name:");
		labelSponsorName.setPreferredSize(dimensionStudentInfoLabel);
		panelParentsInfo.add(labelSponsorName);

		labelSponsorPhone = new JLabel("Sponsor Phone:");
		labelSponsorPhone.setPreferredSize(dimensionStudentInfoLabel);
		panelParentsInfo.add(labelSponsorPhone);

		fieldFatherName = new JTextField();
		fieldFatherName.setPreferredSize(dimensionStudentInfoFields);
		panelParentsInfo.add(fieldFatherName);

		fieldFatherPhone = new JTextField();
		fieldFatherPhone.setPreferredSize(dimensionStudentInfoFields);
		panelParentsInfo.add(fieldFatherPhone);

		fieldFatherAddress = new JTextField();
		fieldFatherAddress.setPreferredSize(dimensionStudentInfoFields);
		panelParentsInfo.add(fieldFatherAddress);

		fieldMotherName = new JTextField();
		fieldMotherName.setPreferredSize(dimensionStudentInfoFields);
		panelParentsInfo.add(fieldMotherName);

		fieldMotherPhone = new JTextField();
		fieldMotherPhone.setPreferredSize(dimensionStudentInfoFields);
		panelParentsInfo.add(fieldMotherPhone);

		fieldSponsorName = new JTextField();
		fieldSponsorName.setPreferredSize(dimensionStudentInfoFields);
		panelParentsInfo.add(fieldSponsorName);

		fieldSponsorPhone = new JTextField();
		fieldSponsorPhone.setPreferredSize(dimensionStudentInfoFields);
		panelParentsInfo.add(fieldSponsorPhone);

		/*
		 * panelParentsInfo ends here
		 */

		/*
		 * panelSponsorsInfo starts here
		 */
		panelSponsorsInfo = new JPanel();
		panelSponsorsInfo.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSponsorsInfo.setPreferredSize(new Dimension(1160, 55));
		 panelSponsorsInfo.setBackground(Color.decode("#5f9ea0"));
		panelSponsorsInfo.setBorder(new TitledBorder("Buttons"));
		add(panelSponsorsInfo);

		btnRecordDiagosisOrTreatment = new JButton("New Record");
		btnRecordDiagosisOrTreatment.setPreferredSize(dimensionButtons);
		panelSponsorsInfo.add(btnRecordDiagosisOrTreatment);

		btnClear = new JButton("Clear Fields");
		btnClear.setPreferredSize(dimensionButtons);
		panelSponsorsInfo.add(btnClear);
		btnClear.addActionListener(e -> {
			clearFields();
		});

		btnExportToExcel = new JButton("Export To Excel");
		btnExportToExcel.setPreferredSize(dimensionButtons);
		panelSponsorsInfo.add(btnExportToExcel);
		btnExportToExcel.addActionListener(e -> {

			fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specify name and folder to export this deatils");

			int selected = fileChooser.showSaveDialog(DispensaryPanel.this);

			if (selected == JFileChooser.APPROVE_OPTION) {
				try {

					fillData1(tableDispenary, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		});

		btnPrint = new JButton("Export To Excel");
		btnPrint.setPreferredSize(dimensionButtons);
		panelSponsorsInfo.add(btnPrint);

		btnViewAllStudentRecords = new JButton("View All Students");
		btnViewAllStudentRecords.setPreferredSize(new Dimension(155, 25));
		panelSponsorsInfo.add(btnViewAllStudentRecords);

		// JLabel labelToPushSpecialCase = new JLabel();
		// labelToPushSpecialCase.setForeground(Color.green);
		// labelToPushSpecialCase.setPreferredSize(new Dimension(255, 25));
		// panelSponsorsInfo.add(labelToPushSpecialCase);

		labelSpecialCase = new JLabel("Special Case");
		// labelSpecialCase.setPreferredSize(dimensionStudentInfoLabel);
		panelSponsorsInfo.add(labelSpecialCase);

		fieldSpecialCase = new JTextField();
		fieldSpecialCase.setPreferredSize(new Dimension(370, 25));
		fieldSpecialCase.setEditable(false);
		panelSponsorsInfo.add(fieldSpecialCase);

		/*
		 * panelTableToHoldDispensaryRecordPerStudent starts here
		 */

		panelTableToHoldDispensaryRecordPerStudent = new JPanel();
		panelTableToHoldDispensaryRecordPerStudent.setPreferredSize(new Dimension(1160, 225));
		// panelTableToHoldDispensaryRecordPerStudent.setBackground(Color.red);
		add(panelTableToHoldDispensaryRecordPerStudent);

		List<DiagnosisAndTreatment> theTreatments = null;

		DiagosisAndTreatmentController controller = null;

		try {
			controller = new DiagosisAndTreatmentController();
			theTreatments = controller.getDispensaryRecords();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		DispensaryTableModel tableModel = new DispensaryTableModel(theTreatments);

		tableDispenary = new JTable();
		tableDispenary.setModel(tableModel);

		scrollPaneDispensary = new JScrollPane();
		scrollPaneDispensary.setViewportView(tableDispenary);
		scrollPaneDispensary.setPreferredSize(new Dimension(1155, 220));
		scrollPaneDispensary.setBorder(new LineBorder(Color.white, 3));
		panelTableToHoldDispensaryRecordPerStudent.add(scrollPaneDispensary);

		/*
		 * filling up the fields on class number entry
		 */

		fieldStudentRegistrationNumber.addKeyListener(new KeyListener() {

			DiagosisAndTreatmentController controller = new DiagosisAndTreatmentController();

			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {

				try {

					if (!fieldStudentRegistrationNumber.getText().trim().isEmpty()) {
						controller.dispensaryClassNumberToFillFields(fieldStudentRegistrationNumber.getText());

						/*
						 * Filling up the student info fields
						 */

						fieldStudentName.setText(
								controller.dispensaryClassNumberToFillFields(fieldStudentRegistrationNumber.getText())
										.getFirstName());

						fieldStudentClass.setText(
								controller.dispensaryClassNumberToFillFields(fieldStudentRegistrationNumber.getText())
										.getStudentClass());

						fieldStudentdormitory.setText(
								controller.dispensaryClassNumberToFillFields(fieldStudentRegistrationNumber.getText())
										.getDormitory());
						fieldStudentYearOfAdmission.setText(
								controller.dispensaryClassNumberToFillFields(fieldStudentRegistrationNumber.getText())
										.getYearOfAdmission());

						fieldSpecialCase.setText(
								controller.dispensaryClassNumberToFillFields(fieldStudentRegistrationNumber.getText())
										.getSpecialCase());

						/*
						 * Filling up the parent info fields
						 */

						fieldFatherName.setText(
								controller.parentInfofields(fieldStudentRegistrationNumber.getText()).getFathersName());

						fieldFatherPhone.setText(controller.parentInfofields(fieldStudentRegistrationNumber.getText())
								.getFathersPhone());

						fieldFatherAddress.setText(controller.parentInfofields(fieldStudentRegistrationNumber.getText())
								.getParentsAddress());

						fieldMotherName.setText(
								controller.parentInfofields(fieldStudentRegistrationNumber.getText()).getMothersName());

						fieldMotherPhone.setText(controller.parentInfofields(fieldStudentRegistrationNumber.getText())
								.getMothersPhone());

						fieldSponsorName.setText(controller.parentInfofields(fieldStudentRegistrationNumber.getText())
								.getSponsorsName());

						fieldSponsorPhone.setText(controller.parentInfofields(fieldStudentRegistrationNumber.getText())
								.getSponsorPhone());

						List<DiagnosisAndTreatment> theTreatments = null;

						DiagosisAndTreatmentController controller = null;

						controller = new DiagosisAndTreatmentController();

						try {
							theTreatments = controller.getDispensaryRecordsForParticularIDNumber(
									fieldStudentRegistrationNumber.getText());
							displayInComboBox(fieldSpecialCase,
									"select special_sickness from dispensary where payment_code='"
											+ fieldStudentRegistrationNumber.getText() + "'");

						} catch (SQLException e1) {
							e1.printStackTrace();
						}

						DispensaryTableModel tableModel = new DispensaryTableModel(theTreatments);
						tableDispenary.setModel(tableModel);

					} else {
						controller.dispensaryClassNumberToFillFields(fieldStudentRegistrationNumber.getText());

						clearFields();

						List<DiagnosisAndTreatment> theTreatments = null;

						DiagosisAndTreatmentController controller = null;

						controller = new DiagosisAndTreatmentController();

						try {
							theTreatments = controller.getDispensaryRecords();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

						DispensaryTableModel tableModel = new DispensaryTableModel(theTreatments);
						tableDispenary.setModel(tableModel);

					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		/*
		 * The diagnosis dialog starts here
		 */

		dialogDispensary = new JDialog();
		dialogDispensary.setResizable(false);
		dialogDispensary.setTitle("Student Diagosis Details");
		dialogDispensary.setSize(255, 650);
		dialogDispensary.setLayout(new FlowLayout(FlowLayout.CENTER));
		dialogDispensary.setLocationRelativeTo(null);

		labelDate = new JLabel("Date:");
		dialogDispensary.add(labelDate);

		dateChooser = new JDateChooser();
		dateChooser.setEnabled(false);
		dateChooser.setPreferredSize(new Dimension(160, 25));
		dialogDispensary.add(dateChooser);

		areaProvisionalDiagnosis = new JTextArea(10, 20);
		areaProvisionalDiagnosis.setWrapStyleWord(true);
		areaProvisionalDiagnosis.setLineWrap(true);
		scrollPaneProvisionalDiagnosis = new JScrollPane();
		scrollPaneProvisionalDiagnosis.setBorder(new TitledBorder(new LineBorder(Color.white, 3), "Diagnosis"));
		scrollPaneProvisionalDiagnosis.setViewportView(areaProvisionalDiagnosis);
		dialogDispensary.add(scrollPaneProvisionalDiagnosis);

		areaTreatmentTaken = new JTextArea(10, 20);
		areaTreatmentTaken.setWrapStyleWord(true);
		areaTreatmentTaken.setLineWrap(true);
		scrollPaneTreatmentTaken = new JScrollPane();
		scrollPaneTreatmentTaken.setBorder(new TitledBorder(new LineBorder(Color.white, 3), "Treatment Taken"));
		scrollPaneTreatmentTaken.setViewportView(areaTreatmentTaken);
		dialogDispensary.add(scrollPaneTreatmentTaken);

		fieldDiagnosisResult = new JTextArea(4, 20);
		fieldDiagnosisResult.setLineWrap(true);
		fieldDiagnosisResult.setWrapStyleWord(true);
		scrollPaneResult = new JScrollPane();
		scrollPaneResult.setViewportView(fieldDiagnosisResult);
		scrollPaneResult.setBorder(new TitledBorder(new LineBorder(Color.white, 3), "Diagnosis Result"));
		dialogDispensary.add(scrollPaneResult);

		btnSave = new JButton("Save");
		btnSave.setPreferredSize(dimensionButtons);
		dialogDispensary.add(btnSave);
		btnSave.addActionListener(new ActionListener() {

			DiagosisAndTreatmentController controller = new DiagosisAndTreatmentController();

			@Override
			public void actionPerformed(ActionEvent e) {

				Date date = dateChooser.getDate();

				String classNo = fieldStudentRegistrationNumber.getText();
				String studentClass = fieldStudentClass.getText();
				String studentName = fieldStudentName.getText();
				String medicalHis = fieldDiagnosisResult.getText();
				String provDiag = areaProvisionalDiagnosis.getText();
				String treatmentTaken = areaTreatmentTaken.getText();

				DiagnosisAndTreatment treatment = null;
				treatment = new DiagnosisAndTreatment(date, classNo, studentClass, studentName, medicalHis, provDiag,
						treatmentTaken);

				try {
					controller.addNewDispensaryRecord(treatment);
					JOptionPane.showMessageDialog(null, "Saved successfully");
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Not Saved " + e1.getMessage());
				}

				clearFields();
				dialogDispensary.setVisible(false);
				refreshTable();

			}
		});

		btnCancel = new JButton("Cancel");
		btnCancel.setPreferredSize(dimensionButtons);
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialogDispensary.setVisible(false);
			}
		});
		dialogDispensary.add(btnCancel);

		btnRecordDiagosisOrTreatment.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (((JTextField) dateChooserOnPanel.getDateEditor().getUiComponent()).getText().trim().isEmpty()
						|| fieldStudentName.getText().isEmpty() || fieldStudentRegistrationNumber.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please make sure the date and student code number are all provided");
					return;
				} else {

					dialogDispensary.setVisible(true);

					if (!((JTextField) dateChooserOnPanel.getDateEditor().getUiComponent()).getText().trim()
							.isEmpty()) {
						String dateStr = ((JTextField) dateChooserOnPanel.getDateEditor().getUiComponent()).getText()
								.trim();
						((JTextField) dateChooser.getDateEditor().getUiComponent()).setText(dateStr);
					}

				}
			}
		});

		/*
		 * set fields to uneditable where necessary
		 */
		fieldStudentName.setEditable(false);

		fieldStudentClass.setEditable(false);

		fieldStudentdormitory.setEditable(false);
		fieldStudentYearOfAdmission.setEditable(false);

		fieldFatherName.setEditable(false);

		fieldFatherPhone.setEditable(false);

		fieldFatherAddress.setEditable(false);

		fieldMotherName.setEditable(false);

		fieldMotherPhone.setEditable(false);

		fieldSponsorName.setEditable(false);

		fieldSponsorPhone.setEditable(false);

	}

	private void refreshTable() {

		DiagosisAndTreatmentController controller = null;

		List<DiagnosisAndTreatment> theTreatments = null;

		try {
			controller = new DiagosisAndTreatmentController();
			theTreatments = controller.getDispensaryRecords();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		DispensaryTableModel tableModel = new DispensaryTableModel(theTreatments);

		tableDispenary.setModel(tableModel);

	}

	public void fillData1(JTable table, java.io.File file) {

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
					Object object=model.getValueAt(i, j);
					if(object=="" || object==null) {
						cell2.setCellValue("");
					}else {
						cell2.setCellValue(model.getValueAt(i, j).toString());
					}
					cell2.setCellStyle(stylebody);
					fSheet.autoSizeColumn(j);
				}
			}
			FileOutputStream fos = new FileOutputStream(new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
			workbook1.write(fos);
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void displayInComboBox(JTextField fieldSpecialCase2, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				fieldSpecialCase2.setText(rs.getString(1));
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

	private void clearFields() {
		fieldStudentName.setText("");

		fieldStudentClass.setText("");

		fieldStudentdormitory.setText("");
		fieldStudentYearOfAdmission.setText("");

		fieldFatherName.setText("");

		fieldFatherPhone.setText("");

		fieldFatherAddress.setText("");

		fieldMotherName.setText("");

		fieldMotherPhone.setText("");

		fieldSponsorName.setText("");

		fieldSponsorPhone.setText("");

		fieldSpecialCase.setText("");
	}

}
