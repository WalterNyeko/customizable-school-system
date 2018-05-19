package school.ui.account;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Optional;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import school.ui.tests.UserMaster;

public class SearchSystem extends JDialog {    
    public static void main(String[] args) {
//        launch(args);
    	new SearchSystem();
    }

	private TextField fieldItemReceived;
	private JFXPanel jfxPanel;
	protected JTable tableadmitted;
	protected JPanel panelTable;
	protected VBox boxFinal;
	protected TableView tableView;
	protected ObservableList<Object> row;

    public SearchSystem() {
    	
    	setTitle("Seacrh The System");
    	setSize(550, 250);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	setLocationRelativeTo(null);
    	
    	Platform.runLater(new Runnable() {

			private ObservableList<String> col;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				TitledPane paneReceived = new TitledPane();

				GridPane GpaneReceived = new GridPane();
				GpaneReceived.setVgap(10);
				GpaneReceived.setHgap(10);
				GpaneReceived.setPadding(new Insets(10, 10, 10, 10));
				
				ComboBox btnEnterReceived = new ComboBox();
				btnEnterReceived.setPrefWidth(250);
				GpaneReceived.add(btnEnterReceived, 0, 0);

				TextField btnClearReceived = new TextField("Search:");
				btnClearReceived.setPrefWidth(200);
				GpaneReceived.add(btnClearReceived, 1, 0);
				
				
				paneReceived.setText("Search Everything");
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
				
				
				
				
				TitledPane paneTable = new TitledPane();

				GridPane GpaneTable = new GridPane();
				GpaneTable.setVgap(10);
				GpaneTable.setHgap(10);
				GpaneTable.setPadding(new Insets(10, 10, 10, 10));
				
				
				
				paneTable.setText("Search Results");
				paneTable.setPadding(new Insets(10, 10, 10, 10));
				

				tableView = new TableView();

				String colHeading[] = { "Date", "Item Name", "Qty Recieved" };

				col = FXCollections.observableArrayList(colHeading);

				row = FXCollections.observableArrayList();

				TableColumn<UserMaster, String> colName = new TableColumn<UserMaster, String>(col.get(0));

				colName.setMinWidth(100);

				colName.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("date"));

				TableColumn<UserMaster, String> colCourse = new TableColumn<UserMaster, String>(col.get(1));

				colCourse.setMinWidth(150);

				colCourse.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("item_name"));

				TableColumn<UserMaster, String> colEmail = new TableColumn<UserMaster, String>(col.get(2));

				colEmail.setMinWidth(80);

				colEmail.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("quantity_received"));


				tableView.getColumns().addAll(colName, colCourse, colEmail);
				
				paneTable.setContent(tableView);
				
				
				
		        VBox box = new VBox();
		        box.getChildren().addAll(paneReceived,paneTable);
		        final Scene scene = new Scene(box);
		        jfxPanel=new JFXPanel();
		        jfxPanel.setPreferredSize(new Dimension(540, 240));
		        jfxPanel.setScene(scene);
		        add(jfxPanel);
		        
			}
    		
    	});
    	
    	this.setVisible(true);
    }
    
    
}