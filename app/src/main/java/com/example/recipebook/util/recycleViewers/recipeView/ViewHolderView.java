package com.example.recipebook.util.recycleViewers.recipeView;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.R;

public class ViewHolderView extends RecyclerView.ViewHolder {
    Context context;
    TextView StepCounter;
    TextView StepContent;

    public ViewHolderView(@NonNull View view) {
        super(view);
        // Set context
        context = view.getContext();
        // Set normal step view
        StepCounter = view.findViewById(R.id.recipeRecyclerViewStepCounter);
        StepContent = view.findViewById(R.id.recipeRecyclerViewStepInfo);
    }
}
