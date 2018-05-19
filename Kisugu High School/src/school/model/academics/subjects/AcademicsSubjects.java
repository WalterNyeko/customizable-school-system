package school.model.academics.subjects;

public class AcademicsSubjects {

	private String subjectName;
	private String subjectCode;
	private String subjectLevel;
	private boolean chosen;

	public AcademicsSubjects(String subjectName, String subjectCode, String subjectLevel, boolean chosen) {
		super();
		this.subjectName = subjectName;
		this.subjectCode = subjectCode;
		this.subjectLevel = subjectLevel;
		this.chosen = chosen;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectLevel() {
		return subjectLevel;
	}

	public void setSubjectLevel(String subjectLevel) {
		this.subjectLevel = subjectLevel;
	}

	public boolean isChosen() {
		return chosen;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

}
