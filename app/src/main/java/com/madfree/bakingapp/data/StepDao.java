package com.madfree.bakingapp.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface StepDao {

    @Insert
    void insert(Step step);

    @Query("SELECT * FROM step WHERE recipeId=:recipeId")
    LiveData<List<Step>> findStepsForRecipe(final int recipeId);

    @Query("SELECT * FROM step WHERE recipeId=:recipeId AND stepId=:stepId")
    LiveData<Step> findStepInfo(final int recipeId, final int stepId);
}
