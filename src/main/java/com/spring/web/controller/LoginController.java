package com.spring.web.controller;

import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.web.entity.User;
import com.spring.web.form.LoginForm;
import com.spring.web.service.UserService;
import com.spring.web.utils.Constants;

@Controller
public class LoginController {

	@Autowired
	private UserService serviceUser;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String login(Model model) {

		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);

		return "login";
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public String login(Model model, HttpSession session, @ModelAttribute("loginForm") LoginForm loginForm) {

		String login = loginForm.getLogin();
		String password = loginForm.getPassword();
		String sessionID = session.getId();
		System.out.println("New Session -> " + sessionID);

		if (login != null && login.length() > 0 && password != null && password.length() > 0) {

			Optional<User> user = serviceUser.fetchById(login);

			if (Objects.nonNull(user) && !user.isEmpty()) {
				if (user.get().getPassword().equals(password)) {

					session.setAttribute(Constants.HTTP_PARAM_USERNAME, login);

					if (Constants.ROLE_ADMIN.equals(user.get().getRole())) {
						return "redirect:/admin/vues/tickets";
					}

					if (Constants.ROLE_CUSTOMER.equals(user.get().getRole())) {
						return "redirect:/customer/vues/tickets";
					}

					if (Constants.ROLE_DEVELOPER.equals(user.get().getRole())) {
						return "redirect:/developer/vues/tickets";
					}
				}
			}
		}

		session.invalidate();
		System.out.println("Session Destroyed -> " + sessionID);
		model.addAttribute("formDisplayErrorMsg", "YES");
		return "login";
	}

	@RequestMapping(value = { "/disconnection" }, method = RequestMethod.GET)
	public String disconnection(Model model, HttpSession session) {

		session.invalidate();
		System.out.println("Session Destroyed -> " + session.getId());

		return "login";
	}
	
	@RequestMapping(value = { "/create-user" }, method = RequestMethod.GET)
	public String createUser(Model model, HttpSession session) {
		return "create-user";
	}
}
