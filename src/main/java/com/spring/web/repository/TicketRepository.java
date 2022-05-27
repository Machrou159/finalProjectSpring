package com.spring.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.spring.web.entity.Ticket;
import com.spring.web.entity.User;

public interface TicketRepository extends CrudRepository<Ticket, String> {

	List<Ticket> findByCustomer(User login);

	List<Ticket> findByDeveloper(User login);
	
	Optional<Ticket> findById(Integer id);
	
	List<Ticket> findByDeveloperIsNull();
}
