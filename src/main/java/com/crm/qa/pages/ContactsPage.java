package com.crm.qa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.crm.qa.base.TestBase;

/**
 * ContactsPage
 *
 * Page Object for Contacts screen
 */
public class ContactsPage extends TestBase {

    // ================= Locators =================

    @FindBy(xpath = "//div[@class='ui header item mb5 light-black']")
    private WebElement contactsLabel;

    @FindBy(name = "first_name")
    private WebElement firstName;

    @FindBy(name = "last_name")
    private WebElement lastName;

    @FindBy(name = "company")
    private WebElement companyInput;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElement saveButton;

    // ================= Constructor =================

    public ContactsPage() {
        PageFactory.initElements(driver, this);
    }

    // ================= Utilities =================

    private WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ================= Actions =================

    public boolean verifyContactsLabel() {
        return getWait()
                .until(ExpectedConditions.visibilityOf(contactsLabel))
                .isDisplayed();
    }

    public void clickOnCreateButton() {
        WebElement createBtn = getWait().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(),'Create')]")
                )
        );
        createBtn.click();
    }

    public void createNewContact(String fName, String lName, String companyName) {

        getWait().until(ExpectedConditions.visibilityOf(firstName)).sendKeys(fName);
        getWait().until(ExpectedConditions.visibilityOf(lastName)).sendKeys(lName);

        enterCompanyName(companyName);

        getWait().until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    /**
     * Handles React autocomplete Company field
     */
    private void enterCompanyName(String companyName) {
        try {
            WebElement company = getWait()
                    .until(ExpectedConditions.elementToBeClickable(companyInput));

            new Actions(driver)
                    .moveToElement(company)
                    .click()
                    .sendKeys(companyName)
                    .perform();

        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].value=arguments[1];",
                    companyInput,
                    companyName
            );
        }
    }
}
