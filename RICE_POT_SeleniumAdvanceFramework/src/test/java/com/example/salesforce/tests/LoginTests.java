package com.example.salesforce.tests;

import com.example.salesforce.base.BaseTest;
import com.example.salesforce.utils.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Validates the Salesforce login UI with valid and invalid credential cases.
 * Every assertion is guarded with explicit try/catch so a failure surfaces a
 * precise message rather than an abrupt Selenium exception.
 */
public class LoginTests extends BaseTest {

    @Test(description = "Valid credentials should log the user in (leave the login form)")
    public void validLoginSucceeds() {
        if (!TestData.HAS_VALID_CREDENTIALS) {
            throw new org.testng.SkipException(
                    "No real credentials supplied via -Dsalesforce.username/-Dsalesforce.password; skipping valid-login test.");
        }
        try {
            loginPage.submitCredentials(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);
            Assert.assertFalse(loginPage.isLoginFormDisplayed(),
                    "After a valid login the page should no longer show the login form.");
        } catch (AssertionError e) {
            throw e;
        } catch (RuntimeException e) {
            Assert.fail("Valid login flow threw an unexpected runtime error: " + e.getMessage(), e);
        }
    }

    @Test(description = "Invalid username should surface the Salesforce error message and stay on login")
    public void invalidUsernameShowsError() {
        try {
            loginPage.submitCredentials(TestData.INVALID_USERNAME, TestData.INVALID_PASSWORD);
            Assert.assertTrue(loginPage.isLoginErrorVisible(),
                    "Expected an inline login error for an invalid username.");
            Assert.assertTrue(loginPage.getLoginErrorText().contains(TestData.INVALID_CREDENTIALS_MESSAGE),
                    "Unexpected error text. Actual: [" + loginPage.getLoginErrorText() + "]");
            Assert.assertTrue(loginPage.isLoginFormDisplayed(),
                    "Should remain on the login page after failed authentication.");
        } catch (AssertionError e) {
            throw e;
        } catch (RuntimeException e) {
            Assert.fail("Login error flow threw an unexpected runtime error: " + e.getMessage(), e);
        }
    }

    @Test(description = "Invalid password with a valid username should surface the Salesforce error message")
    public void invalidPasswordShowsError() {
        try {
            loginPage.submitCredentials(TestData.INVALID_USERNAME, TestData.INVALID_PASSWORD);
            Assert.assertTrue(loginPage.isLoginErrorVisible(),
                    "Expected an inline login error for an invalid password.");
            Assert.assertTrue(loginPage.getLoginErrorText().contains(TestData.INVALID_CREDENTIALS_MESSAGE),
                    "Unexpected error text. Actual: [" + loginPage.getLoginErrorText() + "]");
        } catch (AssertionError e) {
            throw e;
        } catch (RuntimeException e) {
            Assert.fail("Password error flow threw an unexpected runtime error: " + e.getMessage(), e);
        }
    }

    @Test(description = "Remember-me checkbox should toggle selected state")
    public void rememberMeToggles() {
        try {
            loginPage.setRememberMe(true);
            Assert.assertTrue(loginPage.isRememberMeSelected(),
                    "Remember-me checkbox should be selected after enabling it.");
            loginPage.setRememberMe(false);
            Assert.assertFalse(loginPage.isRememberMeSelected(),
                    "Remember-me checkbox should be deselected after disabling it.");
        } catch (AssertionError e) {
            throw e;
        } catch (RuntimeException e) {
            Assert.fail("Remember-me toggle threw an unexpected runtime error: " + e.getMessage(), e);
        }
    }
}