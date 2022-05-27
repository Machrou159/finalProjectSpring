package com.spring.web.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.spring.web.entity.Software;

public interface SoftwareRepository extends CrudRepository<Software, String> {

	Optional<Software> findById(int id);
}
