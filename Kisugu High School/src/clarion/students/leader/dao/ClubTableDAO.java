package clarion.students.leader.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import clarion.student.leaders.core.Club;
import clarion.student.leaders.core.Prefects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import school.ui.finances.CashBookController;

public class ClubTableDAO {

	

	Connection myConnection = null;

	public ClubTableDAO() throws SQLException {
		myConnection =  CashBookController.getConnection();
	}

	public void addNewClubMember(Club prefects, String tableName) throws SQLException {
		PreparedStatement myPreparedStatement = null;
		try {
			myPreparedStatement = myConnection.prepareStatement(
					"insert into  `" + tableName + "`(" + "id, id_number, first_name, middle_name, last_name, "
							+ "student_class, gender, year_of_regime, post ) values(" + "?,?,?,?,?," + "?,?,?,?)");

			myPreparedStatement.setInt(1, prefects.getId());
			myPreparedStatement.setString(2, prefects.getIdNumber());
			myPreparedStatement.setString(3, prefects.getFirstName());
			myPreparedStatement.setString(4, prefects.getMiddleName());
			myPreparedStatement.setString(5, prefects.getLastName());

			myPreparedStatement.setString(6, prefects.getStudentClass());
			myPreparedStatement.setString(7, prefects.getGender());
			myPreparedStatement.setString(8, prefects.getYearOfRegime());
			myPreparedStatement.setString(9, prefects.getPost());

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

	public void editClubMember(Club prefects, String tableName, int id) throws SQLException {
		PreparedStatement myPreparedStatement = null;
		try {
			myPreparedStatement = myConnection.prepareStatement("update  `" + tableName + "` " + "set " + "id_number=?,"
					+ "first_name=?, " + "middle_name=?, " + "last_name=?, " + "student_class=?, " + "gender=?, "
					+ "year_of_regime=?, " + "post=? where id=?");

			myPreparedStatement.setString(1, prefects.getIdNumber());
			myPreparedStatement.setString(2, prefects.getFirstName());
			myPreparedStatement.setString(3, prefects.getMiddleName());
			myPreparedStatement.setString(4, prefects.getLastName());

			myPreparedStatement.setString(5, prefects.getStudentClass());
			myPreparedStatement.setString(6, prefects.getGender());
			myPreparedStatement.setString(7, prefects.getYearOfRegime());
			myPreparedStatement.setString(8, prefects.getPost());

			myPreparedStatement.setInt(9, id);

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

	public void removeClubMember(Object tableName, int id) throws SQLException {
		PreparedStatement myPreparedStatement = null;
		try {
			myPreparedStatement = myConnection
					.prepareStatement("delete from  `" + tableName + "` where id='" + id + "'");

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

	public void editPrefect(Prefects prefects, int id) throws SQLException {
		PreparedStatement myPreparedStatement = null;
		try {
			myPreparedStatement = myConnection
					.prepareStatement("update prefects set id_number=?, first_name=?, middle_name=?, last_name=?, "
							+ "student_class=?, gender=?, year_of_regime=?, post=? where id='" + id + "'");

			// myPreparedStatement.setInt(1, prefects.getId());
			myPreparedStatement.setString(1, prefects.getIdNumber());
			myPreparedStatement.setString(2, prefects.getFirstName());
			myPreparedStatement.setString(3, prefects.getMiddleName());
			myPreparedStatement.setString(4, prefects.getLastName());

			myPreparedStatement.setString(5, prefects.getStudentClass());
			myPreparedStatement.setString(6, prefects.getGender());
			myPreparedStatement.setString(7, prefects.getYearOfRegime());
			myPreparedStatement.setString(8, prefects.getPost());

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

	public ObservableList<Prefects> getAllPrefects() throws SQLException {
		ObservableList<Prefects> prefects = FXCollections.observableArrayList();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		Prefects prefects2 = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from prefects");
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

				prefects2 = new Prefects(id, idNumber, firstName, middleName, lastName, studentClass, gender,
						yearOfRegime, post);
				prefects.add(prefects2);
			}

			return prefects;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return prefects;
	}

}
