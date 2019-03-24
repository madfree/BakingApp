package com.madfree.bakingapp.repository;

import android.app.Application;
import android.util.Log;

import com.madfree.bakingapp.data.Ingredient;
import com.madfree.bakingapp.data.Recipe;
import com.madfree.bakingapp.network.RecipeService;
import com.madfree.bakingapp.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {

    public static final String LOG_TAG = RecipeRepository.class.getSimpleName();

    private final MutableLiveData<List<Recipe>> recipeData = new MutableLiveData<>();
    private final MutableLiveData<List<Ingredient>> ingredientData = new MutableLiveData<>();
    private LiveData<List<Ingredient>> allIngredients;
    private LiveData<List<Ingredient>> ingredientsForRecipe;
    private List<Ingredient> ingredientsList;
    private List<Recipe> recipesDataList;

    public RecipeRepository() {
    }

    public LiveData<List<Recipe>> fetchRecipesFromWeb() {

        RecipeService recipeService = RetrofitInstance.getsInstance();
        Call<List<Recipe>> call = recipeService.getAllRecipes();
        Log.d(LOG_TAG, "URL called: " + call.request().url());

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Integer statusCode = response.code();
                Log.d(LOG_TAG, "Status Code: " + statusCode.toString());
                recipesDataList = response.body();
                for (Recipe recipe: recipesDataList) {
                    String recipeName = recipe.getName();
                    Log.d(LOG_TAG, "Got recipe: " + recipeName);
                }

                Log.d(LOG_TAG, "This is the raw data from the web: " + response.toString());
                recipeData.setValue(recipesDataList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(LOG_TAG, "Error!" + t.getMessage());
            }
        });
        return recipeData;
    }

    public LiveData<List<Ingredient>> getAllIngredients() {
        return allIngredients;
    }

    public LiveData<List<Ingredient>> fetchIngredientsFromWeb(int recipeId) {

        RecipeService recipeService = RetrofitInstance.getsInstance();
        Call<List<Recipe>> call = recipeService.getAllRecipes();
        Log.d(LOG_TAG, "URL called: " + call.request().url());

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Integer statusCode = response.code();
                Log.d(LOG_TAG, "Status Code: " + statusCode.toString());
                recipesDataList = response.body();
                List<Ingredient> ingredients = recipesDataList.get(recipeId).getIngredients();
                Log.d(LOG_TAG, "Got recipe: " + recipesDataList.get(recipeId).getName());
                ingredientsList = new ArrayList<>();
                for (Ingredient ingredientItem: ingredients) {
                    Double quantity = ingredientItem.getQuantity();
                    String measure = ingredientItem.getMeasure();
                    String ingredient = ingredientItem.getIngredient();
                    Log.d(LOG_TAG, "Got ingredient: " + ingredientItem.getIngredient());
                    Ingredient newIngredient = new Ingredient(quantity, measure, ingredient);
                    ingredientsList.add(newIngredient);
                }
                ingredientData.setValue(ingredientsList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(LOG_TAG, "Error!" + t.getMessage());
            }
        });
        return ingredientData;
    }
}
