package clarion.attendance.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import clarion.attendance.core.AttendanceLessons;
import clarion.attendance.dao.AttendanceDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import school.ui.finances.CashBookController;

public class AttendanceLessonsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Attendance Entry's controls
	 */
	private JFXPanel jfxPanelAttendanceLesson;

	private Label labelDateEntriesAttendanceLesson;
	private Label labelNameEntriesAttendanceLesson;
	private Label labelClassEntriesAttendanceLesson;
	private Label labelTimeEntriesAttendanceLesson;
	private Label labelTermEntriesAttendanceLesson;
	private Label labelSubjectEntriesAttendanceLesson;

	private DatePicker datePickerEntriesAttendanceLesson;
	private ComboBox<?> comboBoxNameEntriesAttendanceLesson;
	private ComboBox<?> comboBoxClassEntriesAttendanceLesson;
	private ComboBox<?> comboBoxSubjectEntriesAttendanceLesson;
	private TextField fieldTimeEntriesAttendanceLesson;
	private ComboBox<?> comboBoxTermEntriesAttendanceLesson;

	private GridPane gridPaneEntriesAttendanceLesson;
	private VBox vBoxEntriesAttendanceLesson;
	/*
	 * Save or Clear Fields
	 */
	private Button buttonSaveAttendanceLesson;
	private Button buttonCancelAttendanceLesson;
	private Button buttonUpdateAttendanecLesson;
	private Button buttonPickAttendanceLesson;
	private JDateChooser chooserDate;
	/*
	 * Search Fields
	 */

	private Label labelNameSearchAttendanceLesson;
	private Label labelClassSearchAttendanceLesson;
	private Label labelSubjectSearchAttendanceLesson;
	private Label labeltimeSearchAttendanceLesson;
	private Label labelTermSearchAttendanceLesson;

	private TextField fieldNameSearchAttendanceLesson;
	private TextField fieldClassSearchAttendanceLesson;
	private TextField fieldSubjectSearchAttendanceLesson;
	private TextField fieldtimeSearchAttendanceLesson;
	private TextField fieldTermSearchAttendanceLesson;

	private GridPane gridPaneSearchAttendanceLesson;
	private VBox vBoxsearchAttendanceLesson;

	// private HBox hBoxAttendanceLesson;
	private BorderPane borderPaneAttendanceLesson;
	private Scene sceneAttendanceLesson;
	private VBox vBoxTop;

	private TableView<AttendanceLessons> tableViewAttendanceLessons;

	private TableColumn<AttendanceLessons, Date> columnDate;
	private TableColumn<AttendanceLessons, String> columnTeachersName;
	private TableColumn<AttendanceLessons, String> columnClass;
	private TableColumn<AttendanceLessons, String> columnSubject;

	private TableColumn<AttendanceLessons, String> columnTime;
	private TableColumn<AttendanceLessons, String> columnTerm;

	private TableColumn<AttendanceLessons, Integer> columnSNo;

	private VBox vBoxTableAttendanceLesson;

	private Connection conn;

	private PreparedStatement pst;

	private ObservableList<Object> staffs;

	private ObservableList<Object> classes;

	private ObservableList<Object> subjects;

	private ObservableList<Object> terms;

	protected JTable tableA;

	private int attended;

	protected JComboBox boxClass;

	protected JComboBox boxSubject;

	protected JComboBox boxStaff;

	protected JTable tableB;

	protected JComboBox boxClassB;

	protected JComboBox boxSubjectB;

	protected JComboBox boxStaffB;

	protected JTextField fieldSearchClassNumber;

	protected JTextField fieldPercentageNotes;

	public AttendanceLessonsPanel() {
		// TODO Auto-generated constructor stub

		jfxPanelAttendanceLesson = new JFXPanel();
		borderPaneAttendanceLesson = new BorderPane();

		// vBoxTop = new VBox();
		// borderPaneAttendanceLesson.setTop(vBoxTop);

		tableViewAttendanceLessons = new TableView<>();
		tableViewAttendanceLessons.setTableMenuButtonVisible(true);

		columnSNo = new TableColumn<>("S/No");
		columnSNo.setCellValueFactory(new PropertyValueFactory<>("sNo"));

		columnDate = new TableColumn<>("Date");
		columnDate.setPrefWidth(160);
		columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

		columnTeachersName = new TableColumn<>("Name");
		columnTeachersName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
		columnTeachersName.setPrefWidth(240);

		columnClass = new TableColumn<>("Class");
		columnClass.setCellValueFactory(new PropertyValueFactory<>("teacherClass"));
		columnClass.setPrefWidth(150);

		columnSubject = new TableColumn<>("Subject");
		columnSubject.setCellValueFactory(new PropertyValueFactory<>("teacherSubject"));
		columnSubject.setPrefWidth(160);

		columnTime = new TableColumn<>("Time/Period");
		columnTime.setCellValueFactory(new PropertyValueFactory<>("lessonTime"));
		columnTime.setPrefWidth(250);

		columnTerm = new TableColumn<>("Term");
		columnTerm.setCellValueFactory(new PropertyValueFactory<>("term"));
		columnTerm.setPrefWidth(160);

		tableViewAttendanceLessons.getColumns().addAll(columnSNo, columnDate, columnTeachersName, columnClass,
				columnSubject, columnTime, columnTerm);

		vBoxTableAttendanceLesson = new VBox();
		vBoxTableAttendanceLesson.getChildren().addAll(tableViewAttendanceLessons);

		borderPaneAttendanceLesson.setCenter(vBoxTableAttendanceLesson);

		// hBoxAttendanceLesson = new HBox();

		/*
		 * Entries Control Start Here
		 */
		labelDateEntriesAttendanceLesson = new Label("Date:");
		labelNameEntriesAttendanceLesson = new Label("Name:");
		labelClassEntriesAttendanceLesson = new Label("Class:");
		labelSubjectEntriesAttendanceLesson = new Label("Subject:");
		labelTimeEntriesAttendanceLesson = new Label("Time/Period:");
		labelTermEntriesAttendanceLesson = new Label("Term:");

		datePickerEntriesAttendanceLesson = new DatePicker();

		staffs = FXCollections.observableArrayList();
		comboBoxNameEntriesAttendanceLesson = new ComboBox<>(staffs);
		comboBoxNameEntriesAttendanceLesson.setPromptText("Choose Name");
		displayInComboBoxStaffs(comboBoxNameEntriesAttendanceLesson, "select staff_name from table_teaching_staffs");

		classes = FXCollections.observableArrayList();
		comboBoxClassEntriesAttendanceLesson = new ComboBox<>(classes);
		comboBoxClassEntriesAttendanceLesson.setPromptText("Choose Class");
		displayInComboBoxClass(comboBoxClassEntriesAttendanceLesson, "select class_name from student_classes");

		subjects = FXCollections.observableArrayList();
		comboBoxSubjectEntriesAttendanceLesson = new ComboBox<>(subjects);
		comboBoxSubjectEntriesAttendanceLesson.setPromptText("Choose Subject");
		displayInComboBoxSubject(comboBoxSubjectEntriesAttendanceLesson,
				"select subject_name from student_subjects union select subject_name from student_subjectsa");

		fieldTimeEntriesAttendanceLesson = new TextField();
		fieldTimeEntriesAttendanceLesson.setPromptText("Lesson Period");

		terms = FXCollections.observableArrayList();
		comboBoxTermEntriesAttendanceLesson = new ComboBox<>(terms);
		comboBoxTermEntriesAttendanceLesson.setPromptText("Choose Term");
		displayInComboBoxTerms(comboBoxTermEntriesAttendanceLesson, "select term_name from student_terms");

		buttonSaveAttendanceLesson = new Button("Save");
		buttonCancelAttendanceLesson = new Button("Cancel");
		buttonUpdateAttendanecLesson = new Button("Edit");

		buttonPickAttendanceLesson = new Button("Pick selected row");

		gridPaneEntriesAttendanceLesson = new GridPane();
		vBoxEntriesAttendanceLesson = new VBox();

		/*
		 * Entries Control End Here
		 */

		/*
		 * Search Controls Start Here
		 */

		labelNameSearchAttendanceLesson = new Label("Name:");
		labelClassSearchAttendanceLesson = new Label("Class:");
		labelSubjectSearchAttendanceLesson = new Label("Subject");
		labeltimeSearchAttendanceLesson = new Label("Time/Period:");
		labelTermSearchAttendanceLesson = new Label("Term:");

		fieldNameSearchAttendanceLesson = new TextField();
		fieldNameSearchAttendanceLesson.setPromptText("Search Name");

		fieldClassSearchAttendanceLesson = new TextField();
		fieldClassSearchAttendanceLesson.setPromptText("Search Class");

		fieldSubjectSearchAttendanceLesson = new TextField();
		fieldSubjectSearchAttendanceLesson.setPromptText("Search Subject");

		fieldtimeSearchAttendanceLesson = new TextField();
		fieldtimeSearchAttendanceLesson.setPromptText("Lesson Time");

		fieldTermSearchAttendanceLesson = new TextField();
		fieldTermSearchAttendanceLesson.setPromptText("Search Term");

		gridPaneSearchAttendanceLesson = new GridPane();
		vBoxsearchAttendanceLesson = new VBox();

		/*
		 * Search Controls End Here
		 */

		/*
		 * Adding Entries controls to Holders Start Here
		 */

		gridPaneEntriesAttendanceLesson.setVgap(2);
		gridPaneEntriesAttendanceLesson.setHgap(2);

		gridPaneEntriesAttendanceLesson.setPadding(new Insets(10));

		gridPaneEntriesAttendanceLesson.add(labelDateEntriesAttendanceLesson, 0, 0);
		gridPaneEntriesAttendanceLesson.add(labelNameEntriesAttendanceLesson, 1, 0);
		gridPaneEntriesAttendanceLesson.add(labelClassEntriesAttendanceLesson, 2, 0);
		gridPaneEntriesAttendanceLesson.add(labelSubjectEntriesAttendanceLesson, 3, 0);
		gridPaneEntriesAttendanceLesson.add(labelTimeEntriesAttendanceLesson, 4, 0);
		gridPaneEntriesAttendanceLesson.add(labelTermEntriesAttendanceLesson, 5, 0);

		gridPaneEntriesAttendanceLesson.add(datePickerEntriesAttendanceLesson, 0, 1);
		gridPaneEntriesAttendanceLesson.add(comboBoxNameEntriesAttendanceLesson, 1, 1);
		gridPaneEntriesAttendanceLesson.add(comboBoxClassEntriesAttendanceLesson, 2, 1);
		gridPaneEntriesAttendanceLesson.add(comboBoxSubjectEntriesAttendanceLesson, 3, 1);
		gridPaneEntriesAttendanceLesson.add(fieldTimeEntriesAttendanceLesson, 4, 1);
		gridPaneEntriesAttendanceLesson.add(comboBoxTermEntriesAttendanceLesson, 5, 1);

		gridPaneEntriesAttendanceLesson.add(buttonSaveAttendanceLesson, 0, 2);
		gridPaneEntriesAttendanceLesson.add(buttonPickAttendanceLesson, 1, 2);
		gridPaneEntriesAttendanceLesson.add(buttonUpdateAttendanecLesson, 3, 2);
		gridPaneEntriesAttendanceLesson.add(buttonCancelAttendanceLesson, 4, 2);

		// vBoxEntriesAttendanceLesson.getChildren().addAll(gridPaneEntriesAttendanceLesson);

		/*
		 * Adding Entries controls to Holders End Here
		 */

		/*
		 * Adding Search controls to Holders Starts Here
		 */

		gridPaneSearchAttendanceLesson.setVgap(2);
		gridPaneSearchAttendanceLesson.setHgap(2);

		gridPaneSearchAttendanceLesson.setPadding(new Insets(10));

		gridPaneSearchAttendanceLesson.add(labelNameSearchAttendanceLesson, 0, 0);
		gridPaneSearchAttendanceLesson.add(labelClassSearchAttendanceLesson, 1, 0);
		gridPaneSearchAttendanceLesson.add(labelSubjectSearchAttendanceLesson, 2, 0);
		gridPaneSearchAttendanceLesson.add(labeltimeSearchAttendanceLesson, 3, 0);
		gridPaneSearchAttendanceLesson.add(labelTermSearchAttendanceLesson, 4, 0);

		gridPaneSearchAttendanceLesson.add(fieldNameSearchAttendanceLesson, 0, 1);
		gridPaneSearchAttendanceLesson.add(fieldClassSearchAttendanceLesson, 1, 1);
		gridPaneSearchAttendanceLesson.add(fieldSubjectSearchAttendanceLesson, 2, 1);
		gridPaneSearchAttendanceLesson.add(fieldtimeSearchAttendanceLesson, 3, 1);
		gridPaneSearchAttendanceLesson.add(fieldTermSearchAttendanceLesson, 4, 1);

		VBox boxButtons = new VBox(15);
		boxButtons.setStyle("-fx-border-width: 4;" + "-fx-border-color: white;" + "-fx-border-radius: 5;");
		boxButtons.setPadding(new Insets(10, 10, 10, 10));
		Button btnAttendance = new Button("Students Attendance");
		btnAttendance.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JDialog dialog = new JDialog();
				dialog.setTitle("Students Attendance");
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setSize(1200, 650);
				dialog.setLocationRelativeTo(null);
				dialog.setLayout(new BorderLayout());

				MyTableModel modelA = new MyTableModel();
				modelA.addRow(new Object[] { "1A/01", "Okeny Joel", "12:00-12:40", true });
				modelA.addRow(new Object[] { "1A/02", "Okello Jacob", "12:00-12:40", true });

				tableA = new JTable(modelA);
				tableA.setFont(new Font("Times New Roman", Font.ITALIC, 17));
				tableA.setRowHeight(30);
				tableA.getColumnModel().getColumn(0).setPreferredWidth(40);
				tableA.getColumnModel().getColumn(1).setPreferredWidth(160);
				tableA.getColumnModel().getColumn(2).setPreferredWidth(60);
				tableA.getColumnModel().getColumn(3).setPreferredWidth(50);

				JScrollPane scroller = new JScrollPane(tableA);

				JPanel panelTop = new JPanel();
				panelTop.setBackground(Color.decode("#5f9ea0"));
				panelTop.setPreferredSize(new Dimension(1000, 50));

				chooserDate = new JDateChooser();
				panelTop.add(chooserDate);
				chooserDate.setPreferredSize(new Dimension(160, 25));

				JTextField fieldPeriod = new JTextField();
				fieldPeriod.setPreferredSize(new Dimension(160, 25));
				panelTop.add(fieldPeriod);

				boxClass = new JComboBox();
				panelTop.add(boxClass);
				boxClass.setPreferredSize(new Dimension(160, 25));
				displayInJComboBoxClass(boxClass, "SELECT class_name from student_classes");

				boxSubject = new JComboBox();
				panelTop.add(boxSubject);
				boxSubject.setPreferredSize(new Dimension(160, 25));
				displayInJComboBoxSubject(boxSubject,
						"select subject_name from student_subjects UNION select subject_name from student_subjectsa");

				boxStaff = new JComboBox();
				panelTop.add(boxStaff);
				boxStaff.setPreferredSize(new Dimension(160, 25));
				displayInJComboBoxStaff(boxStaff, "select staff_name from table_teaching_staffs");

				JTextField fieldSearch = new JTextField();
				fieldSearch.setText("Search By Class No:");
				fieldSearch.setPreferredSize(new Dimension(160, 25));
				panelTop.add(fieldSearch);
				fieldSearch.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub
						Calendar cal = Calendar.getInstance();
						String four = "" + cal.getTime();
						String answer = four.substring(four.length() - 4);
						displayData(tableA,
								"select class_number,student_name,'" + fieldPeriod.getText() + "' from `"
										+ boxClass.getSelectedItem() + "` where year='" + answer
										+ "' and class_number LIKE '%" + fieldSearch.getText() + "%'");

					}

					@Override
					public void keyPressed(KeyEvent arg0) {
						// TODO Auto-generated method stub

					}
				});

				boxClass.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(java.awt.event.ActionEvent arg0) {
						// TODO Auto-generated method stub
						Calendar cal = Calendar.getInstance();
						String four = "" + cal.getTime();
						String answer = four.substring(four.length() - 4);
						displayData(tableA, "select class_number,student_name,'" + fieldPeriod.getText() + "' from `"
								+ boxClass.getSelectedItem() + "` where year='" + answer + "'");
					}
				});

				dialog.add(panelTop, BorderLayout.NORTH);

				dialog.add(scroller, BorderLayout.CENTER);

				JButton btnDone = new JButton("Save Attended");
				btnDone.setPreferredSize(new Dimension(150, 25));
				btnDone.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(java.awt.event.ActionEvent arg0) {
						// TODO Auto-generated method stub

						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								submitAttendance();
							}
						});

					}
				});

				JButton btnUpdate = new JButton("Save Unattended");
				btnUpdate.setPreferredSize(new Dimension(150, 25));
				btnUpdate.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(java.awt.event.ActionEvent arg0) {
						// TODO Auto-generated method stub

						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								submitUnattendedLessons();
							}
						});

					}
				});

				JCheckBox btnSelectAll = new JCheckBox("Select All");
				btnSelectAll.setPreferredSize(new Dimension(100, 25));
				btnSelectAll.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(java.awt.event.ActionEvent arg0) {
						// TODO Auto-generated method stub

						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (btnSelectAll.isSelected()) {
									int rows = tableA.getRowCount();
									for (int row = 0; row < rows; row++) {
										tableA.setValueAt(true, row, 3);
									}
								} else {
									int rows = tableA.getRowCount();
									for (int row = 0; row < rows; row++) {
										tableA.setValueAt(false, row, 3);
									}
								}
							}
						});

					}
				});

				JPanel panelSouth = new JPanel();
				panelSouth.setLayout(new FlowLayout());
				panelSouth.add(btnDone);
				panelSouth.add(btnSelectAll);
				panelSouth.add(btnUpdate);
				dialog.add(panelSouth, BorderLayout.SOUTH);
				panelSouth.setBackground(Color.decode("#5f9ea0"));
				// rgb(95, 158, 160)

				dialog.setVisible(true);
			}
		});
		btnAttendance.setPrefWidth(150);
		Button btnNotes = new Button("Student Notes");
		btnNotes.setPrefWidth(150);
		btnNotes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JDialog dialog = new JDialog();
				dialog.setTitle("Students Attendance");
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setSize(1200, 650);
				dialog.setLocationRelativeTo(null);
				dialog.setLayout(new BorderLayout());

				MyTableModelForNotes modelA = new MyTableModelForNotes();
				modelA.addRow(new Object[] { "1A/01", "Okeny Joel", "89" });
				modelA.addRow(new Object[] { "1A/02", "Okello Jacob", "76" });

				tableB = new JTable(modelA);
				tableB.setFont(new Font("Times New Roman", Font.ITALIC, 17));
				tableB.setRowHeight(30);
				tableB.getColumnModel().getColumn(0).setPreferredWidth(40);
				tableB.getColumnModel().getColumn(1).setPreferredWidth(160);
				tableB.getColumnModel().getColumn(2).setPreferredWidth(60);

				JScrollPane scroller = new JScrollPane(tableB);

				JPanel panelTop = new JPanel();
				panelTop.setBackground(Color.decode("#5f9ea0"));
				panelTop.setPreferredSize(new Dimension(1000, 50));

				chooserDate = new JDateChooser();
				panelTop.add(chooserDate);
				chooserDate.setPreferredSize(new Dimension(160, 25));

				fieldPercentageNotes = new JTextField("Put Notes Values(%)");
				fieldPercentageNotes.setPreferredSize(new Dimension(160, 25));
				panelTop.add(fieldPercentageNotes);

				boxClassB = new JComboBox();
				panelTop.add(boxClassB);
				boxClassB.setPreferredSize(new Dimension(160, 25));
				displayInJComboBoxClass(boxClassB, "SELECT class_name from student_classes");

				boxSubjectB = new JComboBox();
				panelTop.add(boxSubjectB);
				boxSubjectB.setPreferredSize(new Dimension(160, 25));
				displayInJComboBoxSubject(boxSubjectB,
						"select subject_name from student_subjects UNION select subject_name from student_subjectsa");

				boxStaffB = new JComboBox();
				panelTop.add(boxStaffB);
				boxStaffB.setPreferredSize(new Dimension(160, 25));
				displayInJComboBoxStaff(boxStaffB, "select staff_name from table_teaching_staffs");

				fieldSearchClassNumber = new JTextField();
				fieldSearchClassNumber.setText("Search By Class No:");
				fieldSearchClassNumber.setPreferredSize(new Dimension(160, 25));
				panelTop.add(fieldSearchClassNumber);
				fieldSearchClassNumber.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub

						displayData(tableB,
								"select class_number,student_name,student_notes from students_notes where class_number LIKE '%"
										+ fieldSearchClassNumber.getText() + "%'");

					}

					@Override
					public void keyPressed(KeyEvent arg0) {
						// TODO Auto-generated method stub

					}
				});

				boxClassB.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(java.awt.event.ActionEvent arg0) {
						// TODO Auto-generated method stub
						Calendar cal = Calendar.getInstance();
						String four = "" + cal.getTime();
						String answer = four.substring(four.length() - 4);
						displayData(tableB, "select class_number,student_name,'" + fieldPercentageNotes.getText()
								+ "' from `" + boxClassB.getSelectedItem() + "` where year='" + answer + "'");
					}
				});

				dialog.add(panelTop, BorderLayout.NORTH);

				dialog.add(scroller, BorderLayout.CENTER);

				JButton btnDone = new JButton("Save Initial Notes");
				btnDone.setPreferredSize(new Dimension(150, 25));
				btnDone.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(java.awt.event.ActionEvent arg0) {
						// TODO Auto-generated method stub

						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								saveInitialNotes();
							}
						});

					}
				});

				JButton btnUpdate = new JButton("Update Notes");
				btnUpdate.setPreferredSize(new Dimension(150, 25));
				btnUpdate.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(java.awt.event.ActionEvent arg0) {

						Platform.runLater(new Runnable() {

							@Override
							public void run() {

								updateNotes();
							}
						});

					}
				});

				JPanel panelSouth = new JPanel();
				panelSouth.setLayout(new FlowLayout());
				panelSouth.add(btnDone);
				panelSouth.add(btnUpdate);
				dialog.add(panelSouth, BorderLayout.SOUTH);
				panelSouth.setBackground(Color.decode("#5f9ea0"));

				dialog.setVisible(true);
			}
		});
		boxButtons.getChildren().addAll(btnAttendance, btnNotes);

		HBox boxUpper = new HBox(5);
		boxUpper.setAlignment(Pos.CENTER);
		boxUpper.setPadding(new Insets(10, 10, 10, 10));
		boxUpper.getChildren().addAll(gridPaneEntriesAttendanceLesson, boxButtons);

		vBoxsearchAttendanceLesson.getChildren().addAll(boxUpper, gridPaneSearchAttendanceLesson);

		datePickerEntriesAttendanceLesson.setEditable(false);

		buttonSaveAttendanceLesson.setOnAction(e -> {

			String date = datePickerEntriesAttendanceLesson.getEditor().getText();
			String teacherName = comboBoxNameEntriesAttendanceLesson.getSelectionModel().getSelectedItem().toString();
			String teacherClass = comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem().toString();
			String teacherSubject = comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					.toString();
			String lessonTime = fieldTimeEntriesAttendanceLesson.getText();
			String term = comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem().toString();

			AttendanceLessons attendanceLessons = new AttendanceLessons(date, teacherName, teacherClass, teacherSubject,
					lessonTime, term);

			try {
				AttendanceDAO attendanceDAO = new AttendanceDAO();
				attendanceDAO.addAttendance(attendanceLessons);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			refreshAttendanceTable();
			clearFields();
		});

		buttonUpdateAttendanecLesson.setOnAction(e -> {
			AttendanceLessons lessons = tableViewAttendanceLessons.getSelectionModel().getSelectedItem();

			int id = lessons.getId();

			String date = datePickerEntriesAttendanceLesson.getEditor().getText();
			String teacherName = comboBoxNameEntriesAttendanceLesson.getEditor().getText();
			String teacherClass = comboBoxClassEntriesAttendanceLesson.getEditor().getText();
			String teacherSubject = comboBoxSubjectEntriesAttendanceLesson.getEditor().getText();

			String lessonTime = fieldTimeEntriesAttendanceLesson.getText();
			String term = comboBoxTermEntriesAttendanceLesson.getEditor().getText();

			AttendanceLessons lessonsPrev = new AttendanceLessons(date, teacherName, teacherClass, teacherSubject,
					lessonTime, term);

			AttendanceLessons lessonsNew = null;

			lessonsNew = lessonsPrev;

			lessonsNew.setDate(date);
			lessonsNew.setTeacherName(teacherName);
			lessonsNew.setTeacherClass(teacherClass);
			lessonsNew.setTeacherSubject(teacherSubject);
			lessonsNew.setLessonTime(lessonTime);
			lessonsNew.setTerm(term);

			try {
				AttendanceDAO dao = new AttendanceDAO();
				dao.editAttendance(lessonsNew, id);

				System.out.println(lessonsNew);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			refreshAttendanceTable();

		});

		buttonPickAttendanceLesson.setOnAction(e -> {

			AttendanceLessons lessons = tableViewAttendanceLessons.getSelectionModel().getSelectedItem();

			datePickerEntriesAttendanceLesson.getEditor().setText(lessons.getDate().toString());

			comboBoxNameEntriesAttendanceLesson.setEditable(true);
			comboBoxNameEntriesAttendanceLesson.getEditor().setText(lessons.getTeacherName());

			comboBoxClassEntriesAttendanceLesson.setEditable(true);
			comboBoxClassEntriesAttendanceLesson.getEditor().setText(lessons.getTeacherClass());

			comboBoxSubjectEntriesAttendanceLesson.setEditable(true);
			comboBoxSubjectEntriesAttendanceLesson.getEditor().setText(lessons.getTeacherSubject());

			fieldTimeEntriesAttendanceLesson.setText(lessons.getLessonTime());

			comboBoxTermEntriesAttendanceLesson.setEditable(true);
			comboBoxTermEntriesAttendanceLesson.getEditor().setText(lessons.getTerm());

		});

		buttonCancelAttendanceLesson.setOnAction(e -> {
			clearFields();
			comboBoxClassEntriesAttendanceLesson.setEditable(false);
			comboBoxNameEntriesAttendanceLesson.setEditable(false);
			comboBoxSubjectEntriesAttendanceLesson.setEditable(false);
			comboBoxTermEntriesAttendanceLesson.setEditable(false);
		});

		// search by teacher name only
		fieldNameSearchAttendanceLesson.setOnKeyReleased(e -> {
			String name = fieldNameSearchAttendanceLesson.getText();
			searchName(name);
		});

		// search by class the teacher teaches
		fieldClassSearchAttendanceLesson.setOnKeyReleased(e -> {
			String clas = fieldClassSearchAttendanceLesson.getText();
			searchClass(clas);
		});

		// search by subject the teacher teaches
		fieldSubjectSearchAttendanceLesson.setOnKeyReleased(e -> {
			String subject = fieldSubjectSearchAttendanceLesson.getText();
			searchSubject(subject);
		});

		// search by time/period of teaching
		fieldtimeSearchAttendanceLesson.setOnKeyReleased(e -> {
			String time = fieldtimeSearchAttendanceLesson.getText();
			searchTime(time);
		});

		// search by the term of teaching
		fieldTermSearchAttendanceLesson.setOnKeyReleased(e -> {
			String term = fieldTermSearchAttendanceLesson.getText();
			searchTerm(term);
		});

		refreshAttendanceTable();

		tableViewAttendanceLessons.setId("table_attendance");

		borderPaneAttendanceLesson.setTop(vBoxsearchAttendanceLesson);
		// borderPaneAttendanceLesson.setRight(vBoxsearchAttendanceLesson);

		sceneAttendanceLesson = new Scene(borderPaneAttendanceLesson);

		String styleSheet = getClass().getResource("attendance.css").toExternalForm();
		sceneAttendanceLesson.getStylesheets().add(styleSheet);

		jfxPanelAttendanceLesson.setScene(sceneAttendanceLesson);
		jfxPanelAttendanceLesson.setPreferredSize(new Dimension(1160, 480));

		this.add(jfxPanelAttendanceLesson);
	}

	private void refreshAttendanceTable() {

		ObservableList<AttendanceLessons> lessons = null;

		try {
			AttendanceDAO daoRefresh = new AttendanceDAO();
			lessons = daoRefresh.getAllAttendanceLesson();
		} catch (Exception e) {
			e.printStackTrace();
		}

		int i = 1;
		for (AttendanceLessons attendanceLessons : lessons) {
			int sn = i++;
			attendanceLessons.setsNo(sn);

		}

		tableViewAttendanceLessons.itemsProperty().setValue(lessons);
	}

	private void searchName(String name) {

		ObservableList<AttendanceLessons> lessons = null;

		try {
			AttendanceDAO daoRefresh = new AttendanceDAO();
			lessons = daoRefresh.SearchTeacherName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}

		tableViewAttendanceLessons.itemsProperty().setValue(lessons);
	}

	private void searchSubject(String subject) {

		ObservableList<AttendanceLessons> lessons = null;

		try {
			AttendanceDAO daoRefresh = new AttendanceDAO();
			lessons = daoRefresh.SearchTeacherSubject(subject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		tableViewAttendanceLessons.itemsProperty().setValue(lessons);
	}

	private void searchTime(String time) {

		ObservableList<AttendanceLessons> lessons = null;

		try {
			AttendanceDAO daoRefresh = new AttendanceDAO();
			lessons = daoRefresh.SearchTeacherTime(time);
		} catch (Exception e) {
			e.printStackTrace();
		}

		tableViewAttendanceLessons.itemsProperty().setValue(lessons);
	}

	private void searchTerm(String term) {

		ObservableList<AttendanceLessons> lessons = null;

		try {
			AttendanceDAO daoRefresh = new AttendanceDAO();
			lessons = daoRefresh.SearchTeacherTerm(term);
		} catch (Exception e) {
			e.printStackTrace();
		}

		tableViewAttendanceLessons.itemsProperty().setValue(lessons);
	}

	private void searchClass(String clas) {

		ObservableList<AttendanceLessons> lessons = null;

		try {
			AttendanceDAO daoRefresh = new AttendanceDAO();
			lessons = daoRefresh.SearchTeacherClass(clas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		tableViewAttendanceLessons.itemsProperty().setValue(lessons);
	}

	private void clearFields() {
		datePickerEntriesAttendanceLesson.getEditor().clear();
		comboBoxNameEntriesAttendanceLesson.getEditor().clear();
		comboBoxClassEntriesAttendanceLesson.getEditor().clear();
		comboBoxSubjectEntriesAttendanceLesson.getEditor().clear();
		fieldTimeEntriesAttendanceLesson.clear();
		comboBoxTermEntriesAttendanceLesson.getEditor().clear();
	}

	public void displayInComboBoxStaffs(ComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				staffs.add(rs.getString(1));
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

	public void displayInComboBoxClass(ComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				classes.add(rs.getString(1));
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

	public void displayInJComboBoxClass(JComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			combo.removeAllItems();
			combo.addItem("Choose Class");
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

	public void displayInJComboBoxStaff(JComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			combo.removeAllItems();
			combo.addItem("Choose Staff");
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

	public void displayInJComboBoxSubject(JComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			combo.removeAllItems();
			combo.addItem("Choose Subject");
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

	public void displayInComboBoxSubject(ComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				subjects.add(rs.getString(1));
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

	public void displayInComboBoxTerms(ComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				terms.add(rs.getString(1));
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

	public class MyTableModel extends DefaultTableModel {

		public MyTableModel() {
			super(new String[] { "Class Number", "Student Name", "Time/Period", "Attended" }, 0);
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			Class clazz = String.class;
			switch (columnIndex) {
			case 0:
				clazz = String.class;
				break;
			case 1:
				clazz = String.class;
				break;

			case 2:
				clazz = String.class;
				break;
			case 3:
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
			if (aValue instanceof Boolean && column == 3) {

				if (aValue != null) {

					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							if (Boolean.TRUE.equals(aValue)) {

							} else {

							}

						}
					});

				}

				Vector rowData = (Vector) getDataVector().get(row);
				rowData.set(3, (boolean) aValue);
				fireTableCellUpdated(row, column);

			}
		}

		@Override
		public Object getValueAt(int row, int column) {

			return super.getValueAt(row, column);
		}

	}

	public class MyTableModelForNotes extends DefaultTableModel {

		public MyTableModelForNotes() {
			super(new String[] { "Class Number", "Student Name", "Percentage Available Notes" }, 0);
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			Class clazz = String.class;
			switch (columnIndex) {
			case 0:
				clazz = String.class;
				break;
			case 1:
				clazz = String.class;
				break;

			case 2:
				clazz = String.class;
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
			if (aValue instanceof Boolean && column == 2) {

				if (aValue != null) {

					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							if (Boolean.TRUE.equals(aValue)) {

							} else {

							}

						}
					});

				}

				Vector rowData = (Vector) getDataVector().get(row);
				rowData.set(2, (boolean) aValue);
				fireTableCellUpdated(row, column);

			}
		}

		@Override
		public Object getValueAt(int row, int column) {

			return super.getValueAt(row, column);
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

	public void submitAttendance() {

		Connection conn = null;
		PreparedStatement pst = null;

		try {

			conn = CashBookController.getConnection();

			int rows = tableA.getRowCount();
			for (int eachRow = 0; eachRow < rows; eachRow++) {
				String classNumber = tableA.getValueAt(eachRow, 0).toString();
				String studentName = tableA.getValueAt(eachRow, 1).toString();
				String timePeriod = tableA.getValueAt(eachRow, 2).toString();
				Boolean aValue = (Boolean) tableA.getValueAt(eachRow, 3);

				if (Boolean.TRUE.equals(aValue)) {

					attended = 1;
				} else {
					attended = 0;
				}

				pst = conn.prepareStatement(
						"insert into `Students Attendance`(`Class Number`,`Student Name`,`Time Period`,`Date`,`Staff Name`,`Attended`,`Subject`,`Students Class`)"
								+ " VALUES(?,?,?,'" + convertFromUtilToSQLDate(chooserDate.getDate()) + "',?,?,?,?)");
				pst.setString(1, classNumber);
				pst.setString(2, studentName);
				pst.setString(3, timePeriod);
				pst.setString(4, boxStaff.getSelectedItem().toString());
				pst.setInt(5, attended);
				pst.setString(6, boxSubject.getSelectedItem().toString());
				pst.setString(7, boxClass.getSelectedItem().toString());
				pst.executeUpdate();
			}

			JOptionPane.showMessageDialog(null, "Records Submitted Successfully");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Records Not Submitted Successfully");
		}

	}

	public void submitUnattendedLessons() {

		Connection conn = null;
		PreparedStatement pst = null;

		try {

			conn = CashBookController.getConnection();

			int rows = tableA.getRowCount();
			for (int eachRow = 0; eachRow < rows; eachRow++) {
				String classNumber = tableA.getValueAt(eachRow, 0).toString();
				String studentName = tableA.getValueAt(eachRow, 1).toString();
				String timePeriod = tableA.getValueAt(eachRow, 2).toString();
				Boolean aValue = (Boolean) tableA.getValueAt(eachRow, 3);

				if (Boolean.TRUE.equals(aValue)) {

					attended = 1;
				} else {
					attended = 0;
				}

				pst = conn.prepareStatement(
						"UPDATE `Students Attendance` SET `Class Number`=?,`Student Name`=?,`Time Period`=?,`Date`='"
								+ convertFromUtilToSQLDate(chooserDate.getDate())
								+ "',`Staff Name`=?,`Attended`=?,`Subject`=?,`Students Class`=? WHERE `Class Number`=? AND `Date`='"
								+ convertFromUtilToSQLDate(chooserDate.getDate()) + "' AND `Time Period`=?");
				pst.setString(1, classNumber);
				pst.setString(2, studentName);
				pst.setString(3, timePeriod);
				pst.setString(4, boxStaff.getSelectedItem().toString());
				pst.setInt(5, attended);
				pst.setString(6, boxSubject.getSelectedItem().toString());
				pst.setString(7, boxClass.getSelectedItem().toString());
				pst.setString(8, classNumber);
				pst.setString(9, timePeriod);
				pst.executeUpdate();
			}

			JOptionPane.showMessageDialog(null, "Records Submitted Successfully");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Records Not Submitted Successfully");
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

	public void saveInitialNotes() {
		Connection conn=null;
		PreparedStatement pst=null;
		try {
			
			conn=CashBookController.getConnection();
			
			int rows=tableB.getRowCount();
			for(int eachRow=0; eachRow<rows;eachRow++) {
				
				String classNumber=tableB.getValueAt(eachRow, 0).toString();
				String studentName=tableB.getValueAt(eachRow, 1).toString();
				String studentNotes=tableB.getValueAt(eachRow, 2).toString();
				
				pst=conn.prepareStatement("insert into students_notes"
						+ "(class_number,student_name,student_notes,subject,staff_name) values"
						+ "('"+classNumber+"','"+studentName+"','"+studentNotes+"','"+boxSubjectB.getSelectedItem()+"','"+boxStaffB.getSelectedItem()+"')");
				pst.executeUpdate();
			}
			
			JOptionPane.showMessageDialog(null, "Initial Notes Recorded Successfully");
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	
	public void updateNotes() {
		Connection conn=null;
		PreparedStatement pst=null;
		try {
			
			conn=CashBookController.getConnection();
			
			int rows=tableB.getRowCount();
			for(int eachRow=0; eachRow<rows;eachRow++) {
				
				String classNumber=tableB.getValueAt(eachRow, 0).toString();
				String studentName=tableB.getValueAt(eachRow, 1).toString();
				String studentNotes=tableB.getValueAt(eachRow, 2).toString();
				String SQL="update students_notes SET student_notes='"+fieldPercentageNotes.getText()+"',"
						+ "subject='"+boxSubjectB.getSelectedItem()+"' WHERE class_number='"+classNumber+"'";
				pst=conn.prepareStatement(SQL);
				
				pst.executeUpdate();
				System.out.println(SQL);
				
				
			}
			
			JOptionPane.showMessageDialog(null, "Student Notes Updated Successfully");
			
			
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Student Notes Not Updated Successfully"+e.getMessage());
			e.printStackTrace();
		}
	}

}
