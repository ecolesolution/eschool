package com.serp.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.serp.security.erpsecurity.CustomAuthenticationEntryPoint;
import com.serp.security.erpsecurity.CustomLogoutSuccessHandler;

/**
 * @author sanjeet choudhary
 *
 */

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;

	private static final String RESOURCE_ID = "resource_id";

	// @Autowired
	// private JwtAccessTokenConverter jwtAccessToken;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
		// .tokenStore(new JwtTokenStore(jwtAccessToken));

	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint).and().logout()
				.logoutUrl("/oauth/logout").logoutSuccessHandler(customLogoutSuccessHandler).and().csrf()
				.requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize")).disable().headers()
				.frameOptions().disable().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests().antMatchers("/hello/")
				.permitAll().antMatchers("/secure/**").authenticated();

	}
}
