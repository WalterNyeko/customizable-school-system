package school.ui.library;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import school.model.library.RecordEachBookDetailModel;

public class RecordEachBookDetailTableModel extends AbstractTableModel {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public static final int OBJECT_COLUMN = -1;
	private static final int BOOK_ID_COLUMN = 0;
	private static final int BOOK_TITLE_COLUMN = 1;
	private static final int SUBJECT_NAME_COLUMN = 2;

	private String[] columnNames = { "Book ID", "Book Title", "Subject Name" };

	private List<RecordEachBookDetailModel> books;

	public RecordEachBookDetailTableModel(List<RecordEachBookDetailModel> theBooks) {
		books = theBooks;
	}

	@Override
	public int getRowCount() {
		return books.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		RecordEachBookDetailModel model = books.get(rowIndex);

		switch (columnIndex) {
		case BOOK_ID_COLUMN:
			return model.getBookId();
		case BOOK_TITLE_COLUMN:
			return model.getBookTitle();
		case SUBJECT_NAME_COLUMN:
			return model.getSubjectName();
		case OBJECT_COLUMN:
			return model;
		default:
			return null;
		}

	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Object cell = getValueAt(0, columnIndex);
		if (cell != null) {
			return cell.getClass();
		} else {
			return String.class;
		}
	}

}
