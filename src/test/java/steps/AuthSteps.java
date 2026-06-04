package steps;

import net.serenitybdd.annotations.Step;
import static org.assertj.core.api.Assertions.assertThat;
import clients.AuthApiClients;
import io.restassured.response.Response;
import support.ScenarioContext;

public class AuthSteps {
    AuthApiClients authClients = new AuthApiClients();

    
    @Step("Authenticate with valid credentials")
    public void authenticateWithValidCredentials() throws Exception  {

        Response response = authClients.authenticate();

        assertThat(response.statusCode()).isEqualTo(200);
        String token = response.jsonPath().getString("token");
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();

        ScenarioContext.setToken(token);
    }


}
