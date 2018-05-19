package clarion.model.dispensary;

public class StudentInfo {

	private int id;
	private String classNumber;
	private String firstName;
	private String middleName;
	private String lastName;

	private String studentClass;
	private String dormitory;
	private String specialCase;
	private String yearOfAdmission;

	public StudentInfo() {

	}

	public StudentInfo(String classNumber) {
		this.classNumber = classNumber;
	}

	
	public StudentInfo(int id, String classNumber, String firstName,
			String studentClass, String dormitory,String yearOfAdmission) {
		super();
		this.id = id;
		this.classNumber = classNumber;
		this.firstName = firstName;
		this.studentClass = studentClass;
		this.dormitory = dormitory;
		this.yearOfAdmission = yearOfAdmission;
	}

	public StudentInfo(int id, String classNumber, String firstName, String middleName, String lastName,
			String studentClass, String dormitory, String yearOfAdmission) {
		super();
		this.id = id;
		this.classNumber = classNumber;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.studentClass = studentClass;
		this.dormitory = dormitory;
		this.yearOfAdmission = yearOfAdmission;
	}

	public int getId() {
		return id;
	}

	public String getClassNumber() {
		return classNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getStudentClass() {
		return studentClass;
	}

	public String getDormitory() {
		return dormitory;
	}

	public String getSpecialCase() {
		return specialCase;
	}

	public String getYearOfAdmission() {
		return yearOfAdmission;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}

	public void setDormitory(String dormitory) {
		this.dormitory = dormitory;
	}

	public void setSpecialCase(String specialCase) {
		this.specialCase = specialCase;
	}

	public void setYearOfAdmission(String yearOfAdmission) {
		this.yearOfAdmission = yearOfAdmission;
	}

	@Override
	public String toString() {
		return "StudentInfo [id=" + id + ", classNumber=" + classNumber + ", firstName=" + firstName + ", middleName="
				+ middleName + ", lastName=" + lastName + ", studentClass=" + studentClass + ", dormitory=" + dormitory
				+ ", specialCase=" + specialCase + ", yearOfAdmission=" + yearOfAdmission + "]";
	}

}
