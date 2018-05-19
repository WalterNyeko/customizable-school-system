
package clarion.academics.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import clarion.academics.ui.SubjectsProgressAnalysisData.MyTableModel;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import school.ui.finances.CashBookController;

public class StudentsProgressAnalysisData extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Attendance Entry's controls
	 */
	private JFXPanel jfxPanelAttendanceLesson;

	private Label labelClassEntriesAttendanceLesson;
	private Label labelTimeEntriesAttendanceLesson;
	private Label labelSubjectEntriesAttendanceLesson;

	private DatePicker datePickerEntriesAttendanceLesson;
	private ComboBox<?> comboBoxNameEntriesAttendanceLesson;
	private ComboBox<?> comboBoxClassEntriesAttendanceLesson;
	private ComboBox<?> comboBoxSubjectEntriesAttendanceLesson;
	private TextField fieldTimeEntriesAttendanceLesson;
	private ComboBox<?> comboBoxTermEntriesAttendanceLesson;

	private GridPane gridPaneEntriesAttendanceLesson;
	private VBox vBoxEntriesAttendanceLesson;
	private String finalSql;
	private String combinedSql;
	/*
	 * Save or Clear Fields
	 */
	private Button buttonSaveAttendanceLesson;
	private Label labelCancelAttendanceLesson;
	private Button buttonUpdateAttendanecLesson;
	private Button buttonPickAttendanceLesson;

	/*
	 * Search Fields
	 */

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

	private HBox vBoxTableAttendanceLesson;

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

	private ObservableList dataTerm;

	private ComboBox comboTerm;

	private ListView<String> lvList;

	private ObservableList<String> items;

	private ListView<String> lvListDisplayed;

	private ObservableList<String> itemsDisplayed;

	private ListView<String> lvListSQL;

	private ObservableList<String> itemsSQL;

	private Button btnChooseTests;

	private Image image;

	private ImageView finalViewedImage;

	private ObservableList data;

	protected JTable tableA;

	private ObservableList<String> paper;

	private ComboBox comboPaper;

	public StudentsProgressAnalysisData() {
		// TODO Auto-generated constructor stub

		jfxPanelAttendanceLesson = new JFXPanel();
		borderPaneAttendanceLesson = new BorderPane();

		// vBoxTop = new VBox();
		// borderPaneAttendanceLesson.setTop(vBoxTop);

		tableViewAttendanceLessons = new TableView<>();
		tableViewAttendanceLessons.setTableMenuButtonVisible(true);

		lvList = new ListView<String>();
		items = FXCollections.observableArrayList();
		lvList.setItems(items);
		lvList.setMaxHeight(Control.USE_PREF_SIZE);
		lvList.setMaxWidth(150);

		lvListDisplayed = new ListView<String>();
		itemsDisplayed = FXCollections.observableArrayList();
		lvListDisplayed.setItems(itemsDisplayed);
		lvListDisplayed.setMaxHeight(Control.USE_PREF_SIZE);
		lvListDisplayed.setMinWidth(250);

		lvListSQL = new ListView<String>();
		itemsSQL = FXCollections.observableArrayList();
		lvListSQL.setItems(itemsSQL);
		lvListSQL.setMaxHeight(Control.USE_PREF_SIZE);
		lvListSQL.setMaxWidth(150);

		VBox boxTables = new VBox(5);
		boxTables.setAlignment(Pos.CENTER);
		boxTables.getChildren().addAll(tableViewAttendanceLessons);

		vBoxTableAttendanceLesson = new HBox();
		vBoxTableAttendanceLesson.getChildren().addAll(lvListDisplayed, boxTables);

		borderPaneAttendanceLesson.setCenter(vBoxTableAttendanceLesson);

		// hBoxAttendanceLesson = new HBox();

		/*
		 * Entries Control Start Here
		 */
		labelClassEntriesAttendanceLesson = new Label("Student Class:");
		labelSubjectEntriesAttendanceLesson = new Label("Subject:");
		labelTimeEntriesAttendanceLesson = new Label("Student Code/Class No:");

		datePickerEntriesAttendanceLesson = new DatePicker();

		staffs = FXCollections.observableArrayList();
		comboBoxNameEntriesAttendanceLesson = new ComboBox<>(staffs);
		comboBoxNameEntriesAttendanceLesson.setPromptText("Choose Name");
		displayInComboBoxStaffs(comboBoxNameEntriesAttendanceLesson, "select staff_name from table_teaching_staffs");

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

		dataTerm = FXCollections.observableArrayList();

		comboTerm = new ComboBox(dataTerm);
		comboTerm.setPromptText("Choose Test");
		displayInComboBoxTerms(comboTerm, "select test_name from students_tests");
		comboTerm.setPrefWidth(150);

		paper = FXCollections.observableArrayList("1", "2", "3");
		comboPaper = new ComboBox<>(paper);
		comboPaper.setPromptText("Choose Paper");
		comboPaper.setVisible(false);

		subjects = FXCollections.observableArrayList();
		comboBoxSubjectEntriesAttendanceLesson = new ComboBox<>(subjects);
		comboBoxSubjectEntriesAttendanceLesson.setPromptText("Choose Subject");
		displayInComboBoxSubject(comboBoxSubjectEntriesAttendanceLesson,
				"select subject_name from student_subjects union select subject_name from student_subjectsa");

		fieldTimeEntriesAttendanceLesson = new TextField();
		fieldTimeEntriesAttendanceLesson.setPromptText("Student Code/Class No");

		buttonSaveAttendanceLesson = new Button("Add Recommendation ");
		labelCancelAttendanceLesson = new Label("Students Name:");
		buttonUpdateAttendanecLesson = new Button("Save Student's Marks");

		btnChooseTests = new Button("Choose Students Tests");

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

		gridPaneEntriesAttendanceLesson.add(labelClassEntriesAttendanceLesson, 1, 0);
		gridPaneEntriesAttendanceLesson.add(labelSubjectEntriesAttendanceLesson, 2, 0);
		gridPaneEntriesAttendanceLesson.add(labelTimeEntriesAttendanceLesson, 0, 0);

		gridPaneEntriesAttendanceLesson.add(comboBoxClassEntriesAttendanceLesson, 1, 1);
		gridPaneEntriesAttendanceLesson.add(comboBoxSubjectEntriesAttendanceLesson, 2, 1);
		gridPaneEntriesAttendanceLesson.add(fieldTimeEntriesAttendanceLesson, 0, 1);
		gridPaneEntriesAttendanceLesson.add(comboPaper, 5, 1);
		gridPaneEntriesAttendanceLesson.add(btnChooseTests, 6, 1);

		gridPaneEntriesAttendanceLesson.add(labelCancelAttendanceLesson, 6, 2);

		/*
		 * Adding Entries controls to Holders End Here
		 */

		tableViewAttendanceLessons.setId("table_attendance");

		hboxHomeMain = new HBox(2);
		vboxLabelPicture = new VBox(2);
		vboxLabelPictureReal = new VBox(2);

		vboxLabelPicture.setPadding(new Insets(10, 10, 10, 10));
		labelPictureStudent = new Label("");
		vboxLabelPictureReal.setPrefSize(150, 145);
		vboxLabelPictureReal.setStyle("-fx-border-color: white");
		vboxLabelPicture.getChildren().add(vboxLabelPictureReal);

		hboxHomeMain.getChildren().addAll(gridPaneEntriesAttendanceLesson, vboxLabelPicture);

		borderPaneAttendanceLesson.setTop(hboxHomeMain);

		/*********************************************
		 * Choose Test Listener
		 *********************************************/

		btnChooseTests.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				lvList.getItems().clear();
				lvListDisplayed.getItems().clear();
				lvListSQL.getItems().clear();

				JDialog dialog = new JDialog();
				dialog.setTitle("Choose Tests");
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setSize(400, 750);
				dialog.setLocation(800, 0);
				dialog.setLayout(new BorderLayout());

				MyTableModel modelA = new MyTableModel();
				modelA.addRow(new Object[] { "BoT II 2018", false });
				modelA.addRow(new Object[] { "MoT II 2018", false });
				modelA.addRow(new Object[] { "EoT II 2018", false });
				modelA.addRow(new Object[] { "Remedial Test", false });
				modelA.addRow(new Object[] { "BoT II 2018", false });
				modelA.addRow(new Object[] { "MoT II 2018", false });
				modelA.addRow(new Object[] { "EoT II 2018", false });
				modelA.addRow(new Object[] { "Remedial Test", false });
				modelA.addRow(new Object[] { "BoT II 2018", false });

				tableA = new JTable(modelA);
				tableA.setFont(new Font("Times New Roman", Font.ITALIC, 17));
				tableA.setRowHeight(30);
				tableA.getColumnModel().getColumn(0).setPreferredWidth(180);

				JScrollPane scroller = new JScrollPane(tableA);

				dialog.add(scroller, BorderLayout.CENTER);

				JButton btnDone = new JButton("Done");
				btnDone.setPreferredSize(new Dimension(100, 25));
				btnDone.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(java.awt.event.ActionEvent arg0) {
						// TODO Auto-generated method stub

						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								if (comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedIndex() <= 3) {
									buildDataForTable();
								} else {
									buildDataForTableAlevel();
								}
							}
						});
						dialog.dispose();

					}
				});

				JPanel panelSouth = new JPanel();
				panelSouth.setLayout(new FlowLayout());
				panelSouth.add(btnDone);

				displayData(tableA, "select test_name from students_tests");

				dialog.add(panelSouth, BorderLayout.SOUTH);

				lvList.getItems().clear();
				lvListDisplayed.getItems().clear();
				lvListSQL.getItems().clear();

				dialog.setVisible(true);
			}
		});

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

	public class MyTableModel extends DefaultTableModel {

		protected String sqlWhere;

		public MyTableModel() {
			super(new String[] { "Test Name", "Choose" }, 0);
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

				if (aValue != null) {

					Platform.runLater(new Runnable() {

						private String sqlSelect;

						@Override
						public void run() {
							// TODO Auto-generated method stub

							if (Boolean.TRUE.equals(aValue)) {
								lvList.getItems().clear();
								lvList.getItems().add("");
								lvListDisplayed.getItems().add((String) getValueAt(row, 0));
								

								String sqlFrom = " ";

								sqlFrom = " FROM students_marks ";

								if (comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedIndex() <= 3) {
								
									
									 sqlSelect = "SELECT  (SELECT `Marks Obtained` from students_marks where `Class Number`='"
											+ fieldTimeEntriesAttendanceLesson.getText() + "' and `Test Name`='"
											+ (String) getValueAt(row, 0) + "' and `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null) AS 'Marks Obtained',"
											+ "AVG(`Marks Obtained`) AS 'Class Average Marks',(SELECT MAX(`Marks Obtained`) from students_marks where `Test Name`='"
											+ (String) getValueAt(row, 0) + "' AND `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null) As 'Best Student',"
											+ "(SELECT MIN(`Marks Obtained`) from students_marks where `Test Name`='"
											+ (String) getValueAt(row, 0) + "' AND `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null) As 'Worst Student',(SELECT COUNT(*) FROM students_marks WHERE `Test Name`='"
											+ (String) getValueAt(row, 0) + "' " + "AND `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null) AS 'Total Population',(SELECT 1+(SELECT COUNT(*) FROM students_marks a WHERE a.`Marks Obtained`>b.`Marks Obtained` "
											+ "AND a.`Test Name`='" + (String) getValueAt(row, 0) + "') "
											+ "AS rank FROM students_marks b WHERE `Class Number`='"
											+ fieldTimeEntriesAttendanceLesson.getText() + "' AND b.`Test Name`='"
											+ (String) getValueAt(row, 0) + "' AND b.`Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null ORDER BY rank LIMIT 1) AS 'Student Rank'";
									
									sqlWhere = " WHERE `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' and `Student Subject`='"
											+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel()
													.getSelectedItem()
											+ "' AND `Test Name`='" + (String) getValueAt(row, 0)
											+ "'  AND `Marks Obtained` is not null";

								} else {
									
									 sqlSelect = "SELECT  (SELECT `Marks Obtained` from students_marks where `Class Number`='"
											+ fieldTimeEntriesAttendanceLesson.getText() + "' and `Test Name`='"
											+ (String) getValueAt(row, 0) + "' and `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null AND students_marks.`Paper`='"
											+ comboPaper.getSelectionModel().getSelectedItem() + "') AS 'Marks Obtained',"
											+ "AVG(`Marks Obtained`) AS 'Class Average Marks',(SELECT MAX(`Marks Obtained`) from students_marks where `Test Name`='"
											+ (String) getValueAt(row, 0) + "' AND `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null AND students_marks.`Paper`='"
											+ comboPaper.getSelectionModel().getSelectedItem() + "') As 'Best Student',"
											+ "(SELECT MIN(`Marks Obtained`) from students_marks where `Test Name`='"
											+ (String) getValueAt(row, 0) + "' AND `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null AND students_marks.`Paper`='"
											+ comboPaper.getSelectionModel().getSelectedItem()
											+ "') As 'Worst Student',(SELECT COUNT(*) FROM students_marks WHERE `Test Name`='"
											+ (String) getValueAt(row, 0) + "' " + "AND `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null AND students_marks.`Paper`='"
											+ comboPaper.getSelectionModel().getSelectedItem()
											+ "') AS 'Total Population',(SELECT 1+(SELECT COUNT(*) FROM students_marks a WHERE a.`Marks Obtained`>b.`Marks Obtained` "
											+ "AND a.`Test Name`='" + (String) getValueAt(row, 0) + "') "
											+ "AS rank FROM students_marks b WHERE `Class Number`='"
											+ fieldTimeEntriesAttendanceLesson.getText() + "' AND b.`Test Name`='"
											+ (String) getValueAt(row, 0) + "' AND b.`Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null AND students_marks.`Paper`='"
											+ comboPaper.getSelectionModel().getSelectedItem()
											+ "' ORDER BY rank LIMIT 1) AS 'Student Rank'";

									sqlWhere = " WHERE `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' and `Student Subject`='"
											+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel()
													.getSelectedItem()
											+ "' AND `Test Name`='" + (String) getValueAt(row, 0)
											+ "'  AND `Marks Obtained` is not null AND students_marks.`Paper`='"
											+ comboPaper.getSelectionModel().getSelectedItem() + "'";

								}

								finalSql = sqlSelect + sqlFrom + sqlWhere;

								lvListSQL.getItems().add(finalSql);
								combinedSql = lvListSQL.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(" UNION "));

								System.out.println(combinedSql);

							} else {
								lvList.getItems().clear();
								lvListDisplayed.getItems().remove((String) getValueAt(row, 0));
								String sqlFrom = " ";

								sqlFrom = " FROM students_marks ";

								if (comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedIndex() <= 3) {
								
									
									 sqlSelect = "SELECT  (SELECT `Marks Obtained` from students_marks where `Class Number`='"
											+ fieldTimeEntriesAttendanceLesson.getText() + "' and `Test Name`='"
											+ (String) getValueAt(row, 0) + "' and `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null) AS 'Marks Obtained',"
											+ "AVG(`Marks Obtained`) AS 'Class Average Marks',(SELECT MAX(`Marks Obtained`) from students_marks where `Test Name`='"
											+ (String) getValueAt(row, 0) + "' AND `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null) As 'Best Student',"
											+ "(SELECT MIN(`Marks Obtained`) from students_marks where `Test Name`='"
											+ (String) getValueAt(row, 0) + "' AND `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null) As 'Worst Student',(SELECT COUNT(*) FROM students_marks WHERE `Test Name`='"
											+ (String) getValueAt(row, 0) + "' " + "AND `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null) AS 'Total Population',(SELECT 1+(SELECT COUNT(*) FROM students_marks a WHERE a.`Marks Obtained`>b.`Marks Obtained` "
											+ "AND a.`Test Name`='" + (String) getValueAt(row, 0) + "') "
											+ "AS rank FROM students_marks b WHERE `Class Number`='"
											+ fieldTimeEntriesAttendanceLesson.getText() + "' AND b.`Test Name`='"
											+ (String) getValueAt(row, 0) + "' AND b.`Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null ORDER BY rank LIMIT 1) AS 'Student Rank'";
									
									sqlWhere = " WHERE `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' and `Student Subject`='"
											+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel()
													.getSelectedItem()
											+ "' AND `Test Name`='" + (String) getValueAt(row, 0)
											+ "'  AND `Marks Obtained` is not null";

								} else {
									
									 sqlSelect = "SELECT  (SELECT `Marks Obtained` from students_marks where `Class Number`='"
											+ fieldTimeEntriesAttendanceLesson.getText() + "' and `Test Name`='"
											+ (String) getValueAt(row, 0) + "' and `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null AND students_marks.`Paper`='"
											+ comboPaper.getSelectionModel().getSelectedItem() + "') AS 'Marks Obtained',"
											+ "AVG(`Marks Obtained`) AS 'Class Average Marks',(SELECT MAX(`Marks Obtained`) from students_marks where `Test Name`='"
											+ (String) getValueAt(row, 0) + "' AND `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null AND students_marks.`Paper`='"
											+ comboPaper.getSelectionModel().getSelectedItem() + "') As 'Best Student',"
											+ "(SELECT MIN(`Marks Obtained`) from students_marks where `Test Name`='"
											+ (String) getValueAt(row, 0) + "' AND `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null AND students_marks.`Paper`='"
											+ comboPaper.getSelectionModel().getSelectedItem()
											+ "') As 'Worst Student',(SELECT COUNT(*) FROM students_marks WHERE `Test Name`='"
											+ (String) getValueAt(row, 0) + "' " + "AND `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null AND students_marks.`Paper`='"
											+ comboPaper.getSelectionModel().getSelectedItem()
											+ "') AS 'Total Population',(SELECT 1+(SELECT COUNT(*) FROM students_marks a WHERE a.`Marks Obtained`>b.`Marks Obtained` "
											+ "AND a.`Test Name`='" + (String) getValueAt(row, 0) + "') "
											+ "AS rank FROM students_marks b WHERE `Class Number`='"
											+ fieldTimeEntriesAttendanceLesson.getText() + "' AND b.`Test Name`='"
											+ (String) getValueAt(row, 0) + "' AND b.`Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' AND `Marks Obtained` is not null AND students_marks.`Paper`='"
											+ comboPaper.getSelectionModel().getSelectedItem()
											+ "' ORDER BY rank LIMIT 1) AS 'Student Rank'";

									sqlWhere = " WHERE `Student Class`='"
											+ comboBoxClassEntriesAttendanceLesson.getSelectionModel().getSelectedItem()
											+ "' and `Student Subject`='"
											+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel()
													.getSelectedItem()
											+ "' AND `Test Name`='" + (String) getValueAt(row, 0)
											+ "'  AND `Marks Obtained` is not null AND students_marks.`Paper`='"
											+ comboPaper.getSelectionModel().getSelectedItem() + "'";

								}

								finalSql = sqlSelect + sqlFrom + sqlWhere;

								lvListSQL.getItems().add(finalSql);
								combinedSql = lvListSQL.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(" UNION "));

								System.out.println(combinedSql);

							}

						}
					});

				}

				Vector rowData = (Vector) getDataVector().get(row);
				rowData.set(1, (boolean) aValue);
				fireTableCellUpdated(row, column);

				System.out.println(getValueAt(row, 0));

			}
		}

		@Override
		public Object getValueAt(int row, int column) {

			return super.getValueAt(row, column);
		}

	}

	protected void displayStudentPicture() {

		try {

			java.sql.Connection conn = CashBookController.getConnection();

			Calendar cal = Calendar.getInstance();

			String four = "" + cal.getTime();

			String answer = four.substring(four.length() - 4);

			PreparedStatement pst = conn.prepareStatement(
					"select students_info.photo,student_ledger.student_name from student_ledger,students_info where (student_ledger.class_number='"
							+ fieldTimeEntriesAttendanceLesson.getText() + "' or student_ledger.payment_code= '"
							+ fieldTimeEntriesAttendanceLesson.getText() + "') and student_ledger.year = '" + answer
							+ "' and students_info.payment_code=student_ledger.payment_code group by students_info.payment_code");
			System.out.println(answer);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {

				String name = rs.getString("student_ledger.student_name");
				labelCancelAttendanceLesson.setText(name);
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

			} else {
				vboxLabelPictureReal.getChildren().remove(finalViewedImage);
				labelCancelAttendanceLesson.setText("Student Name:");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void buildDataForTable() {
		java.sql.Connection c;
		data = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = combinedSql;
			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/**********************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 **********************************/
			tableViewAttendanceLessons.getColumns().clear();
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

				col.setPrefWidth(150);
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

				data.clear();
				data.add(row);

			}

			// FINALLY ADDED TO TableView
			tableViewAttendanceLessons.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

	public void buildDataForTableAlevel() {
		java.sql.Connection c;
		data = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = combinedSql;
			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/**********************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 **********************************/
			tableViewAttendanceLessons.getColumns().clear();
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

				col.setPrefWidth(150);
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

				data.clear();
				data.add(row);

			}

			// FINALLY ADDED TO TableView
			tableViewAttendanceLessons.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
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

}
