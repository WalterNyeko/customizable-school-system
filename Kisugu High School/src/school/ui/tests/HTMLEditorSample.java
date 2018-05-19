package school.ui.tests;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
 
public class HTMLEditorSample extends Application {
 
    @Override
    public void start(Stage stage) {
        stage.setTitle("Send SMS Notifications");
        stage.setWidth(600);
        stage.setHeight(300);   
        final HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setPrefHeight(245);
        
        TextField fieldNumbers=new TextField();
        fieldNumbers.setPrefSize(200, 25);
        
        GridPane pane=new GridPane();
        pane.add(htmlEditor, 0, 0);
        pane.add(fieldNumbers, 0, 1);
        
        Scene scene = new Scene(pane);       
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}
