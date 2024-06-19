package com.ecom.usersservice.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecom.usersservice.dao.UsersRepository;
import com.ecom.usersservice.exception.CustomException;
import com.ecom.usersservice.model.Login;
import com.ecom.usersservice.model.Users;
import com.ecom.usersservice.service.UsersService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/easybuy/user")

public class UsersController {

	private static final Logger logger = LogManager.getLogger(UsersController.class);
	//public String jwttoken;
	@Autowired
	UsersRepository usersRepository;

	public String jwttoken;

	
	@Autowired
	private UsersService usersService;


//	public UsersController(UsersService usersService) {
//        this.usersService = usersService;
//   }

	@GetMapping("/signin")
	public String showLoginpage() {
		return "signin";
	}

	@PostMapping("/createaccount")
	public String createUser(Users user, Model model) throws CustomException {
		// method to create account

		try {

			usersService.createUsers(user);
			logger.info("User registred Successfully");

			// TokenStorage.setJwtToken(jwttoken);
			return "backtologin";

		} catch (IllegalArgumentException e) {

			model.addAttribute("errorMessage", e.getMessage());
			logger.error(e);
			// throw e;
			return "signup";

		}

	}

	



	@GetMapping("/resetpassword")
	public String emailVerification() {
		return "resetpassword";
	}

	@PostMapping("/updatepassword")
	public String resetPassword(@RequestParam(value = "emailid", required = true) String emailid,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "confirmpassword", required = true) String confirmpassword, Model model) {
		try {

			// usersservice.emailVerification(emailid);
			usersService.updatePassword(password, confirmpassword, emailid);

			logger.info("Password updated successfully");
			return "passwordupdate";

		} catch (IllegalArgumentException e) {

			model.addAttribute("errorMessage", e.getMessage());
			logger.error(e);
			return "resetpassword";
		}
	}

	@GetMapping("/signup")
	public String signup() {

		return "signup";
	}

	@GetMapping("/auth/backtohome")
	public String backtoHome() {

		return "welcome";

	}
	
	@GetMapping("/auth/getUsers")	
	public List<Users> getUsers() {
		List<Users> list =usersService.getAllUsers();
		System.out.println(list);
        return list;
    }
	

	@PostMapping("/authenticate")
	// method to perform login validation
	public String loginValidation(Login login, Model model, HttpServletResponse response) {
		
		try {
			jwttoken = usersService.loginValidation(login.getEmailid(), login.getPassword());

			if (jwttoken.isEmpty()) {

				model.addAttribute("errorMessage", "invalid Login Credentials");
				return "signin";

			}

			Cookie jwtcookie = new Cookie("token", jwttoken);

			// Set additional cookie attributes
			jwtcookie.setHttpOnly(true); // Makes the cookie accessible only by the server
			jwtcookie.setSecure(true); // Ensures the cookie is sent only over HTTPS
			jwtcookie.setPath("/"); // Restricts the cookie to the root path
			jwtcookie.setMaxAge(3600);

			response.addCookie(jwtcookie);
			return "welcome";

		} catch (CustomException e) {

			model.addAttribute("errorMessage", e.getMessage());
			logger.error(e);
			return "signin";
		}
	}

	@GetMapping("/auth/logout")
	public String logout(HttpServletResponse response) {
		// method to perform logout validation
		Cookie jwtCookie = new Cookie("token", "");
		jwtCookie.setMaxAge(0);
		jwtCookie.setPath("/");
		response.addCookie(jwtCookie);
		return "signin"; // redirect to the signin page after logout
	}

	
	}
