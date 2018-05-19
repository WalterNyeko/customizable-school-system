package school.ui.student;
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

public class StudentsTreePanel extends JPanel {

	private JTree StudentsTree;
	private DefaultMutableTreeNode treeNodeStudents, teachingNode, nonTeachingNode;
	
	private JScrollPane scrollPaneStudents;
	private ListView<String> lvListDisplayedStudents;
	private ObservableList<String> itemsDisplayed;
	
	public StudentsTreePanel() {
		
		lvListDisplayedStudents = new ListView<String>();
		itemsDisplayed = FXCollections.observableArrayList("Reporting Overview","All Students","Senior One","Senior Two","Senior Three","Senior Four","Senior Five","Senior Six","Student Leaders");
//		itemsDisplayed = FXCollections.observableArrayList("All Students","Teaching Students","Non Teaching Students");
		lvListDisplayedStudents.setItems(itemsDisplayed);
		lvListDisplayedStudents.setMaxWidth(150);
		lvListDisplayedStudents.setPrefHeight(540);
		
		VBox vb=new VBox(2);
		vb.getChildren().add(lvListDisplayedStudents);
		
		Scene scene=new Scene(vb);
		
		JFXPanel panel=new JFXPanel();
		panel.setScene(scene);
		
		Platform.setImplicitExit(false);
		add(panel);
		
	}

	
	
	public JScrollPane getScrollPaneStudents() {
		return scrollPaneStudents;
	}

	public ListView<String> getLvListDisplayedStudents() {
		return lvListDisplayedStudents;
	}

	public void setLvListDisplayedStudents(ListView<String> lvListDisplayedStudents) {
		this.lvListDisplayedStudents = lvListDisplayedStudents;
	}

	

	private DefaultMutableTreeNode addNode(String newnode, DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(newnode);
		parent.add(newChild);
		return newChild;
	}
	
}
