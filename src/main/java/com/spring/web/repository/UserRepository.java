package com.spring.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.spring.web.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

	public List<User> findByRole(String role);
}
