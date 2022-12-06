package bredex.backendTest.rest.db;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import bredex.backendTest.rest.model.Position;
import bredex.backendTest.rest.service.Service;
import bredex.backendTest.rest.model.Client;

public class Database {

	private SessionFactory sessionFactory;
	private static boolean dataLoaded = false;

	public Database() {

		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

		sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		
		if (!dataLoaded) {
			List clients = Service.clientsDML();
			
			List positions = Service.positionsDML();
			
			Session session = sessionFactory.openSession();
			Transaction tr = session.beginTransaction();
			
			for(int i = 0; i < clients.size(); i++) {
				session.save(clients.get(i));
			}
			
			for(int i = 0; i < positions.size(); i++) {
				session.save(positions.get(i));
			}
			
			tr.commit();
			session.close();
			
			dataLoaded = true;
		}

	}

	// Check if an email already exists in the database or not
	public boolean isEmailExists(String email) {

		boolean exists = false;

		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();

		Query q = session.createQuery("SELECT c FROM Client c WHERE c.email= :email", Client.class);
		q.setParameter("email", email);
		List<Client> clients = q.getResultList();

		if (clients.size() > 0) {
			exists = true;
		}

		tr.commit();
		session.close();

		return exists;
	}

	// Register a new client
	public void saveClient(Client client) {

		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();

		session.save(client);

		tr.commit();
		session.close();
	}

	/*
	 * I use this method for API key validation. If there are any clients with the
	 * requested API key, validation is OK. If there are not, than there will be an
	 * API key validation problem.
	 */
	public Client getClientByApiKey(String apiKey) {

		Client client = null;

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Query q = session.createQuery("SELECT c FROM Client c WHERE apiKey= :apiKey", Client.class);
		q.setParameter("apiKey", apiKey);
		
		List<Client> clients = q.getResultList();
		
		if(clients.size() == 1) {
			client = clients.get(0);
		} 

		tx.commit();
		session.close();

		return client;
	}

	// Registrate a new jobpost
	public void savePosition(Position pos) {

		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();

		session.save(pos);

		tr.commit();
		session.close();

	}

	/*
	 * This method help us to gather all the positions from the database regarding
	 * by the keyword location. If no fields are filled in, all positions will be
	 * collected.
	 */
	public List<Position> getPositions(String keyword, String location) {

		List<Position> positions = null;

		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();

		String query = "";
		Query q = null;

		query = "SELECT p FROM Position p WHERE p.roleName LIKE :keyword AND p.location LIKE :location";

		q = session.createQuery(query, Position.class);
		q.setParameter("keyword", "%" + keyword + "%");
		q.setParameter("location", "%" + location + "%");

		positions = q.getResultList();

		tr.commit();
		session.close();

		return positions;
	}

	// Displaying the data of one current position
	public Position getPositionById(int id) {

		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();

		Position pos = session.get(Position.class, id);

		tr.commit();
		session.close();

		// Set the adverter's name by the API key
		// I think its a must, because the API key is a sensitive data, but the
		// advertiser is an important detail
		Client client = getClientByApiKey(pos.getAdvertiser());
		pos.setAdvertiser(client.getName());

		return pos;
	}

	public void close() {
		sessionFactory.close();
	}
}
