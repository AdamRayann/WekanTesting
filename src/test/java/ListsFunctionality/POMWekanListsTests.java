package ListsFunctionality;

import org.example.ListPage;
import org.example.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.example.DriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class POMWekanListsTests {

    private WebDriver driver ;
    private ListPage listPage;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp(){
        driver=getDriver();
        driver.get("http://localhost:5000/");
        loginPage=new LoginPage(driver).get();
    }
    @Test
    public void creatingListTest() throws Exception {
            assertTrue(loginPage.signIn()
                    .addNewBoardAndGetIt("example")
                    .createNewList("list").exists("list"));


    }

    @Test
    public void addingCardToListTest() throws Exception {
            assertTrue(loginPage.signIn()
                    .addNewBoardAndGetIt("example")
                    .createNewList("list").
                    addCard("list","first_card").
                    exists("list","first_card"));


    }

//    @Test
//    public void editListNameTest() throws Exception {
//        synchronized (this) {
//            assertTrue(loginPage.signIn()
//                    .addNewBoardAndGetIt("example")
//                    .createNewList("list").;
//
//        }
//    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
