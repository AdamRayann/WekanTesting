package org.example;

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

    private final WebDriver driver ;
    private final Accounts.Account account;

    private BoardsPage boardsPage;

    private final By userNameTextField = By.id("at-field-username_and_email");
    private final By passwordTextField = By.id("at-field-password");
    private final By signInBtn = By.id("at-btn");
    public LoginPage(WebDriver driver, Accounts.Account account)
    {
        this.driver=driver;
        this.account = account;
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public LoginPage(WebDriver driver)
    {
        this.driver=driver;
        this.account = Accounts.account1;
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    @Override
    protected void load() {
        String baseURL = "http://localhost:5000/sign-in";
        driver.get(baseURL);
    }

    @Override
    protected void isLoaded() throws Error {
        try {
            if (!driver.getCurrentUrl().contains("sign-in")) {
                throw new RuntimeException("This is not the sign-in page");
            }
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Wrong URL", e);
        }
    }

    public BoardsPage signIn() throws Exception {
        try{
            driver.findElement(userNameTextField).sendKeys(account.getUserName());
            driver.findElement(passwordTextField).sendKeys(account.getPassword());
            driver.findElement(signInBtn).click();


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement headerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#header-main-bar > h1")));

            return new BoardsPage(driver);

        }catch (Exception e)
        {
            throw new Exception(e+"");
        }
    }
}
