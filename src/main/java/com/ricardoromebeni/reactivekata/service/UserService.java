package com.ricardoromebeni.reactivekata.service;

import com.ricardoromebeni.reactivekata.model.User;
import com.ricardoromebeni.reactivekata.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Flux<User> findAll(Integer age) {
        if (age != null) {
            return userRepository.findAll().filter(user -> user.getAge() < age);
        }
        return userRepository.findAll();
    }

    public Mono<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Flux<User> findAllStream() {
        return Flux.zip(Flux.interval(Duration.ofSeconds(1)), userRepository.findAll()).map(Tuple2::getT2);
    }

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }
}
