package com.madfree.bakingapp.detail;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.madfree.bakingapp.R;
import com.madfree.bakingapp.data.Recipe;
import com.madfree.bakingapp.widget.IngredientsWidget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    public static final String PREFS_FAVORITE = "Prefs_Favorite";
    public static final String RECIPE_ID = "RECIPE_ID";
    private DetailViewModel sharedViewModel;
    private int recipeId;
    private boolean mTwoPane;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        recipeId = intent.getIntExtra(RECIPE_ID, 0);
        Log.d(LOG_TAG, "Starting DetailActivity with this recipeId from MainActivity: " + recipeId);

        sharedViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        sharedViewModel.setSelectedRecipe(recipeId);
        Log.d(LOG_TAG, "Set recipeId in sharedViewModel in DetailActivity to: " + recipeId);

        sharedViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                setTitle(recipe.getName());
            }
        });

        setContentView(R.layout.activity_detail);
        if (savedInstanceState != null) {
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailListFragment detailListFragment = new DetailListFragment();


        if (findViewById(R.id.info_container) != null) {
            mTwoPane = true;

            DetailInfoFragment detailInfoFragment = new DetailInfoFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.list_container, detailListFragment)
                    .add(R.id.info_container, detailInfoFragment)
                    .commit();
        } else {
            mTwoPane = false;

            fragmentManager.beginTransaction()
                    .add(R.id.list_container, detailListFragment)
                    .commit();
        }
    }

    public int getRecipe() {
        return recipeId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "Setting up the menu");
        final MenuItem item = menu.findItem(R.id.action_favourite);

        sharedViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                boolean favorite = recipe.getFavorite();
                if (!favorite) {
                    Log.d(LOG_TAG, "Status of favorite in selected Recipe is: " + favorite);
                    item.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_no_favorite));
                } else {
                    item.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite));
                    Log.d(LOG_TAG, "Status of favorite in selected Recipe is: " + favorite);
                }
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favourite:
                sharedViewModel.setFavorite(recipeId);
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_FAVORITE, MODE_PRIVATE).edit();
                sharedViewModel.getRecipe().observe(this, new Observer<Recipe>() {
                    @Override
                    public void onChanged(Recipe recipe) {
                        editor.putString("favName", recipe.getName());
                        editor.apply();
                    }
                });
                item.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite));
                updateWidget();
        }
        return true;
    }

    public void updateWidget() {
        Intent intent = new Intent(this, IngredientsWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), IngredientsWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

}
