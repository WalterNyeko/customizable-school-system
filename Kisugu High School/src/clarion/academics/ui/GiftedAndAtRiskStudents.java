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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import school.ui.finances.CashBookController;

public class GiftedAndAtRiskStudents extends JPanel {

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

	private TableColumn columnTimeAgg;

	private TableColumn columnTimeAttendance;

	private TableColumn columnTimeNotes;

	private TableColumn columnClassAgg;

	private TableColumn columnClassAttendance;

	private TableColumn columnClassNotes;

	private ObservableList<Object> data;

	private TextField fieldGiftedLimit;

	private TextField AtRiskLimit;

	private ObservableList<String> paper;

	private ComboBox comboPaper;

	public GiftedAndAtRiskStudents() {
		// TODO Auto-generated constructor stub

		jfxPanelAttendanceLesson = new JFXPanel();
		borderPaneAttendanceLesson = new BorderPane();

		// vBoxTop = new VBox();
		// borderPaneAttendanceLesson.setTop(vBoxTop);

		tableViewAttendanceLessons = new TableView<>();
		tableViewAttendanceLessons.setTableMenuButtonVisible(true);

		tableViewCategory2 = new TableView<>();
		tableViewCategory2.setTableMenuButtonVisible(true);

		columnSNo = new TableColumn<>("S/No");
		columnSNo.setCellValueFactory(new PropertyValueFactory<>("sNo"));
		columnSNo.setPrefWidth(120);

		columnDate = new TableColumn<>("Class No");
		columnDate.setPrefWidth(120);
		columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

		columnTeachersName = new TableColumn<>("Name of Students");
		columnTeachersName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
		columnTeachersName.setPrefWidth(220);

		columnClass = new TableColumn<>("Score(%)");
		columnClass.setCellValueFactory(new PropertyValueFactory<>("teacherClass"));
		columnClass.setPrefWidth(175);

		columnClassAgg = new TableColumn<>("Baseline Aggregate(PLE)");
		columnClassAgg.setCellValueFactory(new PropertyValueFactory<>("teacherClass"));
		columnClassAgg.setPrefWidth(175);

		columnClassAttendance = new TableColumn<>("Lesson Attendance");
		columnClassAttendance.setCellValueFactory(new PropertyValueFactory<>("teacherClass"));
		columnClassAttendance.setPrefWidth(175);

		columnClassNotes = new TableColumn<>("Student Notes");
		columnClassNotes.setCellValueFactory(new PropertyValueFactory<>("teacherClass"));
		columnClassNotes.setPrefWidth(175);

		columnClassGrade = new TableColumn<>("S/No");
		columnClassGrade.setCellValueFactory(new PropertyValueFactory<>("teacherClass"));
		columnClassGrade.setPrefWidth(120);

		columnSubject = new TableColumn<>("Class No");
		columnSubject.setCellValueFactory(new PropertyValueFactory<>("teacherSubject"));
		columnSubject.setPrefWidth(120);

		columnSubjectGrade = new TableColumn<>("Name of Students");
		columnSubjectGrade.setCellValueFactory(new PropertyValueFactory<>("teacherSubject"));
		columnSubjectGrade.setPrefWidth(220);

		columnTime = new TableColumn<>("Score(%)");
		columnTime.setCellValueFactory(new PropertyValueFactory<>("lessonTime"));
		columnTime.setPrefWidth(175);

		columnTimeAgg = new TableColumn<>("Baseline Aggregate(PLE)");
		columnTimeAgg.setCellValueFactory(new PropertyValueFactory<>("lessonTime"));
		columnTimeAgg.setPrefWidth(175);

		columnTimeAttendance = new TableColumn<>("Lesson Attendance");
		columnTimeAttendance.setCellValueFactory(new PropertyValueFactory<>("lessonTime"));
		columnTimeAttendance.setPrefWidth(175);

		columnTimeNotes = new TableColumn<>("Student Notes");
		columnTimeNotes.setCellValueFactory(new PropertyValueFactory<>("lessonTime"));
		columnTimeNotes.setPrefWidth(175);

		tableViewAttendanceLessons.getColumns().addAll(columnSNo, columnDate, columnTeachersName, columnClass,
				columnClassAgg, columnClassAttendance, columnClassNotes);

		tableViewCategory2.getColumns().addAll(columnClassGrade, columnSubject, columnSubjectGrade, columnTime,
				columnTimeAgg, columnTimeAttendance, columnTimeNotes);

		vBoxTableAttendanceLesson = new VBox(20);
		vBoxTableAttendanceLesson.setAlignment(Pos.CENTER);
		vBoxTableAttendanceLesson.getChildren().addAll(tableViewAttendanceLessons, tableViewCategory2);

		borderPaneAttendanceLesson.setCenter(vBoxTableAttendanceLesson);

		// hBoxAttendanceLesson = new HBox();

		/*
		 * Entries Control Start Here
		 */

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

		paper = FXCollections.observableArrayList("1", "2", "3");
		comboPaper = new ComboBox<>(paper);
		comboPaper.setPromptText("Choose Paper");
		comboPaper.setVisible(false);

		terms = FXCollections.observableArrayList();
		comboBoxTermEntriesAttendanceLesson = new ComboBox<>(terms);
		comboBoxTermEntriesAttendanceLesson.setPromptText("Choose Test");
		displayInComboBoxTerms(comboBoxTermEntriesAttendanceLesson, "select test_name from students_tests");
		comboBoxTermEntriesAttendanceLesson.setPrefWidth(200);
		buttonUpdateAttendanecLesson = new Button("Generate Analysis Data");

		buttonUpdateAttendanecLesson.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedIndex() <= 3) {
					buildDataLaterGifted();
					buildDataLaterAtRisk();

				} else {
					buildDataLaterGiftedAlevel();
					buildDataLaterAtRiskAlevel();

				}

			}
		});

		gridPaneEntriesAttendanceLesson = new GridPane();
		vBoxEntriesAttendanceLesson = new VBox();

		Label max10 = new Label("Gifted");
		Label min10 = new Label("At Risk");
		fieldGiftedLimit = new TextField();
		fieldGiftedLimit.setMaxWidth(80);
		AtRiskLimit = new TextField();
		AtRiskLimit.setMaxWidth(80);

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

		gridPaneEntriesAttendanceLesson.add(comboBoxClassEntriesAttendanceLesson, 0, 0);
		gridPaneEntriesAttendanceLesson.add(comboBoxSubjectEntriesAttendanceLesson, 1, 0);
		gridPaneEntriesAttendanceLesson.add(comboPaper, 2, 0);
		gridPaneEntriesAttendanceLesson.add(comboBoxTermEntriesAttendanceLesson, 3, 0);
		gridPaneEntriesAttendanceLesson.add(max10, 4, 0);
		gridPaneEntriesAttendanceLesson.add(min10, 6, 0);
		gridPaneEntriesAttendanceLesson.add(fieldGiftedLimit, 5, 0);
		gridPaneEntriesAttendanceLesson.add(AtRiskLimit, 7, 0);

		gridPaneEntriesAttendanceLesson.add(buttonUpdateAttendanecLesson, 8, 0);

		/*
		 * Adding Entries controls to Holders End Here
		 */

		tableViewAttendanceLessons.setId("table_attendance");

		borderPaneAttendanceLesson.setTop(gridPaneEntriesAttendanceLesson);

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

	public void buildDataLaterGifted() {
		java.sql.Connection c;
		data = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "select DISTINCT students_marks.`Class Number`,students_marks.`Student Class`,students_marks.`Student Subject`,students_marks.`Student Name`,"
					+ "students_marks.`Marks Obtained`,(CASE WHEN students_marks.`Marks Obtained` BETWEEN students_grading.`From (%)` "
					+ "AND students_grading.`To (%)` AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` ELSE 'Unknown' END) AS Grade, "
					+ "students_marks.`Staff Name` from students_marks,students_grading where "
					+ "students_grading.`Subject`=students_marks.`Student Subject` and (CASE WHEN students_marks.`Marks Obtained` BETWEEN students_grading.`From (%)` "
					+ "AND students_grading.`To (%)` AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` ELSE 'Unknown' END)!='Unknown' and students_marks.`Student Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' and students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "'  group by students_marks.`Class Number` ORDER BY students_marks.`Marks Obtained` DESC LIMIT "
					+ fieldGiftedLimit.getText() + "";
			// ResultSet

			System.out.println(SQL);
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

				col.setPrefWidth(160);
				tableViewAttendanceLessons.getColumns().addAll(col);
				System.out.println("Column [" + i + "] ");
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
				data.add(row);

			}

			// FINALLY ADDED TO TableView
			tableViewAttendanceLessons.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

	public void buildDataLaterAtRisk() {
		java.sql.Connection c;
		data = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "select DISTINCT students_marks.`Class Number`,students_marks.`Student Class`,students_marks.`Student Subject`,students_marks.`Student Name`,"
					+ "students_marks.`Marks Obtained`,(CASE WHEN students_marks.`Marks Obtained` BETWEEN students_grading.`From (%)` "
					+ "AND students_grading.`To (%)` AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` ELSE 'Unknown' END) AS Grade, "
					+ "students_marks.`Staff Name` from students_marks,students_grading where "
					+ "students_grading.`Subject`=students_marks.`Student Subject` and (CASE WHEN students_marks.`Marks Obtained` BETWEEN students_grading.`From (%)` "
					+ "AND students_grading.`To (%)` AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` ELSE 'Unknown' END)!='Unknown' and students_marks.`Student Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' and students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "'  group by students_marks.`Class Number` ORDER BY students_marks.`Marks Obtained` ASC LIMIT "
					+ AtRiskLimit.getText() + "";
			// ResultSet
			System.out.println(SQL);
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/************************
			 * 
			 * First Clear the table
			 * 
			 **************************/
			tableViewCategory2.getColumns().clear();
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
				tableViewCategory2.getColumns().addAll(col);
				System.out.println("Column [" + i + "] ");
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
			tableViewCategory2.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

	public void buildDataLaterGiftedAlevel() {
		java.sql.Connection c;
		data = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "select DISTINCT students_marks.`Class Number`,students_marks.`Student Class`,students_marks.`Student Subject`,students_marks.`Student Name`,"
					+ "students_marks.`Marks Obtained`,(CASE WHEN students_marks.`Marks Obtained` BETWEEN students_grading.`From (%)` "
					+ "AND students_grading.`To (%)` AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` ELSE 'Unknown' END) AS Grade, "
					+ "COUNT(`Students Attendance`.`Class Number`) As Attendance  from students_marks,students_grading,`Students Attendance` where "
					+ "students_grading.`Subject`=students_marks.`Student Subject` and (CASE WHEN students_marks.`Marks Obtained` BETWEEN students_grading.`From (%)` "
					+ "AND students_grading.`To (%)` AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` ELSE 'Unknown' END)!='Unknown' and students_marks.`Student Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' and students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Paper`='" + comboPaper.getSelectionModel().getSelectedItem()
					+ "' AND `Students Attendance`.`Class Number`=students_marks.`Class Number`"
					+ " AND `Students Attendance`.`Subject`=students_marks.`Student Subject` group by students_marks.`Class Number` ORDER BY students_marks.`Marks Obtained` DESC LIMIT "
					+ fieldGiftedLimit.getText() + "";
			// ResultSet

			System.out.println(SQL);
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

				col.setPrefWidth(160);
				tableViewAttendanceLessons.getColumns().addAll(col);
				System.out.println("Column [" + i + "] ");
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
				data.add(row);

			}

			// FINALLY ADDED TO TableView
			tableViewAttendanceLessons.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

	public void buildDataLaterAtRiskAlevel() {
		java.sql.Connection c;
		data = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "select DISTINCT students_marks.`Class Number`,students_marks.`Student Class`,students_marks.`Student Subject`,students_marks.`Student Name`,"
					+ "students_marks.`Marks Obtained`,(CASE WHEN students_marks.`Marks Obtained` BETWEEN students_grading.`From (%)` "
					+ "AND students_grading.`To (%)` AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` ELSE 'Unknown' END) AS Grade, "
					+ "students_marks.`Staff Name` from students_marks,students_grading where "
					+ "students_grading.`Subject`=students_marks.`Student Subject` and (CASE WHEN students_marks.`Marks Obtained` BETWEEN students_grading.`From (%)` "
					+ "AND students_grading.`To (%)` AND students_grading.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "' "
					+ "AND students_grading.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' THEN students_grading.`Grade` ELSE 'Unknown' END)!='Unknown' and students_marks.`Student Subject`='"
					+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' and students_marks.`Student Class`='"
					+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Test Name`='"
					+ comboBoxTermEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
					+ "' AND students_marks.`Paper`='" + comboPaper.getSelectionModel().getSelectedItem()
					+ "'  group by students_marks.`Class Number` ORDER BY students_marks.`Marks Obtained` ASC LIMIT "
					+ AtRiskLimit.getText() + "";
			// ResultSet
			System.out.println(SQL);
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/************************
			 * 
			 * First Clear the table
			 * 
			 **************************/
			tableViewCategory2.getColumns().clear();
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
				tableViewCategory2.getColumns().addAll(col);
				System.out.println("Column [" + i + "] ");
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
			tableViewCategory2.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: " + e.getMessage());
		}
	}

}
