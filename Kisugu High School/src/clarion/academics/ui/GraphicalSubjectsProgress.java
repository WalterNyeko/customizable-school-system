package clarion.academics.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import clarion.academics.ui.GraphicalStudentsAnalysis.MyTableModel;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import school.ui.finances.CashBookController;
import school.ui.tests.DisplayDatabase;

public class GraphicalSubjectsProgress extends JPanel {

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
	private String combinedSql;
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

	private Series<String, Double> dataSeries1;

	private LineChart barChart;

	private ObservableList<String> dataTerm;

	private ComboBox comboTerm;

	private ObservableList<String> dataClass;

	private ComboBox comboClass;

	protected JTable tableA;

	private ListView<String> lvListSQL;

	private ObservableList<String> itemsSQL;

	private ListView<String> lvList;

	private ObservableList<String> itemsList;

	public GraphicalSubjectsProgress() {
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
		barChart.setMinWidth(906);
		barChart.setMinHeight(428);
		barChart.setTitle("Subjects Progress Graphical Analysis");

		Button btnLoad = new Button("Load Analysis Graph");
		btnLoad.setPrefWidth(150);
		btnLoad.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				buildDataForLineChart();
			}
		});

		Button btnClear = new Button("Choose Tests");
		btnClear.setPrefWidth(150);

		btnClear.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				lvList.getItems().clear();
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
								buildDataForLineChart();
							}
						});
						dialog.dispose();

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
										tableA.setValueAt(true, row, 1);
									}
								} else {
									int rows = tableA.getRowCount();
									for (int row = 0; row < rows; row++) {
										tableA.setValueAt(false, row, 1);
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

				displayData(tableA, "select test_name from students_tests where test_name LIKE '%"
						+ comboBoxSubjectEntriesAttendanceLesson.getSelectionModel().getSelectedItem() + "'");

				dialog.add(panelSouth, BorderLayout.SOUTH);

				dialog.setVisible(true);
			}
		});

		subjects = FXCollections.observableArrayList();
		comboBoxSubjectEntriesAttendanceLesson = new ComboBox<>(subjects);
		comboBoxSubjectEntriesAttendanceLesson.setPromptText("Choose Subject");
		displayInComboBoxSubject(comboBoxSubjectEntriesAttendanceLesson,
				"select subject_name from student_subjects union select subject_name from student_subjectsa");

		dataClass = FXCollections.observableArrayList();

		comboClass = new ComboBox(dataClass);
		comboClass.setPromptText("Choose Class");
		comboClass.setPrefWidth(150);
		displayInComboBoxClass(comboBoxClassEntriesAttendanceLesson, "select class_name from student_classes");

		VBox boxPanel = new VBox(5);
		boxPanel.setPadding(new Insets(10, 10, 10, 10));
		boxPanel.setAlignment(Pos.CENTER);

		HBox boxTerm = new HBox(5);
		boxTerm.setAlignment(Pos.CENTER);
		boxTerm.getChildren().addAll(comboBoxSubjectEntriesAttendanceLesson, comboClass, btnLoad, btnClear);

		lvListSQL = new ListView<String>();
		itemsSQL = FXCollections.observableArrayList();
		lvListSQL.setItems(itemsSQL);
		lvListSQL.setMaxHeight(Control.USE_PREF_SIZE);
		lvListSQL.setMaxWidth(150);

		lvList = new ListView<String>();
		itemsList = FXCollections.observableArrayList();
		lvList.setItems(itemsList);
		lvList.setMaxHeight(Control.USE_PREF_SIZE);
		lvList.setMaxWidth(250);

		HBox boxTable = new HBox(5);
		boxTable.setAlignment(Pos.CENTER);
		boxTable.getChildren().addAll(lvList, barChart);

		boxPanel.getChildren().addAll(boxTerm, boxTable);

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

	public class MyTableModel extends DefaultTableModel {

		protected String sqlWhere;
		protected String sqlSelectAdditionalInf;
		protected String sqlSelect;
		protected String combinedList;
		protected String groupBy;
		protected String selectSql;
		protected String whereClause;

		public MyTableModel() {
			super(new String[] { "Test Name", "Choose" }, 1);
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

				if (aValue != null) {

					Platform.runLater(new Runnable() {

						

						@Override
						public void run() {
							// TODO Auto-generated method stub

							if (Boolean.TRUE.equals(aValue)) {
								lvList.getItems().add((String) getValueAt(row, 0));
								String value = "'" + (String) getValueAt(row, 0) + "'";
								lvListSQL.getItems().add(value);

								selectSql = "SELECT `Test Name`,AVG(`Marks Obtained`) from students_marks";
								whereClause = " WHERE `Student Class`='"
										+ comboClass.getSelectionModel().getSelectedItem() + "' AND (`Test Name`=";
								combinedList = lvListSQL.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(" OR `Test Name`= "));
								groupBy = ") GROUP BY `Test Name`";
								
								combinedSql=selectSql+whereClause+combinedList+groupBy;

							} else {

								lvList.getItems().remove((String) getValueAt(row, 0));
								String value = "'" + (String) getValueAt(row, 0) + "'";
								lvListSQL.getItems().remove(value);
								
								selectSql = "SELECT `Test Name`,AVG(`Marks Obtained`) from students_marks";
								whereClause = " WHERE `Student Class`='"
										+ comboClass.getSelectionModel().getSelectedItem() + "' AND (`Test Name`=";
								combinedList = lvListSQL.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(" OR `Test Name`= "));
								groupBy = ") GROUP BY `Test Name`";
								
								combinedSql=selectSql+whereClause+combinedList+groupBy;
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

	public void buildDataForLineChart() {

		barChart.getData().clear();
		barChart.layout();

		Connection c;

		// String SQL = "SELECT Name,Marks from customer";
		String SQL = combinedSql;

		dataSeries1 = new XYChart.Series<String, Double>();

		try {
			c = CashBookController.getConnection();

			ResultSet rs = c.createStatement().executeQuery(SQL);
			while (rs.next()) {

				dataSeries1.getData().add(new XYChart.Data<>(rs.getString(1), rs.getDouble(2)));
			}
			barChart.getData().add(dataSeries1);
		} catch (Exception e) {
			System.out.println("Error on Data Generate" + e.getMessage());
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
