package steps;

import net.serenitybdd.annotations.Step;

import static org.assertj.core.api.Assertions.assertThat;
import clients.BookingApiClients;
import io.restassured.response.Response;
import support.ScenarioContext;


public class BookingSteps {
    BookingApiClients bookingClients = new BookingApiClients();

    @Step("Create a valid booking")
    public void createValidBooking() throws Exception {
        Response response = bookingClients.createValidBooking();

        assertThat(response.statusCode()).isEqualTo(200);
        //bookingId is present and valid
        int bookingId = response.jsonPath().getInt("bookingid");
        assertThat(bookingId).isGreaterThan(0);

        //Response fiels is the same as the payload
        assertThat(response.jsonPath().getString("booking.firstname")).isEqualTo("Ismail");
        assertThat(response.jsonPath().getString("booking.lastname")).isEqualTo("Marcil");
        assertThat(response.jsonPath().getInt("booking.totalprice")).isEqualTo(150);

        ScenarioContext.setBookingId(bookingId);
    }

    @Step("get booking by id : {0}")
    public void getBookingById(int bookingID) throws Exception {

        Response response = bookingClients.getBookingById(bookingID);

        assertThat(response.statusCode()).isEqualTo(200);
        //Response fiels is the same as the initial booking
        assertThat(response.jsonPath().getString("firstname")).isNotEmpty();
        assertThat(response.jsonPath().getString("firstname")).isEqualTo("Ismail");
        assertThat(response.jsonPath().getString("lastname")).isEqualTo("Marcil");
        assertThat(response.jsonPath().getInt("totalprice")).isEqualTo(150);

        assertThat(response.jsonPath().getString("bookingdates.checkin")).isEqualTo("2026-06-01");
        assertThat(response.jsonPath().getString("bookingdates.checkout")).isEqualTo("2026-06-15");
    }

    @Step("update the booking with id : {0}")
    public void updateBookingById(int bookingId) throws Exception {
        String token = ScenarioContext.getToken();
        Response response = bookingClients.updateBookingById(bookingId);

        assertThat(response.statusCode()).isEqualTo(200);

        //Updated field matches what we sent
        assertThat(response.jsonPath().getInt("totalprice")).isEqualTo(200);

        //Other fields are still present and not empty
        assertThat(response.jsonPath().getString("firstname")).isNotEmpty();
        assertThat(response.jsonPath().getString("lastname")).isNotEmpty();
        assertThat(response.jsonPath().getString("bookingdates.checkin")).isNotEmpty();
        assertThat(response.jsonPath().getString("bookingdates.checkout")).isNotEmpty();
    }

    @Step("Delete booking with id: {0}")
    public void deleteBookingById(int bookingId) throws Exception {
        String token = ScenarioContext.getToken();
        Response response = bookingClients.deleteBookingById(bookingId);

        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.getBody().asString()).isEqualTo("Created");
    }

    @Step("Get booking not found for id: {0}")
    public void getBookingByIdNotFound(int bookingId) throws Exception {
        Response response = bookingClients.getBookingByIdNotFound(bookingId);

        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(response.getBody().asString()).isEqualTo("Not Found");
    }
}
