package clarion.model.dispensary;

import javafx.scene.Parent;

public class ParentInfo {
	private int id;
	private String classNumber;
	private String fathersName;

	private String mothersName;
	private String guardiansName;
	private String sponsorsName;

	private String fathersPhone;
	private String mothersPhone;
	private String sponsorPhone;
	private String parentsAddress;

	public ParentInfo(int id, String classNumber, String fathersName, String mothersName, String guardiansName,
			String sponsorsName, String fathersPhone, String mothersPhone, String sponsorPhone, String parentsAddress) {
		super();
		this.id = id;
		this.classNumber = classNumber;
		this.fathersName = fathersName;
		this.mothersName = mothersName;
		this.guardiansName = guardiansName;
		this.sponsorsName = sponsorsName;
		this.fathersPhone = fathersPhone;
		this.mothersPhone = mothersPhone;
		this.sponsorPhone = sponsorPhone;
		this.parentsAddress = parentsAddress;
	}

	public int getId() {
		return id;
	}

	public String getClassNumber() {
		return classNumber;
	}

	public String getFathersName() {
		return fathersName;
	}

	public String getMothersName() {
		return mothersName;
	}

	public String getGuardiansName() {
		return guardiansName;
	}

	public String getSponsorsName() {
		return sponsorsName;
	}

	public String getFathersPhone() {
		return fathersPhone;
	}

	public String getMothersPhone() {
		return mothersPhone;
	}

	public String getSponsorPhone() {
		return sponsorPhone;
	}

	public String getParentsAddress() {
		return parentsAddress;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	public void setMothersName(String mothersName) {
		this.mothersName = mothersName;
	}

	public void setGuardiansName(String guardiansName) {
		this.guardiansName = guardiansName;
	}

	public void setSponsorsName(String sponsorsName) {
		this.sponsorsName = sponsorsName;
	}

	public void setFathersPhone(String fathersPhone) {
		this.fathersPhone = fathersPhone;
	}

	public void setMothersPhone(String mothersPhone) {
		this.mothersPhone = mothersPhone;
	}

	public void setSponsorPhone(String sponsorPhone) {
		this.sponsorPhone = sponsorPhone;
	}

	public void setParentsAddress(String parentsAddress) {
		this.parentsAddress = parentsAddress;
	}

	@Override
	public String toString() {
		return "ParentInfo [id=" + id + ", classNumber=" + classNumber + ", fathersName=" + fathersName
				+ ", mothersName=" + mothersName + ", guardiansName=" + guardiansName + ", sponsorsName=" + sponsorsName
				+ ", fathersPhone=" + fathersPhone + ", mothersPhone=" + mothersPhone + ", sponsorPhone=" + sponsorPhone
				+ ", parentsAddress=" + parentsAddress + "]";
	}

}
