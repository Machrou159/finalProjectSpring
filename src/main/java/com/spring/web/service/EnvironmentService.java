package com.spring.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.web.entity.Environment;
import com.spring.web.repository.EnvironmentRepository;

@Service
public class EnvironmentService {

	@Autowired
	private EnvironmentRepository environmentRepository;

	// Save operation
	public Environment saveEnvironment(Environment Environment) {
		return environmentRepository.save(Environment);
	}

	// Read operation
	public List<Environment> fetchAll() {
		return (List<Environment>) environmentRepository.findAll();
	}

	public Optional<Environment> fetchById(Integer id) {
		return (Optional<Environment>) environmentRepository.findById(id);
	}
}
