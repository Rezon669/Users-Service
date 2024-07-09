package com.ecom.usersservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class UserServiceConfig {
	/*
	 * private final JWTFilter jwtFilter; private final UserDetailsServiceImpl
	 * userDetailsService;
	 * 
	 * public UserServiceConfig(JWTFilter jwtFilter, UserDetailsServiceImpl
	 * userDetailsService) { this.jwtFilter = jwtFilter; this.userDetailsService =
	 * userDetailsService; }
	 * 
	 * @SuppressWarnings("deprecation")
	 * 
	 * @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws
	 * Exception { http.csrf(csrf -> csrf.disable()).httpBasic(basic ->
	 * basic.disable()).cors().and() .authorizeRequests(requests ->
	 * requests.requestMatchers("/easybuy/user/signin").permitAll()
	 * .requestMatchers("/easybuy/user/**").permitAll().requestMatchers(
	 * "/easybuy/product/public/**")
	 * .authenticated().requestMatchers("/easybuy/user/admin/**").hasAuthority(
	 * "ADMIN") .requestMatchers("/easybuy/product/admin/**").hasAuthority("ADMIN"))
	 * .exceptionHandling(handling -> handling.authenticationEntryPoint((request,
	 * response, authException) ->
	 * response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")))
	 * .sessionManagement(management ->
	 * management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	 * 
	 * http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	 * 
	 * return http.build(); }
	 */

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).httpBasic(basic -> basic.disable());
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
