package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.recipebook.util.DBHandler;
import com.example.recipebook.util.RecipeDetails;
import com.example.recipebook.util.ToastHandler;
import com.example.recipebook.util.recycleViewers.recipeView.RecipeAdapterView;

public class RecipeViewActivity extends AppCompatActivity {

    private int ID;
    private final DBHandler dbHandler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        // Set Id
        ID = getIntent().getExtras().getInt("recipeId");

        // Get recipe info
        RecipeDetails recipeDetails = dbHandler.getRecipeByID(ID);

        // Get the recipe title and set it
        TextView titleTextView = findViewById(R.id.recipeViewTitle);
        titleTextView.setText(recipeDetails.getTitle());

        // Get the recipe description and set it
        TextView descriptionTextView = findViewById(R.id.recipeViewDescription);
        if (!recipeDetails.getDescription().isEmpty()) {
            descriptionTextView.setVisibility(TextView.VISIBLE);
            descriptionTextView.setText(recipeDetails.getDescription());
        }

        // Check if there is recipe data and if not, return else refresh view
        if (recipeDetails.getRecipe() == null) {
            return;
        } else {
            refreshView();
        }

        // Get create new step button
        findViewById(R.id.recipeViewAddNewStepButton).setOnClickListener(click -> {
            Intent intent = new Intent(this, CreateNewRecipeStepActivity.class);
            intent.putExtra("recipeId", ID);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        // Add listener to edit steps button
        findViewById(R.id.recipeViewEditStepsButton).setOnClickListener(click -> {
            if (dbHandler.getRecipeByID(ID).getRecipe().getStepCount() == 0) {
                new ToastHandler(this).showLongToast("No steps to edit");
                return;
            }
            Intent intent = new Intent(this, EditRecipeStepActivity.class);
            intent.putExtra("recipeId", ID);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        // Add click listener to Cancel button
        findViewById(R.id.recipeViewReturnButton).setOnClickListener(click -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshView();
    }

    private void refreshView() {
        RecyclerView recyclerView = findViewById(R.id.recipeViewRecipeRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipeAdapterView(getApplicationContext(), dbHandler.readRecipeStepInfo(ID)));
    }
}