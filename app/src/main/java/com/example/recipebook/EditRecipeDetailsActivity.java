package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.recipebook.util.DBHandler;

public class EditRecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe_details);
        // Get submit button
        Button submitBtn = findViewById(R.id.editRecipeSubmitButton);
        // Get delete button
        Button deleteBtn = findViewById(R.id.editRecipeDeleteButton);
        // Get cancel button
        ImageButton cancelBtn = findViewById(R.id.editRecipeCancelButton);
        // Get DBHandler instance
        DBHandler dbHandler = new DBHandler(this);
        // Get recipe info
        String[] recipeInfo = dbHandler.readCollection(getIntent().getExtras().getInt("recipeId"));

        // Get title input
        EditText titleInput = findViewById(R.id.editRecipeTitleInput);
        titleInput.setText(recipeInfo[0]);
        // Get description input
        EditText descriptionInput = findViewById(R.id.editRecipeDesciptionInput);
        descriptionInput.setText(recipeInfo[1]);

        // Add click listener to submit button
        submitBtn.setOnClickListener(click -> {
            // Update recipe
            dbHandler.updateCollection(getIntent().getExtras().getInt("recipeId"), titleInput.getText().toString(), descriptionInput.getText().toString());
            // Go back to main activity
            finish();
        });
        // Add click listener to cancel button
        cancelBtn.setOnClickListener(click -> {
            // Go back to main activity
            finish();
        });
        // Add click listener to delete button
        deleteBtn.setOnClickListener(click -> {
            // Delete recipe
            dbHandler.removeCollectionById(getIntent().getExtras().getInt("recipeId"));
            // Go back to main activity
            finish();
        });
    }
}