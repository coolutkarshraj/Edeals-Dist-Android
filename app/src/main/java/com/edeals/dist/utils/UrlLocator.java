package com.edeals.dist.utils;

import com.edeals.dist.Config;

/**
 * Created by team edeals on 27-02-2018.
 */

public class UrlLocator {
    private static String tempIP = "" ;

    public static String getBaseIP() {

        if (tempIP.equals("") || tempIP == null) {
            return Config.IP;
        } else {
            return tempIP;
        }
    }

    public static void setBaseIP(String ip) {
        tempIP = ip;
    }

    public static String getFinalUrl(String url) {
        String ip = getBaseIP();
        return "http://" + ip + ":9090/v1/" + url;
    }

    public static String getFinalUrl(String url, String params) {
        String ip = getBaseIP();
        return "http://" + ip + ":9090/v1/" + url + params;
    }
}
