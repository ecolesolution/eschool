package com.serp.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.serp.security.enums.Authorities;


/**
 * @author sanjeet choudhary
 *
 */

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter  {

	@Value("${oauth2.clientid}")
    private String PROP_CLIENTID;
	@Value("${oauth2.secret}")
    private  String PROP_SECRET;
	@Value("${oauth2.tokenValidityInSeconds}")
    private  String PROP_TOKEN_VALIDITY_SECONDS;

   // @Autowired
    //private DataSource dataSource;
    
   // @Autowired
   // private JwtAccessTokenConverter jwtAccessToken;
    
	@Autowired
	CustomTokenStore customTokenStore;
	
	@Bean
    public TokenStore tokenStore() {
        return customTokenStore;
    }

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.tokenStore(tokenStore())
               // .tokenStore(new JwtTokenStore(jwtAccessToken))
                .authenticationManager(authenticationManager);
                //.accessTokenConverter(jwtAccessToken);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient(PROP_CLIENTID)
                .secret(PROP_SECRET)
                .authorities(Authorities.ROLE_ADMIN.name(), Authorities.ROLE_USER.name())
                .authorizedGrantTypes("password", "authorization_code","refresh_token","implicit")
                .scopes("read", "write","trust")
                .accessTokenValiditySeconds(Integer.parseInt(PROP_TOKEN_VALIDITY_SECONDS));
    }

  
}


