package com.example.salesforce.utils;

/**
 * Test fixtures resolved from Maven-injected system properties so that real
 * or staging credentials are never hardcoded in source. Supply them at run
 * time, e.g.:
 *
 *   -Dsalesforce.username=... -Dsalesforce.password=...
 *   -Dsalesforce.invalid.username=skipped-user@example.com
 *   -Dsalesforce.invalid.password=wrong-password
 *
 * Placeholders are substituted when a property is absent so the suite still
 * compiles and runs against invalid credentials without a real account.
 */
public final class TestData {

    private static final String PLACEHOLDER = "__REPLACE_WITH_REAL_VALUE__";

    private TestData() {
    }

    public static final String LOGIN_URL = resolveOrPlaceholder("salesforce.url",
            "https://login.salesforce.com/?locale=in");

    public static final String VALID_USERNAME = resolveOrPlaceholder("salesforce.username", PLACEHOLDER);
    public static final String VALID_PASSWORD = resolveOrPlaceholder("salesforce.password", PLACEHOLDER);

    public static final boolean HAS_VALID_CREDENTIALS =
            !VALID_USERNAME.equals(PLACEHOLDER) && !VALID_PASSWORD.equals(PLACEHOLDER);

    public static final String INVALID_USERNAME = resolveOrPlaceholder(
            "salesforce.invalid.username", "candidate-curiosity-0001@example.com");
    public static final String INVALID_PASSWORD = resolveOrPlaceholder(
            "salesforce.invalid.password", "DefinitelyWrongPass!42");

    /**
     * The expected Salesforce message shown for invalid credentials. Kept
     * configurable because Salesforce wording can vary by locale/instance.
     */
    public static final String INVALID_CREDENTIALS_MESSAGE = resolveOrPlaceholder(
            "salesforce.invalid.errorMessage",
            "Please check your username and password");

    private static String resolveOrPlaceholder(String key, String fallback) {
        String value = System.getProperty(key);
        return (value == null || value.isBlank()) ? fallback : value.trim();
    }
}