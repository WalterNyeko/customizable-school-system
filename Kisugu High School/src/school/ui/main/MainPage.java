
package school.ui.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.sarxos.webcam.Webcam;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

import clarion.academics.ui.AcademicPanel;
import clarion.academics.ui.AcademicsMarksEntries;
import clarion.academics.ui.AutoGeneratingAndPrintingReportCards;
import clarion.academics.ui.GiftedAndAtRiskStudents;
import clarion.academics.ui.GraphicalStaffsAnalysis;
import clarion.academics.ui.GraphicalStudentsAnalysis;
import clarion.academics.ui.GraphicalSubjectsProgress;
import clarion.academics.ui.IndividualTestsAnalysis;
import clarion.academics.ui.StaffsProgressAnalysisData;
import clarion.academics.ui.StudentsProgressAnalysisData;
import clarion.academics.ui.SubjectsProgressAnalysisData;
import clarion.attendance.ui.AttendanceLessonsPanel;
import clarion.finance.dao.AccountDAO;
import clarion.students.leader.ui.StudentLeadersList;
import clarion.ui.dispensary.DispensaryPanel;
import clarion.ui.dorm.DormPanel;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import school.ui.account.AnnouncementPanel;
import school.ui.account.EmailNotificationPanel;
import school.ui.account.MyAccountChangeLoginDetails;
import school.ui.account.MyRightsPanel;
import school.ui.account.NotificationAlertTone;
import school.ui.account.SMSNotificationPanel;
import school.ui.account.SystemSettings;
import school.ui.account.SystemSettingsPanel;
import school.ui.account.SystemUsersPanel;
import school.ui.admission.AdmissionDetails;
import school.ui.admission.AdmissionListViewPanel;
import school.ui.admission.PrintAdmissionDetails;
import school.ui.admission.RequirementsDialog;
import school.ui.assets.AssetsPanel;
import school.ui.finances.CashBookController;
import school.ui.finances.FinanceAnalyticalCashBook;
import school.ui.finances.FinanceGeneralLedger;
import school.ui.finances.FinanceIndividualAccounts;
import school.ui.finances.FinanceNotes;
import school.ui.finances.FinanceStatementOfComprehensiveIncome;
import school.ui.finances.FinanceStatementOfFinancialPosition;
import school.ui.finances.FinanceStockCard;
import school.ui.finances.FinanceStoreLedger;
import school.ui.finances.FinanceStudentLedgerCard;
import school.ui.finances.FinanceTree;
import school.ui.finances.FinanceTrialBalance;
import school.ui.finances.MyCalculator;
import school.ui.jdbc.JavaDatabaseSelectStatements;
import school.ui.laboratory.LaboratoryICT;
import school.ui.laboratory.LaboratorySciencePractical;
import school.ui.laboratory.LaboratorySwitcherPanel;
import school.ui.library.IssueBook;
import school.ui.library.LibraryManageBooks;
import school.ui.library.LibraryTree;
import school.ui.library.ReturnBook;
import school.ui.multiline.MultiLineTableCellRenderer;
import school.ui.reports.AdminReportPanel;
import school.ui.reports.MessagesAlertsNumbers;
import school.ui.reports.NotificationsAlerts;
import school.ui.staff.NonTeachingStaffs;
import school.ui.staff.StaffsPanel;
import school.ui.staff.StaffsTreePanel;
import school.ui.staff.TeachingStaffsPanel;
import school.ui.student.AddNewPost;
import school.ui.student.AddNewPrefect;
import school.ui.student.AddNewUNSA;
import school.ui.student.DebatorsClub;
import school.ui.student.DiscontinuationRecords;
import school.ui.student.EditPrefects;
import school.ui.student.EditUNSA;
import school.ui.student.GeneralStudentsPanel;
import school.ui.student.StudentsTreePanel;
import school.ui.student.SuspensionRecords;
import school.ui.student.VisitationDay;
import school.ui.tests.UserMaster;
import school.ui.timetable.TimeTableExamination;
import school.ui.timetable.TimeTableLesson;
import school.ui.timetable.TimeTableTeachersSchedule;
import school.ui.timetable.TimeTableTreePanel;

public class MainPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DispensaryPanel dispensaryPanel;

	private JMenu menuFile;
	private JMenu menuHelp;

	private JMenuItem menuItemFile;
	private JMenuBar mBar;

	private JMenu lafMenu;
	private JRadioButtonMenuItem radioButtonMenuItemNoire;
	private JRadioButtonMenuItem radioButtonMenuItemAcryl;
	private JRadioButtonMenuItem radioButtonMenuItemAero;
	private JRadioButtonMenuItem radioButtonMenuItemAluminium;
	private JRadioButtonMenuItem radioButtonMenuItemBernstein;
	private JRadioButtonMenuItem radioButtonMenuItemFast;
	private JRadioButtonMenuItem radioButtonMenuItemHiFi;
	private JRadioButtonMenuItem radioButtonMenuItemMcWin;
	private JRadioButtonMenuItem radioButtonMenuItemSmart;
	private JRadioButtonMenuItem radioButtonMenuItemMint;
	private JRadioButtonMenuItem radioButtonMenuItemLuna;
	private JRadioButtonMenuItem radioButtonMenuItemTexture;
	private JRadioButtonMenuItem radioButtonMenuItemOS;

	static JLabel current_user;
	String node;
	final int ZERO = 0;
	final int ONE = 1;
	static JTabbedPane tabs;

	JTabbedPane tabMyAccount;
	DefaultMutableTreeNode filesystem = new DefaultMutableTreeNode("ADMISSION");
	DefaultMutableTreeNode filesystemforstudents = new DefaultMutableTreeNode("STUDENTS");
	JTree admissiontree, studentstree;
	StudentsTreePanel studentsTreePanel;
	DefaultMutableTreeNode aa, ab, ac, ad, students, s1, s2, s3, s4, s5A, s5Sci, s6A, s6Sci, leaders, prefects, unsa,
			clubs, debate, writers, scripture, interact, sports, patriotic, wild, scouts, mdd;
	JPanel admit, panelAdmittedStudents, panelReportingStudents, panelReportedStudents;
	JCheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb0, ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8, ch9, ch0;

	JTextField fieldID, fieldName, fieldAddress, fieldParentName, fieldParentPhone, fieldParentEmail, fieldOccupation,
			fieldFeesBal, fieldMiddleName, fieldLastName, fi1, fi2, fi3, fi4, fi5, fi6, fi7, fi8, fi9, f0,
			fieldforsearch1, fieldforsearch3;

	JLabel fieldFeesPayment;
	JDateChooser dc1, dc2, dc3, dc4, dc5, dc6, dc7, dc8, dc9, dc0;
	JLabel lab1, lab2, lab3, lab4, lab5, lab6, lab7, lab8, lab9, lab0, lab11, lab12, la1, la2, la3, la4, la5, la6, la7,
			la8;
	JLabel s1girls, s1boys, s1total, s1classtr, s1classcapt, s2girls, s2boys, s2total, s2classtr, s2classcapt, s3girls,
			s3boys, s3total, s3classtr, s3classcapt;
	JLabel s4girls, s4boys, s4total, s4classtr, s4classcapt, s5Artsgirls, s5Artsboys, s5Artstotal, s5Artsclasstr,
			s5Artsclasscapt, s6Artsgirls, s6Artsboys, s6Artstotal, s6Artsclasstr, s6Artsclasscapt;
	JLabel s5Scigirls, s5Sciboys, s5Scitotal, s5Sciclasstr, s5Sciclasscapt, s6Scigirls, s6Sciboys, s6Scitotal,
			s6Sciclasstr, s6Sciclasscapt;

	JLabel s1girlsNo, s1boysNo, s1totalNo, s1classtrName, s1classcaptName, s2girlsNo, s2boysNo, s2totalNo,
			s2classtrName, s2classcaptName, s3girlsNo, s3boysNo, s3totalNo, s3classtrName, s3classcaptName;
	JLabel s4girlsNo, s4boysNo, s4totalNo, s4classtrName, s4classcaptName, s5ArtsgirlsNo, s5ArtsboysNo, s5ArtstotalNo,
			s5ArtsclasstrName, s5ArtsclasscaptName, s6ArtsgirlsNo, s6ArtsboysNo, s6ArtstotalNo, s6ArtsclasstrName,
			s6ArtsclasscaptName;
	JLabel s5ScigirlsNo, s5SciboysNo, s5ScitotalNo, s5SciclasstrName, s5SciclasscaptName, s6ScigirlsNo, s6SciboysNo,
			s6ScitotalNo, s6SciclasstrName, s6SciclasscaptName;

	String countries;
	JTable tableAdmitStudentRequirements;
	JButton btnBackFromOverview, btnPrintOverview;

	JPanel panelS1, panelS2, panelS3, panelS4, panelS5Arts, panelS5Sci, panelS6Arts, panelS6Sci;
	JTable tableS1, tableS2, tableS3, tableS4, tableS5Arts, tableS5Sci, tableS6Arts, tableS6Sci, tablePrefects,
			tableUNSA;
	JLabel labelpictureS1, labelpictureS2, labelpictureS3, labelpictureS4, labelpictureS5Arts, labelpictureS5Sci,
			labelpictureS6Arts, labelpictureS6Sci, labelpicturePrefects, labelpictureUNSA;
	JButton btnbackS1, btnbackS2, btnbackS3, btnbackS4, btnbackS5Arts, btnbackS5Sci, btnbackS6Arts, btnbackS6Sci;
	JButton btnprintS1, btnprintS2, btnprintS3, btnprintS4, btnprintS5Arts, btnprintS5Sci, btnprintS6Arts,
			btnprintS6Sci, btnbackPrefects, btnprintPrefects, btnbackUNSA, btnprintUNSA;
	JPanel panelPrefects, panelUNSA;
	JMenuItem details, visitation, suspension, discontinue, details1, visitation1, suspension1, discontinue1;
	JButton btnBackfromStudents, btnPrintAllStudents;

	DebatorsClub debatorsClub;

	StaffsPanel staffsPanel;
	StaffsTreePanel staffsTreePanel;
	TeachingStaffsPanel teachingStaffsPanel;

	NonTeachingStaffs nonTeachingStaffs;


	LibraryManageBooks libraryManageBooks;
	LibraryTree libraryTree;
	IssueBook issueBook;
	ReturnBook returnBook;

	TimeTableLesson timeTableLesson;
	TimeTableTreePanel timeTableTreePanel;

	TimeTableTeachersSchedule timeTableTeachersSchedule;

	TimeTableExamination timeTableExamination;

	private LaboratoryICT laboratoryICT;
	private LaboratorySciencePractical laboratorySciencePractical;

	private LaboratorySwitcherPanel laboratorySwitcherPanel;

	AnnouncementPanel announcementPanel;
	SMSNotificationPanel smsNotificationPanel;
	EmailNotificationPanel emailNotificationPanel;

	JTabbedPane tabsNotice;
	SystemUsersPanel systemUsersPanel;
	SystemSettings systemSettings;
	private MyAccountChangeLoginDetails myAccountChangeLoginDetails;

	private FinanceTree financeTree;
	private FinanceGeneralLedger financeGeneralLedger;
	private FinanceStoreLedger financeStoreLedger;
	private FinanceStudentLedgerCard financeStudentLedgerCard;
	private FinanceStockCard financeStockCard;
	private FinanceIndividualAccounts financeIndividualAccounts;
	private FinanceStatementOfComprehensiveIncome financeStatementOfComprehensiveIncome;
	private FinanceStatementOfFinancialPosition financeStatementOfFinancialPosition;
	private FinanceNotes financeNotes;

	private AssetsPanel assetsPanel;
	private JTextField fieldIDReporting;

	int checkboxSponsoredValue;
	private JTextField fieldReceiptNumber;
	protected int english;
	protected int math;
	protected int physics;
	protected int chem;
	protected int biology;
	protected int geog;
	protected int history;
	protected int commerce;
	protected int agric;
	protected int ent;
	protected int accounts;
	protected int cre;
	protected int ire;
	protected int fineart;
	protected int computer;
	protected int additionalmath;
	protected int techdrawing;
	protected int literature;
	protected int divinity;
	protected JTable tableRequirements;
	private JLabel fakeLabelPicture;
	private JButton btnUploadPicture;
	private JLabel labelPictureUploaded;
	private JPanel panelbtnUploadAndTakePicture;
	private JButton btnTakePicture;
	protected String ss;
	protected PrintAdmissionDetails printAdmissionDetails;
	private JTable tableadmitted;
	protected DefaultTableModel dtmAdmitted;
	private JButton btnClearPicture;
	private JLabel labelPictureRequirementDebtorsReportingStudents;
	protected DefaultTableModel modelReqTable;
	protected DefaultTableModel modelReportedStudents;
	protected DefaultTableModel DftModel;
	protected DefaultTableModel modelAllStudents;
	private JTable allstudentsinAyear;
	protected DefaultTableModel modelS1Students;
	protected DefaultTableModel modelS2Students;
	protected DefaultTableModel modelS3Students;
	protected DefaultTableModel modelS4Students;
	protected DefaultTableModel modelS5ArtsStudents;
	protected DefaultTableModel modelS5SciStudents;
	private JMenuItem detailsS1;
	private JMenuItem visitationS1;
	private JMenuItem suspensionS1;
	private JMenuItem discontinueS1;
	private JPopupMenu popupS1;
	private JPopupMenu popupS2;
	private JMenuItem detailsS2;
	private JMenuItem visitationS2;
	private JMenuItem suspensionS2;
	private JMenuItem discontinueS2;
	private JPopupMenu popupS3;
	private JMenuItem detailsS3;
	private JMenuItem visitationS3;
	private JMenuItem suspensionS3;
	private JMenuItem discontinueS3;
	private JPopupMenu popupS6Arts;
	private JMenuItem detailsS6Arts;
	private JMenuItem visitationS6Arts;
	private JMenuItem suspensionS6Arts;
	private JMenuItem discontinueS6Arts;
	private JPopupMenu popupS6Sci;
	private JMenuItem detailsS6Sci;
	private JMenuItem visitationS6Sci;
	private JMenuItem suspensionS6Sci;
	private JMenuItem discontinueS6Sci;
	private JPopupMenu popupS4;
	private JMenuItem detailsS4;
	private JMenuItem visitationS4;
	private JMenuItem suspensionS4;
	private JMenuItem discontinueS4;
	private JPopupMenu popupS5Arts;
	private JMenuItem visitationS5Arts;
	private JMenuItem detailsS5Arts;
	private JMenuItem suspensionS5Arts;
	private JMenuItem discontinueS5Arts;
	private JPopupMenu popupS5Sci;
	private JMenuItem detailsS5Sci;
	private JMenuItem visitationS5Sci;
	private JMenuItem suspensionS5Sci;
	private JMenuItem discontinueS5Sci;
	public boolean isRunning;
	public Webcam webcam;
	protected JLabel labelImage;
	protected JButton btnCapture;
	public Image image;
	protected JButton btnStart;
	protected JButton btnStop;
	private JTable fixedTableAllStudents;
	private float sumAllStudents;
	private float minAge;
	private float maxAge;
	protected DefaultTableModel tablemodelPrefects;
	protected DefaultTableModel tablemodelUnsa;
	protected DefaultTableModel tableModelDebators;
	protected DefaultTableModel tableModelWildLife;
	protected DefaultTableModel tableModelInteract;
	protected DefaultTableModel tableModelGamesAndSports;
	protected DefaultTableModel tableModelScoutsAndGuides;
	protected DefaultTableModel tableModelScriptureUnion;
	protected DefaultTableModel tableModelWritersClub;
	protected DefaultTableModel tableModelTeachers;
	protected DefaultTableModel tableModelNonTeachers;
	private JPanel panelStudentInfo;
	private JPanel panelParentInfo;
	private JTextField comboBoxClasses;
	private JTextField fieldCounty;
	private JTextField comboBoxDorms;
	private JTextField fieldParish;
	private JTextField comboBoxNationality;
	private JTextField fieldSubCounty;
	private JTextField comboBoxGender;
	private JTextField fieldWardLC1;
	private JTextField comboBoxTermOfAdmission;
	private JTextField fieldDistrict;
	private JTextField comboBox1Religion;
	private JTextField dateChooserDOB;
	private JTextField fieldMotherName;
	private JTextField fieldFatherPhone;
	private JTextField fieldMotherEmailPhone;
	private JTextField fieldGuardianName;
	private JTextField fieldGuardianPhone;
	private JTextField fieldSponsorName;
	private JTextField fieldSponsorPhone;
	private JComboBox<String> comboBoxSponsorName;
	private JPanel panelStudentDataToBeEntered;
	private JPanel panelStudentPictureAndButtons;
	private JPanel panelStudentDataLeftHolder;
	private JLabel headingSchoolName;
	private JLabel address1BoxNumber;
	private JLabel address2Email;
	private JLabel address3Telephone;
	private JTextField fieldBanks;
	private JComboBox<String> fieldBankName;
	private JTextField fieldfeesPaid;
	private JTextField fieldstudentName;
	private JTextField fieldclassNumber;
	private JPanel panelStudentStudentSearchFields;
	private JPanel panelHoldingAdmittedStudentsAndSearch;
	private JTextField fieldforsearchAdmittedStudents;
	private JTextField fieldforsearchReportedStudents;
	private JPanel panelStudentDataToBeEnteredReported;
	private JPanel panelStudentDataToBeEnteredAdmitted;
	private JScrollPane scrollerAdmittedStudents;
	private JPanel panelStudentInfoAdmitted;
	private JScrollPane scrollerReportedStudents;
	private JPanel panelStudentInfoReported;
	private JTable tablereportedStudents;
	private JPanel panelStudentPictureAndButtonsReportedStudents;
	private JLabel labelPictureRequirementDebtorsAdmittedStudents;
	private JPanel panelStudentPictureAndButtonsAdmittedStudents;
	private JLabel labelPictureRequirementDebtorsReportedStudents;
	private JComboBox<String> comboBoxClassofReporting;
	private JComboBox<String> comboBoxtermOfReporting;
	private JYearChooser yearofReporting;
	private DefaultTableModel dmRequirements;

	private DormPanel dormPanel;

	private JRadioButtonMenuItem radioButtonMenuItemBackGroundColors;

	private JTextField fieldfeesPaidToAccount;

	private JRadioButtonMenuItem radioCalculator;

	protected FinanceAnalyticalCashBook financeAnalyticalCashBook;

	protected FinanceAnalyticalCashBook FinanceAnalyticalCashBook;

	private JComboBox<String> comboBoxClassesReportedStudents;

	private JYearChooser chooseReportingYear;

	private StudentLeadersList studentLeadersList;
	private AttendanceLessonsPanel attendanceLessonsPanel;

	private String sqlStudentPaymentInfo;

	private static Connection conn;

	private static PreparedStatement pst;

	private AdminReportPanel adminReportPanel;

	private ObservableList<Data> data;

	private PieChart chart;

	private JFXPanel fxPanelOvervie;

	private AdmissionListViewPanel admissionListViewPanel;

	private ResultSet rs;

	protected JFileChooser fileChooser;

	private JPanel home;

	private MouseAdapter listenForMouseClickedInAdmission;

	private MouseAdapter listenForMouseClickedInStudents;

	private MouseAdapter listenForMouseClickedInStaffs;

	private MouseAdapter listenForMouseClickedInAcademics;

	private MouseAdapter listenForMouseClickedInLibrary;

	private MouseAdapter listenForMouseClickedInAdmin;

	private MouseAdapter listenForMouseClickedInTimeTable;

	private MouseAdapter listenForMouseClickedInFinance;

	private JLabel logo;

	protected TableView tableView;

	protected ObservableList<String> col;

	protected ObservableList<Object> row;

	private Series dataSeries1;

	private BarChart barChart;

	private ObservableList<Object> dataTerm;

	private ComboBox comboTerm;

	protected Calendar cal;

	protected String year;

	protected String thisyear;

	private MyRightsPanel myRightsPanel;

	private JLabel labelHome;

	private JLabel labelNotice;

	private MessagesAlertsNumbers alertsNumbers;

	private Timeline timeline;

	int intValueBefore;

	int intValueAfter;

	private String previous;

	private String after;

	private AcademicPanel academicPanel;

	private static String OS = null;

	public static String getOsName() {
		if (OS == null) {
			OS = System.getProperty("os.name");
		}
		return OS;
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		// System.out.println(getOsName());

		// try {
		// UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// try {
		// UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// try {
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// } catch (ClassNotFoundException | InstantiationException |
		// IllegalAccessException
		// | UnsupportedLookAndFeelException e) {
		// e.printStackTrace();
		// }
		Platform.setImplicitExit(false);
		new MainPage();

	}

	public static void changeLaf(JFrame frame, String laf) {

		if (laf.equalsIgnoreCase("noire")) {
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (laf.equalsIgnoreCase("acryl")) {
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (laf.equals("aero")) {
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (laf.equalsIgnoreCase("aluminium")) {
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (laf.equalsIgnoreCase("bernstein")) {
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (laf.equals("fast")) {
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (laf.equalsIgnoreCase("HiFi")) {
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (laf.equalsIgnoreCase("mcwin")) {
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (laf.equalsIgnoreCase("Mint")) {
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (laf.equalsIgnoreCase("smart")) {
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (laf.equalsIgnoreCase("luna")) {
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (laf.equalsIgnoreCase("texture")) {
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (laf.equalsIgnoreCase("system")) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
		}

		SwingUtilities.updateComponentTreeUI(frame);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public MainPage() throws SQLException, ClassNotFoundException {

		Platform.setImplicitExit(false);
		this.setTitle("SCHOOL MANAGEMENT APPLICATION");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

//		try {
//			UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		 try {
		 UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		 } catch (Exception e) {
		 e.printStackTrace();
		 }

		mBar = new JMenuBar();
		setJMenuBar(mBar);

		menuFile = new JMenu("File");
		menuItemFile = new JMenuItem("My Account");
		menuFile.add(menuItemFile);

		mBar.add(menuFile);

		lafMenu = new JMenu("View");
		radioButtonMenuItemNoire = new JRadioButtonMenuItem("Mr. Okwera Joseph");
		radioButtonMenuItemAcryl = new JRadioButtonMenuItem("Mr. Komakech George Bush");
		radioButtonMenuItemBernstein = new JRadioButtonMenuItem("Mr. Deputy Headmaster");
		radioButtonMenuItemFast = new JRadioButtonMenuItem("Director of Studies");
		radioButtonMenuItemHiFi = new JRadioButtonMenuItem("Mr. Head Master");
		radioButtonMenuItemMcWin = new JRadioButtonMenuItem("Mr. Monerach Policap");
		radioButtonMenuItemMint = new JRadioButtonMenuItem("Mr. Bongomin Daniel");
		radioButtonMenuItemSmart = new JRadioButtonMenuItem("Asst Director of Studies");
		radioButtonMenuItemLuna = new JRadioButtonMenuItem("Mr. Alican Sundie Odong");
		radioButtonMenuItemAluminium = new JRadioButtonMenuItem("Mr. Lawrence Kaboha");
		radioButtonMenuItemAero = new JRadioButtonMenuItem("Madam Susan");
		radioButtonMenuItemTexture = new JRadioButtonMenuItem("Mr. Walter Nyeko");
		radioButtonMenuItemOS = new JRadioButtonMenuItem("ST. MARY'S HIGH SCHOOL-KISUGU");
		radioButtonMenuItemBackGroundColors = new JRadioButtonMenuItem("Change Application Background");
		radioCalculator = new JRadioButtonMenuItem("Calculator");

		mBar.add(lafMenu);
		lafMenu.add(radioButtonMenuItemOS);
		lafMenu.add(radioButtonMenuItemBernstein);
		lafMenu.add(radioButtonMenuItemNoire);
		lafMenu.add(radioButtonMenuItemAero);
		lafMenu.add(radioButtonMenuItemSmart);
		lafMenu.add(radioButtonMenuItemHiFi);
		lafMenu.add(radioButtonMenuItemFast);

		lafMenu.add(radioButtonMenuItemLuna);
		lafMenu.add(radioButtonMenuItemAluminium);
		lafMenu.add(radioButtonMenuItemMint);
		lafMenu.add(radioButtonMenuItemAcryl);
		lafMenu.add(radioButtonMenuItemMcWin);
		lafMenu.add(radioButtonMenuItemTexture);
		lafMenu.add(radioButtonMenuItemBackGroundColors);

		menuFile.add(radioCalculator);

		radioButtonMenuItemNoire.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLaf(MainPage.this, "noire");
			}
		});
		radioButtonMenuItemAcryl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				changeLaf(MainPage.this, "acryl");
				
			}
		});

		radioButtonMenuItemAero.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLaf(MainPage.this, "aero");
			}
		});

		radioButtonMenuItemAluminium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLaf(MainPage.this, "aluminium");
			}
		});

		radioButtonMenuItemBernstein.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLaf(MainPage.this, "bernstein");
			}
		});

		radioButtonMenuItemFast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLaf(MainPage.this, "fast");
			}
		});

		radioButtonMenuItemHiFi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLaf(MainPage.this, "hifi");
			}
		});

		radioButtonMenuItemMcWin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLaf(MainPage.this, "mcwin");
			}
		});

		radioButtonMenuItemMint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLaf(MainPage.this, "mint");
			}
		});

		radioButtonMenuItemSmart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLaf(MainPage.this, "smart");
			}
		});

		radioButtonMenuItemLuna.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLaf(MainPage.this, "luna");
			}
		});

		radioButtonMenuItemTexture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLaf(MainPage.this, "texture");
			}
		});

		radioButtonMenuItemOS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLaf(MainPage.this, "system");
			}
		});

		radioCalculator.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				MyCalculator calcul = new MyCalculator();
				calcul.setTitle("ST. MARY'S HIGH SCHOOL-KISUGU-CALCULATOR");

			}
		});

		// add help
		menuHelp = new JMenu("Help");
		mBar.add(menuHelp);
		
		JMenuItem manualAcademics=new JMenuItem("Academics-User Guide");
		menuHelp.add(manualAcademics);
		
		manualAcademics.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				UsersManualFrame usersManualFrame =new UsersManualFrame();
//				TrialSwing swing=new TrialSwing();
			}
		});

		JMenuBar barMainPage = new JMenuBar();
		barMainPage.setOpaque(true);
		// this.setJMenuBar(barMainPage);

		JMenuItem Calculator = new JMenuItem("Calculator");
		Calculator.setFont(new Font("Times New Roman", Font.BOLD, 14));
		Calculator.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});

		// // full screen
		// Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		// this.setBounds(0, 0, screensize.width, screensize.height);

		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		// this.setBounds(0, 0, screensize.width, screensize.height);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// whole frame panel
		JPanel main = new JPanel();
		add(main);
		main.setLayout(new BorderLayout(2, 2));

		JPanel CenterPanel = new JPanel();
		CenterPanel.setLayout(new BorderLayout());

		Dimension dimHome = new Dimension(screensize.width - 26, screensize.height - 52);
		main.setPreferredSize(dimHome);
		// main.setBackground(Color.GRAY);

		// upper panel
		JPanel upper = new JPanel();
		main.add(upper, BorderLayout.NORTH);
		upper.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		upper.setPreferredSize(new Dimension(screensize.width - 31, 115));
		upper.setBorder(new LineBorder(Color.black, 2));

		// left panel

		JPanel leftpanel = new JPanel();
		// main.add(leftpanel, BorderLayout.WEST);
		leftpanel.setPreferredSize(new Dimension(160, 540));
		leftpanel.setBorder(new LineBorder(Color.black, 2));

		// middle big panel
		JPanel bigmiddle = new JPanel();
		// main.add(bigmiddle, BorderLayout.CENTER);
		// bigmiddle.setPreferredSize(new Dimension(screensize.width - 196,
		// 540));
		bigmiddle.setBorder(new LineBorder(Color.black, 2));
		bigmiddle.setLayout(new BorderLayout());

		main.add(CenterPanel, BorderLayout.CENTER);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, leftpanel, bigmiddle);

		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(0.5);

		CenterPanel.add(splitPane, BorderLayout.CENTER);
		// lower panel

		JPanel lowerpanel = new JPanel();
		main.add(lowerpanel, BorderLayout.SOUTH);
		lowerpanel.setPreferredSize(new Dimension(screensize.width - 31, 40));
		lowerpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 2));
		lowerpanel.setBorder(new LineBorder(Color.black, 2));

		// size of panels of the tab
		Dimension dim = new Dimension(screensize.width - 200, 485);

		tabs = new JTabbedPane();
		bigmiddle.add(tabs, BorderLayout.CENTER);
		tabs.setTabPlacement(JTabbedPane.NORTH);
		tabs.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// all the tabs
		home = new JPanel();
		tabs.addTab("", home);
		home.setPreferredSize(dim);

		JPanel home1 = new JPanel();
		tabs.addTab("", home1);
		home1.setPreferredSize(dim);

		JPanel home2 = new JPanel();
		tabs.addTab("", home2);
		home2.setPreferredSize(dim);

		JPanel home3 = new JPanel();
		tabs.addTab("", home3);
		home3.setPreferredSize(dim);

		JPanel home4 = new JPanel();
		tabs.addTab("", home4);
		home4.setPreferredSize(dim);

		JPanel home5 = new JPanel();
		tabs.addTab("", home5);
		home5.setPreferredSize(dim);

		JPanel panelDispensaryTab = new JPanel();
		tabs.addTab("", panelDispensaryTab);
		panelDispensaryTab.setPreferredSize(dim);

		JPanel home6 = new JPanel();
		tabs.addTab("", home6);
		home6.setPreferredSize(dim);

		// manage the sizes of the tabs
		Dimension d1 = new Dimension(120, 30);
		labelHome = new JLabel("Home");
		labelHome.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelHome.setPreferredSize(d1);

		labelNotice = new JLabel("Notice");
		labelNotice.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelNotice.setPreferredSize(d1);

		JLabel l3 = new JLabel("Attendance");
		l3.setFont(new Font("Times New Roman", Font.BOLD, 18));
		l3.setPreferredSize(d1);

		JLabel l4 = new JLabel("Assets Register");
		l4.setFont(new Font("Times New Roman", Font.BOLD, 18));
		l4.setPreferredSize(d1);

		JLabel l5 = new JLabel("Dormitory");
		l5.setFont(new Font("Times New Roman", Font.BOLD, 18));
		l5.setPreferredSize(d1);

		JLabel l6 = new JLabel("Laboratory");
		l6.setFont(new Font("Times New Roman", Font.BOLD, 18));
		l6.setPreferredSize(d1);

		JLabel labelDispensary = new JLabel("Dispensary");
		labelDispensary.setFont(new Font("Times New Roman", Font.BOLD, 18));
		labelDispensary.setPreferredSize(d1);

		JLabel l7 = new JLabel("My Account");
		l7.setFont(new Font("Times New Roman", Font.BOLD, 18));
		l7.setPreferredSize(d1);

		// adding the labels to the tabs

		tabs.setTabComponentAt(0, labelHome);
		tabs.setTabComponentAt(1, labelNotice);
		tabs.setTabComponentAt(2, l3);
		tabs.setTabComponentAt(3, l4);
		tabs.setTabComponentAt(4, l5);
		tabs.setTabComponentAt(5, l6);
		tabs.setTabComponentAt(6, labelDispensary);
		tabs.setTabComponentAt(7, l7);

		// photos of main page

		la1 = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("STUDENT.jpg")).getImage();
		la1.setIcon(new ImageIcon(img1));

		// trial for hover pic
		Image img11 = new ImageIcon(this.getClass().getResource("STUDENT1.jpg")).getImage();

		home.add(la1);
		la1.setCursor(new Cursor(Cursor.HAND_CURSOR));

		la2 = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("STAFF.jpg")).getImage();
		Image img22 = new ImageIcon(this.getClass().getResource("STAFF1.jpg")).getImage();
		la2.setIcon(new ImageIcon(img2));
		home.add(la2);
		la2.setCursor(new Cursor(Cursor.HAND_CURSOR));

		la3 = new JLabel("");
		Image img3 = new ImageIcon(this.getClass().getResource("academics.jpg")).getImage();
		Image img33 = new ImageIcon(this.getClass().getResource("academics1.jpg")).getImage();
		la3.setIcon(new ImageIcon(img3));
		home.add(la3);
		la3.setCursor(new Cursor(Cursor.HAND_CURSOR));

		la4 = new JLabel("");
		Image img4 = new ImageIcon(this.getClass().getResource("ADMISSION.jpg")).getImage();
		Image img44 = new ImageIcon(this.getClass().getResource("ADMISSION1.jpg")).getImage();
		la4.setIcon(new ImageIcon(img4));
		home.add(la4);
		la4.setCursor(new Cursor(Cursor.HAND_CURSOR));

		la5 = new JLabel("");
		Image img5 = new ImageIcon(this.getClass().getResource("ADMINISTRATOR.jpg")).getImage();
		Image img55 = new ImageIcon(this.getClass().getResource("ADMINISTRATOR1.jpg")).getImage();
		la5.setIcon(new ImageIcon(img5));
		home.add(la5);
		la5.setCursor(new Cursor(Cursor.HAND_CURSOR));

		la6 = new JLabel("");
		Image img6 = new ImageIcon(this.getClass().getResource("timetable.jpg")).getImage();
		Image img66 = new ImageIcon(this.getClass().getResource("timetable1.jpg")).getImage();
		la6.setIcon(new ImageIcon(img6));
		home.add(la6);
		la6.setCursor(new Cursor(Cursor.HAND_CURSOR));

		la7 = new JLabel("");
		Image img7 = new ImageIcon(this.getClass().getResource("library.png")).getImage();
		Image img77 = new ImageIcon(this.getClass().getResource("library1.jpg")).getImage();
		la7.setIcon(new ImageIcon(img7));
		home.add(la7);
		la7.setCursor(new Cursor(Cursor.HAND_CURSOR));

		la8 = new JLabel("");
		Image img8 = new ImageIcon(this.getClass().getResource("finance.jpg")).getImage();
		Image img88 = new ImageIcon(this.getClass().getResource("finance1.jpg")).getImage();
		la8.setIcon(new ImageIcon(img8));
		home.add(la8);
		la8.setCursor(new Cursor(Cursor.HAND_CURSOR));

		home.setLayout(new FlowLayout(FlowLayout.LEFT, 75, 25));

		// home.setBackground(Color.decode("#2c3e50"));
		// bigmiddle.setBackground(Color.decode("#2c3e50"));

		// this.setMinimumSize(this.getSize());

		JPanel address = new JPanel();

		// address.setBorder(new LineBorder(Color.black,1));
		address.setLayout(new FlowLayout(FlowLayout.LEFT));
		// address.setBackground(Color.decode("#2c3e50"));
		address.setPreferredSize(new Dimension(585, 110));

		JPanel panelLogo = new JPanel();
		panelLogo.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panelLogo.setPreferredSize(new Dimension(157, 122));
		panelLogo.setBorder(new LineBorder(Color.white, 2));
		upper.add(panelLogo);

		logo = new JLabel("");
		Image logoimg = new ImageIcon(this.getClass().getResource("LayibiBadge.jpg")).getImage();
		logo.setBorder(new LineBorder(Color.white, 2));
		// logo.setIcon(new ImageIcon(logoimg));
		panelLogo.add(logo);

		JPanel panelSpace = new JPanel();
		panelSpace.setPreferredSize(new Dimension(200, 125));
		panelSpace.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		// panelSpace.setBorder(new LineBorder(Color.white, 2));
		upper.add(panelSpace);

		JLabel labelStatue = new JLabel();
		labelStatue.setPreferredSize(new Dimension(200, 125));
		Image statueImg = new ImageIcon(this.getClass().getResource("LayibiStatue.jpg")).getImage();
		labelStatue.setIcon(new ImageIcon(statueImg));
		panelSpace.add(labelStatue);

		upper.add(address);
		// address.setBorder(new LineBorder(Color.white,2));
		// upper.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 4));
		logo.setPreferredSize(new Dimension(157, 122));
		// upper.setBackground(Color.decode("#2c3e50"));

		headingSchoolName = new JLabel();
		headingSchoolName.setText("");

		address.add(headingSchoolName);
		// headingSchoolName.setPreferredSize(new Dimension(500, 30));
		headingSchoolName.setForeground(Color.white);
		headingSchoolName.setFont(new Font("Times New Roman", Font.BOLD, 30));

		address1BoxNumber = new JLabel();
		address1BoxNumber.setText("");

		address.add(address1BoxNumber);
		address1BoxNumber.setPreferredSize(new Dimension(450, 16));
		address1BoxNumber.setForeground(Color.white);
		address1BoxNumber.setFont(new Font("Times New Roman", Font.BOLD, 16));

		address2Email = new JLabel();
		address2Email.setText("");

		address.add(address2Email);
		address2Email.setPreferredSize(new Dimension(450, 16));
		address2Email.setForeground(Color.white);
		address2Email.setFont(new Font("Times New Roman", Font.BOLD, 16));

		address3Telephone = new JLabel();
		address3Telephone.setText("");

		address.add(address3Telephone);
		address3Telephone.setPreferredSize(new Dimension(450, 16));
		address3Telephone.setForeground(Color.white);
		address3Telephone.setFont(new Font("Times New Roman", Font.BOLD, 16));
		// this.getContentPane().setBackground(Color.decode("#2c3e50"));

		JPanel loginpanel = new JPanel();
		upper.add(loginpanel);
		loginpanel.setPreferredSize(new Dimension(360, 110));
		// loginpanel.setBorder(new LineBorder(Color.white,2));
		loginpanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		// loginpanel.setBackground(Color.decode("#2c3e50"));

		current_user = new JLabel("User");
		loginpanel.add(current_user);
		current_user.setPreferredSize(new Dimension(180, 25));
		current_user.setForeground(Color.WHITE);
		current_user.setFont(new Font("Times New Roman", Font.BOLD, 16));
		// Notice Board tabs
		tabsNotice = new JTabbedPane();

		// Notice Board Announcement
		JPanel panelAnnouncement = new JPanel();
		panelAnnouncement.setPreferredSize(new Dimension(1020, 460));
		panelAnnouncement.setLayout(new FlowLayout(FlowLayout.LEFT));
		JScrollPane scrollAnnouncement = new JScrollPane(panelAnnouncement);
		tabsNotice.addTab("Announcements", scrollAnnouncement);

		// calling panel for noticeboard announcement
		announcementPanel = new AnnouncementPanel();
		announcementPanel.setPreferredSize(new Dimension(1000, 450));
		panelAnnouncement.add(announcementPanel);

		// SMS Notification
		JPanel panelSMS = new JPanel();
		panelSMS.setPreferredSize(new Dimension(1020, 460));
		panelSMS.setLayout(new FlowLayout(FlowLayout.LEFT));
		JScrollPane scrollSMS = new JScrollPane(panelSMS);
		tabsNotice.addTab("SMS Notification", scrollSMS);

		// // calling panel for sms notification
		// ,smsNotificationPanel = new SMSNotificationPanel();
		// smsNotificationPanel.setPreferredSize(new Dimension(1000, 450));
		// panelSMS.add(smsNotificationPanel);

		// Email Notification
		JPanel panelEmail = new JPanel();
		panelEmail.setPreferredSize(new Dimension(1020, 460));
		panelEmail.setLayout(new FlowLayout(FlowLayout.LEFT));
		JScrollPane scrollEmail = new JScrollPane(panelEmail);
		tabsNotice.addTab("Email Notification", scrollEmail);

		// // calling email notification panel
		// emailNotificationPanel = new EmailNotificationPanel();
		// emailNotificationPanel.setPreferredSize(new Dimension(1000, 450));
		// panelEmail.add(emailNotificationPanel);

		JPanel panelMessenger = new JPanel();
		panelMessenger.setPreferredSize(new Dimension(1020, 460));
		// panelMessenger.setLayout(new FlowLayout(FlowLayout.LEFT));
		JScrollPane scrollMessenger = new JScrollPane(panelMessenger);
		tabsNotice.addTab("Instant Messenger", scrollMessenger);

		// home1.add(tabsNotice);

		tabsNotice.setTabPlacement(JTabbedPane.LEFT);

		// chat app in Notice---home1

		JPanel panelUser = new JPanel();
		panelMessenger.add(panelUser);
		JLabel notifypop = new JLabel("User Who Messaged Me");
		notifypop.setPreferredSize(new Dimension(100, 25));
		panelUser.add(notifypop);
		panelUser.setLayout(new FlowLayout());

		String[] usersStringArray = { "Nyeko", "Walter", "Danel", "Comboni", "Bush", "Policap", "Alican" };

		JComboBox comboUsers = new JComboBox<>(usersStringArray);
		panelUser.add(comboUsers);
		comboUsers.setPreferredSize(new Dimension(100, 25));
		panelUser.setPreferredSize(new Dimension(110, 450));

		JPanel onlinePanel = new JPanel();
		panelMessenger.setLayout(new FlowLayout(FlowLayout.RIGHT));
		onlinePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		panelMessenger.add(onlinePanel);
		onlinePanel.setPreferredSize(new Dimension(220, 450));

		JTextArea usersOnline = new JTextArea();
		usersOnline.setLineWrap(true);
		usersOnline.setWrapStyleWord(true);
		usersOnline.setEditable(false);
		usersOnline.setAutoscrolls(true);
		usersOnline.setFont(new Font("Times New Roman", Font.BOLD, 16));
		// usersOnline.setBackground(Color.decode("#7f8c8d"));
		JScrollPane scrollerUsersBox = new JScrollPane(usersOnline);
		scrollerUsersBox.setPreferredSize(new Dimension(215, 435));
		onlinePanel.add(scrollerUsersBox);

		JPanel chatPanel = new JPanel();
		panelMessenger.add(chatPanel);
		chatPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		chatPanel.setPreferredSize(new Dimension(650, 450));

		JTextArea chatBox = new JTextArea();
		chatBox.setLineWrap(true);
		chatBox.setWrapStyleWord(true);
		chatBox.setEditable(false);
		chatBox.setAutoscrolls(false);
		chatBox.setFont(new Font("Times New Roman", Font.BOLD, 16));
		// chatBox.setBackground(Color.decode("#7f8c8d"));
		JScrollPane scrollerChatBox = new JScrollPane(chatBox);

		scrollerChatBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerChatBox.setPreferredSize(new Dimension(645, 400));
		chatPanel.add(scrollerChatBox);

		JTextField msgBox = new JTextField();
		msgBox.setText("Type Your Message Here...");
		msgBox.setPreferredSize(new Dimension(545, 35));
		chatPanel.add(msgBox);
		msgBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String userMsg = msgBox.getText();
				if (userMsg != null) {
					chatBox.setCaretPosition(chatBox.getDocument().getLength());
				}
			}
		});

		msgBox.setFont(new Font("Times New Roman", Font.BOLD, 16));

		JButton sendBtn = new JButton("Send");
		sendBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		chatPanel.add(sendBtn);
		sendBtn.setPreferredSize(new Dimension(100, 35));

		// allow pressing of Enter to send the message
		getRootPane().setDefaultButton(sendBtn);

		sendBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String msgBefore = msgBox.getText();
				String msgAfter = msgBefore.replaceAll("'", "''");
				String sql = "insert into chat(Message,user,SendTo,Read_By) values ('" + msgAfter + "','"
						+ current_user.getText() + "','" + comboUsers.getSelectedItem() + "','"
						+ comboUsers.getSelectedItem() + "')";

				java.sql.Connection conn = null;

				java.sql.PreparedStatement pst = null;

				try {

					conn = CashBookController.getConnection();
					pst = conn.prepareStatement(sql);

					pst.executeUpdate();

				} catch (Exception e1) {
					e1.printStackTrace();

				} finally {
					if (conn != null) {
						try {
							conn.close();
						} catch (Exception e2) {

						}
					}
				}

				msgBox.setText("Type Your Message Here...");
			}
		});

		msgBox.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {

				if (msgBox.getText().isEmpty()) {
					msgBox.setText("Type Your Message Here...");
				}

			}

			@Override
			public void focusGained(FocusEvent e) {

				msgBox.setText("");
			}
		});

		getRootPane().setDefaultButton(sendBtn);

		// end of chat app

		JLabel text = new JLabel("");
		loginpanel.add(text);
		Image logoutimg = new ImageIcon(this.getClass().getResource("logout.png")).getImage();
		text.setIcon(new ImageIcon(logoutimg));
		text.setCursor(new Cursor(Cursor.HAND_CURSOR));

		JLabel address5 = new JLabel("Sign Out");

		text.addMouseListener(new MouseListener() {

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
				Platform.runLater(new Runnable() {

					private Stage primaryStage;
					private String[] args;

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.initStyle(StageStyle.TRANSPARENT);
						alert.initModality(Modality.WINDOW_MODAL);
						alert.setContentText("Are You Sure You Want To Log Out???");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							Platform.exit();

							System.exit(0);
						}
					}
				});
			}
		});
		address5.addMouseListener(new MouseListener() {

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

				Platform.runLater(new Runnable() {

					private Stage primaryStage;
					private String[] args;

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.initStyle(StageStyle.TRANSPARENT);
						alert.initModality(Modality.WINDOW_MODAL);
						alert.setContentText("Are You Sure You Want To Log Out???");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							Platform.exit();

							System.exit(0);
						}
					}
				});

				// System.exit(0);
			}
		});
		loginpanel.add(address5);
		address5.setPreferredSize(new Dimension(85, 25));
		address5.setForeground(Color.WHITE);
		address5.setFont(new Font("Times New Roman", Font.BOLD, 20));
		address5.setCursor(new Cursor(Cursor.HAND_CURSOR));

		JLabel copyright = new JLabel("Copy Right @ Soft-Earth Technologies");
		lowerpanel.add(copyright);
		// lowerpanel.setBackground(Color.decode("#2c3e50"));
		copyright.setPreferredSize(new Dimension(380, 25));
		copyright.setForeground(Color.WHITE);
		copyright.setFont(new Font("Times New Roman", Font.BOLD, 20));
		copyright.setCursor(new Cursor(Cursor.HAND_CURSOR));
		copyright.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				new KeyBoard();
			}
		});

		JPanel panDateAndInternet = new JPanel();
		lowerpanel.add(panDateAndInternet);
		panDateAndInternet.setPreferredSize(new Dimension(450, 30));
		// panDateAndInternet.setBackground(Color.decode("#2c3e50"));
		panDateAndInternet.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel address6 = new JLabel("");
		loginpanel.add(address6);
		address6.setPreferredSize(new Dimension(50, 50));
		address6.setForeground(Color.WHITE);
		Image searchimg = new ImageIcon(this.getClass().getResource("Camera.png")).getImage();
		Image searchimg1 = new ImageIcon(this.getClass().getResource("Camera1.png")).getImage();
		address6.setIcon(new ImageIcon(searchimg));
		address6.setFont(new Font("Times New Roman", Font.BOLD, 20));
		address6.setCursor(new Cursor(Cursor.HAND_CURSOR));
		address6.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

				address6.setPreferredSize(new Dimension(50, 50));
				address6.setIcon(new ImageIcon(searchimg));
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

				address6.setPreferredSize(new Dimension(65, 60));
				address6.setIcon(new ImageIcon(searchimg1));

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				// SearchSystem createUsers=new SearchSystem();
				Platform.runLater(new Runnable() {

					private Stage stage;
					private ObservableList<String> searchItems;

					@Override
					public void run() {
						// TODO Auto-generated method stub

						TitledPane paneReceived = new TitledPane();

						GridPane GpaneReceived = new GridPane();
						GpaneReceived.setVgap(10);
						GpaneReceived.setHgap(10);
						GpaneReceived.setPadding(new Insets(10, 10, 10, 10));

						searchItems = FXCollections.observableArrayList("Students", "Staffs", "System Users", "Books",
								"Time Table", "Lesson Attendance", "Laboratory Equipments", "School Assets",
								"School Dormitory", "Notice Boards");
						ComboBox btnEnterReceived = new ComboBox(searchItems);
						btnEnterReceived.setPromptText("Choose Where To Search From");
						btnEnterReceived.setPrefWidth(250);
						GpaneReceived.add(btnEnterReceived, 0, 0);

						TextField btnClearReceived = new TextField();
						btnClearReceived.setPromptText("Search:");
						btnClearReceived.setPrefWidth(200);
						GpaneReceived.add(btnClearReceived, 1, 0);

						paneReceived.setText("Search Everything");
						paneReceived.setPadding(new Insets(10, 10, 10, 10));
						paneReceived.setContent(GpaneReceived);

						btnClearReceived.setOnAction(event -> {
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.initStyle(StageStyle.TRANSPARENT);
							alert.initModality(Modality.WINDOW_MODAL);
							alert.setTitle("Are you sure to exit?");
							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == ButtonType.OK) {
								Platform.exit();
								setVisible(false);
							}
						});

						TitledPane paneTable = new TitledPane();

						GridPane GpaneTable = new GridPane();
						GpaneTable.setVgap(10);
						GpaneTable.setHgap(10);
						GpaneTable.setPadding(new Insets(10, 10, 10, 10));

						paneTable.setText("Search Results");
						paneTable.setPadding(new Insets(10, 10, 10, 10));

						tableView = new TableView();

						String colHeading[] = { "Date", "Item Name", "Qty Recieved" };

						col = FXCollections.observableArrayList(colHeading);

						row = FXCollections.observableArrayList();

						TableColumn<UserMaster, String> colName = new TableColumn<UserMaster, String>(col.get(0));

						colName.setMinWidth(100);

						colName.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("date"));

						TableColumn<UserMaster, String> colCourse = new TableColumn<UserMaster, String>(col.get(1));

						colCourse.setMinWidth(150);

						colCourse.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("item_name"));

						TableColumn<UserMaster, String> colEmail = new TableColumn<UserMaster, String>(col.get(2));

						colEmail.setMinWidth(80);

						colEmail.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("quantity_received"));

						tableView.getColumns().addAll(colName, colCourse, colEmail);
						tableView.setPrefHeight(100);
						paneTable.setContent(tableView);

						VBox box = new VBox();
						box.getChildren().addAll(paneReceived, paneTable);
						final Scene scene = new Scene(box);

						Stage stage = new Stage();
						stage = new Stage();
						stage.setScene(scene);
						stage.setTitle("Search System");
						stage.setFullScreenExitHint("");
						stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
						stage.setFullScreen(false);
						stage.show();

					}
				});
			}
		});

		/////////////////// Change Back Gorund of
		/////////////////// Application/////////////////////
		radioButtonMenuItemBackGroundColors.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Color newColor = JColorChooser.showDialog(home, "Choose Background Color", home.getBackground());
				if (newColor != null) {
					home.setBackground(newColor);
					upper.setBackground(newColor);
					lowerpanel.setBackground(newColor);
					leftpanel.setBackground(newColor);
					loginpanel.setBackground(newColor);
					address.setBackground(newColor);
					bigmiddle.setBackground(newColor);
					panDateAndInternet.setBackground(newColor);
					panelSpace.setBackground(newColor);
				}
			}
		});

		JLabel clock = new JLabel();
		clock.setPreferredSize(new Dimension(200, 20));
		panDateAndInternet.add(clock);
		// clock.setText(DateFormat.getDateTimeInstance().format(new Date()));
		clock.setFont(new Font("Times New Roman", Font.BOLD, 15));
		clock.setForeground(Color.WHITE);

		Calendar.getInstance();

		// clock= new JLabel(dateFormat.format(now.getTime()));
		Timer timer1 = new Timer();

		TimerTask tt = new TimerTask() {

			@Override
			public void run() {

				Calendar now = Calendar.getInstance();
				clock.setText(DateFormat.getDateTimeInstance().format(now.getTime()));
			}

		};
		timer1.schedule(tt, 1000, 1000);

		JTextField InetTex = new JTextField();
		InetTex.setPreferredSize(new Dimension(25, 10));
		panDateAndInternet.add(InetTex);

		Timer timer = new Timer();
		TimerTask myTask = new TimerTask() {
			@Override
			public void run() {
				// whatever you need to do every x seconds

				if ((testInet(("afri-whiteevents.com")) == true) || testInet(("google.com")) == true) {
					// checks Internet connection and returns true
					flashMyField(InetTex, Color.GREEN, 600, 5000);
					// f1.setText("Internet Ok");
				} else {
					flashMyField(InetTex, Color.RED, 200, 5000);
					// f1.setText("Internet Disconnected");
				}
				// flash red light
			}
		};

		timer.schedule(myTask, 5000, 5000);

		// leftpanel.setBackground(Color.decode("#2c3e50"));

		Dimension dimadmission = new Dimension(screensize.width - 200, 480);
		JPanel admission = new JPanel();
		admission.setPreferredSize(dimadmission);
		// admission.setBorder(new LineBorder(Color.black, 1));
		home.add(admission);
		admission.setVisible(false);
		// admission.setBackground(Color.decode("#2c3e50"));

		admit = new JPanel();
		panelAdmittedStudents = new JPanel();
		panelReportingStudents = new JPanel();
		panelReportedStudents = new JPanel();

		listenForMouseClickedInAdmission = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				la1.setVisible(false);
				la5.setVisible(false);
				la2.setVisible(false);
				la6.setVisible(false);
				la3.setVisible(false);
				la7.setVisible(false);
				la4.setVisible(false);
				la8.setVisible(false);
				admission.setVisible(true);
				home.setLayout(new FlowLayout(FlowLayout.LEFT));
				admissionListViewPanel.setVisible(true);

				academicPanel.setVisible(false);

			}

		};

		la4.addMouseListener(listenForMouseClickedInAdmission);
		la4.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

				la4.setIcon(new ImageIcon(img4));
			}

			@Override
			public void mouseEntered(MouseEvent e) {

				la4.setIcon(new ImageIcon(img44));

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		aa = addNode("Admit Student", filesystem);

		ab = addNode("Admitted Students", filesystem);

		ac = addNode("Reporting Students", filesystem);

		ad = addNode("Reported Students", filesystem);

		admit.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 4));
		admit.setPreferredSize(dimadmission);
		// admit.setBorder(new LineBorder(Color.black, 1));
		admission.add(admit);
		admit.setVisible(false);

		panelAdmittedStudents.setPreferredSize(dimadmission);
		panelAdmittedStudents.setBorder(new LineBorder(Color.black, 1));
		admission.add(panelAdmittedStudents);
		panelAdmittedStudents.setVisible(false);

		// things in panelAdmittedStudents panel

		panelHoldingAdmittedStudentsAndSearch = new JPanel();
		panelHoldingAdmittedStudentsAndSearch.setPreferredSize(new Dimension(1005, 450));
		panelHoldingAdmittedStudentsAndSearch.setBackground(new Color(0, 102, 102));
		panelAdmittedStudents.add(panelHoldingAdmittedStudentsAndSearch);

		panelStudentDataToBeEnteredAdmitted = new JPanel();
		panelStudentDataToBeEnteredAdmitted.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelStudentDataToBeEnteredAdmitted.setBorder(new TitledBorder("Search Admitted Students"));
		panelStudentDataToBeEnteredAdmitted.setPreferredSize(new Dimension(1000, 80));
		panelStudentDataToBeEnteredAdmitted.setBackground(new Color(0, 102, 102));
		panelHoldingAdmittedStudentsAndSearch.add(panelStudentDataToBeEnteredAdmitted);

		panelStudentPictureAndButtonsAdmittedStudents = new JPanel();
		panelStudentPictureAndButtonsAdmittedStudents.setPreferredSize(new Dimension(140, 450));
		panelStudentPictureAndButtonsAdmittedStudents.setBackground(new Color(0, 102, 102));
		panelAdmittedStudents.add(panelStudentPictureAndButtonsAdmittedStudents);

		labelPictureRequirementDebtorsAdmittedStudents = new JLabel("");
		labelPictureRequirementDebtorsAdmittedStudents.setPreferredSize(new Dimension(130, 110));
		labelPictureRequirementDebtorsAdmittedStudents.setBorder(new LineBorder(Color.WHITE, 2));
		panelStudentPictureAndButtonsAdmittedStudents.add(labelPictureRequirementDebtorsAdmittedStudents);

		panelStudentInfoAdmitted = new JPanel();
		panelStudentInfoAdmitted.setBorder(new TitledBorder("Admitted Students"));
		panelStudentInfoAdmitted.setPreferredSize(new Dimension(1000, 355));
		panelStudentInfoAdmitted.setBackground(new Color(0, 102, 102));
		panelHoldingAdmittedStudentsAndSearch.add(panelStudentInfoAdmitted);

		JLabel search1 = new JLabel();
		search1.setText("Search All:");
		search1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelStudentDataToBeEnteredAdmitted.add(search1);

		fieldforsearchAdmittedStudents = new JTextField();
		panelStudentDataToBeEnteredAdmitted.add(fieldforsearchAdmittedStudents);
		fieldforsearchAdmittedStudents.setPreferredSize(new Dimension(300, 25));
		fieldforsearchAdmittedStudents.setFont(new Font("Times New Roman", Font.BOLD, 15));
		fieldforsearchAdmittedStudents.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

				displayData(tableadmitted, "select students_info.class_number,students_info.student_name,"
						+ "TIMESTAMPDIFF(YEAR,students_info.date_of_birth,Now()),parents_info.parent_address,"
						+ "CASE WHEN parents_info.sponsors_name='Choose Sponsor' THEN 'No' WHEN parents_info.sponsors_name='' THEN 'Unknown' ELSE 'Yes' END AS Sponsored,students_info.dormitory,"
						+ "parents_info.fathers_name,LEFT(students_info.year,4) from students_info,parents_info where "
						+ "students_info.class_number=parents_info.class_number and students_info.student_name=parents_info.student_name and "
						+ "concat(students_info.class_number,students_info.student_name,"
						+ "parents_info.parent_address,"
						+ "parents_info.fathers_name,students_info.year,students_info.dormitory) LIKE '%"
						+ fieldforsearchAdmittedStudents.getText() + "%'");

			}

			@Override
			public void keyPressed(KeyEvent arg0) {

			}
		});

		///////////////////// Table of Admitted
		///////////////////// Students//////////////////////////
		DefaultTableModel modelAdmittedStudents = new DefaultTableModel();

		String[][] data3 = new String[][] { { null, null, null, null, null, null, null, null },
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

		String[] heading3 = new String[] { "Class Number", "Student Name", "Age", "Address", "Sponsored", "Dormitory",
				"Parent Name", "Year" };

		modelAdmittedStudents.setDataVector(data3, heading3);

		tableadmitted = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
		tableadmitted.setAutoCreateRowSorter(true);
		tableadmitted.setModel(modelAdmittedStudents);
		tableadmitted.setShowGrid(false);
		JTableHeader headerAdmit = tableadmitted.getTableHeader();
		headerAdmit.setPreferredSize(new Dimension(1150, 30));

		DefaultTableCellRenderer rendererAdmitted = new DefaultTableCellRenderer();
		rendererAdmitted.setHorizontalAlignment(SwingConstants.CENTER);
		((TableColumnModel) tableadmitted.getColumnModel()).getColumn(2).setCellRenderer(rendererAdmitted);
		((TableColumnModel) tableadmitted.getColumnModel()).getColumn(7).setCellRenderer(rendererAdmitted);

		scrollerAdmittedStudents = new JScrollPane(tableadmitted);
		tableadmitted.setRowHeight(25);
		scrollerAdmittedStudents.setPreferredSize(new Dimension(980, 320));

		panelStudentInfoAdmitted.add(scrollerAdmittedStudents);
		tableadmitted.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				int selectedRow = tableadmitted.getSelectedRow();

				String studentID = (String) tableadmitted.getValueAt(selectedRow, 0);
				String studentName = (String) tableadmitted.getValueAt(selectedRow, 1);
				try {

					java.sql.Connection conn = CashBookController.getConnection();

					PreparedStatement pst = conn.prepareStatement("select photo from students_info where class_number='"
							+ studentID + "' and student_name='" + studentName + "'");
					ResultSet rs = pst.executeQuery();

					if (rs.next()) {

						byte[] img = rs.getBytes("photo");
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image im2 = im.getScaledInstance(labelPictureRequirementDebtorsAdmittedStudents.getWidth(),
								labelPictureRequirementDebtorsAdmittedStudents.getHeight(), Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(im2);
						labelPictureRequirementDebtorsAdmittedStudents.setIcon(newImage);

					} else {
						JOptionPane.showMessageDialog(null, "No Image Found For " + studentName + " In The Database",
								"Lacking " + studentName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});

		JButton backtoHome = new JButton("Back");
		backtoHome.setPreferredSize(new Dimension(135, 25));
		backtoHome.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backtoHome.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				panelAdmittedStudents.setVisible(false);
				home.setVisible(true);
				home.setLayout(new FlowLayout(FlowLayout.LEFT, 75, 25));
				la1.setVisible(true);
				la2.setVisible(true);
				la3.setVisible(true);
				la4.setVisible(true);
				la5.setVisible(true);
				la6.setVisible(true);
				la7.setVisible(true);
				la8.setVisible(true);
				admissionListViewPanel.setVisible(false);

			}
		});
		panelStudentPictureAndButtonsAdmittedStudents.add(backtoHome);

		JButton Print1 = new JButton("Print");
		Print1.setPreferredSize(new Dimension(135, 25));
		Print1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		Print1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {

					boolean completed = tableadmitted.print();

					if (completed) {
						JOptionPane.showMessageDialog(null, "Printed Successfully", "Printed",
								JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (PrinterException e) {

					e.printStackTrace();
				}

			}
		});
		panelStudentPictureAndButtonsAdmittedStudents.add(Print1);

		JButton ExportAdmittedStudents1 = new JButton("Export To Excel");
		ExportAdmittedStudents1.setPreferredSize(new Dimension(135, 25));
		ExportAdmittedStudents1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		ExportAdmittedStudents1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to export this deatils");

				int selected = fileChooser.showSaveDialog(MainPage.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						fillData(tableadmitted, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		panelStudentPictureAndButtonsAdmittedStudents.add(ExportAdmittedStudents1);

		/// things in reported students////////////
		panelStudentStudentSearchFields = new JPanel();
		panelStudentStudentSearchFields.setPreferredSize(new Dimension(1005, 450));
		panelStudentStudentSearchFields.setBackground(new Color(0, 102, 102));
		panelReportedStudents.add(panelStudentStudentSearchFields);

		panelStudentDataToBeEnteredReported = new JPanel();
		panelStudentDataToBeEnteredReported.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelStudentDataToBeEnteredReported.setBorder(new TitledBorder("Search Reported Students"));
		panelStudentDataToBeEnteredReported.setPreferredSize(new Dimension(1000, 80));
		panelStudentDataToBeEnteredReported.setBackground(new Color(0, 102, 102));
		panelStudentStudentSearchFields.add(panelStudentDataToBeEnteredReported);

		panelStudentPictureAndButtonsReportedStudents = new JPanel();
		panelStudentPictureAndButtonsReportedStudents.setPreferredSize(new Dimension(140, 450));
		panelStudentPictureAndButtonsReportedStudents.setBackground(new Color(0, 102, 102));
		panelReportedStudents.add(panelStudentPictureAndButtonsReportedStudents);

		labelPictureRequirementDebtorsReportedStudents = new JLabel("");
		labelPictureRequirementDebtorsReportedStudents.setPreferredSize(new Dimension(130, 110));
		labelPictureRequirementDebtorsReportedStudents.setBorder(new LineBorder(Color.WHITE, 2));
		panelStudentPictureAndButtonsReportedStudents.add(labelPictureRequirementDebtorsReportedStudents);

		panelStudentInfoReported = new JPanel();
		panelStudentInfoReported.setBorder(new TitledBorder("Reported Students"));
		panelStudentInfoReported.setPreferredSize(new Dimension(1000, 355));
		panelStudentInfoReported.setBackground(new Color(0, 102, 102));
		panelStudentStudentSearchFields.add(panelStudentInfoReported);

		new Dimension(80, 25);
		Dimension dimlabels = new Dimension(50, 30);

		JLabel labelClass = new JLabel();
		labelClass.setText("Class:");
		labelClass.setPreferredSize(dimlabels);
		labelClass.setFont(new Font("Times New Roman", Font.BOLD, 14));
		panelStudentDataToBeEnteredReported.add(labelClass);

		comboBoxClassesReportedStudents = new JComboBox();
		comboBoxClassesReportedStudents.setPreferredSize(new Dimension(120, 25));
		panelStudentDataToBeEnteredReported.add(comboBoxClassesReportedStudents);
		comboBoxClassesReportedStudents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				displayData(tablereportedStudents, "select `" + comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.class_number," + "`" + comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.student_name," + "TIMESTAMPDIFF(YEAR,`" + comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.date_of_birth,Now()),parents_info.parent_address,"
						+ "CASE WHEN parents_info.sponsors_name='Choose Sponsor' THEN 'No' WHEN parents_info.sponsors_name=''"
						+ " THEN 'Unknown' ELSE 'Yes' END AS Sponsored,`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.dormitory," + "`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.term,LEFT(`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.year,4) from " + "`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`,parents_info where " + "`"
						+ comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.class_number=parents_info.class_number and " + "`"
						+ comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.student_name=parents_info.student_name " + "and `"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.year='" + chooseReportingYear.getYear()
						+ "'");

			}
		});

		JLabel labelSearchReportedByYear = new JLabel();
		labelSearchReportedByYear.setText("Search By Year:");
		labelSearchReportedByYear.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelStudentDataToBeEnteredReported.add(labelSearchReportedByYear);

		chooseReportingYear = new JYearChooser();
		chooseReportingYear.getYear();
		chooseReportingYear.setPreferredSize(new Dimension(100, 25));
		chooseReportingYear.addPropertyChangeListener("year", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {

				displayData(tablereportedStudents, "select `" + comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.class_number," + "`" + comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.student_name," + "TIMESTAMPDIFF(YEAR,`" + comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.date_of_birth,Now()),parents_info.parent_address,"
						+ "CASE WHEN parents_info.sponsors_name='Choose Sponsor' THEN 'No' WHEN parents_info.sponsors_name=''"
						+ " THEN 'Unknown' ELSE 'Yes' END AS Sponsored,`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.dormitory," + "`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.term,LEFT(`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.year,4) from " + "`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`,parents_info where " + "`"
						+ comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.class_number=parents_info.class_number and " + "`"
						+ comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.student_name=parents_info.student_name " + "and `"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.year='" + chooseReportingYear.getYear()
						+ "'");

			}
		});
		panelStudentDataToBeEnteredReported.add(chooseReportingYear);

		JLabel labelSearchReported = new JLabel();
		labelSearchReported.setText("Search All:");
		labelSearchReported.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panelStudentDataToBeEnteredReported.add(labelSearchReported);

		fieldforsearchReportedStudents = new JTextField();
		panelStudentDataToBeEnteredReported.add(fieldforsearchReportedStudents);
		fieldforsearchReportedStudents.setPreferredSize(new Dimension(300, 25));
		fieldforsearchReportedStudents.setFont(new Font("Times New Roman", Font.BOLD, 15));
		fieldforsearchReportedStudents.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

				displayData(tablereportedStudents, "select `" + comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.class_number," + "`" + comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.student_name," + "TIMESTAMPDIFF(YEAR,`" + comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.date_of_birth,Now()),parents_info.parent_address,"
						+ "CASE WHEN parents_info.sponsors_name='Choose Sponsor' THEN 'No' WHEN parents_info.sponsors_name=''"
						+ " THEN 'Unknown' ELSE 'Yes' END AS Sponsored,`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.dormitory," + "`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.term,LEFT(`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.year,4) from " + "`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`,parents_info where " + "`"
						+ comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.class_number=parents_info.class_number and " + "`"
						+ comboBoxClassesReportedStudents.getSelectedItem()
						+ "`.student_name=parents_info.student_name " + "and `"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.year='" + chooseReportingYear.getYear()
						+ "' and " + "concat(`" + comboBoxClassesReportedStudents.getSelectedItem() + "`.class_number,`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.student_name,"
						+ "parents_info.parent_address," + "parents_info.fathers_name,`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.year,`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.dormitory," + "`"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.term) LIKE '%"
						+ fieldforsearchReportedStudents.getText() + "%' and `"
						+ comboBoxClassesReportedStudents.getSelectedItem() + "`.year='" + chooseReportingYear.getYear()
						+ "'");

			}

			@Override
			public void keyPressed(KeyEvent arg0) {

			}
		});

		///////////////////// Table of Reported
		///////////////////// Students//////////////////////////

		DefaultTableModel modelReportedStudents = new DefaultTableModel();
		String[][] dataReportedStudents = new String[][] { { null, null, null, null, null, null, null, null },
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

		String[] headingReportedStudents = new String[] { "Class Number", "Student Name", "Age", "Address", "Sponsored",
				"Dormitory", "Term", "Year" };

		modelReportedStudents.setDataVector(dataReportedStudents, headingReportedStudents);

		tablereportedStudents = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
		tablereportedStudents.setModel(modelReportedStudents);
		tablereportedStudents.setShowGrid(false);
		tablereportedStudents.setAutoCreateRowSorter(true);
		JTableHeader headerReported = tablereportedStudents.getTableHeader();
		headerReported.setPreferredSize(new Dimension(1150, 30));

		scrollerReportedStudents = new JScrollPane(tablereportedStudents);
		tablereportedStudents.setRowHeight(25);
		scrollerReportedStudents.setPreferredSize(new Dimension(980, 320));

		panelStudentInfoReported.add(scrollerReportedStudents);

		tablereportedStudents.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				int selectedRow = tablereportedStudents.getSelectedRow();

				String studentID = (String) tablereportedStudents.getValueAt(selectedRow, 0);
				String studentName = (String) tablereportedStudents.getValueAt(selectedRow, 1);
				try {

					java.sql.Connection conn = CashBookController.getConnection();

					PreparedStatement pst = conn.prepareStatement("select photo from students_info where class_number='"
							+ studentID + "' and student_name='" + studentName + "'");
					ResultSet rs = pst.executeQuery();

					if (rs.next()) {

						byte[] img = rs.getBytes("photo");
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image im2 = im.getScaledInstance(labelPictureRequirementDebtorsReportedStudents.getWidth(),
								labelPictureRequirementDebtorsReportedStudents.getHeight(), Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(im2);
						labelPictureRequirementDebtorsReportedStudents.setIcon(newImage);

					} else {
						JOptionPane.showMessageDialog(null, "No Image Found For " + studentName + " In The Database",
								"Lacking " + studentName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});

		JButton backFromReported = new JButton("Back");
		backFromReported.setPreferredSize(new Dimension(135, 25));
		backFromReported.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backFromReported.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				panelReportedStudents.setVisible(false);
				home.setVisible(true);
				home.setLayout(new FlowLayout(FlowLayout.LEFT, 75, 25));
				la1.setVisible(true);
				la2.setVisible(true);
				la3.setVisible(true);
				la4.setVisible(true);
				la5.setVisible(true);
				la6.setVisible(true);
				la7.setVisible(true);
				la8.setVisible(true);
				admissionListViewPanel.setVisible(false);

			}
		});
		panelStudentPictureAndButtonsReportedStudents.add(backFromReported);

		JButton printReportedStudents = new JButton("Print");
		printReportedStudents.setPreferredSize(new Dimension(135, 25));
		printReportedStudents.setCursor(new Cursor(Cursor.HAND_CURSOR));
		printReportedStudents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {

					boolean completed = tableadmitted.print();

					if (completed) {
						JOptionPane.showMessageDialog(null, "Printed Successfully", "Printed",
								JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (PrinterException e) {

					e.printStackTrace();
				}

			}
		});
		panelStudentPictureAndButtonsReportedStudents.add(printReportedStudents);

		JButton ExportReportedStudents1 = new JButton("Export To Excel");
		ExportReportedStudents1.setPreferredSize(new Dimension(135, 25));
		ExportReportedStudents1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		ExportReportedStudents1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify name and folder to export this deatils");

				int selected = fileChooser.showSaveDialog(MainPage.this);

				if (selected == JFileChooser.APPROVE_OPTION) {
					try {

						fillData(tablereportedStudents, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		panelStudentPictureAndButtonsReportedStudents.add(ExportReportedStudents1);

		//////////////////// END OF REPORTING STUDENTS AND PARENTS
		//////////////////// INFO////////////////////////////////////////////////

		panelReportingStudents.setPreferredSize(dimadmission);
		admission.add(panelReportingStudents);
		panelReportingStudents.setVisible(false);

		panelReportedStudents.setPreferredSize(dimadmission);
		panelReportedStudents.setBorder(new LineBorder(Color.black, 1));
		admission.add(panelReportedStudents);
		panelReportedStudents.setVisible(false);

		// give a fake label to allow picture of student align to the left
		fakeLabelPicture = new JLabel("");
		fakeLabelPicture.setPreferredSize(new Dimension(900, 30));
		admit.add(fakeLabelPicture);

		panelbtnUploadAndTakePicture = new JPanel();
		panelbtnUploadAndTakePicture.setPreferredSize(new Dimension(120, 100));
		admit.add(panelbtnUploadAndTakePicture);

		btnClearPicture = new JButton("Clear Picture");
		btnClearPicture.setPreferredSize(new Dimension(100, 30));
		panelbtnUploadAndTakePicture.add(btnClearPicture);
		btnClearPicture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				labelPictureUploaded.setIcon(null);

			}
		});

		btnTakePicture = new JButton("Take Picture");
		btnTakePicture.setPreferredSize(new Dimension(100, 30));
		btnTakePicture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFrame framecamera = new JFrame();
				framecamera.setTitle("Capture image");
				framecamera.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				framecamera.setSize(650, 565);
				framecamera.setLocationRelativeTo(null);
				framecamera.setResizable(false);

				labelImage = new JLabel("");
				labelImage.setPreferredSize(new Dimension(400, 330));
				labelImage.setBorder(new LineBorder(Color.white, 3));
				framecamera.add(labelImage, BorderLayout.CENTER);

				btnCapture = new JButton("Capture");
				btnCapture.setPreferredSize(new Dimension(150, 30));
				btnCapture.addActionListener(new ActionListener() {

					private BufferedImage image;
					private JFileChooser fileChooser;

					@Override
					public void actionPerformed(ActionEvent e) {

						if (!isRunning) {
							isRunning = true;
							new VideoFeedTaker().start();
						} else {
							isRunning = false;
							image = webcam.getImage();

							fileChooser = new JFileChooser();
							fileChooser.setDialogTitle("Specify name and folder to save this photo");

							int userSelection = fileChooser.showSaveDialog(MainPage.this);

							if (userSelection == JFileChooser.APPROVE_OPTION) {
								try {

									ImageIO.write(image, "PNG", new File(fileChooser.getSelectedFile() + ".png"));

								} catch (IOException e1) {

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

						webcam.close();
					}
				});
				panelSouth.add(btnStop);

				framecamera.setVisible(true);

			}
		});
		panelbtnUploadAndTakePicture.add(btnTakePicture);

		btnUploadPicture = new JButton("Upload Picture");
		btnUploadPicture.setPreferredSize(new Dimension(100, 30));
		btnUploadPicture.addActionListener(new ActionListener() {

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
					labelPictureUploaded.setIcon(ResizeImage(path));
					ss = path;
				}

				else if (result == JFileChooser.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(null, "No Photo Was Selected", "Please select a photo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		panelbtnUploadAndTakePicture.add(btnUploadPicture);

		labelPictureUploaded = new JLabel("");
		labelPictureUploaded.setPreferredSize(new Dimension(130, 110));
		labelPictureUploaded.setBorder(new LineBorder(Color.WHITE, 2));
		admit.add(labelPictureUploaded);

		JPanel form1 = new JPanel();
		JScrollPane scrollform1 = new JScrollPane(form1);
		form1.setPreferredSize(new Dimension(560, 650));
		scrollform1.setPreferredSize(new Dimension(560, 310));
		new Dimension(140, 30);
		new Dimension(100, 30);
		Border factory1 = BorderFactory.createTitledBorder("Student Details");
		scrollform1.setBorder(factory1);
		admit.add(scrollform1);

		form1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));

		String[][] data1 = new String[][] { { null, null, null, null }, { null, null, null, null }

		};

		String[] heading1 = new String[] { "Req Name", "Quantity", "Balance", "Standard Quantity" };

		tableAdmitStudentRequirements = new JTable(data1, heading1);
		JScrollPane scrollerb1 = new JScrollPane(tableAdmitStudentRequirements);
		tableAdmitStudentRequirements.setRowHeight(25);
		scrollerb1.setPreferredSize(new Dimension(260, 150));

		// form4.add(scrollerb1);

		Dimension dimMinimum = new Dimension(1050, 300);
		Dimension dimScroller = new Dimension(screensize.width + 20, screensize.height + 20);

		// not resizeable frame
		this.setMaximumSize(screensize);
		this.setMinimumSize(dimMinimum);

		JScrollPane scrollthroughframe = new JScrollPane(main);
		scrollthroughframe.setPreferredSize(dimScroller);
		this.add(scrollthroughframe);

		new Dimension(150, 25);
		new Dimension(90, 30);

		JPanel formReporting = new JPanel();
		formReporting.setPreferredSize(new Dimension(550, 440));
		JScrollPane scrollReqts = new JScrollPane(formReporting);
		Border factoryReporting = BorderFactory.createTitledBorder("Student Details");
		scrollReqts.setBorder(factoryReporting);

		formReporting.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel formReportingChooser = new JPanel();
		formReportingChooser.setBorder(new LineBorder(Color.white, 3));
		formReportingChooser.setPreferredSize(new Dimension(800, 110));
		formReporting.add(formReportingChooser);

		// layout
		formReporting.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 3));

		// /////////////Panel Reporting Students///////////////
		panelStudentDataLeftHolder = new JPanel();
		panelStudentDataLeftHolder.setPreferredSize(new Dimension(1005, 445));
		panelStudentDataLeftHolder.setBackground(new Color(0, 102, 102));
		panelReportingStudents.add(panelStudentDataLeftHolder);

		panelStudentDataToBeEntered = new JPanel();
		panelStudentDataToBeEntered.setBorder(new TitledBorder("Student Fees Payments"));
		panelStudentDataToBeEntered.setPreferredSize(new Dimension(999, 150));
		panelStudentDataToBeEntered.setBackground(new Color(0, 102, 102));
		panelStudentDataLeftHolder.add(panelStudentDataToBeEntered);

		panelStudentPictureAndButtons = new JPanel();
		panelStudentPictureAndButtons.setPreferredSize(new Dimension(140, 445));
		panelStudentPictureAndButtons.setBackground(new Color(0, 102, 102));
		panelReportingStudents.add(panelStudentPictureAndButtons);

		labelPictureRequirementDebtorsReportingStudents = new JLabel("");
		labelPictureRequirementDebtorsReportingStudents.setPreferredSize(new Dimension(130, 110));
		labelPictureRequirementDebtorsReportingStudents.setBorder(new LineBorder(Color.WHITE, 2));
		panelStudentPictureAndButtons.add(labelPictureRequirementDebtorsReportingStudents);

		panelStudentInfo = new JPanel();
		panelStudentInfo.setBorder(new TitledBorder("Student Info"));
		panelStudentInfo.setPreferredSize(new Dimension(495, 280));
		panelStudentInfo.setBackground(new Color(0, 102, 102));
		panelStudentDataLeftHolder.add(panelStudentInfo);

		panelParentInfo = new JPanel();
		panelParentInfo.setBorder(new TitledBorder("Parent Info"));
		panelParentInfo.setPreferredSize(new Dimension(495, 280));
		panelParentInfo.setBackground(new Color(0, 102, 102));
		panelStudentDataLeftHolder.add(panelParentInfo);

		Dimension dimFieldsReporting11 = new Dimension(130, 25);
		Dimension dimLabelsReporting11 = new Dimension(100, 25);

		///////////////// Fees Payment Entries///////////////////////////

		JLabel labelclassNumber = new JLabel();
		labelclassNumber.setText("Class Number:");
		labelclassNumber.setPreferredSize(dimLabelsReporting11);
		panelStudentDataToBeEntered.add(labelclassNumber);

		fieldclassNumber = new JTextField();
		fieldclassNumber.setPreferredSize(dimFieldsReporting11);
		fieldclassNumber.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

				showReportinStudentsInfo();

			}

			@Override
			public void keyPressed(KeyEvent arg0) {

			}
		});

		panelStudentDataToBeEntered.add(fieldclassNumber);

		JLabel labelYearofReporting = new JLabel();
		labelYearofReporting.setText("Year:");
		labelYearofReporting.setPreferredSize(dimLabelsReporting11);
		panelStudentDataToBeEntered.add(labelYearofReporting);

		yearofReporting = new JYearChooser();
		yearofReporting.getYear();
		yearofReporting.setPreferredSize(dimFieldsReporting11);
		panelStudentDataToBeEntered.add(yearofReporting);

		panelStudentDataToBeEntered.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));

		JLabel labelstudentName = new JLabel();
		labelstudentName.setText("Student Name:");
		labelstudentName.setPreferredSize(dimLabelsReporting11);
		panelStudentDataToBeEntered.add(labelstudentName);

		fieldstudentName = new JTextField();
		fieldstudentName.setPreferredSize(dimFieldsReporting11);
		panelStudentDataToBeEntered.add(fieldstudentName);

		JLabel labelfeesPaid = new JLabel();
		labelfeesPaid.setText("Amount Paid:");
		labelfeesPaid.setPreferredSize(dimLabelsReporting11);
		panelStudentDataToBeEntered.add(labelfeesPaid);

		fieldfeesPaid = new JTextField();
		fieldfeesPaid.setPreferredSize(dimFieldsReporting11);
		panelStudentDataToBeEntered.add(fieldfeesPaid);

		JLabel labelBankName = new JLabel();
		labelBankName.setText("Bank Paid To:");
		labelBankName.setPreferredSize(dimLabelsReporting11);
		panelStudentDataToBeEntered.add(labelBankName);

		fieldBankName = new JComboBox();
		fieldBankName.setPreferredSize(dimFieldsReporting11);
		fieldBankName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showAccountNumber();
			}
		});

		panelStudentDataToBeEntered.add(fieldBankName);

		JLabel labelClassofReporting = new JLabel();
		labelClassofReporting.setText("Class:");
		labelClassofReporting.setPreferredSize(dimLabelsReporting11);
		panelStudentDataToBeEntered.add(labelClassofReporting);

		comboBoxClassofReporting = new JComboBox();
		comboBoxClassofReporting.setPreferredSize(dimFieldsReporting11);
		panelStudentDataToBeEntered.add(comboBoxClassofReporting);

		JLabel labelTermofReporting = new JLabel();
		labelTermofReporting.setText("Term:");
		labelTermofReporting.setPreferredSize(dimLabelsReporting11);
		panelStudentDataToBeEntered.add(labelTermofReporting);

		comboBoxtermOfReporting = new JComboBox();
		comboBoxtermOfReporting.setPreferredSize(dimFieldsReporting11);
		panelStudentDataToBeEntered.add(comboBoxtermOfReporting);

		JLabel labelfeesPaidToAccount = new JLabel();
		labelfeesPaidToAccount.setText("Account No:");
		labelfeesPaidToAccount.setPreferredSize(dimLabelsReporting11);
		panelStudentDataToBeEntered.add(labelfeesPaidToAccount);

		fieldfeesPaidToAccount = new JTextField();
		fieldfeesPaidToAccount.setPreferredSize(dimFieldsReporting11);
		fieldfeesPaidToAccount.setEditable(false);
		panelStudentDataToBeEntered.add(fieldfeesPaidToAccount);

		JLabel labelFeesBal = new JLabel();
		labelFeesBal.setText("Receipt Number:");
		labelFeesBal.setPreferredSize(dimLabelsReporting11);

		panelStudentDataToBeEntered.add(labelFeesBal);

		fieldReceiptNumber = new JTextField();
		fieldReceiptNumber.setPreferredSize(dimFieldsReporting11);
		panelStudentDataToBeEntered.add(fieldReceiptNumber);

		JLabel labelBankName1 = new JLabel();
		labelBankName1.setText("Payment Code:");
		labelBankName1.setPreferredSize(dimLabelsReporting11);
		panelStudentDataToBeEntered.add(labelBankName1);

		fieldBanks = new JTextField();
		fieldBanks.setPreferredSize(dimFieldsReporting11);
		panelStudentDataToBeEntered.add(fieldBanks);

		fieldBanks.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {

				checkFeesbalance();
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {

			}
		});

		JLabel labelSponsors = new JLabel();
		labelSponsors.setText("Account Name:");
		labelSponsors.setPreferredSize(dimLabelsReporting11);
		panelStudentDataToBeEntered.add(labelSponsors);

		comboBoxSponsorName = new JComboBox();
		comboBoxSponsorName.setPreferredSize(dimFieldsReporting11);

		panelStudentDataToBeEntered.add(comboBoxSponsorName);

		JLabel labelFeesPayment = new JLabel();
		labelFeesPayment.setText("Previous Balance:");
		labelFeesPayment.setPreferredSize(dimFieldsReporting11);
		panelStudentDataToBeEntered.add(labelFeesPayment);

		fieldFeesPayment = new JLabel();
		fieldFeesPayment.setPreferredSize(dimFieldsReporting11);
		fieldFeesPayment.setFont(new Font("Times New roman", Font.BOLD, 18));
		fieldFeesPayment.setForeground(Color.white);
		panelStudentDataToBeEntered.add(fieldFeesPayment);

		/////// Students Info//////////////////////////////////////////

		JLabel labelID = new JLabel();
		labelID.setText("Class Number:");
		labelID.setPreferredSize(dimLabelsReporting11);
		panelStudentInfo.add(labelID);

		fieldID = new JTextField();
		fieldID.setPreferredSize(dimFieldsReporting11);
		panelStudentInfo.add(fieldID);

		JLabel labelName = new JLabel();
		labelName.setText("Student Name:");
		labelName.setPreferredSize(dimLabelsReporting11);
		panelStudentInfo.add(labelName);

		fieldName = new JTextField();
		fieldName.setPreferredSize(dimFieldsReporting11);
		panelStudentInfo.add(fieldName);

		JLabel labelClassReporting = new JLabel();
		labelClassReporting.setText("Class:");
		labelClassReporting.setPreferredSize(dimLabelsReporting11);
		panelStudentInfo.add(labelClassReporting);

		comboBoxClasses = new JTextField();
		comboBoxClasses.setPreferredSize(dimFieldsReporting11);
		panelStudentInfo.add(comboBoxClasses);

		JLabel labelCounty = new JLabel("County:");
		labelCounty.setPreferredSize(dimLabelsReporting11);
		panelStudentInfo.add(labelCounty);

		fieldCounty = new JTextField();
		fieldCounty.setPreferredSize(dimFieldsReporting11);
		panelStudentInfo.add(fieldCounty);

		JLabel labelDorm = new JLabel();
		labelDorm.setText("Dormitory");
		labelDorm.setPreferredSize(dimLabelsReporting11);
		panelStudentInfo.add(labelDorm);

		comboBoxDorms = new JTextField();
		comboBoxDorms.setPreferredSize(dimFieldsReporting11);
		panelStudentInfo.add(comboBoxDorms);

		JLabel labelParish = new JLabel("Parish:");
		labelParish.setPreferredSize(dimLabelsReporting11);
		panelStudentInfo.add(labelParish);

		fieldParish = new JTextField();
		fieldParish.setPreferredSize(dimFieldsReporting11);
		panelStudentInfo.add(fieldParish);

		JLabel labelNationality = new JLabel();
		labelNationality.setText("Nationality");
		labelNationality.setPreferredSize(dimLabelsReporting11);
		panelStudentInfo.add(labelNationality);

		comboBoxNationality = new JTextField();
		comboBoxNationality.getLocale().getCountry().toString();
		comboBoxNationality.setPreferredSize(dimFieldsReporting11);
		panelStudentInfo.add(comboBoxNationality);

		JLabel labelSubCounty = new JLabel("Sub-County:");
		labelSubCounty.setPreferredSize(dimLabelsReporting11);
		panelStudentInfo.add(labelSubCounty);

		fieldSubCounty = new JTextField();
		fieldSubCounty.setPreferredSize(dimFieldsReporting11);
		panelStudentInfo.add(fieldSubCounty);

		JLabel labelGender = new JLabel();
		labelGender.setText("Gender:");
		labelGender.setPreferredSize(dimLabelsReporting11);
		panelStudentInfo.add(labelGender);

		comboBoxGender = new JTextField();
		comboBoxGender.setPreferredSize(dimFieldsReporting11);
		panelStudentInfo.add(comboBoxGender);

		JLabel labelWardLC1 = new JLabel("Ward/LC1:");
		labelWardLC1.setPreferredSize(dimLabelsReporting11);
		panelStudentInfo.add(labelWardLC1);

		fieldWardLC1 = new JTextField();
		fieldWardLC1.setPreferredSize(dimFieldsReporting11);
		panelStudentInfo.add(fieldWardLC1);

		JLabel labelTermOfAdmission = new JLabel();
		labelTermOfAdmission.setText("Term Of Admission:");
		labelTermOfAdmission.setPreferredSize(dimLabelsReporting11);
		panelStudentInfo.add(labelTermOfAdmission);

		comboBoxTermOfAdmission = new JTextField();
		comboBoxTermOfAdmission.setPreferredSize(dimFieldsReporting11);
		panelStudentInfo.add(comboBoxTermOfAdmission);

		JLabel labelDistrict = new JLabel("Home District:");
		labelDistrict.setPreferredSize(dimLabelsReporting11);
		panelStudentInfo.add(labelDistrict);

		fieldDistrict = new JTextField();
		fieldDistrict.setPreferredSize(dimFieldsReporting11);
		panelStudentInfo.add(fieldDistrict);

		JLabel labelReligion = new JLabel();
		labelReligion.setText("Religion:");
		labelReligion.setPreferredSize(dimLabelsReporting11);
		panelStudentInfo.add(labelReligion);

		comboBox1Religion = new JTextField();
		comboBox1Religion.setPreferredSize(dimFieldsReporting11);
		panelStudentInfo.add(comboBox1Religion);

		JLabel labelDOB = new JLabel();
		labelDOB.setText("Date Of Birth:");
		labelDOB.setPreferredSize(dimLabelsReporting11);
		panelStudentInfo.add(labelDOB);

		dateChooserDOB = new JTextField();
		dateChooserDOB.setPreferredSize(dimFieldsReporting11);
		panelStudentInfo.add(dateChooserDOB);

		///////////////////////// Parent Details/////////////////////////////

		JLabel labelParent = new JLabel();
		labelParent.setText("Father's Name");
		labelParent.setPreferredSize(dimLabelsReporting11);
		panelParentInfo.add(labelParent);

		fieldParentName = new JTextField();
		fieldParentName.setPreferredSize(dimFieldsReporting11);
		panelParentInfo.add(fieldParentName);

		JLabel labelMother = new JLabel();
		labelMother.setText("Mother's Name");
		labelMother.setPreferredSize(dimLabelsReporting11);
		panelParentInfo.add(labelMother);

		fieldMotherName = new JTextField();
		fieldMotherName.setPreferredSize(dimFieldsReporting11);
		panelParentInfo.add(fieldMotherName);

		JLabel labelFatherContact = new JLabel();
		labelFatherContact.setText("Father's Phone:");
		labelFatherContact.setPreferredSize(dimLabelsReporting11);
		panelParentInfo.add(labelFatherContact);

		fieldFatherPhone = new JTextField();
		fieldFatherPhone.setPreferredSize(dimFieldsReporting11);
		panelParentInfo.add(fieldFatherPhone);

		JLabel labelMotherEmailContact = new JLabel();
		labelMotherEmailContact.setText("Mother's Phone:");
		labelMotherEmailContact.setPreferredSize(dimLabelsReporting11);
		panelParentInfo.add(labelMotherEmailContact);

		fieldMotherEmailPhone = new JTextField();
		fieldMotherEmailPhone.setPreferredSize(dimFieldsReporting11);
		panelParentInfo.add(fieldMotherEmailPhone);

		JLabel labelGuardian = new JLabel();
		labelGuardian.setText("Guardian's Name");
		labelGuardian.setPreferredSize(dimLabelsReporting11);
		panelParentInfo.add(labelGuardian);

		fieldGuardianName = new JTextField();
		fieldGuardianName.setPreferredSize(dimFieldsReporting11);
		panelParentInfo.add(fieldGuardianName);

		JLabel labelGuardianContact = new JLabel();
		labelGuardianContact.setText("Guardian's Phone:");
		labelGuardianContact.setPreferredSize(dimLabelsReporting11);
		panelParentInfo.add(labelGuardianContact);

		fieldGuardianPhone = new JTextField();
		fieldGuardianPhone.setPreferredSize(dimFieldsReporting11);
		panelParentInfo.add(fieldGuardianPhone);

		JLabel labelSponsor = new JLabel();
		labelSponsor.setText("Sponsor's Name");
		labelSponsor.setPreferredSize(dimLabelsReporting11);
		panelParentInfo.add(labelSponsor);

		fieldSponsorName = new JTextField();
		fieldSponsorName.setEditable(false);
		fieldSponsorName.setPreferredSize(dimFieldsReporting11);
		panelParentInfo.add(fieldSponsorName);

		JLabel labelSponsorContact = new JLabel();
		labelSponsorContact.setText("Sponsor's Phone:");
		labelSponsorContact.setPreferredSize(dimLabelsReporting11);
		panelParentInfo.add(labelSponsorContact);

		fieldSponsorPhone = new JTextField();
		fieldSponsorPhone.setPreferredSize(dimFieldsReporting11);
		panelParentInfo.add(fieldSponsorPhone);

		JLabel labelEmail = new JLabel();
		labelEmail.setText("Parent Email:");
		labelEmail.setPreferredSize(dimLabelsReporting11);
		panelParentInfo.add(labelEmail);

		fieldParentEmail = new JTextField();
		fieldParentEmail.setPreferredSize(dimFieldsReporting11);
		panelParentInfo.add(fieldParentEmail);

		JLabel labelAddress = new JLabel();
		labelAddress.setText("Address:");
		labelAddress.setPreferredSize(dimLabelsReporting11);
		panelParentInfo.add(labelAddress);

		fieldAddress = new JTextField();
		fieldAddress.setPreferredSize(dimFieldsReporting11);
		panelParentInfo.add(fieldAddress);

		JButton requirements = new JButton("Requirements");
		requirements.setPreferredSize(new Dimension(135, 25));
		requirements.setCursor(new Cursor(Cursor.HAND_CURSOR));
		requirements.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				RequirementsDialog requirementsDialogReporting = new RequirementsDialog();
				requirementsDialogReporting.labelClassNumber.setText(fieldclassNumber.getText());
				requirementsDialogReporting.labelTerm.setText(comboBoxtermOfReporting.getSelectedItem().toString());
				DisplayAvailableRequirementsForReportingStudents();

				requirementsDialogReporting.tableforrequirements.setModel(dmRequirements);

				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				renderer.setHorizontalAlignment(SwingConstants.LEFT);
				renderer.setBackground(Color.black);
				requirementsDialogReporting.tableforrequirements.getColumnModel().getColumn(0)
						.setCellRenderer(renderer);
				requirementsDialogReporting.tableforrequirements.getColumnModel().getColumn(1)
						.setCellRenderer(renderer);
				requirementsDialogReporting.tableforrequirements.getColumnModel().getColumn(2)
						.setCellRenderer(renderer);
				requirementsDialogReporting.tableforrequirements.getColumnModel().getColumn(3)
						.setCellRenderer(renderer);

			}
		});
		panelStudentPictureAndButtons.add(requirements);

		JButton Save = new JButton("Save");
		Save.setPreferredSize(new Dimension(135, 25));
		Save.setCursor(new Cursor(Cursor.HAND_CURSOR));
		Save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!fieldfeesPaid.getText().isEmpty()) {
					if (comboBoxClassofReporting.getSelectedIndex() == 0) {

						if (fieldclassNumber.getText().indexOf("1A") != -1
								|| fieldclassNumber.getText().indexOf("1B") != -1
								|| fieldclassNumber.getText().indexOf("1C") != -1) {

							saveToClassInWhicYouAreReporting();

							AddUpdateDelete("update student_ledger set credit='" + fieldfeesPaid.getText()
									+ "', receipt_number='" + fieldReceiptNumber.getText() + "',bank_name='"
									+ fieldBankName.getSelectedItem() + "', " + "bank_account='"
									+ fieldfeesPaidToAccount.getText() + "', account_name='"
									+ comboBoxSponsorName.getSelectedItem() + "' where class_number='"
									+ fieldclassNumber.getText() + "' and term='"
									+ comboBoxtermOfReporting.getSelectedItem() + "' and year='"
									+ yearofReporting.getYear() + "' and student_class='"
									+ comboBoxClassofReporting.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,receipt_number,details,folio,bank_dr,subcategory)"
											+ " select subcategory.catid,'" + fieldReceiptNumber.getText() + "',"
											+ "'School Fees','AutoGenerated Folio','" + fieldfeesPaid.getText() + "',"
											+ "'" + comboBoxSponsorName.getSelectedItem()
											+ "' from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntriesSilently(
									"insert into budget_expense_income_records(catid,subcategory,`"
											+ yearofReporting.getYear() + "`,account_type)"
											+ " select subcategory.catid,'" + comboBoxSponsorName.getSelectedItem()
											+ "','" + fieldfeesPaid.getText()
											+ "',account_type from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							saveToBank();
						} else {
							JOptionPane.showMessageDialog(null, "Class Number and Student Class Do Not Match");
						}

					} else if (comboBoxClassofReporting.getSelectedIndex() == 1) {
						if (fieldclassNumber.getText().indexOf("2A") != -1
								|| fieldclassNumber.getText().indexOf("2B") != -1
								|| fieldclassNumber.getText().indexOf("2C") != -1) {

							saveToClassInWhicYouAreReporting();

							AddUpdateDelete("update student_ledger set credit='" + fieldfeesPaid.getText()
									+ "', receipt_number='" + fieldReceiptNumber.getText() + "',bank_name='"
									+ fieldBankName.getSelectedItem() + "', " + "bank_account='"
									+ fieldfeesPaidToAccount.getText() + "', account_name='"
									+ comboBoxSponsorName.getSelectedItem() + "' where class_number='"
									+ fieldclassNumber.getText() + "' and term='"
									+ comboBoxtermOfReporting.getSelectedItem() + "' and year='"
									+ yearofReporting.getYear() + "' and student_class='"
									+ comboBoxClassofReporting.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,receipt_number,details,folio,bank_dr,subcategory)"
											+ " select subcategory.catid,'" + fieldReceiptNumber.getText() + "',"
											+ "'School Fees','AutoGenerated Folio','" + fieldfeesPaid.getText() + "',"
											+ "'" + comboBoxSponsorName.getSelectedItem()
											+ "' from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntriesSilently(
									"insert into budget_expense_income_records(catid,subcategory,`"
											+ yearofReporting.getYear() + "`,account_type)"
											+ " select subcategory.catid,'" + comboBoxSponsorName.getSelectedItem()
											+ "','" + fieldfeesPaid.getText()
											+ "',account_type from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							saveToBank();
						} else {
							JOptionPane.showMessageDialog(null, "Class Number and Student Class Do Not Match");
						}

					} else if (comboBoxClassofReporting.getSelectedIndex() == 2) {
						if (fieldclassNumber.getText().indexOf("3A") != -1
								|| fieldclassNumber.getText().indexOf("3B") != -1
								|| fieldclassNumber.getText().indexOf("3C") != -1) {

							saveToClassInWhicYouAreReporting();

							AddUpdateDelete("update student_ledger set credit='" + fieldfeesPaid.getText()
									+ "', receipt_number='" + fieldReceiptNumber.getText() + "',bank_name='"
									+ fieldBankName.getSelectedItem() + "', " + "bank_account='"
									+ fieldfeesPaidToAccount.getText() + "', account_name='"
									+ comboBoxSponsorName.getSelectedItem() + "' where class_number='"
									+ fieldclassNumber.getText() + "' and term='"
									+ comboBoxtermOfReporting.getSelectedItem() + "' and year='"
									+ yearofReporting.getYear() + "' and student_class='"
									+ comboBoxClassofReporting.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,receipt_number,details,folio,bank_dr,subcategory)"
											+ " select subcategory.catid,'" + fieldReceiptNumber.getText() + "',"
											+ "'School Fees','AutoGenerated Folio','" + fieldfeesPaid.getText() + "',"
											+ "'" + comboBoxSponsorName.getSelectedItem()
											+ "' from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntriesSilently(
									"insert into budget_expense_income_records(catid,subcategory,`"
											+ yearofReporting.getYear() + "`,account_type)"
											+ " select subcategory.catid,'" + comboBoxSponsorName.getSelectedItem()
											+ "','" + fieldfeesPaid.getText()
											+ "',account_type from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							saveToBank();
						} else {
							JOptionPane.showMessageDialog(null, "Class Number and Student Class Do Not Match");
						}

					} else if (comboBoxClassofReporting.getSelectedIndex() == 3) {
						if (fieldclassNumber.getText().indexOf("4A") != -1
								|| fieldclassNumber.getText().indexOf("4B") != -1
								|| fieldclassNumber.getText().indexOf("4C") != -1) {

							saveToClassInWhicYouAreReporting();

							AddUpdateDelete("update student_ledger set credit='" + fieldfeesPaid.getText()
									+ "', receipt_number='" + fieldReceiptNumber.getText() + "',bank_name='"
									+ fieldBankName.getSelectedItem() + "', " + "bank_account='"
									+ fieldfeesPaidToAccount.getText() + "', account_name='"
									+ comboBoxSponsorName.getSelectedItem() + "' where class_number='"
									+ fieldclassNumber.getText() + "' and term='"
									+ comboBoxtermOfReporting.getSelectedItem() + "' and year='"
									+ yearofReporting.getYear() + "' and student_class='"
									+ comboBoxClassofReporting.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,receipt_number,details,folio,bank_dr,subcategory)"
											+ " select subcategory.catid,'" + fieldReceiptNumber.getText() + "',"
											+ "'School Fees','AutoGenerated Folio','" + fieldfeesPaid.getText() + "',"
											+ "'" + comboBoxSponsorName.getSelectedItem()
											+ "' from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntriesSilently(
									"insert into budget_expense_income_records(catid,subcategory,`"
											+ yearofReporting.getYear() + "`,account_type)"
											+ " select subcategory.catid,'" + comboBoxSponsorName.getSelectedItem()
											+ "','" + fieldfeesPaid.getText()
											+ "',account_type from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							saveToBank();
						} else {
							JOptionPane.showMessageDialog(null, "Class Number and Student Class Do Not Match");
						}

					} else if (comboBoxClassofReporting.getSelectedIndex() == 4) {

						/************** S5 Arts or Sci **************************/

						if (fieldclassNumber.getText().indexOf("5Ar") != -1
								|| fieldclassNumber.getText().indexOf("5Sc") != -1) {

							saveToClassInWhicYouAreReporting();

							AddUpdateDelete("update student_ledger set credit='" + fieldfeesPaid.getText()
									+ "', receipt_number='" + fieldReceiptNumber.getText() + "',bank_name='"
									+ fieldBankName.getSelectedItem() + "', " + "bank_account='"
									+ fieldfeesPaidToAccount.getText() + "', account_name='"
									+ comboBoxSponsorName.getSelectedItem() + "' where class_number='"
									+ fieldclassNumber.getText() + "' and term='"
									+ comboBoxtermOfReporting.getSelectedItem() + "' and year='"
									+ yearofReporting.getYear() + "' and student_class='"
									+ comboBoxClassofReporting.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,receipt_number,details,folio,bank_dr,subcategory)"
											+ " select subcategory.catid,'" + fieldReceiptNumber.getText() + "',"
											+ "'School Fees','AutoGenerated Folio','" + fieldfeesPaid.getText() + "',"
											+ "'" + comboBoxSponsorName.getSelectedItem()
											+ "' from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntriesSilently(
									"insert into budget_expense_income_records(catid,subcategory,`"
											+ yearofReporting.getYear() + "`,account_type)"
											+ " select subcategory.catid,'" + comboBoxSponsorName.getSelectedItem()
											+ "','" + fieldfeesPaid.getText()
											+ "',account_type from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							saveToBank();
						} else {
							JOptionPane.showMessageDialog(null, "Class Number and Student Class Do Not Match");
						}

					} else if (comboBoxClassofReporting.getSelectedIndex() == 5) {

						/************ S6 ***************************/
						if (fieldclassNumber.getText().indexOf("6Ar") != -1
								|| fieldclassNumber.getText().indexOf("6Sc") != -1) {

							saveToClassInWhicYouAreReporting();

							AddUpdateDelete("update student_ledger set credit='" + fieldfeesPaid.getText()
									+ "', receipt_number='" + fieldReceiptNumber.getText() + "',bank_name='"
									+ fieldBankName.getSelectedItem() + "', " + "bank_account='"
									+ fieldfeesPaidToAccount.getText() + "', account_name='"
									+ comboBoxSponsorName.getSelectedItem() + "' where class_number='"
									+ fieldclassNumber.getText() + "' and term='"
									+ comboBoxtermOfReporting.getSelectedItem() + "' and year='"
									+ yearofReporting.getYear() + "' and student_class='"
									+ comboBoxClassofReporting.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntries(
									"insert into cash_book(catid,receipt_number,details,folio,bank_dr,subcategory)"
											+ " select subcategory.catid,'" + fieldReceiptNumber.getText() + "',"
											+ "'School Fees','AutoGenerated Folio','" + fieldfeesPaid.getText() + "',"
											+ "'" + comboBoxSponsorName.getSelectedItem()
											+ "' from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							new CashBookController().InsertCashBookEntriesSilently(
									"insert into budget_expense_income_records(catid,subcategory,`"
											+ yearofReporting.getYear() + "`,account_type)"
											+ " select subcategory.catid,'" + comboBoxSponsorName.getSelectedItem()
											+ "','" + fieldfeesPaid.getText()
											+ "',account_type from subcategory where subcategory.scatname='"
											+ comboBoxSponsorName.getSelectedItem() + "'");

							saveToBank();
						} else {
							JOptionPane.showMessageDialog(null, "Class Number and Student Class Do Not Match");
						}
					} else if (comboBoxClassofReporting.getSelectedIndex() == 6) {

						JOptionPane.showMessageDialog(null, "Class Number Invalid");
					}

				} else {
					JOptionPane.showMessageDialog(notifypop, "No Fees Payment Detected");
				}
			}
		});

		panelStudentPictureAndButtons.add(Save);

		JButton Print = new JButton("Print");
		Print.setPreferredSize(new Dimension(135, 25));
		Print.setCursor(new Cursor(Cursor.HAND_CURSOR));
		Print.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		panelStudentPictureAndButtons.add(Print);

		JButton PrintAndSave = new JButton("Save and Print");
		PrintAndSave.setPreferredSize(new Dimension(135, 25));
		PrintAndSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
		PrintAndSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		panelStudentPictureAndButtons.add(PrintAndSave);

		//////////////////// END OF REPORTING STUDENTS AND PARENTS
		//////////////////// INFO////////////////////////////////////////////////

		studentLeadersList = new StudentLeadersList();
		studentLeadersList.setVisible(false);
		studentLeadersList.setPreferredSize(new Dimension(1170, 490));
		home.add(studentLeadersList);

		JPanel panelforclasses = new JPanel();
		home.add(panelforclasses);
		panelforclasses.setVisible(false);
		panelforclasses.setPreferredSize(dimadmission);

		//////////////// Adding JavaFX BarChart/////////////////

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Student Classes");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Number Of Reprted Students");

		barChart = new BarChart(xAxis, yAxis);
		barChart.setHorizontalGridLinesVisible(true);
		barChart.setHorizontalZeroLineVisible(true);
		barChart.setVerticalGridLinesVisible(true);
		barChart.setVerticalZeroLineVisible(true);
		barChart.setMinWidth(1156);
		barChart.setMinHeight(428);
		barChart.setTitle("Termly Reporting Students Statistics");

		Button btnLoad = new Button("Load");
		btnLoad.setPrefWidth(100);

		Button btnClear = new Button("Clear");
		btnClear.setPrefWidth(100);

		dataTerm = FXCollections.observableArrayList("First Term", "Second Term", "Third Term");

		comboTerm = new ComboBox(dataTerm);
		comboTerm.setPromptText("Choose Term");
		comboTerm.setPrefWidth(150);

		VBox boxPanel = new VBox(5);
		boxPanel.setPadding(new Insets(10, 10, 10, 10));
		boxPanel.setAlignment(Pos.CENTER);

		HBox boxTerm = new HBox(5);
		boxTerm.setAlignment(Pos.CENTER);
		boxTerm.getChildren().addAll(comboTerm, btnLoad, btnClear);

		boxPanel.getChildren().addAll(boxTerm, barChart);

		fxPanelOvervie = new JFXPanel();
		fxPanelOvervie.setPreferredSize(new Dimension(1156, 468));
		Scene sceneOverview = new Scene(boxPanel);
		String styleSheet = getClass().getResource("attendance.css").toExternalForm();
		sceneOverview.getStylesheets().add(styleSheet);

		btnLoad.setOnAction(event -> {
			buildData();
		});

		btnClear.setOnAction(event -> {

			barChart.getData().clear();
			barChart.layout();
		});
		fxPanelOvervie.setScene(sceneOverview);

		panelforclasses.add(fxPanelOvervie);

		JLabel labelforpic = new JLabel("Picture");
		// panelforclasses.add(labelforpic);
		labelforpic.setPreferredSize(new Dimension(50, 50));
		labelforpic.setVisible(false);

		panelforclasses.setLayout(new FlowLayout(FlowLayout.LEFT));

		// buttons on display of all students in the school
		btnBackfromStudents = new JButton("Back To Home");
		btnBackfromStudents.setBackground(Color.red);
		// panelforclasses.add(btnBackfromStudents);
		btnBackfromStudents.setVisible(false);

		btnPrintAllStudents = new JButton("Print");
		// panelforclasses.add(btnPrintAllStudents);
		btnPrintAllStudents.setVisible(false);

		// table for all the students in the school in the current year

		String[][] studentsdata = new String[][] {

				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }

		};

		String[][] studentsdatafixedAllStudents = new String[][] {

				{ null, null, null, null, null, null, null } };

		String[] Studentsheading = new String[] { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory",
				"House" };

		allstudentsinAyear = new JTable(studentsdata, Studentsheading);
		allstudentsinAyear.setShowGrid(false);
		JTableHeader headerAllStdnts = allstudentsinAyear.getTableHeader();
		headerAllStdnts.setBackground(Color.BLACK);
		headerAllStdnts.setPreferredSize(new Dimension(400, 30));
		JScrollPane scrollerforallstudents = new JScrollPane(allstudentsinAyear);
		allstudentsinAyear.setRowHeight(25);
		scrollerforallstudents.setPreferredSize(new Dimension(1150, 400));
		Border bodaforstudents = BorderFactory.createRaisedBevelBorder();
		scrollerforallstudents.setBorder(bodaforstudents);
		// panelforclasses.add(scrollerforallstudents);
		allstudentsinAyear.setShowGrid(false);

		allstudentsinAyear.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent arg0) {

				// dtmAdmitted = new
				// JavaDatabaseSelectStatements().DisplayAdmittedStudents();
				// tableadmitted.setModel(dtmAdmitted);
				// allstudentsinAyear.setModel(dtmAdmitted);

			}

			@Override
			public void componentResized(ComponentEvent arg0) {

			}

			@Override
			public void componentMoved(ComponentEvent arg0) {

			}

			@Override
			public void componentHidden(ComponentEvent arg0) {

			}
		});

		fixedTableAllStudents = new JTable(studentsdatafixedAllStudents, Studentsheading);
		fixedTableAllStudents.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		fixedTableAllStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fixedTableAllStudents.setRowHeight(35);
		fixedTableAllStudents.setForeground(Color.white);
		MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();
		fixedTableAllStudents.setDefaultRenderer(Object.class, renderer);
		fixedTableAllStudents.setPreferredSize(new Dimension(1150, 40));

		JPopupMenu popupMenuDataAllStudents = new JPopupMenu();

		JMenuItem sumClubInfo = new JMenuItem("View Sum");
		sumClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int row = fixedTableAllStudents.getSelectedRow();
				int col = fixedTableAllStudents.getSelectedColumn();
				fixedTableAllStudents.setValueAt((getSumAllStudents()), row, col);

			}
		});

		JMenuItem averageClubInfo = new JMenuItem("View Average");
		averageClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int row = fixedTableAllStudents.getSelectedRow();
				int col = fixedTableAllStudents.getSelectedColumn();
				fixedTableAllStudents.setValueAt((getAverageAgeAllStudents()), row, col);

			}
		});

		JMenuItem rangeClubInfo = new JMenuItem("View Range");
		rangeClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int row = fixedTableAllStudents.getSelectedRow();
				int col = fixedTableAllStudents.getSelectedColumn();
				fixedTableAllStudents.setValueAt((getRangeAgeAllStudents()), row, col);

			}
		});

		JMenuItem maxClubInfo = new JMenuItem("View Max");
		maxClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int row = fixedTableAllStudents.getSelectedRow();
				int col = fixedTableAllStudents.getSelectedColumn();
				fixedTableAllStudents.setValueAt((getMaxAgeAllStudents()), row, col);

			}
		});

		JMenuItem minClubInfo = new JMenuItem("View Min");
		minClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int row = fixedTableAllStudents.getSelectedRow();
				int col = fixedTableAllStudents.getSelectedColumn();
				fixedTableAllStudents.setValueAt((getMinAgeAllStudents()), row, col);

			}
		});

		JMenuItem countClubInfo = new JMenuItem("View Count");
		countClubInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int row = fixedTableAllStudents.getSelectedRow();
				int col = fixedTableAllStudents.getSelectedColumn();
				fixedTableAllStudents.setValueAt((getCountAllStudents()), row, col);

			}
		});

		popupMenuDataAllStudents.add(sumClubInfo);
		popupMenuDataAllStudents.add(averageClubInfo);
		popupMenuDataAllStudents.add(rangeClubInfo);

		popupMenuDataAllStudents.add(maxClubInfo);
		popupMenuDataAllStudents.add(minClubInfo);
		popupMenuDataAllStudents.add(countClubInfo);

		fixedTableAllStudents.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {

				if (SwingUtilities.isRightMouseButton(e)) {
					popupMenuDataAllStudents.show(fixedTableAllStudents, e.getX(), e.getY());
				}
			}
		});

		JScrollPane fixedScroll = new JScrollPane(fixedTableAllStudents) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void setColumnHeaderView(Component view) {
			}
		};

		fixedScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JScrollBar bar = fixedScroll.getVerticalScrollBar();
		JScrollBar dummyBar = new JScrollBar() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
			}
		};
		dummyBar.setPreferredSize(bar.getPreferredSize());
		fixedScroll.setVerticalScrollBar(dummyBar);

		final JScrollBar bar1 = scrollerforallstudents.getHorizontalScrollBar();
		JScrollBar bar2 = fixedScroll.getHorizontalScrollBar();
		bar2.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				bar1.setValue(e.getValue());
			}
		});
		fixedScroll.setPreferredSize(new Dimension(1150, 35));
		// panelforclasses.add(fixedScroll);

		fixedTableAllStudents.getColumnModel().getColumn(0).setPreferredWidth(85);
		fixedTableAllStudents.getColumnModel().getColumn(2).setPreferredWidth(85);
		fixedTableAllStudents.getColumnModel().getColumn(3).setPreferredWidth(85);
		fixedTableAllStudents.getColumnModel().getColumn(4).setPreferredWidth(85);
		fixedTableAllStudents.getColumnModel().getColumn(5).setPreferredWidth(200);
		fixedTableAllStudents.getColumnModel().getColumn(6).setPreferredWidth(200);
		fixedTableAllStudents.getColumnModel().getColumn(1).setPreferredWidth(410);

		GeneralStudentsPanel generalStudentsPanel = new GeneralStudentsPanel();

		listenForMouseClickedInStudents = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				la1.setVisible(false);
				la5.setVisible(false);
				la2.setVisible(false);
				la6.setVisible(false);
				la3.setVisible(false);
				la7.setVisible(false);
				la4.setVisible(false);
				la8.setVisible(false);
				admission.setVisible(false);
				home.setLayout(new FlowLayout(FlowLayout.LEFT));
				admissionListViewPanel.setVisible(false);
				academicPanel.setVisible(false);
				studentsTreePanel.setVisible(true);
				studentLeadersList.setVisible(false);
				generalStudentsPanel.setVisible(true);
				panelforclasses.setVisible(false);
				allstudentsinAyear.setVisible(false);
				home.add(generalStudentsPanel);

				displayData(generalStudentsPanel.tableStudents,
						"select all_students_and_parents.class_number,all_students_and_parents.student_name,"
								+ "CASE WHEN all_students_and_parents.sponsor='Choose Sponsor' THEN 'Unknown'"
								+ " WHEN all_students_and_parents.sponsor is null then 'Choose Sponsor' ELSE "
								+ "all_students_and_parents.sponsor END AS sponsor,all_students_and_parents.payment_code,"
								+ "TIMESTAMPDIFF(YEAR,all_students_and_parents.date_of_birth,Now()),"
								+ "all_students_and_parents.dormitory from all_students_and_parents group by payment_code");

			}

		};

		la1.addMouseListener(listenForMouseClickedInStudents);

		la1.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

				la1.setIcon(new ImageIcon(img1));

			}

			@Override
			public void mouseEntered(MouseEvent e) {

				la1.setIcon(new ImageIcon(img11));

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		// Overview Of The Students Info

		JPanel outerpanel = new JPanel();
		// panelforclasses.add(outerpanel);
		Border bodaouter = BorderFactory.createRaisedBevelBorder();
		outerpanel.setBorder(bodaouter);
		outerpanel.setLayout(new GridLayout(0, 4, 5, 5));
		outerpanel.setVisible(false);

		JPanel senior1 = new JPanel();
		Border seniorone = BorderFactory.createTitledBorder("Senior 1 Overview");
		senior1.setBorder(seniorone);
		outerpanel.add(senior1);
		senior1.setPreferredSize(new Dimension(280, 220));
		senior1.setVisible(false);

		JPanel senior2 = new JPanel();
		Border seniortwo = BorderFactory.createTitledBorder("Senior 2 Overview");
		senior2.setBorder(seniortwo);
		outerpanel.add(senior2);
		senior2.setPreferredSize(new Dimension(280, 220));
		senior2.setVisible(false);

		JPanel senior3 = new JPanel();
		Border seniorthree = BorderFactory.createTitledBorder("Senior 3 Overview");
		senior3.setBorder(seniorthree);
		outerpanel.add(senior3);
		senior3.setPreferredSize(new Dimension(280, 220));
		senior3.setVisible(false);

		JPanel senior4 = new JPanel();
		Border seniorfour = BorderFactory.createTitledBorder("Senior 4 Overview");
		senior4.setBorder(seniorfour);
		outerpanel.add(senior4);
		senior4.setPreferredSize(new Dimension(280, 220));
		senior4.setVisible(false);

		JPanel senior5 = new JPanel();
		Border seniorfive = BorderFactory.createTitledBorder("Senior 5 Arts Overview");
		senior5.setBorder(seniorfive);
		outerpanel.add(senior5);
		senior5.setPreferredSize(new Dimension(280, 220));
		senior5.setVisible(false);

		JPanel senior6 = new JPanel();
		Border seniorsix = BorderFactory.createTitledBorder("Senior 5 Science Overview");
		senior6.setBorder(seniorsix);
		outerpanel.add(senior6);
		senior6.setPreferredSize(new Dimension(280, 220));
		senior6.setVisible(false);

		JPanel senior7 = new JPanel();
		Border seniorseven = BorderFactory.createTitledBorder("Senior 6 Arts Overview");
		senior7.setBorder(seniorseven);
		outerpanel.add(senior7);
		senior7.setPreferredSize(new Dimension(280, 220));
		senior7.setVisible(false);

		JPanel senior8 = new JPanel();
		Border senioreight = BorderFactory.createTitledBorder("Senior 6 Science Overview");
		senior8.setBorder(senioreight);
		outerpanel.add(senior8);
		senior8.setPreferredSize(new Dimension(280, 220));
		senior8.setVisible(false);

		// Tables for the different classes

		// Senior One

		panelS1 = new JPanel();
		panelS1.setPreferredSize(dimadmission);
		home.add(panelS1);
		panelS1.setVisible(false);

		String[][] dataS1 = new String[][] {

				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }

		};

		String[] headingS1 = new String[] { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory", "House" };

		tableS1 = new JTable(dataS1, headingS1);
		JTableHeader headerS1 = tableS1.getTableHeader();
		tableS1.setAutoCreateRowSorter(true);
		tableS1.setShowGrid(false);
		headerS1.setBackground(Color.BLACK);
		headerS1.setPreferredSize(new Dimension(400, 30));
		JScrollPane scrollerS1 = new JScrollPane(tableS1);
		tableS1.setRowHeight(25);
		scrollerS1.setPreferredSize(new Dimension(1145, 345));
		Border bodaS1 = BorderFactory.createRaisedBevelBorder();
		scrollerS1.setBorder(bodaS1);
		tableS1.setShowGrid(false);
		tableS1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouse) {

				int selectedRow = tableS1.getSelectedRow();

				String studentID = (String) tableS1.getValueAt(selectedRow, 0);
				String studentName = (String) tableS1.getValueAt(selectedRow, 1);
				try {

					java.sql.Connection conn = CashBookController.getConnection();

					PreparedStatement pst = conn.prepareStatement(
							"select photo from tableadmissionstudentdetails where id_number='" + studentID + "'");
					ResultSet rs = pst.executeQuery();

					if (rs.next()) {

						byte[] img = rs.getBytes("photo");
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image im2 = im.getScaledInstance(labelpictureS1.getWidth(), labelpictureS1.getHeight(),
								Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(im2);
						labelpictureS1.setIcon(newImage);

					} else {
						JOptionPane.showMessageDialog(null, "No Image Found For " + studentName + " In The Database",
								"Lacking " + studentName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});

		tableS1.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableS1.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableS1.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableS1.getColumnModel().getColumn(4).setPreferredWidth(85);
		tableS1.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableS1.getColumnModel().getColumn(6).setPreferredWidth(200);
		tableS1.getColumnModel().getColumn(1).setPreferredWidth(410);

		popupS1 = new JPopupMenu();
		detailsS1 = new JMenuItem("Edit Student's Details");
		visitationS1 = new JMenuItem("Visitation Records");
		suspensionS1 = new JMenuItem("Suspension Records");
		discontinueS1 = new JMenuItem("Discontinuation Records");

		popupS1.add(detailsS1);
		// popup.add(new JPopupMenu.Separator());
		popupS1.add(visitationS1);
		popupS1.add(suspensionS1);
		popupS1.add(discontinueS1);
		popupS1.setPreferredSize(new Dimension(250, 200));

		tableS1.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {

				if (SwingUtilities.isRightMouseButton(e)) {
					if (SwingUtilities.isRightMouseButton(e))
						popupS1.show(tableS1, e.getX(), e.getY());
				}

			}
		});

		
		btnbackS1 = new JButton("Back");
		panelS1.add(btnbackS1);

		btnprintS1 = new JButton("Print");
		panelS1.add(btnprintS1);

		JLabel fakelabel = new JLabel("");
		panelS1.add(fakelabel);
		fakelabel.setPreferredSize(new Dimension(920, 25));

		labelpictureS1 = new JLabel("Picture");
		labelpictureS1.setPreferredSize(new Dimension(110, 120));
		labelpictureS1.setBorder(new LineBorder(Color.RED, 2));
		panelS1.add(labelpictureS1);

		panelS1.add(scrollerS1);

		// Senior Two

		panelS2 = new JPanel();
		panelS2.setPreferredSize(dimadmission);
		home.add(panelS2);
		panelS2.setVisible(false);

		String[][] dataS2 = new String[][] {

				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }

		};

		String[] headingS2 = new String[] { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory", "House" };

		tableS2 = new JTable(dataS2, headingS2);
		tableS2.setAutoCreateRowSorter(true);
		JTableHeader headerS2 = tableS2.getTableHeader();
		tableS2.setShowGrid(false);
		headerS2.setBackground(Color.BLACK);
		headerS2.setPreferredSize(new Dimension(400, 30));

		JScrollPane scrollerS2 = new JScrollPane(tableS2);
		tableS2.setRowHeight(25);
		scrollerS2.setPreferredSize(new Dimension(1145, 345));
		Border bodaS2 = BorderFactory.createRaisedBevelBorder();
		scrollerS2.setBorder(bodaS2);
		tableS2.setShowGrid(false);
		tableS2.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableS2.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableS2.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableS2.getColumnModel().getColumn(4).setPreferredWidth(85);
		tableS2.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableS2.getColumnModel().getColumn(6).setPreferredWidth(200);
		tableS2.getColumnModel().getColumn(1).setPreferredWidth(410);

		popupS2 = new JPopupMenu();
		detailsS2 = new JMenuItem("Edit Student's Details");
		visitationS2 = new JMenuItem("Visitation Records");
		suspensionS2 = new JMenuItem("Suspension Records");
		discontinueS2 = new JMenuItem("Discontinuation Records");

		popupS2.add(detailsS2);
		popupS2.add(visitationS2);
		popupS2.add(suspensionS2);
		popupS2.add(discontinueS2);
		popupS2.setPreferredSize(new Dimension(250, 200));

		

		visitationS2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				new VisitationDay();

			}
		});

		suspensionS2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SuspensionRecords();

			}
		});

		discontinueS2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DiscontinuationRecords();

			}
		});

		tableS2.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				int selectedRow = tableS2.getSelectedRow();

				String studentID = (String) tableS2.getValueAt(selectedRow, 0);
				String studentName = (String) tableS2.getValueAt(selectedRow, 1);
				try {

					java.sql.Connection conn = CashBookController.getConnection();

					PreparedStatement pst = conn.prepareStatement(
							"select photo from tableadmissionstudentdetails where id_number='" + studentID + "'");
					ResultSet rs = pst.executeQuery();

					if (rs.next()) {

						byte[] img = rs.getBytes("photo");
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image im2 = im.getScaledInstance(labelpictureS2.getWidth(), labelpictureS2.getHeight(),
								Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(im2);
						labelpictureS2.setIcon(newImage);

					} else {
						JOptionPane.showMessageDialog(null, "No Image Found For " + studentName + " In The Database",
								"Lacking " + studentName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (SwingUtilities.isRightMouseButton(arg0))
					popupS2.show(tableS2, arg0.getX(), arg0.getY());

			}
		});

		btnbackS2 = new JButton("Back");
		panelS2.add(btnbackS2);

		btnprintS2 = new JButton("Print");
		panelS2.add(btnprintS2);

		JLabel fakelabel1 = new JLabel("");
		panelS2.add(fakelabel1);
		fakelabel1.setPreferredSize(new Dimension(920, 25));

		labelpictureS2 = new JLabel("Picture S2");
		labelpictureS2.setPreferredSize(new Dimension(110, 120));
		labelpictureS2.setBorder(new LineBorder(Color.RED, 2));
		panelS2.add(labelpictureS2);

		panelS2.add(scrollerS2);

		// Senior Three

		panelS3 = new JPanel();
		panelS3.setPreferredSize(dimadmission);
		home.add(panelS3);
		panelS3.setVisible(false);

		String[][] dataS3 = new String[][] {

				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, "White", null, null, null }, { null, null, null, "White", null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }

		};

		String[] headingS3 = new String[] { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory", "House" };

		tableS3 = new JTable(dataS3, headingS3);
		tableS3.setAutoCreateRowSorter(true);
		JTableHeader headerS3 = tableS3.getTableHeader();
		tableS3.setShowGrid(false);
		headerS3.setBackground(Color.BLACK);
		headerS3.setPreferredSize(new Dimension(400, 30));

		JScrollPane scrollerS3 = new JScrollPane(tableS3);
		tableS3.setRowHeight(25);
		scrollerS3.setPreferredSize(new Dimension(1145, 345));
		Border bodaS3 = BorderFactory.createRaisedBevelBorder();
		scrollerS3.setBorder(bodaS3);
		tableS3.setShowGrid(false);
		tableS3.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableS3.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableS3.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableS3.getColumnModel().getColumn(4).setPreferredWidth(85);
		tableS3.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableS3.getColumnModel().getColumn(6).setPreferredWidth(200);
		tableS3.getColumnModel().getColumn(1).setPreferredWidth(410);

		popupS3 = new JPopupMenu();
		detailsS3 = new JMenuItem("Edit Student's Details");
		visitationS3 = new JMenuItem("Visitation Records");
		suspensionS3 = new JMenuItem("Suspension Records");
		discontinueS3 = new JMenuItem("Discontinuation Records");

		popupS3.add(detailsS3);
		popupS3.add(visitationS3);
		popupS3.add(suspensionS3);
		popupS3.add(discontinueS3);
		popupS3.setPreferredSize(new Dimension(350, 300));

			visitationS3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				new VisitationDay();

			}
		});

		suspensionS3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SuspensionRecords();

			}
		});

		discontinueS3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DiscontinuationRecords();

			}
		});

		tableS3.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				int selectedRow = tableS3.getSelectedRow();

				String studentID = (String) tableS3.getValueAt(selectedRow, 0);
				String studentName = (String) tableS3.getValueAt(selectedRow, 1);
				try {

					java.sql.Connection conn = CashBookController.getConnection();

					PreparedStatement pst = conn.prepareStatement(
							"select photo from tableadmissionstudentdetails where id_number='" + studentID + "'");
					ResultSet rs = pst.executeQuery();

					if (rs.next()) {

						byte[] img = rs.getBytes("photo");
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image im2 = im.getScaledInstance(labelpictureS3.getWidth(), labelpictureS3.getHeight(),
								Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(im2);
						labelpictureS3.setIcon(newImage);

					} else {
						JOptionPane.showMessageDialog(null, "No Image Found For " + studentName + " In The Database",
								"Lacking " + studentName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (SwingUtilities.isRightMouseButton(arg0))
					popupS3.show(tableS3, arg0.getX(), arg0.getY());

			}
		});

		btnbackS3 = new JButton("Back");
		panelS3.add(btnbackS3);

		btnprintS3 = new JButton("Print");
		panelS3.add(btnprintS3);

		JLabel fakelabel2 = new JLabel("");
		panelS3.add(fakelabel2);
		fakelabel2.setPreferredSize(new Dimension(920, 25));

		labelpictureS3 = new JLabel("Picture S3");
		labelpictureS3.setPreferredSize(new Dimension(110, 120));
		labelpictureS3.setBorder(new LineBorder(Color.RED, 2));
		panelS3.add(labelpictureS3);

		panelS3.add(scrollerS3);

		// Senior Six Arts

		panelS6Arts = new JPanel();
		panelS6Arts.setPreferredSize(dimadmission);
		home.add(panelS6Arts);
		panelS6Arts.setVisible(false);

		String[][] dataS6Arts = new String[][] {

				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }

		};

		String[] headingS6Arts = new String[] { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory",
				"House" };

		tableS6Arts = new JTable(dataS6Arts, headingS6Arts);
		tableS6Arts.setAutoCreateRowSorter(true);
		JTableHeader headerS6Arts = tableS6Arts.getTableHeader();
		tableS6Arts.setShowGrid(false);
		headerS6Arts.setBackground(Color.BLACK);
		headerS6Arts.setPreferredSize(new Dimension(400, 30));
		tableS6Arts.setShowGrid(false);
		JScrollPane scrollerS6Arts = new JScrollPane(tableS6Arts);
		tableS6Arts.setRowHeight(25);
		scrollerS6Arts.setPreferredSize(new Dimension(1145, 345));
		Border bodaS6Arts = BorderFactory.createRaisedBevelBorder();
		scrollerS6Arts.setBorder(bodaS6Arts);
		tableS6Arts.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableS6Arts.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableS6Arts.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableS6Arts.getColumnModel().getColumn(4).setPreferredWidth(85);
		tableS6Arts.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableS6Arts.getColumnModel().getColumn(6).setPreferredWidth(200);
		tableS6Arts.getColumnModel().getColumn(1).setPreferredWidth(410);

		popupS6Arts = new JPopupMenu();
		detailsS6Arts = new JMenuItem("Edit Student's Details");
		visitationS6Arts = new JMenuItem("Visitation Records");
		suspensionS6Arts = new JMenuItem("Suspension Records");
		discontinueS6Arts = new JMenuItem("Discontinuation Records");

		popupS6Arts.add(detailsS6Arts);
		popupS6Arts.add(visitationS6Arts);
		popupS6Arts.add(suspensionS6Arts);
		popupS6Arts.add(discontinueS6Arts);
		popupS6Arts.setPreferredSize(new Dimension(250, 200));

		
		visitationS6Arts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				new VisitationDay();

			}
		});

		suspensionS6Arts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SuspensionRecords();

			}
		});

		discontinueS6Arts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DiscontinuationRecords();

			}
		});

		tableS6Arts.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (SwingUtilities.isRightMouseButton(arg0))
					popupS6Arts.show(tableS6Arts, arg0.getX(), arg0.getY());

			}
		});

		btnbackS6Arts = new JButton("Back");
		panelS6Arts.add(btnbackS6Arts);

		btnprintS6Arts = new JButton("Print");
		panelS6Arts.add(btnprintS6Arts);

		JLabel fakelabel6arts = new JLabel("");
		panelS6Arts.add(fakelabel6arts);
		fakelabel6arts.setPreferredSize(new Dimension(920, 25));

		labelpictureS6Arts = new JLabel("Picture S6Arts");
		labelpictureS6Arts.setPreferredSize(new Dimension(110, 120));
		labelpictureS6Arts.setBorder(new LineBorder(Color.RED, 2));
		panelS6Arts.add(labelpictureS6Arts);

		panelS6Arts.add(scrollerS6Arts);

		// Senior Six Science

		panelS6Sci = new JPanel();
		panelS6Sci.setPreferredSize(dimadmission);
		home.add(panelS6Sci);
		panelS6Sci.setVisible(false);

		String[][] dataS6Sci = new String[][] {

				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }

		};

		String[] headingS6Sci = new String[] { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory",
				"House" };

		tableS6Sci = new JTable(dataS6Sci, headingS6Sci);
		tableS6Sci.setAutoCreateRowSorter(true);
		tableS6Sci.setShowGrid(false);
		JTableHeader headerS6Sci = tableS6Sci.getTableHeader();
		tableS6Sci.setShowGrid(false);
		headerS6Sci.setBackground(Color.BLACK);
		headerS6Sci.setPreferredSize(new Dimension(400, 30));

		JScrollPane scrollerS6Sci = new JScrollPane(tableS6Sci);
		tableS6Sci.setRowHeight(25);
		scrollerS6Sci.setPreferredSize(new Dimension(1145, 345));
		Border bodaS6Sci = BorderFactory.createRaisedBevelBorder();
		scrollerS6Sci.setBorder(bodaS6Sci);
		tableS6Sci.setShowGrid(false);
		tableS6Sci.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableS6Sci.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableS6Sci.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableS6Sci.getColumnModel().getColumn(4).setPreferredWidth(85);
		tableS6Sci.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableS6Sci.getColumnModel().getColumn(6).setPreferredWidth(200);
		tableS6Sci.getColumnModel().getColumn(1).setPreferredWidth(410);

		popupS6Sci = new JPopupMenu();
		detailsS6Sci = new JMenuItem("Edit Student's Details");
		visitationS6Sci = new JMenuItem("Visitation Records");
		suspensionS6Sci = new JMenuItem("Suspension Records");
		discontinueS6Sci = new JMenuItem("Discontinuation Records");

		popupS6Sci.add(detailsS6Sci);
		popupS6Sci.add(visitationS6Sci);
		popupS6Sci.add(suspensionS6Sci);
		popupS6Sci.add(discontinueS6Sci);
		popupS6Sci.setPreferredSize(new Dimension(250, 200));

	
		visitationS6Sci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				new VisitationDay();

			}
		});

		suspensionS6Sci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SuspensionRecords();

			}
		});

		discontinueS6Sci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DiscontinuationRecords();

			}
		});

		tableS6Sci.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (SwingUtilities.isRightMouseButton(arg0))
					popupS6Sci.show(tableS6Sci, arg0.getX(), arg0.getY());

			}
		});

		btnbackS6Sci = new JButton("Back");
		panelS6Sci.add(btnbackS6Sci);

		btnprintS6Sci = new JButton("Print");
		panelS6Sci.add(btnprintS6Sci);

		JLabel fakelabel6sci = new JLabel("");
		panelS6Sci.add(fakelabel6sci);
		fakelabel6sci.setPreferredSize(new Dimension(920, 25));

		labelpictureS6Sci = new JLabel("Picture S6 Sci");
		labelpictureS6Sci.setPreferredSize(new Dimension(110, 120));
		labelpictureS6Sci.setBorder(new LineBorder(Color.RED, 2));
		panelS6Sci.add(labelpictureS6Sci);

		panelS6Sci.add(scrollerS6Sci);

		// Senior Four

		panelS4 = new JPanel();
		panelS4.setPreferredSize(dimadmission);
		home.add(panelS4);
		panelS4.setVisible(false);

		String[][] dataS4 = new String[][] {

				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }

		};

		String[] headingS4 = new String[] { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory", "House" };

		tableS4 = new JTable(dataS4, headingS4);
		tableS4.setAutoCreateRowSorter(true);
		tableS4.setShowGrid(false);
		JScrollPane scrollerS4 = new JScrollPane(tableS4);
		tableS4.setRowHeight(25);
		scrollerS4.setPreferredSize(new Dimension(1145, 345));
		Border bodaS4 = BorderFactory.createRaisedBevelBorder();
		scrollerS4.setBorder(bodaS4);
		tableS4.setShowGrid(false);
		tableS4.setIntercellSpacing(new Dimension(0, 0));
		JTableHeader headerS4 = tableS4.getTableHeader();
		headerS4.setBackground(Color.BLACK);
		headerS4.setForeground(Color.white);
		headerS4.setPreferredSize(new Dimension(1170, 30));

		tableS4.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableS4.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableS4.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableS4.getColumnModel().getColumn(4).setPreferredWidth(85);
		tableS4.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableS4.getColumnModel().getColumn(6).setPreferredWidth(200);
		tableS4.getColumnModel().getColumn(1).setPreferredWidth(410);

		popupS4 = new JPopupMenu();
		detailsS4 = new JMenuItem("Edit Student's Details");
		visitationS4 = new JMenuItem("Visitation Records");
		suspensionS4 = new JMenuItem("Suspension Records");
		discontinueS4 = new JMenuItem("Discontinuation Records");

		popupS4.add(detailsS4);
		popupS4.add(visitationS4);
		popupS4.add(suspensionS4);
		popupS4.add(discontinueS4);
		popupS4.setPreferredSize(new Dimension(250, 200));

		
		visitationS4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				new VisitationDay();

			}
		});

		suspensionS4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SuspensionRecords();

			}
		});

		discontinueS4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DiscontinuationRecords();

			}
		});
		tableS4.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				int selectedRow = tableS4.getSelectedRow();

				String studentID = (String) tableS4.getValueAt(selectedRow, 0);
				String studentName = (String) tableS4.getValueAt(selectedRow, 1);
				try {

					java.sql.Connection conn = CashBookController.getConnection();

					PreparedStatement pst = conn.prepareStatement(
							"select photo from tableadmissionstudentdetails where id_number='" + studentID + "'");
					ResultSet rs = pst.executeQuery();

					if (rs.next()) {

						byte[] img = rs.getBytes("photo");
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image im2 = im.getScaledInstance(labelpictureS4.getWidth(), labelpictureS4.getHeight(),
								Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(im2);
						labelpictureS4.setIcon(newImage);

					} else {
						JOptionPane.showMessageDialog(null, "No Image Found For " + studentName + " In The Database",
								"Lacking " + studentName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (SwingUtilities.isRightMouseButton(arg0))
					popupS4.show(tableS4, arg0.getX(), arg0.getY());

			}
		});

		btnbackS4 = new JButton("Back");
		panelS4.add(btnbackS4);

		btnprintS4 = new JButton("Print");
		panelS4.add(btnprintS4);

		JLabel fakelabel3 = new JLabel("");
		panelS4.add(fakelabel3);
		fakelabel3.setPreferredSize(new Dimension(920, 25));

		labelpictureS4 = new JLabel("Picture S4");
		labelpictureS4.setPreferredSize(new Dimension(110, 120));
		labelpictureS4.setBorder(new LineBorder(Color.RED, 2));
		panelS4.add(labelpictureS4);

		panelS4.add(scrollerS4);

		// Senior Five

		panelS5Arts = new JPanel();
		panelS5Arts.setPreferredSize(dimadmission);
		home.add(panelS5Arts);
		panelS5Arts.setVisible(false);

		String[][] dataS5Arts = new String[][] {

				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }

		};

		String[] headingS5Arts = new String[] { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory",
				"House" };

		tableS5Arts = new JTable(dataS5Arts, headingS5Arts);
		tableS5Arts.setAutoCreateRowSorter(true);
		tableS5Arts.setShowGrid(false);
		JTableHeader headerS5Arts = tableS5Arts.getTableHeader();
		headerS5Arts.setBackground(Color.BLACK);
		headerS5Arts.setForeground(Color.white);
		headerS5Arts.setPreferredSize(new Dimension(1170, 30));
		JScrollPane scrollerS5Arts = new JScrollPane(tableS5Arts);
		tableS5Arts.setRowHeight(25);
		scrollerS5Arts.setPreferredSize(new Dimension(1145, 345));
		Border bodaS5Arts = BorderFactory.createRaisedBevelBorder();
		scrollerS5Arts.setBorder(bodaS5Arts);
		tableS5Arts.setShowGrid(false);
		tableS5Arts.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableS5Arts.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableS5Arts.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableS5Arts.getColumnModel().getColumn(4).setPreferredWidth(85);
		tableS5Arts.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableS5Arts.getColumnModel().getColumn(6).setPreferredWidth(200);
		tableS5Arts.getColumnModel().getColumn(1).setPreferredWidth(410);

		popupS5Arts = new JPopupMenu();
		detailsS5Arts = new JMenuItem("Edit Student's Details");
		visitationS5Arts = new JMenuItem("Visitation Records");
		suspensionS5Arts = new JMenuItem("Suspension Records");
		discontinueS5Arts = new JMenuItem("Discontinuation Records");

		popupS5Arts.add(detailsS5Arts);
		popupS5Arts.add(visitationS5Arts);
		popupS5Arts.add(suspensionS5Arts);
		popupS5Arts.add(discontinueS5Arts);
		popupS5Arts.setPreferredSize(new Dimension(250, 200));

		

		visitationS5Arts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				new VisitationDay();

			}
		});

		suspensionS5Arts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SuspensionRecords();

			}
		});

		discontinueS5Arts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DiscontinuationRecords();

			}
		});
		tableS5Arts.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				int selectedRow = tableS5Arts.getSelectedRow();

				String studentID = (String) tableS5Arts.getValueAt(selectedRow, 0);
				String studentName = (String) tableS5Arts.getValueAt(selectedRow, 1);
				try {

					java.sql.Connection conn = CashBookController.getConnection();

					PreparedStatement pst = conn.prepareStatement(
							"select photo from tableadmissionstudentdetails where id_number='" + studentID + "'");
					ResultSet rs = pst.executeQuery();

					if (rs.next()) {

						byte[] img = rs.getBytes("photo");
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image im2 = im.getScaledInstance(labelpictureS5Arts.getWidth(), labelpictureS5Arts.getHeight(),
								Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(im2);
						labelpictureS5Arts.setIcon(newImage);

					} else {
						JOptionPane.showMessageDialog(null, "No Image Found For " + studentName + " In The Database",
								"Lacking " + studentName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (SwingUtilities.isRightMouseButton(arg0))
					popupS5Arts.show(tableS5Arts, arg0.getX(), arg0.getY());

			}
		});

		btnbackS5Arts = new JButton("Back");
		panelS5Arts.add(btnbackS5Arts);

		btnprintS5Arts = new JButton("Print");
		panelS5Arts.add(btnprintS5Arts);

		JLabel fakelabel4 = new JLabel("");
		panelS5Arts.add(fakelabel4);
		fakelabel4.setPreferredSize(new Dimension(920, 25));

		labelpictureS5Arts = new JLabel("Picture 5arts");
		labelpictureS5Arts.setPreferredSize(new Dimension(110, 120));
		labelpictureS5Arts.setBorder(new LineBorder(Color.RED, 2));
		panelS5Arts.add(labelpictureS5Arts);

		panelS5Arts.add(scrollerS5Arts);

		// Senior 5 Sci

		panelS5Sci = new JPanel();
		panelS5Sci.setPreferredSize(dimadmission);
		home.add(panelS5Sci);
		panelS5Sci.setVisible(false);

		String[][] dataS5Sci = new String[][] {

				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }

		};

		String[] headingS5Sci = new String[] { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory",
				"House" };

		tableS5Sci = new JTable(dataS5Sci, headingS5Sci);
		tableS5Sci.setAutoscrolls(true);
		tableS5Sci.setAutoCreateColumnsFromModel(true);
		tableS5Sci.setAutoCreateRowSorter(true);
		tableS5Sci.setShowGrid(false);
		JTableHeader headerS5Sci = tableS5Sci.getTableHeader();
		headerS5Sci.setBackground(Color.BLACK);
		headerS5Sci.setForeground(Color.white);
		headerS5Sci.setPreferredSize(new Dimension(1170, 30));
		JScrollPane scrollerS5Sci = new JScrollPane(tableS5Sci);
		tableS5Sci.setRowHeight(25);
		scrollerS5Sci.setPreferredSize(new Dimension(1145, 345));
		Border bodaS5Sci = BorderFactory.createRaisedBevelBorder();
		scrollerS5Sci.setBorder(bodaS5Sci);
		tableS5Sci.setShowGrid(false);
		tableS5Sci.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableS5Sci.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableS5Sci.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableS5Sci.getColumnModel().getColumn(4).setPreferredWidth(85);
		tableS5Sci.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableS5Sci.getColumnModel().getColumn(6).setPreferredWidth(200);
		tableS5Sci.getColumnModel().getColumn(1).setPreferredWidth(410);

		popupS5Sci = new JPopupMenu();
		detailsS5Sci = new JMenuItem("Edit Student's Details");
		visitationS5Sci = new JMenuItem("Visitation Records");
		suspensionS5Sci = new JMenuItem("Suspension Records");
		discontinueS5Sci = new JMenuItem("Discontinuation Records");

		popupS5Sci.add(detailsS5Sci);
		popupS5Sci.add(visitationS5Sci);
		popupS5Sci.add(suspensionS5Sci);
		popupS5Sci.add(discontinueS5Sci);
		popupS5Sci.setPreferredSize(new Dimension(250, 200));


		visitationS5Sci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				new VisitationDay();

			}
		});

		suspensionS5Sci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SuspensionRecords();

			}
		});

		discontinueS5Sci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DiscontinuationRecords();

			}
		});

		tableS5Sci.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				int selectedRow = tableS5Sci.getSelectedRow();

				String studentID = (String) tableS5Sci.getValueAt(selectedRow, 0);
				String studentName = (String) tableS5Sci.getValueAt(selectedRow, 1);
				try {

					java.sql.Connection conn = CashBookController.getConnection();

					PreparedStatement pst = conn.prepareStatement(
							"select photo from tableadmissionstudentdetails where id_number='" + studentID + "'");
					ResultSet rs = pst.executeQuery();

					if (rs.next()) {

						byte[] img = rs.getBytes("photo");
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image im2 = im.getScaledInstance(labelpictureS5Sci.getWidth(), labelpictureS5Sci.getHeight(),
								Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(im2);
						labelpictureS5Sci.setIcon(newImage);

					} else {
						JOptionPane.showMessageDialog(null, "No Image Found For " + studentName + " In The Database",
								"Lacking " + studentName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (SwingUtilities.isRightMouseButton(arg0))
					popupS5Sci.show(tableS5Sci, arg0.getX(), arg0.getY());

			}
		});

		btnbackS5Sci = new JButton("Back");
		panelS5Sci.add(btnbackS5Sci);

		btnprintS5Sci = new JButton("Print");
		panelS5Sci.add(btnprintS5Sci);

		JLabel fakelabel5a = new JLabel("");
		panelS5Sci.add(fakelabel5a);
		fakelabel5a.setPreferredSize(new Dimension(920, 25));

		labelpictureS5Sci = new JLabel("Picture 5Sci");
		labelpictureS5Sci.setPreferredSize(new Dimension(110, 120));
		labelpictureS5Sci.setBorder(new LineBorder(Color.RED, 2));
		panelS5Sci.add(labelpictureS5Sci);

		panelS5Sci.add(scrollerS5Sci);

		panelPrefects = new JPanel();
		panelPrefects.setPreferredSize(dimadmission);
		home.add(panelPrefects);
		panelPrefects.setVisible(false);

		panelUNSA = new JPanel();
		panelUNSA.setPreferredSize(dimadmission);
		home.add(panelUNSA);
		panelUNSA.setVisible(false);

		// Listener for the tree

		alertsNumbers = new MessagesAlertsNumbers();
		alertsNumbers.getLvListDisplayedFinance().setPrefSize(150, 540);
		leftpanel.add(alertsNumbers);
		alertsNumbers.setVisible(false);

		studentsTreePanel = new StudentsTreePanel();
		studentsTreePanel.getLvListDisplayedStudents().setPrefSize(150, 540);
		leftpanel.add(studentsTreePanel);
		studentsTreePanel.setVisible(false);

		studentsTreePanel.getLvListDisplayedStudents().getSelectionModel().selectedItemProperty()
				.addListener(new InvalidationListener() {

					@Override
					public void invalidated(Observable arg0) {

						String node1 = studentsTreePanel.getLvListDisplayedStudents().getSelectionModel()
								.getSelectedItem().toString();

						if (node1.equals("All Students")) {

							la1.setVisible(false);
							la5.setVisible(false);
							la2.setVisible(false);
							la6.setVisible(false);
							la3.setVisible(false);
							la7.setVisible(false);
							la4.setVisible(false);
							la8.setVisible(false);
							admission.setVisible(false);
							home.setLayout(new FlowLayout(FlowLayout.LEFT));
							admissionListViewPanel.setVisible(false);
							academicPanel.setVisible(false);
							studentsTreePanel.setVisible(true);
							studentLeadersList.setVisible(false);
							generalStudentsPanel.setVisible(true);
							panelforclasses.setVisible(false);
							allstudentsinAyear.setVisible(false);
							home.add(generalStudentsPanel);

							displayData(generalStudentsPanel.tableStudents,
									"select all_students_and_parents.class_number,all_students_and_parents.student_name,"
											+ "CASE WHEN all_students_and_parents.sponsor='Choose Sponsor' THEN 'Unknown'"
											+ " WHEN all_students_and_parents.sponsor is null then 'Choose Sponsor' ELSE "
											+ "all_students_and_parents.sponsor END AS sponsor,all_students_and_parents.payment_code,"
											+ "TIMESTAMPDIFF(YEAR,all_students_and_parents.date_of_birth,Now()),"
											+ "all_students_and_parents.dormitory from all_students_and_parents group by payment_code");

						} else if (node1.equals("Reporting Overview")) {

							studentLeadersList.setVisible(false);
							scrollerforallstudents.setVisible(false);
							fixedScroll.setVisible(false);
							senior1.setVisible(true);
							senior2.setVisible(true);
							senior3.setVisible(true);
							senior4.setVisible(true);
							senior5.setVisible(true);
							senior6.setVisible(true);
							senior7.setVisible(true);
							senior8.setVisible(true);
							outerpanel.setVisible(true);
							panelforclasses.setLayout(new FlowLayout(FlowLayout.LEFT));
							panelS2.setVisible(false);
							panelS1.setVisible(false);
							panelS3.setVisible(false);
							panelS4.setVisible(false);
							panelS5Arts.setVisible(false);
							panelS5Sci.setVisible(false);
							panelS6Arts.setVisible(false);
							panelS6Sci.setVisible(false);

							panelforclasses.setVisible(true);

							btnBackFromOverview.setVisible(true);
							btnPrintOverview.setVisible(true);
							btnBackfromStudents.setVisible(false);
							btnPrintAllStudents.setVisible(false);
							generalStudentsPanel.setVisible(false);

						} else if (node1.equals("Senior One")) {

							cal = Calendar.getInstance();
							year = "" + cal.getTime();
							thisyear = year.substring(year.length() - 4);
							studentLeadersList.setVisible(false);

							generalStudentsPanel.labelStudentImage.setIcon(null);
							generalStudentsPanel.displayStudentsofAClass(generalStudentsPanel.labelClass,
									"select class_name from student_classes where id=1");

							displayData(generalStudentsPanel.tableStudents,
									"select class_number,student_name,CASE WHEN sponsor='Choose Sponsor' THEN 'Not Sponsored' WHEN sponsor='' THEN 'Unknown' ELSE 'Yes' END AS Sponsored,payment_code,"
											+ "TIMESTAMPDIFF(YEAR,`" + generalStudentsPanel.labelClass.getText()
											+ "`.date_of_birth,Now()),dormitory from `"
											+ generalStudentsPanel.labelClass.getText() + "`  where year='" + thisyear
											+ "' and (discipline_status='suspended' or discipline_status is null) group by payment_code");

							generalStudentsPanel.setVisible(true);
							panelforclasses.setVisible(false);
							studentsTreePanel.setVisible(true);
							allstudentsinAyear.setVisible(false);
							home.add(generalStudentsPanel);
							generalStudentsPanel.labelUser.setText(current_user.getText());

						} else if (node1.equals("Senior Two")) {

							studentLeadersList.setVisible(false);

							generalStudentsPanel.labelStudentImage.setIcon(null);
							generalStudentsPanel.displayStudentsofAClass(generalStudentsPanel.labelClass,
									"select class_name from student_classes where id=2");

							displayData(generalStudentsPanel.tableStudents,
									"select class_number,student_name,CASE WHEN sponsor='Choose Sponsor' THEN 'Not Sponsored' WHEN sponsor='' THEN 'Unknown' ELSE 'Yes' END AS Sponsored,payment_code,"
											+ "TIMESTAMPDIFF(YEAR,`" + generalStudentsPanel.labelClass.getText()
											+ "`.date_of_birth,Now()),dormitory from `"
											+ generalStudentsPanel.labelClass.getText() + "` where year='" + thisyear
											+ "' and (discipline_status='suspended' or discipline_status is null) group by payment_code");

							generalStudentsPanel.setVisible(true);
							panelforclasses.setVisible(false);
							studentsTreePanel.setVisible(true);
							allstudentsinAyear.setVisible(false);
							home.add(generalStudentsPanel);
							generalStudentsPanel.labelUser.setText(current_user.getText());

						} else if (node1.equals("Senior Three")) {

							studentLeadersList.setVisible(false);

							generalStudentsPanel.labelStudentImage.setIcon(null);
							generalStudentsPanel.displayStudentsofAClass(generalStudentsPanel.labelClass,
									"select class_name from student_classes where id=3");

							displayData(generalStudentsPanel.tableStudents,
									"select class_number,student_name,CASE WHEN sponsor='Choose Sponsor' THEN 'Not Sponsored' WHEN sponsor='' THEN 'Unknown' ELSE 'Yes' END AS Sponsored,payment_code,"
											+ "TIMESTAMPDIFF(YEAR,`" + generalStudentsPanel.labelClass.getText()
											+ "`.date_of_birth,Now()),dormitory from `"
											+ generalStudentsPanel.labelClass.getText() + "` where year='" + thisyear
											+ "' and (discipline_status='suspended' or discipline_status is null) group by payment_code");

							generalStudentsPanel.setVisible(true);
							panelforclasses.setVisible(false);
							studentsTreePanel.setVisible(true);
							allstudentsinAyear.setVisible(false);
							home.add(generalStudentsPanel);
							generalStudentsPanel.labelUser.setText(current_user.getText());

						} else if (node1.equals("Senior Four")) {

							studentLeadersList.setVisible(false);

							generalStudentsPanel.labelStudentImage.setIcon(null);
							generalStudentsPanel.displayStudentsofAClass(generalStudentsPanel.labelClass,
									"select class_name from student_classes where id=4");

							displayData(generalStudentsPanel.tableStudents,
									"select class_number,student_name,CASE WHEN sponsor='Choose Sponsor' THEN 'Not Sponsored' WHEN sponsor='' THEN 'Unknown' ELSE 'Yes' END AS Sponsored,payment_code,"
											+ "TIMESTAMPDIFF(YEAR,`" + generalStudentsPanel.labelClass.getText()
											+ "`.date_of_birth,Now()),dormitory from `"
											+ generalStudentsPanel.labelClass.getText() + "` where year='" + thisyear
											+ "' and (discipline_status='suspended' or discipline_status is null) group by payment_code");

							generalStudentsPanel.setVisible(true);
							panelforclasses.setVisible(false);
							studentsTreePanel.setVisible(true);
							allstudentsinAyear.setVisible(false);
							home.add(generalStudentsPanel);
							generalStudentsPanel.labelUser.setText(current_user.getText());

						} else if (node1.equals("Senior Five")) {

							studentLeadersList.setVisible(false);

							generalStudentsPanel.labelStudentImage.setIcon(null);
							generalStudentsPanel.displayStudentsofAClass(generalStudentsPanel.labelClass,
									"select class_name from student_classes where id=5");

							displayData(generalStudentsPanel.tableStudents,
									"select class_number,student_name,CASE WHEN sponsor='Choose Sponsor' THEN 'Not Sponsored' WHEN sponsor='' THEN 'Unknown' ELSE 'Yes' END AS Sponsored,payment_code,"
											+ "TIMESTAMPDIFF(YEAR,`" + generalStudentsPanel.labelClass.getText()
											+ "`.date_of_birth,Now()),dormitory from `"
											+ generalStudentsPanel.labelClass.getText() + "` where year='" + thisyear
											+ "' and (discipline_status='suspended' or discipline_status is null) group by payment_code");

							generalStudentsPanel.setVisible(true);
							panelforclasses.setVisible(false);
							studentsTreePanel.setVisible(true);
							allstudentsinAyear.setVisible(false);
							home.add(generalStudentsPanel);

							generalStudentsPanel.labelUser.setText(current_user.getText());

						} else if (node1.equals("Senior Six")) {

							studentLeadersList.setVisible(false);

							generalStudentsPanel.labelStudentImage.setIcon(null);
							generalStudentsPanel.displayStudentsofAClass(generalStudentsPanel.labelClass,
									"select class_name from student_classes where id=6");

							displayData(generalStudentsPanel.tableStudents,
									"select class_number,student_name,CASE WHEN sponsor='Choose Sponsor' THEN 'Not Sponsored' WHEN sponsor='' THEN 'Unknown' ELSE 'Yes' END AS Sponsored,payment_code,"
											+ "TIMESTAMPDIFF(YEAR,`" + generalStudentsPanel.labelClass.getText()
											+ "`.date_of_birth,Now()),dormitory from `"
											+ generalStudentsPanel.labelClass.getText() + "` where year='" + thisyear
											+ "' and (discipline_status='suspended' or discipline_status is null) group by payment_code");
							generalStudentsPanel.setVisible(true);
							panelforclasses.setVisible(false);
							studentsTreePanel.setVisible(true);
							allstudentsinAyear.setVisible(false);
							home.add(generalStudentsPanel);

							generalStudentsPanel.labelUser.setText(current_user.getText());

						} else if (node1.equals("Student Leaders")) {

							generalStudentsPanel.setVisible(false);

							panelPrefects.setVisible(false);
							panelUNSA.setVisible(false);

							outerpanel.setVisible(false);
							panelforclasses.setVisible(false);
							labelforpic.setVisible(false);

							studentLeadersList.setVisible(true);

						}

					}
				});

		// layout for all the class overview panels

		senior1.setLayout(new GridLayout(0, 2));
		senior2.setLayout(new GridLayout(0, 2));
		senior3.setLayout(new GridLayout(0, 2));
		senior4.setLayout(new GridLayout(0, 2));
		senior5.setLayout(new GridLayout(0, 2));
		senior6.setLayout(new GridLayout(0, 2));
		senior7.setLayout(new GridLayout(0, 2));
		senior8.setLayout(new GridLayout(0, 2));

		s1girls = new JLabel("Girls:");
		senior1.add(s1girls);

		s1girlsNo = new JLabel("girlNo:");
		s1girlsNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s1girlsNo.setForeground(new Color(0, 204, 204));
		senior1.add(s1girlsNo);

		s1boys = new JLabel("Boys:");
		senior1.add(s1boys);

		s1boysNo = new JLabel("BoysNo:");
		s1boysNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s1boysNo.setForeground(new Color(0, 204, 204));
		senior1.add(s1boysNo);

		s1total = new JLabel("Total:");
		senior1.add(s1total);

		s1totalNo = new JLabel("TotalNo:");
		s1totalNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s1totalNo.setForeground(new Color(0, 204, 204));
		senior1.add(s1totalNo);

		s1classcapt = new JLabel("Class Captain:");
		senior1.add(s1classcapt);

		s1classcaptName = new JLabel("Class CaptainName:");
		s1classcaptName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s1classcaptName.setForeground(new Color(0, 204, 204));
		senior1.add(s1classcaptName);

		s1classtr = new JLabel("Class Teacher:");
		senior1.add(s1classtr);

		s1classtrName = new JLabel("Class TeacherName:");
		s1classtrName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s1classtrName.setForeground(new Color(0, 204, 204));
		senior1.add(s1classtrName);

		s2girls = new JLabel("Girls:");
		senior2.add(s2girls);

		s2girlsNo = new JLabel("GirlsNo:");
		s2girlsNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s2girlsNo.setForeground(new Color(0, 204, 204));
		senior2.add(s2girlsNo);

		s2boys = new JLabel("Boys:");
		senior2.add(s2boys);

		s2boysNo = new JLabel("BoysNo:");
		s2boysNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s2boysNo.setForeground(new Color(0, 204, 204));
		senior2.add(s2boysNo);

		s2total = new JLabel("Total:");
		senior2.add(s2total);

		s2totalNo = new JLabel("TotalNo:");
		s2totalNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s2totalNo.setForeground(new Color(0, 204, 204));
		senior2.add(s2totalNo);

		s2classcapt = new JLabel("Class Captain:");
		senior2.add(s2classcapt);

		s2classcaptName = new JLabel("Class Captainname:");
		s2classcaptName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s2classcaptName.setForeground(new Color(0, 204, 204));
		senior2.add(s2classcaptName);

		s2classtr = new JLabel("Class Teacher:");
		senior2.add(s2classtr);

		s2classtrName = new JLabel("Class Teacher Name:");
		s2classtrName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s2classtrName.setForeground(new Color(0, 204, 204));
		senior2.add(s2classtrName);

		s3girls = new JLabel("Girls:");
		senior3.add(s3girls);

		s3girlsNo = new JLabel("Girls No:");
		s3girlsNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s3girlsNo.setForeground(new Color(0, 204, 204));
		senior3.add(s3girlsNo);

		s3boys = new JLabel("Boys:");
		senior3.add(s3boys);

		s3boysNo = new JLabel("Boys No:");
		s3boysNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s3boysNo.setForeground(new Color(0, 204, 204));
		senior3.add(s3boysNo);

		s3total = new JLabel("Total:");
		senior3.add(s3total);

		s3totalNo = new JLabel("TotalNo:");
		s3totalNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s3totalNo.setForeground(new Color(0, 204, 204));
		senior3.add(s3totalNo);

		s3classcapt = new JLabel("Class Captain:");
		senior3.add(s3classcapt);

		s3classcaptName = new JLabel("Class Captain Name:");
		s3classcaptName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s3classcaptName.setForeground(new Color(0, 204, 204));
		senior3.add(s3classcaptName);

		s3classtr = new JLabel("Class Teacher:");
		senior3.add(s3classtr);

		s3classtrName = new JLabel("Class Teacher Name:");
		s3classtrName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s3classtrName.setForeground(new Color(0, 204, 204));
		senior3.add(s3classtrName);

		s4girls = new JLabel("Girls:");
		senior4.add(s4girls);

		s4girlsNo = new JLabel("GirlsNo:");
		s4girlsNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s4girlsNo.setForeground(new Color(0, 204, 204));
		senior4.add(s4girlsNo);

		s4boys = new JLabel("Boys:");
		senior4.add(s4boys);

		s4boysNo = new JLabel("Boys No:");
		s4boysNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s4boysNo.setForeground(new Color(0, 204, 204));
		senior4.add(s4boysNo);

		s4total = new JLabel("Total:");
		senior4.add(s4total);

		s4totalNo = new JLabel("Total No:");
		s4totalNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s4totalNo.setForeground(new Color(0, 204, 204));
		senior4.add(s4totalNo);

		s4classcapt = new JLabel("Class Captain:");
		senior4.add(s4classcapt);

		s4classcaptName = new JLabel("Class Captain Name:");
		s4classcaptName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s4classcaptName.setForeground(new Color(0, 204, 204));
		senior4.add(s4classcaptName);

		s4classtr = new JLabel("Class Teacher:");
		senior4.add(s4classtr);

		s4classtrName = new JLabel("Class Teacher Name:");
		s4classtrName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s4classtrName.setForeground(new Color(0, 204, 204));
		senior4.add(s4classtrName);

		s5Artsgirls = new JLabel("Girls:");
		senior5.add(s5Artsgirls);

		s5ArtsgirlsNo = new JLabel("Girls No:");
		s5ArtsgirlsNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s5ArtsgirlsNo.setForeground(new Color(0, 204, 204));
		senior5.add(s5ArtsgirlsNo);

		s5Artsboys = new JLabel("Boys:");
		senior5.add(s5Artsboys);

		s5ArtsboysNo = new JLabel("Boys No:");
		s5ArtsboysNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s5ArtsboysNo.setForeground(new Color(0, 204, 204));
		senior5.add(s5ArtsboysNo);

		s5Artstotal = new JLabel("Total:");
		senior5.add(s5Artstotal);

		s5ArtstotalNo = new JLabel("Total No:");
		s5ArtstotalNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s5ArtstotalNo.setForeground(new Color(0, 204, 204));
		senior5.add(s5ArtstotalNo);

		s5Artsclasscapt = new JLabel("Class Captain:");
		senior5.add(s5Artsclasscapt);

		s5ArtsclasscaptName = new JLabel("Class Captain Name:");
		s5ArtsclasscaptName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s5ArtsclasscaptName.setForeground(new Color(0, 204, 204));
		senior5.add(s5ArtsclasscaptName);

		s5Artsclasstr = new JLabel("Class Teacher:");
		senior5.add(s5Artsclasstr);

		s5ArtsclasstrName = new JLabel("Class Teacher Name:");
		s5ArtsclasstrName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s5ArtsclasstrName.setForeground(new Color(0, 204, 204));
		senior5.add(s5ArtsclasstrName);

		s5Scigirls = new JLabel("Girls:");
		senior6.add(s5Scigirls);

		s5ScigirlsNo = new JLabel("GirlsNo:");
		s5ScigirlsNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s5ScigirlsNo.setForeground(new Color(0, 204, 204));
		senior6.add(s5ScigirlsNo);

		s5Sciboys = new JLabel("Boys:");
		senior6.add(s5Sciboys);

		s5SciboysNo = new JLabel("Boys No:");
		s5SciboysNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s5SciboysNo.setForeground(new Color(0, 204, 204));
		senior6.add(s5SciboysNo);

		s5Scitotal = new JLabel("Total:");
		senior6.add(s5Scitotal);

		s5ScitotalNo = new JLabel("Total No:");
		s5ScitotalNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s5ScitotalNo.setForeground(new Color(0, 204, 204));
		senior6.add(s5ScitotalNo);

		s5Sciclasscapt = new JLabel("Class Captain:");
		senior6.add(s5Sciclasscapt);

		s5SciclasscaptName = new JLabel("Class Captain Name:");
		s5SciclasscaptName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s5SciclasscaptName.setForeground(new Color(0, 204, 204));
		senior6.add(s5SciclasscaptName);

		s5Sciclasstr = new JLabel("Class Teacher:");
		senior6.add(s5Sciclasstr);

		s5SciclasstrName = new JLabel("Class Teacher Name:");
		s5SciclasstrName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s5SciclasstrName.setForeground(new Color(0, 204, 204));
		senior6.add(s5SciclasstrName);

		s6Artsgirls = new JLabel("Girls:");
		senior7.add(s6Artsgirls);

		s6ArtsgirlsNo = new JLabel("Girls No:");
		s6ArtsgirlsNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s6ArtsgirlsNo.setForeground(new Color(0, 204, 204));
		senior7.add(s6ArtsgirlsNo);

		s6Artsboys = new JLabel("Boys:");
		senior7.add(s6Artsboys);

		s6ArtsboysNo = new JLabel("Boys No:");
		s6ArtsboysNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s6ArtsboysNo.setForeground(new Color(0, 204, 204));
		senior7.add(s6ArtsboysNo);

		s6Artstotal = new JLabel("Total:");
		senior7.add(s6Artstotal);

		s6ArtstotalNo = new JLabel("Total No:");
		s6ArtstotalNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s6ArtstotalNo.setForeground(new Color(0, 204, 204));
		senior7.add(s6ArtstotalNo);

		s6Artsclasscapt = new JLabel("Class Captain:");
		senior7.add(s6Artsclasscapt);

		s6ArtsclasscaptName = new JLabel("Class Captain Name:");
		s6ArtsclasscaptName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s6ArtsclasscaptName.setForeground(new Color(0, 204, 204));
		senior7.add(s6ArtsclasscaptName);

		s6Artsclasstr = new JLabel("Class Teacher:");
		senior7.add(s6Artsclasstr);

		s6ArtsclasstrName = new JLabel("Class TeacherName:");
		s6ArtsclasstrName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s6ArtsclasstrName.setForeground(new Color(0, 204, 204));
		senior7.add(s6ArtsclasstrName);

		s6Scigirls = new JLabel("Girls:");
		senior8.add(s6Scigirls);

		s6ScigirlsNo = new JLabel("Girls No:");
		s6ScigirlsNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s6ScigirlsNo.setForeground(new Color(0, 204, 204));
		senior8.add(s6ScigirlsNo);

		s6Sciboys = new JLabel("Boys:");
		senior8.add(s6Sciboys);

		s6SciboysNo = new JLabel("Boys No:");
		s6SciboysNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s6SciboysNo.setForeground(new Color(0, 204, 204));
		senior8.add(s6SciboysNo);

		s6Scitotal = new JLabel("Total:");
		senior8.add(s6Scitotal);

		s6ScitotalNo = new JLabel("TotalNo:");
		s6ScitotalNo.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s6ScitotalNo.setForeground(new Color(0, 204, 204));
		senior8.add(s6ScitotalNo);

		s6Sciclasscapt = new JLabel("Class Captain:");
		senior8.add(s6Sciclasscapt);

		s6SciclasscaptName = new JLabel("Class Captain Name:");
		s6SciclasscaptName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s6SciclasscaptName.setForeground(new Color(0, 204, 204));
		senior8.add(s6SciclasscaptName);

		s6Sciclasstr = new JLabel("Class Teacher:");
		senior8.add(s6Sciclasstr);

		s6SciclasstrName = new JLabel("Class Teacher Name:");
		s6SciclasstrName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		s6SciclasstrName.setForeground(new Color(0, 204, 204));
		senior8.add(s6SciclasstrName);

		btnBackFromOverview = new JButton("Back");
		btnBackFromOverview.setPreferredSize(new Dimension(200, 25));
		// panelforclasses.add(btnBackFromOverview);
		btnBackFromOverview.setVisible(false);
		btnBackFromOverview.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				panelforclasses.setVisible(true);
				allstudentsinAyear.setVisible(true);
				btnBackfromStudents.setVisible(true);
				btnPrintAllStudents.setVisible(true);
				outerpanel.setVisible(false);
				scrollerforallstudents.setVisible(true);
				fixedScroll.setVisible(true);
				btnBackFromOverview.setVisible(false);
				btnPrintOverview.setVisible(false);
				panelforclasses.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
				admissionListViewPanel.setVisible(false);
				studentsTreePanel.setVisible(true);

			}
		});

		btnPrintOverview = new JButton("Print");
		btnPrintOverview.setPreferredSize(new Dimension(200, 25));
		// panelforclasses.add(btnPrintOverview);
		btnPrintOverview.setVisible(false);

		// prefects

		JPopupMenu popPrefect = new JPopupMenu();
		JMenuItem editPrefectDetail = new JMenuItem("Edit Prefect's Details");
		editPrefectDetail.setFont(new Font("Calibri", Font.BOLD, 15));
		popPrefect.add(editPrefectDetail);
		JMenuItem deleteSelectedPrefect = new JMenuItem("Delete Selected Prefect");
		deleteSelectedPrefect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String[] options = { "Delete Student", "Cancel" };
				int row = tablePrefects.getSelectedRow();
				String id = tablePrefects.getValueAt(row, 0).toString();
				int ans = JOptionPane.showOptionDialog(null, "Are You Sure You Want to Delete the selected Student???",
						"Confirmation of Delete Request", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
				if (ans == 0) {

					AddUpdateDelete("delete from prefects where id_number='" + id + "'");

				} else {

				}

			}
		});
		popPrefect.add(deleteSelectedPrefect);
		JMenuItem addNewPrefect = new JMenuItem("Add New Prefect");
		popPrefect.add(addNewPrefect);
		JMenuItem addNewPost = new JMenuItem("Add New Post");
		popPrefect.add(addNewPost);

		popPrefect.setPreferredSize(new Dimension(180, 200));

		editPrefectDetail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				EditPrefects object = new EditPrefects();

				int row = tablePrefects.getSelectedRow();
				String id = tablePrefects.getValueAt(row, 0).toString();
				String sql = "select prefects.first_name,prefects.middle_name,prefects.last_name, "
						+ "prefects.student_class,prefects.gender,prefects.post,prefects.year_of_regime from prefects where prefects.id_number='"
						+ id + "' ";
				try {
					Connection conn = CashBookController.getConnection();

					// prepare statement
					PreparedStatement pst = conn.prepareStatement(sql);

					ResultSet rs = pst.executeQuery();

					while (rs.next()) {
						object.fieldID.setText(id);
						object.fieldName.setText(rs.getString(1));
						object.fieldMiddleName.setText(rs.getString(2));
						object.fieldLastName.setText(rs.getString(3));
						object.comboBoxClasses.setSelectedItem(rs.getString(4));
						object.comboBoxGender.setSelectedItem(rs.getString(5));
						object.fieldLastPost.setText(rs.getString(6));
						object.fieldYearOfRegime.setText(rs.getString(7));
					}
				} catch (Exception e) {

				} finally {
					if (conn != null) {
						try {
							conn.close();
						} catch (Exception e3) {

						}
					}
				}

			}
		});

		JPopupMenu popUNSA = new JPopupMenu();
		JMenuItem editUNSADetail = new JMenuItem("Edit UNSA's Details");
		popUNSA.add(editUNSADetail);
		JMenuItem deleteSelectedUNSA = new JMenuItem("Delete Selected UNSA");
		deleteSelectedUNSA.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String[] options = { "Delete Student", "Cancel" };
				int row = tableUNSA.getSelectedRow();
				String id = tableUNSA.getValueAt(row, 0).toString();
				int ans = JOptionPane.showOptionDialog(null, "Are You Sure You Want to Delete the selected Student???",
						"Confirmation of Delete Request", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
				if (ans == 0) {

					AddUpdateDelete("delete from unsa where id_number='" + id + "'");

				} else {

				}

			}
		});
		popUNSA.add(deleteSelectedUNSA);
		JMenuItem addNewUNSA = new JMenuItem("Add New UNSA");
		popUNSA.add(addNewUNSA);
		JMenuItem addNewPostUNSA = new JMenuItem("Add New Post");
		popUNSA.add(addNewPostUNSA);

		popUNSA.setPreferredSize(new Dimension(180, 200));

		editUNSADetail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				EditUNSA object = new EditUNSA();

				int row = tableUNSA.getSelectedRow();
				String id = tableUNSA.getValueAt(row, 0).toString();
				String sql = "select unsa.first_name,unsa.middle_name,unsa.last_name, "
						+ "unsa.student_class,unsa.gender,unsa.post,unsa.year_of_regime from unsa where unsa.id_number='"
						+ id + "' ";
				try {
					Connection conn = CashBookController.getConnection();

					// prepare statement
					PreparedStatement pst = conn.prepareStatement(sql);

					ResultSet rs = pst.executeQuery();

					while (rs.next()) {
						object.fieldID.setText(id);
						object.fieldName.setText(rs.getString(1));
						object.fieldMiddleName.setText(rs.getString(2));
						object.fieldLastName.setText(rs.getString(3));
						object.comboBoxClasses.setSelectedItem(rs.getString(4));
						object.comboBoxGender.setSelectedItem(rs.getString(5));
						object.fieldLastPost.setText(rs.getString(6));
						object.fieldYearOfRegime.setText(rs.getString(7));
					}
				} catch (Exception e) {

				}

			}
		});

		addNewUNSA.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AddNewUNSA();

			}
		});
		addNewPrefect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AddNewPrefect();
			}
		});

		// adding a new prefect post
		addNewPost.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				AddNewPost post = new AddNewPost();
				if (post.isShowing())
					post.setTitle("Add New Prefect Post");
			}
		});

		// add new UNSA Post
		addNewPostUNSA.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				AddNewPost postUNSA = new AddNewPost();
				if (postUNSA.isShowing())
					postUNSA.setTitle("Add New UNSA Post");
			}
		});

		btnbackPrefects = new JButton("Back");
		panelPrefects.add(btnbackPrefects);

		btnprintPrefects = new JButton("Print");
		panelPrefects.add(btnprintPrefects);

		JLabel fakelabel41 = new JLabel("");
		panelPrefects.add(fakelabel41);
		fakelabel41.setPreferredSize(new Dimension(920, 25));

		labelpicturePrefects = new JLabel("Picture Prefects");
		labelpicturePrefects.setPreferredSize(new Dimension(110, 120));
		labelpicturePrefects.setBorder(new LineBorder(Color.RED, 2));
		panelPrefects.add(labelpicturePrefects);

		String[][] studentsdata1 = new String[][] {

				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }

		};

		String[] Studentsheading1 = new String[] { "ID Number", "Student Name", "Class", "Sex", "Post", "Dormitory",
				"Year of Regime" };

		tablePrefects = new JTable(studentsdata1, Studentsheading1);
		JTableHeader headerPrefects = tablePrefects.getTableHeader();
		headerPrefects.setBackground(Color.BLACK);
		headerPrefects.setForeground(Color.white);
		tablePrefects.setShowGrid(false);
		headerPrefects.setPreferredSize(new Dimension(1170, 30));
		JScrollPane scrollerforprefects = new JScrollPane(tablePrefects);
		tablePrefects.setRowHeight(25);
		scrollerforprefects.setPreferredSize(new Dimension(1145, 345));
		Border bodaforprefects = BorderFactory.createRaisedBevelBorder();
		scrollerforprefects.setBorder(bodaforprefects);
		panelPrefects.add(scrollerforprefects);
		tablePrefects.setShowGrid(false);
		tablePrefects.getColumnModel().getColumn(0).setPreferredWidth(85);
		tablePrefects.getColumnModel().getColumn(2).setPreferredWidth(85);
		tablePrefects.getColumnModel().getColumn(3).setPreferredWidth(85);
		tablePrefects.getColumnModel().getColumn(4).setPreferredWidth(200);
		tablePrefects.getColumnModel().getColumn(5).setPreferredWidth(200);
		tablePrefects.getColumnModel().getColumn(6).setPreferredWidth(85);
		tablePrefects.getColumnModel().getColumn(1).setPreferredWidth(410);

		tablePrefects.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (SwingUtilities.isRightMouseButton(arg0)) {
					popPrefect.show(tablePrefects, arg0.getX(), arg0.getY());
				}

				int selectedRow = tablePrefects.getSelectedRow();

				String studentID = (String) tablePrefects.getValueAt(selectedRow, 0);
				String studentName = (String) tablePrefects.getValueAt(selectedRow, 1);
				try {

					java.sql.Connection conn = CashBookController.getConnection();

					PreparedStatement pst = conn.prepareStatement(
							"select photo from tableadmissionstudentdetails where id_number='" + studentID + "'");
					ResultSet rs = pst.executeQuery();

					if (rs.next()) {

						byte[] img = rs.getBytes("photo");
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image im2 = im.getScaledInstance(labelpicturePrefects.getWidth(),
								labelpicturePrefects.getHeight(), Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(im2);
						labelpicturePrefects.setIcon(newImage);

					} else {
						JOptionPane.showMessageDialog(null, "No Image Found For " + studentName + " In The Database",
								"Lacking " + studentName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});

		btnbackUNSA = new JButton("Back");
		panelUNSA.add(btnbackUNSA);

		btnprintUNSA = new JButton("Print");
		panelUNSA.add(btnprintUNSA);

		JLabel fakelabel411 = new JLabel("");
		panelUNSA.add(fakelabel411);
		fakelabel411.setPreferredSize(new Dimension(920, 25));

		labelpictureUNSA = new JLabel("Picture UNSA");
		labelpictureUNSA.setPreferredSize(new Dimension(110, 120));
		labelpictureUNSA.setBorder(new LineBorder(Color.RED, 2));
		panelUNSA.add(labelpictureUNSA);

		String[][] studentsdata11 = new String[][] {

				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }

		};

		String[] Studentsheading11 = new String[] { "ID Number", "Student Name", "Class", "Sex", "Post", "Dormitory",
				"Year of Regime" };

		tableUNSA = new JTable(studentsdata11, Studentsheading11);
		tableUNSA.setShowGrid(false);
		JTableHeader headerUNSA = tableUNSA.getTableHeader();
		headerUNSA.setBackground(Color.BLACK);
		headerUNSA.setForeground(Color.white);
		headerUNSA.setPreferredSize(new Dimension(1170, 30));
		JScrollPane scrollerforunsa = new JScrollPane(tableUNSA);
		tableUNSA.setRowHeight(25);
		scrollerforunsa.setPreferredSize(new Dimension(1145, 345));
		Border bodaforunsa = BorderFactory.createRaisedBevelBorder();
		scrollerforunsa.setBorder(bodaforunsa);
		panelUNSA.add(scrollerforunsa);

		tableUNSA.setShowGrid(false);
		tableUNSA.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableUNSA.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableUNSA.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableUNSA.getColumnModel().getColumn(4).setPreferredWidth(200);
		tableUNSA.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableUNSA.getColumnModel().getColumn(6).setPreferredWidth(85);
		tableUNSA.getColumnModel().getColumn(1).setPreferredWidth(410);

		tableUNSA.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (SwingUtilities.isRightMouseButton(arg0)) {
					popUNSA.show(tableUNSA, arg0.getX(), arg0.getY());
				}

				int selectedRow = tableUNSA.getSelectedRow();

				String studentID = (String) tableUNSA.getValueAt(selectedRow, 0);
				String studentName = (String) tableUNSA.getValueAt(selectedRow, 1);
				try {

					java.sql.Connection conn = CashBookController.getConnection();

					PreparedStatement pst = conn.prepareStatement(
							"select photo from tableadmissionstudentdetails where id_number='" + studentID + "'");
					ResultSet rs = pst.executeQuery();

					if (rs.next()) {

						byte[] img = rs.getBytes("photo");
						ImageIcon image = new ImageIcon(img);
						Image im = image.getImage();
						Image im2 = im.getScaledInstance(labelpictureUNSA.getWidth(), labelpictureUNSA.getHeight(),
								Image.SCALE_SMOOTH);
						ImageIcon newImage = new ImageIcon(im2);
						labelpictureUNSA.setIcon(newImage);

					} else {
						JOptionPane.showMessageDialog(null, "No Image Found For " + studentName + " In The Database",
								"Lacking " + studentName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});

		// Staffs Panel starts here
		staffsPanel = new StaffsPanel();
		staffsPanel.setVisible(false);
		staffsPanel.setPreferredSize(new Dimension(1170, 490));
		staffsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		home.add(staffsPanel);

		// staff tree panel starts here
		staffsTreePanel = new StaffsTreePanel();
		staffsTreePanel.setVisible(false);
		staffsTreePanel.getLvListDisplayedAdmission().setPrefSize(150, 540);
		leftpanel.add(staffsTreePanel);

		listenForMouseClickedInStaffs = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				btnBackfromStudents.setVisible(false);
				btnPrintAllStudents.setVisible(false);
				panelforclasses.setVisible(false);
				btnBackFromOverview.setVisible(false);
				btnPrintOverview.setVisible(false);
				la1.setVisible(false);
				la5.setVisible(false);
				la2.setVisible(false);
				la6.setVisible(false);
				la3.setVisible(false);
				la7.setVisible(false);
				la4.setVisible(false);
				la8.setVisible(false);
				admission.setVisible(false);
				home.setLayout(new FlowLayout(FlowLayout.LEFT));
				admissionListViewPanel.setVisible(false);
				studentsTreePanel.setVisible(false);
				academicPanel.setVisible(false);

				panelforclasses.setVisible(false);
				allstudentsinAyear.setVisible(false);

				leftpanel.setVisible(true);

				staffsTreePanel.setVisible(true);
				staffsPanel.setVisible(true);

			}

		};
		la2.addMouseListener(listenForMouseClickedInStaffs);
		la2.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

				la2.setIcon(new ImageIcon(img2));
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

				la2.setIcon(new ImageIcon(img22));
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				displayData(staffsPanel.getTableStaffs(),
						"select id_number,staff_name,staff_contact,staff_gender,staff_address,"
								+ "'Teaching Staff' from table_teaching_staffs UNION select id_number,staff_name,"
								+ "staff_contact,staff_gender,staff_address,'Non Teaching Staff' from table_non_teaching_staffs");

			}
		});

		teachingStaffsPanel = new TeachingStaffsPanel();
		teachingStaffsPanel.setVisible(false);
		teachingStaffsPanel.setPreferredSize(new Dimension(1170, 490));
		teachingStaffsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		home.add(teachingStaffsPanel);

		nonTeachingStaffs = new NonTeachingStaffs();
		nonTeachingStaffs.setVisible(false);
		nonTeachingStaffs.setPreferredSize(new Dimension(1170, 490));
		nonTeachingStaffs.setLayout(new FlowLayout(FlowLayout.LEFT));
		home.add(nonTeachingStaffs);

		staffsTreePanel.getLvListDisplayedAdmission().getSelectionModel().selectedItemProperty()
				.addListener(new InvalidationListener() {

					@Override
					public void invalidated(Observable arg0) {

						String node = staffsTreePanel.getLvListDisplayedAdmission().getSelectionModel()
								.getSelectedItem().toString();
						if (node.equalsIgnoreCase("Teaching Staffs")) {
							staffsPanel.setVisible(false);
							teachingStaffsPanel.setVisible(true);
							teachingStaffsPanel.labelTeachingStaffImage.setIcon(null);
							teachingStaffsPanel.labelUser.setText(current_user.getText());
							nonTeachingStaffs.setVisible(false);

							tableModelTeachers = new JavaDatabaseSelectStatements().DisplayTeachingStaffs();
							teachingStaffsPanel.tableTeachingStaffs.setModel(tableModelTeachers);

						} else if (node.equalsIgnoreCase("Non Teaching Staffs")) {
							nonTeachingStaffs.setVisible(true);
							nonTeachingStaffs.labelTeachingStaffImage.setIcon(null);
							nonTeachingStaffs.labelUser.setText(current_user.getText());
							teachingStaffsPanel.setVisible(false);
							staffsPanel.setVisible(false);

							tableModelNonTeachers = new JavaDatabaseSelectStatements().DisplayNonTeachingStaffs();
							nonTeachingStaffs.tableTeachingStaffs.setModel(tableModelNonTeachers);

						} else if (node.equalsIgnoreCase("All Staffs")) {
							btnBackfromStudents.setVisible(false);
							btnPrintAllStudents.setVisible(false);
							panelforclasses.setVisible(false);
							btnBackFromOverview.setVisible(false);
							btnPrintOverview.setVisible(false);
							la1.setVisible(false);
							la5.setVisible(false);
							la2.setVisible(false);
							la6.setVisible(false);
							la3.setVisible(false);
							la7.setVisible(false);
							la4.setVisible(false);
							la8.setVisible(false);
							admission.setVisible(false);
							home.setLayout(new FlowLayout(FlowLayout.LEFT));
							admissionListViewPanel.setVisible(false);
							studentsTreePanel.setVisible(false);

							panelforclasses.setVisible(false);
							allstudentsinAyear.setVisible(false);

							leftpanel.setVisible(true);

							staffsTreePanel.setVisible(true);
							staffsPanel.setVisible(true);

							nonTeachingStaffs.setVisible(false);
							teachingStaffsPanel.setVisible(false);

							displayData(staffsPanel.getTableStaffs(),
									"select id_number,staff_name,staff_contact,staff_gender,staff_address,"
											+ "'Teaching Staff' from table_teaching_staffs UNION select id_number,staff_name,"
											+ "staff_contact,staff_gender,staff_address,'Non Teaching Staff' from table_non_teaching_staffs");

						}

					}
				});

		academicPanel = new AcademicPanel();

		leftpanel.add(academicPanel);
		academicPanel.setVisible(false);

		academicPanel.setPreferredSize(new Dimension(152, 540));

		// academicPanel.getScrollerAcademics().setPreferredSize(new Dimension(150,
		// 535));

		AcademicsMarksEntries academicsMarksEntries = new AcademicsMarksEntries();

		home.add(academicsMarksEntries);
		academicsMarksEntries.setVisible(false);
		
		
		AutoGeneratingAndPrintingReportCards autoGeneratingAndPrintingReportCards = new AutoGeneratingAndPrintingReportCards();

		home.add(autoGeneratingAndPrintingReportCards);
		autoGeneratingAndPrintingReportCards.setVisible(false);

		GraphicalStudentsAnalysis graphicalStudentsAnalysis = new GraphicalStudentsAnalysis();

		home.add(graphicalStudentsAnalysis);
		graphicalStudentsAnalysis.setVisible(false);

		GraphicalStaffsAnalysis graphicalStaffsAnalysis = new GraphicalStaffsAnalysis();

		home.add(graphicalStaffsAnalysis);
		graphicalStaffsAnalysis.setVisible(false);

		GraphicalSubjectsProgress graphicalSubjectsAnalysis = new GraphicalSubjectsProgress();

		home.add(graphicalSubjectsAnalysis);
		graphicalSubjectsAnalysis.setVisible(false);

		StaffsProgressAnalysisData staffsProgressAnalysisData = new StaffsProgressAnalysisData();

		home.add(staffsProgressAnalysisData);
		staffsProgressAnalysisData.setVisible(false);

		StudentsProgressAnalysisData studentsProgressAnalysisData = new StudentsProgressAnalysisData();

		home.add(studentsProgressAnalysisData);
		studentsProgressAnalysisData.setVisible(false);

		SubjectsProgressAnalysisData subjectsProgressAnalysisData = new SubjectsProgressAnalysisData();

		home.add(subjectsProgressAnalysisData);
		subjectsProgressAnalysisData.setVisible(false);

		IndividualTestsAnalysis individualTestsAnalysis = new IndividualTestsAnalysis();

		home.add(individualTestsAnalysis);
		individualTestsAnalysis.setVisible(false);

		GiftedAndAtRiskStudents giftedAndAtRiskStudents = new GiftedAndAtRiskStudents();

		home.add(giftedAndAtRiskStudents);
		giftedAndAtRiskStudents.setVisible(false);

		// academicsMarksEntries = new YearChooserPanel();
		// academicsMarksEntries.setPreferredSize(new Dimension(1170, 490));
		// home.add(academicsMarksEntries);
		// academicsMarksEntries.setVisible(false);

		listenForMouseClickedInAcademics = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				admissionListViewPanel.setVisible(false);
				scrollerforallstudents.setVisible(false);
				fixedScroll.setVisible(false);
				staffsTreePanel.setVisible(false);
				academicPanel.setVisible(true);
				academicsMarksEntries.setVisible(true);
				la1.setVisible(false);
				la2.setVisible(false);
				la3.setVisible(false);
				la4.setVisible(false);
				la5.setVisible(false);
				la6.setVisible(false);
				la7.setVisible(false);
				la8.setVisible(false);
				home.setLayout(new FlowLayout(FlowLayout.LEFT));
				academicsMarksEntries.setVisible(true);

			}

		};

		la3.addMouseListener(listenForMouseClickedInAcademics);
		la3.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

				la3.setIcon(new ImageIcon(img3));
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

				la3.setIcon(new ImageIcon(img33));

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});

		
		academicPanel.getLvListDisplayedStudents().getSelectionModel().selectedItemProperty()
				.addListener(new InvalidationListener() {

					@Override
					public void invalidated(Observable arg0) {
						// TODO Auto-generated method stub

						String selectednode = academicPanel.getLvListDisplayedStudents().getSelectionModel()
								.getSelectedItem().toString();

						if (!selectednode.equalsIgnoreCase("Marks Entry Sheet")) {

							academicsMarksEntries.setVisible(false);
						} else {
							academicsMarksEntries.setVisible(true);
						}

						if (!selectednode.equalsIgnoreCase("Graphical Student's Progress")) {

							graphicalStudentsAnalysis.setVisible(false);

						} else {

							graphicalStudentsAnalysis.setVisible(true);

						}

						if (!selectednode.equalsIgnoreCase("Graphical Teacher's Progress")) {

							graphicalStaffsAnalysis.setVisible(false);

						} else {

							graphicalStaffsAnalysis.setVisible(true);
						}

						if (!selectednode.equalsIgnoreCase("Grapical Subject's Progress")) {
							graphicalSubjectsAnalysis.setVisible(false);
						} else {
							graphicalSubjectsAnalysis.setVisible(true);
						}

						if (!selectednode.equalsIgnoreCase("Teacher's Progress Analysis")) {

							staffsProgressAnalysisData.setVisible(false);
						} else {
							staffsProgressAnalysisData.setVisible(true);
						}

						if (!selectednode.equalsIgnoreCase("Subject's Progress Analysis")) {
							subjectsProgressAnalysisData.setVisible(false);
						} else {
							subjectsProgressAnalysisData.setVisible(true);
						}

						if (!selectednode.equalsIgnoreCase("Student's Progress Analysis")) {

							studentsProgressAnalysisData.setVisible(false);

						} else {
							studentsProgressAnalysisData.setVisible(true);
						}

						if (!selectednode.equalsIgnoreCase("Individual Tests Analysis")) {

							individualTestsAnalysis.setVisible(false);

						} else {

							individualTestsAnalysis.setVisible(true);
						}

						if (!selectednode.equalsIgnoreCase("Gifted / At Risk Students")) {

							giftedAndAtRiskStudents.setVisible(false);
							
						} else {
							
							giftedAndAtRiskStudents.setVisible(true);
							
						}
						
						if (!selectednode.equalsIgnoreCase("Auto Generate Report Cards")) {

							autoGeneratingAndPrintingReportCards.setVisible(false);
							
						} else {
							
							autoGeneratingAndPrintingReportCards.setVisible(true);
							
						}

					}
				});

		listenForMouseClickedInAdmin = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				home.setLayout(new FlowLayout(FlowLayout.LEFT));
				la1.setVisible(false);
				la2.setVisible(false);
				la3.setVisible(false);
				la4.setVisible(false);
				la5.setVisible(false);
				la6.setVisible(false);
				la7.setVisible(false);
				la8.setVisible(false);
				adminReportPanel.setVisible(true);

			}

		};

		la5.addMouseListener(listenForMouseClickedInAdmin);

		la5.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

				la5.setIcon(new ImageIcon(img5));
			}

			@Override
			public void mouseEntered(MouseEvent e) {

				la5.setIcon(new ImageIcon(img55));
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		la6.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

				la6.setIcon(new ImageIcon(img6));
			}

			@Override
			public void mouseEntered(MouseEvent e) {

				la6.setIcon(new ImageIcon(img66));
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		admissionListViewPanel = new AdmissionListViewPanel();
		leftpanel.add(admissionListViewPanel);
		admissionListViewPanel.setVisible(false);
		admissionListViewPanel.setPreferredSize(new Dimension(152, 540));

		admissionListViewPanel.getLvListDisplayedAdmission().getSelectionModel().selectedItemProperty()
				.addListener(new InvalidationListener() {

					@Override
					public void invalidated(Observable arg0) {

						String node = admissionListViewPanel.getLvListDisplayedAdmission().getSelectionModel()
								.getSelectedItem().toString();
						if (node.equals("Admit Student")) {
							new AdmissionDetails();

						} else if (node.equals("Admitted Students")) {
							panelAdmittedStudents.setVisible(true);
							admit.setVisible(false);
							panelReportingStudents.setVisible(false);
							panelReportedStudents.setVisible(false);

							displayData(tableadmitted, "select students_info.class_number,students_info.student_name,"
									+ "TIMESTAMPDIFF(YEAR,students_info.date_of_birth,Now()),parents_info.parent_address,"
									+ "CASE WHEN parents_info.sponsors_name='Choose Sponsor' THEN 'No' WHEN parents_info.sponsors_name=''"
									+ " THEN 'Unknown' ELSE 'Yes' END AS Sponsored,students_info.dormitory,"
									+ "parents_info.fathers_name,LEFT(students_info.year,4) from students_info,parents_info where "
									+ "students_info.class_number=parents_info.class_number and students_info.student_name=parents_info.student_name");
							labelPictureRequirementDebtorsAdmittedStudents.setIcon(null);

						} else if (node.equals("Reporting Students")) {

							fieldBankName.removeAllItems();

							displayInComboBox(fieldBankName, "select bank_name from banks");

							panelReportingStudents.setVisible(true);

							panelAdmittedStudents.setVisible(false);
							admit.setVisible(false);
							panelReportedStudents.setVisible(false);
						} else if (node.equals("Reported Students")) {
							panelReportedStudents.setVisible(true);
							panelAdmittedStudents.setVisible(false);

							panelReportingStudents.setVisible(false);
							admit.setVisible(false);

							displayData(tablereportedStudents,
									"select all_students_and_parents.class_number, all_students_and_parents.student_name,"
											+ "TIMESTAMPDIFF(YEAR, all_students_and_parents.date_of_birth,Now()),all_students_and_parents.address,"
											+ "CASE WHEN all_students_and_parents.sponsor='Choose Sponsor' THEN 'Not Sponsored' WHEN all_students_and_parents.sponsor=''"
											+ " THEN 'Unknown' ELSE 'Yes' END AS Sponsored, all_students_and_parents.dormitory,all_students_and_parents.term,"
											+ "LEFT(all_students_and_parents.year,4) from all_students_and_parents");
						}

					}
				});

		libraryTree = new LibraryTree();
		leftpanel.add(libraryTree);
		libraryTree.setVisible(false);

		libraryManageBooks = new LibraryManageBooks();
		libraryManageBooks.setPreferredSize(new Dimension(1170, 490));
		home.add(libraryManageBooks);
		libraryManageBooks.setVisible(false);
		libraryManageBooks.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 8));

		libraryManageBooks.getScrollPaneAllBooks().setPreferredSize(new Dimension(1150, 180));
		libraryManageBooks.getScrollPaneIssuedBooks().setPreferredSize(new Dimension(1150, 180));

		issueBook = new IssueBook();
		issueBook.setPreferredSize(new Dimension(1170, 490));
		issueBook.getPanelHolder().setPreferredSize(new Dimension(1170, 200));
		issueBook.getPanelBookDetails().setPreferredSize(new Dimension(330, 190));
		issueBook.getPanelStudentDetails().setPreferredSize(new Dimension(330, 190));
		issueBook.getPanelTeacherDetails().setPreferredSize(new Dimension(330, 190));
		home.add(issueBook);
		issueBook.setVisible(false);

		returnBook = new ReturnBook();
		returnBook.setPreferredSize(new Dimension(1170, 490));
		returnBook.getPanelHolder().setPreferredSize(new Dimension(1170, 200));
		returnBook.getPanelBookDetails().setPreferredSize(new Dimension(330, 190));
		returnBook.getPanelStudentDetails().setPreferredSize(new Dimension(330, 190));
		returnBook.getPanelTeacherDetails().setPreferredSize(new Dimension(330, 190));
		home.add(returnBook);
		returnBook.setVisible(false);

		libraryTree.getLvListDisplayedLib().getSelectionModel().selectedItemProperty()
				.addListener(new javafx.beans.value.ChangeListener() {

					@Override
					public void changed(ObservableValue arg0, Object oldValue, Object newValue) {

						String node = libraryTree.getLvListDisplayedLib().getSelectionModel().getSelectedItem()
								.toString();

						if (node.equalsIgnoreCase("Manage Books")) {
							libraryManageBooks.setVisible(true);
							issueBook.setVisible(false);
							returnBook.setVisible(false);
							libraryManageBooks.displayData(libraryManageBooks.tableIssuedBooks,
									"select generaloverviewofbooks.bookTitle,"
											+ "generaloverviewofbooks.subject,generaloverviewofbooks.author,"
											+ "generaloverviewofbooks.publisher,COUNT(issued_books.book_title),"
											+ "generaloverviewofbooks.quantity-COUNT(issued_books.book_title) "
											+ "from generaloverviewofbooks, issued_books "
											+ "where generaloverviewofbooks.bookTitle=issued_books.book_title "
											+ "and issued_books.returned=0 group by issued_books.book_title");
						} else if (node.equalsIgnoreCase("Issue Book")) {
							issueBook.setVisible(true);
							libraryManageBooks.setVisible(false);
							returnBook.setVisible(false);
							issueBook.showBookTitles(issueBook.fieldBookTitle);
							issueBook.displayData(issueBook.tableTeachers,
									"select payment_code,student_name,class_number,book_title,book_id,book_author,issue_date,"
											+ "return_date,CONCAT(TIMESTAMPDIFF(DAY,issue_date,Now())) from issued_books  where teacher_or_student='Teacher' and returned=0");
							issueBook.displayData(issueBook.tableStudents,
									"select payment_code,student_name,class_number,book_title,book_id,book_author,issue_date,"
											+ "return_date,CONCAT(TIMESTAMPDIFF(DAY,issue_date,Now())) from issued_books  where teacher_or_student='Student' and returned=0");

						} else if (node.equalsIgnoreCase("Return Book")) {
							issueBook.setVisible(false);
							libraryManageBooks.setVisible(false);
							returnBook.setVisible(true);
						}
					}
				});

		listenForMouseClickedInLibrary = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				la1.setVisible(false);
				la5.setVisible(false);
				la2.setVisible(false);
				la6.setVisible(false);
				la3.setVisible(false);
				la7.setVisible(false);
				la4.setVisible(false);
				la8.setVisible(false);
				admission.setVisible(false);
				home.setLayout(new FlowLayout(FlowLayout.LEFT));
				admissionListViewPanel.setVisible(false);
				studentsTreePanel.setVisible(false);
				academicPanel.setVisible(false);

				libraryTree.setVisible(true);

			}

		};

		la7.addMouseListener(listenForMouseClickedInLibrary);

		la7.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

				la7.setIcon(new ImageIcon(img7));
			}

			@Override
			public void mouseEntered(MouseEvent e) {

				la7.setIcon(new ImageIcon(img77));
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		la8.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

				la8.setIcon(new ImageIcon(img8));
			}

			@Override
			public void mouseEntered(MouseEvent e) {

				la8.setIcon(new ImageIcon(img88));
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		timeTableExamination = new TimeTableExamination();
		timeTableExamination.setPreferredSize(new Dimension(1170, 490));
		timeTableExamination.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
		home.add(timeTableExamination);
		timeTableExamination.setVisible(false);

		///////////////// Time Table Teachers Schedule /////////////////
		timeTableTeachersSchedule = new TimeTableTeachersSchedule();
		timeTableTeachersSchedule.setPreferredSize(new Dimension(1170, 490));
		timeTableTeachersSchedule.setLayout(new FlowLayout(FlowLayout.CENTER));
		home.add(timeTableTeachersSchedule);
		timeTableTeachersSchedule.setVisible(false);

		timeTableLesson = new TimeTableLesson();
		timeTableLesson.setPreferredSize(new Dimension(1170, 490));
		home.add(timeTableLesson);
		timeTableLesson.setVisible(false);

		timeTableTreePanel = new TimeTableTreePanel();
		leftpanel.add(timeTableTreePanel);
		timeTableTreePanel.setVisible(false);

		timeTableTreePanel.getLvListDisplayed().getSelectionModel().selectedItemProperty()
				.addListener(new javafx.beans.value.ChangeListener() {

					@Override
					public void changed(ObservableValue arg0, Object oldValue, Object newValue) {

						String node = timeTableTreePanel.lvListDisplayed.getSelectionModel().getSelectedItem()
								.toString();
						if (node.equalsIgnoreCase("Lessons")) {
							timeTableLesson.setVisible(true);
							home.setLayout(new FlowLayout(FlowLayout.LEFT));
							timeTableTeachersSchedule.setVisible(false);
							timeTableExamination.setVisible(false);
						} else if (node.equalsIgnoreCase("Teachers Duty Schedule")) {
							timeTableTeachersSchedule.setVisible(true);
							home.setLayout(new FlowLayout(FlowLayout.LEFT));
							timeTableLesson.setVisible(false);
							timeTableExamination.setVisible(false);
						} else if (node.equalsIgnoreCase("Examinations")) {
							timeTableExamination.setVisible(true);
							home.setLayout(new FlowLayout(FlowLayout.LEFT));
							timeTableLesson.setVisible(false);
							timeTableTeachersSchedule.setVisible(false);
						}
					}
				});

		listenForMouseClickedInTimeTable = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				timeTableTreePanel.setVisible(true);
				home.setLayout(new FlowLayout(FlowLayout.LEFT));
				la1.setVisible(false);
				la2.setVisible(false);
				la3.setVisible(false);
				la4.setVisible(false);
				la5.setVisible(false);
				la6.setVisible(false);
				la7.setVisible(false);
				la8.setVisible(false);

			}

		};

		la6.addMouseListener(listenForMouseClickedInTimeTable);
		la6.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});

		tabMyAccount = new JTabbedPane();
		home6.add(tabMyAccount);

		JPanel usersPanel = new JPanel();
		JScrollPane scrollerUsers = new JScrollPane(usersPanel);
		tabMyAccount.addTab("Users", scrollerUsers);
		usersPanel.setPreferredSize(new Dimension(1020, 470));

		systemUsersPanel = new SystemUsersPanel();
		usersPanel.add(systemUsersPanel);
		systemUsersPanel.setPreferredSize(new Dimension(1020, 470));

		JPanel changeLoginDetails = new JPanel();
		JScrollPane scrollerchangeLoginDetails = new JScrollPane(changeLoginDetails);
		tabMyAccount.addTab("Change Login Details", scrollerchangeLoginDetails);
		changeLoginDetails.setPreferredSize(new Dimension(1020, 470));

		JPanel myRights = new JPanel();
		myRightsPanel = new MyRightsPanel();
		myRightsPanel.setPreferredSize(new Dimension(1020, 470));
		myRightsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		myRights.add(myRightsPanel);
		JScrollPane scrollermyRights = new JScrollPane(myRights);
		tabMyAccount.addTab("My Rights", scrollermyRights);
		myRights.setPreferredSize(new Dimension(1020, 470));

		tabMyAccount.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub

				if (tabMyAccount.getSelectedIndex() == 2) {
					displayData(myRightsPanel.table,
							"select subject_name from student_subjects UNION select subject_name from student_subjectsa");
					displayData(myRightsPanel.tableSystemRoles, "select right_name from rights_activities_system");
					displayData(myRightsPanel.tableAdminRoles, "select right_name from rights_activities_admin");

					checkUserRights();
					myRightsPanel.displayInComboBox(myRightsPanel.combo, "select username from application_users");
				}
			}
		});

		JPanel systemSettings = new JPanel();
		SystemSettingsPanel systemSettingsPanel = new SystemSettingsPanel();
		systemSettingsPanel.setPreferredSize(new Dimension(1020, 470));
		systemSettingsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		systemSettings.add(systemSettingsPanel);

		JScrollPane scrollersystemSettings = new JScrollPane(systemSettings);
		tabMyAccount.addTab("System Settings", scrollersystemSettings);
		systemSettings.setPreferredSize(new Dimension(1020, 470));

		tabMyAccount.setTabPlacement(JTabbedPane.LEFT);

		myAccountChangeLoginDetails = new MyAccountChangeLoginDetails();
		myAccountChangeLoginDetails.setVisible(false);
		changeLoginDetails.add(myAccountChangeLoginDetails);

		tabMyAccount.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				if (tabMyAccount.getSelectedIndex() != 1) {

				} else {
					myAccountChangeLoginDetails.setVisible(true);
					myAccountChangeLoginDetails.setPreferredSize(new Dimension(1000, 400));
					myAccountChangeLoginDetails.getPanelToHoldLowerComponents()
							.setPreferredSize(new Dimension(400, 380));
					myAccountChangeLoginDetails.getPanelToHoldTopLabels().setPreferredSize(new Dimension(400, 130));
				}
			}
		});

		//////////////////////////////////////////////////////////////////////////////////////////////
		////////// EVERYTHING ABOUT DORM STARTS FROM HERE
		////////////////////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////

		// home4.setBackground(Color.red);
		/*
		 * 
		 * Dormitory panel starts here
		 * 
		 */
		dormPanel = new DormPanel();
		home4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		home4.add(dormPanel);
		dormPanel.setPreferredSize(new Dimension(1160, 480));

		////// laboratory ICT
		laboratoryICT = new LaboratoryICT();
		home5.add(laboratoryICT);
		laboratoryICT.setPreferredSize(new Dimension(1170, 490));
		laboratoryICT.getScrollPaneItems().setPreferredSize(new Dimension(1153, 400));
		laboratoryICT.getScrollPaneEvents().setPreferredSize(new Dimension(1153, 400));
		laboratoryICT.setVisible(false);

		laboratorySwitcherPanel = new LaboratorySwitcherPanel();
		home5.add(laboratorySwitcherPanel);
		laboratorySwitcherPanel.setVisible(false);

		laboratorySciencePractical = new LaboratorySciencePractical();
		laboratorySciencePractical.setPreferredSize(new Dimension(1170, 490));
		home5.add(laboratorySciencePractical);
		laboratorySciencePractical.setPreferredSize(new Dimension(1170, 490));
		laboratorySciencePractical.getScrollPaneItems().setPreferredSize(new Dimension(1153, 400));
		laboratorySciencePractical.getScrollPaneEvents().setPreferredSize(new Dimension(1153, 400));
		laboratorySciencePractical.setVisible(false);

		assetsPanel = new AssetsPanel();
		home3.add(assetsPanel);
		assetsPanel.setVisible(false);
		assetsPanel.setPreferredSize(new Dimension(1170, 490));

		/*
		 * 
		 * Dispensary starts here
		 * 
		 */

		dispensaryPanel = new DispensaryPanel();
		panelDispensaryTab.add(dispensaryPanel);
		dispensaryPanel.setPreferredSize(new Dimension(1170, 490));

		attendanceLessonsPanel = new AttendanceLessonsPanel();
		attendanceLessonsPanel.setVisible(false);
		attendanceLessonsPanel.setPreferredSize(new Dimension(1170, 490));
		home2.add(attendanceLessonsPanel);

		NotificationsAlerts notificationsAlerts = new NotificationsAlerts();
		notificationsAlerts.setVisible(false);
		notificationsAlerts.setPreferredSize(new Dimension(1170, 490));
		home1.add(notificationsAlerts);

		tabs.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				if (tabs.getSelectedIndex() != 3) {
					assetsPanel.setVisible(false);
				} else {
					assetsPanel.setVisible(true);
					assetsPanel.fieldAsset.removeAllItems();
					displayInComboBox(assetsPanel.fieldAsset,
							"select scatname from subcategory where account_type='Current Asset' or account_type='Fixed Asset'");

				}

				if (tabs.getSelectedIndex() != 2) {
					attendanceLessonsPanel.setVisible(false);
				} else {
					attendanceLessonsPanel.setVisible(true);
				}

				if (tabs.getSelectedIndex() != 1) {
					notificationsAlerts.setVisible(false);
					alertsNumbers.setVisible(false);
					notificationsAlerts.comboNoticeUsers.getSelectionModel().select(0);

					notificationsAlerts.displayInComboBoxNoticeUsers(notificationsAlerts.comboNoticeUsers,
							"select username from application_users");
				} else {
					notificationsAlerts.setVisible(true);
					alertsNumbers.setVisible(true);
					notificationsAlerts.userName.setText(current_user.getText());
					System.out.println(notificationsAlerts.userName.getText());

				}

				if (tabs.getSelectedIndex() != 0) {

					admissionListViewPanel.setVisible(false);
					studentsTreePanel.setVisible(false);
					staffsTreePanel.setVisible(false);
					libraryTree.setVisible(false);
					academicPanel.setVisible(false);
					timeTableTreePanel.setVisible(false);
					financeTree.setVisible(false);
					academicsMarksEntries.setVisible(false);
					graphicalStudentsAnalysis.setVisible(false);
					graphicalSubjectsAnalysis.setVisible(false);
					subjectsProgressAnalysisData.setVisible(false);
					studentsProgressAnalysisData.setVisible(false);
					individualTestsAnalysis.setVisible(false);
					giftedAndAtRiskStudents.setVisible(false);
					autoGeneratingAndPrintingReportCards.setVisible(false);

					
					
					
					

				} else {
					academicsMarksEntries.setVisible(false);
					graphicalStudentsAnalysis.setVisible(false);
					graphicalSubjectsAnalysis.setVisible(false);
					subjectsProgressAnalysisData.setVisible(false);
					studentsProgressAnalysisData.setVisible(false);
					individualTestsAnalysis.setVisible(false);
					giftedAndAtRiskStudents.setVisible(false);
					autoGeneratingAndPrintingReportCards.setVisible(false);
					home.setLayout(new FlowLayout(FlowLayout.LEFT, 75, 25));

					studentLeadersList.setVisible(false);
					generalStudentsPanel.setVisible(false);

					la1.setVisible(true);
					la2.setVisible(true);
					la3.setVisible(true);
					la4.setVisible(true);
					la5.setVisible(true);
					la6.setVisible(true);
					la7.setVisible(true);
					la8.setVisible(true);

					academicPanel.setVisible(false);
					panelforclasses.setVisible(false);
					staffsPanel.setVisible(false);

					panelS1.setVisible(false);
					panelS2.setVisible(false);
					panelS3.setVisible(false);
					panelS4.setVisible(false);
					panelS5Arts.setVisible(false);
					panelS5Sci.setVisible(false);
					panelS6Arts.setVisible(false);
					panelS6Sci.setVisible(false);

					outerpanel.setVisible(false);
					panelforclasses.setVisible(false);
					labelforpic.setVisible(false);

					scrollerforallstudents.setVisible(false);
					fixedScroll.setVisible(false);

					staffsPanel.setVisible(false);

					nonTeachingStaffs.setVisible(false);
					teachingStaffsPanel.setVisible(false);

					academicPanel.setVisible(false);
					admission.setVisible(false);
					timeTableExamination.setVisible(false);
					timeTableLesson.setVisible(false);
					timeTableTeachersSchedule.setVisible(false);

					btnBackFromOverview.setVisible(false);
					btnBackfromStudents.setVisible(false);
					btnbackPrefects.setVisible(false);
					btnPrintAllStudents.setVisible(false);

					libraryManageBooks.setVisible(false);
					issueBook.setVisible(false);
					returnBook.setVisible(false);

					academicsMarksEntries.setVisible(false);

					panelPrefects.setVisible(false);
					panelUNSA.setVisible(false);


					financeGeneralLedger.setVisible(false);
					financeNotes.setVisible(false);
					financeIndividualAccounts.setVisible(false);
					financeStatementOfComprehensiveIncome.setVisible(false);
					financeStatementOfFinancialPosition.setVisible(false);
					financeStockCard.setVisible(false);

					adminReportPanel.setVisible(false);
					libraryManageBooks.setVisible(false);
					libraryTree.setVisible(false);

				}
				if (tabs.getSelectedIndex() != 4) {

					home4.setVisible(false);

				} else {
					home4.setVisible(true);
					admissionListViewPanel.setVisible(false);
					studentsTreePanel.setVisible(false);
					staffsTreePanel.setVisible(false);
					libraryTree.setVisible(false);
					academicPanel.setVisible(false);
					timeTableTreePanel.setVisible(false);
					financeTree.setVisible(false);

				}

				if (tabs.getSelectedIndex() == 5) {
					home4.setVisible(false);
					laboratorySwitcherPanel.setVisible(true);

					laboratorySwitcherPanel.getButtonLabICT().addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {

							laboratoryICT.setVisible(true);
							laboratorySciencePractical.setVisible(false);

						}
					});

					laboratorySwitcherPanel.getButtonLabSciencePractical().addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {

							laboratoryICT.setVisible(false);
							laboratorySciencePractical.setVisible(true);
							laboratorySwitcherPanel.setVisible(false);
						}
					});

				} else {

				}
				if (tabs.getSelectedIndex() != 6) {
					dispensaryPanel.setVisible(false);
				} else {
					dispensaryPanel.setVisible(true);
				}

			}
		});

		///////////////////////////////////////////////////////////

		// FINANCE STARTS HERE//

		///////////////////////////////////////////////////////////

		financeTree = new FinanceTree();
		financeTree.setPreferredSize(new Dimension(152, 540));
		financeTree.setVisible(false);
		leftpanel.add(financeTree);
		// financeTree.getScrollPaneFinance().setPreferredSize(new Dimension(152, 540));

		listenForMouseClickedInFinance = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				financeTree.setVisible(true);

				la1.setVisible(false);
				la5.setVisible(false);
				la2.setVisible(false);
				la6.setVisible(false);
				la3.setVisible(false);
				la7.setVisible(false);
				la4.setVisible(false);
				la8.setVisible(false);

			}

		};

		la8.addMouseListener(listenForMouseClickedInFinance);
		la8.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

				la8.setIcon(new ImageIcon(img8));
			}

			@Override
			public void mouseEntered(MouseEvent e) {

				la8.setIcon(new ImageIcon(img88));
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		financeGeneralLedger = new FinanceGeneralLedger();
		home.add(financeGeneralLedger);
		financeGeneralLedger.setVisible(false);
		financeGeneralLedger.setPreferredSize(new Dimension(1170, 490));

		financeStockCard = new FinanceStockCard();
		home.add(financeStockCard);
		financeStockCard.setVisible(false);
		financeStockCard.setPreferredSize(new Dimension(1170, 490));

		financeIndividualAccounts = new FinanceIndividualAccounts();
		home.add(financeIndividualAccounts);
		financeIndividualAccounts.setVisible(false);
		financeIndividualAccounts.setPreferredSize(new Dimension(1170, 490));

		financeStatementOfComprehensiveIncome = new FinanceStatementOfComprehensiveIncome();
		home.add(financeStatementOfComprehensiveIncome);
		financeStatementOfComprehensiveIncome.setVisible(false);
		financeStatementOfComprehensiveIncome.setPreferredSize(new Dimension(1170, 490));

		financeStatementOfFinancialPosition = new FinanceStatementOfFinancialPosition();
		home.add(financeStatementOfFinancialPosition);
		financeStatementOfFinancialPosition.setVisible(false);
		financeStatementOfFinancialPosition.setPreferredSize(new Dimension(1170, 490));

		financeNotes = new FinanceNotes();
		home.add(financeNotes);
		financeNotes.setVisible(false);
		financeNotes.setPreferredSize(new Dimension(1170, 490));

		financeTree.getLvListDisplayedFinance().getSelectionModel().selectedItemProperty()
				.addListener(new javafx.beans.value.ChangeListener() {

					@Override
					public void changed(ObservableValue arg0, Object oldValue, Object newValue) {

						String node = financeTree.getLvListDisplayedFinance().getSelectionModel().getSelectedItem()
								.toString();
						if (!node.equalsIgnoreCase("Notes")) {
							financeNotes.setVisible(false);
						} else {

							checkUserRightsForNotes();

						}

						if (!node.equalsIgnoreCase("Balance Sheet")) {
							financeStatementOfFinancialPosition.setVisible(false);
						} else {
							checkUserRightsForBalanceSheet();
						}

						if (!node.equalsIgnoreCase("Income Statement")) {
							financeStatementOfComprehensiveIncome.setVisible(false);
						} else {
							checkUserRightsForIncomeStatement();
						}

						if (!node.equalsIgnoreCase("Accounts")) {
							financeIndividualAccounts.setVisible(false);

						} else {
							checkUserRightsForAccounts();
						}

						if (!node.equalsIgnoreCase("Trial Balance")) {

						} else {
							checkUserRightsForTrialBalance();
						}

						if (!node.equalsIgnoreCase("Stock Card")) {
							financeStockCard.setVisible(false);
						} else {
							checkUserRightsForStockCard();

						}

						if (!node.equalsIgnoreCase("Student Ledger Card")) {
						} else {

							checkUserRightsForStudentLedger();
						}

						if (!node.equalsIgnoreCase("Cash Book")) {
							// financeCashBook.setVisible(false);
						} else {

							checkUserRightsForCashBook();

						}
						////
						if (!node.equalsIgnoreCase("General Ledger")) {
							financeGeneralLedger.setVisible(false);

						} else {

							checkUserRightsForGeneralLedger();

						}
						////// THIS STORE LEDGER FOOL SHOULD ALWAYS COME LAST IN THIS
						////// LISTERNER
						if (!node.equalsIgnoreCase("Store Ledger")) {

						} else {
							checkUserRightsForStoreLedger();
						}

					}
				});

		new Timer();

		new TimerTask() {

			@Override
			public void run() {

				// show requirements for admitted student

				Object[] columns = { "Class", "Req Name", "Standard Amount", "Amount Brought", "Balance" };

				DefaultTableModel dm = new DefaultTableModel() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

				};

				dm.setColumnIdentifiers(columns);
				java.sql.Connection connn = null;
				PreparedStatement pstatement = null;
				try {
					tableAdmitStudentRequirements.setModel(dm);

				} catch (Exception exx) {
					exx.printStackTrace();
				} finally {
					if (connn != null) {
						try {
							pstatement.close();
							connn.close();

						} catch (SQLException e) {

							e.printStackTrace();
						}

					}
				}

			}
		};

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {

				

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

				logMeOut(
						"update login_tracking set logout_status='Logged Out', logout_date_and_time=now() where username='"
								+ current_user.getText() + "' and logout_status is null");
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

				SystemUsersPanel notificationsAlerts = new SystemUsersPanel();

				displayData(notificationsAlerts.tableUsers,
						"select username, CONCAT(first_name,' ',last_name) from application_users");

				schoolDetails();
				
				
		        	  
		             

				// checkMessages();

			}
		});

		/**********************************************
		 * 
		 * Checking For New Messages Every 5 second
		 * 
		 * 
		 *********************************************/

		Timer timerMessages = new Timer();
		TimerTask ttaskMessages = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				checkMessages();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						alertsNumbers.displayNumberOfUnreadMessagesPerUser(
								"select message_sender,count(*) from chat_messenger where read_by is null and message_receiver='"
										+ current_user.getText() + "' group by message_sender");

					}
				});
			}

		};
		timerMessages.schedule(ttaskMessages, 100, 5000);
		
		
		/***********************************************************
		 * Timer for backing up database
		 ***********************************************************/
		
		Timer timerfrbackup=new Timer();
		TimerTask taskBackup=new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				backUpDatabaseEvery10Minutes();
		        	
			}
			
		};

		timerfrbackup.schedule(taskBackup, 10000, 600000);
		
		
		JavaDatabaseSelectStatements databaseSelectStatements = new JavaDatabaseSelectStatements();

//		modelAllStudents = databaseSelectStatements.DisplayAllStudents();
//		allstudentsinAyear.setModel(modelAllStudents);

		allstudentsinAyear.getColumnModel().getColumn(0).setPreferredWidth(85);
		allstudentsinAyear.getColumnModel().getColumn(2).setPreferredWidth(85);
		allstudentsinAyear.getColumnModel().getColumn(3).setPreferredWidth(85);
		allstudentsinAyear.getColumnModel().getColumn(4).setPreferredWidth(85);
		allstudentsinAyear.getColumnModel().getColumn(5).setPreferredWidth(200);
		allstudentsinAyear.getColumnModel().getColumn(6).setPreferredWidth(200);
		allstudentsinAyear.getColumnModel().getColumn(1).setPreferredWidth(410);

//		modelS1Students = databaseSelectStatements.DisplaySeniorOneStudents();
//		tableS1.setModel(modelS1Students);

		tableS1.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableS1.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableS1.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableS1.getColumnModel().getColumn(4).setPreferredWidth(85);
		tableS1.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableS1.getColumnModel().getColumn(6).setPreferredWidth(200);
		tableS1.getColumnModel().getColumn(1).setPreferredWidth(410);

//		modelS2Students = databaseSelectStatements.DisplaySeniorTwoStudents();
//		tableS2.setModel(modelS2Students);

		tableS2.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableS2.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableS2.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableS2.getColumnModel().getColumn(4).setPreferredWidth(85);
		tableS2.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableS2.getColumnModel().getColumn(6).setPreferredWidth(200);
		tableS2.getColumnModel().getColumn(1).setPreferredWidth(410);

//		modelS3Students = databaseSelectStatements.DisplaySeniorThreeStudents();
//		tableS3.setModel(modelS3Students);

		tableS3.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableS3.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableS3.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableS3.getColumnModel().getColumn(4).setPreferredWidth(85);
		tableS3.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableS3.getColumnModel().getColumn(6).setPreferredWidth(200);
		tableS3.getColumnModel().getColumn(1).setPreferredWidth(410);

//		modelS4Students = databaseSelectStatements.DisplaySeniorFourStudents();
//		tableS4.setModel(modelS4Students);

		tableS4.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableS4.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableS4.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableS4.getColumnModel().getColumn(4).setPreferredWidth(85);
		tableS4.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableS4.getColumnModel().getColumn(6).setPreferredWidth(200);
		tableS4.getColumnModel().getColumn(1).setPreferredWidth(410);

//		modelS5ArtsStudents = databaseSelectStatements.DisplaySeniorFiveArtsStudents();
//		tableS5Arts.setModel(modelS5ArtsStudents);

		tableS5Arts.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableS5Arts.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableS5Arts.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableS5Arts.getColumnModel().getColumn(4).setPreferredWidth(85);
		tableS5Arts.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableS5Arts.getColumnModel().getColumn(6).setPreferredWidth(200);
		tableS5Arts.getColumnModel().getColumn(1).setPreferredWidth(410);

//		modelS5SciStudents = databaseSelectStatements.DisplaySeniorFiveSciStudents();
//		tableS5Sci.setModel(modelS5SciStudents);

		tableS5Sci.getColumnModel().getColumn(0).setPreferredWidth(85);
		tableS5Sci.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableS5Sci.getColumnModel().getColumn(3).setPreferredWidth(85);
		tableS5Sci.getColumnModel().getColumn(4).setPreferredWidth(85);
		tableS5Sci.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableS5Sci.getColumnModel().getColumn(6).setPreferredWidth(200);
		tableS5Sci.getColumnModel().getColumn(1).setPreferredWidth(410);

//		tablemodelPrefects = databaseSelectStatements.DisplayPrefects();
//		tablePrefects.setModel(tablemodelPrefects);

//		tablemodelUnsa = databaseSelectStatements.DisplayUNSA();
//		tableUNSA.setModel(tablemodelUnsa);

		//////////////////// Admin Report///////////////////
		adminReportPanel = new AdminReportPanel();
		home.add(adminReportPanel);
		adminReportPanel.setVisible(false);
		adminReportPanel.setPreferredSize(new Dimension(1170, 490));

		home.setBackground(new Color(0, 102, 102));
		home1.setBackground(new Color(0, 102, 102));
		home2.setBackground(new Color(0, 102, 102));
		home3.setBackground(new Color(0, 102, 102));
		home4.setBackground(new Color(0, 102, 102));
		home5.setBackground(new Color(0, 102, 102));
		home6.setBackground(new Color(0, 102, 102));
		upper.setBackground(new Color(0, 102, 102));
		lowerpanel.setBackground(new Color(0, 102, 102));
		leftpanel.setBackground(new Color(0, 102, 102));
		loginpanel.setBackground(new Color(0, 102, 102));
		address.setBackground(new Color(0, 102, 102));
		bigmiddle.setBackground(new Color(0, 102, 102));
		panDateAndInternet.setBackground(new Color(0, 102, 102));
		panelSpace.setBackground(new Color(0, 102, 102));

		displayInComboBox(comboBoxClassofReporting, "select class_name from student_classes");
		displayInComboBox(fieldBankName, "select bank_name from banks");
		displayInComboBox(comboBoxClassesReportedStudents, "select class_name from student_classes");
		displayAccountName(comboBoxSponsorName, "select scatname from subcategory");
		displayInComboBox(comboBoxtermOfReporting, "select term_name from student_terms");

		setVisible(true);

		Timer timerRights = new Timer();
		TimerTask ttask = new TimerTask() {

			@Override
			public void run() {
				checkUserRights();

			}
		};

		timerRights.schedule(ttask, 1000);

	}

	public String getCountAllStudents() {
		int rowsCount = allstudentsinAyear.getRowCount();

		return "COUNT = " + rowsCount;
	}

	public String getRangeAgeAllStudents() {

		float maximum = maxAge;
		float minimum = minAge;
		float range = maximum - minimum;
		return "RANGE = " + range;
	}

	public String getMinAgeAllStudents() {
		ArrayList<Float> list = new ArrayList<Float>();
		for (int i = 0; i < allstudentsinAyear.getRowCount(); i++) {

			list.add(Float.parseFloat(allstudentsinAyear.getValueAt(i, 4).toString()));
		}

		minAge = Collections.min(list);
		return "MIN = " + minAge;
	}

	public String getMaxAgeAllStudents() {
		ArrayList<Float> list = new ArrayList<Float>();
		for (int i = 0; i < allstudentsinAyear.getRowCount(); i++) {

			list.add(Float.parseFloat(allstudentsinAyear.getValueAt(i, 4).toString()));
		}

		maxAge = Collections.max(list);

		return "MAX = " + maxAge;
	}

	public String getSumAllStudents() {
		int rowsCount = allstudentsinAyear.getRowCount();
		sumAllStudents = 0;
		for (int i = 0; i < rowsCount; i++) {
			sumAllStudents = sumAllStudents + Float.parseFloat(allstudentsinAyear.getValueAt(i, 4).toString());
		}

		return "SUM = " + sumAllStudents;

	}

	public String getAverageAgeAllStudents() {
		float Sum = sumAllStudents;
		int rowsCount = allstudentsinAyear.getRowCount();
		float average = Sum / rowsCount;
		return "AVERAGE = " + average;
	}

	public void closing() {

		WindowEvent closing = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closing);
	}

	public void flashMyField(final JTextField field, Color flashColor, final int timerDelay, int totalTime) {
		final int totalCount = totalTime / timerDelay;
		javax.swing.Timer timer = new javax.swing.Timer(timerDelay, new ActionListener() {
			int count = 0;

			public void actionPerformed(ActionEvent evt) {
				if (count % 2 == 0) {
					field.setBackground(flashColor);
				} else {
					field.setBackground(null);
					if (count >= totalCount) {
						((javax.swing.Timer) evt.getSource()).stop();
					}
				}
				count++;
			}
		});
		timer.start();
	}

	public static boolean testInet(String site) {
		Socket sock = new Socket();
		InetSocketAddress addr = new InetSocketAddress(site, 80);
		try {
			sock.connect(addr, 3000);
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				sock.close();
			} catch (IOException e) {
			}
		}
	}

	private DefaultMutableTreeNode addNode(String newnode, DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(newnode);
		parent.add(newChild);
		return newChild;
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

	public void logMeOut(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Log Not Executed Successfully " + ex.getMessage());

		}
	}

	public void InsertSilently(String querries) {

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

	public Date convertFromUtilToSQLDate(java.util.Date dateUtil) {

		if (dateUtil != null) {
			java.sql.Date sqlDate = new java.sql.Date(dateUtil.getTime());

			return sqlDate;
		} else {
			return null;
		}
	}

	public ImageIcon ResizeImage(String ImagePath) {

		ImageIcon MyImg = new ImageIcon(ImagePath);
		Image img = MyImg.getImage();
		Image newimg = img.getScaledInstance(labelPictureUploaded.getWidth(), labelPictureUploaded.getHeight(),
				Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newimg);

		return image;

	}
	//// 192.168.43.29

	public void printAdmissionDetails() {
		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setJobName(" Opt De Solver Printing ");

		pj.setPrintable(new Printable() {

			@Override
			public int print(Graphics pg, PageFormat pf, int pageNum) {
				if (pageNum > 0)
					return Printable.NO_SUCH_PAGE;

				Graphics2D g2 = (Graphics2D) pg;
				g2.translate(pf.getImageableX(), pf.getImageableY());

				AffineTransform originalTransform = g2.getTransform();

				double scaleX = pf.getImageableWidth() / printAdmissionDetails.getWidth();
				double scaleY = pf.getImageableHeight() / printAdmissionDetails.getHeight();
				// Maintain aspect ratio
				double scale = Math.min(scaleX, scaleY);
				g2.translate(pf.getImageableX(), pf.getImageableY());
				g2.scale(scale, scale);

				printAdmissionDetails.paintAll(g2);// Users.this.printAll(g2);
													// prints the whole frame

				g2.setTransform(originalTransform);

				return Printable.PAGE_EXISTS;
			}
		});
		if (pj.printDialog() == false)
			return;
		try {
			pj.print();
		} catch (PrinterException xcp) {
			xcp.printStackTrace(System.err);

		}

	}

	public JTextField getFieldIDReporting() {
		return fieldIDReporting;
	}

	public JLabel getLabelPictureUploaded() {
		return labelPictureUploaded;
	}

	public JLabel getcurrent_user() {
		return current_user;
	}

	public JLabel getHeadingSchoolName() {
		return headingSchoolName;
	}

	public JLabel getAddress1BoxNumber() {
		return address1BoxNumber;
	}

	public JLabel getAddress2Email() {
		return address2Email;
	}

	public JLabel getAddress3Telephone() {
		return address3Telephone;
	}

	class VideoFeedTaker extends Thread {
		@Override
		public void run() {

			while (isRunning) {

				try {
					image = webcam.getImage();
					labelImage.setIcon(new ImageIcon(image));

					Thread.sleep(10);
				} catch (InterruptedException e) {

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

			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e3) {

				}
			}
		}

	}

	public DefaultTableModel DisplayAvailableRequirementsForReportingStudents() {

		Connection conn = null;

		Object[] columns = { "Responsible Class", "Requirement Name", "Expected Amount", "Quantity Brought" };

		dmRequirements = new DefaultTableModel();

		dmRequirements.setColumnIdentifiers(columns);

		String selectedClass = comboBoxClassofReporting.getSelectedItem().toString();

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

				dmRequirements.addRow(new String[] { Class, NAME, Standard, null });
			}

			return dmRequirements;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public void checkFeesbalance() {

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement("select debit-credit from student_ledger where" + " payment_code='"
					+ fieldBanks.getText() + "' and credit is not null order by id DESC LIMIT 1");
			rs = pst.executeQuery();

			if (rs.next()) {

				fieldFeesPayment.setText(rs.getString(1));
			}

		} catch (Exception e) {

		}
	}

	public void showReportinStudentsInfo() {

		String sqlStudentsInfo = "select * from students_info,student_ledger where student_ledger.class_number = '"
				+ fieldclassNumber.getText() + "' and student_ledger.year LIKE '%" + yearofReporting.getYear()
				+ "%' and students_info.payment_code=student_ledger.payment_code group by student_ledger.payment_code";
		String sqlParentsInfo = "select * from parents_info where class_number LIKE '%" + fieldclassNumber.getText()
				+ "%' and year LIKE '%" + yearofReporting.getYear() + "%'";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentsInfo);
			rs = pst.executeQuery();

			if (rs.next()) {
				fieldName.setText(rs.getString("students_info.student_name"));
				fieldCounty.setText(rs.getString("students_info.county"));
				fieldSubCounty.setText(rs.getString("students_info.sub_county"));
				comboBoxTermOfAdmission.setText(rs.getString("students_info.term"));
				comboBoxGender.setText(rs.getString("students_info.gender"));
				fieldWardLC1.setText(rs.getString("students_info.ward_lc1"));
				fieldParish.setText(rs.getString("students_info.parish"));
				fieldDistrict.setText(rs.getString("students_info.home_district"));
				comboBox1Religion.setText(rs.getString("students_info.religion"));
				comboBoxNationality.setText(rs.getString("students_info.nationality"));
				comboBoxDorms.setText(rs.getString("students_info.dormitory"));
				comboBoxClasses.setText(rs.getString("students_info.student_class"));
				fieldID.setText(rs.getString("students_info.class_number"));
				fieldstudentName.setText(rs.getString("students_info.student_name"));
				dateChooserDOB.setText(rs.getString("students_info.date_of_birth"));

				byte[] img = rs.getBytes("students_info.photo");
				ImageIcon image = new ImageIcon(img);
				Image im = image.getImage();
				Image im2 = im.getScaledInstance(labelPictureRequirementDebtorsReportingStudents.getWidth(),
						labelPictureRequirementDebtorsReportingStudents.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon newImage = new ImageIcon(im2);
				labelPictureRequirementDebtorsReportingStudents.setIcon(newImage);
				fieldBanks.setText(rs.getString("payment_code"));
			} else {

				fieldName.setText(null);
				fieldCounty.setText(null);
				fieldSubCounty.setText(null);
				comboBoxTermOfAdmission.setText(null);
				comboBoxGender.setText(null);
				fieldWardLC1.setText(null);
				fieldParish.setText(null);
				fieldDistrict.setText(null);
				comboBox1Religion.setText(null);
				comboBoxNationality.setText(null);
				comboBoxDorms.setText(null);
				comboBoxClasses.setText(null);
				fieldID.setText(null);
				fieldstudentName.setText(null);
				dateChooserDOB.setText(null);
				labelPictureRequirementDebtorsReportingStudents.setIcon(null);

			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlParentsInfo);
			rs = pst.executeQuery();

			if (rs.next()) {
				fieldParentEmail.setText(rs.getString("parent_email"));
				fieldFatherPhone.setText(rs.getString("fathers_phone"));
				fieldParentName.setText(rs.getString("fathers_name"));
				fieldMotherName.setText(rs.getString("mothers_name"));
				fieldMotherEmailPhone.setText(rs.getString("mothers_phone"));
				fieldGuardianName.setText(rs.getString("guardians_name"));
				fieldGuardianPhone.setText(rs.getString("guardians_phone"));
				fieldSponsorName.setText(rs.getString("sponsors_name"));
				fieldSponsorPhone.setText(rs.getString("sponsors_phone"));
				fieldAddress.setText(rs.getString("parent_address"));

			} else {

				fieldParentEmail.setText(null);
				fieldFatherPhone.setText(null);
				fieldParentName.setText(null);
				fieldMotherName.setText(null);
				fieldMotherEmailPhone.setText(null);
				fieldGuardianName.setText(null);
				fieldGuardianPhone.setText(null);
				fieldSponsorName.setText(null);
				fieldSponsorPhone.setText(null);
				fieldAddress.setText(null);

			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}

	}

	public void saveToClassInWhicYouAreReporting() {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement("insert into `" + comboBoxClassofReporting.getSelectedItem()
					+ "`(class_number,payment_code,student_class,student_name,date_of_birth,address,sponsor,dormitory,term,year,fathers_contact,mothers_contact,guardians_contact) values(?,?,?,?,"
					+ "'" + dateChooserDOB.getText() + "',?,?,?,?,?,?,?,?)");

			pst.setString(1, fieldID.getText());
			pst.setString(2, fieldBanks.getText());
			pst.setString(3, comboBoxClassofReporting.getSelectedItem().toString());
			pst.setString(4, fieldstudentName.getText());
			pst.setString(5, fieldAddress.getText());
			pst.setString(6, fieldSponsorName.getText());
			pst.setString(7, comboBoxDorms.getText());
			pst.setString(8, comboBoxtermOfReporting.getSelectedItem().toString());
			pst.setLong(9, yearofReporting.getYear());
			pst.setString(10, fieldFatherPhone.getText());
			pst.setString(11, fieldMotherEmailPhone.getText());
			pst.setString(12, fieldGuardianPhone.getText());

			pst.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

	public void showAccountNumber() {

		String sql = "select account_number from banks where bank_name='" + fieldBankName.getSelectedItem() + "'";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next()) {

				fieldfeesPaidToAccount.setText(rs.getString(1));
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}
	}

	public void SaveStudentPaymentInfoToLedger() {

		if (comboBoxtermOfReporting.getSelectedIndex() == 0) {
			sqlStudentPaymentInfo = "insert into student_ledger(class_number,student_name,year,term,receipt_number,account_name,details,credit,bank_name,`First Term`) "
					+ "values(?,?,?,?,?,?,?,?,?)";

		} else if (comboBoxtermOfReporting.getSelectedIndex() == 1) {
			sqlStudentPaymentInfo = "insert into student_ledger(class_number,student_name,year,term,receipt_number,account_name,details,credit,bank_name,`Second Term`) "
					+ "values(?,?,?,?,?,?,?,?,?)";

		} else {
			sqlStudentPaymentInfo = "insert into student_ledger(class_number,student_name,year,term,receipt_number,account_name,details,credit,bank_name,`Third Term`) "
					+ "values(?,?,?,?,?,?,?,?,?)";
		}

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentPaymentInfo);

			pst.setString(1, fieldclassNumber.getText());
			pst.setString(2, fieldstudentName.getText());
			pst.setObject(3, yearofReporting.getYear());
			pst.setString(4, comboBoxtermOfReporting.getSelectedItem().toString());
			pst.setString(5, fieldReceiptNumber.getText());
			pst.setString(7, "Fees Payment For " + comboBoxtermOfReporting.getSelectedItem().toString());
			pst.setString(6, comboBoxSponsorName.getSelectedItem().toString());
			pst.setString(8, fieldfeesPaid.getText());
			pst.setString(9, fieldBankName.getSelectedItem().toString());
			pst.setString(10, comboBoxtermOfReporting.getSelectedItem().toString());

			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Successfully Saved Fees Payment Info");

		} catch (Exception e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Please make sure a picture is attached");
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
				;
			}
		}

	}

	public void saveStudentPaymentInfoToLedger() {

		sqlStudentPaymentInfo = "update student_ledger set credit='" + fieldfeesPaid.getText() + "',receipt_number='"
				+ fieldReceiptNumber.getText() + " account_name='" + comboBoxSponsorName.getSelectedItem()
				+ "' where student_class'" + comboBoxClassofReporting.getSelectedItem() + "' and " + "year='"
				+ yearofReporting.getYear() + "' and student_name='" + fieldstudentName.getText() + "' and term='"
				+ comboBoxtermOfReporting.getSelectedItem() + "' " + "and fathers_contact='"
				+ fieldFatherPhone.getText() + "' and mothers_contact='" + fieldMotherEmailPhone.getText() + "' and "
				+ "guardians_contact='" + fieldGuardianPhone.getText() + "'";

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlStudentPaymentInfo);

			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Successful");

		} catch (Exception e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "" + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		}

	}

	public void displayInComboBox(JComboBox<String> fieldBankName2, String query) {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			fieldBankName2.removeAll();

			while (rs.next()) {
				fieldBankName2.addItem(rs.getString(1));
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

	public void buildData() {
		Connection c;
		Calendar cal = Calendar.getInstance();

		String four = "" + cal.getTime();

		String answer = four.substring(four.length() - 4);

		String SQL = "SELECT student_class,count(student_class) from all_students_and_parents where year LIKE '"
				+ answer + "%' and term='" + comboTerm.getSelectionModel().getSelectedItem()
				+ "' and student_class is not null group by student_class";
		System.out.println(cal.getTime());

		// String SQL = "SELECT Name,Marks from customers where year='" + cal
		// + "' group by student_class";

		dataSeries1 = new XYChart.Series<String, Double>();

		try {
			c = CashBookController.getConnection();

			ResultSet rs = c.createStatement().executeQuery(SQL);
			while (rs.next()) {

				dataSeries1.getData().add(new XYChart.Data<>(rs.getString(1), rs.getDouble(2)));
			}
			barChart.getData().add(dataSeries1);
		} catch (Exception e) {
			System.out.println("Error on DB connection" + e.getMessage());
			return;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e3) {

				}
			}
		}

	}

	public void displayAccountName(JComboBox<String> combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			combo.removeAll();
			combo.removeAllItems();

			while (rs.next()) {
				combo.addItem(rs.getString(1));
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
					fSheet.autoSizeColumn(j);
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

	public void checkUserRights() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + current_user.getText() + "'");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				if (rs.getInt("laboratory tab") == 1) {
					tabs.setEnabledAt(5, true);
				} else {
					tabs.setEnabledAt(5, false);
				}
				if (rs.getInt("dispensary tab") == 1) {
					tabs.setEnabledAt(6, true);
				} else {
					tabs.setEnabledAt(6, false);
				}

				if (rs.getInt("attendance tab") == 1) {
					tabs.setEnabledAt(2, true);
				} else {
					tabs.setEnabledAt(2, false);
				}
				if (rs.getInt("notice tab") == 1) {
					tabs.setEnabledAt(1, true);
				} else {
					tabs.setEnabledAt(1, false);
				}

				if (rs.getInt("dormitory tab") == 1) {
					tabs.setEnabledAt(4, true);
				} else {

					tabs.setEnabledAt(4, false);

				}

				if (rs.getInt("assets tab") == 1) {
					tabs.setEnabledAt(3, true);
				} else {

					tabs.setEnabledAt(3, false);

				}

				if (rs.getInt("admission package") == 1) {

				} else {
					la4.removeMouseListener(listenForMouseClickedInAdmission);

				}

				if (rs.getInt("academics package") == 1) {

				} else {
					la3.removeMouseListener(listenForMouseClickedInAcademics);
				}

				if (rs.getInt("students package") == 1) {

				} else {
					la1.removeMouseListener(listenForMouseClickedInStudents);
				}

				if (rs.getInt("staffs package") == 1) {

				} else {
					la2.removeMouseListener(listenForMouseClickedInStaffs);

				}

				if (rs.getInt("library package") == 1) {

				} else {

					la7.removeMouseListener(listenForMouseClickedInLibrary);

				}

				if (rs.getInt("time table package") == 1) {

				} else {

					la6.removeMouseListener(listenForMouseClickedInTimeTable);
				}

				if (rs.getInt("finance package") == 1 || rs.getInt("store inventory package") == 1) {

				} else {
					la8.removeMouseListener(listenForMouseClickedInFinance);

				}

				if (rs.getInt("administration package") == 1) {

				} else {

					la5.removeMouseListener(listenForMouseClickedInAdmin);

				}

				if (rs.getInt("give permissions to system users") == 1) {

					myRightsPanel.getBtnSave().setEnabled(true);
				} else {

					myRightsPanel.getBtnSave().setEnabled(false);
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

	public void checkUserRightsForCashBook() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + current_user.getText() + "'");

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (rs.getInt("finance package") == 1) {
					try {

						FinanceAnalyticalCashBook = new FinanceAnalyticalCashBook();
					} catch (Exception e1) {

						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "You do not have permission to access this file");
					return;

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

	public void checkUserRightsForAccounts() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + current_user.getText() + "'");

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (rs.getInt("finance package") == 1) {
					try {

						financeIndividualAccounts.setVisible(true);
						home.setLayout(new FlowLayout(FlowLayout.LEFT));
						AccountDAO accountDAO = new AccountDAO();
						FinanceIndividualAccounts.refreshAccountsTable();
						financeIndividualAccounts.displayInComboBox(financeIndividualAccounts.comboBoxAccountNameFilter,
								"select bank_name from banks");
						try {
							accountDAO.getAllAccountsToTable();
						} catch (SQLException e2) {

							e2.printStackTrace();
						}
					} catch (Exception e1) {

						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "You do not have permission to access this file");
					return;

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

	public void checkUserRightsForGeneralLedger() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + current_user.getText() + "'");

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (rs.getInt("finance package") == 1) {
					try {

						financeGeneralLedger.setVisible(true);
						financeGeneralLedger.displayInComboBox(FinanceGeneralLedger.comboBoxAccountName,
								"select scatname from subcategory");
						home.setLayout(new FlowLayout(FlowLayout.LEFT));
						financeGeneralLedger.populateGeneralLedgerTable();
					} catch (Exception e1) {

						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "You do not have permission to access this file");
					return;

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

	public void checkUserRightsForStudentLedger() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + current_user.getText() + "'");

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (rs.getInt("finance package") == 1) {
					try {

						financeStudentLedgerCard = new FinanceStudentLedgerCard();
						financeStudentLedgerCard.setPreferredSize(new Dimension(1160, 480));

						home.setLayout(new FlowLayout(FlowLayout.LEFT));
					} catch (Exception e1) {

						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "You do not have permission to access this file");
					return;

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

	public void checkUserRightsForStoreLedger() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + current_user.getText() + "'");

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (rs.getInt("finance package") == 1 || rs.getInt("inventory_dept") == 1) {
					try {

						financeStoreLedger = new FinanceStoreLedger();
						financeStoreLedger.setPreferredSize(new Dimension(1170, 490));
						home.setLayout(new FlowLayout(FlowLayout.LEFT));
					} catch (Exception e1) {

						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "You do not have permission to access this file");
					return;

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

	public void checkUserRightsForTrialBalance() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + current_user.getText() + "'");

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (rs.getInt("finance package") == 1) {
					try {

						new FinanceTrialBalance();
						home.setLayout(new FlowLayout(FlowLayout.LEFT));

					} catch (Exception e1) {

						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "You do not have permission to access this file");
					return;

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

	public void checkUserRightsForStockCard() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + current_user.getText() + "'");

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (rs.getInt("finance package") == 1 || rs.getInt("inventory_dept") == 1) {
					try {

						financeStockCard.setVisible(true);
						home.setLayout(new FlowLayout(FlowLayout.LEFT));

						FinanceStockCard.buildData(financeStockCard.tableView);
						financeStockCard.displayInComboBox(financeStockCard.fieldItemIssued,
								"select item_name from stores_items");
						financeStockCard.displayInComboBox(financeStockCard.fieldItemReceived,
								"select item_name from stores_items");
					} catch (Exception e1) {

						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "You do not have permission to access this file");
					return;

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

	public void checkUserRightsForIncomeStatement() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + current_user.getText() + "'");

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (rs.getInt("finance package") == 1) {
					try {

						financeStatementOfComprehensiveIncome.setVisible(true);
						home.setLayout(new FlowLayout(FlowLayout.LEFT));

						financeStatementOfComprehensiveIncome.displayInComboBoxOne(
								financeStatementOfComprehensiveIncome.comboBoxTermOne, "select fy_name from fy_names");

						financeStatementOfComprehensiveIncome.displayInComboBoxOne(
								financeStatementOfComprehensiveIncome.comboBoxTermTwo, "select fy_name from fy_names");

						financeStatementOfComprehensiveIncome.displayStatementOfComprehensiveIncome(
								financeStatementOfComprehensiveIncome.tableComprehensiveIncome,
								"select subcategory,sum(`"
										+ financeStatementOfComprehensiveIncome.comboBoxTermOne.getSelectedItem()
										+ "`),sum(`" + financeStatementOfComprehensiveIncome.yearChooserOne.getYear()
										+ "`),sum(`"
										+ financeStatementOfComprehensiveIncome.comboBoxTermTwo.getSelectedItem()
										+ "`),sum(`" + financeStatementOfComprehensiveIncome.yearChooserTwo.getYear()
										+ "`) from budget_expense_income_records where account_type='Income' group by "
										+ "subcategory");

						financeStatementOfComprehensiveIncome.displayStatementOfComprehensiveIncome(
								financeStatementOfComprehensiveIncome.tableComprehensiveIncomeExpense,
								"select subcategory,sum(`"
										+ financeStatementOfComprehensiveIncome.comboBoxTermOne.getSelectedItem()
										+ "`)," + "sum(`"
										+ financeStatementOfComprehensiveIncome.yearChooserOne.getYear() + "`), "
										+ "sum(`"
										+ financeStatementOfComprehensiveIncome.comboBoxTermTwo.getSelectedItem()
										+ "`),sum(`" + financeStatementOfComprehensiveIncome.yearChooserTwo.getYear()
										+ "`) from budget_expense_income_records where account_type='Expense' group by "
										+ "subcategory UNION SELECT 'Depreciation Expense',0 as budget1, sum(asset_cost)-sum(`"
										+ financeStatementOfComprehensiveIncome.yearChooserOne.getYear()
										+ "`),0 as budget2, " + "sum(asset_cost)-sum(`"
										+ financeStatementOfComprehensiveIncome.yearChooserTwo.getYear()
										+ "`) from school_fixed_assets");

						financeStatementOfComprehensiveIncome.labelPeriodOneIncomeValue
								.setText("" + financeStatementOfComprehensiveIncome.decimalformat.format(
										financeStatementOfComprehensiveIncome.getSumOfActualIncomeInPeriodOne()));
						financeStatementOfComprehensiveIncome.labelPeriodTwoIncomeValue
								.setText("" + financeStatementOfComprehensiveIncome.decimalformat.format(
										financeStatementOfComprehensiveIncome.getSumOfActualIncomeInPeriodTwo()));
						financeStatementOfComprehensiveIncome.labelPeriodOneExpenditureValue
								.setText("" + financeStatementOfComprehensiveIncome.decimalformat.format(
										financeStatementOfComprehensiveIncome.getSumOfActualExpenseInPeriodOne()));
						financeStatementOfComprehensiveIncome.labelPeriodTwoExpenditureValue
								.setText("" + financeStatementOfComprehensiveIncome.decimalformat.format(
										financeStatementOfComprehensiveIncome.getSumOfActualExpenseInPeriodTwo()));

						double oneIncome = Double.parseDouble(Float
								.toString(financeStatementOfComprehensiveIncome.getSumOfActualIncomeInPeriodOne()));
						double twoIncome = Double.parseDouble(Float
								.toString(financeStatementOfComprehensiveIncome.getSumOfActualIncomeInPeriodTwo()));

						double oneExpenditure = Double.parseDouble(Float
								.toString(financeStatementOfComprehensiveIncome.getSumOfActualExpenseInPeriodOne()));
						double twoExpenditure = Double.parseDouble(Float
								.toString(financeStatementOfComprehensiveIncome.getSumOfActualExpenseInPeriodTwo()));

						double oneNetIncome = oneIncome - oneExpenditure;
						double twoNetIncome = twoIncome - twoExpenditure;

						financeStatementOfComprehensiveIncome.labelPeriodOneNetIncomeValue
								.setText("" + financeStatementOfComprehensiveIncome.decimalformat.format(oneNetIncome));
						financeStatementOfComprehensiveIncome.labelPeriodTwoNetIncomeValue
								.setText("" + financeStatementOfComprehensiveIncome.decimalformat.format(twoNetIncome));

						displayInComboBox(financeStatementOfComprehensiveIncome.comboBoxTermOne,
								"select fy_name from fy_names");
						displayInComboBox(financeStatementOfComprehensiveIncome.comboBoxTermTwo,
								"select fy_name from fy_names");

					} catch (Exception e1) {

						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "You do not have permission to access this file");
					return;

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

	public void checkUserRightsForBalanceSheet() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + current_user.getText() + "'");

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (rs.getInt("finance package") == 1) {
					try {

						financeStatementOfFinancialPosition.setVisible(true);
						home.setLayout(new FlowLayout(FlowLayout.LEFT));
						financeStatementOfComprehensiveIncome.setVisible(false);
						financeGeneralLedger.setVisible(false);
						financeIndividualAccounts.setVisible(false);
						financeStockCard.setVisible(false);

						financeStatementOfFinancialPosition.displayBalanceSheet(
								financeStatementOfFinancialPosition.tableFinancialPositionAssets,
								"select asset_name,`" + FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
										+ "`, `" + FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
										+ "` from school_fixed_assets UNION "
										+ "SELECT 'Fees Due', (select SUM(debit-credit) from student_ledger where year='"
										+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
										+ "'), (select SUM(debit-credit) from student_ledger where year='"
										+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
										+ "') from student_ledger"
										+ " UNION select 'Receivables',CASE WHEN SUM(debit)-SUM(credit)>0 and"
										+ " date LIKE '%" + FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
										+ "%' THEN SUM(debit)-SUM(credit)"
										+ " ELSE 0 END AS Debit,CASE WHEN SUM(debit)-SUM(credit)>0 and"
										+ " date LIKE '%" + FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
										+ "%' THEN SUM(debit)-SUM(credit)"
										+ " ELSE 0 END AS Debit from accounts_balanced_entries where account_name='Debtors A/C' UNION "
										+ "SELECT 'Cash Balance', CASE WHEN SUM(debit)-SUM(credit)>0 and date LIKE '%"
										+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
										+ "%' THEN SUM(debit)-SUM(credit) ELSE 0 END AS Cash,"
										+ "CASE WHEN SUM(debit)-SUM(credit)>0 and date LIKE '%"
										+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
										+ "%' THEN SUM(debit)-SUM(credit) ELSE 0 END AS Cash from accounts_balanced_entries where account_name='Cash A/C' "
										+ "UNION SELECT 'Bank Balance', CASE WHEN SUM(debit)-SUM(credit)>0 and date LIKE '%"
										+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
										+ "%' THEN SUM(debit)-SUM(credit) ELSE 0 END AS Bank,CASE WHEN SUM(debit)-SUM(credit)>0 and date LIKE '%"
										+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
										+ "%' THEN SUM(debit)-SUM(credit) ELSE 0 END AS Cash from accounts_balanced_entries where account_name='Bank A/C'");

						financeStatementOfFinancialPosition.displayBalanceSheet(
								financeStatementOfFinancialPosition.tableFinancialPositionLiabilities,
								"select 'Payables',CASE WHEN SUM(credit)-SUM(debit)>0 and date LIKE '%"
										+ financeStatementOfFinancialPosition.yearChooserOne.getYear() + "%' "
										+ "THEN SUM(credit)-SUM(debit) ELSE 0 END AS Debit,CASE WHEN SUM(credit)-SUM(debit)>0 "
										+ "and date LIKE '%"
										+ financeStatementOfFinancialPosition.yearChooserTwo.getYear()
										+ "%' THEN SUM(credit)-SUM(debit) ELSE 0 END AS Debit from accounts_balanced_entries where account_name='Creditors A/C' UNION "
										+ "SELECT 'Prepaid Fees', CASE WHEN SUM(credit)-SUM(debit)>0 "
										+ "and date LIKE '%"
										+ financeStatementOfFinancialPosition.yearChooserOne.getYear() + "%' "
										+ "THEN SUM(credit)-SUM(debit) ELSE 0 END AS Extras,CASE WHEN SUM(credit)-SUM(debit)>0 "
										+ "and date LIKE '%"
										+ financeStatementOfFinancialPosition.yearChooserTwo.getYear() + "%' "
										+ "THEN SUM(credit)-SUM(debit) ELSE 0 END AS Extras from student_ledger where credit is not null group by account_name "
										+ "UNION SELECT 'Bank Overdraft', CASE WHEN SUM(credit)-SUM(debit)>0 and date LIKE '%"
										+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear()
										+ "%' THEN SUM(credit)-SUM(debit) ELSE 0 END AS Bank,CASE WHEN SUM(credit)-SUM(debit)>0 and date LIKE '%"
										+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
										+ "%' THEN SUM(credit)-SUM(debit) ELSE 0 END AS Cash from accounts_balanced_entries where account_name='Bank A/C'");

						financeStatementOfFinancialPosition.displayBalanceSheet(
								financeStatementOfFinancialPosition.tableFinancialPositionCapital,
								"select subcategory,sum(`"
										+ FinanceStatementOfFinancialPosition.yearChooserOne.getYear() + "`),sum(`"
										+ FinanceStatementOfFinancialPosition.yearChooserTwo.getYear()
										+ "`) from budget_expense_income_records"
										+ " where account_type='Capital' group by subcategory");
					} catch (Exception e1) {

						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "You do not have permission to access this file");
					return;

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

	public void checkUserRightsForNotes() {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"select * from application_users where username='" + current_user.getText() + "'");

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				if (rs.getInt("finance package") == 1) {
					try {

						financeNotes.setVisible(true);
						home.setLayout(new FlowLayout(FlowLayout.LEFT));
						financeNotes.displayInComboBox(financeNotes.fieldNoteCategory,
								"select catname from category where note_name is null");

						financeNotes.displayNotes(financeNotes.tableNotes,
								"select budget_expense_income_records.date,budget_expense_income_records.subcategory,SUM(budget_expense_income_records.`"
										+ financeNotes.yearChooserOne.getYear()
										+ "`), SUM(budget_expense_income_records.`"
										+ financeNotes.yearChooserTwo.getYear()
										+ "`) from budget_expense_income_records "
										+ "group by budget_expense_income_records.subcategory");

					} catch (Exception e1) {

						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "You do not have permission to access this file");
					return;

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

	public void saveToBank() {

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(
					"insert into `" + fieldBankName.getSelectedItem() + "`(account_name,debit) values('"
							+ comboBoxSponsorName.getSelectedItem() + "','" + fieldfeesPaid.getText() + "')");

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

	public void checkMessages() {

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement("select count(*) from chat_messenger where message_receiver='"
					+ current_user.getText() + "' and read_by is null");

			previous = labelNotice.getText().replaceAll("[^0-9]", "").trim();

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				labelNotice.setText("Notice " + "(" + rs.getInt(1) + ")");

				after = labelNotice.getText().replaceAll("[^0-9]", "").trim();
				

				
				
				if (Integer.parseInt(previous) < Integer.parseInt(after)) {

					NotificationAlertTone.tone(900, 150, 0.9);
					NotificationsAlerts notificationsAlerts = new NotificationsAlerts();
					notificationsAlerts.displayMessages();
				} else {

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

	public void schoolDetails() {
		String sqlViewDetails = "select school_name,school_address,school_email,school_phone,school_logo from school_details";
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pst = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(sqlViewDetails);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				headingSchoolName.setText(rs.getString(1));
				address1BoxNumber.setText(rs.getString(2));
				address2Email.setText(rs.getString(3));
				address3Telephone.setText(rs.getString(4));
				byte[] img = rs.getBytes(5);
				ImageIcon image = new ImageIcon(img);
				Image im = image.getImage();
				Image im2 = im.getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon newImage = new ImageIcon(im2);
				logo.setIcon(newImage);
			}

		} catch (Exception e1) {

			e1.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e3) {

				}
			}
		}
	}
	
	public void backUpDatabaseEvery10Minutes() {
		 LoginHelperClass helperClass=new LoginHelperClass();
   	  
   	  
     	ResultSet result;
     	String dbHost= "";
     	String dbUser = "";
     	String dbPass= "";
			try {
				result = helperClass.displayDataBaseLoginCredentials();
				if(result.next()) {
	        		
	        		 dbHost=result.getString(1);
	        		 dbUser=result.getString(2);
	        		 dbPass=result.getString(3);
	        	}
				
				String dbName="school";
				String filePath="C:\\Users\\WalterNyeko\\Desktop\\school_backup_from_dump.sql";
				
				/********************************************
				 * Execute Shell Command
				 ********************************************/
				
				String executeCmd="";
				executeCmd="C:\\xampp\\mysql\\bin\\mysqldump -u "+dbUser+" -p"+dbPass+" "+dbName+" -R -E >"+filePath;
				
				Process runtimeProcess=Runtime.getRuntime().exec(new String[] {"cmd.exe","/C",executeCmd});
				System.out.println(executeCmd);
				int processComplete=runtimeProcess.waitFor();
				if(processComplete==0) {
					System.out.println("Backup Successfully Completed");
					
					return;
				}else {
					System.out.println("Could Not Complete Backup");
				}
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("Backup Not Succesfully Completed Because: "+e1.getMessage());
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				System.out.println("Backup Not Succesfully Completed: "+e1.getMessage());
				e1.printStackTrace();
			}
	}
}
