package pti.sb_squash_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pti.sb_squash_mvc.database.Database;
import pti.sb_squash_mvc.model.User;


@Controller
public class LoginController {
	
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
