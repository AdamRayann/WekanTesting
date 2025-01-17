package org.wekanPro;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
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
public ListPage createNewList(String listName) {
    if (driver.findElements(addNewListBtn).size() > 0 && driver.findElement(addNewListBtn).isDisplayed()) {
        driver.findElement(addNewListBtn).click();
    }
    else {
        System.out.println("Add New List button is already pressed");
    }

    driver.findElement(By.className("list-name-input")).sendKeys(listName);
    driver.findElement(By.className("confirm")).click();
    driver.findElement(By.className("js-close-inlined-form")).click();

    if (exists(listName)) {
        return this;
    }

    throw new RuntimeException("createNewList failed, the listName doesn't exist");
}



    public Boolean exists(String listName)
    {
        List<WebElement> webElements = driver.findElements(By.className("js-list"));
        if (webElements.isEmpty())
            return false;
        for (WebElement webElement : webElements)
        {
                if (webElement.findElement(By.cssSelector("p")).getText().equals(listName)) {
                    return true;
                }

        }
    return false;
    }

    public ListPage delete(String listName)
    {
        List<WebElement> webElements = driver.findElements(By.className("js-list"));
        for (WebElement webElement : webElements)
        {
                if (webElement.findElement(By.cssSelector("p")).getText().equals(listName)) {
                    webElement.findElement(By.cssSelector(".list-header-menu .js-open-list-menu")).click();
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    WebElement archiveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".pop-over .fa-archive")));
                    archiveButton.click();
                    return this;
                }

        }

        return null;
    }

    public ListPage delete(String listName,String cardName) throws InterruptedException {
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
                if (webElement.findElement(By.cssSelector("p")).getText().equals(listName)) {
                    return cardExists(webElement,cardName);
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
                if (w.findElement(By.cssSelector("p")).getText().equals(cardName)) {
                    return true;
                }

        }
        return false;
    }

    private String getList(String listName) throws InterruptedException {
        List<WebElement> webElements = driver.findElements(By.className("js-list"));
        if (webElements.isEmpty())
            return null;
        for (WebElement webElement : webElements)
        {
                if (webElement.findElement(By.cssSelector("p")).getText().equals(listName)) {
                    return webElement.getDomAttribute("id");
                }

        }
        return null;
    }



    public ListPage addCard(String listName, String cardName) throws InterruptedException {
        String listById = getList(listName);

        if (listById == null) {
            System.out.println("List not found: " + listName);
            return null;
        }
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#" + listById + " .js-add-card"))).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#" + listById + " .js-card-title"))).sendKeys(cardName);

            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#" + listById + " .confirm"))).click();

            driver.findElement(By.className("js-close-inlined-form")).click();

            return this;

    }


    public WebElement getCard(String listName, String cardName) throws InterruptedException {

        String listById = getList(listName);

        List<WebElement> cardElements = driver.findElements(By.cssSelector("#" + listById + " .minicard-wrapper"));

        for (WebElement cardElement : cardElements) {

                WebElement cardNameElement = cardElement.findElement(By.cssSelector("p"));
                if (cardNameElement.getText().equals(cardName)) {

                    return cardElement;
                }

        }

        return null;
    }

    public boolean hasListOrderChanged(List<String> originalOrder) {
        List<String> currentOrder = getListOrder();

        return !currentOrder.equals(originalOrder);
    }

    public List<String> getListOrder() {
        List<WebElement> lists = driver.findElements(By.cssSelector("div.list.js-list"));
        List<String> listNames = new ArrayList<>();

        for (WebElement list : lists) {
            String listName = list.findElement(By.cssSelector(".list-header-name div.viewer p")).getText();
            listNames.add(listName);
        }

        return listNames;
    }


    public ListPage movingList(String sourceListName, String targetListName) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);
        Thread.sleep(1000);
        WebElement sourceList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(getList(sourceListName))));
        WebElement targetList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(getList(targetListName))));

        wait.until(ExpectedConditions.elementToBeClickable(sourceList));
        wait.until(ExpectedConditions.elementToBeClickable(targetList));

        actions.clickAndHold(sourceList)
                .pause(Duration.ofMillis(500)) // Pause for stability
                .moveToElement(targetList)
                .pause(Duration.ofMillis(500))
                .release()
                .perform();

        return this;
    }



    public List<String> getCardOrder(String listName) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement list = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id(getList(listName))));

        List<WebElement> cards = list.findElements(By.cssSelector("div.minicard"));
        List<String> cardNames = new ArrayList<>();

        for (WebElement card : cards) {
            String cardName = card.findElement(By.cssSelector("p")).getText();
            cardNames.add(cardName);
        }

        return cardNames;
    }

    public boolean hasCardOrderChanged(String listName, List<String> originalOrder) throws InterruptedException {
        List<String> currentOrder = getCardOrder(listName);
        return !currentOrder.equals(originalOrder);
    }

    public ListPage movingCard(String sourceListName, String cardName, String targetListName) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement sourceCard = getCard(sourceListName, cardName);

        WebElement targetListBody = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(getList(targetListName))));

        Actions actions = new Actions(driver);
        actions.clickAndHold(sourceCard)
                .moveToElement(targetListBody)
                .release()
                .perform();

        return this;
    }




}
