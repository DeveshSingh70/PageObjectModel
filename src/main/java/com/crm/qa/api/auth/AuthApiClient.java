package com.crm.qa.api.auth;

import com.crm.qa.api.pojo.LoginRequest;
import com.crm.qa.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * AuthApiClient
 * ----------------
 * Responsible ONLY for calling authentication APIs.
 * <p>
 * ⚠ IMPORTANT DESIGN NOTES:
 * - This class does NOT cache token
 * - This class does NOT add Authorization header
 * - This class does NOT affect non-auth APIs
 * <p>
 * Token caching and lifecycle will be handled
 * by TokenManager (next step).
 */
public class AuthApiClient {

    /**
     * Calls Login API and returns access token
     *
     * @return access token as String
     * @throws RuntimeException if login fails
     */
    public static String generateToken() {

        // -------------------------------
        // Build login request payload
        // -------------------------------
        LoginRequest loginRequest = new LoginRequest(
                ConfigReader.getProperty("api.username"),
                ConfigReader.getProperty("api.password")
        );

        // -------------------------------
        // Temporarily override base URI
        // Auth systems often run on a
        // different domain than main APIs
        // -------------------------------
        String authBaseUrl =
                ConfigReader.getProperty("auth.base.url");

        RestAssured.baseURI = authBaseUrl;

        // -------------------------------
        // Call Login API
        // -------------------------------
        Response response =
                given()
                        .log().all()
                        .contentType("application/json")
                        .accept("application/json")
                        .body(loginRequest)
                        .when()
                        .post(ConfigReader.getProperty("auth.login.endpoint"))
                        .then()
                        .log().all()
                        .extract()
                        .response();

        // -------------------------------
        // Fail fast if authentication fails
        // -------------------------------
        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Authentication failed. Status Code: "
                            + response.statusCode()
                            + " | Response: "
                            + response.asString()
            );
        }

        // -------------------------------
        // Extract token from response
        // -------------------------------
        String token = response.jsonPath().getString("token");

        if (token == null || token.isEmpty()) {
            throw new RuntimeException(
                    "Token not found in authentication response"
            );
        }

        return token;
    }
}

