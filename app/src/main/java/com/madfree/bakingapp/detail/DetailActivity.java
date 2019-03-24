package com.madfree.bakingapp.detail;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.madfree.bakingapp.R;
import com.madfree.bakingapp.data.Ingredient;


import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    public static final String RECIPE_ID = "RECIPE_ID";
    private int recipeId;
    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        recipeId = intent.getIntExtra(RECIPE_ID, 0);
        Log.d(LOG_TAG, "Starting DetailActivity with this recipeId from MainActivity: " + recipeId);
    }

    public int getRecipeId() {
        return recipeId;
    }
}
