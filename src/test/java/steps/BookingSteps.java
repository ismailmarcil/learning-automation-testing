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
}
