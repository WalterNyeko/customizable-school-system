package school.controller.admission;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import school.model.admission.AdmissionStudentPersonalDetails;

public interface AdmissionStudentPersonalDetailsInterface {
	public void addStudentPersonalDetailsAtAdmission(AdmissionStudentPersonalDetails details) throws SQLException;

	public void editStudentPersonalDetailsAtAdmission(String idNumber) throws SQLException;

	public AdmissionStudentPersonalDetails convertToAdmittedStudent(ResultSet myResultSet) throws SQLException;

	public List<AdmissionStudentPersonalDetails> getAllAdmittedStudents() throws SQLException;

	public void deleteAdmittedStudent(AdmissionStudentPersonalDetails details) throws SQLException;
}
