package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.recipebook.util.DBHandler;

public class RecipeViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        // Get DBHandler instance
        DBHandler dbHandler = new DBHandler(this);
        // Get recipe info
        String[] recipeInfo = dbHandler.readCollection(getIntent().getExtras().getInt("recipeId"));

        // Get the recipe title and set it
        TextView titleTextView = findViewById(R.id.recipeViewTitle);
        titleTextView.setText(recipeInfo[0]);
        // Get the recipe description and set it
        TextView descriptionTextView = findViewById(R.id.recipeViewDescription);
        descriptionTextView.setText(recipeInfo[1]);

        findViewById(R.id.recipeViewReturnButton).setOnClickListener(click -> {
            finish();
        });
    }
}