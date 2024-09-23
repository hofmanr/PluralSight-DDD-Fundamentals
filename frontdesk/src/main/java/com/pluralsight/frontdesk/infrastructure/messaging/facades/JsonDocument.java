package com.pluralsight.frontdesk.infrastructure.messaging.facades;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.StringReader;

public class JsonDocument {

    private final JsonObject jsonObject;

    private JsonDocument(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public static JsonDocument parse(String jsonString) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonString))) {
            return new JsonDocument(jsonReader.readObject());
        }
    }

    private boolean isPresent(String property) {
        return jsonObject.containsKey(property);
    }

    public Integer getIntProperty(String property) {
        return isPresent(property) ? jsonObject.getInt(property) : null;
    }

    public String getStringProperty(String property) {
        return isPresent(property) ? jsonObject.getString(property) : null;
    }

    public Boolean getBooleanProperty(String property) {
        return isPresent(property) ? jsonObject.getBoolean(property) : null;
    }

    public JsonDocument getInnerDocument(String property) {
        return isPresent(property) ? new JsonDocument(jsonObject.getJsonObject(property)) : null;
    }

    @Override
    public String toString() {
        return "JsonDocument{" +
                "jsonObject=" + jsonObject +
                '}';
    }

//        // reading arrays from json
//        JsonArray jsonArray = jsonObject.getJsonArray("phoneNumbers");
//        long[] numbers = new long[jsonArray.size()];
//        int index = 0;
//        for (JsonValue value : jsonArray) {
//            numbers[index++] = Long.parseLong(value.toString());
//        }
//        gfgWriter.setPhoneNumbers(numbers);
//
//        // reading inner object from json object
//        JsonObject innerJsonObject = jsonObject.getJsonObject("address");
//        Address address = new Address();
//        address.setStreet(innerJsonObject.getString("street"));
//        address.setCity(innerJsonObject.getString("city"));
//        address.setZipcode(innerJsonObject.getInt("zipcode"));
//        gfgWriter.setAddress(address);
}
