package com.madfree.bakingapp.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface IngredientDao {

    @Insert
    void insert(Ingredient ingredient);

    @Update
    void update(Ingredient... ingredients);

    @Delete
    void delete(Ingredient... ingredient);

    @Query("SELECT * FROM Ingredient")
    List<Ingredient> getAllIngredients();

    @Query("SELECT * FROM ingredient WHERE recipeId=:recipeId")
    List<Ingredient> findIngredientsFroRecipe(final int recipeId);


}
