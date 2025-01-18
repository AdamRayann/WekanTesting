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
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    if (driver.findElements(addNewListBtn).size() > 0 && driver.findElement(addNewListBtn).isDisplayed()) {
        wait.until(ExpectedConditions.elementToBeClickable(addNewListBtn)).click();
    } else {
        System.out.println("Add New List button is already pressed");
    }

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("list-name-input"))).sendKeys(listName);
    wait.until(ExpectedConditions.elementToBeClickable(By.className("confirm"))).click();
    wait.until(ExpectedConditions.elementToBeClickable(By.className("js-close-inlined-form"))).click();

    if (exists(listName)) {
        return this;
    }

    throw new RuntimeException("createNewList failed, the listName doesn't exist");
}

    public Boolean exists(String listName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("js-list")));
        List<WebElement> webElements = driver.findElements(By.className("js-list"));

        if (webElements.isEmpty()) return false;

        for (WebElement webElement : webElements) {
            if (webElement.findElement(By.cssSelector("p")).getText().equals(listName)) {
                return true;
            }
        }

        return false;
    }

    public ListPage delete(String listName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> webElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("js-list")));

        for (WebElement webElement : webElements) {
            if (webElement.findElement(By.cssSelector("p")).getText().equals(listName)) {
                webElement.findElement(By.cssSelector(".list-header-menu .js-open-list-menu")).click();

                WebElement archiveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".pop-over .fa-archive")));
                archiveButton.click();
                return this;
            }
        }

        return null;
    }

    public ListPage delete(String listName, String cardName) {
        WebElement card = getCard(listName, cardName);

        if (card == null) return null;

        card.findElement(By.cssSelector("a.minicard-details-menu.js-open-minicard-details-menu[title='cardDetailsActionsPopup-title']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement archiveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".pop-over-list .js-archive")));
        archiveButton.click();

        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".js-confirm")));
        confirmButton.click();

        return this;
    }

    public Boolean exists(String listName, String cardName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("js-list")));

        List<WebElement> webElements = driver.findElements(By.className("js-list"));

        if (webElements.isEmpty()) return false;

        for (WebElement webElement : webElements) {
            if (webElement.findElement(By.cssSelector("p")).getText().equals(listName)) {
                return cardExists(webElement, cardName);
            }
        }
        return false;
    }

    private Boolean cardExists(WebElement webElement, String cardName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("minicards")));
        List<WebElement> webElements = webElement.findElements(By.className("minicards"));

        if (webElements.isEmpty()) return false;

        for (WebElement w : webElements) {
            if (w.findElement(By.cssSelector("p")).getText().equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    private String getList(String listName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("js-list")));

        List<WebElement> webElements = driver.findElements(By.className("js-list"));

        if (webElements.isEmpty()) return null;

        for (WebElement webElement : webElements) {
            if (webElement.findElement(By.cssSelector("p")).getText().equals(listName)) {
                return webElement.getDomAttribute("id");
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

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#" + listById + " .js-add-card"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#" + listById + " .js-card-title"))).sendKeys(cardName);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#" + listById + " .confirm"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.className("js-close-inlined-form"))).click();

        return this;
    }

    public WebElement getCard(String listName, String cardName) {
        String listById = getList(listName);

        if (listById == null) return null;

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.list.js-list")));

        List<String> currentOrder = getListOrder();

        return !currentOrder.equals(originalOrder);
    }

    public List<String> getListOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> lists = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.list.js-list")));
        List<String> listNames = new ArrayList<>();

        for (WebElement list : lists) {
            String listName = list.findElement(By.cssSelector(".list-header-name div.viewer p")).getText();
            listNames.add(listName);
        }

        return listNames;
    }

    public ListPage movingList(String sourceListName, String targetListName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        WebElement sourceList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(getList(sourceListName))));
        WebElement targetList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(getList(targetListName))));

        actions.clickAndHold(sourceList)
                .pause(Duration.ofMillis(500)) // Pause for stability
                .moveToElement(targetList)
                .pause(Duration.ofMillis(500))
                .release()
                .perform();

        return this;
    }




}
