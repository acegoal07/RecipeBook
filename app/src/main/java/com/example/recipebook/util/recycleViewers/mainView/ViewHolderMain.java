package com.example.recipebook.util.recycleViewers.mainView;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.R;

public class ViewHolderMain extends RecyclerView.ViewHolder {
    private final Context context;
    TextView Title;
    TextView Description;
    ImageButton DeleteButton;

    public ViewHolderMain(@NonNull View view) {
        super(view);
        Title = view.findViewById(R.id.Title);
        Description = view.findViewById(R.id.Description);
        DeleteButton = view.findViewById(R.id.EditRecyclerRecipeButton);
        context = view.getContext();
    }
}
