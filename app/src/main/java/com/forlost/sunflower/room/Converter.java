package com.forlost.sunflower.room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Date;

public class Converter {
    @TypeConverter
    public static JsonElement stringToJsonElement(String s) {
        return JsonParser.parseString(s);
    }

    @TypeConverter
    public static String JsonElementToString(JsonElement jsonElement) {
        return new Gson().toJson(jsonElement);
    }

    @TypeConverter
    public static Date revertDate(long value) {
        return new Date(value);
    }

    @TypeConverter
    public static long converterDate(Date value) {
        return value.getTime();
    }
}
