package com.jun.plugin.dfs.api;

import com.google.gson.Gson;

public abstract class GsonUtils {

    private static final Gson GSON = new Gson();

    public static <T> T parseObject(String jsonData, Class<T> type) {
        return GSON.fromJson(jsonData, type);
    }
}
