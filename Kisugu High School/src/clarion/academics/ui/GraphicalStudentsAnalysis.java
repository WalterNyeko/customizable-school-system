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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.formula.functions.T;

import clarion.academics.ui.SubjectsProgressAnalysisData.MyTableModel;
import clarion.attendance.core.AttendanceLessons;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import school.ui.finances.CashBookController;
import school.ui.tests.TableViewWithCheckBoxes;

public class GraphicalStudentsAnalysis extends JPanel {

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
	protected String combinedSql;
	TableView<T> tableview;
	TableColumn<T, Boolean> booleanColumn;

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

	ObservableList<TableViewWithCheckBoxes> items;

	TableView<TableViewWithCheckBoxes> view;

	private ObservableList<Object> data;

	private ListView<String> lvList;

	private ObservableList<String> itemsList;

	protected JTable tableA;

	private ListView<String> lvListSQL;

	private ObservableList<String> itemsSQL;

	private TextField classNumber;

	public GraphicalStudentsAnalysis() {
		// TODO Auto-generated constructor stub

		jfxPanelAttendanceLesson = new JFXPanel();

		//////////////// Adding JavaFX BarChart/////////////////

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Student Tests");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Percentage (%)");

		barChart = new LineChart(xAxis, yAxis);
		barChart.setHorizontalGridLinesVisible(true);
		barChart.setHorizontalZeroLineVisible(true);
		barChart.setVerticalGridLinesVisible(true);
		barChart.setVerticalZeroLineVisible(true);
		barChart.setMinWidth(890);
		barChart.setMinHeight(428);
		// barChart.setTitle("Students Progress Statistics");

		classNumber = new TextField();
		classNumber.setPromptText("Student Code/Class No");

		Button btnLoad = new Button("Load Analysis Graph");
		btnLoad.setPrefWidth(200);
		btnLoad.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				buildDataForLineChart();
			}
		});

		Button btnClear = new Button("Choose Tests");
		btnClear.setPrefWidth(100);
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

				JPanel panelSouth = new JPanel();
				panelSouth.setLayout(new FlowLayout());
				panelSouth.add(btnDone);

				displayData(tableA, "select test_name from students_tests");

				dialog.add(panelSouth, BorderLayout.SOUTH);

				lvList.getItems().clear();

				dialog.setVisible(true);
			}
		});

		dataTerm = FXCollections.observableArrayList();

		comboTerm = new ComboBox(dataTerm);
		comboTerm.setPromptText("Choose Class");
		displayInComboBoxTerms(comboTerm, "select class_name from student_classes");
		comboTerm.setPrefWidth(150);

		VBox boxPanel = new VBox(5);
		boxPanel.setPadding(new Insets(10, 10, 10, 10));
		boxPanel.setAlignment(Pos.CENTER);

		HBox boxTerm = new HBox(5);
		boxTerm.setAlignment(Pos.CENTER);
		boxTerm.getChildren().addAll(classNumber, comboTerm, btnLoad, btnClear);

		HBox boxTableviewandGraph = new HBox(5);

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

		boxTableviewandGraph.getChildren().addAll(lvList, barChart);

		boxPanel.getChildren().addAll(boxTerm, boxTableviewandGraph);

		sceneAttendanceLesson = new Scene(boxPanel);

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

	public void buildDataForLineChart() {

		barChart.getData().clear();
		barChart.layout();

		System.out.println(combinedSql);
		Connection c;
		Calendar cal = Calendar.getInstance();

		String four = "" + cal.getTime();

		String answer = four.substring(four.length() - 4);

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

	public void buildDataForTableWithCheckBoxes(TableView table) {
		java.sql.Connection c;

		data = FXCollections.observableArrayList();
		try {
			c = CashBookController.getConnection();
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "select test_name from students_tests";
			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			ArrayList<String> tableTitr = new ArrayList<>(Arrays.asList("UserName"));
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(tableTitr.get(i));

				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				table.getColumns().addAll(col);
			}

			/********************************
			 * Data added to ObservableList *
			 ********************************/
			String[] rowInfo = new String[rs.getMetaData().getColumnCount() + 1];
			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					if (rs.getString(i) != null)
						rowInfo[i] = rs.getString(i);

					else
						rowInfo[i] = " ";

					row.add(rowInfo[i]);
				}

				data.add(row);
			}

			// FINALLY ADDED TO TableView
			table.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}

	}

	public static class cours {
		private StringProperty cours;
		private StringProperty coursID;
		private BooleanProperty checked;

		public cours(String cours, String coursID) {
			this.cours = new SimpleStringProperty(cours);
			this.coursID = new SimpleStringProperty(coursID);
			this.checked = new SimpleBooleanProperty(false);
		}

		public String getCours() {
			return cours.get();
		}

		public String getCoursID() {
			return coursID.get();
		}

		public boolean isChecked() {
			return checked.get();
		}

		public void setCours(String cours) {
			this.cours.set(cours);
		}

		public void setCoursID(String coursID) {
			this.coursID.set(coursID);
		}

		public void setChecked(boolean checked) {
			this.checked.set(checked);
		}

		public StringProperty coursProperty() {
			return cours;
		}

		public StringProperty coursIDProperty() {
			return coursID;
		}

		public BooleanProperty checkedProperty() {
			return checked;
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

								String sqlSelect = "SELECT `Test Name`,`Marks Obtained`";

								String sqlFrom = " ";

								if (comboTerm.getSelectionModel().getSelectedIndex() <= 3) {

									sqlFrom = " FROM students_marks WHERE `Class Number`='" + classNumber.getText()
											+ "' AND (`Test Name`=";

								} else {
									sqlFrom = " FROM students_marks WHERE `Class Number`='" + classNumber.getText()
											+ "' AND `Paper`='1' AND (`Test Name`=";

								}

								combinedList = lvListSQL.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(" OR `Test Name`= "));
								groupBy = ") group by `Test Name`";
								combinedSql = sqlSelect + sqlFrom + combinedList + groupBy;

								System.out.println(combinedSql);
							} else {

								lvList.getItems().remove((String) getValueAt(row, 0));

								String sqlSelect = "SELECT `Test Name`,`Marks Obtained`";

								String sqlFrom = " ";

								if (comboTerm.getSelectionModel().getSelectedIndex() <= 3) {

									sqlFrom = " FROM students_marks WHERE `Class Number`='" + classNumber.getText()
											+ "' AND (`Test Name`=";

								} else {
									sqlFrom = " FROM students_marks WHERE `Class Number`='" + classNumber.getText()
											+ "' AND `Paper`='1' AND (`Test Name`=";

								}

								combinedList = lvListSQL.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(" OR `Test Name`= "));
								groupBy = ") group by `Test Name`";
								combinedSql = sqlSelect + sqlFrom + combinedList + groupBy;

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
}
