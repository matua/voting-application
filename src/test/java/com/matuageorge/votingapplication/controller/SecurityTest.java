package com.matuageorge.votingapplication.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = NONE)
class SecurityTest {
    private final String VOTE_CONTEXT_PATH = "/api/v1/voting/votes";
    private final String DISH_CONTEXT_PATH = "/api/v1/voting/dishes";
    private final String RESTAURANT_CONTEXT_PATH = "/api/v1/voting/restaurants";
    private final String USER_CONTEXT_PATH = "/api/v1/voting/users";

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
    }

    @Test
    @Order(1)
    void voteByUnactivatedUser() {
        String newVote = """
                {
                    "restaurantName":"Tanuki"
                }
                """;

        given().
                auth().basic("unactivated@i.c", "unactivated")
                .contentType(JSON)
                .accept("application/json")
                .body(newVote)
                .when()
                .post(VOTE_CONTEXT_PATH)
                .then()
                .statusCode(401);
    }

    @Test
    @Order(2)
    void voteByUserWithWrongPassword() {
        String newVote = """
                {
                    "restaurantName":"Tanuki"
                }
                """;

        given().
                auth().basic("user@i.c", "wrong_password")
                .contentType(JSON)
                .accept("application/json")
                .body(newVote)
                .when()
                .post(VOTE_CONTEXT_PATH)
                .then()
                .statusCode(401);
    }

    @Test
    @Order(2)
    void getVotingResultsDetailedByDateByUnauthorizedUser() {
        given()
                .pathParam("date", "16-05-2021")
                .get(VOTE_CONTEXT_PATH + "/results/{date}")
                .then()
                .statusCode(401);
    }

    @Test
    @Order(3)
    void getSimpleVotingResultsDetailedByDateByUnauthorizedUser() {
        given()
                .pathParam("date", "16-05-2021")
                .get(VOTE_CONTEXT_PATH + "/simple-results/{date}")
                .then()
                .statusCode(401);
    }

    @Test
    @Order(4)
    void toggleUserActivateStatusByUnauthenticatedUser() {
        given()
                .pathParam("userEmail", "user2@i.c")
                .contentType(JSON)
                .accept("application/json")
                .when()
                .put(USER_CONTEXT_PATH + "/{userEmail}/toggle-status")
                .then()
                .statusCode(401);
    }

    @Test
    void testGetUserByEmailByUnauthorizedUser() {
        given().
                auth().basic("user@i.c", "user")
                .pathParam("userEmail", "new@i.c")
                .when()
                .get(USER_CONTEXT_PATH + "/{userEmail}")
                .then()
                .statusCode(403);
    }
}