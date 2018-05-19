package clarion.controller.dorm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import clarion.model.dorm.Dorm;
import clarion.model.dorm.DormName;
import school.ui.finances.CashBookController;

public class DormController {

	

	Connection myConnection = null;

	public DormController() throws SQLException {
		myConnection =CashBookController.getConnection();
	}

	public void createDormTable(String tableName, DormName name) throws SQLException {
		PreparedStatement myPreparedStatement = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("create table `" + tableName
					+ "`(id int auto_increment primary key, "
					+ "class_number varchar(100),payment_code varchar(200), student_name varchar(255), student_class varchar(100), year TIMESTAMP)");

			myPreparedStatement.executeUpdate();

			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}

			// insert the particular dorm name into the dorm table
			myPreparedStatement = myConnection.prepareStatement("insert into dorm(id, dormName) values(?,?)");

			// tableName = name.getDormName();

			myPreparedStatement.setInt(1, name.getId());
			myPreparedStatement.setString(2, name.getDormName());

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

	public List<Object> fillDormTree() throws SQLException {
		List<Object> list = new ArrayList<Object>();
		 list.add("Choose Dormitory");

		ResultSet myResultSet = null;
		PreparedStatement myPreparedStatement = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("Select * from `dorm`");
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				// Object dorm = convertToDorm(myResultSet);
				Object dorm = myResultSet.getString(2);
				list.add(dorm);
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

	private Dorm convertToDorm(ResultSet myResultSet) throws SQLException {
		int id = myResultSet.getInt("id");
		String classNumber = myResultSet.getString("class_number");
		String studentName = myResultSet.getString("student_name");
		String studentClass = myResultSet.getString("student_class");
		String year = myResultSet.getString("year");

		Dorm dorm = new Dorm(id, classNumber, studentName, studentClass, year);
		return dorm;
	}

	public List<Dorm> getAllDormStudents(String tableName) throws SQLException {
		List<Dorm> list = new ArrayList<>();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from `" + tableName + "`");
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				Dorm dorm = convertToDorm(myResultSet);
				list.add(dorm);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private DormName covertToDormNameID(ResultSet myResultSet) throws SQLException {
		int id = myResultSet.getInt("id");
		DormName dormName = new DormName(id);
		return dormName;
	}

	public int dormID(String tableName) throws SQLException {
		// DormName dormNames = null;
		int id = 0;
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;
		try {
			myPreparedStatement = myConnection
					.prepareStatement("select id from dorm where dormName = '" + tableName + "'");
			myResultSet = myPreparedStatement.executeQuery();

			if (myResultSet.next()) {
				id = myResultSet.getInt("id");
				// dormNames = new DormName(id);
			}
			// return dormNames;
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return dormNames;
		return id;
	}

	public void changeDormName(Object dormName, String dormNamePrevious, String dormNameNew, int id)
			throws SQLException {
		PreparedStatement myPreparedStatement = null;

		try {

			myPreparedStatement = myConnection
					.prepareStatement("rename table `" + dormNamePrevious + "` to `" + dormNameNew + "`");

			myPreparedStatement.executeUpdate();

			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}

			myPreparedStatement = myConnection.prepareStatement("update `dorm` set dormName = ? where id=?");

			myPreparedStatement.setString(1, dormNameNew);
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

	public void removeDormitory(Object dormitoryName, int id) throws SQLException {
		PreparedStatement myPreparedStatement = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("drop table `" + dormitoryName + "`");
			myPreparedStatement.executeUpdate();

			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}

			myPreparedStatement = myConnection.prepareStatement("delete from dorm where id = ?");

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

	public List<Dorm> searchStudent(String searchValue, String dormName) throws SQLException {
		List<Dorm> searches = new ArrayList<>();

		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from `" + dormName + "` where concat("
					+ "`class_number`, `student_name`, `student_class`, `year`) LIKE '%"+searchValue+"%'");

			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				Dorm dorm = convertToDorm(myResultSet);
				searches.add(dorm);
			}
			return searches;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searches;
	}

	public static void main(String[] args) throws SQLException {
		DormController controller = new DormController();
		System.out.println(controller.searchStudent("o", "HSC Wing"));
	}
	
}
