package com.matuageorge.votingapplication.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;

class RestaurantRestApiControllerTest extends Object {
    private final String CONTEXT_PATH = "/api/v1/voting";

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
    }

    @Test
    void addNewRestaurant() {
        String payload = "{\n" +
                "  \"name\": \"Paradise Food\"\n" +
                "}";

        given().auth()
                .basic("admin@i.c", "admin")
                .contentType(JSON)
                .body(payload)
                .when()
                .post(CONTEXT_PATH + "/restaurants")
                .then()
                .assertThat()
                .statusCode(OK.value());

    }

    @Test
    void addDishToRestaurant() {
    }

    @Test
    void addDishesToRestaurant() {
    }

    @Test
    void getRestaurantById() {
    }

    @Test
    void getRestaurantByName() {
    }

    @Test
    void getAllRestaurants() {
        Response response = given().
                auth().basic("admin@i.c", "admin")
                .pathParam("offset", 0)
                .pathParam("limit", 5)
                .when()
                .get(CONTEXT_PATH + "/restaurants/{offset}/{limit}")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract().response();

        List<String> restaurants = response.jsonPath().getList("entities.name");

        assertEquals(3, restaurants.size());
        String[] expectedRestaurants = { "El Mediterraneo", "Caramel", "Tanuki" };
        assertEquals(restaurants, Arrays.asList(expectedRestaurants));
    }

    @Test
    void updateRestaurant() {
    }

    @Test
    void deleteRestaurantById() {
    }
}