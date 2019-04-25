package com.madfree.bakingapp.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madfree.bakingapp.R;
import com.madfree.bakingapp.data.Ingredient;
import com.madfree.bakingapp.data.Step;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailListFragment extends Fragment implements StepsAdapter.ItemClickListener {

    public static final String LOG_TAG = DetailListFragment.class.getSimpleName();

    private RecyclerView ingredientsListView;
    private RecyclerView stepsListView;
    private DetailViewModel sharedViewModel;
    private IngredientsAdapter ingredientsAdapter;
    private StepsAdapter stepsAdapter;
    private DetailActivity parent;
    private boolean mTwoPane;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lists, container, false);
        Log.d(LOG_TAG, "Starting DetaiListFragement");

        sharedViewModel = ViewModelProviders.of(getActivity()).get(DetailViewModel.class);

        ingredientsListView = (RecyclerView) view.findViewById(R.id.ingredients_recycler_view);
        ingredientsListView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        ingredientsAdapter = new IngredientsAdapter(this.getActivity());
        ingredientsListView.setAdapter(ingredientsAdapter);

        sharedViewModel.getIngredientsList().observe(this, ingredients -> {
            Log.d(LOG_TAG, "Getting ingredients of size: " + ingredients.size() + " from " +
                    "sharedViewModel");
            ingredientsAdapter.setIngredients(ingredients);
            for (int i = 0; i < ingredients.size(); i++) {
                Log.d(LOG_TAG,
                        "This is the ingredient name: " + ingredients.get(i).getMeasure());
            }
        });

        stepsListView = (RecyclerView) view.findViewById(R.id.recycler_view_steps);
        stepsListView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        stepsAdapter = new StepsAdapter(this.getActivity(), this);
        stepsListView.setAdapter(stepsAdapter);

        sharedViewModel.getStepsList().observe(this, steps -> {
            Log.d(LOG_TAG, "Getting steps of size: " + steps.size() + " from sharedViewModel");
            stepsAdapter.setSteps(steps);
            for (int i = 0; i < steps.size(); i++) {
                Log.d(LOG_TAG,
                        "This is the step description: " + steps.get(i).getShortDescription());
            }
        });
        return view;
    }

    @Override
    public void onItemClickListener(int clickedStep) {
        sharedViewModel.setSelectedStep(clickedStep);

        if (getActivity().findViewById(R.id.info_container) != null) {
            mTwoPane = true;
            Fragment detailInfoFragment = new DetailInfoFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.info_container, detailInfoFragment);
            transaction.commit();
        } else {
            mTwoPane = false;
            Fragment detailInfoFragment = new DetailInfoFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.list_container, detailInfoFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
