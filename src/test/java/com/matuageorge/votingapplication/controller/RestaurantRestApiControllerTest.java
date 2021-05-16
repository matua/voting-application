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

class RestaurantRestApiControllerTest {
    private final String CONTEXT_PATH = "/api/v1/voting/restaurants";

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
    }

    @Test
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
    void addDishToRestaurant() {
        String newDish = """
                {
                    "name": "Satsivi",
                    "price": "1000"
                }""";

        given().
                auth().basic("admin@i.c", "admin")
                .pathParam("restaurantId", 1)
                .contentType(JSON)
                .accept("application/json")
                .body(newDish)
                .when()
                .post(CONTEXT_PATH + "/{restaurantId}")
                .then()
                .statusCode(200);
    }

    @Test
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
                .pathParam("restaurantId", 3)
                .contentType(JSON)
                .accept("application/json")
                .body(newDishes)
                .when()
                .post(CONTEXT_PATH + "/{restaurantId}/dishes")
                .then()
                .statusCode(200);
    }

    @Test
    void getRestaurantById() {
        Response response = given().
                auth().basic("admin@i.c", "admin")
                .pathParam("restaurantId", 3)
                .when()
                .get(CONTEXT_PATH + "/search-by-id/{restaurantId}")
                .then()
                .statusCode(200)
                .extract().response();

        String restaurantName = response.jsonPath().getString("name");
        String expectedRestaurantName = "Tanuki";
        assertEquals(expectedRestaurantName, restaurantName);
    }

    @Test
    void getRestaurantByName() {
        Response response = given().
                auth().basic("admin@i.c", "admin")
                .pathParam("restaurantName", "El Mediterraneo")
                .when()
                .get(CONTEXT_PATH + "/search-by-name/{restaurantName}")
                .then()
                .statusCode(200)
                .extract().response();

        String restaurantName = response.jsonPath().getString("name");
        String expectedRestaurantName = "El Mediterraneo";
        assertEquals(expectedRestaurantName, restaurantName);
    }

    @Test
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

        assertEquals(3, restaurants.size());
        String[] expectedRestaurants = {"El Mediterraneo", "Tanuki", "New Restaurant"};
        assertEquals(Arrays.asList(expectedRestaurants), restaurants);
    }

    @Test
    void updateRestaurant() {
        String updatedRestaurant = """
                {
                  "name": "Updated Restaurant"
                }""";

        given().
                auth().basic("admin@i.c", "admin")
                .pathParam("restaurantId", 1)
                .contentType(JSON)
                .accept("application/json")
                .body(updatedRestaurant)
                .when()
                .put(CONTEXT_PATH + "/{restaurantId}")
                .then()
                .statusCode(200);
    }

    @Test
    void deleteRestaurantById() {
        given().
                auth().basic("admin@i.c", "admin")
                .pathParam("restaurantId", 2)
                .contentType(JSON)
                .accept("application/json")
                .when()
                .delete(CONTEXT_PATH + "/{restaurantId}")
                .then()
                .statusCode(200);
    }
}