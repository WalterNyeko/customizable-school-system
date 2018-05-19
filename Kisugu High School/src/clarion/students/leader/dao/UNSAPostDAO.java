package clarion.students.leader.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import clarion.student.leaders.core.UNSAPost;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import school.ui.finances.CashBookController;

public class UNSAPostDAO {

	
	Connection myConnection = null;

	public UNSAPostDAO() throws SQLException {
		myConnection =  CashBookController.getConnection();
	}

	public void addUNSAPost(UNSAPost post) throws SQLException {
		PreparedStatement myPreparedStatement = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("insert into unsa_post(id, post) values(?,?)");

			myPreparedStatement.setInt(1, post.getId());
			myPreparedStatement.setString(2, post.getPrepfectPost());

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

	public ObservableList<String> getUNSAPosts() throws SQLException {
		ObservableList<String> posts = FXCollections.observableArrayList();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from unsa_post");

			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				// int id = myResultSet.getInt("id");
				String post = myResultSet.getString("post");

				posts.add(post);
			}
			return posts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return posts;
	}

	public Object getOneUNSAPost(int id) throws SQLException {
		Object posts = null;
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from unsa_post where id='" + id + "'");

			myResultSet = myPreparedStatement.executeQuery();

			if (myResultSet.next()) {
				int iD = myResultSet.getInt("id");
				String post = myResultSet.getString("post");

			}
			return posts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return posts;
	}

	public UNSAPost getOneUNSAPostID(String post) throws SQLException {
		UNSAPost posts = null;
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from unsa_post where post='" + post + "'");

			myResultSet = myPreparedStatement.executeQuery();

			if (myResultSet.next()) {
				int iD = myResultSet.getInt("id");
				String posT = myResultSet.getString("post");
				posts = new UNSAPost(iD, posT);
			}
			return posts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return posts;
	}

	public void editUNSAPost(UNSAPost post, int id) throws SQLException {
		PreparedStatement myPreparedStatement = null;
		try {
			myPreparedStatement = myConnection.prepareStatement("update unsa_post set post=? where id='" + id + "'");
			myPreparedStatement.setString(1, post.getPrepfectPost());
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
}
