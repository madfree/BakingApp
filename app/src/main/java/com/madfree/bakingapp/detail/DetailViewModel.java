package com.madfree.bakingapp.detail;

import android.app.Application;
import android.util.Log;

import com.madfree.bakingapp.data.Ingredient;
import com.madfree.bakingapp.data.Recipe;
import com.madfree.bakingapp.data.Step;
import com.madfree.bakingapp.repository.RecipeRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class DetailViewModel extends AndroidViewModel {

    public static final String LOG_TAG = DetailViewModel.class.getSimpleName();

    private final MutableLiveData<Integer> selectedRecipe = new MutableLiveData<>();
    private final MutableLiveData<Integer> selectedStep = new MutableLiveData<>();
    private LiveData<List<Ingredient>> ingredientsList;
    private LiveData<List<Step>> stepsList;
    private LiveData<Step> stepInfo;
    private RecipeRepository recipeRepository;


    public DetailViewModel(@NonNull Application application) {
        super(application);
        recipeRepository = new RecipeRepository(application);
        Log.d(LOG_TAG, "New SharedViewModel initialized");
    }

    public void setSelectedRecipe(int recipeId) {
        selectedRecipe.setValue(recipeId);
        Log.d(LOG_TAG, "Set selectedRecipe to: " + selectedRecipe.getValue());
    }

    public MutableLiveData<Integer> getSelectedRecipe() {
        Log.d(LOG_TAG, "Calling selectedRecipeId: " + selectedRecipe.getValue());
        return selectedRecipe;
    }

    public LiveData<Recipe> getRecipe() {
        return recipeRepository.getRecipe(selectedRecipe.getValue());
    }

    public LiveData<List<Ingredient>> getIngredientsList() {
        //Log.d(LOG_TAG, "Calling repository for list of ingredients for recipeId: " + recipeId);
        ingredientsList = recipeRepository.getIngredientsForRecipe(selectedRecipe.getValue());
        return ingredientsList;
    }

    public LiveData<List<Step>> getStepsList() {
        stepsList = recipeRepository.getStepsForRecipe(selectedRecipe.getValue());
        return stepsList;
    }

    public LiveData<Step> getStepInfo() {
        if (selectedStep.getValue() == null) {
            stepInfo = recipeRepository.getStepInfo(selectedRecipe.getValue(), 0);
        } else {
            stepInfo = recipeRepository.getStepInfo(selectedRecipe.getValue(),
                    selectedStep.getValue());
        }
        return stepInfo;
    }

    public void setSelectedStep(int clickedStep) {
        selectedStep.setValue(clickedStep);
    }

    public void setFavorite(int recipeId) {
        recipeRepository.setFavorite(recipeId);
    }

}
