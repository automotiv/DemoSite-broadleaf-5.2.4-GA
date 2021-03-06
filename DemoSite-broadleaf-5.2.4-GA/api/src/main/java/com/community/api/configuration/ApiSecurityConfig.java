package com.community.api.configuration;

import javax.servlet.Filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.profile.web.core.security.RestApiCustomerStateFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelDecisionManagerImpl;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.community.api.filter.EAccessAuthenticationFilter;

/**
 * @author Elbert Bautista (elbertbautista)
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Log LOG = LogFactory.getLog(ApiSecurityConfig.class);
    
    @Autowired
	private AuthenticationEntryPoint authEntryPoint;
    
    @Value("${asset.server.url.prefix.internal}")
    protected String assetServerUrlPrefixInternal;

    @Bean(name="blAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String password = "broadleafapi";
        String user = "broadleafapi";
        auth.inMemoryAuthentication()
            .withUser(user)
            .password(password)
            .authorities("USER");
        ///LOG.info("authenticationManagerBean()" + authenticationManagerBean());
        LOG.info(String.format("%n%n%nBasic auth configured with user %s and password: %s%n%n%n", user, password));
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication().withUser("chandana").password("chandana").roles("USER");
    }
    
   @Override
   public void configure(WebSecurity web) throws Exception {
	   LOG.info("Web securtiy method :: " + web);
       web.ignoring()
           .antMatchers("/api/**/webjars/**",
               "/api/**/images/favicon-*",
               "/api/**/jhawtcode/**",
               "/api/**/swagger-ui.html",
               "/api/**/swagger-resources/**",
               "/api/**/v2/api-docs");
   }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	LOG.info("IN Configure Method");
        /*http
            .antMatcher("/api/**")
            .httpBasic()//.authenticationEntryPoint(authEntryPoint)
            .and()
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/**").hasAuthority("USER")
                .antMatchers("/api/**")
                .authenticated()
                .and()
				.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionFixation()
                .none()
                .enableSessionUrlRewriting(false)
                .and()
            .requiresChannel()
                .anyRequest()
                .requires(ChannelDecisionManagerImpl.ANY_CHANNEL)
            .and()
            .addFilterAfter(apiCustomerStateFilter(), RememberMeAuthenticationFilter.class);*/
    	
    	http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/**").hasRole("USER")
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
    }
    
    @Bean
    public Filter apiCustomerStateFilter() {
        return new RestApiCustomerStateFilter();
    }
    
}
