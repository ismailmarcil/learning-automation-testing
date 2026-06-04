package support;

public class ScenarioContext {
    private static String token;
    private static int bookingId;

    public static void setToken(String t) {
        token = t;
    }
    public static String getToken() {
        return token;
    }


    public static void setBookingId(int id) {
        bookingId = id;
    }
    public static int getBookingId() {
        return bookingId;
    }

}

