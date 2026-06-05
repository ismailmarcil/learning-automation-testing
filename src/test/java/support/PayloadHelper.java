package support;

public class PayloadHelper {

    public static String resolvePlaceholders(String content) {
        return content
                .replace("{{token}}", ScenarioContext.getToken() != null ? ScenarioContext.getToken() : "")
                .replace("{{bookingId}}", String.valueOf(ScenarioContext.getBookingId()));
    }
}