import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.time.Duration;

public class BoardsPage extends LoadableComponent<BoardsPage> {

    private final String BaseURL = "http://localhost:5000/";
    private WebDriver driver ;

    private final By header = By.id("header-main-bar > h1");

    public BoardsPage(WebDriver driver)
    {
        this.driver=driver;
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    @Override
    protected void load() {
        driver.get(BaseURL);
    }

    @Override
    protected void isLoaded() throws Error {
        if (!driver.findElement(header).getText().contains("boards"));{
            throw new RuntimeException("this is not the main page");
        }
    }
    
}
