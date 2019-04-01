package com.madfree.bakingapp.detail;

import android.app.Application;
import android.util.Log;

import com.madfree.bakingapp.data.Ingredient;
import com.madfree.bakingapp.data.Recipe;
import com.madfree.bakingapp.data.Step;
import com.madfree.bakingapp.repository.RecipeRepository;

import java.util.LinkedList;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class DetailViewModel extends AndroidViewModel {

    public static final String LOG_TAG = DetailViewModel.class.getSimpleName();

    private final MutableLiveData<Integer> selectedRecipe = new MutableLiveData<>();
    private final MutableLiveData<Step> selectedStep = new MutableLiveData<>();
    private LiveData<List<Ingredient>> ingredientsList;
    private LiveData<List<Step>> stepsList;
    private RecipeRepository recipeRepository;


    public DetailViewModel(@NonNull Application application) {
        super(application);
        recipeRepository = new RecipeRepository(application);
        Log.d(LOG_TAG, "New SharedViewModel initialized");
    }

    public void setSelectedRecipe(int recipeId) {
        selectedRecipe.setValue(recipeId);
        Log.d(LOG_TAG, "Set selectedRecipe to: " + selectedRecipe.getValue());
        //ingredientsList = recipeRepository.getIngredientsForRecipe(recipeId);
        //Log.d(LOG_TAG, "The ingredientList from repos has size: " + ingredientsList.getValue().size());
    }

    public MutableLiveData<Integer> getSelectedRecipe() {
        Log.d(LOG_TAG, "Calling selectedRecipeId: " + selectedRecipe.getValue());
        return selectedRecipe;
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


    public void setSelectedStep(Step clickedStep) {
        selectedStep.setValue(clickedStep);
    }
}
