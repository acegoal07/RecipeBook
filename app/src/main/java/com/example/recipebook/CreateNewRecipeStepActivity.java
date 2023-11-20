package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.recipebook.util.DBHandler;
import com.example.recipebook.util.ToastHandler;

public class CreateNewRecipeStepActivity extends AppCompatActivity {

    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_recipe_step);

        // Get input fields
        EditText stepInput = findViewById(R.id.createNewStepInput);

        // Get recipe id
        ID = getIntent().getExtras().getInt("recipeId");

        // Get save button
        findViewById(R.id.createNewStepSaveButton).setOnClickListener(click -> {
            if (stepInput.getText().toString().isEmpty()) {
                new ToastHandler().showLongToast(this, "Please enter a step");
                return;
            }
            if (stepInput.getText().toString().contains("!!") || stepInput.getText().toString().contains("::")) {
                new ToastHandler().showLongToast(this, "Step contains special characters which are not allowed");
                return;
            }

            DBHandler dbHandler = new DBHandler(this);

            String info = dbHandler.getRecipeByID(ID).getRecipe().getRawStepsString();
            if (info == null || info.isEmpty()) {
                info = "0::"+stepInput.getText();
            } else {
                StringBuilder stringBuilder = new StringBuilder(info);
                stringBuilder.append("!!0::"+stepInput.getText());
                info = stringBuilder.toString();
            }

            dbHandler.addNewRecipeStep(ID, info);
            finish();
        });

        // Get cancel button
        findViewById(R.id.createNewStepCancelButton).setOnClickListener(click -> {
            finish();
        });
    }
}