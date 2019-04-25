package com.madfree.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.madfree.bakingapp.data.AppDatabase;
import com.madfree.bakingapp.data.Ingredient;
import com.madfree.bakingapp.data.Recipe;

import java.util.List;

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    public static final String LOG_TAG = WidgetDataProvider.class.getSimpleName();

    private Recipe favoriteRecipe;
    private List<Ingredient> mIngredientsList;
    private int recipeId;
    private Context mContext;
    private int mAppWidgetId;

    AppDatabase db;

    public WidgetDataProvider(Context context, Intent intent) {
        this.mContext = context;
        this.mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        db = AppDatabase.getsInstance(mContext);
    }

    @Override
    public void onDataSetChanged() {
        Log.d(LOG_TAG, "onDataSetChanged called with recipeId " + favoriteRecipe);
        try {
            favoriteRecipe = db.recipeDao().getFavorite();
            recipeId = favoriteRecipe.getId();
            mIngredientsList = db.ingredientDao().loadIngredientsForWidget(recipeId);
            Log.d(LOG_TAG, "onDataSetChanged: got a list from database with " + mIngredientsList.size() + " ingredients");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mIngredientsList.size();
        }
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy called");
    }

    @Override
    public int getCount() {
        return mIngredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        String ingredientQuantity = mIngredientsList.get(position).getQuantity().toString();
        //Log.d(LOG_TAG, "This is the ingredient: " + ingredientQuantity);
        String ingredientMeasure = mIngredientsList.get(position).getMeasure();
        //Log.d(LOG_TAG, "This is the ingredient: " + ingredientMeasure);
        String ingredientName = mIngredientsList.get(position).getIngredient();
        //Log.d(LOG_TAG, "This is the ingredient: " + ingredientName);
        String ingredientInfo = ingredientQuantity + " " + ingredientMeasure + " " + ingredientName;
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        view.setTextViewText(android.R.id.text1, ingredientInfo);
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
