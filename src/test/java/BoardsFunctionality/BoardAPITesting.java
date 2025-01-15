package BoardsFunctionality;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.BoardsPage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BoardAPITesting {
    private String token = "1n7YIu0bSGi7ah4Ko8uAWUHbm7fgRDQiuEGFyPEwrM1";
    private String userId = "Fu2uaJfwvsMzQioDH";
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:5000";
    }
    @Test
    public void signInToWekan() {
        String loginPayload = """
            {
                "username": "admin1",
                "password": "admin1"
            }
            """;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(loginPayload)
                .when()
                .post("/users/login");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());

        response.then().statusCode(200);

        String token = response.then().extract().path("token");
        System.out.println("Session Token: " + token);
        this.token=token;

    }
    @Test
    public void regenerateToken() {
        String loginPayload = """
    {
        "username": "admin1",
        "password": "admin1"
    }
    """;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(loginPayload)
                .when()
                .post("http://localhost:5000/users/login");

        System.out.println("Response Body: " + response.asString());
    }

    @Test
    public void createBoardWithApi() {

        String boardPayload = """
    {
        "title": "My Test Board",
        "owner": "%s"
    }
    """.formatted(userId);

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(boardPayload)
                .when()
                .post("http://localhost:5000/api/boards");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());

        response.then()
                .statusCode(200)
                .body("_id", notNullValue());
    }

    @Test
    public void getUserSpecificBoards() {
        String token = "1n7YIu0bSGi7ah4Ko8uAWUHbm7fgRDQiuEGFyPEwrM1";

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("http://localhost:5000/api/users/me/boards");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());
    }

    @Test
    public void deleteAndVerifyBoard() {
        createBoardWithApi();
        String boardId =BoardsPage.getBoardId("My Test Board");

        Response deleteResponse = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("http://localhost:5000/api/boards/" + boardId);

        System.out.println("Delete Response Status Code: " + deleteResponse.getStatusCode());
        System.out.println("Delete Response Body: " + deleteResponse.asString());

        deleteResponse.then().statusCode(200);

        Response getBoardsResponse = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("http://localhost:5000/api/boards");

        System.out.println("Get Boards Response Status Code: " + getBoardsResponse.getStatusCode());
        System.out.println("Get Boards Response Body: " + getBoardsResponse.asString());

        getBoardsResponse.then()
                .statusCode(200)
                .body("find { it._id == '" + boardId + "' }", equalTo(null));
    }

    @Test
    public void getUserInfo() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("http://localhost:5000/api/users/me");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());
    }
    @Test
    public void deleteBoard() {
        String boardId = "Ks3x3bnf8bNxgypDD/fe";

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("http://localhost:5000/api/boards/" + boardId);

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());

        // Assert success
        response.then().statusCode(200);
    }





}