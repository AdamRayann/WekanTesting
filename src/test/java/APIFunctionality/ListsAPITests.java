package APIFunctionality;

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

public class ListsAPITests {

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
    public void getAllListsInBoard() {
        String boardId = "RyAgJa3zh3cb4ceGd";

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(BASE_URL+"/api/boards/" + boardId + "/lists");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());

        if (response.getStatusCode() == 200) {

            List<Map<String, Object>> lists = response.jsonPath().getList("$");
            for (Map<String, Object> list : lists) {
                System.out.println("List ID: " + list.get("_id"));
                System.out.println("Title: " + list.get("title"));
                System.out.println("-------------------");
            }
            response.then()
                    .statusCode(200)
                    .body("_id", notNullValue());
        }
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
                .post(BASE_URL+"/api/boards/" + boardId + "/lists");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());

        response.then()
                .statusCode(200)
                .body("_id", notNullValue());

    }

    @Test
    public void deleteListFromBoard() {
        String boardId = "RyAgJa3zh3cb4ceGd";
        String listId = "DYC6BEZRwJqqFQ7nf";

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(BASE_URL+"/api/boards/" + boardId + "/lists/" + listId);

        response.then()
                .statusCode(200)
                .body("_id", notNullValue());

    }


    }



