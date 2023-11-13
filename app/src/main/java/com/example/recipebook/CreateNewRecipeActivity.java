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
        EditText titleInput = findViewById(R.id.CreateNewRecipeTitleInput);
        // Get description input
        EditText descriptionInput = findViewById(R.id.CreateNewRecipeDesciptionInput);

        // Add click listener to create button
        findViewById(R.id.CreateNewRecipeSubmitButton).setOnClickListener(click -> {
            if (titleInput.getText().toString().isEmpty() || descriptionInput.getText().toString().isEmpty()) {
                // Send Toast message
                new ToastHandler().showLongToast(this, "Please fill in all fields");
                return;
            }
            // Get DBHandler instance
            DBHandler dbHandler = new DBHandler(this);
            // Add new recipe
            dbHandler.addNewCollection(titleInput.getText().toString(), descriptionInput.getText().toString());
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