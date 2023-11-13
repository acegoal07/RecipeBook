package com.example.recipebook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.util.DBHandler;
import com.example.recipebook.util.RecipeAdapter;

public class MainActivity extends AppCompatActivity {

    private DBHandler dbHandler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get Add new recipe button
        Button button = findViewById(R.id.newRecipeButton);
        // Get Recycler view
        RecyclerView recyclerView = findViewById(R.id.RecipeRecycler);
        // Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipeAdapter(getApplicationContext(),dbHandler.readCollections()));

        button.setOnClickListener(click -> {
            Intent Intent = new Intent(this, CreateNewRecipeActivity.class);
            startActivity(Intent);

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.RecipeRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipeAdapter(getApplicationContext(),dbHandler.readCollections()));
    }
}