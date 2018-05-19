package clarion.students.leader.ui;

import java.sql.SQLException;

import clarion.students.leader.dao.UNSAPostDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UNSADialog extends Stage {

	private Scene sceneUNSA;
	private VBox vBoxUNSA;

	private Label labelIDNumber;
	private Label labelFirstName;
	private Label labelMiddleName;
	private Label labelLastName;
	private Label labelStudentClass;
	private Label labelgender;
	private Label labelYearOfRegime;
	private Label labelPost;

	private TextField fieldIDNumber;
	private TextField fieldFirstName;
	private TextField fieldMiddleName;
	private TextField fieldLastName;
	private ComboBox<String> comboBoxStudentClass;
	private ComboBox<String> comboBoxGender;
	private TextField fieldYearOfRegime;
	private ComboBox<String> comboBoxPost;

	private TextField fieldUNSA;
	private Button btnSave;
	private Button btnCancel;
	private GridPane gridPaneUNSA;

	public UNSADialog() {
		this.setTitle("New UNSA");
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);

		vBoxUNSA = new VBox();

		gridPaneUNSA = new GridPane();
		gridPaneUNSA.setVgap(10);
		gridPaneUNSA.setHgap(10);
		gridPaneUNSA.setPadding(new Insets(10));

		labelIDNumber = new Label("ID Number:");
		labelFirstName = new Label("First Name:");
		labelMiddleName = new Label("Middle Name:");
		labelLastName = new Label("Last Name:");
		labelStudentClass = new Label("Class:");
		labelgender = new Label("Gender:");
		labelYearOfRegime = new Label("Year:");
		labelPost = new Label("Post:");

		fieldIDNumber = new TextField();
		fieldFirstName = new TextField();
		fieldMiddleName = new TextField();
		fieldLastName = new TextField();

		ObservableList<String> classesList = FXCollections.observableArrayList(new String("S.1"), new String("S.2"),
				new String("S.3"), new String("S.4"), new String("S.5"), new String("S.6"));

		comboBoxStudentClass = new ComboBox<>();
		comboBoxStudentClass.setPromptText("Choose Class");

		comboBoxStudentClass.itemsProperty().setValue(classesList);

		comboBoxGender = new ComboBox<>();

		ObservableList<String> genderList = FXCollections.observableArrayList(new String("Male"), new String("Female"));

		comboBoxGender.setPromptText("Choose Gender");

		comboBoxGender.itemsProperty().setValue(genderList);

		fieldYearOfRegime = new TextField();

		comboBoxPost = new ComboBox<>();

		comboBoxPost.setPromptText("Choose Post");

		refreshUNSAPostListInCombo();

		btnSave = new Button("Save");

		btnCancel = new Button("Cancel");

		gridPaneUNSA.add(labelIDNumber, 0, 0);
		gridPaneUNSA.add(fieldIDNumber, 1, 0);

		gridPaneUNSA.add(labelFirstName, 0, 1);
		gridPaneUNSA.add(fieldFirstName, 1, 1);

		gridPaneUNSA.add(labelMiddleName, 0, 2);
		gridPaneUNSA.add(fieldMiddleName, 1, 2);

		gridPaneUNSA.add(labelLastName, 0, 3);
		gridPaneUNSA.add(fieldLastName, 1, 3);

		gridPaneUNSA.add(labelStudentClass, 0, 4);
		gridPaneUNSA.add(comboBoxStudentClass, 1, 4);

		gridPaneUNSA.add(labelgender, 0, 5);
		gridPaneUNSA.add(comboBoxGender, 1, 5);

		gridPaneUNSA.add(labelYearOfRegime, 0, 6);
		gridPaneUNSA.add(fieldYearOfRegime, 1, 6);

		gridPaneUNSA.add(labelPost, 0, 7);
		gridPaneUNSA.add(comboBoxPost, 1, 7);

		gridPaneUNSA.add(btnSave, 0, 8);
		gridPaneUNSA.add(btnCancel, 1, 8);

		vBoxUNSA.getChildren().add(gridPaneUNSA);

		btnCancel.setOnAction(e -> {
			this.close();
		});

		sceneUNSA = new Scene(vBoxUNSA);

		this.setScene(sceneUNSA);

		// this.showAndWait();

	}

	public void refreshUNSAPostListInCombo() {
		
		ObservableList<String> posts = null;

		try {
			UNSAPostDAO dao = new UNSAPostDAO();
			posts = dao.getUNSAPosts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		comboBoxPost.itemsProperty().setValue(posts);

	}

	public Scene getSceneUNSA() {
		return sceneUNSA;
	}

	public VBox getvBoxUNSA() {
		return vBoxUNSA;
	}

	public Label getLabelIDNumber() {
		return labelIDNumber;
	}

	public Label getLabelFirstName() {
		return labelFirstName;
	}

	public Label getLabelMiddleName() {
		return labelMiddleName;
	}

	public Label getLabelLastName() {
		return labelLastName;
	}

	public Label getLabelStudentClass() {
		return labelStudentClass;
	}

	public Label getLabelgender() {
		return labelgender;
	}

	public Label getLabelYearOfRegime() {
		return labelYearOfRegime;
	}

	public Label getLabelPost() {
		return labelPost;
	}

	public TextField getFieldIDNumber() {
		return fieldIDNumber;
	}

	public TextField getFieldFirstName() {
		return fieldFirstName;
	}

	public TextField getFieldMiddleName() {
		return fieldMiddleName;
	}

	public TextField getFieldLastName() {
		return fieldLastName;
	}

	public ComboBox<String> getComboBoxStudentClass() {
		return comboBoxStudentClass;
	}

	public ComboBox<String> getComboBoxGender() {
		return comboBoxGender;
	}

	public TextField getFieldYearOfRegime() {
		return fieldYearOfRegime;
	}

	public ComboBox<String> getComboBoxPost() {
		return comboBoxPost;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public GridPane getGridPaneUNSA() {
		return gridPaneUNSA;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public TextField getFieldUNSA() {
		return fieldUNSA;
	}

}
