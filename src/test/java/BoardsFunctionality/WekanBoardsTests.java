package BoardsFunctionality;

import org.example.BoardsPage;
import org.example.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.example.DriverFactory.*;
import static org.junit.jupiter.api.Assertions.*;

public class WekanBoardsTests {
    private LoginPage loginPage;
    private WebDriver driver;
    private BoardsPage boardsPage;

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
        driver.get("http://localhost:5000/");
        loginPage=new LoginPage(driver).get();
        boardsPage = loginPage.signIn();
    }

    @Test
    public void creatingBoardTesting()  {

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
    public void deleteBoardTest() {
        boardsPage.addNewBoard("to_be_deleted");
        boolean boardExist=boardsPage.deleteBoard("to_be_deleted").boardExist("to_be_deleted");

        assertFalse(boardExist);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
