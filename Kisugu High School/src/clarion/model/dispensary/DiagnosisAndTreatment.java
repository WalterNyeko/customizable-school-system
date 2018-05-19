package clarion.model.dispensary;

import java.util.Date;

public class DiagnosisAndTreatment {

	private int id;
	private Date date;
	private String classNumber;

	private String studentClass;
	private String studentName;
	private String medicalHistory;

	private String provisionalDiagnosis;
	private String treatmentTaken;
	private String treatmentTimeStamp;

	public DiagnosisAndTreatment(int id, Date date, String classNumber, String studentClass, String studentName,
			String medicalHistory, String provisionalDiagnosis, String treatmentTaken, String treatmentTimeStamp) {

		this.id = id;
		this.date = date;
		this.classNumber = classNumber;
		this.studentClass = studentClass;
		this.studentName = studentName;
		this.medicalHistory = medicalHistory;
		this.provisionalDiagnosis = provisionalDiagnosis;
		this.treatmentTaken = treatmentTaken;
		this.treatmentTimeStamp = treatmentTimeStamp;

	}

	public DiagnosisAndTreatment(Date date, String classNumber, String studentClass, String studentName,
			String medicalHistory, String provisionalDiagnosis, String treatmentTaken) {

		this.date = date;
		this.classNumber = classNumber;
		this.studentClass = studentClass;
		this.studentName = studentName;
		this.medicalHistory = medicalHistory;
		this.provisionalDiagnosis = provisionalDiagnosis;
		this.treatmentTaken = treatmentTaken;

	}

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getClassNumber() {
		return classNumber;
	}

	public String getStudentClass() {
		return studentClass;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public String getProvisionalDiagnosis() {
		return provisionalDiagnosis;
	}

	public String getTreatmentTaken() {
		return treatmentTaken;
	}

	public String getTreatmentTimeStamp() {
		return treatmentTimeStamp;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public void setProvisionalDiagnosis(String provisionalDiagnosis) {
		this.provisionalDiagnosis = provisionalDiagnosis;
	}

	public void setTreatmentTaken(String treatmentTaken) {
		this.treatmentTaken = treatmentTaken;
	}

	public void setTreatmentTimeStamp(String treatmentTimeStamp) {
		this.treatmentTimeStamp = treatmentTimeStamp;
	}

	@Override
	public String toString() {
		return "DiagnosisAndTreatment [id=" + id + ", date=" + date + ", classNumber=" + classNumber + ", studentClass="
				+ studentClass + ", studentName=" + studentName + ", medicalHistory=" + medicalHistory
				+ ", provisionalDiagnosis=" + provisionalDiagnosis + ", treatmentTaken=" + treatmentTaken
				+ ", treatmentTimeStamp=" + treatmentTimeStamp + "]";
	}

}
