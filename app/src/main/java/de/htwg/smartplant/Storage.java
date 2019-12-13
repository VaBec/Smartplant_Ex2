package de.htwg.smartplant;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import de.htwg.smartplant.rest.jsonmodels.Plant;
import de.htwg.smartplant.rest.jsonmodels.User;

public class Storage {

    public static final String STORAGE_NAME = "SmartPlantStorage";

    private static final Gson parser = new Gson();
    private static final String USER_KEY_PREFIX = "user_";
    private static final String PLANTS_KEY_PREFIX = "plants_";

    public static void savePlantsToStorage(List<Plant> plants, Context context, String userName) {
        saveToStorage(parser.toJson(plants), context, PLANTS_KEY_PREFIX + userName);
    }

    public static void saveUserToStorage(User user, Context context) {
        saveToStorage(parser.toJson(user), context, USER_KEY_PREFIX + user.getUserName());
    }

    private static void saveToStorage(String jsonString, Context context, String key) {
        SharedPreferences.Editor editor = context.getSharedPreferences(STORAGE_NAME, 0)
                .edit();

        editor.putString(key, jsonString);
        editor.apply();
    }

    public static User getUserFromStorage(String userName, Context context) {
        return parser.fromJson(context.getSharedPreferences(STORAGE_NAME, 0)
                .getString(USER_KEY_PREFIX + userName, ""),
                User.class);
    }

    public static List<Plant> getPlantsFromStorage(String userName, Context context) {
        Type plantListeType = new TypeToken<List<Plant>>() {}.getType();

        return parser.fromJson(context.getSharedPreferences(STORAGE_NAME, 0)
                        .getString(PLANTS_KEY_PREFIX + userName, ""), plantListeType);
    }

    private static String getFromStorage(String key, Context context) {
        return context.getSharedPreferences(STORAGE_NAME, 0)
                .getString(key, "");
    }
}
