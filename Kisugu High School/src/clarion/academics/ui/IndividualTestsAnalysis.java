package clarion.academics.ui;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JPanel;

import clarion.attendance.core.AttendanceLessons;
import clarion.attendance.dao.AttendanceDAO;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import school.ui.finances.CashBookController;

public class IndividualTestsAnalysis extends JPanel {

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

	private TableView tableViewAttendanceLessons;

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

	private TableView tableViewCategory2;

	private ObservableList<Object> dataGrades;

	private ObservableList<Object> dataGrades2;

	private TableView tableViewAttendanceLessonsTwo;

	private ObservableList<String> paper;

	private ComboBox comboPaper;

	public IndividualTestsAnalysis() {
		// TODO Auto-generated constructor stub

		jfxPanelAttendanceLesson = new JFXPanel();
		borderPaneAttendanceLesson = new BorderPane();

		// vBoxTop = new VBox();
		// borderPaneAttendanceLesson.setTop(vBoxTop);

		tableViewAttendanceLessons = new TableView<>();
		tableViewAttendanceLessons.setTableMenuButtonVisible(true);
		tableViewAttendanceLessons.setMinHeight(300);

		tableViewAttendanceLessonsTwo = new TableView<>();
		tableViewAttendanceLessonsTwo.setTableMenuButtonVisible(true);

		vBoxTableAttendanceLesson = new VBox(20);
		vBoxTableAttendanceLesson.setAlignment(Pos.CENTER);
		vBoxTableAttendanceLesson.getChildren().addAll(tableViewAttendanceLessons, tableViewAttendanceLessonsTwo);

		borderPaneAttendanceLesson.setCenter(vBoxTableAttendanceLesson);

		// hBoxAttendanceLesson = new HBox();

		/*
		 * Entries Control Start Here
		 */
		labelClassEntriesAttendanceLesson = new Label("Student Class:");
		labelSubjectEntriesAttendanceLesson = new Label("Subject:");
		labelTermEntriesAttendanceLesson = new Label("Test:");

		classes = FXCollections.observableArrayList();
		comboBoxClassEntriesAttendanceLesson = new ComboBox<>(classes);
		comboBoxClassEntriesAttendanceLesson.setPromptText("Choose Class");
		displayInComboBoxClass(comboBoxClassEntriesAttendanceLesson, "select class_name from student_classes");
		comboBoxClassEntriesAttendanceLesson.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedIndex() <= 3) {
					comboPaper.setVisible(false);
				} else {
					comboPaper.setVisible(true);
				}
			}
		});

		subjects = FXCollections.observableArrayList();
		comboBoxSubjectEntriesAttendanceLesson = new ComboBox<>(subjects);
		comboBoxSubjectEntriesAttendanceLesson.setPromptText("Choose Subject");
		displayInComboBoxSubject(comboBoxSubjectEntriesAttendanceLesson,
				"select subject_name from student_subjects union select subject_name from student_subjectsa");

		terms = FXCollections.observableArrayList();
		comboBoxTermEntriesAttendanceLesson = new ComboBox<>(terms);
		comboBoxTermEntriesAttendanceLesson.setPromptText("Choose Test");
		displayInComboBoxTerms(comboBoxTermEntriesAttendanceLesson, "select test_name from students_tests");

		paper = FXCollections.observableArrayList("1", "2", "3");
		comboPaper = new ComboBox<>(paper);
		comboPaper.setPromptText("Choose Paper");
		comboPaper.setVisible(false);

		buttonUpdateAttendanecLesson = new Button("Generate Analysis Data");
		buttonUpdateAttendanecLesson.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedIndex() <= 3) {
					showRecordAnalysisOne();
					showRecordAnalysisTwo();
				} else {
					showRecordAnalysisOneAlevel();
					showRecordAnalysisTwoAlevel();
				}

			}
		});

		gridPaneEntriesAttendanceLesson = new GridPane();
		vBoxEntriesAttendanceLesson = new VBox();

		/*
		 * Entries Control End Here
		 */

		/*
		 * Adding Entries controls to Holders Start Here
		 */

		gridPaneEntriesAttendanceLesson.setVgap(10);
		gridPaneEntriesAttendanceLesson.setHgap(10);
		gridPaneEntriesAttendanceLesson.setAlignment(Pos.CENTER);

		gridPaneEntriesAttendanceLesson.setPadding(new Insets(10));

		gridPaneEntriesAttendanceLesson.add(labelClassEntriesAttendanceLesson, 0, 0);
		gridPaneEntriesAttendanceLesson.add(labelSubjectEntriesAttendanceLesson, 1, 0);
		gridPaneEntriesAttendanceLesson.add(labelTermEntriesAttendanceLesson, 2, 0);

		gridPaneEntriesAttendanceLesson.add(comboBoxClassEntriesAttendanceLesson, 0, 1);
		gridPaneEntriesAttendanceLesson.add(comboBoxSubjectEntriesAttendanceLesson, 1, 1);
		gridPaneEntriesAttendanceLesson.add(comboBoxTermEntriesAttendanceLesson, 2, 1);
		gridPaneEntriesAttendanceLesson.add(comboPaper, 3, 1);
		gridPaneEntriesAttendanceLesson.add(buttonUpdateAttendanecLesson, 4, 1);

		/*
		 * Adding Entries controls to Holders End Here
		 */

		tableViewAttendanceLessons.setId("table_attendance");

		borderPaneAttendanceLesson.setTop(gridPaneEntriesAttendanceLesson);
		// borderPaneAttendanceLesson.setRight(vBoxsearchAttendanceLesson);

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

	public void showRecordAnalysisOne() {
		java.sql.Connection c;
		dataGrades = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();

			String SQL = "select DISTINCT students_marks.`Student Class`,students_marks.`Student Subject`,"
					+ "CONCAT(students_grading.`From (%)`,'-',students_grading.`To (%)`) AS 'Grade Range',"
					+ "(CASE WHEN students_marks.`Marks Obtained` BETWEEN students_grading.`From (%)` AND students_grading.`To (%)` AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` ELSE 'Unknown' END) AS Grade, "
					+ "COUNT(students_grading.`Grade`) AS 'Number of Students' from students_marks,students_grading where "
					+ "students_grading.`Subject`=students_marks.`Student Subject` and (CASE WHEN students_marks.`Marks Obtained` "
					+ "BETWEEN students_grading.`From (%)` AND students_grading.`To (%)`"
					+ " AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "'"
					+ " AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` END)!='Unknown' " + "and students_marks.`Student Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' and students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' group by students_grading.`Grade`,students_grading.`Subject`";

			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/************************
			 * 
			 * First Clear the table
			 * 
			 **************************/
			tableViewAttendanceLessons.getColumns().clear();
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

				col.setPrefWidth(230);
				tableViewAttendanceLessons.getColumns().addAll(col);

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
			tableViewAttendanceLessons.setItems(dataGrades);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

	public void showRecordAnalysisTwo() {
		java.sql.Connection c;
		dataGrades = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL
			String SQL = "select DISTINCT students_marks.`Student Class`,students_marks.`Student Subject`,"
					+ "(SELECT ROUND(COUNT(*)/9,0) from students_marks,students_grading WHERE students_marks.`Marks Obtained`>="
					+ "(SELECT students_grading.`From (%)` from students_grading WHERE students_grading.`Grade`='C3'  "
					+ "AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "') AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Student Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Test Name`=students_grading.`Test Name` AND students_marks.`Student Class`=students_grading.`Student Class`)"
					+ " AS ' Students Btn D1-C3',"
					+ "(SELECT ROUND(COUNT(*)/9,0) from students_marks,students_grading WHERE students_marks.`Marks Obtained` <(SELECT students_grading.`From (%)` "
					+ "from students_grading WHERE students_grading.`Grade`='C3' AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "') "
					+ " AND students_marks.`Marks Obtained`>= (SELECT students_grading.`From (%)` from students_grading WHERE students_grading.`Grade`='C6' "
					+ "AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "') "
					+ "AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Student Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Test Name`=students_grading.`Test Name` AND students_marks.`Student Class`="
					+ "students_grading.`Student Class`) AS ' Students Btn C4-C6',"
					+ "(SELECT ROUND(COUNT(*)/9,0) from students_marks,students_grading WHERE students_marks.`Marks Obtained` < (SELECT students_grading.`From (%)` "
					+ "from students_grading WHERE students_grading.`Grade`='C6' AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "') "
					+ " AND students_marks.`Marks Obtained`>= (SELECT students_grading.`From (%)` from students_grading WHERE students_grading.`Grade`='F9' "
					+ "AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "') "
					+ "AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Student Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Test Name`=students_grading.`Test Name` AND students_marks.`Student Class`="
					+ "students_grading.`Student Class`) AS ' Students Btn P7-F9' from students_marks,students_grading where "
					+ "students_grading.`Subject`=students_marks.`Student Subject` and (CASE WHEN students_marks.`Marks Obtained` "
					+ "BETWEEN students_grading.`From (%)` AND students_grading.`To (%)`"
					+ " AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "'"
					+ " AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` END)!='Unknown' " + "and students_marks.`Student Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' and students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "'  ";

			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/************************
			 * 
			 * First Clear the table
			 * 
			 **************************/
			tableViewAttendanceLessonsTwo.getColumns().clear();
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

				col.setPrefWidth(230);
				tableViewAttendanceLessonsTwo.getColumns().addAll(col);

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
			tableViewAttendanceLessonsTwo.setItems(dataGrades);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

	public void showRecordAnalysisOneAlevel() {
		java.sql.Connection c;
		dataGrades = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();

			String SQL = "select DISTINCT students_marks.`Student Class`,students_marks.`Student Subject`,"
					+ "CONCAT(students_grading.`From (%)`,'-',students_grading.`To (%)`) AS 'Grade Range',"
					+ "(CASE WHEN students_marks.`Marks Obtained` BETWEEN students_grading.`From (%)` AND students_grading.`To (%)` AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` ELSE 'Unknown' END) AS Grade, "
					+ "COUNT(students_grading.`Grade`) AS 'Number of Students' from students_marks,students_grading where "
					+ "students_grading.`Subject`=students_marks.`Student Subject` and (CASE WHEN students_marks.`Marks Obtained` "
					+ "BETWEEN students_grading.`From (%)` AND students_grading.`To (%)`"
					+ " AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "'"
					+ " AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` END)!='Unknown' " + "and students_marks.`Student Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' and students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Paper`='" + comboPaper.getSelectionModel().getSelectedItem()
					+ "' group by students_grading.`Grade`,students_grading.`Subject`";

			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/************************
			 * 
			 * First Clear the table
			 * 
			 **************************/
			tableViewAttendanceLessons.getColumns().clear();
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

				col.setPrefWidth(230);
				tableViewAttendanceLessons.getColumns().addAll(col);

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
			tableViewAttendanceLessons.setItems(dataGrades);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

	public void showRecordAnalysisTwoAlevel() {
		java.sql.Connection c;
		dataGrades = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL
			String SQL = "select DISTINCT students_marks.`Student Class`,students_marks.`Student Subject`,"
					+ "(SELECT ROUND(COUNT(*)/9,0) from students_marks,students_grading WHERE students_marks.`Marks Obtained`>="
					+ "(SELECT students_grading.`From (%)` from students_grading WHERE students_grading.`Grade`='C3'  "
					+ "AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "') AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Student Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Test Name`=students_grading.`Test Name` AND students_marks.`Student Class`="
					+ "students_grading.`Student Class`  AND students_marks.`Paper`='"
					+ comboPaper.getSelectionModel().getSelectedItem() + "')" + " AS ' Students Btn D1-C3',"
					+ "(SELECT ROUND(COUNT(*)/9,0) from students_marks,students_grading WHERE students_marks.`Marks Obtained` <(SELECT students_grading.`From (%)` "
					+ "from students_grading WHERE students_grading.`Grade`='C3' AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "') "
					+ " AND students_marks.`Marks Obtained`>= (SELECT students_grading.`From (%)` from students_grading WHERE students_grading.`Grade`='C6' "
					+ "AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "') "
					+ "AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Student Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Test Name`=students_grading.`Test Name` AND students_marks.`Student Class`="
					+ "students_grading.`Student Class`  AND students_marks.`Paper`='"
					+ comboPaper.getSelectionModel().getSelectedItem() + "') AS ' Students Btn C4-C6',"
					+ "(SELECT ROUND(COUNT(*)/9,0) from students_marks,students_grading WHERE students_marks.`Marks Obtained` < (SELECT students_grading.`From (%)` "
					+ "from students_grading WHERE students_grading.`Grade`='C6' AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "') "
					+ " AND students_marks.`Marks Obtained`>= (SELECT students_grading.`From (%)` from students_grading WHERE students_grading.`Grade`='F9' "
					+ "AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "') "
					+ "AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Student Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_marks.`Test Name`=students_grading.`Test Name` AND students_marks.`Student Class`="
					+ "students_grading.`Student Class`  AND students_marks.`Paper`='"
					+ comboPaper.getSelectionModel().getSelectedItem()
					+ "') AS ' Students Btn P7-F9' from students_marks,students_grading where "
					+ "students_grading.`Subject`=students_marks.`Student Subject` and (CASE WHEN students_marks.`Marks Obtained` "
					+ "BETWEEN students_grading.`From (%)` AND students_grading.`To (%)`"
					+ " AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "'"
					+ " AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` END)!='Unknown' " + "and students_marks.`Student Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' and students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Paper`='" + comboPaper.getSelectionModel().getSelectedItem() + "' ";

			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/************************
			 * 
			 * First Clear the table
			 * 
			 **************************/
			tableViewAttendanceLessonsTwo.getColumns().clear();
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

				col.setPrefWidth(230);
				tableViewAttendanceLessonsTwo.getColumns().addAll(col);

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
			tableViewAttendanceLessonsTwo.setItems(dataGrades);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

}
