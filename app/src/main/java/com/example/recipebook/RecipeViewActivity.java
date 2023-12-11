package com.example.recipebook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.util.classes.RecipeDetails;
import com.example.recipebook.util.enums.RecipeStepTypeEnum;
import com.example.recipebook.util.classes.StepInfo;
import com.example.recipebook.util.handlers.DBHandler;
import com.example.recipebook.util.handlers.ToastHandler;
import com.example.recipebook.util.recycleViewers.recipeView.RecipeAdapterView;

import java.util.Objects;

public class RecipeViewActivity extends AppCompatActivity {
    private int ID;
    private RecipeDetails recipeDetails;
    private final DBHandler DBHandler = new DBHandler(this);
    private final ToastHandler ToastHandler = new ToastHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        // Set Id
        ID = Objects.requireNonNull(getIntent().getExtras()).getInt("recipeId");

        // Get recipe info
        recipeDetails = DBHandler.getRecipeByID(ID);

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
        if (recipeDetails.getRecipe().getRawStepsString() != null) {
            refreshView();
        }

        // Get create new step button
        findViewById(R.id.recipeViewAddNewStepButton).setOnClickListener(click -> {
            Intent intent = new Intent(this, NewRecipeStepActivity.class);
            intent.putExtra("recipeId", ID);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        // Add listener to edit steps button
        findViewById(R.id.recipeViewEditStepsButton).setOnClickListener(click -> {
            if (recipeDetails.getRecipe().getStepCount() == 0) {
                ToastHandler.showLongToast("No steps to edit");
                return;
            }
            Intent intent = new Intent(this, EditRecipeStepActivity.class);
            intent.putExtra("recipeId", ID);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        // Add listener to edit recipe button
        findViewById(R.id.recipeViewEditRecipeButton).setOnClickListener(click -> {
            Intent intent = new Intent(this, EditRecipeDetailsActivity.class);
            intent.putExtra("recipeId", ID);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        // Add click listener to Share button
        findViewById(R.id.recipeViewShareButton).setOnClickListener(click -> {
            if (recipeDetails.getRecipe().getStepCount() == 0) {
                ToastHandler.showLongToast("No steps to share");
                return;
            }

            // Create the intent
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, recipeDetails.getTitle());

            // Build the steps string
            StringBuilder steps = new StringBuilder(recipeDetails.getTitle());
            if (recipeDetails.getDescription() != null && !recipeDetails.getDescription().isEmpty()) {
                steps.append(" - ")
                        .append(recipeDetails.getDescription())
                        .append("\n\n");
            }
            int counter = 1;
            for (StepInfo step : recipeDetails.getRecipe().getSteps()) {
                if (step.getStepType() == RecipeStepTypeEnum.NORMAL) {
                    steps.append("Step ")
                            .append(counter)
                            .append(" : ")
                            .append(step.getStep());
                } else if (step.getStepType() == RecipeStepTypeEnum.COOK) {
                    steps.append("Step ")
                            .append(counter)
                            .append(" : ")
                            .append(step.getCookStepInfo().getFullDisplay());
                }
                if (counter - 1 < recipeDetails.getRecipe().getStepCount()) {
                    steps.append("\n\n");
                    counter++;
                }
            }

            // Add the steps to the intent and start the activity
            shareIntent.putExtra(Intent.EXTRA_TEXT, steps.toString());
            Intent.createChooser(shareIntent, "Share via");
            startActivity(shareIntent);
        });

        // Add click listener to Cancel button
        findViewById(R.id.recipeViewReturnButton).setOnClickListener(click -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        recipeDetails = DBHandler.getRecipeByID(ID);
        if (recipeDetails == null) {
            finish();
        } else {
            refreshView();
        }
    }

    private void refreshView() {
        RecyclerView recyclerView = findViewById(R.id.recipeViewRecipeRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipeAdapterView(getApplicationContext(), DBHandler.readRecipeStepInfo(ID)));

        // Get the recipe title and set it
        TextView titleTextView = findViewById(R.id.recipeViewTitle);
        titleTextView.setText(recipeDetails.getTitle());

        // Get the recipe description and set it
        TextView descriptionTextView = findViewById(R.id.recipeViewDescription);
        if (!recipeDetails.getDescription().isEmpty()) {
            descriptionTextView.setText(recipeDetails.getDescription());
            descriptionTextView.setVisibility(TextView.VISIBLE);
        }
    }
}