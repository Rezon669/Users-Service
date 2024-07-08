package com.ecom.usersservice.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.usersservice.dao.UsersRepository;
import com.ecom.usersservice.exception.CustomException;
import com.ecom.usersservice.model.Login;
import com.ecom.usersservice.model.Users;
import com.ecom.usersservice.serviceimpl.UsersServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/easybuy/user")

public class UsersController {

	private static final Logger logger = LogManager.getLogger(UsersController.class);
	// public String jwttoken;
	@Autowired
	UsersRepository usersRepository;

	public String jwttoken;

	@Autowired
	UsersServiceImpl usersService;

//	public UsersController(UsersService usersService) {
//        this.usersService = usersService;
//   }

//    public UsersController(UsersService usersService) {
//        this.usersService = usersService;
//    }

	@GetMapping("/signin")
	public String showLoginpage() {
		return "signin";
	}

	@PostMapping("/createaccount")
	public ResponseEntity<String> createUser(Users user, Model model) throws CustomException {
		// method to create account

		try {

			usersService.createUsers(user);
			logger.info("User registred Successfully");

			// TokenStorage.setJwtToken(jwttoken);
			// return "backtologin";
			return ResponseEntity.accepted().body("User Created Successfully");

		} catch (CustomException e) {

			// model.addAttribute("errorMessage", e.getMessage());
			logger.error(e);
			// throw e;
			// return "signup";
			return ResponseEntity.badRequest().body(e + " Please check the details once");

		}

	}

	@GetMapping("/admin/resetpassword")
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
			return "Password updated successfully";

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
		List<Users> list = usersService.getAllUsers();
		System.out.println(list);
		return list;
	}

	@PostMapping("/authenticate")
	// method to perform login validation
	public ResponseEntity<String> loginValidation(Login login, Model model, HttpServletResponse response)
			throws CustomException {

		try {
			jwttoken = usersService.loginValidation(login.getEmailid(), login.getPassword());

			if (jwttoken.isEmpty()) {

				// model.addAttribute("errorMessage", "invalid Login Credentials");
				// return "signin";

				return ResponseEntity.notFound().build();

			}

//			Cookie jwtcookie = new Cookie("token", jwttoken);
//
//			// Set additional cookie attributes
//			jwtcookie.setHttpOnly(true); // Makes the cookie accessible only by the server
//			jwtcookie.setSecure(true); // Ensures the cookie is sent only over HTTPS
//			jwtcookie.setPath("/"); // Restricts the cookie to the root path
//			jwtcookie.setMaxAge(3600);
//
//			response.addCookie(jwtcookie);
			// return "welcome";
			return ResponseEntity.ok("Login Successful, Please find the JWT token :  " + jwttoken);

		} catch (CustomException e) {

			// model.addAttribute("errorMessage", e.getMessage());
			logger.error(e);
			// return "signin";
			return ResponseEntity.badRequest().body(e + "");
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
