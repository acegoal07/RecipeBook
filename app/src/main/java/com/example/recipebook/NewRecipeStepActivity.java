package com.example.recipebook;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipebook.util.handlers.DBHandler;
import com.example.recipebook.util.handlers.ToastHandler;

import java.util.Objects;
import java.util.regex.Pattern;

public class NewRecipeStepActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int ID;
    private final DBHandler DBHandler = new DBHandler(this);
    private final ToastHandler ToastHandler = new ToastHandler(this);
    private final Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe_step);

        // Get recipe id and store it
        ID = Objects.requireNonNull(getIntent().getExtras()).getInt("recipeId");

        // Get inputs/outputs
        EditText stepInput = findViewById(R.id.newRecipeStepNormalInput);
        Spinner stepTypeSpinner = findViewById(R.id.newRecipeStepSpinner);
        Spinner symbolSpinner = findViewById(R.id.newRecipeStepCookSymbolSpinner);
        EditText cookTimeHourInput = findViewById(R.id.newRecipeCookHourInput);
        EditText cookTimeMinuteInput = findViewById(R.id.newRecipeCookMinuteInput);
        EditText cookTemperatureInput = findViewById(R.id.newRecipeStepCookTemperatureInput);

        // Set Spinners
        stepTypeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> stepSpinnerArrayAdapter = ArrayAdapter.createFromResource(this, R.array.recipe_step_types, R.layout.spinner);
        stepSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        stepTypeSpinner.setAdapter(stepSpinnerArrayAdapter);

        ArrayAdapter<CharSequence> symbolSpinnerArrayAdapter = ArrayAdapter.createFromResource(this, R.array.temperature_symbols, R.layout.spinner);
        symbolSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        symbolSpinner.setAdapter(symbolSpinnerArrayAdapter);

        // Get save button
        findViewById(R.id.newRecipeStepCreateButton).setOnClickListener(click -> {
            // Get raw step info
            String info = DBHandler.getRecipeByID(ID).getRecipe().getRawStepsString();
            // Create a string builder
            StringBuilder stepString = new StringBuilder(info != null ? info : "");
            // Type specific checks
            if (stepTypeSpinner.getSelectedItemPosition() == 0) {
                // Check if the step input is empty and display a toast if it is
                if (stepInput.getText().toString().isEmpty()) {
                    ToastHandler.showLongToast("Please enter a step");
                    return;
                }

                // Check if there are any special characters and display a toast if there are
                if (!pattern.matcher(stepInput.getText().toString()).matches()) {
                    ToastHandler.showLongToast("Step contains special characters which are not allowed");
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
                    ToastHandler.showLongToast("Please enter a cook time");
                    return;
                }

                // Check how many characters are in the cook time hour and minute inputs and display a toast if there are too many
                if (cookTimeHourInput.getText().toString().length() > 2 || cookTimeMinuteInput.getText().toString().length() > 2) {
                    ToastHandler.showLongToast("Cook time contains too many characters");
                    return;
                }

                // Checks if the cook temperature input is empty and display a toast if it is
                if (cookTemperatureInput.getText().toString().isEmpty()) {
                    ToastHandler.showLongToast("Please enter a cook temperature");
                    return;
                }

                // Checks hour and minute time inputs are valid
                if (Integer.parseInt(cookTimeHourInput.getText().toString().isEmpty() ? "0" : cookTimeHourInput.getText().toString()) > 23 || Integer.parseInt(cookTimeMinuteInput.getText().toString().isEmpty() ? "0" : cookTimeMinuteInput.getText().toString()) > 59) {
                    ToastHandler.showLongToast("Please enter a valid cook time");
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
        findViewById(R.id.newRecipeStepCancelButton).setOnClickListener(click -> finish());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0 && findViewById(R.id.newRecipeStepNormalView).getVisibility() == View.GONE) {
            findViewById(R.id.newRecipeStepCookView).setVisibility(View.GONE);
            findViewById(R.id.newRecipeStepNormalView).setVisibility(View.VISIBLE);
        } else if (position == 1 && findViewById(R.id.newRecipeStepCookView).getVisibility() == View.GONE) {
            findViewById(R.id.newRecipeStepNormalView).setVisibility(View.GONE);
            findViewById(R.id.newRecipeStepCookView).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}