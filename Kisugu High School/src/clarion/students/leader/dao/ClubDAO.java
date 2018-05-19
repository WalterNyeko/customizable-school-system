package clarion.students.leader.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import clarion.controller.dorm.DormController;
import clarion.student.leaders.core.Club;
import clarion.student.leaders.core.ClubName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import school.ui.finances.CashBookController;

public class ClubDAO {

	

	Connection myConnection = null;

	public ClubDAO() throws SQLException {
		myConnection = CashBookController.getConnection();
	}

	public void createClubTable(String tableName, ClubName name) throws SQLException {
		PreparedStatement myPreparedStatement = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("create table `" + tableName
					+ "`(id int auto_increment primary key, "
					+ "id_number varchar(100), "
					+ "first_name varchar(100),"
					+ "middle_name varchar(100),"
					+ "last_name varchar(100),"
					+ "student_class varchar(100),"
					+ "gender varchar(20),"
					+ "year_of_regime varchar(50),"
					+ "post varchar(100)"
					+ ")");

			myPreparedStatement.executeUpdate();

			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}

			// insert the particular dorm name into the dorm table
			myPreparedStatement = myConnection.prepareStatement("insert into club(id, club_name) values(?,?)");

			// tableName = name.getDormName();

			myPreparedStatement.setInt(1, name.getId());
			myPreparedStatement.setString(2, name.getClubName());

			myPreparedStatement.executeUpdate();

			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}
			if (myConnection != null) {
				myConnection.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ObservableList<Object> fillClubTree() throws SQLException {
		ObservableList<Object> list = FXCollections.observableArrayList();

		ResultSet myResultSet = null;
		PreparedStatement myPreparedStatement = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("Select * from `club`");
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				// Object dorm = convertToDorm(myResultSet);
				Object club = myResultSet.getString(2);
				list.add(club);
			}
			return list;

			// } catch (Exception e) {
			// e.printStackTrace();
		} finally {
			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}
			if (myResultSet != null) {
				myResultSet.close();
			}
		}
	}

	private Club convertToClub(ResultSet myResultSet) throws SQLException {
		int id = myResultSet.getInt("id");
		String idNumber = myResultSet.getString("id_number");
		String firstName = myResultSet.getString("first_name");
		String middleName = myResultSet.getString("middle_name");
		String lastName = myResultSet.getString("last_name");
		String studentClass = myResultSet.getString("student_class");
		String gender = myResultSet.getString("gender");
		String yearOfRegime = myResultSet.getString("year_of_regime");
		String post = myResultSet.getString("post");

		Club club = new Club(id, idNumber, firstName, middleName, lastName, studentClass, gender,
				yearOfRegime, post);
		return club;
	}

	public ObservableList<Club> getAllClubMembers(String tableName) throws SQLException {
		ObservableList<Club> clubs = FXCollections.observableArrayList();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from `" + tableName + "`");
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				Club club = convertToClub(myResultSet);
				clubs.add(club);
			}

			return clubs;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}
			if (myResultSet != null) {
				myResultSet.close();
			}
		}
		return clubs;
	}

	private ClubName covertToClubNameID(ResultSet myResultSet) throws SQLException {
		int id = myResultSet.getInt("id");
		ClubName clubName = new ClubName(id);
		return clubName;
	}

	public int clubID(String tableName) throws SQLException {
		int id = 0;
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;
		try {
			myPreparedStatement = myConnection
					.prepareStatement("select id from club where club_name = '" + tableName + "'");
			myResultSet = myPreparedStatement.executeQuery();

			if (myResultSet.next()) {
				id = myResultSet.getInt("id");
			}
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return dormNames;
		return id;
	}

	public void changeDormName(Object clubName, String clubNamePrevious, String clubNameNew, int id)
			throws SQLException {
		PreparedStatement myPreparedStatement = null;

		try {

			myPreparedStatement = myConnection
					.prepareStatement("rename table `" + clubNamePrevious + "` to `" + clubNameNew + "`");

			myPreparedStatement.executeUpdate();

			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}

			myPreparedStatement = myConnection.prepareStatement("update `dorm` set clubName = ? where id=?");

			myPreparedStatement.setString(1, clubNameNew);
			myPreparedStatement.setInt(2, id);

			myPreparedStatement.executeUpdate();

			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}

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

	public void removeClub(Object clubName, int id) throws SQLException {
		PreparedStatement myPreparedStatement = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("drop table `" + clubName + "`");
			myPreparedStatement.executeUpdate();

			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}

			myPreparedStatement = myConnection.prepareStatement("delete from club where id = ?");

			myPreparedStatement.setInt(1, id);

			myPreparedStatement.executeUpdate();

			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}

			if (myConnection != null) {
				myConnection.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void transferStudentDorm(String classNo, String stdName, String stdClass, String year, String toTable,
			String fromTable, int id) throws SQLException {
		PreparedStatement myPreparedStatement = null;

		try {
			myPreparedStatement = myConnection.prepareStatement(
					"insert into `" + toTable + "` (class_number,student_name,student_class, year) values(?,?,?,?)");

			String classNumber = new String();
			String studentName = new String();
			String studentClass = new String();
			// String year = new String();

			myPreparedStatement.setString(1, classNo);
			myPreparedStatement.setString(2, stdName);
			myPreparedStatement.setString(3, stdClass);
			myPreparedStatement.setString(4, year);

			myPreparedStatement.executeUpdate();

			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}

			myPreparedStatement = myConnection.prepareStatement("delete from `" + fromTable + "` where id=?");
			myPreparedStatement.setInt(1, id);

			myPreparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ObservableList<Club> searchClubMember(String searchValue, String clubName) throws SQLException {
		ObservableList<Club> clubs = FXCollections.observableArrayList();

		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from `" + clubName + "` where concat("
					+ "`id_number`, `first_name`, `middle_name`,"
					+ "`last_name`"
					+ "`student_class`"
					+ "`gender`"
					+ "`year_of_regime`"
					+ "`post`) LIKE '%"+searchValue+"%'");

			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				Club club = convertToClub(myResultSet);
				clubs.add(club);
			}
			return clubs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clubs;
	}

//	public static void main(String[] args) throws SQLException {
//		DormController controller = new DormController();
//		System.out.println(controller.searchStudent("o", "HSC Wing"));
//	}
	
}
