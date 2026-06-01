import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ApiTests {
    @BeforeAll
    static void setup(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    @DisplayName("Get one post")
    void getAsinglePost() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", notNullValue());
    }

    @Test
    @DisplayName("Get all posts")
    void getAllPosts() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("size()",equalTo(100));

    }

    @Test
    void createAPost() {
        String requestBody = "{\n" +
                "  \"title\": \"my first post\",\n" +
                "  \"body\": \"this is the content\",\n" +
                "  \"userId\": 1\n" +
                "}";

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("my first post"))
                .body("id", notNullValue());
    }   

}

