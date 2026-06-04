package clients;

import net.serenitybdd.rest.SerenityRest;
import io.restassured.response.Response;
import support.ConfigReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AuthApiClients {
    String baseUrl = ConfigReader.getBaseUrl();

    public Response authenticate() throws Exception {
        String payload = new String(Files.readAllBytes(
                Paths.get("src/test/resources/payloads/auth.json")));

        return SerenityRest
                .given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/auth")
                .then()
                .extract().response();

    }

    public Response invalidAuthenticate() throws Exception {
        String payload = new String(Files.readAllBytes(
                Paths.get("src/test/resources/payloads/invalidAuth.json")));

        return SerenityRest
                .given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/auth")
                .then()
                .extract().response();

    }
}
