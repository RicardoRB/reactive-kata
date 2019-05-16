package com.ricardoromebeni.reactivekata.repository;

import com.ricardoromebeni.reactivekata.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, Long> {
}
