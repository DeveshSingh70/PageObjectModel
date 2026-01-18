package com.crm.qa.apiTests;

import com.crm.qa.api.ApiClient;
import com.crm.qa.api.BaseApiTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * PUT /users/{id} API Test
 *
 * Interview Focus:
 * - Contract testing
 * - Handling non-persistent APIs
 */
public class UpdateUserApiTest extends BaseApiTest {

    @Test(description = "Validate Update User API")
    public void updateUserTest() {

        String payload = """
                {
                  "name": "Updated Devesh",
                  "email": "updated@test.com"
                }
                """;
        // Create ApiClient instance
        ApiClient apiClient = new ApiClient();

        Response response = apiClient.put("/users/1", payload);

        // ===== Assertions =====
        Assert.assertEquals(response.statusCode(), 200,
                "Status code mismatch for PUT request");

        // Validate response is returned
        Assert.assertNotNull(response.asString(),
                "Response body should not be null");
    }
}
