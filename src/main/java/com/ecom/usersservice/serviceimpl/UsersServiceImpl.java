package com.ecom.usersservice.serviceimpl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
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

import com.ecom.usersservice.dao.LoginRepository;
import com.ecom.usersservice.dao.UsersRepository;
import com.ecom.usersservice.exception.CustomException;
import com.ecom.usersservice.feignclient.JwtService;
import com.ecom.usersservice.model.LoginDetails;
import com.ecom.usersservice.model.Users;
import com.ecom.usersservice.service.EmailService;
import com.ecom.usersservice.service.EncryptDecryptService;

@Service
public class UsersServiceImpl {
    private static final Logger logger = LogManager.getLogger(UsersServiceImpl.class);
    private static final String SECRET_KEY = "dfjhb356kbkbj236bkjbqyhdbqiudbsh";
    
  @Autowired
    LoginRepository loginRepo;
  
  
@Autowired
  LoginDetails loginDetails;

@Autowired
JwtService jwtTokenService;

//    @Autowired
//    private UsersRepository usersRepository;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private EmailService emailService;
//
// //   @Autowired
//  //  private EncryptDecryptService encryptDecryptService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    public void setAuthManager(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//  
//
//    
//    public UsersServiceImpl(AuthenticationManager authenticationManager, EmailService emailService) {
//        this.authenticationManager = authenticationManager;
//        this.emailService = emailService;
//    }
    
    private final UsersRepository usersRepository;
  //  private final JWTUtil jwtService;
    private final EmailService emailService;
    private final EncryptDecryptService encryptDecryptService;
    private final PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public UsersServiceImpl(
        UsersRepository usersRepository,
  //      JWTUtil jwtService,
        EmailService emailService,
        EncryptDecryptService encryptDecryptService,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager
    ) {
        this.usersRepository = usersRepository;
   //     this.jwtService = jwtService;
        this.emailService = emailService;
        this.encryptDecryptService = encryptDecryptService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
 


//    @Bean
//     PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//     AuthenticationManager authenticationManager(@Lazy AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    public String createUsers(Users user) throws CustomException {
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getMobilenumber().isEmpty()
                || user.getEmailid().isEmpty() || user.getCity().isEmpty() || user.getRole().isEmpty()) {
            logger.warn("All the fields are mandatory fields");
            throw new CustomException("All the fields are mandatory fields");
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
                // continue
            } else {
                throw new CustomException("Mobile number is not valid");
            }
        } else {
            throw new CustomException("Email id is not valid");
        }

        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        usersRepository.save(user);

        logger.info("Sending welcome email to the given emailid");

        String subject = "EasyBuy: Welcome Email";
        String body = "Hi..." + user.getUsername() + "\n" + "This is the mail from EasyBuy.\n"
                + "Your EasyBuy account has been successfully created\n"
                + "Kindly reach out to us if any information required.\n" + "\n" + "Thank you";

        String toEmail = user.getEmailid();
        try {
            emailService.sendEmail(toEmail, subject, body);
            logger.info("Successfully sent the welcome email to the given emailid");
        } catch (Exception e) {
            logger.error("Failed to send welcome email", e);
        }

        return "created";
    }

    public void updatePassword(String password, String confirmpassword, String emailid) {
        if (emailid.isEmpty()) {
            logger.warn("Please enter the emailid");
            throw new IllegalArgumentException("Please enter the emailid");
        } else if (password.isEmpty() || confirmpassword.isEmpty()) {
            logger.warn("Please enter the Passwords");
            throw new IllegalArgumentException("Please enter the Passwords");
        } else if (!password.equals(confirmpassword)) {
            logger.warn("Passwords didn't match");
            throw new IllegalArgumentException("Passwords didn't match");
        }

        String name = usersRepository.findByUser(emailid);

        if (name != null) {
            String encodedPass = passwordEncoder.encode(password);
            usersRepository.updatePasswordByEmailid(encodedPass, emailid);

            String subject = "EasyBuy: Account Password Updated";
            String body = "Hi " + name + "\n" + "This is the mail from Easybuy.\n"
                    + "Your EasyBuy account password has been updated\n"
                    + "Kindly reach out to us if any information required.\n" + "\n" + "Thank you";
            String toEmail = emailid;
            try {
                emailService.sendEmail(toEmail, subject, body);
            } catch (Exception e) {
                logger.error(e);
            }
        } else {
            logger.error("Given emailid is not found");
            throw new IllegalArgumentException("Given emailid is not found");
        }
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
            logger.warn("EmailID doesn't exist");
            throw new CustomException("EmailID doesn't exist");
        }

        try {
            String role = usersRepository.findEmailid(email);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, password);
            loginDetails.setEmailid(email);
           // System.out.println(email);
            Authentication authentication = authenticationManager.authenticate(auth);

            if (authentication.isAuthenticated()) {
               // jwtToken = jwtService.generateToken(email, role);
            	
            	System.out.println(email +""+ role);
            	
            	jwtToken = jwtTokenService.generateToken(email, role);
            	
            	
            }
        } catch (Exception e) {
        	System.out.println(e);
            throw new CustomException("Invalid Login Credentials");
        }
      
       
        loginDetails.setIssuedAt(new Date());
        loginDetails.setToken(setEncryptedToken(jwtToken));
        loginDetails.setActive(1);
        
        loginRepo.save(loginDetails);
      
        

       // return setEncryptedToken(jwtToken);
        return jwtToken;
    }

    public String setEncryptedToken(String jwttoken) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(jwttoken.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            logger.error(e);
            return "Error: " + e.getMessage();
        }
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^(\\d{10}|\\d{3}-\\d{3}-\\d{4}|\\(\\d{3}\\) \\d{3}-\\d{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static boolean isValidGmailAddress(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
