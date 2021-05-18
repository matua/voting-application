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
class DishRestApiControllerTest {
    private final String DISH_CONTEXT_PATH = "/api/v1/voting/dishes";
    private final String RESTAURANT_CONTEXT_PATH = "/api/v1/voting/restaurants";
    private final int DISH_ID = 15;
    private final int RESTAURANT_ID = 3;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
    }

    @Test
    @Order(1)
    void getDishesByRestaurantId() {
        Response response = given().
                auth().basic("admin@i.c", "admin")
                .pathParam("restaurantId", RESTAURANT_ID)
                .when()
                .get(DISH_CONTEXT_PATH + "/{restaurantId}")
                .then()
                .statusCode(200)
                .extract().response();

        List<String> dishes = response.jsonPath().getList("name");
        assertEquals(5, dishes.size());
        String[] expectedDishes = {"Jambalaya", "Biscuits 'n' gravy", "Smithfield ham", "Chicken fried steak", "Wild Alaska salmon"};
        assertEquals(Arrays.asList(expectedDishes), dishes);
    }

    @Test
    @Order(2)
    void updateDishById() {
        String updatedRestaurant = """
                {
                  "name": "Satsivi",
                  "price": 750
                }""";

        given().
                auth().basic("admin@i.c", "admin")
                .pathParam("dishId", DISH_ID)
                .contentType(JSON)
                .accept("application/json")
                .body(updatedRestaurant)
                .when()
                .put(DISH_CONTEXT_PATH + "/{dishId}")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    void addDishToRestaurant() {
        String newDish = """
                {
                    "name": "Pelmeni",
                    "price": "300"
                }""";

        given().
                auth().basic("admin@i.c", "admin")
                .pathParam("restaurantId", RESTAURANT_ID)
                .contentType(JSON)
                .accept("application/json")
                .body(newDish)
                .when()
                .post(RESTAURANT_CONTEXT_PATH + "/{restaurantId}")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    void deleteDishById() {
        given().
                auth().basic("admin@i.c", "admin")
                .pathParam("dishId", DISH_ID + 1)
                .contentType(JSON)
                .accept("application/json")
                .when()
                .delete(DISH_CONTEXT_PATH + "/{dishId}")
                .then()
                .statusCode(200);
    }
}