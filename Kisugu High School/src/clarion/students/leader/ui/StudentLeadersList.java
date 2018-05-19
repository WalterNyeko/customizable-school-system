package clarion.students.leader.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.JPanel;

import clarion.student.leaders.core.Club;
import clarion.student.leaders.core.PrefectPost;
import clarion.student.leaders.core.Prefects;
import clarion.student.leaders.core.UNSA;
import clarion.student.leaders.core.UNSAPost;
import clarion.students.leader.dao.ClubDAO;
import clarion.students.leader.dao.ClubTableDAO;
import clarion.students.leader.dao.PrefectPostDAO;
import clarion.students.leader.dao.PrefectsDAO;
import clarion.students.leader.dao.UNSADAO;
import clarion.students.leader.dao.UNSAPostDAO;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StudentLeadersList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFXPanel jfxPanelStudentLeaders;

	// prefect
	private TitledPane titledPanePrefects;
	private Button btnAddNewPrefectPost;
	private Button btnEditPrefectPost;
	private VBox vBoxPrefect;
	// private ScrollPane scrollPanePrefects;
	private ListView<String> listViewPrefects;
	private GridPane gridPanePrefects;

	private TableView<Prefects> tableViewPrefects;

	private TableColumn<Prefects, String> tcPrefectsIDNumber;
	private TableColumn<Prefects, String> tcPrefectsFirstName;
	private TableColumn<Prefects, String> tcPrefectsMiddleName;
	private TableColumn<Prefects, String> tcPrefectsLastName;

	private TableColumn<Prefects, String> tcPrefectsNameAll;

	private TableColumn<Prefects, String> tcPrefectsStudentClass;
	private TableColumn<Prefects, String> tcPrefectsGender;
	private TableColumn<Prefects, String> tcPrefectsYearOfRegime;
	private TableColumn<Prefects, String> tcPrefectsPost;

	private HBox hBoxPrefects;
	private VBox vBoxPrefectsBtnsFirstSet;
	private GridPane gridPanePrefectsBtnFirstSet;

	private Button btnAddNewPrefect;
	private Button btnEditPrefect;
	private Button btnRemovePrefect;

	private BorderPane borderPanePrefects;

	private Button btnRemovePrefectPost;

	// UNSA
	private TitledPane titledPaneUNSA;
	private Button btnAddNewUNSAPost;
	private Button btnEditUNSAPost;
	private VBox vBoxUNSA;
	// private ScrollPane scrollPaneUNSA;
	// private ListView<Object> listViewUNSA;

	private TableView<UNSA> tableViewUNSA;

	private TableColumn<UNSA, String> tcUNSAIDNumber;
	private TableColumn<UNSA, String> tcUNSAFirstName;
	private TableColumn<UNSA, String> tcUNSAMiddleName;
	private TableColumn<UNSA, String> tcUNSALastName;

	private TableColumn<UNSA, String> tcUNSANameAll;

	private TableColumn<UNSA, String> tcUNSAStudentClass;
	private TableColumn<UNSA, String> tcUNSAGender;
	private TableColumn<UNSA, String> tcUNSAYearOfRegime;
	private TableColumn<UNSA, String> tcUNSAPost;

	private HBox hBoxUNSA;
	private VBox vBoxUNSABtnsFirstSet;
	private GridPane gridPaneUNSABtnFirstSet;

	private Button btnAddNewUNSA;
	private Button btnEditUNSA;
	private Button btnRemoveUNSA;

	private BorderPane borderPaneUNSA;

	private GridPane gridPaneUNSA;
	private Button btnRemoveUNSAPost;

	// private Button btnAddNewUNSAPost;
	// private Button btnEditUNSAPost;
	// private VBox vBoxUNSA;
	// private ScrollPane scrollPaneUNSA;
	private ListView<String> listViewUNSA;

	// Clubs & Society
	private TitledPane titledPaneClubsAndSociety;
	private GridPane gridPaneClubsAndSociety;
	private Button btnAddNewClubsAndSociety;
	private Button btnEditClubsAndSociety;
	private VBox vBoxClubsAndSociety;

	private TableView<Club> tableViewClub;

	private TableColumn<Club, String> tcClubIDNumber;
	private TableColumn<Club, String> tcClubFirstName;
	private TableColumn<Club, String> tcClubMiddleName;
	private TableColumn<Club, String> tcClubLastName;

	private TableColumn<Club, String> tcClubNameAll;

	private TableColumn<Club, String> tcClubStudentClass;
	private TableColumn<Club, String> tcClubGender;
	private TableColumn<Club, String> tcClubYearOfRegime;
	private TableColumn<Club, String> tcClubPost;

	private HBox hBoxClub;
	private VBox vBoxClubBtnsFirstSet;
	private GridPane gridPaneClubBtnFirstSet;

	private Label labelID;

	private Button btnAddNewClub;
	private Button btnEditClub;
	private Button btnRemoveClubAndSociety;

	private BorderPane borderPaneClub;

	private GridPane gridPaneClub;
	private Button btnRemoveClub;

	// private Button btnAddNewClubPost;
	// private Button btnEditClubPost;
	// private VBox vBoxClub;
	// private ScrollPane scrollPaneClub;
	private ListView<Object> listViewClub;

	// private ScrollPane scrollPaneClubsAndSociety;
	private ListView<Object> listViewClubsAndSociety;

	private Accordion accordionStudentLeaders;

	private BorderPane borderPaneStudentLeaders;

	private Scene sceneStudentLeaders;

	// private Button btnAddNewStudentLeader;

	// private JList<Object> listStudentLeaders;

	public StudentLeadersList() {
		setItUp();
	}

	private void setItUp() {
		// this.setBackground(Color.red);

		jfxPanelStudentLeaders = new JFXPanel();
		jfxPanelStudentLeaders.setPreferredSize(new Dimension(1160, 480));

		borderPaneStudentLeaders = new BorderPane();

		/************************************************************************************
		 * components into the big BorderPane start here * *
		 ************************************************************************************/

		// prefects start

		btnAddNewPrefectPost = new Button("New Prefect Post");
		btnAddNewPrefectPost.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		// btnAddNewPrefectPost.setStyle("-fx-background-color:#4447fe;");

		btnEditPrefectPost = new Button("Edit Prefect Post");
		btnEditPrefectPost.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		btnRemovePrefectPost = new Button("Remove Prefect Post");
		btnRemovePrefectPost.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		listViewPrefects = new ListView<>();

		gridPanePrefects = new GridPane();
		gridPanePrefects.setVgap(10);
		gridPanePrefects.setHgap(10);
		gridPanePrefects.setPadding(new Insets(10));

		gridPanePrefects.add(btnAddNewPrefectPost, 0, 0);
		gridPanePrefects.add(btnEditPrefectPost, 1, 0);

		listViewPrefects = new ListView<>();
		refreshPrefectPostList();

		vBoxPrefect = new VBox();
		vBoxPrefect.setSpacing(10);

		vBoxPrefect.getChildren().addAll(gridPanePrefects, listViewPrefects, btnRemovePrefectPost);

		// prefects end

		/************************************************************************************
		 *************************** UNSA starts ********************************************
		 ************************************************************************************/

		btnAddNewUNSAPost = new Button("New UNSA Post");

		btnAddNewUNSAPost.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		btnEditUNSAPost = new Button("Edit UNSA Post");
		btnEditUNSAPost.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		gridPaneUNSA = new GridPane();
		gridPaneUNSA = new GridPane();
		gridPaneUNSA.setVgap(10);
		gridPaneUNSA.setHgap(10);
		gridPaneUNSA.setPadding(new Insets(10));

		gridPaneUNSA.add(btnAddNewUNSAPost, 0, 0);
		gridPaneUNSA.add(btnEditUNSAPost, 1, 0);

		listViewUNSA = new ListView<>();

		vBoxUNSA = new VBox();
		vBoxUNSA.setSpacing(10);

		btnRemoveUNSAPost = new Button("Remove UNSA Post");
		btnRemoveUNSAPost.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		vBoxUNSA.getChildren().addAll(gridPaneUNSA, listViewUNSA, btnRemoveUNSAPost);

		/************************************************************************************
		 *************************** ClubsAndSociety starts *********************************
		 ************************************************************************************/

		btnAddNewClubsAndSociety = new Button("New Club & Society");
		btnAddNewClubsAndSociety.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		btnEditClubsAndSociety = new Button("Edit Club & Society");
		btnEditClubsAndSociety.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		gridPaneClubsAndSociety = new GridPane();
		gridPaneClubsAndSociety = new GridPane();
		gridPaneClubsAndSociety.setVgap(10);
		gridPaneClubsAndSociety.setHgap(10);
		gridPaneClubsAndSociety.setPadding(new Insets(10));

		gridPaneClubsAndSociety.add(btnAddNewClubsAndSociety, 0, 0);
		gridPaneClubsAndSociety.add(btnEditClubsAndSociety, 1, 0);

		listViewClubsAndSociety = new ListView<>();

		vBoxClubsAndSociety = new VBox();

		vBoxClubsAndSociety.setSpacing(10);

		btnRemoveClubAndSociety = new Button("Remove Club/Society");
		btnRemoveClubAndSociety.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		vBoxClubsAndSociety.getChildren().addAll(gridPaneClubsAndSociety, listViewClubsAndSociety,
				btnRemoveClubAndSociety);

		gridPaneClubsAndSociety = new GridPane();

		titledPanePrefects = new TitledPane();
		titledPanePrefects.setExpanded(false);
		titledPanePrefects.setText("Prefects Posts");
		titledPanePrefects.setContent(vBoxPrefect);

		titledPaneUNSA = new TitledPane();
		titledPaneUNSA.setText("UNSA Posts");
		titledPaneUNSA.setContent(vBoxUNSA);

		titledPaneClubsAndSociety = new TitledPane();
		titledPaneClubsAndSociety.setText("Clubs And Societies");
		titledPaneClubsAndSociety.setContent(vBoxClubsAndSociety);

		accordionStudentLeaders = new Accordion();
		accordionStudentLeaders.getPanes().addAll(titledPanePrefects, titledPaneUNSA, titledPaneClubsAndSociety);

		/************************************************************************************
		 *************************** Prefects starts ****************************************
		 ************************************************************************************/

		borderPanePrefects = new BorderPane();
		borderPanePrefects.setVisible(false);

		tableViewPrefects = new TableView<>();
		tableViewPrefects.setTableMenuButtonVisible(true);

		tcPrefectsIDNumber = new TableColumn<>("ID Number");
		tcPrefectsIDNumber.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
		tcPrefectsIDNumber.setPrefWidth(120);

		tcPrefectsFirstName = new TableColumn<>("First Name");
		tcPrefectsFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		tcPrefectsFirstName.setPrefWidth(120);

		tcPrefectsMiddleName = new TableColumn<>("Middle Name");
		tcPrefectsMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
		tcPrefectsMiddleName.setPrefWidth(120);

		tcPrefectsLastName = new TableColumn<>("Last Name");
		tcPrefectsLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		tcPrefectsLastName.setPrefWidth(120);

		tcPrefectsNameAll = new TableColumn<>("Name");
		tcPrefectsNameAll.getColumns().addAll(tcPrefectsFirstName, tcPrefectsMiddleName, tcPrefectsLastName);

		// tcPrefectsNameAll.setStyle("-fx-background-color: green;");

		tcPrefectsStudentClass = new TableColumn<>("Class");
		tcPrefectsStudentClass.setCellValueFactory(new PropertyValueFactory<>("studentClass"));
		tcPrefectsStudentClass.setPrefWidth(60);

		tcPrefectsGender = new TableColumn<>("Gender");
		tcPrefectsGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		tcPrefectsGender.setPrefWidth(60);

		tcPrefectsYearOfRegime = new TableColumn<>("Year");
		tcPrefectsYearOfRegime.setCellValueFactory(new PropertyValueFactory<>("yearOfRegime"));
		tcPrefectsYearOfRegime.setPrefWidth(60);

		tcPrefectsPost = new TableColumn<>("Post");
		tcPrefectsPost.setCellValueFactory(new PropertyValueFactory<>("post"));
		tcPrefectsPost.setPrefWidth(120);

		tableViewPrefects.getColumns().addAll(tcPrefectsIDNumber, tcPrefectsNameAll, tcPrefectsStudentClass,
				tcPrefectsGender, tcPrefectsYearOfRegime, tcPrefectsPost);

		refreshPrefrectsTable();

		borderPanePrefects.setCenter(tableViewPrefects);

		// Manipulating prefects start
		hBoxPrefects = new HBox();
		vBoxPrefectsBtnsFirstSet = new VBox();
		hBoxPrefects.getChildren().addAll(vBoxPrefectsBtnsFirstSet);

		btnAddNewPrefect = new Button("Add New Prefect");
		btnAddNewPrefect.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		btnEditPrefect = new Button("Edit Prefect");
		btnEditPrefect.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		btnRemovePrefect = new Button("Remove");
		btnRemovePrefect.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		gridPanePrefectsBtnFirstSet = new GridPane();
		gridPanePrefectsBtnFirstSet.setVgap(10);
		gridPanePrefectsBtnFirstSet.setHgap(10);
		gridPanePrefectsBtnFirstSet.setPadding(new Insets(10));

		gridPanePrefectsBtnFirstSet.add(btnAddNewPrefect, 0, 0);
		gridPanePrefectsBtnFirstSet.add(btnEditPrefect, 0, 1);
		gridPanePrefectsBtnFirstSet.add(btnRemovePrefect, 0, 2);

		vBoxPrefectsBtnsFirstSet.getChildren().add(gridPanePrefectsBtnFirstSet);

		borderPanePrefects.setTop(hBoxPrefects);

		/************************************************************************************
		 *************************** UNSA starts ****************************************
		 ************************************************************************************/

		borderPaneUNSA = new BorderPane();
		borderPaneUNSA.setVisible(false);
		borderPaneStudentLeaders.setCenter(borderPaneUNSA);

		tableViewUNSA = new TableView<>();
		tableViewUNSA.setTableMenuButtonVisible(true);

		tcUNSAIDNumber = new TableColumn<>("ID Number");
		tcUNSAIDNumber.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
		tcUNSAIDNumber.setPrefWidth(120);

		tcUNSAFirstName = new TableColumn<>("First Name");
		tcUNSAFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		tcUNSAFirstName.setPrefWidth(120);

		tcUNSAMiddleName = new TableColumn<>("Middle Name");
		tcUNSAMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
		tcUNSAMiddleName.setPrefWidth(120);

		tcUNSALastName = new TableColumn<>("Last Name");
		tcUNSALastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		tcUNSALastName.setPrefWidth(120);

		tcUNSANameAll = new TableColumn<>("Name");
		tcUNSANameAll.getColumns().addAll(tcUNSAFirstName, tcUNSAMiddleName, tcUNSALastName);

		tcUNSAStudentClass = new TableColumn<>("Class");
		tcUNSAStudentClass.setCellValueFactory(new PropertyValueFactory<>("studentClass"));
		tcUNSAStudentClass.setPrefWidth(60);

		tcUNSAGender = new TableColumn<>("Gender");
		tcUNSAGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		tcUNSAGender.setPrefWidth(60);

		tcUNSAYearOfRegime = new TableColumn<>("Year");
		tcUNSAYearOfRegime.setCellValueFactory(new PropertyValueFactory<>("yearOfRegime"));
		tcUNSAYearOfRegime.setPrefWidth(60);

		tcUNSAPost = new TableColumn<>("Post");
		tcUNSAPost.setCellValueFactory(new PropertyValueFactory<>("post"));
		tcUNSAPost.setPrefWidth(120);

		tableViewUNSA.getColumns().addAll(tcUNSAIDNumber, tcUNSANameAll, tcUNSAStudentClass, tcUNSAGender,
				tcUNSAYearOfRegime, tcUNSAPost);

		refreshUNSATable();

		borderPaneUNSA.setCenter(tableViewUNSA);

		// Manipulating UNSA start
		hBoxUNSA = new HBox();
		vBoxUNSABtnsFirstSet = new VBox();
		hBoxUNSA.getChildren().addAll(vBoxUNSABtnsFirstSet);

		btnAddNewUNSA = new Button("Add New UNSA");
		btnAddNewUNSA.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		btnEditUNSA = new Button("Edit UNSA");
		btnEditUNSA.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		btnRemoveUNSA = new Button("Remove");
		btnRemoveUNSA.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		gridPaneUNSABtnFirstSet = new GridPane();
		gridPaneUNSABtnFirstSet.setVgap(10);
		gridPaneUNSABtnFirstSet.setHgap(10);
		gridPaneUNSABtnFirstSet.setPadding(new Insets(10));

		gridPaneUNSABtnFirstSet.add(btnAddNewUNSA, 0, 0);
		gridPaneUNSABtnFirstSet.add(btnEditUNSA, 0, 1);
		gridPaneUNSABtnFirstSet.add(btnRemoveUNSA, 0, 2);

		vBoxUNSABtnsFirstSet.getChildren().add(gridPaneUNSABtnFirstSet);

		borderPaneUNSA.setTop(hBoxUNSA);

		/************************************************************************************
		 *************************** Club starts ****************************************
		 ************************************************************************************/

		borderPaneClub = new BorderPane();
		borderPaneClub.setVisible(false);
		borderPaneStudentLeaders.setCenter(borderPaneClub);

		tableViewClub = new TableView<>();
		tableViewClub.setTableMenuButtonVisible(true);

		tcClubIDNumber = new TableColumn<>("ID Number");
		tcClubIDNumber.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
		tcClubIDNumber.setPrefWidth(120);

		tcClubFirstName = new TableColumn<>("First Name");
		tcClubFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		tcClubFirstName.setPrefWidth(120);

		tcClubMiddleName = new TableColumn<>("Middle Name");
		tcClubMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
		tcClubMiddleName.setPrefWidth(120);

		tcClubLastName = new TableColumn<>("Last Name");
		tcClubLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		tcClubLastName.setPrefWidth(120);

		tcClubNameAll = new TableColumn<>("Name");
		tcClubNameAll.getColumns().addAll(tcClubFirstName, tcClubMiddleName, tcClubLastName);

		tcClubStudentClass = new TableColumn<>("Class");
		tcClubStudentClass.setCellValueFactory(new PropertyValueFactory<>("studentClass"));
		tcClubStudentClass.setPrefWidth(60);

		tcClubGender = new TableColumn<>("Gender");
		tcClubGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		tcClubGender.setPrefWidth(60);

		tcClubYearOfRegime = new TableColumn<>("Year");
		tcClubYearOfRegime.setCellValueFactory(new PropertyValueFactory<>("yearOfRegime"));
		tcClubYearOfRegime.setPrefWidth(60);

		tcClubPost = new TableColumn<>("Post");
		tcClubPost.setCellValueFactory(new PropertyValueFactory<>("post"));
		tcClubPost.setPrefWidth(120);

		tableViewClub.getColumns().addAll(tcClubIDNumber, tcClubNameAll, tcClubStudentClass, tcClubGender,
				tcClubYearOfRegime, tcClubPost);

		// refreshClubTable();

		borderPaneClub.setCenter(tableViewClub);

		// Manipulating Club start

		hBoxClub = new HBox();
		vBoxClubBtnsFirstSet = new VBox();
		hBoxClub.getChildren().addAll(vBoxClubBtnsFirstSet);

		btnAddNewClub = new Button("Add Club Member");
		btnAddNewClub.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		btnEditClub = new Button("Edit Club Member");
		btnEditClub.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		btnRemoveClub = new Button("Remove Club Member");
		btnRemoveClub.setStyle("-fx-font: bold 8pt \"Arial\"; "
				+ "-fx-effect: dropshadow( one-pass-box , #8B4500 , 8 , 0.0 , 2 , 0 )");

		labelID = new Label();

		gridPaneClubBtnFirstSet = new GridPane();
		gridPaneClubBtnFirstSet.setVgap(10);
		gridPaneClubBtnFirstSet.setHgap(10);
		gridPaneClubBtnFirstSet.setPadding(new Insets(10));

		gridPaneClubBtnFirstSet.add(btnAddNewClub, 0, 0);
		gridPaneClubBtnFirstSet.add(btnEditClub, 0, 1);
		gridPaneClubBtnFirstSet.add(btnRemoveClub, 0, 2);

		// gridPaneClubBtnFirstSet.add(labelID, 3, 2);

		vBoxClubBtnsFirstSet.getChildren().add(gridPaneClubBtnFirstSet);

		borderPaneClub.setTop(hBoxClub);

		/*
		 * Opening the prefect post dialog
		 */

		btnAddNewPrefectPost.setOnAction(e -> {
			titledPanePrefects.setExpanded(false);
			new PrefectPostDialog().showAndWait();
		});

		/*
		 * opening the UNSA post dialog
		 */

		btnAddNewUNSAPost.setOnAction(e -> {
			titledPaneUNSA.setExpanded(false);
			new UNSAPostDialog().showAndWait();
		});

		btnAddNewClubsAndSociety.setOnAction(e -> {
			titledPaneClubsAndSociety.setExpanded(false);
			new ClubPostDialog().showAndWait();
		});

		btnEditPrefectPost.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				titledPanePrefects.setExpanded(false);

				Object post = listViewPrefects.getSelectionModel().getSelectedItem();

				PrefectPostDialog dialog = new PrefectPostDialog();

				dialog.getFieldPrefectPost().setText(post.toString());

				dialog.setTitle("Changing Prefect Post");

				dialog.getBtnSave().setText("Save Changes");

				final PrefectPostDAO postDAO = new PrefectPostDAO();
				// int id = 0;

				try {

					int id = postDAO.getOnePrefectPostID(post.toString()).getId();

					dialog.getBtnSave().setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							PrefectPost prefectPostPrevious = null;

							prefectPostPrevious = new PrefectPost(post.toString());

							PrefectPost prefectPost = prefectPostPrevious;

							String newPost = dialog.getFieldPrefectPost().getText();

							prefectPost.setPrepfectPost(newPost);

							prefectPost = new PrefectPost(newPost);
							try {
								postDAO.editPrefectPost(prefectPost, id);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							refreshPrefectPostList();

							dialog.close();
							titledPanePrefects.setExpanded(true);

						}
					});

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				dialog.showAndWait();

			}
		});

		btnEditUNSAPost.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				titledPaneUNSA.setExpanded(false);

				Object post = listViewUNSA.getSelectionModel().getSelectedItem();

				UNSAPostDialog dialog = new UNSAPostDialog();

				dialog.getFieldUNSAPost().setText(post.toString());

				dialog.setTitle("Changing UNSA Post");

				dialog.getBtnSave().setText("Save Changes");

				UNSAPostDAO postDAO;
					

				try {

					postDAO = new UNSAPostDAO();
					int id = postDAO.getOneUNSAPostID(post.toString()).getId();

					dialog.getBtnSave().setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							UNSAPost UNSAPostPrevious = null;

							UNSAPostPrevious = new UNSAPost(post.toString());

							UNSAPost UNSAPost = UNSAPostPrevious;

							String newPost = dialog.getFieldUNSAPost().getText();

							UNSAPost.setPrepfectPost(newPost);

							UNSAPost = new UNSAPost(newPost);
								try {
									postDAO.editUNSAPost(UNSAPost, id);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							refreshUNSAPostList();

							dialog.close();
							titledPaneUNSA.setExpanded(true);

						}
					});

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				dialog.showAndWait();

			}
		});

		btnAddNewPrefect.setOnAction(e -> {

			PrefectDialog dialog = new PrefectDialog();

			dialog.getBtnSave().setOnAction(e2 -> {

				PrefectsDAO prefectsDAO = null;
				try {
					prefectsDAO = new PrefectsDAO();
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}

				String idNumber = dialog.getFieldIDNumber().getText();
				String firstName = dialog.getFieldFirstName().getText();
				String middleName = dialog.getFieldMiddleName().getText();
				String lastName = dialog.getFieldLastName().getText();
				String studentClass = dialog.getComboBoxStudentClass().getSelectionModel().getSelectedItem().toString();
				String gender = dialog.getComboBoxGender().getSelectionModel().getSelectedItem().toString();
				String yearOfRegime = dialog.getFieldYearOfRegime().getText();
				String post = dialog.getComboBoxPost().getSelectionModel().getSelectedItem().toString();

				Prefects prefects = null;
				prefects = new Prefects(idNumber, firstName, middleName, lastName, studentClass, gender, yearOfRegime,
						post);

				try {
					prefectsDAO.addNewPrefect(prefects);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				dialog.close();
				refreshPrefrectsTable();

			});

			dialog.showAndWait();
		});

		btnAddNewUNSA.setOnAction(e -> {

			UNSADialog dialog = new UNSADialog();

			dialog.getBtnSave().setOnAction(e2 -> {

				UNSADAO UNSAsDAO = null;
				try {
					UNSAsDAO = new UNSADAO();
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}

				String idNumber = dialog.getFieldIDNumber().getText();
				String firstName = dialog.getFieldFirstName().getText();
				String middleName = dialog.getFieldMiddleName().getText();
				String lastName = dialog.getFieldLastName().getText();
				String studentClass = dialog.getComboBoxStudentClass().getSelectionModel().getSelectedItem().toString();
				String gender = dialog.getComboBoxGender().getSelectionModel().getSelectedItem().toString();
				String yearOfRegime = dialog.getFieldYearOfRegime().getText();
				String post = dialog.getComboBoxPost().getSelectionModel().getSelectedItem().toString();

				UNSA UNSAs = null;
				UNSAs = new UNSA(idNumber, firstName, middleName, lastName, studentClass, gender, yearOfRegime, post);

				try {
					UNSAsDAO.addNewUNSA(UNSAs);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				dialog.close();
				refreshUNSATable();

			});

			dialog.showAndWait();
		});

		btnEditPrefect.setOnAction(e -> {

			PrefectDialog dialog = new PrefectDialog();
			dialog.getBtnSave().setText("Save Changes");
			dialog.setTitle("Editing Prefects Info");
			dialog.getComboBoxStudentClass().setEditable(true);
			dialog.getComboBoxGender().setEditable(true);
			dialog.getComboBoxPost().setEditable(true);

			Prefects prefectsPrev = tableViewPrefects.getSelectionModel().getSelectedItem();

			int id = prefectsPrev.getId();

			if (id < 0) {
				Alert alert = new Alert(AlertType.NONE);
				alert.setTitle("Message");
				alert.setContentText("You must select a row from the table");
				alert.showAndWait();
			} else {
				dialog.getFieldIDNumber().setText(prefectsPrev.getIdNumber());
				dialog.getFieldFirstName().setText(prefectsPrev.getFirstName());
				dialog.getFieldMiddleName().setText(prefectsPrev.getMiddleName());
				dialog.getFieldLastName().setText(prefectsPrev.getLastName());
				dialog.getComboBoxStudentClass().getEditor().setText(prefectsPrev.getStudentClass());
				dialog.getComboBoxGender().getEditor().setText(prefectsPrev.getGender());
				dialog.getFieldYearOfRegime().setText(prefectsPrev.getYearOfRegime());
				dialog.getComboBoxPost().getEditor().setText(prefectsPrev.getPost());

				Prefects prefNew = prefectsPrev;

				dialog.getBtnSave().setOnAction(e1 -> {

					String idNumber = dialog.getFieldIDNumber().getText();
					String firstName = dialog.getFieldFirstName().getText();
					String middleName = dialog.getFieldMiddleName().getText();
					String lastName = dialog.getFieldLastName().getText();
					String studentClass = dialog.getComboBoxStudentClass().getSelectionModel().getSelectedItem();
					String gender = dialog.getComboBoxGender().getSelectionModel().getSelectedItem();
					String yearOfRegime = dialog.getFieldYearOfRegime().getText();
					String post = dialog.getComboBoxPost().getSelectionModel().getSelectedItem();

					prefNew.setIdNumber(idNumber);
					prefNew.setFirstName(firstName);
					prefNew.setMiddleName(middleName);
					prefNew.setLastName(lastName);
					prefNew.setStudentClass(studentClass);
					prefNew.setGender(gender);
					prefNew.setYearOfRegime(yearOfRegime);
					prefNew.setPost(post);

					PrefectsDAO dao = null;
					try {
						dao = new PrefectsDAO();
					} catch (SQLException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}

					try {
						dao.editPrefect(prefNew, id);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					dialog.close();
					refreshPrefrectsTable();

				});

			}
			dialog.showAndWait();
		});

		btnEditUNSA.setOnAction(e -> {

			UNSADialog dialog = new UNSADialog();
			dialog.getBtnSave().setText("Save Changes");
			dialog.setTitle("Editing UNSA Info");
			dialog.getComboBoxStudentClass().setEditable(true);
			dialog.getComboBoxGender().setEditable(true);
			dialog.getComboBoxPost().setEditable(true);

			UNSA UNSAsPrev = tableViewUNSA.getSelectionModel().getSelectedItem();

			int id = UNSAsPrev.getId();

			if (id < 0) {
				Alert alert = new Alert(AlertType.NONE);
				alert.setTitle("Message");
				alert.setContentText("You must select a row from the table");
				alert.showAndWait();
			} else {
				dialog.getFieldIDNumber().setText(UNSAsPrev.getIdNumber());
				dialog.getFieldFirstName().setText(UNSAsPrev.getFirstName());
				dialog.getFieldMiddleName().setText(UNSAsPrev.getMiddleName());
				dialog.getFieldLastName().setText(UNSAsPrev.getLastName());
				dialog.getComboBoxStudentClass().getEditor().setText(UNSAsPrev.getStudentClass());
				dialog.getComboBoxGender().getEditor().setText(UNSAsPrev.getGender());
				dialog.getFieldYearOfRegime().setText(UNSAsPrev.getYearOfRegime());
				dialog.getComboBoxPost().getEditor().setText(UNSAsPrev.getPost());

				UNSA prefNew = UNSAsPrev;

				dialog.getBtnSave().setOnAction(e1 -> {

					String idNumber = dialog.getFieldIDNumber().getText();
					String firstName = dialog.getFieldFirstName().getText();
					String middleName = dialog.getFieldMiddleName().getText();
					String lastName = dialog.getFieldLastName().getText();
					String studentClass = dialog.getComboBoxStudentClass().getSelectionModel().getSelectedItem();
					String gender = dialog.getComboBoxGender().getSelectionModel().getSelectedItem();
					String yearOfRegime = dialog.getFieldYearOfRegime().getText();
					String post = dialog.getComboBoxPost().getSelectionModel().getSelectedItem();

					prefNew.setIdNumber(idNumber);
					prefNew.setFirstName(firstName);
					prefNew.setMiddleName(middleName);
					prefNew.setLastName(lastName);
					prefNew.setStudentClass(studentClass);
					prefNew.setGender(gender);
					prefNew.setYearOfRegime(yearOfRegime);
					prefNew.setPost(post);

					UNSADAO dao = null;
					try {
						dao = new UNSADAO();
					} catch (SQLException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}

					try {
						dao.editUNSA(prefNew, id);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					dialog.close();
					refreshUNSATable();

				});

			}
			dialog.showAndWait();
		});

		btnEditClub.setOnAction(e -> {

			ClubDialog dialog = new ClubDialog();

			String tableName = listViewClubsAndSociety.getSelectionModel().getSelectedItem().toString();

			dialog.setTitle("Edit " + tableName + " Member info");

			dialog.getBtnSave().setText("Save Change");
			dialog.getComboBoxStudentClass().setEditable(true);
			dialog.getComboBoxGender().setEditable(true);
			// dialog.getFieldPost().getText();

			Club clubPrev = tableViewClub.getSelectionModel().getSelectedItem();

			int id = clubPrev.getId();

			if (id < 0) {
				Alert alert = new Alert(AlertType.NONE);
				alert.setTitle("Message");
				alert.setContentText("You must select a row from the table");
				alert.showAndWait();
			} else {
				dialog.getFieldIDNumber().setText(clubPrev.getIdNumber());
				dialog.getFieldFirstName().setText(clubPrev.getFirstName());
				dialog.getFieldMiddleName().setText(clubPrev.getMiddleName());
				dialog.getFieldLastName().setText(clubPrev.getLastName());
				dialog.getComboBoxStudentClass().getEditor().setText(clubPrev.getStudentClass());
				dialog.getComboBoxGender().getEditor().setText(clubPrev.getGender());
				dialog.getFieldYearOfRegime().setText(clubPrev.getYearOfRegime());
				dialog.getFieldPost().setText(clubPrev.getPost());

				Club clubNew = clubPrev;

				dialog.getBtnSave().setOnAction(e1 -> {

					String idNumber = dialog.getFieldIDNumber().getText();
					String firstName = dialog.getFieldFirstName().getText();
					String middleName = dialog.getFieldMiddleName().getText();
					String lastName = dialog.getFieldLastName().getText();
					// String studentClass =
					// dialog.getComboBoxStudentClass().getSelectionModel().getSelectedItem()
					// .toString();

					String studentClass = dialog.getComboBoxStudentClass().getEditor().getText();

					// dialog.getComboBoxStudentClass().getEditor().setText(prefectsPrev.getStudentClass());

					// String gender =
					// dialog.getComboBoxGender().getSelectionModel().getSelectedItem().toString();
					String gender = dialog.getComboBoxGender().getEditor().getText();
					String yearOfRegime = dialog.getFieldYearOfRegime().getText();
					String post = dialog.getFieldPost().getText();

					clubNew.setIdNumber(idNumber);
					clubNew.setFirstName(firstName);
					clubNew.setMiddleName(middleName);
					clubNew.setLastName(lastName);
					clubNew.setStudentClass(studentClass);
					clubNew.setGender(gender);
					clubNew.setYearOfRegime(yearOfRegime);
					clubNew.setPost(post);

					ClubTableDAO dao = null;
					try {
						dao = new ClubTableDAO();
					} catch (SQLException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}

					try {
						dao.editClubMember(clubNew, tableName, id);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					dialog.close();
					refreshClubList();

				});

			}
			dialog.showAndWait();
		});

		/*
		 * delete a prefect post
		 */
		btnRemovePrefectPost.setOnAction(e -> {
			String post = listViewPrefects.getSelectionModel().getSelectedItem().toString();
			PrefectPostDAO dao = new PrefectPostDAO();

			try {
				dao.removePrefectPost(post);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			refreshPrefectPostList();

		});

		/*
		 * delete a prefect from the list (demote)
		 */

		btnRemovePrefect.setOnAction(e -> {
			Prefects prefects = tableViewPrefects.getSelectionModel().getSelectedItem();
			String idNumber = prefects.getIdNumber();

			PrefectPostDAO dao = new PrefectPostDAO();

			try {
				dao.removePrefect(idNumber);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			refreshPrefrectsTable();

		});

		/*
		 * remove UNSA post
		 */

		btnRemoveUNSAPost.setOnAction(e -> {

			String post = listViewUNSA.getSelectionModel().getSelectedItem().toString();
			

			try {
				UNSADAO dao = new UNSADAO();
				dao.removeUNSAPost(post);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			refreshUNSAPostList();

		});

		btnRemoveUNSA.setOnAction(e -> {
			UNSA unsa = tableViewUNSA.getSelectionModel().getSelectedItem();
			String idNumber = unsa.getIdNumber();

			

			try {
				UNSADAO unsadao = new UNSADAO();
				unsadao.removeUNSA(idNumber);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			refreshUNSATable();

		});

		btnRemoveClubAndSociety.setOnAction(e -> {
			

			Object tableName = listViewClubsAndSociety.getSelectionModel().getSelectedItem();

			String idString = labelID.getText();

			Integer id = new Integer(idString);

			try {
				ClubDAO clubDAO = new ClubDAO();
				clubDAO.removeClub(tableName, id);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			refreshClubList();

		});

		btnRemoveClub.setOnAction(e -> {
			

			Object tableName = listViewClubsAndSociety.getSelectionModel().getSelectedItem();

			Club club = tableViewClub.getSelectionModel().getSelectedItem();

			int id = club.getId();

			// String idString = labelID.getText();

			// Integer id = new Integer(idString);

			try {
				ClubTableDAO dao = new ClubTableDAO();
				dao.removeClubMember(tableName, id);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			refreshClubList();

		});

		btnAddNewClub.setOnAction(e -> {
			String tableName = listViewClubsAndSociety.getSelectionModel().getSelectedItem().toString();

			ClubDialog dialog = new ClubDialog();

			dialog.setTitle("Add New " + tableName + " member");

			dialog.getBtnSave().setOnAction(ev -> {

				

				String idNumber = dialog.getFieldIDNumber().getText();
				String firstName = dialog.getFieldFirstName().getText();
				String middleName = dialog.getFieldMiddleName().getText();
				String lastName = dialog.getFieldLastName().getText();
				String studentClass = dialog.getComboBoxStudentClass().getSelectionModel().getSelectedItem();
				String gender = dialog.getComboBoxGender().getSelectionModel().getSelectedItem();
				String yearOfRegime = dialog.getFieldYearOfRegime().getText();
				String post = dialog.getFieldPost().getText();

				Club club = null;
				club = new Club(idNumber, firstName, middleName, lastName, studentClass, gender, yearOfRegime, post);

				try {
					ClubTableDAO dao = new ClubTableDAO();
					dao.addNewClubMember(club, tableName);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				refreshClubList();
				dialog.close();

			});

			dialog.showAndWait();

		});

		titledPanePrefects.setOnMouseClicked(e -> {
			refreshPrefectPostList();
		});

		titledPaneUNSA.setOnMouseClicked(e -> {
			refreshUNSAPostList();
		});

		titledPaneClubsAndSociety.setOnMouseClicked(e -> {
			refreshClubList();
		});

		titledPanePrefects.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (titledPanePrefects.isExpanded()) {
				refreshPrefectPostList();
				borderPaneStudentLeaders.setCenter(borderPanePrefects);
				borderPanePrefects.setVisible(true);
				borderPaneUNSA.setVisible(false);
				borderPaneClub.setVisible(false);
			} else {
				borderPanePrefects.setVisible(false);
			}

		});

		titledPaneUNSA.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (titledPaneUNSA.isExpanded()) {
				borderPaneStudentLeaders.setCenter(borderPaneUNSA);
				borderPaneUNSA.setVisible(true);
				borderPanePrefects.setVisible(false);
				borderPaneClub.setVisible(false);
			} else {
				borderPaneUNSA.setVisible(false);
			}
		});

		titledPaneClubsAndSociety.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (titledPaneClubsAndSociety.isExpanded()) {
				borderPaneStudentLeaders.setCenter(borderPaneClub);
				borderPaneClub.setVisible(true);
				borderPanePrefects.setVisible(false);
				borderPaneUNSA.setVisible(false);
			} else {
				borderPaneClub.setVisible(false);
			}
		});

		titledPanePrefects.addEventHandler(KeyEvent.KEY_TYPED, e -> {
			if (titledPanePrefects.isExpanded()) {
				borderPaneStudentLeaders.setCenter(borderPanePrefects);
				borderPanePrefects.setVisible(true);
				borderPaneUNSA.setVisible(false);
				borderPaneClub.setVisible(false);
			} else {
				borderPanePrefects.setVisible(false);
			}
		});

		titledPaneUNSA.addEventHandler(KeyEvent.KEY_TYPED, e -> {
			if (titledPaneUNSA.isExpanded()) {
				borderPaneStudentLeaders.setCenter(borderPaneUNSA);
				borderPaneUNSA.setVisible(true);
				borderPanePrefects.setVisible(false);
				borderPaneClub.setVisible(false);
			} else {
				borderPaneUNSA.setVisible(false);
			}
		});

		titledPaneClubsAndSociety.addEventHandler(KeyEvent.KEY_TYPED, e -> {
			if (titledPaneClubsAndSociety.isExpanded()) {
				borderPaneStudentLeaders.setCenter(borderPaneClub);
				borderPaneClub.setVisible(true);
				borderPanePrefects.setVisible(false);
				borderPaneUNSA.setVisible(false);
			} else {
				borderPaneClub.setVisible(false);
			}
		});

		refreshClubList();

		listViewClubsAndSociety.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			ObservableList<Club> clubs = null;

			Object id = null;

			

			try {
				
				ClubDAO clubDAO = new ClubDAO();
				clubs = clubDAO.getAllClubMembers(newValue.toString());

				id = clubDAO.clubID(newValue.toString());

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			labelID.setText(id.toString());

			tableViewClub.itemsProperty().setValue(clubs);
			//

		});

		// listViewClubsAndSociety.addEventHandler(MouseEvent.MOUSE_CLICKED, e
		// ->{
		//
		//
		// });

		// tableViewClub.setStyle(
		// "-fx-background-color: green ;"
		//// "-fx-background-image: url(gold8.jpeg)"
		// );

		tableViewClub.setId("table_club");

		String stylesheet = getClass().getResource("attendance.css").toExternalForm();

		borderPaneStudentLeaders.setLeft(accordionStudentLeaders);

		sceneStudentLeaders = new Scene(borderPaneStudentLeaders);

		sceneStudentLeaders.getStylesheets().addAll(stylesheet);

		jfxPanelStudentLeaders.setScene(sceneStudentLeaders);

		this.add(jfxPanelStudentLeaders);

	}

	public void refreshPrefrectsTable() {
		
		ObservableList<Prefects> prefects = null;

		try {
			PrefectsDAO daoRefresh = new PrefectsDAO();
			prefects = daoRefresh.getAllPrefects();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// tableViewPrefects.setItems(prefects);
		tableViewPrefects.itemsProperty().setValue(prefects);

	}

	public void refreshUNSATable() {
		
		ObservableList<UNSA> prefects = null;

		try {
			UNSADAO daoRefresh = new UNSADAO();
			prefects = daoRefresh.getAllUNSA();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// tableViewPrefects.setItems(prefects);
		tableViewUNSA.itemsProperty().setValue(prefects);

	}

	public void refreshPrefectPostList() {
		PrefectPostDAO dao = new PrefectPostDAO();
		ObservableList<String> posts = null;

		try {
			posts = dao.getPrefectPosts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		listViewPrefects.itemsProperty().setValue(posts);

	}

	public void refreshUNSAPostList() {
		
		ObservableList<String> posts = null;

		try {
			UNSAPostDAO dao = new UNSAPostDAO();
			posts = dao.getUNSAPosts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		listViewUNSA.itemsProperty().setValue(posts);

	}

	public void refreshClubList() {
				ObservableList<Object> posts = null;

		try {
			ClubDAO dao = new ClubDAO();

			posts = dao.fillClubTree();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		listViewClubsAndSociety.itemsProperty().setValue(posts);

	}

	public TitledPane getTitledPanePrefects() {
		return titledPanePrefects;
	}

	public TitledPane getTitledPaneUNSA() {
		return titledPaneUNSA;
	}

	public TitledPane getTitledPaneClubsAndSociety() {
		return titledPaneClubsAndSociety;
	}

}
