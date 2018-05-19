package school.ui.tests;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import school.ui.finances.CashBookController;

public class TableViewWithCheckBoxes {

   private final StringProperty  name   = new SimpleStringProperty();
   private final BooleanProperty delete = new SimpleBooleanProperty();
private ObservableList<Object> data;

   public TableViewWithCheckBoxes( String nm, boolean del ) {
      name  .set( nm  );
      delete.set( del );
   }

   public StringProperty  nameProperty  () { return name;   }
   public BooleanProperty deleteProperty() { return delete; }

public StringProperty getName() {
	return name;
}

public BooleanProperty getDelete() {
	return delete;
}
   
  
}

