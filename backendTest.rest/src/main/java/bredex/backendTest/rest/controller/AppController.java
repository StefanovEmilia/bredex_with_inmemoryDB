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
import bredex.backendTest.rest.response.ErrorMessage;
import bredex.backendTest.rest.response.PositionList;
import bredex.backendTest.rest.response.Response;
import bredex.backendTest.rest.service.Service;
import bredex.backendTest.rest.model.Client;

@RestController
public class AppController {
	
	@GetMapping("/client")
	public Response regClient(@RequestParam(name="name") String name, @RequestParam(name="email") String email) throws Exception {
		
		//Create a response. 
		Response response = new Response();
		
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
			
				response.setMessage(apiKey);
				
			} else {
				// If the email already exists, send an error message
				response = new ErrorMessage(1, false);
				response.setMessage("This email already exists.");
			}
			
		} else {
			// If there are any validation problems, send an error message according to the problematic field.
			if(name.length() <= 100) {
				response = new ErrorMessage(10, false);
				response.setMessage("Validation problem: Invalid email! Please try again!");
			} else {
				response = new ErrorMessage(10, false);
				response.setMessage("Validation problem: The name is too long! Max. allowed length: 100 characters.");
			}
			
		}
		
		db.close();
		
		return response;
	}
	
	@PostMapping("/position")
	public Response createPosition(@RequestParam(name="roleName") String roleName, @RequestParam(name="location") String location, 
								 @RequestParam(name="apiKey") String apiKey) throws Exception {
		
		Response response = new Response();	
		Database db = new Database();
		
		//check the apiKey		
		if( Service.isValidApi(apiKey)) {
			
			//check length validations
			if( Service.isValidRoleAndLocation(roleName, location) ) {
				
				//save the position to the database and create the url
								
				Position pos = new Position();
				pos.setAdvertiser(apiKey);
				pos.setLocation(location);
				pos.setRoleName(roleName);
				
				db.savePosition(pos);
				
				List<Position> positions = db.getPositions("", "");
				int id = positions.get(positions.size() - 1).getId();
				
				response.setMessage("http://localhost:8080/position/" + id);
				
				db.close();
				
			} else {	
				// If there are any validation problems, send an error message
				response = new ErrorMessage(10, false);
				response.setMessage( Service.createErrorMessage(roleName, location));				
			}			
		} else {
			// If the API key is invalid, send an error message
			response = Service.invalidApi();
		}
				
		return response;
	}
	
	@GetMapping("/position/search")
	public Response getPositions(@RequestParam(name="keyword")String keyword, @RequestParam(name="location")String location, 
											@RequestParam(name="apiKey")String apiKey) throws Exception {
		
		Response response = new Response();
		Database db = new Database();
		
		//Check the API key
		if( Service.isValidApi(apiKey)) {
			
			//Check length validations
			if(Service.isValidRoleAndLocation(keyword, location)) {
				
				//Search positions and create the URL list. 
				//Notice that if the search is unsuccessful, and there are no validation errors, the list size will be 0!			
				List<Position> positions = db.getPositions(keyword, location);
				ArrayList<String> urlList = new ArrayList<String>();
				
				//Check if we found results
				if(positions.size() > 0) {
					
					//Fill the list with the urls
					for(int i = 0; i < positions.size(); i++) {
						
						int id = positions.get(i).getId();
						String url = "http://localhost:8080/position/" + id;
						urlList.add(url);
						
					}
					
					response = new PositionList(urlList);
				}
				
				response.setMessage("Search results: " + urlList.size() + " jobs matching your request found.");
				
				db.close();
				
			} else {
				//If there are any validation problems, send an error message.
				response = new ErrorMessage(10, false);
				response.setMessage(Service.createErrorMessage(keyword, location));
			}
			
		} else {
			//If the API key is invalid, send an error message.
			response = Service.invalidApi();
		}
		
		return response;
	}
	
	// I think this request should be understood as being allowed for everyone, regardless of whether they have an API key or not.
	@GetMapping("/position/{id}")
	public Position getPositionById(@PathVariable("id") int id) throws Exception {
		
		Database db = new Database();
		
		Position pos = db.getPositionById(id);
		
		db.close();
		
		return pos;
	}
	
}
