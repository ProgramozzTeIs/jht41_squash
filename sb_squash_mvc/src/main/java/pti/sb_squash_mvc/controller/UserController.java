package pti.sb_squash_mvc.controller;

import java.io.IOException;
import java.util.List;

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
public class UserController {

	@GetMapping("/searchuser")
	public String searchuser(Model model, @RequestParam(name = "logedinuser") int linuserid ,@RequestParam(name = "user") int userId) throws JDOMException, IOException {
		
		String html = "";
		
		Database db = new Database();
		
		User user = db.getUserById(linuserid);
		
		if(user.isLogin()) {
		
			List<Game> games = db.getGamesByUserId(userId);
			
			List<User> users = db.getPlayers();
			
			List<Location> locations = db.getLocations();
			
			model.addAttribute("userList", users);
			model.addAttribute("locationList", locations);
			model.addAttribute("gameList", games);
			model.addAttribute("user", user);
			html = "user.html";
		}
		else {
			model.addAttribute("feedback", "you have to log in");
			html = "login.html";
		}
		
		return html;
	}
	
	@GetMapping("/searchlocation")
	public String searchLocation(Model model,
			@RequestParam(name= "loggedInUserId") int userId,
			@RequestParam(name= "locationId") int locationId
			) throws JDOMException, IOException {
		
		String page = "";
	
		
		Database db = new Database();
		User user = db.getUserById(userId);
		
		if(user.isLogin() == false) {
			
			model.addAttribute("feedback", "you have to log in");
			
			page = "login.html";
			
		}else {
			List<User> userList = db.getPlayers();
			List<Game> gameList = db.getGamesByLocationId(locationId);
			List<Location> locationList = db.getLocations();
			
			page = "user.html";
			
			model.addAttribute("user", user);
			model.addAttribute("userList", userList);
			model.addAttribute("gameList", gameList);
			model.addAttribute("locationList",locationList);
		}
	
		db.close();
		
		return page;
	}
	
	@PostMapping("/change")
	public String changePassword(Model model,
			@RequestParam(name= "userId") int userId,
			@RequestParam(name= "password") String pwd
			) throws JDOMException, IOException {
		
		String page = "";
		
		Database db = new Database();
		User user = db.getUserById(userId);
		
		
		
		if(user.isLogin() == false || user.getType().equals("user") == false) {
			
			
			model.addAttribute("feedback", "you have to log in");
			
			page = "login.html";
			
		}else {
			
			user.setPwd(pwd);
			db.updateUser(user);
			List<User> userList = db.getPlayers();
			List<Game> gameList = db.getGames();
			List<Location> locationList = db.getLocations();
			
			page = "user.html";
			
			model.addAttribute("user", user);
			model.addAttribute("userList", userList);
			model.addAttribute("gameList", gameList);
			model.addAttribute("locationList", locationList);	
			
		}
		db.close();
		

		return page;
	}

}
