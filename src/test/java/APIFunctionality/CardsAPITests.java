package APIFunctionality;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardsAPITests {

    private String token = "1n7YIu0bSGi7ah4Ko8uAWUHbm7fgRDQiuEGFyPEwrM1";
    private String userId = "Fu2uaJfwvsMzQioDH";
    private static String BASE_URL = "http://localhost:5000";
    @BeforeAll
    public static void setup() {
        BASE_URL = System.getProperty("base.url", "http://localhost:5000");
        RestAssured.baseURI = BASE_URL;
        System.out.println("Base URL: " + BASE_URL);
    }

    @Test
    public void getCardsInList() {
        String boardId = "RyAgJa3zh3cb4ceGd";
        String listId = "qqqPYySpASJ3q3ATL";

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(BASE_URL+"/api/boards/" + boardId + "/lists/" + listId + "/cards");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());

        response.then()
                .statusCode(200)
                .body("_id", notNullValue());

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
                .post(BASE_URL+"/api/boards/" + boardId + "/lists/" + listId + "/cards");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());

        response.then()
                .statusCode(200)
                .body("_id", notNullValue());
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
                .put(BASE_URL+"/api/boards/" + boardId + "/lists/" + currentListId + "/cards/" + cardId);


        Response verifyResponse = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(BASE_URL+"/api/boards/" + boardId + "/lists/" + newListId + "/cards");

        assertTrue (verifyResponse.asString().contains(cardId));

    }
}
