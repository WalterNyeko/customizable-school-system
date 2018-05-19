package clarion.academics.ui;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

import clarion.attendance.core.AttendanceLessons;
import clarion.attendance.dao.AttendanceDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import school.ui.finances.CashBookController;

public class GraphicalStaffsAnalysis extends JPanel {

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

	private Series<String, Double> dataSeries1;

	private LineChart barChart;

	private ObservableList<String> dataTerm;

	private ComboBox comboTerm;

	private ObservableList<String> dataClass;

	private ComboBox comboClass;

	public GraphicalStaffsAnalysis() {
		// TODO Auto-generated constructor stub

		jfxPanelAttendanceLesson = new JFXPanel();



		//////////////// Adding JavaFX BarChart/////////////////

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Student Tests");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Average Class Percentage (%)");

		barChart = new LineChart(xAxis, yAxis);
		barChart.setHorizontalGridLinesVisible(true);
		barChart.setHorizontalZeroLineVisible(true);
		barChart.setVerticalGridLinesVisible(true);
		barChart.setVerticalZeroLineVisible(true);
		barChart.setMinWidth(1156);
		barChart.setMinHeight(428);
		barChart.setTitle("Staffs Progress Graphical Analysis");
		

		Button btnLoad = new Button("Load Analysis Graph");
		btnLoad.setPrefWidth(130);

		Button btnClear = new Button("Clear Analysis Graph");
		btnClear.setPrefWidth(130);
		
		
		staffs=FXCollections.observableArrayList();
		comboBoxNameEntriesAttendanceLesson = new ComboBox<>(staffs);
		comboBoxNameEntriesAttendanceLesson.setPromptText("Choose Name");
		displayInComboBoxStaffs(comboBoxNameEntriesAttendanceLesson, "select staff_name from table_teaching_staffs");


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

		
		
		dataClass = FXCollections.observableArrayList("Senior One","Senior Two","Senior Three","Senior Four","Senior Five","Senior Six");

		comboClass = new ComboBox(dataClass);
		comboClass.setPromptText("Choose Class");
		comboClass.setPrefWidth(150);

		VBox boxPanel = new VBox(5);
		boxPanel.setPadding(new Insets(10, 10, 10, 10));
		boxPanel.setAlignment(Pos.CENTER);

		HBox boxTerm = new HBox(5);
		boxTerm.setAlignment(Pos.CENTER);
		boxTerm.getChildren().addAll(comboBoxNameEntriesAttendanceLesson,comboTerm,comboBoxSubjectEntriesAttendanceLesson,comboClass, btnLoad, btnClear);

		boxPanel.getChildren().addAll(boxTerm, barChart);

		/*
		 * 
		 * 
		 * My Class Number/ Student Code Listener
		 * 
		 * 
		 * 
		 */

		

		sceneAttendanceLesson = new Scene(boxPanel);

		String styleSheet = getClass().getResource("attendance.css").toExternalForm();
		sceneAttendanceLesson.getStylesheets().add(styleSheet);

		jfxPanelAttendanceLesson.setScene(sceneAttendanceLesson);
		jfxPanelAttendanceLesson.setPreferredSize(new Dimension(1160, 480));

		this.add(jfxPanelAttendanceLesson);
	}

	protected void displayStudentPicture() {

		try {

			java.sql.Connection conn = CashBookController.getConnection();

			PreparedStatement pst = conn
					.prepareStatement("select photo from students_info where CONCAT(class_number,payment_code) LIKE '%"
							+ fieldTimeEntriesAttendanceLesson.getText() + "%'");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

			} else {
				// JOptionPane.showMessageDialog(null, "No Image Found For " + studentName + "
				// In The Database",
				// "Lacking " + studentName + "'s Photo In Database",
				// JOptionPane.INFORMATION_MESSAGE);
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

}
