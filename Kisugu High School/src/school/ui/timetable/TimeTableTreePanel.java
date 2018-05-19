package school.ui.timetable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TimeTableTreePanel extends JPanel {

	private JTree timeTableTree;
	private DefaultMutableTreeNode parent;
	private DefaultMutableTreeNode treeModelTimetableClassLessons;
	private DefaultMutableTreeNode treeModelTimetableClassExams;
	private DefaultMutableTreeNode treeModelTimetableDutyScheduleTeachers;
	private JScrollPane scrollPaneTimeTable;
	public ListView<String> lvListDisplayed;
	private ObservableList<String> itemsDisplayed;

	public TimeTableTreePanel() {
		buildGUI();
	}

	private void buildGUI() {

//		parent = new DefaultMutableTreeNode("Time Table");
//
//		timeTableTree = new JTree(parent);
//
//		timeTableTree.setBackground(Color.black);
//		timeTableTree.setForeground(Color.white);
//		timeTableTree.setFont(new Font("Times New Roman", Font.BOLD, 15));
//		timeTableTree.setCursor(new Cursor(Cursor.HAND_CURSOR));
//
//		scrollPaneTimeTable = new JScrollPane(timeTableTree);
//		// scrollPaneTimeTable.setPreferredSize(new Dimension(152, 540));
//
//		treeModelTimetableClassLessons = addNode("Lessons", parent);
//		treeModelTimetableClassExams = addNode("Examinations", parent);
//		treeModelTimetableDutyScheduleTeachers = addNode("Teachers Duty Schedule", parent);
		
		
		
		
		
		lvListDisplayed = new ListView<String>();
		itemsDisplayed = FXCollections.observableArrayList("Lessons","Examinations","Teachers Duty Schedule");
		lvListDisplayed.setItems(itemsDisplayed);
		lvListDisplayed.setMaxHeight(Control.USE_PREF_SIZE);
		lvListDisplayed.setMaxWidth(170);
		
		VBox vb=new VBox(2);
		vb.getChildren().add(lvListDisplayed);
		
		Scene scene=new Scene(vb);
		
		JFXPanel panel=new JFXPanel();
		panel.setScene(scene);
		
		add(panel);

	}

	public JTree getTimeTableTree() {
		return timeTableTree;
	}

	public JScrollPane getScrollPaneTimeTable() {
		return scrollPaneTimeTable;
	}

	private DefaultMutableTreeNode addNode(String nodeName, DefaultMutableTreeNode parentNodeName) {
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nodeName);
		parentNodeName.add(newNode);
		return newNode;
	}

	public ListView<String> getLvListDisplayed() {
		return lvListDisplayed;
	}

	public void setLvListDisplayed(ListView<String> lvListDisplayed) {
		this.lvListDisplayed = lvListDisplayed;
	}
	
	

}
