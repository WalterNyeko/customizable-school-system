package school.ui.tests;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ManyScenes extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Hello World");

        Group root1 = new Group();
        Group root2 = new Group();
        Group root3 = new Group();

        final Scene scene1 = new Scene(root1, 300, 250);
        final Scene scene2 = new Scene(root2, 300, 250);
        final Scene scene3 = new Scene(root3, 300, 250);


        Button go1 = new Button();
        go1.setLayoutX(100);
        go1.setLayoutY(80);
        go1.setText("Go  to scene2");
        go1.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                primaryStage.setScene(scene2);
            }
        });
        root1.getChildren().addAll(new Label("Scene 1"), go1);



        Button go2 = new Button();
        go2.setLayoutX(100);
        go2.setLayoutY(80);
        go2.setText("Go to scene3");
        go2.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                primaryStage.setScene(scene3);
            }
        });

        root2.getChildren().addAll(new TextField(), go2);



        Button go3 = new Button();
        go3.setLayoutX(100);
        go3.setLayoutY(80);
        go3.setText("Back to scene1");
        go3.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                primaryStage.setScene(scene1);
            }
        });



        root3.getChildren().addAll(new TextArea(), go3);




        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}