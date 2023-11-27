package com.example.recipebook.util.recycleViewers.mainView;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.R;

public class ViewHolderMain extends RecyclerView.ViewHolder {
    Context context;
    TextView Title;
    TextView Description;
    ImageButton DeleteButton;

    public ViewHolderMain(@NonNull View view) {
        super(view);
        context = view.getContext();
        Title = view.findViewById(R.id.recipeRecyclerMainTitle);
        Description = view.findViewById(R.id.recipeRecyclerMainDescription);
        DeleteButton = view.findViewById(R.id.recipeRecyclerMainEditButton);
    }
}
