package school.ui.tests;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
 
public class TitledPaneSample extends Application {

    final Label label = new Label("N/A");
       
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override public void start(Stage stage) {
        stage.setTitle("TitledPane");
        Scene scene = new Scene(new Group(), 860, 450);
        scene.setFill(Color.GHOSTWHITE);
        
        // --- GridPane container
        TitledPane gridTitlePane = new TitledPane();
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("To: "), 0, 0);
        grid.add(new TextField(), 1, 0);
        grid.add(new Label("Cc: "), 0, 1);
        grid.add(new TextField(), 1, 1);
        grid.add(new Label("Subject: "), 0, 2);
        grid.add(new TextField(), 1, 2);        
        grid.add(new Label("Body: "), 0, 3);
        grid.add(new Label("Choose Class: "), 0, 4);
        grid.add(new Button("Send"), 2, 4);
        
        final HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setPrefHeight(245);
        htmlEditor.setPrefWidth(700);
        grid.add(htmlEditor,1, 3);
        gridTitlePane.setText("Send Email Alerts ");
        gridTitlePane.setContent(grid);
        
        ComboBox combo=new ComboBox();
        combo.setPrefWidth(250);
        grid.add(combo, 1, 4);
        
        
        
        TitledPane gridTitlePaneSMS = new TitledPane();
        GridPane gridSMS = new GridPane();
        gridSMS.setVgap(4);
        gridSMS.setPadding(new Insets(5, 5, 5, 5));
        gridSMS.add(new Label("To: "), 0, 0);
        gridSMS.add(new TextField(), 1, 0);
        gridSMS.add(new Label("Body: "), 0, 1);
        gridSMS.add(new Label("Choose Class: "), 0, 2);
        gridSMS.add(new Button("Send"), 2, 2);
        
        final HTMLEditor htmlEditorSMS = new HTMLEditor();
        htmlEditorSMS.setPrefHeight(245);
        htmlEditorSMS.setPrefWidth(700);
        gridSMS.add(htmlEditorSMS,1, 1);
        gridTitlePaneSMS.setText("Send SMS Alerts ");
        gridTitlePaneSMS.setContent(gridSMS);
        
        ComboBox comboSMS=new ComboBox();
        comboSMS.setPrefWidth(250);
        gridSMS.add(comboSMS, 1, 2);
        
        
        VBox hbox = new VBox(10);
        hbox.setPadding(new Insets(20, 0, 0, 20));
        hbox.getChildren().setAll(gridTitlePane,gridTitlePaneSMS);
 
        Group root = (Group)scene.getRoot();
        root.getChildren().add(hbox);
        stage.setScene(scene);
        stage.show();
    }
}