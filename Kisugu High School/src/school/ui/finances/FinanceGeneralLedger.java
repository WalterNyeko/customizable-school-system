package school.ui.finances;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumnModel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.textfield.TextFields;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import com.sun.javafx.scene.control.skin.TableViewSkinBase;

import clarion.finance.core.Accounts;
import clarion.finance.core.GeneralLedger;
import clarion.finance.core.SubCategory;
import clarion.finance.dao.AccountDAO;
import clarion.finance.dao.GeneralLedgerDAO;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import javafx.util.StringConverter;

public class FinanceGeneralLedger extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFXPanel jfxPanel;
	private Scene scene;
	private BorderPane borderPane;

	private VBox vBoxFields;

	private Label labelDate;
	private DatePicker datePicker;
	private CheckBox checkBoxDr;
	private CheckBox checkBoxCr;
	private HBox hBoxToggle;

	private Label labelVoucherNumber;
	private TextField fieldVoucherNumber;

	private Label labelDetails;
	private static TextField fieldDetails;
	private Label labelAmount;
	private TextField fieldAmount;

	private Button buttonSave;
	private Button buttonCancel;

	private GridPane gridPaneFields;

	private VBox vBoxBottomButtons;
	private Button buttonExport;
	private Button buttonPrint;
	private GridPane gridPaneBottomButtons;

	private VBox vBoxTable;
	private TableView<GeneralLedger> tableViewGeneralLedger;

	private TableColumn<GeneralLedger, Date> columnDate;
	private TableColumn<GeneralLedger, String> columnVoucherNumber;
	private TableColumn<GeneralLedger, String> columnDetails;

	private TableColumn columnAmount;
	private TableColumn<GeneralLedger, String> columnAmountDr;
	private TableColumn<GeneralLedger, String> columnAmountCr;

	private TableColumn<GeneralLedger, String> columnAccountName;
	private TableColumn<GeneralLedger, String> columnBalance;

	private Label labelFolio;
	private TextField fieldFolio;

	private Label labelAccountName;

	private Connection conn;

	private PreparedStatement pst;

	private ObservableList<String> dataAccountName;

	protected JFileChooser fileChooser;
	public static ComboBox<String> comboBoxAccountName;

	@SuppressWarnings("unchecked")
	public FinanceGeneralLedger() {
Platform.runLater(new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};

		jfxPanel = new JFXPanel();
		borderPane = new BorderPane();

		// fields
		vBoxFields = new VBox();
		gridPaneFields = new GridPane();
		// gridPaneFields.setAlignment(Pos.CENTER);
		gridPaneFields.setVgap(10);
		gridPaneFields.setHgap(10);
		gridPaneFields.setPadding(new Insets(10));
		vBoxFields.getChildren().addAll(gridPaneFields);

		hBoxToggle = new HBox();
		hBoxToggle.setPadding(new Insets(10));
		hBoxToggle.setSpacing(10);

		labelDate = new Label("Date:");
		labelAmount = new Label("Amount:");
		labelDetails = new Label("Details");
		labelVoucherNumber = new Label("Voucher Number");

		labelFolio = new Label("Folio");
		fieldFolio = new TextField();

		datePicker = new DatePicker();
		datePicker.setConverter(converter);

		fieldVoucherNumber = new TextField();
		fieldDetails = new TextField();
		fieldAmount = new TextField();

		buttonSave = new Button("ENTER");
		buttonCancel = new Button("Cancel");

		HBox hBoxButtonsTop = new HBox();
		hBoxButtonsTop.setSpacing(10);
		hBoxButtonsTop.getChildren().addAll(buttonSave, buttonCancel);

		checkBoxDr = new CheckBox("Dr");

		checkBoxCr = new CheckBox("Cr");

		hBoxToggle.setPadding(new Insets(10));
		hBoxToggle.getChildren().addAll(checkBoxDr, checkBoxCr);

		labelAccountName = new Label("Select Account:");
		dataAccountName=FXCollections.observableArrayList();
		comboBoxAccountName = new ComboBox<String>(dataAccountName);

		// gridPaneFields.add(hBoxToggle, 2, 0);

		gridPaneFields.add(labelDate, 0, 0);
		gridPaneFields.add(datePicker, 1, 0);
		datePicker.setPromptText("Choose date");

		gridPaneFields.add(labelVoucherNumber, 2, 0);
		gridPaneFields.add(fieldVoucherNumber, 3, 0);
		fieldVoucherNumber.setPromptText("Voucher Number");

		gridPaneFields.add(labelFolio, 4, 0);
		gridPaneFields.add(fieldFolio, 5, 0);
		fieldFolio.setPromptText("Folio");

		gridPaneFields.add(labelDetails, 6, 0);
		gridPaneFields.add(fieldDetails, 7, 0);
		fieldDetails.setPromptText("Details");

		gridPaneFields.add(labelAmount, 0, 1);
		gridPaneFields.add(fieldAmount, 1, 1);
		fieldAmount.setPromptText("Amount");

		gridPaneFields.add(labelAccountName, 2, 1);
		gridPaneFields.add(comboBoxAccountName, 3, 1);
		comboBoxAccountName.setPromptText("Choose Account");

		gridPaneFields.add(hBoxToggle, 4, 1);
		gridPaneFields.add(hBoxButtonsTop, 5, 1);

		// table
		vBoxTable = new VBox();

		tableViewGeneralLedger = new TableView<>();
		tableViewGeneralLedger.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn columnSNo = new TableColumn<>("#");
		columnSNo.setCellValueFactory(
				new Callback<CellDataFeatures<GeneralLedger, GeneralLedger>, ObservableValue<GeneralLedger>>() {
					@Override
					public ObservableValue<GeneralLedger> call(CellDataFeatures<GeneralLedger, GeneralLedger> p) {
						return new ReadOnlyObjectWrapper(p.getValue());
					}
				});

		columnSNo.setCellFactory(
				new Callback<TableColumn<GeneralLedger, GeneralLedger>, TableCell<GeneralLedger, GeneralLedger>>() {
					@Override
					public TableCell<GeneralLedger, GeneralLedger> call(
							TableColumn<GeneralLedger, GeneralLedger> param) {
						return new TableCell<GeneralLedger, GeneralLedger>() {
							@Override
							protected void updateItem(GeneralLedger item, boolean empty) {
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
		columnSNo.setPrefWidth(120);
		columnSNo.setMaxWidth(200);

		columnDate = new TableColumn<>("Date");
		columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

		columnAccountName = new TableColumn<>("Account");
		columnAccountName.setCellValueFactory(new PropertyValueFactory<>("accountName"));

		columnVoucherNumber = new TableColumn<>("Voucher Number");
		columnVoucherNumber.setCellValueFactory(new PropertyValueFactory<>("voucherNumber"));

		columnDetails = new TableColumn<>("Details");
		columnDetails.setCellValueFactory(new PropertyValueFactory<>("details"));

		columnAmount = new TableColumn<>("Amount");
		columnAmountDr = new TableColumn<>("Dr");
		columnAmountDr.setCellValueFactory(new PropertyValueFactory<>("amountDrString"));

		columnAmountCr = new TableColumn<>("Cr");
		columnAmountCr.setCellValueFactory(new PropertyValueFactory<>("amountCrString"));

		columnBalance = new TableColumn<>("Balance");
		columnBalance.setCellValueFactory(new PropertyValueFactory<>("balanceString"));

		columnAmount.getColumns().addAll(columnAmountDr, columnAmountCr, columnBalance);

		tableViewGeneralLedger.getColumns().addAll(columnSNo, columnDate, columnAccountName, columnVoucherNumber,
				columnDetails, columnAmount);

		vBoxTable.getChildren().addAll(tableViewGeneralLedger);

		// bottom
		vBoxBottomButtons = new VBox();
		vBoxBottomButtons.setAlignment(Pos.CENTER);

		gridPaneBottomButtons = new GridPane();
		gridPaneBottomButtons.setAlignment(Pos.CENTER);
		gridPaneBottomButtons.setVgap(10);
		gridPaneBottomButtons.setHgap(10);
		gridPaneBottomButtons.setPadding(new Insets(10));

		buttonExport = new Button("Export");
		buttonExport.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				

				 fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify name and folder to export this deatils");

					int selected = fileChooser.showSaveDialog(FinanceGeneralLedger.this);

					if (selected == JFileChooser.APPROVE_OPTION) {
						try {

							fillData(tableViewGeneralLedger, new java.io.File(fileChooser.getSelectedFile() + ".xlsx"));
							
							writeExcel( new java.io.File(fileChooser.getSelectedFile() + ".csv"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}

			}
		});
		buttonPrint = new Button("Print");

		gridPaneBottomButtons.add(buttonExport, 0, 0);
		gridPaneBottomButtons.add(buttonPrint, 1, 0);

		vBoxBottomButtons.getChildren().addAll(gridPaneBottomButtons);

		borderPane.setTop(vBoxFields);
		borderPane.setCenter(vBoxTable);
		borderPane.setBottom(vBoxBottomButtons);

		// listening

		populateGeneralLedgerTable();

//		fillComboAccountName();
		fieldDetails.setOnKeyReleased(e -> {
			fillFieldAccountDetails();
		});

		checkBoxCr.setOnAction(e -> {
			alterChecksCr();
		});

		checkBoxDr.setOnAction(e -> {
			alterChecksDr();
		});

		buttonSave.setOnAction(e -> {
			transactGeneralLedger();
		});

		scene = new Scene(borderPane);
		

		jfxPanel.setScene(scene);
		
		String styleSheet = getClass().getResource("generalledger.css").toExternalForm();
		scene.getStylesheets().add(styleSheet);
		

		
		jfxPanel.setPreferredSize(new Dimension(1160, 480));
		
		
		add(jfxPanel);
	}
});
	}


	public static void fillFieldAccountDetails() {
		ObservableList<String> list = null;
		AccountDAO dao = new AccountDAO();

		try {
			list = dao.getAllAccountDetails();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TextFields.bindAutoCompletion(fieldDetails, list);

	}

	private void alterChecksCr() {
		if (checkBoxCr.isSelected()) {
			checkBoxDr.setSelected(false);
		}
	}

	private void alterChecksDr() {
		if (checkBoxDr.isSelected()) {
			checkBoxCr.setSelected(false);
		}
	}

	private void transactGeneralLedger() {

		LocalDate localDate = datePicker.getValue();
		Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

		String accountName = comboBoxAccountName.getSelectionModel().getSelectedItem();
		String voucherNumber = fieldVoucherNumber.getText();
		String details = fieldDetails.getText();
		String amountStr = fieldAmount.getText();

		String folio = fieldFolio.getText().toUpperCase();

		boolean isDr = false;
		boolean isCr = false;

		BigDecimal balance = null;

		BigDecimal amountDr = BigDecimal.ZERO;
		BigDecimal amountCr = BigDecimal.ZERO;

		GeneralLedgerDAO dao = new GeneralLedgerDAO();

		GeneralLedger generalLedger = null;
		Accounts accounts = null;
		Accounts accounts2 = new Accounts();

		GeneralLedger generalLedger2 = new GeneralLedger();

		SubCategory subCategory = null;

		String subCategoryName = null;

		AccountDAO accountDAO = new AccountDAO();

		if (checkBoxDr.isSelected()) {
			isDr = true;
			BigDecimal amount = new BigDecimal(amountStr);
			generalLedger2.setDr(isDr);

			accounts2.setDr(isDr);

			balance = amount.subtract(amountCr);

			generalLedger = new GeneralLedger(sqlDate, isDr, isCr, accountName, voucherNumber, details, amount,
					balance);
			accounts = new Accounts(sqlDate, accountName, voucherNumber, details, folio, amount, isDr, isCr, balance);

//			subCategoryName = accountName + ":" + details + ":" + sqlDate;
			subCategoryName = accountName;

			subCategory = new SubCategory(subCategoryName);

			try {
				dao.newTransactionDr(generalLedger, accounts);
				accountDAO.newAccountUnderAccountsCategory(subCategory);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (checkBoxCr.isSelected()) {
			isCr = true;
			BigDecimal amount = new BigDecimal(amountStr);

			generalLedger2.setCr(isCr);
			accounts2.setCr(isCr);

			balance = amount.subtract(amountDr);

			generalLedger = new GeneralLedger(sqlDate, isDr, isCr, accountName, voucherNumber, details, amount,
					balance);
			accounts = new Accounts(sqlDate, accountName, voucherNumber, details, folio, amount, isDr, isCr, balance);

//			subCategoryName = accountName + ":" + details + ":" + sqlDate;
			subCategoryName = accountName;

			subCategory = new SubCategory(subCategoryName);

			try {
				dao.newTransactionCr(generalLedger, accounts);
				accountDAO.newAccountUnderAccountsCategory(subCategory);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		FinanceIndividualAccounts.refreshAccountsTable();
		populateGeneralLedgerTable();

	}

	public void populateGeneralLedgerTable() {
		ObservableList<GeneralLedger> ledgers = null;
		GeneralLedgerDAO dao = new GeneralLedgerDAO();
		try {
			ledgers = dao.populateGeneralLegderTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tableViewGeneralLedger.itemsProperty().setValue(ledgers);

	}

	public JFXPanel getJfxPanel() {
		return jfxPanel;
	}

	private void clearGeneralLedgerFields() {
		datePicker.getEditor().clear();
		fieldAmount.clear();
		fieldDetails.clear();
		fieldFolio.clear();
		fieldVoucherNumber.clear();
	}

	public void displayInComboBox(ComboBox combo, String query) {

		try {
			conn = CashBookController.getConnection();
			pst = conn.prepareStatement(query);

			ResultSet rs = pst.executeQuery();


			dataAccountName.clear();
			while (rs.next()) {

				dataAccountName.add(rs.getString(1));
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

	public void fillData(TableView table, java.io.File file) {

		try {


			XSSFWorkbook workbook1 = new XSSFWorkbook();
			
			XSSFSheet   fSheet = workbook1.createSheet("Data Sheet");


			TableColumnModel model=new TableColumnModel() {
				
				@Override
				public void setSelectionModel(ListSelectionModel arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void setColumnSelectionAllowed(boolean arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void setColumnMargin(int arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void removeColumnModelListener(TableColumnModelListener arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void removeColumn(javax.swing.table.TableColumn arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void moveColumn(int arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public int getTotalColumnWidth() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public ListSelectionModel getSelectionModel() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public int[] getSelectedColumns() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public int getSelectedColumnCount() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public Enumeration<javax.swing.table.TableColumn> getColumns() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public boolean getColumnSelectionAllowed() {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public int getColumnMargin() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public int getColumnIndexAtX(int arg0) {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public int getColumnIndex(Object arg0) {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public int getColumnCount() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public javax.swing.table.TableColumn getColumn(int arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void addColumnModelListener(TableColumnModelListener arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void addColumn(javax.swing.table.TableColumn arg0) {
					// TODO Auto-generated method stub
					
				}
			};
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

		        XSSFRow fRow1 = fSheet.createRow((short) 0);
			
				
				table.skinProperty().addListener((a,b,newSkin)->{
					for (int i = 0; i < table.getColumns().size(); i++) {
					TableHeaderRow headers=((TableViewSkinBase)newSkin).getTableHeaderRow();
					XSSFCell cell1 = fRow1.createCell((short) i);
					cell1.setCellValue(headers.toString());
				    cell1.setCellStyle(style);
					}
				});
				
				
			int j = 0;
			for (int i = 0; i < table.getColumns().size(); i++) {
				XSSFRow fRow = fSheet.createRow((short) i+1);
				for (j = 0; j < table.getColumns().size(); j++) {
					  XSSFCell cell2 = fRow.createCell((short) j);
					  
					String cells= table.getColumns().getClass().getCanonicalName();
				    cell2.setCellValue(cells);
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
	
	public void writeExcel(File file) throws IOException {
		Writer writer=null;
		try {
			
			 file=new File("C:\\Users\\WalterNyeko\\Desktop\\Trial.csv");
			writer=new BufferedWriter(new FileWriter(file));
			Object[] data=null;
			for(Object financeGeneralLedger: data) {
				
				String text=tableViewGeneralLedger.getColumns().get(2).toString();
				writer.write(text);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		writer.flush();
		writer.close();
	}
	
}
