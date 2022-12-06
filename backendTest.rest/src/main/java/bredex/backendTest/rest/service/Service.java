package bredex.backendTest.rest.service;

import java.util.ArrayList;
import java.util.List;

import bredex.backendTest.rest.db.Database;
import bredex.backendTest.rest.model.Client;
import bredex.backendTest.rest.model.Position;
import bredex.backendTest.rest.response.ErrorMessage;
import bredex.backendTest.rest.response.Response;

public class Service {

	// ----- SERVICE METHODS ----- //
	public static boolean isValidRoleAndLocation(String roleName, String location) {

		boolean isValid = false;

		if ((roleName.length() <= 50) && (location.length() <= 50)) {

			isValid = true;
		}

		return isValid;
	}

	public static String createErrorMessage(String role, String location) {

		String field = "";
		String error = "";

		if (role.length() > 50) {
			field = "role name";
		} else {
			field = "location";
		}

		error = "Validation problem: The " + field + " is too long! Max. allowed length: 50 characters.";

		return error;

	}
	
	public static boolean isValidApi(String apiKey) {

		boolean valid = false;

		Database db = new Database();

		Client client = db.getClientByApiKey(apiKey);

		if (client != null) {
			valid = true;
		}

		db.close();

		return valid;
	}

	public static Response invalidApi() {

		Response response = new ErrorMessage(11, false);
		response.setMessage("Invalid API Key: You must be granted a valid key.");

		return response;

	}

	public static List<Client> clientsDML() {
		
		List<Client> clients = new ArrayList<Client>();
		
		Client client1 = new Client(1, "22ce4d33-6f0b-47b5-b3bf-a01204d9940b", "John Doe", "tesztApi.kod?@gmail.com");
		Client client2 = new Client(2, "ab796345-f91f-4646-a294-f8e6f1fe0807", "Gipsz Jakab", "jakab0089@domain.com");
		Client client3 = new Client(3, "test", "Teszt Elek", "teszt@gmail.com");
		
		clients.add(client1);
		clients.add(client2);
		clients.add(client3);
		
		return clients;
	}

	public static List<Position> positionsDML() {
		
		List<Position> positions = new ArrayList<Position>();
		
		Position pos1 = new Position(1, "22ce4d33-6f0b-47b5-b3bf-a01204d9940b", "junior java developer", "remote");
		Position pos2 = new Position(2, "22ce4d33-6f0b-47b5-b3bf-a01204d9940b", "medior java developer", "remote");
		Position pos3 = new Position(3, "22ce4d33-6f0b-47b5-b3bf-a01204d9940b", "senior java developer", "remote");
		Position pos4 = new Position(4, "22ce4d33-6f0b-47b5-b3bf-a01204d9940b", "senior java engineer", "remote");
		Position pos5 = new Position(5, "ab796345-f91f-4646-a294-f8e6f1fe0807", "senior java engineer", "remote");
		Position pos6 = new Position(6, "test", "junior python engineer", "london");
		Position pos7 = new Position(7, "test", "java backend developer", "london");
		
		positions.add(pos1);
		positions.add(pos2);
		positions.add(pos3);
		positions.add(pos4);
		positions.add(pos5);
		positions.add(pos6);
		positions.add(pos7);
		
		return positions;
	}

}
