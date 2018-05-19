package school.ui.student;

public class Prefects {

	private String idNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private String studentClass;
	private String gender;
	private String yearOfRegime;
	private String post;

	public Prefects(String idNumber, String firstName, String middleName, String lastName, String studentClass,
			String gender, String yearOfRegime, String post) {
		super();
		this.idNumber = idNumber;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.studentClass = studentClass;
		this.gender = gender;
		this.yearOfRegime = yearOfRegime;
		this.post = post;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getYearOfRegime() {
		return yearOfRegime;
	}

	public void setYearOfRegime(String yearOfRegime) {
		this.yearOfRegime = yearOfRegime;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

}
