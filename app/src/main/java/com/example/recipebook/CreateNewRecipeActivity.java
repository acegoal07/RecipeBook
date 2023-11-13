package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.recipebook.util.DBHandler;
import com.example.recipebook.util.ToastHandler;

public class CreateNewRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_recipe);
        // Get Submit button
        Button submitBtn = findViewById(R.id.CreateNewRecipeSubmitButton);
        // Get Cancel button
        ImageButton cancelBtn = findViewById(R.id.createNewRecipeCancelButton);
        // Get title input
        EditText titleInput = findViewById(R.id.CreateNewRecipeTitleInput);
        // Get description input
        EditText descriptionInput = findViewById(R.id.CreateNewRecipeDesciptionInput);
        // Add click listener to submit button
        submitBtn.setOnClickListener(click -> {
            // Get DBHandler instance
            DBHandler dbHandler = new DBHandler(this);
            // Add new recipe
            dbHandler.addNewCollection(titleInput.getText().toString(), descriptionInput.getText().toString());
            // Send Toast message
            new ToastHandler().showLongToast(this, "Recipe added");
            // Go back to main activity
            finish();
        });
        // Add click listener to cancel button
        cancelBtn.setOnClickListener(click -> {
            // Go back to main activity
            finish();
        });
    }
}