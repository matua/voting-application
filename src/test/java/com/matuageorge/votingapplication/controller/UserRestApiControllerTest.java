package com.matuageorge.votingapplication.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class UserRestApiControllerTest {
    private final String CONTEXT_PATH = "/api/v1/voting/users";

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
    }

    @Test
    @Order(1)
    void addNewUser() {
        String newUser = """
                {
                  "email": "new@i.c",
                  "password": "new"
                }
                """;

        given().
                auth().basic("admin@i.c", "admin")
                .contentType(JSON)
                .accept("application/json")
                .body(newUser)
                .when()
                .post(CONTEXT_PATH)
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    void getUserByEmail() {
        Response response = given().
                auth().basic("admin@i.c", "admin")
                .pathParam("userEmail", "new@i.c")
                .when()
                .get(CONTEXT_PATH + "/{userEmail}")
                .then()
                .statusCode(200)
                .extract().response();

        String userEmail = response.jsonPath().getString("email");
        String expectedUserEmail = "new@i.c";
        assertEquals(expectedUserEmail, userEmail);
    }

    @Test
    @Order(3)
    void getAllUsers() {
        Response response = given().
                auth().basic("admin@i.c", "admin")
                .pathParam("offset", 0)
                .pathParam("limit", 5)
                .when()
                .get(CONTEXT_PATH + "/{offset}/{limit}")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract().response();

        List<String> users = response.jsonPath().getList("entities.email");

        assertEquals(5, users.size());
        String[] expectedRestaurants = {"admin@i.c", "user@i.c", "user2@i.c", "user3@i.c", "notactivated@i.c"};
        assertEquals(Arrays.asList(expectedRestaurants), users);
    }

    @Test
    @Order(4)
    void updateUser() {
        String updatedUser = """
                {
                  "email": "updated@i.c",
                  "password": "updated"
                }""";

        given().
                auth().basic("admin@i.c", "admin")
                .pathParam("userEmail", "new@i.c")
                .contentType(JSON)
                .accept("application/json")
                .body(updatedUser)
                .when()
                .put(CONTEXT_PATH + "/{userEmail}")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(5)
    void toggleUserActivateStatus() {
        given().
                auth().basic("admin@i.c", "admin")
                .pathParam("userEmail", "updated@i.c")
                .contentType(JSON)
                .accept("application/json")
                .when()
                .put(CONTEXT_PATH + "/{userEmail}/toggle-status")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(6)
    void deleteUserByEmail() {
        given().
                auth().basic("admin@i.c", "admin")
                .pathParam("userEmail", "updated@i.c")
                .contentType(JSON)
                .accept("application/json")
                .when()
                .delete(CONTEXT_PATH + "/{userEmail}")
                .then()
                .statusCode(200);
    }
}