package com.serp.security.erpsecurity;

import com.serp.security.domain.Authority;
import com.serp.security.domain.User;
import com.serp.security.exceptions.UserNotActivatedException;
import com.serp.security.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author sanjeet choudhary
 *
 */

@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) {

		log.debug("Authenticating {}", login);
		String lowercaseLogin = login.toLowerCase();

		User userFromDatabase;
		if (lowercaseLogin.contains("@")) {
			userFromDatabase = userRepository.findByEmail(lowercaseLogin);
		} else {
			userFromDatabase = userRepository.findByUsername(lowercaseLogin);
		}

		if (userFromDatabase == null) {
			throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
		} else if (!userFromDatabase.isActivated()) {
			throw new UserNotActivatedException("User " + lowercaseLogin + " is not activated");
		}

		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (Authority authority : userFromDatabase.getAuthorities()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
			grantedAuthorities.add(grantedAuthority);
		}

		return new org.springframework.security.core.userdetails.User(userFromDatabase.getUsername(),
				userFromDatabase.getPassword(), grantedAuthorities);

	}

}
