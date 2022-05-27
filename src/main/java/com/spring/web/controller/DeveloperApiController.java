package com.spring.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.web.dto.ReqStatusUpdateDTO;
import com.spring.web.dto.RespMessageDTO;
import com.spring.web.entity.Status;
import com.spring.web.entity.Ticket;
import com.spring.web.entity.User;
import com.spring.web.service.StatusService;
import com.spring.web.service.TicketService;
import com.spring.web.service.UserService;

@Controller
@RequestMapping("/developer/api/")
public class DeveloperApiController {

	@Autowired
	private TicketService serviceTicket;

	@Autowired
	private UserService serviceUser;

	@Autowired
	private StatusService serviceStatus;

	@RequestMapping(value = { "/tickets" }, method = RequestMethod.GET)
	public ResponseEntity<List<Ticket>> tickets(@RequestParam @NonNull String developerLogin) {


		Optional<User> developer = serviceUser.fetchById(developerLogin);

		if (isNotEmpty(developer)) {

			List<Ticket> tickets = serviceTicket.fetchTicketsByDeveloper(developer.get());

			if (Objects.nonNull(tickets) && !tickets.isEmpty()) {
				return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Ticket>>(new ArrayList<Ticket>(), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<List<Ticket>>(new ArrayList<Ticket>(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = { "/update-ticket" }, method = RequestMethod.POST)
	public ResponseEntity<RespMessageDTO> updateTicket(@RequestBody @Valid ReqStatusUpdateDTO dto) {

		RespMessageDTO message = new RespMessageDTO();

		Optional<Ticket> ticket = serviceTicket.fetchById(dto.getId());
		Optional<Status> status = serviceStatus.fetchById(dto.getStatusId());

		if (isNotEmpty(ticket)) {
			if (isNotEmpty(status)) {
				Ticket updateTicket = ticket.get();
				updateTicket.setStatus(status.get());
				serviceTicket.saveTicket(updateTicket);
				message.setMessage("Affectation terminée avec succès");
				return new ResponseEntity<RespMessageDTO>(message, HttpStatus.OK);
			} else {
				message.setMessage("Le status [" + dto.getStatusId() + "] est introuvable");
			}
		} else {
			message.setMessage("Le ticket [" + dto.getId() + "] est introuvable");
		}

		return new ResponseEntity<RespMessageDTO>(message, HttpStatus.BAD_REQUEST);
	}

	public String format(Object obj) {
		return Objects.nonNull(obj) ? obj.toString() : "";
	}

	public boolean isNotEmpty(String obj) {
		return Objects.nonNull(obj) && !obj.isEmpty();
	}

	public boolean isNotEmpty(Optional<?> obj) {
		return Objects.nonNull(obj) && !obj.isEmpty();
	}
}
