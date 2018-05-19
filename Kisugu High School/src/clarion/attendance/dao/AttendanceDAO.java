package clarion.attendance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clarion.attendance.core.AttendanceLessons;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import school.ui.finances.CashBookController;

public class AttendanceDAO {

	
	
	Connection myConnection = null;
	
	public AttendanceDAO() throws SQLException {
		myConnection = CashBookController.getConnection();
	}
	
	public void addAttendance(AttendanceLessons lessons) throws SQLException{
		PreparedStatement myPreparedStatement = null;
		
		try {
			myPreparedStatement = myConnection.prepareStatement("insert into attendance_lesson"
					+ "(id,"
					+ " date, "
					+ "teacher_name, "
					
					
					+ "teacher_class,"
					+ " teacher_subject,"
					+ "lesson_time,"
					+ ""
					+ " term"
					+ ")"
					+ ""
					+ "values"
					+ "(?,?,"
					+ "?,?,"
					+ "?,"
					+ "?,?)");
			
//			Date utilDate = lessons.getDate();
//			java.sql.Date sqlDate = null;
//			if (utilDate != null) {
//				sqlDate = new java.sql.Date(utilDate.getTime());
//			}
			
			
			myPreparedStatement.setInt(1, lessons.getId());
			myPreparedStatement.setString(2, lessons.getDate());
			myPreparedStatement.setString(3, lessons.getTeacherName());
			myPreparedStatement.setString(4, lessons.getTeacherClass());
			myPreparedStatement.setString(5, lessons.getTeacherSubject());
			myPreparedStatement.setString(6, lessons.getLessonTime());
			myPreparedStatement.setString(7, lessons.getTerm());
			
			myPreparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void editAttendance(AttendanceLessons lessons, int id) throws SQLException{
		PreparedStatement myPreparedStatement = null;
		
		try {
			myPreparedStatement = myConnection.prepareStatement("update attendance_lesson"

					+ " set date=?, "
					+ "teacher_name=?, "
					
					
					+ "teacher_class=?,"
					+ " teacher_subject=?,"
					+ "lesson_time=?,"
					+ ""
					+ " term=?"
					+ " where id='"+id+"'");
			
			
			
			
			myPreparedStatement.setString(1, lessons.getDate());
			myPreparedStatement.setString(2, lessons.getTeacherName());
			myPreparedStatement.setString(3, lessons.getTeacherClass());
			myPreparedStatement.setString(4, lessons.getTeacherSubject());
			myPreparedStatement.setString(5, lessons.getLessonTime());
			myPreparedStatement.setString(6, lessons.getTerm());
			
//			myPreparedStatement.setInt(7, id);
			
			myPreparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public ObservableList<AttendanceLessons> getAllAttendanceLesson() throws SQLException{
		ObservableList<AttendanceLessons> lessons = FXCollections.observableArrayList();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;
		int i = 0;
		try {
			myPreparedStatement = myConnection.prepareStatement(""
					+ "select "
					+ "id, date, teacher_name,"
					+ ""
					+ "teacher_class, teacher_subject, lesson_time,"
					+ ""
					+ "term"
					+ " from attendance_lesson");
			
			myResultSet = myPreparedStatement.executeQuery();
			
			while(myResultSet.next()){
				int id = myResultSet.getInt("id");
				String date = myResultSet.getString("date");
				String name = myResultSet.getString("teacher_name");
				
				String teacherClass = myResultSet.getString("teacher_class");
				String teacherSubject = myResultSet.getString("teacher_subject");
				String lessonTime = myResultSet.getString("lesson_time");
				
				String term = myResultSet.getString("term");
				
				
				
				
				
				AttendanceLessons attendanceLessons = null;
				attendanceLessons = new AttendanceLessons(id, i++, date, name, teacherClass, teacherSubject, lessonTime, term);
				
				lessons.add(attendanceLessons);
				
			}
			
			return lessons;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lessons;
	}
	
	
	public ObservableList<AttendanceLessons> SearchTeacherName(String name) throws SQLException{
		ObservableList<AttendanceLessons> lessons = FXCollections.observableArrayList();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;
		
		try {
			myPreparedStatement = myConnection.prepareStatement("select "
					+ "id, date, "
					+ "teacher_name,"
					+ "teacher_class, teacher_subject, lesson_time,"
					+ ""
					+ "term"
					+ " from attendance_lesson where teacher_name LIKE '%" +name+"%'");
			myResultSet = myPreparedStatement.executeQuery();
			
			while (myResultSet.next()) {
				int id = myResultSet.getInt("id");
				String date = myResultSet.getString("date");
				String teacherName = myResultSet.getString("teacher_name");
				
				String teacherClass = myResultSet.getString("teacher_class");
				String teacherSubject = myResultSet.getString("teacher_subject");
				String lessonTime = myResultSet.getString("lesson_time");
				
				String term = myResultSet.getString("term");
				
				AttendanceLessons attendanceLessons = null;
				attendanceLessons = new AttendanceLessons(id, date, teacherName, teacherClass, teacherSubject, lessonTime, term);
				
				lessons.add(attendanceLessons);
				
			}
			
			return lessons;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lessons;
	}
	

	public ObservableList<AttendanceLessons> SearchTeacherClass(String clas) throws SQLException{
		ObservableList<AttendanceLessons> lessons = FXCollections.observableArrayList();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;
		
		try {
			myPreparedStatement = myConnection.prepareStatement("select "
					+ "id, date, "
					+ "teacher_name,"
					+ "teacher_class, teacher_subject, lesson_time,"
					+ ""
					+ "term"
					+ " from attendance_lesson where teacher_class LIKE '%" +clas+"%'");
			myResultSet = myPreparedStatement.executeQuery();
			
			while (myResultSet.next()) {
				int id = myResultSet.getInt("id");
				String date = myResultSet.getString("date");
				String teacherName = myResultSet.getString("teacher_name");
				
				String teacherClass = myResultSet.getString("teacher_class");
				String teacherSubject = myResultSet.getString("teacher_subject");
				String lessonTime = myResultSet.getString("lesson_time");
				
				String term = myResultSet.getString("term");
				
				AttendanceLessons attendanceLessons = null;
				attendanceLessons = new AttendanceLessons(id, date, teacherName, teacherClass, teacherSubject, lessonTime, term);
				
				lessons.add(attendanceLessons);
				
			}
			
			return lessons;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lessons;
	}

	
	public ObservableList<AttendanceLessons> SearchTeacherSubject(String subject) throws SQLException{
		ObservableList<AttendanceLessons> lessons = FXCollections.observableArrayList();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;
		
		try {
			myPreparedStatement = myConnection.prepareStatement("select "
					+ "id, date, "
					+ "teacher_name,"
					+ "teacher_class, teacher_subject, lesson_time,"
					+ ""
					+ "term"
					+ " from attendance_lesson where teacher_subject LIKE '%" +subject+"%'");
			myResultSet = myPreparedStatement.executeQuery();
			
			while (myResultSet.next()) {
				int id = myResultSet.getInt("id");
				String date = myResultSet.getString("date");
				String teacherName = myResultSet.getString("teacher_name");
				
				String teacherClass = myResultSet.getString("teacher_class");
				String teacherSubject = myResultSet.getString("teacher_subject");
				String lessonTime = myResultSet.getString("lesson_time");
				
				String term = myResultSet.getString("term");
				
				AttendanceLessons attendanceLessons = null;
				attendanceLessons = new AttendanceLessons(id, date, teacherName, teacherClass, teacherSubject, lessonTime, term);
				
				lessons.add(attendanceLessons);
				
			}
			
			return lessons;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lessons;
	}


	
	public ObservableList<AttendanceLessons> SearchTeacherTime(String time) throws SQLException{
		ObservableList<AttendanceLessons> lessons = FXCollections.observableArrayList();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;
		
		try {
			myPreparedStatement = myConnection.prepareStatement("select "
					+ "id, date, "
					+ "teacher_name,"
					+ "teacher_class, teacher_subject, lesson_time,"
					+ ""
					+ "term"
					+ " from attendance_lesson where lesson_time LIKE '%" +time+"%'");
			myResultSet = myPreparedStatement.executeQuery();
			
			while (myResultSet.next()) {
				int id = myResultSet.getInt("id");
				String date = myResultSet.getString("date");
				String teacherName = myResultSet.getString("teacher_name");
				
				String teacherClass = myResultSet.getString("teacher_class");
				String teacherSubject = myResultSet.getString("teacher_subject");
				String lessonTime = myResultSet.getString("lesson_time");
				
				String term = myResultSet.getString("term");
				
				AttendanceLessons attendanceLessons = null;
				attendanceLessons = new AttendanceLessons(id, date, teacherName, teacherClass, teacherSubject, lessonTime, term);
				
				lessons.add(attendanceLessons);
				
			}
			
			return lessons;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lessons;
	}

	
	public ObservableList<AttendanceLessons> SearchTeacherTerm(String termTemp) throws SQLException{
		ObservableList<AttendanceLessons> lessons = FXCollections.observableArrayList();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;
		
		try {
			myPreparedStatement = myConnection.prepareStatement("select "
					+ "id, date, "
					+ "teacher_name,"
					+ "teacher_class, teacher_subject, lesson_time,"
					+ ""
					+ "term"
					+ " from attendance_lesson where term LIKE '%" +termTemp+"%'");
			myResultSet = myPreparedStatement.executeQuery();
			
			while (myResultSet.next()) {
				int id = myResultSet.getInt("id");
				String date = myResultSet.getString("date");
				String teacherName = myResultSet.getString("teacher_name");
				
				String teacherClass = myResultSet.getString("teacher_class");
				String teacherSubject = myResultSet.getString("teacher_subject");
				String lessonTime = myResultSet.getString("lesson_time");
				
				String term = myResultSet.getString("term");
				
				AttendanceLessons attendanceLessons = null;
				attendanceLessons = new AttendanceLessons(id, date, teacherName, teacherClass, teacherSubject, lessonTime, term);
				
				lessons.add(attendanceLessons);
				
			}
			
			return lessons;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lessons;
	}



	
	
//	public void yearTest() throws SQLException {
//		PreparedStatement myPreparedStatement = null;
//
//		try {
//			myPreparedStatement = myConnection.prepareStatement("insert into year_test(year) " + "values(?)");
//
//			myPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
//			
//			myPreparedStatement.executeUpdate();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//
//	public List<String> getAll() throws SQLException{
//		PreparedStatement myPreparedStatement= null;
//		ResultSet myResultSet = null;
//		List<String> strings = new ArrayList<>();
//		try {
//			myPreparedStatement = myConnection.prepareStatement("select year(year) from year_test");
//			myResultSet = myPreparedStatement.executeQuery();
//			while(myResultSet.next()){
//				String year = myResultSet.getString("year(year)");
//				strings.add(year);
//			}
//			return strings;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return strings;
//		
//	}
	
//	public static void main(String[] args) throws SQLException {
//		AttendanceDAO dao = new AttendanceDAO();
//		System.out.println(dao.getAll());
//	}
	
}



