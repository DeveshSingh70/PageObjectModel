package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.qa.base.TestBase;

public class LoginPage extends TestBase {

    // ================= Page Factory (Object Repository) =================

    // Email / Username field
    @FindBy(name = "email")
    WebElement email;

    // Password field
    @FindBy(name = "password")
    WebElement password;

    // Login button (UPDATED for new FreeCRM UI)
    @FindBy(xpath = "//div[contains(text(),'Login')]")
    WebElement loginBtn;

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

    // Perform login action
    public HomePage login(String un, String pwd) {
        email.clear();
        email.sendKeys(un);

        password.clear();
        password.sendKeys(pwd);

        loginBtn.click();

        return new HomePage(); // navigation to HomePage
    }
}
