package com.madfree.bakingapp.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madfree.bakingapp.R;
import com.madfree.bakingapp.data.Recipe;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    public static final String LOG_TAG = RecipeAdapter.class.getSimpleName();

    private final Context mContext;
    final private ItemClickListener mListener;
    private List<Recipe> mRecipeList;

    public RecipeAdapter(Context context, ItemClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recipe_list_item, viewGroup, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int position) {
        recipeViewHolder.recipeNameView.setText(mRecipeList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (mRecipeList == null) {
            return 0;
        }
        return mRecipeList.size();

    }

    public List<Recipe> getRecipeList() {
        return mRecipeList;
    }

    public void setRecipes(List<Recipe> recipeList) {
        mRecipeList = recipeList;
        Log.d(LOG_TAG, "number of recipes in adapter is: " + mRecipeList.size());
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(Recipe clickedRecipe);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView recipeNameView;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameView = itemView.findViewById(R.id.recipeNameTv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = getAdapterPosition();
            Recipe recipe = mRecipeList.get(elementId);
            mListener.onItemClickListener(recipe);
        }
    }
}
