package org.wekanPro;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoardsPage extends LoadableComponent<BoardsPage> {

    private static WebDriver driver = null;
    private final String BaseURL = "http://localhost:3000";
    //private final String BaseURL = "https://492c-84-110-182-34.ngrok-free.app/";
    private final By header = By.cssSelector("#header-main-bar > h1");
    private final By addNewBoardBtn = By.cssSelector("#content > .wrapper > .board-list > .js-add-board > .board-list-item");
    private final By newBoardNameTextField = By.className("js-new-board-title");
    private final By newBoardCreateBtn = By.xpath("/html/body/div[4]/div[2]/div/div[1]/form/input");

    public BoardsPage(WebDriver driver)
    {
        this.driver=driver;
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    @Override
    protected void load() {
        //String baseURL = "http://localhost:5000/";


        driver.get(BaseURL);
    }

    @Override
    protected void isLoaded() throws Error {
//        try {
//            if (!driver.findElement(header).getText().contains("boards")) {
//                throw new RuntimeException("This is not the main page");
//            }
//        } catch (NoSuchElementException e) {
//            throw new RuntimeException("Header element not found", e);
//        }
    }

    public String getHeader() {
        return driver.findElement(this.header).getText();
    }


    public ListPage addNewBoardAndGetIt(String boardName) throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(addNewBoardBtn).click();
        driver.findElement(newBoardNameTextField).sendKeys(boardName);
        driver.findElement(newBoardCreateBtn).click();
        Thread.sleep(1000);
        return new ListPage(driver);
    }

    public BoardsPage addNewBoard(String boardName) throws InterruptedException {
        String url = driver.getCurrentUrl();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addNewBoardButton = wait.until(ExpectedConditions.elementToBeClickable(addNewBoardBtn));

        addNewBoardButton.click();
        driver.findElement(newBoardNameTextField).sendKeys(boardName);
        driver.findElement(newBoardCreateBtn).click();
        driver.get(url);
        Thread.sleep(1000);
        return this;

    }
    public static String getBoardId(String boardName) throws InterruptedException {
        Thread.sleep(1000);

        List<WebElement> boards = driver.findElements(By.cssSelector("ul.board-list li.js-board"));
        for (WebElement board : boards) {
            WebElement nameElement = board.findElement(By.cssSelector("span.board-list-item-name div.viewer p"));
            if (nameElement.getText().equalsIgnoreCase(boardName)) {

                WebElement linkElement = board.findElement(By.cssSelector("a.js-open-board"));
                return linkElement.getDomAttribute("href");
            }
        }
        return null;

    }


    public boolean boardExist(String boardName) throws InterruptedException {
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.board-list")));
        Thread.sleep(1000);
        List<WebElement> boards = driver.findElements(By.cssSelector("ul.board-list span.board-list-item-name"));

        for (WebElement board : boards) {
            if (board.getText().equalsIgnoreCase(boardName)) {
                return true;
            }
        }
        return false;

    }

    public boolean addToFavorite(String boardName) throws InterruptedException {
        Thread.sleep(1000);
        String boardId = getBoardId(boardName);
        WebElement boardElement = driver.findElement(By.cssSelector("a.js-open-board[href='" + boardId + "']"));
        WebElement starIcon = boardElement.findElement(By.cssSelector("i.fa.js-star-board"));

        starIcon.click();


        WebElement header = driver.findElement(By.cssSelector("#header-quick-access .header-quick-access-list a[href='" + boardId + "']"));

        return header != null;


    }

    public BoardsPage deleteBoard(String boardName) throws InterruptedException {
        Thread.sleep(1000);
        String boardId = getBoardId(boardName);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement boardElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("a.js-open-board[href='" + boardId + "']")));

            WebElement archiveIcon = boardElement.findElement(By.cssSelector("i.fa-archive"));

            archiveIcon.click();

            return this;

    }


    public BoardsPage movingBoard(String boardName, String targetBoardName) throws InterruptedException {
        Thread.sleep(1000);
        String boardId = getBoardId(boardName);
        String targetBoardId = getBoardId(targetBoardName);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement sourceBoard = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a.js-open-board[href='" + boardId + "']")));

        WebElement targetBoard = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a.js-open-board[href='" + targetBoardId + "']")));

        Actions actions = new Actions(driver);
        actions.clickAndHold(sourceBoard)
                .moveToElement(targetBoard,50,3)
                .release()
                .perform();


        return this;

    }
    public List<String> getBoardOrder() throws InterruptedException {
        Thread.sleep(1000);
        List<WebElement> boards = driver.findElements(By.cssSelector("ul.board-list li.js-board"));
        List<String> boardNames = new ArrayList<>();

        for (WebElement board : boards) {
            String boardName = board.findElement(By.cssSelector("span.board-list-item-name div.viewer p")).getText();
            boardNames.add(boardName);
        }

        return boardNames;
    }

    public boolean hasBoardOrderChanged(List<String> originalOrder) throws InterruptedException {
        Thread.sleep(1000);
        List<String> currentOrder = getBoardOrder();

        return !currentOrder.equals(originalOrder);
    }

    public void clearAll() throws InterruptedException {

        List<WebElement> boards = driver.findElements(By.cssSelector("ul.board-list li.js-board"));

        for (WebElement board : boards) {
            String boardName = board.findElement(By.cssSelector("span.board-list-item-name div.viewer p")).getText();
            deleteBoard(boardName);
        }


    }





}
