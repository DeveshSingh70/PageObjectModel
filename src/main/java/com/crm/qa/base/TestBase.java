package com.crm.qa.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import com.crm.qa.util.TestUtil;
import com.crm.qa.util.WebEventListener;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

    public static WebDriver driver;
    public static Properties prop;

    // ================= Constructor =================
    // Loads configuration only once
    public TestBase() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir")
                            + "/src/main/java/com/crm/qa/config/config.properties");
            prop.load(ip);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    // ================= Browser Initialization =================
    public static void initialization() {

        String browserName = prop.getProperty("browser").toLowerCase();

        // Browser selection
        switch (browserName) {
            case "chrome":
                driver = WebDriverManager.chromedriver().create();
                break;

            case "firefox":
            case "ff":
                driver = WebDriverManager.firefoxdriver().create();
                break;

            default:
                throw new RuntimeException("Unsupported browser: " + browserName);
        }

        // Register Selenium 4 WebDriver Listener (logging only)
        WebDriverListener listener = new WebEventListener();
        driver = new EventFiringDecorator(listener).decorate(driver);

        // Browser settings
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        // Explicit wait strategy only
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));

        // Launch application
        driver.get(prop.getProperty("url"));
    }
}
