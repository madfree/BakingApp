package com.madfree.bakingapp.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipe WHERE id=:id")
    LiveData<Recipe> findById(int id);

    @Query("SELECT count(*) FROM Recipe")
    int count();

    @Query("UPDATE Recipe SET isFavorite=:newFavorite WHERE id=:id ")
    void setFavorite(boolean newFavorite, int id);

    @Query("UPDATE Recipe SET isFavorite=0")
    void removeFavorite();

    @Query("SELECT * FROM Recipe WHERE isFavorite=1")
    Recipe getFavorite();

    @Insert
    void insertAll(Recipe... recipes);

    @Insert
    void insert (Recipe recipe);

    @Delete
    void delete(Recipe recipe);

}
