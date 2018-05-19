package school.ui.tests;

 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import school.ui.finances.CashBookController;

public class Main extends Application {
	
	private ListView<String> lvList;
	private ObservableList<String> items;

	
	 private TreeView tree;//declaration of the treeView
	  HashMap<Integer, composant> node = new HashMap<>(); //for child nodes
	  HashMap<Integer, composant> pere = new HashMap<>(); //for parent nodes
	  composant c; //object from component class
	
	
	 Map<Integer, TreeItem<String>> itemById = new HashMap<>();
     Map<Integer, Integer> parents = new HashMap<>();

	private PreparedStatement ps;
	private Connection cnx;
	private ResultSet rs;

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("HBox Test");

    // HBox
    HBox hb = new HBox();
    hb.setPadding(new Insets(15, 12, 15, 12));
    hb.setSpacing(10);

    // Buttons
    Button btn1 = new Button();
    btn1.setText("Button1");
//    hb.getChildren().add(btn1);

    Button btn2 = new Button();
    btn2.setText("Button2");
//    hb.getChildren().add(btn2);

    Button btn3 = new Button();
    btn3.setText("Button3");
//    hb.getChildren().add(btn3);

    Button btn4 = new Button();
    btn4.setText("Button4");
    
    lvList=new ListView();
    
//    tree=new TreeView<>();
    fillTree();
    
    hb.getChildren().addAll(btn1,btn2,btn3,btn4,lvList);

    // Adding HBox to the scene
    Scene scene = new Scene(hb);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  
 
  private void fillTree() {
      String query = "SELECT * FROM banks";
      try {
    	  cnx=CashBookController.getConnection();
          ps = cnx.prepareStatement(query);
     
         rs = ps.executeQuery();

         
          while (rs.next()) {
              int code = rs.getInt("id");
              String composant = rs.getString("bank_name");
              int parent = rs.getInt("id");
//              String niveau = rs.getString("niveau");
//              int id = rs.getInt("id");

//              itemById.put(code, new TreeItem<>(composant));
//              parents.put(code, parent);
              
              lvList.getItems().add(composant);
          }
          ps.close();
          rs.close();

          TreeItem<String> root = null;
          for (Map.Entry<Integer, TreeItem<String>> entry : itemById.entrySet()) {
              Integer key = entry.getKey();
              Integer parent = parents.get(key);
              if (parent.equals(key)) {
                  // in case the root item points to itself, this is it
                  root = entry.getValue();
              } else {
                  TreeItem<String> parentItem = itemById.get(parent);
                  if (parentItem == null) {
                      // in case the root item has no parent in the resultset, this is it
                      root = entry.getValue();
                  } else {
                      // add to parent treeitem
                      parentItem.getChildren().add(entry.getValue());
                  }
              }
          }
          
//          tree.setRoot(root);
      }catch(Exception e) {
    	  e.printStackTrace();
      }
      
  }
}

   