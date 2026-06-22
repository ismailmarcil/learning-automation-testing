package support;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;
import net.thucydides.model.environment.SystemEnvironmentVariables;

public class ConfigReader {

    private static EnvironmentVariables environmentVariables
            = SystemEnvironmentVariables.createEnvironmentVariables();

    public static String getBaseUrl() {
        return EnvironmentSpecificConfiguration
                .from(environmentVariables)
                .getProperty("base.url");
    }
}