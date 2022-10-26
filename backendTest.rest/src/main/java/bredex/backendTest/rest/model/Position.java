package bredex.backendTest.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="positions")
public class Position {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="apiKey")
	private String adverter;
	
	@Column(name="roleName")
	private String roleName;
	
	@Column(name="location")
	private String location;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdverter() {
		return adverter;
	}

	public void setAdverter(String adverter) {
		this.adverter = adverter;
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
		return "Position [id=" + id + ", adverter=" + adverter + ", roleName=" + roleName + ", location=" + location
				+ "]";
	}
	

}
