package clarion.ui.dispensary;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import clarion.model.dispensary.DiagnosisAndTreatment;

public class DispensaryTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int OBJECT_COLUMN = -1;
	private static final int DATE_COLUMN = 0;
	private static final int CLASS_NO_COLUMN = 1;
	private static final int STUDENT_CLASS_COLUMN = 2;
	private static final int STUDENT_NAME_COLUMN = 3;
	private static final int MEDICAL_HOSTORY_COLUMN = 4;
	private static final int PROVISIONAL_DIAGNOSIS_COLUMN = 5;
	private static final int TREATMENT_TAKEN_COLUMN = 6;
	private static final int TIMESTAMP_COLUMN = 7;

	private String colNames[] = { "Date", "Student Code", "Class", "Name", "Medical History", "Provisional Diagnosis",
			"Treatment Taken", "Time" };

	private List<DiagnosisAndTreatment> treatments;

	public DispensaryTableModel(List<DiagnosisAndTreatment> theTreatments) {
		treatments = theTreatments;
	}

	@Override
	public int getRowCount() {
		return treatments.size();
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DiagnosisAndTreatment treatment = treatments.get(rowIndex);

		switch (columnIndex) {
		case DATE_COLUMN:
			return treatment.getDate();
		case CLASS_NO_COLUMN:
			return treatment.getClassNumber();
		case STUDENT_CLASS_COLUMN:
			return treatment.getStudentClass();
		case STUDENT_NAME_COLUMN:
			return treatment.getStudentName();
		case MEDICAL_HOSTORY_COLUMN:
			return treatment.getMedicalHistory();
		case PROVISIONAL_DIAGNOSIS_COLUMN:
			return treatment.getProvisionalDiagnosis();
		case TREATMENT_TAKEN_COLUMN:
			return treatment.getTreatmentTaken();
		case TIMESTAMP_COLUMN:
			return treatment.getTreatmentTimeStamp();
		case OBJECT_COLUMN:
			return treatment;
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
