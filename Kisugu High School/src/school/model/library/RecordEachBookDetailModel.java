package school.model.library;

public class RecordEachBookDetailModel {

	private int id;
	private String bookId;
	private String bookTitle;
	private String subjectName;

	public RecordEachBookDetailModel(int id, String bookId, String bookTitle, String subjectName) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.subjectName = subjectName;
	}

	public RecordEachBookDetailModel(String bookId, String bookTitle, String subjectName) {
		super();
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.subjectName = subjectName;
	}

	public RecordEachBookDetailModel(String subjectName) {
		super();
		this.subjectName = subjectName;
	}

	public RecordEachBookDetailModel() {

	}

	public int getId() {
		return id;
	}

	public String getBookId() {
		return bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Override
	public String toString() {
		return "RecordEachBookDetailModel [id=" + id + ", bookId=" + bookId + ", bookTitle=" + bookTitle
				+ ", subjectName=" + subjectName + "]";
	}

}
