package com.crm.qa.apiTests;

import com.crm.qa.api.ApiClient;
import com.crm.qa.api.BaseApiTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * DELETE /users/{id} API Test
 *
 * Interview Focus:
 * - Verifying delete operation
 * - Status code validation
 */
public class DeleteUserApiTest extends BaseApiTest {

    @Test(description = "Validate Delete User API")
    public void deleteUserTest() {

        // Create ApiClient instance
        ApiClient apiClient = new ApiClient();

        Response response = apiClient.delete("/users/1");

        // JSONPlaceholder returns 200 for DELETE
        Assert.assertEquals(response.statusCode(), 200,
                "Delete API status code mismatch");
    }
}
