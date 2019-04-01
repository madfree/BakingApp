package com.madfree.bakingapp.main;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.madfree.bakingapp.R;
import com.madfree.bakingapp.data.AppDatabase;
import com.madfree.bakingapp.data.Recipe;
import com.madfree.bakingapp.detail.DetailActivity;
import com.madfree.bakingapp.utils.AppExecutors;
import com.madfree.bakingapp.utils.EspressoIdlingResource;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ItemClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String RECIPE_ID = "RECIPE_ID";
    private RecipeAdapter mAdapter;
    private EspressoIdlingResource mEspressoIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mEspressoIdlingResource == null) {
            mEspressoIdlingResource = new EspressoIdlingResource();
        }
        return mEspressoIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase db = AppDatabase.getsInstance(getApplication());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int count = db.recipeDao().count();
                Log.d(LOG_TAG, "There are " + count + " recipes in the database");
            }
        });

        RecyclerView mRecyclerView = findViewById(R.id.recipes_recyclerView);
        mAdapter = new RecipeAdapter(this, this);
        if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
        mRecyclerView.setAdapter(mAdapter);

        getIdlingResource();
        if (mEspressoIdlingResource != null) {
            mEspressoIdlingResource.setIdleState(false);
        }

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getRecipesListObservable().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                Log.d(LOG_TAG,
                        "Updating list of recipes from LiveData in ViewModel with " + recipes.size() + " recipes");
                if (mEspressoIdlingResource != null) {
                    mEspressoIdlingResource.setIdleState(true);
                    mAdapter.setRecipes(recipes);
                }
            }
        });
    }

    @Override
    public void onItemClickListener(Recipe clickedRecipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(RECIPE_ID, clickedRecipe.getId());
        startActivity(intent);
        Log.d(LOG_TAG, "Starting DetailActivity with Recipe: " + clickedRecipe.getName());
    }
}
