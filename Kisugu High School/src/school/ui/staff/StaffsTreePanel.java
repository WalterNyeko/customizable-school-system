package school.ui.staff;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class StaffsTreePanel extends JPanel {

	private JTree staffsTree;
	private DefaultMutableTreeNode treeNodeSTAFFS, teachingNode, nonTeachingNode;
	
	private JScrollPane scrollPaneStaffs;
	private ListView<String> lvListDisplayedAdmission;
	private ObservableList<String> itemsDisplayed;
	
	public StaffsTreePanel() {
		
		lvListDisplayedAdmission = new ListView<String>();
//		itemsDisplayed = FXCollections.observableArrayList("Reporting Overview","Senior One","Senior Two","Senior Three","Senior Four","Senior Five","Senior Six","Student Leaders");
		itemsDisplayed = FXCollections.observableArrayList("All Staffs","Teaching Staffs","Non Teaching Staffs");
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

	
	
	public JScrollPane getScrollPaneStaffs() {
		return scrollPaneStaffs;
	}

	public ListView<String> getLvListDisplayedAdmission() {
		return lvListDisplayedAdmission;
	}

	public void setLvListDisplayedAdmission(ListView<String> lvListDisplayedAdmission) {
		this.lvListDisplayedAdmission = lvListDisplayedAdmission;
	}

	

	private DefaultMutableTreeNode addNode(String newnode, DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(newnode);
		parent.add(newChild);
		return newChild;
	}
	
}
