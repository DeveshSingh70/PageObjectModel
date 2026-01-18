package com.crm.qa.apiTests;

import com.crm.qa.api.ApiClient;
import com.crm.qa.api.BaseApiTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * POST /users API Test
 *
 * Interview Focus:
 * - Validates API contract instead of persistence
 * - Handles mock API behavior correctly
 */
public class CreateUserApiTest extends BaseApiTest {

    @Test(description = "Validate Create User API")
    public void createUserTest() {


        String payload = """
                {
                  "name": "Devesh Singh",
                  "username": "devesh_sdet",
                  "email": "devesh@test.com"
                }
                """;

        // Create ApiClient instance
        ApiClient apiClient = new ApiClient();

        Response response = apiClient.post("/users", payload);

        // ===== Assertions =====
        Assert.assertEquals(response.statusCode(), 201, "Status code mismatch");

        // JSONPlaceholder returns id but does not persist data
        Assert.assertNotNull(response.jsonPath().getString("id"),
                "User ID should be generated");

        Assert.assertNotNull(response.asString(),
                "Response body should not be null");
    }
}
