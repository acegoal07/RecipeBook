package com.example.recipebook.util.recycleViewers.recipeView;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.R;

public class ViewHolderView extends RecyclerView.ViewHolder {
    private final Context context;
    LinearLayout NormalStep;
    TextView StepCounter;
    TextView Step;

    public ViewHolderView(@NonNull View view) {
        super(view);
        NormalStep = view.findViewById(R.id.recipeRecyclerViewNormalView);
        StepCounter = view.findViewById(R.id.recipeRecyclerViewStepCounter);
        Step = view.findViewById(R.id.recipeRecyclerViewStepInfo);
        context = view.getContext();
    }
}
