package clarion.students.leader.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import clarion.student.leaders.core.PrefectPost;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import school.ui.finances.CashBookController;

public class PrefectPostDAO {

	

	Connection myConnection = null;

	public PrefectPostDAO() {
		myConnection =  CashBookController.getConnection();
	}

	public void removePrefectPost(String post) throws SQLException {
		PreparedStatement myPreparedStatement = null;
		try {
			myPreparedStatement = myConnection.prepareStatement("delete from prefect_post where post='" + post + "'");

			// myPreparedStatement.setString(1, post);

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

	public void removePrefect(String idNumber) throws SQLException {
		PreparedStatement myPreparedStatement = null;
		try {
			myPreparedStatement = myConnection
					.prepareStatement("delete from prefects where id_number='" + idNumber + "'");

			// myPreparedStatement.setString(1, post);

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

	public void addPrefectPost(PrefectPost post) throws SQLException {
		PreparedStatement myPreparedStatement = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("insert into prefect_post(id, post) values(?,?)");

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

	public ObservableList<String> getPrefectPosts() throws SQLException {
		ObservableList<String> posts = FXCollections.observableArrayList();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from prefect_post");

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

	public Object getOnePrefectPost(int id) throws SQLException {
		Object posts = null;
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from prefect_post where id='" + id + "'");

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

	public PrefectPost getOnePrefectPostID(String post) throws SQLException {
		PrefectPost posts = null;
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from prefect_post where post='" + post + "'");

			myResultSet = myPreparedStatement.executeQuery();

			if (myResultSet.next()) {
				int iD = myResultSet.getInt("id");
				String posT = myResultSet.getString("post");
				posts = new PrefectPost(iD, posT);
			}
			return posts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return posts;
	}

	public void editPrefectPost(PrefectPost post, int id) throws SQLException {
		PreparedStatement myPreparedStatement = null;
		try {
			myPreparedStatement = myConnection.prepareStatement("update prefect_post set post=? where id='" + id + "'");
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
