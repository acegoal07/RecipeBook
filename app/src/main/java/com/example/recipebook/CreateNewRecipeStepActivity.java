package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.recipebook.util.DBHandler;
import com.example.recipebook.util.ToastHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateNewRecipeStepActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_recipe_step);

        // Get recipe id and store it
        ID = getIntent().getExtras().getInt("recipeId");
        // Get input fields
        EditText stepInput = findViewById(R.id.createNewRecipeStepInput);
        // Get Spinner
        Spinner stepSpinner = findViewById(R.id.createNewRecipeStepSpinner);

        // Set Spinner
        stepSpinner.setOnItemSelectedListener(this);
        ArrayAdapter stepSpinnerArrayAdapter = new ArrayAdapter(this, R.layout.spinner, new String[]{"Normal"});
        stepSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        stepSpinner.setAdapter(stepSpinnerArrayAdapter);

        // Get save button
        findViewById(R.id.createNewRecipeStepSaveButton).setOnClickListener(click -> {
            // Check if the step input is empty and display a toast if it is
            if (stepInput.getText().toString().isEmpty()) {
                new ToastHandler(this).showLongToast("Please enter a step");
                return;
            }
            // Create a pattern check for any special characters
            Pattern regex = Pattern.compile("[^A-Za-z0-9]");
            Matcher matcher = regex.matcher(stepInput.getText().toString());
            // Check if there are any special characters and display a toast if there are
            if (matcher.find()) {
                new ToastHandler(this).showLongToast("Step contains special characters which are not allowed");
                return;
            }

            // Get DBHandler
            DBHandler dbHandler = new DBHandler(this);
            // Get raw step info
            String info = dbHandler.getRecipeByID(ID).getRecipe().getRawStepsString();

            if (info == null || info.isEmpty()) {
                info = stepSpinner.getSelectedItemPosition()+"::"+stepInput.getText();
            } else {
                StringBuilder stringBuilder = new StringBuilder(info);
                stringBuilder.append("!!"+stepSpinner.getSelectedItemPosition()+"::"+stepInput.getText());
                info = stringBuilder.toString();
            }
            // Add new step
            dbHandler.addNewRecipeStep(ID, info);
            finish();
        });

        // Get cancel button
        findViewById(R.id.createNewStepCancelButton).setOnClickListener(click -> {
            finish();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}