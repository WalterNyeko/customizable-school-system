package school.ui.tests;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DisplayDatabase extends Application{  
	TableView tableview;
   @Override  

   public void start(Stage stage) throws Exception {  

     stage.setFullScreen(false); 
     
     Button btnMore=new Button("More");
     btnMore.setOnAction(new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			TableViewDemo.buildData(tableview);
		}
	});

       

     //TableView  

     tableview = new TableView();  

     TableViewDemo.buildData(tableview);  

     //Adding GridPane  

     GridPane gridPane = new GridPane();  

     gridPane.setPadding(new Insets(20,20,20,20));  

     gridPane.setHgap(5);  

     gridPane.setVgap(5);  

     //Main Scene 
     
     VBox box=new VBox(5);
     box.getChildren().addAll(btnMore,tableview);

     Scene scene = new Scene(box);      

     stage.setScene(scene);  

     stage.show();  

   }  

   public static void main(String args[]){  

     launch(args);  

   }
 

 }
