package com.spring.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.web.entity.Urgence;
import com.spring.web.repository.UrgenceRepository;

@Service
public class UrgenceService {

	@Autowired
	private UrgenceRepository urgenceRepository;

	// Save operation
	public Urgence saveUrgence(Urgence urgence) {
		return urgenceRepository.save(urgence);
	}

	// Read operation
	public List<Urgence> fetchAll() {
		return (List<Urgence>) urgenceRepository.findAll();
	}

	public Optional<Urgence> fetchById(Integer id) {
		return (Optional<Urgence>) urgenceRepository.findById(id);
	}
}
