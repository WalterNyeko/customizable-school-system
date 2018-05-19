package school.ui.library;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import school.model.library.GeneralOverViewOfBooks;

public class GeneralOverViewOfBooksTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int OBJECT_COLUMN = -1;
	private static final int DATE_IN_COLUMN = 0;
	private static final int BOOK_TITLE_COLUMN = 1;
	private static final int SUBJECT_COLUMN = 2;
	private static final int AUTHOR_COLUMN = 3;
	private static final int PUBLISHER_COLUMN = 4;
	private static final int SHELF_NAME_COLUMN = 5;
	private static final int QUANTITY_COLUMN = 6;
	private static final int DONOR_COLUMN = 7;

	String[] columNames = { "Date In", "Book Title", "Subject", "Author", "Publisher", "Shelf Name", "Quantity",
			"Donor" };

	private List<GeneralOverViewOfBooks> books;

	public GeneralOverViewOfBooksTableModel(List<GeneralOverViewOfBooks> theBooks) {
		books = theBooks;
	}

	@Override
	public int getRowCount() {
		return books.size();
	}

	@Override
	public int getColumnCount() {
		return columNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		GeneralOverViewOfBooks ofBooks = books.get(rowIndex);

		switch (columnIndex) {
		case DATE_IN_COLUMN:
			return ofBooks.getDateIn();
		case BOOK_TITLE_COLUMN:
			return ofBooks.getBookTitle();
		case SUBJECT_COLUMN:
			return ofBooks.getSubject();
		case AUTHOR_COLUMN:
			return ofBooks.getAuthor();
		case PUBLISHER_COLUMN:
			return ofBooks.getPublisher();
		case SHELF_NAME_COLUMN:
			return ofBooks.getShelfName();
		case QUANTITY_COLUMN:
			return ofBooks.getQuantity();
		case DONOR_COLUMN:
			return ofBooks.getDonor();
		case OBJECT_COLUMN:
			return ofBooks;
		default:
			return null;
		}

		// return
	}

	@Override
	public Class<?> getColumnClass(int c) {
		Object cell = getValueAt(0, c);

		if (cell != null) {
			return cell.getClass();
		} else {
			return String.class;
		}
	}

}
