package com.spring.web.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.spring.web.entity.Status;

public interface StatusRepository extends CrudRepository<Status, String> {

	Optional<Status> findById(int id);
}
