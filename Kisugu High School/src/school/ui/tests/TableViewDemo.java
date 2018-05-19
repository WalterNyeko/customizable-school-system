package school.ui.tests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import school.ui.finances.CashBookController;

public class TableViewDemo {

	private static ObservableList<UserMaster> row;

	private static ObservableList<String> col;

	public static void buildData(TableView<UserMaster> tableview) {

		Connection c;

		String SQL = "SELECT * from subcategory";


		try {

			String colHeading[] = { "ID", "Category ID", "Account", "Account Type" };

			col = FXCollections.observableArrayList(colHeading);

			row = FXCollections.observableArrayList();

			c = CashBookController.getConnection();

			ResultSet rs = c.createStatement().executeQuery(SQL);

			TableColumn<UserMaster, String> colName = new TableColumn<UserMaster, String>(col.get(0));

			colName.setMinWidth(200);

			colName.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("name"));

			TableColumn<UserMaster, String> colCourse = new TableColumn<UserMaster, String>(col.get(1));

			colCourse.setMinWidth(200);

			colCourse.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("course"));

			TableColumn<UserMaster, String> colEmail = new TableColumn<UserMaster, String>(col.get(2));

			colEmail.setMinWidth(200);

			colEmail.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("email"));

			TableColumn<UserMaster, String> colCity = new TableColumn<UserMaster, String>(col.get(3));

			colCity.setMinWidth(200);

			colCity.setCellValueFactory(new PropertyValueFactory<UserMaster, String>("city"));

			tableview.getColumns().addAll(colName, colCourse, colEmail, colCity);


			while (rs.next()) {

//				UserMaster cm = new UserMaster();
//				cm.name.set(rs.getString("scatid"));
//				
//				 cm.course.set(rs.getString("catid"));
//				
//				 cm.email.set(rs.getString("scatname"));
//				
//				 cm.city.set(rs.getString("account_type"));
//				
//				 row.add(cm);
			}
			tableview.setItems(row);

		} catch (SQLException sqex) {

			sqex.printStackTrace();

			System.out.println("Error on Building Data");

		}

	}
}
