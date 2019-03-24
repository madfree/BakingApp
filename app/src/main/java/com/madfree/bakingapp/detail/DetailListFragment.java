package com.madfree.bakingapp.detail;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madfree.bakingapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class DetailListFragment extends Fragment {

    private RecyclerView ingredientsListView;
    private DetailActivity parent;
    private int recipeId;
    private IngredientsAdapter ingredientsAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipeId = parent.getRecipeId();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ingredientsListView = view.findViewById(R.id.ingredients_list);
        return view;
    }
}
