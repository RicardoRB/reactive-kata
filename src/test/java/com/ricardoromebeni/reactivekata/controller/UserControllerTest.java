package com.ricardoromebeni.reactivekata.controller;

import com.ricardoromebeni.reactivekata.model.User;
import com.ricardoromebeni.reactivekata.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserRepository userRepository;

    private final User user1 = new User("Amitai", 18);
    private final User user2 = new User("Mario", 20);
    private final User user3 = new User("SongHee", 24);
    private final User user4 = new User("Karolina", 55);
    private final User user5 = new User("Ricardo", 99);

    @Test
    public void GIVEN_nothing_WHEN_Users_THEN_Return_All_Users() {

        BDDMockito.given(this.userRepository.findAll())
                .willReturn(Flux.just(user1, user2, user3, user4, user5));

        webTestClient
                .get().uri("/users")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class).contains(user1);

    }

    @Test
    public void GIVEN_An_Age_WHEN_Users_Age_THEN_Return_All_Users_That_Below_That_Age() {


        BDDMockito.given(this.userRepository.findAll())
                .willReturn(Flux.just(user1, user2, user3, user4, user5));

        webTestClient
                .get().uri(builder ->
                builder.path("users")
                        .queryParam("age", 25)
                        .build())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(3)
                .contains(user1, user2, user3);

    }

    @Test
    public void GIVEN_Nothing_WHEN_Users_Id_THEN_Return_All_Users_That_Below_That_Age() {

        BDDMockito.given(this.userRepository.findById(1L)).willReturn(Mono.just(user1));

        webTestClient
                .get().uri("/users/1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(user1);

    }

    @Test
    public void GIVEN_Nothing_WHEN_Users_Stream_THEN_Return_All_Users_Must_Be_Stream() {

        BDDMockito.given(this.userRepository.findAll())
                .willReturn(Flux.just(user1, user2, user3, user4, user5));

        webTestClient
                .get().uri("/users/stream")
                .exchange()
                .expectStatus().isOk();

    }
}
