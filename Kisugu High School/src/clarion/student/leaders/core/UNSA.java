package clarion.student.leaders.core;

import javafx.beans.property.SimpleStringProperty;

public class UNSA {

	private int id;
	private SimpleStringProperty idNumber;
	private SimpleStringProperty firstName;
	private SimpleStringProperty middleName;
	private SimpleStringProperty studentClass;

	private SimpleStringProperty lastName;
	private SimpleStringProperty gender;
	private SimpleStringProperty yearOfRegime;
	private SimpleStringProperty post;

	public UNSA(int id, String idNumber, String firstName, String middleName, String lastName, String studentClass,
			String gender, String yearOfRegime, String post) {
		super();
		this.id = id;
		this.idNumber = new SimpleStringProperty(idNumber);
		this.firstName = new SimpleStringProperty(firstName);
		this.middleName = new SimpleStringProperty(middleName);
		this.lastName = new SimpleStringProperty(lastName);
		this.studentClass = new SimpleStringProperty(studentClass);
		this.gender = new SimpleStringProperty(gender);
		this.yearOfRegime = new SimpleStringProperty(yearOfRegime);
		this.post = new SimpleStringProperty(post);
	}
	
	public UNSA(String idNumber, String firstName, String middleName, String lastName, String studentClass,
			String gender, String yearOfRegime, String post) {
		super();
		this.idNumber = new SimpleStringProperty(idNumber);
		this.firstName = new SimpleStringProperty(firstName);
		this.middleName = new SimpleStringProperty(middleName);
		this.lastName = new SimpleStringProperty(lastName);
		this.studentClass = new SimpleStringProperty(studentClass);
		this.gender = new SimpleStringProperty(gender);
		this.yearOfRegime = new SimpleStringProperty(yearOfRegime);
		this.post = new SimpleStringProperty(post);
	}

	public int getId() {
		return id;
	}

	public String getStudentClass(){
		return studentClass.get();
	}
	
	public void setStudentClass(String studentClass){
		this.studentClass.set(studentClass);
	}
	
	public String getIdNumber() {
		return idNumber.get();
	}

	public String getFirstName() {
		return firstName.get();
	}

	public String getMiddleName() {
		return middleName.get();
	}

	public String getLastName() {
		return lastName.get();
	}

	public String getGender() {
		return gender.get();
	}

	public String getYearOfRegime() {
		return yearOfRegime.get();
	}

	public String getPost() {
		return post.get();
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber.set(idNumber);
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public void setMiddleName(String middleName) {
		this.middleName.set(middleName);
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public void setGender(String gender) {
		this.gender.set(gender);
	}

	public void setYearOfRegime(String yearOfRegime) {
		this.yearOfRegime.set(yearOfRegime);
	}

	public void setPost(String post) {
		this.post.set(post);
	}

}
