package com.spring.web.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.web.repository.UserRepository;


@Service
public class DbUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// Search user in database
		Optional<com.spring.web.entity.User> user = userRepository.findById(username);

		if (Objects.nonNull(user) && !user.isEmpty()) {
			
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        authorities.add(new SimpleGrantedAuthority(user.get().getRole()));
			
			return new User(username, user.get().getPassword(), authorities);
		} else {
			throw new UsernameNotFoundException("Login and password are not found");
		}
	}
}
