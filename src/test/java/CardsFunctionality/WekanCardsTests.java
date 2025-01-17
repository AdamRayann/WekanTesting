package CardsFunctionality;

import org.wekanPro.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import static org.wekanPro.DriverFactory.getDriver;
import static org.wekanPro.DriverFactory.skipNgrokPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WekanCardsTests {

    private LoginPage loginPage;
    private WebDriver driver;

    //    @BeforeEach
//    public void setUp(){
//        driver=new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.get("http://localhost:5000/");
//        loginPage=new LoginPage(driver).get();
//        //boardsPage=new BoardsPage(driver).get();
//    }
    @BeforeEach
    public void setUp(){
        driver=getDriver();
        //driver.get("http://localhost:5000/");
        driver.get("https://17e0-84-110-182-34.ngrok-free.app/sign-in");
        skipNgrokPage(driver);
        loginPage=new LoginPage(driver).get();
    }
//    @Test
//    public void addingCommentTest() throws Exception {
//            assertTrue(loginPage.signIn()
//                    .addNewBoardAndGetIt("example")
//                    .createNewList("list")
//                    .addCard("list", "firstCard")
//                    .getCard("list", "firstCard")
//                    .addComment("this is Wekan's first test"));
//
//    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
