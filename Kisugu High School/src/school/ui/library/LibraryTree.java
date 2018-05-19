package school.ui.library;
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
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class LibraryTree extends JPanel {

	private JTree libraryTree;
	private DefaultMutableTreeNode treeNodeLIBRARY, booksNode,issueBookNode, returnNode;
	
	private JScrollPane scrollPaneLibrary;
	private ListView<String> lvListDisplayedLib;
	private ObservableList<String> itemsDisplayed;
	
	public LibraryTree() {
//		treeNodeLIBRARY = new DefaultMutableTreeNode("LIBRARY");
//		libraryTree = new JTree(treeNodeLIBRARY);
//		
//		libraryTree.setFont(new Font("Times New Roman", Font.BOLD, 15));
//		
//		libraryTree.setBackground(Color.BLACK);
//		libraryTree.setForeground(Color.white);
//		
//		booksNode = addNode("Manage Books", treeNodeLIBRARY);
//		 issueBookNode = addNode("Issue Book", treeNodeLIBRARY);
//		 returnNode = addNode("Return Book", treeNodeLIBRARY);
//		
//		scrollPaneLibrary = new JScrollPane(libraryTree);
//		
//		
//		
//		
//		add(scrollPaneLibrary);
//		
		
		
		lvListDisplayedLib = new ListView<String>();
		itemsDisplayed = FXCollections.observableArrayList("Manage Books","Issue Book","Return Book");
		lvListDisplayedLib.setItems(itemsDisplayed);
//		lvListDisplayedLib.setMaxHeight(Control.USE_PREF_SIZE);
		lvListDisplayedLib.setMaxWidth(170);
		lvListDisplayedLib.setPrefHeight(540);
		
		VBox vb=new VBox(2);
		vb.getChildren().add(lvListDisplayedLib);
		
		Scene scene=new Scene(vb);
		
		JFXPanel panel=new JFXPanel();
		panel.setScene(scene);
		
		Platform.setImplicitExit(false);
		add(panel);
		
	}

	

	public ListView<String> getLvListDisplayedLib() {
		return lvListDisplayedLib;
	}



	public void setLvListDisplayedLib(ListView<String> lvListDisplayedLib) {
		this.lvListDisplayedLib = lvListDisplayedLib;
	}



	private DefaultMutableTreeNode addNode(String newnode, DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(newnode);
		parent.add(newChild);
		return newChild;
	}



	public JTree getLibraryTree() {
		return libraryTree;
	}



	public JScrollPane getScrollPaneLibrary() {
		return scrollPaneLibrary;
	}
	
}
