package school.ui.student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import school.ui.finances.CashBookController;

public class PrefectsController {

	

	Connection myConnection = null;

	public PrefectsController() throws SQLException {
		myConnection =  CashBookController.getConnection();
	}

	public void addPrefect(Prefects prefects) throws SQLException {
		PreparedStatement myPreparedStatement = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("insert into prefects(id_number,"
					+ "first_name, middle_name, last_name, student_class, gender, year_of_regime,"
					+ "post) values(?,?,?,?," + "?,?,?,?)");

			myPreparedStatement.setString(1, prefects.getIdNumber());
			myPreparedStatement.setString(2, prefects.getFirstName());
			myPreparedStatement.setString(3, prefects.getMiddleName());
			myPreparedStatement.setString(4, prefects.getLastName());
			myPreparedStatement.setString(5, prefects.getStudentClass());
			myPreparedStatement.setString(6, prefects.getGender());
			myPreparedStatement.setString(7, prefects.getYearOfRegime());
			myPreparedStatement.setString(8, prefects.getPost());

			myPreparedStatement.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Successfully Saved New Prefect");

			myPreparedStatement.close();
			myConnection.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
