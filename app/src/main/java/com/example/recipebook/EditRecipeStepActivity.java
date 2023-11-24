package com.example.recipebook;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipebook.util.DBHandler;
import com.example.recipebook.util.RecipeSteps;
import com.example.recipebook.util.StepInfo;
import com.example.recipebook.util.ToastHandler;

import java.util.ArrayList;

public class EditRecipeStepActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int ID;
    private final DBHandler DBHandler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe_step);

        // Set ID
        ID = getIntent().getIntExtra("recipeId", 0);
        // Get recipe data
        RecipeSteps recipe = DBHandler.getRecipeByID(ID).getRecipe();
        // Get steps data
        ArrayList<StepInfo> steps = recipe.getSteps();

        // Get inputs/output
        EditText stepEditText = findViewById(R.id.editRecipeStepNormalInput);
        EditText cookTimeHourInput = findViewById(R.id.editRecipeStepCookHourInput);
        EditText cookTimeMinuteInput = findViewById(R.id.editRecipeStepCookMinuteInput);
        EditText cookTemperatureInput = findViewById(R.id.editRecipeStepCookTemperatureInput);
        Spinner stepSpinner = findViewById(R.id.editRecipeStepSpinner);
        Spinner cookTemperatureSymbolSpinner = findViewById(R.id.editRecipeStepCookTemperatureSymbolSpinner);

        // Get Spinner data
        int stepCount = recipe.getStepCount();
        ArrayList<String> stepCounter = new ArrayList<>();
        for (int i = 1; i <= stepCount; i++) {
            stepCounter.add("Step " + i);
        }

        // Set Spinners
        stepSpinner.setOnItemSelectedListener(this);
        ArrayAdapter stepSpinnerArrayAdapter = new ArrayAdapter(this, R.layout.spinner, stepCounter);
        stepSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        stepSpinner.setAdapter(stepSpinnerArrayAdapter);

        ArrayAdapter cookTemperatureSymbolSpinnerArrayAdapter = ArrayAdapter.createFromResource(this, R.array.temperature_symbols, R.layout.spinner);
        cookTemperatureSymbolSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        cookTemperatureSymbolSpinner.setAdapter(cookTemperatureSymbolSpinnerArrayAdapter);

        // Refresh view
        refreshView(0);

        // Listener for save button
        findViewById(R.id.editRecipeStepSaveButton).setOnClickListener(click -> {
            if (steps.get(stepSpinner.getSelectedItemPosition()).getStepType() == StepInfo.RecipeStepType.NORMAL) {
                // add a check to see if any changes have been made
                if (stepEditText.getText().toString().equals(steps.get(stepSpinner.getSelectedItemPosition()).getStep())) {
                    new ToastHandler(this).showLongToast("No changes have been made");
                    return;
                }
                // Check if the step input is empty and display a toast if it is
                if (stepEditText.getText().toString().isEmpty()) {
                    new ToastHandler(this).showLongToast("Please enter a step");
                    return;
                }
                // Check if the step input is empty and display a toast if it is
                if (stepEditText.getText().toString().matches("[^A-Za-z0-9]")) {
                    new ToastHandler(this).showLongToast("Step contains special characters which are not allowed");
                    return;
                }
                // Update step
                DBHandler.updateRecipeStep(ID, stepSpinner.getSelectedItemPosition(), "0::"+stepEditText.getText().toString());
            } else if (steps.get(stepSpinner.getSelectedItemPosition()).getStepType() == StepInfo.RecipeStepType.COOK) {
                // Check if any changes have been made
                if (cookTimeHourInput.getText().toString().equals(steps.get(stepSpinner.getSelectedItemPosition()).getCookHour()) &&
                        cookTimeMinuteInput.getText().toString().equals(steps.get(stepSpinner.getSelectedItemPosition()).getCookMinute()) &&
                        cookTemperatureInput.getText().toString().equals(steps.get(stepSpinner.getSelectedItemPosition()).getCookTemperature()) &&
                        cookTemperatureSymbolSpinnerArrayAdapter.getItem(cookTemperatureSymbolSpinner.getSelectedItemPosition()).equals(steps.get(stepSpinner.getSelectedItemPosition()).getCookTemperatureSymbolPosition())) {
                    new ToastHandler(this).showLongToast("No changes have been made");
                    return;
                }
                // make sure theres a time input
                if (cookTimeHourInput.getText().toString().isEmpty() && cookTimeMinuteInput.getText().toString().isEmpty()) {
                    new ToastHandler(this).showLongToast("Please enter a time");
                    return;
                }
                // make sure theres a temperature input
                if (cookTemperatureInput.getText().toString().isEmpty()) {
                    new ToastHandler(this).showLongToast("Please enter a temperature");
                    return;
                }
                // Update step
                DBHandler.updateRecipeStep(ID, stepSpinner.getSelectedItemPosition(),"1::"+cookTimeHourInput.getText()+"%%"+cookTimeMinuteInput.getText()+"%%"+cookTemperatureInput.getText()+"%%"+cookTemperatureSymbolSpinner.getSelectedItem());
            }
            // Finish activity
            finish();
        });

        // Listener for delete button
        findViewById(R.id.editRecipeStepDeleteStepButton).setOnClickListener(click -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Step")
                    .setMessage("Are you sure you want to delete this Step?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Get selected step
                        int selectedStep = stepSpinner.getSelectedItemPosition();
                        // Delete step
                        DBHandler.removeRecipeStep(ID, selectedStep);
                        // Finish activity
                        finish();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        return;
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });

        // Listener for cancel button
        findViewById(R.id.editRecipeStepCancelButton).setOnClickListener(click -> {
            finish();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        refreshView(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void refreshView(int position) {
        // Get recipe data
        RecipeSteps recipe = DBHandler.getRecipeByID(ID).getRecipe();
        // Get steps data
        ArrayList<StepInfo> steps = recipe.getSteps();

        // Get views
        findViewById(R.id.editRecipeStepNormalView).setVisibility(View.GONE);
        findViewById(R.id.editRecipeStepCookView).setVisibility(View.GONE);

        // Get step edit text
        EditText stepEditText = findViewById(R.id.editRecipeStepNormalInput);
        stepEditText.setText("");
        EditText cookTimeHourInput = findViewById(R.id.editRecipeStepCookHourInput);
        cookTimeHourInput.setText("");
        EditText cookTimeMinuteInput = findViewById(R.id.editRecipeStepCookMinuteInput);
        cookTimeMinuteInput.setText("");
        EditText cookTemperatureInput = findViewById(R.id.editRecipeStepCookTemperatureInput);
        cookTemperatureInput.setText("");
        Spinner cookTemperatureSymbolSpinner = findViewById(R.id.editRecipeStepCookTemperatureSymbolSpinner);
        cookTemperatureSymbolSpinner.setSelection(0);

        // Stored data to view
        if (steps.get(position).getStepType() == StepInfo.RecipeStepType.NORMAL) {
            findViewById(R.id.editRecipeStepNormalView).setVisibility(View.VISIBLE);
            stepEditText.setText(steps.get(position).getStep());
        } else if (steps.get(position).getStepType() == StepInfo.RecipeStepType.COOK) {
            findViewById(R.id.editRecipeStepCookView).setVisibility(View.VISIBLE);
            cookTimeHourInput.setText(steps.get(position).getCookHour());
            cookTimeMinuteInput.setText(steps.get(position).getCookMinute());
            cookTemperatureInput.setText(steps.get(position).getCookTemperature());
            cookTemperatureSymbolSpinner.setSelection(Integer.parseInt(steps.get(position).getCookTemperatureSymbolPosition()));
        }
    }
}