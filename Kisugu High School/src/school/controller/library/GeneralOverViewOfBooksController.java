package school.controller.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import school.model.library.GeneralOverViewOfBooks;
import school.ui.finances.CashBookController;

public class GeneralOverViewOfBooksController {

	

	Connection myConnection = null;

	public GeneralOverViewOfBooksController() throws SQLException {
		myConnection = CashBookController.getConnection();
	}

	public void addNewBookBatch(GeneralOverViewOfBooks books) throws SQLException {
		PreparedStatement myPreparedStatement = null;

		try {

			myPreparedStatement = myConnection.prepareStatement("insert into GeneralOverViewOfBooks"
					+ "(id, dateIn, bookTitle, subject, author, publisher, shelfName, quantity, donor)"
					+ " values(?,?,?,?,?,?," + "?,?,?)");

			Date utilDate = books.getDateIn();
			java.sql.Date sqlDate = null;

			if (utilDate != null) {
				sqlDate = new java.sql.Date(utilDate.getTime());
			}

			myPreparedStatement.setInt(1, books.getId());
			myPreparedStatement.setDate(2, sqlDate);
			myPreparedStatement.setString(3, books.getBookTitle());
			myPreparedStatement.setString(4, books.getSubject());
			myPreparedStatement.setString(5, books.getAuthor());
			myPreparedStatement.setString(6, books.getPublisher());

			myPreparedStatement.setString(7, books.getShelfName());
			myPreparedStatement.setInt(8, books.getQuantity());
			myPreparedStatement.setString(9, books.getDonor());

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

	public GeneralOverViewOfBooks convertToBooks(ResultSet myResultSet) throws SQLException {
		int id = myResultSet.getInt("id");
		Date date = myResultSet.getDate("dateIn");
		String bookTitle = myResultSet.getString("bookTitle");
		String subject = myResultSet.getString("subject");
		String author = myResultSet.getString("author");
		String publisher = myResultSet.getString("publisher");
		String shelfName = myResultSet.getString("shelfName");
		int quantity = myResultSet.getInt("quantity");
		String donor = myResultSet.getString("donor");

		GeneralOverViewOfBooks books = new GeneralOverViewOfBooks(id, date, bookTitle, subject, author, publisher,
				shelfName, quantity, donor);

		return books;
	}

	public List<GeneralOverViewOfBooks> getAllBookBatches() throws SQLException {
		List<GeneralOverViewOfBooks> books = new ArrayList<>();
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("select * from GeneralOverViewOfBooks");
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				GeneralOverViewOfBooks viewOfBooks = convertToBooks(myResultSet);
				books.add(viewOfBooks);
			}
			return books;
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
		return books;
	}

	public void editBookBatches(GeneralOverViewOfBooks books) throws SQLException {
		PreparedStatement myPreparedStatement = null;

		try {
			myPreparedStatement = myConnection.prepareStatement("update GeneralOverViewOfBooks set"
					+ " dateIn=?, bookTitle=?, subject=?, author=?, publisher=?,"
					+ "shelfName=?, quantity=?, donor=? where id=?");

			Date utilDate = books.getDateIn();
			java.sql.Date sqlDate = null;
			if (utilDate != null) {
				sqlDate = new java.sql.Date(utilDate.getTime());
			}

			myPreparedStatement.setDate(1, sqlDate);
			myPreparedStatement.setString(2, books.getBookTitle());
			myPreparedStatement.setString(3, books.getSubject());
			myPreparedStatement.setString(4, books.getAuthor());
			myPreparedStatement.setString(5, books.getPublisher());

			myPreparedStatement.setString(6, books.getShelfName());
			myPreparedStatement.setInt(7, books.getQuantity());
			myPreparedStatement.setString(8, books.getDonor());
			myPreparedStatement.setInt(9, books.getId());

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
