package clarion.controller.dispensary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clarion.model.dispensary.DiagnosisAndTreatment;
import clarion.model.dispensary.ParentInfo;
import clarion.model.dispensary.StudentInfo;
import school.ui.finances.CashBookController;

public class DiagosisAndTreatmentController {

	

	Connection myConnection = null;

	public DiagosisAndTreatmentController() throws SQLException {
		myConnection = CashBookController.getConnection();
	}

	public void addNewDispensaryRecord(DiagnosisAndTreatment andTreatment) throws SQLException {
		PreparedStatement myPreparedStatement = null;

		try {
			myPreparedStatement = myConnection
					.prepareStatement("insert into diagnosis(" + "id, date, classNumber, studentClass, studentName, "
							+ "medicalHistory, provisionalDiagnosis, treatmentTaken, treatmentTimeStamp)" + " values("
							+ "?,?,?,?,?" + ",?,?,?,?)");

			Date utilDate = null;

			utilDate = andTreatment.getDate();

			java.sql.Date sqldate = null;

			if (utilDate != null) {
				sqldate = new java.sql.Date(utilDate.getTime());
			}

			myPreparedStatement.setInt(1, andTreatment.getId());
			myPreparedStatement.setDate(2, sqldate);
			myPreparedStatement.setString(3, andTreatment.getClassNumber());
			myPreparedStatement.setString(4, andTreatment.getStudentClass());
			myPreparedStatement.setString(5, andTreatment.getStudentName());
			myPreparedStatement.setString(6, andTreatment.getMedicalHistory());
			myPreparedStatement.setString(7, andTreatment.getProvisionalDiagnosis());
			myPreparedStatement.setString(8, andTreatment.getTreatmentTaken());
			myPreparedStatement.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

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

	private DiagnosisAndTreatment convertToDiagnosis(ResultSet myResultSet) throws SQLException {
		int id = myResultSet.getInt("id");
		Date date = myResultSet.getDate("date");
		String classNumber = myResultSet.getString("classNumber");
		String studentClass = myResultSet.getString("studentClass");
		String studentName = myResultSet.getString("studentName");
		String medHistory = myResultSet.getString("medicalHistory");
		String provisionalDiag = myResultSet.getString("provisionalDiagnosis");
		String treatmentTaken = myResultSet.getString("treatmentTaken");
		String timeStamp = myResultSet.getString("treatmentTimeStamp");

		DiagnosisAndTreatment treatment = new DiagnosisAndTreatment(id, date, classNumber, studentClass, studentName,
				medHistory, provisionalDiag, treatmentTaken, timeStamp);
		return treatment;
	}

	public List<DiagnosisAndTreatment> getDispensaryRecords() throws SQLException {
		List<DiagnosisAndTreatment> treatments = new ArrayList<>();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select id, date, classNumber, studentClass, "
					+ "studentName, medicalHistory, provisionalDiagnosis, treatmentTaken, "
					+ "treatmentTimeStamp from diagnosis");
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				DiagnosisAndTreatment treatment = convertToDiagnosis(myResultSet);
				treatments.add(treatment);
			}

			return treatments;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return treatments;
	}

	public List<DiagnosisAndTreatment> getDispensaryRecordsForParticularIDNumber(String classNo) throws SQLException {
		List<DiagnosisAndTreatment> treatments = new ArrayList<>();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select id, date, classNumber, studentClass, "
					+ "studentName, medicalHistory, provisionalDiagnosis, treatmentTaken, "
					+ "treatmentTimeStamp from diagnosis where classNumber LIKE '%" + classNo + "%'");
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				DiagnosisAndTreatment treatment = convertToDiagnosis(myResultSet);
				treatments.add(treatment);
			}

			return treatments;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return treatments;
	}

	public StudentInfo dispensaryClassNumberToFillFields(String classNo) throws SQLException {
		StudentInfo studentInfos = null;

		PreparedStatement myPreparedStatement = null;

		ResultSet myResultSet = null;

		try {

			myPreparedStatement = myConnection.prepareStatement("select * from students_info where payment_code = '"
					+ classNo + "'");
			
			myResultSet = myPreparedStatement.executeQuery();

			if (myResultSet.next()) {

				int id = myResultSet.getInt("id");
				String classNum = myResultSet.getString("class_number");
				String student_class = myResultSet.getString("student_class");
				String student_Name = myResultSet.getString("student_name");
				String dormitory = myResultSet.getString("dormitory");
				String year = myResultSet.getString("year");

				studentInfos = new StudentInfo(id, classNum, student_Name,student_class, dormitory, year);

			}

			return studentInfos;

		} finally {
			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}
			if (myResultSet != null) {
				myResultSet.close();
			}

		}

	}

	public ParentInfo parentInfofields(String classNo) {
		ParentInfo info = null;
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {

			myPreparedStatement = myConnection
					.prepareStatement("select * from parents_info where payment_code = '" + classNo + "'");
			myResultSet = myPreparedStatement.executeQuery();

			if (myResultSet.next()) {
				int id = myResultSet.getInt("id");
				String classNum = myResultSet.getString("class_number");
				String fName = myResultSet.getString("fathers_name");
				String mName = myResultSet.getString("mothers_name");
				String gName = myResultSet.getString("guardians_name");
				String sName = myResultSet.getString("sponsors_name");
				String fathersPhone = myResultSet.getString("fathers_phone");
				String mothersPhone = myResultSet.getString("mothers_phone");
				String sponsorsPhone = myResultSet.getString("sponsors_phone");
				String parentAddress = myResultSet.getString("parent_address");

				info = new ParentInfo(id, classNum, fName, mName, gName, sName, fathersPhone, mothersPhone,
						sponsorsPhone, parentAddress);

			}

			return info;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	public Connection checkConnection() throws SQLException {

		if (myConnection.isClosed()) {
			return myConnection;
		} else {
			return null;
		}

	}

	// public static void main(String[] args) throws SQLException {
	// DiagosisAndTreatmentController controller = new
	// DiagosisAndTreatmentController();
	// // System.out.println(controller.getDispensaryRecords());
	// //
	// System.out.println(controller.dispensaryClassNumberToFillFields("u17/002"));
	// System.out.println(controller.parentInfofields("u17/001"));
	// System.out.println(controller.getDispensaryRecords());
	// }

}
