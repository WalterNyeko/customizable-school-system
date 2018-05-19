package school.ui.tests;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CircleConstructor extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("");
		Group root = new Group();
		Scene scene = new Scene(root, 300, 250, Color.WHITE);

		Group g = new Group();

		DropShadow ds1 = new DropShadow();
		ds1.setOffsetY(4.0);

		Circle c = new Circle();
		c.setEffect(ds1);
		c.setCenterX(50.0);
		c.setCenterY(25.0);
		c.setRadius(10.0);
		c.setFill(Color.RED);
		c.setCache(true);
		
		Circle wLAN = new Circle();
		wLAN.setEffect(ds1);
		wLAN.setCenterX(50.0);
		wLAN.setCenterY(95.0);
		wLAN.setRadius(10.0);
		wLAN.setFill(Color.RED);
		wLAN.setCache(true);
		
		Circle internet = new Circle();
		internet.setEffect(ds1);
		internet.setCenterX(50.0);
		internet.setCenterY(55.0);
		internet.setRadius(10.0);
		internet.setFill(Color.RED);
		internet.setCache(true);

		g.getChildren().addAll(c,wLAN,internet);

		root.getChildren().add(g);
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), evt -> c.setVisible(false)),
				new KeyFrame(Duration.seconds(2), evt -> c.setVisible(true)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}