package com.spring.web.controller;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.spring.web.entity.Status;
import com.spring.web.entity.Ticket;
import com.spring.web.entity.User;
import com.spring.web.form.TicketForm;
import com.spring.web.service.StatusService;
import com.spring.web.service.TicketService;
import com.spring.web.service.UserService;
import com.spring.web.utils.Constants;

@Controller
@RequestMapping("/developer/vues/")
public class DeveloperVuesController {

	@Autowired
	private TicketService serviceTicket;

	@Autowired
	private StatusService serviceStatus;

	@Autowired
	private UserService serviceUser;

	@RequestMapping(value = { "/tickets" }, method = RequestMethod.GET)
	public String tickets(Model model, HttpSession session) {

		final Object developer = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(developer)) {

			final Optional<User> user = serviceUser.fetchById(developer.toString());

			if (isNotEmpty(user)) {
				model.addAttribute("tickets", serviceTicket.fetchTicketsByDeveloper(user.get()));
			} else {
				model.addAttribute("tickets", new ArrayList<Ticket>());
			}

			return "developer/tickets";

		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/tickets/update-status" }, method = RequestMethod.GET)
	public String updateTicket(Model model, HttpSession session, @RequestParam @NotNull String tid,
			@RequestParam @NotNull String sid) {

		final Object developer = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(developer) && Objects.nonNull(tid) && Objects.nonNull(sid)) {
			
			String stid = ("ENCOURS".equals(sid) || "EN COURS".equals(sid)) ? "1" : "2";
			
			model.addAttribute("id", format(tid));
			model.addAttribute("statusID", stid);
			model.addAttribute("status", serviceStatus.fetchAll());

			TicketForm ticketForm = new TicketForm();
			ticketForm.setId(format(tid));
			ticketForm.setStatus(stid);
			model.addAttribute("ticketForm", ticketForm);

			return "developer/update-status";
		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/tickets/update-status" }, method = RequestMethod.POST)
	public RedirectView updateTicket(Model model, RedirectAttributes attributes, HttpSession session,
			@ModelAttribute("ticketForm") TicketForm ticketForm) {

		Object login = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(login) && login.toString().length() > 0) {

			String identifier = ticketForm.getId();
			String statusData = ticketForm.getStatus();

			if (!isNotEmpty(statusData) || !isNotEmpty(identifier)) {
				return new RedirectView("update-status");
			}

			int statusID = ticketForm.getStatus() == null ? -1 : Integer.parseInt(ticketForm.getStatus());

			Optional<User> user = serviceUser.fetchById(login.toString());
			Optional<Status> status = serviceStatus.fetchById(statusID);

			if (isNotEmpty(user) && isNotEmpty(status) && Objects.nonNull(identifier)) {
				Optional<Ticket> ticket = serviceTicket.fetchById(Integer.valueOf(identifier));
				if (isNotEmpty(ticket)) {
					Ticket updateTicket = ticket.get();
					updateTicket.setStatus(status.get());
					serviceTicket.saveTicket(updateTicket);
					return new RedirectView("./");
				}
			}
		}

		return new RedirectView("/login");
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

	@RequestMapping(value = { "/tickets/statistics" }, method = RequestMethod.GET)
	public String statistics(Model model) {
		return "developer/statistics";
	}
}
