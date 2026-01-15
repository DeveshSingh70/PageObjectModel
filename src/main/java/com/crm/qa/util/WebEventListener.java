package com.crm.qa.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import com.crm.qa.base.TestBase;
import com.crm.qa.util.TestUtil;

public class WebEventListener implements WebDriverListener {

    @Override
    public void beforeGet(WebDriver driver, String url) {
        System.out.println("Navigating to: " + url);
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        System.out.println("Navigation completed: " + url);
    }

    @Override
    public void beforeClick(WebElement element) {
        System.out.println("Clicking element: " + element);
    }

    @Override
    public void afterClick(WebElement element) {
        System.out.println("Clicked element: " + element);
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        System.out.println("Finding element: " + locator);
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement element) {
        System.out.println("Element found: " + locator);
    }

   /* @Override
    public void afterGetScreenshotAs(WebDriver driver, OutputType<?> outputType, Object screenshot) {
        System.out.println("Screenshot captured");
    }
*/
    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        System.out.println("ERROR OCCURRED: " + e.getMessage());
        try {
            WebDriver driver = TestBase.driver; // use static driver from TestBase
            TestUtil.takeScreenshotAtEndOfTest(driver, "ErrorScreenshot");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
