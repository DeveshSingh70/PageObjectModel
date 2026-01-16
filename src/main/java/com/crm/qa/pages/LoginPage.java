package com.crm.qa.pages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.crm.qa.base.TestBase;

public class LoginPage extends TestBase {

    // ================= Page Factory (Object Repository) =================

    // Email / Username field
    @FindBy(name = "email")
    WebElement email;

    // Password field
    @FindBy(name = "password")
    WebElement password;

    // Login button (FreeCRM new UI)
    @FindBy(xpath = "//div[contains(text(),'Login')]")
    WebElement loginBtn;

    // Post-login element (used for sync)
    @FindBy(xpath = "//span[text()='Contacts']/parent::a")
    WebElement contactsMenu;

    // ================= Constructor =================

    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    // ================= Page Actions =================

    // Validate Login Page Title
    public String validateLoginPageTitle() {
        return driver.getTitle();
    }

    // Check if Email field is enabled
    public boolean isEmailFieldEnabled() {
        return email.isEnabled();
    }

    // Check if Login button is displayed
    public boolean isLoginButtonDisplayed() {
        return loginBtn.isDisplayed();
    }

    /**
     * Perform login and wait for successful navigation
     *
     * @param un  username
     * @param pwd password
     * @return HomePage
     */
    public HomePage login(String un, String pwd) {

        email.clear();
        email.sendKeys(un);

        password.clear();
        password.sendKeys(pwd);

        loginBtn.click();

        return new HomePage();
    }
}
