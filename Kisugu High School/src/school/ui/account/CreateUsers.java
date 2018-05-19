package school.ui.account;
import java.awt.Dimension;
import java.util.Optional;

import javax.swing.JDialog;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class CreateUsers extends JDialog {    
    public static void main(String[] args) {
//        launch(args);
    	new CreateUsers();
    }

	private TextField fieldItemReceived;
	private JFXPanel jfxPanel;

    public CreateUsers() {
    	
    	setTitle("Create Users");
    	setSize(750, 250);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	setLocationRelativeTo(null);
    	
    	Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				TitledPane paneReceived = new TitledPane();

				GridPane GpaneReceived = new GridPane();
				GpaneReceived.setVgap(10);
				GpaneReceived.setHgap(10);
				GpaneReceived.setPadding(new Insets(10, 10, 10, 10));
				GpaneReceived.add(new Label("Date of Creation"), 0, 0);
				DatePicker dateReceived = new DatePicker();
				dateReceived.getValue();
				GpaneReceived.add(dateReceived, 1, 0);

				GpaneReceived.add(new Label("First Name"), 2, 0);
				
				TextField fieldQtyReceived = new TextField();
				fieldQtyReceived.setPromptText("E.g Walter");
				GpaneReceived.add(fieldQtyReceived, 3, 0);

				GpaneReceived.add(new Label("Last Name"), 0, 1);


				fieldItemReceived = new TextField();
				fieldItemReceived.setPromptText("E.g Nyeko");
				GpaneReceived.add(fieldItemReceived, 1, 1);

				Label labelOtherNames=new Label("Other Names");
//				labelOtherNames.setPrefWidth(200);
				GpaneReceived.add(labelOtherNames, 2, 1);
				TextField fieldStockCardNumberReceived = new TextField();
				fieldStockCardNumberReceived.setPromptText("E.g P'Adong");
				GpaneReceived.add(fieldStockCardNumberReceived, 3, 1);

				GpaneReceived.add(new Label("User Name"), 0, 2);
				TextField fieldDeliveryNoteReceived = new TextField();
				fieldDeliveryNoteReceived.setPromptText("E.g Walter Nyeko");
				GpaneReceived.add(fieldDeliveryNoteReceived, 1, 2);

				GpaneReceived.add(new Label("Password"), 2, 2);
				PasswordField fieldReceiverReceived = new PasswordField();
				fieldReceiverReceived.setPromptText("E.g walter!2!%25");
				GpaneReceived.add(fieldReceiverReceived, 3, 2);

				GpaneReceived.add(new Label("Re-type Password"), 0, 3);
				PasswordField fieldRemarksReceived = new PasswordField();
				fieldRemarksReceived.setPromptText("Repeat the password");
				GpaneReceived.add(fieldRemarksReceived, 1, 3);

				Button btnEnterReceived = new Button("Create Account");
				btnEnterReceived.setPrefWidth(150);
				GpaneReceived.add(btnEnterReceived, 2, 3);

				Button btnClearReceived = new Button("Cancel");
				btnClearReceived.setPrefWidth(200);
				GpaneReceived.add(btnClearReceived, 3, 3);
				paneReceived.setText("Create Application Users Accounts");
				paneReceived.setPadding(new Insets(10, 10, 10, 10));
				paneReceived.setContent(GpaneReceived);
				
				btnClearReceived.setOnAction(event -> {
		            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		            alert.initStyle(StageStyle.TRANSPARENT);
		            alert.initModality(Modality.WINDOW_MODAL);
		            alert.setTitle("Are you sure to exit?");
		            Optional<ButtonType> result = alert.showAndWait();
		            if (result.get() == ButtonType.OK){
		                Platform.exit();
		                setVisible(false);
		            }
		        });

		        
		        VBox box = new VBox();
		        box.getChildren().add(paneReceived);
		        final Scene scene = new Scene(box);
		        jfxPanel=new JFXPanel();
		        jfxPanel.setPreferredSize(new Dimension(740, 240));
		        jfxPanel.setScene(scene);
		        add(jfxPanel);
		        
			}
    		
    	});
    	
    	this.setVisible(true);
    }
}