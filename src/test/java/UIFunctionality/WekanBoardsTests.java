package UIFunctionality;

import org.openqa.selenium.Dimension;
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
    private String BaseURL;

    @BeforeEach
    public void setUp() throws Exception {
        BaseURL = System.getProperty("base.url", "http://localhost:5000/");
        driver = getDriver();
        configureWindowSize(driver);
        driver.get(BaseURL);
        if (!BaseURL.equals("http://localhost:5000/"))
            skipNgrokPage(driver);
        loginPage = new LoginPage(driver).get();
        boardsPage = loginPage.signIn();
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
    public void creatingBoardTesting() throws InterruptedException {

            boardsPage.addNewBoard("example");
            boolean boardExist=boardsPage.boardExist("example");

            assertTrue(boardExist, "Board 'example' should exist");

    }

    @Test
    public void addToFavoriteTest() throws InterruptedException {
        boardsPage.addNewBoard("fav");
        boardsPage.addNewBoard("not fav");
        boolean addedToFavorite =boardsPage.addToFavorite("fav");


        assertTrue(addedToFavorite);

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

//    @Test
//    public void testResponsiveDesign() throws InterruptedException {
//        // Define screen resolutions
//        int[][] screenResolutions = {
//                {1920, 1080}, // Desktop
//                {768, 1024},  // Tablet
//                {375, 667}    // Mobile
//        };
//
//        for (int[] resolution : screenResolutions) {
//            // Set browser window size
//            driver.manage().window().setSize(new Dimension(resolution[0], resolution[1]));
//
//            moveBoardTest();
//
//
//        }
//    }






    @AfterEach
    public void tearDown() {
        boardsPage.clearAll();
        if (driver != null) {
            driver.quit();
        }
    }

}
