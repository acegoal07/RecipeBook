package com.example.recipebook.util.recycleViewers.recipeView;

public class RecipeInfoView {
    private int id;
    private String title;
    private String description;
    private String[] ingredients;
    /**
     * Constructor for RecipeCollection
     * @param id The id of the recipe collection
     * @param title The title of the recipe collection
     * @param description The description of the recipe collection
     */
    public RecipeInfoView(int id, String title, String description, String[] ingredients) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
    }
    /**
     * Gets the id of the recipe collection
     * @return The id
     */
    public int getId() {
        return this.id;
    }
    /**
     * Gets the title of the recipe collection
     * @return The title
     */
    public String getTitle() {
        return this.title;
    }
    /**
     * Gets the description of the recipe collection
     * @return The description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the ingredients of the recipe collection
     * @return The ingredients
     */
    public String[] getIngredients() {
        return this.ingredients;
    }
}
