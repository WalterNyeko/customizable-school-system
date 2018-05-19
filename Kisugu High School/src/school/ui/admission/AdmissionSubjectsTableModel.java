package school.ui.admission;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import school.model.academics.subjects.AcademicsSubjects;

public class AdmissionSubjectsTableModel extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int SUBJECT_NAME = 0;
	private static final int CHOOSE = 1;

	private String[] colNames = { "Subject Name", "Choose" };

	private List<AcademicsSubjects> subjects;

	public AdmissionSubjectsTableModel(List<AcademicsSubjects> theSubject) {
		subjects = theSubject;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return subjects.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return colNames.length;
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return colNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

		AcademicsSubjects ac = subjects.get(rowIndex);

		switch (columnIndex) {
		case SUBJECT_NAME:
			return ac.getSubjectName();
		case CHOOSE:
			return ac.isChosen();
		case OBJECT_COL:
			return ac;
		default:
			return null;
		}

		// return null;
	}

	@Override
	// TODO Auto-generated method stub
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex < 1) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

		AcademicsSubjects ac = subjects.get(rowIndex);
		switch (columnIndex) {
		case SUBJECT_NAME:
			ac.setSubjectName((String) aValue);
		case CHOOSE:
			ac.setChosen((boolean) aValue);
		}
		
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	
	
	
	
	@Override
	public Class getColumnClass(int c) {
		Object cell = getValueAt(0, c);

		if (cell != null) {
			return cell.getClass();
		} else {
			return String.class;
		}
	}

}
