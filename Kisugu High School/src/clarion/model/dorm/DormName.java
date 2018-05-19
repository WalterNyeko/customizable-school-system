package clarion.model.dorm;

public class DormName {

	private int id;
	private String dormName;

	public DormName(int id, String dormName) {
		super();
		this.id = id;
		this.dormName = dormName;
	}

	public DormName(String dormName) {
		super();
		this.dormName = dormName;
	}

	public DormName(int id) {
		super();
		this.id = id;
	}

	public DormName() {

	}

	public int getId() {
		return id;
	}

	public String getDormName() {
		return dormName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDormName(String dormName) {
		this.dormName = dormName;
	}

	@Override
	public String toString() {
		return "DormName [id=" + id + ", dormName=" + dormName + "]";
	}

}
