package bredex.backendTest.rest.model;

public class Client {

	private String apiKey;
	private String name;
	private String email;

	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Client(String apiKey, String name, String email) {
		super();
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
