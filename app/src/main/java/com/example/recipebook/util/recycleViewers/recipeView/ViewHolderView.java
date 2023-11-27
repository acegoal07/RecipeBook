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
    LinearLayout NormalStepView;
    TextView NormalStepCounter;
    TextView NormalStep;

    LinearLayout CookStepView;
    TextView CookStepCounter;
    TextView CookTime;
    TextView CookTemperature;

    public ViewHolderView(@NonNull View view) {
        super(view);
        // Set context
        context = view.getContext();
        // Set normal step view
        NormalStepView = view.findViewById(R.id.recipeRecyclerViewNormalView);
        NormalStepCounter = view.findViewById(R.id.recipeRecyclerViewNormalStepCounter);
        NormalStep = view.findViewById(R.id.recipeRecyclerViewNormalStepInfo);
        // Set cook step view
        CookStepView = view.findViewById(R.id.recipeRecyclerViewCookView);
        CookStepCounter = view.findViewById(R.id.recipeRecyclerViewCookStepCounter);
        CookTime = view.findViewById(R.id.recipeRecyclerViewCookTime);
        CookTemperature = view.findViewById(R.id.recipeRecyclerViewCookTempreture);
    }
}
