package com.mattmx.packbrowser.util.packhub;

import com.google.gson.*;

import java.util.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AbstractPackDeserializer implements JsonDeserializer<AbstractPack> {
    @Override
    public AbstractPack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();
        JsonArray jsonPreviews = json.get("previews").getAsJsonArray();
        List<String> previews = new ArrayList<>();
        for (int i = 0; i < jsonPreviews.size(); i++) {
            String val = jsonPreviews.get(i).getAsString();
            previews.add(val);
        }
        JsonArray jsonTags = json.get("tags").getAsJsonArray();
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < jsonTags.size(); i++) {
            String val = jsonTags.get(i).getAsString();
            tags.add(val);
        }
        return AbstractPack.builder()
                .name(json.get("name").getAsString())
                .createdAt(json.get("createdAt").getAsString())
                .updatedAt(json.get("updatedAt").getAsString())
                .downloads(json.get("downloads").getAsInt())
                .icon(json.get("icon").getAsString())
                .price(json.get("price").getAsString())
                .isPublic(json.get("public").getAsBoolean())
                .id(json.get("id").getAsInt())
                .description(json.get("description").getAsString())
                .previews(previews.toArray(String[]::new))
                .tags(tags.toArray(String[]::new)).build();
    }
}
