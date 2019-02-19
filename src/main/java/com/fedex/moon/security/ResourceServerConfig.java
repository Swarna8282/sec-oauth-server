package com.fedex.moon.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

// very important to include @Order(1) or else, it will not direct u to enter your login details but rather give not authenticated
@Configuration
@EnableResourceServer
@Order(1)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter{
	
	// If we don't use this encoder bean, it will give 'There is no PasswordEncoder mapped for the id "null"'
	@Bean
	public BCryptPasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure (HttpSecurity hSecurity) throws Exception {
		
		hSecurity.requestMatchers()
			.antMatchers("/login", "/oauth/authorize")
			.and()
			.authorizeRequests()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.permitAll();
	}
	
	@Override
	protected void configure (AuthenticationManagerBuilder builder) throws Exception {
		
		builder.inMemoryAuthentication()
			.withUser("swan").password(passwordEncoder().encode("123")).roles("USER")
			.and()
			.withUser("gold").password(passwordEncoder().encode("123")).roles("ADMIN");
	}

}
