package school.ui.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class LoginHelperClass {

	private static Connection con = null;
	private static boolean hasData = false;

	public ResultSet displayDataBaseLoginCredentials() throws SQLException, ClassNotFoundException {

		if (con == null) {
			getConnection();
		}

		Statement statement = con.createStatement();

		ResultSet result = statement.executeQuery("SELECT dbhost,dbusername,dbpassword from dbUsers ORDER BY id ASC LIMIT 1");

		return result;

	}
	
	
	public ResultSet displayDataBaseLoginCredentialsForInternet() throws SQLException, ClassNotFoundException {

		if (con == null) {
			getConnection();
		}

		Statement statement = con.createStatement();

		ResultSet result = statement.executeQuery("SELECT dbhost,dbusername,dbpassword from dbUsers ORDER BY id DESC LIMIT 1");

		return result;

	}

	public ResultSet displayRememberedUsers() throws SQLException, ClassNotFoundException {

		if (con == null) {
			getConnection();
		}

		Statement statement = con.createStatement();

		ResultSet result = statement.executeQuery("SELECT username,password from RememberMe");

		return result;

	}

	void getConnection() throws SQLException, ClassNotFoundException {

		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:SQLiteDBForMYSQLDBCredentialsKisugu.db");

		initialise();
	}

	private void initialise() throws SQLException {

		if (!hasData) {

			hasData = true;
			
			/////////////////////Checking Table dbUsers
			Statement statement = null;
			ResultSet resultOfExistenceOfTable = null;
			statement = con.createStatement();
			resultOfExistenceOfTable = statement
					.executeQuery("SELECT name from sqlite_master WHERE type='table' AND name='dbUsers'");
			
			/////////////////////Checking table Remembered users
			ResultSet resultOfExistenceOfTableRememberMe=null;
			Statement statementRem=null;
			statementRem=con.createStatement();
			resultOfExistenceOfTableRememberMe=statementRem.executeQuery("SELECT name from sqlite_master WHERE type='table' AND name='RememberMe'");
			
			
			while (!resultOfExistenceOfTable.next()) {
				System.out.println("Constructing a table for you");

				Statement statementForCreatingTable = con.createStatement();
				statementForCreatingTable.execute(
						"CREATE TABLE dbUsers(id int(50) primary key,dbhost varchar(200),dbusername varchar(200), dbpassword varchar(200))");

				System.out.println("Table Created Successfully");

				PreparedStatement state = con
						.prepareStatement("INSERT INTO dbUsers(dbhost,dbusername,dbpassword) values(?,?,?)");
				state.setString(1, "jdbc:mysql://localhost:3306/kisugu?autoReconnect=true&useSSL=false");
				state.setString(2, "root");
				state.setString(3, "mysql");
				state.execute();


				System.out.println("User Added Successfully");

			}

			while (!resultOfExistenceOfTableRememberMe.next()) {
				Statement statementForCreatingTableRememberMe = con.createStatement();
				statementForCreatingTableRememberMe.execute(
						"CREATE TABLE RememberMe(id int(50) primary key,username varchar(200), password varchar(200))");

				System.out.println("Table Created Successfully");

				PreparedStatement stateRem = con
						.prepareStatement("INSERT INTO RememberMe(username,password) values(?,?)");
				stateRem.setString(1, "Walter@SoftEarth");
				stateRem.setString(2, "walter123");
				stateRem.execute();

				System.out.println("Default Remembered User Added Successfully");

			}

		}
	}
	
	public void updateRememberedUser(String username, String password) throws ClassNotFoundException, SQLException {
		
		
		if (con == null) {
			getConnection();
		}

		PreparedStatement stateRem = con
				.prepareStatement("UPDATE RememberMe SET username=?, password=? WHERE id is null");
		stateRem.setString(1, username);
		stateRem.setString(2, password);
		stateRem.execute();
		
	}

}
