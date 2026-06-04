package tests;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import steps.AuthSteps;
import steps.BookingSteps;

@ExtendWith(SerenityJUnit5Extension.class)
public class PublicAPISmokeFlowTest {
    AuthSteps authSteps = new AuthSteps();
    BookingSteps bookingSteps = new BookingSteps();


    @Test
    void public_api_smoke_flow() throws Exception {
        authSteps.authenticateWithValidCredentials();
        authSteps.authenticateWithInvalidCredentials();
        bookingSteps.createValidBooking();

    }




}