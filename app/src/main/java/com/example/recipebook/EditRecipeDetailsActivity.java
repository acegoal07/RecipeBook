package com.example.recipebook;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipebook.util.DBHandler;
import com.example.recipebook.util.ToastHandler;

public class EditRecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe_details);

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

        // Add click listener to save button
        findViewById(R.id.editRecipeSubmitButton).setOnClickListener(click -> {
            if (titleInput.getText().toString().isEmpty()) {
                // Send Toast message
                new ToastHandler().showLongToast(this, "Please fill in the title field");
                return;
            }
            // Update recipe
            dbHandler.updateCollection(getIntent().getExtras().getInt("recipeId"), titleInput.getText().toString(), descriptionInput.getText().toString());
            // Send toast
            new ToastHandler().showLongToast(this, "Changes Saved");
            // Go back to main activity
            finish();
        });

        // Add click listener to cancel button
        findViewById(R.id.editRecipeCancelButton).setOnClickListener(click -> {
            // Go back to main activity
            finish();
        });

        // Add click listener to delete button
        findViewById(R.id.editRecipeDeleteButton).setOnClickListener(click -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Recipe")
                    .setMessage("Are you sure you want to delete this recipe?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Delete recipe
                        dbHandler.removeCollectionById(getIntent().getExtras().getInt("recipeId"));
                        // Send toast
                        new ToastHandler().showLongToast(this, "Recipe Deleted");
                        // Go back to main activity
                        finish();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        // Do nothing
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
    }
}