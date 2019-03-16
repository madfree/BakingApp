package com.madfree.bakingapp.repository;

import android.app.Application;
import android.util.Log;

import com.madfree.bakingapp.data.Recipe;
import com.madfree.bakingapp.network.RecipeService;
import com.madfree.bakingapp.network.RetrofitInstance;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipeRepository {

    public static final String LOG_TAG = RecipeRepository.class.getSimpleName();

    private LiveData<List<Recipe>> allRecipes;

    public RecipeRepository() {
        allRecipes = fetchRecipesFromWeb();
    }

    public LiveData<List<Recipe>> fetchRecipesFromWeb() {

        final MutableLiveData<List<Recipe>> data = new MutableLiveData<>();
        RecipeService recipeService = RetrofitInstance.getsInstance();
        Call<List<Recipe>> call = recipeService.getAllRecipes();
        Log.d(LOG_TAG, "URL called: " + call.request().url());

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Integer statusCode = response.code();
                Log.d(LOG_TAG, "Status Code: " + statusCode.toString());
                List<Recipe> recipesDataList = response.body();
                for (Recipe recipe: recipesDataList) {
                    String recipeName = recipe.getName();
                    Log.d(LOG_TAG, "Got recipe: " + recipeName);
                }
                Log.d(LOG_TAG, "This is the raw data from the web: " + response.toString());
                data.setValue(recipesDataList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(LOG_TAG, "Error!" + t.getMessage());
            }
        });
        return data;
    }

}
