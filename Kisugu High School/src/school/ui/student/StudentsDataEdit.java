package school.ui.student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import java.util.Collections;
import java.util.Locale;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import com.github.sarxos.webcam.Webcam;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

import school.ui.admission.AdmissionDetails.BlinkLabel;
import school.ui.admission.TrialCheckBox.MyTableModel;
import school.ui.finances.CashBookController;
import school.ui.jdbc.JavaDatabaseSelectStatements;

public class StudentsDataEdit extends JDialog {

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

	public JTextField fieldCode;

	private String paymentcode;

	private int student_year;

	private Connection con;

	private ResultSet rs;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new StudentsDataEdit();
	}

	public StudentsDataEdit() {

		setTitle("Edit Students Personal Data");
		setSize(1345, 550);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		panelMainHolder = new JPanel();
		panelMainHolder.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		add(panelMainHolder);

		panelLeftHolder = new JPanel();
		panelLeftHolder.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panelLeftHolder.setPreferredSize(new Dimension(1015, 500));
		Border bodaforLeftHolder = BorderFactory.createRaisedBevelBorder();
		panelLeftHolder.setBorder(bodaforLeftHolder);
		panelLeftHolder.setBackground(new Color(0, 102, 102));
		panelMainHolder.add(panelLeftHolder);

		panelRightHolder = new JPanel();
		panelRightHolder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelRightHolder.setPreferredSize(new Dimension(295, 500));
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

		panelHealthInfo = new JPanel();
		panelHealthInfo.setBorder(new TitledBorder("Health Info"));
		panelHealthInfo.setPreferredSize(new Dimension(1005, 200));
		panelHealthInfo.setBackground(new Color(0, 102, 102));
		panelLeftHolder.add(panelHealthInfo);

		btnAdmitStudent = new JButton("Save Changes");
		btnAdmitStudent.setPreferredSize(new Dimension(150, 30));
		btnAdmitStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				updateStudentInfo();
				SaveParentInfo();
				saveHealthInfo();
				updateSubjectsStudents();
				updateSubjectsStudentsAlevel();
				
			}
		});

		panelSubjectsOlevel = new JPanel();
		panelSubjectsOlevel.setBorder(new TitledBorder("O-Level Subjects Info"));
		panelSubjectsOlevel.setPreferredSize(new Dimension(280, 220));
		panelSubjectsOlevel.setBackground(new Color(0, 102, 102));
		panelRightHolder.add(panelSubjectsOlevel);
		panelSubjectsOlevel.setVisible(true);

		panelSubjectsAlevel = new JPanel();
		panelSubjectsAlevel.setBorder(new TitledBorder("A-Level Subjects Info"));
		panelSubjectsAlevel.setPreferredSize(new Dimension(280, 220));
		panelSubjectsAlevel.setBackground(new Color(0, 102, 102));
		panelRightHolder.add(panelSubjectsAlevel);
		panelSubjectsAlevel.setVisible(true);

		panelRightHolder.add(btnAdmitStudent);

		////////////// Subjects For O-Level/////////////////////

		///////////// Using JTable////////////////

		MyTableModel model = new MyTableModel();
		model.addRow(new Object[] { "English", false });
		model.addRow(new Object[] { "Mathematics", false });
		model.addRow(new Object[] { "History", false });
		model.addRow(new Object[] { "Agriculture", false });
		table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(180);
		
		JScrollPane scroller1=new JScrollPane(table);
		scroller1.setPreferredSize(new Dimension(270, 215));

		panelSubjectsOlevel.add(scroller1);

		/////////////// A-Level Subjects//////////////////////////////////////

		////////////// Using JTable ///////////////////////////////////////////

		MyTableModel modelA = new MyTableModel();
		modelA.addRow(new Object[] { "General Paper", false });
		modelA.addRow(new Object[] { "Mathematics", false });
		modelA.addRow(new Object[] { "Geography", false });
		modelA.addRow(new Object[] { "Biology", false });
		tableA = new JTable(modelA);
		tableA.getColumnModel().getColumn(0).setPreferredWidth(180);
		JScrollPane scroller2=new JScrollPane(tableA);
		scroller2.setPreferredSize(new Dimension(270, 215));
		panelSubjectsAlevel.add(scroller2);

		

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

		Dimension dimFields = new Dimension(130, 25);
		Dimension dimLabels = new Dimension(100, 25);

		JLabel labelCode = new JLabel();
		labelCode.setText("Payment Code:");
		labelCode.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelCode);

		fieldCode = new JTextField();
		fieldCode.setPreferredSize(dimFields);
		panelStudentInfo.add(fieldCode);
		fieldCode.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

				callStudentsDataFromStudentsInfo();
				callStudentsDateFromParentsInfo();
				callStudentsDataFromHealthInfo();
				callStudentsDataFromALevelSubjects();
				callStudentsDataFromOLevelSubjects();

			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		panelStudentInfo.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel labelID = new JLabel();
		labelID.setText("Class Number:");
		labelID.setPreferredSize(dimLabels);
		panelStudentInfo.add(labelID);

		fieldID = new JTextField();
		fieldID.setPreferredSize(dimFields);
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
		fieldFeesPayment.setEditable(false);

		JLabel labelFeesBal = new JLabel();
		labelFeesBal.setText("Choose Year:");
		labelFeesBal.setPreferredSize(dimLabels);

		panelParentInfo.add(labelFeesBal);

		yearChooser = new JYearChooser();
		yearChooser.getYear();
		yearChooser.setPreferredSize(dimFields);
		panelParentInfo.add(yearChooser);
		yearChooser.setEnabled(false);

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
		comboBoxBanks.setEnabled(false);

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
		comboBoxSponsorName.setEnabled(false);
		panelParentInfo.add(comboBoxSponsorName);

		JLabel labelYear = new JLabel();
		labelYear.setText("Receipt Number:");
		labelYear.setPreferredSize(dimLabels);
		panelParentInfo.add(labelYear);

		fieldReceiptNumber = new JTextField();
		fieldReceiptNumber.setPreferredSize(dimFields);
		panelParentInfo.add(fieldReceiptNumber);
		fieldReceiptNumber.setEditable(false);

		JLabel labelBankAccount = new JLabel();
		labelBankAccount.setText("Bank Account:");
		labelBankAccount.setPreferredSize(dimLabels);
		panelParentInfo.add(labelBankAccount);

		fieldBankAccount = new JTextField();
		fieldBankAccount.setPreferredSize(dimFields);
		fieldBankAccount.setEditable(false);
		panelParentInfo.add(fieldBankAccount);

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

	protected void callStudentsDataFromOLevelSubjects() {
		// TODO Auto-generated method stub
		String sql = "select * from subjects_offered where payment_code='" + fieldCode.getText() + "'";
		try {

			con = CashBookController.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				
				for(int row=0; row<table.getModel().getRowCount(); row++) {
					
					
					String subjectName=table.getValueAt(row, 0).toString();
					
					
					if(rs.getInt(subjectName)==1) {
						table.setValueAt(Boolean.TRUE, row, 1);
						
						
					}else {
						table.setValueAt(Boolean.FALSE, row, 1);
					}
					
					
					
				}


			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
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

	}

	protected void callStudentsDataFromALevelSubjects() {
		// TODO Auto-generated method stub
		
		String sql = "select * from subjects_offereda where payment_code='" + fieldCode.getText() + "'";
		try {

			con = CashBookController.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				
				for(int row=0; row<tableA.getModel().getRowCount(); row++) {
					
					
					String subjectName=tableA.getValueAt(row, 0).toString();
					
					
					if(rs.getInt(subjectName)==1) {
						tableA.setValueAt(Boolean.TRUE, row, 1);
						
						
					}else {
						tableA.setValueAt(Boolean.FALSE, row, 1);
					}
					
					
					
				}


			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
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

	}

	protected void callStudentsDateFromParentsInfo() {
		// TODO Auto-generated method stub
		String sql = "select * from parents_info where payment_code='" + fieldCode.getText() + "'";
		try {

			con = CashBookController.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {

				fieldAddress.setText(rs.getString("parent_address"));
				fieldFatherPhone.setText(rs.getString("fathers_phone"));
				fieldMothersPhone.setText(rs.getString("mothers_phone"));
				fieldParentName.setText(rs.getString("fathers_name"));
				fieldMotherName.setText(rs.getString("mothers_name"));
				fieldParentEmail.setText(rs.getString("parent_email"));
				fieldGuardianName.setText(rs.getString("guardians_name"));
				fieldGuardianPhone.setText(rs.getString("guardians_phone"));
				fieldSponsorName.setSelectedItem(rs.getString("sponsors_name"));
				fieldSponsorPhone.setText(rs.getString("sponsors_phone"));

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
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

	}

	protected void callStudentsDataFromHealthInfo() {
		// TODO Auto-generated method stub
		
		String sql = "select * from dispensary where payment_code='" + fieldCode.getText() + "'";
		try {

			con = CashBookController.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {

				fieldAllergyExplanation.setText(rs.getString("allergy"));
				fieldDisabilityExplanation.setText(rs.getString("disability"));
				fieldSpecialCaseExplanation.setText(rs.getString("special_sickness"));
				fieldIllnessExplanation.setText(rs.getString("medical_history"));

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
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


	}

	protected void callStudentsDataFromStudentsInfo() {
		// TODO Auto-generated method stub

		String sql = "select * from students_info where payment_code='" + fieldCode.getText() + "'";
		try {

			con = CashBookController.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {

				fieldName.setText(rs.getString("student_name"));
				fieldID.setText(rs.getString("class_number"));
				fieldCounty.setText(rs.getString("county"));
				fieldSubCounty.setText(rs.getString("sub_county"));
				fieldParish.setText(rs.getString("parish"));
				fieldWardLC1.setText(rs.getString("ward_lc1"));
				fieldDistrict.setText(rs.getString("home_district"));
				dateChooserDOB.setDate(rs.getDate("date_of_birth"));
				comboBox1Religion.setSelectedItem(rs.getString("religion"));
				comboBoxClasses.setSelectedItem(rs.getString("student_class"));
				comboBoxDorms.setSelectedItem(rs.getString("dormitory"));
				comboBoxNationality.setSelectedItem(rs.getString("nationality"));
				comboBoxGender.setSelectedItem(rs.getString("gender"));
				comboBoxTermOfAdmission.setSelectedItem(rs.getString("term"));

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
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

	public void updateStudentInfo() {

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

		
		String sqlStudentInfo = "update students_info set year=?,payment_code=?,class_number=?,student_class=?,student_name=?,dormitory=?,term=?,"
				+ "date_of_birth='"+ (convertFromUtilToSQLDate(dateChooserDOB.getDate()))+"',religion=?,nationality=?,gender=?,home_district=?,"
						+ "county=?,sub_county=?,parish=?,ward_lc1=? where payment_code='"+paymentcode+"'";

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

			pst.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Student info did not update successfully");
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
		if (classNumber.indexOf("A") != -1) {

			classNumber = classNumber.replace("1A", "2A");
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

		String sqlStudentInfo = "insert into students_info(year,class_number,payment_code,student_class,student_name,dormitory,term,"
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?)";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentInfo);

			pst.setObject(1, student_year);
			pst.setString(2, classNumber);
			pst.setString(3, paymentcode);
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
		if (classNumber.indexOf("A") != -1) {

			classNumber = classNumber.replace("1A", "3A");
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

		String sqlStudentInfo = "insert into students_info(year,class_number,payment_code,student_class,student_name,dormitory,term,"
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?)";

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
		if (classNumber.indexOf("A") != -1) {

			classNumber = classNumber.replace("1A", "4A");
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

		String sqlStudentInfo = "insert into students_info(year,class_number,payment_code,student_class,student_name,dormitory,term,"
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?)";

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
		if (classNumber.indexOf("5Ar") != -1) {

			classNumber = classNumber.replace("5A", "6A");
		} else if (classNumber.indexOf("5Sc") != -1) {

			classNumber = classNumber.replace("5S", "6S");

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
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?)";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentInfo);

			pst.setObject(1, student_year);
			pst.setString(2, classNumber);
			pst.setString(3, paymentcode);
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
		if (classNumber.indexOf("A") != -1) {

			classNumber = classNumber.replace("2A", "3A");
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
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?)";

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
		if (classNumber.indexOf("A") != -1) {

			classNumber = classNumber.replace("2A", "4A");
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
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?)";

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
		if (classNumber.indexOf("A") != -1) {

			classNumber = classNumber.replace("3A", "4A");
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
				+ "date_of_birth,religion,nationality,gender,home_district,county,sub_county,parish,ward_lc1,photo) "
				+ "values(?,?,?,?,?,?,?," + "'" + (convertFromUtilToSQLDate(dateChooserDOB.getDate()))
				+ "',?,?,?,?,?,?,?,?,?)";

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

		String sqlParentsInfo = "update parents_info set class_number='" + fieldID.getText() + "',payment_code='"
				+ paymentcode + "',student_class='" + comboBoxClasses.getSelectedItem() + "',student_name='" + fieldName.getText() + "',fathers_name='"
				+ fieldParentName.getText() + "',mothers_name='" + fieldMotherName.getText() + "',guardians_name='" + fieldGuardianName.getText()
				+ "',sponsors_name='" + fieldSponsorName.getSelectedItem() + "',fathers_phone='" + fieldFatherPhone.getText() + "',mothers_phone='"
				+ fieldMothersPhone.getText() + "',guardians_phone='" + fieldGuardianPhone.getText() + "',sponsors_phone=" + "'"
				+ fieldSponsorPhone.getText() + "',parent_email='" + fieldParentEmail.getText() + "',parent_address='" + fieldAddress.getText()
				+ "' WHERE payment_code='"+paymentcode+"'";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlParentsInfo);

			pst.executeUpdate();
			} catch (Exception e) {

			e.printStackTrace();
			 JOptionPane.showMessageDialog(null, "Staffs Info Is Not Updated Successfully");
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

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"update dispensary set class_number=?,payment_code=?,student_class=?,student_name=?,fathers_name=?,mothers_name=?,"
							+ "guardians_name=?,sponsors_name=?,fathers_phone=?,mothers_phone=?,"
							+ "guardians_phone=?,sponsor_phone=?,dormitory=?,medical_history=?,disability=?,allergy=?,special_sickness=?");
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

			pst.executeUpdate();


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

	public void updateSubjectsStudents() {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;

			conn = CashBookController.getConnection();

			for (int j = 0; j < table.getModel().getRowCount(); j++) {

				String headingSubject = table.getValueAt(j, 0).toString();
				String sqlStatement = "update subjects_offered set `" + headingSubject + "`=? where payment_code='"+paymentcode+"'";
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
				String sqlStatement = "update subjects_offereda set `" + headingSubject + "`=? where payment_code='"+paymentcode+"'";
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

			 JOptionPane.showMessageDialog(null, fieldName.getText()+"'s Data Updated Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

}
