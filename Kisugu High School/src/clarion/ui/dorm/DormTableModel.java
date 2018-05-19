package clarion.ui.dorm;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import clarion.model.dorm.Dorm;

public class DormTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int OBJECT_COLUMN = -1;
	private static final int CLASS_NUMBER = 0;
	private static final int STUDENT_NAME = 1;
	private static final int STUDENT_CLASS = 2;
	private static final int YEAR = 3;

	private String[] colName = { "Class Number", "Student Name", "Student Class", "Year" };

	private List<Dorm> list;

	public DormTableModel(List<Dorm> dorms) {
		list = dorms;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return colName.length;
	}

	@Override
	public String getColumnName(int column) {
		return colName[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Dorm object = (Dorm) list.get(rowIndex);

		switch (columnIndex) {
		case CLASS_NUMBER:
			return object.getClassNumber();
		case STUDENT_NAME:
			return object.getStudentName();
		case STUDENT_CLASS:
			return object.getStudentClass();
		case YEAR:
			return object.getYear();
		case OBJECT_COLUMN:
			return object;
		}

		return null;
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
