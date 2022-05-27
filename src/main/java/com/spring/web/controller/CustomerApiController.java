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

import com.spring.web.dto.ReqTicketCreationDTO;
import com.spring.web.dto.RespMessageDTO;
import com.spring.web.entity.Environment;
import com.spring.web.entity.Software;
import com.spring.web.entity.Status;
import com.spring.web.entity.Ticket;
import com.spring.web.entity.Urgence;
import com.spring.web.entity.User;
import com.spring.web.service.EnvironmentService;
import com.spring.web.service.SoftwareService;
import com.spring.web.service.StatusService;
import com.spring.web.service.TicketService;
import com.spring.web.service.UrgenceService;
import com.spring.web.service.UserService;
import com.spring.web.utils.Constants;

@Controller
@RequestMapping("/customer/api/")
public class CustomerApiController {

	@Autowired
	private TicketService serviceTicket;

	@Autowired
	private StatusService serviceStatus;

	@Autowired
	private UserService serviceUser;

	@Autowired
	private EnvironmentService envService;

	@Autowired
	private UrgenceService urgenceService;

	@Autowired
	private SoftwareService softwareService;

	@RequestMapping(value = { "/tickets" }, method = RequestMethod.GET)
	public ResponseEntity<List<Ticket>> tickets(@RequestParam @NonNull String customerLogin) {

		Optional<User> customer = serviceUser.fetchById(customerLogin);

		if (isNotEmpty(customer)) {

			List<Ticket> tickets = serviceTicket.fetchTicketsByCustomer(customer.get());

			if (Objects.nonNull(tickets) && !tickets.isEmpty()) {
				return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Ticket>>(new ArrayList<Ticket>(), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<List<Ticket>>(new ArrayList<Ticket>(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = { "/create-ticket" }, method = RequestMethod.POST)
	public ResponseEntity<RespMessageDTO> updateTicket(@RequestBody @Valid ReqTicketCreationDTO dto) {

		RespMessageDTO message = new RespMessageDTO();

		Optional<User> customer = serviceUser.fetchById(dto.getCustomerLogin());
		Optional<Environment> environment = envService.fetchById(dto.getEnvironment());
		Optional<Software> software = softwareService.fetchById(dto.getSoftware());
		Optional<Status> status = serviceStatus.fetchById(dto.getStatus());
		Optional<Urgence> urgence = urgenceService.fetchById(dto.getUrgence());

		if (isNotEmpty(customer) && isNotEmpty(environment) && isNotEmpty(software) && isNotEmpty(status)
				&& isNotEmpty(urgence)) {

			if (!Constants.ROLE_CUSTOMER.equals(customer.get().getRole())) {
				message.setMessage("Le login [" + dto.getCustomerLogin() + "] n'est pas un client");
			} else {
				Ticket ticket = new Ticket();
				ticket.setDescription(dto.getDescription());
				ticket.setCustomer(customer.get());
				ticket.setEnvironment(environment.get());
				ticket.setStatus(status.get());
				ticket.setSoftware(software.get());
				ticket.setUrgence(urgence.get());

				serviceTicket.saveTicket(ticket);
				message.setMessage("Création terminée avec succès");

				return new ResponseEntity<RespMessageDTO>(message, HttpStatus.OK);
			}
		} else {
			message.setMessage(
					"Tous les attributs sont obligatoires, merci de vérifier si les données fournies sont correctes");
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
