package BoardsFunctionality;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BoardAPITesting {
    private String Token ="wlpIQe02qL_LWKKqXzIaA-OXxWaNnhCH4MH2HIbOcdZ";
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
        Token=token;
    }
    @Test
    public void createBoardWithToken() {
        String boardPayload = """
    {
        "title": "My Test Board",
        "owner": "admin1"
    }
    """;

        System.out.println("Token: " + Token);

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token)
                .body(boardPayload)
                .when()
                .post("http://localhost:5000/api/boards");

        // Log the response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());

        // Assertions
        response.then()
                .statusCode(200);
    }

    @Test
    public void getAllBoards() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .when()
                .get("http://localhost:5000/api/boards");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());
    }

    @Test
    public void getUserBoards() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .when()
                .get("http://localhost:5000/api/users/me/boards");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());
    }
    @Test
    public void debugForbiddenError() {
        Response response = given()
                .header("Authorization", "Bearer " + Token)
                .when()
                .get("http://localhost:5000/api/users/me");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());
    }

}