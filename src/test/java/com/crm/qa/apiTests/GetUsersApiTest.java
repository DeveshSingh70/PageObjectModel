package com.crm.qa.apiTests;

import com.crm.qa.api.BaseApiTest;
import com.crm.qa.api.ApiClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class to validate GET /users API
 * <p>
 * Covers:
 * - Status code validation
 * - Response body validation
 * - Field-level assertions
 * - Nested JSON validation
 * <p>
 * Interview Point:
 * "Test layer focuses only on validation.
 * HTTP logic is abstracted in ApiClient."
 */
public class GetUsersApiTest extends BaseApiTest {

    @Test(description = "Validate GET Users API")
    public void getUsersTest() {

        // Create ApiClient instance
        ApiClient apiClient = new ApiClient();

        // API call via ApiClient
        Response response = apiClient.get("/users");

        // ===== Assertions =====

        // Status code validation
        Assert.assertEquals(response.statusCode(), 200,
                "Status code mismatch");

        // Validate response is not empty
        Assert.assertTrue(response.jsonPath().getList("$").size() > 0,
                "User list is empty");

        // Validate mandatory fields of first user
        Assert.assertNotNull(response.jsonPath().getString("[0].id"),
                "User ID is null");

        Assert.assertNotNull(response.jsonPath().getString("[0].name"),
                "User name is null");

        Assert.assertTrue(
                response.jsonPath().getString("[0].email").contains("@"),
                "Invalid email format"
        );

        // ===== Nested Object Validation =====
        Assert.assertNotNull(
                response.jsonPath().getString("[0].address.city"),
                "City is missing"
        );

        Assert.assertNotNull(
                response.jsonPath().getString("[0].company.name"),
                "Company name is missing"
        );

        // Optional: Print response for debugging
        response.prettyPrint();
    }
}
