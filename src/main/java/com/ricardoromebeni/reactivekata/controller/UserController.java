package com.ricardoromebeni.reactivekata.controller;

import com.ricardoromebeni.reactivekata.model.User;
import com.ricardoromebeni.reactivekata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Flux<User> findAll(@RequestParam(name = "age", required = false) Integer age) {
        return userService.findAll(age);
    }

    @GetMapping("/{id}")
    public Mono<User> findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> findAllStream() {
        return userService.findAllStream();
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

}
