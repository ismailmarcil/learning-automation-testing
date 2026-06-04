package tests;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import steps.AuthSteps;

@ExtendWith(SerenityJUnit5Extension.class)
public class PublicAPISmokeFlow {
    AuthSteps authSteps = new AuthSteps();

    @Test
    void public_api_smoke_flow() throws Exception {
        authSteps.authenticateWithValidCredentials();
    }


}