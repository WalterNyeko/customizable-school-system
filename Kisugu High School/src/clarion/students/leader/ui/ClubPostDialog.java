package clarion.students.leader.ui;

import java.sql.SQLException;

import clarion.student.leaders.core.ClubName;
import clarion.students.leader.dao.ClubDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClubPostDialog extends Stage {

	private Scene sceneClubPost;
	private VBox vBoxClubPost;
	private Label labelClubPost;
	private TextField fieldClubPost;
	private Button btnSave;
	private Button btnCancel;
	private GridPane gridPaneClubPost;

	public ClubPostDialog() {
		this.setTitle("New Club/Society");
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);

		vBoxClubPost = new VBox();

		gridPaneClubPost = new GridPane();
		gridPaneClubPost.setVgap(10);
		gridPaneClubPost.setHgap(10);
		gridPaneClubPost.setPadding(new Insets(10));

		labelClubPost = new Label("Club/Society Name:");
		fieldClubPost = new TextField();

		btnSave = new Button("Save");

		btnCancel = new Button("Cancel");

		gridPaneClubPost.add(labelClubPost, 0, 0);
		gridPaneClubPost.add(fieldClubPost, 1, 0);
		gridPaneClubPost.add(btnSave, 0, 1);
		gridPaneClubPost.add(btnCancel, 1, 1);

		vBoxClubPost.getChildren().add(gridPaneClubPost);

		btnSave.setOnAction(e -> {
			String name = fieldClubPost.getText();

			

			ClubName clubName = new ClubName(name);

			try {
				ClubDAO clubDAO = new ClubDAO();
				clubDAO.createClubTable(name, clubName);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			this.close();

		});

		btnCancel.setOnAction(e -> {
			this.close();
		});

		sceneClubPost = new Scene(vBoxClubPost);

		this.setScene(sceneClubPost);

		// this.showAndWait();

	}

	public Button getBtnSave() {
		return btnSave;
	}

	public TextField getFieldClubPost() {
		return fieldClubPost;
	}

}
