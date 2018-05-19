package school.ui.finances;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.toedter.calendar.JDateChooser;

import colgroup.ColumnGroup;
import colgroup.GroupableTableColumnModel;
import colgroup.GroupableTableHeader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class FinanceStoreLedger extends JDialog {

	private JDateChooser dateChooser;
	private JTextField fieldInvoiceNo;
	private JTextField fieldReceivedFrom;
	private JTextField fieldPriceReceipt;
	private JTextField fieldQtyReceipt;
	private JTextField fieldValueReceipt;

	private JLabel labelDate;
	private JLabel labelInvoiceNo;
	private JLabel labelReceivedFrom;
	private JLabel labelPriceReceipt;
	private JLabel labelQtyReceipt;
	private JLabel labelValueReceipt;

	private JButton btnEnterReceipt;

	private JPanel panelReceipts;

	private JTextField fieldIssuesTo;
	private JTextField fieldPriceIssues;
	private JTextField fieldQtyIssues;
	private JTextField fieldValueIssues;

	private JLabel labelIssuesTo;
	private JLabel labelPriceIssues;
	private JLabel labelQtyIssues;
	private JLabel labelValueIssues;

	private JPanel panelIssues;

	private JTextArea areaRemarks;
	private JLabel labelRemarks;

	private JTable tableCashBook;
	private JScrollPane scrollPaneCashBook;

	private JButton btnSave;
	private JButton btnPrint;
	private JButton btnExport;
	private JButton btnEnterIssues;
	private JPanel panelAddItems;
	private JLabel labelStoreItem;
	private JTextField fieldStoreItem;
	private JButton btnAddItem;
	private JLabel labelItemName;
	private JComboBox fieldItemName;
	private JLabel labelItemNameIssues;
	private JComboBox fieldItemNameIssues;
	private Connection conn;
	private PreparedStatement pst;
	private JLabel labelDateIssues;
	private JDateChooser dateChooserIssues;
	private JLabel labelInvoiceNoReceipt;
	private JTextField fieldInvoiceNoReceipt;
	protected JFileChooser fileChooser;
	protected ComboBox fieldItemReceived;
	protected ObservableList<Object> lisOfItemsIssuable;
	protected ComboBox fieldItemIssued;
	protected JFXPanel jfxPanel;
	private ObservableList<Object> listOfItemsreceiveable;

	public FinanceStoreLedger() {
		// TODO Auto-generated constructor stub
		setUpStoreLedger();
	}

	private void setUpStoreLedger() {

		setTitle("Store Ledger");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		setBackground(Color.decode("#5f9ea0"));
		setSize(screensize.width - 20, screensize.height - 145);
		setLocationRelativeTo(null);

		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		
		
		/***************************************************
		 * 
		 * 
		 * FX GUI
		 * 
		 * 
		 ***************************************************/
		
		Platform.runLater(new Runnable() {
			
			

			@Override
			public void run() {
				//
				
				TitledPane paneReceived = new TitledPane();

				GridPane GpaneReceived = new GridPane();
				GpaneReceived.setVgap(5);
				GpaneReceived.setHgap(5);
				GpaneReceived.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
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

				GpaneReceived.add(new Label("Price"), 2, 1);
				TextField fieldStockCardNumberReceived = new TextField();
				GpaneReceived.add(fieldStockCardNumberReceived, 3, 1);

				GpaneReceived.add(new Label("Received From"), 0, 2);
				TextField fieldDeliveryNoteReceived = new TextField();
				GpaneReceived.add(fieldDeliveryNoteReceived, 1, 2);

				GpaneReceived.add(new Label("Invoice Number"), 2, 2);
				TextField fieldReceiverReceived = new TextField();
				GpaneReceived.add(fieldReceiverReceived, 3, 2);

				GpaneReceived.add(new Label("Value"), 0, 3);
				TextField fieldRemarksReceived = new TextField();
				GpaneReceived.add(fieldRemarksReceived, 1, 3);

				Button btnEnterReceived = new Button("Save Received Item");
				GpaneReceived.add(btnEnterReceived, 2, 3);
				btnEnterReceived.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						
						AddItem("insert into store_ledger(date,invoice_no,item_name,received_from,price_rf,quantity_rf,item_value_rf)"
								+ " values('" +dateReceived.getValue() + "','"
								+ fieldReceiverReceived.getText() + "'," + "'" + fieldItemReceived.getSelectionModel().getSelectedItem() + "','"
								+ fieldDeliveryNoteReceived.getText() + "'," + "'" + fieldStockCardNumberReceived.getText() + "','"
								+ fieldQtyReceived.getText() + "'," + "'" + fieldRemarksReceived.getText() + "')");
						
						displayStoreLedger(tableCashBook,
								"select date,item_name,received_from,price_rf,quantity_rf,item_value_rf,"
								+ "issued_to,price_it,quantity_it,item_value_it,(quantity_rf)-(quantity_it),"
								+ "(item_value_rf)-(item_value_it) from store_ledger");
					}
				});

				Button btnClearReceived = new Button("Stock At Hand");
				btnClearReceived.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub

						FinanceStockAtHandStores atHand = new FinanceStockAtHandStores();
						displayData(atHand.tableSuspension,
								"select date, item_name,sum(quantity_rf),"
								+ "sum(quantity_it),sum(quantity_rf)-"
								+ "sum(quantity_it),sum(item_value_rf)-sum(item_value_it) from store_ledger group by item_name");
						
						
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
			

				GpaneIssued.add(new Label("Price"), 2, 1);
				TextField fieldStockCardNumberIssued = new TextField();
				GpaneIssued.add(fieldStockCardNumberIssued, 3, 1);

				GpaneIssued.add(new Label("Invoice No:"), 0, 2);
				TextField fieldDeliveryNoteIssued = new TextField();
				GpaneIssued.add(fieldDeliveryNoteIssued, 1, 2);

				GpaneIssued.add(new Label("Dept Issued To"), 2, 2);
				TextField fieldReceiverIssued = new TextField();
				GpaneIssued.add(fieldReceiverIssued, 3, 2);

				GpaneIssued.add(new Label("Value"), 0, 3);
				TextField fieldRemarksIssued = new TextField();
				GpaneIssued.add(fieldRemarksIssued, 1, 3);

				Button btnEnterIssued = new Button("Save issued Item");
				GpaneIssued.add(btnEnterIssued, 2, 3);
				btnEnterIssued.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						AddItem("insert into store_ledger(date,invoice_no,item_name,issued_to,price_it,quantity_it,item_value_it)"
								+ " values('" +dateIssued.getValue() + "','"
								+ fieldDeliveryNoteIssued.getText() + "'," + "'" + fieldItemIssued.getSelectionModel().getSelectedItem() + "','"
								+ fieldReceiverIssued.getText() + "'," + "'" + fieldStockCardNumberIssued.getText() + "','"
								+ fieldQtyIssued.getText() + "'," + "'" + fieldRemarksIssued.getText() + "')");
						
						displayStoreLedger(tableCashBook,
								"select date,item_name,received_from,price_rf,quantity_rf,item_value_rf,"
								+ "issued_to,price_it,quantity_it,item_value_it,(quantity_rf)-(quantity_it),"
								+ "(item_value_rf)-(item_value_it) from store_ledger");
					}
				});

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
				paneIssued.setPadding(new javafx.geometry.Insets(0, 0, 0, 0));
				paneIssued.setContent(GpaneIssued);
				
				
				
				TitledPane paneStores = new TitledPane();

				GridPane GpaneStores = new GridPane();
				GpaneStores.setVgap(5);
				GpaneStores.setHgap(5);
				GpaneStores.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
				GpaneStores.add(new Label("Date Recorded"), 0, 0);
				DatePicker dateStores = new DatePicker();
				dateStores.getValue();
				GpaneStores.add(dateStores, 1, 0);

				GpaneStores.add(new Label("Item Name "), 0, 1);
				TextField fieldQtyStores = new TextField();
				GpaneStores.add(fieldQtyStores, 1, 1);
				
				Button btnSave=new Button("Save Item");
				btnSave.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						AddUpdateDelete("insert into stores_items(item_name,date) values('"+fieldQtyStores.getText()+"','"+dateStores.getValue()+"')");
						displayInComboBox(fieldItemReceived, "select item_name from stores_items");
						displayInComboBox(fieldItemIssued, "select item_name from stores_items");
						
					}
				});
				GpaneStores.add(btnSave, 1, 2);
				
				Button btnExport=new Button("Export To Excel");
				btnExport.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Specify name and folder to export this deatils");

						int selected = fileChooser.showSaveDialog(FinanceStoreLedger.this);

						
						if (selected == JFileChooser.APPROVE_OPTION) {
							try {

								fillData(tableCashBook, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
							} catch (Exception e1) {
								e1.printStackTrace();
							}

						}
					}
				});
				GpaneStores.add(btnExport, 0, 3);
				btnExport.setPrefWidth(150);
				
				Button btnPrint=new Button("Print To PDF");
				btnPrint.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Specify name and folder to export this deatils");

						int selected = fileChooser.showSaveDialog(FinanceStoreLedger.this);

						
						if (selected == JFileChooser.APPROVE_OPTION) {
							try {

								fillData(tableCashBook, new java.io.File(fileChooser.getSelectedFile() + ".pdf"));
							} catch (Exception e1) {
								e1.printStackTrace();
							}

						}
					}
				});
				GpaneStores.add(btnPrint, 1, 3);
				
				
				
				paneStores.setText("Add Stores Item");
				paneStores.setContent(GpaneStores);
				
				

				HBox boxReceivedAndIssued = new HBox(5);
				boxReceivedAndIssued.getChildren().addAll(paneReceived,paneStores, paneIssued);
				Scene scene=new Scene(boxReceivedAndIssued);
				
				String styleSheet = getClass().getResource("attendance.css").toExternalForm();
				scene.getStylesheets().add(styleSheet);
				
				jfxPanel=new JFXPanel();
				jfxPanel.setScene(scene);
				
				
				jfxPanel.setPreferredSize(new Dimension(screensize.width - 40, screensize.height - 600));
				add(jfxPanel);
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				displayInComboBox(fieldItemReceived, "select item_name from stores_items");
				displayInComboBox(fieldItemIssued, "select item_name from stores_items");
				
				
				
				
				
				
				
				
				
				
			///// fields and labels

				panelReceipts = new JPanel();
				panelReceipts.setBackground(Color.decode("#5f9ea0"));
//				add(panelReceipts);
				panelReceipts.setLayout(new FlowLayout(FlowLayout.CENTER));
				panelReceipts.setBorder(new TitledBorder("Receipts"));
				panelReceipts.setPreferredSize(new Dimension(500, 250));

				panelAddItems = new JPanel();
				panelAddItems.setBackground(Color.decode("#5f9ea0"));
//				add(panelAddItems);
				panelAddItems.setLayout(new FlowLayout(FlowLayout.CENTER));
				panelAddItems.setBorder(new TitledBorder("Add Stores Items"));
				panelAddItems.setPreferredSize(new Dimension(220, 250));

				panelIssues = new JPanel();
				panelIssues.setBackground(Color.decode("#5f9ea0"));
//				add(panelIssues);
				panelIssues.setLayout(new FlowLayout(FlowLayout.CENTER));
				panelIssues.setBorder(new TitledBorder("Issues"));
				panelIssues.setPreferredSize(new Dimension(500, 250));

				// Dimension of labels and fields

				Dimension dimLabels = new Dimension(150, 20);
				Dimension dimFields = new Dimension(250, 20);

				labelStoreItem = new JLabel("Stores Item:");
				labelStoreItem.setPreferredSize(dimLabels);
				panelAddItems.add(labelStoreItem);
				fieldStoreItem = new JTextField();
				fieldStoreItem.setPreferredSize(new Dimension(150, 25));
				panelAddItems.add(fieldStoreItem);

				btnAddItem = new JButton("Add Item");
				btnAddItem.setPreferredSize(new Dimension(100, 30));
				btnAddItem.addActionListener(new ActionListener() {

					

					@Override
					public void actionPerformed(java.awt.event.ActionEvent arg0) {
						// TODO Auto-generated method stub
						AddItem("insert into stores_items(item_name) values('" + fieldStoreItem.getText() + "')");
						fieldItemName.removeAllItems();
						fieldItemNameIssues.removeAllItems();
						displayInComboBox(fieldItemName, "select item_name from stores_items");
						displayInComboBox(fieldItemNameIssues, "select item_name from stores_items");

					}
				});
				panelAddItems.add(btnAddItem);

				labelDate = new JLabel("Date:");
				labelDate.setPreferredSize(dimLabels);
				panelReceipts.add(labelDate);
				dateChooser = new JDateChooser();
				dateChooser.getDate();
				dateChooser.setPreferredSize(dimFields);
				panelReceipts.add(dateChooser);

				labelInvoiceNoReceipt = new JLabel("Invoice No.:");
				labelInvoiceNoReceipt.setPreferredSize(dimLabels);
				panelReceipts.add(labelInvoiceNoReceipt);
				fieldInvoiceNoReceipt = new JTextField();
				fieldInvoiceNoReceipt.setPreferredSize(dimFields);
				panelReceipts.add(fieldInvoiceNoReceipt);

				labelItemName = new JLabel("Item Name:");
				labelItemName.setPreferredSize(dimLabels);
				panelReceipts.add(labelItemName);
				fieldItemName = new JComboBox();
				fieldItemName.setPreferredSize(dimFields);
				panelReceipts.add(fieldItemName);

				labelReceivedFrom = new JLabel("Received From");
				labelReceivedFrom.setPreferredSize(dimLabels);
				panelReceipts.add(labelReceivedFrom);
				fieldReceivedFrom = new JTextField();
				fieldReceivedFrom.setPreferredSize(dimFields);
				panelReceipts.add(fieldReceivedFrom);

				labelPriceReceipt = new JLabel("Price:");
				labelPriceReceipt.setPreferredSize(dimLabels);
				panelReceipts.add(labelPriceReceipt);
				fieldPriceReceipt = new JTextField();
				fieldPriceReceipt.setPreferredSize(dimFields);
				panelReceipts.add(fieldPriceReceipt);

				labelQtyReceipt = new JLabel("Quantity:");
				labelQtyReceipt.setPreferredSize(dimLabels);
				panelReceipts.add(labelQtyReceipt);
				fieldQtyReceipt = new JTextField();
				fieldQtyReceipt.setPreferredSize(dimFields);
				panelReceipts.add(fieldQtyReceipt);

				labelValueReceipt = new JLabel("Value:");
				labelValueReceipt.setPreferredSize(dimLabels);
				panelReceipts.add(labelValueReceipt);
				fieldValueReceipt = new JTextField();
				fieldValueReceipt.setPreferredSize(dimFields);
				panelReceipts.add(fieldValueReceipt);

				btnEnterReceipt = new JButton("Enter");
				btnEnterReceipt.setPreferredSize(new Dimension(100, 20));
				btnEnterReceipt.addActionListener(new ActionListener() {

				

					@Override
					public void actionPerformed(java.awt.event.ActionEvent arg0) {
						// TODO Auto-generated method stub


						AddItem("insert into store_ledger(date,invoice_no,item_name,received_from,price_rf,quantity_rf,item_value_rf)"
								+ " values('" + convertFromUtilToSQLDate(dateChooser.getDate()) + "','"
								+ fieldInvoiceNoReceipt.getText() + "'," + "'" + fieldItemName.getSelectedItem() + "','"
								+ fieldReceivedFrom.getText() + "'," + "'" + fieldPriceReceipt.getText() + "','"
								+ fieldQtyReceipt.getText() + "'," + "'" + fieldValueReceipt.getText() + "')");
						
						displayStoreLedger(tableCashBook,
								"select date,item_name,received_from,price_rf,quantity_rf,item_value_rf,"
								+ "issued_to,price_it,quantity_it,item_value_it,SUM(quantity_rf)-SUM(quantity_it),"
								+ "SUM(item_value_rf)-SUM(item_value_it) from store_ledger group by item_name");
						
					}
				});
				panelReceipts.add(btnEnterReceipt);

				////// ISSUES
				labelDateIssues = new JLabel("Date:");
				labelDateIssues.setPreferredSize(dimLabels);
				panelIssues.add(labelDateIssues);
				dateChooserIssues = new JDateChooser();
				dateChooserIssues.getDate();
				dateChooserIssues.setPreferredSize(dimFields);
				panelIssues.add(dateChooserIssues);

				labelItemNameIssues = new JLabel("Item Name:");
				labelItemNameIssues.setPreferredSize(dimLabels);
				panelIssues.add(labelItemNameIssues);
				fieldItemNameIssues = new JComboBox();
				fieldItemNameIssues.setPreferredSize(dimFields);
				panelIssues.add(fieldItemNameIssues);

				labelInvoiceNo = new JLabel("Invoice No.:");
				labelInvoiceNo.setPreferredSize(dimLabels);
				panelIssues.add(labelInvoiceNo);
				fieldInvoiceNo = new JTextField();
				fieldInvoiceNo.setPreferredSize(dimFields);
				panelIssues.add(fieldInvoiceNo);

				labelIssuesTo = new JLabel("Issued To");
				labelIssuesTo.setPreferredSize(dimLabels);
				panelIssues.add(labelIssuesTo);
				fieldIssuesTo = new JTextField();
				fieldIssuesTo.setPreferredSize(dimFields);
				panelIssues.add(fieldIssuesTo);

				labelPriceIssues = new JLabel("Price:");
				labelPriceIssues.setPreferredSize(dimLabels);
				panelIssues.add(labelPriceIssues);
				fieldPriceIssues = new JTextField();
				fieldPriceIssues.setPreferredSize(dimFields);
				panelIssues.add(fieldPriceIssues);

				labelQtyIssues = new JLabel("Quantity:");
				labelQtyIssues.setPreferredSize(dimLabels);
				panelIssues.add(labelQtyIssues);
				fieldQtyIssues = new JTextField();
				fieldQtyIssues.setPreferredSize(dimFields);
				panelIssues.add(fieldQtyIssues);

				labelValueIssues = new JLabel("Value:");
				labelValueIssues.setPreferredSize(dimLabels);
				panelIssues.add(labelValueIssues);
				fieldValueIssues = new JTextField();
				fieldValueIssues.setPreferredSize(dimFields);
				panelIssues.add(fieldValueIssues);

				btnEnterIssues = new JButton("Enter");
				btnEnterIssues.setPreferredSize(new Dimension(100, 20));
				btnEnterIssues.addActionListener(new ActionListener() {

					

					@Override
					public void actionPerformed(java.awt.event.ActionEvent arg0) {
						// TODO Auto-generated method stub
						AddItem("insert into store_ledger(date,invoice_no,item_name,issued_to,price_it,quantity_it,item_value_it)"
								+ " values('" + convertFromUtilToSQLDate(dateChooser.getDate()) + "','"
								+ fieldInvoiceNo.getText() + "'," + "'" + fieldItemNameIssues.getSelectedItem() + "','"
								+ fieldIssuesTo.getText() + "'," + "'" + fieldPriceIssues.getText() + "','"
								+ fieldQtyIssues.getText() + "'," + "'" + fieldValueIssues.getText() + "')");
						
						displayStoreLedger(tableCashBook,
								"select date,item_name,received_from,price_rf,quantity_rf,item_value_rf,"
								+ "issued_to,price_it,quantity_it,item_value_it,SUM(quantity_rf)-SUM(quantity_it),"
								+ "SUM(item_value_rf)-SUM(item_value_it) from store_ledger group by item_name");
					}
				});
				panelIssues.add(btnEnterIssues);

				DefaultTableModel model = new DefaultTableModel();

				String[] headerCashBook = { "Date", "Item Name", "Received From", "Price", "Quantity", "Value", "Issued To",
						"Price", "Quantity", "Value", "Quantity", "Value", "Remarks" };

				Object[][] dataCashBook = { { null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null } };

				model.setDataVector(dataCashBook, headerCashBook);

				tableCashBook = new JTable();
				tableCashBook.setShowGrid(false);
				tableCashBook.setRowHeight(30);
				tableCashBook.setBackground(Color.decode("#5f9ea0"));
				// tab

				///// column grouping////
				tableCashBook.setColumnModel(new GroupableTableColumnModel());
				tableCashBook
						.setTableHeader(new GroupableTableHeader((GroupableTableColumnModel) tableCashBook.getColumnModel()));
				tableCashBook.setModel(model);
				////////////////////////

				GroupableTableColumnModel cm = (GroupableTableColumnModel) tableCashBook.getColumnModel();
				ColumnGroup groupReceipts = new ColumnGroup("RECEIPTS");
				groupReceipts.add(cm.getColumn(2));
				groupReceipts.add(cm.getColumn(3));
				groupReceipts.add(cm.getColumn(4));
				groupReceipts.add(cm.getColumn(5));

				ColumnGroup groupIssues = new ColumnGroup("ISSUES");
				groupIssues.add(cm.getColumn(6));
				groupIssues.add(cm.getColumn(7));
				groupIssues.add(cm.getColumn(8));
				groupIssues.add(cm.getColumn(9));

				//// balance
				ColumnGroup groupBalance = new ColumnGroup("BALANCE");
				groupBalance.add(cm.getColumn(10));
				groupBalance.add(cm.getColumn(11));

				GroupableTableHeader header = (GroupableTableHeader) tableCashBook.getTableHeader();
				cm.addColumnGroup(groupReceipts);
				cm.addColumnGroup(groupIssues);
				cm.addColumnGroup(groupBalance);
				header.setBackground(Color.black);
				header.setForeground(Color.white);

				// header.getColumnModel().getcolumn

				// v.setCellRenderer(new ColorColumnRenderer(Color.green,
				// Color.magenta));

				// set Date column width
				// tableCashBook.getColumnModel().getColumn(0).setPreferredWidth(5);

				// set Folio column width
				tableCashBook.getColumnModel().getColumn(3).setMaxWidth(200);
				tableCashBook.getColumnModel().getColumn(3).setPreferredWidth(120);

				// using folio as basis

				// set Date column width
				tableCashBook.getColumnModel().getColumn(0).setMaxWidth(250);
				tableCashBook.getColumnModel().getColumn(0).setPreferredWidth(220);

				// set Receipt Number width
				tableCashBook.getColumnModel().getColumn(1).setMaxWidth(280);
				tableCashBook.getColumnModel().getColumn(1).setPreferredWidth(250);

				// set Details width
				tableCashBook.getColumnModel().getColumn(2).setMaxWidth(300);
				tableCashBook.getColumnModel().getColumn(2).setPreferredWidth(200);

				// set Bank Dr width
				tableCashBook.getColumnModel().getColumn(5).setMaxWidth(200);
				tableCashBook.getColumnModel().getColumn(5).setPreferredWidth(120);

				// set Cash Dr width
				tableCashBook.getColumnModel().getColumn(4).setMaxWidth(200);
				tableCashBook.getColumnModel().getColumn(4).setPreferredWidth(120);

				// set Cash Cr width
				tableCashBook.getColumnModel().getColumn(6).setMaxWidth(300);
				tableCashBook.getColumnModel().getColumn(6).setPreferredWidth(200);

				tableCashBook.getColumnModel().getColumn(7).setMaxWidth(200);
				tableCashBook.getColumnModel().getColumn(7).setPreferredWidth(120);

				tableCashBook.getColumnModel().getColumn(8).setMaxWidth(200);
				tableCashBook.getColumnModel().getColumn(8).setPreferredWidth(120);

				tableCashBook.getColumnModel().getColumn(9).setMaxWidth(200);
				tableCashBook.getColumnModel().getColumn(9).setPreferredWidth(120);

				tableCashBook.getColumnModel().getColumn(10).setMaxWidth(200);
				tableCashBook.getColumnModel().getColumn(10).setPreferredWidth(120);

				tableCashBook.getColumnModel().getColumn(11).setMaxWidth(200);
				tableCashBook.getColumnModel().getColumn(11).setPreferredWidth(120);

				tableCashBook.getColumnModel().getColumn(12).setMaxWidth(250);
				tableCashBook.getColumnModel().getColumn(12).setPreferredWidth(200);

				tableCashBook.setFont(new Font("Times New Roman", Font.BOLD, 14));

				////////////////////////////

				scrollPaneCashBook = new JScrollPane(tableCashBook);

				scrollPaneCashBook.setPreferredSize(new Dimension(screensize.width - 40, screensize.height - 365));

				scrollPaneCashBook.setBorder(new LineBorder(Color.white, 3));
				add(scrollPaneCashBook);
				setBackground(Color.decode("#5f9ea0"));

				setVisible(true);

				displayInComboBox(fieldItemName, "select item_name from stores_items");
				displayInComboBox(fieldItemNameIssues, "select item_name from stores_items");
				displayStoreLedger(tableCashBook,
						"select date,item_name,received_from,price_rf,quantity_rf,item_value_rf,"
						+ "issued_to,price_it,quantity_it,item_value_it,(quantity_rf)-(quantity_it),"
						+ "(item_value_rf)-(item_value_it) from store_ledger");
			}
		});

			
		
		
		

		
	}

	public void displayInComboBox(JComboBox combo, String query) {
		
		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			combo.removeAll();

			while (rs.next()) {
				combo.addItem(rs.getString(1));
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

	public void AddItem(String querries) {

	

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

	public void displayStoreLedger(JTable table, String query) {

		try {

			conn = CashBookController.getConnection();
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
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
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	


	public void fillData(JTable table, java.io.File file) {

		try {


			XSSFWorkbook workbook1 = new XSSFWorkbook();
			
			XSSFSheet   fSheet = workbook1.createSheet("Data Sheet");

			TableModel model = table.getModel();

			CellStyle style = workbook1.createCellStyle();
			CellStyle stylebody = workbook1.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			XSSFFont font = workbook1.createFont();
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);
			style.setFont(font);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setBorderBottom(CellStyle.BORDER_THIN);
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setBorderRight(CellStyle.BORDER_THIN);
			style.setBorderTop(CellStyle.BORDER_THIN);
			
			
			stylebody.setBorderBottom(CellStyle.BORDER_THIN);
			stylebody.setBorderLeft(CellStyle.BORDER_THIN);
			stylebody.setBorderRight(CellStyle.BORDER_THIN);
			stylebody.setBorderTop(CellStyle.BORDER_THIN);

		        TableColumnModel model1 = table.getTableHeader().getColumnModel();
		        
		        XSSFRow fRow1 = fSheet.createRow((short) 0);
			for (int i = 0; i < model.getColumnCount(); i++) {
				
				XSSFCell cell1 = fRow1.createCell((short) i);
				cell1.setCellValue(model1.getColumn(i).getHeaderValue().toString());
			    cell1.setCellStyle(style);
			}
			int j = 0;
			for (int i = 0; i < model.getRowCount(); i++) {
				XSSFRow fRow = fSheet.createRow((short) i+1);
				for (j = 0; j < model.getColumnCount(); j++) {
					  XSSFCell cell2 = fRow.createCell((short) j);
				    cell2.setCellValue(model.getValueAt(i, j).toString());
				    cell2.setCellStyle(stylebody);
				    fSheet.autoSizeColumn(j);
				}
			}
			fSheet.setDisplayGridlines(false);
			FileOutputStream fos =new FileOutputStream(new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
			workbook1.write(fos);
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
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
}
