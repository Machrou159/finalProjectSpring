package com.spring.web.controller;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.spring.web.entity.Environment;
import com.spring.web.entity.Software;
import com.spring.web.entity.Status;
import com.spring.web.entity.Ticket;
import com.spring.web.entity.Urgence;
import com.spring.web.entity.User;
import com.spring.web.form.TicketForm;
import com.spring.web.service.EnvironmentService;
import com.spring.web.service.SoftwareService;
import com.spring.web.service.StatusService;
import com.spring.web.service.TicketService;
import com.spring.web.service.UrgenceService;
import com.spring.web.service.UserService;
import com.spring.web.utils.Constants;

@Controller
@RequestMapping("/customer/vues/")
public class CustomerVuesController {

	@Autowired
	private TicketService serviceTicket;

	@Autowired
	private StatusService serviceStatus;

	@Autowired
	private SoftwareService serviceSoftware;

	@Autowired
	private EnvironmentService serviceEnvironment;

	@Autowired
	private UrgenceService serviceUrgence;

	@Autowired
	private UserService serviceUser;

	@RequestMapping(value = { "/tickets" }, method = RequestMethod.GET)
	public String tickets(Model model, HttpSession session) {

		final Object customer = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(customer)) {

			final Optional<User> user = serviceUser.fetchById(customer.toString());

			if (isNotEmpty(user)) {
				model.addAttribute("tickets", serviceTicket.fetchTicketsByCustomer(user.get()));
			} else {
				model.addAttribute("tickets", new ArrayList<Ticket>());
			}

			return "customer/tickets";

		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/tickets/create-ticket" }, method = RequestMethod.GET)
	public String createTicket(Model model, HttpSession session,
			@ModelAttribute("formDisplayErrorMsg") String formDisplayErrorMsg,
			@ModelAttribute("descriptionValue") String descriptionValue) {

		final Object customer = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(customer)) {

			model.addAttribute("status", serviceStatus.fetchAll());
			model.addAttribute("environments", serviceEnvironment.fetchAll());
			model.addAttribute("softwares", serviceSoftware.fetchAll());
			model.addAttribute("urgence", serviceUrgence.fetchAll());

			System.out.println(descriptionValue);

			TicketForm ticketForm = new TicketForm();
			ticketForm.setDescription(format(model.getAttribute("descriptionValue")));
			ticketForm.setStatus(format(model.getAttribute("statusValue")));
			ticketForm.setUrgence(format(model.getAttribute("urgenceValue")));
			ticketForm.setEnvironment(format(model.getAttribute("environmentValue")));
			ticketForm.setSoftware(format(model.getAttribute("softwareValue")));

			model.addAttribute("ticketForm", ticketForm);

			return "customer/create-ticket";
		} else {
			return "/login";
		}
	}

	public String format(Object obj) {
		return Objects.nonNull(obj) ? obj.toString() : "";
	}

	@RequestMapping(value = { "/tickets/create-ticket" }, method = RequestMethod.POST)
	public RedirectView createTicket(Model model, RedirectAttributes attributes, HttpSession session,
			@ModelAttribute("ticketForm") TicketForm ticketForm,
			@ModelAttribute("formDisplayErrorMsg") String formDisplayErrorMsg) {

		Object login = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(login) && login.toString().length() > 0) {

			String descriptionData = ticketForm.getDescription();
			String environmentData = ticketForm.getEnvironment();
			String softwareData = ticketForm.getSoftware();
			String statusData = ticketForm.getStatus();
			String urgenceData = ticketForm.getUrgence();

			if (!isNotEmpty(urgenceData) || !isNotEmpty(statusData) || !isNotEmpty(softwareData)
					|| !isNotEmpty(environmentData) || !isNotEmpty(descriptionData)) {

				attributes.addAttribute("formDisplayErrorMsg", "YES");

//				attributes.addAttribute("descriptionValue", format(descriptionData));
//				attributes.addAttribute("statusValue", format(statusData));
//				attributes.addAttribute("urgenceValue", format(urgenceData));
//				attributes.addAttribute("environmentValue", format(environmentData));
//				attributes.addAttribute("softwareValue", format(softwareData));				
//				TicketForm tickForm = new TicketForm();				
//				ticketForm.setDescription(descriptionData);	
//				attributes.addAttribute("description", format(softwareData));
//				model.addAttribute("ticketForm", ticketForm);

				return new RedirectView("create-ticket");
			}

			int environmentID = ticketForm.getEnvironment() == null ? -1
					: Integer.parseInt(ticketForm.getEnvironment());
			int softwareID = ticketForm.getSoftware() == null ? -1 : Integer.parseInt(ticketForm.getSoftware());
			int statusID = ticketForm.getStatus() == null ? -1 : Integer.parseInt(ticketForm.getStatus());
			int urgenceID = ticketForm.getUrgence() == null ? -1 : Integer.parseInt(ticketForm.getUrgence());

			Optional<User> user = serviceUser.fetchById(login.toString());
			Optional<Environment> environment = serviceEnvironment.fetchById(environmentID);
			Optional<Software> software = serviceSoftware.fetchById(softwareID);
			Optional<Status> status = serviceStatus.fetchById(statusID);
			Optional<Urgence> urgence = serviceUrgence.fetchById(urgenceID);

			if (isNotEmpty(user) && isNotEmpty(environment) && isNotEmpty(software) && isNotEmpty(status)
					&& isNotEmpty(urgence)) {

				Ticket ticket = new Ticket(user.get(), null, descriptionData, status.get(), urgence.get(),
						environment.get(), software.get());
				serviceTicket.saveTicket(ticket);
//				return "redirect:/customer/vues/tickets";
//				return new RedirectView("customer/vues/tickets");
				return new RedirectView("./");
			}
		}

//		return new RedirectView("login");
		return new RedirectView("/login");
	}

	public boolean isNotEmpty(String obj) {
		return Objects.nonNull(obj) && !obj.isEmpty();
	}

	public boolean isNotEmpty(Optional<?> obj) {
		return Objects.nonNull(obj) && !obj.isEmpty();
	}

	@RequestMapping(value = { "/tickets/statistics" }, method = RequestMethod.GET)
	public String statistics(Model model) {
		return "customer/statistics";
	}
}
