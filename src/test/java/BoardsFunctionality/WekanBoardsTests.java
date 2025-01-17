package BoardsFunctionality;

import org.wekanPro.BoardsPage;
import org.wekanPro.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.wekanPro.DriverFactory.*;
import static org.junit.jupiter.api.Assertions.*;

public class WekanBoardsTests {
    private LoginPage loginPage;
    private WebDriver driver;
    private BoardsPage boardsPage;
    String BaseURL = "https://492c-84-110-182-34.ngrok-free.app/";

//    @BeforeEach
//    public void setUp(){
//        driver=new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.get("http://localhost:5000/");
//        loginPage=new LoginPage(driver).get();
//        //boardsPage=new BoardsPage(driver).get();
//    }
    @BeforeEach
    public void setUp() throws Exception {
        driver=getDriver();
        //driver.get("http://localhost:5000/");
        driver.get(BaseURL);
        skipNgrokPage(driver);
        loginPage=new LoginPage(driver).get();
        boardsPage = loginPage.signIn();
    }

    @Test
    public void creatingBoardTesting() throws InterruptedException {

            boardsPage.addNewBoard("example");
            boolean boardExist=boardsPage.boardExist("example");

            assertTrue(boardExist, "Board 'example' should exist");

    }

    @Test
    public void addToFavoriteTest()  {
        boolean addedToFavorite =boardsPage.addToFavorite("f");
        boolean notAddedToFavorite =boardsPage.addToFavorite("example");

        assertTrue(addedToFavorite);
        assertFalse(notAddedToFavorite);

    }

    @Test
    public void deleteBoardTest() throws InterruptedException {
        boardsPage.addNewBoard("to_be_deleted");
        boolean boardExist=boardsPage.deleteBoard("to_be_deleted").boardExist("to_be_deleted");

        assertFalse(boardExist);
    }


    @Test
    public void moveBoardTest() throws InterruptedException {


        boardsPage.addNewBoard("board1");
        boardsPage.addNewBoard("board2");
        boardsPage.addNewBoard("board3");
        List<String> originalOrder = boardsPage.getBoardOrder();

        boolean boardMoved=boardsPage.movingBoard("board3","board1").hasBoardOrderChanged(originalOrder);

        assertTrue(boardMoved);
    }
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
