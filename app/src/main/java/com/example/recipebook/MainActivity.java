package com.example.recipebook;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.util.handlers.DBHandler;
import com.example.recipebook.util.recycleViewers.mainView.RecipeAdapterMain;

public class MainActivity extends AppCompatActivity {

    private final DBHandler dbHandler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Refresh view
        refreshView();
        // Add listener to create new recipe button
        findViewById(R.id.mainNewRecipeButton).setOnClickListener(click -> {
            Intent Intent = new Intent(this, CreateNewRecipeActivity.class);
            startActivity(Intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshView();
    }

    private void refreshView() {
        RecyclerView recyclerView = findViewById(R.id.mainRecipeRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipeAdapterMain(getApplicationContext(), dbHandler.getRecipes()));
    }
}