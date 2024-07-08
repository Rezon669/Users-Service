//package com.ecom.usersservice.jwt;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.ecom.usersservice.serviceimpl.UserDetailsServiceImpl;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class JWTFilter extends OncePerRequestFilter {
//	private static final Logger logger = LogManager.getLogger(JWTFilter.class);
//	private static final String SECRET_KEY = "dfjhb356kbkbj236bkjbqyhdbqiudbsh";
//
//	@Autowired
//	private UserDetailsServiceImpl userDetailsService;
//
//	@Autowired
//	private JWTUtil jwtUtil;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		String authHeader = request.getHeader("Authorization");
//
//		String cookieString = null;
//		String token = null;
//		if (authHeader == null) {
//			cookieString = request.getHeader("Cookie");
//
//			// cookieString=userService.getDecryptedToken(cookieString);
//			if (cookieString != null && cookieString.contains("token")) {
//				try {
//					// System.out.println("Without encryption " + cookieString);
//					String[] parts = cookieString.split("; ");
//
//					for (String part : parts) {
//						if (part.startsWith("token=")) {
//							// Extract token part after 'token='
//							token = part.substring(6);
//
//							break; // Token found, exit the loop
//						}
//					}
//					Cipher cipher = Cipher.getInstance("AES");
//					SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
//					// SecretKeySpec secretKey = new
//					// SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
//					cipher.init(Cipher.DECRYPT_MODE, secretKey);
//
//					byte[] decodedBytes = Base64.getDecoder().decode(token);
//					byte[] decryptedBytes = cipher.doFinal(decodedBytes);
//					String decryptedToken = new String(decryptedBytes);
//
//					token = decryptedToken;
//					// return decryptedToken;
//				} catch (Exception e) {
//					// Handle decryption error
//					logger.error(e);
//				}
//				// System.out.println("This is the token after decryption " + token);
//
//				if (token != null) {
//					authHeader = "Bearer " + token.trim();
//
//				} else {
//					logger.error("Token not found");
//				}
//			}
//		}
//		if (authHeader != null && !authHeader.isEmpty() && authHeader.startsWith("Bearer ")) {
//			String jwt = authHeader.substring(7);
//			if (jwt == null || jwt.isEmpty()) {
//				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
//			} else {
//				try {
//					String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);
//					UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email,
//							userDetails.getPassword(), userDetails.getAuthorities());
//					if (SecurityContextHolder.getContext().getAuthentication() == null) {
//						SecurityContextHolder.getContext().setAuthentication(authToken);
//					}
//				} catch (JWTVerificationException exc) {
//					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
//				}
//			}
//
//		}
//
//		filterChain.doFilter(request, response);
//
//	}
//}