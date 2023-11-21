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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe_step);

        // Set ID
        ID = getIntent().getIntExtra("recipeId", 0);
        // Get DBHandler
        DBHandler dbHandler = new DBHandler(this);
        // Get recipe data
        RecipeSteps recipe = dbHandler.getRecipeByID(ID).getRecipe();
        // Get steps data
        ArrayList<StepInfo> steps = recipe.getSteps();

        // Get Spinner data
        int stepCount = recipe.getStepCount();
        ArrayList<String> stepNames = new ArrayList<>();
        for (int i = 1; i <= stepCount; i++) {
            stepNames.add("Step " + i);
        }

        // Set Spinner
        Spinner stepSpinner = findViewById(R.id.editRecipeStepSpinner);
        stepSpinner.setOnItemSelectedListener(this);
        ArrayAdapter stepSpinnerArrayAdapter = new ArrayAdapter(this, R.layout.spinner, stepNames);
        stepSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        stepSpinner.setAdapter(stepSpinnerArrayAdapter);

        // Get step edit text
        EditText stepText = findViewById(R.id.editRecipeStepStep);
        stepText.setText(steps.get(0).getStep());

        // Listener for save button
        findViewById(R.id.editRecipeStepSaveButton).setOnClickListener(click -> {
            // add a check to see if any changes have been made
            if (stepText.getText().toString().equals(steps.get(stepSpinner.getSelectedItemPosition()).getStep())) {
                new ToastHandler(this).showLongToast("No changes have been made");
                return;
            }
            // Get step edit text
            EditText stepEditText = findViewById(R.id.editRecipeStepStep);
            // Get step text
            String newStep = stepEditText.getText().toString();
            // Check if the step input is empty and display a toast if it is
            if (newStep.isEmpty()) {
                new ToastHandler(this).showLongToast("Please enter a step");
                return;
            }
            // Check if the step input is empty and display a toast if it is
            if (newStep.toString().matches("[^A-Za-z0-9]")) {
                new ToastHandler(this).showLongToast("Step contains special characters which are not allowed");
                return;
            }
            // Get selected step
            int selectedStep = stepSpinner.getSelectedItemPosition();
            // Update step
            dbHandler.updateRecipeStep(ID, selectedStep, "0::"+newStep);
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
                        dbHandler.removeRecipeStep(ID, selectedStep);
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
        // Get recipe data
        RecipeSteps recipe = new DBHandler(this).getRecipeByID(ID).getRecipe();
        // Get steps data
        ArrayList<StepInfo> steps = recipe.getSteps();
        // Get step edit text
        EditText stepText = findViewById(R.id.editRecipeStepStep);
        stepText.setText(steps.get(position).getStep());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}