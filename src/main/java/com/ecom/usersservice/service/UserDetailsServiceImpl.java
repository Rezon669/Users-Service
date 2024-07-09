package com.ecom.usersservice.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ecom.usersservice.dao.UsersRepository;
import com.ecom.usersservice.model.Users;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Users> userRes = usersRepository.findByEmailid(email);
		if (!userRes.isPresent())
			throw new UsernameNotFoundException("Could not find User with email = " + email);
		Users user = userRes.get();
		return new org.springframework.security.core.userdetails.User(email, user.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
	}
}