package APIFunctionality;
import static APIFunctionality.ListsAPITests.userId;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BoardAPITests {
    public static String token;
    private static String BASE_URL;

    @BeforeAll
    public static void setup() {

        BASE_URL = System.getProperty("base_url", "http://localhost:3000");
        RestAssured.baseURI = BASE_URL;
        System.out.println("Base URL: " + BASE_URL);
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
        response.then()
                .statusCode(200)
                .body("id", notNullValue());

    }
//    @Test
//    public void regenerateToken() {
//        String loginPayload = """
//    {
//        "username": "admin1",
//        "password": "admin1"
//    }
//    """;
//
//        Response response = given()
//                .header("Content-Type", "application/json")
//                .body(loginPayload)
//                .when()
//                .post(BASE_URL+"/users/login");
//
//        System.out.println("Response Body: " + response.asString());
//        response.then()
//                .statusCode(200)
//                .body("id", notNullValue());
//    }

//    @Test
//    public void createBoardWithApi() {
//
//        String boardPayload = """
//    {
//        "title": "My Test Board",
//        "owner": "%s"
//    }
//    """.formatted(userId);
//
//        Response response = given()
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + token)
//                .body(boardPayload)
//                .when()
//                .post(BASE_URL+"/api/boards");
//
//        System.out.println("Response Status Code: " + response.getStatusCode());
//        System.out.println("Response Body: " + response.asString());
//
//        response.then()
//                .statusCode(200)
//                .body("_id", notNullValue());
//    }

//    @Test
//    public void getBoardsForUser() {
//
//
//        Response response = given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get(BASE_URL+"/api/users/" + userId + "/boards");
//
//        System.out.println("Response Status Code: " + response.getStatusCode());
//        System.out.println("Response Body: " + response.asString());
//        response.then()
//                .statusCode(200)
//                .body("_id", notNullValue());
//    }

//    @Test
//    public void getBoardForUser() {
//        String BoardId ="KKS97e8SPQ29MwDbM";
//
//        Response response = given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get(BASE_URL+"/api/boards/" + BoardId);
//
//        System.out.println("Response Status Code: " + response.getStatusCode());
//        System.out.println("Response Body: " + response.asString());
//        response.then()
//                .statusCode(200)
//                .body("_id", notNullValue());
//    }



}