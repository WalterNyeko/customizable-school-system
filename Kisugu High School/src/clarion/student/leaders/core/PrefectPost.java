package clarion.student.leaders.core;

import javafx.beans.property.SimpleStringProperty;

public class PrefectPost {

	private int id;
	private SimpleStringProperty prepfectPost;

	public PrefectPost(int id, String prepfectPost) {
		super();
		this.id = id;
		this.prepfectPost = new SimpleStringProperty(prepfectPost);
	}

	public PrefectPost(String prepfectPost) {
		super();
		this.prepfectPost = new SimpleStringProperty(prepfectPost);
	}

	public int getId() {
		return id;
	}

	public String getPrepfectPost() {
		return prepfectPost.get();
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPrepfectPost(String prepfectPost) {
		this.prepfectPost.set(prepfectPost);
	}

}
