package school.ui.reports;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import net.proteanit.sql.DbUtils;
import school.ui.finances.CashBookController;

public class AdminReportPanel extends JPanel {

	////////////////////// Dynamic SQL///////////////////////
	private ListView<String> lvList;
	private ObservableList<String> items;

	private ObservableList<ObservableList> dataTableView;
	private TableView tableview;

	////////////////// Progress Bar Stuffs//////////////////
	final Float[] values = new Float[] { -2f };
	final Label[] labels = new Label[values.length];
	final ProgressBar[] pbs = new ProgressBar[values.length];
	final ProgressIndicator[] pins = new ProgressIndicator[values.length];
	final HBox hbs[] = new HBox[values.length];
	String finalSql;

	private String[] columnNamesArr;
	private String[][] dataOfJTable;
	private ArrayList columnNamesList;

	private final Node rootIcon = new ImageView(new Image(getClass().getResourceAsStream("root.jpg")));

	List<Employee> employees = Arrays.<Employee>asList(new Employee("Students Dismissal Reports", "Students Reports"),
			new Employee("Students Transfer Reports", "Students Reports"),
			new Employee("Students Suspension Reports", "Students Reports"),
			new Employee("Fees Defaulters Report", "Students Reports"),
			new Employee("Students Health Reports", "Students Reports"),
			new Employee("Lost Books Reports", "Library Reports"),
			new Employee("Books Entry Reports", "Library Reports"),
			new Employee("Unreturned Books Reports", "Library Reports"),
			new Employee("Issued Books Reports", "Library Reports"),
			new Employee("Lesson Attendance Reports", "Staffs Reports"),
			new Employee("Staff Transfer Reports", "Staffs Reports"),
			new Employee("Duty Schedules Reports", "Staffs Reports"),
			new Employee("Staff Subject Performance Reports", "Staffs Reports"),
			new Employee("Staff Responsibilities Reports", "Staffs Reports"),
			new Employee("Subject Teachers Reports", "Staffs Reports"),
			new Employee("Best Students Performance Reports", "Academics Reports"),
			new Employee("Worst Students Performance Reports", "Academics Reports"),
			new Employee("Performance Comparison Reports", "Academics Reports"),
			new Employee("Students Results Per Class", "Academics Reports"),
			new Employee("Grades Reports", "Academics Reports"),
			new Employee("Laboratory Clearance Reports", "Clearance Reports"),
			new Employee("Library Clearance Reports", "Clearance Reports"),
			new Employee("Dispensary Clearance Reports", "Clearance Reports"),
			new Employee("Dormitory Clearance Reports", "Clearance Reports"),
			new Employee("School Fees Clearance Reports", "Clearance Reports"),
			new Employee("Stock Purchases Reports", "Stock Inventory Level Reports"),
			new Employee("Stock issuance Reports", "Stock Inventory Level Reports"),
			new Employee("Stock Sales Reports", "Stock Inventory Level Reports"),
			new Employee("Inventory Level Reports", "Stock Inventory Level Reports"));
	TreeItem<String> rootNode = new TreeItem<String>("SCHOOL ADMINISTRATIVE REPORTS", rootIcon);
	private GridPane gridPaneFilter;
	private JFXPanel jfxPanel;
	private Scene scene;
	private TitledPane gridTitlePane;
	private TitledPane gridTitlePaneSMS;
	private TitledPane gridTitlePaneFields;
	private TabPane tabPane;
	private TabPane tabPaneStudents;
	private TabPane tabPaneLibrary;
	private TabPane tabPaneStaffs;
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private ObservableList<String> data = FXCollections.observableArrayList();
	private ObservableList<String> TableViewTitles = FXCollections.observableArrayList();
	protected String joinedTitles;

	private ObservableList<String> titleFinal = FXCollections.observableArrayList();
	private Object colOfTable;
	private JFrame dialogReport;
	private DefaultTableModel defaultTableModel;
	private JTable table;
	private TableColumnModel tableColumnModel;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JDialog mainFrame;
	private JPanel panelButton;
	private JCheckBox col1;
	private JButton addButton;
	private ListView<String> lvListDisplayed;
	private ObservableList<String> itemsDisplayed;
	private Button btnRun;
	private Button btnRunTermly;
	private ObservableList<String> observableTerms;
	private GridPane gridTermly;
	private GridPane grid;
	private String year;
	private DatePicker datePkYear;
	private ComboBox comboClassTermly;
	private ComboBox term;
	private ComboBox combo;

	public AdminReportPanel() throws ClassNotFoundException, SQLException {

		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				
				
				////////////////////////////////////////////////////////////
				columnNamesList = new ArrayList<>();

				dataOfJTable = new String[1][columnNamesList.size()];

				columnNamesArr = new String[columnNamesList.size()];
				for (int i = 0; i < columnNamesList.size(); i++) {
					columnNamesArr[i] = (String) columnNamesList.get(i);
					dataOfJTable[0][i] = "";
				}

				defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

				table = new JTable(defaultTableModel);
				tableColumnModel = table.getColumnModel();

				for (int i = 0; i < columnNamesList.size(); i++) {
					tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
				}
				table.setPreferredScrollableViewportSize(table.getPreferredSize());
				scrollPane = new JScrollPane(table);
				
				lvList = new ListView<String>();
				items = FXCollections.observableArrayList();
				lvList.setItems(items);
				lvList.setMaxHeight(Control.USE_PREF_SIZE);
				lvList.setMaxWidth(150);

				lvListDisplayed = new ListView<String>();
				itemsDisplayed = FXCollections.observableArrayList();
				lvListDisplayed.setItems(itemsDisplayed);
				lvListDisplayed.setMaxHeight(Control.USE_PREF_SIZE);
				lvListDisplayed.setMaxWidth(170);
				
				final Image depIcon = new Image(getClass().getResourceAsStream("department.jpg"));

				rootNode.setExpanded(true);
				for (Employee employee : employees) {
					TreeItem<String> empLeaf = new TreeItem<String>(employee.getName());
					boolean found = false;
					for (TreeItem<String> depNode : rootNode.getChildren()) {
						if (depNode.getValue().contentEquals(employee.getDepartment())) {
							depNode.getChildren().add(empLeaf);
							found = true;
							break;
						}
					}
					if (!found) {
						TreeItem<String> depNode = new TreeItem<String>(employee.getDepartment(), new ImageView(depIcon));
						rootNode.getChildren().add(depNode);
						depNode.getChildren().add(empLeaf);
					}
				}

				VBox box = new VBox();

				TreeView<String> treeView = new TreeView<String>(rootNode);
				treeView.setEditable(false);
				treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
					@Override
					public TreeCell<String> call(TreeView<String> p) {
						return new TextFieldTreeCellImpl();
					}
				});

				box.getChildren().add(treeView);
				treeView.setPrefHeight(1000);
				treeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						if (mouseEvent.getClickCount() == 1) {
							TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();

							if (item.getValue().equals("Students Transfer Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(0);
								tabPaneStudents.getSelectionModel().select(2);
								gridTermly.setVisible(false);
								grid.setVisible(true);
								combo.setVisible(true);
							} else if (item.getValue().equals("Students Suspension Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(0);
								tabPaneStudents.getSelectionModel().select(1);
								gridTermly.setVisible(false);
								grid.setVisible(true);
								combo.setVisible(true);
							} else if (item.getValue().equals("Fees Defaulters Report")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(0);
								tabPaneStudents.getSelectionModel().select(3);
								gridTermly.setVisible(true);
								grid.setVisible(false);
								combo.setVisible(true);
							} else if (item.getValue().equals("Students Dismissal Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(0);
								tabPaneStudents.getSelectionModel().select(0);
								gridTermly.setVisible(false);
								grid.setVisible(true);
								combo.setVisible(true);
							} else if (item.getValue().equals("Students Health Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(0);
								tabPaneStudents.getSelectionModel().select(4);
								gridTermly.setVisible(false);
								grid.setVisible(true);
								combo.setVisible(true);
							} else if (item.getValue().equals("Lost Books Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(2);
								tabPaneLibrary.getSelectionModel().select(3);
								gridTermly.setVisible(false);
								grid.setVisible(true);
								combo.setVisible(true);
							} else if (item.getValue().equals("Books Entry Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(2);
								tabPaneLibrary.getSelectionModel().select(0);
								gridTermly.setVisible(false);
								grid.setVisible(true);
								combo.setVisible(false);
							} else if (item.getValue().equals("Issued Books Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(2);
								tabPaneLibrary.getSelectionModel().select(1);
								gridTermly.setVisible(false);
								grid.setVisible(true);
								combo.setVisible(true);
							}

							else if (item.getValue().equals("Unreturned Books Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(2);
								tabPaneLibrary.getSelectionModel().select(2);
								gridTermly.setVisible(false);
								grid.setVisible(true);
								combo.setVisible(true);
							} else if (item.getValue().equals("Lesson Attendance Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(1);
								tabPaneStaffs.getSelectionModel().select(0);
								gridTermly.setVisible(false);
								grid.setVisible(true);
							} else if (item.getValue().equals("Staff Transfer Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(1);
								tabPaneStaffs.getSelectionModel().select(1);
								gridTermly.setVisible(false);
								grid.setVisible(true);
								combo.setVisible(true);
							} else if (item.getValue().equals("Duty Schedules Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(1);
								tabPaneStaffs.getSelectionModel().select(2);
								gridTermly.setVisible(false);
								grid.setVisible(true);
								combo.setVisible(true);
							} else if (item.getValue().equals("Staff Subject Performance Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(1);
								tabPaneStaffs.getSelectionModel().select(3);
								gridTermly.setVisible(false);
								grid.setVisible(true);
								combo.setVisible(true);
							} else if (item.getValue().equals("Staff Responsibilities Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(1);
								tabPaneStaffs.getSelectionModel().select(4);
								gridTermly.setVisible(false);
								grid.setVisible(true);
								combo.setVisible(true);
							} else if (item.getValue().equals("Subject Teachers Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);
								tabPane.getSelectionModel().select(1);
								tabPaneStaffs.getSelectionModel().select(5);
								gridTermly.setVisible(false);
								grid.setVisible(true);
								combo.setVisible(true);
							} else if (item.getValue().equals("Best Students Performance Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);

							} else if (item.getValue().equals("Worst Students Performance Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);

							} else if (item.getValue().equals("Performance Comparison Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);

							} else if (item.getValue().equals("Grades Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);

							} else if (item.getValue().equals("Laboratory Clearance Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);

							} else if (item.getValue().equals("Dispensary Clearance Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);

							} else if (item.getValue().equals("Library Clearance Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);

							} else if (item.getValue().equals("Dormitory Clearance Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);

							} else if (item.getValue().equals("School Fees Clearance Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);

							} else if (item.getValue().equals("Stock Purchases Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);

							} else if (item.getValue().equals("Stock issuance Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);

							} else if (item.getValue().equals("Stock Sales Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);

							} else if (item.getValue().equals("Inventory Level Reports")) {

								gridTitlePane.setExpanded(true);
								gridTitlePaneSMS.setExpanded(true);

							}
						}
					}
				});
				////////////////// Progress Bar//////////////////////

				for (int i = 0; i < values.length; i++) {
					final Label label = labels[i] = new Label();

					final ProgressBar pb = pbs[i] = new ProgressBar();
					pb.setProgress(values[i]);

					final ProgressIndicator pin = pins[i] = new ProgressIndicator();
					pin.setProgress(values[i]);
					final HBox hb = hbs[i] = new HBox();
					hb.setSpacing(1);
					hb.setAlignment(Pos.CENTER);
					hb.getChildren().addAll(label, pb, pin);
				}

				///////////////////////////////////////////////////////////////

				///////////// TitledPane-Content For Reports That Can Be Run With
				///////////// Dates///////////////////////////

				gridTitlePane = new TitledPane();
				grid = new GridPane();
				grid.setVgap(10);
				grid.setAlignment(Pos.CENTER);
				grid.setPadding(new Insets(5, 5, 5, 5));

				DatePicker datePk = new DatePicker();
				Label labelFrom = new Label("Start Date: ");
				grid.add(labelFrom, 0, 0);
				grid.add(datePk, 1, 0);

				DatePicker datePkToDate = new DatePicker();

				Label labelTo = new Label("End Date: ");
				grid.add(labelTo, 0, 1);
				grid.add(datePkToDate, 1, 1);

				combo = new ComboBox(data);
				combo.setPrefWidth(200);

				combo.setOnAction(new EventHandler<Event>() {

					@Override
					public void handle(Event arg0) {
						// TODO Auto-generated method stub

						if (tabPane.getSelectionModel().getSelectedIndex() == 0) {//////////////// Students

							if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 0) {/////////// Dismissed Students
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;

							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 1) {////////// Suspended
																										////////// Students

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;

							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 2) {////////// Transferred
																										////////// Students

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;

							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 4) {////////// Students Health
																										////////// Report

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
								System.out.println(finalSql);
							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 3) {////////// Fees Defaulters

								grid.setVisible(false);
								gridTermly.setVisible(true);

							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() != 3) {
								grid.setVisible(true);
								gridTermly.setVisible(false);
							}

						} else if (tabPane.getSelectionModel().getSelectedIndex() == 1) {//////////////// Staffs Reports

						} else if (tabPane.getSelectionModel().getSelectedIndex() == 2) {//////////////// Library Reports

							if (tabPaneLibrary.getSelectionModel().getSelectedIndex() == 2) {//////// Unreturned Books

								String sqlSelect = "SELECT ";
								String sqlFrom = " ";
								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
								sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
								String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0";
								finalSql = sqlSelect + sqlFrom + sqlWhere;
								System.out.println(finalSql);
							} else if (tabPaneLibrary.getSelectionModel().getSelectedIndex() == 3) {///// Lost Books

								String sqlSelect = "SELECT ";
								String sqlFrom = " ";
								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
								sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
								String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 and TIMESTAMPDIFF(DAY,issued_books.issue_date,NOW())>365";
								finalSql = sqlSelect + sqlFrom + sqlWhere;
								System.out.println(finalSql);
							}else if (tabPaneLibrary.getSelectionModel().getSelectedIndex() == 1) {///////// All Issued Books

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

								String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
								System.out.println(finalSql);
							}

						} else if (tabPane.getSelectionModel().getSelectedIndex() == 3) {//////////////// Clearance Reports

						} else if (tabPane.getSelectionModel().getSelectedIndex() == 4) {//////////////// Finance Reports

						}
					}
				});

				datePk.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (tabPane.getSelectionModel().getSelectedIndex() == 0) {//////////////// Students

							if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 0) {/////////// Dismissed Students
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;

							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 1) {////////// Suspended
																										////////// Students

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;

							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 2) {////////// Transferred
																										////////// Students

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;

							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 4) {////////// Students Health
																										////////// Report

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
								System.out.println(finalSql);
							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 3) {////////// Fees Defaulters

								grid.setVisible(false);
								gridTermly.setVisible(true);

							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() != 3) {
								grid.setVisible(true);
								gridTermly.setVisible(false);
							}

						} else if (tabPane.getSelectionModel().getSelectedIndex() == 1) {//////////////// Staffs Reports

						} else if (tabPane.getSelectionModel().getSelectedIndex() == 2) {//////////////// Library Reports

							if (tabPaneLibrary.getSelectionModel().getSelectedIndex() == 0) {/////////////// Books Entry
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM generaloverviewofbooks ";

								String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
										+ datePkToDate.getValue() + "'";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
								System.out.println(finalSql);
							} else if (tabPaneLibrary.getSelectionModel().getSelectedIndex() == 2) {//////// Unreturned Books

								String sqlSelect = "SELECT ";
								String sqlFrom = " ";
								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
								sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
								String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0";
								finalSql = sqlSelect + sqlFrom + sqlWhere;
								System.out.println(finalSql);
							} else if (tabPaneLibrary.getSelectionModel().getSelectedIndex() == 3) {///// Lost Books

								String sqlSelect = "SELECT ";
								String sqlFrom = " ";
								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
								sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
								String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 and TIMESTAMPDIFF(DAY,issued_books.issue_date,NOW())>365";
								finalSql = sqlSelect + sqlFrom + sqlWhere;
								System.out.println(finalSql);
							}else if (tabPaneLibrary.getSelectionModel().getSelectedIndex() == 1) {///////// All Issued Books

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

								String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
								System.out.println(finalSql);
							}

						} else if (tabPane.getSelectionModel().getSelectedIndex() == 3) {//////////////// Clearance Reports

						} else if (tabPane.getSelectionModel().getSelectedIndex() == 4) {//////////////// Finance Reports

						}
					}
				});

				datePkToDate.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (tabPane.getSelectionModel().getSelectedIndex() == 0) {//////////////// Students

							if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 0) {/////////// Dismissed Students
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;

							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 1) {////////// Suspended
																										////////// Students

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;

							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 2) {////////// Transferred
																										////////// Students

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;

							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 4) {////////// Students Health
																										////////// Report

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
								System.out.println(finalSql);
							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 3) {////////// Fees Defaulters

								grid.setVisible(false);
								gridTermly.setVisible(true);

							} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() != 3) {
								grid.setVisible(true);
								gridTermly.setVisible(false);
							}

						} else if (tabPane.getSelectionModel().getSelectedIndex() == 1) {//////////////// Staffs Reports

						} else if (tabPane.getSelectionModel().getSelectedIndex() == 2) {//////////////// Library Reports

							if (tabPaneLibrary.getSelectionModel().getSelectedIndex() == 0) {/////////////// Books Entry
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM generaloverviewofbooks ";

								String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
										+ datePkToDate.getValue() + "'";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
								System.out.println(finalSql);
							} else if (tabPaneLibrary.getSelectionModel().getSelectedIndex() == 2) {//////// Unreturned Books

								String sqlSelect = "SELECT ";
								String sqlFrom = " ";
								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
								sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
								String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0";
								finalSql = sqlSelect + sqlFrom + sqlWhere;
								System.out.println(finalSql);
							} else if (tabPaneLibrary.getSelectionModel().getSelectedIndex() == 3) {///////// Lost Books

								String sqlSelect = "SELECT ";
								String sqlFrom = " ";
								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
								sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
								String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 and TIMESTAMPDIFF(DAY,issued_books.issue_date,NOW())>365";
								finalSql = sqlSelect + sqlFrom + sqlWhere;
								System.out.println(finalSql);
								
								
								
							}else if (tabPaneLibrary.getSelectionModel().getSelectedIndex() == 1) {///////// All Issued Books

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

								String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
								System.out.println(finalSql);
							}


						} else if (tabPane.getSelectionModel().getSelectedIndex() == 3) {//////////////// Clearance Reports

						} else if (tabPane.getSelectionModel().getSelectedIndex() == 4) {//////////////// Finance Reports

						}
					}
				});

				Label ChooseClass = new Label("");
				ChooseClass.setPrefWidth(150);
				grid.add(ChooseClass, 2, 0);

				grid.add(combo, 3, 0);
				combo.setPromptText("Choose Class");

				gridTitlePane.setText("Execute");

				VBox vb = new VBox();
				vb.setSpacing(1);

				vb.setVisible(false);

				vb.getChildren().addAll(hbs);

				grid.add(vb, 2, 1);
				btnRun = new Button("Generate Report");

				// grid.setStyle("-fx-background-image: url('root.jpg')");

				btnRun.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub

						Group root = new Group();
						scene = new Scene(root, 250, 55, Color.BLACK);
						Stage stage = new Stage();

						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(scene);
						stage.setTitle("Progress Controls");

						for (int i = 0; i < values.length; i++) {
							final Label label = labels[i] = new Label();

							final ProgressBar pb = pbs[i] = new ProgressBar();
							pb.setProgress(values[i]);

							final ProgressIndicator pin = pins[i] = new ProgressIndicator();
							pin.setProgress(values[i]);
							final HBox hb = hbs[i] = new HBox();
							hb.setSpacing(5);
							hb.setAlignment(Pos.CENTER);
							hb.getChildren().addAll(label, pb, pin);
						}

						final VBox vb = new VBox();
						vb.setSpacing(1);
						vb.getChildren().addAll(hbs);
						scene.setRoot(vb);
						stage.setResizable(false);
						stage.setOpacity(0.8);
						stage.show();

						//////////////// One Time Action//////////////
						Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), ae -> stage.close()));
						timeline.play();

						////////////// Periodic Action//////////////

						// Timeline timeline1 = new Timeline(new KeyFrame(
						// Duration.millis(2500),
						// ae -> stage.close()));
						// timeline1.setCycleCount(Animation.INDEFINITE);
						// timeline1.play();

						buildReportFrame();
					}
				});
				grid.add(btnRun, 3, 1);

				///////////// TitledPane-Content For Reports That Can Be Run With Terms And
				///////////// Year///////////////////////////

				gridTermly = new GridPane();
				gridTermly.setVgap(10);
				gridTermly.setAlignment(Pos.CENTER);
				gridTermly.setPadding(new Insets(5, 5, 5, 5));
				gridTermly.setVisible(false);

				observableTerms = FXCollections.observableArrayList("First Term", "Second Term", "Third Term");
				term = new ComboBox(observableTerms);
				term.setPromptText("Choose Term");
				Label labelTerm = new Label("Term: ");
				gridTermly.add(labelTerm, 0, 0);
				gridTermly.add(term, 1, 0);

				datePkYear = new DatePicker();

				Label labelYear = new Label("Year: ");
				gridTermly.add(labelYear, 0, 1);
				gridTermly.add(datePkYear, 1, 1);

				comboClassTermly = new ComboBox(data);
				comboClassTermly.setPrefWidth(200);

				comboClassTermly.setOnAction(new EventHandler<Event>() {

					@Override
					public void handle(Event arg0) {
						// TODO Auto-generated method stub
						if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 3) {////////// Fees Defaulters

							grid.setVisible(false);
							gridTermly.setVisible(true);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;

						} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() != 3) {
							grid.setVisible(true);
							gridTermly.setVisible(false);
						}
					}
				});

				term.setOnAction(new EventHandler<Event>() {

					@Override
					public void handle(Event arg0) {
						// TODO Auto-generated method stub
						if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 3) {////////// Fees Defaulters

							grid.setVisible(false);
							gridTermly.setVisible(true);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;

						} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() != 3) {
							grid.setVisible(true);
							gridTermly.setVisible(false);
						}
					}
				});

				datePkYear.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 3) {////////// Fees Defaulters

							grid.setVisible(false);
							gridTermly.setVisible(true);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;

						} else if (tabPaneStudents.getSelectionModel().getSelectedIndex() != 3) {
							grid.setVisible(true);
							gridTermly.setVisible(false);
						}
					}
				});
				gridTermly.add(comboClassTermly, 3, 0);
				comboClassTermly.setPromptText("Choose Class");

				VBox vboxHome = new VBox(5);
				vboxHome.getChildren().addAll(grid, gridTermly);

				gridTitlePane.setContent(vboxHome);

				VBox vbTermly = new VBox();
				vbTermly.setSpacing(1);

				vbTermly.setVisible(false);

				vbTermly.getChildren().addAll(hbs);

				gridTermly.add(vbTermly, 2, 1);
				btnRunTermly = new Button("Generate Report");

				// grid.setStyle("-fx-background-image: url('root.jpg')");

				btnRunTermly.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub

						Group root = new Group();
						scene = new Scene(root, 250, 55, Color.BLACK);
						Stage stage = new Stage();

						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(scene);
						stage.setTitle("Progress Controls");

						for (int i = 0; i < values.length; i++) {
							final Label label = labels[i] = new Label();

							final ProgressBar pb = pbs[i] = new ProgressBar();
							pb.setProgress(values[i]);

							final ProgressIndicator pin = pins[i] = new ProgressIndicator();
							pin.setProgress(values[i]);
							final HBox hb = hbs[i] = new HBox();
							hb.setSpacing(5);
							hb.setAlignment(Pos.CENTER);
							hb.getChildren().addAll(label, pb, pin);
						}

						final VBox vb = new VBox();
						vb.setSpacing(1);
						vb.getChildren().addAll(hbs);
						scene.setRoot(vb);
						stage.setResizable(false);
						stage.setOpacity(0.8);
						stage.show();

						//////////////// One Time Action//////////////
						Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), ae -> stage.close()));
						timeline.play();

						////////////// Periodic Action//////////////

						// Timeline timeline1 = new Timeline(new KeyFrame(
						// Duration.millis(2500),
						// ae -> stage.close()));
						// timeline1.setCycleCount(Animation.INDEFINITE);
						// timeline1.play();

						buildReportFrame();
					}
				});
				gridTermly.add(btnRunTermly, 3, 1);

				////////////////////////////////////////////////////

				gridTitlePaneFields = new TitledPane();

				gridTitlePaneSMS = new TitledPane();
				GridPane gridSMS = new GridPane();
				gridSMS.setVgap(4);
				gridSMS.setAlignment(Pos.CENTER);

				tabPane = new TabPane();
				tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

					@Override
					public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab arg2) {
						// TODO Auto-generated method stub
						lvListDisplayed.getItems().clear();
						lvList.getItems().clear();
						columnNamesList.clear();

						dataOfJTable = new String[1][columnNamesList.size()];

						columnNamesArr = new String[columnNamesList.size()];
						for (int i = 0; i < columnNamesList.size(); i++) {
							columnNamesArr[i] = (String) columnNamesList.get(i);
							dataOfJTable[0][i] = "";
						}

						defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

						table = new JTable(defaultTableModel);
						tableColumnModel = table.getColumnModel();

						for (int i = 0; i < columnNamesList.size(); i++) {
							tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
						}
						table.setPreferredScrollableViewportSize(table.getPreferredSize());
						scrollPane = new JScrollPane(table);
					}
				});

				Tab tabStudents = new Tab();
				HBox hboxStudents = new HBox();
				tabStudents.setClosable(false);
				tabStudents.setContent(hboxStudents);
				hboxStudents.setPrefSize(500, 200);
				tabStudents.setText("Students");

				Tab tabStaffs = new Tab();
				HBox hboxStaffs = new HBox();
				tabStaffs.setClosable(false);
				tabStaffs.setContent(hboxStaffs);
				hboxStaffs.setPrefSize(500, 200);
				tabStaffs.setText("Staffs");

				Tab tabLibrary = new Tab();
				HBox hboxLibrary = new HBox();
				tabLibrary.setClosable(false);
				tabLibrary.setContent(hboxLibrary);
				hboxLibrary.setPrefSize(500, 200);
				tabLibrary.setText("Library");

				Tab tabClearance = new Tab();
				HBox hboxClearance = new HBox();
				hboxClearance.setPrefSize(500, 200);
				tabClearance.setClosable(false);
				tabClearance.setContent(hboxClearance);
				tabClearance.setText("Clearance");

				Tab tabFinance = new Tab();
				HBox hboxFinance = new HBox();
				tabFinance.setClosable(false);
				tabFinance.setContent(hboxFinance);
				hboxFinance.setPrefSize(500, 200);
				tabFinance.setText("Finance");

				tabPaneStudents = new TabPane();
				tabPaneStudents.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

					@Override
					public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab arg2) {
						// TODO Auto-generated method stub
						
						lvListDisplayed.getItems().clear();
						lvList.getItems().clear();
						columnNamesList.clear();

						dataOfJTable = new String[1][columnNamesList.size()];

						columnNamesArr = new String[columnNamesList.size()];
						for (int i = 0; i < columnNamesList.size(); i++) {
							columnNamesArr[i] = (String) columnNamesList.get(i);
							dataOfJTable[0][i] = "";
						}

						defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

						table = new JTable(defaultTableModel);
						tableColumnModel = table.getColumnModel();

						for (int i = 0; i < columnNamesList.size(); i++) {
							tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
						}
						table.setPreferredScrollableViewportSize(table.getPreferredSize());
						scrollPane = new JScrollPane(table);

						if (tabPaneStudents.getSelectionModel().getSelectedIndex() == 3) {
							gridTermly.setVisible(true);
							grid.setVisible(false);
							combo.setVisible(true);
						} else {
							gridTermly.setVisible(false);
							grid.setVisible(true);
							combo.setVisible(true);
						}
					}
				});

				final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
					public DateCell call(final DatePicker datePicker) {
						return new DateCell() {
							@Override
							public void updateItem(LocalDate item, boolean empty) {
								super.updateItem(item, empty);

								if (item.isBefore(datePk.getValue().plusDays(1))) {
									setStyle("-fx-background-color: #ff4444;");
									setDisable(true);
								}
								long p = ChronoUnit.DAYS.between(datePk.getValue(), item);

								setTooltip(new Tooltip("You're About To Generate Report For " + p + " Days"));
							}
						};
					}
				};
				datePkToDate.setDayCellFactory(dayCellFactory);

				StringConverter converter = new StringConverter<LocalDate>() {

					DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

					@Override
					public LocalDate fromString(String string) {
						// TODO Auto-generated method stub
						if (string != null && !string.isEmpty()) {
							return LocalDate.parse(string, dateFormatter);
						} else {
							return null;
						}
					}

					@Override
					public String toString(LocalDate date) {
						// TODO Auto-generated method stub

						if (date != null) {
							return dateFormatter.format(date);
						} else {
							return "";
						}
					}

				};

				StringConverter converterYearChooser = new StringConverter<LocalDate>() {

					DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

					@Override
					public LocalDate fromString(String string) {
						// TODO Auto-generated method stub
						if (string != null && !string.isEmpty()) {
							return LocalDate.parse(string, dateFormatter);
						} else {
							return null;
						}
					}

					@Override
					public String toString(LocalDate date) {
						// TODO Auto-generated method stub

						if (date != null) {
							return dateFormatter.format(date);
						} else {
							return "";
						}
					}

				};
				datePk.setConverter(converter);
				datePkToDate.setConverter(converter);
				datePkYear.setConverter(converter);

				Tab tabDismissedStudents = new Tab();
				HBox hboxDismissedStudents = new HBox();
				tabDismissedStudents.setClosable(false);
				tabDismissedStudents.setContent(hboxDismissedStudents);
				hboxDismissedStudents.setPrefSize(500, 200);
				tabDismissedStudents.setText("Dismissed Students");

				Tab tabSuspendedStudents = new Tab();
				HBox hboxSuspendedStudents = new HBox();
				tabSuspendedStudents.setClosable(false);
				tabSuspendedStudents.setContent(hboxSuspendedStudents);
				hboxSuspendedStudents.setPrefSize(500, 200);
				tabSuspendedStudents.setText("Suspended Students");

				Tab tabFeesDefaulters = new Tab();
				HBox hboxFeesDefaulters = new HBox();
				tabFeesDefaulters.setClosable(false);
				tabFeesDefaulters.setContent(hboxFeesDefaulters);
				hboxFeesDefaulters.setPrefSize(500, 200);
				tabFeesDefaulters.setText("FeesDefaulters");

				Tab tabHealthRecords = new Tab();
				HBox hboxHealthRecords = new HBox();
				tabHealthRecords.setClosable(false);
				tabHealthRecords.setContent(hboxHealthRecords);
				hboxHealthRecords.setPrefSize(500, 200);
				tabHealthRecords.setText("Health Records");

				Tab tabTransferRecords = new Tab();
				HBox hboxTransferRecords = new HBox();
				tabTransferRecords.setClosable(false);
				tabTransferRecords.setContent(hboxTransferRecords);
				hboxTransferRecords.setPrefSize(500, 200);
				tabTransferRecords.setText("Transfer Records");

				tabPaneStudents.getTabs().addAll(tabDismissedStudents, tabSuspendedStudents, tabTransferRecords,
						tabFeesDefaulters, tabHealthRecords);
				tabPaneStudents.setPrefSize(740, 220);

				hboxStudents.getChildren().add(tabPaneStudents);

				tabPaneLibrary = new TabPane();
				tabPaneLibrary.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

					@Override
					public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab arg2) {
						// TODO Auto-generated method stub
						lvListDisplayed.getItems().clear();
						lvList.getItems().clear();
						columnNamesList.clear();

						dataOfJTable = new String[1][columnNamesList.size()];

						columnNamesArr = new String[columnNamesList.size()];
						for (int i = 0; i < columnNamesList.size(); i++) {
							columnNamesArr[i] = (String) columnNamesList.get(i);
							dataOfJTable[0][i] = "";
						}

						defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

						table = new JTable(defaultTableModel);
						tableColumnModel = table.getColumnModel();

						for (int i = 0; i < columnNamesList.size(); i++) {
							tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
						}
						table.setPreferredScrollableViewportSize(table.getPreferredSize());
						scrollPane = new JScrollPane(table);

						if (tabPaneLibrary.getSelectionModel().getSelectedIndex() == 0) {

							combo.setVisible(false);
						} else {
							combo.setVisible(true);
						}
					}
				});

				Tab tabEntryLibrary = new Tab();
				HBox hboxEntryLibrary = new HBox();
				tabEntryLibrary.setClosable(false);
				tabEntryLibrary.setContent(hboxEntryLibrary);
				hboxEntryLibrary.setPrefSize(500, 200);
				tabEntryLibrary.setText("Books Entry");

				Tab tabSuspendedLibrary = new Tab();
				HBox hboxSuspendedLibrary = new HBox();
				tabSuspendedLibrary.setClosable(false);
				tabSuspendedLibrary.setContent(hboxSuspendedLibrary);
				hboxSuspendedLibrary.setPrefSize(500, 200);
				tabSuspendedLibrary.setText("Issued Books");

				Tab tabBooksLost = new Tab();
				HBox hboxBooksLost = new HBox();
				tabBooksLost.setClosable(false);
				tabBooksLost.setContent(hboxBooksLost);
				hboxBooksLost.setPrefSize(500, 200);
				tabBooksLost.setText("Books Lost");

				Tab tabUnreturnedBooks = new Tab();
				HBox hboxUnreturned = new HBox();
				tabUnreturnedBooks.setClosable(false);
				tabUnreturnedBooks.setContent(hboxUnreturned);
				hboxUnreturned.setPrefSize(500, 200);
				tabUnreturnedBooks.setText("Unreturned Books");

				tabPaneLibrary.getTabs().addAll(tabEntryLibrary, tabSuspendedLibrary, tabUnreturnedBooks, tabBooksLost);
				tabPaneLibrary.setPrefSize(740, 220);

				hboxLibrary.getChildren().add(tabPaneLibrary);

				tabPaneStaffs = new TabPane();
				tabPaneStaffs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

					@Override
					public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab arg2) {
						// TODO Auto-generated method stub
						lvListDisplayed.getItems().clear();
						lvList.getItems().clear();
						columnNamesList.clear();

						dataOfJTable = new String[1][columnNamesList.size()];

						columnNamesArr = new String[columnNamesList.size()];
						for (int i = 0; i < columnNamesList.size(); i++) {
							columnNamesArr[i] = (String) columnNamesList.get(i);
							dataOfJTable[0][i] = "";
						}

						defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

						table = new JTable(defaultTableModel);
						tableColumnModel = table.getColumnModel();

						for (int i = 0; i < columnNamesList.size(); i++) {
							tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
						}
						table.setPreferredScrollableViewportSize(table.getPreferredSize());
						scrollPane = new JScrollPane(table);
					}
				});

				Tab tabDismissedStaffs = new Tab();
				HBox hboxDismissedStaffs = new HBox();
				tabDismissedStaffs.setClosable(false);
				tabDismissedStaffs.setContent(hboxDismissedStaffs);
				hboxDismissedStaffs.setPrefSize(500, 200);
				tabDismissedStaffs.setText("Lesson Attendance");

				Tab tabEntryStaffs = new Tab();
				HBox hboxEntryStaffs = new HBox();
				tabEntryStaffs.setClosable(false);
				tabEntryStaffs.setContent(hboxEntryStaffs);
				hboxEntryStaffs.setPrefSize(500, 200);
				tabEntryStaffs.setText("Transfer Records");

				Tab tabSuspendedStaffs = new Tab();
				HBox hboxSuspendedStaffs = new HBox();
				tabSuspendedStaffs.setClosable(false);
				tabSuspendedStaffs.setContent(hboxSuspendedStaffs);
				hboxSuspendedStaffs.setPrefSize(500, 200);
				tabSuspendedStaffs.setText("Duty Schedules");

				Tab tabStaffsPerformance = new Tab();
				HBox hboxStaffsPerformance = new HBox();
				tabStaffsPerformance.setClosable(false);
				tabStaffsPerformance.setContent(hboxStaffsPerformance);
				hboxStaffsPerformance.setPrefSize(500, 200);
				tabStaffsPerformance.setText("Staffs Performance");

				Tab tabResponsiblitiesOfStaffs = new Tab();
				HBox hboxResponsiblitiesOfStuffs = new HBox();
				tabResponsiblitiesOfStaffs.setClosable(false);
				tabResponsiblitiesOfStaffs.setContent(hboxResponsiblitiesOfStuffs);
				hboxResponsiblitiesOfStuffs.setPrefSize(500, 200);
				tabResponsiblitiesOfStaffs.setText("Staff Responsibilities");

				Tab tabStaffsSubjectsTaught = new Tab();
				HBox hboxStaffsSubjectsTaught = new HBox();
				tabStaffsSubjectsTaught.setClosable(false);
				tabStaffsSubjectsTaught.setContent(hboxStaffsSubjectsTaught);
				hboxStaffsSubjectsTaught.setPrefSize(500, 200);
				tabStaffsSubjectsTaught.setText("Teachers Subject");

				tabPaneStaffs.getTabs().addAll(tabDismissedStaffs, tabEntryStaffs, tabSuspendedStaffs, tabStaffsPerformance,
						tabResponsiblitiesOfStaffs, tabStaffsSubjectsTaught);
				tabPaneStaffs.setPrefSize(740, 220);

				hboxStaffs.getChildren().add(tabPaneStaffs);

				tabPane.getTabs().addAll(tabStudents, tabStaffs, tabLibrary, tabClearance, tabFinance);
				tabPane.setPrefSize(750, 220);

				gridSMS.setPadding(new Insets(5, 5, 5, 5));

				gridSMS.setPadding(new Insets(5, 5, 5, 5));
				gridSMS.add(tabPane, 0, 0);
				gridTitlePaneSMS.setText("Available Fields ");
				gridTitlePaneFields.setText("Selected Fields");
				gridTitlePaneSMS.setContent(gridSMS);

				VBox hbox = new VBox(10);
				hbox.setPadding(new Insets(0, 0, 0, 0));
				gridTitlePane.setExpanded(false);
				gridTitlePaneSMS.setExpanded(false);
				gridTitlePaneFields.setExpanded(true);
				gridTitlePane.setPrefWidth(500);
				gridTitlePaneSMS.setPrefWidth(500);
				gridTitlePaneFields.setPrefWidth(230);
				gridTitlePaneFields.setPrefHeight(600);

				hbox.setPrefSize(650, 290);
				hbox.getChildren().addAll(gridTitlePane, gridTitlePaneSMS);

				//////////////////// TitledPane End Here///////////////////////

				///////////// CheckBoxes Begin from here////////////////

				/////////////////// DismissedStudents/////////////////////////
				hboxDismissedStudents.setPadding(new Insets(15, 10, 15, 10));
				CheckBox boxDateDismissedStudents = new CheckBox("Date of Dismissal");
				CheckBox boxTermDismissedStudents = new CheckBox("Term of Dismissal");
				CheckBox boxYearDismissedStudents = new CheckBox("Year of Dismissal");
				CheckBox boxReasonDismissedStudents = new CheckBox("Reason for Dismissal");
				CheckBox boxNameDismissedStudents = new CheckBox("Name of Students");
				CheckBox boxClassDismissedStudents = new CheckBox("Student Class");
				CheckBox boxClassNumberDismissedStudents = new CheckBox("Student Code");
				CheckBox boxAgeDismissedStudents = new CheckBox("Student Age");
				CheckBox boxFatherNameDismissedStudents = new CheckBox("Father's Name");
				CheckBox boxFatherContactDismissedStudents = new CheckBox("Father's Contact");
				CheckBox boxMotherNameDismissedStudents = new CheckBox("Mother's Name");
				CheckBox boxMotherContactDismissedStudents = new CheckBox("Mother's Contact");
				CheckBox boxGuardianNameDismissedStudents = new CheckBox("Guardian's Name");
				CheckBox boxGuardianContactDismissedStudents = new CheckBox("Guardian's Contact");
				CheckBox boxSponsorshipStatusDismissedStudents = new CheckBox("Sponsorship Status");
				CheckBox boxSponsorNameDismissedStudents = new CheckBox("Sponsor's Name");
				CheckBox boxSponsorContactDismissedStudents = new CheckBox("Sponsor's Contact");
				CheckBox boxParentAddressDismissedStudents = new CheckBox("Parents' Address");
				CheckBox boxDurationDismissedStudents = new CheckBox("Duration of School Attendance");
				CheckBox boxClassofAdmissionDismissedStudents = new CheckBox("Class Admitted In");
				CheckBox boxTermOfAdmissionDismissedStudents = new CheckBox("Term of Admission");
				CheckBox boxYearOfAdmissionDismissedStudents = new CheckBox("Year of Admission");

				GridPane paneDismissedStudents = new GridPane();
				paneDismissedStudents.setVgap(5);
				paneDismissedStudents.setHgap(5);
				paneDismissedStudents.setAlignment(Pos.CENTER);

				paneDismissedStudents.add(boxDateDismissedStudents, 0, 0);
				boxDateDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDateDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxDateDismissedStudents.setSelected(false);
							} else {

								lvList.getItems().add("dismissal_record.date");

								lvListDisplayed.getItems().add("Date of Dismissal");
								columnNamesList.add("Date of Dismissal");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}

						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxDateDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("dismissal_record.date");
								lvListDisplayed.getItems().remove("Date of Dismissal");
								columnNamesList.remove("Date of Dismissal");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});
				paneDismissedStudents.add(boxTermDismissedStudents, 1, 0);
				boxTermDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxTermDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxTermDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("dismissal_record.term");

								lvListDisplayed.getItems().add("Term of Dismissal");
								columnNamesList.add("Term of Dismissal");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxTermDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("dismissal_record.term");

								lvListDisplayed.getItems().remove("Term of Dismissal");
								columnNamesList.remove("Term of Dismissal");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});

				paneDismissedStudents.add(boxYearDismissedStudents, 2, 0);
				boxYearDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxYearDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxYearDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("dismissal_record.year");
								lvListDisplayed.getItems().add("Year");
								columnNamesList.add("Year");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxYearDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("dismissal_record.year");
								lvListDisplayed.getItems().remove("Year");
								columnNamesList.remove("Year");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});

				paneDismissedStudents.add(boxClassDismissedStudents, 3, 0);
				boxClassDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxClassDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxClassDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("all_students_and_parents.class_number");
								lvListDisplayed.getItems().add("Student's Class");
								columnNamesList.add("Student's Class");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxClassDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("all_students_and_parents.class_number");
								lvListDisplayed.getItems().remove("Student's Class");
								columnNamesList.remove("Student's Class");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});

				paneDismissedStudents.add(boxNameDismissedStudents, 0, 2);

				boxNameDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxNameDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxNameDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("all_students_and_parents.student_name");
								lvListDisplayed.getItems().add("Student's Name");
								columnNamesList.add("Student's Name");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxNameDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("all_students_and_parents.student_name");
								lvListDisplayed.getItems().remove("Student's Name");
								columnNamesList.remove("Student's Name");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});

				paneDismissedStudents.add(boxAgeDismissedStudents, 1, 2);

				boxAgeDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxAgeDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxAgeDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("TIMESTAMPDIFF(YEAR,all_students_and_parents.date_of_birth,NOW())");
								lvListDisplayed.getItems().add("Student Age");
								columnNamesList.add("Student Age");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxAgeDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("TIMESTAMPDIFF(YEAR,all_students_and_parents.date_of_birth,NOW())");
								lvListDisplayed.getItems().remove("Student Age");
								columnNamesList.remove("Student Age");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});

				paneDismissedStudents.add(boxClassNumberDismissedStudents, 2, 2);

				boxClassNumberDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxClassNumberDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxClassNumberDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("dismissal_record.id_number");
								lvListDisplayed.getItems().add("Student Code");
								columnNamesList.add("Student Code");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxClassNumberDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("dismissal_record.id_number");
								lvListDisplayed.getItems().remove("Student Code");
								columnNamesList.remove("Student Code");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});

				paneDismissedStudents.add(boxReasonDismissedStudents, 3, 2);
				boxReasonDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxReasonDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxReasonDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("dismissal_record.reason");
								lvListDisplayed.getItems().add("Reason");
								columnNamesList.add("Reason");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxReasonDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("dismissal_record.reason");
								lvListDisplayed.getItems().remove("Reason");
								columnNamesList.remove("Reason");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);
								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});

				paneDismissedStudents.add(boxFatherNameDismissedStudents, 0, 3);
				boxFatherNameDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherNameDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxFatherNameDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("all_students_and_parents.fathers_name");

								lvListDisplayed.getItems().add("Father's Name");
								columnNamesList.add("Father's Name");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxFatherNameDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("all_students_and_parents.fathers_name");

								lvListDisplayed.getItems().remove("Father's Name");
								columnNamesList.remove("Father's Name");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});

				paneDismissedStudents.add(boxFatherContactDismissedStudents, 1, 3);

				boxFatherContactDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherContactDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxFatherContactDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("all_students_and_parents.fathers_contact");

								lvListDisplayed.getItems().add("Father's Contact");
								columnNamesList.add("Father's Contact");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxFatherContactDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("all_students_and_parents.fathers_contact");

								lvListDisplayed.getItems().remove("Father's Contact");
								columnNamesList.remove("Father's Contact");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});

				paneDismissedStudents.add(boxMotherNameDismissedStudents, 2, 3);

				boxMotherNameDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherNameDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxMotherNameDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("all_students_and_parents.mothers_name");

								lvListDisplayed.getItems().add("Mother's Name");
								columnNamesList.add("Mother's Name");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxMotherNameDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("all_students_and_parents.mothers_name");

								lvListDisplayed.getItems().remove("Mother's Name");
								columnNamesList.remove("Mother's Name");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});

				paneDismissedStudents.add(boxMotherContactDismissedStudents, 3, 3);
				boxMotherContactDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherContactDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxMotherContactDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("all_students_and_parents.mothers_contact");

								lvListDisplayed.getItems().add("Mother's Contact");
								columnNamesList.add("Mother's Contact");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxMotherContactDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("all_students_and_parents.mothers_contact");

								lvListDisplayed.getItems().remove("Mother's Contact");
								columnNamesList.remove("Mother's Contact");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});

				paneDismissedStudents.add(boxGuardianNameDismissedStudents, 0, 4);
				boxGuardianNameDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianNameDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxGuardianNameDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("all_students_and_parents.guardians_name");

								lvListDisplayed.getItems().add("Guardian's Name");
								columnNamesList.add("Guardian's Name");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxGuardianNameDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("all_students_and_parents.guardians_name");

								lvListDisplayed.getItems().remove("Guardian's Name");
								columnNamesList.remove("Guardian's Name");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});

				paneDismissedStudents.add(boxGuardianContactDismissedStudents, 1, 4);

				boxGuardianContactDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianContactDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxGuardianContactDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("all_students_and_parents.guardians_contact");

								lvListDisplayed.getItems().add("Guardian's Contact");
								columnNamesList.add("Guardian's Contact");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxGuardianContactDismissedStudents.setSelected(true);
							} else {

								lvList.getItems().remove("all_students_and_parents.guardians_contact");

								lvListDisplayed.getItems().remove("Guardian's Contact");
								columnNamesList.remove("Guardian's Contact");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});

				paneDismissedStudents.add(boxSponsorshipStatusDismissedStudents, 2, 4);

				boxSponsorshipStatusDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorshipStatusDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxSponsorshipStatusDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add(
										"CASE WHEN all_students_and_parents.sponsor='Choose Sponsor' THEN 'Not Sponsored' ELSE 'Sponsored' END AS Sponsor");

								lvListDisplayed.getItems().add("Sponsored");
								columnNamesList.add("Sponsored");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxSponsorshipStatusDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove(
										"CASE WHEN all_students_and_parents.sponsor='Choose Sponsor' THEN 'Not Sponsored' ELSE 'Sponsored' END AS Sponsor");

								lvListDisplayed.getItems().remove("Sponsored");
								columnNamesList.remove("Sponsored");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						}
					}
				});

				paneDismissedStudents.add(boxSponsorNameDismissedStudents, 3, 4);

				boxSponsorNameDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorNameDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxSponsorNameDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("all_students_and_parents.sponsor");

								lvListDisplayed.getItems().add("Sponsor Name");
								columnNamesList.add("Sponsor Name");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;
							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxSponsorNameDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("all_students_and_parents.sponsor");

								lvListDisplayed.getItems().remove("Sponsor Name");
								columnNamesList.remove("Sponsor Name");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;

							}
						}
					}
				});

				paneDismissedStudents.add(boxParentAddressDismissedStudents, 0, 5);

				boxParentAddressDismissedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxParentAddressDismissedStudents.isSelected()) {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxParentAddressDismissedStudents.setSelected(false);
							} else {
								lvList.getItems().add("all_students_and_parents.parent_address");

								lvListDisplayed.getItems().add("Parent Address");
								columnNamesList.add("Parent Address");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;

							}
						} else {

							if (datePk.getValue() == null || datePkToDate.getValue() == null
									|| combo.getSelectionModel().getSelectedItem() == null) {
								JOptionPane.showMessageDialog(null,
										"Please Make Sure That You Have Selected The Date And Class");
								boxParentAddressDismissedStudents.setSelected(true);
							} else {
								lvList.getItems().remove("all_students_and_parents.parent_address");

								lvListDisplayed.getItems().remove("Parent Address");
								columnNamesList.remove("Parent Address");

								dataOfJTable = new String[1][columnNamesList.size()];

								columnNamesArr = new String[columnNamesList.size()];
								for (int i = 0; i < columnNamesList.size(); i++) {
									columnNamesArr[i] = (String) columnNamesList.get(i);
									dataOfJTable[0][i] = "";
								}

								defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

								table = new JTable(defaultTableModel);
								tableColumnModel = table.getColumnModel();

								for (int i = 0; i < columnNamesList.size(); i++) {
									tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
								}
								table.setPreferredScrollableViewportSize(table.getPreferredSize());
								scrollPane = new JScrollPane(table);

								String sqlSelect = "SELECT ";

								String sqlFrom = " ";

								String joined = lvList.getItems().stream().map(Object::toString)
										.collect(Collectors.joining(", "));
								joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

								sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

								String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
										+ datePk.getValue() + "' and '" + datePkToDate.getValue()
										+ "' and all_students_and_parents.student_class='"
										+ combo.getSelectionModel().getSelectedItem()
										+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

								finalSql = sqlSelect + sqlFrom + sqlWhere;

							}

						}
					}
				});

				hboxDismissedStudents.getChildren().addAll(paneDismissedStudents);

				////////////////////////////////////////////////////////////

				/////////////////// SuspendedStudents/////////////////////////
				hboxSuspendedStudents.setPadding(new Insets(15, 10, 15, 10));
				CheckBox boxDateSuspendedStudents = new CheckBox("Date of Suspension");
				CheckBox boxTermSuspendedStudents = new CheckBox("Return Date");
				CheckBox boxYearSuspendedStudents = new CheckBox("Year of Suspension");
				CheckBox boxReasonSuspendedStudents = new CheckBox("Reason for Suspension");
				CheckBox boxNameSuspendedStudents = new CheckBox("Name of Students");
				CheckBox boxClassSuspendedStudents = new CheckBox("Student Class");
				CheckBox boxClassNumberSuspendedStudents = new CheckBox("Student Code");
				CheckBox boxAgeSuspendedStudents = new CheckBox("Student Age");
				CheckBox boxFatherNameSuspendedStudents = new CheckBox("Father's Name");
				CheckBox boxFatherContactSuspendedStudents = new CheckBox("Father's Contact");
				CheckBox boxMotherNameSuspendedStudents = new CheckBox("Mother's Name");
				CheckBox boxMotherContactSuspendedStudents = new CheckBox("Mother's Contact");
				CheckBox boxGuardianNameSuspendedStudents = new CheckBox("Guardian's Name");
				CheckBox boxGuardianContactSuspendedStudents = new CheckBox("Guardian's Contact");
				CheckBox boxSponsorshipStatusSuspendedStudents = new CheckBox("Sponsorship Status");
				CheckBox boxSponsorNameSuspendedStudents = new CheckBox("Sponsor's Name");
				CheckBox boxSponsorContactSuspendedStudents = new CheckBox("Sponsor's Contact");
				CheckBox boxParentAddressSuspendedStudents = new CheckBox("Parents' Address");
				CheckBox boxDurationSuspendedStudents = new CheckBox("Duration of Suspension");
				CheckBox boxClassofAdmissionSuspendedStudents = new CheckBox("Class Admitted In");
				CheckBox boxTermOfAdmissionSuspendedStudents = new CheckBox("Term of Admission");

				GridPane paneSuspendedStudents = new GridPane();
				paneSuspendedStudents.setVgap(5);
				paneSuspendedStudents.setHgap(5);
				paneSuspendedStudents.setAlignment(Pos.CENTER);

				paneSuspendedStudents.add(boxDateSuspendedStudents, 0, 0);
				boxDateSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDateSuspendedStudents.isSelected()) {
							lvList.getItems().add("suspension_record.date");

							lvListDisplayed.getItems().add("Date of Suspension");
							columnNamesList.add("Date of Suspension");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;

						} else {
							lvList.getItems().remove("suspension_record.date");
							lvListDisplayed.getItems().remove("Date of Suspension");
							columnNamesList.remove("Date of Suspension");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxTermSuspendedStudents, 1, 0);
				boxTermSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxTermSuspendedStudents.isSelected()) {
							lvList.getItems().add("suspension_record.date_of_return");

							lvListDisplayed.getItems().add("Return Date");
							columnNamesList.add("Return Date");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("suspension_record.date_of_return");

							lvListDisplayed.getItems().remove("Return Date");
							columnNamesList.remove("Return Date");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxClassSuspendedStudents, 3, 0);
				boxClassSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxClassSuspendedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.class_number");
							lvListDisplayed.getItems().add("Student's Class");
							columnNamesList.add("Student's Class");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.class_number");
							lvListDisplayed.getItems().remove("Student's Class");
							columnNamesList.remove("Student's Class");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxNameSuspendedStudents, 0, 2);
				boxNameSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxNameSuspendedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.student_name");
							lvListDisplayed.getItems().add("Student's Name");
							columnNamesList.add("Student's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.student_name");
							lvListDisplayed.getItems().remove("Student's Name");
							columnNamesList.remove("Student's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxAgeSuspendedStudents, 1, 2);
				boxAgeSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxAgeSuspendedStudents.isSelected()) {
							lvList.getItems().add("TIMESTAMPDIFF(YEAR,all_students_and_parents.date_of_birth,NOW())");
							lvListDisplayed.getItems().add("Student Age");
							columnNamesList.add("Student Age");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("TIMESTAMPDIFF(YEAR,all_students_and_parents.date_of_birth,NOW())");
							lvListDisplayed.getItems().remove("Student Age");
							columnNamesList.remove("Student Age");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxClassNumberSuspendedStudents, 2, 2);
				boxClassNumberSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxClassNumberSuspendedStudents.isSelected()) {
							lvList.getItems().add("suspension_record.id_number");
							lvListDisplayed.getItems().add("Student Code");
							columnNamesList.add("Student Code");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("suspension_record.id_number");
							lvListDisplayed.getItems().remove("Student Code");
							columnNamesList.remove("Student Code");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxReasonSuspendedStudents, 3, 2);
				boxReasonSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxReasonSuspendedStudents.isSelected()) {
							lvList.getItems().add("suspension_record.reason");
							lvListDisplayed.getItems().add("Reason");
							columnNamesList.add("Reason");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("suspension_record.reason");
							lvListDisplayed.getItems().remove("Reason");
							columnNamesList.remove("Reason");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxFatherNameSuspendedStudents, 0, 3);
				boxFatherNameSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherNameSuspendedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.fathers_name");

							lvListDisplayed.getItems().add("Father's Name");
							columnNamesList.add("Father's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.fathers_name");

							lvListDisplayed.getItems().remove("Father's Name");
							columnNamesList.remove("Father's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxFatherContactSuspendedStudents, 1, 3);
				boxFatherContactSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherContactSuspendedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.fathers_contact");

							lvListDisplayed.getItems().add("Father's Contact");
							columnNamesList.add("Father's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.fathers_contact");

							lvListDisplayed.getItems().remove("Father's Contact");
							columnNamesList.remove("Father's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxMotherNameSuspendedStudents, 2, 3);
				boxMotherNameSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherNameSuspendedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.mothers_name");

							lvListDisplayed.getItems().add("Mother's Name");
							columnNamesList.add("Mother's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.mothers_name");

							lvListDisplayed.getItems().remove("Mother's Name");
							columnNamesList.remove("Mother's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxMotherContactSuspendedStudents, 3, 3);
				boxMotherContactSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherContactSuspendedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.mothers_contact");

							lvListDisplayed.getItems().add("Mother's Contact");
							columnNamesList.add("Mother's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.mothers_contact");

							lvListDisplayed.getItems().remove("Mother's Contact");
							columnNamesList.remove("Mother's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxGuardianNameSuspendedStudents, 0, 4);
				boxGuardianNameSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianNameSuspendedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.guardians_name");

							lvListDisplayed.getItems().add("Guardian's Name");
							columnNamesList.add("Guardian's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.guardians_name");

							lvListDisplayed.getItems().remove("Guardian's Name");
							columnNamesList.remove("Guardian's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxGuardianContactSuspendedStudents, 1, 4);
				boxGuardianContactSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianContactSuspendedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.guardians_contact");

							lvListDisplayed.getItems().add("Guardian's Contact");
							columnNamesList.add("Guardian's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.guardians_contact");

							lvListDisplayed.getItems().remove("Guardian's Contact");
							columnNamesList.remove("Guardian's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxSponsorshipStatusSuspendedStudents, 2, 4);
				boxSponsorshipStatusSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorshipStatusSuspendedStudents.isSelected()) {
							lvList.getItems().add(
									"CASE WHEN all_students_and_parents.sponsor='Choose Sponsor' THEN 'Not Sponsored' ELSE 'Sponsored' END AS Sponsor");

							lvListDisplayed.getItems().add("Sponsored");
							columnNamesList.add("Sponsored");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove(
									"CASE WHEN all_students_and_parents.sponsor='Choose Sponsor' THEN 'Not Sponsored' ELSE 'Sponsored' END AS Sponsor");

							lvListDisplayed.getItems().remove("Sponsored");
							columnNamesList.remove("Sponsored");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxSponsorNameSuspendedStudents, 3, 4);
				boxSponsorNameSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorNameSuspendedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.sponsor");

							lvListDisplayed.getItems().add("Sponsor Name");
							columnNamesList.add("Sponsor Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.sponsor");

							lvListDisplayed.getItems().remove("Sponsor Name");
							columnNamesList.remove("Sponsor Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneSuspendedStudents.add(boxDurationSuspendedStudents, 2, 0);
				boxDurationSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDurationSuspendedStudents.isSelected()) {
							lvList.getItems().add("TIMESTAMPDIFF(DAY,suspension_record.date,suspension_record.date_of_return)");

							lvListDisplayed.getItems().add("Duration of Suspension");
							columnNamesList.add("Duration of Suspension");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems()
									.remove("TIMESTAMPDIFF(DAY,suspension_record.date,suspension_record.date_of_return)");

							lvListDisplayed.getItems().remove("Duration of Suspension");
							columnNamesList.remove("Duration of Suspension");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;

						}
					}
				});

				paneSuspendedStudents.add(boxParentAddressSuspendedStudents, 0, 5);
				boxParentAddressSuspendedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxParentAddressSuspendedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.parent_address");

							lvListDisplayed.getItems().add("Parent Address");
							columnNamesList.add("Parent Address");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.parent_address");

							lvListDisplayed.getItems().remove("Parent Address");
							columnNamesList.remove("Parent Address");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM suspension_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and suspension_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and suspension_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;

						}
					}
				});

				hboxSuspendedStudents.getChildren().addAll(paneSuspendedStudents);

				////////////////////////////////////////////////////////////

				/////////////////// TransferedStudents/////////////////////////
				hboxTransferRecords.setPadding(new Insets(15, 10, 15, 10));
				CheckBox boxDateTransferedStudents = new CheckBox("Date of Transfer");
				CheckBox boxTermTransferedStudents = new CheckBox("Term of Transfer");
				CheckBox boxYearTransferedStudents = new CheckBox("Year of Transfer");
				CheckBox boxReasonTransferedStudents = new CheckBox("Reason for Transfer");
				CheckBox boxNameTransferedStudents = new CheckBox("Name of Students");
				CheckBox boxClassTransferedStudents = new CheckBox("Student Class");
				CheckBox boxClassNumberTransferedStudents = new CheckBox("Student Code");
				CheckBox boxAgeTransferedStudents = new CheckBox("Student Age");
				CheckBox boxFatherNameTransferedStudents = new CheckBox("Father's Name");
				CheckBox boxFatherContactTransferedStudents = new CheckBox("Father's Contact");
				CheckBox boxMotherNameTransferedStudents = new CheckBox("Mother's Name");
				CheckBox boxMotherContactTransferedStudents = new CheckBox("Mother's Contact");
				CheckBox boxGuardianNameTransferedStudents = new CheckBox("Guardian's Name");
				CheckBox boxGuardianContactTransferedStudents = new CheckBox("Guardian's Contact");
				CheckBox boxSponsorshipStatusTransferedStudents = new CheckBox("Sponsorship Status");
				CheckBox boxSponsorNameTransferedStudents = new CheckBox("Sponsor's Name");
				CheckBox boxSponsorContactTransferedStudents = new CheckBox("Sponsor's Contact");
				CheckBox boxParentAddressTransferedStudents = new CheckBox("Parents' Address");
				CheckBox boxDurationTransferedStudents = new CheckBox("Duration of Transfer");
				CheckBox boxClassofAdmissionTransferedStudents = new CheckBox("Class Admitted In");
				CheckBox boxTermOfAdmissionTransferedStudents = new CheckBox("Term of Admission");

				GridPane paneTransferedStudents = new GridPane();
				paneTransferedStudents.setVgap(5);
				paneTransferedStudents.setHgap(5);
				paneTransferedStudents.setAlignment(Pos.CENTER);

				paneTransferedStudents.add(boxDateTransferedStudents, 0, 0);
				boxDateTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDateTransferedStudents.isSelected()) {
							lvList.getItems().add("dismissal_record.date");

							lvListDisplayed.getItems().add("Date of Transfer");
							columnNamesList.add("Date of Transfer");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;

						} else {
							lvList.getItems().remove("dismissal_record.date");
							lvListDisplayed.getItems().remove("Date of Transfer");
							columnNamesList.remove("Date of Transfer");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxTermTransferedStudents, 1, 0);
				boxTermTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxTermTransferedStudents.isSelected()) {
							lvList.getItems().add("dismissal_record.term");

							lvListDisplayed.getItems().add("Term of Transfer");
							columnNamesList.add("Term of Transfer");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("dismissal_record.term");

							lvListDisplayed.getItems().remove("Term of Transfer");
							columnNamesList.remove("Term of Transfer");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxYearTransferedStudents, 2, 0);
				boxYearTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxYearTransferedStudents.isSelected()) {
							lvList.getItems().add("dismissal_record.year");
							lvListDisplayed.getItems().add("Year");
							columnNamesList.add("Year");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("dismissal_record.year");
							lvListDisplayed.getItems().remove("Year");
							columnNamesList.remove("Year");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxClassTransferedStudents, 3, 0);
				boxClassTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxClassTransferedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.class_number");
							lvListDisplayed.getItems().add("Student's Class");
							columnNamesList.add("Student's Class");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.class_number");
							lvListDisplayed.getItems().remove("Student's Class");
							columnNamesList.remove("Student's Class");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxNameTransferedStudents, 0, 2);
				boxNameTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxNameTransferedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.student_name");
							lvListDisplayed.getItems().add("Student's Name");
							columnNamesList.add("Student's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.student_name");
							lvListDisplayed.getItems().remove("Student's Name");
							columnNamesList.remove("Student's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxAgeTransferedStudents, 1, 2);
				boxAgeTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxAgeTransferedStudents.isSelected()) {
							lvList.getItems().add("TIMESTAMPDIFF(YEAR,all_students_and_parents.date_of_birth,NOW())");
							lvListDisplayed.getItems().add("Student Age");
							columnNamesList.add("Student Age");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("TIMESTAMPDIFF(YEAR,all_students_and_parents.date_of_birth,NOW())");
							lvListDisplayed.getItems().remove("Student Age");
							columnNamesList.remove("Student Age");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxClassNumberTransferedStudents, 2, 2);
				boxClassNumberTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxClassNumberTransferedStudents.isSelected()) {
							lvList.getItems().add("dismissal_record.id_number");
							lvListDisplayed.getItems().add("Student Code");
							columnNamesList.add("Student Code");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("dismissal_record.id_number");
							lvListDisplayed.getItems().remove("Student Code");
							columnNamesList.remove("Student Code");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxReasonTransferedStudents, 3, 2);
				boxReasonTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxReasonTransferedStudents.isSelected()) {
							lvList.getItems().add("dismissal_record.reason");
							lvListDisplayed.getItems().add("Reason");
							columnNamesList.add("Reason");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("dismissal_record.reason");
							lvListDisplayed.getItems().remove("Reason");
							columnNamesList.remove("Reason");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxFatherNameTransferedStudents, 0, 3);
				boxFatherNameTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherNameTransferedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.fathers_name");

							lvListDisplayed.getItems().add("Father's Name");
							columnNamesList.add("Father's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.fathers_name");

							lvListDisplayed.getItems().remove("Father's Name");
							columnNamesList.remove("Father's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxFatherContactTransferedStudents, 1, 3);
				boxFatherContactTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherContactTransferedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.fathers_contact");

							lvListDisplayed.getItems().add("Father's Contact");
							columnNamesList.add("Father's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.fathers_contact");

							lvListDisplayed.getItems().remove("Father's Contact");
							columnNamesList.remove("Father's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxMotherNameTransferedStudents, 2, 3);
				boxMotherNameTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherNameTransferedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.mothers_name");

							lvListDisplayed.getItems().add("Mother's Name");
							columnNamesList.add("Mother's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.mothers_name");

							lvListDisplayed.getItems().remove("Mother's Name");
							columnNamesList.remove("Mother's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxMotherContactTransferedStudents, 3, 3);
				boxMotherContactTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherContactTransferedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.mothers_contact");

							lvListDisplayed.getItems().add("Mother's Contact");
							columnNamesList.add("Mother's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.mothers_contact");

							lvListDisplayed.getItems().remove("Mother's Contact");
							columnNamesList.remove("Mother's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxGuardianNameTransferedStudents, 0, 4);
				boxGuardianNameTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianNameTransferedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.guardians_name");

							lvListDisplayed.getItems().add("Guardian's Name");
							columnNamesList.add("Guardian's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.guardians_name");

							lvListDisplayed.getItems().remove("Guardian's Name");
							columnNamesList.remove("Guardian's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxGuardianContactTransferedStudents, 1, 4);
				boxGuardianContactTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianContactTransferedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.guardians_contact");

							lvListDisplayed.getItems().add("Guardian's Contact");
							columnNamesList.add("Guardian's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.guardians_contact");

							lvListDisplayed.getItems().remove("Guardian's Contact");
							columnNamesList.remove("Guardian's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxSponsorshipStatusTransferedStudents, 2, 4);
				boxSponsorshipStatusTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorshipStatusTransferedStudents.isSelected()) {
							lvList.getItems().add(
									"CASE WHEN all_students_and_parents.sponsor='Choose Sponsor' THEN 'Not Sponsored' ELSE 'Sponsored' END AS Sponsor");

							lvListDisplayed.getItems().add("Sponsored");
							columnNamesList.add("Sponsored");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove(
									"CASE WHEN all_students_and_parents.sponsor='Choose Sponsor' THEN 'Not Sponsored' ELSE 'Sponsored' END AS Sponsor");

							lvListDisplayed.getItems().remove("Sponsored");
							columnNamesList.remove("Sponsored");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxSponsorNameTransferedStudents, 3, 4);
				boxSponsorNameTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorNameTransferedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.sponsor");

							lvListDisplayed.getItems().add("Sponsor Name");
							columnNamesList.add("Sponsor Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.sponsor");

							lvListDisplayed.getItems().remove("Sponsor Name");
							columnNamesList.remove("Sponsor Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneTransferedStudents.add(boxParentAddressTransferedStudents, 0, 5);
				boxParentAddressTransferedStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxParentAddressTransferedStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.parent_address");

							lvListDisplayed.getItems().add("Parent Address");
							columnNamesList.add("Parent Address");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.parent_address");

							lvListDisplayed.getItems().remove("Parent Address");
							columnNamesList.remove("Parent Address");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM dismissal_record,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and dismissal_record.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and dismissal_record.id_number=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;

						}
					}
				});

				hboxTransferRecords.getChildren().addAll(paneTransferedStudents);

				////////////////////////////////////////////////////////////

				/////////////////// HealthStudents/////////////////////////
				hboxHealthRecords.setPadding(new Insets(15, 10, 15, 10));
				CheckBox boxDateDiagnosisStudents = new CheckBox("Date of Diagnosis");
				CheckBox boxTermDiagnosisStudents = new CheckBox("Medical Findings");
				CheckBox boxYearDiagnosisStudents = new CheckBox("Treatment Taken");
				CheckBox boxReasonDiagnosisStudents = new CheckBox("Reason for Diagnosis");
				CheckBox boxNameDiagnosisStudents = new CheckBox("Name of Students");
				CheckBox boxClassDiagnosisStudents = new CheckBox("Student Class");
				CheckBox boxClassNumberDiagnosisStudents = new CheckBox("Student Code");
				CheckBox boxAgeDiagnosisStudents = new CheckBox("Student Age");
				CheckBox boxFatherNameDiagnosisStudents = new CheckBox("Father's Name");
				CheckBox boxFatherContactDiagnosisStudents = new CheckBox("Father's Contact");
				CheckBox boxMotherNameDiagnosisStudents = new CheckBox("Mother's Name");
				CheckBox boxMotherContactDiagnosisStudents = new CheckBox("Mother's Contact");
				CheckBox boxGuardianNameDiagnosisStudents = new CheckBox("Guardian's Name");
				CheckBox boxGuardianContactDiagnosisStudents = new CheckBox("Guardian's Contact");
				CheckBox boxSponsorshipStatusDiagnosisStudents = new CheckBox("Sponsorship Status");
				CheckBox boxSponsorNameDiagnosisStudents = new CheckBox("Sponsor's Name");
				CheckBox boxSponsorContactDiagnosisStudents = new CheckBox("Sponsor's Contact");
				CheckBox boxParentAddressDiagnosisStudents = new CheckBox("Parents' Address");
				CheckBox boxDurationDiagnosisStudents = new CheckBox("Duration of Diagnosis");
				CheckBox boxClassofAdmissionDiagnosisStudents = new CheckBox("Class Admitted In");
				CheckBox boxTermOfAdmissionDiagnosisStudents = new CheckBox("Term of Admission");

				GridPane paneDiagnosisStudents = new GridPane();
				paneDiagnosisStudents.setVgap(5);
				paneDiagnosisStudents.setHgap(5);
				paneDiagnosisStudents.setAlignment(Pos.CENTER);

				paneDiagnosisStudents.add(boxDateDiagnosisStudents, 0, 0);
				boxDateDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDateDiagnosisStudents.isSelected()) {
							lvList.getItems().add("diagnosis.date");

							lvListDisplayed.getItems().add("Date of Diagnosis");
							columnNamesList.add("Date of Diagnosis");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;

						} else {
							lvList.getItems().remove("diagnosis.date");
							lvListDisplayed.getItems().remove("Date of Diagnosis");
							columnNamesList.remove("Date of Diagnosis");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxTermDiagnosisStudents, 1, 0);
				boxTermDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxTermDiagnosisStudents.isSelected()) {
							lvList.getItems().add("diagnosis.medicalHistory");

							lvListDisplayed.getItems().add("Medical Findings");
							columnNamesList.add("Medical Findings");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("diagnosis.medicalHistory");

							lvListDisplayed.getItems().remove("Medical Findings");
							columnNamesList.remove("Medical Findings");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxYearDiagnosisStudents, 2, 0);
				boxYearDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxYearDiagnosisStudents.isSelected()) {
							lvList.getItems().add("diagnosis.treatmentTaken");
							lvListDisplayed.getItems().add("Treatment Taken");
							columnNamesList.add("Treatment Taken");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("diagnosis.treatmentTaken");
							lvListDisplayed.getItems().remove("Treatment Taken");
							columnNamesList.remove("Treatment Taken");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxClassDiagnosisStudents, 3, 0);
				boxClassDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxClassDiagnosisStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.class_number");
							lvListDisplayed.getItems().add("Student's Class");
							columnNamesList.add("Student's Class");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.class_number");
							lvListDisplayed.getItems().remove("Student's Class");
							columnNamesList.remove("Student's Class");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxNameDiagnosisStudents, 0, 2);
				boxNameDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxNameDiagnosisStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.student_name");
							lvListDisplayed.getItems().add("Student's Name");
							columnNamesList.add("Student's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.student_name");
							lvListDisplayed.getItems().remove("Student's Name");
							columnNamesList.remove("Student's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxAgeDiagnosisStudents, 1, 2);
				boxAgeDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxAgeDiagnosisStudents.isSelected()) {
							lvList.getItems().add("TIMESTAMPDIFF(YEAR,all_students_and_parents.date_of_birth,NOW())");
							lvListDisplayed.getItems().add("Student Age");
							columnNamesList.add("Student Age");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("TIMESTAMPDIFF(YEAR,all_students_and_parents.date_of_birth,NOW())");
							lvListDisplayed.getItems().remove("Student Age");
							columnNamesList.remove("Student Age");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxClassNumberDiagnosisStudents, 2, 2);
				boxClassNumberDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxClassNumberDiagnosisStudents.isSelected()) {
							lvList.getItems().add("diagnosis.classNumber");
							lvListDisplayed.getItems().add("Student Code");
							columnNamesList.add("Student Code");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("diagnosis.classNumber");
							lvListDisplayed.getItems().remove("Student Code");
							columnNamesList.remove("Student Code");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxReasonDiagnosisStudents, 3, 2);
				boxReasonDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxReasonDiagnosisStudents.isSelected()) {
							lvList.getItems().add("diagnosis.provisionalDiagnosis");
							lvListDisplayed.getItems().add("Reason");
							columnNamesList.add("Reason");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("diagnosis.provisionalDiagnosis");
							lvListDisplayed.getItems().remove("Reason");
							columnNamesList.remove("Reason");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxFatherNameDiagnosisStudents, 0, 3);
				boxFatherNameDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherNameDiagnosisStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.fathers_name");

							lvListDisplayed.getItems().add("Father's Name");
							columnNamesList.add("Father's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.fathers_name");

							lvListDisplayed.getItems().remove("Father's Name");
							columnNamesList.remove("Father's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxFatherContactDiagnosisStudents, 1, 3);
				boxFatherContactDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherContactDiagnosisStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.fathers_contact");

							lvListDisplayed.getItems().add("Father's Contact");
							columnNamesList.add("Father's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.fathers_contact");

							lvListDisplayed.getItems().remove("Father's Contact");
							columnNamesList.remove("Father's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxMotherNameDiagnosisStudents, 2, 3);
				boxMotherNameDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherNameDiagnosisStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.mothers_name");

							lvListDisplayed.getItems().add("Mother's Name");
							columnNamesList.add("Mother's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.mothers_name");

							lvListDisplayed.getItems().remove("Mother's Name");
							columnNamesList.remove("Mother's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxMotherContactDiagnosisStudents, 3, 3);
				boxMotherContactDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherContactDiagnosisStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.mothers_contact");

							lvListDisplayed.getItems().add("Mother's Contact");
							columnNamesList.add("Mother's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.mothers_contact");

							lvListDisplayed.getItems().remove("Mother's Contact");
							columnNamesList.remove("Mother's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxGuardianNameDiagnosisStudents, 0, 4);
				boxGuardianNameDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianNameDiagnosisStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.guardians_name");

							lvListDisplayed.getItems().add("Guardian's Name");
							columnNamesList.add("Guardian's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.guardians_name");

							lvListDisplayed.getItems().remove("Guardian's Name");
							columnNamesList.remove("Guardian's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxGuardianContactDiagnosisStudents, 1, 4);
				boxGuardianContactDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianContactDiagnosisStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.guardians_contact");

							lvListDisplayed.getItems().add("Guardian's Contact");
							columnNamesList.add("Guardian's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.guardians_contact");

							lvListDisplayed.getItems().remove("Guardian's Contact");
							columnNamesList.remove("Guardian's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxSponsorshipStatusDiagnosisStudents, 2, 4);
				boxSponsorshipStatusDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorshipStatusDiagnosisStudents.isSelected()) {
							lvList.getItems().add(
									"CASE WHEN all_students_and_parents.sponsor='Choose Sponsor' THEN 'Not Sponsored' ELSE 'Sponsored' END AS Sponsor");

							lvListDisplayed.getItems().add("Sponsored");
							columnNamesList.add("Sponsored");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove(
									"CASE WHEN all_students_and_parents.sponsor='Choose Sponsor' THEN 'Not Sponsored' ELSE 'Sponsored' END AS Sponsor");

							lvListDisplayed.getItems().remove("Sponsored");
							columnNamesList.remove("Sponsored");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxSponsorNameDiagnosisStudents, 3, 4);
				boxSponsorNameDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorNameDiagnosisStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.sponsor");

							lvListDisplayed.getItems().add("Sponsor Name");
							columnNamesList.add("Sponsor Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.sponsor");

							lvListDisplayed.getItems().remove("Sponsor Name");
							columnNamesList.remove("Sponsor Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneDiagnosisStudents.add(boxParentAddressDiagnosisStudents, 0, 5);
				boxParentAddressDiagnosisStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxParentAddressDiagnosisStudents.isSelected()) {
							lvList.getItems().add("all_students_and_parents.parent_address");

							lvListDisplayed.getItems().add("Parent Address");
							columnNamesList.add("Parent Address");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {
							lvList.getItems().remove("all_students_and_parents.parent_address");

							lvListDisplayed.getItems().remove("Parent Address");
							columnNamesList.remove("Parent Address");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM diagnosis,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and diagnosis.date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and diagnosis.classNumber=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);

						}
					}
				});

				hboxHealthRecords.getChildren().addAll(paneDiagnosisStudents);

				////////////////////////////////////////////////////////////

				/////////////////// /////////////////////////
				/////////////////// Fees Defaulters/////////////////////////
				hboxFeesDefaulters.setPadding(new Insets(15, 10, 15, 10));
				CheckBox boxDateFeesDefaultersStudents = new CheckBox("Amount Paid");
				CheckBox boxTermFeesDefaultersStudents = new CheckBox("Term");
				CheckBox boxYearFeesDefaultersStudents = new CheckBox("Year");
				CheckBox boxReasonFeesDefaultersStudents = new CheckBox("Balance");
				CheckBox boxNameFeesDefaultersStudents = new CheckBox("Name of Students");
				CheckBox boxClassFeesDefaultersStudents = new CheckBox("Student Class");
				CheckBox boxClassNumberFeesDefaultersStudents = new CheckBox("Student Code");
				CheckBox boxAgeFeesDefaultersStudents = new CheckBox("Student Age");
				CheckBox boxFatherNameFeesDefaultersStudents = new CheckBox("Father's Name");
				CheckBox boxFatherContactFeesDefaultersStudents = new CheckBox("Father's Contact");
				CheckBox boxMotherNameFeesDefaultersStudents = new CheckBox("Mother's Name");
				CheckBox boxMotherContactFeesDefaultersStudents = new CheckBox("Mother's Contact");
				CheckBox boxGuardianNameFeesDefaultersStudents = new CheckBox("Guardian's Name");
				CheckBox boxGuardianContactFeesDefaultersStudents = new CheckBox("Guardian's Contact");
				CheckBox boxSponsorshipStatusFeesDefaultersStudents = new CheckBox("Sponsorship Status");
				CheckBox boxSponsorNameFeesDefaultersStudents = new CheckBox("Sponsor's Name");
				CheckBox boxSponsorContactFeesDefaultersStudents = new CheckBox("Sponsor's Contact");
				CheckBox boxParentAddressFeesDefaultersStudents = new CheckBox("Parents' Address");
				CheckBox boxDurationFeesDefaultersStudents = new CheckBox("Duration of FeesDefaulters");
				CheckBox boxClassofAdmissionFeesDefaultersStudents = new CheckBox("Class Admitted In");
				CheckBox boxTermOfAdmissionFeesDefaultersStudents = new CheckBox("Term of Admission");

				GridPane paneFeesDefaultersStudents = new GridPane();
				paneFeesDefaultersStudents.setVgap(5);
				paneFeesDefaultersStudents.setHgap(5);
				paneFeesDefaultersStudents.setAlignment(Pos.CENTER);

				paneFeesDefaultersStudents.add(boxDateFeesDefaultersStudents, 0, 0);
				boxDateFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDateFeesDefaultersStudents.isSelected()) {

							lvList.getItems().add("student_ledger.credit");

							lvListDisplayed.getItems().add("Amount Paid");
							columnNamesList.add("Amount Paid");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);

						} else {

							lvList.getItems().remove("dismissal_record.date");
							lvListDisplayed.getItems().remove("Amount Paid");
							columnNamesList.remove("Amount Paid");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxTermFeesDefaultersStudents, 3, 0);
				boxTermFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxTermFeesDefaultersStudents.isSelected()) {
							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("student_ledger.term");

							lvListDisplayed.getItems().add("Term");
							columnNamesList.add("Term");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("student_ledger.term");

							lvListDisplayed.getItems().remove("Term");
							columnNamesList.remove("Term");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxYearFeesDefaultersStudents, 2, 0);
				boxYearFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxYearFeesDefaultersStudents.isSelected()) {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("student_ledger.year");
							lvListDisplayed.getItems().add("Year");
							columnNamesList.add("Year");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("dismissal_record.year");
							lvListDisplayed.getItems().remove("Year");
							columnNamesList.remove("Year");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxClassFeesDefaultersStudents, 3, 2);
				boxClassFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxClassFeesDefaultersStudents.isSelected()) {
							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("all_students_and_parents.class_number");
							lvListDisplayed.getItems().add("Student's Class");
							columnNamesList.add("Student's Class");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("all_students_and_parents.class_number");
							lvListDisplayed.getItems().remove("Student's Class");
							columnNamesList.remove("Student's Class");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxNameFeesDefaultersStudents, 0, 2);
				boxNameFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxNameFeesDefaultersStudents.isSelected()) {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("all_students_and_parents.student_name");
							lvListDisplayed.getItems().add("Student's Name");
							columnNamesList.add("Student's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("all_students_and_parents.student_name");
							lvListDisplayed.getItems().remove("Student's Name");
							columnNamesList.remove("Student's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxAgeFeesDefaultersStudents, 1, 2);
				boxAgeFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxAgeFeesDefaultersStudents.isSelected()) {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("TIMESTAMPDIFF(YEAR,all_students_and_parents.date_of_birth,NOW())");
							lvListDisplayed.getItems().add("Student Age");
							columnNamesList.add("Student Age");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("TIMESTAMPDIFF(YEAR,all_students_and_parents.date_of_birth,NOW())");
							lvListDisplayed.getItems().remove("Student Age");
							columnNamesList.remove("Student Age");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxClassNumberFeesDefaultersStudents, 2, 2);
				boxClassNumberFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxClassNumberFeesDefaultersStudents.isSelected()) {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("student_ledger.payment_code");
							lvListDisplayed.getItems().add("Student Code");
							columnNamesList.add("Student Code");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("student_ledger.payment_code");
							lvListDisplayed.getItems().remove("Student Code");
							columnNamesList.remove("Student Code");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxReasonFeesDefaultersStudents, 1, 0);
				boxReasonFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxReasonFeesDefaultersStudents.isSelected()) {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("student_ledger.debit-student_ledger.credit");
							lvListDisplayed.getItems().add("Balance");
							columnNamesList.add("Balance");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("student_ledger.debit-student_ledger.credit");
							lvListDisplayed.getItems().remove("Balance");
							columnNamesList.remove("Balance");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxFatherNameFeesDefaultersStudents, 0, 3);
				boxFatherNameFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherNameFeesDefaultersStudents.isSelected()) {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("all_students_and_parents.fathers_name");

							lvListDisplayed.getItems().add("Father's Name");
							columnNamesList.add("Father's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("all_students_and_parents.fathers_name");

							lvListDisplayed.getItems().remove("Father's Name");
							columnNamesList.remove("Father's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxFatherContactFeesDefaultersStudents, 1, 3);
				boxFatherContactFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherContactFeesDefaultersStudents.isSelected()) {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("all_students_and_parents.fathers_contact");

							lvListDisplayed.getItems().add("Father's Contact");
							columnNamesList.add("Father's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("all_students_and_parents.fathers_contact");

							lvListDisplayed.getItems().remove("Father's Contact");
							columnNamesList.remove("Father's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxMotherNameFeesDefaultersStudents, 2, 3);
				boxMotherNameFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherNameFeesDefaultersStudents.isSelected()) {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("all_students_and_parents.mothers_name");

							lvListDisplayed.getItems().add("Mother's Name");
							columnNamesList.add("Mother's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("all_students_and_parents.mothers_name");

							lvListDisplayed.getItems().remove("Mother's Name");
							columnNamesList.remove("Mother's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxMotherContactFeesDefaultersStudents, 3, 3);
				boxMotherContactFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherContactFeesDefaultersStudents.isSelected()) {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("all_students_and_parents.mothers_contact");

							lvListDisplayed.getItems().add("Mother's Contact");
							columnNamesList.add("Mother's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("all_students_and_parents.mothers_contact");

							lvListDisplayed.getItems().remove("Mother's Contact");
							columnNamesList.remove("Mother's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxGuardianNameFeesDefaultersStudents, 0, 4);
				boxGuardianNameFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianNameFeesDefaultersStudents.isSelected()) {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("all_students_and_parents.guardians_name");

							lvListDisplayed.getItems().add("Guardian's Name");
							columnNamesList.add("Guardian's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("all_students_and_parents.guardians_name");

							lvListDisplayed.getItems().remove("Guardian's Name");
							columnNamesList.remove("Guardian's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxGuardianContactFeesDefaultersStudents, 1, 4);
				boxGuardianContactFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianContactFeesDefaultersStudents.isSelected()) {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("all_students_and_parents.guardians_contact");

							lvListDisplayed.getItems().add("Guardian's Contact");
							columnNamesList.add("Guardian's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("all_students_and_parents.guardians_contact");

							lvListDisplayed.getItems().remove("Guardian's Contact");
							columnNamesList.remove("Guardian's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxSponsorshipStatusFeesDefaultersStudents, 2, 4);
				boxSponsorshipStatusFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorshipStatusFeesDefaultersStudents.isSelected()) {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add(
									"CASE WHEN all_students_and_parents.sponsor='Choose Sponsor' THEN 'Not Sponsored' ELSE 'Sponsored' END AS Sponsor");

							lvListDisplayed.getItems().add("Sponsored");
							columnNamesList.add("Sponsored");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove(
									"CASE WHEN all_students_and_parents.sponsor='Choose Sponsor' THEN 'Not Sponsored' ELSE 'Sponsored' END AS Sponsor");

							lvListDisplayed.getItems().remove("Sponsored");
							columnNamesList.remove("Sponsored");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxSponsorNameFeesDefaultersStudents, 3, 4);
				boxSponsorNameFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorNameFeesDefaultersStudents.isSelected()) {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("all_students_and_parents.sponsor");

							lvListDisplayed.getItems().add("Sponsor Name");
							columnNamesList.add("Sponsor Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("all_students_and_parents.sponsor");

							lvListDisplayed.getItems().remove("Sponsor Name");
							columnNamesList.remove("Sponsor Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						}
					}
				});

				paneFeesDefaultersStudents.add(boxParentAddressFeesDefaultersStudents, 0, 5);
				boxParentAddressFeesDefaultersStudents.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxParentAddressFeesDefaultersStudents.isSelected()) {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().add("all_students_and_parents.parent_address");

							lvListDisplayed.getItems().add("Parent Address");
							columnNamesList.add("Parent Address");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
						} else {

							String yearLike = "" + datePkYear.getValue();
							year = yearLike.substring(yearLike.length() - 4);

							lvList.getItems().remove("all_students_and_parents.parent_address");

							lvListDisplayed.getItems().remove("Parent Address");
							columnNamesList.remove("Parent Address");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM student_ledger,all_students_and_parents ";

							String sqlWhere = " WHERE all_students_and_parents.student_name is not null and student_ledger.year= '"
									+ datePkYear.getValue().getYear() + "' and student_ledger.term='"
									+ term.getSelectionModel().getSelectedItem()
									+ "' and all_students_and_parents.student_class='"
									+ comboClassTermly.getSelectionModel().getSelectedItem()
									+ "' and student_ledger.payment_code=all_students_and_parents.payment_code group by all_students_and_parents.payment_code";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);

						}
					}
				});

				hboxFeesDefaulters.getChildren().addAll(paneFeesDefaultersStudents);

			
				//////// lost
				//////// books///////////////////////////////////////////////////////////////////////////////
				//////////////////////////////////////////// lost
				//////// books/////////////////////////////
				/////////////////////////////// lostbooks///////////////////////////////////////////////////

				hboxBooksLost.setPadding(new Insets(15, 10, 15, 10));
				CheckBox boxDateOfBorrowingBooksLost = new CheckBox("Date of Borrowing");
				CheckBox boxDateOfReturnBooksLost = new CheckBox("Expected Return Date");
				CheckBox boxDurationTakenBooksLost = new CheckBox("Duration Taken With Book");
				CheckBox boxLateByBooksLost = new CheckBox("Late By");
				CheckBox boxStudentCodeBooksLost = new CheckBox("Student Code");
				CheckBox boxClassNumberOrContactBooksLost = new CheckBox("Class Number");
				CheckBox boxTeacherOrStudentNameBooksLost = new CheckBox("Borrowing ID");
				CheckBox boxIDNumberBooksLost = new CheckBox("Year of Borrowing");
				CheckBox boxFatherNameBooksLost = new CheckBox("Father's Name");
				CheckBox boxFatherContactBooksLost = new CheckBox("Father's Contact");
				CheckBox boxMotherNameBooksLost = new CheckBox("Mother's Name");
				CheckBox boxMotherContactBooksLost = new CheckBox("Mother's Contact");
				CheckBox boxGuardianNameBooksLost = new CheckBox("Guardian's Name");
				CheckBox boxGuardianContactBooksLost = new CheckBox("Guardian's Contact");
				CheckBox boxSponsorshipStatusBooksLost = new CheckBox("Book Title");
				CheckBox boxSponsorNameBooksLost = new CheckBox("Book ID");
				CheckBox boxSponsorContactBooksLost = new CheckBox("Book Author");
				CheckBox boxParentAddressBooksLost = new CheckBox("Parents' Address");
				CheckBox boxStaffNameBooksLost = new CheckBox("Student's Name");

				GridPane paneBooksLost = new GridPane();

				paneBooksLost.setVgap(5);
				paneBooksLost.setHgap(5);
				paneBooksLost.setAlignment(Pos.CENTER);
				paneBooksLost.add(boxDateOfBorrowingBooksLost, 0, 0);
				boxDateOfBorrowingBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDateOfBorrowingBooksLost.isSelected()) {

							lvList.getItems().add("issued_books.issue_date");

							lvListDisplayed.getItems().add("Issue Date");

							columnNamesList.add("Issue Date");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.issue_date");
							lvListDisplayed.getItems().remove("Issue Date");
							columnNamesList.remove("Issue Date");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxDateOfReturnBooksLost, 1, 0);
				boxDateOfReturnBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDateOfReturnBooksLost.isSelected()) {
							lvList.getItems().add("issued_books.return_date");
							lvListDisplayed.getItems().add("Return Date");
							columnNamesList.add("Return Date");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							TableViewTitles.add("Return Date");
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.return_date");
							lvListDisplayed.getItems().remove("Return Date");
							columnNamesList.remove("Return Date");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxDurationTakenBooksLost, 2, 0);
				boxDurationTakenBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDurationTakenBooksLost.isSelected()) {
							lvList.getItems().add("TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())");
							lvListDisplayed.getItems().add("Duration Taken");

							columnNamesList.add("Duration Taken (Days)");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())");
							lvListDisplayed.getItems().remove("Duration Taken");
							columnNamesList.remove("Duration Taken (Days)");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxLateByBooksLost, 3, 0);
				boxLateByBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxLateByBooksLost.isSelected()) {
							lvList.getItems().add("TIMESTAMPDIFF(DAY,issued_books.return_date,Now())");
							lvListDisplayed.getItems().add("Late By");
							columnNamesList.add("Late By (Days)");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("TIMESTAMPDIFF(DAY,issued_books.return_date,Now())");
							lvListDisplayed.getItems().remove("Late By");
							columnNamesList.remove("Late By (Days)");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxStudentCodeBooksLost, 0, 1);
				boxStudentCodeBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxStudentCodeBooksLost.isSelected()) {
							lvList.getItems().add("issued_books.payment_code");
							lvListDisplayed.getItems().add("Student ID");
							columnNamesList.add("Student ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.payment_code");
							lvListDisplayed.getItems().remove("Student ID");
							columnNamesList.remove("Student ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxClassNumberOrContactBooksLost, 1, 1);

				boxClassNumberOrContactBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxClassNumberOrContactBooksLost.isSelected()) {
							lvList.getItems().add("issued_books.class_number");
							lvListDisplayed.getItems().add("Class Number");
							columnNamesList.add("Class Number");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.class_number");
							lvListDisplayed.getItems().remove("Class Number");
							columnNamesList.remove("Class Number");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxStaffNameBooksLost, 2, 1);

				boxStaffNameBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxStaffNameBooksLost.isSelected()) {
							lvList.getItems().add("issued_books.student_name");
							lvListDisplayed.getItems().add("Student Name");
							columnNamesList.add("Student Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.student_name");
							lvListDisplayed.getItems().remove("Student Name");
							columnNamesList.remove("Student Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxIDNumberBooksLost, 3, 1);

				boxIDNumberBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxIDNumberBooksLost.isSelected()) {
							lvList.getItems().add("issued_books.year");
							lvListDisplayed.getItems().add("Year of Borrowing");
							columnNamesList.add("Year of Borrowing");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.year");
							lvListDisplayed.getItems().remove("Year of Borrowing");
							columnNamesList.remove("Year of Borrowing");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxTeacherOrStudentNameBooksLost, 0, 2);

				boxTeacherOrStudentNameBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxTeacherOrStudentNameBooksLost.isSelected()) {
							lvList.getItems().add("issued_books.id");
							lvListDisplayed.getItems().add("Borrowing ID");
							columnNamesList.add("Borrowing ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.id");
							lvListDisplayed.getItems().remove("Borrowing ID");
							columnNamesList.remove("Borrowing ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxFatherNameBooksLost, 1, 2);
				boxFatherNameBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherNameBooksLost.isSelected()) {
							lvList.getItems().add("all_students_and_parents.fathers_name");
							lvListDisplayed.getItems().add("Father's Name");
							columnNamesList.add("Father's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.fathers_name");
							lvListDisplayed.getItems().remove("Father's Name");
							columnNamesList.remove("Father's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxFatherContactBooksLost, 2, 2);

				boxFatherContactBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherContactBooksLost.isSelected()) {
							lvList.getItems().add("all_students_and_parents.fathers_contact");
							lvListDisplayed.getItems().add("Father's Contact");
							columnNamesList.add("Father's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.fathers_contact");
							lvListDisplayed.getItems().remove("Father's Contact");
							columnNamesList.remove("Father's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxMotherNameBooksLost, 3, 2);

				boxMotherNameBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherNameBooksLost.isSelected()) {
							lvList.getItems().add("all_students_and_parents.mothers_name");
							lvListDisplayed.getItems().add("Mother's Name");
							columnNamesList.add("Mother's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.mothers_name");
							lvListDisplayed.getItems().remove("Mother's Name");
							columnNamesList.remove("Mother's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxMotherContactBooksLost, 0, 3);
				boxMotherContactBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherContactBooksLost.isSelected()) {
							lvList.getItems().add("all_students_and_parents.mothers_contact");
							lvListDisplayed.getItems().add("Mother's Contact");
							columnNamesList.add("Mother's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.mothers_contact");
							lvListDisplayed.getItems().remove("Mother's Contact");
							columnNamesList.remove("Mother's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxGuardianNameBooksLost, 1, 3);
				boxGuardianNameBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianNameBooksLost.isSelected()) {
							lvList.getItems().add("all_students_and_parents.guardians_name");
							columnNamesList.add("Guardian's Name");
							lvListDisplayed.getItems().add("Guardian's Name");
							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.guardians_name");
							lvListDisplayed.getItems().remove("Guardian's Name");
							columnNamesList.remove("Guardian's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxGuardianContactBooksLost, 2, 3);

				boxGuardianContactBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianContactBooksLost.isSelected()) {
							lvList.getItems().add("all_students_and_parents.guardians_contact");
							lvListDisplayed.getItems().add("Guardian's Contact");
							columnNamesList.add("Guardian's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.guardians_contact");
							lvListDisplayed.getItems().remove("Guardian's Contact");
							columnNamesList.remove("Guardian's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxSponsorshipStatusBooksLost, 3, 3);

				boxSponsorshipStatusBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorshipStatusBooksLost.isSelected()) {
							lvList.getItems().add("issued_books.book_title");
							lvListDisplayed.getItems().add("Book Title");
							columnNamesList.add("Book Title");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.book_title");
							lvListDisplayed.getItems().remove("Book Title");
							columnNamesList.remove("Book Title");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxSponsorNameBooksLost, 0, 4);

				boxSponsorNameBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorNameBooksLost.isSelected()) {
							lvList.getItems().add("issued_books.book_id");
							lvListDisplayed.getItems().add("Book ID");
							columnNamesList.add("Book ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.book_id");
							lvListDisplayed.getItems().remove("Book ID");
							columnNamesList.remove("Book ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxSponsorContactBooksLost, 1, 4);

				boxSponsorContactBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorContactBooksLost.isSelected()) {
							lvList.getItems().add("issued_books.book_author");
							lvListDisplayed.getItems().add("Book Author");
							columnNamesList.add("Book Author");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 and TIMESTAMPDIFF(DAY,issued_books.issue_date,NOW())>365";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.book_author");
							lvListDisplayed.getItems().remove("Book Author");
							columnNamesList.remove("Book Author");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 and TIMESTAMPDIFF(DAY,issued_books.issue_date,NOW())>365";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneBooksLost.add(boxParentAddressBooksLost, 2, 4);

				boxParentAddressBooksLost.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxParentAddressBooksLost.isSelected()) {
							lvList.getItems().add("all_students_and_parents.parent_address");
							lvListDisplayed.getItems().add("Parent's Address");
							columnNamesList.add("Parent's Address");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 and TIMESTAMPDIFF(DAY,issued_books.issue_date,NOW())>365";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.parent_address");
							lvListDisplayed.getItems().remove("Parent's Address");
							columnNamesList.remove("Parent's Address");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"+ datePk.getValue() + "' and '" + datePkToDate.getValue()+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 and TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())>364 and TIMESTAMPDIFF(DAY,issued_books.issue_date,NOW())>365";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				hboxBooksLost.getChildren().addAll(paneBooksLost);

				////////////////////////////////////////////////////// end of lost of
				////////////////////////////////////////////////////// books///////////////////////////////////////

				/////////////////// Unreturned Books/////////////////////////
				hboxUnreturned.setPadding(new Insets(15, 10, 15, 10));
				CheckBox boxDateOfBorrowingUnreturnedBooks = new CheckBox("Date of Borrowing");
				CheckBox boxDateOfReturnUnreturnedBooks = new CheckBox("Expected Return Date");
				CheckBox boxDurationTakenUnreturnedBooks = new CheckBox("Duration Taken With Book");
				CheckBox boxLateByUnreturnedBooks = new CheckBox("Late By");
				CheckBox boxStudentCodeUnreturnedBooks = new CheckBox("Student Code");
				CheckBox boxClassNumberOrContactUnreturnedBooks = new CheckBox("Class Number");
				CheckBox boxTeacherOrStudentNameUnreturnedBooks = new CheckBox("Borrowing ID");
				CheckBox boxIDNumberUnreturnedBooks = new CheckBox("Year of Borrowing");
				CheckBox boxFatherNameUnreturnedBooks = new CheckBox("Father's Name");
				CheckBox boxFatherContactUnreturnedBooks = new CheckBox("Father's Contact");
				CheckBox boxMotherNameUnreturnedBooks = new CheckBox("Mother's Name");
				CheckBox boxMotherContactUnreturnedBooks = new CheckBox("Mother's Contact");
				CheckBox boxGuardianNameUnreturnedBooks = new CheckBox("Guardian's Name");
				CheckBox boxGuardianContactUnreturnedBooks = new CheckBox("Guardian's Contact");
				CheckBox boxSponsorshipStatusUnreturnedBooks = new CheckBox("Book Title");
				CheckBox boxSponsorNameUnreturnedBooks = new CheckBox("Book ID");
				CheckBox boxSponsorContactUnreturnedBooks = new CheckBox("Book Author");
				CheckBox boxParentAddressUnreturnedBooks = new CheckBox("Parents' Address");
				CheckBox boxStaffNameUnreturnedBooks = new CheckBox("Student's Name");

				GridPane paneUnreturnedBooks = new GridPane();

				paneUnreturnedBooks.setVgap(5);
				paneUnreturnedBooks.setHgap(5);
				paneUnreturnedBooks.setAlignment(Pos.CENTER);

				paneUnreturnedBooks.add(boxDateOfBorrowingUnreturnedBooks, 0, 0);
				boxDateOfBorrowingUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDateOfBorrowingUnreturnedBooks.isSelected()) {

							lvList.getItems().add("issued_books.issue_date");

							lvListDisplayed.getItems().add("Issue Date");

							columnNamesList.add("Issue Date");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.issue_date");
							lvListDisplayed.getItems().remove("Issue Date");
							columnNamesList.remove("Issue Date");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxDateOfReturnUnreturnedBooks, 1, 0);
				boxDateOfReturnUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDateOfReturnUnreturnedBooks.isSelected()) {
							lvList.getItems().add("issued_books.return_date");
							lvListDisplayed.getItems().add("Return Date");
							columnNamesList.add("Return Date");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							TableViewTitles.add("Return Date");
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.return_date");
							lvListDisplayed.getItems().remove("Return Date");
							columnNamesList.remove("Return Date");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxDurationTakenUnreturnedBooks, 2, 0);
				boxDurationTakenUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDurationTakenUnreturnedBooks.isSelected()) {
							lvList.getItems().add("TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())");
							lvListDisplayed.getItems().add("Duration Taken");

							columnNamesList.add("Duration Taken (Days)");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())");
							lvListDisplayed.getItems().remove("Duration Taken");
							columnNamesList.remove("Duration Taken (Days)");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxLateByUnreturnedBooks, 3, 0);
				boxLateByUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxLateByUnreturnedBooks.isSelected()) {
							lvList.getItems().add("TIMESTAMPDIFF(DAY,issued_books.return_date,Now())");
							lvListDisplayed.getItems().add("Late By");
							columnNamesList.add("Late By (Days)");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("TIMESTAMPDIFF(DAY,issued_books.return_date,Now())");
							lvListDisplayed.getItems().remove("Late By");
							columnNamesList.remove("Late By (Days)");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxStudentCodeUnreturnedBooks, 0, 1);
				boxStudentCodeUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxStudentCodeUnreturnedBooks.isSelected()) {
							lvList.getItems().add("issued_books.payment_code");
							lvListDisplayed.getItems().add("Student ID");
							columnNamesList.add("Student ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.payment_code");
							lvListDisplayed.getItems().remove("Student ID");
							columnNamesList.remove("Student ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxClassNumberOrContactUnreturnedBooks, 1, 1);

				boxClassNumberOrContactUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxClassNumberOrContactUnreturnedBooks.isSelected()) {
							lvList.getItems().add("issued_books.class_number");
							lvListDisplayed.getItems().add("Class Number");
							columnNamesList.add("Class Number");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.class_number");
							lvListDisplayed.getItems().remove("Class Number");
							columnNamesList.remove("Class Number");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxStaffNameUnreturnedBooks, 2, 1);

				boxStaffNameUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxStaffNameUnreturnedBooks.isSelected()) {
							lvList.getItems().add("issued_books.student_name");
							lvListDisplayed.getItems().add("Student Name");
							columnNamesList.add("Student Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.student_name");
							lvListDisplayed.getItems().remove("Student Name");
							columnNamesList.remove("Student Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxIDNumberUnreturnedBooks, 3, 1);

				boxIDNumberUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxIDNumberUnreturnedBooks.isSelected()) {
							lvList.getItems().add("issued_books.year");
							lvListDisplayed.getItems().add("Year of Borrowing");
							columnNamesList.add("Year of Borrowing");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.year");
							lvListDisplayed.getItems().remove("Year of Borrowing");
							columnNamesList.remove("Year of Borrowing");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxTeacherOrStudentNameUnreturnedBooks, 0, 2);

				boxTeacherOrStudentNameUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxTeacherOrStudentNameUnreturnedBooks.isSelected()) {
							lvList.getItems().add("issued_books.id");
							lvListDisplayed.getItems().add("Borrowing ID");
							columnNamesList.add("Borrowing ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.id");
							lvListDisplayed.getItems().remove("Borrowing ID");
							columnNamesList.remove("Borrowing ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxFatherNameUnreturnedBooks, 1, 2);
				boxFatherNameUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherNameUnreturnedBooks.isSelected()) {
							lvList.getItems().add("all_students_and_parents.fathers_name");
							lvListDisplayed.getItems().add("Father's Name");
							columnNamesList.add("Father's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.fathers_name");
							lvListDisplayed.getItems().remove("Father's Name");
							columnNamesList.remove("Father's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxFatherContactUnreturnedBooks, 2, 2);

				boxFatherContactUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherContactUnreturnedBooks.isSelected()) {
							lvList.getItems().add("all_students_and_parents.fathers_contact");
							lvListDisplayed.getItems().add("Father's Contact");
							columnNamesList.add("Father's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.fathers_contact");
							lvListDisplayed.getItems().remove("Father's Contact");
							columnNamesList.remove("Father's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxMotherNameUnreturnedBooks, 3, 2);

				boxMotherNameUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherNameUnreturnedBooks.isSelected()) {
							lvList.getItems().add("all_students_and_parents.mothers_name");
							lvListDisplayed.getItems().add("Mother's Name");
							columnNamesList.add("Mother's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.mothers_name");
							lvListDisplayed.getItems().remove("Mother's Name");
							columnNamesList.remove("Mother's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxMotherContactUnreturnedBooks, 0, 3);
				boxMotherContactUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherContactUnreturnedBooks.isSelected()) {
							lvList.getItems().add("all_students_and_parents.mothers_contact");
							lvListDisplayed.getItems().add("Mother's Contact");
							columnNamesList.add("Mother's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.mothers_contact");
							lvListDisplayed.getItems().remove("Mother's Contact");
							columnNamesList.remove("Mother's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxGuardianNameUnreturnedBooks, 1, 3);
				boxGuardianNameUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianNameUnreturnedBooks.isSelected()) {
							lvList.getItems().add("all_students_and_parents.guardians_name");
							columnNamesList.add("Guardian's Name");
							lvListDisplayed.getItems().add("Guardian's Name");
							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.guardians_name");
							lvListDisplayed.getItems().remove("Guardian's Name");
							columnNamesList.remove("Guardian's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxGuardianContactUnreturnedBooks, 2, 3);

				boxGuardianContactUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianContactUnreturnedBooks.isSelected()) {
							lvList.getItems().add("all_students_and_parents.guardians_contact");
							lvListDisplayed.getItems().add("Guardian's Contact");
							columnNamesList.add("Guardian's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.guardians_contact");
							lvListDisplayed.getItems().remove("Guardian's Contact");
							columnNamesList.remove("Guardian's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxSponsorshipStatusUnreturnedBooks, 3, 3);

				boxSponsorshipStatusUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorshipStatusUnreturnedBooks.isSelected()) {
							lvList.getItems().add("issued_books.book_title");
							lvListDisplayed.getItems().add("Book Title");
							columnNamesList.add("Book Title");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.book_title");
							lvListDisplayed.getItems().remove("Book Title");
							columnNamesList.remove("Book Title");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxSponsorNameUnreturnedBooks, 0, 4);

				boxSponsorNameUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorNameUnreturnedBooks.isSelected()) {
							lvList.getItems().add("issued_books.book_id");
							lvListDisplayed.getItems().add("Book ID");
							columnNamesList.add("Book ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.book_id");
							lvListDisplayed.getItems().remove("Book ID");
							columnNamesList.remove("Book ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0 ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxSponsorContactUnreturnedBooks, 1, 4);

				boxSponsorContactUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorContactUnreturnedBooks.isSelected()) {
							lvList.getItems().add("issued_books.book_author");
							lvListDisplayed.getItems().add("Book Author");
							columnNamesList.add("Book Author");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.book_author");
							lvListDisplayed.getItems().remove("Book Author");
							columnNamesList.remove("Book Author");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneUnreturnedBooks.add(boxParentAddressUnreturnedBooks, 2, 4);

				boxParentAddressUnreturnedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxParentAddressUnreturnedBooks.isSelected()) {
							lvList.getItems().add("all_students_and_parents.parent_address");
							lvListDisplayed.getItems().add("Parent's Address");
							columnNamesList.add("Parent's Address");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.parent_address");
							lvListDisplayed.getItems().remove("Parent's Address");
							columnNamesList.remove("Parent's Address");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code and issued_books.returned=0";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				hboxUnreturned.getChildren().addAll(paneUnreturnedBooks);

				/*
				 * *****************************************************************************
				 * ***************** ***********************************Unreturned
				 * Books*******************************************
				 * *****************************************************************************
				 * ***************** ********************************End
				 * Here******************************************************
				 * 
				 */

				/*
				 * *****************************************************************************
				 * ***************** ***********************************Issued
				 * Books*******************************************
				 * *****************************************************************************
				 * ***************** ********************************Begin
				 * Here****************************************************
				 * 
				 */

				/////////////////// Issued Books/////////////////////////
				hboxSuspendedLibrary.setPadding(new Insets(15, 10, 15, 10));
				CheckBox boxDateOfBorrowingIssuedBooks = new CheckBox("Date of Borrowing");
				CheckBox boxDateOfReturnIssuedBooks = new CheckBox("Expected Return Date");
				CheckBox boxDurationTakenIssuedBooks = new CheckBox("Duration Taken With Book");
				CheckBox boxLateByIssuedBooks = new CheckBox("Late By");
				CheckBox boxStudentCodeIssuedBooks = new CheckBox("Student Code");
				CheckBox boxClassNumberOrContactIssuedBooks = new CheckBox("Class Number");
				CheckBox boxTeacherOrStudentNameIssuedBooks = new CheckBox("Student Or Teacher");
				CheckBox boxIDNumberIssuedBooks = new CheckBox("Student Age");
				CheckBox boxFatherNameIssuedBooks = new CheckBox("Father's Name");
				CheckBox boxFatherContactIssuedBooks = new CheckBox("Father's Contact");
				CheckBox boxMotherNameIssuedBooks = new CheckBox("Mother's Name");
				CheckBox boxMotherContactIssuedBooks = new CheckBox("Mother's Contact");
				CheckBox boxGuardianNameIssuedBooks = new CheckBox("Guardian's Name");
				CheckBox boxGuardianContactIssuedBooks = new CheckBox("Guardian's Contact");
				CheckBox boxSponsorshipStatusIssuedBooks = new CheckBox("Book Title");
				CheckBox boxSponsorNameIssuedBooks = new CheckBox("Book ID");
				CheckBox boxSponsorContactIssuedBooks = new CheckBox("Book Author");
				CheckBox boxParentAddressIssuedBooks = new CheckBox("Parents' Address");
				CheckBox boxStaffNameIssuedBooks = new CheckBox("Student Name");
				GridPane paneIssuedBooks = new GridPane();
				paneIssuedBooks.setVgap(5);
				paneIssuedBooks.setHgap(5);
				paneIssuedBooks.setAlignment(Pos.CENTER);

				paneIssuedBooks.add(boxDateOfBorrowingIssuedBooks, 0, 0);
				boxDateOfBorrowingIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDateOfBorrowingIssuedBooks.isSelected()) {
							lvList.getItems().add("issued_books.issue_date");
							lvListDisplayed.getItems().add("Date of Issuance");
							columnNamesList.add("Date of Issuance");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.issue_date");
							lvListDisplayed.getItems().remove("Date of Issuance");
							columnNamesList.remove("Date of Issuance");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxDateOfReturnIssuedBooks, 1, 0);
				boxDateOfReturnIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDateOfReturnIssuedBooks.isSelected()) {
							lvList.getItems().add("issued_books.return_date");
							lvListDisplayed.getItems().add("Date of Return");
							columnNamesList.add("Date of Return");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.return_date");
							lvListDisplayed.getItems().remove("Date of Return");
							columnNamesList.remove("Date of Return");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxDurationTakenIssuedBooks, 2, 0);
				boxDurationTakenIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDurationTakenIssuedBooks.isSelected()) {
							lvList.getItems().add("TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())");
							lvListDisplayed.getItems().add("Duration Since Book Was Borrowed");
							columnNamesList.add("Duration Since Book Was Borrowed");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("TIMESTAMPDIFF(DAY,issued_books.issue_date,Now())");
							lvListDisplayed.getItems().remove("Duration Since Book Was Borrowed");
							columnNamesList.remove("Duration Since Book Was Borrowed");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxLateByIssuedBooks, 3, 0);
				boxLateByIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxLateByIssuedBooks.isSelected()) {
							lvList.getItems().add("TIMESTAMPDIFF(DAY,issued_books.return_date,Now())");
							lvListDisplayed.getItems().add("Late By");
							columnNamesList.add("Late By");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("TIMESTAMPDIFF(DAY,issued_books.return_date,Now())");
							lvListDisplayed.getItems().remove("Late By");
							columnNamesList.remove("Late By");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxStudentCodeIssuedBooks, 0, 1);
				boxStudentCodeIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxStudentCodeIssuedBooks.isSelected()) {
							lvList.getItems().add("issued_books.payment_code");
							lvListDisplayed.getItems().add("Payment Code");
							columnNamesList.add("Payment Code");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.payment_code");
							lvListDisplayed.getItems().remove("Payment Code");
							columnNamesList.remove("Payment Code");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxClassNumberOrContactIssuedBooks, 1, 1);

				boxClassNumberOrContactIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxClassNumberOrContactIssuedBooks.isSelected()) {
							lvList.getItems().add("issued_books.class_number");
							lvListDisplayed.getItems().add("Class Number");
							columnNamesList.add("Class Number");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.class_number");
							lvListDisplayed.getItems().remove("Class Number");
							columnNamesList.remove("Class Number");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxStaffNameIssuedBooks, 2, 1);

				boxStaffNameIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxStaffNameIssuedBooks.isSelected()) {
							lvList.getItems().add("issued_books.student_name");
							lvListDisplayed.getItems().add("Student Name");
							columnNamesList.add("Student Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.student_name");
							lvListDisplayed.getItems().remove("Student Name");
							columnNamesList.remove("Student Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxIDNumberIssuedBooks, 3, 1);

				boxIDNumberIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxIDNumberIssuedBooks.isSelected()) {
							lvList.getItems().add("TIMESTAMPDIFF(all_students_and_parents.date_of_birth,Now())");
							lvListDisplayed.getItems().add("Student Age");
							columnNamesList.add("Student Age");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("TIMESTAMPDIFF(all_students_and_parents.date_of_birth,Now())");
							lvListDisplayed.getItems().remove("Student Age");
							columnNamesList.remove("Student Age");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxTeacherOrStudentNameIssuedBooks, 0, 2);

				boxTeacherOrStudentNameIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxTeacherOrStudentNameIssuedBooks.isSelected()) {
							lvList.getItems().add("'Student'");
							lvListDisplayed.getItems().add("Student Or Teacher");
							columnNamesList.add("Student Or Teacher");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("'Student'");
							lvListDisplayed.getItems().remove("Student Or Teacher");
							columnNamesList.remove("Student Or Teacher");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}			}
				});

				paneIssuedBooks.add(boxFatherNameIssuedBooks, 1, 2);
				boxFatherNameIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherNameIssuedBooks.isSelected()) {
							lvList.getItems().add("all_students_and_parents.fathers_name");
							lvListDisplayed.getItems().add("Father's Name");
							columnNamesList.add("Father's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.fathers_name");
							lvListDisplayed.getItems().remove("Father's Name");
							columnNamesList.remove("Father's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxFatherContactIssuedBooks, 2, 2);

				boxFatherContactIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxFatherContactIssuedBooks.isSelected()) {
							lvList.getItems().add("all_students_and_parents.fathers_contact");
							lvListDisplayed.getItems().add("Father's Contact");
							columnNamesList.add("Father's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.fathers_contact");
							lvListDisplayed.getItems().remove("Father's Contact");
							columnNamesList.remove("Father's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxMotherNameIssuedBooks, 3, 2);

				boxMotherNameIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherNameIssuedBooks.isSelected()) {
							lvList.getItems().add("all_students_and_parents.mothers_name");
							lvListDisplayed.getItems().add("Mother's Name");
							columnNamesList.add("Mother's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.mothers_name");
							lvListDisplayed.getItems().remove("Mother's Name");
							columnNamesList.remove("Mother's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxMotherContactIssuedBooks, 0, 3);
				boxMotherContactIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxMotherContactIssuedBooks.isSelected()) {
							lvList.getItems().add("all_students_and_parents.mothers_contact");
							lvListDisplayed.getItems().add("Mother's Contact");
							columnNamesList.add("Mother's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.mothers_contact");
							lvListDisplayed.getItems().remove("Mother's Contact");
							columnNamesList.remove("Mother's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxGuardianNameIssuedBooks, 1, 3);
				boxGuardianNameIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianNameIssuedBooks.isSelected()) {
							lvList.getItems().add("all_students_and_parents.guardians_name");
							columnNamesList.add("Guardian's Name");
							lvListDisplayed.getItems().add("Guardian's Name");
							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.guardians_name");
							lvListDisplayed.getItems().remove("Guardian's Name");
							columnNamesList.remove("Guardian's Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxGuardianContactIssuedBooks, 2, 3);

				boxGuardianContactIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxGuardianContactIssuedBooks.isSelected()) {
							lvList.getItems().add("all_students_and_parents.guardians_contact");
							lvListDisplayed.getItems().add("Guardian's Contact");
							columnNamesList.add("Guardian's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.guardians_contact");
							lvListDisplayed.getItems().remove("Guardian's Contact");
							columnNamesList.remove("Guardian's Contact");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxSponsorshipStatusIssuedBooks, 3, 3);

				boxSponsorshipStatusIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorshipStatusIssuedBooks.isSelected()) {
							lvList.getItems().add("issued_books.book_title");
							lvListDisplayed.getItems().add("Book Title");
							columnNamesList.add("Book Title");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.book_title");
							lvListDisplayed.getItems().remove("Book Title");
							columnNamesList.remove("Book Title");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxSponsorNameIssuedBooks, 0, 4);

				boxSponsorNameIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorNameIssuedBooks.isSelected()) {
							lvList.getItems().add("issued_books.book_id");
							lvListDisplayed.getItems().add("Book ID");
							columnNamesList.add("Book ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";

							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.book_id");
							lvListDisplayed.getItems().remove("Book ID");
							columnNamesList.remove("Book ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code  ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxSponsorContactIssuedBooks, 1, 4);

				boxSponsorContactIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxSponsorContactIssuedBooks.isSelected()) {
							lvList.getItems().add("issued_books.book_author");
							lvListDisplayed.getItems().add("Book Author");
							columnNamesList.add("Book Author");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("issued_books.book_author");
							lvListDisplayed.getItems().remove("Book Author");
							columnNamesList.remove("Book Author");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneIssuedBooks.add(boxParentAddressIssuedBooks, 2, 4);

				boxParentAddressIssuedBooks.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxParentAddressIssuedBooks.isSelected()) {
							lvList.getItems().add("all_students_and_parents.parent_address");
							lvListDisplayed.getItems().add("Parent's Address");
							columnNamesList.add("Parent's Address");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("all_students_and_parents.parent_address");
							lvListDisplayed.getItems().remove("Parent's Address");
							columnNamesList.remove("Parent's Address");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";
							String sqlFrom = " ";
							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
							joinedTitles = TableViewTitles.stream().map(Object::toString).collect(Collectors.joining(", "));
							sqlFrom = joined + " FROM issued_books,all_students_and_parents ";
							String sqlWhere = " WHERE issued_books.student_name is not null and issued_books.issue_date between '"
									+ datePk.getValue() + "' and '" + datePkToDate.getValue()
									+ "' and all_students_and_parents.student_class='"
									+ combo.getSelectionModel().getSelectedItem()
									+ "' and issued_books.payment_code=all_students_and_parents.payment_code ";
							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				hboxSuspendedLibrary.getChildren().addAll(paneIssuedBooks);

				/*
				 * *****************************************************************************
				 * ***************** ***********************************Issued
				 * Books*******************************************
				 * *****************************************************************************
				 * ***************** ********************************End
				 * Here******************************************************
				 * 
				 */
				/////////////////// BOOK ENTRY/////////////////////////
				hboxEntryLibrary.setPadding(new Insets(15, 10, 15, 10));
				CheckBox boxDateOfBorrowingEntryLibrary = new CheckBox("Date of Entry");
				CheckBox boxDateOfReturnEntryLibrary = new CheckBox("Book Title");
				CheckBox boxDurationTakenEntryLibrary = new CheckBox("Subject");
				CheckBox boxLateByEntryLibrary = new CheckBox("Book Author");
				CheckBox boxStudentCodeEntryLibrary = new CheckBox("Book Publisher");
				CheckBox boxClassNumberOrContactEntryLibrary = new CheckBox("Shelf Stored In");
				CheckBox boxTeacherOrStudentNameEntryLibrary = new CheckBox("Entry ID");
				CheckBox boxIDNumberEntryLibrary = new CheckBox("Donor Or Source");
				CheckBox boxStaffNameEntryLibrary = new CheckBox("Quantity Received");

				GridPane paneEntryLibrary = new GridPane();

				paneEntryLibrary.setVgap(5);
				paneEntryLibrary.setHgap(5);
				paneEntryLibrary.setAlignment(Pos.CENTER);

				paneEntryLibrary.add(boxDateOfBorrowingEntryLibrary, 0, 0);
				boxDateOfBorrowingEntryLibrary.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDateOfBorrowingEntryLibrary.isSelected()) {

							lvList.getItems().add("dateIn");

							lvListDisplayed.getItems().add("Entry Date");

							columnNamesList.add("Entry Date");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("dateIn");
							lvListDisplayed.getItems().remove("Entry Date");
							columnNamesList.remove("Entry Date");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneEntryLibrary.add(boxDateOfReturnEntryLibrary, 1, 0);
				boxDateOfReturnEntryLibrary.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDateOfReturnEntryLibrary.isSelected()) {
							lvList.getItems().add("bookTitle");
							lvListDisplayed.getItems().add("Book Title");
							columnNamesList.add("Book Title");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							TableViewTitles.add("Return Date");
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("bookTitle");
							lvListDisplayed.getItems().remove("Book Title");
							columnNamesList.remove("Book Title");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneEntryLibrary.add(boxDurationTakenEntryLibrary, 2, 0);
				boxDurationTakenEntryLibrary.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxDurationTakenEntryLibrary.isSelected()) {
							lvList.getItems().add("subject");
							lvListDisplayed.getItems().add("Subject");

							columnNamesList.add("Subject");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("subject");
							lvListDisplayed.getItems().remove("Subject");
							columnNamesList.remove("Subject");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneEntryLibrary.add(boxLateByEntryLibrary, 3, 0);
				boxLateByEntryLibrary.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxLateByEntryLibrary.isSelected()) {
							lvList.getItems().add("author");
							lvListDisplayed.getItems().add("Book Author");
							columnNamesList.add("Book Author");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("author");
							lvListDisplayed.getItems().remove("Book Author");
							columnNamesList.remove("Book Author");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneEntryLibrary.add(boxStudentCodeEntryLibrary, 0, 1);
				boxStudentCodeEntryLibrary.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxStudentCodeEntryLibrary.isSelected()) {
							lvList.getItems().add("shelfName");
							lvListDisplayed.getItems().add("Storage Shelf Name");
							columnNamesList.add("Storage Shelf Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("shelfName");
							lvListDisplayed.getItems().remove("Storage Shelf Name");
							columnNamesList.remove("Storage Shelf Name");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneEntryLibrary.add(boxClassNumberOrContactEntryLibrary, 1, 1);

				boxClassNumberOrContactEntryLibrary.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxClassNumberOrContactEntryLibrary.isSelected()) {
							lvList.getItems().add("quantity");
							lvListDisplayed.getItems().add("Quantity Received");
							columnNamesList.add("Quantity Received");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("quantity");
							lvListDisplayed.getItems().remove("Quantity Received");
							columnNamesList.remove("Quantity Received");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneEntryLibrary.add(boxStaffNameEntryLibrary, 2, 1);

				boxStaffNameEntryLibrary.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxStaffNameEntryLibrary.isSelected()) {
							lvList.getItems().add("donor");
							lvListDisplayed.getItems().add("Donor Or Source");
							columnNamesList.add("Donor Or Source");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("donor");
							lvListDisplayed.getItems().remove("Donor Or Source");
							columnNamesList.remove("Donor Or Source");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneEntryLibrary.add(boxIDNumberEntryLibrary, 3, 1);

				boxIDNumberEntryLibrary.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (boxIDNumberEntryLibrary.isSelected()) {
							lvList.getItems().add("id");
							lvListDisplayed.getItems().add("Entry ID");
							columnNamesList.add("Entry ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);
							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						} else {
							lvList.getItems().remove("id");
							lvListDisplayed.getItems().remove("Entry ID");
							columnNamesList.remove("Entry ID");

							dataOfJTable = new String[1][columnNamesList.size()];

							columnNamesArr = new String[columnNamesList.size()];
							for (int i = 0; i < columnNamesList.size(); i++) {
								columnNamesArr[i] = (String) columnNamesList.get(i);
								dataOfJTable[0][i] = "";
							}

							defaultTableModel = new DefaultTableModel(dataOfJTable, columnNamesArr);

							table = new JTable(defaultTableModel);
							tableColumnModel = table.getColumnModel();

							for (int i = 0; i < columnNamesList.size(); i++) {
								tableColumnModel.getColumn(i).setPreferredWidth(((String) columnNamesList.get(i)).length());
							}
							table.setPreferredScrollableViewportSize(table.getPreferredSize());
							scrollPane = new JScrollPane(table);

							String sqlSelect = "SELECT ";

							String sqlFrom = " ";

							String joined = lvList.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));

							sqlFrom = joined + " FROM generaloverviewofbooks ";

							String sqlWhere = " WHERE dateIn between '" + datePk.getValue() + "' and '"
									+ datePkToDate.getValue() + "'";

							finalSql = sqlSelect + sqlFrom + sqlWhere;
							System.out.println(finalSql);
						}
					}
				});

				paneEntryLibrary.add(boxTeacherOrStudentNameEntryLibrary, 0, 2);

				hboxEntryLibrary.getChildren().addAll(paneEntryLibrary);

				/*
				 * *****************************************************************************
				 * ***************** *********************************** END OF BOOK ENTRY
				 */

				//////////////////////// ListView///////////////////////////
				BorderPane border = new BorderPane();
				border.setPadding(new Insets(10, 0, 10, 10));

				

				border.setCenter(lvListDisplayed);
				gridTitlePaneFields.setContent(border);

				////////////////////////////// GridPane//////////////////

				gridPaneFilter = new GridPane();
				gridPaneFilter.setVgap(10);
				gridPaneFilter.setHgap(10);
				gridPaneFilter.setPadding(new Insets(10));

				gridPaneFilter.add(box, 0, 0);
				gridPaneFilter.add(hbox, 1, 0);
				gridPaneFilter.add(gridTitlePaneFields, 2, 0);

				jfxPanel = new JFXPanel();

				scene = new Scene(gridPaneFilter);
				scene.setFill(Color.LIGHTGRAY);
				gridPaneFilter.setPrefSize(1170, 490);

				jfxPanel.setScene(scene);
				jfxPanel.setPreferredSize(new Dimension(1160, 480));
				add(jfxPanel);

				String styleSheet = getClass().getResource("attendance.css").toExternalForm();
				scene.getStylesheets().add(styleSheet);

				generateClasses(combo);
			}
		});
		
		
	}

	private final class TextFieldTreeCellImpl extends TreeCell<String> {

		private TextField textField;

		public TextFieldTreeCellImpl() {
		}

		@Override
		public void startEdit() {
			super.startEdit();

			if (textField == null) {
				createTextField();
			}
			setText(null);
			setGraphic(textField);
			textField.selectAll();
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();
			setText((String) getItem());
			setGraphic(getTreeItem().getGraphic());
		}

		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (textField != null) {
						textField.setText(getString());
					}
					setText(null);
					setGraphic(textField);
				} else {
					setText(getString());
					setGraphic(getTreeItem().getGraphic());
				}
			}
		}

		private void createTextField() {
			textField = new TextField(getString());
			textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent t) {
					if (t.getCode() == KeyCode.ENTER) {
						commitEdit(textField.getText());
					} else if (t.getCode() == KeyCode.ESCAPE) {
						cancelEdit();
					}
				}
			});
		}

		private String getString() {
			return getItem() == null ? "" : getItem().toString();
		}
	}

	public static class Employee {

		private final SimpleStringProperty name;
		private final SimpleStringProperty department;

		private Employee(String name, String department) {
			this.name = new SimpleStringProperty(name);
			this.department = new SimpleStringProperty(department);
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

	public void generateClasses(ComboBox combo) {

		String sql = "select class_name from student_classes";
		try {
			con = CashBookController.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next()) {
				data.add(rs.getString(1));

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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

	public void buildReportFrame() {
		mainFrame = new JDialog();

		// col1 = new JCheckBox("Column 1:");
		//
		// col1.setSelected(true);

		JMenuBar bar = new JMenuBar();
		mainFrame.setJMenuBar(bar);

		JMenu menuExport = new JMenu("Export");

		JMenuItem itemExcel = new JMenuItem("Export To Excel Values");
		JMenuItem itemCSV = new JMenuItem("Export To CSV Values");
		JMenuItem itemPDF = new JMenuItem("Export To PDF Values");

		menuExport.add(itemExcel);
		menuExport.addSeparator();
		menuExport.add(itemCSV);
		menuExport.addSeparator();
		menuExport.add(itemPDF);

		bar.add(menuExport);

		mainFrame.setTitle("My Report");

		// addButton = new JButton("Export To Excel");
		//
		// addButton.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// Vector rowData = null;
		// defaultTableModel.addRow(rowData);
		// table.validate();
		// }
		//
		// @Override
		// public void actionPerformed(java.awt.event.ActionEvent e) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		panel = new JPanel();
		// panel.add(col1);

		panelButton = new JPanel();
		// panelButton.add(addButton);

		table.validate();

		mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		mainFrame.add(scrollPane, BorderLayout.CENTER);
		mainFrame.add(panel, BorderLayout.NORTH);
		mainFrame.add(panelButton, BorderLayout.SOUTH);
		mainFrame.setSize(800, 450);
		mainFrame.setLocationRelativeTo(null);

		columnNamesList.remove("#");
		table.validate();
		displayDataOnly(table, finalSql);

		Timer timer = new Timer();
		TimerTask ttask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mainFrame.setVisible(true);
			}
		};
		timer.schedule(ttask, 5000);

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

				table.setModel(DbUtils.resultSetToTableModel(rs));
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
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void displayDataOnly(JTable table, String query) {

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();

			while (table.getRowCount() > 0) {
				defaultTableModel.removeRow(0);

			}
			int columns = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				Object[] row = new Object[columns];
				for (int i = 1; i <= columns; i++) {
					row[i - 1] = rs.getObject(i);
				}
				defaultTableModel.insertRow(rs.getRow() - 1, row);

			}

			pst.close();
			conn.close();
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void isEndDateGreaterThanStartDate(String dateformat, String startDate, String endDate,
			DatePicker staDate, DatePicker enDate, Button btnRun) {

		String format = dateformat;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		java.util.Date sDate = null;
		java.util.Date eDate = null;
		try {
			sDate = sdf.parse(startDate);
			eDate = sdf.parse(endDate);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (eDate.after(sDate)) {
			staDate.setStyle("-fx-background-color: green;");
			enDate.setStyle("-fx-background-color: green;");
			btnRun.setDisable(false);
			System.out.println("Start Date is Earlier Than End Date");
		} else if (eDate.equals(sDate)) {
			staDate.setStyle("-fx-background-color: green;");
			enDate.setStyle("-fx-background-color: green;");
			btnRun.setDisable(false);
			System.out.println("End Date is Equal To Start Date");
		} else if (eDate.before(sDate)) {
			staDate.setStyle("-fx-background-color: red;");
			enDate.setStyle("-fx-background-color: red;");
			btnRun.setDisable(true);
			System.out.println("End Date is Earlier Than Start Date");
		}

	}

	public ResultSet displaySchoolLogo() throws SQLException, ClassNotFoundException {

		con = CashBookController.getConnection();

		Statement statement = con.createStatement();

		ResultSet result = statement.executeQuery("SELECT school_logo from school_details");

		return result;

	}

}
