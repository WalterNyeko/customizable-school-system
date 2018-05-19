package school.ui.student;

public class PrefectPost {
	private int id;
	private String postName;

	public PrefectPost(int id, String postName) {
		super();
		this.id = id;
		this.postName = postName;
	}
	public PrefectPost(String postName) {
		super();
		this.postName = postName;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}


}
