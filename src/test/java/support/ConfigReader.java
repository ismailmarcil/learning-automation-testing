package support;

public class ConfigReader {
    public static String getBaseUrl() { //static so we don't have to create an instance of ConfigReader to call the method.
        return "https://restful-booker.herokuapp.com";

    }
}
