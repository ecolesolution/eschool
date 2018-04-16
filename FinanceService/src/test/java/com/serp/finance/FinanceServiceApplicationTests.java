package com.serp.finance;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.serp.finance.entity.FeeCategory;
import com.serp.finance.repo.FeeCategoryRepository;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinanceServiceApplicationTests {
	
    @Autowired
    FeeCategoryRepository repository;

    @Autowired
    ReactiveMongoOperations operations;

    @Before
    public void setUp() {
		 operations.collectionExists(FeeCategory.class).flatMap(exists -> exists ? operations.dropCollection(FeeCategory.class)
				 : Mono.just(exists))
		 .flatMap(o -> operations.createCollection(FeeCategory.class))
		 .then()
         .block();;
		Set<String> batchIds=Set.of("B101","B102","D001");
		final FeeCategory category=new FeeCategory("Batch2k18", "2018 batch fee plan", batchIds);
		repository.save(category)
		.then()
        .block();;
	}

	@Test
	public void contextLoads() {
		
	}

}
