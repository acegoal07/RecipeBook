package com.example.recipebook;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipebook.util.DBHandler;
import com.example.recipebook.util.RecipeDetails;
import com.example.recipebook.util.ToastHandler;

public class EditRecipeDetailsActivity extends AppCompatActivity {

    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe_details);

        // Get DBHandler instance
        DBHandler dbHandler = new DBHandler(this);

        // Get recipe info
        RecipeDetails recipeDetails = dbHandler.getRecipeByID(getIntent().getExtras().getInt("recipeId"));

        // Get recipe id
        ID = getIntent().getExtras().getInt("recipeId");

        // Get temp title and description
        String tempTitle = recipeDetails.getTitle();
        String tempDescription = recipeDetails.getDescription();

        // Get title input
        EditText titleInput = findViewById(R.id.editRecipeDetailsTitleInput);
        titleInput.setText(tempTitle);

        // Get description input
        EditText descriptionInput = findViewById(R.id.editRecipeDetailsDesciptionInput);
        descriptionInput.setText(tempDescription);

        // Add click listener to save button
        findViewById(R.id.editRecipeDetailsSaveButton).setOnClickListener(click -> {
            // Check if any changes were made
            if (titleInput.getText().toString().equals(tempTitle) && descriptionInput.getText().toString().equals(tempDescription)) {
                // Send Toast message
                new ToastHandler(this).showLongToast("No changes made");
                return;
            } else {
                // Check if title was changed
                if (!titleInput.getText().toString().equals(tempTitle)) {
                    // Make sure title is not empty
                    if (titleInput.getText().toString().isEmpty()) {
                        // Send Toast message
                        new ToastHandler(this).showLongToast("Please fill in the title field");
                        return;
                    }
                    // Check length of title
                    if (titleInput.getText().toString().length() > 24) {
                        // Send Toast message
                        new ToastHandler(this).showLongToast("Title must be 24 characters long or less");
                        return;
                    }
                    // Check if there are any special characters and display a toast if there are
                    if (titleInput.getText().toString().matches("[^A-Za-z0-9]")) {
                        // Send Toast message
                        new ToastHandler(this).showLongToast("Title contains special characters which are not allowed");
                        return;
                    }
                    // Update database
                    dbHandler.updateRecipeTitle(ID, titleInput.getText().toString());
                }
                // Check if description was changed
                if (!descriptionInput.getText().toString().equals(tempDescription)) {
                    // Check length of description
                    if (descriptionInput.getText().toString().length() > 50) {
                        // Send Toast message
                        new ToastHandler(this).showLongToast("Description must be 50 characters long or less");
                        return;
                    }
                    // Check if there are any special characters and display a toast if there are
                    if (descriptionInput.getText().toString().matches("[^A-Za-z0-9]")) {
                        // Send Toast message
                        new ToastHandler(this).showLongToast("Description contains special characters which are not allowed");
                        return;
                    }
                    // Update database
                    dbHandler.updateRecipeDescription(ID, descriptionInput.getText().toString());
                }
            }
            // Send toast
            new ToastHandler(this).showLongToast("Changes Saved");
            // Go back to main activity
            finish();
        });

        // Add click listener to cancel button
        findViewById(R.id.editRecipeDetailsCancelButton).setOnClickListener(click -> {
            // Go back to main activity
            finish();
        });

        // Add click listener to delete button
        findViewById(R.id.editRecipeDetailsDeleteButton).setOnClickListener(click -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Recipe")
                    .setMessage("Are you sure you want to delete this recipe?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Delete recipe
                        dbHandler.removeRecipeById(ID);
                        // Send toast
                        new ToastHandler(this).showLongToast("Recipe Deleted");
                        // Go back to main activity
                        finish();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        return;
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
    }
}