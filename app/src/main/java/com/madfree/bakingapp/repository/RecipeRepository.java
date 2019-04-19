package com.madfree.bakingapp.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.madfree.bakingapp.data.AppDatabase;
import com.madfree.bakingapp.data.Ingredient;
import com.madfree.bakingapp.data.IngredientDao;
import com.madfree.bakingapp.data.Recipe;
import com.madfree.bakingapp.data.RecipeDao;
import com.madfree.bakingapp.data.Step;
import com.madfree.bakingapp.data.StepDao;
import com.madfree.bakingapp.network.RecipeService;
import com.madfree.bakingapp.network.RetrofitInstance;
import com.madfree.bakingapp.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {

    public static final String LOG_TAG = RecipeRepository.class.getSimpleName();

    private final MutableLiveData<Recipe> selectedRecipe = new MutableLiveData<>();
    private final MutableLiveData<Step> selectedStep = new MutableLiveData<>();
    private LiveData<List<Recipe>> allRecipes;
    private LiveData<List<Ingredient>> ingredientList;
    private LiveData<List<Step>> stepsList;
    private LiveData<Recipe> recipeInfo;
    private LiveData<Step> stepInfo;
    AppExecutors mExecutors;
    AppDatabase mDb;

    public RecipeRepository(Context context) {
        mDb = AppDatabase.getsInstance(context);
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        allRecipes = mDb.recipeDao().getAllRecipes();
        return allRecipes;
    }

    public LiveData<Recipe> getRecipe(int recipeId) {
        return mDb.recipeDao().findById(recipeId);
    }

    public LiveData<List<Ingredient>> getIngredientsForRecipe(int recipeId) {
        ingredientList = mDb.ingredientDao().findIngredientsForRecipe(recipeId);
        //Log.d(LOG_TAG, "Loading ingredientsList with size: " + ingredientList.getValue().size());
        return ingredientList;
    }

    public LiveData<List<Step>> getStepsForRecipe(int recipeId) {
        stepsList = mDb.stepDao().findStepsForRecipe(recipeId);
        //Log.d(LOG_TAG, "Loading stepsList with size: " + stepsList.getValue().size());
        return stepsList;
    }

    public LiveData<Step> getStepInfo(int recipeId, int stepId) {
        stepInfo = mDb.stepDao().findStepInfo(recipeId,stepId);
        //Log.d(LOG_TAG, "Loading stepInfo with description: " + stepInfo.getValue().getShortDescription());
        return stepInfo;
    }

    public void setFavorite(int recipeId) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.recipeDao().removeFavorite();
                mDb.recipeDao().setFavorite(true, recipeId);
                Log.d(LOG_TAG, "Set new favorite recipe: " + recipeId);
            }
        });
    }
}