package com.crm.qa.pages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.crm.qa.base.TestBase;

public class HomePage extends TestBase {

    // ================= Locators =================

    /*
     * Contacts menu in left sidebar
     * span[text()='Contacts'] is directly inside <a> tag
     * So we MUST use parent::a (NOT ancestor::a)
     */
    @FindBy(xpath = "//span[text()='Contacts']/parent::a")
    WebElement contactsMenu;

    // Visible only on hover over Contacts
    @FindBy(xpath = "//a[@role='menuitem' and .='New']")
    WebElement newContactLink;


    @FindBy(xpath = "//span[text()='Deals']/parent::a")
    WebElement dealsLink;

    @FindBy(xpath = "//span[text()='Tasks']/parent::a")
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

    /*
     * Used in LoginTest
     * Confirms login success by checking Contacts menu visibility
     */
    public boolean isContactsMenuVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.visibilityOf(contactsMenu)).isDisplayed();
    }

    // Click Contacts menu
    public ContactsPage clickOnContactsMenu() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(contactsMenu)).click();
        return new ContactsPage();
    }

    /*
     * Hover over Contacts and click New Contact
     * Used in NewContact test
     */
    public void clickOnNewContactLink() {
        contactsMenu.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(newContactLink)).click();
    }



    public DealsPage clickOnDealsLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(dealsLink)).click();
        return new DealsPage();
    }

    public TasksPage clickOnTasksLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(tasksLink)).click();
        return new TasksPage();
    }
}
