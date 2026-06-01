import net.serenitybdd.annotations.Step;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PostSteps {

    @Step("Get post with id {0}")
    public void getSinglePost(int id) {
        given()
                .when()
                .get("/posts/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("userId", notNullValue());
    }

    @Step("Get all posts and verify count")
    public void getAllPosts() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("size()", equalTo(100));
    }

    @Step("Create a new post")
    public void createPost() {
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