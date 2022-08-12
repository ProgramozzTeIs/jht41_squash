package pti.sb_squash_mvc.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class Database {
	
	private SessionFactory sessionFactory;
	
	public Database() {
		
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		
		sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	}
	
	public void close() {
		sessionFactory.close();
	}

}
