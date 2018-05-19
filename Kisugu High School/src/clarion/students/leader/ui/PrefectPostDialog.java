package clarion.students.leader.ui;

import java.sql.SQLException;

import clarion.student.leaders.core.PrefectPost;
import clarion.students.leader.dao.PrefectPostDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrefectPostDialog extends Stage {

	private Scene scenePrefectPost;
	private VBox vBoxPrefectPost;
	private Label labelPrefectPost;
	private TextField fieldPrefectPost;
	private Button btnSave;
	private Button btnCancel;
	private GridPane gridPanePrefectPost;

	public PrefectPostDialog() {
		this.setTitle("New Prefect Post");
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);

		vBoxPrefectPost = new VBox();

		gridPanePrefectPost = new GridPane();
		gridPanePrefectPost.setVgap(10);
		gridPanePrefectPost.setHgap(10);
		gridPanePrefectPost.setPadding(new Insets(10));

		labelPrefectPost = new Label("Post:");
		fieldPrefectPost = new TextField();

		btnSave = new Button("Save");

		btnCancel = new Button("Cancel");

		gridPanePrefectPost.add(labelPrefectPost, 0, 0);
		gridPanePrefectPost.add(fieldPrefectPost, 1, 0);
		gridPanePrefectPost.add(btnSave, 0, 1);
		gridPanePrefectPost.add(btnCancel, 1, 1);

		vBoxPrefectPost.getChildren().add(gridPanePrefectPost);

		btnSave.setOnAction(e -> {
			String post = fieldPrefectPost.getText();

			PrefectPostDAO dao = new PrefectPostDAO();

			PrefectPost prefectPost = new PrefectPost(post);

			try {
				dao.addPrefectPost(prefectPost);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// studentLeadersList.refreshPrefectPostList();

			this.close();

			// StudentLeadersList list = new StudentLeadersList();

			// list.getTitledPanePrefects().setExpanded(true);

		});

		btnCancel.setOnAction(e -> {
			this.close();
		});

		scenePrefectPost = new Scene(vBoxPrefectPost);

		this.setScene(scenePrefectPost);

		// this.showAndWait();

	}

	public Button getBtnSave() {
		return btnSave;
	}

	public TextField getFieldPrefectPost() {
		return fieldPrefectPost;
	}

}
