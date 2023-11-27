package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.recipebook.util.handlers.DBHandler;
import com.example.recipebook.util.handlers.ToastHandler;

import java.util.Objects;

public class CreateNewRecipeStepActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int ID;
    private final DBHandler DBHandler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_recipe_step);

        // Get recipe id and store it
        ID = Objects.requireNonNull(getIntent().getExtras()).getInt("recipeId");

        // Get inputs/outputs
        EditText stepInput = findViewById(R.id.createNewRecipeStepNormalInput);
        Spinner stepTypeSpinner = findViewById(R.id.createNewRecipeStepSpinner);
        Spinner symbolSpinner = findViewById(R.id.createNewRecipeStepCookSymbolSpinner);
        EditText cookTimeHourInput = findViewById(R.id.createNewRecipeCookHourInput);
        EditText cookTimeMinuteInput = findViewById(R.id.createNewRecipeCookMinuteInput);
        EditText cookTemperatureInput = findViewById(R.id.createNewRecipeStepCookTemperatureInput);

        // Set Spinners
        stepTypeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> stepSpinnerArrayAdapter = ArrayAdapter.createFromResource(this, R.array.recipe_step_types, R.layout.spinner);
        stepSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        stepTypeSpinner.setAdapter(stepSpinnerArrayAdapter);

        ArrayAdapter<CharSequence> symbolSpinnerArrayAdapter = ArrayAdapter.createFromResource(this, R.array.temperature_symbols, R.layout.spinner);
        symbolSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        symbolSpinner.setAdapter(symbolSpinnerArrayAdapter);

        // Get save button
        findViewById(R.id.createNewRecipeStepSaveButton).setOnClickListener(click -> {
            // Get raw step info
            String info = DBHandler.getRecipeByID(ID).getRecipe().getRawStepsString();
            // Create a string builder
            StringBuilder stepString = new StringBuilder(info != null ? info : "");
            // Type specific checks
            if (stepTypeSpinner.getSelectedItemPosition() == 0) {
                // Check if the step input is empty and display a toast if it is
                if (stepInput.getText().toString().isEmpty()) {
                    new ToastHandler(this).showLongToast("Please enter a step");
                    return;
                }
                // Check if there are any special characters and display a toast if there are
                if (stepInput.getText().toString().matches("[^A-Za-z0-9]")) {
                    new ToastHandler(this).showLongToast("Step contains special characters which are not allowed");
                    return;
                }
                // Create the string for the string builder
                if (stepString.toString().isEmpty()) {
                    stepString
                            .append(stepTypeSpinner.getSelectedItemPosition())
                            .append("::")
                            .append(stepInput.getText());
                } else {
                    stepString
                            .append("!!")
                            .append(stepTypeSpinner.getSelectedItemPosition())
                            .append("::")
                            .append(stepInput.getText());
                }
            } else if (stepTypeSpinner.getSelectedItemPosition() == 1) {
                // Check if the cook time hour and minute inputs are empty and display a toast if they are
                if (cookTimeHourInput.getText().toString().isEmpty() && cookTimeMinuteInput.getText().toString().isEmpty()) {
                    new ToastHandler(this).showLongToast("Please enter a cook time");
                    return;
                }
                // Checks if the cook temperature input is empty and display a toast if it is
                if (cookTemperatureInput.getText().toString().isEmpty()) {
                    new ToastHandler(this).showLongToast("Please enter a cook temperature");
                    return;
                }
                // Checks hour and minute time inputs are valid
                if (Integer.parseInt(cookTimeHourInput.getText().toString().isEmpty() ? "0" : cookTimeHourInput.getText().toString()) > 23 || Integer.parseInt(cookTimeMinuteInput.getText().toString().isEmpty() ? "0" : cookTimeMinuteInput.getText().toString()) > 59) {
                    new ToastHandler(this).showLongToast("Please enter a valid cook time");
                    return;
                }
                // Create the string for the string builder
                if (stepString.toString().isEmpty()) {
                    stepString
                            .append(stepTypeSpinner.getSelectedItemPosition())
                            .append("::")
                            .append(cookTimeHourInput.getText().toString().isEmpty() ? "0" : cookTimeHourInput.getText())
                            .append("%%")
                            .append(cookTimeMinuteInput.getText().toString().isEmpty() ? "0" : cookTimeMinuteInput.getText())
                            .append("%%")
                            .append(cookTemperatureInput.getText())
                            .append("%%")
                            .append(symbolSpinner.getSelectedItem());
                } else {
                    stepString
                            .append("!!")
                            .append(stepTypeSpinner.getSelectedItemPosition())
                            .append("::")
                            .append(cookTimeHourInput.getText().toString().isEmpty() ? "0" : cookTimeHourInput.getText())
                            .append("%%")
                            .append(cookTimeMinuteInput.getText().toString().isEmpty() ? "0" : cookTimeMinuteInput.getText())
                            .append("%%")
                            .append(cookTemperatureInput.getText())
                            .append("%%")
                            .append(symbolSpinner.getSelectedItem());
                }
            }
            // Add new step
            DBHandler.addNewRecipeStep(ID, stepString.toString());
            finish();
        });

        // Get cancel button
        findViewById(R.id.createNewStepCancelButton).setOnClickListener(click -> finish());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0 && findViewById(R.id.createNewRecipeStepNormalView).getVisibility() == View.GONE) {
            findViewById(R.id.createNewRecipeStepCookView).setVisibility(View.GONE);
            findViewById(R.id.createNewRecipeStepNormalView).setVisibility(View.VISIBLE);
        } else if (position == 1 && findViewById(R.id.createNewRecipeStepCookView).getVisibility() == View.GONE) {
            findViewById(R.id.createNewRecipeStepNormalView).setVisibility(View.GONE);
            findViewById(R.id.createNewRecipeStepCookView).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}