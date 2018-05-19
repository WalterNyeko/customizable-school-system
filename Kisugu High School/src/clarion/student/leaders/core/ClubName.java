package clarion.student.leaders.core;

import javafx.beans.property.SimpleStringProperty;

public class ClubName {

	private int id;
	private SimpleStringProperty clubName;

	public ClubName(int id, String clubName) {
		super();
		this.id = id;
		this.clubName = new SimpleStringProperty(clubName);
	}
	
	public ClubName(String clubName) {
		super();
		this.clubName = new SimpleStringProperty(clubName);
	}
	
	public ClubName(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getClubName() {
		return clubName.get();
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setClubName(String clubName) {
		this.clubName.set(clubName);
	}

}
