package bredex.backendTest.rest.model;

public class Position {
	
	private int id;
	private String advertiser;
	private String roleName;
	private String location;
	
	public Position() {

	}

	public Position(int id, String advertiser, String roleName, String location) {
		super();
		this.id = id;
		this.advertiser = advertiser;
		this.roleName = roleName;
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(String advertiser) {
		this.advertiser = advertiser;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Position [id=" + id + ", advertiser=" + advertiser + ", roleName=" + roleName + ", location=" + location
				+ "]";
	}
	

}
