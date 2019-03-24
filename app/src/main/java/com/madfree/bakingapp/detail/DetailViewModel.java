package com.madfree.bakingapp.detail;

import com.madfree.bakingapp.data.Ingredient;
import com.madfree.bakingapp.data.Recipe;
import com.madfree.bakingapp.data.Step;
import com.madfree.bakingapp.repository.RecipeRepository;

import java.util.List;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetailViewModel extends ViewModel {

    private final MutableLiveData<Recipe> selectedRecipe = new MutableLiveData<>();
    private RecipeRepository recipeRepository = new RecipeRepository();

    public void selectedRecipe(Recipe recipe) {
        selectedRecipe.setValue(recipe);
    }

    public MutableLiveData<Recipe> getSelectedRecipe() {
        return selectedRecipe;
    }

    public LiveData<List<Ingredient>> getIngredientsList(int recipeId) {
        return recipeRepository.fetchIngredientsFromWeb(recipeId);
    }

//    public List<Step> getStepsList() {
//        return recipeRepository.getStepsForRecipe();
//    }
//
//    public String getStepDescription(Step step) {
//        return recipeRepository.getDescriptionForStep(step);
//    }
}
