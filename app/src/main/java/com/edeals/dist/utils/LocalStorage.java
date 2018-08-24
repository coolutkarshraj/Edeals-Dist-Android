package com.edeals.dist.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.edeals.dist.models.DistributorProfile;

/**
 * Created by team edeals on 2/11/2018.
 */

public class LocalStorage {
    private static LocalStorage instance;
    private SharedPreferences storage;
    final String DISTRIBUTOR = "Distributor";
    public static final String ONLINE_STATUS = "onlineStatus";

    public LocalStorage(Context context) {
        storage = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static LocalStorage getInstance(Context context) {
        if (instance == null)
            instance = new LocalStorage(context);
        return instance;
    }

    public void putString(String name, String value) {
        storage.edit().putString(name, String.valueOf(value)).apply();
    }

    public String getString(String name) {
        return storage.getString(name, "");
    }

    public void putInt(String name, int value) {
        storage.edit().putInt(name, value).apply();
    }

    public int getInt(String name) {
        return storage.getInt(name, -1);
    }

    public void putDistributorProfile(DistributorProfile profile) {
        final Gson gson = new Gson();
        String profileInJson = gson.toJson(profile);
        storage.edit().putString(DISTRIBUTOR, profileInJson).apply();
    }

    public DistributorProfile getDistributorProfile() {
        final Gson gson = new Gson();
        String profileJson = storage.getString(DISTRIBUTOR, null);
        DistributorProfile profile = gson.fromJson(profileJson, DistributorProfile.class);
        return profile;
    }
    public void clearAll() {
        storage.edit().clear().apply();
    }
}

