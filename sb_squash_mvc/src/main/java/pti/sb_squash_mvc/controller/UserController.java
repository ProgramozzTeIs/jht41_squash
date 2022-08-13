package pti.sb_squash_mvc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pti.sb_squash_mvc.database.Database;
import pti.sb_squash_mvc.model.Game;
import pti.sb_squash_mvc.model.Location;
import pti.sb_squash_mvc.model.User;



@Controller
public class UserController {

	@GetMapping("/searchuser")
	public String searchuser(Model model, @RequestParam(name = "logedinuser") int linuserid ,@RequestParam(name = "user") int userId) {
		
		String html = "";
		
		Database db = new Database();
		
		User user = db.getUserById(linuserid);
		
		if(user.isLogin()) {
		
			List<Game> games = db.getGamesByUserId(userId);
			
			List<User> users = db.getPlayers();
			
			List<Location> locations = db.getLocations();
			
			model.addAttribute("users", users);
			model.addAttribute("locations", locations);
			model.addAttribute("games", games);
			model.addAttribute("user", user);
			html = "user.html";
		}
		else {
			model.addAttribute("feedback", "you have to log in");
			html = "login.html";
		}
		
		return html;
	}
	
}
