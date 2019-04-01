package com.madfree.bakingapp.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.madfree.bakingapp.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import androidx.lifecycle.ViewModelProviders;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    public static final String RECIPE_ID = "RECIPE_ID";
    private int recipeId;
    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        recipeId = intent.getIntExtra(RECIPE_ID, 0);
        Log.d(LOG_TAG, "Starting DetailActivity with this recipeId from MainActivity: " + recipeId);

        DetailViewModel sharedViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        sharedViewModel.setSelectedRecipe(recipeId);
        Log.d(LOG_TAG, "Set recipeId in sharedViewModel in DetailActivity to: " + recipeId);

        setContentView(R.layout.activity_detail);

        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailListFragment detailListFragment = new DetailListFragment();
        fragmentManager.beginTransaction()
                .add(R.id.list_container, detailListFragment)
                .commit();
    }

    public int getRecipe() {
        return recipeId;
    }
}
