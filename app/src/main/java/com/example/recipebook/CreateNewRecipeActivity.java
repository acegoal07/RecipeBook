package com.example.recipebook;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipebook.util.DBHandler;
import com.example.recipebook.util.ToastHandler;

public class CreateNewRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_recipe);

        // Get title input
        EditText titleInput = findViewById(R.id.createNewRecipeTitleInput);
        // Get description input
        EditText descriptionInput = findViewById(R.id.createNewRecipeDesciptionInput);

        // Add click listener to create button
        findViewById(R.id.createNewRecipeCreateButton).setOnClickListener(click -> {
            // Make sure title is not empty
            if (titleInput.getText().toString().isEmpty()) {
                // Send Toast message
                new ToastHandler().showLongToast(this, "Please fill in the title field");
                return;
            }
            // Check length of title
            if (titleInput.getText().toString().length() > 24) {
                // Send Toast message
                new ToastHandler().showLongToast(this, "Title must be 24 characters long or less");
                return;
            }
            // Check length of description
            if (descriptionInput.getText().toString().length() > 50) {
                // Send Toast message
                new ToastHandler().showLongToast(this, "Description must be 50 characters long or less");
                return;
            }
            // Get DBHandler instance
            DBHandler dbHandler = new DBHandler(this);
            // Add new recipe
            dbHandler.addNewRecipe(titleInput.getText().toString(), descriptionInput.getText().toString());
            // Send Toast message
            new ToastHandler().showLongToast(this, "Recipe Created");
            // Go back to main activity
            finish();
        });

        // Add click listener to cancel button
        findViewById(R.id.createNewRecipeCancelButton).setOnClickListener(click -> {
            // Go back to main activity
            finish();
        });
    }
}