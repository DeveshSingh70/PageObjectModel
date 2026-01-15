package com.crm.qa.testcases;

import com.crm.qa.util.TestUtil;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;

public class LoginPageTest extends TestBase {

    LoginPage loginPage;
    HomePage homePage;

    public LoginPageTest() {
        super();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        loginPage = new LoginPage();
    }

    /**
     * Validate email field is enabled
     */
    @Test(priority = 1)
    public void emailFieldEnabledTest() {
        Assert.assertTrue(
                loginPage.isEmailFieldEnabled(),
                "Email field is not enabled on Login page"
        );
    }

    /**
     * Validate login button is visible
     */
    @Test(priority = 2)
    public void loginButtonDisplayedTest() {
        Assert.assertTrue(
                loginPage.isLoginButtonDisplayed(),
                "Login button is not displayed"
        );
    }

    /**
     * Validate successful login
     */
    @Test(priority = 3)
    public void loginTest() {
        homePage = loginPage.login(
                prop.getProperty("username"),
                prop.getProperty("password")
        );

        // ✅ Step 2: Validate Contacts menu
        Assert.assertTrue(
                homePage.isContactsMenuVisible(),
                "Login failed – Contacts menu not visible"
        );
    }


    @AfterMethod
    public void tearDown(ITestResult result) {

        if (ITestResult.FAILURE == result.getStatus()) {
            // ✅ PASS DRIVER + TEST NAME
            TestUtil.takeScreenshotAtEndOfTest(driver, result.getName());
        }

        if (driver != null) {
            driver.quit();
        }
    }

}
