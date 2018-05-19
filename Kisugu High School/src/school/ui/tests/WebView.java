package school.ui.tests;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
public class WebView extends Application{

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		javafx.scene.web.WebView webview=new javafx.scene.web.WebView();
		Button btn1=new Button("Upload Student Marks");
		Button btn2=new Button("Generate Marks Sheet");
		
		VBox box=new VBox(5);
		box.getChildren().addAll(webview,btn1,btn2);
		
		WebEngine engine=webview.getEngine();
		btn1.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				engine.load("http://localhost/phpmyadmin/tbl_import.php?db=kisugu&table=students_marks&token=7962adf44c5551716cae97ac0f0844d5");
			}
		});
		
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				engine.executeScript("window.location=\"https://www.youtube.com/\";");
			}
		});
		engine.setJavaScriptEnabled(true);
		Scene scene=new Scene(box,900,450);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
