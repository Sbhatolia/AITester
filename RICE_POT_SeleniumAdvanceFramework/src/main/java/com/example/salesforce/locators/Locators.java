package com.example.salesforce.locators;

/**
 * Central registry of XPath locators for the Salesforce login page.
 * Single source of truth - edit here when the Salesforce DOM changes.
 *
 * Locators verified against the live production markup at
 * https://login.salesforce.com/?locale=in (2026):
 *   - username input has id="username"
 *   - password input (dynamically injected by LoginHint JS) has id="password"
 *   - login submit button has id="Login"
 *   - remember-me checkbox has id="rememberUn"
 *   - invalid-credentials error container has id="error"
 *
 * Only XPath expressions are used (no CSS / id / name / class strategies).
 */
public final class Locators {

    private Locators() {
    }

    public static final String USERNAME_INPUT =
            "//input[@id='username']";

    public static final String PASSWORD_INPUT =
            "//input[@id='password']";

    public static final String LOGIN_BUTTON =
            "//input[@id='Login']";

    public static final String REMEMBER_ME_CHECKBOX =
            "//input[@id='rememberUn']";

    public static final String LOGIN_ERROR =
            "//div[@id='error']";
}