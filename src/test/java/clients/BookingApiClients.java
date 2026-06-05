package clients;

import support.ConfigReader;
import io.restassured.response.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.serenitybdd.rest.SerenityRest;
import support.PayloadHelper;


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

    public Response getBookingById(int bookingID) throws Exception {
        return SerenityRest
                .given()
                .baseUri(baseUrl)
                .accept("application/json")
                .when()
                .get("/booking/"+bookingID)
                .then()
                .extract().response();
    }

    public Response updateBookingById(int bookingId) throws Exception {
        String payload = new String(Files.readAllBytes(
                Paths.get("src/test/resources/payloads/updateBooking.json")));
        return SerenityRest
                .given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .accept("application/json")
                .header("Cookie", PayloadHelper.resolvePlaceholders("token={{token}})"))
                .body(payload)
                .when()
                .put("/booking/"+bookingId)
                .then()
                .extract().response();
    }

    public Response deleteBookingById(int bookingId) throws Exception {
        return SerenityRest
                .given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .header("Cookie", PayloadHelper.resolvePlaceholders("token={{token}}"))
                .when()
                .delete("/booking/" + bookingId)
                .then()
                .extract().response();
    }

    public Response getBookingByIdNotFound(int bookingId) throws Exception {
        return SerenityRest
                .given()
                .baseUri(baseUrl)
                .accept("application/json")
                .when()
                .get("/booking/" + bookingId)
                .then()
                .extract().response();
    }




}
