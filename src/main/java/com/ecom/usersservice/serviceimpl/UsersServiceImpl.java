package com.ecom.usersservice.serviceimpl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.usersservice.dao.UsersRepository;
import com.ecom.usersservice.exception.CustomException;
import com.ecom.usersservice.model.Users;
import com.ecom.usersservice.security.JWTUtil;
import com.ecom.usersservice.service.EmailService;
import com.ecom.usersservice.service.EncryptDecryptService;
import com.ecom.usersservice.service.UsersService;

@Service

public class UsersServiceImpl implements UsersService{
	private static final Logger logger = LogManager.getLogger(UsersServiceImpl.class);

	private static final String SECRET_KEY = "dfjhb356kbkbj236bkjbqyhdbqiudbsh";

	@Autowired
	private UsersRepository usersRepository;
	
	
	@Autowired
	private JWTUtil jwtUtil;

	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@Autowired
	EncryptDecryptService encryptDescryptService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//  @Autowired
	 //   private AuditLogRepository auditLogRepository;

	public UsersServiceImpl(EmailService emailService) {
		this.emailService = emailService;

	}
	
	@Autowired
	public void setAuthManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	

	//AuditLog auditLog = new AuditLog();



	public String createUsers(Users user) throws CustomException {

		if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getMobilenumber().isEmpty()
				|| user.getEmailid().isEmpty() || user.getCity().isEmpty() || user.getRole().isEmpty()) {
			logger.warn("All the fields are mandataroy fields");
			throw new CustomException("All the fields are mandataroy fields");
		} else if (usersRepository.findByUsername(user.getUsername()).isPresent()) {
			logger.warn("Username already exists");
			throw new CustomException("Username already exists");
		} else if (usersRepository.findByEmailid(user.getEmailid()).isPresent()) {
			logger.warn("Email id already exists");
			throw new CustomException("Email id already exists");
		}

		String emailid = user.getEmailid();
		String phno = user.getMobilenumber();

		boolean isValidEmail = isValidGmailAddress(emailid);
		boolean isValidPhno = isValidPhoneNumber(phno);

		if (isValidEmail) {
			if (isValidPhno) {

			} else {
				throw new CustomException("Mobile number is not valid");
			}
		} else {
			throw new CustomException("Email id is not valid");
		}

		String encodedPass = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPass);
		usersRepository.save(user);

		// token = jwtUtil.generateToken(user.getEmailid(), user.getRole());

		logger.info("Sending welcome email to the given emailid");

		String subject = "EasyBuy: Welcome Email";

		String body = "Hi..." + user.getUsername() + "\n" + "This is the mail form the EasyBuy.\n"
				+ "Your EasyBuy account has been successfully created\n"
				+ "Kindly reach out to us if any information required.\n" + "\n" + "Thank you";

		// MailTemplate template =
		// mailTemplateRepository.findBytemplatename("usercreation");

		String toEmail = user.getEmailid();
		try {
			// emailService.sendEmail(toEmail, subject, body);
			emailService.sendEmail(toEmail, subject, body);

			logger.info("Successfully sent the welcome email to the given emailid");

		} catch (Exception e) {

			logger.info("Failed to send welcome email");
			logger.error(e);
		}

		return "created";
	}

	
	public void updatePassword(String password, String confirmpassword, String emailid) {

		if (emailid.isEmpty()) {
			logger.warn("Please enter the emaild");
			throw new IllegalArgumentException("Please enter the emaild");
		} else if (password.isEmpty() || confirmpassword.isEmpty()) {
			logger.warn("Please enter the Passwords");
			throw new IllegalArgumentException("Please enter the Passwords");
		} else if (!password.equals(confirmpassword)) {
			logger.warn("Passwords didn't match");
			throw new IllegalArgumentException("Passwords didn't match");
		}

		String name = usersRepository.findByUser(emailid);

		if (name != null) {
			// String hashpassowrd = generateHashvalue(name, password);
			String encodedPass = passwordEncoder.encode(password);

			usersRepository.updatePasswordByEmailid(encodedPass, emailid);
			
			
		//	auditLog.setUsername(name);
		//	auditLog.setLogged_at(LocalDateTime.now());
		//	auditLog.setAction("Updated the Password");
			
		//	auditLogRepository.save(auditLog);

			String subject = "EasyBuy: Account Password Updated";

			String body = "Hi " + name + "\n" + "This is the mail form the Easybuy.\n"
					+ "Your EasyBuy account password has been updated\n"
					+ "Kindly reach out to us if any information required.\n" + "\n" + "Thank you";
			String toEmail = emailid;
			try {
				emailService.sendEmail(toEmail, subject, body);
			} catch (Exception e) {
				logger.error(e);
			}
		} else {

			logger.error("Given emaild is not found");
			throw new IllegalArgumentException("Given emaild is not found");
		}

	}

	// method to generate hash value
	/*
	 * public String generateHashvalue(String name, String password) {
	 * 
	 * String genratedHashvalue = name + password;
	 * 
	 * try { MessageDigest digest = MessageDigest.getInstance("SHA-256"); byte[]
	 * hash = digest.digest(genratedHashvalue.getBytes(StandardCharsets.UTF_8));
	 * 
	 * // Convert byte array to a hexadecimal representation StringBuilder
	 * hexStringBuilder = new StringBuilder(); for (byte b : hash) { String hex =
	 * Integer.toHexString(0xff & b); if (hex.length() == 1) {
	 * hexStringBuilder.append('0'); } hexStringBuilder.append(hex); }
	 * 
	 * return hexStringBuilder.toString(); } catch (NoSuchAlgorithmException e) {
	 * e.printStackTrace(); return null; } }
	 * 
	 */
	/*
	 * public String setEncryptedToken(String jwttoken) { // String jwtToken =
	 * "YourJWTToken"; // Replace with your actual JWT token
	 * 
	 * try { Cipher cipher = Cipher.getInstance("AES"); SecretKeySpec secretKey =
	 * new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
	 * cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	 * 
	 * byte[] encryptedBytes = cipher.doFinal(jwttoken.getBytes()); String
	 * encryptedToken = Base64.getEncoder().encodeToString(encryptedBytes);
	 * System.out.println(encryptedToken); return encryptedToken; } catch (Exception
	 * e) { // Handle encryption error logger.error(e); return "Error: " +
	 * e.getMessage(); } }
	 */

	// Additional code to decrypt the token when needed

	/*
	 * public String getDecryptedToken(String cookieString) { if
	 * (!cookieString.isEmpty()) { try { Cipher cipher = Cipher.getInstance("AES");
	 * SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
	 * cipher.init(Cipher.DECRYPT_MODE, secretKey);
	 * 
	 * byte[] decodedBytes = Base64.getDecoder().decode(cookieString); byte[]
	 * decryptedBytes = cipher.doFinal(decodedBytes); String decryptedToken = new
	 * String(decryptedBytes);
	 * 
	 * return decryptedToken; } catch (Exception e) { // Handle decryption error
	 * return "Decryption error: " + e.getMessage(); } } else { return
	 * "No encrypted token found in the cookie"; } }
	 */

	public static boolean isValidPhoneNumber(String phoneNumber) {
		// This regex matches common formats of phone numbers, but might need
		// adjustments based on your needs
		String regex = "^(\\d{10}|\\d{3}-\\d{3}-\\d{4}|\\(\\d{3}\\) \\d{3}-\\d{4})$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}

	public static boolean isValidGmailAddress(String email) {
		// "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
		String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}


	
		public List<Users> getAllUsers() {
	        return usersRepository.findAll();
	    }
	


		public String loginValidation(String email, String password) throws CustomException {

			String jwtToken = null;
			if (email.isEmpty() || password.isEmpty()) {
				logger.warn("Please enter the Emailid & Password");
				throw new CustomException("Please enter the Emailid & Password");
			}

			if (usersRepository.findByEmailid(email).isEmpty()) {
				logger.warn("EmailID doestn't exists");
				throw new CustomException("EmailID doestn't exist");
			}

			try {
				String role = usersRepository.findEmailid(email);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, password);
				Authentication authentication = authenticationManager.authenticate(auth);

				if (authentication.isAuthenticated()) {

					jwtToken = jwtUtil.generateToken(email, role);
					
					
				//	 System.out.println(jwtToken);
					// String encryptedToken = setEncryptedToken(jwtToken);

					// return encryptedToken;
					
					

				}
			} catch (Exception e) {

				throw new CustomException("Invalid Login Credentials");
			}
			//AuditLog auditLog = new AuditLog();
			String uname=usersRepository.findByUser(email);
			//auditLog.setUsername(uname);
			//auditLog.setLogged_at(LocalDateTime.now());
			//auditLog.setAction("Logged into Application");
			
			//auditLogRepository.save(auditLog);
			return setEncryptedToken(jwtToken);
		}

		public String setEncryptedToken(String jwttoken) {

			try {
				Cipher cipher = Cipher.getInstance("AES");
				SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);

				byte[] encryptedBytes = cipher.doFinal(jwttoken.getBytes(StandardCharsets.UTF_8));
				String encryptedToken = Base64.getEncoder().encodeToString(encryptedBytes);
				// System.out.println(encryptedToken);
				return encryptedToken;
			} catch (Exception e) {

				logger.error(e);
				return "Error: " + e.getMessage();
			}
		}
		


	
}