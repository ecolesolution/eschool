package com.serp.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.serp.security.domain.User;

/**
 * @author sanjeet choudhary
 *
 */

public interface UserRepository extends MongoRepository<User, String> {

	User findByUsername(String username);

	User findByEmail(String email);

	@Query("{ 'email': ?0, 'activationKey': ?1}")
	User findByEmailAndActivationKey(String email, String activationKey);

	User findByEmailAndResetPasswordKey(String email, String resetPasswordKey);

}