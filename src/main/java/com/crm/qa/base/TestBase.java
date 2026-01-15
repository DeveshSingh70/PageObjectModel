package com.crm.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import com.crm.qa.util.TestUtil;
import com.crm.qa.util.WebEventListener;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

    public static WebDriver driver;
    public static Properties prop;

    // ================= Constructor =================
    public TestBase() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir")
                            + "/src/main/java/com/crm/qa/config/config.properties"
            );
            prop.load(ip);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("config.properties not found", e);
        } catch (IOException e) {
            throw new RuntimeException("Error loading config.properties", e);
        }
    }

    // ================= Initialization =================
    public static void  initialization() {

        String browserName = prop.getProperty("browser").toLowerCase();

        // 1️⃣ Browser launch
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

        // 2️⃣ Register WebDriver Listener (Selenium 4)
        WebDriverListener listener = new WebEventListener();
        driver = new EventFiringDecorator(listener).decorate(driver);

        // 3️⃣ Browser settings
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));

        // ❌ No implicit wait (explicit waits only)

        // 4️⃣ Open application URL
        driver.get(prop.getProperty("url"));
    }
}
