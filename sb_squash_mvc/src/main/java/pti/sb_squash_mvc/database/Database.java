package pti.sb_squash_mvc.database;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import pti.sb_squash_mvc.model.Location;
import pti.sb_squash_mvc.model.User;




public class Database {
	
	private SessionFactory sessionFactory;
	
	public Database() {
		
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		
		sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	}
	
	public User getUserByNameAndPwd(String name, String pwd) {
		
		User user = null;
		
		Session session = sessionFactory.openSession();
		
		Transaction ta = session.beginTransaction();
		
		Query q = session.createQuery("SELECT u FROM User u WHERE u.name = :name AND u.pwd = :pwd");
		q.setParameter("name", name);
		q.setParameter("pwd", pwd);
		List<User> users = q.getResultList();
		
		if(!users.isEmpty()) {
			user = users.get(0);
		}
		
		ta.commit();
		session.close();
		
		return user;
	}
	
	
	public void updateUser(User user) {
		
		Session session = sessionFactory.openSession();
		
		Transaction ta = session.beginTransaction();
		
		session.update(user);
		
		ta.commit();
		session.close();
		
	}
	
	
	public void regLocation(Location location) {
		
		Session session = sessionFactory.openSession();
		
		Transaction ta = session.beginTransaction();
		
		session.save(location);
		
		ta.commit();
		session.close();
		
	}
	
	
	public User getUserById(int userId) {
		
		Session session = sessionFactory.openSession();
		
		Transaction ta = session.beginTransaction();
		
		User user = session.get(User.class, userId);
		
		ta.commit();
		session.close();
		
		return user;
	}
	
	
	
	
	public void close() {
		sessionFactory.close();
	}

}
