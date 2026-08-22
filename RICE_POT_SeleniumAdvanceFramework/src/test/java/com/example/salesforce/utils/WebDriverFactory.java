package com.example.salesforce.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * Builds and tears down the WebDriver instance. Centralises driver setup so
 * tests never touch driver lifecycle directly.
 */
public final class WebDriverFactory {

    private static final Duration IMPLICIT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(60);

    private WebDriverFactory() {
    }

    public static WebDriver createDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_TIMEOUT);
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT);
        return driver;
    }

    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            try {
                driver.quit();
            } catch (RuntimeException e) {
                // Driver may already be gone; teardown must never mask test results.
            }
        }
    }
}