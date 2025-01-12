package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ListPage  {

    private final By addNewListBtn = By.cssSelector("div.list-header-add > a.open-list-composer");
    private WebDriver driver;
    public ListPage(WebDriver driver) {
        this.driver=driver;
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

//    public boolean createNewList(String listName)
//    {
//        driver.findElement(addNewListBtn).click();
//        driver.findElement(By.className("list-name-input")).sendKeys(listName);
//        driver.findElement(By.className("confirm")).click();
//
//        return exists(listName);
//    }
    public ListPage createNewList(String listName)
    {
        driver.findElement(addNewListBtn).click();
        driver.findElement(By.className("list-name-input")).sendKeys(listName);
        driver.findElement(By.className("confirm")).click();

        if (exists(listName)){

            return this;
        }
        throw new RuntimeException("createNewList failed , the listName doesn't exist");

    }

    public Boolean exists(String listName)
    {
        List<WebElement> webElements = driver.findElements(By.className("js-list"));
        if (webElements.isEmpty())
            return false;
        for (WebElement webElement : webElements)
        {
            try {
                if (webElement.findElement(By.cssSelector("p")).getText().equals(listName)) {
                    return true;
                }
            } catch (NoSuchElementException e) {
                continue;
            }
        }
    return false;
    }

    public String getList(String listName)
    {
        List<WebElement> webElements = driver.findElements(By.className("js-list"));
        if (webElements.isEmpty())
            return null;
        for (WebElement webElement : webElements)
        {
            try {
                if (webElement.findElement(By.cssSelector("p")).getText().equals(listName)) {
                    return webElement.getDomAttribute("id");
                }
            } catch (NoSuchElementException e) {
                continue;
            }
        }
        return null;
    }
    public ListPage addCard(String listName, String cardName) {
        String listById = getList(listName);

        if (listById == null) {
            System.out.println("List not found: " + listName);
            return null;
        }
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#" + listById + " .js-add-card"))).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#" + listById + " .js-card-title"))).sendKeys(cardName);

            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#" + listById + " .confirm"))).click();

            return this;
        } catch (TimeoutException e) {
            System.out.println("Failed to add card: " + e.getMessage());
            return null;
        }
    }


    public CardPage getCard(String listName, String cardName) {

        String listById = getList(listName);

        if (listById == null) {
            System.out.println("List not found: " + listName);
            return null;
        }

        List<WebElement> cardElements = driver.findElements(By.cssSelector("#" + listById + " .minicard-wrapper"));

        for (WebElement cardElement : cardElements) {
            try {
                WebElement cardNameElement = cardElement.findElement(By.cssSelector("p"));
                if (cardNameElement.getText().equals(cardName)) {
                    cardNameElement.click();
                    driver.findElement(By.className("fa-window-maximize")).click();
                    return new CardPage(driver);
                }
            } catch (NoSuchElementException e) {
                continue;
            }
        }

        return new CardPage(driver);
    }

}
