package com.edeals.dist;

/**
 * Created by team edeals on 19-02-2018.
 */

public class Config {
    public final static String IP = "192.168.43.106";

    public static class Url {
        public final static String products = "distributor/products/getAll";
        public final static String registerUser = "user/register";
        public final static String login = "user/login";
        public final static String logout = "user/logout/";
        public final static String orders ="distributor/orders/getAll/";
        public final static String inbox ="distributor/inbox/get/";
        public final static String updateOnlineStatus = "user/updateOnlineStatus";
        public final static String updateDeviceToken = "user/updateDeviceToken";
        public final static String defaultBrands = "distributor/getAllBrands";
    }

    public final static int splashTimeout = 3000;

    public final static String serverNotResponding = "Service temporarily unavailable.";
    public final static String backPressMessage = "double tab to minimize";
    public final static String emptyInbox = "No message found";
    public final static String zeroSearchResuls = "No results found\nPlease search again";

    public final static int PaymentReceived = 1;
    public final static int StockOutdated = 2;
    public final static int NewProductInTrend = 3;
    public final static int NewOrder = 4;
    public final static int OrderDelivered = 5;
}
