package org.wekanPro;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CardPage  {
    private final By commentTextField = By.className("js-new-comment-input");
    private final By addCommentBtn = By.className("confirm");
    private final WebDriver driver;

    public CardPage(WebDriver driver) {
        this.driver = driver;

    }

    public boolean addComment(String comment) {

            WebElement commentField = driver.findElement(commentTextField);

            commentField.clear();
            commentField.sendKeys(comment);

            driver.findElement(addCommentBtn).click();

            return true;

    }

//    private boolean commentExist(String comment) {
//
//        List<WebElement> comments = driver.findElements(By.cssSelector(".comment"));
//
//        for (WebElement commentElement : comments) {
//            try {
//                WebElement commentTextElement = commentElement.findElement(By.cssSelector(".comment-text .viewer p"));
//                String fullCommentText = commentTextElement.getText();
//
//                System.out.println(fullCommentText);
//                System.out.println(comment);
//                if (fullCommentText.strip().contains(comment.strip())) {
//                    return true;
//                }
//            } catch (NoSuchElementException e) {
//                continue;
//            }
//        }
//        System.out.println("Comment not found: " + comment);
//        return false;
//    }
}
