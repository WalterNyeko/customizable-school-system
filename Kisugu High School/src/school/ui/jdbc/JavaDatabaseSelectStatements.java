package school.ui.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.table.DefaultTableModel;

import school.ui.finances.CashBookController;
import school.ui.main.MainPage;
import school.ui.timetable.ViewTimeTable;

public class JavaDatabaseSelectStatements {
	MainPage mainPage;
	DefaultTableModel dmMTC;
	ViewTimeTable viewTimeTable;

	Connection conn = null;

	public JavaDatabaseSelectStatements() {
		try {
			conn = CashBookController.getConnection();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public DefaultTableModel DisplayAvailableRequirements() {

		Object[] columns = { "Class", "Req Name", "Standard Amount", "Req ID" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select class_requirements,requirement_name,standard_quantity,ID from requirementslist";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String Class = rs.getString(1);
				String NAME = rs.getString(2);
				String Standard = rs.getString(3);
				String IDReq = rs.getString(4);

				dm.addRow(new String[] { Class, NAME, Standard, IDReq });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayReportedStudents() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Age", "Address", "Dormitory", "House",
				"Term", "Year" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select tableadmissionstudentdetails.id_number,CONCAT(tableadmissionstudentdetails.first_name, ' ', tableadmissionstudentdetails.middle_name, ' ', tableadmissionstudentdetails.last_name)"
				+ " as Name,requirements_student.student_class,tableadmissionstudentdetails.gender,TIMESTAMPDIFF(YEAR,tableadmissionstudentdetails.dob,Now()),tableadmissionstudentdetails.parent_address,"
				+ "tableadmissionstudentdetails.dormitory,tableadmissionstudentdetails.house,requirements_student.term,requirements_student.year from tableadmissionstudentdetails,requirements_student where "
				+ "tableadmissionstudentdetails.id_number=requirements_student.id_number group by requirements_student.id_number";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Age = rs.getString(5);
				String Address = rs.getString(6);
				String Dormitory = rs.getString(7);
				String House = rs.getString(8);
				String Term = rs.getString(9);
				String year = rs.getString(10);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Age, Address, Dormitory, House, Term, year });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (Exception e2) {

				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayAllStudents() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory", "House" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select id_number,CONCAT(first_name, ' ', middle_name, ' ', last_name) as Name,class_joining,gender,TIMESTAMPDIFF(YEAR,dob,Now()),dormitory,house from tableadmissionstudentdetails";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Age = rs.getString(5);
				String Dormitory = rs.getString(6);
				String House = rs.getString(7);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Age, Dormitory, House });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplaySeniorTwoStudents() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory", "House" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select id_number,CONCAT(first_name, ' ', middle_name, ' ', last_name) as Name,class_joining,gender,TIMESTAMPDIFF(YEAR,dob,Now()),dormitory,house from tableadmissionstudentdetails where class_joining='S.2'";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Age = rs.getString(5);
				String Dormitory = rs.getString(6);
				String House = rs.getString(7);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Age, Dormitory, House });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplaySeniorThreeStudents() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory", "House" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select id_number,CONCAT(first_name, ' ', middle_name, ' ', last_name) as Name,class_joining,gender,TIMESTAMPDIFF(YEAR,dob,Now()),dormitory,house from tableadmissionstudentdetails where class_joining='S.3'";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Age = rs.getString(5);
				String Dormitory = rs.getString(6);
				String House = rs.getString(7);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Age, Dormitory, House });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplaySeniorFourStudents() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory", "House" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select id_number,CONCAT(first_name, ' ', middle_name, ' ', last_name) as Name,class_joining,gender,TIMESTAMPDIFF(YEAR,dob,Now()),dormitory,house from tableadmissionstudentdetails where class_joining='S.4'";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Age = rs.getString(5);
				String Dormitory = rs.getString(6);
				String House = rs.getString(7);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Age, Dormitory, House });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplaySeniorFiveArtsStudents() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory", "House" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select id_number,CONCAT(first_name, ' ', middle_name, ' ', last_name) as Name,class_joining,gender,TIMESTAMPDIFF(YEAR,dob,Now()),dormitory,house from tableadmissionstudentdetails where class_joining='S.5Arts'";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Age = rs.getString(5);
				String Dormitory = rs.getString(6);
				String House = rs.getString(7);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Age, Dormitory, House });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplaySeniorFiveSciStudents() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory", "House" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select id_number,CONCAT(first_name, ' ', middle_name, ' ', last_name) as Name,class_joining,gender,TIMESTAMPDIFF(YEAR,dob,Now()),dormitory,house from tableadmissionstudentdetails where class_joining='S.5Sci'";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Age = rs.getString(5);
				String Dormitory = rs.getString(6);
				String House = rs.getString(7);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Age, Dormitory, House });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplaySeniorOneStudents() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Age", "Dormitory", "House" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);
		PreparedStatement pst = null;
		ResultSet rs = null;

		String sql = "select id_number,CONCAT(first_name, ' ', middle_name, ' ', last_name) as Name,class_joining,gender,TIMESTAMPDIFF(YEAR,dob,Now()),dormitory,house from tableadmissionstudentdetails where class_joining='S.1'";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Age = rs.getString(5);
				String Dormitory = rs.getString(6);
				String House = rs.getString(7);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Age, Dormitory, House });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplaySuspendedStudents() {

		Object[] columns = { "Student Code", "Student Name", "Class", "Reason", "Date Of Suspension", "Duration",
				"Date of Return" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select suspension_record.id_number,all_students_and_parents.student_name"
				+ " as Name,all_students_and_parents.student_class,suspension_record.reason,suspension_record.date,TIMESTAMPDIFF(DAY,suspension_record.date,suspension_record.date_of_return)as duration,suspension_record.date_of_return from suspension_record,all_students_and_parents where "
				+ "all_students_and_parents.payment_code=suspension_record.id_number";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Reason = rs.getString(4);
				String DateOfSuspension = rs.getString(5);
				String Duration = rs.getString(6);
				String DateOfReturn = rs.getString(7);

				dm.addRow(new String[] { ID, NAME, Class, Reason, DateOfSuspension, Duration + " days", DateOfReturn });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayDismissedStudents() {

		Object[] columns = { "ID Number", "Student Name", "Joined In? (Class)", "Age", "Reason", "Date Of Dismissal" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select dismissal_record.id_number,students_info.student_name,students_info.student_class,"
				+ "TIMESTAMPDIFF(YEAR,students_info.date_of_birth,NOW()),dismissal_record.reason,dismissal_record.date"
				+ " from dismissal_record,students_info where students_info.payment_code=dismissal_record.id_number group by students_info.payment_code";

		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Reason = rs.getString(5);
				String DateOfDismissal = rs.getString(6);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Reason, DateOfDismissal });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayVisitedStudents() {

		Object[] columns = { "Student ID Number", "Student Name", "Vistor's Name", "Contact", "Address",
				"Student Class" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;

		Calendar cal = Calendar.getInstance();

		String answer = "" + cal.getTime();
		String date = answer.substring(answer.length() - 4);

		String sql = "select student_id_number,student_name,visitor_name,visitor_contact,visitor_address,student_class from visitation where date LIKE '%"
				+ date + "%' and term=";

		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Reason = rs.getString(5);
				String DateOfDismissal = rs.getString(6);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Reason, DateOfDismissal });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayPrefects() {

		Object[] columns = { "ID Number", "Prefect's Name", "Class", "Sex", "Post", "Dormitory", "Regime Year" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select prefects.id_number, CONCAT(prefects.first_name,' ',prefects.middle_name,' ',prefects.last_name), "
				+ "prefects.student_class,prefects.gender,prefects.post,tableadmissionstudentdetails.dormitory,prefects.year_of_regime "
				+ "from tableadmissionstudentdetails,prefects where tableadmissionstudentdetails.id_number=prefects.id_number ";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Post = rs.getString(5);
				String Dormitory = rs.getString(6);
				String year = rs.getString(7);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Post, Dormitory, year });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayUNSA() {

		Object[] columns = { "ID Number", "Prefect's Name", "Class", "Sex", "Post", "Dormitory", "Regime Year" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);
		PreparedStatement pst = null;
		ResultSet rs = null;

		String sql = "select unsa.id_number, CONCAT(unsa.first_name,' ',unsa.middle_name,' ',unsa.last_name), "
				+ "unsa.student_class,unsa.gender,unsa.post,tableadmissionstudentdetails.dormitory,unsa.year_of_regime "
				+ "from tableadmissionstudentdetails,unsa where tableadmissionstudentdetails.id_number=unsa.id_number ";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Post = rs.getString(5);
				String Dormitory = rs.getString(6);
				String year = rs.getString(7);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Post, Dormitory, year });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayDebators() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Position" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select debators.id_number, debators.student_name, "
				+ "debators.student_class,debators.gender,debators.position "
				+ "from tableadmissionstudentdetails,debators where tableadmissionstudentdetails.id_number=debators.id_number ";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Post = rs.getString(5);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Post });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayWildLife() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Position" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select wild_life_club.id_number, wild_life_club.student_name, "
				+ "wild_life_club.student_class,wild_life_club.gender,wild_life_club.position "
				+ "from tableadmissionstudentdetails,wild_life_club where tableadmissionstudentdetails.id_number=wild_life_club.id_number ";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Post = rs.getString(5);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Post });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayInteractClub() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Position" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select interact_club.id_number, interact_club.student_name, "
				+ "interact_club.student_class,interact_club.gender,interact_club.position "
				+ "from tableadmissionstudentdetails,interact_club where tableadmissionstudentdetails.id_number=interact_club.id_number ";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Post = rs.getString(5);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Post });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public DefaultTableModel DisplayPatriotismStudents() {

		DefaultTableModel model = new DefaultTableModel();
		Object[] heading = { "ID Number", "Student Name", "Class", "Sex", "Position" };
		model.setColumnIdentifiers(heading);

		String url = "jdbc:mysql://localhost:3306/school";
		String user = "root";
		String pass = "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select patriotism_club.id_number, patriotism_club.student_name, "
				+ "patriotism_club.student_class,patriotism_club.gender,patriotism_club.position "
				+ "from tableadmissionstudentdetails,patriotism_club where tableadmissionstudentdetails.id_number=patriotism_club.id_number ";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Dormitory = rs.getString(5);

				model.addRow(new Object[] { ID, NAME, Class, Gender, Dormitory });

			}

			return model;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayGamesAdnSports() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Position" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select games_and_sports.id_number, games_and_sports.student_name, "
				+ "games_and_sports.student_class,games_and_sports.gender,games_and_sports.position "
				+ "from tableadmissionstudentdetails,games_and_sports where tableadmissionstudentdetails.id_number=games_and_sports.id_number ";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Post = rs.getString(5);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Post });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayScriptureUnion() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Position" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select scripture_union.id_number, scripture_union.student_name, "
				+ "scripture_union.student_class,scripture_union.gender,scripture_union.position "
				+ "from tableadmissionstudentdetails,scripture_union where tableadmissionstudentdetails.id_number=scripture_union.id_number ";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Post = rs.getString(5);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Post });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayWritersClub() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Position" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select writersclub.id_number, writersclub.student_name, "
				+ "writersclub.student_class,writersclub.gender,writersclub.position "
				+ "from tableadmissionstudentdetails,writersclub where tableadmissionstudentdetails.id_number=writersclub.id_number ";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Post = rs.getString(5);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Post });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayScoutsAndGuides() {

		Object[] columns = { "ID Number", "Student Name", "Class", "Sex", "Position" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select scouts_and_guides.id_number, scouts_and_guides.student_name, "
				+ "scouts_and_guides.student_class,scouts_and_guides.gender,scouts_and_guides.position "
				+ "from tableadmissionstudentdetails,scouts_and_guides where tableadmissionstudentdetails.id_number=scouts_and_guides.id_number ";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Post = rs.getString(5);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Post });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayTeachingStaffs() {

		Object[] columns = { "ID Number", "Staff Name", "Email", "TIN Number", "Qualification", "Contact", "Gender",
				"Payroll Status", "Responsibility" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select table_teaching_staffs.id_number,table_teaching_staffs.staff_name,"
				+ "table_teaching_staffs.staff_email,table_teaching_staffs.tin_number,table_teaching_staffs.nssf_number,table_teaching_staffs.staff_contact,"
				+ "table_teaching_staffs.staff_gender,table_teaching_staffs.staff_address,"
				+ "GROUP_CONCAT(teaching_staffs.responsibilty) from table_teaching_staffs,"
				+ "teaching_staffs where table_teaching_staffs.id_number=teaching_staffs.staff_id group by teaching_staffs.staff_id";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String TIN = rs.getString(4);
				String NSSF = rs.getString(5);
				String Gender = rs.getString(6);
				String Age = rs.getString(7);
				String Dormitory = rs.getString(8);
				String House = rs.getString(9);

				dm.addRow(new String[] { ID, NAME, Class, TIN, NSSF, Gender, Age, Dormitory, House });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayNonTeachingStaffs() {

		Object[] columns = { "ID Number", "Staff Name", "Email", "TIN Number", "Post", "Contact", "Gender",
				"Payroll Status", "Responsibility" };

		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);

		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select table_non_teaching_staffs.id_number,table_non_teaching_staffs.staff_name,"
				+ "table_non_teaching_staffs.staff_email,table_non_teaching_staffs.tin_number,table_non_teaching_staffs.nssf_number,table_non_teaching_staffs.staff_contact,"
				+ "table_non_teaching_staffs.staff_gender,table_non_teaching_staffs.staff_address,"
				+ "GROUP_CONCAT(non_teaching_staff.responsibility) from table_non_teaching_staffs,"
				+ "non_teaching_staff where table_non_teaching_staffs.id_number=non_teaching_staff.staff_id group by non_teaching_staff.staff_id";
		try {
			conn = CashBookController.getConnection();
			// prepare statement
			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String TIN = rs.getString(4);
				String NSSF = rs.getString(5);
				String Gender = rs.getString(6);
				String Age = rs.getString(7);
				String Dormitory = rs.getString(8);
				String House = rs.getString(9);

				dm.addRow(new String[] { ID, NAME, Class, TIN, NSSF, Gender, Age, Dormitory, House });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayTeachersScheduleTimeTable() {

		Object[] columns = { "WEEK", "FROM DATE", "TO DATE", "TEACHERS ON DUTY", "SUPERVISOR" };
		DefaultTableModel dm = new DefaultTableModel();

		dm.setColumnIdentifiers(columns);
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select week_name,from_date,to_date,teachers_names,supervisor_name from teachers_schedule_time_table";
		try {

			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Age = rs.getString(5);

				dm.addRow(new String[] { ID, NAME, Class, Gender, Age });
			}

			return dm;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public DefaultTableModel DisplayMarkSheetS1() {

		Object[] columns = { "ID", "NAME", "CLASS", "BOT", "GRADE", "MOT", "GRADE", "EOT", "GRADE" };
		dmMTC = new DefaultTableModel();

		dmMTC.setColumnIdentifiers(columns);
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select DISTINCT mark_sheet_s1.id_number,mark_sheet_s1.student_name,mark_sheet_s1.student_class,bot,"
				+ "(CASE WHEN bot BETWEEN table_grading.grading_from and table_grading.grading_to THEN LEFT(table_grading.grade,2) ELSE 'Unknown' END),"
				+ "mark_sheet_s1.mot,mark_sheet_s1.mot_grade,mark_sheet_s1.eot,mark_sheet_s1.eot_grade,mark_sheet_s1.remarks"
				+ " from mark_sheet_s1,table_grading where mark_sheet_s1.subject=table_grading.subject and "
				+ "(CASE WHEN bot BETWEEN table_grading.grading_from and table_grading.grading_to THEN LEFT(table_grading.grade,2) END)!='Unknown'";
		try {

			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				String ID = rs.getString(1);
				String NAME = rs.getString(2);
				String Class = rs.getString(3);
				String Gender = rs.getString(4);
				String Age = rs.getString(5);
				String Class1 = rs.getString(6);
				String Gender1 = rs.getString(7);
				String Age1 = rs.getString(8);
				String Age2 = rs.getString(9);

				dmMTC.addRow(new String[] { ID, NAME, Class, Gender, Age, Class1, Gender1, Age1, Age2 });
			}

			return dmMTC;
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					pst.close();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
