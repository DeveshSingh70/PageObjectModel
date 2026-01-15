package com.crm.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;

/**
 * ContactsPageTest
 * Test cases related to Contacts page
 */
public class ContactsPageTest extends TestBase {

    LoginPage loginPage;
    HomePage homePage;
    ContactsPage contactsPage;

    public ContactsPageTest() {
        super();
    }

    // ================= Setup =================
    @BeforeMethod
    public void setUp() {

        initialization();

        loginPage = new LoginPage();

        // Login
        homePage = loginPage.login(
                prop.getProperty("username"),
                prop.getProperty("password")
        );

        // Navigate to Contacts page
        contactsPage = homePage.clickOnContactsMenu();
    }

    // ================= Tests =================

    /**
     * Verify Contacts page is loaded
     */
    @Test(priority = 1)
    public void verifyContactsPageLabelTest() {
        Assert.assertTrue(
                contactsPage.verifyContactsLabel(),
                "Contacts page header not visible"
        );
    }

    /**
     * Verify creating a new contact
     */
    @Test(priority = 2)
    public void createNewContactTest() {

        // Click Create button
        contactsPage.clickOnCreateButton();

        // Create contact
        contactsPage.createNewContact(
                "Tom",
                "Peter",
                "Google"
        );
    }

    // ================= Teardown =================
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
