package com.community.api.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
	
	private static final Log LOG = LogFactory.getLog(CustomUserDetailsService.class);
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOG.info("in load user method");
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
		authorities.add(authority);

		UserDetails user = new User("broadleafapi", "broadleafapi", authorities);
		return user;
	}
}
