package com.sujan.uxcam.data;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AppPreference {
    public static String PREF_KEY = "UXCAM_Pref";
    public static String SEARCH_LIST_KEY = "search_list";


    private SharedPreferences preferences;


    @Inject
    public AppPreference(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public List<String> getSearchList() {
        String searchList = preferences.getString(SEARCH_LIST_KEY, "");
        if (searchList.isEmpty()) {
            return new ArrayList<>();
        }

        return new Gson().fromJson(searchList, new TypeToken<List<String>>() {
        }.getType());
    }

    public boolean setSearchList(List<String> searchList) {
        String search = new Gson().toJson(searchList);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SEARCH_LIST_KEY, search);
        return editor.commit();
    }
}
