package school.ui.admission;

import javax.swing.JPanel;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class AdmissionListViewPanel extends JPanel{

	private ListView<String> lvListDisplayedAdmission;
	private ObservableList<String> itemsDisplayed;

	public AdmissionListViewPanel() {
		
		buildAdmission();
		
	}
	public void buildAdmission() {
		lvListDisplayedAdmission = new ListView<String>();
		itemsDisplayed = FXCollections.observableArrayList("Admit Student","Admitted Students","Reporting Students","Reported Students");
		lvListDisplayedAdmission.setItems(itemsDisplayed);
		lvListDisplayedAdmission.setMaxWidth(150);
		lvListDisplayedAdmission.setPrefHeight(540);
		
		VBox vb=new VBox(2);
		vb.getChildren().add(lvListDisplayedAdmission);
		
		Scene scene=new Scene(vb);
		
		JFXPanel panel=new JFXPanel();
		panel.setScene(scene);
		
		Platform.setImplicitExit(false);
		add(panel);
	}

	public ListView<String> getLvListDisplayedAdmission() {
		return lvListDisplayedAdmission;
	}

	public void setLvListDisplayedAdmission(ListView<String> lvListDisplayedAdmission) {
		this.lvListDisplayedAdmission = lvListDisplayedAdmission;
	}
	
}
