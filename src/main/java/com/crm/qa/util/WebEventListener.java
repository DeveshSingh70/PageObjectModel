package com.crm.qa.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

/**
 * WebEventListener
 *
 * Listens to Selenium WebDriver events and logs actions.
 *
 * IMPORTANT NOTE:
 * Selenium WebDriverWait internally throws NoSuchElementException
 * during polling. This is EXPECTED behavior.
 *
 * If we don't ignore it here, logs will show false failures
 * even though the test PASSES.
 */
public class WebEventListener implements WebDriverListener {

    // ===================== NAVIGATION =====================

    @Override
    public void beforeGet(WebDriver driver, String url) {
        System.out.println("[NAVIGATION] Opening URL: " + url);
    }

    // ===================== FIND ELEMENT =====================

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        System.out.println("[FIND] Locating element: " + locator);
    }

    // ===================== CLICK =====================

    @Override
    public void beforeClick(WebElement element) {
        System.out.println("[ACTION] Clicking element: " + element);
    }

    // ===================== ERROR HANDLING (MOST IMPORTANT) =====================

    /**
     * This method is triggered whenever Selenium throws an exception.
     *
     * FIX APPLIED:
     * - Ignore NoSuchElementException thrown during WebDriverWait polling
     * - Capture screenshot ONLY for real failures
     * - Rethrow real exceptions to FAIL the test
     */
    @Override
    public void onError(Object target,
                        Method method,
                        Object[] args,
                        InvocationTargetException e) {

        Throwable cause = e.getTargetException();

        // ✅ IGNORE NoSuchElementException during explicit waits
        if (cause instanceof NoSuchElementException) {
            return; // do NOT log, do NOT take screenshot, do NOT fail
        }

        // ❌ REAL FAILURE (log + screenshot + fail)
        System.err.println("[SELENIUM ERROR] Method: " + method.getName());
        System.err.println("[SELENIUM ERROR] Cause: " + cause);

        // Capture screenshot for debugging
        TestUtil.takeScreenshotAtEndOfTest(method.getName());

        // Fail the test
        throw new RuntimeException(cause);
    }
}
