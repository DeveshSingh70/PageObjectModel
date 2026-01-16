package com.crm.qa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.crm.qa.base.TestBase;

public class HomePage extends TestBase {

    // ================= Locators =================

    // Contacts menu in left sidebar (React-safe)
    @FindBy(css = "a[href='/contacts']")
    WebElement contactsMenu;

    // Visible only on hover / click over Contacts
    @FindBy(xpath = "//a[@role='menuitem' and .='New']")
    WebElement newContactLink;

    // Deals menu
    @FindBy(css = "a[href='/deals']")
    WebElement dealsLink;

    // Tasks menu
    @FindBy(css = "a[href='/tasks']")
    WebElement tasksLink;

    // ================= Constructor =================
    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    // ================= Actions =================

    // Verify Home Page title
    public String verifyHomePageTitle() {
        return driver.getTitle();
    }

    // Confirms login success by checking Contacts menu visibility
    public boolean isContactsMenuVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOf(contactsMenu));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Click Contacts menu
    public ContactsPage clickOnContactsMenu() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(contactsMenu)).click();
        return new ContactsPage();
    }

    // Click New Contact
    public void clickOnNewContactLink() {
        contactsMenu.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(newContactLink)).click();
    }

    // Click Deals menu
    public DealsPage clickOnDealsLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(dealsLink)).click();
        return new DealsPage();
    }

    // Click Tasks menu
    public TasksPage clickOnTasksLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(tasksLink)).click();
        return new TasksPage();
    }
}
