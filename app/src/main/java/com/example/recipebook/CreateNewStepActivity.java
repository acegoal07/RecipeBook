package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.recipebook.util.DBHandler;
import com.example.recipebook.util.StepInfo;
import com.example.recipebook.util.ToastHandler;

public class CreateNewStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_step);

        // Get input fields
        EditText stepInput = findViewById(R.id.createNewStepInput);

        // Get save button
        findViewById(R.id.createNewStepSaveButton).setOnClickListener(click -> {
            if (stepInput.getText().toString().isEmpty()) {
                new ToastHandler().showLongToast(this, "Please enter a step");
                return;
            }
            if (stepInput.getText().toString().contains("!!") || stepInput.getText().toString().contains("--")) {
                new ToastHandler().showLongToast(this, "Step contains special characters which are not allowed");
                return;
            }

            DBHandler dbHandler = new DBHandler(this);

            String info = dbHandler.readCollection(getIntent().getExtras().getInt("recipeId"))[2];
            if (info == null) {
                info = "0--"+stepInput.getText().toString();
            } else {
                StringBuilder stringBuilder = new StringBuilder(info);
                stringBuilder.append("!!0--"+stepInput.getText().toString());
                info = stringBuilder.toString();
            }

            dbHandler.addNewStep(getIntent().getExtras().getInt("recipeId"), info);
            finish();
        });

        // Get cancel button
        findViewById(R.id.createNewStepCancelButton).setOnClickListener(click -> {
            finish();
        });
    }
}