package com.spring.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.web.entity.Status;
import com.spring.web.repository.StatusRepository;

@Service
public class StatusService {

	@Autowired
	private StatusRepository statusRepository;

	// Save operation
	public Status saveStatus(Status Status) {
		return statusRepository.save(Status);
	}

	// Read operation
	public List<Status> fetchAll() {
		return (List<Status>) statusRepository.findAll();
	}

	public Optional<Status> fetchById(Integer id) {
		return (Optional<Status>) statusRepository.findById(id);
	}
}
