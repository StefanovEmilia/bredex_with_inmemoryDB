package bredex.backendTest.rest.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import bredex.backendTest.rest.model.Position;
import bredex.backendTest.rest.model.Client;

public class Database {

	private Connection conn;
	private static boolean firstDatabaseCreated = false;

	public Database() throws Exception {
		
		try {
		     Class.forName("org.hsqldb.jdbc.JDBCDriver" );
		 } catch (Exception e) {
		     System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
		     e.printStackTrace();
		     return;
		 }

		conn = DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "sa", "");
		
		//create tables
		String createClients = readToString("C:\\Users\\nagyb\\Desktop\\Java 4.1\\Free\\bredex_with_inmemoryDB\\backendTest.rest\\src\\main\\resources\\db\\clients.sql");
		conn.createStatement().executeUpdate(createClients);
		String createPositions = readToString("C:\\Users\\nagyb\\Desktop\\Java 4.1\\Free\\bredex_with_inmemoryDB\\backendTest.rest\\src\\main\\resources\\db\\positions.sql");
		conn.createStatement().executeUpdate(createPositions);
		
		//add data
		if(!firstDatabaseCreated) {
			String populateTables = readToString("C:\\Users\\nagyb\\Desktop\\Java 4.1\\Free\\bredex_with_inmemoryDB\\backendTest.rest\\src\\main\\resources\\db\\data.sql");
			conn.createStatement().executeUpdate(populateTables);
			firstDatabaseCreated = true;
		}
	}

	//Check if an email already exists in the database or not
	public boolean isEmailExists(String email) throws SQLException {

		boolean exists = false;
		
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM clients WHERE email= ?");
		ps.setString(1, email);
		
		ResultSet rs = ps.executeQuery();

		if(rs.next()) {
			exists = true;
		}
		
		rs.close();
		ps.close();

		return exists;
	}

	//Register a new client
	public void saveClient(Client client) throws SQLException {

		PreparedStatement ps = conn.prepareStatement("INSERT INTO clients (apiKey, name, email) VALUES (?,?,?)");
		ps.setString(1, client.getApiKey());
		ps.setString(2, client.getName());
		ps.setString(3, client.getEmail());
		
		ps.executeUpdate();
		
		ps.close();
	}
	
	/*I use this method for API key validation. 
	 * If there are any clients with the requested API key, validation is OK.
	 * If there are not, than there will be an API key validation problem.
	 */
	public boolean isValidApi(String apiKey) throws SQLException {

		boolean valid = false;

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM clients WHERE apiKey=?");
		ps.setString(1, apiKey);
		
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {			
			valid = true;
		}
		
		rs.close();
		ps.close();

		return valid;
	}
	
	//Get a client by the api key
	public Client getClientByApiKey(String apiKey) throws SQLException {
		
		Client client = null;
		
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM clients WHERE apiKey=?");
		ps.setString(1, apiKey);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {			
			
			String name = rs.getString("name");
			String email = rs.getString("email");
			
			client = new Client(apiKey, name, email);
		}
		
		rs.close();
		ps.close();
		
		return client;
		
	}

	//Registrate a new jobpost
	public void savePosition(Position pos) throws SQLException {

		PreparedStatement ps = conn.prepareStatement("INSERT INTO positions (advertiser_apiKey, roleName, location) VALUES (?,?,?)");
		ps.setString(1, pos.getAdvertiser());
		ps.setString(2, pos.getRoleName());
		ps.setString(3, pos.getLocation());
		
		ps.executeUpdate();
		
		ps.close();

	}

	/* This method help us to gather all the positions from the database regarding by the keyword location.
	 * If no fields are filled in, all positions will be collected.
	 */
	public List<Position> getPositions(String keyword, String location) throws SQLException {

		List<Position> positions = new ArrayList<Position>();

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM positions WHERE roleName LIKE ? AND location LIKE ?");
		ps.setString(1, "%" + keyword + "%");
		ps.setString(2, "%" + location + "%");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			
			int id = rs.getInt("id");
			String advertiser = rs.getString("advertiser_apiKey");
			String roleName = rs.getString("roleName");
			String loc = rs.getString("location");
			
			Position pos = new Position(id, advertiser, roleName, loc);
			
			positions.add(pos);
		}
		
		rs.close();
		ps.close();

		return positions;
	}

	//Displaying the data of one current position
	public Position getPositionById(int id) throws SQLException {
		
		Position pos = null;

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM positions WHERE id=?");
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			
			String apiKey = rs.getString("advertiser_apiKey");
			String roleName = rs.getString("roleName");
			String location = rs.getString("location");
			
			pos = new Position(id, apiKey, roleName, location);
		}
		
		// Set the adverter's name by the API key
		// I think its a must, because the API key is a sensitive data, but the advertiser is an important detail
		if(pos != null) {
			Client client = getClientByApiKey(pos.getAdvertiser());
			pos.setAdvertiser(client.getName());
		}

		return pos;
	}

	public void close() throws SQLException {
		conn.close();
	}
	
	public static String readToString(String fname) throws Exception {
		File file = new File(fname);
		String string = FileUtils.readFileToString(file, "utf-8");
		return string;
	}

}
