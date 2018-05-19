package school.ui.tests;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserMaster {

	public SimpleStringProperty item_name = new SimpleStringProperty();

	public SimpleStringProperty delivery_note = new SimpleStringProperty();

	public SimpleStringProperty department = new SimpleStringProperty();

//	public SimpleStringProperty receiver = new SimpleStringProperty();

	public ObjectProperty<Date> date = new SimpleObjectProperty<Date>();

	public SimpleStringProperty remarks = new SimpleStringProperty();

	public SimpleStringProperty quantity_received = new SimpleStringProperty();

	public SimpleStringProperty quantity_issued = new SimpleStringProperty();

	public SimpleStringProperty total = new SimpleStringProperty();

	public SimpleStringProperty balance_left = new SimpleStringProperty();

	public SimpleStringProperty stock_at_hand = new SimpleStringProperty();


	
	

	public String getDepartment() {
		return department.get();
	}

	
//
//	public String getReceiver() {
//		return receiver.get();
//	}

	

	public Date getDate() {
		return date.get();
	}


	public String getRemarks() {
		return remarks.get();
	}

	

	public String getQuantity_received() {
		return quantity_received.get();
	}

	

	public String getQuantity_issued() {
		return quantity_issued.get();
	}

	

	public String getTotal() {
		return total.get();
	}

	

	public String getBalance_left() {
		return balance_left.get();
	}

	
	

	public String setStock_at_hand() {
		return stock_at_hand.get();
	}
	
	public String getItem_name() {
		return item_name.get();
	}

	

	public String getDelivery_note() {
		return delivery_note.get();
	}


}
