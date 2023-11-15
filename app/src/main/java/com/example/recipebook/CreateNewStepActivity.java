package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.recipebook.util.DBHandler;
import com.example.recipebook.util.StepInfo;

public class CreateNewStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_step);

        // Get input fields
        EditText stepInput = findViewById(R.id.createNewStepInput);

        // Get save button
        findViewById(R.id.createNewStepSaveButton).setOnClickListener(click -> {
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