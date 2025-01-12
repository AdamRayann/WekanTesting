package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.time.Duration;

public class BoardsPage extends LoadableComponent<BoardsPage> {

    private final WebDriver driver ;

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
        String baseURL = "http://localhost:5000/";
        driver.get(baseURL);
    }

    @Override
    protected void isLoaded() throws Error {
        try {
            if (!driver.findElement(header).getText().contains("boards")) {
                throw new RuntimeException("This is not the main page");
            }
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Header element not found", e);
        }
    }

    public String getHeader() {
        return driver.findElement(this.header).getText();
    }

    public ListPage addNewBoard(String boardName) {
        driver.findElement(addNewBoardBtn).click();
        driver.findElement(newBoardNameTextField).sendKeys(boardName);
        driver.findElement(newBoardCreateBtn).click();

        return new ListPage(driver);
    }
}
