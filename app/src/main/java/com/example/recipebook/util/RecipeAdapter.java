package com.example.recipebook.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.EditRecipeDetailsActivity;
import com.example.recipebook.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private static final String TAG = "RecipeAdaptor";
    public List<RecipeInfo> RecipeInfo;

    /**
     * Constructor for RecipeAdapter
     * @param applicationContext The application context
     * @param collections An ArrayList of RecipeInfo objects
     */
    public RecipeAdapter(Context applicationContext, ArrayList<RecipeInfo> collections) {
        this.context = applicationContext;
        RecipeInfo = collections;
    }

    /**
     * Creates a new ViewHolder object whenever the RecyclerView needs a new one.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_recycler_view,parent,false));
    }

    /**
     * Sets the data for the recycler view
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Set data for recipe collection
        holder.Title.setText(RecipeInfo.get(position).getTitle());
        if (RecipeInfo.get(position).getDescription().isEmpty()) {
            holder.Description.setVisibility(RecyclerView.GONE);
        } else {
            holder.Description.setText(RecipeInfo.get(position).getDescription());
        }
        // Set click listener for delete button
        holder.DeleteButton.setOnClickListener(v -> {
            Intent Intent = new Intent(v.getContext(), EditRecipeDetailsActivity.class);
            Intent.putExtra("recipeId", RecipeInfo.get(position).getId());
            Intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(Intent);
        });
        // Set click listener for recipe item
        holder.itemView.setOnClickListener(v -> Log.d(TAG, "onClick: clicked on: "+ RecipeInfo.get(position).returnAllInfo()));
    }

    /**
     * Returns the number of items in the recycler view
     * @return int
     */
    @Override
    public int getItemCount() {
        return RecipeInfo.size();
    }
}