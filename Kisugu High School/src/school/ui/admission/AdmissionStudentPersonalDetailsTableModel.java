package school.ui.admission;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import school.model.admission.AdmissionStudentPersonalDetails;

public class AdmissionStudentPersonalDetailsTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7083646109130601898L;

	public static final int OBJECT_COL = -1;
	private static final int ID_NUMBER = 0;
	private static final int STUDENT_NAME_COL = 1;
	private static final int CLASS_COL = 2;
	private static final int GENDER_COL = 3;
	private static final int AGE_COL = 4;
	private static final int ADDRESS_COL = 5;
	private static final int SPONSORED_COL = 6;
	private static final int DORM_COL = 7;
	private static final int HOUSE_COL = 8;
	private static final int PARENT_NAME_COL = 9;
	private static final int PARENT_CONTACT_COL = 10;
	private static final int YEAR_COL = 11;

	String[] heading3 = new String[] { "ID Number", "Student Name", "Class", "Sex", "Age", "Address", "Sponsored",
			"Dormitory", "House", "Parent Name", "Contact", "Year" };

	List<AdmissionStudentPersonalDetails> details = null;

	public AdmissionStudentPersonalDetailsTableModel(List<AdmissionStudentPersonalDetails> theDetails) {
		// TODO Auto-generated constructor stub
		details = theDetails;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return details.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return heading3.length;
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return heading3[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		AdmissionStudentPersonalDetails tempDetails = details.get(rowIndex);

		switch (columnIndex) {
		case ID_NUMBER:
			return tempDetails.getIdNumber();
		case STUDENT_NAME_COL:
			return tempDetails.getFirstName() + " " + tempDetails.getMiddleName() + " " + tempDetails.getLastName();
		case CLASS_COL:
			return tempDetails.getClassJoining();
		case GENDER_COL:
			return tempDetails.getGender();
		case AGE_COL:
			return tempDetails.getDob();
		case ADDRESS_COL:
			return tempDetails.getParentAddress();
		case SPONSORED_COL:
			return tempDetails.isSponsored();
		case DORM_COL:
			return tempDetails.getDormitory();
		case HOUSE_COL:
			return tempDetails.getHouse();
		case PARENT_NAME_COL:
			return tempDetails.getParentName();
		case PARENT_CONTACT_COL:
			return tempDetails.getParentPhone();
		case YEAR_COL:
			return tempDetails.getYearOfAdmission();
		case OBJECT_COL:
			return tempDetails;
		default:
			return null;
		}

	}

	@Override
	public Class<?> getColumnClass(int col) {
		// TODO Auto-generated method stub
		Object cell = getValueAt(0, col);

		if (cell != null) {
			return cell.getClass();
		} else {
			return String.class;
		}

	}

}
