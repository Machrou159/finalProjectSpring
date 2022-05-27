package com.spring.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.web.dto.ReqRefDataDTO;
import com.spring.web.dto.ReqTicketAffectationDTO;
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

@Controller
@RequestMapping("/admin/api/")
public class AdminApiController {

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
	public ResponseEntity<List<Ticket>> tickets() {

		List<Ticket> tickets = serviceTicket.fetchTicketsByDeveloperIsNull();

		if (Objects.nonNull(tickets) && !tickets.isEmpty()) {
			return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Ticket>>(new ArrayList<Ticket>(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = { "/tickets/affect-developer" }, method = RequestMethod.POST)
	public ResponseEntity<RespMessageDTO> updateTicket(@RequestBody @Valid ReqTicketAffectationDTO dto) {

		RespMessageDTO message = new RespMessageDTO();

		Optional<User> developer = serviceUser.fetchById(dto.getDeveloperLogin());

		if (isNotEmpty(developer)) {
			Optional<Ticket> ticket = serviceTicket.fetchById(Integer.valueOf(dto.getId()));
			if (isNotEmpty(ticket)) {
				Ticket updateTicket = ticket.get();
				updateTicket.setDeveloper(developer.get());
				serviceTicket.saveTicket(updateTicket);
				message.setMessage("Affectation terminée avec succès");
				return new ResponseEntity<RespMessageDTO>(message, HttpStatus.OK);
			} else {
				message.setMessage("Le ticket [" + dto.getId() + "] est introuvable");
			}
		} else {
			message.setMessage("Le login [" + dto.getDeveloperLogin() + "] est introuvable");
		}

		return new ResponseEntity<RespMessageDTO>(message, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = { "/tickets/delete-ticket" }, method = RequestMethod.DELETE)
	public ResponseEntity<RespMessageDTO> deleteTicket(@RequestParam @NotNull String id) {

		RespMessageDTO message = new RespMessageDTO();

		Optional<Ticket> ticket = serviceTicket.fetchById(Integer.valueOf(id));

		if (isNotEmpty(ticket)) {
			serviceTicket.deleteTicket(ticket.get());
			message.setMessage("Suppression terminée avec succès");
		} else {
			message.setMessage("Le ticket est introuvable");
		}

		return new ResponseEntity<RespMessageDTO>(message, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = { "/settings/create-ref" }, method = RequestMethod.POST)
	public ResponseEntity<RespMessageDTO> createReferenceData(@RequestParam @NotNull String type,
			@RequestBody @Valid ReqRefDataDTO dto) {

		RespMessageDTO message = new RespMessageDTO();

		if (dto.getLabel() == null) {
			message.setMessage("Le lable ne peut pas être null");
		} else {
			if ("environement".equals(type)) {
				Environment ref = new Environment(dto.getLabel());
				envService.saveEnvironment(ref);
				message.setMessage("Creation terminée avec succès");
				return new ResponseEntity<RespMessageDTO>(message, HttpStatus.OK);
			} else if ("status".equals(type)) {
				Status ref = new Status(dto.getLabel());
				serviceStatus.saveStatus(ref);
				message.setMessage("Creation terminée avec succès");
				return new ResponseEntity<RespMessageDTO>(message, HttpStatus.OK);
			} else if ("software".equals(type)) {
				Software ref = new Software(dto.getLabel());
				softwareService.saveSoftware(ref);
				message.setMessage("Creation terminée avec succès");
				return new ResponseEntity<RespMessageDTO>(message, HttpStatus.OK);
			} else if ("urgence".equals(type)) {
				Urgence ref = new Urgence(dto.getLabel());
				urgenceService.saveUrgence(ref);
				message.setMessage("Creation terminée avec succès");
				return new ResponseEntity<RespMessageDTO>(message, HttpStatus.OK);
			} else {
				message.setMessage(
						"Les valeurs possibles pour le paramètre 'type' sont : [environement, status, software, urgence]");
			}
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
