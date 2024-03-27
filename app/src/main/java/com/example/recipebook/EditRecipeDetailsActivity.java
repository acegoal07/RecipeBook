package com.example.recipebook;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipebook.util.classes.RecipeDetails;
import com.example.recipebook.util.enums.RecipeTypeEnum;
import com.example.recipebook.util.handlers.DBHandler;
import com.example.recipebook.util.handlers.ToastHandler;

import java.util.Objects;
import java.util.regex.Pattern;

public class EditRecipeDetailsActivity extends AppCompatActivity {
   private int ID;
   private final DBHandler DBHandler = new DBHandler(this);
   private final ToastHandler ToastHandler = new ToastHandler(this);
   private RecipeTypeEnum recipeType;
   private final Pattern pattern = Pattern.compile("[a-zA-Z0-9\\s]*");

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_edit_recipe_details);

      // Get recipe id
      ID = Objects.requireNonNull(getIntent().getExtras()).getInt("recipeId");

      // Get recipe info
      RecipeDetails recipeDetails = DBHandler.getRecipeByID(ID);

      // Get recipe type
      recipeType = recipeDetails.getType();

      // Get title input
      EditText titleInput = findViewById(R.id.editRecipeDetailsTitleInput);
      String tempTitle = recipeDetails.getRawTitle();
      titleInput.setText(tempTitle);

      // Get description input
      EditText descriptionInput = findViewById(R.id.editRecipeDetailsDesciptionInput);
      String tempDescription = recipeDetails.getDescription();
      descriptionInput.setText(tempDescription);

      // get radio buttons
      RadioButton defaultRecipeTypeButton = findViewById(R.id.editRecipeDetailsDefaultRadioButton);
      RadioButton vegetarianRecipeTypeButton = findViewById(R.id.editRecipeDetailsVegetarianRadioButton);
      RadioButton veganRecipeTypeButton = findViewById(R.id.editRecipeDetailsVeganRadioButton);

      if (recipeDetails.getType() == RecipeTypeEnum.DEFAULT) {
         defaultRecipeTypeButton.setChecked(true);
      } else if (recipeDetails.getType() == RecipeTypeEnum.VEGETARIAN) {
         vegetarianRecipeTypeButton.setChecked(true);
      } else if (recipeDetails.getType() == RecipeTypeEnum.VEGAN) {
         veganRecipeTypeButton.setChecked(true);
      }

      // Add click listener to save button
      findViewById(R.id.editRecipeDetailsSaveButton).setOnClickListener(click -> {
         // Check if any changes were made
         if (titleInput.getText().toString().equals(tempTitle) &&
                 descriptionInput.getText().toString().equals(tempDescription) &&
                 recipeDetails.getType() == recipeType) {
            // Send Toast message
            ToastHandler.showLongToast("No changes made");
            return;
         } else {
            // Check if title was changed
            if (!titleInput.getText().toString().equals(tempTitle)) {
               // Make sure title is not empty
               if (titleInput.getText().toString().isEmpty()) {
                  // Send Toast message
                  ToastHandler.showLongToast("Please fill in the title field");
                  return;
               }

               // Check length of title
               if (titleInput.getText().toString().length() > 24) {
                  // Send Toast message
                  ToastHandler.showLongToast("Title must be 24 characters long or less");
                  return;
               }

               // Check if there are any special characters and display a toast if there are
               if (!pattern.matcher(titleInput.getText().toString()).matches()) {
                  // Send Toast message
                  ToastHandler.showLongToast("Title contains special characters which are not allowed");
                  return;
               }

               // Update database
               DBHandler.updateRecipeTitle(ID, titleInput.getText().toString());
            }
            // Check if description was changed
            if (!descriptionInput.getText().toString().equals(tempDescription)) {
               // Check length of description
               if (descriptionInput.getText().toString().length() > 50) {
                  // Send Toast message
                  ToastHandler.showLongToast("Description must be 50 characters long or less");
                  return;
               }
               // Check if there are any special characters and display a toast if there are
               if (!pattern.matcher(descriptionInput.getText().toString()).matches()) {
                  // Send Toast message
                  ToastHandler.showLongToast("Description contains special characters which are not allowed");
                  return;
               }
               // Update database
               DBHandler.updateRecipeDescription(ID, descriptionInput.getText().toString());
            }
            // Check if recipe type was changed
            if (recipeDetails.getType() != recipeType) {
               // Update database
               DBHandler.updateRecipeType(ID, recipeType.toString());
            }
         }
         // Send toast
         ToastHandler.showLongToast("Changes Saved");
         // Go back to main activity
         finish();
      });

      // Add click listener to cancel button
      findViewById(R.id.editRecipeDetailsCancelButton).setOnClickListener(click -> {
         // Go back to main activity
         finish();
      });

      // Add click listeners to all radio buttons
      findViewById(R.id.editRecipeDetailsDefaultRadioButton).setOnClickListener(click -> recipeType = RecipeTypeEnum.DEFAULT);
      findViewById(R.id.editRecipeDetailsVegetarianRadioButton).setOnClickListener(click -> recipeType = RecipeTypeEnum.VEGETARIAN);
      findViewById(R.id.editRecipeDetailsVeganRadioButton).setOnClickListener(click -> recipeType = RecipeTypeEnum.VEGAN);

      // Add click listener to delete button
      findViewById(R.id.editRecipeDetailsDeleteButton).setOnClickListener(click -> new AlertDialog.Builder(this)
              .setTitle("Delete Recipe")
              .setMessage("Are you sure you want to delete this recipe?")
              .setPositiveButton("Yes", (dialog, which) -> {
                 // Delete recipe
                 DBHandler.removeRecipeById(ID);
                 // Send toast
                 ToastHandler.showLongToast("Recipe Deleted");
                 // Go back to main activity
                 finish();
              })
              .setNegativeButton("No", (dialog, which) -> {
              })
              .setIcon(android.R.drawable.ic_dialog_alert)
              .show());
   }
}