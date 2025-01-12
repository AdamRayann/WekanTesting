package BoardsFunctionality;

import integrationData.Accounts;
import org.example.BoardsPage;
import org.example.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class POMWekanBoardsTesting {
    private LoginPage loginPage;
    private WebDriver driver;

    @BeforeEach
    public void setUp(){
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:5000/");
        loginPage=new LoginPage(driver).get();
        //boardsPage=new BoardsPage(driver).get();
    }

    @Test
    public void test1() throws Exception {

        assertTrue(loginPage.signIn().
                addNewBoard("example").
                createNewList("list").
                addCard("list","firstCard").
                getCard("list","firstCard").
                addComment("this is Wekan's first test"));

    }

//    @AfterEach
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
