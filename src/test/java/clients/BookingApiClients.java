package clients;

import support.ConfigReader;
import io.restassured.response.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.serenitybdd.rest.SerenityRest;


public class BookingApiClients {
    String baseUrl = ConfigReader.getBaseUrl();

    public Response createValidBooking() throws Exception {
        String payload = new String(Files.readAllBytes(
                Paths.get("src/test/resources/payloads/createBooking.json")));
        return SerenityRest
                .given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .accept("application/json")
                .body(payload)
                .when()
                .post("/booking")
                .then()
                .extract().response();
    }



}
