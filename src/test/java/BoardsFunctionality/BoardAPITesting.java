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
    private static String BASE_URL = "http://localhost:5000";
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
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
    public void getBoardsForUser() {


        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("http://localhost:5000/api/users/" + userId + "/boards");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());
    }

    @Test
    public void getBoardForUser() {
        String BoardId ="KKS97e8SPQ29MwDbM";

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("http://localhost:5000/api/boards/" + BoardId);

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());
    }



}