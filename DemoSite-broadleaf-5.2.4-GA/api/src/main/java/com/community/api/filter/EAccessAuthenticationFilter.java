package com.community.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

public class EAccessAuthenticationFilter extends RequestHeaderAuthenticationFilter {
	
	private static final Log LOG = LogFactory.getLog(EAccessAuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	LOG.info("EAccessAuthenticationFilter" + SecurityContextHolder.getContext().getAuthentication());
        if(SecurityContextHolder.getContext().getAuthentication() == null){
            //Do my authentication stuff
            //PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(user, credential, authorities);
            //SecurityContextHolder.getContext().setAuthentication(authentication);
        }  
        super.doFilter(request, response, chain);
     }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

}
