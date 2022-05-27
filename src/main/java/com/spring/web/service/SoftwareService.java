package com.spring.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.web.entity.Software;
import com.spring.web.repository.SoftwareRepository;

@Service
public class SoftwareService {

	@Autowired
	private SoftwareRepository softwareRepository;

	// Save operation
	public Software saveSoftware(Software Software) {
		return softwareRepository.save(Software);
	}

	// Read operation
	public List<Software> fetchAll() {
		return (List<Software>) softwareRepository.findAll();
	}

	public Optional<Software> fetchById(Integer id) {
		return (Optional<Software>) softwareRepository.findById(id);
	}
}
