package com.example.recipebook;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipebook.util.enums.RecipeTypeEnum;
import com.example.recipebook.util.handlers.DBHandler;
import com.example.recipebook.util.handlers.ToastHandler;

import java.util.regex.Pattern;

public class NewRecipeActivity extends AppCompatActivity {
   private final DBHandler DBHandler = new DBHandler(this);
   private final ToastHandler ToastHandler = new ToastHandler(this);
   private RecipeTypeEnum recipeType = RecipeTypeEnum.DEFAULT;
   private final Pattern pattern = Pattern.compile("[a-zA-Z0-9\\s]*");

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_new_recipe);

      // Get title input
      EditText titleInput = findViewById(R.id.newRecipeTitleInput);
      // Get description input
      EditText descriptionInput = findViewById(R.id.newRecipeDesciptionInput);

      // Add click listener to create button
      findViewById(R.id.newRecipeCreateButton).setOnClickListener(click -> {
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

         // Check length of description
         if (descriptionInput.getText().toString().length() > 50) {
            // Send Toast message
            ToastHandler.showLongToast("Description must be 50 characters long or less");
            return;
         }

         // Check if there are any special characters and display a toast if there are
         if (!pattern.matcher(titleInput.getText().toString()).matches()) {
            // Send Toast message
            ToastHandler.showLongToast("Title contains special characters which are not allowed");
            return;
         }
         if (!pattern.matcher(descriptionInput.getText().toString()).matches()) {
            // Send Toast message
            ToastHandler.showLongToast("Description contains special characters which are not allowed");
            return;
         }

         // Add new recipe
         DBHandler.addNewRecipe(titleInput.getText().toString(), recipeType.toString(), descriptionInput.getText().toString());

         // Send Toast message
         ToastHandler.showLongToast("Recipe Created");

         // Go back to main activity
         finish();
      });

      // Add click listeners to all radio buttons
      findViewById(R.id.newRecipeDefaultRadioButton).setOnClickListener(click -> recipeType = RecipeTypeEnum.DEFAULT);
      findViewById(R.id.newRecipeVegetarianRadioButton).setOnClickListener(click -> recipeType = RecipeTypeEnum.VEGETARIAN);
      findViewById(R.id.newRecipeVeganRadioButton).setOnClickListener(click -> recipeType = RecipeTypeEnum.VEGAN);

      // Add click listener to cancel button
      findViewById(R.id.newRecipeCancelButton).setOnClickListener(click -> {
         // Go back to main activity
         finish();
      });
   }
}