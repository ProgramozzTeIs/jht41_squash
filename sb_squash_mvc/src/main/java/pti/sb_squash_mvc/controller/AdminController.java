package pti.sb_squash_mvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.jdom2.JDOMException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pti.sb_squash_mvc.database.Database;
import pti.sb_squash_mvc.model.Game;
import pti.sb_squash_mvc.model.Location;
import pti.sb_squash_mvc.model.User;
import pti.sb_squash_mvc.services.XMLParser;

@Controller
public class AdminController {
	
	
	@PostMapping("/admin/regplayer")
	public String regPlayer(Model model, @RequestParam(name = "name") String name) throws JDOMException, IOException {
		
		Database db = new Database();
		
		User user = new User();
		
		user.setType("user");
		
		user.setName(name);
		
		user.setFirstlogin(true);
		
		user.setLogin(false);
		
		user.setId(0);
		
		Integer pwd = new Random().nextInt(1000);
		
		String password = pwd.toString();
		
		user.setPwd(password);
		
		db.regUser(user);
		
		
		model.addAttribute("user", user);
		model.addAttribute("userList", db.getPlayers());
		model.addAttribute("locationList", db.getLocations());
		model.addAttribute("feedbackRegPlayer", "succesful registration");
		
		
		db.close();
		
		
		return "admin.html";
	}
	
	/** LOCATION REGISTRATION */
	@PostMapping("/admin/reglocation")
	public String regLocation(
						Model model,
						@RequestParam(name = "locationName") String locationName,
						@RequestParam(name = "address") String address,
						@RequestParam(name = "rent") int rent,
						@RequestParam(name = "userid") int userid
			) throws JDOMException, IOException {
		
		Location newLocation = new Location();
		newLocation.setAddress(address);
		newLocation.setName(locationName);
		newLocation.setRent(rent);
		
		Database db = new Database();
		
		db.regLocation(newLocation);
			
		model.addAttribute("feedbackRegLocation", "Location saved.");
		model.addAttribute("user", db.getUserById(userid));
		model.addAttribute("locationList", db.getLocations());
		model.addAttribute("userList", db.getPlayers());
		
		
		db.close();
		
		return "admin.html";
	
	}
	
	/** GAME REGISTRATION
	 * @throws IOException 
	 * @throws JDOMException */
	@PostMapping("/admin/reggame")
	public String regGame(
						Model model,
						@RequestParam(name = "userOneId") int userOneId,
						@RequestParam(name = "userTwoId") int userTwoId,
						@RequestParam(name = "userOnePoints") int userOnePoints,
						@RequestParam(name = "userTwoPoints") int userTwoPoints,
						@RequestParam(name = "locationId") int locationId,
						@RequestParam(name = "date") String date,
						@RequestParam(name = "userid") int userid
			) throws JDOMException, IOException {
		
		Database db = new Database();
		
		if( (db.getLocationById(locationId) != null) && (db.getUserById(userOneId) != null) && (db.getUserById(userTwoId) != null) ) {
			
			Game newGame = new Game();
			
			newGame.setUserOneId(userOneId);
			newGame.setUserTwoId(userTwoId);
			newGame.setUserOnePoint(userOnePoints);
			newGame.setUserTwoPoint(userTwoPoints);
			newGame.setLocationId(locationId);
			newGame.setDate(date);
			
			db.regGame(newGame);
			
			model.addAttribute("feedbackRegGame", "Game saved.");
			model.addAttribute("user", db.getUserById(userid));
			model.addAttribute("locationList", db.getLocations());
			model.addAttribute("userList", db.getPlayers());
			
			
			db.close();
			
		}
		else {
		
			model.addAttribute("feddbackRegGame", "ERROR: Wrong user ID or location ID!");
			
			
			model.addAttribute("user", db.getUserById(userid));
			model.addAttribute("locationList", db.getLocations());
			model.addAttribute("userList", db.getPlayers());
		}
		
		return "admin.html";
		
	}
	
	@GetMapping("/admin/export")
	public String exportDBToXML(Model model,
			@RequestParam(name="userid") int userid) throws IOException, JDOMException {
		
		XMLParser pr = new XMLParser();
		Database db = new Database();
		
		pr.saveusersTOXML(db.getPlayers());
		pr.saveLocationToXML(db.getLocations());
		pr.saveGamesToXML(db.getGames());
		
		
		model.addAttribute("user", db.getUserById(userid));
		model.addAttribute("locationList", db.getLocations());
		model.addAttribute("userList", db.getPlayers());
		model.addAttribute("feedbackExport","Export was successfull");
		db.close();
		
		
		return "admin.html";
	}
	
	@GetMapping("/admin/import")
	public String importDBFromXML(Model model, 
			@RequestParam(name= "userid") int userid,
			@RequestParam(name= "ordergames") String gameorder,
			@RequestParam(name= "orderlocations") String locationorder,
			@RequestParam(name= "orderusers") String userorder) throws JDOMException, IOException
			{
			
		XMLParser ps = new XMLParser();
		Database db = new Database();
		
		ArrayList<Game> gameList = ps.addGamesFromXML(gameorder);
		
		for(int g = 0 ; g < gameList.size() ; g++) {
			
			Game game = gameList.get(g);
			db.regGame(game);
			
		}
		
		ArrayList<Location> locationList = ps.addLocationFromXML(locationorder);
		
		for( int l = 0 ; l < locationList.size() ; l ++) {
			
			Location loc = locationList.get(l);
			db.regLocation(loc);
		}
		ArrayList<User> userList = ps.addUserFromXML(userorder);
		
		for(int u = 0 ; u < userList.size() ; u++) {
			
			User newUser = userList.get(u);
			db.regUser(newUser);
		}
		
		model.addAttribute("user", db.getUserById(userid));
		model.addAttribute("locationList", db.getLocations());
		model.addAttribute("userList", db.getPlayers());
		model.addAttribute("feedbackImport","Import was successfull");
		
		db.close();
		
		return "admin.html";
	}

}
