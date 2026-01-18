package com.crm.qa.apiTests;

import com.crm.qa.api.BaseApiTest;
import com.crm.qa.api.auth.AuthApiClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.path.xml.XmlPath.given;

/**
 * LoginApiTest
 * ------------
 * Explicit test to validate Login API.
 *
 * IMPORTANT:
 * - This test only validates login response
 * - Token lifecycle is handled by TokenManager
 * - No token is stored here manually
 */
public class LoginApiTest extends BaseApiTest {

    @Test
    public void loginAndGenerateToken() {

        String token = AuthApiClient.generateToken();

        Assert.assertNotNull(token, "❌ Login failed: Token is null");
        Assert.assertFalse(token.isEmpty(), "❌ Login failed: Token is empty");

        System.out.println("✅ Login API PASSED | Token generated successfully");
    }




}
