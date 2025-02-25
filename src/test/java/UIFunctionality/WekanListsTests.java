package UIFunctionality;

import org.junit.jupiter.api.function.Executable;
import org.openqa.selenium.Dimension;
import org.wekanPro.BoardsPage;
import org.wekanPro.ListPage;
import org.wekanPro.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static DriverFactory.DriverFactory.getDriver;
import static DriverFactory.DriverFactory.skipNgrokPage;

public class WekanListsTests {

    private WebDriver driver ;
    private ListPage listPage;
    private LoginPage loginPage;
    private String BaseURL;


    @BeforeEach
    public void setUp(){
        BaseURL = System.getProperty("base_url", "http://localhost:3000/");
        driver = getDriver();
        configureWindowSize(driver);
        driver.get(BaseURL);
        if (!BaseURL.equals("http://localhost:3000/"))
            skipNgrokPage(driver);
        loginPage = new LoginPage(driver).get();

    }


    /**
     * Configures the browser window size based on the device type.
     * The device type is passed as a system property `device.type` (e.g., pc, tablet, phone).
     *
     * @param driver WebDriver instance
     */
    private void configureWindowSize(WebDriver driver) {
        String deviceType = System.getProperty("device.type", "pc").toLowerCase();

        switch (deviceType) {
            case "tablet":
                driver.manage().window().setSize(new Dimension(768, 1024)); // Tablet resolution
                break;
            case "phone":
                driver.manage().window().setSize(new Dimension(375, 812)); // Phone resolution
                break;
            case "pc":
            default:
                driver.manage().window().maximize(); // Default to PC (maximize window)
                break;
        }
        System.out.println("Browser window configured for device type: " + deviceType);
    }

//    @Test
//    public void creatingListTest() throws Exception {
//        boolean listExist=loginPage.signIn()
//                .addNewBoardAndGetIt("example")
//                .createNewList("list").exists("list");
//
//        assertTrue(listExist);
//
//
//    }
//
//    @Test
//    public void deletingListTest() throws Exception {
//        boolean listExist=loginPage.signIn()
//                .addNewBoardAndGetIt("example")
//                .createNewList("list-to-be-deleted").
//                delete("list-to-be-deleted").
//                exists("list-to-be-deleted");
//
//        assertFalse(listExist);
//    }
//
//
//
//    @Test
//    public void moveListTest() throws Exception {
//        listPage=loginPage.signIn().addNewBoardAndGetIt("example").createNewList("Done");
//        listPage.createNewList("Doing");
//        listPage.createNewList("To Do");
//
//        List<String> originalOrder = listPage.getListOrder();
//        listPage.movingList("Done", "To Do");
//
//        boolean listMoved=listPage.hasListOrderChanged(originalOrder);
//
//        assertFalse(listMoved);
//    }
//
//    @Test
//    public void boardEmptyName()  {
//        Executable action = () -> loginPage.signIn().addNewBoardAndGetIt("new board")
//                .createNewList("").addCard("","new card");
//
//        assertThrows(Exception.class, action);
//    }
//



    @AfterEach
    public void tearDown() throws InterruptedException {
        BoardsPage boardsPage = new BoardsPage(driver);
        boardsPage.clearAll();
        if (driver != null) {
            driver.quit();
        }
    }
}
