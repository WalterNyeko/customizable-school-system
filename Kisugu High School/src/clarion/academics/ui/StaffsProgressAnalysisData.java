package clarion.academics.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clarion.attendance.core.AttendanceLessons;
import clarion.attendance.dao.AttendanceDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import school.ui.finances.CashBookController;

public class StaffsProgressAnalysisData extends JPanel {

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

	private ObservableList<String> dataTerm;

	private ComboBox comboTerm;

	public StaffsProgressAnalysisData() {
		// TODO Auto-generated constructor stub

		jfxPanelAttendanceLesson = new JFXPanel();
		borderPaneAttendanceLesson = new BorderPane();

		// vBoxTop = new VBox();
		// borderPaneAttendanceLesson.setTop(vBoxTop);

		tableViewAttendanceLessons = new TableView<>();
		tableViewAttendanceLessons.setTableMenuButtonVisible(true);

		columnSNo = new TableColumn<>("Student No");
		columnSNo.setCellValueFactory(new PropertyValueFactory<>("sNo"));
		columnSNo.setPrefWidth(90);

		columnDate = new TableColumn<>("Class No");
		columnDate.setPrefWidth(90);
		columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

		columnTeachersName = new TableColumn<>("Student Name");
		columnTeachersName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
		columnTeachersName.setPrefWidth(220);

		columnClass = new TableColumn<>("BOT Marks");
		columnClass.setCellValueFactory(new PropertyValueFactory<>("teacherClass"));
		columnClass.setPrefWidth(90);
		
		columnClassGrade = new TableColumn<>("BOT Grade");
		columnClassGrade.setCellValueFactory(new PropertyValueFactory<>("teacherClass"));
		columnClassGrade.setPrefWidth(90);

		columnSubject = new TableColumn<>("MOT Marks");
		columnSubject.setCellValueFactory(new PropertyValueFactory<>("teacherSubject"));
		columnSubject.setPrefWidth(90);
		
		columnSubjectGrade = new TableColumn<>("MOT Grade");
		columnSubjectGrade.setCellValueFactory(new PropertyValueFactory<>("teacherSubject"));
		columnSubjectGrade.setPrefWidth(90);

		columnTime = new TableColumn<>("EOT Marks");
		columnTime.setCellValueFactory(new PropertyValueFactory<>("lessonTime"));
		columnTime.setPrefWidth(90);
		
		columnTimeGrade = new TableColumn<>("EOT Grade");
		columnTimeGrade.setCellValueFactory(new PropertyValueFactory<>("lessonTime"));
		columnTimeGrade.setPrefWidth(90);

		columnTerm = new TableColumn<>("Ave Marks");
		columnTerm.setCellValueFactory(new PropertyValueFactory<>("term"));
		columnTerm.setPrefWidth(100);

		columnTermGrade = new TableColumn<>("Ave Grade");
		columnTermGrade.setCellValueFactory(new PropertyValueFactory<>("term"));
		columnTermGrade.setPrefWidth(100);
		
		

		
		tableViewAttendanceLessons.getColumns().addAll(columnSNo, columnDate, columnTeachersName, columnClass,columnClassGrade,
				columnSubject,columnSubjectGrade, columnTime,columnTimeGrade, columnTerm,columnTermGrade);

		vBoxTableAttendanceLesson = new VBox();
		vBoxTableAttendanceLesson.getChildren().addAll(tableViewAttendanceLessons);

		borderPaneAttendanceLesson.setCenter(vBoxTableAttendanceLesson);

		// hBoxAttendanceLesson = new HBox();

		/*
		 * Entries Control Start Here
		 */
		labelDateEntriesAttendanceLesson = new Label("Tests");
		labelNameEntriesAttendanceLesson = new Label("Staff Name:");
		labelClassEntriesAttendanceLesson = new Label("Student Class:");
		labelSubjectEntriesAttendanceLesson = new Label("Subject:");
		labelTimeEntriesAttendanceLesson = new Label("Staff ID No:");
		labelTermEntriesAttendanceLesson = new Label("Term:");

		datePickerEntriesAttendanceLesson = new DatePicker();

		staffs=FXCollections.observableArrayList();
		comboBoxNameEntriesAttendanceLesson = new ComboBox<>(staffs);
		comboBoxNameEntriesAttendanceLesson.setPromptText("Choose Name");
		displayInComboBoxStaffs(comboBoxNameEntriesAttendanceLesson, "select staff_name from table_teaching_staffs");

		
		classes=FXCollections.observableArrayList();
		comboBoxClassEntriesAttendanceLesson = new ComboBox<>(classes);
		comboBoxClassEntriesAttendanceLesson.setPromptText("Choose Class");
		displayInComboBoxClass(comboBoxClassEntriesAttendanceLesson, "select class_name from student_classes");

		dataTerm = FXCollections.observableArrayList();

		comboTerm = new ComboBox(dataTerm);
		comboTerm.setPromptText("Choose Test");
		displayInComboBoxTerms(comboTerm, "select test_name from students_tests");
		comboTerm.setPrefWidth(150);
		
		
		subjects=FXCollections.observableArrayList();
		comboBoxSubjectEntriesAttendanceLesson = new ComboBox<>(
				subjects);
		comboBoxSubjectEntriesAttendanceLesson.setPromptText("Choose Subject");
		displayInComboBoxSubject(comboBoxSubjectEntriesAttendanceLesson, "select subject_name from student_subjects union select subject_name from student_subjectsa");

		fieldTimeEntriesAttendanceLesson = new TextField();
		fieldTimeEntriesAttendanceLesson.setPromptText("Staff ID No:");
		

		buttonSaveAttendanceLesson = new Button("Add Recommendation ");
		buttonCancelAttendanceLesson = new Button("Generate Results");
		buttonUpdateAttendanecLesson = new Button("Save Student's Marks");

		buttonPickAttendanceLesson = new Button("Edit Student's Marks");

		gridPaneEntriesAttendanceLesson = new GridPane();
		vBoxEntriesAttendanceLesson = new VBox();

		/*
		 * Entries Control End Here
		 */

		

		/*
		 * Adding Entries controls to Holders Start Here
		 */

		gridPaneEntriesAttendanceLesson.setVgap(10);
		gridPaneEntriesAttendanceLesson.setHgap(2);

		gridPaneEntriesAttendanceLesson.setPadding(new Insets(10));

		gridPaneEntriesAttendanceLesson.add(labelDateEntriesAttendanceLesson, 5, 0);
		gridPaneEntriesAttendanceLesson.add(labelNameEntriesAttendanceLesson, 1, 0);
		gridPaneEntriesAttendanceLesson.add(labelClassEntriesAttendanceLesson, 2, 0);
		gridPaneEntriesAttendanceLesson.add(labelSubjectEntriesAttendanceLesson, 3, 0);
		gridPaneEntriesAttendanceLesson.add(labelTimeEntriesAttendanceLesson, 0, 0);

		gridPaneEntriesAttendanceLesson.add(comboTerm, 5, 1);
		gridPaneEntriesAttendanceLesson.add(comboBoxNameEntriesAttendanceLesson, 1, 1);
		gridPaneEntriesAttendanceLesson.add(comboBoxClassEntriesAttendanceLesson, 2, 1);
		gridPaneEntriesAttendanceLesson.add(comboBoxSubjectEntriesAttendanceLesson, 3, 1);
		gridPaneEntriesAttendanceLesson.add(fieldTimeEntriesAttendanceLesson, 0, 1);

		
		gridPaneEntriesAttendanceLesson.add(buttonCancelAttendanceLesson, 5, 2);


		/*
		 * Adding Entries controls to Holders End Here
		 */

		
		
		tableViewAttendanceLessons.setId("table_attendance");
		
		hboxHomeMain=new HBox(2);
		vboxLabelPicture=new VBox(2);
		vboxLabelPictureReal=new VBox(2);
		
		vboxLabelPicture.setPadding(new Insets(10, 10, 10, 10));
		labelPictureStudent=new Label("");
		vboxLabelPictureReal.setPrefSize(150, 145);
		vboxLabelPictureReal.setStyle("-fx-border-color: white");
		vboxLabelPicture.getChildren().add(vboxLabelPictureReal);
	
		
		hboxHomeMain.getChildren().addAll(gridPaneEntriesAttendanceLesson,vboxLabelPicture);

		borderPaneAttendanceLesson.setTop(hboxHomeMain);
		
		
		
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

			PreparedStatement pst = conn.prepareStatement("select photo from students_info where CONCAT(class_number,payment_code) LIKE '%"
					+ fieldTimeEntriesAttendanceLesson.getText() + "%'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {


				
				
				
			} else {
//				JOptionPane.showMessageDialog(null, "No Image Found For " + studentName + " In The Database",
//						"Lacking " + studentName + "'s Photo In Database", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
				dataTerm.add(rs.getString(1));
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

}
