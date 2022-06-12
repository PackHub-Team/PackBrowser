package com.mattmx.packbrowser.util.packhub;

import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PackHubApi {
    public String url() {
        return switch (PackHub.environment()) {
            case DEV -> "http://192.168.1.132:9001";
            case CLIENT -> "https://api.packhub.net";
        };
    }

    public AbstractPack getPackFromId(int id) {
        return getPackFromUrl(url() + "/pack/" + id);
    }

    public AbstractPack getPackFromUrl(String url) {
        return AbstractPack.from(getPage(url));
    }

    public List<AbstractPack> getExclusivePacks() {
        return getPacksFromUrl(url() + "/packs/exclusive");
    }

    public List<AbstractPack> getRecentPacks() {
        return getPacksFromUrl(url() + "/packs/recent");
    }

    public List<AbstractPack> getPopularPacks() {
        return getPacksFromUrl(url() + "/packs/popular");
    }

    public List<AbstractPack> search(PackHubSearchQuery query) {
        return search(query.build());
    }

    public List<AbstractPack> search(String query) {
        return getPacksFromUrl(url() + "/search/" + query);
    }

    public List<AbstractPack> getPacksFromUrl(String url) {
        return AbstractPack.listFrom(getPage(url));
    }

    private String getPage(String url) {
        try {
            URL u = new URL(url);
            URI uri = new URI(u.getProtocol(), u.getUserInfo(), IDN.toASCII(u.getHost()), u.getPort(), u.getPath(), u.getQuery(), u.getRef());
            URLConnection connection = new URL(uri.toASCIIString()).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
