package com.madfree.bakingapp.main;

import android.app.Application;

import com.madfree.bakingapp.data.AppDatabase;
import com.madfree.bakingapp.data.Recipe;
import com.madfree.bakingapp.repository.RecipeRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Recipe>> recipesListObservable;
    private RecipeRepository recipeRepository;


    public MainViewModel(@NonNull Application application) {
        super(application);
        recipeRepository = new RecipeRepository(application);
        recipesListObservable = recipeRepository.getAllRecipes();
    }

    public LiveData<List<Recipe>> getRecipesListObservable() {
        return recipesListObservable;
    }

}
