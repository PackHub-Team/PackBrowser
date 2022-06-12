package com.mattmx.packbrowser.gui;

public enum PackTabs {
    EXCLUSIVE("Exclusive"),
    RECENT("Recent"),
    POPULAR("Popular");
    String name;
    PackTabs(String n) {
        name = n;
    }
    @Override
    public String toString() {
        return name;
    }
}
