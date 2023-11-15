package com.example.recipebook.util;

public class RecipeInfo {
    private int id;
    private String title;
    private String description;
    private String recipe;

    /**
     * Constructor for RecipeCollection
     * @param id The id of the recipe collection
     * @param title The title of the recipe collection
     * @param description The description of the recipe collection
     * @param recipe The recipe of the recipe collection
     */
    public RecipeInfo(int id, String title, String description, String recipe) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.recipe = recipe;
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
     * Gets the recipe of the recipe collection
     * @return The recipe
     */
    public String getRecipe() {
        return this.recipe;
    }
}
