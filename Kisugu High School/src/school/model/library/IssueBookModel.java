package school.model.library;

import java.util.Date;

public class IssueBookModel {

	private String teacherId;
	private String teacherName;
	private String teacherContact;

	private String studentId;
	private String studentName;
	private String studentClass;

	private String bookIdNumber;
	private String bookTitle;
	private String bookAuthor;

	private Date issueDate;
	private Date returnDate;

	public IssueBookModel(String teacherId, String teacherName, String teacherContact, String studentId,
			String studentName, String studentClass, String bookIdNumber, String bookTitle, String bookAuthor,
			Date issueDate, Date returnDate) {
		super();
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.teacherContact = teacherContact;
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentClass = studentClass;
		this.bookIdNumber = bookIdNumber;
		this.bookTitle = bookTitle;
		this.bookAuthor = bookAuthor;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public String getTeacherContact() {
		return teacherContact;
	}

	public String getStudentId() {
		return studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getStudentClass() {
		return studentClass;
	}

	public String getBookIdNumber() {
		return bookIdNumber;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public void setTeacherContact(String teacherContact) {
		this.teacherContact = teacherContact;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}

	public void setBookIdNumber(String bookIdNumber) {
		this.bookIdNumber = bookIdNumber;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	@Override
	public String toString() {
		return "IssueBookModel [teacherId=" + teacherId + ", teacherName=" + teacherName + ", teacherContact="
				+ teacherContact + ", studentId=" + studentId + ", studentName=" + studentName + ", studentClass="
				+ studentClass + ", bookIdNumber=" + bookIdNumber + ", bookTitle=" + bookTitle + ", bookAuthor="
				+ bookAuthor + ", issueDate=" + issueDate + ", returnDate=" + returnDate + "]";
	}

}
