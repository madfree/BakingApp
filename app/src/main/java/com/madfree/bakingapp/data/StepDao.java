package com.madfree.bakingapp.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface StepDao {

    @Insert
    void insert(Step step);

    @Update
    void update(Step... steps);

    @Delete
    void delete(Step... steps);

    @Query("SELECT * FROM Step")
    List<Step> getAllSteps();

    @Query("SELECT * FROM step WHERE recipeId=:recipeId")
    LiveData<List<Step>> findStepsForRecipe(final int recipeId);
}
