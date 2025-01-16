package ListsFunctionality;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListsAPITesting {

    private String token = "1n7YIu0bSGi7ah4Ko8uAWUHbm7fgRDQiuEGFyPEwrM1";
    private String userId = "Fu2uaJfwvsMzQioDH";
    private static String BASE_URL = "http://localhost:5000";
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }


    @Test
    public void getAllListsInBoard() {
        String boardId = "RyAgJa3zh3cb4ceGd";

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("http://localhost:5000/api/boards/" + boardId + "/lists");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());

        if (response.getStatusCode() == 200) {

            List<Map<String, Object>> lists = response.jsonPath().getList("$");
            for (Map<String, Object> list : lists) {
                System.out.println("List ID: " + list.get("_id"));
                System.out.println("Title: " + list.get("title"));
                System.out.println("-------------------");
            }
            assertEquals(200, response.getStatusCode());
        }
    }
    @Test
    public void getCardsInList() {
        String boardId = "RyAgJa3zh3cb4ceGd";
        String listId = "qqqPYySpASJ3q3ATL";

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("http://localhost:5000/api/boards/" + boardId + "/lists/" + listId + "/cards");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());

        assertEquals(200, response.getStatusCode());

    }
    @Test
    public void createNewListInBoard() {
        String boardId = "RyAgJa3zh3cb4ceGd";
        String listTitle = " New List";

        String payload = "{ \"title\": \"" + listTitle + "\" }";

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("http://localhost:5000/api/boards/" + boardId + "/lists");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());

        assertEquals(200,response.getStatusCode());

    }

    @Test
    public void deleteListFromBoard() {
        String boardId = "RyAgJa3zh3cb4ceGd";
        String listId = "DYC6BEZRwJqqFQ7nf";

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("http://localhost:5000/api/boards/" + boardId + "/lists/" + listId);

        assertEquals(200, response.getStatusCode());

    }

    @Test
    public void createCardInList() {
        String boardId = "RyAgJa3zh3cb4ceGd";
        String listId = "vd8rjnn3uqpgKinjQ";

        String cardPayload = """
    {
        "title": "New Card Title",
        "description": "This is a test card created via API",
        "authorId": "Fu2uaJfwvsMzQioDH",
        "swimlaneId": "%s"
    }
    """;

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(cardPayload)
                .when()
                .post("http://localhost:5000/api/boards/" + boardId + "/lists/" + listId + "/cards");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());

        assertEquals(200, response.getStatusCode());
    }
    @Test
    public void moveCardToAnotherList() {
        String boardId = "RyAgJa3zh3cb4ceGd";
        String currentListId = "DYC6BEZRwJqqFQ7nf";
        String newListId = "vd8rjnn3uqpgKinjQ";
        String cardId = "hGCNDX4ATxbiBuHsk";

        String moveCardPayload = """
    {
        "listId": "%s"
    }
    """.formatted(newListId);

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(moveCardPayload)
                .when()
                .put("http://localhost:5000/api/boards/" + boardId + "/lists/" + currentListId + "/cards/" + cardId);


        Response verifyResponse = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("http://localhost:5000/api/boards/" + boardId + "/lists/" + newListId + "/cards");

        assertTrue (verifyResponse.asString().contains(cardId));

        }
    }



