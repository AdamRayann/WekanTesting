package BoardsFunctionality;

import org.example.BoardsPage;
import org.example.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.example.DriverFactory.*;
import static org.junit.jupiter.api.Assertions.*;

public class POMWekanBoardsTests {
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

        assertTrue(boardsPage.boardExist("example"), "Board 'example' should exist");
        assertFalse(boardsPage.boardExist("example1"), "Board 'example1' should not exist");
    }

    @Test
    public void addToFavoriteTest()  {

        assertTrue(boardsPage.addToFavorite("f"));
        assertFalse(boardsPage.addToFavorite("example"));
    }

    @Test
    public void deleteBoardTest() {

        assertTrue(boardsPage.deleteBoard("example"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
