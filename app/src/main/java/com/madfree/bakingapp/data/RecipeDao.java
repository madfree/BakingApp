package com.madfree.bakingapp.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipe WHERE id=:id")
    Recipe findById(int id);

    @Query("SELECT count(*) FROM Recipe")
    int count();

    @Insert
    void insertAll(Recipe... recipes);

    @Insert
    void insert (Recipe recipe);

    @Delete
    void delete(Recipe recipe);
}
