package school.model.library;

import java.util.Date;

public class GeneralOverViewOfBooks {

	private int id;
	private Date dateIn;
	private String bookTitle;
	private String subject;
	private String author;
	private String publisher;

	private String shelfName;
	private int quantity;
	private String donor;

	public GeneralOverViewOfBooks(int id, Date dateIn, String bookTitle, String subject, String author,
			String publisher, String shelfName, int quantity, String donor) {
		super();
		this.id = id;
		this.dateIn = dateIn;
		this.bookTitle = bookTitle;
		this.subject = subject;
		this.author = author;
		this.publisher = publisher;
		this.shelfName = shelfName;
		this.quantity = quantity;
		this.donor = donor;
	}

	
	
	public GeneralOverViewOfBooks(Date dateIn, String bookTitle, String subject, String author,
			String publisher, String shelfName, int quantity, String donor) {
		super();
		this.dateIn = dateIn;
		this.bookTitle = bookTitle;
		this.subject = subject;
		this.author = author;
		this.publisher = publisher;
		this.shelfName = shelfName;
		this.quantity = quantity;
		this.donor = donor;
	}

	
	
	public int getId() {
		return id;
	}

	public Date getDateIn() {
		return dateIn;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public String getSubject() {
		return subject;
	}

	public String getAuthor() {
		return author;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getShelfName() {
		return shelfName;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getDonor() {
		return donor;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setShelfName(String shelfName) {
		this.shelfName = shelfName;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setDonor(String donor) {
		this.donor = donor;
	}

	@Override
	public String toString() {
		return "GeneralOverViewOfBooks [id=" + id + ", dateIn=" + dateIn + ", bookTitle=" + bookTitle + ", subject="
				+ subject + ", author=" + author + ", publisher=" + publisher + ", shelfName=" + shelfName
				+ ", quantity=" + quantity + ", donor=" + donor + "]";
	}

}
