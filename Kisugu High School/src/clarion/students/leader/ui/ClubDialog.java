package clarion.students.leader.ui;

import java.sql.SQLException;

import clarion.students.leader.dao.PrefectPostDAO;
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

public class ClubDialog extends Stage {

	private Scene scenePrefect;
	private VBox vBoxPrefect;

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
//	private ComboBox<Object> comboBoxPost;
	private TextField fieldPost;

	private TextField fieldPrefect;
	private Button btnSave;
	private Button btnCancel;
	private GridPane gridPanePrefect;

	public ClubDialog() {
		this.setTitle("New Club Member");
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);

		vBoxPrefect = new VBox();

		gridPanePrefect = new GridPane();
		gridPanePrefect.setVgap(10);
		gridPanePrefect.setHgap(10);
		gridPanePrefect.setPadding(new Insets(10));

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
		fieldPost = new TextField();

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

		

		

//		refreshPrefectPostListInCombo();

		btnSave = new Button("Save");

		btnCancel = new Button("Cancel");

		gridPanePrefect.add(labelIDNumber, 0, 0);
		gridPanePrefect.add(fieldIDNumber, 1, 0);

		gridPanePrefect.add(labelFirstName, 0, 1);
		gridPanePrefect.add(fieldFirstName, 1, 1);

		gridPanePrefect.add(labelMiddleName, 0, 2);
		gridPanePrefect.add(fieldMiddleName, 1, 2);

		gridPanePrefect.add(labelLastName, 0, 3);
		gridPanePrefect.add(fieldLastName, 1, 3);

		gridPanePrefect.add(labelStudentClass, 0, 4);
		gridPanePrefect.add(comboBoxStudentClass, 1, 4);

		gridPanePrefect.add(labelgender, 0, 5);
		gridPanePrefect.add(comboBoxGender, 1, 5);

		gridPanePrefect.add(labelYearOfRegime, 0, 6);
		gridPanePrefect.add(fieldYearOfRegime, 1, 6);

		gridPanePrefect.add(labelPost, 0, 7);
		gridPanePrefect.add(fieldPost, 1, 7);

		gridPanePrefect.add(btnSave, 0, 8);
		gridPanePrefect.add(btnCancel, 1, 8);

		vBoxPrefect.getChildren().add(gridPanePrefect);

		btnCancel.setOnAction(e -> {
			this.close();
		});

		scenePrefect = new Scene(vBoxPrefect);

		this.setScene(scenePrefect);

		// this.showAndWait();

	}

//	public void refreshPrefectPostListInCombo() {
//		PrefectPostDAO dao = new PrefectPostDAO();
//		ObservableList<Object> posts = null;
//
//		try {
//			posts = dao.getPrefectPosts();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		comboBoxPost.itemsProperty().setValue(posts);
//
//	}

	public TextField getFieldPost() {
		return fieldPost;
	}

	public Scene getScenePrefect() {
		return scenePrefect;
	}

	public VBox getvBoxPrefect() {
		return vBoxPrefect;
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

//	public ComboBox<?> getComboBoxPost() {
//		return comboBoxPost;
//	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public GridPane getGridPanePrefect() {
		return gridPanePrefect;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public TextField getFieldPrefect() {
		return fieldPrefect;
	}

}
