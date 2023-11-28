package com.example.recipebook.util.recycleViewers.mainView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.EditRecipeDetailsActivity;
import com.example.recipebook.R;
import com.example.recipebook.RecipeViewActivity;
import com.example.recipebook.util.classes.RecipeDetails;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapterMain extends RecyclerView.Adapter<ViewHolderMain> {
    private Context context;
    private static final String TAG = "RecipeAdaptorMain";
    public List<RecipeDetails> RecipeDetails;

    /**
     * Constructor for RecipeAdapter
     * @param applicationContext The application context
     * @param collections An ArrayList of RecipeInfo objects
     */
    public RecipeAdapterMain(Context applicationContext, ArrayList<RecipeDetails> collections) {
        this.context = applicationContext;
        this.RecipeDetails = collections;
    }

    /**
     * Creates a new ViewHolder object whenever the RecyclerView needs a new one.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return ViewHolder
     */
    @NonNull
    @Override
    public ViewHolderMain onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderMain(LayoutInflater.from(context).inflate(R.layout.recipe_recycler_main, parent, false));
    }

    /**
     * Sets the data for the recycler view
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolderMain holder, @SuppressLint("RecyclerView") int position) {
        // Set data for recipe collection
        RecipeDetails recipeDetails = RecipeDetails.get(position);
        holder.Title.setText(recipeDetails.getTitle());
        if (!recipeDetails.getDescription().isEmpty()) {
            holder.Description.setVisibility(RecyclerView.VISIBLE);
            holder.Description.setText(recipeDetails.getDescription());
        }
        // Set click listener for delete button
        holder.DeleteButton.setOnClickListener(v -> {
            Intent Intent = new Intent(v.getContext(), EditRecipeDetailsActivity.class);
            Intent.putExtra("recipeId", recipeDetails.getId());
            Intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(Intent);
        });
        // Set click listener for recipe item
        holder.itemView.setOnClickListener(v -> {
            Intent Intent = new Intent(v.getContext(), RecipeViewActivity.class);
            Intent.putExtra("recipeId", recipeDetails.getId());
            Intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(Intent);
        });
    }

    /**
     * Returns the number of items in the recycler view
     * @return int The number of items in the recycler view
     */
    @Override
    public int getItemCount() {
        return RecipeDetails.size();
    }
}
