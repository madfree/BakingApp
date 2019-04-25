package com.madfree.bakingapp.detail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madfree.bakingapp.R;
import com.madfree.bakingapp.data.Ingredient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static java.lang.String.valueOf;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    public static final String LOG_TAG = IngredientsAdapter.class.getSimpleName();

    private List<Ingredient> mIngredientsList;
    private Context context;

    public IngredientsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.ingredient_list_item, viewGroup, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder ingredientViewHolder, int position) {
        ingredientViewHolder.ingredientQuantityTextView.setText(valueOf(mIngredientsList.get(position).getQuantity()));
        ingredientViewHolder.ingredientMeasureTextView.setText(mIngredientsList.get(position).getMeasure());
        ingredientViewHolder.ingredientInfoTextView.setText(mIngredientsList.get(position).getIngredient());
    }

    @Override
    public int getItemCount() {
        if (mIngredientsList == null) {
            return 0;
        }
        return mIngredientsList.size();
    }

    public List<Ingredient> getIngredientList() {
        return mIngredientsList;
    }

    public void setIngredients(List<Ingredient> ingredientList) {
        mIngredientsList = ingredientList;
        Log.d(LOG_TAG, "number of ingredients in adapter is: " + mIngredientsList.size());
        notifyDataSetChanged();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        final TextView ingredientQuantityTextView;
        final TextView ingredientMeasureTextView;
        final TextView ingredientInfoTextView;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientQuantityTextView = itemView.findViewById(R.id.ingredients_quantity);
            ingredientMeasureTextView = itemView.findViewById(R.id.ingredients_measure);
            ingredientInfoTextView = itemView.findViewById(R.id.ingredients_text);
        }
    }
}