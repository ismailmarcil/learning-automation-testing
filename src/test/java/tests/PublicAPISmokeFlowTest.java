package tests;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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

    @ParameterizedTest
    @CsvFileSource(resources = "/testdata/invalidCredentials.csv", numLinesToSkip = 1)
    @Tag("regression")
    void invalid_auth_multiple_credentials(String username, String password) throws Exception {
        authSteps.authenticateWithInvalidCredentials(username, password);
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