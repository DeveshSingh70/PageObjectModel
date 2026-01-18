package com.crm.qa.api;

import com.crm.qa.api.auth.TokenManager;
import com.crm.qa.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;

/**
 * BaseApiTest
 * -----------
 * Central API configuration class.
 *
 * Responsibilities:
 * 1. Initialize RestAssured Base URI
 * 2. Create reusable RequestSpecification
 * 3. Conditionally attach Authorization token
 *
 * IMPORTANT:
 * - This class NEVER calls AuthApiClient directly
 * - Token lifecycle is handled by TokenManager
 *
 * This design allows authenticated and
 * non-authenticated APIs to coexist safely
 * in the same TestNG suite.
 */
public class BaseApiTest {

    /**
     * Shared RequestSpecification
     * Used by all API test classes
     */
    protected static RequestSpecification requestSpec;

    /**
     * Runs ONCE before entire API suite execution
     */
    @BeforeSuite
    public void setupApi() {

        // ---------------------------------
        // Set base URI for non-auth APIs
        // ---------------------------------
        RestAssured.baseURI =
                ConfigReader.getProperty("api.base.url");

        /**
         * Build base request specification
         *
         * NOTE:
         * - Content-Type should be set at test level
         * - Accept header is safe globally
         */
        RequestSpecBuilder builder =
                new RequestSpecBuilder()
                        .addHeader("Accept", "application/json");

        /**
         * ---------------------------------
         * OPTIONAL TOKEN HANDLING
         * ---------------------------------
         * Token is generated ONLY if:
         * api.token.enabled=true
         *
         * TokenManager guarantees:
         * - Token is generated once
         * - Token is cached
         * - Thread-safe access
         */
        String tokenEnabled =
                ConfigReader.getProperty("api.token.enabled");

        if ("true".equalsIgnoreCase(
                tokenEnabled == null ? "false" : tokenEnabled)) {

            String authToken = TokenManager.getToken();

            builder.addHeader(
                    "Authorization",
                    "Bearer " + authToken
            );
        }

        // ---------------------------------
        // Build reusable request specification
        // ---------------------------------
        requestSpec = builder.build();

        // ---------------------------------
        // Relax HTTPS validation
        // Useful for test / staging envs
        // ---------------------------------
        RestAssured.useRelaxedHTTPSValidation();
    }
}