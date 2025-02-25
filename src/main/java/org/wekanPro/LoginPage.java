package org.wekanPro;

import integrationData.Accounts;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends LoadableComponent<LoginPage> {

    private final WebDriver driver;
    private final Accounts.Account account;
    private final By userNameTextField = By.id("at-field-username_and_email");
    private final By passwordTextField = By.id("at-field-password");
    private final By signInBtn = By.id("at-btn");
    private final String baseURL;

    public LoginPage(WebDriver driver, Accounts.Account account) {
        this.driver = driver;
        this.account = account;
        this.baseURL = System.getProperty("base.url", "http://localhost:3000/") + "sign-in";
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.account = Accounts.account1;
        this.baseURL = System.getProperty("base.url", "http://localhost:3000/") + "sign-in";
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Override
    protected void load() {
        driver.get(baseURL);
    }

    @Override
    protected void isLoaded() throws Error {
        try {
            if (!driver.getCurrentUrl().contains("sign-in")) {
                //throw new IllegalStateException("The current page is not the sign-in page");

            }
        } catch (NoSuchElementException e) {
            throw new IllegalStateException("Error verifying the sign-in page URL", e);
        }
    }

    public BoardsPage signIn() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(userNameTextField));
        usernameField.click();
        usernameField.sendKeys(account.getUserName());
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(passwordTextField));
        passwordField.sendKeys(account.getPassword());

        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(signInBtn));
        signInButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#header-main-bar > h1")));

        return new BoardsPage(driver);
    }
}
