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

}
