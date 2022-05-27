package com.spring.web.controller;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.web.security.dto.JwtRequest;
import com.spring.web.security.dto.JwtResponse;
import com.spring.web.security.service.DbUserDetailsService;
import com.spring.web.security.utils.JwtTokenUtil;

/**
 * jwt（json web token）
 */
@RestController
@CrossOrigin
public class JwtAuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private DbUserDetailsService userDetailsService;

	/**
	 * username password / json web token
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		final String role = getRole(userDetails.getAuthorities());

		return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername(), role));
	}

	private String getRole(Collection<? extends GrantedAuthority> authorities) {

		if (authorities != null) {
			Optional<? extends GrantedAuthority> role = authorities.parallelStream().findFirst();
			if (Objects.nonNull(role)) {
				return role.get() != null ? role.get().getAuthority() : "";
			}
		}

		return "";
	}

	/**
	 * username password / json web token
	 */
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}