package com.mattmx.packbrowser.util.packhub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Builder
public class AbstractPack extends ValidResponse {
    static Gson gson = new GsonBuilder()
            .registerTypeAdapter(AbstractPack.class, new AbstractPackDeserializer())
            .registerTypeAdapter(new TypeToken<ArrayList<AbstractPack>>(){}.getType(), new AbstractPackListDeserializer())
            .enableComplexMapKeySerialization()
            .create();

    private int id;
    @Getter @Setter private String name;
    @Getter @Setter private String description;
    @Getter @Setter private int maker;
    @Getter @Setter private String icon;
    @Getter @Setter private String[] previews;
    @Getter @Setter private String[] tags;
    @Getter @Setter private String price;
    @Getter @Setter private boolean isPublic;
    @Getter @Setter private int downloads;
    @Getter @Setter private String version;
    @Getter @Setter private String createdAt;
    @Getter @Setter private String updatedAt;

    public String getFirstPreview() {
        return previews == null ? "/assets/packbrowser/preview_default.png" : (previews.length > 0 ? previews[0] : "/assets/packbrowser/preview_default.png");
    }

    public boolean hasPreview() {
        return previews != null && previews.length > 0;
    }

    public boolean hasIcon() {
        return icon != null;
    }

    public String getIcon() {
        return icon == null ? "/assets/packbrowser/icon_default.png" : icon;
    }

    public String getDownloadsShort() {
        int power;
        double value = downloads;
        String suffix = " kmbt";
        String formattedNumber;
        NumberFormat formatter = new DecimalFormat("#,###.#");
        power = (int)StrictMath.log10(value);
        value = value/(Math.pow(10,(power/3f)*3));
        formattedNumber=formatter.format(value);
        formattedNumber = formattedNumber + suffix.charAt(power/3);
        return formattedNumber.length() > 4 ? formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
    }

    public static AbstractPack from(String packString) {
        return gson.fromJson(packString, AbstractPack.class);
    }

    public static List<AbstractPack> listFrom(String packString) {
        Type listType = new TypeToken<ArrayList<AbstractPack>>(){}.getType();
        return new Gson().fromJson(packString, listType);
    }
}
