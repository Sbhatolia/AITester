package com.example.salesforce.pages;

import com.example.salesforce.locators.Locators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object Model for the Salesforce login page.
 *
 * Dust-free instantiation via {@link PageFactory#initElements}. All locators
 * are XPath (see {@link Locators}). Reusable action methods hide Selenium
 * interaction details from the tests.
 */
public class LoginPage extends BasePage {

    @FindBy(xpath = Locators.USERNAME_INPUT)
    private WebElement usernameInput;

    @FindBy(xpath = Locators.PASSWORD_INPUT)
    private WebElement passwordInput;

    @FindBy(xpath = Locators.LOGIN_BUTTON)
    private WebElement loginButton;

    @FindBy(xpath = Locators.REMEMBER_ME_CHECKBOX)
    private WebElement rememberMeCheckbox;

    @FindBy(xpath = Locators.LOGIN_ERROR)
    private WebElement loginError;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void enterUsername(String username) {
        newWait().until(ExpectedConditions.visibilityOf(usernameInput)).clear();
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        newWait().until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(password);
    }

    public void clickLogin() {
        newWait().until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void submitCredentials(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public void setRememberMe(boolean select) {
        newWait().until(ExpectedConditions.visibilityOf(rememberMeCheckbox));
        boolean selected = rememberMeCheckbox.isSelected();
        if (select && !selected) {
            rememberMeCheckbox.click();
        } else if (!select && selected) {
            rememberMeCheckbox.click();
        }
    }

    public boolean isRememberMeSelected() {
        newWait().until(ExpectedConditions.visibilityOf(rememberMeCheckbox));
        return rememberMeCheckbox.isSelected();
    }

    /**
     * Reads the invalid-credentials error message text. Returns an empty
     * string when the error element is absent (no failed validation).
     */
    public String getLoginErrorText() {
        try {
            newWait().until(ExpectedConditions.visibilityOf(loginError));
            return loginError.getText().trim();
        } catch (org.openqa.selenium.TimeoutException e) {
            return "";
        }
    }

    public boolean isLoginErrorVisible() {
        return !getLoginErrorText().isEmpty();
    }

    /**
     * @return true when the login form fields are present, signalling the page
     *         has (re)loaded back to a login state.
     */
    public boolean isLoginFormDisplayed() {
        return isElementPresent(Locators.USERNAME_INPUT, java.time.Duration.ofSeconds(8));
    }
}