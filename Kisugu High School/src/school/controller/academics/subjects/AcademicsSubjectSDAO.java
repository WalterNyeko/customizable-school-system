package school.controller.academics.subjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import school.controller.academics.interfaces.AcademicsSubjectDAOInterface;
import school.model.academics.subjects.AcademicsSubjects;
import school.ui.finances.CashBookController;

public class AcademicsSubjectSDAO implements AcademicsSubjectDAOInterface{


	Connection myConnection = null;

	public AcademicsSubjectSDAO() throws SQLException {
		myConnection =CashBookController.getConnection();
	}

	@Override
	public void addNewSubject(AcademicsSubjects subjects) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editSubject(AcademicsSubjects subjects) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myPreparedStatement = null;
		
		try {
			myPreparedStatement = myConnection.prepareStatement(
					"insert into academic_subjects"
					+ "(subject_name, subject_code, subject, subject_level, is_chosen)"
					+ "values(?,?,?,?)");
			
			myPreparedStatement.setString(1, subjects.getSubjectName());
			myPreparedStatement.setString(2, subjects.getSubjectCode());
			myPreparedStatement.setString(3, subjects.getSubjectLevel());
			myPreparedStatement.setBoolean(4, subjects.isChosen());
			
			myPreparedStatement.executeUpdate();
			
			myPreparedStatement.close();
			myConnection.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteSubject(AcademicsSubjects subjects) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AcademicsSubjects convertToSubject(ResultSet myResultSet) throws SQLException {
		// TODO Auto-generated method stub
		
		String name = myResultSet.getString("subject_name");
		String code = myResultSet.getString("subject_code");
		String level = myResultSet.getString("subject_level");
		boolean isChosen = myResultSet.getBoolean("is_chosen");
		
		AcademicsSubjects subjects = new AcademicsSubjects(name, code, level, isChosen);
		
		return subjects;
	}

	@Override
	public List<AcademicsSubjects> getAllSubjects() throws SQLException {
		// TODO Auto-generated method stub
		List<AcademicsSubjects> academicsSubjects = new ArrayList<>();

		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from academic_subjects");
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				AcademicsSubjects subjects = convertToSubject(myResultSet);
				academicsSubjects.add(subjects);
			}
			return academicsSubjects;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

}
