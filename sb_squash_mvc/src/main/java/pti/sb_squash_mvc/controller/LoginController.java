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
public class LoginController {
	
	@GetMapping("/")
	public String loadLoginpage() {
		
		return "login.html";
	}
	
	@PostMapping("/login")
	public String checkLogin(Model model, @RequestParam(name="name") String name, @RequestParam(name="pwd") String pwd) throws JDOMException, IOException {
		
		String page = "";
		
		Database db = new Database();
		
		User user = db.getUserByNameAndPwd(name, pwd);
		
		if(user != null) {
			
			user.setLogin(true);
			db.updateUser(user);
			
			if(user.getType().equals("user")) {
				
				if(user.isFirstlogin()) {
					
					user.setFirstlogin(false);
					db.updateUser(user);
				
					model.addAttribute("userid", user.getId());
					
					page = "changepwd.html";
				
				} else {
					
					List<Game> gameList = db.getGames();
					List<Location> locationList = db.getLocations();
					List<User> userList = db.getPlayers();
					
					model.addAttribute("userList", userList);
					model.addAttribute("locationList", locationList);
					model.addAttribute("gameList", gameList);
					model.addAttribute("user", user);
					System.out.println(gameList);
					page = "user.html";
				}
				
			} else {
				
				List<Location> locationList = db.getLocations();
				List<User> userList = db.getPlayers();
				
				model.addAttribute("userList", userList);
				model.addAttribute("locationList", locationList);
				model.addAttribute("user", user);
				
				page = "admin.html";
			}
			
		} else {
			page = "login.html";
			model.addAttribute("feedback","Invalid username or password!");
		}
		
		db.close();
		
		return page;		
	}
	
	@GetMapping("/logout")
	public String logout(Model model, @RequestParam(name = "userid") int userId) {
		
		Database db = new Database();
		
		User user = db.getUserById(userId);
		
		user.setLogin(false);
		
		db.updateUser(user);
		
		db.close();
		
		model.addAttribute("feedback", "Succesful log out!");
		
		return "login.html";
	}
	

}
