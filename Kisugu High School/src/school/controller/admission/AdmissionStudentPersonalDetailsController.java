package school.controller.admission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import school.model.admission.AdmissionStudentPersonalDetails;
import school.ui.finances.CashBookController;

public class AdmissionStudentPersonalDetailsController implements AdmissionStudentPersonalDetailsInterface {


	Connection myConnection = null;

	public AdmissionStudentPersonalDetailsController() {
		myConnection = CashBookController.getConnection();
	}

	@Override
	public void addStudentPersonalDetailsAtAdmission(AdmissionStudentPersonalDetails details) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myPreparedStatement = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("insert into AdmissionStudentPersonalDetails("
					+ "idNumber, firstName, middleName, lastName, gender, "
					+ "dob, nationality, classJoining, house, dormitory "
					+ "religion, term, yearOfAdmission, parentName, parentEmail "
					+ "parentPhone, parentOccupation, parentAddress, isSponsored, sponsorName" + ")" + "values("
					+ "?,?,?,?,?," + "?,?,?,?,?," + "?,?,?,?,?," + "?,?,?,?,?," + "?,?,?,?,?");

			myPreparedStatement.setString(1, details.getIdNumber());
			myPreparedStatement.setString(2, details.getFirstName());
			myPreparedStatement.setString(3, details.getMiddleName());
			myPreparedStatement.setString(4, details.getLastName());
			myPreparedStatement.setString(5, details.getGender());

			Date UtilDob = details.getDob();
			java.sql.Date sqlDob = null;
			if (UtilDob != null) {
				sqlDob = new java.sql.Date(UtilDob.getTime());
			}

			myPreparedStatement.setDate(6, sqlDob);

			myPreparedStatement.setString(7, details.getNationality());
			myPreparedStatement.setString(8, details.getClassJoining());
			myPreparedStatement.setString(9, details.getHouse());
			myPreparedStatement.setString(10, details.getDormitory());
			myPreparedStatement.setString(11, details.getReligion());

			myPreparedStatement.setString(12, details.getTerm());

			Calendar calendarYear = Calendar.getInstance();
			java.sql.Date sqlYear = new java.sql.Date(calendarYear.getTimeInMillis());

			myPreparedStatement.setDate(13, sqlYear);
			myPreparedStatement.setString(14, details.getParentName());
			myPreparedStatement.setString(15, details.getParentEmail());
			myPreparedStatement.setString(16, details.getParentPhone());

			myPreparedStatement.setString(17, details.getParentOccupation());
			myPreparedStatement.setString(18, details.getParentAddress());
			myPreparedStatement.setBoolean(19, details.isSponsored());
			myPreparedStatement.setString(20, details.getSponsorName());

			myPreparedStatement.executeUpdate();

			// close statement and connection
			myPreparedStatement.close();
			myConnection.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Override
	public void editStudentPersonalDetailsAtAdmission(String idNumber) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public AdmissionStudentPersonalDetails convertToAdmittedStudent(ResultSet myResultSet) throws SQLException {
		// TODO Auto-generated method stub
		String idNumber = myResultSet.getString("idNumber");
		String firstName = myResultSet.getString("firstName");
		String middleName = myResultSet.getString("middleName");
		String lastName = myResultSet.getString("lastName");
		String gender = myResultSet.getString("gender");

		Date dob = myResultSet.getDate("dob");
		String nationality = myResultSet.getString("nationality");
		String classJoining = myResultSet.getString("classJoining");
		String house = myResultSet.getString("house");
		String dormitory = myResultSet.getString("dormitory");

		String religion = myResultSet.getString("religion");

		Date utilYear = myResultSet.getDate("year");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(utilYear);

		return null;
	}

	@Override
	public List<AdmissionStudentPersonalDetails> getAllAdmittedStudents() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAdmittedStudent(AdmissionStudentPersonalDetails details) throws SQLException {
		// TODO Auto-generated method stub

	}

}
