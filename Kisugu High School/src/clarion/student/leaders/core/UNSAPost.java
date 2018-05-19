package clarion.student.leaders.core;

import javafx.beans.property.SimpleStringProperty;

public class UNSAPost {

	private int id;
	private SimpleStringProperty UNSAPost;

	public UNSAPost(int id, String UNSAPost) {
		super();
		this.id = id;
		this.UNSAPost = new SimpleStringProperty(UNSAPost);
	}

	public UNSAPost(String UNSAPost) {
		super();
		this.UNSAPost = new SimpleStringProperty(UNSAPost);
	}

	public int getId() {
		return id;
	}

	public String getPrepfectPost() {
		return UNSAPost.get();
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPrepfectPost(String UNSAPost) {
		this.UNSAPost.set(UNSAPost);
	}

}
