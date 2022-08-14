package pti.sb_squash_mvc.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.jdom2.JDOMException;
import org.springframework.web.client.RestTemplate;

import pti.sb_squash_mvc.model.Game;
import pti.sb_squash_mvc.model.Location;
import pti.sb_squash_mvc.model.User;
import pti.sb_squash_mvc.services.RestHandler;
import pti.sb_squash_mvc.services.XMLParser;




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
		
		Query query = session.createQuery( "SELECT g FROM Game g WHERE g.userOneId = :oneid OR g.userTwoId = :twoid" , Game.class);
		query.setParameter("oneid", id);
		query.setParameter("twoid", id);
		
		List<Game> games = query.getResultList();
		
		for(int i = 0; i < games.size(); i++) {
			Game game = games.get(i);
			
			game.setUserOne(session.get(User.class, game.getUserOneId()));
			game.setUserTwo(session.get(User.class, game.getUserTwoId()));
			game.setLocation(session.get(Location.class, game.getLocationId()));
			
		}
		
		transaction.commit();
		session.close();
		
		return games;
		
	}
	
	/** GET ALL GAMES */
	public List<Game> getGames() {
		
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Query query = session.createQuery( "SELECT g FROM Game g" , Game.class);
		List<Game> allGames = query.getResultList();
		
		for(int i = 0; i < allGames.size(); i++) {
			Game game = allGames.get(i);
			
			game.setUserOne(session.get(User.class, game.getUserOneId()));
			game.setUserTwo(session.get(User.class, game.getUserTwoId()));
			game.setLocation(session.get(Location.class, game.getLocationId()));
			
		}
		
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
		
		for(int i = 0  ; i < gameList.size() - 1 ; i++) {
			
			Game gameOne = gameList.get(i);
			Game gameTwo = gameList.get(i + 1);
			
			int compareToResult =
					gameOne.getDate().compareTo(gameTwo.getDate());
					
			if(compareToResult < 0 ) {
				
				Game temp = gameOne;
				gameList.set(i,gameList.get(i+1));
				gameList.set(i+1, temp);
				i = -1;
				
			}
			
		}
	
		
		trans.commit();
		session.close();
		
		return gameList;
	}
	
	/** GET ALL LOCATIONS */ 
	public List<Location> getLocations() throws JDOMException, IOException{
		
		Session session = sessionFactory.openSession();
		Transaction trans = session.beginTransaction();
		
		Query query = session.createQuery("SELECT l FROM Location l", Location.class);
		List<Location> allLocations = query.getResultList();
		
		for(int location = 0 ; location < allLocations.size() ; location++) {
			
			// send request to REST API napiarfolyam.hu//
			RestTemplate restT = new RestTemplate();
			XMLParser parser = new XMLParser();
			String xml = restT.getForObject("http://api.napiarfolyam.hu/?valuta=eur", String.class);
			
			// calculate average EUR price (in Forint)//
			ArrayList<Double> eurList = parser.getEUR(xml);
			double sumEur = 0;
			
			for(int i = 0; i < eurList.size(); i++) {
				
				sumEur += eurList.get(i);
			}		
			double averageEur = sumEur / eurList.size();
			
			// calculate the rental price from Forint to EUR and set rentEur attribute
			double rentEur = allLocations.get(location).getRent() / averageEur;
			allLocations.get(location).setRentEUR(rentEur);
			
		}
		
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
	
	// GET LOCATION BY ID //
	public Location getLocationById(int id) throws JDOMException, IOException {
		
		Location location = null;
		
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		RestHandler rh = new RestHandler();
		
		location = session.get(Location.class, id);		
		
		// FROM RESTHANDLER
		double eur = rh.getEur(location);
		
		location.setRentEUR(eur);
		
		tr.commit();
		session.close();
		
		return location;
	}

	
	public void close() {
		sessionFactory.close();
	}

}
