package school.ui.finances;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import school.ui.tests.UserMaster;

public class FinanceStockCard extends JPanel {

	public TableView tableView;
	private Scene scene;
	private JFXPanel jfxPanel;
	private ObservableList<Object> listOfItemsreceiveable;
	private ObservableList<Object> lisOfItemsIssuable;
	private Connection conn;
	private PreparedStatement pst;
	public ComboBox fieldItemReceived;
	public ComboBox fieldItemIssued;

	private static ObservableList<UserMaster> row;

	private static ObservableList<String> col;
	private static ResultSet rs;

	public FinanceStockCard() {
		setUpEntries();
	}

	private void setUpEntries() {

	Platform.runLater(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			TitledPane paneReceived = new TitledPane();

			GridPane GpaneReceived = new GridPane();
			GpaneReceived.setVgap(5);
			GpaneReceived.setHgap(5);
			GpaneReceived.setPadding(new Insets(10, 10, 10, 10));
			GpaneReceived.add(new Label("Date Received"), 0, 0);
			DatePicker dateReceived = new DatePicker();
			dateReceived.getValue();
			GpaneReceived.add(dateReceived, 1, 0);

			GpaneReceived.add(new Label("Quantity Received"), 2, 0);
			TextField fieldQtyReceived = new TextField();
			GpaneReceived.add(fieldQtyReceived, 3, 0);

			GpaneReceived.add(new Label("Item Received"), 0, 1);

			listOfItemsreceiveable = FXCollections.observableArrayList();

			fieldItemReceived = new ComboBox(listOfItemsreceiveable);
			fieldItemReceived.setPromptText("Choose Item");
			GpaneReceived.add(fieldItemReceived, 1, 1);

			GpaneReceived.add(new Label("Stock Card Number"), 2, 1);
			TextField fieldStockCardNumberReceived = new TextField();
			GpaneReceived.add(fieldStockCardNumberReceived, 3, 1);

			GpaneReceived.add(new Label("Delivery Note"), 0, 2);
			TextField fieldDeliveryNoteReceived = new TextField();
			GpaneReceived.add(fieldDeliveryNoteReceived, 1, 2);

			GpaneReceived.add(new Label("Received By"), 2, 2);
			TextField fieldReceiverReceived = new TextField();
			GpaneReceived.add(fieldReceiverReceived, 3, 2);

			GpaneReceived.add(new Label("Remarks"), 0, 3);
			TextField fieldRemarksReceived = new TextField();
			GpaneReceived.add(fieldRemarksReceived, 1, 3);

			Button btnEnterReceived = new Button("Enter");
			GpaneReceived.add(btnEnterReceived, 2, 3);

			Button btnClearReceived = new Button("Stock At Hand");
			btnClearReceived.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub

					FinanceStockAtHand atHand = new FinanceStockAtHand();
					displayData(atHand.tableSuspension,
							"select date, item_name,sum(quantity_received),"
							+ "sum(quantity_issued),sum(quantity_received)-"
							+ "sum(quantity_issued),remarks from stock_inventory group by item_name");
				}
			});
			GpaneReceived.add(btnClearReceived, 3, 3);
			paneReceived.setText("Received Stock");
			paneReceived.setPadding(new Insets(0, 0, 0, 0));
			paneReceived.setContent(GpaneReceived);

			TitledPane paneIssued = new TitledPane();

			GridPane GpaneIssued = new GridPane();
			GpaneIssued.setVgap(5);
			GpaneIssued.setHgap(5);
			GpaneIssued.setPadding(new Insets(10, 10, 10, 10));
			GpaneIssued.add(new Label("Date Issued"), 0, 0);
			DatePicker dateIssued = new DatePicker();
			dateIssued.getValue();
			GpaneIssued.add(dateIssued, 1, 0);

			GpaneIssued.add(new Label("Quantity Issued"), 2, 0);
			TextField fieldQtyIssued = new TextField();
			GpaneIssued.add(fieldQtyIssued, 3, 0);

			GpaneIssued.add(new Label("Item Issued"), 0, 1);

			lisOfItemsIssuable = FXCollections.observableArrayList();

			fieldItemIssued = new ComboBox(lisOfItemsIssuable);
			fieldItemIssued.setPromptText("Choose Item");
			GpaneIssued.add(fieldItemIssued, 1, 1);

			GpaneIssued.add(new Label("Stock Card Number"), 2, 1);
			TextField fieldStockCardNumberIssued = new TextField();
			GpaneIssued.add(fieldStockCardNumberIssued, 3, 1);

			GpaneIssued.add(new Label("Delivery Note"), 0, 2);
			TextField fieldDeliveryNoteIssued = new TextField();
			GpaneIssued.add(fieldDeliveryNoteIssued, 1, 2);

			GpaneIssued.add(new Label("Dept Issued To"), 2, 2);
			TextField fieldReceiverIssued = new TextField();
			GpaneIssued.add(fieldReceiverIssued, 3, 2);

			GpaneIssued.add(new Label("Remarks"), 0, 3);
			TextField fieldRemarksIssued = new TextField();
			GpaneIssued.add(fieldRemarksIssued, 1, 3);

			Button btnEnterIssued = new Button("Enter");
			GpaneIssued.add(btnEnterIssued, 2, 3);

			Button btnClearIssued = new Button("Clear All Fields");
			btnClearIssued.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					fieldDeliveryNoteIssued.setText(null);
					fieldReceiverIssued.setText(null);
					fieldQtyIssued.setText(null);
					fieldReceiverIssued.setText(null);
					fieldRemarksIssued.setText(null);
					fieldStockCardNumberIssued.setText(null);
					dateIssued.setValue(null);
					
					
					fieldDeliveryNoteReceived.setText(null);
					fieldReceiverReceived.setText(null);
					fieldQtyReceived.setText(null);
					fieldReceiverReceived.setText(null);
					fieldRemarksReceived.setText(null);
					fieldStockCardNumberReceived.setText(null);
					dateReceived.setValue(null);
				
				}
			});
			GpaneIssued.add(btnClearIssued, 3, 3);
			paneIssued.setText("Issued Stock");
			paneIssued.setPadding(new Insets(0, 0, 0, 0));
			paneIssued.setContent(GpaneIssued);

			HBox boxReceivedAndIssued = new HBox(5);
			boxReceivedAndIssued.getChildren().addAll(paneReceived, paneIssued);

			btnEnterReceived.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					AddUpdateDelete(
							"insert into stock_inventory(date,item_name,quantity_received,stock_card,delivery_note,received_by,remarks)"
									+ " values('" + (dateReceived.getValue()) + "','"
									+ fieldItemReceived.getSelectionModel().getSelectedItem() + "','"
									+ fieldQtyReceived.getText() + "'," + "'" + fieldStockCardNumberReceived.getText()
									+ "','" + fieldDeliveryNoteReceived.getText() + "','" + fieldReceiverReceived.getText()
									+ "'," + "'" + fieldRemarksReceived.getText() + "')");
					buildData(tableView);
				}
			});

			btnEnterIssued.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					AddUpdateDelete(
							"insert into stock_inventory(date,item_name,quantity_issued,stock_card,delivery_note,department_issued_to,remarks)"
									+ " values('" + (dateIssued.getValue()) + "','"
									+ fieldItemIssued.getSelectionModel().getSelectedItem() + "','"
									+ fieldQtyIssued.getText() + "'," + "'" + fieldStockCardNumberIssued.getText() + "','"
									+ fieldDeliveryNoteIssued.getText() + "','" + fieldReceiverIssued.getText() + "'," + "'"
									+ fieldRemarksIssued.getText() + "')");

					buildData(tableView);

				}
			});

			tableView = new TableView();

			String colHeading[] = { "Date", "Item Name", "Qty Recieved", "Delivery Note", "Stock At Hand", "Total",
					"Qty Issued", "Balance", "Dept Issued To", "Remarks" };

			col = FXCollections.observableArrayList(colHeading);

			row = FXCollections.observableArrayList();

			TableColumn<UserMaster, String> colName = new TableColumn<UserMaster, String>(col.get(0));

			colName.setMinWidth(140);

			colName.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("date"));

			TableColumn<UserMaster, String> colCourse = new TableColumn<UserMaster, String>(col.get(1));

			colCourse.setMinWidth(180);

			colCourse.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("item_name"));

			TableColumn<UserMaster, String> colEmail = new TableColumn<UserMaster, String>(col.get(2));

			colEmail.setMinWidth(140);

			colEmail.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("quantity_received"));

			TableColumn<UserMaster, String> colCity = new TableColumn<UserMaster, String>(col.get(3));

			colCity.setMinWidth(180);

			colCity.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("delivery_note"));

			TableColumn<UserMaster, String> colEmail1 = new TableColumn<UserMaster, String>(col.get(6));

			colEmail1.setMinWidth(140);

			colEmail1.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("quantity_issued"));

			TableColumn<UserMaster, String> colCity2 = new TableColumn<UserMaster, String>(col.get(8));

			colCity2.setMinWidth(180);

			colCity2.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("department"));

			TableColumn<UserMaster, String> colCity3 = new TableColumn<UserMaster, String>(col.get(9));

			colCity3.setMinWidth(150);

			colCity3.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("remarks"));

			tableView.getColumns().addAll(colName, colCourse, colEmail, colCity, colEmail1, colCity2, colCity3);

			buildData(tableView);

			GridPane paneHome = new GridPane();
			paneHome.setAlignment(Pos.CENTER);
			paneHome.setVgap(5);
			paneHome.setHgap(5);
			paneHome.setPadding(new Insets(10, 0, 10, 0));
			paneHome.add(boxReceivedAndIssued, 0, 0);
			paneHome.add(tableView, 0, 1);
			paneHome.setMinHeight(480);
			paneHome.setMinWidth(1160);
			paneHome.setAlignment(Pos.CENTER);

			scene = new Scene(paneHome);
			jfxPanel = new JFXPanel();
			jfxPanel.setScene(scene);
			jfxPanel.setPreferredSize(new Dimension(1160, 480));

			String styleSheet = getClass().getResource("generalledger.css").toExternalForm();
			scene.getStylesheets().add(styleSheet);

			displayInComboBox(fieldItemIssued, "select item_name from stores_items");
			displayInComboBox(fieldItemReceived, "select item_name from stores_items");

			add(jfxPanel);	
		}
	});

	}

	public static void buildData(TableView<UserMaster> tableview) {

		Connection c;

		String SQL = "SELECT date,item_name,quantity_received,delivery_note,"
				+ "quantity_issued,department_issued_to,remarks from stock_inventory";

		try {

			c = CashBookController.getConnection();

			rs = c.createStatement().executeQuery(SQL);

			for (int rows = 0; rows < row.size(); rows++) {
				row.clear();
			}

			while (rs.next()) {

				UserMaster cm = new UserMaster();

				cm.date.set(rs.getDate(1));
				cm.item_name.set(rs.getString(2));
				cm.quantity_received.set(rs.getString(3));
				cm.delivery_note.set(rs.getString(4));
				cm.quantity_issued.set(rs.getString(5));
				cm.department.set(rs.getString(6));
				cm.remarks.set(rs.getString(7));

				row.add(cm);
			}
			tableview.setItems(row);

		} catch (SQLException sqex) {

			sqex.printStackTrace();

			System.out.println("Error On Building Data for Stock Level: " + sqex.getMessage());

		}

	}

	public void AddUpdateDelete(String querries) {

		try {

			java.sql.Connection conn = null;
			java.sql.PreparedStatement pst = null;
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(querries);

			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Request Executed Successfully");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Request Not Executed Successfully " + ex.getMessage());

		}
	}

	public Date convertFromUtilToSQLDate(java.util.Date dateUtil) {

		if (dateUtil != null) {
			java.sql.Date sqlDate = new java.sql.Date(dateUtil.getTime());

			return sqlDate;
		} else {
			return null;
		}
	}

	public void displayInComboBox(ComboBox combo, String query) {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			listOfItemsreceiveable.clear();
			lisOfItemsIssuable.clear();
			while (rs.next()) {

				listOfItemsreceiveable.add(rs.getString(1));
				lisOfItemsIssuable.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {

				try {
					conn.close();
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public void displayData(JTable table, String query) {

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			while (table.getRowCount() > 0) {
				((DefaultTableModel) table.getModel()).removeRow(0);

			}
			int columns = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				Object[] row = new Object[columns];
				for (int i = 1; i <= columns; i++) {
					row[i - 1] = rs.getObject(i);
				}
				((DefaultTableModel) table.getModel()).insertRow(rs.getRow() - 1, row);
			}
			rs.close();
			pst.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
