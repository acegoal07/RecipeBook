package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.recipebook.util.DBHandler;
import com.example.recipebook.util.StepInfo;
import com.example.recipebook.util.recycleViewers.mainView.RecipeAdapterMain;
import com.example.recipebook.util.recycleViewers.recipeView.RecipeAdapterView;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeViewActivity extends AppCompatActivity {

    private DBHandler dbHandler = new DBHandler(this);
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        // Set Id
        id = getIntent().getExtras().getInt("recipeId");
        // Get DBHandler instance
        DBHandler dbHandler = new DBHandler(this);
        // Get recipe info
        String[] recipeInfo = dbHandler.readCollection(id);

        // Get the recipe title and set it
        TextView titleTextView = findViewById(R.id.recipeViewTitle);
        titleTextView.setText(recipeInfo[0]);
        // Get the recipe description and set it
        TextView descriptionTextView = findViewById(R.id.recipeViewDescription);
        if (recipeInfo[1].isEmpty()) {
            descriptionTextView.setVisibility(TextView.GONE);
        } else {
            descriptionTextView.setText(recipeInfo[1]);
        }

        // Get Recycler view
        RecyclerView recyclerView = findViewById(R.id.recipeViewRecipeRecycler);
        // Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipeAdapterView(getApplicationContext(), dbHandler.readCollectionsStepInfo(id)));

        // Get create new step button
        findViewById(R.id.recipeViewAddNewItemButton).setOnClickListener(click -> {
            Intent intent = new Intent(this, CreateNewStepActivity.class);
            intent.putExtra("recipeId", id);
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
        RecyclerView recyclerView = findViewById(R.id.recipeViewRecipeRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipeAdapterView(getApplicationContext(), dbHandler.readCollectionsStepInfo(getIntent().getExtras().getInt("recipeId"))));
    }
}