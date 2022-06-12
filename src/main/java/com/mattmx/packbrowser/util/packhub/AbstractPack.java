package com.mattmx.packbrowser.util.packhub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AbstractPack {
    static Gson gson = new GsonBuilder().create();

    @Getter @Setter private int id;
    @Getter @Setter private String name;
    @Getter @Setter private String description;
    @Getter @Setter private int maker;
    @Getter @Setter private String icon;
    @Getter @Setter private String price;
    @Getter @Setter private int downloads;
    @Getter @Setter private String version;
    @Getter @Setter private String createdAt;
    @Getter @Setter private String updatedAt;

    public static AbstractPack from(String packString) {
        return gson.fromJson(packString, AbstractPack.class);
    }

    public static List<AbstractPack> listFrom(String packString) {
        Type listType = new TypeToken<ArrayList<AbstractPack>>(){}.getType();
        return new Gson().fromJson(packString, listType);
    }
}
