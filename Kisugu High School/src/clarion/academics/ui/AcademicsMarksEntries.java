package clarion.academics.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
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

import com.toedter.calendar.JYearChooser;

import clarion.attendance.core.AttendanceLessons;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import school.ui.finances.CashBookController;

public class AcademicsMarksEntries extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//////////////////Progress Bar Stuffs//////////////////
final Float[] values = new Float[] { -2f };
final Label[] labels = new Label[values.length];
final ProgressBar[] pbs = new ProgressBar[values.length];
final ProgressIndicator[] pins = new ProgressIndicator[values.length];
final HBox hbs[] = new HBox[values.length];

	/*
	 * Attendance Entry's controls
	 */
	private JFXPanel jfxPanelAttendanceLesson;
	private TableView tableViewGrades;
	private ComboBox comboBoxClass;
	private ComboBox comboBoxTest;
	private ComboBox comboBoxSubject;
	private ObservableList<Object> dataClass;
	private ObservableList<Object> dataSubject;
	private ObservableList<Object> dataTestName;

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

	private Label labelTermSearchAttendanceLessonEOT;

	private TextField fieldTermSearchAttendanceLessonEOT;

	private HBox hboxHomeMain;

	private VBox vboxLabelPicture;

	private Label labelPictureStudent;

	private VBox vboxLabelPictureReal;

	private TableColumn columnClassGrade;

	private TableColumn columnSubjectGrade;

	private TableColumn columnTimeGrade;

	private TableColumn columnTermGrade;

	private TableColumn columnTermGradeAgg;

	protected JFXPanel jfxPanel;

	protected Stage stage;

	private Image image;

	private ImageView finalViewedImage;

	private ObservableList<Object> data;

	private TableView tableview;

	private ObservableList<Object> dataGrades;

	protected TreeView tree;

	private Connection cnx;

	private PreparedStatement ps;

	private ResultSet rs;

	private Connection con;

	private Statement stm;

	HashMap<Integer, Tests> node = new HashMap<>(); // for child nodes
	HashMap<Integer, Tests> pere = new HashMap<>(); // for parent nodes
	Tests c; // object from Test class

	protected ObservableList<Object> testsCats;

	protected ObservableList<Object> tests;

	private int parentNode;

	private int codeNode;

	private Label labelPaymentCode;

	private Button buttonDeleteMarks;

	private ObservableList<Object> papers;

	private ComboBox comboPaper;

	private ResultSet result;

	private ObservableList<Object> dataGrades1;

	private ObservableList<Object> dataGrades2;

	private ObservableList<Object> dataGrades3;

	private Button buttonExternalMarks;

	protected JFileChooser fileChooser;

	protected JTable tableData;

	protected Stage primaryStage;

	public AcademicsMarksEntries() {
		// TODO Auto-generated constructor stub

		jfxPanelAttendanceLesson = new JFXPanel();
		borderPaneAttendanceLesson = new BorderPane();

		// vBoxTop = new VBox();
		// borderPaneAttendanceLesson.setTop(vBoxTop);

		vBoxTableAttendanceLesson = new VBox();

		borderPaneAttendanceLesson.setCenter(vBoxTableAttendanceLesson);

		// hBoxAttendanceLesson = new HBox();

		/*
		 * Entries Control Start Here
		 */
		labelNameEntriesAttendanceLesson = new Label("Staff Name:");
		labelClassEntriesAttendanceLesson = new Label("Student Class:");
		labelSubjectEntriesAttendanceLesson = new Label("Subject:");
		labelTimeEntriesAttendanceLesson = new Label("Class Number:");
		labelTermEntriesAttendanceLesson = new Label("Student Tests:");
		labelPaymentCode = new Label("Payment Code");

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

		papers = FXCollections.observableArrayList("1", "2", "3");
		comboPaper = new ComboBox<>(papers);
		comboPaper.setPromptText("Choose Paper");
		comboPaper.setVisible(false);

		fieldTimeEntriesAttendanceLesson = new TextField();
		fieldTimeEntriesAttendanceLesson.setPromptText("Class Number");

		terms = FXCollections.observableArrayList();
		comboBoxTermEntriesAttendanceLesson = new ComboBox<>(terms);
		comboBoxTermEntriesAttendanceLesson.setPromptText("Choose Test");
		displayInComboBoxTerms(comboBoxTermEntriesAttendanceLesson, "select test_name from students_tests");

		comboBoxSubjectEntriesAttendanceLesson.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				buildDataLater();
				if (comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedIndex() >= 18) {
					comboPaper.setVisible(true);
				} else {
					comboPaper.setVisible(false);
				}

			}
		});

		comboBoxClassEntriesAttendanceLesson.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				buildDataLater();

			}
		});

		comboBoxNameEntriesAttendanceLesson.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				buildDataLater();

			}
		});

		comboBoxTermEntriesAttendanceLesson.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				buildDataLater();

			}
		});

		comboPaper.setOnAction(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				buildDataLaterWithPaper();
			}
		});

		buttonSaveAttendanceLesson = new Button("Add Test ");
		buttonCancelAttendanceLesson = new Button("Set Test's Grades");
		buttonCancelAttendanceLesson.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				Platform.runLater(new Runnable() {

					private Stage stage;
					private ObservableList<String> searchItems;

					@Override
					public void run() {
						// TODO Auto-generated method stub

						TitledPane paneTable = new TitledPane();

						GridPane GpaneTable = new GridPane();
						GpaneTable.setVgap(10);
						GpaneTable.setHgap(10);
						GpaneTable.setPadding(new Insets(10, 10, 10, 10));

						paneTable.setText("Search Results");
						paneTable.setPadding(new Insets(10, 10, 10, 10));

						tableViewGrades = new TableView();
						saveAndShowGrades();

						dataClass = FXCollections.observableArrayList();
						comboBoxClass = new ComboBox(dataClass);
						displayInComboBoxClassGrades(comboBoxClass, "select class_name from student_classes");

						dataSubject = FXCollections.observableArrayList();
						comboBoxSubject = new ComboBox(dataSubject);
						displayInComboBoxSubjectGrades(comboBoxSubject,
								"select subject_name from student_subjects UNION select subject_name from student_subjectsa");

						dataTestName = FXCollections.observableArrayList();
						comboBoxTest = new ComboBox(dataTestName);
						displayInComboBoxTestName(comboBoxTest, "select test_name from students_tests");

						TextField fieldFrom = new TextField();
						TextField fieldTo = new TextField();
						TextField fieldGrade = new TextField();

						GridPane paneCombos = new GridPane();
						paneCombos.setVgap(15);
						paneCombos.setHgap(5);
						paneCombos.setAlignment(Pos.CENTER);

						comboBoxClass.setPromptText("Choose Class");
						comboBoxSubject.setPromptText("Choose Subject");
						comboBoxTest.setPromptText("Choose Test");

						comboBoxClass.setOnAction(new EventHandler<Event>() {

							@Override
							public void handle(Event arg0) {
								// TODO Auto-generated method stub

								saveAndShowGradesClass();
							}
						});

						comboBoxSubject.setOnAction(new EventHandler<Event>() {

							@Override
							public void handle(Event arg0) {
								// TODO Auto-generated method stub

								saveAndShowGradesSubject();
							}
						});

						comboBoxTest.setOnAction(new EventHandler<Event>() {

							@Override
							public void handle(Event arg0) {
								// TODO Auto-generated method stub

								saveAndShowGradesTest();
							}
						});

						fieldFrom.setPromptText("From:");
						fieldTo.setPromptText("To:");
						fieldGrade.setPromptText("Grade:");

						Button submit = new Button("Save");
						submit.setDefaultButton(true);
						submit.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent arg0) {
								// TODO Auto-generated method stub

								AddUpdateDeleteSilently(
										"insert into students_grading(`Student Class`,`Subject`,`Test Name`,`From (%)`,`To (%)`,`Grade`)"
												+ " VALUES('" + comboBoxClass.getSelectionModel().getSelectedItem()
												+ "','" + comboBoxSubject.getSelectionModel().getSelectedItem() + "',"
												+ "'" + comboBoxTest.getSelectionModel().getSelectedItem() + "','"
												+ fieldFrom.getText() + "','" + fieldTo.getText() + "','"
												+ fieldGrade.getText() + "')");
								fieldFrom.setText("");
								fieldTo.setText(null);
								fieldGrade.setText(null);
								saveAndShowGradesTest();
							}
						});

						paneCombos.add(comboBoxClass, 0, 0);
						paneCombos.add(comboBoxSubject, 1, 0);
						paneCombos.add(comboBoxTest, 2, 0);

						paneCombos.add(fieldFrom, 0, 1);
						paneCombos.add(fieldTo, 1, 1);
						paneCombos.add(fieldGrade, 2, 1);

						paneCombos.add(submit, 2, 2);

						VBox vboxHome = new VBox(5);

						vboxHome.getChildren().addAll(paneCombos, tableViewGrades);

						paneTable.setContent(vboxHome);

						VBox box = new VBox();
						box.getChildren().addAll(paneTable);
						final Scene scene = new Scene(box);

						String styleSheet = getClass().getResource("attendance.css").toExternalForm();
						scene.getStylesheets().add(styleSheet);

						Stage stage = new Stage();
						stage = new Stage();
						stage.setScene(scene);
						stage.setTitle("Set Grades");
						stage.setFullScreenExitHint("");
						stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
						stage.setFullScreen(false);
						stage.show();

					}
				});
			}
		});

		gridPaneEntriesAttendanceLesson = new GridPane();

		/*
		 * Entries Control End Here
		 */

		/*
		 * Search Controls Start Here
		 */

		labelNameSearchAttendanceLesson = new Label("Student Name");

		fieldtimeSearchAttendanceLesson = new TextField();
		fieldtimeSearchAttendanceLesson.setPromptText("Test Mark");

		gridPaneSearchAttendanceLesson = new GridPane();
		vBoxsearchAttendanceLesson = new VBox();

		tableview = new TableView();
		vBoxTableAttendanceLesson.getChildren().addAll(tableview);

		buildData();

		/*
		 * Search Controls End Here
		 */

		/*
		 * Adding Entries controls to Holders Start Here
		 */

		gridPaneEntriesAttendanceLesson.setVgap(15);
		gridPaneEntriesAttendanceLesson.setHgap(15);

		gridPaneEntriesAttendanceLesson.setPadding(new Insets(10));

		gridPaneEntriesAttendanceLesson.add(labelNameEntriesAttendanceLesson, 1, 0);
		gridPaneEntriesAttendanceLesson.add(labelClassEntriesAttendanceLesson, 2, 0);
		gridPaneEntriesAttendanceLesson.add(labelSubjectEntriesAttendanceLesson, 3, 0);
		gridPaneEntriesAttendanceLesson.add(labelTimeEntriesAttendanceLesson, 0, 0);
		gridPaneEntriesAttendanceLesson.add(labelTermEntriesAttendanceLesson, 4, 0);

		gridPaneEntriesAttendanceLesson.add(comboBoxNameEntriesAttendanceLesson, 1, 1);
		gridPaneEntriesAttendanceLesson.add(comboBoxClassEntriesAttendanceLesson, 2, 1);
		gridPaneEntriesAttendanceLesson.add(comboBoxSubjectEntriesAttendanceLesson, 3, 1);
		gridPaneEntriesAttendanceLesson.add(fieldTimeEntriesAttendanceLesson, 0, 1);
		gridPaneEntriesAttendanceLesson.add(comboBoxTermEntriesAttendanceLesson, 4, 1);
		gridPaneEntriesAttendanceLesson.add(comboPaper, 3, 2);
		gridPaneEntriesAttendanceLesson.add(buttonSaveAttendanceLesson, 0, 2);
		gridPaneEntriesAttendanceLesson.add(buttonCancelAttendanceLesson, 4, 2);

		buttonSaveAttendanceLesson.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				GridPane pane = new GridPane();
				pane.setVgap(20);
				pane.setHgap(10);
				pane.setAlignment(Pos.BASELINE_CENTER);

				Label labelTestName = new Label("Test Name:");
				TextField fieldTestName = new TextField();
				testsCats = FXCollections.observableArrayList();
				ComboBox boxTests = new ComboBox(testsCats);
				boxTests.setPromptText("Choose Category");
				displayInComboBoxTestsCategories(boxTests, "select catname from tests_categories");

				tests = FXCollections.observableArrayList();
				ComboBox comboSubject = new ComboBox(tests);
				comboSubject.setPromptText("Choose Subject");
				displayInComboBoxTests(comboSubject,
						"select subject_name from student_subjects union select subject_name from student_subjectsa");

				Button btnAddSubject = new Button("Add This Subject");
				btnAddSubject.setPrefWidth(150);
				Button btnAdd = new Button("Add Test Category");
				btnAdd.setPrefWidth(150);
				Button btnExit = new Button("Exit");
				btnExit.setPrefWidth(150);
				btnExit.setOnAction(event -> {
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.initStyle(StageStyle.TRANSPARENT);
					alert.initModality(Modality.WINDOW_MODAL);
					alert.setContentText("Are you sure to exit adding test?");
					alert.setHeaderText("Confirm Exit Of Adding Tests");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {

						stage.hide();
					}
				});

				btnAdd.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						AddUpdateDelete(
								"insert into tests_categories (catname) values('" + fieldTestName.getText() + "')");
						fillTree();

					}
				});

				btnAddSubject.setOnAction(new EventHandler<ActionEvent>() {

					private String sqlSubject;
					private String class_name;

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub

						String cat = boxTests.getSelectionModel().getSelectedItem().toString();
						String sub = comboSubject.getSelectionModel().getSelectedItem().toString();
						String finalName = cat + " " + sub;

						AddUpdateDelete("insert into students_tests (catid,test_name) select id,'" + finalName
								+ "' from tests_categories where catname='"
								+ boxTests.getSelectionModel().getSelectedItem() + "'");

						if (comboSubject.getSelectionModel().getSelectedIndex() < 18) {
							ResultSet resultSubject;
							try {

								sqlSubject = "select class_name from student_classes where id<5";

								resultSubject = generateCellValue(sqlSubject);

								while (resultSubject.next()) {

									class_name = resultSubject.getString(1);

									AddUpdateDeleteSilently(
											"insert into students_grading(`From (%)`,`To (%)`,`Grade`,`Subject`,`Test Name`,`Student Class`) values(80.0,100.0,'D1','"
													+ sub + "','" + finalName + "','" + class_name
													+ "'),(75.0,79.0,'D2','" + sub + "','" + finalName + "','"
													+ class_name + "'),(65.0,74.0,'C3','" + sub + "','" + finalName
													+ "','" + class_name + "'),(60,64,'C4','" + sub + "','" + finalName
													+ "','" + class_name + "'),(55,59,'C5','" + sub + "','" + finalName
													+ "','" + class_name + "'),(50,54,'C6','" + sub + "','" + finalName
													+ "','" + class_name + "'),(45,49,'P7','" + sub + "','" + finalName
													+ "','" + class_name + "'),(40,44,'P8','" + sub + "','" + finalName
													+ "','" + class_name + "'),(0,39,'F9','" + sub + "','" + finalName
													+ "','" + class_name + "')");
								}

							} catch (Exception e) {
								// TODO: handle exception
							}

						} else {
							ResultSet resultSubject;
							try {

								sqlSubject = "select class_name from student_classes where id>4";

								resultSubject = generateCellValue(sqlSubject);

								while (resultSubject.next()) {

									class_name = resultSubject.getString(1);

									AddUpdateDeleteSilently(
											"insert into students_grading(`From (%)`,`To (%)`,`Grade`,`Subject`,`Test Name`,`Student Class`) values(80.0,100.0,'D1','"
													+ sub + "','" + finalName + "','" + class_name
													+ "'),(75.0,79.0,'D2','" + sub + "','" + finalName + "','"
													+ class_name + "'),(65.0,74.0,'C3','" + sub + "','" + finalName
													+ "','" + class_name + "'),(60,64,'C4','" + sub + "','" + finalName
													+ "','" + class_name + "'),(55,59,'C5','" + sub + "','" + finalName
													+ "','" + class_name + "'),(50,54,'C6','" + sub + "','" + finalName
													+ "','" + class_name + "'),(45,49,'P7','" + sub + "','" + finalName
													+ "','" + class_name + "'),(40,44,'P8','" + sub + "','" + finalName
													+ "','" + class_name + "'),(0,39,'F9','" + sub + "','" + finalName
													+ "','" + class_name + "')");
								}

							} catch (Exception e) {
								// TODO: handle exception
							}

						}

						fillTree();

					}
				});

				pane.add(labelTestName, 0, 0);
				pane.add(fieldTestName, 1, 0);
				pane.add(boxTests, 0, 1);
				pane.add(comboSubject, 1, 1);
				pane.add(btnAddSubject, 2, 1);
				pane.add(btnAdd, 2, 0);
				pane.add(btnExit, 2, 2);

				pane.setPadding(new Insets(10, 10, 10, 10));

				tree = new TreeView();
				tree.setStyle("-fx-font-size: 18");
				// fillTree();
				comboSubject.setMinWidth(150);
				boxTests.setMinWidth(150);
				VBox box = new VBox();
				box.getChildren().addAll(pane, tree);
				final Scene scene = new Scene(box);
				scene.setFill(null);
				stage = new Stage();
				stage.setMinWidth(350);
				stage.setMinHeight(550);
				stage.setScene(scene);
				stage.setTitle("Add Tests");
				stage.setFullScreenExitHint("");
				stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
				stage.setFullScreen(false);
				stage.show();

			}
		});

		/*
		 * Adding Entries controls to Holders End Here
		 */

		/*
		 * Adding Search controls to Holders Starts Here
		 */

		buttonUpdateAttendanecLesson = new Button("Save Student's Marks");
		buttonUpdateAttendanecLesson.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedIndex() <= 17) {
					AddUpdateDeleteStudentsData(
							"insert into students_marks(`Class Number`,`Student Class`,`Student Name`,`Student Subject`,"
									+ "`Staff Name`,`Test Name`,`Marks Obtained`) values" + "('"
									+ fieldTimeEntriesAttendanceLesson.getText() + "'," + "'"
									+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "',"
									+ "'" + labelNameSearchAttendanceLesson.getText() + "','"
									+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
									+ "','" + comboBoxNameEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
									+ "','" + comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
									+ "','" + fieldtimeSearchAttendanceLesson.getText() + "')");
					buildDataLater();
				} else {
					AddUpdateDeleteStudentsData(
							"insert into students_marks(`Class Number`,`Student Class`,`Student Name`,`Student Subject`,"
									+ "`Staff Name`,`Test Name`,`Paper`,`Marks Obtained`) values" + "('"
									+ fieldTimeEntriesAttendanceLesson.getText() + "'," + "'"
									+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "',"
									+ "'" + labelNameSearchAttendanceLesson.getText() + "','"
									+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
									+ "','" + comboBoxNameEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
									+ "','" + comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
									+ "','" + comboPaper.getSelectionModel().getSelectedItem() + "','"
									+ fieldtimeSearchAttendanceLesson.getText() + "')");
					System.out.println(comboPaper.getSelectionModel().getSelectedItem());
					buildDataLaterWithPaper();
				}
			}
		});
		buttonPickAttendanceLesson = new Button("Edit Student's Marks");
		buttonDeleteMarks = new Button("Delete Record");
		buttonExternalMarks = new Button("External Marks");

		gridPaneSearchAttendanceLesson.setVgap(5);
		gridPaneSearchAttendanceLesson.setHgap(20);

		gridPaneSearchAttendanceLesson.setPadding(new Insets(10));

		gridPaneSearchAttendanceLesson.add(labelNameSearchAttendanceLesson, 0, 0);
		gridPaneSearchAttendanceLesson.add(labelPaymentCode, 1, 0);

		gridPaneSearchAttendanceLesson.add(fieldtimeSearchAttendanceLesson, 2, 0);

		gridPaneSearchAttendanceLesson.add(buttonUpdateAttendanecLesson, 3, 0);

		gridPaneSearchAttendanceLesson.add(buttonPickAttendanceLesson, 4, 0);

		gridPaneSearchAttendanceLesson.add(buttonDeleteMarks, 5, 0);
		gridPaneSearchAttendanceLesson.add(buttonExternalMarks, 6, 0);

		buttonExternalMarks.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				javafx.scene.web.WebView webview = new javafx.scene.web.WebView();
				Button btn1 = new Button("Upload Student Marks");
				Button btn2 = new Button("Generate Marks Sheet");

				VBox box = new VBox(5);
				box.setAlignment(Pos.CENTER);

				HBox boxBtns = new HBox(5);
				boxBtns.setAlignment(Pos.CENTER);
				boxBtns.getChildren().addAll(btn1, btn2);
				box.getChildren().addAll(boxBtns, webview);

				WebEngine engine = webview.getEngine();
				btn1.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						engine.load(
								"http://localhost/phpmyadmin/tbl_import.php?db=kisugu&table=students_marks&token=7962adf44c5551716cae97ac0f0844d5");
					}
				});

				btn2.setOnAction(new EventHandler<ActionEvent>() {

					private JScrollPane scrollerData;

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub

						primaryStage.hide();

						JFrame dialog = new JFrame();
						dialog.setTitle("External Mark Sheet Generator");
						dialog.setSize(1200, 600);
						dialog.setLocationRelativeTo(null);
						dialog.setLayout(new BorderLayout(10, 10));

						String[] papers = { "Choose Paper", "N/A", "1", "2", "3" };

						JComboBox comboClass = new JComboBox();
						JComboBox comboSubject = new JComboBox();
						JComboBox comboStaff = new JComboBox();
						JComboBox comboPaper = new JComboBox(papers);
						JComboBox comboTest = new JComboBox();
						JYearChooser yearChooser = new JYearChooser();
						yearChooser.getYear();

						comboClass.setPreferredSize(new Dimension(120, 20));
						comboSubject.setPreferredSize(new Dimension(180, 20));
						comboStaff.setPreferredSize(new Dimension(120, 20));
						comboPaper.setPreferredSize(new Dimension(120, 20));
						yearChooser.setPreferredSize(new Dimension(120, 20));
						comboTest.setPreferredSize(new Dimension(220, 20));

						displayInComboBox(comboClass, "Select class_name from student_classes");
						displayInComboBox(comboSubject,
								"select subject_name from student_subjects UNION select subject_name from student_subjectsa");
						displayInComboBox(comboStaff, "select staff_name from table_teaching_staffs");
						displayInComboBox(comboTest, "select test_name from students_tests");

						JButton btnGenerate = new JButton("Generate");
						JButton btnExport = new JButton("Export Mark Sheet To Excel");
						JPanel panel = new JPanel();
						panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						panel.add(comboClass);
						panel.add(comboSubject);
						panel.add(comboTest);
						panel.add(comboStaff);
						panel.add(yearChooser);
						panel.add(btnGenerate);
						panel.add(comboPaper);
						panel.add(btnExport);

						dialog.getContentPane().add(panel, BorderLayout.NORTH);

						DefaultTableModel modelAdmittedStudents = new DefaultTableModel();
						String[][] data3 = new String[][] { { null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null },
								{ null, null, null, null, null, null, null, null }

						};

						String[] heading3 = new String[] { "Class Number", "Student Class", "Student Name",
								"Student Subject", "Staff Name", "Test Name", "Paper", "Marks Obtained" };

						modelAdmittedStudents.setDataVector(data3, heading3);

						tableData = new JTable() {
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
						tableData.setAutoCreateRowSorter(true);
						tableData.setModel(modelAdmittedStudents);
						tableData.setShowGrid(false);
						scrollerData = new JScrollPane(tableData);

						btnGenerate.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(java.awt.event.ActionEvent arg0) {
								// TODO Auto-generated method stub

								displayData(tableData, "SELECT class_number,student_class,student_name," + "'"
										+ comboSubject.getSelectedItem() + "','" + comboStaff.getSelectedItem() + "',"
										+ "'" + comboTest.getSelectedItem() + "','" + comboPaper.getSelectedItem()
										+ "','' from student_ledger where student_class='" + comboClass.getSelectedItem()
										+ "' and year='" + yearChooser.getYear() + "' group by class_number");

							}
						});

						btnExport.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(java.awt.event.ActionEvent arg0) {
								// TODO Auto-generated method stub
								fileChooser = new JFileChooser();
								fileChooser.setDialogTitle("Specify name and folder to export this deatils");

								int selected = fileChooser.showSaveDialog(AcademicsMarksEntries.this);

								if (selected == JFileChooser.APPROVE_OPTION) {
									try {

										fillData1(tableData, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
									} catch (Exception e1) {
										e1.printStackTrace();
									}

								}
							}
						});

						tableData.setBorder(new LineBorder(Color.black, 2));

						dialog.add(scrollerData, BorderLayout.CENTER);

						dialog.setVisible(true);

					}
				});

				engine.setJavaScriptEnabled(true);
				Scene scene = new Scene(box, 1250, 700);
				primaryStage = new Stage();
				primaryStage.setScene(scene);
				primaryStage.setMaximized(true);
				primaryStage.setTitle("Upload Marks or Generate External Mark Sheet");
				primaryStage.show();
				String styleSheet = getClass().getResource("attendance.css").toExternalForm();
				scene.getStylesheets().add(styleSheet);

			}
		});

		vBoxsearchAttendanceLesson.getChildren().addAll(gridPaneEntriesAttendanceLesson,
				gridPaneSearchAttendanceLesson);
		vBoxsearchAttendanceLesson.setAlignment(Pos.CENTER);

		hboxHomeMain = new HBox(2);
		vboxLabelPicture = new VBox(2);
		vboxLabelPictureReal = new VBox(0);

		vboxLabelPicture.setPadding(new Insets(10, 10, 10, 10));
		labelPictureStudent = new Label("");
		labelPictureStudent.setPrefSize(150, 145);
		vboxLabelPictureReal.setMaxSize(150, 145);
		vboxLabelPictureReal.setPrefSize(150, 145);
		vboxLabelPictureReal.setStyle("-fx-border-color: white");
		// vboxLabelPicture.setBorder(new Border(new BorderStrokeStyle(Color.white,
		// BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		vboxLabelPicture.getChildren().add(vboxLabelPictureReal);

		hboxHomeMain.getChildren().addAll(vBoxsearchAttendanceLesson, vboxLabelPicture);
		hboxHomeMain.setAlignment(Pos.CENTER);

		borderPaneAttendanceLesson.setTop(hboxHomeMain);
		// borderPaneAttendanceLesson.setRight(vBoxsearchAttendanceLesson);

		/*
		 * 
		 * 
		 * My Class Number/ Student Code Listener
		 * 
		 * 
		 * 
		 */

		fieldTimeEntriesAttendanceLesson.setOnKeyReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub

				displayStudentPicture();

			}
		});

		sceneAttendanceLesson = new Scene(borderPaneAttendanceLesson);

		String styleSheet = getClass().getResource("attendance.css").toExternalForm();
		sceneAttendanceLesson.getStylesheets().add(styleSheet);

		jfxPanelAttendanceLesson.setScene(sceneAttendanceLesson);
		jfxPanelAttendanceLesson.setPreferredSize(new Dimension(1160, 480));

		this.add(jfxPanelAttendanceLesson);
	}

	protected void displayStudentPicture() {

		try {

			java.sql.Connection conn = CashBookController.getConnection();

			Calendar cal = Calendar.getInstance();

			String four = "" + cal.getTime();

			String answer = four.substring(four.length() - 4);

			PreparedStatement pst = conn.prepareStatement(
					"select students_info.photo,student_ledger.student_name,student_ledger.payment_code from student_ledger,students_info where (student_ledger.class_number='"
							+ fieldTimeEntriesAttendanceLesson.getText() + "' or student_ledger.payment_code= '"
							+ fieldTimeEntriesAttendanceLesson.getText() + "') and student_ledger.year = '" + answer
							+ "' and students_info.payment_code=student_ledger.payment_code group by students_info.payment_code");
			System.out.println(answer);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				String name = rs.getString("student_ledger.student_name");
				labelNameSearchAttendanceLesson.setText(name);

				String code = rs.getString("student_ledger.payment_code");
				labelPaymentCode.setText(code);

				InputStream is = rs.getBinaryStream("students_info.photo");
				OutputStream os = new FileOutputStream(new File("photo.jpg"));
				byte[] content = new byte[1025];
				int size = 0;

				while ((size = is.read(content)) != -1) {

					os.write(content, 0, size);

				}

				os.close();
				is.close();

				image = new Image("file:photo.jpg", 145, 150, true, true);

				finalViewedImage = new ImageView(image);
				finalViewedImage.setFitHeight(145);
				finalViewedImage.setFitWidth(150);
				// finalViewedImage.setPreserveRatio(true);
				vboxLabelPictureReal.getChildren().clear();
				vboxLabelPictureReal.getChildren().add(finalViewedImage);
				fieldtimeSearchAttendanceLesson.setEditable(true);

			} else {
				vboxLabelPictureReal.getChildren().remove(finalViewedImage);
				labelNameSearchAttendanceLesson.setText("Student Name:");
				labelPaymentCode.setText("Payment Code");
				fieldtimeSearchAttendanceLesson.setEditable(false);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
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

	public void displayInComboBoxClassGrades(ComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			dataClass.clear();
			while (rs.next()) {
				dataClass.add(rs.getString(1));

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

	public void displayInComboBoxSubjectGrades(ComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			dataSubject.clear();
			while (rs.next()) {
				dataSubject.add(rs.getString(1));

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

	public void displayInComboBoxTestName(ComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			dataTestName.clear();
			while (rs.next()) {
				dataTestName.add(rs.getString(1));

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

			terms.clear();
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

	public void displayInComboBoxTests(ComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			tests.clear();
			while (rs.next()) {
				tests.add(rs.getString(1));
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

	public void displayInComboBoxTestsCategories(ComboBox combo, String query) {
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			testsCats.clear();
			while (rs.next()) {
				testsCats.add(rs.getString(1));
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

	public void AddUpdateDelete(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.initStyle(StageStyle.TRANSPARENT);
					alert.initModality(Modality.WINDOW_MODAL);
					alert.setContentText("Request Executed Successfully");
					alert.setHeaderText("Notice of Success");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {

						alert.close();
					}
				}
			});

			displayInComboBoxTerms(comboBoxTermEntriesAttendanceLesson, "select test_name from students_tests");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

	public void AddUpdateDeleteStudentsData(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Mark Recorded Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Student Class Number Is Not In The System\nPlease check why this student is not in the system");

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

	public Date convertFromUtilToSQLDate(java.util.Date dateUtil) {

		if (dateUtil != null) {
			java.sql.Date sqlDate = new java.sql.Date(dateUtil.getTime());

			return sqlDate;
		} else {
			return null;
		}
	}

	public void buildData() {
		java.sql.Connection c;
		data = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "select `Class Number`,`Student Class`,`Student Subject`,`Student Name`,`Staff Name` from students_marks";
			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/************************
			 * 
			 * First Clear the table
			 * 
			 **************************/
			tableview.getColumns().clear();
			/**********************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 **********************************/
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));

				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				col.setPrefWidth(200);
				tableview.getColumns().addAll(col);

			}

			/********************************
			 * Data added to ObservableList *
			 ********************************/
			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add(rs.getString(i));
				}
				System.out.println("Row [1] added " + row);
				data.add(row);

			}

			// FINALLY ADDED TO TableView
			tableview.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

	public void buildDataLater() {
		java.sql.Connection c;
		data = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "select DISTINCT students_marks.`Class Number`,students_marks.`Student Class`,students_marks.`Student Subject`,students_marks.`Student Name`,"
					+ "students_marks.`Marks Obtained`,"
					+ "(CASE WHEN students_marks.`Marks Obtained` BETWEEN students_grading.`From (%)` AND students_grading.`To (%)` "
					+ "AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "THEN students_grading.`Grade` ELSE 'Unknown' END) AS Grade, "
					+ "students_marks.`Staff Name` from students_marks,students_grading where students_grading.`Subject`=students_marks.`Student Subject`"
					+ " and students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' and "
					+ "students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' and students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' and (CASE WHEN students_marks.`Marks Obtained` BETWEEN students_grading.`From (%)` AND students_grading.`To (%)` AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` END)!='Unknown'";
			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/************************
			 * 
			 * First Clear the table
			 * 
			 **************************/
			tableview.getColumns().clear();
			/**********************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 **********************************/
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				col.setPrefWidth(160);
				tableview.getColumns().addAll(col);

			}

			/********************************
			 * Data added to ObservableList *
			 ********************************/
			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add(rs.getString(i));
				}
				System.out.println("Row [1] added " + row);
				data.add(row);

			}

			// FINALLY ADDED TO TableView
			tableview.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

	public void buildDataLaterWithPaper() {
		java.sql.Connection c;
		data = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "select DISTINCT students_marks.`Class Number`,students_marks.`Student Class`,students_marks.`Student Subject`,students_marks.`Student Name`,"
					+ "students_marks.`Marks Obtained`,"
					+ "(CASE WHEN students_marks.`Marks Obtained` BETWEEN students_grading.`From (%)` AND students_grading.`To (%)` "
					+ "AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "THEN students_grading.`Grade` ELSE 'Unknown' END) AS Grade, "
					+ "students_marks.`Staff Name` from students_marks,students_grading where students_grading.`Subject`=students_marks.`Student Subject`"
					+ " and students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' and "
					+ "students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' and students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' and (CASE WHEN students_marks.`Marks Obtained` BETWEEN students_grading.`From (%)` AND students_grading.`To (%)` AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Paper`='" + comboPaper.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` END)!='Unknown'";
			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/************************
			 * 
			 * First Clear the table
			 * 
			 **************************/
			tableview.getColumns().clear();
			/**********************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 **********************************/
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				col.setPrefWidth(160);
				tableview.getColumns().addAll(col);

			}

			/********************************
			 * Data added to ObservableList *
			 ********************************/
			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add(rs.getString(i));
				}
				System.out.println("Row [1] added " + row);
				data.add(row);

			}

			// FINALLY ADDED TO TableView
			tableview.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

	public void saveAndShowGrades() {
		java.sql.Connection c;
		dataGrades = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "select `Student Class`,`Subject`,`Test Name`,`From (%)`,`To (%)`,`Grade` from students_grading";
			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/************************
			 * 
			 * First Clear the table
			 * 
			 **************************/
			tableViewGrades.getColumns().clear();
			/**********************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 **********************************/
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));

				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				col.setPrefWidth(200);
				tableViewGrades.getColumns().addAll(col);

			}

			/********************************
			 * Data added to ObservableList *
			 ********************************/
			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add(rs.getString(i));
				}

				dataGrades.add(row);

			}

			// FINALLY ADDED TO TableView
			tableViewGrades.setItems(dataGrades);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

	public void saveAndShowGradesClass() {
		java.sql.Connection c;
		dataGrades1 = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "select `Student Class`,`Subject`,`Test Name`,`From (%)`,`To (%)`,`Grade` "
					+ "from students_grading where `Student Class`='"
					+ comboBoxClass.getSelectionModel().getSelectedItem() + "' " + "and `Subject`='"
					+ comboBoxSubject.getSelectionModel().getSelectedItem() + "' and `Test Name`='"
					+ comboBoxTest.getSelectionModel().getSelectedItem() + "'";

			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/************************
			 * 
			 * First Clear the table
			 * 
			 **************************/
			tableViewGrades.getColumns().clear();
			/**********************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 **********************************/
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));

				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				col.setPrefWidth(200);
				tableViewGrades.getColumns().addAll(col);

			}

			/********************************
			 * Data added to ObservableList *
			 ********************************/
			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add(rs.getString(i));
				}

				dataGrades1.add(row);

			}

			// FINALLY ADDED TO TableView
			tableViewGrades.setItems(dataGrades1);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

	public void saveAndShowGradesSubject() {
		java.sql.Connection c;
		dataGrades2 = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "select `Student Class`,`Subject`,`Test Name`,`From (%)`,`To (%)`,`Grade` "
					+ "from students_grading where `Student Class`='"
					+ comboBoxClass.getSelectionModel().getSelectedItem() + "' " + "and `Subject`='"
					+ comboBoxSubject.getSelectionModel().getSelectedItem() + "' and `Test Name`='"
					+ comboBoxTest.getSelectionModel().getSelectedItem() + "'";

			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/************************
			 * 
			 * First Clear the table
			 * 
			 **************************/
			tableViewGrades.getColumns().clear();
			/**********************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 **********************************/
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));

				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				col.setPrefWidth(200);
				tableViewGrades.getColumns().addAll(col);

			}

			/********************************
			 * Data added to ObservableList *
			 ********************************/
			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add(rs.getString(i));
				}

				dataGrades2.add(row);

			}

			// FINALLY ADDED TO TableView
			tableViewGrades.setItems(dataGrades2);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

	public void saveAndShowGradesTest() {
		java.sql.Connection c;
		dataGrades3 = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "select `Student Class`,`Subject`,`Test Name`,`From (%)`,`To (%)`,`Grade` "
					+ "from students_grading where `Student Class`='"
					+ comboBoxClass.getSelectionModel().getSelectedItem() + "' " + "and `Subject`='"
					+ comboBoxSubject.getSelectionModel().getSelectedItem() + "' and `Test Name`='"
					+ comboBoxTest.getSelectionModel().getSelectedItem() + "'";
			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			System.out.println(SQL);

			/************************
			 * 
			 * First Clear the table
			 * 
			 **************************/
			tableViewGrades.getColumns().clear();
			/**********************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 **********************************/
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));

				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				col.setPrefWidth(200);
				tableViewGrades.getColumns().addAll(col);

			}

			/********************************
			 * Data added to ObservableList *
			 ********************************/
			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add(rs.getString(i));
				}

				dataGrades3.add(row);

			}

			// FINALLY ADDED TO TableView
			tableViewGrades.setItems(dataGrades3);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

	private void fillTree() {
		String query = "SELECT id,catname FROM tests_categories";
		try {
			cnx = CashBookController.getConnection();
			ps = cnx.prepareStatement(query);
			rs = ps.executeQuery();

			try {
				con = CashBookController.getConnection();
				stm = con.createStatement();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			while (rs.next()) {

				ResultSet result = stm
						.executeQuery("select test_name from students_tests where catid='" + rs.getInt(1) + "'");
				while (result.next()) {
					parentNode = rs.getInt("id");
					String composant = rs.getString("catname");
					codeNode = rs.getInt("id");
					String niveau = result.getString("test_name");

					c = new Tests(codeNode, parentNode, composant, niveau);
					node.put(codeNode, c);
					pere.put(parentNode, c);
				}
				node.put(codeNode, c);
				pere.put(parentNode, c);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error" + e);
		}

		TreeItem<String> system = new TreeItem<>("School Tests");
		// brows and fill parents node
		for (Integer k : pere.keySet()) {
			Tests p = pere.get(k);
			TreeItem<String> parent = new TreeItem<>();
			parent.setValue(p.getName());

			if (p.getDeptID() == k) {
				// if the parent > 0 it must attach to the root node
				if (k >= 0) {
					system.getChildren().add(parent);

				} else {

				}
			}

			// brows and fill child hashmap
			for (Integer i : node.keySet()) {
				Tests c = node.get(i);
				TreeItem<String> noeud = new TreeItem<>();
				noeud.setValue(c.getDepartment());

				if (c.getNameID() == i) {
					// if the parent > 0 it must attach to the root node
					if (i >= 0) {

						parent.getChildren().add(noeud);

					} else {

					}
				}
			}
		}
		tree.setRoot(system);
	}

	public static class Tests {

		private final SimpleStringProperty name;
		private final SimpleStringProperty department;
		private final Integer deptID;
		private final Integer nameID;

		private Tests(Integer id, Integer nameID, String name, String department) {

			this.deptID = id;
			this.nameID = nameID;
			this.name = new SimpleStringProperty(name);
			this.department = new SimpleStringProperty(department);
		}

		public Integer getDeptID() {
			return deptID;
		}

		public Integer getNameID() {
			return nameID;
		}

		public String getName() {
			return name.get();
		}

		public void setName(String fName) {
			name.set(fName);
		}

		public String getDepartment() {
			return department.get();
		}

		public void setDepartment(String fName) {
			department.set(fName);
		}
	}

	public ResultSet generateCellValue(String sql) {

		Statement statement;
		try {
			if (con == null) {
				con = CashBookController.getConnection();
			}

			statement = con.createStatement();

			result = statement.executeQuery(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}

		return result;
	}

	public void fillData1(JTable table, java.io.File file) {

		try {

			XSSFWorkbook workbook1 = new XSSFWorkbook();
			// Sheet sheet1 = workbook1.createSheet("Color Test");

			XSSFSheet fSheet;
			fSheet = workbook1.createSheet("New Mark Sheet");

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
					Object object = model.getValueAt(i, j);
					if (object == "" || object == null) {
						cell2.setCellValue("");
					} else {
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

}
