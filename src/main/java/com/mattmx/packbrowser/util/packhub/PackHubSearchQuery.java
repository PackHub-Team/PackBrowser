package com.mattmx.packbrowser.util.packhub;

import lombok.Builder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Builder
public class PackHubSearchQuery {
    private final String search;
    private final Category category;
    private final String version;
    private final SortBy sortBy;
    public enum SortBy {
        DATE_OLDEST("date_oldest"), DATE_NEWEST("date_newest"), RELEVENCY("relevency");
        String st;
        SortBy(String by) {
            st = by;
        }
        @Override
        public String toString() {
            return st;
        }
    }
    public enum Category {
        ALL("all"), PACKS("packs"), CREATORS("pack_makers");
        String st;
        Category(String s) {
            st = s;
        }
        @Override
        public String toString() {
            return st;
        }
    }

    public boolean valid() {
        return search != null && search.length() >= 3;
    }

    public String build() {
        // http://192.168.1.132:9001/search/Matte?category=packs&version=1.17.1&sortBy=date_newest
        String url = search + "?category=" + (category == null ? Category.ALL.toString() : category.toString());
        if (version != null) url += "&version=" + version;
        if (sortBy != null) url += "&sortBy=" + sortBy.toString();
        return url;
    }
}
