package com.example.recipebook.util.classes;

import static com.example.recipebook.util.enums.RecipeTypeEnum.VEGAN;

import com.example.recipebook.util.enums.RecipeTypeEnum;

public class RecipeDetails {
   private final int id;
   private final String title;
   private final RecipeTypeEnum recipeType;
   private final String description;
   private final RecipeSteps recipe;

   /**
    * Constructor for RecipeCollection
    *
    * @param id          The id of the recipe collection
    * @param title       The title of the recipe collection
    * @param description The description of the recipe collection
    * @param recipe      The recipe of the recipe collection
    */
   public RecipeDetails(int id, String title, RecipeTypeEnum type, String description, RecipeSteps recipe) {
      this.id = id;
      this.title = title;
      this.recipeType = type;
      this.description = description;
      this.recipe = recipe;
   }

   /**
    * Gets the id of the recipe collection
    *
    * @return The id
    */
   public int getId() {
      return this.id;
   }

   /**
    * Gets the title of the recipe collection
    *
    * @return The title
    */
   public String getTitle() {
      if (this.recipeType == VEGAN) {
         return this.title + " (Ve)";
      }
      if (this.recipeType == RecipeTypeEnum.VEGETARIAN) {
         return this.title + " (V)";
      }
      return this.title;
   }

   /**
    * Gets the title of the recipe collection
    *
    * @return The title
    */
   public String getRawTitle() {
      return this.title;
   }

   /**
    * Gets the type of the recipe collection
    *
    * @return The type
    */
   public RecipeTypeEnum getType() {
      return this.recipeType;
   }

   /**
    * Gets the description of the recipe collection
    *
    * @return The description
    */
   public String getDescription() {
      return this.description;
   }

   /**
    * Gets the recipe of the recipe collection
    *
    * @return The recipe
    */
   public RecipeSteps getRecipe() {
      return this.recipe;
   }
}
