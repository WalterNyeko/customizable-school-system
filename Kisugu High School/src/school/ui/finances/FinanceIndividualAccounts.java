package school.ui.finances;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JPanel;

import clarion.finance.core.Accounts;
import clarion.finance.dao.AccountDAO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class FinanceIndividualAccounts extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFXPanel jfxPanel;
	private Scene scene;
	private BorderPane borderPane;

	private Label labelAccountName;
	private TextField fieldAccountName;

	private Label labelDetails;
	private TextField fieldDetails;
	private Button buttonEnter;

	private VBox vBoxSave;
	private VBox vBoxFilter;
	private GridPane gridPaneSave;
	private GridPane gridPaneFilter;

	// filter
	private Label labelAccountNameFilter;
	public ComboBox comboBoxAccountNameFilter;

	private Label labelDetailsFilter;
	private TextField comboBoxDetailsFilter;

	// table
	private static TableView<Accounts> tableViewAccounts;
	private TableColumn<Accounts, Date> columnDate;
	private TableColumn<Accounts, String> columnAccountName;
	private TableColumn<Accounts, String> columnVoucherNumber;
	private TableColumn<Accounts, String> columnDetails;
	private TableColumn<Accounts, String> columnFolio;

	private TableColumn columnAmount;
	private TableColumn<Accounts, String> columnAmountDr;
	private TableColumn<Accounts, String> columnAmountCr;
	private TableColumn<Accounts, String> columnBalane;

	private VBox vBoxTable;

	// export
	private Button buttonExport;
	private Button buttonPrint;

	private Separator separator;
	private HBox hBox;
	private VBox vBoxMain;
	private Connection conn;
	private PreparedStatement pst;
	private ObservableList<Object> listOfItemsreceiveable;
	private Button btnCheck;

	@SuppressWarnings("unchecked")
	public FinanceIndividualAccounts() {
		jfxPanel = new JFXPanel();
		borderPane = new BorderPane();

		hBox = new HBox();
		vBoxSave = new VBox();
		gridPaneSave = new GridPane();
		gridPaneSave.setVgap(10);
		gridPaneSave.setHgap(10);
		gridPaneSave.setPadding(new Insets(10));

		vBoxFilter = new VBox();
		gridPaneFilter = new GridPane();
		gridPaneFilter.setVgap(10);
		gridPaneFilter.setHgap(10);
		gridPaneFilter.setPadding(new Insets(10));
		gridPaneFilter.setAlignment(Pos.CENTER);

		labelAccountNameFilter = new Label("Bank Name:");
		listOfItemsreceiveable = FXCollections.observableArrayList();
		comboBoxAccountNameFilter = new ComboBox(listOfItemsreceiveable);
		displayInComboBox(comboBoxAccountNameFilter, "select bank_name from banks");
		comboBoxAccountNameFilter.setOnAction(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {

				displayAccountNumber(comboBoxDetailsFilter, "select account_number from banks where bank_name='"
						+ comboBoxAccountNameFilter.getSelectionModel().getSelectedItem() + "'");
				btnCheck.setText(
						"Check Bank Balance For " + comboBoxAccountNameFilter.getSelectionModel().getSelectedItem());
			}
		});
		comboBoxAccountNameFilter.setPromptText("Choose Bank");
		labelDetailsFilter = new Label("Account Number:");
		comboBoxDetailsFilter = new TextField();
		comboBoxDetailsFilter.setEditable(false);

		btnCheck = new Button("Check Bank Balance");
		btnCheck.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				FinanceBankBalanceDialog balanceDialog = new FinanceBankBalanceDialog();
				balanceDialog.setTitle("Bank Balance For "+comboBoxAccountNameFilter.getSelectionModel().getSelectedItem());
				balanceDialog.labelCashMM
						.setText("Balance in " + comboBoxAccountNameFilter.getSelectionModel().getSelectedItem() + ":");
				balanceDialog.showDailyOfTheDay(balanceDialog.labelCashMMValue, "select sum(debit)-sum(credit) from `"
						+ comboBoxAccountNameFilter.getSelectionModel().getSelectedItem() + "`");
				balanceDialog.showDailyOfTheDay(balanceDialog.labelCashExpensesValue,
						"select sum(debit)-sum(credit) from accounts_balanced_entries where account_name='Bank A/C'");
				balanceDialog.showDailyOfTheDayActual(balanceDialog.labelCashDDValue,
						"select sum(debit)-sum(credit) from accounts_balanced_entries where account_name='Bank A/C'",
						"select sum(debit)-sum(credit) from `"+ comboBoxAccountNameFilter.getSelectionModel().getSelectedItem() +"`");

			}
		});
		gridPaneFilter.add(labelAccountNameFilter, 0, 0);
		gridPaneFilter.add(comboBoxAccountNameFilter, 1, 0);

		gridPaneFilter.add(labelDetailsFilter, 2, 0);
		gridPaneFilter.add(comboBoxDetailsFilter, 3, 0);

		gridPaneFilter.add(btnCheck, 4, 0);

		vBoxFilter.getChildren().addAll(gridPaneFilter);
		vBoxFilter.setAlignment(Pos.CENTER);

		vBoxMain = new VBox(10);
		vBoxMain.setAlignment(Pos.CENTER);

		// table
		vBoxTable = new VBox();
		tableViewAccounts = new TableView<>();
		vBoxTable.setPrefWidth(1150);
		tableViewAccounts.setPrefSize(800, 460);
		// tableViewAccounts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn columnSNo = new TableColumn<>("#");
		columnSNo.setCellValueFactory(new Callback<CellDataFeatures<Accounts, Accounts>, ObservableValue<Accounts>>() {
			@Override
			public ObservableValue<Accounts> call(CellDataFeatures<Accounts, Accounts> p) {
				return new ReadOnlyObjectWrapper(p.getValue());
			}
		});

		columnSNo.setCellFactory(new Callback<TableColumn<Accounts, Accounts>, TableCell<Accounts, Accounts>>() {
			@Override
			public TableCell<Accounts, Accounts> call(TableColumn<Accounts, Accounts> param) {
				return new TableCell<Accounts, Accounts>() {
					@Override
					protected void updateItem(Accounts item, boolean empty) {
						super.updateItem(item, empty);

						if (this.getTableRow() != null && item != null) {
							setText(this.getTableRow().getIndex() + 1 + "");
						} else {
							setText("");
						}
					}
				};
			}
		});
		columnSNo.setSortable(false);
		columnSNo.setPrefWidth(40);
		columnSNo.setMaxWidth(50);

		columnDate = new TableColumn<>("Date");
		columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		columnDate.setMinWidth(150);

		columnAccountName = new TableColumn<>("Account Name");
		columnAccountName.setCellValueFactory(new PropertyValueFactory<>("accountName"));
		columnAccountName.setMinWidth(200);

		columnVoucherNumber = new TableColumn<>("Voucher Number");
		columnVoucherNumber.setCellValueFactory(new PropertyValueFactory<>("voucherNumber"));
		columnVoucherNumber.setMinWidth(150);

		columnDetails = new TableColumn<>("Details");
		columnDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
		columnDetails.setMinWidth(200);

		columnFolio = new TableColumn<>("Folio");
		columnFolio.setCellValueFactory(new PropertyValueFactory<>("folio"));
		columnFolio.setMinWidth(100);

		columnAmount = new TableColumn<>("Amount");

		columnAmountDr = new TableColumn<>("Dr");
		columnAmountDr.setCellValueFactory(new PropertyValueFactory<>("amountDrString"));
		columnAmountDr.setMinWidth(100);

		columnAmountCr = new TableColumn<>("Cr");
		columnAmountCr.setCellValueFactory(new PropertyValueFactory<>("amountCrString"));
		columnAmountCr.setMinWidth(100);

		columnBalane = new TableColumn<>("Balance");
		columnBalane.setCellValueFactory(new PropertyValueFactory<>("balanceString"));
		columnBalane.setMinWidth(100);

		columnAmount.getColumns().addAll(columnAmountDr, columnAmountCr, columnBalane);

		tableViewAccounts.getColumns().addAll(columnSNo, columnDate, columnAccountName, columnVoucherNumber,
				columnDetails, columnFolio, columnAmount);

		vBoxTable.getChildren().add(tableViewAccounts);

		vBoxMain.getChildren().addAll(vBoxFilter, vBoxTable);

		// borderPane.setTop(hBox);
		borderPane.setCenter(vBoxMain);

		refreshAccountsTable();

		scene = new Scene(borderPane);

		String styleSheet = getClass().getResource("generalledger.css").toExternalForm();
		scene.getStylesheets().add(styleSheet);

		jfxPanel.setPreferredSize(new Dimension(1160, 480));
		jfxPanel.setScene(scene);

		this.add(jfxPanel);
	}

	protected void displayAccountName() {
		// TODO Auto-generated method stub

	}

	private void openNewAccount() {

		if (fieldAccountName.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("The Account name must be specified");
			alert.showAndWait();
			return;
		} else if (fieldDetails.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("The details must be specified");
			alert.showAndWait();
			return;
		} else {

			String accountName = fieldAccountName.getText();
			String details = fieldDetails.getText();

			AccountDAO dao = new AccountDAO();

			Accounts accounts = new Accounts(accountName, details);

			try {
				dao.newAccount(accounts);

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Account " + accountName + " \n was successfully created");
				alert.showAndWait();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			FinanceGeneralLedger.fillFieldAccountDetails();

		}

	}

	public static void refreshAccountsTable() {
		ObservableList<Accounts> accounts = null;
		AccountDAO dao = new AccountDAO();

		try {
			accounts = dao.getAllAccountsToTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tableViewAccounts.itemsProperty().setValue(accounts);

	}

	public JFXPanel getJfxPanel() {
		return jfxPanel;
	}

	public void displayInComboBox(ComboBox combo, String query) {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			listOfItemsreceiveable.clear();
			while (rs.next()) {

				listOfItemsreceiveable.add(rs.getString(1));
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

	public void displayAccountNumber(TextField field, String query) {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				field.setText(rs.getString(1));
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

}
