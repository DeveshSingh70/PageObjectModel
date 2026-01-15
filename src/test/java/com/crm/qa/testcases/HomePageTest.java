package com.crm.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;

public class HomePageTest extends TestBase {

    LoginPage loginPage;
    HomePage homePage;
    ContactsPage contactsPage;

    public HomePageTest() {
        super();
    }

    // ================= Setup =================
    @BeforeMethod
    public void setUp() {
        initialization();
        loginPage = new LoginPage();
        homePage = loginPage.login(
                prop.getProperty("username"),
                prop.getProperty("password")
        );
    }

    // ================= Tests =================

    /**
     * Verify login successful by checking Contacts menu visibility
     */
    @Test(priority = 1)
    public void verifyLoginSuccessTest() {
        Assert.assertTrue(
                homePage.isContactsMenuVisible(),
                "Login failed: Contacts menu not visible"
        );
    }

    /**
     * Verify clicking Contacts menu navigates to Contacts page
     */
    @Test(priority = 2)
    public void verifyContactsMenuClickTest() {
        contactsPage = homePage.clickOnContactsMenu();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("contacts"),
                "Contacts page not opened"
        );
    }

    // ================= Teardown =================
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
