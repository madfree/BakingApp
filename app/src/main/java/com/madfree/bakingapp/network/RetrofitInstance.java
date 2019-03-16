package com.madfree.bakingapp.network;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    private static final String LOG_TAG = RetrofitInstance.class.getSimpleName();
    private static RecipeService sInstance;
    private static final Object sLock = new Object();

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        Log.d(LOG_TAG, "Retrofit Instance started!");
        return retrofit;
    }

    public static RecipeService getsInstance() {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = getRetrofitInstance().create(RecipeService.class);
            }
            Log.d(LOG_TAG, "Recipe Service started!");
            return sInstance;
        }
    }
}
