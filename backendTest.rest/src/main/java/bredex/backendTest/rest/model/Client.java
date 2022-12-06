package bredex.backendTest.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="clients")
public class Client {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="apiKey")
	private String apiKey;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;

	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Client(int id, String apiKey, String name, String email) {
		super();
		this.id = id;
		this.apiKey = apiKey;
		this.name = name;
		this.email = email;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Client [apiKey=" + apiKey + ", name=" + name + ", email=" + email + "]";
	}
	
	

}
