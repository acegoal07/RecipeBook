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
    LinearLayout NormalStepView;
    TextView NormalStepCounter;
    TextView NormalStep;

    public ViewHolderView(@NonNull View view) {
        super(view);
        NormalStepView = view.findViewById(R.id.recipeRecyclerViewNormalView);
        NormalStepCounter = view.findViewById(R.id.recipeRecyclerViewStepCounter);
        NormalStep = view.findViewById(R.id.recipeRecyclerViewStepInfo);
        context = view.getContext();
    }
}
