package com.madfree.bakingapp.detail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madfree.bakingapp.R;
import com.madfree.bakingapp.data.Step;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    public static final String LOG_TAG = StepsAdapter.class.getSimpleName();

    private final Context mContext;

    private List<Step> mStepsList;

    public StepsAdapter(Context context) {
        this.mContext = context;

    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.steps_list_item, viewGroup, false);
        return new StepsAdapter.StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.StepsViewHolder stepsViewHolder, int position) {
        stepsViewHolder.stepsNameView.setText(mStepsList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mStepsList == null) {
            return 0;
        }
        return mStepsList.size();

    }

    public List<Step> getStepsList() {
        return mStepsList;
    }

    public void setSteps(List<Step> stepsList) {
        mStepsList = stepsList;
        Log.d(LOG_TAG, "number of recipes in adapter is: " + mStepsList.size());
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(Step  clickedStep);
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {
        final TextView stepsNameView;

        StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            stepsNameView = itemView.findViewById(R.id.stepsNameTextView);
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View view) {
//            int elementId = getAdapterPosition();
//            Step step = mStepsList.get(elementId);
//            mListener.onItemClickListener(step);
//        }
    }
}
