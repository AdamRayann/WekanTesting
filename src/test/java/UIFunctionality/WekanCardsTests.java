package UIFunctionality;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.openqa.selenium.Dimension;
import org.wekanPro.BoardsPage;
import org.wekanPro.ListPage;
import org.wekanPro.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.wekanPro.DriverFactory.getDriver;
import static org.wekanPro.DriverFactory.skipNgrokPage;

public class WekanCardsTests {

    private LoginPage loginPage;
    private WebDriver driver;
    private String BaseURL;


    @BeforeEach
    public void setUp(){
        BaseURL = System.getProperty("base_url", "http://localhost:5000/");
        driver = getDriver();
        configureWindowSize(driver);
        driver.get(BaseURL);
        if (!BaseURL.equals("http://localhost:5000/"))
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

    @Test
    public void addingCardToListTest() throws Exception {
        boolean cardExist=loginPage.signIn()
                .addNewBoardAndGetIt("example")
                .createNewList("list").
                addCard("list","first_card").
                exists("list","first_card");

        assertTrue(cardExist);
    }

    @Test
    public void deletingCardsInListTest() throws Exception {
        Executable action = () ->loginPage.signIn()
                .addNewBoardAndGetIt("example")
                .createNewList("list-to-be-deleted").
                addCard("list-to-be-deleted","cards-to-be-deleted").
                delete("list-to-be-deleted","cards-to-be-deleted").
                exists("list-to-be-deleted","cards-to-be-deleted");

        assertThrows(Exception.class,action);
    }

    @Test
    public void testCardMove() throws Exception {
        String sourceListName = "Doing";
        String targetListName = "To Do";
        String cardToBeMoved = "Task 1";

        ListPage listPage = loginPage.signIn().addNewBoardAndGetIt("example")
                .createNewList(sourceListName)
                .addCard(sourceListName,cardToBeMoved)
                .createNewList(targetListName);

        List<String> originalSourceOrder = listPage.getCardOrder(sourceListName);
        List<String> originalTargetOrder = listPage.getCardOrder(targetListName);

        listPage.movingCard(sourceListName, cardToBeMoved, targetListName);

        assertTrue(listPage.hasCardOrderChanged(sourceListName, originalSourceOrder), "The source list order should have changed.");
        assertTrue(listPage.hasCardOrderChanged(targetListName, originalTargetOrder), "The target list order should have changed.");
    }
    @Test
    public void boardEmptyName()  {
        Executable action = () -> loginPage.signIn().addNewBoardAndGetIt("new board")
                .createNewList("new list").addCard("new list","")
                .exists("new list","");

        assertThrows(Exception.class, action);
    }



    @AfterEach
    public void tearDown() {
        BoardsPage boardsPage = new BoardsPage(driver);
        boardsPage.clearAll();
        if (driver != null) {
            driver.quit();
        }
    }
}
