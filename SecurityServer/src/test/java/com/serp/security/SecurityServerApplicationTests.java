package com.serp.security;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.serp.security.domain.Authority;
import com.serp.security.domain.User;
import com.serp.security.repository.AuthorityRepository;
import com.serp.security.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityServerApplicationTests {

	@Autowired
	MongoOperations operations;

	@Autowired
	UserRepository userRepo;
	@Autowired
	AuthorityRepository authorityRepository;

	@Before
	public void setUp() {

		if(!(operations.collectionExists(Authority.class) && operations.collectionExists(User.class))) {
		Authority authorities = new Authority("ROLE_ADMIN");
		Set<Authority> set = new HashSet<Authority>();
		
		set.add(authorities);
		authorityRepository.save(set);
		User user = new User("admin", "$2a$10$MOEVFxXay10liUlakiBAh.K028fUmACIHc8yX9z4bCtix.V.Z1sb6",
				"sanjeet@outlook.com", true, "", "", set);
		userRepo.save(user);
		}

	}

	@Test
	public void contextLoads() {
	}

}
