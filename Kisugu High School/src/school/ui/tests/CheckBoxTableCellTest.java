package school.ui.tests;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import school.ui.finances.CashBookController;

public class CheckBoxTableCellTest extends Application {

	private TableView<cours> tableView;

	public ObservableList<cours> coursData;// = FXCollections.observableArrayList();

	protected List<cours> items;

	private ObservableList<Object> data;
	TableColumn<cours, Boolean> checkedCol;

	@Override

	public void start(Stage primaryStage) {

		this.tableView = new TableView<cours>();
		final TableColumn<cours, String> Cours = new TableColumn<cours, String>("Cours");
		checkedCol = new TableColumn<cours, Boolean>("Checked");

		java.sql.Connection c;

		data = FXCollections.observableArrayList();
		coursData = FXCollections.observableArrayList(new Callback<cours, Observable[]>() {

			@Override
			public Observable[] call(cours param) {
				return new Observable[] { param.checkedProperty() };
			}
		});

		try {
			c = CashBookController.getConnection();
			String SQL = "select test_name from students_tests";
			ResultSet rs = c.createStatement().executeQuery(SQL);

			/********************************
			 * Data added to ObservableList *
			 ********************************/

			String[] rowInfo = new String[rs.getMetaData().getColumnCount() + 1];
			while (rs.next()) {
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if (rs.getString(i) != null) {
						rowInfo[i] = rs.getString(i);

						items = Arrays.asList(new cours(rowInfo[i]));
					} else {
						rowInfo[i] = " ";
						row.add(rowInfo[i]);
					}
				}
				
				
				data.add(row);
			}
			
			for(cours values: items) {
				coursData.add(values);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data: "+e.getMessage());
		}

		
//		coursData.addAll(items);

		tableView.getColumns().addAll(Cours, checkedCol);
		tableView.setItems(coursData);

		coursData.addListener(new ListChangeListener<cours>() {

			@Override
			public void onChanged(ListChangeListener.Change<? extends cours> c) {
				while (c.next()) {
					if (c.wasUpdated()) {
						System.out.println("Cours " + items.get(c.getFrom()).getCours() + " changed value to "
								+ items.get(c.getFrom()).isChecked());
						if (items.get(c.getFrom()).isChecked() == true) {
							JOptionPane.showMessageDialog(null, "Yes");
						}
					}
				}
			}
		});

		Cours.setCellValueFactory(new PropertyValueFactory<cours, String>("cours"));

		checkedCol.setCellValueFactory(new PropertyValueFactory<cours, Boolean>("checked"));

		checkedCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkedCol));

		checkedCol.setEditable(true);
		tableView.setEditable(true);

		final BorderPane root = new BorderPane();
		root.setCenter(tableView);

		coursData.addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable o) {
				System.out.println("checkBox change state ");
				// Here is my problem.
				// When the user click on a checkBox , the method isn't call .
			}
		});

		// buildDataForTableWithCheckBoxes(tableView);

		Scene scene = new Scene(root, 300, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static class cours {
		private StringProperty cours;
		private BooleanProperty checked;

		public cours(String cours) {
			this.cours = new SimpleStringProperty(cours);
			this.checked = new SimpleBooleanProperty(true);
		}

		public String getCours() {
			return cours.get();
		}

		public boolean isChecked() {
			return checked.get();
		}

		public void setCours(String cours) {
			this.cours.set(cours);
		}

		public void setChecked(boolean checked) {
			this.checked.set(checked);
		}

		public StringProperty coursProperty() {
			return cours;
		}

		public BooleanProperty checkedProperty() {
			return checked;
		}
	}

	public void buildDataForTableWithCheckBoxes(TableView table) {
		java.sql.Connection c;

		try {
			c = CashBookController.getConnection();
			String SQL = "select test_name from students_tests";
			ResultSet rs = c.createStatement().executeQuery(SQL);

			ArrayList<String> tableTitr = new ArrayList<>(Arrays.asList("UserName"));
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

				final int j = i;
				TableColumn col = new TableColumn(tableTitr.get(i));

				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				table.getColumns().addAll(col);
			}

			/********************************
			 * Data added to ObservableList *
			 ********************************/
			String[] rowInfo = new String[rs.getMetaData().getColumnCount() + 1];
			while (rs.next()) {
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if (rs.getString(i) != null)
						rowInfo[i] = rs.getString(i);

					else
						rowInfo[i] = " ";

					row.add(rowInfo[i]);
				}

				data.add(row);
			}

			table.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}

	}
}
