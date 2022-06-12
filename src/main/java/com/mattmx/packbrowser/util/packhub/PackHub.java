package com.mattmx.packbrowser.util.packhub;

import com.mattmx.packbrowser.Packbrowser;

public class PackHub {
    private static PackHubApi api = new PackHubApi();

    public static PackHubApi api() {
        return api;
    }

    public String url() {
        return switch (environment()) {
            case DEV -> "http://192.168.1.132:9000";
            case CLIENT -> "https://www.packhub.net";
        };
    }

    public static boolean validSearch(String query) {
        return query != null && query.length() >= 3;
    }

    public static Environment.EnvironmentType environment() {
        return Packbrowser.class.getAnnotation(Environment.class).type();
    }
}
