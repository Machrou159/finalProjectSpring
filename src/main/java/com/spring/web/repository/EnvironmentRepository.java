package com.spring.web.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.spring.web.entity.Environment;

public interface EnvironmentRepository extends CrudRepository<Environment, String> {

	Optional<Environment> findById(int id);
}
