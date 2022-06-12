package com.mattmx.packbrowser.gui;

public enum PackTabs {
    EXCLUSIVE("Exclusive", 0),
    RECENT("Recent", 1),
    POPULAR("Popular", 2);
    private String name;
    private int num;
    PackTabs(String n, int i) {
        name = n;
        num = i;
    }
    public int num() {
        return num;
    }
    @Override
    public String toString() {
        return name;
    }
    public static PackTabs get(int i) {
        for (PackTabs tab : values()) {
            if (tab.num() == i) {
                return tab;
            }
        }
        return null;
    }
}
