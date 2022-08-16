package pti.sb_squash_mvc.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import pti.sb_squash_mvc.model.Game;
import pti.sb_squash_mvc.model.Location;
import pti.sb_squash_mvc.model.User;

public class XMLParser {
	
	public ArrayList<Double> getEUR(String xml) throws JDOMException, IOException {
		
		ArrayList<Double> eurList = new ArrayList<Double>();
		
		SAXBuilder sb = new SAXBuilder();
		StringReader sr = new StringReader(xml);
		Document doc = sb.build(sr);
		
		Element rootElement = doc.getRootElement();
		Element valutaElement = rootElement.getChild("valuta");
		List<Element> itemElements = valutaElement.getChildren("item");
		
		for(int i = 0; i < itemElements.size(); i++) {
			
			Element item = itemElements.get(i);
			Element eladasElement = item.getChild("eladas");
			double eur = Double.parseDouble(eladasElement.getValue());
			
			eurList.add(eur);
		}
		
		return eurList;
	}
	
	//** SAVE GAMES TO XMLFILE* /
	public String saveGamesToXML(List<Game> gameList) throws IOException {
		
		String xmlFile = "C:\\Users\\tamas\\OneDrive\\Desktop\\order\\games.xml";
		
			FileWriter writer = new FileWriter(xmlFile);
			XMLOutputter toXml = new XMLOutputter(Format.getPrettyFormat());
				
			Document doc = new Document();
			Element rootElement = new Element("games");
			doc.setRootElement(rootElement);
			
			/** ITERATE OVER GAMES ---> GAME*/
			for(int game = 0; game < gameList.size() ; game++) {
				
				Game g = gameList.get(game);
				
				/**<game>*/
				Element gameElement = new Element("game");
				
				
				/**<gamerOne>*/
				Element gamerOneElement = new Element("gamerOneId");
				gamerOneElement.setText(g.getUserOneId() + "");
				gameElement.addContent(gamerOneElement);
				
				/**<gamerOnePoints>*/
				Element gamerOnePointsElement = new Element("gamerOnePoints");
				gamerOnePointsElement.setText(g.getUserOnePoint() + "");
				gameElement.addContent(gamerOnePointsElement);
					
					
				/**<gamerTwo>*/
				Element gamerTwoElement = new Element("gamerTwoId");
				gamerTwoElement.setText(g.getUserTwoId() + "");
				gameElement.addContent(gamerTwoElement);
				
				/**<gamerTwoPoints>*/
				Element gamerTwoPointsElement = new Element("gamerTwoPoints");
				gamerTwoPointsElement.setText(g.getUserTwoPoint() + "");
				gameElement.addContent(gamerTwoPointsElement);
					
				/**<locationId>*/
				Element locationIdElement = new Element("locationId");
				locationIdElement.setText(g.getLocationId() + "");
				gameElement.addContent(locationIdElement);
					
				/**<date>*/
				Element dateElement = new Element("date");
				dateElement.setText(g.getDate());
				gameElement.addContent(dateElement);
					
				rootElement.addContent(gameElement);
			}
			
			toXml.output(doc, writer);
		
		return xmlFile;
		
	}
	
	//** SAVE LOCATION TO XML FILE */
	public String saveLocationToXML(List<Location> locationList) throws IOException {
		
		String xmlFile = "C:\\Users\\tamas\\OneDrive\\Desktop\\order\\locations.xml";
		
		FileWriter witer = new FileWriter(xmlFile);
		XMLOutputter toXml = new XMLOutputter(Format.getPrettyFormat());
		
		Document doc = new Document();
		Element rootElement = new Element("locations");
		doc.setRootElement(rootElement);
		
		
		/** ITERATE OVER LOCATIONS ---> LOCATION*/
		for(int location = 0; location < locationList.size() ; location++) {
			
			
			Location loc = locationList.get(location);
			
			/**<location>*/
			Element locationElement = new Element("location");
			
			/**<name>*/
			Element nameElement = new Element("name");
			nameElement.setText(loc.getName());
			locationElement.addContent(nameElement);
			
			/**<address>*/
			Element addressElement = new Element("address");
			addressElement.setText(loc.getAddress());
			locationElement.addContent(addressElement);
			
			/**<rent>*/
			Element rentElement = new Element("rent");
			rentElement.setText(loc.getRent() + "") ;
			locationElement.addContent(rentElement);
			
			
			rootElement.addContent(locationElement);
			
			
		}
		toXml.output(doc, witer);
	
				return xmlFile;
	}
	
	//** SAVE USERS TO XML FILE */
	public String saveusersTOXML(List<User> userList) throws IOException {
		
		String xmlFile = "C:\\Users\\tamas\\OneDrive\\Desktop\\order\\users.xml";
		
		FileWriter writer = new FileWriter(xmlFile);
		XMLOutputter toXml = new XMLOutputter(Format.getPrettyFormat());
		
		Document doc = new Document();
		Element rootElement = new Element("users");
		doc.setRootElement(rootElement);
		
		/** ITERATE OVER USERS ---> USERS*/
		for(int u = 0 ; u < userList.size() ; u++) {
			
			User user = userList.get(u);
			
			/**<user>*/
			Element userElement = new Element("user");
			
			/**<name>*/
			Element nameElement = new Element("name");
			nameElement.setText(user.getName());
			nameElement.setAttribute("password",user.getPwd());
			userElement.addContent(nameElement);
			
			/**<type>*/
			Element typeElement = new Element("type");
			typeElement.setText(user.getType());
			userElement.addContent(typeElement);
			
			/**<login>*/
			Element loginElement = new Element("login");
			loginElement.setText(user.isLogin() + "");
			userElement.addContent(loginElement);
			
			/**<firstlogin>*/
			Element firstLoginElement = new Element("firstLogin");
			firstLoginElement.setText(user.isFirstlogin()  + "");
			userElement.addContent(firstLoginElement);
			
			
			
			rootElement.addContent(userElement);
			
			
		}
		toXml.output(doc, writer);
		
		
		return xmlFile;
		
		
	}
	
	/**IMPORT GAMES FROM XML*/
	public ArrayList<Game> addGamesFromXML(String order) throws JDOMException, IOException{
		
		ArrayList<Game> gameList = new ArrayList<Game>();
		/** OPEN */
		SAXBuilder sb = new SAXBuilder();
		File file = new File(order);
		Document doc = sb.build(file);
		
		/** NAVIGATION */
		Element rootElement = doc.getRootElement();
		
		/**READ GAMES*/
		List<Element> gamesElement = rootElement.getChildren("game");
		
		for( int g = 0; g < gamesElement.size() ; g++) {
			
			Game game = new Game();
			
			Element gamerOneIdElement = gamesElement.get(g).getChild("gamerOneId");
			
				int gamerOneId = Integer.parseInt(gamerOneIdElement.getValue());
				
			Element gamerOnePoints = gamesElement.get(g).getChild("gamerOnePoints");
			
				int gamer1Points = Integer.parseInt(gamerOnePoints.getValue());
			
			Element gamerTwoIdElement = gamesElement.get(g).getChild("gamerTwoId");
				
				int gamerTwoId = Integer.parseInt(gamerTwoIdElement.getValue());
				
			Element gamerTwoPointsElement = gamesElement.get(g).getChild("gamerTwoPoints");
			
				int gamer2points = Integer.parseInt(gamerTwoPointsElement.getValue());
				
			Element locationIdElement = gamesElement.get(g).getChild("locationId");
			
				int locationId = Integer.parseInt(locationIdElement.getValue());
				
			Element dateElement = gamesElement.get(g).getChild("date");
			
				String date = dateElement.getValue();
				
			game.setUserOneId(gamerOneId);
			game.setUserTwoId(gamerTwoId);
			game.setUserOnePoint(gamer1Points);
			game.setUserTwoPoint(gamer2points);
			game.setLocationId(locationId);
			game.setDate(date);
			
			gameList.add(game);
		}
		
		
		
		return gameList;
	}
	
	/**IMPORT LOCATIONS FROM XML*/
	public ArrayList<Location> addLocationFromXML(String order) throws JDOMException, IOException{
		
		ArrayList<Location> locationList = new ArrayList();
		/** OPEN */
		SAXBuilder sb = new SAXBuilder();
		File file = new File(order);
		Document doc = sb.build(file);
		
		/** NAVIGATION */
		Element rootElement = doc.getRootElement();
		
		/**READ LOCATIONS*/
		List<Element> locationsElement = rootElement.getChildren("location");
		
		
		for(int l = 0 ; l < locationsElement.size() ; l++) {
			
			Location loc = new Location();
			
			Element nameElement = locationsElement.get(l).getChild("name");
				String name = nameElement.getValue();
				
			Element addressElement = locationsElement.get(l).getChild("address");
				String address = addressElement.getValue();
				
			Element rentElement = locationsElement.get(l).getChild("rent");
				int rent = Integer.parseInt(rentElement.getValue());
				
				loc.setName(name);
				loc.setAddress(address);
				loc.setRent(rent);
				
				locationList.add(loc);
			
		}
		
		
		return locationList;
	}
	/**IMPORT USERS FROM XML*/
	public ArrayList<User> addUserFromXML(String order) throws JDOMException, IOException{
		
		ArrayList<User> userList = new ArrayList<User>();
		/** OPEN */
		SAXBuilder sb = new SAXBuilder();
		File file = new File(order);
		Document doc = sb.build(file);
		
		/** NAVIGATION */
		Element rootElement = doc.getRootElement();
		
		/**READ USERS*/
		List<Element> usersElement = rootElement.getChildren("user");
		
		for(int u = 0 ; u < usersElement.size() ; u++ ) {
			
			User user = new User();
			
			Element nameElement = usersElement.get(u).getChild("name");
				String name = nameElement.getValue();
				String password = nameElement.getAttributeValue("password");
				
			Element typeElement = usersElement.get(u).getChild("type");
				String type = typeElement.getValue();
				
			Element loginElement = usersElement.get(u).getChild("login");
				String login = loginElement.getValue();
				
			Element firstloginElement = usersElement.get(u).getChild("firstLogin");
				String firstlogin = firstloginElement.getValue();
			
				user.setName(name);
				user.setPwd(password);
				user.setType(type);
				
				if(login.equals("false")) {
					
					user.setLogin(false);
				}else {
					user.setLogin(true);
				}
				
				if(firstlogin.equals("false")) {
					
					user.setFirstlogin(false);
				}else {
					user.setFirstlogin(true);
				}
				userList.add(user);
				
			
		}
		return userList;
	}

	

}
