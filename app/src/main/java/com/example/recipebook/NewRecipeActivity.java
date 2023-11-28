package com.example.recipebook;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipebook.util.handlers.DBHandler;
import com.example.recipebook.util.handlers.ToastHandler;

public class NewRecipeActivity extends AppCompatActivity {

    private final DBHandler DBHandler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        // Get title input
        EditText titleInput = findViewById(R.id.newRecipeTitleInput);
        // Get description input
        EditText descriptionInput = findViewById(R.id.newRecipeDesciptionInput);

        // Add click listener to create button
        findViewById(R.id.newRecipeCreateButton).setOnClickListener(click -> {
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

            // Check length of description
            if (descriptionInput.getText().toString().length() > 50) {
                // Send Toast message
                new ToastHandler(this).showLongToast("Description must be 50 characters long or less");
                return;
            }

            // Check if there are any special characters and display a toast if there are
            if (titleInput.getText().toString().matches("[^A-Za-z0-9]")) {
                // Send Toast message
                new ToastHandler(this).showLongToast("Title contains special characters which are not allowed");
                return;
            }
            if (descriptionInput.getText().toString().matches("[^A-Za-z0-9]")) {
                // Send Toast message
                new ToastHandler(this).showLongToast("Description contains special characters which are not allowed");
                return;
            }

            // Add new recipe
            DBHandler.addNewRecipe(titleInput.getText().toString(), descriptionInput.getText().toString());

            // Send Toast message
            new ToastHandler(this).showLongToast("Recipe Created");

            // Go back to main activity
            finish();
        });

        // Add click listener to cancel button
        findViewById(R.id.newRecipeCancelButton).setOnClickListener(click -> {
            // Go back to main activity
            finish();
        });
    }
}