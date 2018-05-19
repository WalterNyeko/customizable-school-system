package school.model.admission;

import java.util.Calendar;
import java.util.Date;

public class AdmissionStudentPersonalDetails {

	private String idNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;

	private Date dob;
	private String nationality;
	private String classJoining;
	private String house;
	private String dormitory;

	private String religion;
	private String term;
	private Calendar yearOfAdmission;
	private String parentName;
	private String parentEmail;

	private String parentPhone;
	private String parentOccupation;
	private String parentAddress;
	private boolean isSponsored;
	private String sponsorName;

	public AdmissionStudentPersonalDetails(String idNumber, String firstName, String middleName, String lastName,
			String gender, Date dob, String nationality, String classJoining, String house, String dormitory,
			String religion, String term, Calendar yearOfAdmission, String parentName, String parentEmail,
			String parentPhone, String parentOccupation, String parentAddress, boolean isSponsored,
			String sponsorName) {
		super();
		this.idNumber = idNumber;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.gender = gender;
		this.dob = dob;
		this.nationality = nationality;
		this.classJoining = classJoining;
		this.house = house;
		this.dormitory = dormitory;
		this.religion = religion;
		this.term = term;
		this.yearOfAdmission = yearOfAdmission;
		this.parentName = parentName;
		this.parentEmail = parentEmail;
		this.parentPhone = parentPhone;
		this.parentOccupation = parentOccupation;
		this.parentAddress = parentAddress;
		this.isSponsored = isSponsored;
		this.sponsorName = sponsorName;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getClassJoining() {
		return classJoining;
	}

	public void setClassJoining(String classJoining) {
		this.classJoining = classJoining;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getDormitory() {
		return dormitory;
	}

	public void setDormitory(String dormitory) {
		this.dormitory = dormitory;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Calendar getYearOfAdmission() {
		return yearOfAdmission;
	}

	public void setYearOfAdmission(Calendar yearOfAdmission) {
		this.yearOfAdmission = yearOfAdmission;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentEmail() {
		return parentEmail;
	}

	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	public String getParentPhone() {
		return parentPhone;
	}

	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}

	public String getParentOccupation() {
		return parentOccupation;
	}

	public void setParentOccupation(String parentOccupation) {
		this.parentOccupation = parentOccupation;
	}

	public String getParentAddress() {
		return parentAddress;
	}

	public void setParentAddress(String parentAddress) {
		this.parentAddress = parentAddress;
	}

	public boolean isSponsored() {
		return isSponsored;
	}

	public void setSponsored(boolean isSponsored) {
		this.isSponsored = isSponsored;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

}
