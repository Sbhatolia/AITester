package com.example.salesforce.diag;

import com.example.salesforce.utils.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Throwaway diagnostic: opens the login page, types a username, and checks
 * whether a password field materialises (modern Salesforce passwordless flow).
 * NOT part of the test suite.
 */
public final class DomDump {

    public static void main(String[] args) {
        WebDriver driver = WebDriverFactory.createDriver();
        try {
            driver.get("https://login.salesforce.com/?locale=in");
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(20))
                    .until(d -> d.findElement(By.xpath("//input[@id='username']")).getAttribute("value") != null);

            dumpInputs(driver);

            WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
            username.sendKeys("nobody-qa-0001@example.com");

            // Wait a moment for any async swap to a password/passkey UI.
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(8))
                    .until(d -> d.findElements(By.xpath("//input[contains(@id,'password') or contains(@type,'password')]")).size() > 0
                            ? Boolean.TRUE
                            : (d.findElements(By.xpath("//button[contains(.,'Log In') or contains(.,'Next') or contains(.,'Continue')]")).size() > 0 ? Boolean.TRUE : null));

            System.out.println("=== AFTER TYPING USERNAME ===");
            dumpInputs(driver);

            System.out.println("=== HEADLESS BODY SNIPPET ===");
            String body = driver.findElement(By.tagName("body")).getAttribute("innerText");
            System.out.println(body.substring(0, Math.min(1500, body.length())));
        } finally {
            WebDriverFactory.quitDriver(driver);
        }
    }

    private static void dumpInputs(WebDriver driver) {
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        System.out.println("=== INPUT COUNT: " + inputs.size() + " ===");
        for (WebElement in : inputs) {
            String type = in.getAttribute("type");
            if ("hidden".equals(type)) {
                continue;
            }
            System.out.println("input id=[" + in.getAttribute("id") + "] name=["
                    + in.getAttribute("name") + "] type=[" + type + "] visible=[" + in.isDisplayed() + "]");
        }
        List<WebElement> buttons = driver.findElements(By.xpath("//button"));
        for (WebElement b : buttons) {
            System.out.println("button text=[" + b.getText() + "] visible=[" + b.isDisplayed() + "]");
        }
    }
}