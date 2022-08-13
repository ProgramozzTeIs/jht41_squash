package pti.sb_squash_mvc.database;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import pti.sb_squash_mvc.model.Game;
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
	
	
	/** REGISTER NEW USER (PLAYER) */
	public User regUser(User newuser) {
		
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		session.save(newuser);
		
		transaction.commit();
		session.close();
		
		return newuser;	
		
	}
	
	/** GET ALL GAMES BY USER ID-s */
	public List<Game> getGamesByUserId(int id) {
		
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Query query = session.createQuery( "SELECT g FROM Game g WHERE g.useroneid = :oneid OR g.usertwoid = :twoid" , Game.class);
		query.setParameter("oneid", id);
		query.setParameter("twoid", id);
		
		List<Game> games = query.getResultList();
		
		transaction.commit();
		session.close();
		
		return games;
		
	}
	
	/** GET ALL GAMES */
	public List<Game> getGames() {
		
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Query query = session.createQuery( "SELECT g FROM Game " , Game.class);
		List<Game> allGames = query.getResultList();
		
		transaction.commit();
		session.close();
		
		return allGames;
		
	}
	
	/** GET GAME By ID */
	public Game getGameById(int id) {
		
		
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		
		Game game = session.get(Game.class, id);
		
		game.setUserOne(session.get(User.class, game.getUserOneId()));
		game.setUserTwo(session.get(User.class, game.getUserTwoId()));
		game.setLocation(session.get(Location.class, game.getLocationId()));
		
		trans.commit();
		session.close();
		
		return game;
	}
	
	/** GET GAMES BY LOCATIONID */
	public List<Game> getGamesByLocationId(int id){
		
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		
		Query query = session.createQuery("SELECT g FROM Game g WHERE g.locationId = :id",Game.class);
		query.setParameter("id", id);
		List<Game> gameList = query.getResultList();
		
		for(int game = 0 ; game < gameList.size() ; game++) {
			
			
			gameList.get(game).setUserOne(session.get(User.class, gameList.get(game).getUserOneId()));
			gameList.get(game).setUserTwo(session.get(User.class, gameList.get(game).getUserTwoId()));
			gameList.get(game).setLocation(session.get(Location.class, gameList.get(game).getLocationId()));
			
			
		}
		
		trans.commit();
		session.close();
		
		return gameList;
	}
	
	/** GET ALL LOCATIONS */ 
	public List<Location> getLocations(){
		
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		
		Query query = session.createQuery("SELECT l FROM Location l", Location.class);
		List<Location> allLocations = query.getResultList();
		
		trans.commit();
		session.close();
		
		return allLocations;
	}
	
	// GET ALL USERS (except Admin) //
	public List<User> getPlayers() {
		
		List<User> playerList = null;
		
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		
		Query q = session.createQuery("SELECT u FROM User u WHERE type = 'user'", User.class);
		playerList = q.getResultList();
		
		tr.commit();
		session.close();
		
		return playerList;	
	}
	
	// SAVE A NEW GAME //
	public Game regGame (Game newgame) {
		
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		
		session.save(newgame);
		
		tr.commit();
		session.close();
		
		return newgame;
	}

	
	public void close() {
		sessionFactory.close();
	}

}
