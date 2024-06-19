package com.ecom.usersservice.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JWTUtil {

	@Value("${jwt_secret}")
	private String secret;
	
	@Value("${token_expire_time}")
	private long tokenExpire;
	
	

	public String generateToken(String email, String role) throws IllegalArgumentException, JWTCreationException {
		
		
		return JWT.create().withSubject("User Details").withIssuer("EasyBuy").withClaim("email", email)
				.withClaim("role", role).withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + tokenExpire)).sign(Algorithm.HMAC256(secret));
	}

	public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {

		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withSubject("User Details").withIssuer("EasyBuy")
				.acceptExpiresAt(System.currentTimeMillis()).build();

		DecodedJWT jwt = verifier.verify(token);

		Date expiration = jwt.getExpiresAt();

		if (expiration.before(new Date())) {

			throw new TokenExpiredException(token, null);

		}

		return jwt.getClaim("email").asString();
	}
}
