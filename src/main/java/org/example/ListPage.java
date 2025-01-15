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


    //for lists
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

    public ListPage delete(String listName)
    {
        List<WebElement> webElements = driver.findElements(By.className("js-list"));
        if (webElements.isEmpty())
            return null;
        for (WebElement webElement : webElements)
        {
            try {
                if (webElement.findElement(By.cssSelector("p")).getText().equals(listName)) {
                    webElement.findElement(By.cssSelector(".list-header-menu .js-open-list-menu")).click();
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    WebElement archiveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".pop-over .fa-archive")));
                    archiveButton.click();
                    return this;
                }
            } catch (NoSuchElementException e) {
                continue;
            }
        }

        return null;
    }

    public ListPage delete(String listName,String cardName)
    {
        WebElement card = getCard(listName,cardName);
        if (card==null)
            return null;
        card.findElement(By.cssSelector("a.minicard-details-menu.js-open-minicard-details-menu[title='cardDetailsActionsPopup-title']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement archiveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".pop-over-list .js-archive")));
        archiveButton.click();

        archiveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".js-confirm")));
        archiveButton.click();
        return this;
    }


    //for cards
    public Boolean exists(String listName,String cardName)
    {
        List<WebElement> webElements = driver.findElements(By.className("js-list"));
        if (webElements.isEmpty())
            return false;
        for (WebElement webElement : webElements)
        {
            try {
                if (webElement.findElement(By.cssSelector("p")).getText().equals(listName)) {
                    return cardExists(webElement,cardName);
                }
            } catch (NoSuchElementException e) {
                continue;
            }
        }
        return false;
    }


    private Boolean cardExists(WebElement webElement,String cardName)
    {
        List<WebElement> webElements = webElement.findElements(By.className("minicards"));
        if (webElements.isEmpty())
            return false;
        for (WebElement w : webElements)
        {
            try {
                if (w.findElement(By.cssSelector("p")).getText().equals(cardName)) {
                    return true;
                }
            } catch (NoSuchElementException e) {
                continue;
            }
        }
        return false;
    }

    private String getList(String listName)
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


    public WebElement getCard(String listName, String cardName) {

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

                    return cardElement;
                }
            } catch (NoSuchElementException e) {
                continue;
            }
        }

        return null;
    }

}
