package com.madfree.bakingapp.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface IngredientDao {

    @Insert
    void insert(Ingredient ingredient);

    @Query("SELECT * FROM ingredient WHERE recipeId=:recipeId")
    LiveData<List<Ingredient>> findIngredientsForRecipe(int recipeId);

    @Query("SELECT * FROM Ingredient WHERE recipeId=:recipeId")
    List<Ingredient> loadIngredientsForWidget(int recipeId);
}
