package com.matuageorge.votingapplication.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Iterator;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class VoteRestApiControllerTest {
    private final String CONTEXT_PATH = "/api/v1/voting/votes";

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
    }

    @Test
    @Order(1)
    void vote() {
        String newVote = """
                {
                    "restaurantName":"Tanuki"
                }
                """;

        given().
                auth().basic("user@i.c", "user")
                .contentType(JSON)
                .accept("application/json")
                .body(newVote)
                .when()
                .post(CONTEXT_PATH)
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    void getVotingResultsDetailedByDate() {
        Response response = given().
                auth().basic("admin@i.c", "admin")
                .pathParam("date", "16-05-2021")
                .when()
                .get(CONTEXT_PATH + "/results/{date}")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract().response();

        String bodyString = response.body().asString();

        String[] expectedJsonKeys = {"Restaurant(name=Caramel)", "Restaurant(name=Tanuki)"};

        try {
            JSONObject responseBodyJson = new JSONObject(bodyString);
            Iterator<?> keys = responseBodyJson.keys();
            int counter = 0;
            while (keys.hasNext()) {
                String key = (String) keys.next();
                assertEquals(expectedJsonKeys[counter], key);
                counter++;
            }
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(3)
    void getSimpleVotingResults() {
        Response response = given().
                auth().basic("admin@i.c", "admin")
                .pathParam("date", "16-05-2021")
                .when()
                .get(CONTEXT_PATH + "/simple-results/{date}")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract().response();

        String bodyString = response.body().asString();

        String[] expectedJsonKeys = {"Restaurant(name=Caramel)", "Restaurant(name=Tanuki)"};

        try {
            JSONObject responseBodyJson = new JSONObject(bodyString);
            Iterator<?> keys = responseBodyJson.keys();
            int counter = 0;
            while (keys.hasNext()) {
                String key = (String) keys.next();
                assertEquals(expectedJsonKeys[counter], key);
                counter++;
            }
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(4)
    void deleteById() {
        given().
                auth().basic("admin@i.c", "admin")
                .pathParam("voteId", 2)
                .contentType(JSON)
                .accept("application/json")
                .when()
                .delete(CONTEXT_PATH + "/{voteId}")
                .then()
                .statusCode(200);
    }
}