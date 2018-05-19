package clarion.model.dorm;

public class Dorm {

	private int id;
	private String classNumber;
	private String studentName;
	private String studentClass;
	private String year;

	public Dorm(int id, String classNumber, String studentName, String studentClass, String year) {
		super();
		this.id = id;
		this.classNumber = classNumber;
		this.studentName = studentName;
		this.studentClass = studentClass;
		this.year = year;
	}

	public Dorm(String classNumber, String studentName, String studentClass, String year) {
		super();
		this.classNumber = classNumber;
		this.studentName = studentName;
		this.studentClass = studentClass;
		this.year = year;
	}

	public Dorm(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getClassNumber() {
		return classNumber;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getStudentClass() {
		return studentClass;
	}

	public String getYear() {
		return year;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Dorm [id=" + id + ", classNumber=" + classNumber + ", studentName=" + studentName + ", studentClass="
				+ studentClass + ", year=" + year + "]";
	}

}
