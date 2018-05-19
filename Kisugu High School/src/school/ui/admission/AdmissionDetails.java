package school.ui.admission;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import com.github.sarxos.webcam.Webcam;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

import school.ui.finances.CashBookController;
import school.ui.jdbc.JavaDatabaseSelectStatements;
import school.ui.main.EditReqPopup;

public class AdmissionDetails extends JDialog {

	JPanel panelStudentInfo, panelParentInfo, panelUCEParticulars, panelHealthInfo, panelLeftHolder, panelPhoto,
			panelSubjects, panelRightHolder, panelMainHolder, panelPicture;

	public boolean isRunning;
	public Webcam webcam;
	protected JButton btnCapture;
	public Image image;
	protected JButton btnStart;
	protected JButton btnStop;
	private String ss;

	private JLabel labelStudentPhoto;
	private JButton btnTakePhoto;
	private JButton btnUploadPhoto;
	private JButton btnClearPhoto;
	private JPanel panelPictureButtons;
	private JPanel panelSubjectsOlevel;
	private JPanel panelSubjectsAlevel;
	private JButton btnRequirements;
	private JPanel panelIndexAndLevel;
	private JPanel panelOlevelSubjectsParticulars;
	private JPanel panelPrimarylevelSubjectsParticulars;
	private JLabel labelIndexNumber;
	private JLabel labelLevel;
	private JTextField fieldIndexNumber;
	private JComboBox comboStudentPassedLevel;
	private JLabel labelEnglish;
	private JLabel labelCRE;
	private JLabel labelLiterature;
	private JLabel labelGeography;
	private JLabel labelFrench;
	private JLabel labelHistory;
	private JLabel labelMathematics;
	private JLabel labelAMaths;
	private JLabel labelPhysics;
	private JLabel labelChemistry;
	private JLabel labelBiology;
	private JLabel labelFineArts;
	private JLabel labelCommerce;
	private JLabel labelWoodWork;
	private JLabel labelTD;
	private JLabel labelMetalWork;
	private JLabel labelOthers;
	private JTextField fieldEnglish;
	private JTextField fieldLiterauture;
	private JTextField fieldCRE;
	private JTextField fieldGeography;
	private JTextField fieldFrench;
	private JTextField fieldHistory;
	private JTextField fieldMathematics;
	private JTextField fieldAMaths;
	private JTextField fieldPhysics;
	private JTextField fieldChemistry;
	private JTextField fieldBiology;
	private JTextField fieldFineArts;
	private JTextField fieldCommerce;
	private JTextField fieldWoodWork;
	private JTextField fieldTD;
	private JTextField fieldMetalWork;
	private JTextField fieldOthers;
	private JLabel labelAggregate;
	private JTextField fieldAggregate;
	private JLabel labelDivision;
	private JComboBox fieldDivision;
	private JLabel labelP7Mathematics;
	private JTextField fieldP7Mathematics;
	private JLabel labelP7English;
	private JTextField fieldP7English;
	private JLabel labelP7SocialStudies;
	private JTextField fieldP7SocialStudies;
	private JLabel labelP7Science;
	private JTextField fieldP7Science;
	private JLabel labelP7Aggregate;
	private JTextField fieldP7Aggregate;
	private JLabel labelP7Division;
	private JComboBox fieldP7Division;
	protected JCheckBox checkEnglish;
	protected JCheckBox checkMathematics;
	protected JCheckBox checkPhysics;
	protected JCheckBox checkChemistry;
	protected JCheckBox checkBiology;
	protected JCheckBox checkGeography;
	protected JCheckBox checkHistory;
	protected JCheckBox checkCommerce;
	protected JCheckBox checkAgriculture;
	protected JCheckBox checkFineArts;
	protected JCheckBox checkAccount;
	protected JCheckBox checkCRE;
	protected JCheckBox checkMusic;
	protected JCheckBox checkSubMTC;
	protected JCheckBox checkSubICT;
	protected JCheckBox checkDivinity;
	protected JCheckBox checkLiterature;
	protected JCheckBox checkTD;
	protected int CHECKED = 1;
	protected int math;
	protected int english;
	protected int physics;
	protected int chem;
	protected int biology;
	protected int history;
	protected int geog;
	protected int agric;
	protected int commerce;
	protected int accounts;
	protected int ent;
	protected int fineart;
	protected int techdrawing;
	protected int cre;
	protected int computer;
	protected int additionalmath;
	protected JCheckBox checkWoodWork;
	protected int NOT_CHECKED = 0;
	protected int woodwork;
	protected int literature;
	protected int divinity;
	protected int metalwork;
	protected int kiswahili;
	private JButton btnAdmitStudent;
	private JLabel labelAllergy;
	private JRadioButton radioYesAllergy;
	private JRadioButton radioNoAllergy;
	private JTextField fieldAllergyExplanation;
	private JLabel labelDisability;
	private JRadioButton radioYesDisability;
	private JRadioButton radioNoDisability;
	private JTextField fieldDisabilityExplanation;
	private JLabel labelIllness;
	private JRadioButton radioYesIllness;
	private JRadioButton radioNoIllness;
	private JTextField fieldIllnessExplanation;
	private JLabel labelSpecialCase;
	private JRadioButton radioYesSpecialCase;
	private JRadioButton radioNoSpecialCase;
	private JTextField fieldSpecialCaseExplanation;
	protected int checkboxSponsoredValue;
	private JTextField fieldID;
	private JTextField fieldParentName;
	private JTextField fieldParentEmail;
	private JTextField fieldName;
	private JTextField fieldFeesPayment;
	private JTextField fieldAddress;
	private JDateChooser dateChooserDOB;
	private JTextField fieldReceiptNumber;
	private JTextField fieldDistrict;
	private JTextField fieldCounty;
	private JTextField fieldSubCounty;
	private JTextField fieldParish;
	private JTextField fieldWardLC1;
	private JTextField fieldMotherName;
	private JTextField fieldFatherPhone;
	private JTextField fieldMothersPhone;
	private JTextField fieldGuardianName;
	private JTextField fieldGuardianPhone;
	protected int BiologyAlevel;
	protected int TDAlevel;
	protected int ArtAlevel;
	protected int ChemistryAlevel;
	protected int AgricultureAlevel;
	protected int PhysicsAlevel;
	protected int MathematicsAlevel;
	protected int GeographyAlevel;
	protected int EntAlvel;
	protected int Economics;
	protected int GP;

	private JLabel labelFormerSchool;

	private JTextField fieldFormerSchool;

	protected JTable tableRequirements;

	protected DefaultTableModel modelTableAvailableRequirements;

	private JComboBox comboBoxClasses;

	private DefaultTableModel dm;

	private JComboBox comboBoxTermOfAdmission;

	protected int HistoryAlevel;

	protected int LiteratureAlevel;

	private JComboBox comboBoxDorms;

	private JComboBox comboBoxGender;

	private JComboBox comboBoxNationality;

	private JComboBox comboBox1Religion;

	protected FileInputStream inputStream;

	private JComboBox fieldSponsorName;

	private JTextField fieldSponsorPhone;

	private JComboBox comboBoxSponsorName;

	protected String url;

	protected String user;

	protected String pass;

	protected String classNumber;

	protected String studentClass;

	protected String dormitory;

	protected String gender;

	protected String nationality;

	protected String term;

	protected String religion;

	protected String studentName;

	protected String district;

	protected String county;

	protected String subcounty;

	protected String parish;

	protected String ward;

	java.sql.Connection conn = null;
	java.sql.PreparedStatement pst = null;

	protected int music;

	protected int submath;

	private JLabel labelagriculture;

	private JTextField fieldAgriculture;

	private String sqlStudentPaymentInfo;

	private JComboBox comboBoxBanks;

	private JYearChooser yearChooser;

	private JTextField fieldBankAccount;

	private String sqlStudentPaymentInfoS3;

	protected JComboBox comboforClass;

	private String standardquantity;

	private JTable table;

	private JTable tableA;

	private String subjects;

	private JTextField fieldCode;

	private String paymentcode;

	private int student_year;

	private ScrollPane paneA;

	private JCheckBox labelOnBursary;

	private JCheckBox labelFullBursary;

	private JCheckBox labelPartialBursary;

	private JCheckBox labelDay;

	private JCheckBox labelBoarding;

	protected String dayOrBoarding;

	protected String bursaryStatus;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new AdmissionDetails();
	}

	public AdmissionDetails() {

		setTitle("Students Personal Data");
		setSize(1345, 750);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		panelMainHolder = new JPanel();
		panelMainHolder.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		add(panelMainHolder);

		panelLeftHolder = new JPanel();
		panelLeftHolder.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panelLeftHolder.setPreferredSize(new Dimension(1015, 705));
		Border bodaforLeftHolder = BorderFactory.createRaisedBevelBorder();
		panelLeftHolder.setBorder(bodaforLeftHolder);
		panelLeftHolder.setBackground(new Color(0, 102, 102));
		panelMainHolder.add(panelLeftHolder);

		panelRightHolder = new JPanel();
		panelRightHolder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelRightHolder.setPreferredSize(new Dimension(295, 705));
		panelRightHolder.setBackground(new Color(0, 102, 102));
		Border bodaforRightHolder = BorderFactory.createRaisedBevelBorder();
		panelRightHolder.setBorder(bodaforRightHolder);
		panelMainHolder.add(panelRightHolder);

		panelStudentInfo = new JPanel();
		panelStudentInfo.setBorder(new TitledBorder("Student Info"));
		panelStudentInfo.setPreferredSize(new Dimension(500, 280));
		panelStudentInfo.setBackground(new Color(0, 102, 102));
		panelLeftHolder.add(panelStudentInfo);

		panelParentInfo = new JPanel();
		panelParentInfo.setBorder(new TitledBorder("Parent Info"));
		panelParentInfo.setPreferredSize(new Dimension(500, 280));
		panelParentInfo.setBackground(new Color(0, 102, 102));
		panelLeftHolder.add(panelParentInfo);

		panelUCEParticulars = new JPanel();
		panelUCEParticulars.setBorder(new TitledBorder("PLE/UCE Particulars"));
		panelUCEParticulars.setPreferredSize(new Dimension(1005, 200));
		panelUCEParticulars.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelUCEParticulars.setBackground(new Color(0, 102, 102));
		panelLeftHolder.add(panelUCEParticulars);

		panelHealthInfo = new JPanel();
		panelHealthInfo.setBorder(new TitledBorder("Health Info"));
		panelHealthInfo.setPreferredSize(new Dimension(1005, 200));
		panelHealthInfo.setBackground(new Color(0, 102, 102));
		panelLeftHolder.add(panelHealthInfo);

		panelPicture = new JPanel();
		panelPicture.setPreferredSize(new Dimension(160, 170));
		panelPicture.setBackground(new Color(0, 102, 102));
		panelRightHolder.add(panelPicture);

		panelPictureButtons = new JPanel();
		panelPictureButtons.setPreferredSize(new Dimension(120, 170));
		panelPictureButtons.setBackground(new Color(0, 102, 102));
		panelRightHolder.add(panelPictureButtons);

		Dimension dimButtons = new Dimension(110, 25);

		btnTakePhoto = new JButton("Take Photo");
		btnTakePhoto.setPreferredSize(dimButtons);
		panelPictureButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 20));
		btnTakePhoto.addActionListener(new ActionListener() {

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

							int userSelection = fileChooser.showSaveDialog(AdmissionDetails.this);

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

		panelPictureButtons.add(btnTakePhoto);

		btnUploadPhoto = new JButton("Upload Photo");
		btnUploadPhoto.setPreferredSize(dimButtons);
		btnUploadPhoto.addActionListener(new ActionListener() {

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
					labelStudentPhoto.setIcon(ResizeImage(path));
					ss = path;
				}

				else if (result == JFileChooser.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(null, "No Photo Was Selected", "Please select a photo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		panelPictureButtons.add(btnUploadPhoto);

		btnClearPhoto = new JButton("Clear Photo");
		btnClearPhoto.setPreferredSize(dimButtons);
		btnClearPhoto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				labelStudentPhoto.setIcon(null);

			}
		});
		panelPictureButtons.add(btnClearPhoto);

		labelStudentPhoto = new JLabel();
		labelStudentPhoto.setBorder(new LineBorder(Color.white, 2));
		labelStudentPhoto.setPreferredSize(new Dimension(150, 160));
		panelPicture.add(labelStudentPhoto);

		btnRequirements = new JButton("Students Requirements");
		btnRequirements.setPreferredSize(new Dimension(150, 30));
		btnRequirements.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				RequirementsDialog requirementsDialog = new RequirementsDialog();
				requirementsDialog.labelClassNumber.setText(fieldID.getText());
				requirementsDialog.labelTerm.setText(comboBoxTermOfAdmission.getSelectedItem().toString());
				requirementsDialog.labelPaymentCode.setText(fieldCode.getText());
				DisplayAvailableRequirementsForReportingStudents();

				requirementsDialog.tableforrequirements.setModel(dm);

				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				renderer.setHorizontalAlignment(SwingConstants.LEFT);
				renderer.setBackground(Color.black);
				requirementsDialog.tableforrequirements.getColumnModel().getColumn(0).setCellRenderer(renderer);
				requirementsDialog.tableforrequirements.getColumnModel().getColumn(1).setCellRenderer(renderer);
				requirementsDialog.tableforrequirements.getColumnModel().getColumn(2).setCellRenderer(renderer);
				requirementsDialog.tableforrequirements.getColumnModel().getColumn(3).setCellRenderer(renderer);

			}
		});
		panelRightHolder.add(btnRequirements);

		panelSubjectsAlevel = new JPanel();
		panelSubjectsAlevel.setBorder(new TitledBorder("A-Level Subjects Info"));
		panelSubjectsAlevel.setPreferredSize(new Dimension(280, 410));
		panelSubjectsAlevel.setBackground(new Color(0, 102, 102));
		panelRightHolder.add(panelSubjectsAlevel);
		panelSubjectsAlevel.setVisible(false);

		panelSubjectsOlevel = new JPanel();
		panelSubjectsOlevel.setBorder(new TitledBorder("O-Level Subjects Info"));
		panelSubjectsOlevel.setPreferredSize(new Dimension(280, 410));
		panelSubjectsOlevel.setBackground(new Color(0, 102, 102));
		panelRightHolder.add(panelSubjectsOlevel);
		panelSubjectsOlevel.setVisible(false);

		btnAdmitStudent = new JButton("Admit Student");
		btnAdmitStudent.setPreferredSize(new Dimension(150, 30));
		btnAdmitStudent.setEnabled(false);
		btnAdmitStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (comboStudentPassedLevel.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Please choose the level the student is joining");
				} else if (comboStudentPassedLevel.getSelectedIndex() == 1) {

					if (comboBoxClasses.getSelectedIndex() == 4) {

						if (fieldID.getText().indexOf("S5A") != -1 || fieldID.getText().indexOf("S5S") != -1) {

							/***************** Student Info ****************/
							SaveS1StudentInfo();
							saveToDorm();
							SaveS6StudentInfo();
							/***************** End *************************/

							saveToBank();
							SaveParentInfo();
							SaveUCEParticulars();
							saveHealthInfo();
							saveToClassInWhicYouAreReporting();
							updateSubjectsStudentsAlevel();
							saveStudentPaymentInfoToLedger();

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,receipt_number,details,folio,bank_dr,subcategory)"
											+ " select subcategory.catid,'" + fieldReceiptNumber.getText() + "',"
											+ "'School Fees','AutoGenerated Folio','" + fieldFeesPayment.getText()
											+ "'," + "'" + comboBoxSponsorName.getSelectedItem()
											+ "' from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntriesSilently(
									"insert into budget_expense_income_records(catid,subcategory,`"
											+ yearChooser.getYear() + "`,account_type)" + " select subcategory.catid,'"
											+ comboBoxSponsorName.getSelectedItem() + "','" + fieldFeesPayment.getText()
											+ "',account_type from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

						} else {
							JOptionPane.showMessageDialog(null, "Class Number and Student Class Do Not Match");
						}

					} else if (comboBoxClasses.getSelectedIndex() == 5) {
						if (fieldID.getText().indexOf("S6A") != -1 || fieldID.getText().indexOf("S6S") != -1) {
							/***************** Student Info ****************/
							SaveS1StudentInfo();
							saveToDorm();
							/***************** End *************************/

							saveToBank();
							SaveParentInfo();
							SaveUCEParticulars();
							saveHealthInfo();
							saveToClassInWhicYouAreReporting();
							updateSubjectsStudentsAlevel();
							saveStudentPaymentInfoToLedger();

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,receipt_number,details,folio,bank_dr,subcategory)"
											+ " select subcategory.catid,'" + fieldReceiptNumber.getText() + "',"
											+ "'School Fees','AutoGenerated Folio','" + fieldFeesPayment.getText()
											+ "'," + "'" + comboBoxSponsorName.getSelectedItem()
											+ "' from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntriesSilently(
									"insert into budget_expense_income_records(catid,subcategory,`"
											+ yearChooser.getYear() + "`,account_type)" + " select subcategory.catid,'"
											+ comboBoxSponsorName.getSelectedItem() + "','" + fieldFeesPayment.getText()
											+ "',account_type from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

						} else {
							JOptionPane.showMessageDialog(null, "Class Number and Student Class Do Not Match");
						}
					}

				} else if (comboStudentPassedLevel.getSelectedIndex() == 2) {

					if (comboBoxClasses.getSelectedIndex() == 0) {

						if (fieldID.getText().indexOf("S1") != -1 || fieldID.getText().indexOf("1B") != -1
								|| fieldID.getText().indexOf("1C") != -1) {
							/***************** Student Info ****************/
							SaveS1StudentInfo();
							saveToDorm();
							SaveS2StudentInfo();
							SaveS3StudentInfo();
							SaveS4StudentInfo();
							/***************** End *************************/

							saveToBank();
							SaveParentInfo();
							SavePLEParticulars();
							saveHealthInfo();
							saveToClassInWhicYouAreReporting();
							updateSubjectsStudents();
							saveStudentPaymentInfoToLedger();
							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,receipt_number,details,folio,bank_dr,subcategory)"
											+ " select subcategory.catid,'" + fieldReceiptNumber.getText() + "',"
											+ "'School Fees','AutoGenerated Folio','" + fieldFeesPayment.getText()
											+ "'," + "'" + comboBoxSponsorName.getSelectedItem()
											+ "' from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntriesSilently(
									"insert into budget_expense_income_records(catid,subcategory,`"
											+ yearChooser.getYear() + "`,account_type)" + " select subcategory.catid,'"
											+ comboBoxSponsorName.getSelectedItem() + "','" + fieldFeesPayment.getText()
											+ "',account_type from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

						} else {
							JOptionPane.showMessageDialog(null, "Class Number and Student Class Do Not Match");
						}

					} else if (comboBoxClasses.getSelectedIndex() == 1) {

						if (fieldID.getText().indexOf("S2") != -1 || fieldID.getText().indexOf("2B") != -1
								|| fieldID.getText().indexOf("2C") != -1) {

							/***************** Student Info ****************/
							SaveS1StudentInfo();
							saveToDorm();
							SaveS3StudentInfoForS2Admission();
							SaveS4StudentInfoForS2Admission();
							/****************** End ************************/

							saveToBank();
							SaveParentInfo();
							saveHealthInfo();
							saveToClassInWhicYouAreReporting();
							updateSubjectsStudents();
							saveStudentPaymentInfoToLedger();

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,receipt_number,details,folio,bank_dr,subcategory)"
											+ " select subcategory.catid,'" + fieldReceiptNumber.getText() + "',"
											+ "'School Fees','AutoGenerated Folio','" + fieldFeesPayment.getText()
											+ "'," + "'" + comboBoxSponsorName.getSelectedItem()
											+ "' from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntriesSilently(
									"insert into budget_expense_income_records(catid,subcategory,`"
											+ yearChooser.getYear() + "`,account_type)" + " select subcategory.catid,'"
											+ comboBoxSponsorName.getSelectedItem() + "','" + fieldFeesPayment.getText()
											+ "',account_type from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

						} else {
							JOptionPane.showMessageDialog(null, "Class Number and Student Class Do Not Match");
						}

					} else if (comboBoxClasses.getSelectedIndex() == 2) {

						if (fieldID.getText().indexOf("S3") != -1 || fieldID.getText().indexOf("3B") != -1
								|| fieldID.getText().indexOf("3C") != -1) {

							/***************** Student Info ****************/
							SaveS1StudentInfo();
							saveToDorm();
							SaveS4StudentInfoForS3Admission();
							/***************** End *************************/

							saveToBank();
							SaveParentInfo();
							saveHealthInfo();
							saveToClassInWhicYouAreReporting();
							updateSubjectsStudents();
							saveStudentPaymentInfoToLedger();

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,receipt_number,details,folio,bank_dr,subcategory)"
											+ " select subcategory.catid,'" + fieldReceiptNumber.getText() + "',"
											+ "'School Fees','AutoGenerated Folio','" + fieldFeesPayment.getText()
											+ "'," + "'" + comboBoxSponsorName.getSelectedItem()
											+ "' from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntriesSilently(
									"insert into budget_expense_income_records(catid,subcategory,`"
											+ yearChooser.getYear() + "`,account_type)" + " select subcategory.catid,'"
											+ comboBoxSponsorName.getSelectedItem() + "','" + fieldFeesPayment.getText()
											+ "',account_type from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

						} else {
							JOptionPane.showMessageDialog(null, "Class Number and Student Class Do Not Match");
						}

					} else if (comboBoxClasses.getSelectedIndex() == 3) {

						if (fieldID.getText().indexOf("S4") != -1 || fieldID.getText().indexOf("4B") != -1
								|| fieldID.getText().indexOf("4C") != -1) {

							/***************** Student Info ****************/
							SaveS1StudentInfo();
							saveToDorm();
							/***************** Student Info ****************/

							saveToBank();
							SaveParentInfo();
							saveHealthInfo();
							saveToClassInWhicYouAreReporting();
							updateSubjectsStudents();
							saveStudentPaymentInfoToLedger();

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,receipt_number,details,folio,bank_dr,subcategory)"
											+ " select subcategory.catid,'" + fieldReceiptNumber.getText() + "',"
											+ "'School Fees','AutoGenerated Folio','" + fieldFeesPayment.getText()
											+ "'," + "'" + comboBoxSponsorName.getSelectedItem()
											+ "' from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntriesSilently(
									"insert into budget_expense_income_records(catid,subcategory,`"
											+ yearChooser.getYear() + "`,account_type)" + " select subcategory.catid,'"
											+ comboBoxSponsorName.getSelectedItem() + "','" + fieldFeesPayment.getText()
											+ "',account_type from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

						} else {
							JOptionPane.showMessageDialog(null, "Class Number and Student Class Do Not Match");
						}
					}

				}

			}
		});

		panelRightHolder.add(btnAdmitStudent);

		panelIndexAndLevel = new JPanel();
		panelIndexAndLevel.setPreferredSize(new Dimension(200, 160));
		panelIndexAndLevel.setBorder(new LineBorder(Color.black, 1));
		panelIndexAndLevel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
		panelUCEParticulars.add(panelIndexAndLevel);

		panelOlevelSubjectsParticulars = new JPanel();
		panelOlevelSubjectsParticulars.setPreferredSize(new Dimension(780, 160));
		panelOlevelSubjectsParticulars.setBorder(new TitledBorder("U.C.E Particulars"));
		panelOlevelSubjectsParticulars.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelOlevelSubjectsParticulars.setBackground(new Color(0, 102, 102));
		panelUCEParticulars.add(panelOlevelSubjectsParticulars);
		panelOlevelSubjectsParticulars.setVisible(false);

		panelPrimarylevelSubjectsParticulars = new JPanel();
		panelPrimarylevelSubjectsParticulars.setPreferredSize(new Dimension(780, 160));
		panelPrimarylevelSubjectsParticulars.setBorder(new TitledBorder("P.L.E Particulars"));
		panelPrimarylevelSubjectsParticulars.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 20));
		panelPrimarylevelSubjectsParticulars.setBackground(new Color(0, 102, 102));
		panelUCEParticulars.add(panelPrimarylevelSubjectsParticulars);
		panelPrimarylevelSubjectsParticulars.setVisible(false);

		labelLevel = new JLabel("Joining A-Level/O-Level:");
		labelLevel.setPreferredSize(new Dimension(150, 20));
		panelIndexAndLevel.add(labelLevel);

		String[] levelPLEorUCE = { "Choose Level", "A-Level", "O-Level" };
		comboStudentPassedLevel = new JComboBox(levelPLEorUCE);
		comboStudentPassedLevel.setPreferredSize(new Dimension(150, 25));
		panelIndexAndLevel.add(comboStudentPassedLevel);

		labelIndexNumber = new JLabel("Index Number:");
		labelIndexNumber.setPreferredSize(new Dimension(150, 20));
		panelIndexAndLevel.add(labelIndexNumber);

		fieldIndexNumber = new JTextField();
		fieldIndexNumber.setPreferredSize(new Dimension(150, 25));
		panelIndexAndLevel.add(fieldIndexNumber);

		labelFormerSchool = new JLabel("Last School Attended:");
		labelFormerSchool.setPreferredSize(new Dimension(150, 20));
		panelIndexAndLevel.add(labelFormerSchool);

		fieldFormerSchool = new JTextField();
		fieldFormerSchool.setPreferredSize(new Dimension(150, 25));
		panelIndexAndLevel.add(fieldFormerSchool);

		comboStudentPassedLevel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int selectedLevel = comboStudentPassedLevel.getSelectedIndex();
				switch (selectedLevel) {

				case 0:

					break;
				case 1:
					panelOlevelSubjectsParticulars.setVisible(true);
					panelPrimarylevelSubjectsParticulars.setVisible(false);
					panelSubjectsOlevel.setVisible(false);
					panelSubjectsAlevel.setVisible(true);
					labelFormerSchool.setText("Former Secondary School");
					break;

				case 2:
					panelOlevelSubjectsParticulars.setVisible(false);
					panelPrimarylevelSubjectsParticulars.setVisible(true);
					panelSubjectsOlevel.setVisible(true);
					panelSubjectsAlevel.setVisible(false);
					labelFormerSchool.setText("Former Primary School");

					break;

				}

			}
		});

		Dimension dimfields = new Dimension(50, 20);
		Dimension dimlabels = new Dimension(120, 20);

		labelEnglish = new JLabel("English:");
		labelEnglish.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelEnglish);

		fieldEnglish = new JTextField();
		fieldEnglish.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldEnglish);

		labelLiterature = new JLabel("Lit. In English:");
		labelLiterature.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelLiterature);

		fieldLiterauture = new JTextField();
		fieldLiterauture.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldLiterauture);

		labelagriculture = new JLabel("Agriculture:");
		labelagriculture.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelagriculture);

		fieldAgriculture = new JTextField();
		fieldAgriculture.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldAgriculture);

		labelCRE = new JLabel("C.R.E");
		labelCRE.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelCRE);

		fieldCRE = new JTextField();
		fieldCRE.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldCRE);

		labelGeography = new JLabel("Geography");
		labelGeography.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelGeography);

		fieldGeography = new JTextField();
		fieldGeography.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldGeography);

		labelFrench = new JLabel("French");
		labelFrench.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelFrench);

		fieldFrench = new JTextField();
		fieldFrench.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldFrench);

		labelHistory = new JLabel("History");
		labelHistory.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelHistory);

		fieldHistory = new JTextField();
		fieldHistory.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldHistory);

		labelMathematics = new JLabel("Mathematics:");
		labelMathematics.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelMathematics);

		fieldMathematics = new JTextField();
		fieldMathematics.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldMathematics);

		labelAMaths = new JLabel("A.Maths:");
		labelAMaths.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelAMaths);

		fieldAMaths = new JTextField();
		fieldAMaths.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldAMaths);

		labelPhysics = new JLabel("Physics:");
		labelPhysics.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelPhysics);

		fieldPhysics = new JTextField();
		fieldPhysics.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldPhysics);

		labelChemistry = new JLabel("Chemistry:");
		labelChemistry.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelChemistry);

		fieldChemistry = new JTextField();
		fieldChemistry.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldChemistry);

		labelBiology = new JLabel("Biology:");
		labelBiology.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelBiology);

		fieldBiology = new JTextField();
		fieldBiology.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldBiology);

		labelFineArts = new JLabel("Fine Arts:");
		labelFineArts.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelFineArts);

		fieldFineArts = new JTextField();
		fieldFineArts.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldFineArts);

		labelCommerce = new JLabel("Commerce:");
		labelCommerce.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelCommerce);

		fieldCommerce = new JTextField();
		fieldCommerce.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldCommerce);

		labelWoodWork = new JLabel("Wood Work:");
		labelWoodWork.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelWoodWork);

		fieldWoodWork = new JTextField();
		fieldWoodWork.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldWoodWork);

		labelTD = new JLabel("Technical Drawing:");
		labelTD.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelTD);

		fieldTD = new JTextField();
		fieldTD.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldTD);

		labelMetalWork = new JLabel("Metal Work:");
		labelMetalWork.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelMetalWork);

		fieldMetalWork = new JTextField();
		fieldMetalWork.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldMetalWork);

		labelOthers = new JLabel("Others:");
		labelOthers.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelOthers);

		fieldOthers = new JTextField();
		fieldOthers.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldOthers);

		labelAggregate = new JLabel("Aggregate:");
		labelAggregate.setPreferredSize(dimlabels);
		panelOlevelSubjectsParticulars.add(labelAggregate);

		fieldAggregate = new JTextField();
		fieldAggregate.setPreferredSize(dimfields);
		panelOlevelSubjectsParticulars.add(fieldAggregate);

		labelDivision = new JLabel("Division:");
		panelOlevelSubjectsParticulars.add(labelDivision);

		String[] division = { "Choose Division", "I", "II", "III", "IV" };
		fieldDivision = new JComboBox(division);
		fieldDivision.setPreferredSize(new Dimension(120, 20));
		panelOlevelSubjectsParticulars.add(fieldDivision);

		labelP7Mathematics = new JLabel("Mathematics:");
		labelP7Mathematics.setPreferredSize(dimlabels);
		panelPrimarylevelSubjectsParticulars.add(labelP7Mathematics);

		fieldP7Mathematics = new JTextField();
		fieldP7Mathematics.setPreferredSize(dimfields);
		fieldP7Mathematics.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

				String sci = fieldP7Science.getText();

				int science = Integer.parseInt(sci);

				String eng = fieldP7English.getText();
				int english = Integer.parseInt(eng);

				String sst = fieldP7SocialStudies.getText();
				int socialStudies = Integer.parseInt(sst);

				String mtc = fieldP7Mathematics.getText();
				int math = Integer.parseInt(mtc);

				int total = science + english + socialStudies + math;

				if (total < 13) {
					fieldP7Division.setSelectedIndex(1);
				} else if (total > 12 && total < 25) {
					fieldP7Division.setSelectedIndex(2);
				} else if (total > 24 && total < 29) {
					fieldP7Division.setSelectedIndex(3);
				} else if (total > 28 && total < 33) {
					fieldP7Division.setSelectedIndex(4);
				} else if (total > 32 && total < 36) {
					fieldP7Division.setSelectedIndex(5);
				} else if (total == 36) {
					fieldP7Division.setSelectedIndex(6);
				} else {
					JOptionPane.showMessageDialog(null, "Invalid grade enterred somewhere");
				}

				fieldP7Aggregate.setText("" + total);
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		panelPrimarylevelSubjectsParticulars.add(fieldP7Mathematics);

		labelP7English = new JLabel("English:");
		labelP7English.setPreferredSize(dimlabels);
		panelPrimarylevelSubjectsParticulars.add(labelP7English);

		fieldP7English = new JTextField();
		fieldP7English.setPreferredSize(dimfields);
		fieldP7English.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

				String sci = fieldP7Science.getText();

				int science = Integer.parseInt(sci);

				String eng = fieldP7English.getText();
				int english = Integer.parseInt(eng);

				String sst = fieldP7SocialStudies.getText();
				int socialStudies = Integer.parseInt(sst);

				String mtc = fieldP7Mathematics.getText();
				int math = Integer.parseInt(mtc);

				int total = science + english + socialStudies + math;

				if (total < 13) {
					fieldP7Division.setSelectedIndex(1);
				} else if (total > 12 && total < 25) {
					fieldP7Division.setSelectedIndex(2);
				} else if (total > 24 && total < 29) {
					fieldP7Division.setSelectedIndex(3);
				} else if (total > 28 && total < 33) {
					fieldP7Division.setSelectedIndex(4);
				} else if (total > 32 && total < 36) {
					fieldP7Division.setSelectedIndex(5);
				} else if (total == 36) {
					fieldP7Division.setSelectedIndex(6);
				} else {
					JOptionPane.showMessageDialog(null, "Invalid grade enterred somewhere");
				}

				fieldP7Aggregate.setText("" + total);
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		panelPrimarylevelSubjectsParticulars.add(fieldP7English);

		labelP7SocialStudies = new JLabel("Social Studies:");
		labelP7SocialStudies.setPreferredSize(dimlabels);
		panelPrimarylevelSubjectsParticulars.add(labelP7SocialStudies);

		fieldP7SocialStudies = new JTextField();
		fieldP7SocialStudies.setPreferredSize(dimfields);
		fieldP7SocialStudies.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				String sci = fieldP7Science.getText();

				int science = Integer.parseInt(sci);

				String eng = fieldP7English.getText();
				int english = Integer.parseInt(eng);

				String sst = fieldP7SocialStudies.getText();
				int socialStudies = Integer.parseInt(sst);

				String mtc = fieldP7Mathematics.getText();
				int math = Integer.parseInt(mtc);

				int total = science + english + socialStudies + math;

				if (total < 13) {
					fieldP7Division.setSelectedIndex(1);
				} else if (total > 12 && total < 25) {
					fieldP7Division.setSelectedIndex(2);
				} else if (total > 24 && total < 29) {
					fieldP7Division.setSelectedIndex(3);
				} else if (total > 28 && total < 33) {
					fieldP7Division.setSelectedIndex(4);
				} else if (total > 32 && total < 36) {
					fieldP7Division.setSelectedIndex(5);
				} else if (total == 36) {
					fieldP7Division.setSelectedIndex(6);
				} else {
					JOptionPane.showMessageDialog(null, "Invalid grade enterred somewhere");
				}

				fieldP7Aggregate.setText("" + total);

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		panelPrimarylevelSubjectsParticulars.add(fieldP7SocialStudies);

		labelP7Science = new JLabel("Science:");
		labelP7Science.setPreferredSize(dimlabels);
		panelPrimarylevelSubjectsParticulars.add(labelP7Science);

		fieldP7Science = new JTextField();
		fieldP7Science.setPreferredSize(dimfields);
		fieldP7Science.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				String sci = fieldP7Science.getText();

				int science = Integer.parseInt(sci);

				String eng = fieldP7English.getText();
				int english = Integer.parseInt(eng);

				String sst = fieldP7SocialStudies.getText();
				int socialStudies = Integer.parseInt(sst);

				String mtc = fieldP7Mathematics.getText();
				int math = Integer.parseInt(mtc);

				int total = science + english + socialStudies + math;

				if (total < 13) {
					fieldP7Division.setSelectedIndex(1);
				} else if (total > 12 && total < 25) {
					fieldP7Division.setSelectedIndex(2);
				} else if (total > 24 && total < 29) {
					fieldP7Division.setSelectedIndex(3);
				} else if (total > 28 && total < 33) {
					fieldP7Division.setSelectedIndex(4);
				} else if (total > 32 && total < 36) {
					fieldP7Division.setSelectedIndex(5);
				} else if (total == 36) {
					fieldP7Division.setSelectedIndex(6);
				} else {
					JOptionPane.showMessageDialog(null, "Invalid grade enterred somewhere");
				}

				fieldP7Aggregate.setText("" + total);

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		panelPrimarylevelSubjectsParticulars.add(fieldP7Science);

		labelP7Aggregate = new JLabel("Aggregate:");
		labelP7Aggregate.setPreferredSize(dimlabels);
		panelPrimarylevelSubjectsParticulars.add(labelP7Aggregate);

		fieldP7Aggregate = new JTextField();
		fieldP7Aggregate.setPreferredSize(dimfields);
		panelPrimarylevelSubjectsParticulars.add(fieldP7Aggregate);

		labelP7Division = new JLabel("Division:");
		panelPrimarylevelSubjectsParticulars.add(labelP7Division);

		String[] P7Division = { "Choose Division", "I", "II", "III", "IV" };
		fieldP7Division = new JComboBox(P7Division);
		fieldP7Division.setPreferredSize(new Dimension(120, 20));
		panelPrimarylevelSubjectsParticulars.add(fieldP7Division);

		////////////// Subjects For O-Level/////////////////////

		///////////// Using JTable////////////////

		MyTableModel model = new MyTableModel();
		model.addRow(new Object[] { "English", false });
		model.addRow(new Object[] { "Mathematics", false });
		model.addRow(new Object[] { "History", false });
		model.addRow(new Object[] { "Agriculture", false });
		table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(180);

		panelSubjectsOlevel.add(table);

		/////////////// A-Level Subjects//////////////////////////////////////

		////////////// Using JTable ///////////////////////////////////////////

		MyTableModel modelA = new MyTableModel();
		modelA.addRow(new Object[] { "General Paper", false });
		modelA.addRow(new Object[] { "Mathematics", false });
		modelA.addRow(new Object[] { "Geography", false });
		modelA.addRow(new Object[] { "Biology", false });
		tableA = new JTable(modelA);
		tableA.getColumnModel().getColumn(0).setPreferredWidth(180);

		panelSubjectsAlevel.add(tableA);

		labelAllergy = new JLabel("Are you allergic to any food");
		labelAllergy.setPreferredSize(new Dimension(220, 25));
		panelHealthInfo.add(labelAllergy);

		final BlinkLabel bl = new BlinkLabel("Please Explain!");
		final BlinkLabel bl1 = new BlinkLabel("Please Explain!");
		final BlinkLabel bl2 = new BlinkLabel("Please Explain!");
		final BlinkLabel bl3 = new BlinkLabel("Please Explain!");

		radioYesAllergy = new JRadioButton("Yes");
		panelHealthInfo.add(radioYesAllergy);
		radioYesAllergy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (radioYesAllergy.isSelected()) {

					fieldAllergyExplanation.setEditable(true);
					radioNoAllergy.setSelected(false);
					fieldAllergyExplanation.setPreferredSize(new Dimension(508, 30));
					bl.setVisible(true);

				} else {
					fieldAllergyExplanation.setEditable(false);

				}

			}
		});

		radioNoAllergy = new JRadioButton("No");
		panelHealthInfo.add(radioNoAllergy);
		radioNoAllergy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (radioNoAllergy.isSelected()) {

					fieldAllergyExplanation.setEditable(false);
					radioYesAllergy.setSelected(false);
					fieldAllergyExplanation.setPreferredSize(new Dimension(600, 30));
					bl.setVisible(false);

				}

			}
		});

		panelHealthInfo.add(bl);
		bl.setVisible(false);
		fieldAllergyExplanation = new JTextField();
		fieldAllergyExplanation.setPreferredSize(new Dimension(600, 30));
		panelHealthInfo.add(fieldAllergyExplanation);
		fieldAllergyExplanation.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (!fieldAllergyExplanation.getText().isEmpty()) {
					bl.setVisible(false);
					fieldAllergyExplanation.setPreferredSize(new Dimension(600, 30));
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		fieldAllergyExplanation.setEditable(false);

		labelDisability = new JLabel("Any Disability,Handicap, or Infirmity?");
		labelDisability.setPreferredSize(new Dimension(220, 25));
		panelHealthInfo.add(labelDisability);

		radioYesDisability = new JRadioButton("Yes");
		panelHealthInfo.add(radioYesDisability);
		radioYesDisability.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (radioYesDisability.isSelected()) {

					fieldDisabilityExplanation.setEditable(true);
					radioNoDisability.setSelected(false);
					fieldDisabilityExplanation.setPreferredSize(new Dimension(508, 30));
					bl1.setVisible(true);

				} else {
					fieldDisabilityExplanation.setEditable(false);
				}

			}
		});

		radioNoDisability = new JRadioButton("No");
		panelHealthInfo.add(radioNoDisability);
		radioNoDisability.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (radioNoDisability.isSelected()) {

					fieldDisabilityExplanation.setEditable(false);
					radioYesDisability.setSelected(false);
					fieldDisabilityExplanation.setPreferredSize(new Dimension(600, 30));
					bl1.setVisible(false);

				}

			}
		});

		panelHealthInfo.add(bl1);
		bl1.setVisible(false);

		fieldDisabilityExplanation = new JTextField();
		fieldDisabilityExplanation.setPreferredSize(new Dimension(600, 30));
		panelHealthInfo.add(fieldDisabilityExplanation);
		fieldDisabilityExplanation.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (!fieldDisabilityExplanation.getText().isEmpty()) {
					bl1.setVisible(false);
					fieldDisabilityExplanation.setPreferredSize(new Dimension(600, 30));
				}

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		fieldDisabilityExplanation.setEditable(false);

		labelIllness = new JLabel("Any History of Chronic Illness?");
		labelIllness.setPreferredSize(new Dimension(220, 25));
		panelHealthInfo.add(labelIllness);

		radioYesIllness = new JRadioButton("Yes");
		panelHealthInfo.add(radioYesIllness);
		radioYesIllness.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (radioYesIllness.isSelected()) {

					fieldIllnessExplanation.setEditable(true);
					radioNoIllness.setSelected(false);
					fieldIllnessExplanation.setPreferredSize(new Dimension(508, 30));
					bl2.setVisible(true);

				} else {
					fieldIllnessExplanation.setEditable(false);
				}

			}
		});

		radioNoIllness = new JRadioButton("No");
		panelHealthInfo.add(radioNoIllness);
		radioNoIllness.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (radioNoIllness.isSelected()) {

					fieldIllnessExplanation.setEditable(false);
					radioYesIllness.setSelected(false);
					fieldIllnessExplanation.setPreferredSize(new Dimension(600, 30));
					bl2.setVisible(false);

				}

			}
		});

		panelHealthInfo.add(bl2);
		bl2.setVisible(false);

		fieldIllnessExplanation = new JTextField();
		fieldIllnessExplanation.setPreferredSize(new Dimension(600, 30));
		panelHealthInfo.add(fieldIllnessExplanation);
		fieldIllnessExplanation.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (!fieldIllnessExplanation.getText().isEmpty()) {
					bl2.setVisible(false);
					fieldIllnessExplanation.setPreferredSize(new Dimension(600, 30));
				}

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		fieldIllnessExplanation.setEditable(false);

		labelSpecialCase = new JLabel("Any Special Sickness Currently?");
		labelSpecialCase.setPreferredSize(new Dimension(220, 25));
		panelHealthInfo.add(labelSpecialCase);

		radioYesSpecialCase = new JRadioButton("Yes");
		panelHealthInfo.add(radioYesSpecialCase);
		radioYesSpecialCase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (radioYesSpecialCase.isSelected()) {

					fieldSpecialCaseExplanation.setEditable(true);
					radioNoSpecialCase.setSelected(false);
					fieldSpecialCaseExplanation.setPreferredSize(new Dimension(508, 30));
					bl3.setVisible(true);

				} else {
					fieldSpecialCaseExplanation.setEditable(false);
				}

			}
		});

		radioNoSpecialCase = new JRadioButton("No");
		panelHealthInfo.add(radioNoSpecialCase);
		radioNoSpecialCase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (radioNoSpecialCase.isSelected()) {

					fieldSpecialCaseExplanation.setEditable(false);
					radioYesSpecialCase.setSelected(false);
					fieldSpecialCaseExplanation.setPreferredSize(new Dimension(600, 30));
					bl3.setVisible(false);

				}

			}
		});

		panelHealthInfo.add(bl3);
		bl3.setVisible(false);

		fieldSpecialCaseExplanation = new JTextField();
		fieldSpecialCaseExplanation.setPreferredSize(new Dimension(600, 30));
		panelHealthInfo.add(fieldSpecialCaseExplanation);
		fieldSpecialCaseExplanation.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (!fieldSpecialCaseExplanation.getText().isEmpty()) {
					bl3.setVisible(false);
					fieldSpecialCaseExplanation.setPreferredSize(new Dimension(600, 30));
				}

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		fieldSpecialCaseExplanation.setEditable(false);

		/////// Students Info//////////////////////////////////////////

		Dimension dimFields = new Dimension(130, 23);
		Dimension dimLabels = new Dimension(100, 23);

		JLabel labelCode = new JLabel();
		labelCode.setText("Payment Code:");
		labelCode.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelCode);

		fieldCode = new JTextField();
		fieldCode.setPreferredSize(dimFields);
		fieldCode.setEditable(false);
		panelStudentInfo.add(fieldCode);

		panelStudentInfo.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel labelID = new JLabel();
		labelID.setText("Class Number:");
		labelID.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelID);

		fieldID = new JTextField();
		fieldID.setPreferredSize(dimFields);
		fieldID.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				fieldCode.setText(fieldID.getText());
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		panelStudentInfo.add(fieldID);

		JLabel labelName = new JLabel();
		labelName.setText("Student Name:");
		labelName.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelName);

		fieldName = new JTextField();
		fieldName.setPreferredSize(dimFields);
		panelStudentInfo.add(fieldName);

		JLabel labelClass = new JLabel();
		labelClass.setText("Class:");
		labelClass.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelClass);

		comboBoxClasses = new JComboBox();
		comboBoxClasses.setPreferredSize(dimFields);
		panelStudentInfo.add(comboBoxClasses);

		JLabel labelCounty = new JLabel("County:");
		labelCounty.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelCounty);

		fieldCounty = new JTextField();
		fieldCounty.setPreferredSize(dimFields);
		panelStudentInfo.add(fieldCounty);

		JLabel labelDorm = new JLabel();
		labelDorm.setText("Dormitory");
		labelDorm.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelDorm);

		comboBoxDorms = new JComboBox();
		comboBoxDorms.setPreferredSize(dimFields);
		panelStudentInfo.add(comboBoxDorms);

		JLabel labelParish = new JLabel("Parish:");
		labelParish.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelParish);

		fieldParish = new JTextField();
		fieldParish.setPreferredSize(dimFields);
		panelStudentInfo.add(fieldParish);

		JLabel labelNationality = new JLabel();
		labelNationality.setText("Nationality");
		labelNationality.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelNationality);

		comboBoxNationality = new JComboBox();
		comboBoxNationality.getLocale().getCountry().toString();
		comboBoxNationality.setPreferredSize(dimFields);
		panelStudentInfo.add(comboBoxNationality);

		JLabel labelSubCounty = new JLabel("Sub-County:");
		labelSubCounty.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelSubCounty);

		fieldSubCounty = new JTextField();
		fieldSubCounty.setPreferredSize(dimFields);
		panelStudentInfo.add(fieldSubCounty);

		JLabel labelGender = new JLabel();
		labelGender.setText("Gender:");
		labelGender.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelGender);

		String[] gender = { "Male", "Female" };

		comboBoxGender = new JComboBox(gender);
		comboBoxGender.setPreferredSize(dimFields);
		panelStudentInfo.add(comboBoxGender);

		JLabel labelWardLC1 = new JLabel("Ward/LC1:");
		labelWardLC1.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelWardLC1);

		fieldWardLC1 = new JTextField();
		fieldWardLC1.setPreferredSize(dimFields);
		panelStudentInfo.add(fieldWardLC1);

		JLabel labelTermOfAdmission = new JLabel();
		labelTermOfAdmission.setText("Term Of Admission:");
		labelTermOfAdmission.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelTermOfAdmission);

		comboBoxTermOfAdmission = new JComboBox();
		comboBoxTermOfAdmission.setPreferredSize(dimFields);
		panelStudentInfo.add(comboBoxTermOfAdmission);

		JLabel labelDistrict = new JLabel("Home District:");
		labelDistrict.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelDistrict);

		fieldDistrict = new JTextField();
		fieldDistrict.setPreferredSize(dimFields);
		panelStudentInfo.add(fieldDistrict);

		JLabel labelReligion = new JLabel();
		labelReligion.setText("Religion:");
		labelReligion.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelReligion);

		comboBox1Religion = new JComboBox();
		comboBox1Religion.setPreferredSize(dimFields);
		panelStudentInfo.add(comboBox1Religion);

		JLabel labelDOB = new JLabel();
		labelDOB.setText("Date Of Birth:");
		labelDOB.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelDOB);

		dateChooserDOB = new JDateChooser();
		dateChooserDOB.getDate();
		dateChooserDOB.setPreferredSize(dimFields);
		panelStudentInfo.add(dateChooserDOB);

		labelDay = new JCheckBox("Day ");
		labelDay.setPreferredSize(dimLabels);
		labelDay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (labelDay.isSelected()) {
					labelBoarding.setSelected(false);
					dayOrBoarding = "Day";
				} else {
					dayOrBoarding = "Boarding";
				}
			}
		});
		panelStudentInfo.add(labelDay);

		labelBoarding = new JCheckBox("Boarding");
		labelBoarding.setPreferredSize(dimFields);
		labelBoarding.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (labelBoarding.isSelected()) {
					labelDay.setSelected(false);
					dayOrBoarding = "Boarding";
				} else {
					dayOrBoarding = "Day";
				}
			}
		});
		panelStudentInfo.add(labelBoarding);

		labelOnBursary = new JCheckBox("No Bursary");
		labelOnBursary.setPreferredSize(dimLabels);
		labelOnBursary.setSelected(true);
		labelOnBursary.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (labelOnBursary.isSelected()) {
					labelFullBursary.setSelected(false);
					labelPartialBursary.setSelected(false);

					bursaryStatus = "Not On Bursary";
				} else {
					labelFullBursary.setSelected(true);
					bursaryStatus="Full Bursary";
				}
			}
		});
		panelStudentInfo.add(labelOnBursary);

		labelFullBursary = new JCheckBox("Full Bursary");
		labelFullBursary.setPreferredSize(dimFields);
		labelFullBursary.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (labelFullBursary.isSelected()) {
					labelOnBursary.setSelected(false);
					labelPartialBursary.setSelected(false);
					
					bursaryStatus="Full Bursary";
				} else {
					
					bursaryStatus="Not On Bursary";
				}
			}
		});
		panelStudentInfo.add(labelFullBursary);

		labelPartialBursary = new JCheckBox("Partial Bursary");
		labelPartialBursary.setPreferredSize(dimLabels);
		labelPartialBursary.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (labelPartialBursary.isSelected()) {
					labelOnBursary.setSelected(false);
					labelFullBursary.setSelected(false);
					bursaryStatus="Partial Bursary";
				} else {

					bursaryStatus="Not On Bursary";
				}
			}
		});
		panelStudentInfo.add(labelPartialBursary);

		JCheckBox labelSponsored = new JCheckBox("Sponsored");
		labelSponsored.setPreferredSize(dimFields);
		panelStudentInfo.add(labelSponsored);

		///////////////////////// Parent Details/////////////////////////////

		JLabel labelParent = new JLabel();
		labelParent.setText("Father's Name");
		labelParent.setPreferredSize(dimLabels);
		panelParentInfo.add(labelParent);

		fieldParentName = new JTextField();
		fieldParentName.setPreferredSize(dimFields);
		panelParentInfo.add(fieldParentName);

		JLabel labelMother = new JLabel();
		labelMother.setText("Mother's Name");
		labelMother.setPreferredSize(dimLabels);
		panelParentInfo.add(labelMother);

		fieldMotherName = new JTextField();
		fieldMotherName.setPreferredSize(dimFields);
		panelParentInfo.add(fieldMotherName);

		JLabel labelFatherContact = new JLabel();
		labelFatherContact.setText("Father's Phone:");
		labelFatherContact.setPreferredSize(dimLabels);
		panelParentInfo.add(labelFatherContact);

		fieldFatherPhone = new JTextField();
		fieldFatherPhone.setPreferredSize(dimFields);
		panelParentInfo.add(fieldFatherPhone);

		JLabel labelMotherEmailContact = new JLabel();
		labelMotherEmailContact.setText("Mother's Phone:");
		labelMotherEmailContact.setPreferredSize(dimLabels);
		panelParentInfo.add(labelMotherEmailContact);

		fieldMothersPhone = new JTextField();
		fieldMothersPhone.setPreferredSize(dimFields);
		panelParentInfo.add(fieldMothersPhone);

		JLabel labelGuardian = new JLabel();
		labelGuardian.setText("Guardian's Name");
		labelGuardian.setPreferredSize(dimLabels);
		panelParentInfo.add(labelGuardian);

		fieldGuardianName = new JTextField();
		fieldGuardianName.setPreferredSize(dimFields);
		panelParentInfo.add(fieldGuardianName);

		JLabel labelGuardianContact = new JLabel();
		labelGuardianContact.setText("Guardian's Phone:");
		labelGuardianContact.setPreferredSize(dimLabels);
		panelParentInfo.add(labelGuardianContact);

		fieldGuardianPhone = new JTextField();
		fieldGuardianPhone.setPreferredSize(dimFields);
		panelParentInfo.add(fieldGuardianPhone);

		JLabel labelSponsor = new JLabel();
		labelSponsor.setText("Sponsor's Name");
		labelSponsor.setPreferredSize(dimLabels);
		panelParentInfo.add(labelSponsor);

		String sponsor[] = { "Choose Sponsor" };
		fieldSponsorName = new JComboBox(sponsor);
		fieldSponsorName.setEditable(false);
		fieldSponsorName.setPreferredSize(dimFields);
		panelParentInfo.add(fieldSponsorName);

		displayInComboBox(fieldSponsorName, "select sponsors_name from student_sponsors");

		JLabel labelSponsorContact = new JLabel();
		labelSponsorContact.setText("Sponsor's Phone:");
		labelSponsorContact.setPreferredSize(dimLabels);
		panelParentInfo.add(labelSponsorContact);

		fieldSponsorPhone = new JTextField();
		fieldSponsorPhone.setPreferredSize(dimFields);
		panelParentInfo.add(fieldSponsorPhone);

		JLabel labelEmail = new JLabel();
		labelEmail.setText("Parent Email:");
		labelEmail.setPreferredSize(dimLabels);
		panelParentInfo.add(labelEmail);

		fieldParentEmail = new JTextField();
		fieldParentEmail.setPreferredSize(dimFields);
		panelParentInfo.add(fieldParentEmail);

		JLabel labelAddress = new JLabel();
		labelAddress.setText("Address:");
		labelAddress.setPreferredSize(dimLabels);
		panelParentInfo.add(labelAddress);

		fieldAddress = new JTextField();
		fieldAddress.setPreferredSize(dimFields);
		panelParentInfo.add(fieldAddress);

		JLabel labelFeesPayment = new JLabel();
		labelFeesPayment.setText("Fees Paid:");
		labelFeesPayment.setPreferredSize(dimLabels);
		panelParentInfo.add(labelFeesPayment);

		fieldFeesPayment = new JTextField();
		fieldFeesPayment.setPreferredSize(dimFields);
		fieldFeesPayment.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (fieldFeesPayment.getDocument().getLength() < 4) {
					btnAdmitStudent.setEnabled(false);
				} else {
					btnAdmitStudent.setEnabled(true);
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		panelParentInfo.add(fieldFeesPayment);

		JLabel labelFeesBal = new JLabel();
		labelFeesBal.setText("Choose Year:");
		labelFeesBal.setPreferredSize(dimLabels);

		panelParentInfo.add(labelFeesBal);

		yearChooser = new JYearChooser();
		yearChooser.getYear();
		yearChooser.setPreferredSize(dimFields);
		panelParentInfo.add(yearChooser);

		JLabel labelBankName = new JLabel();
		labelBankName.setText("Bank Name:");
		labelBankName.setPreferredSize(dimLabels);
		panelParentInfo.add(labelBankName);

		comboBoxBanks = new JComboBox();
		comboBoxBanks.setPreferredSize(dimFields);
		panelParentInfo.add(comboBoxBanks);
		comboBoxBanks.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				displayInTextField(fieldBankAccount,
						"select account_number from banks where bank_name='" + comboBoxBanks.getSelectedItem() + "'");
			}
		});

		JLabel labelSponsors = new JLabel();
		labelSponsors.setText("Account Name:");
		labelSponsors.setPreferredSize(dimLabels);
		panelParentInfo.add(labelSponsors);

		comboBoxSponsorName = new JComboBox();
		comboBoxSponsorName.setPreferredSize(dimFields);
		comboBoxSponsorName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		panelParentInfo.add(comboBoxSponsorName);

		JLabel labelYear = new JLabel();
		labelYear.setText("Receipt Number:");
		labelYear.setPreferredSize(dimLabels);
		panelParentInfo.add(labelYear);

		fieldReceiptNumber = new JTextField();
		fieldReceiptNumber.setPreferredSize(dimFields);
		panelParentInfo.add(fieldReceiptNumber);

		JLabel labelBankAccount = new JLabel();
		labelBankAccount.setText("Bank Account:");
		labelBankAccount.setPreferredSize(dimLabels);
		panelParentInfo.add(labelBankAccount);

		fieldBankAccount = new JTextField();
		fieldBankAccount.setPreferredSize(dimFields);
		// fieldBankAccount.setEditable(false);
		panelParentInfo.add(fieldBankAccount);

		JButton addReq1 = new JButton("Add New Requirement");
		addReq1.setPreferredSize(new Dimension(135, 25));
		addReq1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		addReq1.addActionListener(new ActionListener() {

			private JDialog addframe;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				addframe = new JDialog();
				addframe.setTitle("Add New Requirement");
				addframe.setSize(350, 200);
				addframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				addframe.setLocationRelativeTo(null);

				JPanel addframepanel = new JPanel();

				JLabel responsibleClass = new JLabel("Responsible Class:");
				responsibleClass.setPreferredSize(new Dimension(150, 25));
				addframepanel.add(responsibleClass);

				comboforClass = new JComboBox();
				comboforClass.setPreferredSize(new Dimension(120, 25));
				addframepanel.add(comboforClass);

				addframepanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 15));
				JLabel reqname = new JLabel("Name of Requirement:");
				reqname.setPreferredSize(new Dimension(150, 25));
				addframepanel.add(reqname);

				JTextField fieldforname = new JTextField();
				fieldforname.setPreferredSize(new Dimension(120, 25));
				addframepanel.add(fieldforname);

				JLabel reqQty = new JLabel("Quantity of Requirement:");
				reqQty.setPreferredSize(new Dimension(150, 25));
				addframepanel.add(reqQty);

				JTextField fieldforQty = new JTextField();
				fieldforQty.setPreferredSize(new Dimension(120, 25));
				addframepanel.add(fieldforQty);

				JButton addR = new JButton("Save");
				addR.setPreferredSize(new Dimension(120, 25));
				addR.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						new AdmissionController().AddRequirements(
								"insert into requirementslist(class_requirements,requirement_name,standard_quantity) values('"
										+ comboforClass.getSelectedItem() + "'," + "'" + fieldforname.getText() + "','"
										+ fieldforQty.getText() + "')");
					}
				});
				addframepanel.add(addR);

				addframe.add(addframepanel);

				displayInComboBox(comboforClass, "select class_name from student_classes");
				addframe.setVisible(true);

			}
		});

		panelRightHolder.add(addReq1);
		JButton EditReq1 = new JButton("Edit Requirement");
		EditReq1.setPreferredSize(new Dimension(128, 25));
		EditReq1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		EditReq1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JDialog addframe = new JDialog();
				addframe.setTitle("Edit Requirement");
				addframe.setSize(550, 300);
				addframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				addframe.setLocationRelativeTo(null);

				JPanel addframepanel = new JPanel();

				addframepanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 15));

				String[][] data = new String[][] { { "Req1", "Qty" }, { "Req1", "Qty" }, { "Req1", "Qty" },
						{ "Req1", "Qty" }, { "Req1", "Qty" }, { "Req1", "Qty" }, { "Req1", "Qty" },

				};

				String[] heading = new String[] { "Req Name", "Quantity" };

				tableRequirements = new JTable(data, heading);
				JTableHeader headerReqts1 = tableRequirements.getTableHeader();
				headerReqts1.setPreferredSize(new Dimension(400, 30));
				JScrollPane scroller = new JScrollPane(tableRequirements);
				tableRequirements.setRowHeight(25);
				scroller.setPreferredSize(new Dimension(500, 200));

				addframepanel.add(scroller);
				tableRequirements.addMouseListener(new MouseListener() {

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

						EditReqPopup object = new EditReqPopup();
						object.setLocationRelativeTo(null);

						int index = tableRequirements.getSelectedRow();
						TableModel model = tableRequirements.getModel();
						String classes = model.getValueAt(index, 0).toString();
						String name = model.getValueAt(index, 1).toString();
						String qty = model.getValueAt(index, 2).toString();
						String IDReq = model.getValueAt(index, 3).toString();

						object.fieldforclass.setText(classes);
						object.fieldforname.setText(name);
						object.fieldforQty.setText(qty);
						object.fieldforID.setText(IDReq);

					}
				});

				JButton addR = new JButton("Exit");
				addR.setPreferredSize(new Dimension(120, 25));
				addR.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						addframe.setVisible(false);

					}
				});
				addframepanel.add(addR);

				addframe.add(addframepanel);

				addframe.addWindowListener(new WindowAdapter() {

					private DefaultTableModel modelTableAvailableRequirements;

					@Override
					public void windowActivated(WindowEvent arg0) {

						modelTableAvailableRequirements = new JavaDatabaseSelectStatements()
								.DisplayAvailableRequirements();
						tableRequirements.setModel(modelTableAvailableRequirements);

					}
				});
				addframe.setVisible(true);
			}
		});

		panelRightHolder.add(EditReq1);

		displayInComboBox(comboBoxClasses, "select class_name from student_classes");
		displayInComboBox(comboBoxTermOfAdmission, "select term_name from student_terms");
		displayInComboBox(comboBoxNationality, "select nationality_name from student_nationality");
		displayInComboBox(comboBoxSponsorName, "select scatname from subcategory");
		displayInComboBox(comboBox1Religion, "select religion_name from student_religion");
		displayInComboBox(comboBoxGender, "select sex_name from student_sex");
		displayInComboBox(comboBoxDorms, "select dormName from dorm");
		displayInComboBox(comboBoxBanks, "select bank_name from banks");
		displayData(table, "select subject_name from student_subjects");
		displayData(tableA, "select subject_name from student_subjectsA");

		setVisible(true);

	}

	public class MyTableModel extends DefaultTableModel {

		public MyTableModel() {
			super(new String[] { "Name", "Present" }, 0);
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			Class clazz = String.class;
			switch (columnIndex) {
			case 0:
				clazz = String.class;
				break;
			case 1:
				clazz = Boolean.class;
				break;
			}
			return clazz;
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return true;
		}

		@Override
		public void setValueAt(Object aValue, int row, int column) {
			if (aValue instanceof Boolean && column == 1) {
				System.out.println(aValue);
				Vector rowData = (Vector) getDataVector().get(row);
				rowData.set(1, (boolean) aValue);
				fireTableCellUpdated(row, column);

			}
		}

	}

	public ImageIcon ResizeImage(String ImagePath) {

		ImageIcon MyImg = new ImageIcon(ImagePath);
		Image img = MyImg.getImage();
		Image newimg = img.getScaledInstance(labelStudentPhoto.getWidth(), labelStudentPhoto.getHeight(),
				Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newimg);

		return image;

	}

	public JComboBox getComboBoxClasses() {
		return comboBoxClasses;
	}

	public DefaultTableModel DisplayAvailableRequirementsForReportingStudents() {

		Connection conn = null;

		Object[] columns = { "Responsible Class", "Requirement Name", "Expected Amount", "Quantity Brought" };

		dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		String selectedClass = comboBoxClasses.getSelectedItem().toString();

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select class_requirements,requirement_name,standard_quantity from requirementslist where class_requirements='"
				+ selectedClass + "'";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String Class = rs.getString(1);
				String NAME = rs.getString(2);
				String Standard = rs.getString(3);

				dm.addRow(new String[] { Class, NAME, Standard, null });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public Date convertFromUtilToSQLDate(java.util.Date dateUtil) {

		if (dateUtil != null) {
			java.sql.Date sqlDate = new java.sql.Date(dateUtil.getTime());

			return sqlDate;
		} else {
			return null;
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

	public class BlinkLabel extends JLabel {
		private static final long serialVersionUID = 1L;

		private static final int BLINKING_RATE = 1000; // in ms

		private boolean blinkingOn = true;

		public BlinkLabel(String text) {
			super(text);
			Timer timer = new Timer(BLINKING_RATE, new TimerListener(this));
			timer.setInitialDelay(0);
			timer.start();
		}

		public void setBlinking(boolean flag) {
			this.blinkingOn = flag;
		}

		public boolean getBlinking(boolean flag) {
			return this.blinkingOn;
		}

		private class TimerListener implements ActionListener {
			private BlinkLabel bl;
			private Color bg;
			private Color fg;
			private boolean isForeground = true;

			public TimerListener(BlinkLabel bl) {
				this.bl = bl;
				fg = bl.getForeground();
				bg = bl.getBackground();
			}

			public void actionPerformed(ActionEvent e) {
				if (bl.blinkingOn) {
					if (isForeground) {
						bl.setForeground(fg);
					} else {
						bl.setForeground(bg);
					}
					isForeground = !isForeground;
				} else {
					// here we want to make sure that the label is visible
					// if the blinking is off.
					if (isForeground) {
						bl.setForeground(fg);
						isForeground = false;
					}
				}
			}

		}
	}

	public void SaveS1StudentInfo() {

		classNumber = fieldID.getText();
		student_year = yearChooser.getYear();
		studentClass = comboBoxClasses.getSelectedItem().toString();
		dormitory = comboBoxDorms.getSelectedItem().toString();
		gender = comboBoxGender.getSelectedItem().toString();
		nationality = comboBoxNationality.getSelectedItem().toString();
		term = comboBoxTermOfAdmission.getSelectedItem().toString();
		religion = comboBox1Religion.getSelectedItem().toString();
		studentName = fieldName.getText();
		district = fieldDistrict.getText();
		county = fieldCounty.getText();
		subcounty = fieldSubCounty.getText();
		parish = fieldParish.getText();
		ward = fieldWardLC1.getText();
		paymentcode = fieldCode.getText();

		try {
			inputStream = new FileInputStream(new File(ss));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		String sqlStudentInfo = "insert into students_info(year,payment_code,class_number,student_class,student_name,dormitory,term,"
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo,day_boarding,bursary_status) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?,?,?)";
		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentInfo);
			pst.setObject(1, student_year);
			pst.setString(2, paymentcode);
			pst.setString(3, classNumber);
			pst.setString(4, studentClass);
			pst.setString(5, studentName);
			pst.setString(6, dormitory);
			pst.setString(7, term);
			pst.setString(8, religion);
			pst.setString(9, nationality);
			pst.setString(10, gender);
			pst.setString(11, district);
			pst.setString(12, county);
			pst.setString(13, subcounty);
			pst.setString(14, parish);
			pst.setString(15, ward);
			pst.setBlob(16, inputStream);
			pst.setString(17, dayOrBoarding);
			pst.setString(18, bursaryStatus);

			pst.executeUpdate();
			// JOptionPane.showMessageDialog(null, "Successfully Saved " + studentName + "'s
			// Info");

		} catch (Exception e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Please make sure a picture is attached");
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				;
			}
		}

	}

	public void SaveS2StudentInfo() {

		classNumber = fieldID.getText();
		if (classNumber.indexOf("S") != -1) {

			classNumber = classNumber.replace("S1", "S2");
		} else if (classNumber.indexOf("B") != -1) {

			classNumber = classNumber.replace("1B", "2B");
		} else if (classNumber.indexOf("C") != -1) {
			classNumber = classNumber.replace("1C", "2C");

		} else {
			JOptionPane.showMessageDialog(null, "Wrong/Invalid Class Number Detected");
		}

		studentClass = comboBoxClasses.getSelectedItem().toString();
		dormitory = comboBoxDorms.getSelectedItem().toString();
		gender = comboBoxGender.getSelectedItem().toString();
		nationality = comboBoxNationality.getSelectedItem().toString();
		term = comboBoxTermOfAdmission.getSelectedItem().toString();
		religion = comboBox1Religion.getSelectedItem().toString();
		studentName = fieldName.getText();
		district = fieldDistrict.getText();
		county = fieldCounty.getText();
		subcounty = fieldSubCounty.getText();
		parish = fieldParish.getText();
		ward = fieldWardLC1.getText();

		try {
			inputStream = new FileInputStream(new File(ss));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		String sqlStudentInfo = "insert into students_info(year,payment_code,class_number,student_class,student_name,dormitory,term,"
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo,day_boarding,bursary_status) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?,?,?)";
		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentInfo);
			pst.setObject(1, student_year);
			pst.setString(2, paymentcode);
			pst.setString(3, classNumber);
			pst.setString(4, "Senior Two");
			pst.setString(5, studentName);
			pst.setString(6, dormitory);
			pst.setString(7, term);
			pst.setString(8, religion);
			pst.setString(9, nationality);
			pst.setString(10, gender);
			pst.setString(11, district);
			pst.setString(12, county);
			pst.setString(13, subcounty);
			pst.setString(14, parish);
			pst.setString(15, ward);
			pst.setBlob(16, inputStream);
			pst.setString(17, dayOrBoarding);
			pst.setString(18, bursaryStatus);

			pst.executeUpdate();

			AddFeesStudentsRowsToLedger(
					"update student_ledger set year=year+1 where student_class='Senior Two' and payment_code='"
							+ paymentcode + "'");

		} catch (Exception e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Please make sure a picture is attached");
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void SaveS3StudentInfo() {

		classNumber = fieldID.getText();
		if (classNumber.indexOf("S") != -1) {

			classNumber = classNumber.replace("S1", "S3");
		} else if (classNumber.indexOf("B") != -1) {

			classNumber = classNumber.replace("1B", "3B");
		} else if (classNumber.indexOf("C") != -1) {
			classNumber = classNumber.replace("1C", "3C");

		} else {
			JOptionPane.showMessageDialog(null, "Wrong/Invalid Class Number Detected");
		}

		studentClass = comboBoxClasses.getSelectedItem().toString();
		dormitory = comboBoxDorms.getSelectedItem().toString();
		gender = comboBoxGender.getSelectedItem().toString();
		nationality = comboBoxNationality.getSelectedItem().toString();
		term = comboBoxTermOfAdmission.getSelectedItem().toString();
		religion = comboBox1Religion.getSelectedItem().toString();
		studentName = fieldName.getText();
		district = fieldDistrict.getText();
		county = fieldCounty.getText();
		subcounty = fieldSubCounty.getText();
		parish = fieldParish.getText();
		ward = fieldWardLC1.getText();

		try {
			inputStream = new FileInputStream(new File(ss));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		String sqlStudentInfo = "insert into students_info(year,payment_code,class_number,student_class,student_name,dormitory,term,"
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo,day_boarding,bursary_status) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?,?,?)";
		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentInfo);
			pst.setObject(1, student_year);
			pst.setString(2, paymentcode);
			pst.setString(3, classNumber);
			pst.setString(4, "Senior Three");
			pst.setString(5, studentName);
			pst.setString(6, dormitory);
			pst.setString(7, term);
			pst.setString(8, religion);
			pst.setString(9, nationality);
			pst.setString(10, gender);
			pst.setString(11, district);
			pst.setString(12, county);
			pst.setString(13, subcounty);
			pst.setString(14, parish);
			pst.setString(15, ward);
			pst.setBlob(16, inputStream);
			pst.setString(17, dayOrBoarding);
			pst.setString(18, bursaryStatus);
			pst.executeUpdate();
			AddFeesStudentsRowsToLedger(
					"update student_ledger set year=year+2 where student_class='Senior Three' and payment_code='"
							+ paymentcode + "'");

		} catch (Exception e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Please make sure a picture is attached");
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void SaveS4StudentInfo() {

		classNumber = fieldID.getText();
		if (classNumber.indexOf("S") != -1) {

			classNumber = classNumber.replace("S1", "S4");
		} else if (classNumber.indexOf("B") != -1) {

			classNumber = classNumber.replace("1B", "4B");
		} else if (classNumber.indexOf("C") != -1) {
			classNumber = classNumber.replace("1C", "4C");

		} else {
			JOptionPane.showMessageDialog(null, "Wrong/Invalid Class Number Detected");
		}

		studentClass = comboBoxClasses.getSelectedItem().toString();
		dormitory = comboBoxDorms.getSelectedItem().toString();
		gender = comboBoxGender.getSelectedItem().toString();
		nationality = comboBoxNationality.getSelectedItem().toString();
		term = comboBoxTermOfAdmission.getSelectedItem().toString();
		religion = comboBox1Religion.getSelectedItem().toString();
		studentName = fieldName.getText();
		district = fieldDistrict.getText();
		county = fieldCounty.getText();
		subcounty = fieldSubCounty.getText();
		parish = fieldParish.getText();
		ward = fieldWardLC1.getText();

		try {
			inputStream = new FileInputStream(new File(ss));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		String sqlStudentInfo = "insert into students_info(year,payment_code,class_number,student_class,student_name,dormitory,term,"
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo,day_boarding,bursary_status) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?,?,?)";
		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentInfo);
			pst.setObject(1, student_year);
			pst.setString(2, paymentcode);
			pst.setString(3, classNumber);
			pst.setString(4, "Senior Four");
			pst.setString(5, studentName);
			pst.setString(6, dormitory);
			pst.setString(7, term);
			pst.setString(8, religion);
			pst.setString(9, nationality);
			pst.setString(10, gender);
			pst.setString(11, district);
			pst.setString(12, county);
			pst.setString(13, subcounty);
			pst.setString(14, parish);
			pst.setString(15, ward);
			pst.setBlob(16, inputStream);
			pst.setString(17, dayOrBoarding);
			pst.setString(18, bursaryStatus);

			pst.executeUpdate();
			AddFeesStudentsRowsToLedger(
					"update student_ledger set year=year+3 where student_class='Senior Four' and payment_code='"
							+ paymentcode + "'");

		} catch (Exception e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Please make sure a picture is attached");
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void SaveS6StudentInfo() {

		classNumber = fieldID.getText();
		if (classNumber.indexOf("S5A") != -1) {

			classNumber = classNumber.replace("S5A", "S6A");
		} else if (classNumber.indexOf("S5S") != -1) {

			classNumber = classNumber.replace("S5S", "S6S");

		} else {
			JOptionPane.showMessageDialog(null, "Wrong/Invalid Class Number Detected");
		}

		studentClass = comboBoxClasses.getSelectedItem().toString();
		dormitory = comboBoxDorms.getSelectedItem().toString();
		gender = comboBoxGender.getSelectedItem().toString();
		nationality = comboBoxNationality.getSelectedItem().toString();
		term = comboBoxTermOfAdmission.getSelectedItem().toString();
		religion = comboBox1Religion.getSelectedItem().toString();
		studentName = fieldName.getText();
		district = fieldDistrict.getText();
		county = fieldCounty.getText();
		subcounty = fieldSubCounty.getText();
		parish = fieldParish.getText();
		ward = fieldWardLC1.getText();

		try {
			inputStream = new FileInputStream(new File(ss));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String sqlStudentInfo = "insert into students_info(year,payment_code,class_number,student_class,student_name,dormitory,term,"
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo,day_boarding,bursary_status) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?,?,?)";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentInfo);
			pst.setObject(1, student_year);
			pst.setString(2, paymentcode);
			pst.setString(3, classNumber);
			pst.setString(4, "Senior Six");
			pst.setString(5, studentName);
			pst.setString(6, dormitory);
			pst.setString(7, term);
			pst.setString(8, religion);
			pst.setString(9, nationality);
			pst.setString(10, gender);
			pst.setString(11, district);
			pst.setString(12, county);
			pst.setString(13, subcounty);
			pst.setString(14, parish);
			pst.setString(15, ward);
			pst.setBlob(16, inputStream);
			pst.setString(17, dayOrBoarding);
			pst.setString(18, bursaryStatus);
			pst.executeUpdate();
			AddFeesStudentsRowsToLedger(
					"update student_ledger set year=year+1 where student_class='Senior Six' and payment_code='"
							+ paymentcode + "'");

		} catch (Exception e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Please make sure a picture is attached");
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void SaveS3StudentInfoForS2Admission() {

		classNumber = fieldID.getText();
		if (classNumber.indexOf("S") != -1) {

			classNumber = classNumber.replace("S2", "S3");
		} else if (classNumber.indexOf("B") != -1) {

			classNumber = classNumber.replace("2B", "3B");
		} else if (classNumber.indexOf("C") != -1) {
			classNumber = classNumber.replace("2C", "3C");

		} else {
			JOptionPane.showMessageDialog(null, "Wrong/Invalid Class Number Detected");
		}

		studentClass = comboBoxClasses.getSelectedItem().toString();
		dormitory = comboBoxDorms.getSelectedItem().toString();
		gender = comboBoxGender.getSelectedItem().toString();
		nationality = comboBoxNationality.getSelectedItem().toString();
		term = comboBoxTermOfAdmission.getSelectedItem().toString();
		religion = comboBox1Religion.getSelectedItem().toString();
		studentName = fieldName.getText();
		district = fieldDistrict.getText();
		county = fieldCounty.getText();
		subcounty = fieldSubCounty.getText();
		parish = fieldParish.getText();
		ward = fieldWardLC1.getText();

		try {
			inputStream = new FileInputStream(new File(ss));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String sqlStudentInfo = "insert into students_info(year,class_number,payment_code,student_class,student_name,dormitory,term,"
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo,day_boarding,bursary_status) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?,?,?)";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentInfo);

			pst.setObject(1, student_year);
			pst.setString(2, classNumber);
			pst.setString(3, paymentcode);
			pst.setString(4, "Senior Three");
			pst.setString(5, studentName);
			pst.setString(6, dormitory);
			pst.setString(7, term);
			pst.setString(8, religion);
			pst.setString(9, nationality);
			pst.setString(10, gender);
			pst.setString(11, district);
			pst.setString(12, county);
			pst.setString(13, subcounty);
			pst.setString(14, parish);
			pst.setString(15, ward);
			pst.setBlob(16, inputStream);
			pst.setString(17, dayOrBoarding);
			pst.setString(18, bursaryStatus);
			
			pst.executeUpdate();
			AddFeesStudentsRowsToLedger(
					"update student_ledger set year=year+1 where student_class='Senior Three' and payment_code='"
							+ paymentcode + "'");

		} catch (Exception e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Please make sure a picture is attached");
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void SaveS4StudentInfoForS2Admission() {

		classNumber = fieldID.getText();
		if (classNumber.indexOf("S") != -1) {

			classNumber = classNumber.replace("S2", "S4");
		} else if (classNumber.indexOf("B") != -1) {

			classNumber = classNumber.replace("2B", "4B");
		} else if (classNumber.indexOf("C") != -1) {
			classNumber = classNumber.replace("2C", "4C");

		} else {
			JOptionPane.showMessageDialog(null, "Wrong/Invalid Class Number Detected");
		}

		studentClass = comboBoxClasses.getSelectedItem().toString();
		dormitory = comboBoxDorms.getSelectedItem().toString();
		gender = comboBoxGender.getSelectedItem().toString();
		nationality = comboBoxNationality.getSelectedItem().toString();
		term = comboBoxTermOfAdmission.getSelectedItem().toString();
		religion = comboBox1Religion.getSelectedItem().toString();
		studentName = fieldName.getText();
		district = fieldDistrict.getText();
		county = fieldCounty.getText();
		subcounty = fieldSubCounty.getText();
		parish = fieldParish.getText();
		ward = fieldWardLC1.getText();

		try {
			inputStream = new FileInputStream(new File(ss));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String sqlStudentInfo = "insert into students_info(year,class_number,payment_code,student_class,student_name,dormitory,term,"
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo,day_boarding,bursary_status) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?,?,?)";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentInfo);

			pst.setObject(1, student_year);
			pst.setString(2, classNumber);
			pst.setString(3, paymentcode);
			pst.setString(4, "Senior Four");
			pst.setString(5, studentName);
			pst.setString(6, dormitory);
			pst.setString(7, term);
			pst.setString(8, religion);
			pst.setString(9, nationality);
			pst.setString(10, gender);
			pst.setString(11, district);
			pst.setString(12, county);
			pst.setString(13, subcounty);
			pst.setString(14, parish);
			pst.setString(15, ward);
			pst.setBlob(16, inputStream);
			pst.setString(17, dayOrBoarding);
			pst.setString(18, bursaryStatus);

			pst.executeUpdate();
			AddFeesStudentsRowsToLedger(
					"update student_ledger set year=year+2 where student_class='Senior Four' and payment_code='"
							+ paymentcode + "'");

		} catch (Exception e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Please make sure a picture is attached");
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void SaveS4StudentInfoForS3Admission() {

		classNumber = fieldID.getText();
		if (classNumber.indexOf("S") != -1) {

			classNumber = classNumber.replace("S3", "S4");
		} else if (classNumber.indexOf("B") != -1) {

			classNumber = classNumber.replace("3B", "4B");
		} else if (classNumber.indexOf("C") != -1) {
			classNumber = classNumber.replace("3C", "4C");

		} else {
			JOptionPane.showMessageDialog(null, "Wrong/Invalid Class Number Detected");
		}

		studentClass = comboBoxClasses.getSelectedItem().toString();
		dormitory = comboBoxDorms.getSelectedItem().toString();
		gender = comboBoxGender.getSelectedItem().toString();
		nationality = comboBoxNationality.getSelectedItem().toString();
		term = comboBoxTermOfAdmission.getSelectedItem().toString();
		religion = comboBox1Religion.getSelectedItem().toString();
		studentName = fieldName.getText();
		district = fieldDistrict.getText();
		county = fieldCounty.getText();
		subcounty = fieldSubCounty.getText();
		parish = fieldParish.getText();
		ward = fieldWardLC1.getText();

		try {
			inputStream = new FileInputStream(new File(ss));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String sqlStudentInfo = "insert into students_info(year,class_number,payment_code,student_class,student_name,dormitory,term,"
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo,day_boarding,bursary_status) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?,?,?)";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentInfo);

			pst.setObject(1, student_year);
			pst.setString(2, classNumber);
			pst.setString(3, paymentcode);
			pst.setString(4, "Senior Four");
			pst.setString(5, studentName);
			pst.setString(6, dormitory);
			pst.setString(7, term);
			pst.setString(8, religion);
			pst.setString(9, nationality);
			pst.setString(10, gender);
			pst.setString(11, district);
			pst.setString(12, county);
			pst.setString(13, subcounty);
			pst.setString(14, parish);
			pst.setString(15, ward);
			pst.setBlob(16, inputStream);
			pst.setString(17, dayOrBoarding);
			pst.setString(18, bursaryStatus);
			pst.executeUpdate();
			AddFeesStudentsRowsToLedger(
					"update student_ledger set year=year+1 where student_class='Senior Four' and payment_code='"
							+ paymentcode + "'");

		} catch (Exception e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Please make sure a picture is attached");
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void SaveParentInfo() {

		String sqlParentsInfo = "insert into parents_info(class_number,payment_code,student_class,student_name,"
				+ "fathers_name,mothers_name,guardians_name,sponsors_name,fathers_phone,mothers_phone,"
				+ "guardians_phone,sponsors_phone,parent_email,parent_address) values('" + fieldID.getText() + "','"
				+ paymentcode + "','" + comboBoxClasses.getSelectedItem() + "'," + "'" + fieldName.getText() + "','"
				+ fieldParentName.getText() + "','" + fieldMotherName.getText() + "','" + fieldGuardianName.getText()
				+ "'," + "'" + fieldSponsorName.getSelectedItem() + "','" + fieldFatherPhone.getText() + "','"
				+ fieldMothersPhone.getText() + "','" + fieldGuardianPhone.getText() + "'," + "'"
				+ fieldSponsorPhone.getText() + "','" + fieldParentEmail.getText() + "','" + fieldAddress.getText()
				+ "')";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlParentsInfo);

			pst.executeUpdate();
			// JOptionPane.showMessageDialog(null, "Successfully Saved " + studentName + "'s
			// Parents Info");

		} catch (Exception e) {

			e.printStackTrace();
			// JOptionPane.showMessageDialog(null, "Please make sure a picture
			// is attached");
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void SaveUCEParticulars() {
		try {
			inputStream = new FileInputStream(new File(ss));
			// Blob blobInput=(Blob) inputStream;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"insert into uce_particulars(class_number,payment_code,student_class,student_name,"
							+ "index_number,former_school,english,geography,mathematics,physic,agriculture,chemistry,biology,td,fine_art,cre,commerce,history,"
							+ "wood_work,metal_work,additional_mathematics,literature,others,aggregate,division,photo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pst.setString(1, classNumber);
			pst.setString(2, paymentcode);
			pst.setString(3, studentClass);
			pst.setString(4, studentName);
			pst.setString(5, fieldIndexNumber.getText());
			pst.setString(6, fieldFormerSchool.getText());
			pst.setString(7, fieldEnglish.getText());
			pst.setString(8, fieldGeography.getText());
			pst.setString(9, fieldMathematics.getText());
			pst.setString(10, fieldPhysics.getText());
			pst.setString(11, fieldAgriculture.getText());
			pst.setString(12, fieldChemistry.getText());
			pst.setString(13, fieldBiology.getText());
			pst.setString(14, fieldTD.getText());
			pst.setString(15, fieldFineArts.getText());
			pst.setString(16, fieldCRE.getText());
			pst.setString(17, fieldCommerce.getText());
			pst.setString(18, fieldHistory.getText());
			pst.setString(19, fieldWoodWork.getText());
			pst.setString(20, fieldMetalWork.getText());
			pst.setString(21, fieldAMaths.getText());
			pst.setString(22, fieldLiterauture.getText());
			pst.setString(23, fieldOthers.getText());
			pst.setString(24, fieldAggregate.getText());
			pst.setString(25, fieldDivision.getSelectedItem().toString());
			pst.setBlob(26, inputStream);

			pst.executeUpdate();

			// JOptionPane.showMessageDialog(null, "Student O-Level Subejects Submitted
			// Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}

	}

	public void SavePLEParticulars() {

		try {
			inputStream = new FileInputStream(new File(ss));
			// Blob blobInput=(Blob) inputStream;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"insert into ple_particulars(class_number,payment_code,student_class,student_name,"
							+ "index_number,former_school,mathematics,english,science,social_studies,aggregate,division,photo) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pst.setString(1, classNumber);
			pst.setString(2, paymentcode);
			pst.setString(3, studentClass);
			pst.setString(4, studentName);
			pst.setString(5, fieldIndexNumber.getText());
			pst.setString(6, fieldFormerSchool.getText());
			pst.setString(7, fieldP7English.getText());
			pst.setString(8, fieldP7Mathematics.getText());
			pst.setString(9, fieldP7Science.getText());
			pst.setString(10, fieldP7SocialStudies.getText());
			pst.setString(11, fieldP7Aggregate.getText());
			pst.setString(12, fieldP7Division.getSelectedItem().toString());
			pst.setBlob(13, inputStream);

			pst.executeUpdate();

			// JOptionPane.showMessageDialog(null, "Student PLE Particulars Submitted
			// Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}

	}

	public void SaveUCESubjects() {

		try {
			inputStream = new FileInputStream(new File(ss));
			// Blob blobInput=(Blob) inputStream;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement("insert into o_level_subjects(class_number,student_class,student_name,"
					+ "english,geography,mathematics,physics,agriculture,chemistry,biology,td,fine_art,cre,commerce,history,account,"
					+ "wood_work,metal_work,music,literature,kiswahili,photo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pst.setString(1, classNumber);
			pst.setString(2, studentClass);
			pst.setString(3, studentName);
			pst.setInt(4, english);
			pst.setInt(5, geog);
			pst.setInt(6, math);
			pst.setInt(7, physics);
			pst.setInt(8, agric);
			pst.setInt(9, chem);
			pst.setInt(10, biology);
			pst.setInt(11, techdrawing);
			pst.setInt(12, fineart);
			pst.setInt(13, cre);
			pst.setInt(14, commerce);
			pst.setInt(15, history);
			pst.setInt(16, accounts);
			pst.setInt(17, woodwork);
			pst.setInt(18, metalwork);
			pst.setInt(19, music);
			pst.setInt(20, literature);
			pst.setInt(21, kiswahili);
			pst.setBlob(22, inputStream);

			pst.executeUpdate();

			// JOptionPane.showMessageDialog(null, "Student O-Level Subejects Submitted
			// Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}

	}

	public void SaveUACESubjects() {

		try {
			inputStream = new FileInputStream(new File(ss));
			// Blob blobInput=(Blob) inputStream;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement("insert into a_level_subjects(class_number,student_class,student_name,"
					+ "s_computer,s_mathematics,general_paper,geography,mathematics,physics,agriculture,chemistry,biology,td,fine_art,divinity,economics,history,entrepreneurship,"
					+ "literature,photo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pst.setString(1, classNumber);
			pst.setString(2, studentClass);
			pst.setString(3, studentName);
			pst.setInt(4, computer);
			pst.setInt(5, submath);
			pst.setInt(6, GP);
			pst.setInt(7, GeographyAlevel);
			pst.setInt(8, MathematicsAlevel);
			pst.setInt(9, PhysicsAlevel);
			pst.setInt(10, AgricultureAlevel);
			pst.setInt(11, ChemistryAlevel);
			pst.setInt(12, BiologyAlevel);
			pst.setInt(13, TDAlevel);
			pst.setInt(14, ArtAlevel);
			pst.setInt(15, divinity);
			pst.setInt(16, Economics);
			pst.setInt(17, HistoryAlevel);
			pst.setInt(18, EntAlvel);
			pst.setInt(19, LiteratureAlevel);
			pst.setBlob(20, inputStream);

			pst.executeUpdate();

			// JOptionPane.showMessageDialog(null, "Student A-Level Subejects Submitted
			// Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}

	}

	public void saveHealthInfo() {
		try {
			inputStream = new FileInputStream(new File(ss));
			// Blob blobInput=(Blob) inputStream;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"insert into dispensary(class_number,payment_code,student_class,student_name,fathers_name,mothers_name,"
							+ "guardians_name,sponsors_name,fathers_phone,mothers_phone,"
							+ "guardians_phone,sponsor_phone,dormitory,medical_history,disability,allergy,special_sickness,photo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pst.setString(1, classNumber);
			pst.setString(2, paymentcode);
			pst.setString(3, studentClass);
			pst.setString(4, studentName);
			pst.setString(5, fieldParentName.getText());
			pst.setString(6, fieldMotherName.getText());
			pst.setString(7, fieldGuardianName.getText());
			pst.setString(8, fieldSponsorName.getSelectedItem().toString());
			pst.setString(9, fieldFatherPhone.getText());
			pst.setString(10, fieldMothersPhone.getText());
			pst.setString(11, fieldGuardianPhone.getText());
			pst.setString(12, fieldSponsorPhone.getText());
			pst.setString(14, fieldIllnessExplanation.getText());
			pst.setString(13, comboBoxDorms.getSelectedItem().toString());
			pst.setString(15, fieldDisabilityExplanation.getText());
			pst.setString(16, fieldAllergyExplanation.getText());
			pst.setString(17, fieldSpecialCaseExplanation.getText());
			pst.setBlob(18, inputStream);

			pst.executeUpdate();

			// JOptionPane.showMessageDialog(null, "Student Health Info Submitted
			// Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

	public void saveToDorm() {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement("insert into `" + comboBoxDorms.getSelectedItem()
					+ "`(class_number,payment_code,student_class,student_name) values(?,?,?,?)");

			pst.setString(1, fieldID.getText());
			pst.setString(2, fieldCode.getText());
			pst.setString(3, comboBoxClasses.getSelectedItem().toString());
			pst.setString(4, fieldName.getText());

			pst.executeUpdate();

			// JOptionPane.showMessageDialog(null, "Student Dorm Info Submitted
			// Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

	public void saveToClassInWhicYouAreReporting() {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement("insert into `" + comboBoxClasses.getSelectedItem()
					+ "`(payment_code,class_number,student_class,student_name,date_of_birth,address,sponsor,dormitory,"
					+ "term,year,fathers_contact,mothers_contact,guardians_contact,fathers_name,mothers_name,guardians_name) values(?,?,?,?,"
					+ "'" + convertFromUtilToSQLDate(dateChooserDOB.getDate()) + "',?,?,?,?,?,?,?,?,?,?,?)");
			pst.setString(1, paymentcode);
			pst.setString(2, fieldID.getText());
			pst.setString(3, comboBoxClasses.getSelectedItem().toString());
			pst.setString(4, studentName);
			pst.setString(5, fieldAddress.getText());
			pst.setString(6, fieldSponsorName.getSelectedItem().toString());
			pst.setString(7, comboBoxDorms.getSelectedItem().toString());
			pst.setString(8, comboBoxTermOfAdmission.getSelectedItem().toString());
			pst.setLong(9, yearChooser.getYear());
			pst.setString(10, fieldFatherPhone.getText());
			pst.setString(11, fieldMothersPhone.getText());
			pst.setString(12, fieldGuardianPhone.getText());
			pst.setString(13, fieldParentName.getText());
			pst.setString(14, fieldMotherName.getText());
			pst.setString(15, fieldGuardianName.getText());

			pst.executeUpdate();

			// JOptionPane.showMessageDialog(null, "Student Added To His Class
			// Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

	public void saveStudentPaymentInfoToLedger() {

		sqlStudentPaymentInfo = "update student_ledger set credit='" + fieldFeesPayment.getText() + "',receipt_number='"
				+ fieldReceiptNumber.getText() + "' where " + "year='" + yearChooser.getYear() + "' and payment_code='"
				+ paymentcode + "' and term='" + comboBoxTermOfAdmission.getSelectedItem() + "'";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentPaymentInfo);

			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, fieldName.getText() + " Admitted Successfully");

		} catch (Exception e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "" + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void AddFeesStudentsRowsToLedger(String querries) {

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

	public void saveToBank() {

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"insert into `" + comboBoxBanks.getSelectedItem() + "`(account_name,debit) values('"
							+ comboBoxSponsorName.getSelectedItem() + "','" + fieldFeesPayment.getText() + "')");

			pst.executeUpdate();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void loadAndUpdateYearForS4ClassForS2Admission() {

		sqlStudentPaymentInfo = "select class_name from `student_classes` where id =4";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentPaymentInfo);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (fieldID.getText().indexOf("S") != -1) {
					AddFeesStudentsRowsToLedger("update student_ledger set year=year+2 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else if (fieldID.getText().indexOf("B") != -1) {
					AddFeesStudentsRowsToLedger("update student_ledger set year=year+2 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else if (fieldID.getText().indexOf("C") != -1) {
					AddFeesStudentsRowsToLedger("update student_ledger set year=year+2 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else {
					JOptionPane.showMessageDialog(null, "Wrong/Invalid Class Number Detected");
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void loadAndUpdateYearForS4ClassForS3Admission() {

		sqlStudentPaymentInfo = "select class_name from `student_classes` where id =4";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentPaymentInfo);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (fieldID.getText().indexOf("S") != -1) {
					AddFeesStudentsRowsToLedger("update student_ledger set year=year+1 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else if (fieldID.getText().indexOf("B") != -1) {
					AddFeesStudentsRowsToLedger("update student_ledger set year=year+1 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else if (fieldID.getText().indexOf("C") != -1) {
					AddFeesStudentsRowsToLedger("update student_ledger set year=year+1 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else {
					JOptionPane.showMessageDialog(null, "Wrong/Invalid Class Number Detected");
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void loadAndUpdateYearForS3Class() {

		sqlStudentPaymentInfoS3 = "select class_name from `student_classes` where id =3";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentPaymentInfoS3);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (fieldID.getText().indexOf("S") != -1) {

					AddFeesStudentsRowsToLedger("update student_ledger set year=year+2 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else if (fieldID.getText().indexOf("B") != -1) {
					AddFeesStudentsRowsToLedger("update student_ledger set year=year+2 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else if (fieldID.getText().indexOf("C") != -1) {
					AddFeesStudentsRowsToLedger("update student_ledger set year=year+2 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else {
					JOptionPane.showMessageDialog(null, "Wrong/Invalid Class Number Detected");
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void loadAndUpdateYearForS3ClassForS2Admission() {

		sqlStudentPaymentInfoS3 = "select class_name from `student_classes` where id =3)";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentPaymentInfoS3);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (fieldID.getText().indexOf("S") != -1) {
					AddFeesStudentsRowsToLedger("update student_ledger set year=year+1 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else if (fieldID.getText().indexOf("B") != -1) {
					AddFeesStudentsRowsToLedger("update student_ledger set year=year+1 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else if (fieldID.getText().indexOf("C") != -1) {
					AddFeesStudentsRowsToLedger("update student_ledger set year=year+1 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else {
					JOptionPane.showMessageDialog(null, "Wrong/Invalid Class Number Detected");
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void loadAndUpdateYearForS2Class() {

		sqlStudentPaymentInfo = "select class_name from `student_classes` where id =2";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentPaymentInfo);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (fieldID.getText().indexOf("S") != -1) {
					AddFeesStudentsRowsToLedger("update student_ledger set year=year+1 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else if (fieldID.getText().indexOf("B") != -1) {
					AddFeesStudentsRowsToLedger("update student_ledger set year=year+1 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else if (fieldID.getText().indexOf("C") != -1) {
					AddFeesStudentsRowsToLedger("update student_ledger set year=year+1 where student_class='"
							+ rs.getString(1) + "' and payment_code='" + paymentcode + "' order by id DESC LIMIT 3");

				} else {
					JOptionPane.showMessageDialog(null, "Wrong/Invalid Class Number Detected");
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
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

	public void displayInTextField(JTextField field, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				field.setText(rs.getString(1));
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

	public void updateSubjects() {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			int rows = table.getRowCount();
			conn = CashBookController.getConnection();

			for (int row = 0; row < rows; row++) {

				subjects = (String) table.getValueAt(row, 0);
				Boolean name = (Boolean) table.getValueAt(row, 1);

				pst = conn
						.prepareStatement("update subjects_offered set `" + subjects + "`=? order by id desc limit 1");
				pst.setBoolean(1, name);

				pst.addBatch();
			}
			pst.executeBatch();

			// JOptionPane.showMessageDialog(null, "Subjects Saved Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

	public void updateSubjectsStudents() {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;

			conn = CashBookController.getConnection();

			for (int j = 0; j < table.getModel().getRowCount(); j++) {

				String headingSubject = table.getValueAt(j, 0).toString();
				String sqlStatement = "update subjects_offered set `" + headingSubject + "`=? where payment_code='"
						+ paymentcode + "'";
				Boolean valueSelected = (Boolean) table.getValueAt(j, 1);
				pst = conn.prepareStatement(sqlStatement);
				if (Boolean.TRUE.equals(valueSelected)) {
					pst.setObject(1, valueSelected);
					pst.executeUpdate();
				} else {
					pst.setObject(1, valueSelected);
					pst.executeUpdate();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

	public void updateSubjectsStudentsAlevel() {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;

			conn = CashBookController.getConnection();

			for (int j = 0; j < tableA.getModel().getRowCount(); j++) {

				String headingSubject = tableA.getValueAt(j, 0).toString();
				String sqlStatement = "update subjects_offereda set `" + headingSubject + "`=? where payment_code='"
						+ paymentcode + "'";
				Boolean valueSelected = (Boolean) tableA.getValueAt(j, 1);
				pst = conn.prepareStatement(sqlStatement);
				if (Boolean.TRUE.equals(valueSelected)) {
					pst.setObject(1, valueSelected);
					pst.executeUpdate();
				} else {
					pst.setObject(1, valueSelected);
					pst.executeUpdate();
				}
			}

			JOptionPane.showMessageDialog(null, fieldName.getText() + "'s Data Updated Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

}
