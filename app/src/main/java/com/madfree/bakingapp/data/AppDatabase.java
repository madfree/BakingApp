package com.madfree.bakingapp.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.madfree.bakingapp.network.RecipeService;
import com.madfree.bakingapp.network.RetrofitInstance;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Database(entities = {Recipe.class, Ingredient.class, Step.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "recipes_db";
    private static AppDatabase sInstance;

    public abstract RecipeDao recipeDao();
    public abstract IngredientDao ingredientDao();
    public abstract StepDao stepDao();

    public static AppDatabase getsInstance(Context context) {

        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    private static RoomDatabase.Callback roomCallback = new AppDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            // TODO: Change to onOpen() before submit
            super.onOpen(db);
            fetchRecipeData();
            Log.d(LOG_TAG, "roomCallback starting to set up database");
        }
    };

    private static void fetchRecipeData() {
        final RecipeService recipeService = RetrofitInstance.getsInstance();

        recipeService.getAllRecipes().enqueue(new retrofit2.Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Integer statusCode = response.code();
                Log.d(LOG_TAG, "Status Code: " + statusCode.toString());
                List<Recipe> recipeList = response.body();
                new PopulateDbAsync(sInstance).execute(recipeList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(LOG_TAG, "Error!" + t.getMessage());
            }
        });
    }

    private static class PopulateDbAsync extends AsyncTask<List<Recipe>, Void, Void> {

        private final RecipeDao recipeDao;
        private final IngredientDao ingredientDao;
        private final StepDao stepDao;

        PopulateDbAsync(AppDatabase db) {
            recipeDao = db.recipeDao();
            ingredientDao = db.ingredientDao();
            stepDao = db.stepDao();
        }

        @Override
        protected Void doInBackground(List<Recipe>... lists) {
            sInstance.clearAllTables();

            for (int i = 0; i < lists[0].size(); i++) {
                String recipeName = lists[0].get(i).getName();
                int recipeId = lists[0].get(i).getId();
                int servings = lists[0].get(i).getServings();
                String imageUrl = lists[0].get(i).getImage();
                Recipe newRecipe = new Recipe(recipeId, recipeName, servings, imageUrl);
                recipeDao.insert(newRecipe);
                Log.d(LOG_TAG, "Inserted recipe: " + recipeName);

                List<Ingredient> ingredientList = lists[0].get(i).getIngredients();
                for (int j = 0; j < ingredientList.size(); j++) {
                    String ingredientName = ingredientList.get(j).getIngredient();
                    String ingredientMeasure = ingredientList.get(j).getMeasure();
                    Double ingredientQuantity = ingredientList.get(j).getQuantity();
                    Ingredient ingredient = new Ingredient(ingredientQuantity, ingredientMeasure,
                            ingredientName, recipeId);
                    ingredientDao.insert(ingredient);
                    Log.d(LOG_TAG, "Inserted ingredient: " + ingredientName);
                }

                List<Step> stepList = lists[0].get(i).getSteps();
                for (int k = 0; k < stepList.size(); k++) {
                    int stepId = stepList.get(k).getId();
                    String stepShortDesc = stepList.get(k).getShortDescription();
                    String stepDesc = stepList.get(k).getDescription();
                    String stepVideoUrl = stepList.get(k).getVideoURL();
                    String stepThumbNailUrl = stepList.get(k).getThumbnailURL();
                    Step step = new Step(stepId, stepShortDesc, stepDesc, stepVideoUrl,
                            stepThumbNailUrl, recipeId);
                    stepDao.insert(step);
                    Log.d(LOG_TAG, "Inserted step: " + stepShortDesc);
                }
            }
            return null;
        }
    }
}
