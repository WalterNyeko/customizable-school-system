package clarion.students.leader.ui;

import java.sql.SQLException;

import clarion.student.leaders.core.UNSAPost;
import clarion.students.leader.dao.UNSAPostDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UNSAPostDialog extends Stage {

	private Scene sceneUNSAPost;
	private VBox vBoxUNSAPost;
	private Label labelUNSAPost;
	private TextField fieldUNSAPost;
	private Button btnSave;
	private Button btnCancel;
	private GridPane gridPaneUNSAPost;

	public UNSAPostDialog() {
		this.setTitle("New UNSA Post");
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);

		vBoxUNSAPost = new VBox();

		gridPaneUNSAPost = new GridPane();
		gridPaneUNSAPost.setVgap(10);
		gridPaneUNSAPost.setHgap(10);
		gridPaneUNSAPost.setPadding(new Insets(10));

		labelUNSAPost = new Label("Post:");
		fieldUNSAPost = new TextField();

		btnSave = new Button("Save");

		btnCancel = new Button("Cancel");

		gridPaneUNSAPost.add(labelUNSAPost, 0, 0);
		gridPaneUNSAPost.add(fieldUNSAPost, 1, 0);
		gridPaneUNSAPost.add(btnSave, 0, 1);
		gridPaneUNSAPost.add(btnCancel, 1, 1);

		vBoxUNSAPost.getChildren().add(gridPaneUNSAPost);

		btnSave.setOnAction(e -> {
			String post = fieldUNSAPost.getText();

			

			UNSAPost UNSAPost = new UNSAPost(post);

			try {
				UNSAPostDAO dao = new UNSAPostDAO();
				dao.addUNSAPost(UNSAPost);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// studentLeadersList.refreshUNSAPostList();

			this.close();

			// StudentLeadersList list = new StudentLeadersList();

			// list.getTitledPaneUNSAs().setExpanded(true);

		});

		btnCancel.setOnAction(e -> {
			this.close();
		});

		sceneUNSAPost = new Scene(vBoxUNSAPost);

		this.setScene(sceneUNSAPost);

		// this.showAndWait();

	}

	public Button getBtnSave() {
		return btnSave;
	}

	public TextField getFieldUNSAPost() {
		return fieldUNSAPost;
	}

}
