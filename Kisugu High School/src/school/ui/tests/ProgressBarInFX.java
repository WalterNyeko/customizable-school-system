package school.ui.tests;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ProgressBarInFX  extends Application {

final Float[] values = new Float[] {-2f};
final Label [] labels = new Label[values.length];
final ProgressBar[] pbs = new ProgressBar[values.length];
final ProgressIndicator[] pins = new ProgressIndicator[values.length];
final HBox hbs [] = new HBox [values.length];
private Scene scene;

    @Override
    public void start(Stage stage) {
        Group root = new Group();
		scene = new Scene(root, 250, 55);
		stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle("Progress Controls");
 
 
        for (int i = 0; i < values.length; i++) {
            final Label label = labels[i] = new Label();
 
            final ProgressBar pb = pbs[i] = new ProgressBar();
            pb.setProgress(values[i]);
 
            final ProgressIndicator pin = pins[i] = new ProgressIndicator();
            pin.setProgress(values[i]);
            final HBox hb = hbs[i] = new HBox();
            hb.setSpacing(5);
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().addAll(label, pb, pin);
        }
 
        final VBox vb = new VBox();
        vb.setSpacing(1);
        vb.getChildren().addAll(hbs);
        scene.setRoot(vb);
        stage.setResizable(false);
        stage.setOpacity(0.8);
        stage.show();
        
        ////////////////One Time Action//////////////
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> stage.close()));
        timeline.play();
        
        //////////////Periodic Action//////////////
        
//        Timeline timeline1 = new Timeline(new KeyFrame(
//                Duration.millis(2500),
//                ae -> stage.close()));
//        timeline1.setCycleCount(Animation.INDEFINITE);
//        timeline1.play();
        
      
    }
    public static void main(String[] args) {
        launch(args);
        
	
    }
}