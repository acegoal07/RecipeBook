package com.example.recipebook.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private static final String TAG = "RecipeAdaptor";
    public List<RecipeInfo> RecipeInfo;

    /**
     * Constructor for RecipeAdapter
     * @param applicationContext
     * @param collections
     */
    public RecipeAdapter(Context applicationContext, ArrayList<RecipeInfo> collections) {
        this.context = applicationContext;
        RecipeInfo = collections;
    }

    /**
     * Creates a new ViewHolder object whenever the RecyclerView needs a new one.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_recycler_view,parent,false));
    }

    /**
     * Sets the data for the recycler view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Set data for recipe collection
        holder.Title.setText(RecipeInfo.get(position).getTitle());
        holder.Description.setText(RecipeInfo.get(position).getDescription());
        // Set click listener for delete button
        holder.DeleteButton.setOnClickListener(v -> {
            DBHandler db = new DBHandler(v.getContext());
            db.removeCollectionById(RecipeInfo.get(position).getId());
            RecyclerView recyclerView = holder.DeleteButton.getRootView().findViewById(R.id.RecipeRecycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
            recyclerView.setAdapter(new RecipeAdapter(context.getApplicationContext(),db.readCollections()));
            new ToastHandler().showLongToast(v.getContext(), "Recipe Deleted");
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
