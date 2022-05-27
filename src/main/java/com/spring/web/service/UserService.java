package com.spring.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.web.entity.User;
import com.spring.web.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	// Save operation
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	// Read operation
	public List<User> fetchAll() {
		return (List<User>) userRepository.findAll();
	}
	
	// Read operation
	public List<User> fetchByRole(String role) {
		return (List<User>) userRepository.findByRole(role);
	}

	public Optional<User> fetchById(String id) {
		return (Optional<User>) userRepository.findById(id);
	}
}
