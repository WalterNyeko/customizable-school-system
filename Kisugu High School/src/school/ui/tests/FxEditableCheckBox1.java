package school.ui.tests;
import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
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

public class FxEditableCheckBox1 extends Application {

   @Override
   public void start( Stage stage ) throws Exception {
      final TableView<TableViewWithCheckBoxes> view = new TableView<>();
      final ObservableList<TableColumn<TableViewWithCheckBoxes, ?>> columns = view.getColumns();

      final TableColumn<TableViewWithCheckBoxes, String> nameColumn = new TableColumn<>( "Name" );
      nameColumn.setCellValueFactory( new PropertyValueFactory<>( "name" ));
      columns.add(  nameColumn );

      final TableColumn<TableViewWithCheckBoxes, Boolean> loadedColumn = new TableColumn<>( "Delete" );
      loadedColumn.setCellValueFactory( new PropertyValueFactory<>( "delete" ));
      loadedColumn.setCellFactory( tc -> new CheckBoxTableCell<>());
      columns.add( loadedColumn );

      final ObservableList<TableViewWithCheckBoxes> items =
         FXCollections.observableArrayList(
            new TableViewWithCheckBoxes( "Microsoft Windows 3.1"    , true  ),
            new TableViewWithCheckBoxes( "Microsoft Windows 3.11"   , true  ),
            new TableViewWithCheckBoxes( "Microsoft Windows 95"     , true  ),
            new TableViewWithCheckBoxes( "Microsoft Windows NT 3.51", true  ),
            new TableViewWithCheckBoxes( "Microsoft Windows NT 4"   , true  ),
            new TableViewWithCheckBoxes( "Microsoft Windows 2000"   , true  ),
            new TableViewWithCheckBoxes( "Microsoft Windows Vista"  , true  ),
            new TableViewWithCheckBoxes( "Microsoft Windows Seven"  , false ),
            new TableViewWithCheckBoxes( "Linux all versions :-)"   , false ));
      view.setItems( items );
      view.setEditable( true );

      final Button delBtn = new Button( "Delete" );
      delBtn.setMaxWidth( Double.MAX_VALUE );
      delBtn.setOnAction( e -> {
         final Set<TableViewWithCheckBoxes> del = new HashSet<>();
         for( final TableViewWithCheckBoxes os : view.getItems()) {
            if( os.deleteProperty().get()) {
               del.add( os );
            }
         }
         view.getItems().removeAll( del );
      });
      stage.setScene( new Scene( new BorderPane( view, null, null, delBtn, null )));
      BorderPane.setAlignment( delBtn, Pos.CENTER );
      stage.show();
   }

   public static void main( String[] args ) {
      launch( args );
   }
}