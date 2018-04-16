package com.serp.finance.repo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.serp.finance.entity.FeeCategory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface FeeCategoryRepository extends ReactiveMongoRepository<FeeCategory, String> {
	 //public FeeCategory findByCategoryName(String name);

	Flux<FeeCategory> findByCategoryName(Mono<String> name);

	 @Query("{ 'categoryName': ?0}")
	Mono<FeeCategory> findByNameAndImageUrl(String name);
}
