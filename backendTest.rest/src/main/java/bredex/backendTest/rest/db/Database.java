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
import bredex.backendTest.rest.model.User;

public class Database {
	
	private SessionFactory sessionFactory;
	
	public Database() {
		
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		
		sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	}
	
	public void saveClient(User user) {
		
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		
		session.save(user);
		
		tr.commit();
		session.close();
	}
	
	public void savePosition(Position pos) {
		
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		
		session.save(pos);
		
		tr.commit();
		session.close();
		
	}
	
	public List<Position> getPositions(String keyword, String location) {
		
		List<Position> positions = null;
		
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		
		Query q = session.createQuery("SELECT p FROM Position p WHERE p.roleName LIKE :keywod AND p.location = :location");
		q.setParameter("keyword", "%" + keyword + "%");
		q.setParameter("location", location);
		
		positions = q.getResultList();
		
		tr.commit();
		session.close();
		
		return positions;
	}
	
	public User getUserByApiKey(String apiKey) {
		
		User user = null;
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		user = session.get(User.class, apiKey);
		
		tx.commit();
		session.close();
	
		return user;
	}
	
	public void close() {
		sessionFactory.close();
	}

}
