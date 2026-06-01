import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.restassured.RestAssured;

@ExtendWith(SerenityJUnit5Extension.class)
public class PostsApiTest {

    @Steps
    PostSteps posts;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    void shouldGetASinglePost() {
        posts.getSinglePost(2);
    }

    @Test
    void shouldGetAllPosts() {
        posts.getAllPosts();
    }

    @Test
    void shouldCreateAPost() {
        posts.createPost();
    }

}