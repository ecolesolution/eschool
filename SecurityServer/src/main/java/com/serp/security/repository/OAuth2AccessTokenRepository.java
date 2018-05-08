/**
 * 
 */
package com.serp.security.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.serp.security.domain.OAuth2AccessToken;

/**
 * @author sanjeet choudhary
 *
 */
public interface OAuth2AccessTokenRepository extends MongoRepository<OAuth2AccessToken, String> {

	@Query("{'_id':?0}")
	public OAuth2AccessToken findByTokenId(String tokenId);
	@Query("{'_id':?0}")
	public OAuth2AccessToken deleteByTokenId(String tokenId);
	public OAuth2AccessToken deleteByRefreshToken(String refreshToken);
	//@Query("{'authenticationId':?0}")
	public OAuth2AccessToken findByAuthenticationId(String authenticationId);
	public List<OAuth2AccessToken> findByUsernameAndClientId(String username,String clientId);
	public List<OAuth2AccessToken> findByClientId(String clientId);
	
	
	
}
