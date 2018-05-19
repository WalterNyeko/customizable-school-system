package school.ui.tests;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Collections;
import java.util.List;

public class TableDemo extends Application {
    private int rowIndex = 0;
    private int elementIndex = 0;
    final TableView tableView = new TableView();
    ObservableList<String[]> dataRows = FXCollections.observableArrayList();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        FlowPane pane = new FlowPane();
        tableView.setRowFactory(new CountingRowFactory());
        createTable();
        pane.getChildren().add(tableView);
        List items = Collections.singletonList(new Object());
        tableView.setItems(dataRows);
        stage.setScene(new Scene(pane));
        stage.show();
    }

    private TableColumn createColumn(String header) {
        TableColumn column = new TableColumn(header);
        column.setCellFactory(new CellFactory());
        column.setCellValueFactory(new CountingValueFactory());
        return column;
    }

    private static class CellFactory implements Callback {
        @Override
        public Object call(Object column) {
            return new TableCell() {
                @Override
                protected void updateItem(Object o, boolean b) {
                    super.updateItem(o, b);
                    setText(String.valueOf(o));
                }
            };
        }
    }

    private class CountingRowFactory implements Callback {
        @Override
        public Object call(Object o) {
            TableRow row = new TableRow();
            row.setUserData(rowIndex);
            rowIndex++;
            return row;
        }
    }

    private class CountingValueFactory implements Callback {
        @Override
        public Object call(Object o) {
            SimpleIntegerProperty property = new SimpleIntegerProperty(elementIndex);
            elementIndex++;
            return property;
        }
    }
    public void createTable(){
        String[] header  = {"a","b","c","d","e","f","g","h"};
        for(String s: header)
            tableView.getColumns().add(createColumn(s));
        for(int i=0;i<30;i++){
            String[] temp  = header;
            for(int j=0;j<temp.length;j++)
                temp[j] += + i ; 
            dataRows.add(temp);
        }
    }
}