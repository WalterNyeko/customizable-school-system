package school.controller.academics.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import school.model.academics.subjects.AcademicsSubjects;

public interface AcademicsSubjectDAOInterface {

	public void addNewSubject(AcademicsSubjects subjects) throws SQLException;

	public void editSubject(AcademicsSubjects subjects) throws SQLException;

	public void deleteSubject(AcademicsSubjects subjects) throws SQLException;

	public AcademicsSubjects convertToSubject(ResultSet myResultSet) throws SQLException;

	public List<AcademicsSubjects> getAllSubjects() throws SQLException;

}
