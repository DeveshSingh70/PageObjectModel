package com.crm.qa.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * ApiClient
 * ---------
 * Centralized HTTP client for API calls
 *
 * Interview Point:
 * "All request execution and logging is handled here.
 *  Tests focus only on assertions."
 */
public class ApiClient {

    public static Response get(String endpoint) {
        return given()
                .spec(BaseApiTest.requestSpec)
                .log().all()
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response post(String endpoint, Object body) {
        return given()
                .spec(BaseApiTest.requestSpec)
                .body(body)
                .log().all()
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response put(String endpoint, Object body) {
        return given()
                .spec(BaseApiTest.requestSpec)
                .body(body)
                .log().all()
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response delete(String endpoint) {
        return given()
                .spec(BaseApiTest.requestSpec)
                .log().all()
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
