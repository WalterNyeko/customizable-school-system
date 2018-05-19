package school.ui.main;

import javax.speech.AudioException;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class UsersManualFrame extends JDialog {

	private Scene scene;
	private JFXPanel jfxpanel;
	private static final String VOICENAME = "kevin16k";
	Voice voice;
	private Text2Voice text2Voice;

	public UsersManualFrame() {
		// TODO Auto-generated constructor stub
		setTitle("User Manual");
		setSize(650, 800);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				VBox boxTextArea = new VBox(5);
				TextArea area = new TextArea(
						"I am aged 24 years a graduate of Kyambogo University with a Bachelor’s Degree in Accounting and Finance. I am hardworking, intelligent, team player, assertive, fast learner, flexible and above all I can work under pressure."
								+ " I have developed multitasking skills, got exposure of how auditing is done practically in the Office of the Auditor General where I trained as an intern in the internal audit department and skills in the accounting field in the Accounts department where am currently volunteering. "
								+ "My course in accounting and finance, has also equipped me with knowledge and provided me with a solid base upon which I believe I can leverage such skills into the Internal Audit Assistant role. I have enclosed more information in my CV."
								+ "I will be grateful if my application is put into consideration and I look forward to hearing from you soon."
								+ "");
				area.setWrapText(true);
				area.setPrefHeight(700);
				area.setPrefWidth(600);

				GridPane pane = new GridPane();
				pane.setVgap(10);
				pane.setHgap(10);
				pane.setAlignment(Pos.CENTER);

				Button speak = new Button("Read For Me");
				Button stop = new Button("Stop Reading");
				Button continueReading = new Button("Continue reading");
				Button exit = new Button("Exit Guide");

				pane.add(speak, 0, 0);
				pane.add(stop, 1, 0);
				pane.add(continueReading, 2, 0);
				pane.add(exit, 3, 0);

				speak.setOnAction(new EventHandler<ActionEvent>() {

					

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						text2Voice=new Text2Voice(area.getText());
						try {
							text2Voice.doSpeak(area.getText());
							text2Voice.terminate();
						} catch (EngineException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (AudioException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

				stop.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						try {
							text2Voice.terminate();
						} catch (EngineException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (EngineStateError e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

				boxTextArea.getChildren().addAll(area, pane);

				scene = new Scene(boxTextArea);

				jfxpanel = new JFXPanel();

				jfxpanel.setScene(scene);
				add(jfxpanel);
				setVisible(true);
				
			}
		});
		
	}

}
