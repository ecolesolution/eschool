package com.serp.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.serp.security.domain.Authority;


/**
 * @author sanjeet choudhary
 *
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {
}
