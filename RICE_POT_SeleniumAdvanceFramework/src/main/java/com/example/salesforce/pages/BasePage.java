package com.example.salesforce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class shared by every page object. Encapsulates wait creation and
 * centralises robust exception handling so callers receive clear, consistent
 * failure messages instead of raw Selenium stack noise.
 *
 * Intentionally relies on explicit {@link WebDriverWait} only - no Thread.sleep.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    private final Duration defaultTimeout;

    protected BasePage(WebDriver driver) {
        this(driver, Duration.ofSeconds(15));
    }

    protected BasePage(WebDriver driver, Duration defaultTimeout) {
        this.driver = driver;
        this.defaultTimeout = defaultTimeout;
    }

    protected WebDriverWait newWait() {
        return new WebDriverWait(driver, defaultTimeout);
    }

    protected WebElement waitForVisible(String xpath) {
        try {
            return newWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        } catch (TimeoutException e) {
            throw new AssertionError(
                    "Element not visible within " + defaultTimeout.toSeconds()
                            + "s using XPath: " + xpath, e);
        }
    }

    protected WebElement waitForClickable(String xpath) {
        try {
            return newWait().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        } catch (TimeoutException e) {
            throw new AssertionError(
                    "Element not clickable within " + defaultTimeout.toSeconds()
                            + "s using XPath: " + xpath, e);
        }
    }

    /**
     * Deterministically waits until the given XPath either disappears or is no
     * longer visible. Useful after form submission navigates away from the page.
     */
    protected boolean waitForInvisible(String xpath, Duration timeout) {
        try {
            return new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void safeClick(String xpath) {
        try {
            waitForClickable(xpath).click();
        } catch (org.openqa.selenium.WebDriverException e) {
            throw new AssertionError(
                    "Failed to click element with XPath: " + xpath, e);
        }
    }

    protected void safeType(String xpath, String value) {
        try {
            WebElement field = waitForVisible(xpath);
            field.clear();
            field.sendKeys(value);
        } catch (org.openqa.selenium.WebDriverException e) {
            throw new AssertionError(
                    "Failed to type into element with XPath: " + xpath, e);
        }
    }

    protected boolean isElementPresent(String xpath, Duration timeout) {
        try {
            return !waitForInvisible(xpath, timeout);
        } catch (RuntimeException e) {
            return false;
        }
    }
}