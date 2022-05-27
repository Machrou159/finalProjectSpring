package com.spring.web.controller;

import java.util.ArrayList;
import java.util.List;
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

import com.spring.web.entity.Environment;
import com.spring.web.entity.Software;
import com.spring.web.entity.Status;
import com.spring.web.entity.Ticket;
import com.spring.web.entity.Urgence;
import com.spring.web.entity.User;
import com.spring.web.form.DevForm;
import com.spring.web.form.RefForm;
import com.spring.web.service.EnvironmentService;
import com.spring.web.service.SoftwareService;
import com.spring.web.service.StatusService;
import com.spring.web.service.TicketService;
import com.spring.web.service.UrgenceService;
import com.spring.web.service.UserService;
import com.spring.web.utils.Constants;

@Controller
@RequestMapping("/admin/vues/")
public class AdminVuesController {

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
	public String tickets(Model model, HttpSession session) {

		final Object admin = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(admin)) {

			final Optional<User> user = serviceUser.fetchById(admin.toString());

			if (isNotEmpty(user)) {
				model.addAttribute("tickets", serviceTicket.fetchTicketsByDeveloperIsNull());
			} else {
				model.addAttribute("tickets", new ArrayList<Ticket>());
			}

			return "admin/tickets";

		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/tickets/affect-developer" }, method = RequestMethod.GET)
	public String updateTicket(Model model, HttpSession session, @RequestParam @NotNull String tid) {

		final Object admin = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(admin) && Objects.nonNull(tid)) {

			model.addAttribute("id", format(tid));
			model.addAttribute("developers", serviceUser.fetchByRole(Constants.ROLE_DEVELOPER));

			DevForm devForm = new DevForm();
			devForm.setId(format(tid));
			model.addAttribute("devForm", devForm);

			return "admin/affect-developer";
		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/tickets/delete-ticket" }, method = RequestMethod.GET)
	public String deleteTicket(Model model, HttpSession session, @RequestParam @NotNull String tid) {

		final Object admin = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(admin) && Objects.nonNull(tid)) {

			Optional<Ticket> ticket = serviceTicket.fetchById(Integer.valueOf(tid));

			if (isNotEmpty(ticket)) {
				serviceTicket.deleteTicket(ticket.get());
			}

			return "redirect:./";
		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/tickets/affect-developer" }, method = RequestMethod.POST)
	public RedirectView updateTicket(Model model, RedirectAttributes attributes, HttpSession session,
			@ModelAttribute("devForm") DevForm devForm) {

		Object admin = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(admin) && admin.toString().length() > 0) {

			String identifier = devForm.getId();
			String developerLogin = devForm.getDeveloper();

			if (!isNotEmpty(developerLogin) || !isNotEmpty(identifier)) {
				return new RedirectView("affect-developer");
			}

			Optional<User> user = serviceUser.fetchById(admin.toString());
			Optional<User> developer = serviceUser.fetchById(developerLogin);

			if (isNotEmpty(user) && isNotEmpty(developer) && Objects.nonNull(identifier)) {
				Optional<Ticket> ticket = serviceTicket.fetchById(Integer.valueOf(identifier));
				if (isNotEmpty(ticket)) {
					Ticket updateTicket = ticket.get();
					updateTicket.setDeveloper(developer.get());
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

		String total = "0";
		String t1 = "0,0";
		String t2 = "0,0";

		List<Ticket> tickets = serviceTicket.fetchAll();

		if (tickets != null && !tickets.isEmpty()) {
			int s = tickets.size();
			total = "" + s;
			if (s != 0) {
				int t1s = 0;
				int t2s = 0;
				for (Ticket t : tickets) {
					if (t.getStatus() != null) {
						if (t.getStatus().getId() == 1) {
							t1s++;
						}
						if (t.getStatus().getId() == 2) {
							t2s++;
						}
					}
				}
				t1 = "" + ((float) (t1s / s)) * 100;
				t2 = "" + ((float) (t2s / s)) * 100;
			}

			tickets.clear();
		}

		model.addAttribute("numbTickets", total);
		model.addAttribute("tauxEnCours", t1 + "%");
		model.addAttribute("tauxResolus", t2 + "%");

		return "admin/statistics";
	}

	@RequestMapping(value = { "/tickets/environnements" }, method = RequestMethod.GET)
	public String environnements(Model model, HttpSession session) {

		final Object admin = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(admin)) {

			final Optional<User> user = serviceUser.fetchById(admin.toString());

			if (isNotEmpty(user)) {
				model.addAttribute("listOfValues", envService.fetchAll());
			} else {
				model.addAttribute("listOfValues", new ArrayList<Ticket>());
			}

			return "admin/environnements";

		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/tickets/softwares" }, method = RequestMethod.GET)
	public String softwares(Model model, HttpSession session) {

		final Object admin = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(admin)) {

			final Optional<User> user = serviceUser.fetchById(admin.toString());

			if (isNotEmpty(user)) {
				model.addAttribute("listOfValues", softwareService.fetchAll());
			} else {
				model.addAttribute("listOfValues", new ArrayList<Ticket>());
			}

			return "admin/softwares";

		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/tickets/status" }, method = RequestMethod.GET)
	public String status(Model model, HttpSession session) {

		final Object admin = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(admin)) {

			final Optional<User> user = serviceUser.fetchById(admin.toString());

			if (isNotEmpty(user)) {
				model.addAttribute("listOfValues", serviceStatus.fetchAll());
			} else {
				model.addAttribute("listOfValues", new ArrayList<Ticket>());
			}

			return "admin/status";
		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/tickets/urgences" }, method = RequestMethod.GET)
	public String urgences(Model model, HttpSession session) {

		final Object admin = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(admin)) {

			final Optional<User> user = serviceUser.fetchById(admin.toString());

			if (isNotEmpty(user)) {
				model.addAttribute("listOfValues", urgenceService.fetchAll());
			} else {
				model.addAttribute("listOfValues", new ArrayList<Ticket>());
			}

			return "admin/urgences";

		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/tickets/customers" }, method = RequestMethod.GET)
	public String customers(Model model, HttpSession session) {

		final Object admin = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(admin)) {

			final Optional<User> user = serviceUser.fetchById(admin.toString());

			if (isNotEmpty(user)) {
				model.addAttribute("listOfValues", serviceUser.fetchAll());
			} else {
				model.addAttribute("listOfValues", new ArrayList<Ticket>());
			}

			return "admin/customers";
		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/settings/create-environement" }, method = RequestMethod.GET)
	public String createEnvironement(Model model, HttpSession session,
			@ModelAttribute("formDisplayErrorMsg") String formDisplayErrorMsg,
			@ModelAttribute("labelValue") String labelValue) {

		final Object admin = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(admin)) {

			System.out.println(labelValue);

			RefForm refRForm = new RefForm();
			refRForm.setLabel(format(model.getAttribute("labelValue")));
			model.addAttribute("refForm", refRForm);

			return "admin/create-environment";
		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/settings/create-environement" }, method = RequestMethod.POST)
	public RedirectView createEnvironement(Model model, RedirectAttributes attributes, HttpSession session,
			@ModelAttribute("refFrom") RefForm refForm,
			@ModelAttribute("formDisplayErrorMsg") String formDisplayErrorMsg) {

		Object login = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(login) && login.toString().length() > 0) {

			String labelData = refForm.getLabel();

			if (!isNotEmpty(labelData)) {

				attributes.addAttribute("formDisplayErrorMsg", "YES");
				return new RedirectView("create-status");
			}

			Environment ref = new Environment(labelData);
			envService.saveEnvironment(ref);

			return new RedirectView("../tickets/status");
		}

		return new RedirectView("/login");
	}

	@RequestMapping(value = { "/settings/create-status" }, method = RequestMethod.GET)
	public String createStatus(Model model, HttpSession session,
			@ModelAttribute("formDisplayErrorMsg") String formDisplayErrorMsg,
			@ModelAttribute("labelValue") String labelValue) {

		final Object admin = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(admin)) {

			System.out.println(labelValue);

			RefForm refRForm = new RefForm();
			refRForm.setLabel(format(model.getAttribute("labelValue")));
			model.addAttribute("refForm", refRForm);

			return "admin/create-status";
		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/settings/create-software" }, method = RequestMethod.GET)
	public String createSoftware(Model model, HttpSession session,
			@ModelAttribute("formDisplayErrorMsg") String formDisplayErrorMsg,
			@ModelAttribute("labelValue") String labelValue) {

		final Object admin = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(admin)) {

			System.out.println(labelValue);

			RefForm refRForm = new RefForm();
			refRForm.setLabel(format(model.getAttribute("labelValue")));
			model.addAttribute("refForm", refRForm);

			return "admin/create-software";
		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/settings/create-urgence" }, method = RequestMethod.GET)
	public String createUrgence(Model model, HttpSession session,
			@ModelAttribute("formDisplayErrorMsg") String formDisplayErrorMsg,
			@ModelAttribute("labelValue") String labelValue) {

		final Object admin = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(admin)) {

			System.out.println(labelValue);

			RefForm refRForm = new RefForm();
			refRForm.setLabel(format(model.getAttribute("labelValue")));
			model.addAttribute("refForm", refRForm);

			return "admin/create-urgence";
		} else {
			return "/login";
		}
	}

	@RequestMapping(value = { "/settings/create-status" }, method = RequestMethod.POST)
	public RedirectView createStatus(Model model, RedirectAttributes attributes, HttpSession session,
			@ModelAttribute("refFrom") RefForm refForm,
			@ModelAttribute("formDisplayErrorMsg") String formDisplayErrorMsg) {

		Object login = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(login) && login.toString().length() > 0) {

			String labelData = refForm.getLabel();

			if (!isNotEmpty(labelData)) {

				attributes.addAttribute("formDisplayErrorMsg", "YES");
				return new RedirectView("create-status");
			}

			Status ref = new Status(labelData);
			serviceStatus.saveStatus(ref);

			return new RedirectView("../tickets/status");
		}

		return new RedirectView("/login");
	}

	@RequestMapping(value = { "/settings/create-software" }, method = RequestMethod.POST)
	public RedirectView createSoftware(Model model, RedirectAttributes attributes, HttpSession session,
			@ModelAttribute("refFrom") RefForm refForm,
			@ModelAttribute("formDisplayErrorMsg") String formDisplayErrorMsg) {

		Object login = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(login) && login.toString().length() > 0) {

			String labelData = refForm.getLabel();

			if (!isNotEmpty(labelData)) {

				attributes.addAttribute("formDisplayErrorMsg", "YES");
				return new RedirectView("create-software");
			}

			Software ref = new Software(labelData);
			softwareService.saveSoftware(ref);

			return new RedirectView("../tickets/softwares");
		}

		return new RedirectView("/login");
	}

	@RequestMapping(value = { "/settings/create-urgence" }, method = RequestMethod.POST)
	public RedirectView createUrgence(Model model, RedirectAttributes attributes, HttpSession session,
			@ModelAttribute("refFrom") RefForm refForm,
			@ModelAttribute("formDisplayErrorMsg") String formDisplayErrorMsg) {

		Object login = session.getAttribute(Constants.HTTP_PARAM_USERNAME);

		if (Objects.nonNull(login) && login.toString().length() > 0) {

			String labelData = refForm.getLabel();

			if (!isNotEmpty(labelData)) {

				attributes.addAttribute("formDisplayErrorMsg", "YES");
				return new RedirectView("create-software");
			}

			Urgence ref = new Urgence(labelData);
			urgenceService.saveUrgence(ref);

			return new RedirectView("../tickets/urgences");
		}

		return new RedirectView("/login");
	}
}
