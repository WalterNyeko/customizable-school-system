package school.controller.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import school.model.library.RecordEachBookDetailModel;
import school.ui.finances.CashBookController;

public class IssueBooksController {

	
	Connection myConnection = null;

	public IssueBooksController() throws SQLException {
		myConnection = CashBookController.getConnection();
	}

	public RecordEachBookDetailModel convertToEachBook(ResultSet myResultSet) throws SQLException {
		int id = myResultSet.getInt("id");
		String bookId = myResultSet.getString("bookId");
		String bookTitle = myResultSet.getString("bookTitle");
		String subjectName = myResultSet.getString("subjectName");

		RecordEachBookDetailModel model = new RecordEachBookDetailModel(id, bookId, bookTitle, subjectName);
		return model;
	}

	public List<RecordEachBookDetailModel> getEachBookDetails() throws SQLException {
		List<RecordEachBookDetailModel> detailModels = new ArrayList<>();

		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from EachBookDetail");
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				RecordEachBookDetailModel detailModel = convertToEachBook(myResultSet);
				detailModels.add(detailModel);
			}
			return detailModels;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}
			if (myResultSet != null) {
				myResultSet.close();
			}
			if (myConnection != null) {
				myConnection.close();
			}
		}

		return detailModels;
	}

	public void addEachBookDetail(RecordEachBookDetailModel model) throws SQLException {
		PreparedStatement myPreparedStatement = null;
		try {
			myPreparedStatement = myConnection.prepareStatement(
					"insert into EachBookDetail(" + "id, bookId, bookTitle, subjectName) " + "values(?,?,?,?)");
			myPreparedStatement.setInt(1, model.getId());
			myPreparedStatement.setString(2, model.getBookId());
			myPreparedStatement.setString(3, model.getBookTitle());
			myPreparedStatement.setString(4, model.getSubjectName());

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

	public RecordEachBookDetailModel searchBook(String id) throws Exception {
		RecordEachBookDetailModel book = null;

		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection
					.prepareStatement("select subjectName from EachBookDetail where " + "bookId LIKE ?");

			// RecordEachBookDetailModel model = new
			// RecordEachBookDetailModel(id);
			// id = model.getBookId();

			myPreparedStatement.setString(1, id);

			myResultSet = myPreparedStatement.executeQuery();

			if (myResultSet.next()) {
				String sub = myResultSet.getString("subjectName");
				book = new RecordEachBookDetailModel(sub);

			}
			return book;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}
			if (myResultSet != null) {
				myResultSet.close();
			}
			if (myConnection != null) {
				myConnection.close();
			}
		}

		return book;

	}

	public static void main(String[] args) throws Exception {
		IssueBooksController controller = new IssueBooksController();
		System.out.println(controller.searchBook("MTC001"));
	}

}
