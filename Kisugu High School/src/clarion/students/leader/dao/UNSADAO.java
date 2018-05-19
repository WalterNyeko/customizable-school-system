package clarion.students.leader.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import clarion.student.leaders.core.UNSA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import school.ui.finances.CashBookController;

public class UNSADAO {

	

	Connection myConnection = null;

	public UNSADAO() throws SQLException {
		myConnection =  CashBookController.getConnection();
	}

	
	
	
	
	
	
	
	
	public void removeUNSAPost(String post) throws SQLException {
		PreparedStatement myPreparedStatement = null;
		try {
			myPreparedStatement = myConnection.prepareStatement("delete from unsa_post where post='" + post + "'");

			// myPreparedStatement.setString(1, post);

			myPreparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}
			if (myConnection != null) {
				myConnection.close();
			}
		}

	}

	public void removeUNSA(String idNumber) throws SQLException {
		PreparedStatement myPreparedStatement = null;
		try {
			myPreparedStatement = myConnection
					.prepareStatement("delete from unsa where id_number='" + idNumber + "'");

			// myPreparedStatement.setString(1, post);

			myPreparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}
			if (myConnection != null) {
				myConnection.close();
			}
		}
	}

	
	
	
	
	
	
	
	
	
	public void addNewUNSA(UNSA UNSA) throws SQLException {
		PreparedStatement myPreparedStatement = null;
		try {
			myPreparedStatement = myConnection
					.prepareStatement("insert into unsa(" + "id, id_number, first_name, middle_name, last_name, "
							+ "student_class, gender, year_of_regime, post ) values(" + "?,?,?,?,?," + "?,?,?,?)");

			myPreparedStatement.setInt(1, UNSA.getId());
			myPreparedStatement.setString(2, UNSA.getIdNumber());
			myPreparedStatement.setString(3, UNSA.getFirstName());
			myPreparedStatement.setString(4, UNSA.getMiddleName());
			myPreparedStatement.setString(5, UNSA.getLastName());

			myPreparedStatement.setString(6, UNSA.getStudentClass());
			myPreparedStatement.setString(7, UNSA.getGender());
			myPreparedStatement.setString(8, UNSA.getYearOfRegime());
			myPreparedStatement.setString(9, UNSA.getPost());

			myPreparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}
			if (myConnection != null) {
				myConnection.close();
			}
		}
	}

	public void editUNSA(UNSA UNSA, int id) throws SQLException {
		PreparedStatement myPreparedStatement = null;
		try {
			myPreparedStatement = myConnection
					.prepareStatement("update unsa set id_number=?, first_name=?, middle_name=?, last_name=?, "
							+ "student_class=?, gender=?, year_of_regime=?, post=? where id='" + id + "'");

//			myPreparedStatement.setInt(1, UNSA.getId());
			myPreparedStatement.setString(1, UNSA.getIdNumber());
			myPreparedStatement.setString(2, UNSA.getFirstName());
			myPreparedStatement.setString(3, UNSA.getMiddleName());
			myPreparedStatement.setString(4, UNSA.getLastName());

			myPreparedStatement.setString(5, UNSA.getStudentClass());
			myPreparedStatement.setString(6, UNSA.getGender());
			myPreparedStatement.setString(7, UNSA.getYearOfRegime());
			myPreparedStatement.setString(8, UNSA.getPost());

			myPreparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}
			if (myConnection != null) {
				myConnection.close();
			}
		}
	}

	public ObservableList<UNSA> getAllUNSA() throws SQLException {
		ObservableList<UNSA> UNSA = FXCollections.observableArrayList();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		UNSA UNSA2 = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from unsa");
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				int id = myResultSet.getInt("id");
				String idNumber = myResultSet.getString("id_number");
				String firstName = myResultSet.getString("first_name");
				String middleName = myResultSet.getString("middle_name");
				String lastName = myResultSet.getString("last_name");
				String studentClass = myResultSet.getString("student_class");
				String gender = myResultSet.getString("gender");
				String yearOfRegime = myResultSet.getString("year_of_regime");
				String post = myResultSet.getString("post");

				UNSA2 = new UNSA(id, idNumber, firstName, middleName, lastName, studentClass, gender,
						yearOfRegime, post);
				UNSA.add(UNSA2);
			}

			return UNSA;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return UNSA;
	}

}
