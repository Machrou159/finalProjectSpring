package com.spring.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.web.entity.Ticket;
import com.spring.web.entity.User;
import com.spring.web.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	// Save operation
	public Ticket saveTicket(Ticket Ticket) {
		return ticketRepository.save(Ticket);
	}
	
	// Delete operation
	public void deleteTicket(Ticket Ticket) {
		ticketRepository.delete(Ticket);
	}

	// Read operation
	public Optional<Ticket> fetchById(Integer id) {
		return  ticketRepository.findById(id);
	}
	
	// Read operation
	public List<Ticket> fetchAll() {
		return (List<Ticket>) ticketRepository.findAll();
	}
	
	// Read operation
	public List<Ticket> fetchTicketsByCustomer(User customer) {
		return  ticketRepository.findByCustomer(customer);
	}	
	
	// Read operation
	public List<Ticket> fetchTicketsByDeveloper(User developer) {
		return  ticketRepository.findByDeveloper(developer);
	}
	
	// Read operation
	public List<Ticket> fetchTicketsByDeveloperIsNull() {
		return  ticketRepository.findByDeveloperIsNull();
	}
}
