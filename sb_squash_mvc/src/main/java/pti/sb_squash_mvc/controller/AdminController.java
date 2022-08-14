package pti.sb_squash_mvc.controller;

import java.io.IOException;
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

@Controller
public class AdminController {
	
	
	@GetMapping("/admin/regplayer")
	public String regPlayer(Model model, @RequestParam(name = "name") String name) {
		
		Database db = new Database();
		
		User user = null;
		
		user.setType("user");
		
		user.setName(name);
		
		user.setFirstlogin(true);
		
		user.setLogin(false);
		
		user.setId(0);
		
		Integer pwd = new Random().nextInt(1000);
		
		String password = pwd.toString();
		
		user.setPwd(password);
		
		db.regUser(user);
		
//		model.addAttribute("users", db.getPlayers());
//		model.addAttribute("locations", db.getLocations());
//		model.addAttribute("users", db.getGames());
		
		
		db.close();
		
		
		return "admin.html";
	}
	
	/** LOCATION REGISTRATION */
	@PostMapping("/admin/reglocation")
	public String regLocation(
						Model model,
						@RequestParam(name = "locationName") String locationName,
						@RequestParam(name = "address") String address,
						@RequestParam(name = "rent") int rent
			) throws JDOMException, IOException {
		
		Location newLocation = new Location();
		newLocation.setAddress(address);
		newLocation.setName(locationName);
		newLocation.setRent(rent);
		
		Database db = new Database();
		
		db.regLocation(newLocation);
			
		model.addAttribute("feedbackRegLocation", "Location saved.");
		//model.addAttribute("user", db.getUserById(0)); -- INPUTBA KELLENE AZ AKTUÁLIS USER ID
		model.addAttribute("locations", db.getLocations());
		model.addAttribute("players", db.getPlayers());
		model.addAttribute("games", db.getGames()); // Ezt most nem kérte a feladat de forntenden ott van :)
		
		db.close();
		
		return "admin.htlm";
	
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
						@RequestParam(name = "date") String date
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
			
			model.addAttribute("feddbackRegGame", "Game saved.");
			//model.addAttribute("user", db.getUserById(0)); -- INPUTBA KELLENE AZ AKTUÁLIS USER ID
			model.addAttribute("locations", db.getLocations());
			model.addAttribute("players", db.getPlayers());
			model.addAttribute("games", db.getGames()); // Ezt most nem kérte a feladat de forntenden ott van :)
			
			db.close();
			
		}
		else {
		
			model.addAttribute("feddbackRegGame", "ERROR: Wrong user ID or location ID!");
			
		}
		
		return "admin.html";
		
	}
}
