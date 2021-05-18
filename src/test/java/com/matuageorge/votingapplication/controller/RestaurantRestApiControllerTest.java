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
class RestaurantRestApiControllerTest {
    private final String CONTEXT_PATH = "/api/v1/voting/restaurants";

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
    }

    @Test
    @Order(1)
    void addNewRestaurant() {
        String newRestaurant = """
                {
                    "name": "New Restaurant"
                }
                """;

        given().
                auth().basic("admin@i.c", "admin")
                .contentType(JSON)
                .accept("application/json")
                .body(newRestaurant)
                .when()
                .post(CONTEXT_PATH)
                .then()
                .statusCode(200);
    }


    @Test
    @Order(2)
    void addDishesToRestaurant() {
        String newDishes = """
                [
                    {
                        "name": "Shaurma",
                        "price": 450
                      },
                    {
                        "name": "Pertsi",
                        "price": 900
                    }
                ]""";

        given().
                auth().basic("admin@i.c", "admin")
                .pathParam("restaurantId", 4)
                .contentType(JSON)
                .accept("application/json")
                .body(newDishes)
                .when()
                .post(CONTEXT_PATH + "/{restaurantId}/dishes")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    void getRestaurantById() {
        Response response = given().
                auth().basic("admin@i.c", "admin")
                .pathParam("restaurantId", 4)
                .when()
                .get(CONTEXT_PATH + "/search-by-id/{restaurantId}")
                .then()
                .statusCode(200)
                .extract().response();

        String restaurantName = response.jsonPath().getString("name");
        String expectedRestaurantName = "New Restaurant";
        assertEquals(expectedRestaurantName, restaurantName);
    }

    @Test
    @Order(4)
    void getRestaurantByName() {
        Response response = given().
                auth().basic("admin@i.c", "admin")
                .pathParam("restaurantName", "New Restaurant")
                .when()
                .get(CONTEXT_PATH + "/search-by-name/{restaurantName}")
                .then()
                .statusCode(200)
                .extract().response();

        String restaurantName = response.jsonPath().getString("name");
        String expectedRestaurantName = "New Restaurant";
        assertEquals(expectedRestaurantName, restaurantName);
    }

    @Test
    @Order(5)
    void getAllRestaurants() {
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

        List<String> restaurants = response.jsonPath().getList("entities.name");

        assertEquals(4, restaurants.size());
        String[] expectedRestaurants = {"El Mediterraneo", "Caramel", "Tanuki", "New Restaurant"};
        assertEquals(Arrays.asList(expectedRestaurants), restaurants);
    }

    @Test
    @Order(6)
    void updateRestaurant() {
        String updatedRestaurant = """
                {
                  "name": "Updated Restaurant"
                }""";

        given().
                auth().basic("admin@i.c", "admin")
                .pathParam("restaurantId", 4)
                .contentType(JSON)
                .accept("application/json")
                .body(updatedRestaurant)
                .when()
                .put(CONTEXT_PATH + "/{restaurantId}")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(7)
    void deleteRestaurantById() {
        given().
                auth().basic("admin@i.c", "admin")
                .pathParam("restaurantId", 1)
                .contentType(JSON)
                .accept("application/json")
                .when()
                .delete(CONTEXT_PATH + "/{restaurantId}")
                .then()
                .statusCode(200);
    }
}