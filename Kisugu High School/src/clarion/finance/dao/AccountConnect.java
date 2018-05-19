package clarion.finance.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import school.ui.finances.CashBookController;

public class AccountConnect {

	

	static Connection myConn = null;
	static ResultSet myRs = null;
	static PreparedStatement myStmt = null;

	public static Connection getConnection() throws SQLException {
		myConn = CashBookController.getConnection();
		return myConn;
	}

	public static void closeAll() throws SQLException {

		if (myStmt != null) {
			myStmt.close();
		}

		if (myRs != null) {
			myRs.close();
		}

		if (myConn != null) {
			myConn.close();
		}
	}

	public static void closeStmtAndRs() throws SQLException {
		if (myStmt != null) {
			myStmt.close();
		}

		if (myRs != null) {
			myRs.close();
		}

	}

	public static void closeStmtOnly() throws SQLException {
		if (myStmt != null) {
			myStmt.close();
		}

	}

	public static void closeStmtAndConn() throws SQLException {
		if (myStmt != null) {
			myStmt.close();
		}

		if (myConn != null) {
			myConn.close();
		}

	}

}
