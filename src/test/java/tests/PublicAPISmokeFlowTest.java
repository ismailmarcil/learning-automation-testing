package tests;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import steps.AuthSteps;
import steps.BookingSteps;
import support.ScenarioContext;

@ExtendWith(SerenityJUnit5Extension.class)
public class PublicAPISmokeFlowTest {
    AuthSteps authSteps = new AuthSteps();
    BookingSteps bookingSteps = new BookingSteps();


    @Test
    @Tag("smoke")
    void valid_auth() throws Exception {
        authSteps.authenticateWithValidCredentials();
    }

    @Test
    @Tag("regression")
    void invalid_auth() throws Exception {
        authSteps.authenticateWithInvalidCredentials();
    }

    @Test
    @Tag("smoke")
    void full_booking_flow() throws Exception {
        authSteps.authenticateWithValidCredentials();
        bookingSteps.createValidBooking();
        bookingSteps.getBookingById(ScenarioContext.getBookingId());
        bookingSteps.updateBookingById(ScenarioContext.getBookingId());
        bookingSteps.deleteBookingById(ScenarioContext.getBookingId());
    }

    @Test
    @Tag("regression")
    void booking_not_found() throws Exception {
        authSteps.authenticateWithValidCredentials();
        bookingSteps.createValidBooking();
        bookingSteps.deleteBookingById(ScenarioContext.getBookingId());
        bookingSteps.getBookingByIdNotFound(ScenarioContext.getBookingId());

    }





}