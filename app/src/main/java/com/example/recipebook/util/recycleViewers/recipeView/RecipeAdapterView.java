package com.example.recipebook.util.recycleViewers.recipeView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.R;
import com.example.recipebook.util.classes.StepInfo;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapterView extends RecyclerView.Adapter<ViewHolderView> {
    private Context context;
    private static final String TAG = "RecipeAdaptorView";
    public List<StepInfo> RecipeSteps;

    /**
     * Constructor for RecipeAdapter
     *
     * @param applicationContext The application context
     * @param collections        An ArrayList of RecipeInfo objects
     */
    public RecipeAdapterView(Context applicationContext, ArrayList<StepInfo> collections) {
        this.context = applicationContext;
        RecipeSteps = collections;
    }

    /**
     * Creates a new ViewHolder object whenever the RecyclerView needs a new one.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return ViewHolder
     */
    @NonNull
    @Override
    public ViewHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderView(LayoutInflater.from(context).inflate(R.layout.recipe_recycler_step_view, parent, false));
    }

    /**
     * Sets the data for the recycler view
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderView holder, @SuppressLint("RecyclerView") int position) {
        if (RecipeSteps == null) {
            return;
        }
        String stepCounterText = "Step " + (position + 1) + ": ";
        holder.StepCounter.setText(stepCounterText);
        switch (RecipeSteps.get(position).getStepType()) {
            case NORMAL:
                holder.StepContent.setText(RecipeSteps.get(position).getStep());
                break;
            case COOK:
                holder.StepContent.setText(RecipeSteps.get(position).getCookStepInfo().getFullDisplay());
                break;
        }
    }

    /**
     * Returns the number of items in the recycler view
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        if (RecipeSteps == null) {
            return 0;
        }
        return RecipeSteps.size();
    }
}
