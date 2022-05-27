package com.spring.web.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.spring.web.entity.Urgence;

public interface UrgenceRepository extends CrudRepository<Urgence, String> {

	Optional<Urgence> findById(int id);
}
