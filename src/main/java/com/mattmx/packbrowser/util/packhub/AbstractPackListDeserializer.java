package com.mattmx.packbrowser.util.packhub;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AbstractPackListDeserializer implements JsonDeserializer<List<AbstractPack>> {
    @Override
    public List<AbstractPack> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        List<AbstractPack> ret = new ArrayList<>();
        JsonArray arr = jsonElement.getAsJsonArray();
        for (int i = 0; i < arr.size(); i++) {
            JsonObject json = arr.get(i).getAsJsonObject();
            // "Not a JSON array"
            JsonArray jsonPreviews = json.getAsJsonArray("previews");
            List<String> previews = new ArrayList<>();
            for (int j = 0; j < jsonPreviews.size(); j++) {
                String val = jsonPreviews.get(j).getAsString();
                previews.add(val);
            }
            JsonArray jsonTags = json.get("tags").getAsJsonArray();
            List<String> tags = new ArrayList<>();
            for (int j = 0; j < jsonTags.size(); j++) {
                String val = jsonTags.get(j).getAsString();
                tags.add(val);
            }
            ret.add(AbstractPack.builder()
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
                    .tags(tags.toArray(String[]::new)).build());
        }
        return ret;
    }
}
