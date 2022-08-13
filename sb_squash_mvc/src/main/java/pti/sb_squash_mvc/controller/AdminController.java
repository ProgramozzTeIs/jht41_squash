package pti.sb_squash_mvc.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pti.sb_squash_mvc.database.Database;
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

}
