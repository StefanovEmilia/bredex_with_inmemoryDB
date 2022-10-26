package bredex.backendTest.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bredex.backendTest.rest.db.Database;
import bredex.backendTest.rest.model.Position;
import bredex.backendTest.rest.model.Client;

@RestController
public class AppController {

	@PostMapping("/client")
	public String regClient(@RequestParam(name="name") String name, @RequestParam(name="email") String email) {
		
		//Create a response. 
		String response = "";
		
		//Open the database
		Database db = new Database();
		
		//Create email validation and check it
		String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
		Pattern pattern = Pattern.compile(emailRegex);
		boolean validEmail = pattern.matcher(email).matches();
		
		if(name.length() <= 100 && validEmail) {
			
			//Check if the email exists or not
			if( !db.isEmailExists(email)) {
				
				//If the email is unique, generate a random API key and save the client to the database
				String apiKey = UUID.randomUUID().toString();
				
				Client client = new Client();
				client.setName(name);
				client.setEmail(email);
				client.setApiKey(apiKey);
				
				db.saveClient(client);
			
				response = apiKey;
				
			} else {
				response = "This email already exists.";
			}
			
		} else {
			
			if(name.length() <= 100) {
				response = "Validation problem: Invalid email! Please try again!";
			} else {
				response = "Validation problem: The name is too long! Max. allowed length: 100 characters.";
			}
			
		}
		
		db.close();
		
		return response;
	}
	
	@PostMapping("/position")
	public String createPosition(@RequestParam(name="roleName") String roleName, @RequestParam(name="location") String location, 
								 @RequestParam(name="apiKey") String apiKey) {
		
		String response = "";	
		
		//check the apiKey		
		if(isValidApi(apiKey)) {
			
			//check length validations
			if( isValidRoleAndLocation(roleName, location) ) {
				
				//save the position to the database and create the url
				Database db = new Database();
				
				Position pos = new Position();
				pos.setAdverter(apiKey);
				pos.setLocation(location);
				pos.setRoleName(roleName);
				
				db.savePosition(pos);
				
				int id = db.getLatestPositionID();
				
				response = "http://localhost:8080/position/" + id;
				
				db.close();
				
			} else {				
				
				response = createErrorMessage(roleName, location);
			}
			
		} else {
			response = "Invalid API Key!";
		}
				
		return response;
	}
	
	@GetMapping("/position/search")
	public ArrayList<String> getPositions(@RequestParam(name="keyword")String keyword, @RequestParam(name="location")String location, 
											@RequestParam(name="apiKey")String apiKey) {
		
		ArrayList<String> response = new ArrayList<String>();
		
		//Check the API key
		if(isValidApi(apiKey)) {
			
			//Check length validations
			if(isValidRoleAndLocation(keyword, location)) {
				
				//Search positions and create the URL list. 
				//Notice that if the search is unsuccessful, and there are no validation errors, the response size will be 0!
				Database db = new Database();
				
				List<Position> positions = db.getPositions(keyword, location);
				
				//Check if we found results
				if(positions.size() > 0) {
					
					//Fill the list with the urls
					for(int i = 0; i < positions.size(); i++) {
						
						int id = positions.get(i).getId();
						String url = "http://localhost:8080/position/" + id;
						response.add(url);
						
					}
				}
				
				db.close();
				
			} else {
				response.add(createErrorMessage(keyword, location));
			}
			
		} else {
			response.add("Invalid API Key!");
		}
		
		return response;
	}
	
	@GetMapping("/position/{id}")
	public Position getPositionById(@PathVariable("id") int id) {
		
		Database db = new Database();
		
		Position pos = db.getPositionById(id);
		
		db.close();
		
		return pos;
	}
	
	
	
	// ----- AUXILIARY METHODS ----- //
	public boolean isValidRoleAndLocation(String roleName, String location) {
		
		 boolean isValid = false;
		 
		 if( (roleName.length() <= 50) && (location.length() <= 50) ) {
			 
			 isValid = true;
		 }
		 
		 return isValid;
	}
	
	public String createErrorMessage(String role, String location) {
		
		String field = "";
		String error = "";
		
		if(role.length() > 50) {
			field = "role name";
		} else {
			field = "location";
		}
		
		error = "Validation problem: The " + field + " is too long! Max. allowed length: 50 characters.";
		
		return error;
		
	}
	
	public boolean isValidApi(String apiKey) {
		
		boolean valid = false;
		
		Database db = new Database();
		
		Client client = db.getClientByApiKey(apiKey);
		
		if(client != null) {
			valid = true;
		}
		
		db.close();
		
		return valid;
	}
}
