package school.ui.tests;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Nyekorac extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		TableView<Person> table = new TableView<>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setTableMenuButtonVisible(true);
		TableColumn<Person, String> column = new TableColumn<>("first");
		column.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Person, String> p) {
				return new ReadOnlyObjectWrapper(p.getValue().getFirstName());
			}
		});
		table.getColumns().add(column);
		column = new TableColumn<>("last");
		column.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Person, String> p) {
				return new ReadOnlyObjectWrapper(p.getValue().getLastName());
			}
		});
		table.getColumns().add(column);
		table.getItems().addAll(new Person("peter", "M."), new Person("john", "doe"));
		table.getSelectionModel().selectedItemProperty().addListener(new SelectionListener());
		table.getSelectionModel().selectedIndexProperty().addListener(new indexListener());
		primaryStage.setScene(new Scene(table));
		primaryStage.show();
	}
}

class SelectionListener implements ChangeListener<Person> {
	@Override
	public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
		if (newValue == null) {
			System.out.println("null selection");
		}
		else {
			System.out.println("item selected: "+newValue.getFirstName());
		}
	}
}

class indexListener implements ChangeListener<Number> {
	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		if (newValue == null) {
			System.out.println("null index");
		}
		else {
			System.out.println(newValue);
		}
	}
}

class Person {
	String first, last;

	public Person(String first, String last) {
		super();
		this.first = first;
		this.last = last;
	}

	public String getFirstName() {
		return first;
	}

	public Object getLastName() {
		return last;
	}

	public Observable checkedProperty() {
		// TODO Auto-generated method stub
		return null;
	}
}