package ListsFunctionality;

import org.example.ListPage;
import org.example.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.example.DriverFactory.getDriver;
import static org.example.DriverFactory.skipNgrokPage;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WekanListsTests {

    private WebDriver driver ;
    private ListPage listPage;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp(){
        driver=getDriver();
        //driver.get("http://localhost:5000/");
        driver.get("https://492c-84-110-182-34.ngrok-free.app/");
        skipNgrokPage(driver);
        loginPage=new LoginPage(driver).get();
    }
    @Test
    public void creatingListTest() throws Exception {
        boolean listExist=loginPage.signIn()
                .addNewBoardAndGetIt("example")
                .createNewList("list").exists("list");

        assertTrue(listExist);


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
    public void deletingListTest() throws Exception {
        boolean listExist=loginPage.signIn()
                .addNewBoardAndGetIt("example")
                .createNewList("list-to-be-deleted").
                delete("list-to-be-deleted").
                exists("list-to-be-deleted");

        assertFalse(listExist);
    }

    @Test
    public void deletingCardsInListTest() throws Exception {
        boolean cardExist=loginPage.signIn()
                .addNewBoardAndGetIt("example")
                .createNewList("list-to-be-deleted").
                addCard("list-to-be-deleted","cards-to-be-deleted").
                delete("list-to-be-deleted","cards-to-be-deleted").
                exists("list-to-be-deleted","cards-to-be-deleted");

        assertFalse(cardExist);
    }

    @Test
    public void moveListTest() throws Exception {
        listPage=loginPage.signIn().addNewBoardAndGetIt("example").createNewList("Done");
        listPage.createNewList("Doing");
        listPage.createNewList("To Do");

        List<String> originalOrder = listPage.getListOrder();
        listPage.movingList("Done", "To Do");

        boolean listMoved=listPage.hasListOrderChanged(originalOrder);

        assertFalse(listMoved);
    }


    @Test
    public void testCardMove() throws Exception {
        String sourceListName = "Doing";
        String targetListName = "To Do";
        String cardToBeMoved = "Task 1";

        listPage=loginPage.signIn().addNewBoardAndGetIt("example");
        listPage.createNewList(sourceListName).addCard(sourceListName,cardToBeMoved);
        listPage.createNewList(targetListName);


        List<String> originalSourceOrder = listPage.getCardOrder(sourceListName);
        List<String> originalTargetOrder = listPage.getCardOrder(targetListName);

        listPage.movingCard(sourceListName, "Task 1", targetListName);

        assertTrue(listPage.hasCardOrderChanged(sourceListName, originalSourceOrder), "The source list order should have changed.");
        assertTrue(listPage.hasCardOrderChanged(targetListName, originalTargetOrder), "The target list order should have changed.");
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
