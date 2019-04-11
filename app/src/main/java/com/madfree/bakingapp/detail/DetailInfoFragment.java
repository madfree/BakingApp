package com.madfree.bakingapp.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madfree.bakingapp.R;
import com.madfree.bakingapp.data.Step;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class DetailInfoFragment extends Fragment {

    public static final String LOG_TAG = DetailInfoFragment.class.getSimpleName();

    private DetailViewModel sharedViewModel;
    private TextView mStepDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        Log.d(LOG_TAG, "Starting DetaiListFragement");

        sharedViewModel = ViewModelProviders.of(getActivity()).get(DetailViewModel.class);

        mStepDescription = view.findViewById(R.id.step_description_txt);

        sharedViewModel.getStepInfo().observe(this, new Observer<Step>() {
            @Override
            public void onChanged(Step step) {
                Log.d(LOG_TAG, "Getting stepInfo of stepId: " + step.getStepId() + " from sharedViewModel");
                mStepDescription.setText(step.getDescription());
            }
        });
        return view;
    }
}
