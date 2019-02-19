package com.fedex.moon.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	// If we don't use this, it will give 'There is no PasswordEncoder mapped for the id "null"'
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void configure (final AuthorizationServerSecurityConfigurer configurer) throws Exception {
		configurer.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure (final ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer.inMemory()
			.withClient("MyClientId")
			.secret(passwordEncoder.encode("secret"))
			.authorizedGrantTypes("authorization_code")
			.scopes("user_info")
			.autoApprove(true)
			.redirectUris("http://localhost:8881/ui/login", "http://localhost:8881/login");
	}

}
