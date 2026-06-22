package steps;

import net.serenitybdd.annotations.Step;

import static org.assertj.core.api.Assertions.assertThat;
import clients.AuthApiClients;
import io.restassured.response.Response;
import support.ScenarioContext;

public class
AuthSteps {
    AuthApiClients authClients = new AuthApiClients();

    @Step("Authenticate with valid credentials")
    public void authenticateWithValidCredentials() throws Exception {

        Response response = authClients.authenticate();

        assertThat(response.statusCode()).isEqualTo(200);
        String token = response.jsonPath().getString("token");
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();

        ScenarioContext.setToken(token);
    }

    @Step("Authenticate with invalid credentials")
    public void authenticateWithInvalidCredentials() throws Exception {
        Response response = authClients.invalidAuthenticate();

        assertThat(response.statusCode()).isEqualTo(200);

        String token = response.jsonPath().getString("token");
        String reason = response.jsonPath().getString("reason");

        assertThat(token).isNull();
        assertThat(reason).isNotNull();
        assertThat(reason).isNotEmpty();
        assertThat(reason).isEqualTo("Bad credentials");
    }

    @Step("Authenticate with invalid credentials: {0} / {1}")
    public void authenticateWithInvalidCredentials(String username, String password) throws Exception {
        Response response = authClients.authenticate(username, password);

        assertThat(response.statusCode()).isEqualTo(200);

        String token = response.jsonPath().getString("token");
        String reason = response.jsonPath().getString("reason");

        assertThat(token).isNull();
        assertThat(reason).isNotNull();
        assertThat(reason).isNotEmpty();
        assertThat(reason).isEqualTo("Bad credentials");
    }

}
