package com.example.salesforce.base;

import com.example.salesforce.pages.LoginPage;
import com.example.salesforce.utils.TestData;
import com.example.salesforce.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

/**
 * Shared TestNG lifecycle for every login test: opens the target URL before
 * the test block and guarantees driver teardown afterwards.
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected LoginPage loginPage;
    private final String baseUrl;

    protected BaseTest() {
        this(TestData.LOGIN_URL);
    }

    protected BaseTest(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @BeforeTest(alwaysRun = true)
    public void setUp() {
        driver = WebDriverFactory.createDriver();
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        try {
            WebDriverFactory.quitDriver(driver);
        } finally {
            driver = null;
        }
    }
}