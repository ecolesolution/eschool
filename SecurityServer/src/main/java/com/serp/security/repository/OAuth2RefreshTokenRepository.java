/**
 * 
 */
package com.serp.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.serp.security.domain.OAuth2RefreshToken;

/**
 * @author sanjeet choudhary
 *
 */
public interface OAuth2RefreshTokenRepository extends MongoRepository<OAuth2RefreshToken, String>{

	public OAuth2RefreshToken findByTokenId(String tokenId);
	public boolean deleteByTokenId(String tokenId);
}
