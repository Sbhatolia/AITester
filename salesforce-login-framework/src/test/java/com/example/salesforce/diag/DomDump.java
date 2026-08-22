package com.example.salesforce.diag;

import com.example.salesforce.utils.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Throwaway diagnostic: verifies what happens after clicking Login with a
 * username but no password on the modern passwordless Salesforce login.
 * NOT part of the test suite.
 */
public final class DomDump {

    public static void main(String[] args) {
        WebDriver driver = WebDriverFactory.createDriver();
        try {
            driver.get("https://login.salesforce.com/?locale=in");
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(20))
                    .until(d -> d.findElement(By.xpath("//input[@id='username']")).getAttribute("value") != null);

            WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
            username.sendKeys("nobody-qa-0001@example.com");

            WebElement login = driver.findElement(By.xpath("//input[@id='Login']"));
            login.click();

            // Observe what happens: error, navigation, password field, or passkey prompt.
            try {
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(12))
                        .until(d -> {
                            List<WebElement> pw = d.findElements(By.xpath("//input[contains(@id,'password') or contains(@type,'password')]"));
                            List<WebElement> err = d.findElements(By.xpath("//div[@id='error']"));
                            if (!pw.isEmpty()) {
                                System.out.println(">>> PASSWORD FIELD APPEARED after Login click");
                                return Boolean.TRUE;
                            }
                            if (!err.isEmpty() && err.get(0).isDisplayed()) {
                                System.out.println(">>> ERROR APPEARED: [" + err.get(0).getText() + "]");
                                return Boolean.TRUE;
                            }
                            return null;
                        });
            } catch (org.openqa.selenium.TimeoutException te) {
                System.out.println(">>> No password field and no error within 12s after Login click");
            }

            System.out.println("=== URL NOW: " + driver.getCurrentUrl());

            System.out.println("=== VISIBLE INPUTS ===");
            for (WebElement in : driver.findElements(By.tagName("input"))) {
                if (!"hidden".equals(in.getAttribute("type")) && in.isDisplayed()) {
                    System.out.println("input id=[" + in.getAttribute("id") + "] name=["
                            + in.getAttribute("name") + "] type=[" + in.getAttribute("type") + "]");
                }
            }

            System.out.println("=== VISIBLE BODY TEXT (1200 chars) ===");
            String body = driver.findElement(By.tagName("body")).getAttribute("innerText");
            System.out.println(body.substring(0, Math.min(1200, body.length())));
        } catch (Exception e) {
            System.out.println(">>> EXCEPTION: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        } finally {
            WebDriverFactory.quitDriver(driver);
        }
    }
}